package com.framgia.thanghn.scmusic.ultils;

public class StringUtil {

    public static String parseMilliSecondsToTimer(long milliseconds) {
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        return hours > 0 ? String.format("%02d:%02d:%02d",
                hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }
}
