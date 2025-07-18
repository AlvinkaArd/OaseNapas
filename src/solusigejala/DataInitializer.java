// DataInitializer.java
package solusigejala;

public class DataInitializer {

    /**
     * Membuat dan mengisi array biasa RekomendasiSolusiData[] dengan data dummy.
     * Ini digunakan untuk menginisialisasi data yang akan ditampilkan di aplikasi.
     *
     * @return RekomendasiSolusiDataArray yang berisi data gejala dan solusi dummy.
     */
    public static RekomendasiSolusiDataArray initializeDummyData() {
        // Langsung mendeklarasikan array biasa dengan ukuran yang sudah ditentukan.
        // Di sini, kita akan punya 7 data rekomendasi.
        RekomendasiSolusiData[] dataArray = new RekomendasiSolusiData[3];

        // Mengisi array secara langsung berdasarkan indeks.
        dataArray[0] = new RekomendasiSolusiData(
                "Stress",
                "Lakukan pola 4-4-4.",
                "https://www.alodokter.com/box-breathing-teknik-pernapasan-untuk-mengatasi-stres"
        );
        dataArray[1] = new RekomendasiSolusiData(
                "Sulit Tidur",
                "Lakukan pola 4-7-8.",
                "https://www.alodokter.com/teknik-pernapasan-4-7-8-untuk-membantu-tidur"
        );
        dataArray[2] = new RekomendasiSolusiData(
                "Kecemasan",
                "Lakukan pola 4-4-4",
                "https://www.pantai.com.my/id/health-pulse/breathing-exercises-for-anxiety-stress#:~:text=Teknik%20pernapasan%204%2D4%2D4,mengembuskan%20napas%20selama%20empat%20hitungan."
        );


        // Mengembalikan objek RekomendasiSolusiDataArray yang membungkus array biasa ini.
        return new RekomendasiSolusiDataArray(dataArray);
    }
}