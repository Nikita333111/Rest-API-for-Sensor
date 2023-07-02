package iitu.edu.RestApiAppForSensor.controllers;

import iitu.edu.RestApiAppForSensor.dto.SensorDTO;
import iitu.edu.RestApiAppForSensor.models.Sensor;
import iitu.edu.RestApiAppForSensor.services.SensorService;
import iitu.edu.RestApiAppForSensor.util.SensorErrorResponse;
import iitu.edu.RestApiAppForSensor.util.SensorNotCreatedException;
import iitu.edu.RestApiAppForSensor.util.SensorValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, SensorValidator sensorValidator, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> addSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                BindingResult bindingResult){
        //проверка что в бд нет таких сенсоров
        sensorValidator.validate(sensorDTO,bindingResult);
        if(bindingResult.hasErrors()){
            StringBuilder errorsMsg = new StringBuilder();

            for (FieldError error : bindingResult.getFieldErrors()){
                errorsMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new SensorNotCreatedException(errorsMsg.toString());
        }
        // В бд нет сенсоров с таким именем
        sensorService.save(convertToPerson(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }



    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e){
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    public Sensor convertToPerson(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO,Sensor.class);
    }

    public SensorDTO convertToDTO(Sensor sensor){
        return modelMapper.map(sensor,SensorDTO.class);
    }
}
