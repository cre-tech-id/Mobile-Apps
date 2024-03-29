package com.example.weddingoraganizer.api;

public class BodyPesanan {
    private Integer pemesan_id;
    private String paket_id;
    private String lokasi;
    private String tanggal_book;
    private String harga_paket;

    public void setPemesan_id(Integer pemesan_id){

        this.pemesan_id = pemesan_id;
    }

    public Integer getPemesan_id(){

        return pemesan_id;
    }

    public void setPaket_id(String paket_id){

        this.paket_id = paket_id;
    }

    public String getPaket_id(){

        return paket_id;
    }

    public void setTanggal_book(String tanggal_book){

        this.tanggal_book = tanggal_book;
    }

    public String getTanggal_book(){

        return tanggal_book;
    }

    public void setLokasi(String lokasi){

        this.lokasi = lokasi;
    }

    public String getLokasi(){

        return lokasi;
    }

    public void setHarga_paket(String harga_paket){
        this.harga_paket = harga_paket;
    }

    public String getHarga_paket(){
        return harga_paket;
    }
}

