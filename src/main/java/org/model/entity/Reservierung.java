package org.model.entity;

public class Reservierung {
    private int resId;
    private int endId;
    private int autoId;

    public Reservierung(int resId, int endId, int autoId){
        setResId(resId);
        setEndId(endId);
        setAutoId(autoId);
    }

    public Reservierung() {

    }

    public void setEndId(int endId){
        this.endId = endId;
    }

    public int getEndId(){
        return this.endId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getResId() {
        return resId;
    }

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }

}
