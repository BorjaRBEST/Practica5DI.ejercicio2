package org.example;

import java.io.InputStream;
import java.util.Properties;

public class Conexion {
    private static final String PROPERTIES_FILE = "database.properties";
    private static Properties properties = null;

    public static String getPropiedad(String clave) {
        if (properties == null) {
            properties = new Properties();
            try {
                InputStream inputStream = Conexion.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
                properties.load(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return properties.getProperty(clave);
    }

    public static String getUsuario() {
        return getPropiedad("db.username");
    }

    public static String getContraseña() {
        return getPropiedad("db.password");
    }

    public static String getUrl() {
        return getPropiedad("db.url");
    }
}
