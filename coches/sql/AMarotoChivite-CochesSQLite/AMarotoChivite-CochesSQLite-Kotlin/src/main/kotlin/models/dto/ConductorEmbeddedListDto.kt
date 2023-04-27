package models.dto

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "lista_conductores")
class ConductorEmbeddedListDto {
    @field:ElementList(name = "lista", entry = "conductor", inline = true)
    var dtoList: List<ConductorEmbeddedDto>? = null
}
