package Dto

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.io.Serializable

@Root(name = "ConductorDTOList")
class ConductorDTOList(
    @field:ElementList(name = "ConductorDTOList", inline = true)
    @param:ElementList(name = "ConductorDTOList", inline = true)  var conductorDTOList:List<ConductorDTO>): Serializable {
    constructor(): this(mutableListOf())
}