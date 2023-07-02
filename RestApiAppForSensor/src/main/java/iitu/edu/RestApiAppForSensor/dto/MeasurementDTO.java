package iitu.edu.RestApiAppForSensor.dto;

import iitu.edu.RestApiAppForSensor.models.Sensor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementDTO {
    @NotNull
    @Min(value = -100, message = "value must be between -100 and 100")
    @Max(value = 100, message = "value must be between -100 and 100")
    private float value;

    @NotNull
    private boolean raining;

    @NotNull
    private SensorDTO sensor;
}
