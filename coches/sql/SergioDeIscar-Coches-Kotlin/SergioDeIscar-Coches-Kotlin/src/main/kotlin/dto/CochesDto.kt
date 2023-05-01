package dto

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "coches")
data class CochesDto(
    @field:ElementList(name = "coches")
    @param:ElementList(name = "coches")
    val coches: List<CocheDto>
)