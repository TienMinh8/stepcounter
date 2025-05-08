package com.example.step;

import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

/**
 * Lớp ứng dụng chính cho việc cấu hình MultiDex
 */
public class StepApplication extends MultiDexApplication {
    private static final String TAG = "StepApplication";
    
    @Override
    protected void attachBaseContext(Context base) {
        try {
            super.attachBaseContext(base);
            // Cài đặt MultiDex trước khi ứng dụng được tạo
            MultiDex.install(this);
            Log.d(TAG, "MultiDex đã được cài đặt");
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi cài đặt MultiDex", e);
        }
    }
    
    @Override
    public void onCreate() {
        try {
            super.onCreate();
            Log.d(TAG, "Ứng dụng đã khởi tạo thành công");
            
            // Khởi tạo các thành phần cần thiết khác
            initializeComponents();
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi khởi tạo ứng dụng", e);
        }
    }
    
    private void initializeComponents() {
        try {
            // Các khởi tạo khác nếu cần
            // Ví dụ: Khởi tạo thư viện, analytics, crash reporting, etc.
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi khởi tạo các thành phần", e);
        }
    }
} 