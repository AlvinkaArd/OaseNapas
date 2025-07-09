package chart;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import jadwal_latihan.DataArray;
import jadwal_latihan.JadwalLatihan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import utils.Constants;
import utils.NavigationUtil;
import utils.XmlUtil;

/**
 * Controller untuk halaman chart/statistik yang menampilkan riwayat latihan
 */
public class FXMLChartController implements Initializable {
    
    private static final Logger LOGGER = Logger.getLogger(FXMLChartController.class.getName());

    @FXML
    private BarChart<String, Number> barChartDurasi;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private DataArray collectedData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupChart();
        loadDataAndPopulateChart();
    }
    
    /**
     * Setup judul chart dan label sumbu
     */
    private void setupChart() {
        barChartDurasi.setTitle("Durasi Latihan Berdasarkan Sesi");
        xAxis.setLabel("Nama Sesi");
        yAxis.setLabel("Durasi (Menit)");
    }
    
    /**
     * Muat data dari file XML dan isi chart
     */
    private void loadDataAndPopulateChart() {
        collectedData = XmlUtil.loadDataFromXml(Constants.DATA_FILE);
        populateChart();
    }
    
    /**
     * Isi chart dengan data dari collectedData
     */
    private void populateChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Durasi Latihan");

        if (collectedData != null && collectedData.getCollectedData() != null) {
            for (int i = 0; i < collectedData.getIndex(); i++) {
                JadwalLatihan jadwal = collectedData.getCollectedData()[i];
                if (jadwal != null) {
                    series.getData().add(new XYChart.Data<>(jadwal.getNamaSesi(), jadwal.getDurasi()));
                }
            }
        }
        
        barChartDurasi.getData().clear();
        barChartDurasi.getData().add(series);
        
        LOGGER.info("Chart diisi dengan " + series.getData().size() + " titik data");
    }

    @FXML
    public void kembaliButton(ActionEvent event) {
        NavigationUtil.navigateToMainPage(event);
    }
}