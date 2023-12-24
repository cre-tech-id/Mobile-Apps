package com.example.medis.database;

public class DetailCairan {
    private int id;
    private String menu;
    private String id_cairan;
    private String jam;
    private String konsumsi_cairan;

    DetailCairan(String menu, String id_cairan, String konsumsi_cairan, String jam) {
        this.menu = menu;
        this.id_cairan = id_cairan;
        this.konsumsi_cairan = konsumsi_cairan;
        this.jam = jam;
    }
    DetailCairan(Integer id, String menu, String id_cairan, String konsumsi_cairan, String jam) {
        this.id = id;
        this.menu = menu;
        this.id_cairan = id_cairan;
        this.konsumsi_cairan = konsumsi_cairan;
        this.jam = jam;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMenu() {
        return menu;
    }
    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getIdCairan() {
        return id_cairan;
    }
    public void setIdCairan(String id_cairan) {
        this.id_cairan = id_cairan;
    }

    public String getJam() {
        return jam;
    }
    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getKonsumsiCairan() {
        return konsumsi_cairan;
    }
    public void setKonsumsiCairan(String konsumsi_cairan) {
        this.konsumsi_cairan = konsumsi_cairan;
    }



}
