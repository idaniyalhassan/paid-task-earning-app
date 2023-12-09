package com.example.paidtask;

import android.content.Context;
import android.content.SharedPreferences;

public class PointManager {
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String POINTS_KEY = "points";
    public static int loadPoints(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(POINTS_KEY, 0);
    }

    public static void savePoints(Context context, int points) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(POINTS_KEY, points);
        editor.apply();
    }

    public static void addPoints(Context context, int pointsToAdd) {
        int currentPoints = loadPoints(context);
        currentPoints += pointsToAdd;
        savePoints(context, currentPoints);
    }
    public static void minusToPoints(Context context, int pointsToMinus) {
        int currentPoints = loadPoints(context);
        currentPoints -= pointsToMinus;
        savePoints(context, currentPoints);
    }
}
