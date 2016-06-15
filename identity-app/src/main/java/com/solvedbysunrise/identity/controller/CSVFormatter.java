package com.solvedbysunrise.identity.controller;

public class CSVFormatter {


    private String dateFormat;

    private String durationFormat;

    public CSVFormatter() {
        dateFormat = "yyyy-mmm-dd";
        durationFormat = "mmm";
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public String getDurationFormat() {
        return durationFormat;
    }
}
