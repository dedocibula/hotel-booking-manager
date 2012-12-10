package cz.fi.muni.pa165.hotelbookingmanagerdesktop.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrej Galád
 */
public class ServerURIHelper {

    public static String loadURLForClient() {
        Properties properties = PropertiesInit();
        return properties.getProperty("urlClient");
    }

    public static String loadURLForHotel() {
        Properties properties = PropertiesInit();
        return properties.getProperty("urlHotel");
    }

    private static Properties PropertiesInit() {
        Properties properties = new Properties();
        try {
            try (InputStream in = ServerURIHelper.class.getResourceAsStream("/serverConfig.properties")) {
                properties.load(in);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerURIHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return properties;
    }
}
