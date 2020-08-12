package com.example.newborn.change.bo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.newborn.appdatabase.Converters;

import java.util.Date;
import java.util.Date;

/**
 * class representing change
 */
@Entity
public class Change implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String baby;
    private Date changeTime;
    private Boolean isPoop;

    //constructor
    public Change() {
    }

    @Ignore
    public Change(int id, String baby, Date changeTime, Boolean isPoop) {
        this.id = id;
        this.baby = baby;
        this.changeTime = changeTime;
        this.isPoop = isPoop;
    }
    @Ignore
    public Change(String baby, Date changeTime, Boolean isPoop) {
        this.baby = baby;
        this.changeTime = changeTime;
        this.isPoop = isPoop;
    }

    protected Change(Parcel in) {
        id = in.readInt();
        baby = in.readString();
        byte tmpIsPoop = in.readByte();
        isPoop = tmpIsPoop == 0 ? null : tmpIsPoop == 1;
        changeTime = new Date(in.readLong());
    }

    public static final Creator<Change> CREATOR = new Creator<Change>() {
        @Override
        public Change createFromParcel(Parcel in) {
            return new Change(in);
        }

        @Override
        public Change[] newArray(int size) {
            return new Change[size];
        }
    };

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBaby() {
        return baby;
    }

    public void setBaby(String baby) {
        this.baby = baby;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public Boolean getPoop() {
        return isPoop;
    }

    public void setPoop(Boolean poop) {
        isPoop = poop;
    }

    @Override
    public String toString() {
        return "Change{" +
                "id=" + id +
                ", baby=" + baby +
                ", changeTime=" + changeTime +
                ", isPoop=" + isPoop +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(baby);
        parcel.writeByte((byte) (isPoop == null ? 0 : isPoop ? 1 : 2));
        parcel.writeLong(changeTime.getTime());
    }
}
