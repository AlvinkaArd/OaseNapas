package jadwal_latihan;

/**
 * Struktur data untuk mengelola objek JadwalLatihan
 * Menyediakan fungsionalitas untuk menyimpan, mengambil, dan mengelola data jadwal latihan
 */
public class DataArray {
    private int index;
    private JadwalLatihan[] collectedData;

    /**
     * Konstruktor untuk inisialisasi array data
     * @param capacity Jumlah maksimum elemen yang dapat ditampung array
     */
    public DataArray(int capacity) {
        this.index = 0;
        this.collectedData = new JadwalLatihan[capacity];
    }

    /**
     * Tambahkan data jadwal latihan baru ke array
     */
    public void addData(String namaSesi, String musikLatar, String gejala, String suaraPemandu, 
                       String waktuLatihan, int durasi, String tarik, String tahan, String buang) {
        if (index < collectedData.length) {
            collectedData[index] = new JadwalLatihan(namaSesi, musikLatar, gejala, suaraPemandu, 
                                                   waktuLatihan, durasi, tarik, tahan, buang);
            index++;
        } else {
            throw new IllegalStateException("Array data penuh. Tidak dapat menambah data lagi.");
        }
    }

    /**
     * Dapatkan semua data yang valid (entri yang tidak null)
     * @return Array yang hanya berisi entri yang valid
     */
    public JadwalLatihan[] getValidData() {
        JadwalLatihan[] validData = new JadwalLatihan[index];
        System.arraycopy(collectedData, 0, validData, 0, index);
        return validData;
    }

    // getter dan setter
    public JadwalLatihan[] getCollectedData() { return collectedData; }
    public int getIndex() { return index; }
    public void setIndex(int index) { 
        if (index >= 0 && index <= collectedData.length) {
            this.index = index; 
        } else {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
    }

    /**
     * Periksa apakah array kosong
     * @return true jika belum ada data yang ditambahkan
     */
    public boolean isEmpty() {
        return index == 0;
    }

    /**
     * Periksa apakah array penuh
     * @return true jika array telah mencapai kapasitas maksimum
     */
    public boolean isFull() {
        return index >= collectedData.length;
    }

    /**
     * Dapatkan kapasitas array saat ini
     * @return Jumlah maksimum elemen yang dapat disimpan array
     */
    public int getCapacity() {
        return collectedData.length;
    }
}
