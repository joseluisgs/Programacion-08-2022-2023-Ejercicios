package dto

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "modulos")
data class ModulosDto(
    @field:ElementList(name = "modulos", inline = true)
    @param:ElementList(name = "modulos", inline = true)
    val modulos: List<ModuloDto>
)