package models.dto

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "lista_conductores")
class ConductorListDto {
    @field:ElementList(name = "lista", entry = "condcutor", inline = true)
    var dtoList: List<ConductorDto>? = null
}
