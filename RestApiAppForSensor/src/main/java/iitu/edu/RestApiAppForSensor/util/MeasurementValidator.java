package iitu.edu.RestApiAppForSensor.util;

import iitu.edu.RestApiAppForSensor.dto.MeasurementDTO;
import iitu.edu.RestApiAppForSensor.models.Measurement;
import iitu.edu.RestApiAppForSensor.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurement = (MeasurementDTO) target;

        if(sensorService.isSensorExist(measurement.getSensor().getName())){
            return;
        }
        errors.rejectValue("sensor","","Such sensor for measurement does not exist");
    }
}
