package com.example.newborn.model.sleep;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * class representing sleep
 */
@Entity
public class Sleep implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String baby;
    private Date startTime;
    private Date endTime;

    //constructors
    public Sleep() {
    }
    @Ignore
    public Sleep(int id, String baby, Date startTime, Date endTime) {
        this.id = id;
        this.baby = baby;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    @Ignore
    public Sleep(String baby, Date startTime) {
        this.id = id;
        this.baby = baby;
        this.startTime = startTime;
    }
    @Ignore
    public Sleep(String baby, Date startTime, Date endTime) {
        this.baby = baby;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    protected Sleep(Parcel in) {
        id = in.readInt();
        baby = in.readString();
        startTime = new Date(in.readLong());
        endTime = new Date(in.readLong());
    }

    public static final Creator<Sleep> CREATOR = new Creator<Sleep>() {
        @Override
        public Sleep createFromParcel(Parcel in) {
            return new Sleep(in);
        }

        @Override
        public Sleep[] newArray(int size) {
            return new Sleep[size];
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    //to string
    @Override
    public String toString() {
        return "Sleep{" +
                "id=" + id +
                ", baby=" + baby +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
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
        parcel.writeLong(startTime.getTime());
        parcel.writeLong(endTime.getTime());
    }
}
