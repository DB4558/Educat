package com.example.educat_be.Response;

public class FinalResponse {

    String message;

    Boolean status;

    String accesstoken;

    public FinalResponse() {
    }

    public FinalResponse(String message, Boolean status, String accesstoken) {
        this.message = message;
        this.status = status;
        this.accesstoken = accesstoken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }
}
