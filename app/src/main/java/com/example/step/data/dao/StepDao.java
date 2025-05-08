package com.example.step.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.annotation.Nullable;

import com.example.step.data.entity.StepEntity;

import java.util.Date;
import java.util.List;

@Dao
public interface StepDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StepEntity stepEntity);
    
    @Update
    void update(StepEntity stepEntity);
    
    @Query("SELECT * FROM steps WHERE date = :date")
    @Nullable
    StepEntity getStepsByDate(Date date);
    
    @Query("SELECT * FROM steps WHERE date = :date")
    LiveData<StepEntity> observeStepsByDate(Date date);
    
    @Query("SELECT * FROM steps WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    LiveData<List<StepEntity>> getStepsBetweenDates(Date startDate, Date endDate);
    
    @Query("SELECT SUM(stepCount) FROM steps WHERE date BETWEEN :startDate AND :endDate")
    LiveData<Integer> getTotalStepsBetweenDates(Date startDate, Date endDate);
    
    @Query("SELECT SUM(distance) FROM steps WHERE date BETWEEN :startDate AND :endDate")
    LiveData<Double> getTotalDistanceBetweenDates(Date startDate, Date endDate);
    
    @Query("SELECT SUM(calories) FROM steps WHERE date BETWEEN :startDate AND :endDate")
    LiveData<Integer> getTotalCaloriesBetweenDates(Date startDate, Date endDate);
    
    @Query("SELECT MAX(stepCount) FROM steps")
    LiveData<Integer> getMaxSteps();
} 