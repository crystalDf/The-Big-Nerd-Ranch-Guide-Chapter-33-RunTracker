package com.star.runtracker;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Run {
    private Date mStartDate;

    public Run() {
        mStartDate = new Date();
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date startDate) {
        mStartDate = startDate;
    }

    public int getDurationSeconds(long endMillis) {
        return (int) ((endMillis - mStartDate.getTime()) / 1000);
    }

    public static String formatDuration(int durationSeconds) {
        int seconds = durationSeconds % 60;
        int minutes = (durationSeconds / 60) % 60;
        int hours = durationSeconds / 3600;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public String getFormattedDate() {
        String format = "EEEE, MMM d, yyyy HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(mStartDate);
    }
}
