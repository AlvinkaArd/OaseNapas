package main_latihanpernapasan; // Sesuaikan dengan package Anda

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BreathingModel {

    // Durasi untuk setiap fase dalam detik
    private final IntegerProperty tarikDuration = new SimpleIntegerProperty(4);
    private final IntegerProperty tahanDuration = new SimpleIntegerProperty(4);
    private final IntegerProperty buangDuration = new SimpleIntegerProperty(6);
    private final IntegerProperty totalDurationMinutes = new SimpleIntegerProperty(5); // Total durasi latihan dalam menit

    // Properti untuk status UI yang akan diobservasi oleh View/Controller
    private final StringProperty instructionText = new SimpleStringProperty("Siap untuk memulai!");
    private final StringProperty countdownText = new SimpleStringProperty("00:00");

    // Getters untuk properti
    public IntegerProperty tarikDurationProperty() {
        return tarikDuration;
    }

    public IntegerProperty tahanDurationProperty() {
        return tahanDuration;
    }

    public IntegerProperty buangDurationProperty() {
        return buangDuration;
    }

    public IntegerProperty totalDurationMinutesProperty() {
        return totalDurationMinutes;
    }

    public StringProperty instructionTextProperty() {
        return instructionText;
    }

    public StringProperty countdownTextProperty() {
        return countdownText;
    }

    // Setters jika diperlukan (misalnya, jika View langsung mengubah Model,
    // tetapi lebih baik melalui Controller)
    public void setInstructionText(String text) {
        this.instructionText.set(text);
    }

    public void setCountdownText(String text) {
        this.countdownText.set(text);
    }

    // Metode untuk menghitung total durasi dalam milidetik
    public long getTotalDurationMillis() {
        return (long) totalDurationMinutes.get() * 60 * 1000;
    }
}