package fr.senioradom.testApp.validator;

import fr.senioradom.testApp.dto.GPSPointDTO;
import java.util.ArrayList;
import java.util.List;

public class GPSPointValidator {

    public static List<String> validate(GPSPointDTO gpsPointDTO){
        List<String> errors = new ArrayList<>();
        if(null==gpsPointDTO){
            errors.add("PGS Point mustn't be null");
        }
        if(null != gpsPointDTO && 0==gpsPointDTO.getLatitude()){
            errors.add("Latitude mustn't be equal to ZERO");
        }
        if(null != gpsPointDTO && 0==gpsPointDTO.getLongitude()){
            errors.add("Longitude mustn't be equal to ZERO");
        }
        return errors;
    }
}
