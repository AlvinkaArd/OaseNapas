package main_latihanpernapasan;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import latihan_pernapasan.LatihanPernapasan;
import java.util.logging.Logger;

public class BreathingController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(BreathingController.class.getName());

    @FXML
    private Label instructionLabel;
    @FXML
    private Label countdownLabel;

    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;

    @FXML
    private Label tarikDisplayLabel;
    @FXML
    private Label tahanDisplayLabel;
    @FXML
    private Label buangDisplayLabel;
    @FXML
    private Label durasiDisplayLabel;

    @FXML
    private MediaView backgroundMediaView;

    private MediaPlayer backgroundMediaPlayer;
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = new BreathingModel();

        instructionLabel.textProperty().bind(model.instructionTextProperty());
        countdownLabel.textProperty().bind(model.countdownTextProperty());

        breathingTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateBreathing(now);
            }
        };

        setupBackgroundVideo();
    }

    private void setupBackgroundVideo() {
        String videoFileName = "bgDaun1.mp4";
        URL videoUrl = getClass().getResource("/Resources/Audio/" + videoFileName);

        if (videoUrl != null) {
            Media media = new Media(videoUrl.toExternalForm());
            backgroundMediaPlayer = new MediaPlayer(media);

            backgroundMediaView.setMediaPlayer(backgroundMediaPlayer);
            backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundMediaPlayer.setMute(true);

            LOGGER.info("Background video loaded: " + videoFileName);
        } else {
            LOGGER.severe("Background video file not found: /Resources/Audio/" + videoFileName);
        }
    }

    public void setLatihanData(LatihanPernapasan latihan) {
        if (latihan != null) {
            tarikDisplayLabel.setText(latihan.getTarik() + " detik");
            tahanDisplayLabel.setText(latihan.getTahan() + " detik");
            buangDisplayLabel.setText(latihan.getBuang() + " detik");
            durasiDisplayLabel.setText(String.valueOf(latihan.getDurasi()) + " menit");

            try {
                model.tarikDurationProperty().set(Integer.parseInt(latihan.getTarik()));
                model.tahanDurationProperty().set(Integer.parseInt(latihan.getTahan()));
                model.buangDurationProperty().set(Integer.parseInt(latihan.getBuang()));
                model.totalDurationMinutesProperty().set(latihan.getDurasi());
            } catch (NumberFormatException e) {
                LOGGER.severe("Error parsing breathing durations: " + e.getMessage());
                model.tarikDurationProperty().set(0);
                model.tahanDurationProperty().set(0);
                model.buangDurationProperty().set(0);
            }
        }
    }

    @FXML
    private void startBreathing() {
        // Show confirmation dialog before starting
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Mulai Latihan");
        alert.setHeaderText("Anda siap mulai latihan?");
        alert.setContentText("Silahkan cari tempat dan posisi ternyaman anda sebelum mulai.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) { // Changed to ButtonType.OK for a standard "OK" button
            startButton.setDisable(true);
            stopButton.setDisable(false);

            if (backgroundMediaPlayer != null) {
                backgroundMediaPlayer.play();
            }

            currentTarikDuration = model.tarikDurationProperty().get();
            currentTahanDuration = model.tahanDurationProperty().get();
            currentBuangDuration = model.buangDurationProperty().get();

            startTime = System.nanoTime();
            phaseStartTime = System.nanoTime();
            currentPhase = BreathingPhase.TARIK;
            model.setInstructionText("Tarik!");

            breathingTimer.start();
        } else {
            // User cancelled, do not start the exercise
            LOGGER.info("Latihan dibatalkan oleh pengguna.");
        }
    }

    @FXML
    private void stopBreathing() {
        breathingTimer.stop();
        startButton.setDisable(false);
        stopButton.setDisable(true);
        model.setInstructionText("Latihan Selesai.");
        model.setCountdownText("00:00");

        if (backgroundMediaPlayer != null) {
            backgroundMediaPlayer.stop();
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Latihan Selesai");
        alert.setHeaderText("Latihan pernapasan telah selesai.");
        alert.setContentText("Apakah Anda ingin kembali ke halaman utama?");

        ButtonType buttonYes = new ButtonType("Ya");
        ButtonType buttonNo = new ButtonType("Tidak");

        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonYes) {
            returnToMainPage();
        }
    }

    private void updateBreathing(long now) {
        long elapsedTime = (now - startTime) / 1_000_000_000;
        long remainingTotalTime = (model.getTotalDurationMillis() / 1000) - elapsedTime;

        if (remainingTotalTime <= 0) {
            Platform.runLater(this::stopBreathing);
            return;
        }

        model.setCountdownText(formatTime((int) remainingTotalTime));

        long phaseElapsedTime = (now - phaseStartTime) / 1_000_000_000;

        switch (currentPhase) {
            case TARIK:
                if (phaseElapsedTime >= currentTarikDuration) {
                    currentPhase = BreathingPhase.TAHAN;
                    model.setInstructionText("Tahan!");
                    phaseStartTime = now;
                }
                break;
            case TAHAN:
                if (currentTahanDuration == 0 || phaseElapsedTime >= currentTahanDuration) {
                    currentPhase = BreathingPhase.BUANG;
                    model.setInstructionText("Buang!");
                    phaseStartTime = now;
                }
                break;
            case BUANG:
                if (phaseElapsedTime >= currentBuangDuration) {
                    currentPhase = BreathingPhase.TARIK;
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

    private void returnToMainPage() {
        try {
            URL mainPageUrl = getClass().getResource("/main_page/FXMLMainPage.fxml");
            if (mainPageUrl == null) {
                LOGGER.severe("MainPage.fxml not found. Check the path.");
                return;
            }
            FXMLLoader loader = new FXMLLoader(mainPageUrl);
            Parent root = loader.load();

            Scene currentScene = startButton.getScene();
            if (currentScene != null) {
                currentScene.setRoot(root);
            } else {
                LOGGER.warning("Current scene is null. Cannot return to main page.");
            }
        } catch (IOException e) {
            LOGGER.severe("Failed to load the main page FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}