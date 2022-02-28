package fr.senioradom.testApp.service;

import fr.senioradom.testApp.dto.GPSPointDTO;
import fr.senioradom.testApp.model.GPSPoint;

import java.util.List;

public interface GPSPointService {
    GPSPointDTO createGPSPoint(GPSPointDTO point);
    GPSPointDTO getGPSPoint(Long id);
    List<GPSPointDTO> getAllGPSPoints();
    void deleteGPSPoint(Long id);
    boolean distanceBetweenPoints(Long idFirstPoint, Long idSecondPoint);
}
