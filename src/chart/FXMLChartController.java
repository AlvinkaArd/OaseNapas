package chart;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import jadwal_latihan.DataArray;
import jadwal_latihan.JadwalLatihan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class FXMLChartController implements Initializable {

    @FXML
    private BarChart<String, Number> barChartDurasi;

    @FXML
    private javafx.scene.chart.CategoryAxis xAxis;

    @FXML
    private javafx.scene.chart.NumberAxis yAxis;

    private DataArray collectedData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        barChartDurasi.setTitle("Durasi Latihan Berdasarkan Sesi");
        xAxis.setLabel("Nama Sesi");
        yAxis.setLabel("Durasi (Menit)");

        XStream xStream = new XStream(new StaxDriver());

        xStream.alias("DataArray", DataArray.class);
        xStream.alias("JadwalLatihan", JadwalLatihan.class);

        xStream.allowTypesByWildcard(new String[] {
            "jadwal_latihan.**"
        });

        try {
            FileInputStream fis = new FileInputStream("SavedData.xml");
            collectedData = (DataArray) xStream.fromXML(fis);
            fis.close();
        } catch (Exception e) {
            System.err.println("Error saat memuat data: " + e.getMessage());
            collectedData = new DataArray(5);
        }

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
        barChartDurasi.getData().add(series);
    }

    @FXML
    public void kembaliButton(ActionEvent event) throws IOException {
        Parent scene2 = FXMLLoader.load(getClass().getResource("/main_page/FXMLMainPage.fxml"));
        Scene scene = new Scene(scene2);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Main Page");
        stage.show();
        System.out.println("Kembali ke halaman utama dari Riwayat Latihan");
    }
}