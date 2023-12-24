package com.example.weddingoraganizer;

public class ItemEvent {
    public ItemEvent(int imgEvent, String descEvent) {
        this.imgEvent = imgEvent;
        this.descEvent = descEvent;
    }

    public int getImgEvent() {
        return imgEvent;
    }

    public String getDescEvent() {
        return descEvent;
    }

    private int imgEvent;
    private String descEvent;
}
