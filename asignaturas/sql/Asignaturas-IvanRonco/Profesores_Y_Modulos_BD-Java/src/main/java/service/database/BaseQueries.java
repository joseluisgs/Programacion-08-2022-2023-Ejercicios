package service.database;

import models.Docencia;
import models.Modulo;
import models.Profesor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static utils.Utils.toEnumGrado;

public class BaseQueries {

    private static Logger logger = LoggerFactory.getLogger(BaseQueries.class);

    public static List<Docencia> getAllDocencias() throws SQLException, IOException {
        logger.debug("Consigo todas las docencias");
        List<Docencia> docencias = new ArrayList<Docencia>();
        try {
            Connection connection = ConfigDatabase.getInstance().connection;
            String sqlModulos = "SELECT * FROM DOCENCIA";

            ResultSet resultSet = connection.prepareStatement(sqlModulos).executeQuery();

            while(resultSet.next()){
                docencias.add(
                        new Docencia(
                                resultSet.getLong("PROFESOR"),
                                UUID.fromString(resultSet.getString("MODULO")),
                                resultSet.getString("GRUPO")
                        )
                );
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return docencias;
    }

    public static List<Profesor> getAllProfesores() throws SQLException, IOException {
        logger.debug("Consigo todos los profesores");
        List<Profesor> profesores = new ArrayList<Profesor>();
        List<Modulo> modulos = new ArrayList<Modulo>();
        try {
            Connection connection = ConfigDatabase.getInstance().connection;
            String sqlProfesor = "SELECT * FROM PROFESOR;";
            String sqlModulos = "SELECT * FROM MODULO WHERE UUID IN (SELECT MODULO FROM DOCENCIA WHERE PROFESOR = ?);";

            ResultSet result = connection.prepareStatement(sqlProfesor).executeQuery();

            while (result.next()){
                long id = result.getLong("ID");

                PreparedStatement preparedStatement = connection.prepareStatement(sqlModulos);
                preparedStatement.setLong(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    modulos.add(
                            new Modulo(
                                    UUID.fromString(resultSet.getString("UUID")),
                                    resultSet.getString("NOMBRE"),
                                    resultSet.getInt("CURSO"),
                                    toEnumGrado(resultSet.getString("GRADO"))
                            )
                    );
                }

                profesores.add(
                        new Profesor(
                                id,
                                result.getString("NOMBRE"),
                                LocalDateTime.parse(result.getString("FECHA_INCORPORACION")),
                                modulos
                        )
                );
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return profesores;
    }

    public static Profesor getProfesoreById(Long id) throws SQLException, IOException {
        logger.debug("Consigo el profesor con id: "+id);
        Profesor profesor = null;
        List<Modulo> modulos = new ArrayList<Modulo>();
        try {
            Connection connection = ConfigDatabase.getInstance().connection;
            String sqlProfesor = "SELECT * FROM PROFESOR WHERE ID = ?;";
            String sqlModulos = "SELECT * FROM MODULO WHERE UUID IN (SELECT MODULO FROM DOCENCIA WHERE PROFESOR = ?);";

            PreparedStatement preparedStatementProfesor = connection.prepareStatement(sqlProfesor);
            preparedStatementProfesor.setLong(1, id);

            ResultSet result = preparedStatementProfesor.executeQuery();

            while (result.next()){
                PreparedStatement preparedStatement = connection.prepareStatement(sqlModulos);
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    modulos.add(
                            new Modulo(
                                    UUID.fromString(resultSet.getString("UUID")),
                                    resultSet.getString("NOMBRE"),
                                    resultSet.getInt("CURSO"),
                                    toEnumGrado(resultSet.getString("GRADO"))
                            )
                    );
                }

                profesor = new Profesor(
                        id,
                        result.getString("NOMBRE"),
                        LocalDateTime.parse(result.getString("FECHA_INCORPORACION")),
                        modulos
                );
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return profesor;
    }

    public static Profesor createProfesor(Profesor profesor) throws SQLException, IOException {
        logger.debug("Creo un nuevo profesor");

        long myId = 0L;

        List<Modulo> modulos = profesor.getModulos();
        for(int i=0; i<modulos.size(); i++){
            if(getModuloById(modulos.get(i).getUuid()) == null){
                createModulo(modulos.get(i));
            }
        }

        try {
            Connection connection = ConfigDatabase.getInstance().connection;
            String sqlModulos = "INSERT INTO PROFESOR VALUES (null, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlModulos, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, profesor.getNombre());
            preparedStatement.setString(2, profesor.getFechaIncorporacion().toString());

            preparedStatement.executeUpdate();

            ResultSet resultId = preparedStatement.getGeneratedKeys();
            if (resultId.next()){
                myId = resultId.getLong(1);
            }

            String sqlDocencia = "INSERT INTO DOCENCIA VALUES (?, ?, ?);";
            String[] cursos = {"1ºA", "1ºB", "1ºC", "2ºA", "2ºB", "2ºC", "3ºA", "3ºB", "3ºC"};

            preparedStatement = connection.prepareStatement(sqlDocencia);

            preparedStatement.setLong(1, myId);
            for(int i=0; i<modulos.size(); i++){
                preparedStatement.setString(2, modulos.get(i).getUuid().toString());
                preparedStatement.setString(3, cursos[(int) (Math.random()* cursos.length)]);

                preparedStatement.executeUpdate();
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new Profesor(
                myId,
                profesor.getNombre(),
                profesor.getFechaIncorporacion(),
                modulos
        );
    }

    public static Profesor updateProfesor(Profesor profesor) throws SQLException, IOException {
        logger.debug("Actualizo un profesor");

        List<Modulo> modulos = profesor.getModulos();
        for(int i=0; i<modulos.size(); i++){
            if(getModuloById(modulos.get(i).getUuid()) == null){
                createModulo(modulos.get(i));
            }else{
                updateModulo(modulos.get(i));
            }
        }

        try {
            Connection connection = ConfigDatabase.getInstance().connection;
                String sqlModulos = "UPDATE PROFESOR SET NOMBRE = ?, FECHA_INCORPORACION = ? WHERE ID = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlModulos);
            preparedStatement.setString(1, profesor.getNombre());
            preparedStatement.setString(2, profesor.getFechaIncorporacion().toString());
            preparedStatement.setLong(3, profesor.getId());

            preparedStatement.executeUpdate();

            String sqlDeleteDocencias = "DELETE FROM DOCENCIA WHERE PROFESOR = ?;";

            preparedStatement = connection.prepareStatement(sqlDeleteDocencias);
            preparedStatement.setLong(1, profesor.getId());

            preparedStatement.executeUpdate();

            String sqlDocencia = "INSERT INTO DOCENCIA VALUES (?, ?, ?);";
            String[] cursos = {"1ºA, 1ºB, 1ºC, 2ºA, 2ºB, 2ºC, 3ºA, 3ºB, 3ºC,"};

            preparedStatement = connection.prepareStatement(sqlDocencia);

            preparedStatement.setLong(1, profesor.getId());
            for(int i=0; i<modulos.size(); i++){
                preparedStatement.setString(2, modulos.get(i).toString());
                preparedStatement.setString(3, cursos[(int) (Math.random()* cursos.length)]);

                preparedStatement.executeUpdate();
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return profesor;
    }

    public static void deleteAllProfesores() throws SQLException, IOException {
        logger.debug("Borro todos los módulos");
        try {
            Connection connection = ConfigDatabase.getInstance().connection;
            String sqlModulos = "DELETE FROM PROFESOR;";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlModulos);
            preparedStatement.executeUpdate();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void deleteProfesorById(Long id) throws SQLException, IOException {
        logger.debug("Borro el módulo de id: "+id);
        try {
            Connection connection = ConfigDatabase.getInstance().connection;
            String sqlModulos = "DELETE FROM PROFESOR WHERE ID = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlModulos);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static List<Modulo> getAllModulos() throws SQLException, IOException {
        logger.debug("Consigo todos los módulo");
        List<Modulo> modulos = new ArrayList<Modulo>();
        try {
            Connection connection = ConfigDatabase.getInstance().connection;
            String sqlModulos = "SELECT * FROM MODULO";

            ResultSet resultSet = connection.prepareStatement(sqlModulos).executeQuery();

            while(resultSet.next()){
                modulos.add(
                        new Modulo(
                                UUID.fromString(resultSet.getString("UUID")),
                                resultSet.getString("NOMBRE"),
                                resultSet.getInt("CURSO"),
                                toEnumGrado(resultSet.getString("GRADO"))
                        )
                );
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return modulos;
    }

    public static Modulo getModuloById(UUID uuid) throws SQLException, IOException {
        logger.debug("Consigo el módulo con id: "+uuid);
        Modulo modulo = null;
        try {
            Connection connection = ConfigDatabase.getInstance().connection;
            String sqlModulos = "SELECT * FROM MODULO WHERE UUID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlModulos);
            preparedStatement.setString(1, uuid.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                modulo = new Modulo(
                        UUID.fromString(resultSet.getString("UUID")),
                        resultSet.getString("NOMBRE"),
                        resultSet.getInt("CURSO"),
                        toEnumGrado(resultSet.getString("GRADO"))
                );
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return modulo;
    }

    public static Modulo createModulo(Modulo modulo) throws SQLException, IOException {
        logger.debug("Creo un nuevo módulo");
        try {
            Connection connection = ConfigDatabase.getInstance().connection;
            String sqlModulos = "INSERT INTO MODULO VALUES (?, ?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlModulos);
            preparedStatement.setString(1, modulo.getUuid().toString());
            preparedStatement.setString(2, modulo.getNombre());
            preparedStatement.setInt(3, modulo.getCurso());
            preparedStatement.setString(4, modulo.getGrado().toString());

            preparedStatement.executeUpdate();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return modulo;
    }

    public static Modulo updateModulo(Modulo modulo) throws SQLException, IOException {
        logger.debug("Actualizo un módulo");
        try {
            Connection connection = ConfigDatabase.getInstance().connection;
            String sqlModulos = "UPDATE MODULO SET NOMBRE = ?, CURSO = ?, GRADO = ? WHERE UUID = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlModulos);
            preparedStatement.setString(1, modulo.getNombre());
            preparedStatement.setInt(2, modulo.getCurso());
            preparedStatement.setString(3, modulo.getGrado().toString());
            preparedStatement.setString(4, modulo.getUuid().toString());

            preparedStatement.executeUpdate();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return modulo;
    }

    public static void deleteAllModulos() throws SQLException, IOException {
        logger.debug("Borro todos los módulos");
        try {
            Connection connection = ConfigDatabase.getInstance().connection;
            String sqlModulos = "DELETE FROM MODULO;";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlModulos);
            preparedStatement.executeUpdate();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void deleteModuloById(UUID uuid) throws SQLException, IOException {
        logger.debug("Borro el módulo de id: "+uuid);
        try {
            Connection connection = ConfigDatabase.getInstance().connection;
            String sqlModulos = "DELETE FROM MODULO WHERE UUID = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlModulos);
            preparedStatement.setString(1, uuid.toString());

            preparedStatement.executeUpdate();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
