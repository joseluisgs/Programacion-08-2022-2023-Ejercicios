package dto.profesor;

import com.squareup.moshi.Json;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Data
@AllArgsConstructor
@Json(name = "profesores")
@Root(name = "profesores")
public class ListaProfesoresDto {
    @Json(name = "profesor")
    @ElementList(name = "profesor")
    List<ProfesorDto> profesoresDtos;
}