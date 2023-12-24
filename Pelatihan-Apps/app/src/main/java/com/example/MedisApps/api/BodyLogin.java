package com.medis.MedisApps.api;

public class BodyLogin{
    private String email;
    private String password;

    public void setPassword(String password){

        this.password = password;
    }

    public String getPassword(){

        return password;
    }

    public void setEmail(String email){

        this.email = email;
    }

    public String getEmail(){

        return email;
    }
}
