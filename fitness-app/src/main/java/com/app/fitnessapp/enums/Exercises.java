package com.app.fitnessapp.enums;

public enum Exercises {
    Walking("1", "Walking"),
    Running("2", "Running"),
    Swimming("3", "Swimming"),
    Planks("4", "Planks"),
    Squats("5", "Squats"),
    Lunges("6", "Lunges"),
    Cycling("7", "Cycling"),
    Crunches("8", "Crunches"),
    PushUps("9", "PushUps"),
    DeadLifting("10", "DeadLifting");

    private final String code;
    private final String fullName;

    Exercises(String code, String fullName) {
        this.code = code;
        this.fullName = fullName;
    }

    public String getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }
}
