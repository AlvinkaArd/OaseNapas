package jadwal_latihan;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class JadwalLatihan {
    private SimpleStringProperty namaSesi, musikLatar, gejala, suaraPemandu, waktuLatihan, tarik, tahan, buang;
    private SimpleIntegerProperty durasi;

    public JadwalLatihan(String namaSesi, String musikLatar, String gejala, String suaraPemandu, String waktuLatihan, int durasi, String tarik, String tahan, String buang) {
        this.namaSesi = new SimpleStringProperty(namaSesi);
        this.waktuLatihan = new SimpleStringProperty(waktuLatihan);
        this.gejala = new SimpleStringProperty(gejala);
        this.musikLatar = new SimpleStringProperty(musikLatar);
        this.suaraPemandu = new SimpleStringProperty(suaraPemandu);
        this.durasi = new SimpleIntegerProperty(durasi);
        this.tarik = new SimpleStringProperty(tarik);
        this.tahan = new SimpleStringProperty(tahan);
        this.buang = new SimpleStringProperty(buang);
    }

    public JadwalLatihan() {
        this("", "","", "", "", 0, "", "", "");
    }

    public String getNamaSesi() {
        return namaSesi.get();
    }

    public void setNamaSesi(String ss) {
        this.namaSesi.set(ss);
    }

    public String getMusikLatar() {
        return musikLatar.get();
    }

    public void setMusikLatar(String ml) {
        this.musikLatar.set(ml);
    }

    public String getGejala() {
        return gejala.get();
    }

    public void setGejala(String gj){
        this.gejala.set(gj);
    }

    public String getSuaraPemandu() {
        return suaraPemandu.get();
    }

    public void setSuaraPemandu(String sp) {
        this.suaraPemandu.set(sp);
    }

    public String getWaktuLatihan() {
        return waktuLatihan.get();
    }

    public void setWaktuLatihan(String wl) {
        this.waktuLatihan.set(wl);
    }

    public int getDurasi() {
        return durasi.get();
    }

    public void setDurasi(int durasi) {
        this.durasi.set(durasi);
    }

    public String getTahan() {
        return tahan.get();
    }

    public void setTahan(String tahan) {
        this.tahan.set(tahan);
    }

    public String getTarik() {
        return tarik.get();
    }

    public void setTarik(String tarik) {
        this.tahan.set(tarik);
    }

    public String getBuang() {
        return buang.get();
    }

    public void setBuang(String buang) {
        this.buang.set(buang);
    }
}