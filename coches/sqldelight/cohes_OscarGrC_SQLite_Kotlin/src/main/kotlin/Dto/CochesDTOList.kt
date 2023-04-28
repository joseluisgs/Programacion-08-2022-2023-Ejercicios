package Dto

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.io.Serializable

@Root(name = "CochesDTOList")
class CochesDTOList(
    @field:ElementList(name = "CochesDTOList", inline = true)
    @param:ElementList(name = "CochesDTOList", inline = true)  var cochesDTOList:List<CocheDTO>):Serializable{
constructor(): this(mutableListOf())
}