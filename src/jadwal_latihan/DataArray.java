package jadwal_latihan;

import java.time.LocalDate;
import java.time.format.DateTimeParseException; // Import for parsing exceptions

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
     * Tambahkan data jadwal latihan baru ke array (String waktuLatihan - Deprecated)
     * Ini akan mencoba mengonversi String ke LocalDate. Jika format tidak sesuai,
     * tanggal akan menjadi null.
     * @deprecated Gunakan addData(String, String, String, String, LocalDate, int, String, String, String) sebagai gantinya.
     */
    @Deprecated
    public void addData(String namaSesi, String musikLatar, String gejala, String suaraPemandu,
                        String waktuLatihan, int durasi, String tarik, String tahan, String buang) {
        if (index < collectedData.length) {
            LocalDate parsedDate = null;
            if (waktuLatihan != null && !waktuLatihan.trim().isEmpty()) {
                try {
                    // IMPORTANT: This parsing assumes ISO_LOCAL_DATE format (e.g., "2025-07-18").
                    // If your existing String data is "Senin Pagi", this will fail.
                    // For such cases, you might want to consider not parsing it to LocalDate
                    // if it's purely descriptive, or handle specific date formats if possible.
                    parsedDate = LocalDate.parse(waktuLatihan);
                } catch (DateTimeParseException e) {
                    System.err.println("WARNING: Could not parse date string '" + waktuLatihan + "'. Using null for date. Error: " + e.getMessage());
                }
            }
            collectedData[index] = new JadwalLatihan(namaSesi, musikLatar, gejala, suaraPemandu,
                                                     parsedDate, durasi, tarik, tahan, buang);
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

    /**
     * Tambahkan data jadwal latihan baru ke array (dengan LocalDate untuk waktuLatihan)
     */
    public void addData(String sesi, String musik, String gejala, String pemandu, LocalDate waktu, int durasi,
            String tarik, String tahan, String buang) {
        if (index < collectedData.length) {
            collectedData[index] = new JadwalLatihan(sesi, musik, gejala, pemandu,
                                                     waktu, durasi, tarik, tahan, buang);
            index++;
        } else {
            throw new IllegalStateException("Array data penuh. Tidak dapat menambah data lagi.");
        }
    }
}