package com.example.newborn.appRepository;

import java.time.LocalDateTime;

/**
 * This class is used to pass two parameters to AsyncTask
 */
public class DoubleParamAsync {
    public String baby;
    public LocalDateTime date;

    public DoubleParamAsync(String baby, LocalDateTime date) {
        this.baby = baby;
        this.date = date;
    }
}
