package iitu.edu.RestApiAppForSensor.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "Sensor")
@Getter
@Setter
@NoArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotNull
    @Size(min = 3,max = 30, message = "Sensor's name should contain at least 3 and maximum 30 characters")
    @Column(name = "name")
    private String name;

    //@JsonManagedReference
    @OneToMany(mappedBy = "sensor",fetch = FetchType.LAZY)
    List<Measurement> measurements;

    public Sensor(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Sensor{" +
                "name='" + name + '\'' +
                '}';
    }
}
