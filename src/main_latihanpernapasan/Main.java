package main_latihanpernapasan; // Sesuaikan dengan package Anda

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Pastikan nama FXML dan package controller sesuai
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("breathing_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 450); // Sesuaikan ukuran jika perlu
        stage.setTitle("Latihan Pernapasan MVC");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}