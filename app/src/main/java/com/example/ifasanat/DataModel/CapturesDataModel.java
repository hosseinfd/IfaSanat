package com.example.ifasanat.DataModel;


import android.os.Parcel;
import android.os.Parcelable;

public class CapturesDataModel implements Parcelable {

    private int id;
    private String imagePath;

    public CapturesDataModel() {
    }

    protected CapturesDataModel(Parcel in) {
        id = in.readInt();
        imagePath = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public static final Creator<CapturesDataModel> CREATOR = new Creator<CapturesDataModel>() {
        @Override
        public CapturesDataModel createFromParcel(Parcel in) {
            return new CapturesDataModel(in);
        }

        @Override
        public CapturesDataModel[] newArray(int size) {
            return new CapturesDataModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(imagePath);
    }

}
