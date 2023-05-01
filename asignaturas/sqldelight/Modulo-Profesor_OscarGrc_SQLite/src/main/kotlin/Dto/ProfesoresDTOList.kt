package Dto

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.io.Serializable

@Root(name = "ProfesoresDTOList")
class ProfesoresDTOList(
    @field:ElementList(name = "ProfesoresDTOList", inline = true)
    @param:ElementList(name = "ProfesoresDTOList", inline = true)  var profesoresDTOList:List<ProfesorDTO>):Serializable{
constructor(): this(mutableListOf())
}