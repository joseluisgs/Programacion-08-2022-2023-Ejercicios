package Dto

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.io.Serializable

@Root(name = "ModulosDTOList")
class ModulosDTOList(
    @field:ElementList(name = "ModulosDTOList", inline = true)
    @param:ElementList(name = "ModulosDTOList", inline = true)  var moduloDTOList:List<ModuloDTO>): Serializable {
    constructor(): this(mutableListOf())
}