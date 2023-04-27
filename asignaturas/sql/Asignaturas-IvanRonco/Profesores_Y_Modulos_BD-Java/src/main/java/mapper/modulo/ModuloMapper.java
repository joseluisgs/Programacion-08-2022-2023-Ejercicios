package mapper.modulo;

import dto.modulo.ListaModulosDto;
import dto.modulo.ModuloDto;
import models.Modulo;
import utils.Utils;

import java.util.List;
import java.util.UUID;

public class ModuloMapper {
    public static ModuloDto toModuloDto(Modulo modulo){
        return new ModuloDto(
                modulo.getUuid().toString(),
                modulo.getNombre(),
                String.valueOf(modulo.getCurso()),
                modulo.getGrado().toString()
        );
    }

    public static Modulo toModulo(ModuloDto moduloDto){
        return new Modulo(
                UUID.fromString(moduloDto.getUuid()),
                moduloDto.getNombre(),
                Integer.parseInt(moduloDto.getCurso()),
                Utils.toEnumGrado(moduloDto.getGrado())
        );
    }

    public static ListaModulosDto toModulosDto(List<Modulo> modulos){
        return new ListaModulosDto(
                modulos.stream().map(it -> toModuloDto(it)).toList()
        );
    }

    public static List<Modulo> toModulos(ListaModulosDto modulosDto){
        return modulosDto.getModuloDtos().stream().map(it -> toModulo(it)).toList();
    }
}
