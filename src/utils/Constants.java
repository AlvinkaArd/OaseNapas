package utils;

/**
 * Konstanta untuk seluruh aplikasi
 */
public final class Constants {
    
    // Mencegah instansiasi
    private Constants() {}
    
    // Informasi aplikasi
    public static final String APP_NAME = "Oase Napas";
    public static final String APP_SUBTITLE = "Aplikasi Latihan Pernapasan";
    
    // Path file
    public static final String DATA_FILE = "SavedData.xml";
    
    // Path resource FXML
    public static final String MAIN_PAGE_FXML = "/main_page/FXMLMainPage.fxml";
    public static final String JADWAL_LATIHAN_FXML = "/jadwal_latihan/FXMLJadwalLatihan.fxml";
    public static final String LATIHAN_PERNAPASAN_FXML = "/latihan_pernapasan/FXMLLatihanPernapasan.fxml";
    public static final String CHART_FXML = "/chart/FXMLChart.fxml";
    
    // Judul jendela
    public static final String MAIN_PAGE_TITLE = "Halaman Utama";
    public static final String JADWAL_LATIHAN_TITLE = "Pengaturan Jadwal Latihan";
    public static final String LATIHAN_PERNAPASAN_TITLE = "Latihan Pernapasan";
    public static final String RIWAYAT_LATIHAN_TITLE = "Riwayat Latihan";
    
    // Limit data
    public static final int DEFAULT_ARRAY_SIZE = 10;
    public static final int MIN_DURASI = 1;
    public static final int MAX_DURASI = 60;
    public static final int DEFAULT_DURASI = 5;
    
    // Opsi choice box
    public static final String[] POLA_PERNAPASAN_OPTIONS = {"Sulit tidur", "Stress", "Sulit fokus"};
    public static final String[] MUSIK_OPTIONS = {"Tanpa suara latar", "Suara hujan", "Suasana hutan"};
    public static final String[] SUARA_PEMANDU_OPTIONS = {"Pria", "Wanita"};
    
    // Nilai default
    public static final String DEFAULT_POLA_PERNAPASAN = "Sulit tidur";
    public static final String DEFAULT_MUSIK = "Tanpa suara latar";
    public static final String DEFAULT_SUARA_PEMANDU = "Pria";
}
