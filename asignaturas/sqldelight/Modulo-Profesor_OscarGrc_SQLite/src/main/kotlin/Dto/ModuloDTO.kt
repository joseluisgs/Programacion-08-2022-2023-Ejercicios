package Dto

import models.Grado
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.util.*

@Root(name = "ModuloDTO")
class ModuloDTO (@field:Element(name="UUID")   var uuid:String,
                 @field:Element(name="nombre") var nombre:String,
                 @field:Element(name="curso")  var curso:String,
                 @field:Element(name="grado")  var grado:String){
    constructor():this("","","","")
}
