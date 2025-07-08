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

public class LatihanPernapasanController implements Initializable {
    @FXML
    private Spinner<Integer> spDurasi;

    @FXML
    private TextField tfTarik, tfTahan, tfBuang;

    @FXML
    private ChoiceBox<String> cbPolaPernapasan, cbMusik, cbSuaraPemandu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0);
        spDurasi.setValueFactory(value);

        cbPolaPernapasan.setValue("Sulit tidur");
        cbPolaPernapasan.getItems().addAll("Sulit tidur","Stress","Sulit fokus");

        cbMusik.setValue("Tanpa suara latar");
        cbMusik.getItems().addAll("Tanpa suara latar","Suara hujan", "Suasana hutan");

        cbSuaraPemandu.setValue("Pria");
        cbSuaraPemandu.getItems().addAll("Pria", "Wanita");
    }

    @FXML
    public void jadwalActionButton(ActionEvent event) throws IOException {
        Parent scene2 = FXMLLoader.load(getClass().getResource("/jadwal_latihan/FXMLJadwalLatihan.fxml"));
        Scene scene = new Scene(scene2);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Pengaturan Jadwal Latihan");
        stage.show();
        System.out.println("Ke halaman pengaturan jadwal latihan");
    }

    @FXML
    public void kembaliButton(ActionEvent event) throws IOException {
        Parent scene2 = FXMLLoader.load(getClass().getResource("/main_page/FXMLMainPage.fxml"));
        Scene scene = new Scene(scene2);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Main Page");
        stage.show();
        System.out.println("Ke halaman utama");
    }

}
