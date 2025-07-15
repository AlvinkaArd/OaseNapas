package jadwal_latihan;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.Constants;
import utils.NavigationUtil;
import utils.XmlUtil;

public class JadwalLatihanController implements Initializable {
    
    private static final Logger LOGGER = Logger.getLogger(JadwalLatihanController.class.getName());
    
    private JadwalLatihanList data;
    private DataArray collectedData;

    @FXML
    private TableView<JadwalLatihan> tvJadwal;

    @FXML
    private Spinner<Integer> spDurasi;

    @FXML
    private TextField tfTarik, tfTahan, tfBuang, tfNamaSesi, tfWaktuLatihan;

    @FXML
    private ChoiceBox<String> cbGejala, cbMusik, cbSuaraPemandu;

    @FXML
    private TableColumn<JadwalLatihan, String> tcNamaSesi, tcGejala, tcMusik, tcSuaraPemandu, 
                                               tcWaktuLatihan, tcTarik, tcTahan, tcBuang;
    
    @FXML
    private TableColumn<JadwalLatihan, Integer> tcDurasi;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableColumns();
        initializeDurationSpinner();
        initializeChoiceBoxes();
        initializeData();
        setupTableSelectionListener();
    }
    
    /**
     * Inisialisasi factory nilai kolom tabel
     */
    private void initializeTableColumns() {
        tcNamaSesi.setCellValueFactory(new PropertyValueFactory<>("namaSesi"));
        tcGejala.setCellValueFactory(new PropertyValueFactory<>("gejala"));
        tcMusik.setCellValueFactory(new PropertyValueFactory<>("musikLatar"));
        tcSuaraPemandu.setCellValueFactory(new PropertyValueFactory<>("suaraPemandu"));
        tcWaktuLatihan.setCellValueFactory(new PropertyValueFactory<>("waktuLatihan"));
        tcDurasi.setCellValueFactory(new PropertyValueFactory<>("durasi"));
        tcTarik.setCellValueFactory(new PropertyValueFactory<>("tarik"));
        tcTahan.setCellValueFactory(new PropertyValueFactory<>("tahan"));
        tcBuang.setCellValueFactory(new PropertyValueFactory<>("buang"));
    }
    
    /**
     * Inisialisasi spinner durasi
     */
    private void initializeDurationSpinner() {
        SpinnerValueFactory<Integer> durasiValueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(
                Constants.MIN_DURASI, Constants.MAX_DURASI, Constants.DEFAULT_DURASI);
        spDurasi.setValueFactory(durasiValueFactory);
    }
    
    /**
     * Inisialisasi choice box dengan nilai default
     */
    private void initializeChoiceBoxes() {
        cbGejala.setValue("Stress");
        cbGejala.getItems().addAll("Stress", "Sulit Tidur", "Kecemasan");

        cbMusik.setValue(Constants.DEFAULT_MUSIK);
        cbMusik.getItems().addAll(Constants.MUSIK_OPTIONS);

        cbSuaraPemandu.setValue(Constants.DEFAULT_SUARA_PEMANDU);
        cbSuaraPemandu.getItems().addAll(Constants.SUARA_PEMANDU_OPTIONS);
    }
    
    /**
     * Inisialisasi struktur data dan muat data yang tersimpan
     */
    private void initializeData() {
        data = new JadwalLatihanList();
        tvJadwal.setItems(data.getData());
        collectedData = new DataArray(Constants.DEFAULT_ARRAY_SIZE);
        loadSavedData();
    }
    
    /**
     * Setup listener seleksi tabel
     */
    private void setupTableSelectionListener() {
        tvJadwal.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFormFromSelection(newSelection);
            }
        }
        );
    }

    @FXML
    public void addButtonAction(ActionEvent event) {
        if (!validateFormData()) {
            return;
        }
        
        String sesi = tfNamaSesi.getText().trim();
        String musik = cbMusik.getValue();
        String gejala = cbGejala.getValue();
        String pemandu = cbSuaraPemandu.getValue();
        String waktu = tfWaktuLatihan.getText().trim();
        int durasi = spDurasi.getValue();
        String tarik = tfTarik.getText().trim();
        String tahan = tfTahan.getText().trim();
        String buang = tfBuang.getText().trim();

        data.addData(sesi, musik, gejala, pemandu, waktu, durasi, tarik, tahan, buang);
        collectedData.addData(sesi, musik, gejala, pemandu, waktu, durasi, tarik, tahan, buang);

        saveDataToXML();
        clearFormButtonAction(event);
        LOGGER.info("Jadwal baru berhasil ditambahkan");
    }

    @FXML
    public void deleteButtonAction(ActionEvent event) {
        JadwalLatihan selectedJadwal = tvJadwal.getSelectionModel().getSelectedItem();
        
        if (selectedJadwal != null) {
            data.getData().remove(selectedJadwal);
            removeFromCollectedData(selectedJadwal);
            saveDataToXML();
            
            tvJadwal.getSelectionModel().clearSelection();
            clearFormButtonAction(event);
            LOGGER.info("Jadwal berhasil dihapus");
        } else {
            LOGGER.warning("Tidak ada jadwal yang dipilih untuk dihapus");
        }
    }

    @FXML
    public void updateButtonAction(ActionEvent event) {
        JadwalLatihan selectedJadwal = tvJadwal.getSelectionModel().getSelectedItem();
        
        if (selectedJadwal != null && validateFormData()) {
            updateJadwalFromForm(selectedJadwal);
            updateCollectedData(selectedJadwal);
            
            tvJadwal.refresh();
            saveDataToXML();
            LOGGER.info("Jadwal berhasil diperbarui");
        } else if (selectedJadwal == null) {
            LOGGER.warning("Tidak ada jadwal yang dipilih untuk diperbarui");
        }
    }

    @FXML
    public void kembaliButton(ActionEvent event) {
        NavigationUtil.navigateToMainPage(event);
    }

    @FXML
    public void loadDataButtonAction(ActionEvent event) {
        data.clear();
        collectedData = new DataArray(Constants.DEFAULT_ARRAY_SIZE);
        loadSavedData();
        LOGGER.info("Data dimuat ulang dari file XML");
    }

    @FXML
    public void clearFormButtonAction(ActionEvent event) {
        clearFormFields();
        tvJadwal.getSelectionModel().clearSelection();
        LOGGER.info("Form telah dibersihkan");
    }
    
    /**
     * Validasi data form sebelum diproses
     * @return true jika data valid, false jika tidak
     */
    private boolean validateFormData() {
        if (tfNamaSesi.getText().trim().isEmpty()) {
            LOGGER.warning("Nama sesi tidak boleh kosong");
            return false;
        }
        if (tfWaktuLatihan.getText().trim().isEmpty()) {
            LOGGER.warning("Waktu latihan tidak boleh kosong");
            return false;
        }
        return true;
    }
    
    /**
     * Perbarui objek JadwalLatihan dari data form
     * @param jadwal Objek yang akan diperbarui
     */
    private void updateJadwalFromForm(JadwalLatihan jadwal) {
        jadwal.setNamaSesi(tfNamaSesi.getText().trim());
        jadwal.setMusikLatar(cbMusik.getValue());
        jadwal.setGejala(cbGejala.getValue());
        jadwal.setSuaraPemandu(cbSuaraPemandu.getValue());
        jadwal.setWaktuLatihan(tfWaktuLatihan.getText().trim());
        jadwal.setDurasi(spDurasi.getValue());
        jadwal.setTarik(tfTarik.getText().trim());
        jadwal.setTahan(tfTahan.getText().trim());
        jadwal.setBuang(tfBuang.getText().trim());
    }
    
    /**
     * Perbarui entri yang sesuai di collectedData
     * @param updatedJadwal Objek jadwal yang telah diperbarui
     */
    private void updateCollectedData(JadwalLatihan updatedJadwal) {
        for (int i = 0; i < collectedData.getIndex(); i++) {
            JadwalLatihan current = collectedData.getCollectedData()[i];
            if (current != null && isSameJadwal(current, updatedJadwal)) {
                collectedData.getCollectedData()[i] = updatedJadwal;
                break;
            }
        }
    }
    
    /**
     * Isi field form dari item tabel yang dipilih
     * @param selectedItem Objek JadwalLatihan yang dipilih
     */
    private void populateFormFromSelection(JadwalLatihan selectedItem) {
        tfNamaSesi.setText(selectedItem.getNamaSesi());
        cbMusik.setValue(selectedItem.getMusikLatar());
        cbGejala.setValue(selectedItem.getGejala());
        cbSuaraPemandu.setValue(selectedItem.getSuaraPemandu());
        tfWaktuLatihan.setText(selectedItem.getWaktuLatihan());
        spDurasi.getValueFactory().setValue(selectedItem.getDurasi());
        tfTarik.setText(selectedItem.getTarik());
        tfTahan.setText(selectedItem.getTahan());
        tfBuang.setText(selectedItem.getBuang());
    }
    
    /**
     * Bersihkan semua field form ke nilai default
     */
    private void clearFormFields() {
        tfNamaSesi.clear();
        cbMusik.setValue(Constants.DEFAULT_MUSIK);
        cbGejala.setValue("Stress");
        cbSuaraPemandu.setValue(Constants.DEFAULT_SUARA_PEMANDU);
        tfWaktuLatihan.clear();
        spDurasi.getValueFactory().setValue(Constants.DEFAULT_DURASI);
        tfTarik.clear();
        tfTahan.clear();
        tfBuang.clear();
    }
    
    /**
     * Hapus jadwal dari collectedData
     * @param jadwalToRemove Jadwal yang akan dihapus
     */
    private void removeFromCollectedData(JadwalLatihan jadwalToRemove) {
        JadwalLatihan[] newCollectedData = new JadwalLatihan[collectedData.getCollectedData().length];
        int newIndex = 0;
        
        for (int i = 0; i < collectedData.getIndex(); i++) {
            JadwalLatihan current = collectedData.getCollectedData()[i];
            if (current != null && !isSameJadwal(current, jadwalToRemove)) {
                newCollectedData[newIndex] = current;
                newIndex++;
            }
        }
        
        collectedData = new DataArray(collectedData.getCollectedData().length);
        System.arraycopy(newCollectedData, 0, collectedData.getCollectedData(), 0, newIndex);
        collectedData.setIndex(newIndex);
    }
    
    /**
     * Periksa apakah dua objek JadwalLatihan sama
     * @param jadwal1 Jadwal pertama
     * @param jadwal2 Jadwal kedua
     * @return true jika keduanya mewakili data yang sama
     */
    private boolean isSameJadwal(JadwalLatihan jadwal1, JadwalLatihan jadwal2) {
        return jadwal1.getNamaSesi().equals(jadwal2.getNamaSesi()) &&
               jadwal1.getMusikLatar().equals(jadwal2.getMusikLatar()) &&
               jadwal1.getGejala().equals(jadwal2.getGejala()) &&
               jadwal1.getSuaraPemandu().equals(jadwal2.getSuaraPemandu()) &&
               jadwal1.getWaktuLatihan().equals(jadwal2.getWaktuLatihan()) &&
               jadwal1.getDurasi() == jadwal2.getDurasi() &&
               jadwal1.getTarik().equals(jadwal2.getTarik()) &&
               jadwal1.getTahan().equals(jadwal2.getTahan()) &&
               jadwal1.getBuang().equals(jadwal2.getBuang());
    }
    
    /**
     * Simpan data ke file XML menggunakan kelas utilitas
     */
    private void saveDataToXML() {
        if (XmlUtil.saveDataToXml(collectedData, Constants.DATA_FILE)) {
            LOGGER.info("Data berhasil disimpan ke XML");
        } else {
            LOGGER.warning("Gagal menyimpan data ke XML");
        }
    }

    /**
     * Muat data yang tersimpan dari file XML
     */
    private void loadSavedData() {
        DataArray loadedData = XmlUtil.loadDataFromXml(Constants.DATA_FILE);
        
        int loadedCount = 0;
        for (int i = 0; i < loadedData.getIndex(); i++) {
            JadwalLatihan jadwal = loadedData.getCollectedData()[i];
            if (jadwal != null) {
                data.getData().add(jadwal);
                if (!collectedData.isFull()) {
                    collectedData.getCollectedData()[collectedData.getIndex()] = jadwal;
                    collectedData.setIndex(collectedData.getIndex() + 1);
                }
                loadedCount++;
            }
        }
        
        tvJadwal.setItems(data.getData());
        LOGGER.info("Berhasil memuat " + loadedCount + " item dari XML");
    }
}