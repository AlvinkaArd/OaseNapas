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

    private void loadDataAndPopulateChart() {
        collectedData = XmlUtil.loadDataFromXml(Constants.DATA_FILE);
        populateChart();
    }
    
    private void populateChart() {
        ObservableList<PieChart.Data> chartData = chartGejala.getData(); // Ambil data yang sedang ada di chart

        if (collectedData != null && collectedData.getCollectedData() != null) {
            for (int i = 0; i < collectedData.getIndex(); i++) {
                JadwalLatihan jadwal = collectedData.getCollectedData()[i];
                if (jadwal != null) {
                    String gejalaName = jadwal.getGejala();
                    double durasiValue = jadwal.getDurasi();

                    boolean found = false;
                    for (PieChart.Data data : chartData) {
                        if (data.getName().equalsIgnoreCase(gejalaName)) {
                            data.setPieValue(durasiValue);
                            found = true;
                            break; 
                        }
                    }

                    if (!found) {
                        chartData.add(new PieChart.Data(gejalaName, durasiValue));
                    }
                }
            }
        }
    }
    
    @FXML
    public void kembaliButton(ActionEvent event) {
        NavigationUtil.navigateToMainPage(event);
    }
}