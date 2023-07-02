package iitu.edu.RestApiAppForSensor.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SensorDTO {
    @NotNull
    @Size(min = 3,max = 30, message = "Sensor's name should contain at least 3 and maximum 30 characters")
    private String name;
}
