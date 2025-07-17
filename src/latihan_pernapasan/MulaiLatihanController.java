package latihan_pernapasan;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utils.NavigationUtil; // Pastikan ini ada

public class MulaiLatihanController implements Initializable {

    @FXML
    private Label labelDurasi;
    @FXML
    private Label labelGejala; // New FXML ID for gejala label
    @FXML
    private Label labelTarik;
    @FXML
    private Label labelTahan;
    @FXML
    private Label labelBuang;
    @FXML
    private Label labelMusik;
    @FXML
    private Label labelSuaraPemandu;

    private LatihanPernapasan latihanData; // Untuk menyimpan data yang diterima

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inisialisasi awal jika diperlukan, tapi display data akan dipanggil setelah setLatihanData
    }

    // Metode untuk menerima data dari scene sebelumnya
    public void setLatihanData(LatihanPernapasan data) {
        this.latihanData = data;
        displayLatihanData(); // Panggil metode untuk menampilkan data
    }

    // Metode untuk menampilkan data ke UI
    private void displayLatihanData() {
        if (latihanData != null) {
            labelDurasi.setText(latihanData.getDurasi() + " menit");
            labelGejala.setText(latihanData.getGejala()); // Set the actual gejala from data
            // Display tarik/tahan/buang only if they are not empty (for custom patterns)
            if (!latihanData.getTarik().isEmpty() && !latihanData.getTahan().isEmpty() && !latihanData.getBuang().isEmpty()) {
                labelTarik.setText(latihanData.getTarik() + " detik");
                labelTahan.setText(latihanData.getTahan() + " detik");
                labelBuang.setText(latihanData.getBuang() + " detik");
            } else {
                // For standard patterns, you might want to hide these labels or show "N/A"
                labelTarik.setText("N/A");
                labelTahan.setText("N/A");
                labelBuang.setText("N/A");
            }

            labelMusik.setText(latihanData.getMusikLatar());
            labelSuaraPemandu.setText(latihanData.getSuaraPemandu());
        }
    }

    // This method name is more descriptive for starting the actual exercise
    @FXML
    public void mulaiLatihanAsli(ActionEvent event) {
        NavigationUtil.navigateToMulaiLatihan(event);
        System.out.println("Memulai latihan dengan data: " + latihanData.getDurasi() + " menit, " + latihanData.getMusikLatar());
    }

    @FXML
    public void kembaliKePengaturan(ActionEvent event) {
        NavigationUtil.navigateToBreathingPage(event); // Menggunakan metode baru untuk kembali ke pengaturan
    }
}