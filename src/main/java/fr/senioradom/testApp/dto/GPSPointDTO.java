package fr.senioradom.testApp.dto;

import fr.senioradom.testApp.model.GPSPoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GPSPointDTO {
    private Long id;
    private double longitude;
    private double latitude;

    public static GPSPointDTO fromEntity(GPSPoint point){
        if(point == null){
            return null;
        }
        return GPSPointDTO.builder()
                .id(point.getId())
                .latitude(point.getLatitude())
                .longitude(point.getLongitude())
                .build();
    }
    public static GPSPoint toEntity(GPSPointDTO dto){
        if(dto == null){
            return null;
        }
        return GPSPoint.builder()
                .id(dto.getId())
                .longitude(dto.getLongitude())
                .latitude(dto.getLatitude())
                .build();
    }
}
