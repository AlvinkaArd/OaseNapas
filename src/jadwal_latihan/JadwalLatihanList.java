package jadwal_latihan;

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
     * Tambahkan data jadwal latihan baru ke list
     */
    public void addData(String namaSesi, String musikLatar, String gejala, String suaraPemandu, 
                       String waktuLatihan, int durasi, String tarik, String tahan, String buang) {
        JadwalLatihan jadwal = new JadwalLatihan(namaSesi, musikLatar, gejala, suaraPemandu, 
                                               waktuLatihan, durasi, tarik, tahan, buang);
        list.add(jadwal);
    }

    /**
     * Tambahkan data jadwal latihan baru ke list (kompatibilitas mundur)
     */
    public void setData(String namaSesi, String musikLatar, String gejala, String suaraPemandu, 
                       String waktuLatihan, int durasi, String tarik, String tahan, String buang) {
        addData(namaSesi, musikLatar, gejala, suaraPemandu, waktuLatihan, durasi, tarik, tahan, buang);
    }

    /**
     * Tambahkan data contoh untuk keperluan testing
     */
    public void addSampleData() {
        list.add(new JadwalLatihan("Sesi Pagi", "Suara hujan", "Stress", "Wanita", 
                                  "Rabu, 25 Januari", 15, "4", "4", "4"));
        list.add(new JadwalLatihan("Sebelum Tidur", "Suasana hutan", "Sulit tidur", "Pria", 
                                  "Minggu, 29 Juni", 10, "4", "3", "7"));
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
}
