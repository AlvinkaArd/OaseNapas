package jadwal_latihan;

public class DataArray {
    private int index;
    private JadwalLatihan[] collectedData;

    public DataArray(int n) {
        this.index = 0;
        this.collectedData = new JadwalLatihan[n];
    }

    public void addData(String namaSesi, String musikLatar, String suaraPemandu, String waktuLatihan, int durasi, String tarik, String tahan, String buang) {
        collectedData[index] = new JadwalLatihan();
        collectedData[index].setNamaSesi(namaSesi);
        collectedData[index].setMusikLatar(musikLatar);
        collectedData[index].setSuaraPemandu(suaraPemandu);
        collectedData[index].setWaktuLatihan(waktuLatihan);
        collectedData[index].setDurasi(durasi);
        collectedData[index].setTahan(tarik);
        collectedData[index].setTahan(tahan);
        collectedData[index].setBuang(buang);
    }

    public void incrementIndex() {
        index++;
    }
}
