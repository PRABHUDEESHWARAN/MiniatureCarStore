package com.project.carstore.security;

public class ValidateDTO {
    private Boolean valid;
    private String role;

    public ValidateDTO(Boolean valid, String role) {
        this.valid = valid;
        this.role = role;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
