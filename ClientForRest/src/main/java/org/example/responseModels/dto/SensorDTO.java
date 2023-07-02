package org.example.responseModels.dto;

public class SensorDTO {
    private String name;

    public SensorDTO(String name) {
        this.name = name;
    }

    public SensorDTO(){}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SensorDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
