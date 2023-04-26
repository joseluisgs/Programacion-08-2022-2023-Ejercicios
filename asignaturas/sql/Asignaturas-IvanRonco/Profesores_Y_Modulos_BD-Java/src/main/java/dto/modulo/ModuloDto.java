package dto.modulo;

import com.squareup.moshi.Json;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Data
@AllArgsConstructor
@Json(name = "modulo")
@Root(name = "modulo")
public class ModuloDto {
    @Json(name = "modulo_id")
    @Element(name = "modulo_id")
    String uuid;

    @Json(name = "nombre")
    @Element(name = "nombre")
    String nombre;

    @Json(name = "curso")
    @Element(name = "curso")
    String curso;

    @Json(name = "grado")
    @Element(name = "grado")
    String grado;
}
