package main_page;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.lang.model.element.Element;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.NavigationUtil;

/**
 * Controller untuk halaman utama aplikasi
 */
public class FXMLMainPageController {

    @FXML
    private BorderPane mainPane;

    @FXML
    public void mulaiLatihanButton(ActionEvent event) {
        NavigationUtil.navigateToBreathingPage(event);
    }

    @FXML
    public void aturJadwalButton(ActionEvent event) {
        NavigationUtil.navigateToSchedulePage(event);
    }

    @FXML
    public void riwayatLatihanButton(ActionEvent event) {
        NavigationUtil.navigateToChartPage(event);
    }

    @FXML
    public void goRekomendasi(ActionEvent event) throws IOException {
        Parent scene2 = FXMLLoader.load(getClass().getResource("/solusigejala/Rekomendasi.fxml"));
        Scene scene = new Scene(scene2);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Rekomendasi page");
        stage.setMaximized(true); 
        stage.show();
        System.out.println("Ke halaman rekomendasi");
    }

     /**
     * This custom method is called from the main application class to pass the loaded data.
     *
     * @param xmlData The XML data as a string, or null if no data was found.
     */
    public void initializeWithData(String xmlData) {
        if (xmlData != null) {
            try {
                // Parse the XML data here
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = (Document) builder.parse(new ByteArrayInputStream(xmlData.getBytes()));
                ((org.w3c.dom.Node) doc.getDefaultRootElement()).normalize();

            } catch (Exception e) {
                System.err.println("Error parsing XML data.");
                e.printStackTrace();
            }
        }
    }
}
