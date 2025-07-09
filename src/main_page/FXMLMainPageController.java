package main_page;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FXMLMainPageController {

    @FXML
    private BorderPane mainPane;

    @FXML
    public void mulaiLatihanButton(ActionEvent event) throws IOException {
        Parent scene2 = FXMLLoader.load(getClass().getResource("/latihan_pernapasan/FXMLLatihanPernapasan.fxml"));
        Scene scene = new Scene(scene2);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Latihan Pernapasan");
        stage.show();
        System.out.println("Ke halaman latihan pernapasan");
    }

    @FXML
    public void aturJadwalButton(ActionEvent event) throws IOException {
        Parent scene2 = FXMLLoader.load(getClass().getResource("/jadwal_latihan/FXMLJadwalLatihan.fxml"));
        Scene scene = new Scene(scene2);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Latihan Pernapasan");
        stage.show();
        System.out.println("Ke halaman latihan pernapasan");
    }

    @FXML
    public void riwayatLatihanButton(ActionEvent event) throws IOException {
        Parent scene2 = FXMLLoader.load(getClass().getResource("/chart/FXMLChart.fxml"));
        Scene scene = new Scene(scene2);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Riwayat Latihan");
        stage.show();
        System.out.println("Ke halaman riwayat latihan");
    }
}
