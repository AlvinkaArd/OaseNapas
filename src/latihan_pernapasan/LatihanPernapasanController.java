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
import javafx.stage.Stage;
import utils.Constants;
import utils.NavigationUtil;

/**
 * Controller untuk halaman latihan pernapasan
 */
public class LatihanPernapasanController implements Initializable {
    
    @FXML
    private Spinner<Integer> spDurasi;

    @FXML
    private TextField tfTarik, tfTahan, tfBuang;

    @FXML
    private ChoiceBox<String> cbPolaPernapasan, cbMusik, cbSuaraPemandu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDurationSpinner();
        initializeChoiceBoxes();
    }
    
    /**
     * Inisialisasi spinner durasi dengan batasan yang sesuai
     */
    private void initializeDurationSpinner() {
        SpinnerValueFactory<Integer> valueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(
                Constants.MIN_DURASI, Constants.MAX_DURASI, Constants.DEFAULT_DURASI);
        spDurasi.setValueFactory(valueFactory);
    }
    
    /**
     * Inisialisasi semua choice box dengan nilai default dan opsi-opsinya
     */
    private void initializeChoiceBoxes() {
        // Choice box pola pernapasan
        cbPolaPernapasan.setValue(Constants.DEFAULT_POLA_PERNAPASAN);
        cbPolaPernapasan.getItems().addAll(Constants.POLA_PERNAPASAN_OPTIONS);

        // Choice box musik latar
        cbMusik.setValue(Constants.DEFAULT_MUSIK);
        cbMusik.getItems().addAll(Constants.MUSIK_OPTIONS);

        // Choice box suara pemandu
        cbSuaraPemandu.setValue(Constants.DEFAULT_SUARA_PEMANDU);
        cbSuaraPemandu.getItems().addAll(Constants.SUARA_PEMANDU_OPTIONS);
    }

    @FXML
    public void jadwalActionButton(ActionEvent event) {
        NavigationUtil.navigateToSchedulePage(event);
    }

    @FXML
    public void kembaliButton(ActionEvent event) {
        NavigationUtil.navigateToMainPage(event);
    }

    // @FXML
    // public void mulaiButton(ActionEvent event) {
    //     // TODO: Implementasikan logika latihan pernapasan yang sebenarnya
    //     // Untuk sementara, navigasi ke halaman chart untuk menampilkan hasil latihan
    //     NavigationUtil.navigateToChartPage(event);
    // }

    @FXML
    public void mulaiButton(ActionEvent event) throws IOException {
        NavigationUtil.navigateToMulaiLatihan(event);
    }

    public Spinner<Integer> getSpDurasi() {
        return this.spDurasi;
    }

    public TextField getTfTarik() {
        return this.tfTarik;
    }

    public TextField getTfTahan() {
        return this.tfTahan;
    }

    public TextField getTfBuang() {
        return this.tfBuang;
    }

    public ChoiceBox<String> getCbPolaPernapasan() {
        return this.cbPolaPernapasan;
    }

    public ChoiceBox<String> getCbMusik() {
        return this.cbMusik;
    }

    public ChoiceBox<String> getCbSuaraPemandu() {
        return this.cbSuaraPemandu;
    }
}
