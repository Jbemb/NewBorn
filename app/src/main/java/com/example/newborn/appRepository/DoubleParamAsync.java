package com.example.newborn.appRepository;

import java.util.Date;

/**
 * This class is used to pass two parameters to AsyncTask
 */
public class DoubleParamAsync {
    public String baby;
    public Date date;

    public DoubleParamAsync(String baby, Date date) {
        this.baby = baby;
        this.date = date;
    }
}
