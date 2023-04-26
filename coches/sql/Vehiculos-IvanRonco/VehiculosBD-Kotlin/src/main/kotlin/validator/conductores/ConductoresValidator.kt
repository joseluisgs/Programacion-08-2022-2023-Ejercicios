package validator.conductores

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import error.ConductorError
import models.conductor.Conductor
import validator.validate

fun Conductor.validate(): Result<Conductor, ConductorError>{
    val dniPattern = Regex("[0-9]{8}[A-Z]")
    if(!this.dni.matches(dniPattern)){
        return Err(
            ConductorError.DniNoValido(this.dni)
        )
    }
    if(this.nombre.isEmpty()){
        return Err(
            ConductorError.NombreNoValido(this.nombre)
        )
    }
    if(this.apellidos.isEmpty()){
        return Err(
            ConductorError.ApellidosNoValidos(this.apellidos)
        )
    }
    val minEdad = 18
    if(this.edad < minEdad){
        return Err(
            ConductorError.EdadNoValida(this.edad.toString(), minEdad.toString())
        )
    }
    this.vehiculos.forEach {
        it.validate()
    }
    return Ok(this)
}