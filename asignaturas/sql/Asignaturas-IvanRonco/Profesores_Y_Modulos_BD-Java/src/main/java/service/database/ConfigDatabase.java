package service.database;

import config.ConfigApp;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigDatabase{
    private static ConfigDatabase instance = null;
    public static ConfigDatabase getInstance() {
        if (instance == null) instance = new ConfigDatabase();
        return instance;
    }

    @Inject
    public ConfigDatabase() {
        try{
            initDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Logger logger = LoggerFactory.getLogger(ConfigDatabase.class);
    public static Connection connection;
    private boolean initDatabase;

    {
        initDatabase = ConfigApp.getInstance().APP_INIT_DATABASE;
    }

    public void openConnection() throws IOException, SQLException {
        logger.debug("Abrimos la conexión con la DB");
        // si la conexión es nula, ya ha sido cerrada.
        if(connection == null) {
            connection = DriverManager.getConnection(ConfigApp.getInstance().APP_URL);
        }
    }
    public void closeConnection() throws SQLException {
        logger.debug("Cerramos la conexión con la DB");
        // Si la conexión no es nula, sigue abierta.
        if (connection != null){
            connection.close();
            connection = null;
        }
    }

    public void initDatabase() throws SQLException, IOException {
        openConnection();
        try {
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            BufferedReader br;
            if (initDatabase) {
                br = new BufferedReader(new FileReader(ClassLoader.getSystemResource("DropAndCreateTables.sql").getPath()));
            } else {
                br = new BufferedReader(new FileReader(ClassLoader.getSystemResource("CreateTables.sql").getPath()));
            }
            scriptRunner.runScript(br);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            closeConnection();
        }
    }
}
