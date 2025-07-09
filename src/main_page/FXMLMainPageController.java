package main_page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
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
}
