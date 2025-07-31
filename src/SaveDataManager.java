import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveDataManager {

    private static final String APP_NAME = "OaseNapas"; 
    private static final String SAVE_FILE_NAME = "SavedData.xml";

    private static File getSaveFile() {
        String userHome = System.getProperty("user.home");
        String os = System.getProperty("os.name").toLowerCase();

        File saveDir;
        if (os.contains("win")) {
            // Windows:
            String appData = System.getenv("APPDATA");
            saveDir = new File(appData != null ? appData : userHome, APP_NAME);
        } else if (os.contains("mac")) {
            // macOS
            saveDir = new File(userHome, "Library/Application Support/" + APP_NAME);
        } else {
            // Linux/Unix
            saveDir = new File(userHome, "." + APP_NAME);
        }

        // Create the directory
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }

        return new File(saveDir, SAVE_FILE_NAME);
    }

    public static void save(String xmlData) {
        File saveFile = getSaveFile();
        try (FileWriter writer = new FileWriter(saveFile)) {
            writer.write(xmlData);
            System.out.println("Save data written to: " + saveFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save data. Please check file permissions.");
        }
    }

    public static String load() {
        File saveFile = getSaveFile();
        if (!saveFile.exists()) {
            System.out.println("Save file not found. Starting with new data.");
            return null;
        }

        try (java.util.Scanner scanner = new java.util.Scanner(saveFile).useDelimiter("\\A")) {
            String xmlData = scanner.hasNext() ? scanner.next() : "";
            System.out.println("Save data loaded from: " + saveFile.getAbsolutePath());
            return xmlData;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load data.");
            return null;
        }
    }
}