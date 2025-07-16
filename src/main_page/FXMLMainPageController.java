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
import utils.NavigationUtil;

/**
 * Controller untuk halaman utama aplikasi
 */
public class FXMLMainPageController {

    @FXML
    private BorderPane mainPane;

    @FXML
    public void mulaiLatihanButton(ActionEvent event) {
        NavigationUtil.navigateToBreathingPage(event);
    }

    @FXML
    public void aturJadwalButton(ActionEvent event) {
        NavigationUtil.navigateToSchedulePage(event);
    }

    @FXML
    public void riwayatLatihanButton(ActionEvent event) {
        NavigationUtil.navigateToChartPage(event);
    }
     @FXML
    public void goRekomendasi(ActionEvent event) throws IOException {
        Parent scene2 = FXMLLoader.load(getClass().getResource("/solusigejala/Rekomendasi.fxml"));
        Scene scene = new Scene(scene2);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Rekomendasi page");
        stage.show();
        System.out.println("Ke halaman rekomendasi");
    }
}
