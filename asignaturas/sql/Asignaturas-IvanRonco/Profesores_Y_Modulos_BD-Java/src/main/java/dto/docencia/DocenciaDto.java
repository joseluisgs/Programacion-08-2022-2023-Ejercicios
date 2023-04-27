package dto.docencia;

import com.squareup.moshi.Json;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Data
@AllArgsConstructor
@Json(name = "docencia")
@Root(name = "docencia")
public class DocenciaDto {
    @Json(name = "profesor_id")
    @Element(name = "profesor_id")
    String profesor;

    @Json(name = "modulo_id")
    @Element(name = "modulo_id")
    String modulo;

    @Json(name = "grupo")
    @Element(name = "grupo")
    String grupo;
}
