package com.app.fitnessapp;

import com.app.fitnessapp.enums.Exercises;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FitnessController {

    public TableView<Workout> workoutTable;
    public TableColumn<Workout, String> nameColumn;
    public TableColumn<Workout, String> exerciseColumn;
    public TableColumn<Workout, Integer> durationColumn;
    public TableColumn<Workout, LocalDate> dateColumn;
    public TableColumn<Workout, Integer> caloriesColumn;

    private ObservableList<Workout> workoutData = FXCollections.observableArrayList();

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<Exercises> exerciseDropdown;

    @FXML
    private TextField durationField;

    @FXML
    private DatePicker dateField;

    @FXML
    private Label caloriesBurnedLabel;

    private final int runningCaloriesPerMinute = 10;
    private final int walkingCaloriesPerMinute = 5;
    private final int swimmingCaloriesPerMinute = 8;
    private final int plankCaloriesPerMinute = 6;
    private final int squatCaloriesPerMinute = 7;
    private final int lungeCaloriesPerMinute = 8;
    private final int cycleCaloriesPerMinute = 9;
    private final int crunchCaloriesPerMinute = 6;
    private final int pushUpCaloriesPerMinute = 7;
    private final int deadLiftCaloriesPerMinute = 11;

    @FXML
    public void initialize() {
        exerciseDropdown.getItems().addAll(Exercises.values());

        exerciseDropdown.valueProperty().addListener((observable, oldValue, newValue) -> calculateCalories());
        durationField.textProperty().addListener((observable, oldValue, newValue) -> calculateCalories());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        exerciseColumn.setCellValueFactory(new PropertyValueFactory<>("exercise"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<>("caloriesBurned"));

        workoutTable.setItems(workoutData);
    }

    @FXML
    private void showDailyData() {
        fetchDataForTimeframe(LocalDate.now(), LocalDate.now());
    }

    @FXML
    private void showWeeklyData() {
        LocalDate startOfWeek = LocalDate.now().minus(7, ChronoUnit.DAYS);
        fetchDataForTimeframe(startOfWeek, LocalDate.now());
    }

    @FXML
    private void showMonthlyData() {
        LocalDate startOfMonth = LocalDate.now().minus(30, ChronoUnit.DAYS);
        fetchDataForTimeframe(startOfMonth, LocalDate.now());
    }

    private void calculateCalories() {
        Exercises exercise = exerciseDropdown.getValue();
        String durationText = durationField.getText();
        int duration = durationText.isEmpty() ? 0 : Integer.parseInt(durationText);
        int caloriesBurned = 0;

        if (exercise != null && duration > 0) {
            switch (exercise) {
                case Walking:
                    caloriesBurned = walkingCaloriesPerMinute * duration;
                    break;
                case Running:
                    caloriesBurned = runningCaloriesPerMinute * duration;
                    break;
                case Swimming:
                    caloriesBurned = swimmingCaloriesPerMinute * duration;
                    break;
                case Planks:
                    caloriesBurned = plankCaloriesPerMinute * duration;
                    break;
                case Squats:
                    caloriesBurned = squatCaloriesPerMinute * duration;
                    break;
                case Lunges:
                    caloriesBurned = lungeCaloriesPerMinute * duration;
                    break;
                case Cycling:
                    caloriesBurned = cycleCaloriesPerMinute * duration;
                    break;
                case Crunches:
                    caloriesBurned = crunchCaloriesPerMinute * duration;
                    break;
                case PushUps:
                    caloriesBurned = pushUpCaloriesPerMinute * duration;
                    break;
                case DeadLifting:
                    caloriesBurned = deadLiftCaloriesPerMinute * duration;
                    break;
            }
        }

        caloriesBurnedLabel.setText(String.valueOf(caloriesBurned));
    }

    @FXML
    protected void handleSubmit() {
        String name = nameField.getText();
        Exercises exercise = exerciseDropdown.getValue();
        int duration = Integer.parseInt(durationField.getText());
        LocalDate date = dateField.getValue();
        int caloriesBurned = Integer.parseInt(caloriesBurnedLabel.getText());

        saveWorkoutData(name, exercise != null ? exercise.getFullName() : null, duration, date, caloriesBurned);
    }

    private void saveWorkoutData(String name, String exercise, int duration, LocalDate date, int caloriesBurned) {
        String url = "jdbc:mysql://localhost:3306/fitness_tracker";
        String user = "root";
        String password = "";

        String query = "INSERT INTO workout_log (name, exercise, duration, date, calories_burned) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setString(2, exercise);
            pstmt.setInt(3, duration);
            pstmt.setDate(4, java.sql.Date.valueOf(date));
            pstmt.setInt(5, caloriesBurned);

            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleReset() {
        nameField.clear();
        exerciseDropdown.setValue(null);
        durationField.clear();
        dateField.setValue(null);
        caloriesBurnedLabel.setText("0");
    }

    private void fetchDataForTimeframe(LocalDate startDate, LocalDate endDate) {
        workoutData.clear();
        String url = "jdbc:mysql://localhost:3306/fitness_tracker";
        String user = "root";
        String password = "";

        String query = "SELECT name, exercise, duration, date, calories_burned FROM workout_log WHERE date BETWEEN ? AND ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDate(1, java.sql.Date.valueOf(startDate));
            pstmt.setDate(2, java.sql.Date.valueOf(endDate));

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String exercise = rs.getString("exercise");
                int duration = rs.getInt("duration");
                LocalDate date = rs.getDate("date").toLocalDate();
                int caloriesBurned = rs.getInt("calories_burned");

                workoutData.add(new Workout(name, exercise, duration, date, caloriesBurned));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
