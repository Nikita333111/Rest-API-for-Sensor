package iitu.edu.RestApiAppForSensor.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "Measurement")
@Getter
@Setter
@NoArgsConstructor
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotNull
    @Min(value = -100, message = "value must be between -100 and 100")
    @Max(value = 100, message = "value must be between -100 and 100")
    @Column(name = "value")
    private float value;
    @NotNull
    @Column(name = "raining")
    private boolean raining;

    @Column(name = "check_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sensor_name",referencedColumnName = "name")
    //@JsonBackReference
    private Sensor sensor;

    public Measurement(float value, boolean raining, Sensor sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                ", value=" + value +
                ", raining=" + raining +
                '}';
    }
}
