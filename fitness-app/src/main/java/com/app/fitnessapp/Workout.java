package com.app.fitnessapp;

import java.time.LocalDate;

public class Workout {
    private String name;
    private String exercise;
    private int duration;
    private LocalDate date;
    private int caloriesBurned;

    public Workout(String name, String exercise, int duration, LocalDate date, int caloriesBurned) {
        this.name = name;
        this.exercise = exercise;
        this.duration = duration;
        this.date = date;
        this.caloriesBurned = caloriesBurned;
    }

    public String getName() {
        return name;
    }

    public String getExercise() {
        return exercise;
    }

    public int getDuration() {
        return duration;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }
}
