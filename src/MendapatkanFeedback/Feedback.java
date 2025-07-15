package MendapatkanFeedback;

import java.util.ArrayList;

public class Feedback {
    private int tahapanMasukan;
    private String isiMasukan;
    private ArrayList<String> daftarMasukan;

    public Feedback(int tahapanMasukan, String isiMasukan) {
        this.tahapanMasukan = tahapanMasukan;
        this.isiMasukan = isiMasukan;
        this.daftarMasukan = new ArrayList<>();
    }

    public Feedback() {
        this(0, "");
        this.daftarMasukan = new ArrayList<>();
    }

    public void setTahapanMasukan(int tahapanMasukan) {
        this.tahapanMasukan = tahapanMasukan;
    }

    public void setIsiMasukan(String isiMasukan) {
        this.isiMasukan = isiMasukan;
    }
    public int getTahapanMasukan() {
        return tahapanMasukan;
    }
    public String getIsiMasukan() {
        return isiMasukan;
    }
    public void buatMasukan(int tahapanMasukan, String isiMasukan) {
        setTahapanMasukan(tahapanMasukan);
        setIsiMasukan(isiMasukan);
    }
    public void editMasukan(int tahapanMasukan, String isiMasukan) {
        setTahapanMasukan(tahapanMasukan);
        setIsiMasukan(isiMasukan);
    }
    public void simpanMasukan() {
        System.out.println("Masukan disimpan: Tahapan " + getTahapanMasukan() + ", Isi: " + getIsiMasukan());
    }
    public void hapusMasukan() {
        System.out.println("Masukan dihapus: Tahapan " + getTahapanMasukan() + ", Isi: " + getIsiMasukan());
    }

}
