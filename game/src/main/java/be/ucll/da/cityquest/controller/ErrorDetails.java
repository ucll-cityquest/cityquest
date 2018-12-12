package be.ucll.da.cityquest.controller;

import java.util.Date;

class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;

    ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}