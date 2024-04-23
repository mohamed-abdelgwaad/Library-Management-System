package com.maids.Library_Management_System.Models;



import com.fasterxml.jackson.annotation.JsonIgnore;

public class LoginResponseDTO {
    @JsonIgnore
    private ApplicationUser user;
    private String jwt;

    public LoginResponseDTO(){
        super();
    }

    public LoginResponseDTO(ApplicationUser user, String jwt){
        this.user = user;
        this.jwt = jwt;
    }

    public ApplicationUser getUser(){
        return this.user;
    }

    public void setUser(ApplicationUser user){
        this.user = user;
    }

    public String getJwt(){
        return this.jwt;
    }

    public void setJwt(String jwt){
        this.jwt = jwt;
    }

}
