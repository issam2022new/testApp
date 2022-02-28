package fr.senioradom.testApp.service;

import fr.senioradom.testApp.dto.GPSPointDTO;
import fr.senioradom.testApp.exception.EntityNotFoundException;
import fr.senioradom.testApp.exception.EntityNotValidException;
import fr.senioradom.testApp.exception.ErrorCodes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GPSPointServiceTest {

    @Autowired
    private GPSPointService service;

    @Test
    public void shouldSaveGPSPointWithSuccess(){
        GPSPointDTO expectedPoint = GPSPointDTO.builder()
                                .longitude(12.12546)
                                .latitude(12)
                                .build();
        GPSPointDTO savedPoint = service.createGPSPoint(expectedPoint);
        assertNotNull(savedPoint);
        assertNotNull(savedPoint.getId());
        assertEquals(expectedPoint.getLatitude(),savedPoint.getLatitude());
        assertEquals(expectedPoint.getLongitude(),savedPoint.getLongitude());

    }

    @Test
    public void shouldThrowInvalidEntityException( ) {
        GPSPointDTO point = GPSPointDTO.builder()
                            .build();
        EntityNotValidException exception = assertThrows(EntityNotValidException.class, ()->{
           service.createGPSPoint(point);
        });

        assertEquals(ErrorCodes.GPSPOINT_NOT_VALID,exception.getErrorCodes());

    }
    @Test
    public void shouldThrowEntityNotFoundExceptionWhenSave(){
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,()->{
            service.getGPSPoint(0L);
        });
        assertEquals(ErrorCodes.GPSPOINT_NOT_FOUND,exception.getErrorCodes());
        assertEquals("GPS Point Not Found with id 0",exception.getMessage());
    }

    @Test
    public void shouldReturnTrueWhenCalculateDistanceBetweenSamePoints(){
        GPSPointDTO fisrtPoint=GPSPointDTO.builder()
                .latitude(12.5)
                .longitude(12.45585)
                .build();

        GPSPointDTO  saved= service.createGPSPoint(fisrtPoint);
        boolean result = service.distanceBetweenPoints(saved.getId(),saved.getId());
        assertEquals(true,result);
    }

    @Test
    public void shouldThrowEntityNotValidExceptionWhenCalculateDistanceWithGPSPointNullID(){
        GPSPointDTO point=GPSPointDTO.builder()
                .latitude(12.5)
                .longitude(12.45585)
                .build();

        EntityNotValidException exception= assertThrows(EntityNotValidException.class,()->{
            service.distanceBetweenPoints(point.getId(), point.getId());
        });
        assertEquals(ErrorCodes.GPSPOINT_NOT_VALID,exception.getErrorCodes());
        assertEquals("GPS Point ID is null",exception.getMessage());
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionWhenCalculateDistanceWithGPSPointNotExist(){

        Long FIRST_ID = 125L;
        Long SECOND_ID = 125L;

        EntityNotFoundException exception= assertThrows(EntityNotFoundException.class,()->{
            service.distanceBetweenPoints(FIRST_ID, SECOND_ID);
        });
        assertEquals(ErrorCodes.GPSPOINT_NOT_FOUND,exception.getErrorCodes());
        assertEquals("GPS Point Not Found with id 125",exception.getMessage());
    }

    @Test
    public void shouldReturnTrueWhenCalculateDistanceWith2GPSPointsLessthan10KM(){

        GPSPointDTO first = GPSPointDTO.builder()
                                            .latitude(48.815418)
                                            .longitude(2.297650)
                                            .build();
        GPSPointDTO second = GPSPointDTO.builder()
                .latitude(48.819259)
                .longitude(2.303504)
                .build();
        GPSPointDTO MALAKOF_METRO_LOCATION= service.createGPSPoint(first);
        GPSPointDTO SENIOR_ADOM_LOCATION= service.createGPSPoint(second);
        boolean result = service.distanceBetweenPoints(SENIOR_ADOM_LOCATION.getId(),MALAKOF_METRO_LOCATION.getId());

        assertEquals(true,result);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionWhenGetGPSPointWith0ID(){
        service.getGPSPoint(0L);
    }

}