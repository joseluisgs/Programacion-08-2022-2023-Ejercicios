package validator

import exceptions.ProfesorException
import models.Profesor

fun Profesor.validate(): Boolean{
    require(this.nombre.isNotEmpty()){
        throw ProfesorException.NombreNoValido(nombre)
    }
    val min = 0
    require(this.experiencia >= min){
        throw ProfesorException.ExperienciasNoValida(experiencia.toString(), min.toString())
    }
    return true
}