package service.storage.profesor;

import config.ConfigApp;
import dto.modulo.ModuloDto;
import dto.profesor.ProfesorDto;
import models.Docencia;
import models.Modulo;
import models.Profesor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.storage.docencia.DocenciaStorageServiceCsv;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static mapper.modulo.ModuloMapper.toModulo;
import static mapper.modulo.ModuloMapper.toModuloDto;
import static mapper.profesor.ProfesorMapper.toProfesor;
import static mapper.profesor.ProfesorMapper.toProfesorDto;

public class ProfesorStorageServiceCsv implements ProfesorStorageService {
    private Logger logger = LoggerFactory.getLogger(DocenciaStorageServiceCsv.class);

    private File file = new File(ConfigApp.getInstance().APP_DATA+File.separator+"profesores.csv");

    @Inject
    public ProfesorStorageServiceCsv() {
    }

    @Override
    public void safeAll(List<Profesor> entities) {
        logger.debug("Se guardan todas las docencias en el fichero CSV");

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("id;nombre;fecha_incorporación;modulos"+"\n");

            for(int i = 0;i<entities.stream().map(it -> toProfesorDto(it)).toList().size(); i++){
                bw.append(entities.get(i).getId()+";"+entities.get(i).getNombre()+";"+entities.get(i).getFechaIncorporacion()+";"+toCsv(entities.get(i).getModulos())+"\n");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String toCsv(List<Modulo> modulos) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("[");
        for(int i=0;i<modulos.size();i++) {
            Modulo linea = modulos.get(i);
            if(i!=0) {
                mensaje.append("|" + linea.getUuid() + "," + linea.getNombre() + "," + linea.getCurso() + "," + linea.getGrado());
            }else{
                mensaje.append(linea.getUuid() + "," + linea.getNombre() + "," + linea.getCurso() + "," + linea.getGrado());
            }
        }
        mensaje.append("]");
        return mensaje.toString();
    }

    @Override
    public List<Profesor> loadAll() throws IOException {
        logger.debug("Se cargan todas las docencias del fichero CSV");

        if(!Files.exists(file.toPath())) return Collections.emptyList();
        List<Docencia> docencias = new ArrayList<>();
        return Files.readAllLines(file.toPath()).stream().skip(1)
                .map(it -> it.split(";"))
                .map(it -> Arrays.stream(it).map(they -> they.trim()))
                .map(it -> it.toList())
                .map(it ->
                        toProfesor(new ProfesorDto(
                                it.get(0),
                                it.get(1),
                                it.get(2),
                                fromCsv(it.get(3))
                        )))
                .toList();
    }

    private List<ModuloDto> fromCsv(String modulos) {
        return Arrays.stream(modulos.substring(1, modulos.length()-1)
                .split("|"))
                .map(it -> it.split(","))
                .map(it ->
                        new ModuloDto(
                                it[0],
                                it[1],
                                it[2],
                                it[3]
                        )
                ).toList();
    }
}
