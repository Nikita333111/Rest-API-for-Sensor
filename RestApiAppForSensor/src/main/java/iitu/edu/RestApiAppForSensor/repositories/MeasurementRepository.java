package iitu.edu.RestApiAppForSensor.repositories;

import iitu.edu.RestApiAppForSensor.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {
    int countAllByRainingIsTrue();
}
