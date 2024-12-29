package org.example.dto;

public class ErrorMessage {

    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorMessage(String message) {
        this.message = message;
    }

    public ErrorMessage(){};

}
