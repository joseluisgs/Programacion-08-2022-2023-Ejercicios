package validator

import com.github.michaelbull.result.*
import error.VehiculoError
import models.vehiculo.Vehiculo
import validator.motores.validate

fun Vehiculo.validate(): Result<Vehiculo, VehiculoError>{
    if(this.modelo.isEmpty()){
        return Err(VehiculoError.ModeloNoValido(modelo))
    }
    if(this.kilometros < 0){
        return Err(VehiculoError.KilometrosNoValidos(kilometros.toString(), "0"))
    }
    if(this.añoMatriculacion < 1950){
        return Err(VehiculoError.AñoMatriculacion(añoMatriculacion.toString(), "1950"))
    }
    return this.motor.validate().andThen { Ok(this) }
}