package latihan_pernapasan;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utils.NavigationUtil;
import main_latihanpernapasan.BreathingController; // Import BreathingController

public class MulaiLatihanController implements Initializable {

    @FXML
    private Label labelDurasi;
    @FXML
    private Label labelGejala;
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
        // Inisialisasi awal jika diperlukan. Data akan ditampilkan setelah setLatihanData dipanggil.
    }

    // Metode untuk menerima data lengkap dari scene sebelumnya (LatihanPernapasanController)
    public void setLatihanData(LatihanPernapasan data) {
        this.latihanData = data;
        displayLatihanData(); // Panggil metode untuk menampilkan data ke UI pada MulaiLatihanView
    }

    // Metode untuk menampilkan data ke UI MulaiLatihanView
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

    // Ini adalah metode yang dipanggil saat tombol "Mulai Latihan" di MulaiLatihanView diklik
    @FXML
    public void mulaiLatihanAsli(ActionEvent event) {
        if (latihanData == null) {
            System.err.println("Error: Latihan data not set in MulaiLatihanController.");
            // Mungkin tampilkan alert ke pengguna
            return;
        }

        try {
            // Muat FXML untuk BreathingController (misal: breathing_view.fxml)
            // Pastikan path ini benar: "/main_latihanpernapasan/breathing_view.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_latihanpernapasan/breathing_view.fxml"));
            Parent root = loader.load();

            // Dapatkan controller dari scene latihan pernapasan
            BreathingController breathingController = loader.getController();

            // Buat objek LatihanPernapasan BARU atau gunakan yang sudah ada,
            // tapi hanya dengan data yang relevan (durasi, tarik, tahan, buang)
            LatihanPernapasan dataUntukBreathing = new LatihanPernapasan(
                null, null, null, // Data lain tidak diperlukan di BreathingController
                latihanData.getDurasi(),
                latihanData.getTarik(),
                latihanData.getTahan(),
                latihanData.getBuang()
            );

            // Teruskan data yang dipilih ke BreathingController
            breathingController.setLatihanData(dataUntukBreathing);

            // Tampilkan scene latihan pernapasan
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            // stage.setMaximized(true); // Opsional, sesuaikan kebutuhan Anda
            stage.show();

            System.out.println("Memulai latihan dengan data: "
                    + latihanData.getDurasi() + " menit, "
                    + latihanData.getTarik() + " tarik, "
                    + latihanData.getTahan() + " tahan, "
                    + latihanData.getBuang() + " buang."
            );

        } catch (IOException e) {
            System.err.println("Gagal memuat halaman BreathingView: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void kembaliKePengaturan(ActionEvent event) {
        NavigationUtil.navigateToBreathingPage(event); // Menggunakan metode baru untuk kembali ke pengaturan
    }
}