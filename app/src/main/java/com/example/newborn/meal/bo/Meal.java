package com.example.newborn.meal.bo;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.VisibleForTesting;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.newborn.appdatabase.Converters;

import java.util.Date;

/**
 * Class representing a meal
 */
@Entity
public class Meal implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String baby;
    private Date time;
    private int quantity;
    private String breast;

    //constructors
    public Meal() {
    }

    @Ignore
    public Meal(int id, String baby, Date time, int quantity, String breast
    ) {
        this.id = id;
        this.baby = baby;
        this.time = time;
        this.quantity = quantity;
        this.breast = breast;
    }
    @Ignore
    public Meal(String baby, Date time) {
        this.id = id;
        this.baby = baby;
        this.time = time;
    }
    @Ignore
    public Meal(String baby, Date time, int quantity) {
        this.id = id;
        this.baby = baby;
        this.time = time;
        this.quantity = quantity;
    }
    @Ignore
    public Meal(String baby, Date time, String breast
    ) {
        this.id = id;
        this.baby = baby;
        this.time = time;
        this.breast = breast;
    }
    @Ignore
    public Meal(String baby, Date time, int quantity, String breast
    ) {
        this.baby = baby;
        this.time = time;
        this.quantity = quantity;
        this.breast = breast;
    }

    protected Meal(Parcel in) {
        id = in.readInt();
        baby = in.readString();
        quantity = in.readInt();
        breast = in.readString();
        time = new Date(in.readLong());
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBreast() {
        return breast;
    }

    public void setBreast(String breast) {
        this.breast = breast;
    }

    //to String
    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", baby=" + baby +
                ", time=" + time +
                ", quantity=" + quantity +
                ", breast=" + breast +
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
        parcel.writeString(breast);
        parcel.writeLong(time.getTime());
    }
}
