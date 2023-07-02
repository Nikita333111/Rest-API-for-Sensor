package iitu.edu.RestApiAppForSensor.services;

import iitu.edu.RestApiAppForSensor.models.Sensor;
import iitu.edu.RestApiAppForSensor.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }


    public boolean isSensorExist(String name){
        return sensorRepository.findSensorByName(name).isPresent();
    }

    @Transactional
    public void save(Sensor sensor){
        sensorRepository.save(sensor);
    }
}
