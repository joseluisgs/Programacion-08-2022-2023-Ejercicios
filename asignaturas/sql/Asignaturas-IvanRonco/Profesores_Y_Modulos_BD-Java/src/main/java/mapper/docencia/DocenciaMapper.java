package mapper.docencia;

import dto.docencia.DocenciaDto;
import dto.docencia.ListaDocenciasDto;
import models.Docencia;

import java.util.List;
import java.util.UUID;

public class DocenciaMapper {
    public static DocenciaDto toDocenciaDto(Docencia docencia){
        return new DocenciaDto(
                String.valueOf(docencia.getProfesor()),
                docencia.getModulo().toString(),
                docencia.getGrupo()
        );
    }

    public static Docencia toDocencia(DocenciaDto docenciaDto){
        return new Docencia(
                Long.parseLong(docenciaDto.getProfesor()),
                UUID.fromString(docenciaDto.getModulo()),
                docenciaDto.getGrupo()
        );
    }

    public static ListaDocenciasDto toDocenciasDto(List<Docencia> docencias){
        return new ListaDocenciasDto(
                docencias.stream().map(it -> toDocenciaDto(it)).toList()
        );
    }

    public static List<Docencia> toDocencias(ListaDocenciasDto docenciasDto){
        return docenciasDto.getDocenciasDtos().stream().map(it -> toDocencia(it)).toList();
    }
}
