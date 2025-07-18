package jadwal_latihan;

import java.time.LocalDate;
import java.time.format.DateTimeParseException; // Import for parsing exceptions

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JadwalLatihanList {
    private final ObservableList<JadwalLatihan> list;

    /**
     * Konstruktor menginisialisasi observable list kosong
     */
    public JadwalLatihanList() {
        this.list = FXCollections.observableArrayList();
    }

    /**
     * Dapatkan observable list untuk binding UI
     * @return ObservableList yang mengandung objek JadwalLatihan
     */
    public ObservableList<JadwalLatihan> getData() {
        return this.list;
    }

    /**
     * Tambahkan data jadwal latihan baru ke list (String waktuLatihan - Deprecated)
     * Ini akan mencoba mengonversi String ke LocalDate. Jika format tidak sesuai,
     * tanggal akan menjadi null.
     * @deprecated Gunakan addData(String, String, String, String, LocalDate, int, String, String, String) sebagai gantinya.
     */
    // @Deprecated
    // public void addData(String namaSesi, String musikLatar, String gejala, String suaraPemandu,
    //                     String waktuLatihan, int durasi, String tarik, String tahan, String buang) {
    //     LocalDate parsedDate = null;
    //     if (waktuLatihan != null && !waktuLatihan.trim().isEmpty()) {
    //         try {
    //             // IMPORTANT: This parsing assumes ISO_LOCAL_DATE format (e.g., "2025-07-18").
    //             // If your existing String data is "Senin Pagi", this will fail.
    //             // For such cases, you might want to consider not parsing it to LocalDate
    //             // if it's purely descriptive, or handle specific date formats if possible.
    //             parsedDate = LocalDate.parse(waktuLatihan);
    //         } catch (DateTimeParseException e) {
    //             System.err.println("WARNING: Could not parse date string '" + waktuLatihan + "'. Using null for date. Error: " + e.getMessage());
    //         }
    //     }
    //     addData(namaSesi, musikLatar, gejala, suaraPemandu, parsedDate, durasi, tarik, tahan, buang);
    // }

    /**
     * Tambahkan data jadwal latihan baru ke list (kompatibilitas mundur)
     * @deprecated Gunakan addData(String, String, String, String, LocalDate, int, String, String, String) sebagai gantinya.
     */
    @Deprecated
    public void setData(String namaSesi, String musikLatar, String gejala, String suaraPemandu,
                        LocalDate waktuLatihan, int durasi, String tarik, String tahan, String buang) {
        // Just call the addData method that handles String parsing
        addData(namaSesi, musikLatar, gejala, suaraPemandu, waktuLatihan, durasi, tarik, tahan, buang);
    }

    /**
     * Tambahkan data contoh untuk keperluan testing
     */
    public void addSampleData() {
        // Use LocalDate.of for specific dates
        list.add(new JadwalLatihan("Sesi Pagi", "Suara hujan", "Stress", "Wanita",
                                    LocalDate.of(2025, 7, 18), 15, "4", "4", "4"));
        list.add(new JadwalLatihan("Sebelum Tidur", "Suasana hutan", "Sulit tidur", "Pria",
                                    LocalDate.of(2025, 7, 19), 10, "4", "3", "7"));
    }

    /**
     * Hapus semua data dari list
     */
    public void clear() {
        list.clear();
    }

    /**
     * Dapatkan ukuran list
     * @return Jumlah item dalam list
     */
    public int size() {
        return list.size();
    }

    /**
     * Periksa apakah list kosong
     * @return true jika list tidak mengandung elemen
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Tambahkan data jadwal latihan baru ke list (dengan LocalDate untuk waktuLatihan)
     */
    public void addData(String sesi, String musik, String gejala, String pemandu, LocalDate waktu, int durasi,
                        String tarik, String tahan, String buang) {
        JadwalLatihan jadwal = new JadwalLatihan(sesi, musik, gejala, pemandu,
                                                waktu, durasi, tarik, tahan, buang);
        list.add(jadwal);
    }
}