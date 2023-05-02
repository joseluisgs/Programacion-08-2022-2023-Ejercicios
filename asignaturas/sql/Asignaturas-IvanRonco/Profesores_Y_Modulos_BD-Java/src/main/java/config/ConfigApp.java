package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;


public class ConfigApp {
    private static ConfigApp instance = null;
    @Inject
    public ConfigApp() {
        try {
            loadProperties();
            initStorage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static ConfigApp getInstance() {
        if (instance == null) instance = new ConfigApp();
        return instance;
    }

    private final Logger logger = LoggerFactory.getLogger(ConfigApp.class);

    public String APP_DATA;
    public String APP_AUTHOR;
    public String APP_VERSION;

    public String APP_URL;
    public boolean APP_INIT_DATABASE;


    public void loadProperties() throws IOException {
        logger.debug("Cargamos todas las propiedades de fichero 'config.properties'");

        Properties properties = new Properties();
        properties.load(new FileInputStream(ClassLoader.getSystemResource("config.properties").getFile()));

        APP_DATA = properties.getProperty("APP_DATA", "data");
        APP_DATA = System.getProperty("user.dir")+ File.separator+APP_DATA;
        APP_AUTHOR = properties.getProperty("APP_AUTHOR", "IvanRoncoCebadera");
        APP_VERSION = properties.getProperty("APP_VERSION", "1.0.0");

        APP_URL = properties.getProperty("APP_URL", "jdbc:sqlite:database.db");
        APP_INIT_DATABASE = properties.getProperty("APP_INIT_DATABASE", "true").equals("true") ;
    }

    private void initStorage() throws IOException {
        logger.debug("Cargamos, en caso de que no este ya, la carpeta donde almacenaremos nuestros ficheros.");
        Files.createDirectories(Path.of(APP_DATA));
    }


}
