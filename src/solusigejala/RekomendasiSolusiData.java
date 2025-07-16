package solusigejala;

import jadwal_latihan.JadwalLatihan;

public class RekomendasiSolusiData {
    private String gejala;
    private  String rekomendasiPola;
    private String artikel;

    public RekomendasiSolusiData() {
        // Konstruktor default untuk XStream
    }
    public RekomendasiSolusiData(String gejala, String rekomendasiPola, String artikel){
        this.gejala = gejala;
        this.rekomendasiPola = rekomendasiPola;
        this.artikel = artikel;
    }
    public String getGejala() { 
        return gejala; 
    }
    public String getRekomendasiPola(){
        return rekomendasiPola;
    }
    public String getArtikel(){
        return artikel;
    }

    public void setGejala(String gejala) { 
        this.gejala = gejala; 
    }
    public void setRekomendasiPola(String rekomendasiPola) { 
        this.rekomendasiPola = rekomendasiPola; 
    }
    public void setArtikel(String artikel) { 
        this.artikel = artikel; 
    }
}
