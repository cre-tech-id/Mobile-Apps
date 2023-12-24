package com.example.weddingoraganizer.api;

public class LoginResponse{
    private String message;
    private boolean status;

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message + status;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public boolean isStatus(){
        return status;
    }

    @Override
    public String toString(){
        return
                "LoginResponse{" +
                        "hehe = '" + message + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }

}
