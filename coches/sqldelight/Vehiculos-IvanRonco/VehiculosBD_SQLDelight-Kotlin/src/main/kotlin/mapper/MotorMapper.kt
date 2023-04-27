package mapper

import database.MotorEntity
import models.motor.*
import java.util.*

fun MotorEntity.toMotor(): Motor{
    return when(this.tipo){
        "MotorGasolina" -> {
            MotorGasolina(
                uuid = UUID.fromString(this.uuid),
                modelo = this.modelo,
                caballos = this.caballos.toInt(),
                cilindradaGasolina = this.cilindrada!!.toInt()
            )
        }
        "MotorDiesel" -> {
            MotorDiesel(
                uuid = UUID.fromString(this.uuid),
                modelo = this.modelo,
                caballos = this.caballos.toInt(),
                cilindradaDiesel = this.cilindrada!!.toInt()
            )
        }
        "MotorElectrico" -> {
            MotorElectrico(
                uuid = UUID.fromString(this.uuid),
                modelo = this.modelo,
                caballos = this.caballos.toInt(),
                porcentajeCargado = this.carga!!
            )
        }
        else -> {
            MotorHibrido(
                uuid = UUID.fromString(this.uuid),
                modelo = this.modelo,
                caballos = this.caballos.toInt(),
                capacidadGasolina = this.capacidadGasolina!!,
                capacidadElectrica = this.capacidadElectrica!!
            )
        }
    }
}

fun Motor.toMotorEntity(): MotorEntity{
    return when(this::class.simpleName){
        "MotorGasolina" -> {
            MotorEntity(
                uuid = this.uuid.toString(),
                modelo = this.modelo,
                caballos = this.caballos.toLong(),
                tipo = "MotorGasolina",
                cilindrada = (this as MotorGasolina).cilindradaGasolina.toLong(),
                capacidadElectrica = null,
                capacidadGasolina = null,
                carga = null
            )
        }
        "MotorDiesel" -> {
            MotorEntity(
                uuid = this.uuid.toString(),
                modelo = this.modelo,
                caballos = this.caballos.toLong(),
                tipo = "MotorDiesel",
                cilindrada = (this as MotorDiesel).cilindradaDiesel.toLong(),
                capacidadElectrica = null,
                capacidadGasolina = null,
                carga = null
            )
        }
        "MotorElectrico" -> {
            MotorEntity(
                uuid = this.uuid.toString(),
                modelo = this.modelo,
                caballos = this.caballos.toLong(),
                tipo = "MotorElectrico",
                cilindrada = null,
                capacidadElectrica = null,
                capacidadGasolina = null,
                carga = (this as MotorElectrico).porcentajeCargado
            )
        }
        else -> {
            MotorEntity(
                uuid = this.uuid.toString(),
                modelo = this.modelo,
                caballos = this.caballos.toLong(),
                tipo = "MotorHibrido",
                cilindrada = null,
                capacidadElectrica = (this as MotorHibrido).capacidadElectrica,
                capacidadGasolina = (this as MotorHibrido).capacidadGasolina,
                carga = null
            )
        }
    }
}