package jadwal_latihan;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Kelas model untuk data jadwal latihan pernapasan
 * Menggunakan properti JavaFX untuk binding data UI
 */
public class JadwalLatihan {
    private final SimpleStringProperty namaSesi;
    private final SimpleStringProperty musikLatar;
    private final SimpleStringProperty gejala;
    private final SimpleStringProperty suaraPemandu;
    private final SimpleStringProperty waktuLatihan;
    private final SimpleStringProperty tarik;
    private final SimpleStringProperty tahan;
    private final SimpleStringProperty buang;
    private final SimpleIntegerProperty durasi;

    /**
     * Konstruktor dengan semua parameter
     */
    public JadwalLatihan(String namaSesi, String musikLatar, String gejala, String suaraPemandu, 
                        String waktuLatihan, int durasi, String tarik, String tahan, String buang) {
        this.namaSesi = new SimpleStringProperty(namaSesi);
        this.musikLatar = new SimpleStringProperty(musikLatar);
        this.gejala = new SimpleStringProperty(gejala);
        this.suaraPemandu = new SimpleStringProperty(suaraPemandu);
        this.waktuLatihan = new SimpleStringProperty(waktuLatihan);
        this.durasi = new SimpleIntegerProperty(durasi);
        this.tarik = new SimpleStringProperty(tarik);
        this.tahan = new SimpleStringProperty(tahan);
        this.buang = new SimpleStringProperty(buang);
    }

    /**
     * Konstruktor default dengan nilai kosong
     */
    public JadwalLatihan() {
        this("", "", "", "", "", 0, "", "", "");
    }

    // getter
    public String getNamaSesi() { return namaSesi.get(); }
    public String getMusikLatar() { return musikLatar.get(); }
    public String getGejala() { return gejala.get(); }
    public String getSuaraPemandu() { return suaraPemandu.get(); }
    public String getWaktuLatihan() { return waktuLatihan.get(); }
    public int getDurasi() { return durasi.get(); }
    public String getTarik() { return tarik.get(); }
    public String getTahan() { return tahan.get(); }
    public String getBuang() { return buang.get(); }

    // setter
    public void setNamaSesi(String namaSesi) { this.namaSesi.set(namaSesi); }
    public void setMusikLatar(String musikLatar) { this.musikLatar.set(musikLatar); }
    public void setGejala(String gejala) { this.gejala.set(gejala); }
    public void setSuaraPemandu(String suaraPemandu) { this.suaraPemandu.set(suaraPemandu); }
    public void setWaktuLatihan(String waktuLatihan) { this.waktuLatihan.set(waktuLatihan); }
    public void setDurasi(int durasi) { this.durasi.set(durasi); }
    public void setTarik(String tarik) { this.tarik.set(tarik); }
    public void setTahan(String tahan) { this.tahan.set(tahan); }
    public void setBuang(String buang) { this.buang.set(buang); }
}