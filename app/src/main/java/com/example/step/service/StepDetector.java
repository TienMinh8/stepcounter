package com.example.step.service;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

public class StepDetector implements SensorEventListener {
    
    private static final String TAG = "StepDetector";
    private static final float THRESHOLD = 10.0f; // Threshold for step detection
    private static final int SAMPLE_SIZE = 5; // Number of samples to use for smoothing
    
    private float[] lastValues = new float[3];
    private float[] accelValues = new float[SAMPLE_SIZE * 3];
    private int accelIndex = 0;
    private boolean isFirstStep = true;
    private float lastMagnitude = 0;
    private boolean isStepping = false;
    
    private OnStepListener stepListener;
    
    public StepDetector(OnStepListener listener) {
        this.stepListener = listener;
    }
    
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event == null || event.values == null || event.values.length < 3) {
            Log.w(TAG, "Invalid sensor event received");
            return;
        }
        
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            detectStepFromAccelerometer(event.values);
        } else if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            // Direct use of hardware step counter
            if (stepListener != null) {
                stepListener.onStep((int) event.values[0]);
            }
        }
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used in this implementation
    }
    
    private void detectStepFromAccelerometer(float[] values) {
        try {
            // Store the values
            for (int i = 0; i < 3; i++) {
                accelValues[accelIndex + i] = values[i];
            }
            accelIndex = (accelIndex + 3) % (SAMPLE_SIZE * 3);
            
            // Calculate acceleration magnitude
            float magnitude = calculateMagnitude(values);
            
            // Detect steps based on magnitude changes
            if (isFirstStep) {
                isFirstStep = false;
            } else {
                if (!isStepping && magnitude > lastMagnitude + THRESHOLD) {
                    isStepping = true;
                    if (stepListener != null) {
                        stepListener.onStepDetected();
                    }
                } else if (isStepping && magnitude < lastMagnitude - THRESHOLD) {
                    isStepping = false;
                }
            }
            
            lastMagnitude = magnitude;
            System.arraycopy(values, 0, lastValues, 0, 3);
        } catch (Exception e) {
            Log.e(TAG, "Error detecting step", e);
        }
    }
    
    private float calculateMagnitude(float[] values) {
        if (values == null || values.length < 3) {
            return 0.0f;
        }
        
        try {
            // Simple magnitude calculation
            return (float) Math.sqrt(values[0]*values[0] + values[1]*values[1] + values[2]*values[2]);
        } catch (Exception e) {
            Log.e(TAG, "Error calculating magnitude", e);
            return 0.0f;
        }
    }
    
    public interface OnStepListener {
        void onStepDetected();
        void onStep(int stepCount);
    }
} 