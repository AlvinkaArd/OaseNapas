package jadwal_latihan;

/**
 * Array data sederhana untuk serialisasi XML
 * Menggunakan JadwalLatihanData sebagai ganti JadwalLatihan untuk menghindari masalah serialisasi properti JavaFX
 */
public class DataArrayForSerialization {
    private int index;
    private JadwalLatihanData[] collectedData;

    public DataArrayForSerialization() {
        // Konstruktor default untuk XStream
    }

    public DataArrayForSerialization(int n) {
        this.index = 0;
        this.collectedData = new JadwalLatihanData[n];
    }

    // Konversi dari DataArray
    public static DataArrayForSerialization fromDataArray(DataArray dataArray) {
        DataArrayForSerialization result = new DataArrayForSerialization(dataArray.getIndex());
        result.index = dataArray.getIndex();
        
        for (int i = 0; i < dataArray.getIndex(); i++) {
            JadwalLatihan jadwal = dataArray.getCollectedData()[i];
            if (jadwal != null) {
                result.collectedData[i] = JadwalLatihanData.fromJadwalLatihan(jadwal);
            }
        }
        return result;
    }

    // Konversi ke DataArray
    public DataArray toDataArray() {
        DataArray result = new DataArray(index);
        result.setIndex(this.index);
        
        for (int i = 0; i < index; i++) {
            if (collectedData[i] != null) {
                result.getCollectedData()[i] = collectedData[i].toJadwalLatihan();
            }
        }
        return result;
    }

    // getter dan setter
    public int getIndex() { return index; }
    public void setIndex(int index) { this.index = index; }

    public JadwalLatihanData[] getCollectedData() { return collectedData; }
    public void setCollectedData(JadwalLatihanData[] collectedData) { this.collectedData = collectedData; }
}
