package com.project.carstore.security;

public class UserDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

    private String role;
    private Long mobileNo;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }



    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public Long getMobileNo() {
        return mobileNo;
    }
    public void setMobileNo(Long mobileNo) {
        this.mobileNo = mobileNo;
    }
    public UserDTO(String firstName, String lastName, String username, String email, String password, String role, Long mobileNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.mobileNo = mobileNo;
    }
}
