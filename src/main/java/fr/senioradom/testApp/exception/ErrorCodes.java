package fr.senioradom.testApp.exception;

import lombok.Getter;

public enum ErrorCodes {

    GPSPOINT_NOT_FOUND(100),
    GPSPOINT_NOT_VALID(101);
    @Getter
    private  int code;

    ErrorCodes(int code) {
        this.code = code;
    }
}
