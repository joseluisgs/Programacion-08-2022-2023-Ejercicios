package dto.modulo;


import com.squareup.moshi.Json;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Data
@AllArgsConstructor
@Json(name = "modulos")
@Root(name = "modulos")
public class ListaModulosDto {
    @Json(name = "modulo")
    @ElementList(name = "modulo")
    List<ModuloDto> moduloDtos;
}
