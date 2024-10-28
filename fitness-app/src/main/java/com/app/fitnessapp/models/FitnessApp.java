package com.app.fitnessapp.models;

public class FitnessApp {
    private String name;
    private String exercise;
    private int duration;
    private String date;
    private int caloriesBurned;
    private int userId;

    public FitnessApp(String name, String exercise, int duration, String date, int caloriesBurned, int userId) {
        this.name = name;
        this.exercise = exercise;
        this.duration = duration;
        this.date = date;
        this.caloriesBurned = caloriesBurned;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "name='" + name + '\'' +
                ", exercise='" + exercise + '\'' +
                ", duration=" + duration +
                " minutes, date='" + date + '\'' +
                ", caloriesBurned=" + caloriesBurned +
                ", userId=" + userId +
                '}';
    }
}
