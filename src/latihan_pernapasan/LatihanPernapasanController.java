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
// import main_latihanpernapasan.BreathingController; // Tidak lagi langsung memanggil BreathingController dari sini

public class LatihanPernapasanController implements Initializable {

    @FXML
    private Spinner<Integer> spDurasi;
    @FXML
    private TextField tfTarik, tfTahan, tfBuang;
    @FXML
    private ChoiceBox<String> cbPolaPernapasan, cbMusik, cbSuaraPemandu;

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

    private void setupMusicListener() {
        cbMusik.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            stopMusic();

            if (newVal != null && !newVal.equals("Tidak ada")) {
                playMusic(newVal.toLowerCase() + ".mp3");
            }
        });
    }

    public void playMusic(String audioFile) {
        try {
            URL resource = getClass().getResource("Resources/Audio/" + audioFile); // Pastikan path ini benar relatif terhadap LatihanPernapasanController
            if (resource != null) {
                Media media = new Media(resource.toString());
                musicPlayer = new MediaPlayer(media);
                musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                musicPlayer.play();
            } else {
                System.out.println("File musik tidak ditemukan: " + audioFile);
            }
        } catch (Exception e) {
            System.out.println("Error memutar musik: " + e.getMessage());
        }
    }

    private void stopMusic() {
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
    }

    @FXML
    public void jadwalActionButton(ActionEvent event) {
        NavigationUtil.navigateToSchedulePage(event);
    }

    @FXML
    public void kembaliButton(ActionEvent event) {
        NavigationUtil.navigateToMainPage(event);
    }

    @FXML
    public void mulaiButton(ActionEvent event) throws IOException {
        stopMusic();

        int durasi = spDurasi.getValue();
        String polaPernapasan = cbPolaPernapasan.getValue();
        String musikLatar = cbMusik.getValue();
        String suaraPemandu = cbSuaraPemandu.getValue();
        String tarik = tfTarik.getText();
        String tahan = tfTahan.getText();
        String buang = tfBuang.getText();

        // Buat objek LatihanPernapasan yang lengkap
        LatihanPernapasan latihanBaru = new LatihanPernapasan(
                musikLatar,
                polaPernapasan, // Menggunakan polaPernapasan sebagai 'gejala'
                suaraPemandu,
                durasi,
                tarik,
                tahan,
                buang
        );

        try {
            // Muat FXML untuk MulaiLatihanController (misal: MulaiLatihanView.fxml)
            // Pastikan path ini benar: "/latihan_pernapasan/MulaiLatihanView.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/latihan_pernapasan/MulaiLatihan.fxml"));
            Parent root = loader.load();

            // Dapatkan controller dari scene baru
            MulaiLatihanController mulaiLatihanController = loader.getController();

            // Teruskan objek LatihanPernapasan ke MulaiLatihanController
            mulaiLatihanController.setLatihanData(latihanBaru);

            // Tampilkan scene baru
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            // stage.setMaximized(true); // Opsional, sesuaikan kebutuhan Anda
            stage.show();

            // Opsional: Tutup stage sebelumnya jika Anda tidak ingin kembali lagi ke LatihanPernapasan
            // ((Stage)((Node)event.getSource()).getScene().getWindow()).close();

        } catch (IOException e) {
            System.err.println("Gagal memuat halaman MulaiLatihanView: " + e.getMessage());
            e.printStackTrace();
        }
    }

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