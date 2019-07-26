package com.example.ifasanat.DataModel;

public class CapturesAdapterModel {
    // fragments position
    private int pos;
    private CapturesDataModel model;

    public CapturesAdapterModel(int pos, CapturesDataModel model) {
        this.pos = pos;
        this.model = model;
    }

    public CapturesAdapterModel(CapturesDataModel model){
        this.pos = -1;
        this.model = model;
    }

    public int getPos() {
        return pos;
    }

    public CapturesDataModel getModel() {
        return model;
    }

    public void setModel(CapturesDataModel model) {
        this.model = model;
    }
}
