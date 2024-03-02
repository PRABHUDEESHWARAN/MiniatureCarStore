package com.project.carstore.security;
public class AuthenticationResponse{
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public AuthenticationResponse() {
    }

    public String getToken() {
        return token;
    }

    public AuthenticationResponse(String token) {
        this.token = token;
    }
}
