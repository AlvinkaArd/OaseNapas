package main_latihanpernapasan; // Sesuaikan dengan package Anda

import main_latihanpernapasan.BreathingModel; // Pastikan ini sesuai dengan package Anda
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class BreathingController implements Initializable {

    @FXML
    private Label instructionLabel;
    @FXML
    private Label countdownLabel;
    @FXML
    private Spinner<Integer> tarikDurationSpinner;
    @FXML
    private Spinner<Integer> tahanDurationSpinner;
    @FXML
    private Spinner<Integer> buangDurationSpinner;
    @FXML
    private Spinner<Integer> totalDurationSpinner;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;

    private BreathingModel model; // Instansiasi Model
    private AnimationTimer breathingTimer;
    private long startTime;
    private int currentTarikDuration;
    private int currentTahanDuration;
    private int currentBuangDuration;

    private enum BreathingPhase {
        TARIK, TAHAN, BUANG
    }

    private BreathingPhase currentPhase;
    private long phaseStartTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = new BreathingModel(); // Inisialisasi Model

        // Bind UI elements to Model properties
        instructionLabel.textProperty().bind(model.instructionTextProperty());
        countdownLabel.textProperty().bind(model.countdownTextProperty());

        // Inisialisasi spinner dan bind ke properti model
        SpinnerValueFactory.IntegerSpinnerValueFactory tarikFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, model.tarikDurationProperty().get());
        tarikDurationSpinner.setValueFactory(tarikFactory);
        model.tarikDurationProperty().bind(tarikDurationSpinner.valueProperty()); // Bind dua arah

        SpinnerValueFactory.IntegerSpinnerValueFactory tahanFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, model.tahanDurationProperty().get());
        tahanDurationSpinner.setValueFactory(tahanFactory);
        model.tahanDurationProperty().bind(tahanDurationSpinner.valueProperty());

        SpinnerValueFactory.IntegerSpinnerValueFactory buangFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, model.buangDurationProperty().get());
        buangDurationSpinner.setValueFactory(buangFactory);
        model.buangDurationProperty().bind(buangDurationSpinner.valueProperty());

        SpinnerValueFactory.IntegerSpinnerValueFactory totalFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, model.totalDurationMinutesProperty().get());
        totalDurationSpinner.setValueFactory(totalFactory);
        model.totalDurationMinutesProperty().bind(totalDurationSpinner.valueProperty());

        // Inisialisasi Timer
        breathingTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateBreathing(now);
            }
        };
    }

    @FXML
    private void startBreathing() {
        startButton.setDisable(true);
        stopButton.setDisable(false);

        // Ambil nilai dari model (yang sudah ter-bind dari spinner)
        currentTarikDuration = model.tarikDurationProperty().get();
        currentTahanDuration = model.tahanDurationProperty().get();
        currentBuangDuration = model.buangDurationProperty().get();

        startTime = System.nanoTime();
        phaseStartTime = System.nanoTime();
        currentPhase = BreathingPhase.TARIK;
        model.setInstructionText("Tarik!"); // Update model
        // countdownLabel sudah terikat ke model.countdownTextProperty()

        breathingTimer.start();
    }

    @FXML
    private void stopBreathing() {
        breathingTimer.stop();
        startButton.setDisable(false);
        stopButton.setDisable(true);
        model.setInstructionText("Latihan Selesai."); // Update model
        model.setCountdownText("00:00"); // Update model
    }

    private void updateBreathing(long now) {
        long elapsedTime = (now - startTime) / 1_000_000_000; // Total waktu berlalu dalam detik
        long remainingTotalTime = (model.getTotalDurationMillis() / 1000) - elapsedTime;

        if (remainingTotalTime <= 0) {
            Platform.runLater(this::stopBreathing);
            return;
        }

        // Update total countdown di model
        model.setCountdownText(formatTime((int) remainingTotalTime));

        long phaseElapsedTime = (now - phaseStartTime) / 1_000_000_000; // Waktu berlalu dalam fase saat ini

        switch (currentPhase) {
            case TARIK:
                if (phaseElapsedTime >= currentTarikDuration) {
                    currentPhase = BreathingPhase.TAHAN;
                    model.setInstructionText("Tahan!"); // Update model
                    phaseStartTime = now;
                }
                break;
            case TAHAN:
                if (phaseElapsedTime >= currentTahanDuration) {
                    currentPhase = BreathingPhase.BUANG;
                    model.setInstructionText("Buang!"); // Update model
                    phaseStartTime = now;
                }
                break;
            case BUANG:
                if (phaseElapsedTime >= currentBuangDuration) {
                    currentPhase = BreathingPhase.TARIK; // Kembali ke Tarik
                    model.setInstructionText("Tarik!"); // Update model
                    phaseStartTime = now;
                }
                break;
        }
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}