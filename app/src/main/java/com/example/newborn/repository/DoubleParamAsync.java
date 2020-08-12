package com.example.newborn.repository;

import java.util.Date;

/**
 * This class is used to pass two parameters to AsyncTask
 */
public class DoubleParamAsync {
    public String baby;
    public Date dayStart;
    public Date dayEnd;

    public DoubleParamAsync(String baby, Date dayStart, Date dayEnd) {
        this.baby = baby;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
    }
}
