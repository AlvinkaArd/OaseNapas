package jadwal_latihan;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JadwalLatihanList {
    private ObservableList<JadwalLatihan> list;

    public JadwalLatihanList() {
        list = FXCollections.observableArrayList();
    }

    public ObservableList<JadwalLatihan> getData() {
        return this.list;
    }

    public void setData(String namaPola, String musikLatar, String gejala, String suaraPemandu, String waktuLatihan, int durasi, String tarik, String tahan, String buang) {
        list.add(new JadwalLatihan(namaPola, musikLatar, gejala, suaraPemandu, waktuLatihan, durasi, tarik, tahan, buang));
    }

    public void setDummy() {
        list.add(new JadwalLatihan("Sesi Pagi", "Suara hujan", "Stress", "Wanita", "Rabu, 25 Januari", 15, "4", "4","4"));
        list.add(new JadwalLatihan("Sebelum Tidur", "Suasana hutan", "Sulit tidur", "Pria", "Minggu, 29 Juni", 10, "4", "3", "7"));
    }
}
