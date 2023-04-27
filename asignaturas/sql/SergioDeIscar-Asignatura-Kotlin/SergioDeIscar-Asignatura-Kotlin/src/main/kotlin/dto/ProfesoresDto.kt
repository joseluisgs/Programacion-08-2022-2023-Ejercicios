package dto

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "profesores")
data class ProfesoresDto(
    @field:ElementList(name = "profesor")
    @param:ElementList(name = "profesor")
    val profesores: List<ProfesorDto>
)