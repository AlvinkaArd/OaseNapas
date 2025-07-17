package utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Kelas utilitas untuk operasi navigasi umum
 */
public final class NavigationUtil {
    
    private static final Logger LOGGER = Logger.getLogger(NavigationUtil.class.getName());
    
    // Mencegah instansiasi
    private NavigationUtil() {}
    
    /**
     * Navigasi ke halaman yang berbeda
     * @param event Event aksi dari klik tombol
     * @param fxmlPath Path ke file FXML
     * @param title Judul jendela
     * @return true jika navigasi berhasil, false jika sebaliknya
     */
    public static boolean navigateToPage(ActionEvent event, String fxmlPath, String title) {
        try {
            Parent scene = FXMLLoader.load(NavigationUtil.class.getResource(fxmlPath));
            Scene newScene = new Scene(scene);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(newScene);
            stage.setTitle(title);
            stage.show();
            
            LOGGER.info("Successfully navigated to: " + title);
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error navigating to " + title + ": " + e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Navigasi ke halaman utama
     * @param event Event aksi
     * @return true jika navigasi berhasil
     */
    public static boolean navigateToMainPage(ActionEvent event) {
        return navigateToPage(event, Constants.MAIN_PAGE_FXML, Constants.MAIN_PAGE_TITLE);
    }
    
    /**
     * Navigasi ke halaman jadwal
     * @param event Event aksi
     * @return true jika navigasi berhasil
     */
    public static boolean navigateToSchedulePage(ActionEvent event) {
        return navigateToPage(event, Constants.JADWAL_LATIHAN_FXML, Constants.JADWAL_LATIHAN_TITLE);
    }
    
    /**
     * Navigasi ke halaman latihan pernapasan
     * @param event Event aksi
     * @return true jika navigasi berhasil
     */
    public static boolean navigateToBreathingPage(ActionEvent event) {
        return navigateToPage(event, Constants.LATIHAN_PERNAPASAN_FXML, Constants.LATIHAN_PERNAPASAN_TITLE);
    }

    public static FXMLLoader navigateAndLoad(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource(Constants.LATIHAN_PERNAPASAN_FXML));
            Parent root = loader.load(); 
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle(Constants.LATIHAN_PERNAPASAN_TITLE);
            stage.setScene(new Scene(root));
            stage.show();
            return loader; 
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load FXML: " + Constants.LATIHAN_PERNAPASAN_FXML, e);
            return null; 
        }
    }
    
    /**
     * Navigasi ke halaman chart/riwayat
     * @param event Event aksi
     * @return true jika navigasi berhasil
     */
    public static boolean navigateToChartPage(ActionEvent event) {
        return navigateToPage(event, Constants.CHART_FXML, Constants.RIWAYAT_LATIHAN_TITLE);
    }

    public static boolean navigateToMulaiLatihan(ActionEvent event) {
        return navigateToPage(event, Constants.MULAILATIHAN_FXML, Constants.MULAILATIHAN_TITLE);
    }

    public static void navigateToLatihanPernapasanPage(ActionEvent event) {
        // Ini tidak lagi digunakan secara langsung karena data passing dilakukan di controller LatihanPernapasanController
        // Method ini bisa dihapus atau dipertahankan jika ada kasus lain yang tidak memerlukan passing data
        try {
             Parent root = FXMLLoader.load(NavigationUtil.class.getResource("/latihan_pernapasan/MulaiLatihan.fxml"));
             Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
             Scene scene = new Scene(root);
             stage.setScene(scene);
             stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
