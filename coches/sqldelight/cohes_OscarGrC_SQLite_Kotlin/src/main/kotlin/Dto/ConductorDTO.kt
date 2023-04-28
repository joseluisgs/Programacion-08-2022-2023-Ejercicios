package Dto

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.time.LocalDate
import java.util.*

@Root(name = "ConductorDTO")
class ConductorDTO (@field:Element(name="UUID")   var uuid:String,
                @field:Element(name="nombre")     var nombre:String,
                @field:Element(name="fechaCarnet")var fechaCarnet:String){
    constructor():this("","","")
}