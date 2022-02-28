package fr.senioradom.testApp.controller;

import fr.senioradom.testApp.dto.GPSPointDTO;
import fr.senioradom.testApp.service.GPSPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static  fr.senioradom.testApp.utils.Constants.*;

@RestController
@RequestMapping(APP_ROOT_PATH)
@CrossOrigin(origins =APP_CROSS_ORIGIN_URL)
public class GPSPointController {

    private GPSPointService gpsPointService;

    @Autowired
    public GPSPointController(GPSPointService gpsPointService) {
        this.gpsPointService = gpsPointService;
    }

    @GetMapping("{id}")
    public GPSPointDTO getGPSPoint(@PathVariable("id") long id){
        return gpsPointService.getGPSPoint(id);

    }

    @PostMapping
    public GPSPointDTO createGPSPoint(@RequestBody GPSPointDTO point){
        return gpsPointService.createGPSPoint(point);
    }
    @GetMapping
    public List<GPSPointDTO> getListGPSPoints(){
        return gpsPointService.getAllGPSPoints();
    }

    @DeleteMapping("{id}")
    public  void deleteGPSPoint(@PathVariable("id") long id){
         gpsPointService.deleteGPSPoint(id);
    }
    @GetMapping("/distance/{firstId}/{secondId}")
    public boolean calculateDistance(@PathVariable("firstId") long firstId,@PathVariable("secondId") long secondId){
        return gpsPointService.distanceBetweenPoints(firstId,secondId);
    }


}
