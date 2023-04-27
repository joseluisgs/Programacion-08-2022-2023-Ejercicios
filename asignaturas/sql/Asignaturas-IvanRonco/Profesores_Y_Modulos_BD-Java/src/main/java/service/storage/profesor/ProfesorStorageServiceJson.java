package service.storage.profesor;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import config.ConfigApp;
import dto.profesor.ListaProfesoresDto;
import models.Profesor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static mapper.profesor.ProfesorMapper.toProfesores;
import static mapper.profesor.ProfesorMapper.toProfesoresDto;

public class ProfesorStorageServiceJson implements ProfesorStorageService {

    private Logger logger = LoggerFactory.getLogger(ProfesorStorageServiceJson.class);

    private File file = new File(ConfigApp.getInstance().APP_DATA+File.separator+"profesores.json");

    private Moshi moshi= new Moshi.Builder().build();
    private JsonAdapter adapter = moshi.adapter(ListaProfesoresDto.class);

    public ProfesorStorageServiceJson() throws IOException {}

    @Override
    public void safeAll(List<Profesor> entities) {
        logger.debug("Se guardan todas los profesores en el fichero JSON");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(adapter.indent("   ").toJson(toProfesoresDto(entities)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Profesor> loadAll() {
        logger.debug("Se cargan todas los profesores del fichero JSON");
        if(!Files.exists(file.toPath())) return Collections.emptyList();
        List<Profesor> docencias = new ArrayList<>();
        try{
            docencias = toProfesores((ListaProfesoresDto) adapter.fromJson(Files.readString(Path.of(file.getPath()))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return docencias;
    }
}
