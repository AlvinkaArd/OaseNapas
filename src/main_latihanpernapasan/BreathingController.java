package main_latihanpernapasan;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer; 
import javafx.scene.media.MediaView; 
import javafx.util.Duration; 

import java.net.URL;
import java.util.ResourceBundle;
import latihan_pernapasan.LatihanPernapasan;
import java.util.logging.Logger; 

public class BreathingController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(BreathingController.class.getName()); // Inisialisasi Logger

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
    private MediaView backgroundMediaView; // FXML ID untuk MediaView

    private MediaPlayer backgroundMediaPlayer; // MediaPlayer untuk video background
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

            // Bind MediaView ke MediaPlayer
            backgroundMediaView.setMediaPlayer(backgroundMediaPlayer);

            // Atur agar video loop terus-menerus
            backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            // Matikan suara video jika itu hanya background visual
            backgroundMediaPlayer.setMute(true);

            // Pastikan video dimulai saat scene dimuat
            // backgroundMediaPlayer.play(); // Bisa dipanggil di sini atau di event start breathing
             LOGGER.info("Background video loaded: " + videoFileName);
        } else {
            LOGGER.severe("Background video file not found: /assets/videos/" + videoFileName);
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
        startButton.setDisable(true);
        stopButton.setDisable(false);

        // Pastikan video diputar saat latihan dimulai
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
    }

    @FXML
    private void stopBreathing() {
        breathingTimer.stop();
        startButton.setDisable(false);
        stopButton.setDisable(true);
        model.setInstructionText("Latihan Selesai.");
        model.setCountdownText("00:00");

        // Hentikan video saat latihan selesai
        if (backgroundMediaPlayer != null) {
            backgroundMediaPlayer.stop();
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
}