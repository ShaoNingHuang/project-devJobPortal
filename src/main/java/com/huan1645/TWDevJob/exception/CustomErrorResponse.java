package com.huan1645.TWDevJob.exception;

import java.time.LocalDateTime;
import java.util.Date;

public class CustomErrorResponse {
    private int statusCode;
    private String message;
    private Date timestamp;


    public CustomErrorResponse() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
