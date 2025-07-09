package jadwal_latihan;

public class DataArray {
    private int index;
    private JadwalLatihan[] collectedData;

    
    public JadwalLatihan[] getCollectedData() {
        return collectedData;
    }

    public int getIndex() {
        return index;
    }

    public DataArray(int n) {
        this.index = 0;
        this.collectedData = new JadwalLatihan[n];
    }

    public void addData(String namaSesi, String musikLatar, String gejala, String suaraPemandu, String waktuLatihan, int durasi, String tarik, String tahan, String buang) {
        collectedData[index] = new JadwalLatihan();
        collectedData[index].setNamaSesi(namaSesi);
        collectedData[index].setMusikLatar(musikLatar);
        collectedData[index].setGejala(gejala);
        collectedData[index].setSuaraPemandu(suaraPemandu);
        collectedData[index].setWaktuLatihan(waktuLatihan);
        collectedData[index].setDurasi(durasi);
        collectedData[index].setTarik(tarik);
        collectedData[index].setTahan(tahan);
        collectedData[index].setBuang(buang);
    }

    public void incrementIndex() {
        index++;
    }

    public JadwalLatihan[] getValidData() {
        JadwalLatihan[] validData = new JadwalLatihan[index];
        for (int i = 0; i < index; i++) {
            validData[i] = collectedData[i];
        }
        return validData;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
