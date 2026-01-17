package com.sinan.utilities;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static com.sinan.utilities.FrameworkConstants.CONFIG_PATH;

public class ConfigManager {

    private static Properties properties;

    // "static" blok: Bu sınıf ilk çağrıldığında SADECE BİR KERE çalışır.
    static {
        try{
            FileInputStream input= new FileInputStream(CONFIG_PATH);
            properties= new Properties();
            properties.load(input);
            input.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException("Config dosyası yüklenmedi");
        }
    }

    public static String getProperty(String key){
        return properties.getProperty(key);
    }
}
