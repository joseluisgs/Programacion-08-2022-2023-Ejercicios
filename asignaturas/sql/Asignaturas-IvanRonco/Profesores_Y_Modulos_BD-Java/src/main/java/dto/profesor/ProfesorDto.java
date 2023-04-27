package dto.profesor;

import com.squareup.moshi.Json;
import dto.modulo.ListaModulosDto;
import dto.modulo.ModuloDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Data
@AllArgsConstructor
@Json(name = "profesor")
@Root(name = "profesor")
public class ProfesorDto {
    @Json(name = "profesor_id")
    @Element(name = "profesor_id")
    String id;

    @Json(name = "nombre")
    @Element(name = "nombre")
    String nombre;

    @Json(name = "fecha_incorporación")
    @Element(name = "fecha_incorporación")
    String fechaIncorporacion;

    @Json(name = "modulos")
    @ElementList(name = "modulos")
    List<ModuloDto> modulosDto;
}
