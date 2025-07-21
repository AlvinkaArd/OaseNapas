package main_latihanpernapasan;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
// Removed Spinner imports as they are no longer in FXML
// import javafx.scene.control.Spinner;
// import javafx.scene.control.SpinnerValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import latihan_pernapasan.LatihanPernapasan; // Import your LatihanPernapasan model

public class BreathingController implements Initializable {

    @FXML
    private Label instructionLabel;
    @FXML
    private Label countdownLabel;

    // Removed @FXML for Spinners
    // @FXML
    // private Spinner<Integer> tarikDurationSpinner;
    // @FXML
    // private Spinner<Integer> tahanDurationSpinner;
    // @FXML
    // private Spinner<Integer> buangDurationSpinner;
    // @FXML
    // private Spinner<Integer> totalDurationSpinner;

    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;

    // Labels to display passed data (already present in your FXML)
    @FXML
    private Label tarikDisplayLabel; // Corresponds to Tarik:
    @FXML
    private Label tahanDisplayLabel; // Corresponds to Tahan:
    @FXML
    private Label buangDisplayLabel; // Corresponds to Buang:
    @FXML
    private Label durasiDisplayLabel; // Corresponds to Durasi Total:

    private BreathingModel model;
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
    public void initialize(URL url, ResourceBundle rb) {
        model = new BreathingModel();

        // Bind UI elements to Model properties
        instructionLabel.textProperty().bind(model.instructionTextProperty());
        countdownLabel.textProperty().bind(model.countdownTextProperty());

        breathingTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateBreathing(now);
            }
        };
    }

    // Method to receive data from LatihanPernapasanController
    public void setLatihanData(LatihanPernapasan latihan) {
        if (latihan != null) {
            // Update the Labels with the received data
            tarikDisplayLabel.setText(latihan.getTarik() + " detik"); // Add unit for clarity
            tahanDisplayLabel.setText(latihan.getTahan() + " detik");
            buangDisplayLabel.setText(latihan.getBuang() + " detik");
            durasiDisplayLabel.setText(String.valueOf(latihan.getDurasi()) + " menit");

            // Also, set the values in your BreathingModel directly
            // Now that spinners are gone, the model's properties are not automatically updated from them.
            // We need to set them here for the timer logic.
            try {
                model.tarikDurationProperty().set(Integer.parseInt(latihan.getTarik()));
                model.tahanDurationProperty().set(Integer.parseInt(latihan.getTahan()));
                model.buangDurationProperty().set(Integer.parseInt(latihan.getBuang()));
                model.totalDurationMinutesProperty().set(latihan.getDurasi());
            } catch (NumberFormatException e) {
                System.err.println("Error parsing breathing durations: " + e.getMessage());
                // Consider adding more robust error handling, e.g., default values or an alert
                // For now, setting to 0 or a safe default if parsing fails
                model.tarikDurationProperty().set(0);
                model.tahanDurationProperty().set(0);
                model.buangDurationProperty().set(0);
                // total duration is already an int, no parsing needed
            }
        }
    }

    @FXML
    private void startBreathing() {
        startButton.setDisable(true);
        stopButton.setDisable(false);

        // Get the values directly from the model, which were set by setLatihanData
        currentTarikDuration = model.tarikDurationProperty().get();
        currentTahanDuration = model.tahanDurationProperty().get();
        currentBuangDuration = model.buangDurationProperty().get();

        startTime = System.nanoTime();
        phaseStartTime = System.nanoTime();
        currentPhase = BreathingPhase.TARIK;
        model.setInstructionText("Tarik!");

        breathingTimer.start();
    }

    @FXML
    private void stopBreathing() {
        breathingTimer.stop();
        startButton.setDisable(false);
        stopButton.setDisable(true);
        model.setInstructionText("Latihan Selesai.");
        model.setCountdownText("00:00");
    }

    private void updateBreathing(long now) {
        long elapsedTime = (now - startTime) / 1_000_000_000; // Total time elapsed in seconds
        long remainingTotalTime = (model.getTotalDurationMillis() / 1000) - elapsedTime;

        if (remainingTotalTime <= 0) {
            Platform.runLater(this::stopBreathing);
            return;
        }

        // Update total countdown
        model.setCountdownText(formatTime((int) remainingTotalTime));

        long phaseElapsedTime = (now - phaseStartTime) / 1_000_000_000; // Time elapsed in current phase

        switch (currentPhase) {
            case TARIK:
                if (phaseElapsedTime >= currentTarikDuration) {
                    currentPhase = BreathingPhase.TAHAN;
                    model.setInstructionText("Tahan!");
                    phaseStartTime = now;
                }
                break;
            case TAHAN:
                // Handle case where 'tahan' duration might be 0
                if (currentTahanDuration == 0 || phaseElapsedTime >= currentTahanDuration) {
                    currentPhase = BreathingPhase.BUANG;
                    model.setInstructionText("Buang!");
                    phaseStartTime = now;
                }
                break;
            case BUANG:
                if (phaseElapsedTime >= currentBuangDuration) {
                    currentPhase = BreathingPhase.TARIK; // Loop back to Tarik
                    model.setInstructionText("Tarik!");
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