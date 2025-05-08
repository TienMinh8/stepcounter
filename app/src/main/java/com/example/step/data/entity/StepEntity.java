package com.example.step.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.room.Index;

import com.example.step.util.DateConverter;

import java.util.Date;

@Entity(tableName = "steps", indices = {@Index(value = {"date"}, unique = true)})
public class StepEntity {
    
    @PrimaryKey(autoGenerate = true)
    private long id;
    
    @TypeConverters(DateConverter.class)
    private Date date;
    
    private int stepCount;
    
    private double distance;
    
    private int calories;
    
    private long durationInMillis;
    
    public StepEntity() {
    }
    
    public StepEntity(Date date, int stepCount, double distance, int calories, long durationInMillis) {
        this.date = date;
        this.stepCount = Math.max(0, stepCount);
        this.distance = Math.max(0, distance);
        this.calories = Math.max(0, calories);
        this.durationInMillis = Math.max(0, durationInMillis);
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public int getStepCount() {
        return stepCount;
    }
    
    public void setStepCount(int stepCount) {
        this.stepCount = Math.max(0, stepCount);
    }
    
    public double getDistance() {
        return distance;
    }
    
    public void setDistance(double distance) {
        this.distance = Math.max(0, distance);
    }
    
    public int getCalories() {
        return calories;
    }
    
    public void setCalories(int calories) {
        this.calories = Math.max(0, calories);
    }
    
    public long getDurationInMillis() {
        return durationInMillis;
    }
    
    public void setDurationInMillis(long durationInMillis) {
        this.durationInMillis = Math.max(0, durationInMillis);
    }
} 