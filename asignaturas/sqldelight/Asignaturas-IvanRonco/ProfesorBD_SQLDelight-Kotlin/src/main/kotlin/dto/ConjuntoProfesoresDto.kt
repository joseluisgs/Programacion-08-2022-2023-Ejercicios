package dto

import com.squareup.moshi.Json
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Json(name = "profesores")
@Root(name = "profesores")
data class ConjuntoProfesoresDto(
    @Json(name = "profesores")
    @field:ElementList(name = "profesores", inline = true)
    @param:ElementList(name = "profesores", inline = true)
    val profesores: List<ProfesorDto>
)