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
        tcTarik.setCellValueFactory(new PropertyValueFactory<>("tarik"));
        tcTahan.setCellValueFactory(new PropertyValueFactory<>("tahan"));
        tcBuang.setCellValueFactory(new PropertyValueFactory<>("buang"));

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

        // Add selection listener to table
        tvJadwal.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tableRowSelected();
            }
        });

        data.setDummy();

        collectedData = new DataArray(9);

        // LOAD DATA
        loadSavedData();

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

        collectedData.addData(sesi, musik, gejala, pemandu, waktu, durasi, tarik, tahan, buang);
        collectedData.incrementIndex();

        System.out.println("Jadwal baru ditambahkan");

        // SAVE DATA ACTION
        XStream xStream = new XStream(new StaxDriver());
        
        // Configure XStream with proper aliases to match the expected XML format
        xStream.alias("jadwal__latihan.DataArray", DataArrayForSerialization.class);
        xStream.alias("jadwal__latihan.JadwalLatihan", JadwalLatihanData.class);
        xStream.allowTypesByWildcard(new String[] {
            "jadwal_latihan.**"
        });

        // Convert to serialization-safe format
        DataArrayForSerialization dataToSave = DataArrayForSerialization.fromDataArray(collectedData);
        String xml = xStream.toXML(dataToSave);
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
        String gejala = cbGejala.getValue();
        String pemandu = cbSuaraPemandu.getValue();
        String waktu = tfWaktuLatihan.getText();
        int durasi = spDurasi.getValue();
        String tarik = tfTarik.getText();
        String tahan = tfTahan.getText();
        String buang = tfBuang.getText();

        jadwal.setNamaSesi(sesi);
        jadwal.setMusikLatar(musik);
        jadwal.setGejala(gejala);
        jadwal.setSuaraPemandu(pemandu);
        jadwal.setWaktuLatihan(waktu);
        jadwal.setDurasi(durasi);
        jadwal.setTarik(tarik);
        jadwal.setTahan(tahan);
        jadwal.setBuang(buang);
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

    @FXML
    public void loadDataButtonAction(ActionEvent event) {
        // Clear existing data first
        data.getData().clear();
        collectedData = new DataArray(9);
        
        // Load data from file
        loadSavedData();
        
        System.out.println("Data dimuat ulang dari SavedData.xml");
    }

    private void loadSavedData() {
        try {
            java.io.File file = new java.io.File("SavedData.xml");
            if (file.exists()) {
                XStream xStream = new XStream(new StaxDriver());
                
                // Configure XStream with the same aliases used for saving
                xStream.alias("jadwal__latihan.DataArray", DataArrayForSerialization.class);
                xStream.alias("jadwal__latihan.JadwalLatihan", JadwalLatihanData.class);
                xStream.allowTypesByWildcard(new String[] {
                    "jadwal_latihan.**"
                });
                
                // Try to handle security settings for newer XStream versions
                try {
                    XStream.setupDefaultSecurity(xStream);
                    xStream.allowTypes(new Class[]{DataArrayForSerialization.class, JadwalLatihanData.class});
                } catch (Exception securityException) {
                    // Ignore security setup errors for older XStream versions
                }
                
                DataArrayForSerialization loadedData = (DataArrayForSerialization) xStream.fromXML(file);
                DataArray convertedData = loadedData.toDataArray();
                
                // Load data into the table view
                int loadedCount = 0;
                for (int i = 0; i < convertedData.getIndex(); i++) {
                    JadwalLatihan jadwal = convertedData.getCollectedData()[i];
                    if (jadwal != null) {
                        data.getData().add(jadwal);
                        // Copy to collectedData as well
                        if (collectedData.getIndex() < collectedData.getCollectedData().length) {
                            collectedData.getCollectedData()[collectedData.getIndex()] = jadwal;
                            collectedData.incrementIndex();
                        }
                        loadedCount++;
                    }
                }
                tvJadwal.setItems(data.getData());
                System.out.println("Data berhasil dimuat dari SavedData.xml: " + loadedCount + " item");
            } else {
                System.out.println("File SavedData.xml tidak ditemukan");
            }
        } catch (Exception e) {
            System.err.println("Error loading saved data: " + e.getMessage());
            e.printStackTrace();
            
            // Try to load with fallback method for older XML format
            try {
                loadSavedDataFallback();
            } catch (Exception fallbackException) {
                System.err.println("Fallback loading also failed: " + fallbackException.getMessage());
            }
        }
    }
    
    private void loadSavedDataFallback() {
        // Fallback method to handle old XML format with JavaFX properties
        try {
            java.io.File file = new java.io.File("SavedData.xml");
            if (file.exists()) {
                // Read as plain text and try to parse manually
                String xmlContent = java.nio.file.Files.readString(file.toPath());
                
                // Simple pattern matching for basic data extraction
                if (xmlContent.contains("<jadwal__latihan.JadwalLatihan>")) {
                    System.out.println("Attempting to parse XML manually...");
                    parseXMLManually(xmlContent);
                }
            }
        } catch (Exception e) {
            System.err.println("Manual XML parsing failed: " + e.getMessage());
        }
    }
    
    private void parseXMLManually(String xmlContent) {
        // Simple manual parsing for demonstration
        // This is a basic implementation - in production, you'd use a proper XML parser
        try {
            String[] jadwalBlocks = xmlContent.split("<jadwal__latihan.JadwalLatihan>");
            
            for (int i = 1; i < jadwalBlocks.length; i++) { // Skip first empty split
                String block = jadwalBlocks[i];
                if (block.contains("</jadwal__latihan.JadwalLatihan>")) {
                    String content = block.split("</jadwal__latihan.JadwalLatihan>")[0];
                    
                    // Extract values using simple string methods
                    String namaSesi = extractValue(content, "namaSesi");
                    String musikLatar = extractValue(content, "musikLatar");
                    String gejala = extractValue(content, "gejala");
                    String suaraPemandu = extractValue(content, "suaraPemandu");
                    String waktuLatihan = extractValue(content, "waktuLatihan");
                    String tarik = extractValue(content, "tarik");
                    String tahan = extractValue(content, "tahan");
                    String buang = extractValue(content, "buang");
                    
                    int durasi = 0;
                    try {
                        String durasiStr = extractValue(content, "durasi");
                        if (!durasiStr.isEmpty()) {
                            durasi = Integer.parseInt(durasiStr);
                        }
                    } catch (NumberFormatException e) {
                        durasi = 0;
                    }
                    
                    // Create and add the jadwal
                    JadwalLatihan jadwal = new JadwalLatihan(namaSesi, musikLatar, gejala, 
                                                           suaraPemandu, waktuLatihan, durasi, 
                                                           tarik, tahan, buang);
                    data.getData().add(jadwal);
                    
                    if (collectedData.getIndex() < collectedData.getCollectedData().length) {
                        collectedData.getCollectedData()[collectedData.getIndex()] = jadwal;
                        collectedData.incrementIndex();
                    }
                }
            }
            
            tvJadwal.setItems(data.getData());
            System.out.println("Manual parsing completed. Loaded " + (jadwalBlocks.length - 1) + " items");
            
        } catch (Exception e) {
            System.err.println("Manual parsing error: " + e.getMessage());
        }
    }
    
    private String extractValue(String content, String tagName) {
        try {
            // Look for both simple format: <tagName>value</tagName>
            // and complex format: <tagName><value>content</value></tagName>
            
            String startTag = "<" + tagName + ">";
            String endTag = "</" + tagName + ">";
            
            int startIndex = content.indexOf(startTag);
            if (startIndex == -1) return "";
            
            int endIndex = content.indexOf(endTag, startIndex);
            if (endIndex == -1) return "";
            
            String value = content.substring(startIndex + startTag.length(), endIndex).trim();
            
            // Check if this is a complex format with nested <value> tags
            if (value.contains("<value>") && value.contains("</value>")) {
                int valueStart = value.indexOf("<value>") + 7;
                int valueEnd = value.indexOf("</value>");
                if (valueEnd > valueStart) {
                    return value.substring(valueStart, valueEnd);
                }
            }
            
            return value;
        } catch (Exception e) {
            return "";
        }
    }

    @FXML
    public void tableRowSelected() {
        JadwalLatihan selectedItem = tvJadwal.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Populate form fields with selected item data
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
    }

    @FXML
    public void clearFormButtonAction(ActionEvent event) {
        // Clear all form fields
        tfNamaSesi.clear();
        cbMusik.setValue("Tanpa suara latar");
        cbGejala.setValue("Stress");
        cbSuaraPemandu.setValue("Pria");
        tfWaktuLatihan.clear();
        spDurasi.getValueFactory().setValue(0);
        tfTarik.clear();
        tfTahan.clear();
        tfBuang.clear();
        
        // Clear table selection
        tvJadwal.getSelectionModel().clearSelection();
        
        System.out.println("Form telah dibersihkan");
    }
}