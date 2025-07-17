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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import utils.Constants;
import utils.NavigationUtil;

public class LatihanPernapasanController implements Initializable {

    @FXML
    private Spinner<Integer> spDurasi;
    @FXML
    private TextField tfTarik, tfTahan, tfBuang;
    @FXML
    private ChoiceBox<String> cbPolaPernapasan, cbMusik, cbSuaraPemandu;

    // Tambahkan MediaPlayer untuk musik latar
    private MediaPlayer musicPlayer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDurationSpinner();
        initializeChoiceBoxes();
        setupMusicListener();
    }

    private void initializeDurationSpinner() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                Constants.MIN_DURASI, Constants.MAX_DURASI, Constants.DEFAULT_DURASI);
        spDurasi.setValueFactory(valueFactory);
    }

    private void initializeChoiceBoxes() {
        cbPolaPernapasan.setValue(Constants.DEFAULT_POLA_PERNAPASAN);
        cbPolaPernapasan.getItems().addAll(Constants.POLA_PERNAPASAN_OPTIONS);

        cbMusik.setValue(Constants.DEFAULT_MUSIK);
        cbMusik.getItems().addAll(Constants.MUSIK_OPTIONS);

        cbSuaraPemandu.setValue(Constants.DEFAULT_SUARA_PEMANDU);
        cbSuaraPemandu.getItems().addAll(Constants.SUARA_PEMANDU_OPTIONS);
    }

    // Hanya setup listener untuk musik
    private void setupMusicListener() {
        cbMusik.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            stopMusic(); // Hentikan musik yang sedang diputar

            if (newVal != null && !newVal.equals("Tidak ada")) {
                playMusic(newVal.toLowerCase() + ".mp3");
            }
        });
    }

    // Method untuk memutar musik
    public void playMusic(String audioFile) {
        try {
            URL resource = getClass().getResource("Resources/Audio/" + audioFile);
            if (resource != null) {
                Media media = new Media(resource.toString());
                musicPlayer = new MediaPlayer(media);
                musicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop musik
                musicPlayer.play();
            } else {
                System.out.println("File musik tidak ditemukan: " + audioFile);
            }
        } catch (Exception e) {
            System.out.println("Error memutar musik: " + e.getMessage());
        }
    }

    // Method untuk menghentikan musik
    private void stopMusic() {
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
    }

    // Method yang sudah ada (tidak diubah)
    @FXML
    public void jadwalActionButton(ActionEvent event) {
        NavigationUtil.navigateToSchedulePage(event);
    }

    @FXML
    public void kembaliButton(ActionEvent event) {
        NavigationUtil.navigateToMainPage(event);
    }

    //tambahan passdata
    @FXML
    public void mulaiButton(ActionEvent event) throws IOException {
        stopMusic(); // Hentikan musik saat mulai latihan

        // 1. Ambil data dari UI
        int durasi = spDurasi.getValue();
        String polaPernapasan = cbPolaPernapasan.getValue();
        String musikLatar = cbMusik.getValue();
        String suaraPemandu = cbSuaraPemandu.getValue();
        String tarik = tfTarik.getText();
        String tahan = tfTahan.getText();
        String buang = tfBuang.getText();


        // 2. Buat objek LatihanPernapasan
        // Perhatikan bahwa "gejala" tidak diambil dari UI di sini. Jika Anda memiliki
        // ChoiceBox untuk gejala, Anda harus menambahkannya. Untuk sementara, kita pakai ""
        LatihanPernapasan latihanBaru = new LatihanPernapasan(
                musikLatar, // musikLatar
                polaPernapasan, // gejala, kita gunakan polaPernapasan sebagai pengganti sementara
                suaraPemandu, // suaraPemandu
                durasi,       // durasi
                tarik,        // tarik
                tahan,        // tahan
                buang         // buang
        );

        try {
            // 3. Load FXML untuk scene baru (MulaiLatihan.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/latihan_pernapasan/MulaiLatihan.fxml"));
            Parent root = loader.load();

            // 4. Dapatkan controller dari scene baru
            MulaiLatihanController mulaiLatihanController = loader.getController();

            // 5. Teruskan objek LatihanPernapasan ke controller scene baru
            mulaiLatihanController.setLatihanData(latihanBaru);

            // 6. Tampilkan scene baru
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Gagal memuat halaman MulaiLatihan: " + e.getMessage());
            e.printStackTrace();}
    }

    // Getter methods (tidak diubah)
    public Spinner<Integer> getSpDurasi() {
        return spDurasi;
    }

    public TextField getTfTarik() {
        return tfTarik;
    }

    public TextField getTfTahan() {
        return tfTahan;
    }

    public TextField getTfBuang() {
        return tfBuang;
    }

    public ChoiceBox<String> getCbPolaPernapasan() {
        return cbPolaPernapasan;
    }

    public ChoiceBox<String> getCbMusik() {
        return cbMusik;
    }

    public ChoiceBox<String> getCbSuaraPemandu() {
        return cbSuaraPemandu;
    }
}