package com.example.newborn.sleep.bo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

/**
 * class representing sleep
 */
@Entity
public class Sleep implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String baby;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    //constructors
    public Sleep() {
    }
    @Ignore
    public Sleep(int id, String baby, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.baby = baby;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    @Ignore
    public Sleep(String baby, LocalDateTime startTime) {
        this.id = id;
        this.baby = baby;
        this.startTime = startTime;
    }
    @Ignore
    public Sleep(String baby, LocalDateTime startTime, LocalDateTime endTime) {
        this.baby = baby;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    protected Sleep(Parcel in) {
        id = in.readInt();
        baby = in.readString();
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
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
    }
}
