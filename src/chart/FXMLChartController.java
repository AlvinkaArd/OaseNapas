package chart;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import jadwal_latihan.DataArray;
import jadwal_latihan.JadwalLatihan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import utils.Constants;
import utils.NavigationUtil;
import utils.XmlUtil;
public class FXMLChartController implements Initializable {
    @FXML
    private PieChart chartGejala;

    private DataArray collectedData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDataAndPopulateChart();
        chartGejala.setTitle("Durasi Latihan Berdasarkan Gejala");
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
        ObservableList<PieChart.Data> series = FXCollections.observableArrayList();

        if (collectedData != null && collectedData.getCollectedData() != null) {
            for (int i = 0; i < collectedData.getIndex(); i++) {
                JadwalLatihan jadwal = collectedData.getCollectedData()[i];
                if (jadwal != null) {
                    series.add(new PieChart.Data(jadwal.getGejala(), jadwal.getDurasi()));
                }
            }
        }
        
        chartGejala.getData().clear();
        chartGejala.setData(series);
    }

    @FXML
    public void kembaliButton(ActionEvent event) {
        NavigationUtil.navigateToMainPage(event);
    }
}