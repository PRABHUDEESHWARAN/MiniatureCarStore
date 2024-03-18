package com.project.carstore.security;
public class AuthenticationResponse{
    private String token;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthenticationResponse() {
    }

    public String getToken() {
        return token;
    }

    public AuthenticationResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }
}
