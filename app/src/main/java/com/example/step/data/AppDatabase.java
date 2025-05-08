package com.example.step.data;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.step.BuildConfig;
import com.example.step.data.dao.StepDao;
import com.example.step.data.entity.StepEntity;
import com.example.step.util.DateConverter;

@Database(entities = {StepEntity.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    
    private static final String TAG = "AppDatabase";
    private static final String DATABASE_NAME = "step_database";
    private static volatile AppDatabase INSTANCE;
    
    public abstract StepDao stepDao();
    
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    try {
                        RoomDatabase.Builder<AppDatabase> builder = Room.databaseBuilder(
                                context.getApplicationContext(),
                                AppDatabase.class,
                                DATABASE_NAME);
                        
                        // Cho phép query trên main thread trong development
                        // Chỉ nên dùng trong development
                        if (BuildConfig.DEBUG) {
                            builder.allowMainThreadQueries();
                        }
                        
                        // Thêm fallback migration để tránh crash khi schema thay đổi
                        builder.fallbackToDestructiveMigration();
                        
                        // Xây dựng database instance
                        INSTANCE = builder.build();
                        
                        Log.d(TAG, "Database initialized successfully");
                    } catch (Exception e) {
                        Log.e(TAG, "Error initializing database", e);
                        
                        // Fallback to in-memory database in case of error
                        try {
                            INSTANCE = Room.inMemoryDatabaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class)
                                    .fallbackToDestructiveMigration()
                                    .build();
                            Log.d(TAG, "Fallback to in-memory database");
                        } catch (Exception innerException) {
                            Log.e(TAG, "Failed to create in-memory database", innerException);
                            throw new RuntimeException("Cannot create database", innerException);
                        }
                    }
                }
            }
        }
        return INSTANCE;
    }
    
    // Phương thức để destroy instance trong testing hoặc khi cần thiết
    public static void destroyInstance() {
        INSTANCE = null;
    }
} 