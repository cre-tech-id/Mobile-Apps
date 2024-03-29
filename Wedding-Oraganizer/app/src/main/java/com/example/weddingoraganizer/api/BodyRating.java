package com.example.weddingoraganizer.api;

public class BodyRating{
    private String users_id;
    private String penyedia_id;
    private String rating;
    private Integer selesai;

    public void setUserid(String users_id){
        this.users_id = users_id;
    }

    public String getUserid(){
        return users_id;
    }

    public void setPenyediaid(String penyedia_id){
        this.penyedia_id = penyedia_id;
    }

    public String getPenyediaid(){
        return penyedia_id;
    }

    public void setRating(String rating){
        this.rating = rating;
    }

    public String getRating(){
        return rating;
    }

    public void setSelesai(Integer selesai){
        this.selesai = selesai;
    }

    public Integer getSelesai(){
        return selesai;
    }
}
