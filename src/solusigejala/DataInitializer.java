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
                "Teknik pernapasan 4-4-4 adalah teknik bernapas terstruktur untuk mengurangi kecemasan dan stres. Teknik ini terdiri dari\n" + //
                                        "tarikan napas selama empat hitungan, menahannya selama empat hitungan, dan mengembuskan napas selama empat\n" + //
                                        "hitungan. Metode in menyelaraskan napas dengan sebuah pola tertentu, mempermudah relaksasi. Untuk menerapkan teknik\n" + //
                                        "ini, Anda dapat:\n" + //
                                        "• Mencari tempt tenang dan duduk dengan nyaman, sehingga Anda dapat berfokus dengan napas Anda.\n" + //
                                        "• Tarik napas lewat hidung, sambil menghitung sampai 4.\n" + //
                                        "• Tahan napas Anda selama 4 hitungan.\n" + //
                                        "• Embuskan napas lewat mulut perlahan, sambil menghitung sampai 4, menjaga rime yang stabil.\n" + //
                                        "Mengatur napas menurunkan laju detak jantung dan menenangkan sistem saraf. Praktik ini meningkatkan asupan oksigen\n" + //
                                        "dalam tubuh, membuat pikiran lebih jernih, dan mengurangi ketegangan. Sifat ritmisnya juga mendukung mindfulness dengan\n" + //
                                        "memusatkan perhatian Anda pada momen di masa sekarang dan mengurangi pikiran yang membuat Anda cemas."
        );
        dataArray[1] = new RekomendasiSolusiData(
                "",
                "",
                ""
        );
        dataArray[2] = new RekomendasiSolusiData(
                "",
                "",
                ""
        );


        // Mengembalikan objek RekomendasiSolusiDataArray yang membungkus array biasa ini.
        return new RekomendasiSolusiDataArray(dataArray);
    }
}