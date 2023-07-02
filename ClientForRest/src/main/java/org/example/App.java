package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.responseModels.dto.MeasurementDTO;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws IOException {
       /* registerSensor("sensor_from_client");
        int ten;
        for(int i = 0;i < 1000;i++){
            if(i % 2 == 0)
                ten = -10;
            else ten = 10;

            float value = new Random().nextFloat() * ten;
            boolean raining = new Random().nextBoolean();

            sendMeasurement(value,raining,"sensor_from_client");
        }*/

        MeasurementDTO[] measurements = getMeasurements();

        for (MeasurementDTO measurement : measurements){
            System.out.println(measurement);
        }
    }


    public static void registerSensor(String sensorName){
        String url = "http://localhost:8080/sensors/registration";

        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("name", sensorName);
        makePostRequest(url,jsonMap);
    }

    public static void sendMeasurement(float value, boolean raining, String sensorName){
        String url = "http://localhost:8080/measurements/add";

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("value",value);
        jsonMap.put("raining", raining);
        jsonMap.put("sensor", Map.of("name",sensorName));

        makePostRequest(url,jsonMap);
    }


    public static void makePostRequest(String url, Object jsonMap){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(jsonMap,headers);

        try {
            restTemplate.exchange(url,HttpMethod.POST,request,String.class);

            System.out.println("Успешно отправленно на сервер");
        } catch (HttpClientErrorException e){
            System.out.println("Ошибка");
            System.out.println(e.getMessage());
        }
    }


    public static MeasurementDTO[] getMeasurements(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/measurements";

        ResponseEntity<MeasurementDTO[]> response = restTemplate.getForEntity(url,MeasurementDTO[].class);

        if(response.getBody() == null || response.getBody()[0] == null)
            return null;

        return response.getBody();
    }
}
