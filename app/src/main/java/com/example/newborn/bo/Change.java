package com.example.newborn.bo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

/**
 * class representing change
 */
@Entity
public class Change implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int baby;
    private LocalDateTime changeTime;
    private Boolean isPoop;

    //constructor
    public Change(int baby, LocalDateTime changeTime, Boolean isPoop) {
        this.baby = baby;
        this.changeTime = changeTime;
        this.isPoop = isPoop;
    }

    protected Change(Parcel in) {
        id = in.readInt();
        baby = in.readInt();
        byte tmpIsPoop = in.readByte();
        isPoop = tmpIsPoop == 0 ? null : tmpIsPoop == 1;
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

    public int getBaby() {
        return baby;
    }

    public void setBaby(int baby) {
        this.baby = baby;
    }

    public LocalDateTime getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(LocalDateTime changeTime) {
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
        parcel.writeInt(baby);
        parcel.writeByte((byte) (isPoop == null ? 0 : isPoop ? 1 : 2));
    }
}
