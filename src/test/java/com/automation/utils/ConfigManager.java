package com.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    static Properties properties;

    public static void initConfig() {
        properties=new Properties();
        try {
            properties.load(new FileInputStream("src/test/resources/config/config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getConfigValue(String key){
        return properties.getProperty(key);
    }
    public static void setConfigValue(String key,String value){
        properties.setProperty(key,value);
    }
}
