package Dto


import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "CocheDTO")
    class CocheDTO (@field:Element(name="id")    var id:String,
                    @field:Element(name="marca") var marca:String,
                    @field:Element(name="modelo")var modelo:String,
                    @field:Element(name="precio")var precio:String,
                    @field:Element(name="motor") var motor:String){
                          constructor():this("","","","","")
}