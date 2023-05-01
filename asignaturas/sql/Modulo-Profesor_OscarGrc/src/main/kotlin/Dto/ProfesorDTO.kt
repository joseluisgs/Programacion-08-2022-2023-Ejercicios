package Dto


import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.time.LocalDate

@Root(name = "ProfesorDTO")
    class ProfesorDTO (@field:Element(name="id")    var id:String,
                       @field:Element(name="nombre") var nombre:String,
                       @field:Element(name="fechaIncorporacion")var fechaIncorporacion:String){
                          constructor():this("","","")
}
