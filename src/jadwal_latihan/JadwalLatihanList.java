package jadwal_latihan;

import java.time.LocalDate;
import java.time.format.DateTimeParseException; 

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JadwalLatihanList {
    private final ObservableList<JadwalLatihan> list;

    public JadwalLatihanList() {
        this.list = FXCollections.observableArrayList();
    }

    public ObservableList<JadwalLatihan> getData() {
        return this.list;
    }

    @Deprecated
    public void setData(String namaSesi, String musikLatar, String gejala, String suaraPemandu,
                        LocalDate waktuLatihan, int durasi, String tarik, String tahan, String buang) {
        addData(namaSesi, musikLatar, gejala, suaraPemandu, waktuLatihan, durasi, tarik, tahan, buang);
    }


    public void clear() {
        list.clear();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void addData(String sesi, String musik, String gejala, String pemandu, LocalDate waktu, int durasi,
                        String tarik, String tahan, String buang) {
        JadwalLatihan jadwal = new JadwalLatihan(sesi, musik, gejala, pemandu,
                                                waktu, durasi, tarik, tahan, buang);
        list.add(jadwal);
    }
}