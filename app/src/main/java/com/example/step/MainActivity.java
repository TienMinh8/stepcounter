package com.example.step.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.step.R;
import com.example.step.data.entity.StepEntity;
import com.example.step.databinding.ActivityMainBinding;
import com.example.step.viewmodel.StepViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ACTIVITY_RECOGNITION = 1001;
    
    private ActivityMainBinding binding;
    private StepViewModel viewModel;
    private Button startStopButton;
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // Set up EdgeToEdge
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(StepViewModel.class);
        
        // Initialize UI components
        initializeUI();
        
        // Check permissions
        checkPermissions();
    }
    
    private void initializeUI() {
        startStopButton = binding.buttonStartStop;
        
        // Set up chart
        setupChart();
        
        // Set up observers for step data
        viewModel.getStepCount().observe(this, steps -> {
            binding.textSteps.setText(String.valueOf(steps));
        });
        
        viewModel.getDistance().observe(this, distance -> {
            binding.textDistance.setText(String.format(Locale.getDefault(), "%.2f km", distance));
        });
        
        viewModel.getCalories().observe(this, calories -> {
            binding.textCalories.setText(String.format(Locale.getDefault(), "%d kcal", calories));
        });
        
        // Weekly statistics
        viewModel.getTotalWeeklySteps().observe(this, steps -> {
            if (steps != null) {
                binding.textWeeklySteps.setText("Total Steps: " + steps);
            }
        });
        
        viewModel.getTotalWeeklyDistance().observe(this, distance -> {
            if (distance != null) {
                binding.textWeeklyDistance.setText(String.format(Locale.getDefault(), 
                        "Total Distance: %.2f km", distance));
            }
        });
        
        viewModel.getTotalWeeklyCalories().observe(this, calories -> {
            if (calories != null) {
                binding.textWeeklyCalories.setText("Total Calories: " + calories + " kcal");
            }
        });
        
        viewModel.getWeeklySteps().observe(this, this::updateChart);
        
        // Service running status
        viewModel.isServiceRunning().observe(this, isRunning -> {
            if (isRunning) {
                startStopButton.setText(R.string.stop_tracking);
            } else {
                startStopButton.setText(R.string.start_tracking);
            }
        });
        
        // Start/Stop button click listener
        startStopButton.setOnClickListener(v -> {
            Boolean isRunning = viewModel.isServiceRunning().getValue();
            if (isRunning != null && isRunning) {
                viewModel.stopStepCounting();
            } else {
                if (checkPermissions()) {
                    viewModel.startStepCounting();
                }
            }
        });
    }
    
    private void setupChart() {
        // Create the chart
        barChart = new BarChart(this);
        binding.chartContainer.addView(barChart);
        
        // Configure the chart
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
        barChart.setHighlightFullBarEnabled(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setPinchZoom(false);
        barChart.setScaleEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.getLegend().setEnabled(false);
        
        // Configure X axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        
        // Configure Y axis
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
        
        // Set empty data initially
        updateChart(new ArrayList<>());
    }
    
    private void updateChart(List<StepEntity> steps) {
        if (steps == null || steps.isEmpty()) {
            barChart.setData(null);
            barChart.invalidate();
            return;
        }
        
        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        
        // Get day names for the week
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        
        // Create entries for each day of the week
        for (int i = 0; i < 7; i++) {
            String dayLabel = dayFormat.format(calendar.getTime());
            labels.add(dayLabel);
            
            // Find step data for this day
            int stepsForDay = 0;
            for (StepEntity stepEntity : steps) {
                Calendar stepCal = Calendar.getInstance();
                stepCal.setTime(stepEntity.getDate());
                if (calendar.get(Calendar.YEAR) == stepCal.get(Calendar.YEAR) &&
                        calendar.get(Calendar.DAY_OF_YEAR) == stepCal.get(Calendar.DAY_OF_YEAR)) {
                    stepsForDay = stepEntity.getStepCount();
                    break;
                }
            }
            
            entries.add(new BarEntry(i, stepsForDay));
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
        
        // Create a dataset and customize it
        BarDataSet dataSet = new BarDataSet(entries, "Daily Steps");
        dataSet.setColor(Color.parseColor("#4CAF50"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(10f);
        
        // Create and set data
        BarData data = new BarData(dataSet);
        data.setBarWidth(0.6f);
        
        barChart.setData(data);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.invalidate();
    }
    
    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) 
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                        REQUEST_ACTIVITY_RECOGNITION
                );
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ACTIVITY_RECOGNITION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.startStepCounting();
            } else {
                Toast.makeText(this, "Permission required to count steps", Toast.LENGTH_SHORT).show();
            }
        }
    }
}