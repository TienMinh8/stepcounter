package com.example.step.util;

import androidx.room.TypeConverter;
import android.util.Log;

import java.util.Date;

public class DateConverter {
    
    private static final String TAG = "DateConverter";
    
    @TypeConverter
    public static Date toDate(Long timestamp) {
        if (timestamp == null) {
            Log.w(TAG, "Null timestamp received in toDate conversion");
            return null;
        }
        
        try {
            return new Date(timestamp);
        } catch (Exception e) {
            Log.e(TAG, "Error converting timestamp to Date", e);
            return null;
        }
    }
    
    @TypeConverter
    public static Long fromDate(Date date) {
        if (date == null) {
            Log.w(TAG, "Null date received in fromDate conversion");
            return null;
        }
        
        try {
            return date.getTime();
        } catch (Exception e) {
            Log.e(TAG, "Error converting Date to timestamp", e);
            return null;
        }
    }
} 