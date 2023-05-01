package dto

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "conductores")
data class ConductoresDto(
    @field:ElementList(name = "conductores", inline = true)
    @param:ElementList(name = "conductores", inline = true)
    val conductores: List<ConductorDto>
)