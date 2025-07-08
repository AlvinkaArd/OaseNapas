package jadwal_latihan;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class JadwalLatihanController implements Initializable{
    JadwalLatihanList data;
    DataArray collectedData;

    @FXML
    private TableView<JadwalLatihan> tvJadwal;

    @FXML
    private Spinner<Integer> spDurasi;

    @FXML
    private TextField tfTarik; 
    @FXML
    private TextField tfTahan;
    @FXML
    private TextField tfBuang; 

    @FXML
    private TextField tfNamaSesi; 
    
    @FXML
    private TextField tfWaktuLatihan;

    @FXML
    private ChoiceBox<String> cbGejala, cbMusik, cbSuaraPemandu;

    @FXML
    private TableColumn<JadwalLatihan, String> tcNamaSesi, tcGejala, tcMusik, tcSuaraPemandu, tcWaktuLatihan;
    
    @FXML
    private TableColumn<JadwalLatihan, String> tcTarik, tcTahan, tcBuang;

    @FXML
    private TableColumn<JadwalLatihan, Integer> tcDurasi;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcNamaSesi.setCellValueFactory(new PropertyValueFactory<>("namaSesi"));
        tcGejala.setCellValueFactory(new PropertyValueFactory<>("gejala"));
        tcMusik.setCellValueFactory(new PropertyValueFactory<>("musikLatar"));
        tcSuaraPemandu.setCellValueFactory(new PropertyValueFactory<>("suaraPemandu"));
        tcWaktuLatihan.setCellValueFactory(new PropertyValueFactory<>("waktuLatihan"));
        tcDurasi.setCellValueFactory(new PropertyValueFactory<>("durasi"));

        SpinnerValueFactory<Integer> durasiValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0);
        spDurasi.setValueFactory(durasiValueFactory);

        cbGejala.setValue("Stress");
        cbGejala.getItems().addAll("Stress","Sulit Tidur", "Kecemasan");

        cbMusik.setValue("Tanpa suara latar");
        cbMusik.getItems().addAll("Tanpa suara latar","Suara hujan", "Suasana hutan");

        cbSuaraPemandu.setValue("Pria");
        cbSuaraPemandu.getItems().addAll("Pria", "Wanita");

        data = new JadwalLatihanList();
        tvJadwal.setItems(data.getData());

        data.setDummy();

        collectedData = new DataArray(5);

        // LOAD DATA

    }

    @FXML
    public void addButtonAction(ActionEvent event) {
        String sesi = tfNamaSesi.getText();
        String musik = cbMusik.getValue();
        String gejala = cbGejala.getValue();
        String pemandu = cbSuaraPemandu.getValue();
        String waktu = tfWaktuLatihan.getText();
        int durasi = spDurasi.getValue();
        String tarik = tfTarik.getText();
        String tahan = tfTahan.getText();
        String buang = tfBuang.getText();

        data.setData(sesi, musik, gejala, pemandu, waktu, durasi, tarik, tahan, buang);
        tvJadwal.setItems(data.getData());

        collectedData.addData(sesi, musik, pemandu, waktu, durasi);

        System.out.println("Jadwal baru ditambahkan");

        // SAVE DATA ACTION
        XStream xStream = new XStream(new StaxDriver());

        String xml = xStream.toXML(collectedData);
        FileOutputStream outputDoc;

        try {
            byte[] collectedData = xml.getBytes("UTF-8");
            outputDoc = new FileOutputStream("SavedData.xml");
            outputDoc.write(collectedData);
            outputDoc.close();
        } catch (Exception io) {
            System.err.println("An error occours: " + io.getMessage());
        }
    }

    @FXML
    public void deleteButtonAction(ActionEvent event) {
        JadwalLatihan jadwal = tvJadwal.getSelectionModel().getSelectedItem();
        data.getData().remove(jadwal);
    }

    @FXML
    public void updateButtonAction(ActionEvent event) {
        JadwalLatihan jadwal = tvJadwal.getSelectionModel().getSelectedItem();
        
        String sesi = tfNamaSesi.getText();
        String musik = cbMusik.getValue();
        String pemandu = cbSuaraPemandu.getValue();
        String waktu = tfWaktuLatihan.getText();
        int durasi = spDurasi.getValue();

        jadwal.setNamaSesi(sesi);
        jadwal.setMusikLatar(musik);
        jadwal.setSuaraPemandu(pemandu);
        jadwal.setWaktuLatihan(waktu);
        jadwal.setDurasi(durasi);
        tvJadwal.refresh();
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