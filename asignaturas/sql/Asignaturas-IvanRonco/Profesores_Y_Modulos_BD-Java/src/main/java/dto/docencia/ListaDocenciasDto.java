package dto.docencia;

import com.squareup.moshi.Json;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Data
@AllArgsConstructor
@Json(name = "docencias")
@Root(name = "docencias")
public class ListaDocenciasDto {
    @Json(name = "docencia")
    @ElementList(name = "docencia")
    List<DocenciaDto> docenciasDtos;
}
