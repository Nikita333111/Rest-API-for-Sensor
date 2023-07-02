package iitu.edu.RestApiAppForSensor.services;

import iitu.edu.RestApiAppForSensor.models.Measurement;
import iitu.edu.RestApiAppForSensor.models.Sensor;
import iitu.edu.RestApiAppForSensor.repositories.MeasurementRepository;
import iitu.edu.RestApiAppForSensor.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }

    public List<Measurement> getAll(){
        return measurementRepository.findAll();
    }

    public int RainyDaysCount(){
        return measurementRepository.countAllByRainingIsTrue();
    }

    @Transactional
    public void save(Measurement measurement){
        completeMeasurement(measurement);

        measurementRepository.save(measurement);
    }

    private void completeMeasurement(Measurement measurement){
        measurement.setDate(new Date());

        Sensor sensor = sensorRepository.findSensorByName(measurement.getSensor().getName()).get();
        measurement.setSensor(sensor);
    }
}
