package fr.senioradom.testApp.service;

import fr.senioradom.testApp.dto.GPSPointDTO;
import fr.senioradom.testApp.exception.EntityNotFoundException;
import fr.senioradom.testApp.exception.EntityNotValidException;
import fr.senioradom.testApp.exception.ErrorCodes;
import fr.senioradom.testApp.model.GPSPoint;
import fr.senioradom.testApp.repository.GPSPointRepository;
import fr.senioradom.testApp.validator.GPSPointValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GPSPointServiceImpl implements GPSPointService {

    private GPSPointRepository gpsPointRepository;

    @Autowired
    public GPSPointServiceImpl(GPSPointRepository gpsPointRepository) {
        this.gpsPointRepository = gpsPointRepository;
    }

    @Override
    public GPSPointDTO createGPSPoint(GPSPointDTO point) {

        List<String> errors = GPSPointValidator.validate(point);
        if(!errors.isEmpty()){
            log.error("GPS Point is not valid {}",point);
            throw new EntityNotValidException("GPS Point is not Valid", ErrorCodes.GPSPOINT_NOT_VALID,errors);
        }

        return GPSPointDTO.fromEntity(
                gpsPointRepository.save(
                        GPSPointDTO.toEntity(point)
                )
        );
    }

    @Override
    public GPSPointDTO getGPSPoint(Long id) {
        if (null == id){
            log.error("GPS Point ID is null");
            throw  new EntityNotValidException("GPS Point ID is null", ErrorCodes.GPSPOINT_NOT_VALID);
        }
        GPSPoint gpsPoint = gpsPointRepository.findById(id).orElseThrow( () ->
                        new EntityNotFoundException("GPS Point Not Found with id "+ id, ErrorCodes.GPSPOINT_NOT_FOUND)
        );

        return GPSPointDTO.fromEntity(gpsPoint);
    }

    @Override
    public List<GPSPointDTO> getAllGPSPoints() {
        return gpsPointRepository.findAll()
                .stream()
                .map(GPSPointDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteGPSPoint(Long id) {

        Optional<GPSPoint> point = gpsPointRepository.findById(id);
        if(!point.isPresent()){
            log.error("Can't delete a non existing GPS Point");
            throw  new EntityNotFoundException("Can't delete a non existing GPS Point",ErrorCodes.GPSPOINT_NOT_FOUND);
        }
        gpsPointRepository.delete(point.get());
    }


    private boolean distanceBetweenPoints(GPSPointDTO firstPoint, GPSPointDTO secondPoint) {
        List<String> firstErrors = GPSPointValidator.validate(firstPoint);
        List<String> secondErrors = GPSPointValidator.validate(secondPoint);

        if(!firstErrors.isEmpty()){
            log.error("GPS Point is not valid {}",firstPoint);
            throw new EntityNotValidException("GPS Point is not Valid", ErrorCodes.GPSPOINT_NOT_VALID);
        }
        if(!secondErrors.isEmpty()){
            log.error("GPS Point is not valid {}",secondErrors);
            throw new EntityNotValidException("GPS Point is not Valid", ErrorCodes.GPSPOINT_NOT_VALID);
        }
        if(firstPoint.getLatitude()==secondPoint.getLatitude() &&
            firstPoint.getLongitude()==secondPoint.getLongitude()
        ){
            return true;
        }
        else {
            double theta = firstPoint.getLongitude() - secondPoint.getLongitude();
            double dist = Math.sin(Math.toRadians(firstPoint.getLatitude()))
                    * Math.sin(Math.toRadians(secondPoint.getLatitude()))
                    + Math.cos(Math.toRadians(firstPoint.getLatitude()))
                    * Math.cos(Math.toRadians(secondPoint.getLatitude()))
                    * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;
            return (dist<=10);
        }
    }

    @Override
    public boolean distanceBetweenPoints(Long idFirstPoint, Long idSecondPoint) {
        GPSPointDTO firstPoint= this.getGPSPoint(idFirstPoint);
        GPSPointDTO secondPoint = this.getGPSPoint(idSecondPoint);
        return distanceBetweenPoints(firstPoint, secondPoint);
    }


}
