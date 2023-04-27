package validator.motores

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import error.MotorError
import models.motor.*

fun Motor.validate(): Result<Motor, MotorError>{
    if(this.modelo.isEmpty()){
        return Err(MotorError.ModeloNoValido(modelo))
    }
    if(this.caballos < 0){
        return Err(MotorError.CaballosNoValidos(caballos.toString(), "0"))
    }
    when(this){
        is MotorElectrico -> {
            if(this.porcentajeCargado < 0.0){
                return Err(MotorError.PorcentajeCargaNoValida(porcentajeCargado.toString(), "0.0"))
            }
        }
        is MotorHibrido -> {
            if(this.capacidadElectrica < 0.0){
                return Err(MotorError.CapacidadNoValida(capacidadElectrica.toString(), "0.0"))
            }
            if(this.capacidadGasolina < 0.0){
                return Err(MotorError.CapacidadNoValida(capacidadGasolina.toString(), "0.0"))
            }
        }
        is MotorGasolina -> {
            if(this.cilindradaGasolina < 0){
                return Err(MotorError.CilindradaNoValida(cilindradaGasolina.toString(), "0"))
            }
        }
        is MotorDiesel -> {
            if(this.cilindradaDiesel < 0){
                return Err(MotorError.CilindradaNoValida(cilindradaDiesel.toString(), "0"))
            }
        }
    }
    return Ok(this)
}