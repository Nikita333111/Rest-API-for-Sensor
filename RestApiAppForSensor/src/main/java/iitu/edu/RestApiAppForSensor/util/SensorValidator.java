package iitu.edu.RestApiAppForSensor.util;

import iitu.edu.RestApiAppForSensor.dto.SensorDTO;
import iitu.edu.RestApiAppForSensor.models.Sensor;
import iitu.edu.RestApiAppForSensor.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensor = (SensorDTO) target;

        if(sensorService.isSensorExist(sensor.getName()))
            errors.rejectValue("name","","Such sensor already exists");
    }
}
