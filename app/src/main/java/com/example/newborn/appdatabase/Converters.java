package com.example.newborn.appdatabase;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Created and implemented by Amandine
 * This class allows to provide a converter to Room in order to
 * insert temporal data to the database
 */
public class Converters {

    /**
     * Function to convert a Long object to a Date Object
     * @param value
     * @return
     */
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    /**
     * Function to convert a Date object to a Long Object
     * @param date
     * @return
     */
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
