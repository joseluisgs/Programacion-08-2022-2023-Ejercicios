package models.dto

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "lista_vehiculos")
class VehicleListDto {
    @field:ElementList(name = "lista", entry = "vehiculo", inline = true)
    var dtoList: List<VehicleDto>? = null
}
