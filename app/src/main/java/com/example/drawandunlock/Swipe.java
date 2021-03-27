package com.example.drawandunlock;

public class Swipe {

    private float startPointX;
    private float startPointY;
    private float pressure;
    private float endPointX;
    private float endPointY;
    private float duration;

    public Swipe() {
        this.startPointX = 0;
        this.startPointY = 0;
        this.pressure = 0;
        this.endPointX = 0;
        this.endPointY = 0;
        this.duration = 0;
    }

    public float getStartPointX() {
        return startPointX;
    }

    public void setStartPointX(float startPointX) {
        this.startPointX = startPointX;
    }

    public float getStartPointY() {
        return startPointY;
    }

    public void setStartPointY(float startPointY) {
        this.startPointY = startPointY;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getEndPointX() {
        return endPointX;
    }

    public void setEndPointX(float endPointX) {
        this.endPointX = endPointX;
    }

    public float getEndPointY() {
        return endPointY;
    }

    public void setEndPointY(float endPointY) {
        this.endPointY = endPointY;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }
}