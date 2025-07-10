import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Kelas aplikasi utama untuk Oase Napas - Aplikasi Latihan Pernapasan
 */
public class GUI_OaseNapas extends Application {
    
    private static final Logger LOGGER = Logger.getLogger(GUI_OaseNapas.class.getName());
    private static final String APP_TITLE = "Oase Napas - Aplikasi Latihan Pernapasan";
    private static final String MAIN_PAGE_FXML = "/main_page/FXMLMainPage.fxml";

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(MAIN_PAGE_FXML));
            Scene scene = new Scene(root);
            
            stage.setTitle(APP_TITLE);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Kesalahan saat memulai aplikasi: " + e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}