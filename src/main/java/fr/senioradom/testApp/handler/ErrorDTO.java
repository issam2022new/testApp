package fr.senioradom.testApp.handler;

import fr.senioradom.testApp.exception.ErrorCodes;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDTO {

    private Integer httpCode;
    private ErrorCodes code;
    private String message;
    private List<String> errors;

}
