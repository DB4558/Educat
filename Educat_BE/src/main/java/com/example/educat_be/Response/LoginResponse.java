package com.example.educat_be.Response;

import com.example.educat_be.DTO.TokenDTO;

public class LoginResponse {
    String message;
    Boolean status;
    TokenDTO tokenDTO;

    public LoginResponse(String message, Boolean status, TokenDTO tokenDTO) {
        this.message = message;
        this.status = status;
        this.tokenDTO = tokenDTO;
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

    public TokenDTO getTokenDTO() {
        return tokenDTO;
    }

    public void setTokenDTO(TokenDTO tokenDTO) {
        this.tokenDTO = tokenDTO;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", tokenDTO=" + tokenDTO +
                '}';
    }
}
