package com.example.step.viewmodel;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.step.data.entity.StepEntity;
import com.example.step.repository.StepRepository;
import com.example.step.service.StepCounterService;
import com.example.step.util.DateUtils;

import java.util.Date;
import java.util.List;

public class StepViewModel extends AndroidViewModel {
    
    private static final String TAG = "StepViewModel";
    
    private final StepRepository repository;
    private StepCounterService stepService;
    private boolean isBound = false;
    private final MutableLiveData<Boolean> isServiceRunning = new MutableLiveData<>(false);
    
    // LiveData for UI
    private final MediatorLiveData<Integer> stepCount = new MediatorLiveData<>();
    private final MediatorLiveData<Double> distance = new MediatorLiveData<>();
    private final MediatorLiveData<Integer> calories = new MediatorLiveData<>();
    
    // Data for charts and statistics
    private LiveData<List<StepEntity>> weeklySteps;
    private LiveData<Integer> totalWeeklySteps;
    private LiveData<Double> totalWeeklyDistance;
    private LiveData<Integer> totalWeeklyCalories;
    private LiveData<Integer> maxSteps;
    
    // User settings
    private final MutableLiveData<Float> userHeight = new MutableLiveData<>(170f); // Default height in cm
    private final MutableLiveData<Float> userWeight = new MutableLiveData<>(70f); // Default weight in kg
    private final MutableLiveData<Float> userStrideLength = new MutableLiveData<>(0.7f); // Default stride length in meters
    
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof StepCounterService.StepBinder) {
                StepCounterService.StepBinder binder = (StepCounterService.StepBinder) service;
                stepService = binder.getService();
                isBound = true;
                
                // Update UI with current values from service
                updateStepInfo();
                
                // Set step listener to get updates
                if (stepService != null) {
                    stepService.setStepListener(StepViewModel.this::updateStepData);
                }
                
                isServiceRunning.postValue(true);
                Log.d(TAG, "Service connected");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            stepService = null;
            isServiceRunning.postValue(false);
            Log.d(TAG, "Service disconnected");
        }
    };
    
    public StepViewModel(@NonNull Application application) {
        super(application);
        repository = new StepRepository(application);
        
        // Initialize LiveData from repository
        weeklySteps = repository.getStepsForCurrentWeek();
        totalWeeklySteps = repository.getTotalStepsForCurrentWeek();
        totalWeeklyDistance = repository.getTotalDistanceForCurrentWeek();
        totalWeeklyCalories = repository.getTotalCaloriesForCurrentWeek();
        maxSteps = repository.getMaxSteps();
        
        // Initialize with current day data from repository
        LiveData<StepEntity> todaySteps = repository.observeStepsByDate(DateUtils.getTodayStart());
        stepCount.addSource(todaySteps, stepEntity -> {
            if (stepEntity != null) {
                stepCount.setValue(stepEntity.getStepCount());
                distance.setValue(stepEntity.getDistance());
                calories.setValue(stepEntity.getCalories());
            }
        });
        
        // Bind to service if it's running
        bindStepService();
    }
    
    public void startStepCounting() {
        Context context = getApplication();
        
        // Create and start the service
        Intent intent = new Intent(context, StepCounterService.class);
        context.startService(intent);
        
        // Bind to the service
        try {
            if (context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)) {
                Log.d(TAG, "Binding to service");
            } else {
                Log.e(TAG, "Could not bind to service");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error binding to service", e);
        }
    }
    
    public void stopStepCounting() {
        if (isBound && stepService != null) {
            try {
                // Save current data
                stepService.saveCurrentData();
                
                // Unbind from service
                getApplication().unbindService(serviceConnection);
                isBound = false;
                
                // Stop the service
                getApplication().stopService(new Intent(getApplication(), StepCounterService.class));
                isServiceRunning.postValue(false);
                
                Log.d(TAG, "Service stopped");
            } catch (Exception e) {
                Log.e(TAG, "Error stopping service", e);
            }
        }
    }
    
    private void bindStepService() {
        try {
            Context context = getApplication();
            Intent intent = new Intent(context, StepCounterService.class);
            
            // Check if the service class exists
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            } else {
                Log.e(TAG, "StepCounterService not found");
                isServiceRunning.setValue(false);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error binding to StepCounterService", e);
            isServiceRunning.setValue(false);
        }
    }
    
    private void applyUserSettingsToService() {
        try {
            if (isBound && stepService != null) {
                Float strideLength = userStrideLength.getValue();
                Float weight = userWeight.getValue();
                
                if (strideLength != null && strideLength > 0) {
                    stepService.setUserStrideLength(strideLength);
                }
                
                if (weight != null && weight > 0) {
                    stepService.setUserWeight(weight);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error applying user settings to service", e);
        }
    }
    
    public void updateUserHeight(float height) {
        if (height <= 0) {
            return;  // Tránh giá trị không hợp lệ
        }
        
        userHeight.setValue(height);
        // Calculate and update stride length based on height
        // A simple formula: stride length is approximately 0.415 * height in cm
        float strideLength = 0.415f * height / 100f; // Convert to meters
        userStrideLength.setValue(strideLength);
        applyUserSettingsToService();
    }
    
    public void updateUserWeight(float weight) {
        if (weight <= 0) {
            return;  // Tránh giá trị không hợp lệ
        }
        
        userWeight.setValue(weight);
        applyUserSettingsToService();
    }
    
    // Getters for LiveData
    public LiveData<Integer> getStepCount() {
        return stepCount;
    }
    
    public LiveData<Double> getDistance() {
        return distance;
    }
    
    public LiveData<Integer> getCalories() {
        return calories;
    }
    
    public LiveData<Boolean> isServiceRunning() {
        return isServiceRunning;
    }
    
    public LiveData<List<StepEntity>> getWeeklySteps() {
        return weeklySteps;
    }
    
    public LiveData<Integer> getTotalWeeklySteps() {
        return totalWeeklySteps;
    }
    
    public LiveData<Double> getTotalWeeklyDistance() {
        return totalWeeklyDistance;
    }
    
    public LiveData<Integer> getTotalWeeklyCalories() {
        return totalWeeklyCalories;
    }
    
    public LiveData<Integer> getMaxSteps() {
        return maxSteps;
    }
    
    public LiveData<Float> getUserHeight() {
        return userHeight;
    }
    
    public LiveData<Float> getUserWeight() {
        return userWeight;
    }
    
    public LiveData<Float> getUserStrideLength() {
        return userStrideLength;
    }
    
    // Update step information from service
    private void updateStepInfo() {
        if (isBound && stepService != null) {
            updateStepData(
                    stepService.getStepCount(),
                    stepService.getDistance(),
                    stepService.getCalories()
            );
        }
    }
    
    // Update LiveData with new step information
    private void updateStepData(int steps, double distanceValue, int caloriesValue) {
        stepCount.postValue(steps);
        distance.postValue(distanceValue);
        calories.postValue(caloriesValue);
    }
    
    @Override
    protected void onCleared() {
        super.onCleared();
        if (isBound) {
            try {
                // Save data and unbind service when ViewModel is destroyed
                if (stepService != null) {
                    stepService.saveCurrentData();
                }
                getApplication().unbindService(serviceConnection);
                isBound = false;
            } catch (Exception e) {
                Log.e(TAG, "Error in onCleared()", e);
            }
        }
    }
} 