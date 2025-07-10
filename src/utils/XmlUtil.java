package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import jadwal_latihan.DataArray;
import jadwal_latihan.DataArrayForSerialization;
import jadwal_latihan.JadwalLatihanData;

/**
 * Kelas utilitas untuk operasi serialisasi dan deserialisasi XML
 */
public final class XmlUtil {
    
    private static final Logger LOGGER = Logger.getLogger(XmlUtil.class.getName());
    
    // Mencegah instansiasi
    private XmlUtil() {}
    
    /**
     * Buat dan konfigurasi XStream untuk operasi XML
     * @return Instance XStream yang telah dikonfigurasi
     */
    public static XStream createXStream() {
        XStream xStream = new XStream(new StaxDriver());
        xStream.alias("jadwal__latihan.DataArray", DataArrayForSerialization.class);
        xStream.alias("jadwal__latihan.JadwalLatihan", JadwalLatihanData.class);
        xStream.allowTypesByWildcard(new String[]{"jadwal_latihan.**"});
        return xStream;
    }
    
    /**
     * Simpan DataArray ke file XML
     * @param dataArray Data yang akan disimpan
     * @param filename Nama file untuk menyimpan
     * @return true if save was successful, false otherwise
     */
    public static boolean saveDataToXml(DataArray dataArray, String filename) {
        if (dataArray == null) {
            LOGGER.warning("Cannot save null data array");
            return false;
        }
        
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            XStream xStream = createXStream();
            DataArrayForSerialization serializableData = DataArrayForSerialization.fromDataArray(dataArray);
            xStream.toXML(serializableData, fos);
            
            LOGGER.info("Data successfully saved to " + filename);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error saving data to " + filename + ": " + e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Muat DataArray dari file XML
     * @param filename Nama file untuk dimuat
     * @return DataArray yang mengandung data yang dimuat, atau array kosong jika gagal
     */
    public static DataArray loadDataFromXml(String filename) {
        try (FileInputStream fis = new FileInputStream(filename)) {
            XStream xStream = createXStream();
            DataArrayForSerialization loadedData = (DataArrayForSerialization) xStream.fromXML(fis);
            
            LOGGER.info("Data successfully loaded from " + filename);
            return loadedData.toDataArray();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error loading data from " + filename + ": " + e.getMessage());
            return new DataArray(Constants.DEFAULT_ARRAY_SIZE);
        }
    }
}
