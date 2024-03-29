package com.example.weddingoraganizer.api;

public class BodyKomentar{
    private String users_id;
    private String penyedia_id;
    private Integer paket_id;
    private String komentar;

    public void setKomentar(String komentar){

        this.komentar = komentar;
    }

    public String getKomentar(){

        return komentar;
    }

    public void setPaket_id(Integer paket_id){

        this.paket_id = paket_id;
    }

    public Integer getPaket_id(){

        return paket_id;
    }

    public void setUsers_id(String users_id){

        this.users_id = users_id;
    }

    public String getUsers_id(){

        return users_id;
    }

    public void setPenyedia_id(String penyedia_id){

        this.penyedia_id = penyedia_id;
    }

    public String getPenyedia_id(){

        return penyedia_id;
    }
}
