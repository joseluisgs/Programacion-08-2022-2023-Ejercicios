package dto

import com.squareup.moshi.Json
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Json(name = "profesor")
@Root(name = "profesor")
data class ProfesorDto(
    @Json(name = "uuid")
    @field:Element(name = "uuid")
    @param:Element(name = "uuid")
    val uuid: String,

    @Json(name = "nombre")
    @field:Element(name = "nombre")
    @param:Element(name = "nombre")
    val nombre: String,

    @Json(name = "experiencia")
    @field:Element(name = "experiencia")
    @param:Element(name = "experiencia")
    val experiencia: String
)