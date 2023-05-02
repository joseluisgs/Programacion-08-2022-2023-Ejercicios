package service.storage.modulo;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import config.ConfigApp;
import dto.modulo.ListaModulosDto;
import models.Modulo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static mapper.modulo.ModuloMapper.toModulos;
import static mapper.modulo.ModuloMapper.toModulosDto;

public class ModuloStorageServiceJson implements ModuloStorageService {
    private Logger logger = LoggerFactory.getLogger(ModuloStorageServiceJson.class);

    private File file = new File(ConfigApp.getInstance().APP_DATA+File.separator+"modulos.json");

    private Moshi moshi= new Moshi.Builder().build();
    private JsonAdapter adapter = moshi.adapter(ListaModulosDto.class);

    @Inject
    public ModuloStorageServiceJson() {
    }

    @Override
    public void safeAll(List<Modulo> entities) {
        logger.debug("Se guardan todas los módulos en el fichero JSON");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(adapter.indent("   ").toJson(toModulosDto(entities)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Modulo> loadAll() {
        logger.debug("Se cargan todas los módulos del fichero JSON");
        if(!Files.exists(file.toPath())) return Collections.emptyList();
        List<Modulo> modulos = new ArrayList<>();
        try{
            modulos = toModulos((ListaModulosDto) adapter.fromJson(Files.readString(Path.of(file.getPath()))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return modulos;
    }
}
