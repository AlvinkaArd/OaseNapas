package main_latihanpernapasan;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import latihan_pernapasan.LatihanPernapasanController;

public class FXMLMulaiLatihanController {

    @FXML
    private Label napasLabel; // fx:id for the "10" label

    @FXML
    private Label countdownLabel; // fx:id for a new Label to display the countdown

    private int currentNapas = 10;
    private Timeline countdownTimeline;
    private int countdownSeconds = 5; // initial countdown duration in seconds

    @FXML
    public void initialize() {
        updateNapasLabel();

        if (countdownLabel != null) {
            countdownLabel.setText("");
        }
    }

    // @FXML
    // private void goBack(ActionEvent event) {
    // System.out.println("Go back button clicked!");

    // }
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        Parent scene2 = FXMLLoader.load(getClass().getResource("/latihan_pernapasan/FXMLLatihanPernapasan.fxml"));
        Scene scene = new Scene(scene2);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Latihan Pernapasan");
        stage.show();
        System.out.println("Ke halaman latihan pernapasan");
    }

    @FXML
    private void playMusic(ActionEvent event) {
        System.out.println("Play button clicked!");
        startCountdown();
    }

    @FXML
    private void decrementNapas(ActionEvent event) {
        if (currentNapas > 0) {
            currentNapas--;
            updateNapasLabel();
        }
    }

    @FXML
    private void incrementNapas(ActionEvent event) {
        currentNapas++;
        updateNapasLabel();
    }

    private void updateNapasLabel() {
        napasLabel.setText(String.valueOf(currentNapas));
    }

    private void startCountdown() {

        if (countdownTimeline != null) {
            countdownTimeline.stop();
        }

        countdownSeconds = 5;

        countdownTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    countdownSeconds--;
                    if (countdownLabel != null) {
                        countdownLabel.setText("Mulai dalam: " + countdownSeconds);
                    }

                    if (countdownSeconds <= 0) {
                        countdownTimeline.stop();
                        if (countdownLabel != null) {
                            countdownLabel.setText("Mulai!");
                        }
                        System.out.println("Countdown selesai! Audio/aktivitas dimulai.");
                        LatihanPernapasanController audio = new LatihanPernapasanController();

                        audio.playMusic("relaksasi.mp3"); // Ganti dengan nama file musik yang sesuai
                    }
                }));
        countdownTimeline.setCycleCount(countdownSeconds + 1); // Run for initial seconds + 1 for "Mulai!" message
        countdownTimeline.play();
    }
}