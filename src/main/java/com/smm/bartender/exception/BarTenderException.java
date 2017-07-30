package com.smm.bartender.exception;

import java.io.IOException;
import java.util.Properties;

public class BarTenderException extends Exception {

    private static final long serialVersionUID = 3418115173867797817L;

    public static final Integer CODE_400_VALUE = 400;
    public static final Integer CODE_404_VALUE = 404;
    public static final Integer CODE_429_VALUE = 429;
    public static final Integer CODE_500_VALUE = 500;

    private Integer errorCode;

    public BarTenderException(Integer code) {
        super(getCodeText(code));
        this.errorCode = code;
    }

    public BarTenderException(Integer code, Throwable throwable) {
        super(getCodeText(code), throwable);
        this.errorCode = code;
    }

    private static String getCodeText(Integer code) {
        String errorMessage = "(Error message could not be retrieved)";

        try {
            Properties prop = new Properties();
            prop.load(BarTenderException.class.getClassLoader().getResourceAsStream("configuration/application/BarTender.properties"));
            errorMessage = prop.getProperty("api.error." + code);
        } catch (IOException e) {
            // Properties could not be loaded. Use initialisation value.
        }
        return errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
