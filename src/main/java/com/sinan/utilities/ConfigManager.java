package com.sinan.utilities;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.sinan.utilities.FrameworkConstants.CONFIG_PATH;

public class ConfigManager {

    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static Properties properties;

    static {
        try{
            logger.info("Config dosyası yükleniyor: {}", CONFIG_PATH);
            FileInputStream input= new FileInputStream(CONFIG_PATH);
            properties= new Properties();
            properties.load(input);
            input.close();
            logger.info("Config dosyası başarıyla yüklendi");
        } catch (FileNotFoundException e) {
            logger.error("Config dosyası bulunamadı: {}", CONFIG_PATH, e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("Config dosyası yüklenirken hata oluştu", e);
            throw new RuntimeException("Config dosyası yüklenmedi");
        }
    }

    public static String getProperty(String key){
        return properties.getProperty(key);
    }
}
