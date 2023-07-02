package iitu.edu.RestApiAppForSensor.controllers;

import iitu.edu.RestApiAppForSensor.dto.MeasurementDTO;
import iitu.edu.RestApiAppForSensor.dto.SensorDTO;
import iitu.edu.RestApiAppForSensor.models.Measurement;
import iitu.edu.RestApiAppForSensor.models.Sensor;
import iitu.edu.RestApiAppForSensor.services.MeasurementService;
import iitu.edu.RestApiAppForSensor.util.MeasurementErrorResponse;
import iitu.edu.RestApiAppForSensor.util.MeasurementNotCreatedException;
import iitu.edu.RestApiAppForSensor.util.MeasurementValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementValidator measurementValidator;
    private final MeasurementService measurementService;

    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementValidator measurementValidator, MeasurementService measurementService, ModelMapper modelMapper) {
        this.measurementValidator = measurementValidator;
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<MeasurementDTO> getMeasurements(){
        return measurementService.getAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public int getRainyDaysCount(){
        return measurementService.RainyDaysCount();
    }


    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult){
        // проверять что сенсор из измерении зарегестрирован в бд, если нет выдавать ошибку.
        measurementValidator.validate(measurementDTO,bindingResult);

        if(bindingResult.hasErrors()){
            StringBuilder errorsMsg = new StringBuilder();

            for(FieldError error : bindingResult.getFieldErrors()){
                errorsMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new MeasurementNotCreatedException(errorsMsg.toString());
        }

        //сенсор из температуры есть в бд - сохраняем
        measurementService.save(convertToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException e){
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }



    public Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        System.out.println(measurementDTO.getSensor());
        System.out.println(measurement.getSensor().getName());
        return measurement;
    }

    public MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
