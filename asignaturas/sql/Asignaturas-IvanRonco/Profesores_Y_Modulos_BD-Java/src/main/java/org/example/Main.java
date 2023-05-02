package org.example;

import di.components.DaggerDocenciaComponent;
import di.components.DaggerModuloComponent;
import di.components.DaggerProfesorComponent;
import models.GradoModulo;
import models.Modulo;
import models.Profesor;
import service.database.BaseQueries;
import service.database.ConfigDatabase;
import utils.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws Exception {

        var profeStorage = DaggerProfesorComponent.builder().build().build();
        var moduloStorage = DaggerModuloComponent.builder().build().build();
        var docenciaStorage = DaggerDocenciaComponent.builder().build().build();

        ConfigDatabase configDatabase = ConfigDatabase.getInstance();

        BaseQueries baseQueries = new BaseQueries(ConfigDatabase.getInstance());

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

        configDatabase.openConnection();

        profesores.forEach(prof -> {
            try {
                baseQueries.createProfesor(prof);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        baseQueries.getAllProfesores().forEach(System.out::println);

        System.out.println();

        System.out.println("Las docencias:");

        baseQueries.getAllDocencias().forEach(System.out::println);

        System.out.println();

        System.out.println("Los modulos:");

        baseQueries.getAllModulos().forEach(System.out::println);

        System.out.println();

        System.out.println("Un profesor con el id: 1");

        System.out.println(baseQueries.getProfesoreById(1L));

        profeStorage.safeAll(baseQueries.getAllProfesores());

        profeStorage.loadAll().forEach(System.out::println);

        moduloStorage.safeAll(modulos);

        docenciaStorage.safeAll(baseQueries.getAllDocencias());

        ConfigDatabase.getInstance().closeConnection();
    }
}