package solusigejala;

// Controller.java

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox; // MODIFIKASI: Import ComboBox untuk digunakan sebagai input gejala
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.stream.Collectors; // MODIFIKASI: Import Collectors untuk Stream API (jika digunakan)
import java.util.Arrays; // MODIFIKASI: Import Arrays untuk menggunakan Arrays.stream


public class RekomendasiSolusiController {

    @FXML
    private ComboBox<String> gejalaComboBox; // MODIFIKASI: Mengganti TextField dengan ComboBox<String> untuk pilihan gejala

    @FXML
    private VBox resultContainer;

    private RekomendasiSolusiDataArray allRekomendasiData;

    /**
     * Inisialisasi controller. Dipanggil otomatis setelah FXML dimuat.
     * Mengisi data dummy saat aplikasi dimulai.
     */
    @FXML
    public void initialize() {
        allRekomendasiData = DataInitializer.initializeDummyData();

        // MODIFIKASI: Mengisi ComboBox dengan daftar gejala dari data dummy.
        // Ambil semua gejala unik dari dataArray.
        String[] gejalaOptions = Arrays.stream(allRekomendasiData.getRekomendasiSolusiData()) // Konversi array ke Stream
                                       .map(RekomendasiSolusiData::getGejala) // Ambil hanya nama gejala
                                       .distinct() // Hapus gejala duplikat (jika ada)
                                       .toArray(String[]::new); // Ubah kembali menjadi array String
        gejalaComboBox.getItems().addAll(gejalaOptions); // Tambahkan semua opsi gejala ke ComboBox

        // Opsional: Atur prompt text atau nilai default untuk ComboBox.
        gejalaComboBox.setPromptText("Pilih gejala"); //
    }

    /**
     * Menangani aksi ketika tombol "Cari" diklik.
     * Mencari gejala berdasarkan input pengguna dan menampilkan rekomendasi yang relevan.
     *
     * @param event Aksi event dari klik tombol.
     */
    @FXML
    private void handleSearchButtonAction(ActionEvent event) {
        String selectedGejala = gejalaComboBox.getValue(); // MODIFIKASI: Ambil nilai yang dipilih dari ComboBox

        resultContainer.getChildren().clear();

        if (selectedGejala == null || selectedGejala.isEmpty()) { // MODIFIKASI: Periksa jika tidak ada gejala yang dipilih
            showAlert("Pilihan Kosong", "Mohon pilih gejala dari daftar.");
            return;
        }

        boolean found = false;
        // Iterasi melalui array biasa untuk mencari kecocokan
        for (RekomendasiSolusiData data : allRekomendasiData.getRekomendasiSolusiData()) {
            // MODIFIKASI: Cocokkan gejala yang dipilih secara langsung. Tidak perlu toLowerCase()
            // karena pilihan ComboBox berasal dari data asli yang sudah konsisten.
            if (data.getGejala().equals(selectedGejala)) {
                displayResult(data);
                found = true;
                break;
            }
        }

        if (!found) {
            showAlert("Tidak Ditemukan", "Tidak ada rekomendasi untuk gejala yang dipilih ini.");
        }
    }

    /**
     * Menampilkan detail rekomendasi di dalam VBox resultContainer.
     *
     * @param data Objek RekomendasiSolusiData yang akan ditampilkan.
     */
    private void displayResult(RekomendasiSolusiData data) {
        Text gejalaText = new Text("Gejala: " + data.getGejala());
        gejalaText.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Text rekomendasiText = new Text("Rekomendasi Pola: " + data.getRekomendasiPola());
        rekomendasiText.setWrappingWidth(380);
        rekomendasiText.setStyle("-fx-font-size: 12px;");

        Hyperlink artikelLink = new Hyperlink("Baca Artikel: " + data.getArtikel());
        artikelLink.setStyle("-fx-font-size: 12px;");
        artikelLink.setOnAction(e -> openLink(data.getArtikel()));

        resultContainer.getChildren().addAll(gejalaText, rekomendasiText, artikelLink);
        resultContainer.setSpacing(8);
    }

    /**
     * Mencoba membuka URL di browser default pengguna.
     *
     * @param url String URL yang akan dibuka.
     */
    private void openLink(String url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                showAlert("Error", "Browser tidak dapat dibuka secara otomatis. Silakan salin URL berikut: " + url);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            showAlert("Error", "Terjadi kesalahan saat membuka tautan.");
        }
    }

    /**
     * Menampilkan dialog informasi sederhana kepada pengguna.
     *
     * @param title   Judul dialog.
     * @param message Isi pesan dialog.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        Parent scene2 = FXMLLoader.load(getClass().getResource("/main_page/FXMLMainPage.fxml"));
        Scene scene = new Scene(scene2);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("main page");
        System.out.println("Ke halaman main");
        stage.show(); 
    }
}