package org.example;

import models.GradoModulo;
import models.Modulo;
import models.Profesor;
import service.database.BaseQueries;
import service.database.ConfigDatabase;
import service.storage.docencia.DocenciaStorageServiceCsv;
import service.storage.docencia.DocenciaStorageServiceJson;
import service.storage.modulo.ModuloStorageServiceCsv;
import service.storage.modulo.ModuloStorageServiceJson;
import service.storage.profesor.ProfesorStorageService;
import service.storage.profesor.ProfesorStorageServiceCsv;
import service.storage.profesor.ProfesorStorageServiceJson;
import utils.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {

        var profeStorage = new ProfesorStorageServiceCsv();
        var moduloStorage = new ModuloStorageServiceCsv();
        var docenciaStorage = new DocenciaStorageServiceCsv();

        ConfigDatabase configDatabase = ConfigDatabase.getInstance();

        List<Modulo> modulos = new ArrayList<>();
        UUID uuid1 = UUID.randomUUID();
        modulos.add(new Modulo(uuid1, "LDM", Utils.sacarNumeroAleatorioEntre1Y2(), GradoModulo.DAM));
        modulos.add(new Modulo(UUID.randomUUID(), "LDM", Utils.sacarNumeroAleatorioEntre1Y2(), GradoModulo.ASIR));
        modulos.add(new Modulo(UUID.randomUUID(), "Sist", Utils.sacarNumeroAleatorioEntre1Y2(), GradoModulo.DAW));
        modulos.add(new Modulo(UUID.randomUUID(), "BD", Utils.sacarNumeroAleatorioEntre1Y2(), GradoModulo.SMR));
        modulos.add(new Modulo(UUID.randomUUID(), "Prog", Utils.sacarNumeroAleatorioEntre1Y2(), GradoModulo.ASIR));
        modulos.add(new Modulo(UUID.randomUUID(), "Entor", Utils.sacarNumeroAleatorioEntre1Y2(), GradoModulo.SMR));
        modulos.add(new Modulo(UUID.randomUUID(), "FOL", Utils.sacarNumeroAleatorioEntre1Y2(), GradoModulo.ASIR));

        List<Profesor> profesores = new ArrayList<>();
        profesores.add(new Profesor(0L, "Jesus", LocalDateTime.now(), modulos.subList(0,3)));
        profesores.add(new Profesor(0L, "Manuel", LocalDateTime.now(), modulos.subList(4, modulos.size()-1)));

        System.out.println("Los profesores:");

        profesores.forEach(System.out::println);

        ConfigDatabase.getInstance().openConnection();

        profesores.forEach(prof -> {
            try {
                BaseQueries.createProfesor(prof);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        BaseQueries.getAllProfesores().forEach(System.out::println);

        System.out.println();

        System.out.println("Las docencias:");

        BaseQueries.getAllDocencias().forEach(System.out::println);

        System.out.println();

        System.out.println("Los modulos:");

        BaseQueries.getAllModulos().forEach(System.out::println);

        System.out.println();

        System.out.println("Un profesor con el id: 1");

        System.out.println(BaseQueries.getProfesoreById(1L));

        profeStorage.safeAll(profesores);

        profeStorage.loadAll().forEach(System.out::println);

        moduloStorage.safeAll(modulos);

        docenciaStorage.safeAll(BaseQueries.getAllDocencias());

        ConfigDatabase.getInstance().closeConnection();
    }
}