package com.example.newborn.meal.bo;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

/**
 * Class representing a meal
 */
@Entity
public class Meal implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String baby;
    private LocalDateTime time;
    private int quantity;
    // right = true, left = false
    private boolean rightBreast;

    //constructors
    public Meal() {
    }

    @Ignore
    public Meal(int id, String baby, LocalDateTime time, int quantity, boolean rightBreast) {
        this.id = id;
        this.baby = baby;
        this.time = time;
        this.quantity = quantity;
        this.rightBreast = rightBreast;
    }
    @Ignore
    public Meal(String baby, LocalDateTime time) {
        this.id = id;
        this.baby = baby;
        this.time = time;
    }
    @Ignore
    public Meal(String baby, LocalDateTime time, int quantity) {
        this.id = id;
        this.baby = baby;
        this.time = time;
        this.quantity = quantity;
    }
    @Ignore
    public Meal(String baby, LocalDateTime time, boolean rightBreast) {
        this.id = id;
        this.baby = baby;
        this.time = time;
        this.rightBreast = rightBreast;
    }
    @Ignore
    public Meal(String baby, LocalDateTime time, int quantity, boolean rightBreast) {
        this.baby = baby;
        this.time = time;
        this.quantity = quantity;
        this.rightBreast = rightBreast;
    }

    protected Meal(Parcel in) {
        id = in.readInt();
        baby = in.readString();
        quantity = in.readInt();
        rightBreast = in.readByte() != 0;
    }

    public static final Creator<Meal> CREATOR = new Creator<Meal>() {
        @Override
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        @Override
        public Meal[] newArray(int size) {
            return new Meal[size];
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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isRightBreast() {
        return rightBreast;
    }

    public void setRightBreast(boolean rightBreast) {
        this.rightBreast = rightBreast;
    }
    //to String
    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", baby=" + baby +
                ", time=" + time +
                ", quantity=" + quantity +
                ", rightBreast=" + rightBreast +
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
        parcel.writeInt(quantity);
        parcel.writeByte((byte) (rightBreast ? 1 : 0));
    }
}
