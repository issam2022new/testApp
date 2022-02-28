package fr.senioradom.testApp.repository;

import fr.senioradom.testApp.model.GPSPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GPSPointRepository extends JpaRepository<GPSPoint,Long> {
}
