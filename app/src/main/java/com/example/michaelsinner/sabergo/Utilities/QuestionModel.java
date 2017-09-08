package com.example.michaelsinner.sabergo.Utilities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Michael Sinner on 6/9/2017.
 */

public class QuestionModel implements Parcelable {
   // private final String idQuestion;
    //private final String areaQuestion;


    protected QuestionModel(Parcel in) {
    }

    public static final Creator<QuestionModel> CREATOR = new Creator<QuestionModel>() {
        @Override
        public QuestionModel createFromParcel(Parcel in) {
            return new QuestionModel(in);
        }

        @Override
        public QuestionModel[] newArray(int size) {
            return new QuestionModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
