package com.example.step.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.step.data.AppDatabase;
import com.example.step.data.dao.StepDao;
import com.example.step.data.entity.StepEntity;
import com.example.step.util.DateUtils;

import java.util.Date;
import java.util.List;

public class StepRepository {
    
    private final StepDao stepDao;
    
    public StepRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        stepDao = database.stepDao();
    }
    
    public void insertStepData(StepEntity stepEntity) {
        new InsertStepAsyncTask(stepDao).execute(stepEntity);
    }
    
    public void updateStepData(StepEntity stepEntity) {
        new UpdateStepAsyncTask(stepDao).execute(stepEntity);
    }
    
    public StepEntity getStepsByDate(Date date) {
        if (date == null) {
            return null;
        }
        
        try {
            return new GetStepsByDateAsyncTask(stepDao).execute(date).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public LiveData<StepEntity> observeStepsByDate(Date date) {
        return stepDao.observeStepsByDate(date);
    }
    
    public LiveData<List<StepEntity>> getStepsForCurrentWeek() {
        Date[] weekDates = DateUtils.getCurrentWeekDates();
        return stepDao.getStepsBetweenDates(weekDates[0], weekDates[1]);
    }
    
    public LiveData<List<StepEntity>> getStepsForCurrentMonth() {
        Date[] monthDates = DateUtils.getCurrentMonthDates();
        return stepDao.getStepsBetweenDates(monthDates[0], monthDates[1]);
    }
    
    public LiveData<Integer> getTotalStepsForCurrentWeek() {
        Date[] weekDates = DateUtils.getCurrentWeekDates();
        return stepDao.getTotalStepsBetweenDates(weekDates[0], weekDates[1]);
    }
    
    public LiveData<Double> getTotalDistanceForCurrentWeek() {
        Date[] weekDates = DateUtils.getCurrentWeekDates();
        return stepDao.getTotalDistanceBetweenDates(weekDates[0], weekDates[1]);
    }
    
    public LiveData<Integer> getTotalCaloriesForCurrentWeek() {
        Date[] weekDates = DateUtils.getCurrentWeekDates();
        return stepDao.getTotalCaloriesBetweenDates(weekDates[0], weekDates[1]);
    }
    
    public LiveData<Integer> getMaxSteps() {
        return stepDao.getMaxSteps();
    }
    
    private static class InsertStepAsyncTask extends AsyncTask<StepEntity, Void, Void> {
        private final StepDao stepDao;
        
        private InsertStepAsyncTask(StepDao stepDao) {
            this.stepDao = stepDao;
        }
        
        @Override
        protected Void doInBackground(StepEntity... stepEntities) {
            if (stepEntities != null && stepEntities.length > 0 && stepEntities[0] != null) {
                stepDao.insert(stepEntities[0]);
            }
            return null;
        }
    }
    
    private static class UpdateStepAsyncTask extends AsyncTask<StepEntity, Void, Void> {
        private final StepDao stepDao;
        
        private UpdateStepAsyncTask(StepDao stepDao) {
            this.stepDao = stepDao;
        }
        
        @Override
        protected Void doInBackground(StepEntity... stepEntities) {
            if (stepEntities != null && stepEntities.length > 0 && stepEntities[0] != null) {
                stepDao.update(stepEntities[0]);
            }
            return null;
        }
    }
    
    private static class GetStepsByDateAsyncTask extends AsyncTask<Date, Void, StepEntity> {
        private final StepDao stepDao;
        
        private GetStepsByDateAsyncTask(StepDao stepDao) {
            this.stepDao = stepDao;
        }
        
        @Override
        protected StepEntity doInBackground(Date... dates) {
            if (dates == null || dates.length == 0 || dates[0] == null) {
                return null;
            }
            
            try {
                return stepDao.getStepsByDate(dates[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
} 