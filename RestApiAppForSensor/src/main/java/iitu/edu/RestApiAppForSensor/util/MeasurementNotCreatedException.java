package iitu.edu.RestApiAppForSensor.util;

public class MeasurementNotCreatedException extends RuntimeException{
    public MeasurementNotCreatedException(String message){
        super(message);
    }
}
