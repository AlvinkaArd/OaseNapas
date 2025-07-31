// GUI_OaseNapas.java
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main_page.FXMLMainPageController;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class GUI_OaseNapas extends Application {

    private static final Logger LOGGER = Logger.getLogger(GUI_OaseNapas.class.getName());
    private static final String APP_TITLE = "Oase Napas - Aplikasi Latihan Pernapasan";
    private static final String MAIN_PAGE_FXML = "/main_page/FXMLMainPage.fxml";

    @Override
    public void start(Stage stage) { 
        // load XML Data
        try {
            String xmlData = SaveDataManager.load();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_PAGE_FXML));
            Parent root = loader.load();
            FXMLMainPageController controller = loader.getController();
            
            if (controller != null) {
                controller.initializeWithData(xmlData);
            }
            
            Scene scene = new Scene(root);
            
            stage.setTitle(APP_TITLE);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setMaximized(true); 
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Kesalahan saat memulai aplikasi: " + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("Failed to load application FXML or data.", e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}