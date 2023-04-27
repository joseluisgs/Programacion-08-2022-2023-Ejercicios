package mapper.profesor;

import mapper.modulo.ModuloMapper;
import dto.profesor.ListaProfesoresDto;
import dto.profesor.ProfesorDto;
import models.Profesor;

import java.time.LocalDateTime;
import java.util.List;

import static mapper.modulo.ModuloMapper.toModulo;

public class ProfesorMapper {
    public static ProfesorDto toProfesorDto(Profesor profesor){
        return new ProfesorDto(
                String.valueOf(profesor.getId()),
                profesor.getNombre(),
                profesor.getFechaIncorporacion().toString(),
                ModuloMapper.toModulosDto(profesor.getModulos()).getModuloDtos()
        );
    }

    public static Profesor toProfesor(ProfesorDto profesorDto){
        return new Profesor(
                Long.parseLong(profesorDto.getId()),
                profesorDto.getNombre(),
                LocalDateTime.parse(profesorDto.getFechaIncorporacion()),
                profesorDto.getModulosDto().stream().map(it -> toModulo(it)).toList()
        );
    }

    public static ListaProfesoresDto toProfesoresDto(List<Profesor> profesors){
        return new ListaProfesoresDto(
                profesors.stream().map(it -> toProfesorDto(it)).toList()
        );
    }

    public static List<Profesor> toProfesores(ListaProfesoresDto profesorsDto){
        return profesorsDto.getProfesoresDtos().stream().map(it -> toProfesor(it)).toList();
    }
}
