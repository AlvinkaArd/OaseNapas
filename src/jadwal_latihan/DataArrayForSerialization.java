package jadwal_latihan;

/**
 * Simple data array for XML serialization
 * Uses JadwalLatihanData instead of JadwalLatihan to avoid JavaFX property serialization issues
 */
public class DataArrayForSerialization {
    private int index;
    private JadwalLatihanData[] collectedData;

    public DataArrayForSerialization() {
        // Default constructor for XStream
    }

    public DataArrayForSerialization(int n) {
        this.index = 0;
        this.collectedData = new JadwalLatihanData[n];
    }

    // Convert from DataArray
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

    // Convert to DataArray
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

    // Getters and setters
    public int getIndex() { return index; }
    public void setIndex(int index) { this.index = index; }

    public JadwalLatihanData[] getCollectedData() { return collectedData; }
    public void setCollectedData(JadwalLatihanData[] collectedData) { this.collectedData = collectedData; }
}
