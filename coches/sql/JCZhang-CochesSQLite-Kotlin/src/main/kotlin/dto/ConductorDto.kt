package dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root


@Root(name = "conductor")
data class ConductorDto(

    @field: Attribute(name = "uuid")
    @param: Attribute(name = "uuid")
    val uuid: String,

    @field: Attribute(name = "nombre")
    @param: Attribute(name = "nombre")
    val nombre: String,

    @field: Attribute(name = "fechaCarnet")
    @param: Attribute(name = "fechaCarnet")
    val fechaCarnet: String

)

@Root(name ="conductores")
data class ConductorListDto(
    @field: Attribute(name = "conductores")
    @param: Attribute(name = "conductores")
    val conductores: List<ConductorDto>
)




