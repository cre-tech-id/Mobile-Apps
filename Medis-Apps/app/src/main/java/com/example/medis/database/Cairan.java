package com.example.medis.database;

public class Cairan {
    private int id;
    private String tanggal;
    private String batas_cairan;
    private String urin;
    private String total_cairan;
    private String berat;
    private String sisa_cairan;
    private String konsumsi_cairan;

    void Cairan(String tanggal, String batas_cairan, String urin, String total_cairan, String berat) {
        this.tanggal = tanggal;
        this.batas_cairan = batas_cairan;
        this.urin = urin;
        this.total_cairan = total_cairan;
        this.berat = berat;
    }
    Cairan(Integer id, String tanggal, String urin, String batas_cairan, String berat, String total_cairan, String konsumsi_cairan, String sisa_konsumsi) {
        this.id = id;
        this.tanggal = tanggal;
        this.batas_cairan = batas_cairan;
        this.urin = urin;
        this.total_cairan = total_cairan;
        this.berat = berat;
        this.konsumsi_cairan = konsumsi_cairan;
        this.sisa_cairan = sisa_konsumsi;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
    public String getBatascairan() {
        return batas_cairan;
    }
    public void setBatascairan(String batas_cairan) {
        this.batas_cairan = batas_cairan;
    }

    public String getUrin() {
        return urin;
    }
    public void setUrin(String urin) {
        this.urin = urin;
    }

    public String getTotalCairan() {
        return total_cairan;
    }
    public void setTotalCairan(String total_cairan) {
        this.total_cairan = total_cairan;
    }

    public String getBerat() {
        return berat;
    }
    public void setBerat(String berat) {
        this.berat = berat;
    }

    public String getSisaCairan() {
        return sisa_cairan;
    }
    public void setSisaCairan(String sisa_cairan) {
        this.sisa_cairan = sisa_cairan;
    }

    public String getKonsumsiCairan() {
        return konsumsi_cairan;
    }
    public void setKonsumsiCairan(String sisa_cairan) {
        this.sisa_cairan = sisa_cairan;
    }
}
