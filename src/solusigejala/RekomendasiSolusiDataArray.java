package solusigejala;
// RekomendasiSolusiDataArray.java
public class RekomendasiSolusiDataArray {
    private RekomendasiSolusiData[] rekomendasiSolusiData;

    // Konstruktor default untuk XStream (jika Anda menggunakannya)
    public RekomendasiSolusiDataArray() {
    }

    public RekomendasiSolusiDataArray(RekomendasiSolusiData[] rekomendasiSolusiData) {
        this.rekomendasiSolusiData = rekomendasiSolusiData;
    }

    public RekomendasiSolusiData[] getRekomendasiSolusiData() {
        return rekomendasiSolusiData;
    }

    public void setRekomendasiSolusiData(RekomendasiSolusiData[] rekomendasiSolusiData) {
        this.rekomendasiSolusiData = rekomendasiSolusiData;
    }
}



