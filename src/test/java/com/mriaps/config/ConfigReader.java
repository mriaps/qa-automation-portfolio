package com.mriaps.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties PROPERTIES = load();

    private ConfigReader() {
    }

    private static Properties load() {
        Properties properties = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException("config.properties not found in classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load config.properties", e);
        }
        return properties;
    }

    // Nilai dari parameter Maven (-Dkey=value) lebih diutamakan daripada isi file
    public static String get(String key) {
        String value = System.getProperty(key, PROPERTIES.getProperty(key));
        if (value == null) {
            throw new IllegalArgumentException("Missing config key: " + key);
        }
        return value;
    }

    public static String baseUrl() {
        return get("base.url");
    }

    public static String standardUsername() {
        return get("standard.username");
    }

    public static String standardPassword() {
        return get("standard.password");
    }

    public static int timeoutSeconds() {
        return Integer.parseInt(get("timeout.seconds"));
    }
}