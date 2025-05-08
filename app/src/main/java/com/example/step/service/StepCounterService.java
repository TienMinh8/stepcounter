package com.example.step.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.step.R;
import com.example.step.data.entity.StepEntity;
import com.example.step.repository.StepRepository;
import com.example.step.util.DateUtils;
import com.example.step.view.MainActivity;

import java.util.Calendar;
import java.util.Date;

public class StepCounterService extends Service implements StepDetector.OnStepListener {
    
    private static final String TAG = "StepCounterService";
    private static final String CHANNEL_ID = "step_counter_channel";
    private static final int NOTIFICATION_ID = 1;
    private static final String PREF_NAME = "StepCounterPrefs";
    private static final String PREF_STEP_COUNT = "step_count";
    private static final String PREF_LAST_SAVE_DATE = "last_save_date";
    
    private final IBinder binder = new LocalBinder();
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor stepCounter;
    private StepDetector stepDetector;
    private StepRepository stepRepository;
    private int currentStepCount = 0;
    private double currentDistance = 0;
    private int currentCalories = 0;
    private long startTimeInMillis;
    private boolean isRunning = false;
    private MutableLiveData<Integer> stepCountLiveData = new MutableLiveData<>(0);
    private MutableLiveData<Double> distanceLiveData = new MutableLiveData<>(0.0);
    private MutableLiveData<Integer> caloriesLiveData = new MutableLiveData<>(0);
    
    // User settings
    private float userStrideLength = 0.7f; // Default stride length in meters
    private float userWeight = 70.0f; // Default weight in kg
    
    // Step listener interface for callback
    public interface StepListener {
        void onStepUpdate(int steps, double distance, int calories);
    }
    
    private StepListener stepListener;
    
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "StepCounterService created");
        
        stepRepository = new StepRepository(this);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        stepDetector = new StepDetector(this);
        
        // Load saved step count
        loadStepCount();
        
        // Update live data with current values
        stepCountLiveData.setValue(currentStepCount);
        distanceLiveData.setValue(currentDistance);
        caloriesLiveData.setValue(currentCalories);
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "StepCounterService started");
        startForeground(NOTIFICATION_ID, createNotification());
        
        startTimeInMillis = System.currentTimeMillis();
        registerSensors();
        isRunning = true;
        
        return START_STICKY;
    }
    
    @Override
    public void onDestroy() {
        Log.d(TAG, "StepCounterService destroyed");
        unregisterSensors();
        saveStepCount();
        isRunning = false;
        super.onDestroy();
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    
    @Override
    public void onStepDetected() {
        currentStepCount++;
        updateStepData();
    }
    
    @Override
    public void onStep(int stepCount) {
        // This is for devices with a dedicated step counter sensor
        // The step counter provides an absolute value since boot
        // We need to track the delta
        
        // For simplicity, we'll just increment our counter
        currentStepCount++;
        updateStepData();
    }
    
    private void updateStepData() {
        // Calculate distance based on step count and stride length
        currentDistance = currentStepCount * userStrideLength / 1000.0; // Convert to kilometers
        
        // Calculate calories burned based on step count, user weight, and a simple formula
        // A very basic estimate: 0.04 calories per step per kg of body weight
        currentCalories = (int) (currentStepCount * 0.04 * userWeight);
        
        // Update LiveData
        updateLiveData();
        
        // Update notification
        updateNotification();
        
        // Periodically save data to the database
        if (currentStepCount % 100 == 0) {
            saveStepData();
        }
    }
    
    private void registerSensors() {
        try {
            if (sensorManager == null) {
                Log.e(TAG, "SensorManager is null");
                return;
            }
            
            if (stepDetector == null) {
                Log.e(TAG, "StepDetector is null");
                return;
            }
            
            if (stepCounter != null) {
                // Prefer hardware step counter if available
                boolean registered = sensorManager.registerListener(
                        stepDetector, 
                        stepCounter, 
                        SensorManager.SENSOR_DELAY_NORMAL);
                
                if (registered) {
                    Log.d(TAG, "Registered hardware step counter");
                } else {
                    Log.w(TAG, "Failed to register hardware step counter");
                }
            } else if (accelerometer != null) {
                // Fall back to accelerometer
                boolean registered = sensorManager.registerListener(
                        stepDetector, 
                        accelerometer, 
                        SensorManager.SENSOR_DELAY_NORMAL);
                
                if (registered) {
                    Log.d(TAG, "Registered accelerometer for step detection");
                } else {
                    Log.w(TAG, "Failed to register accelerometer");
                }
            } else {
                Log.e(TAG, "No suitable sensors found for step counting");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error registering sensors", e);
        }
    }
    
    private void unregisterSensors() {
        try {
            if (sensorManager != null && stepDetector != null) {
                sensorManager.unregisterListener(stepDetector);
                Log.d(TAG, "Unregistered sensors");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error unregistering sensors", e);
        }
    }
    
    private Notification createNotification() {
        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Step Counter Service",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        
        // Create intent for tapping the notification
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 
                0, 
                notificationIntent, 
                PendingIntent.FLAG_IMMUTABLE
        );
        
        // Xử lý trường hợp null trước khi gọi format
        String distanceText = currentDistance > 0 ? 
            String.format("%.2f", currentDistance) : "0.00";
        
        // Build the notification
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Step Counter")
                .setContentText("Steps: " + currentStepCount + " | Distance: " + distanceText + " km")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();
    }
    
    private void updateNotification() {
        try {
            NotificationManager notificationManager = 
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                Notification notification = createNotification();
                if (notification != null) {
                    notificationManager.notify(NOTIFICATION_ID, notification);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error updating notification", e);
        }
    }
    
    private void saveStepData() {
        Date today = new Date();
        if (today == null) {
            return;
        }
        
        long durationInMillis = System.currentTimeMillis() - startTimeInMillis;
        
        Date todayStart = DateUtils.getTodayStart();
        if (todayStart == null) {
            return;
        }
        
        StepEntity stepEntity = stepRepository.getStepsByDate(todayStart);
        if (stepEntity == null) {
            // Create new entry for today
            stepEntity = new StepEntity(
                    todayStart,
                    currentStepCount,
                    currentDistance,
                    currentCalories,
                    durationInMillis
            );
            stepRepository.insertStepData(stepEntity);
        } else {
            // Update existing entry
            stepEntity.setStepCount(currentStepCount);
            stepEntity.setDistance(currentDistance);
            stepEntity.setCalories(currentCalories);
            stepEntity.setDurationInMillis(durationInMillis);
            stepRepository.updateStepData(stepEntity);
        }
        
        // Save to SharedPreferences too for quick access
        saveStepCount();
    }
    
    private void saveStepCount() {
        try {
            SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            if (prefs != null) {
                SharedPreferences.Editor editor = prefs.edit();
                if (editor != null) {
                    editor.putInt(PREF_STEP_COUNT, currentStepCount);
                    editor.putLong(PREF_LAST_SAVE_DATE, System.currentTimeMillis());
                    editor.apply();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error saving step count to SharedPreferences", e);
        }
    }
    
    private void loadStepCount() {
        try {
            SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            if (prefs != null) {
                long lastSaveDate = prefs.getLong(PREF_LAST_SAVE_DATE, 0);
                
                // Check if the last save was today
                if (isSameDay(lastSaveDate, System.currentTimeMillis())) {
                    currentStepCount = prefs.getInt(PREF_STEP_COUNT, 0);
                    // Calculate other metrics based on the loaded step count
                    currentDistance = currentStepCount * userStrideLength / 1000.0;
                    currentCalories = (int) (currentStepCount * 0.04 * userWeight);
                } else {
                    // Reset for the new day
                    currentStepCount = 0;
                    currentDistance = 0;
                    currentCalories = 0;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error loading step count from SharedPreferences", e);
            // Reset values in case of error
            currentStepCount = 0;
            currentDistance = 0;
            currentCalories = 0;
        }
    }
    
    private boolean isSameDay(long time1, long time2) {
        try {
            if (time1 == 0 || time2 == 0) {
                return false;
            }
            
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTimeInMillis(time1);
            cal2.setTimeInMillis(time2);
            return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                   cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        } catch (Exception e) {
            Log.e(TAG, "Error in isSameDay", e);
            return false;
        }
    }
    
    // Methods for binding
    public boolean isRunning() {
        return isRunning;
    }
    
    public int getCurrentStepCount() {
        return currentStepCount;
    }
    
    public double getCurrentDistance() {
        return currentDistance;
    }
    
    public int getCurrentCalories() {
        return currentCalories;
    }
    
    public LiveData<Integer> getStepCountLiveData() {
        return stepCountLiveData;
    }
    
    public LiveData<Double> getDistanceLiveData() {
        return distanceLiveData;
    }
    
    public LiveData<Integer> getCaloriesLiveData() {
        return caloriesLiveData;
    }
    
    public void setUserStrideLength(float strideLength) {
        this.userStrideLength = strideLength;
        // Recalculate distance
        currentDistance = currentStepCount * userStrideLength / 1000.0;
        distanceLiveData.setValue(currentDistance);
    }
    
    public void setUserWeight(float weight) {
        this.userWeight = weight;
        // Recalculate calories
        currentCalories = (int) (currentStepCount * 0.04 * userWeight);
        caloriesLiveData.setValue(currentCalories);
    }
    
    public class LocalBinder extends Binder {
        public StepCounterService getService() {
            return StepCounterService.this;
        }
    }
    
    // Update LiveData
    private void updateLiveData() {
        try {
            if (stepCountLiveData != null) {
                stepCountLiveData.postValue(currentStepCount);
            }
            
            if (distanceLiveData != null) {
                distanceLiveData.postValue(currentDistance);
            }
            
            if (caloriesLiveData != null) {
                caloriesLiveData.postValue(currentCalories);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error updating LiveData", e);
        }
    }
    
    public void setStepListener(StepListener listener) {
        this.stepListener = listener;
        
        // Notify with current values immediately
        if (this.stepListener != null) {
            this.stepListener.onStepUpdate(currentStepCount, currentDistance, currentCalories);
        }
    }
} 