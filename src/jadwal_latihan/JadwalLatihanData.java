package jadwal_latihan;

/**
 * Objek transfer data sederhana untuk serialisasi XML
 * Kelas ini menggunakan field Java biasa sebagai ganti properti JavaFX
 * untuk menghindari masalah akses modul dengan XStream
 */
public class JadwalLatihanData {
    private String namaSesi;
    private String musikLatar;
    private String gejala;
    private String suaraPemandu;
    private String waktuLatihan;
    private String tarik;
    private String tahan;
    private String buang;
    private int durasi;

    public JadwalLatihanData() {
        // Konstruktor default untuk XStream
    }

    public JadwalLatihanData(String namaSesi, String musikLatar, String gejala, String suaraPemandu, 
                           String waktuLatihan, int durasi, String tarik, String tahan, String buang) {
        this.namaSesi = namaSesi;
        this.musikLatar = musikLatar;
        this.gejala = gejala;
        this.suaraPemandu = suaraPemandu;
        this.waktuLatihan = waktuLatihan;
        this.durasi = durasi;
        this.tarik = tarik;
        this.tahan = tahan;
        this.buang = buang;
    }

    // Buat dari JadwalLatihan
    public static JadwalLatihanData fromJadwalLatihan(JadwalLatihan jadwal) {
        return new JadwalLatihanData(
            jadwal.getNamaSesi(),
            jadwal.getMusikLatar(),
            jadwal.getGejala(),
            jadwal.getSuaraPemandu(),
            jadwal.getWaktuLatihan(),
            jadwal.getDurasi(),
            jadwal.getTarik(),
            jadwal.getTahan(),
            jadwal.getBuang()
        );
    }

    // Konversi ke JadwalLatihan
    public JadwalLatihan toJadwalLatihan() {
        return new JadwalLatihan(namaSesi, musikLatar, gejala, suaraPemandu, 
                               waktuLatihan, durasi, tarik, tahan, buang);
    }

    //  getter & setter
    public String getNamaSesi() { return namaSesi; }
    public void setNamaSesi(String namaSesi) { this.namaSesi = namaSesi; }

    public String getMusikLatar() { return musikLatar; }
    public void setMusikLatar(String musikLatar) { this.musikLatar = musikLatar; }

    public String getGejala() { return gejala; }
    public void setGejala(String gejala) { this.gejala = gejala; }

    public String getSuaraPemandu() { return suaraPemandu; }
    public void setSuaraPemandu(String suaraPemandu) { this.suaraPemandu = suaraPemandu; }

    public String getWaktuLatihan() { return waktuLatihan; }
    public void setWaktuLatihan(String waktuLatihan) { this.waktuLatihan = waktuLatihan; }

    public String getTarik() { return tarik; }
    public void setTarik(String tarik) { this.tarik = tarik; }

    public String getTahan() { return tahan; }
    public void setTahan(String tahan) { this.tahan = tahan; }

    public String getBuang() { return buang; }
    public void setBuang(String buang) { this.buang = buang; }

    public int getDurasi() { return durasi; }
    public void setDurasi(int durasi) { this.durasi = durasi; }
}
