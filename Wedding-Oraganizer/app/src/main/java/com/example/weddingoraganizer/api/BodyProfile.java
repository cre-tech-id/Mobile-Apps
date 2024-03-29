package com.example.weddingoraganizer.api;

public class BodyProfile {
    private String id;
    private String nama;
    private String alamat;
    private String no;

    public void setNama(String nama){

        this.nama = nama;
    }

    public String getNama(){

        return nama;
    }

    public void setAlamat(String alamat){

        this.alamat = alamat;
    }

    public String getAlamat(){

        return alamat;
    }

    public void setNo(String no){

        this.no = no;
    }

    public String getNo(){

        return no;
    }

    public void setId(String id){

        this.id = id;
    }

    public String getId(){

        return id;
    }
}

