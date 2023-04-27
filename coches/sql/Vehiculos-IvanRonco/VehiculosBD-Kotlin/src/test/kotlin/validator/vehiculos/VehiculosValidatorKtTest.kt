package validator.vehiculos

import com.github.michaelbull.result.*
import error.VehiculoError
import models.motor.MotorDiesel
import models.motor.MotorElectrico
import models.motor.MotorGasolina
import models.motor.MotorHibrido
import models.vehiculo.Vehiculo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import validator.validate
import java.util.*

internal class VehiculosValidatorKtTest{

    val uuidVehiculo = UUID.fromString("36cd96e9-f06b-4a0e-9375-3725459b576a")
    val uuidMotor = UUID.fromString("f5d1ddd5-64d5-4a79-9f4b-0afe496d16d1")
    val uuidConductor = UUID.fromString("9126fe9b-9a6a-45d8-a613-00313efc6d0e")

    @Test
    fun validatorCorrect(){
        val vehiculoCorrecto = Vehiculo(
            uuid = uuidVehiculo,
            modelo = "Peugeot",
            kilometros = 123,
            añoMatriculacion = 1976,
            apto = true,
            motor = MotorGasolina(
                uuid = uuidMotor,
                modelo = "Kadilak",
                caballos = 125,
                cilindradaGasolina = 8
            ),
            conductorId = uuidConductor
        )
        assertEquals(Ok(vehiculoCorrecto), vehiculoCorrecto.validate())
    }

    @Test
    fun validatorModeloIncorrect(){
        val vehiculoConNobreIncorrecto = Vehiculo(
            uuid = uuidVehiculo,
            modelo = "",
            kilometros = 123,
            añoMatriculacion = 1976,
            apto = true,
            motor = MotorGasolina(
                uuid = uuidMotor,
                modelo = "Kadilak",
                caballos = 125,
                cilindradaGasolina = 8
            ),
            conductorId = uuidConductor
        )
        assertEquals(
            """El modelo: "", no es válido.""",
            vehiculoConNobreIncorrecto.validate().getError()!!.message
        )
    }

    @Test
    fun validatorKilometrosIncorrect(){
        val vehiculoConPrecioIncorrecto = Vehiculo(
            uuid = uuidVehiculo,
            modelo = "Peugeot",
            kilometros = -123,
            añoMatriculacion = 1976,
            apto = true,
            motor = MotorGasolina(
                uuid = uuidMotor,
                modelo = "Kadilak",
                caballos = 125,
                cilindradaGasolina = 8
            ),
            conductorId = uuidConductor
        )
        assertEquals(
            "Los kilometros: -123, no pueden ser menor que 0.",
            vehiculoConPrecioIncorrecto.validate().getError()!!.message
        )
    }

    @Test
    fun validatorAñoMatriculacionIncorrect(){
        val vehiculoConCantidadIncorrecto = Vehiculo(
            uuid = uuidVehiculo,
            modelo = "Peugeot",
            kilometros = 123,
            añoMatriculacion = 1876,
            apto = true,
            motor = MotorGasolina(
                uuid = uuidMotor,
                modelo = "Kadilak",
                caballos = 125,
                cilindradaGasolina = 8
            ),
            conductorId = uuidConductor
        )
        assertEquals(
            "El año de matriculación: 1876, no puede ser menor que 1950.",
            vehiculoConCantidadIncorrecto.validate().getError()!!.message
        )
    }

    @Test
    fun validatorModeloMotorIncorrect(){
        val vehiculoConCantidadIncorrecto = Vehiculo(
            uuid = uuidVehiculo,
            modelo = "Peugeot",
            kilometros = 123,
            añoMatriculacion = 1976,
            apto = true,
            motor = MotorGasolina(
                uuid = uuidMotor,
                modelo = "",
                caballos = 125,
                cilindradaGasolina = 8
            ),
            conductorId = uuidConductor
        )
        assertEquals(
            """El modelo: "", no es válido.""",
            vehiculoConCantidadIncorrecto.validate().getError()!!.message
        )
    }

    @Test
    fun validatorCaballosMotorIncorrect(){
        val vehiculoConCantidadIncorrecto = Vehiculo(
            uuid = uuidVehiculo,
            modelo = "Peugeot",
            kilometros = 123,
            añoMatriculacion = 1976,
            apto = true,
            motor = MotorGasolina(
                uuid = uuidMotor,
                modelo = "Kadilak",
                caballos = -125,
                cilindradaGasolina = 8
            ),
            conductorId = uuidConductor
        )
        assertEquals(
            "Los caballos: -125, no pueden ser menor que 0.",
            vehiculoConCantidadIncorrecto.validate().getError()!!.message
        )
    }

    @Test
    fun validatorCilindradaMotorGasolinaIncorrect(){
        val vehiculoConCantidadIncorrecto = Vehiculo(
            uuid = uuidVehiculo,
            modelo = "Peugeot",
            kilometros = 123,
            añoMatriculacion = 1976,
            apto = true,
            motor = MotorGasolina(
                uuid = uuidMotor,
                modelo = "Kadilak",
                caballos = 125,
                cilindradaGasolina = -8
            ),
            conductorId = uuidConductor
        )
        assertEquals(
            "La cilindrada: -8, no puede ser menor que 0.",
            vehiculoConCantidadIncorrecto.validate().getError()!!.message
        )
    }

    @Test
    fun validatorCilindradaMotorDieselIncorrect(){
        val vehiculoConCantidadIncorrecto = Vehiculo(
            uuid = uuidVehiculo,
            modelo = "Peugeot",
            kilometros = 123,
            añoMatriculacion = 1976,
            apto = true,
            motor = MotorDiesel(
                uuid = uuidMotor,
                modelo = "Kadilak",
                caballos = 125,
                cilindradaDiesel = -8
            ),
            conductorId = uuidConductor
        )
        assertEquals(
            "La cilindrada: -8, no puede ser menor que 0.",
            vehiculoConCantidadIncorrecto.validate().getError()!!.message
        )
    }

    @Test
    fun validatorCargaMotorElectricoIncorrect(){
        val vehiculoConCantidadIncorrecto = Vehiculo(
            uuid = uuidVehiculo,
            modelo = "Peugeot",
            kilometros = 123,
            añoMatriculacion = 1976,
            apto = true,
            motor = MotorElectrico(
                uuid = uuidMotor,
                modelo = "Kadilak",
                caballos = 125,
                porcentajeCargado = -8.0
            ),
            conductorId = uuidConductor
        )
        assertEquals(
            "El porcentaje de carga: -8.0%, no puede ser menor que 0.0%.",
            vehiculoConCantidadIncorrecto.validate().getError()!!.message
        )
    }

    @Test
    fun validatorCapacidadGasolinaMotorHibridoIncorrect(){
        val vehiculoConCantidadIncorrecto = Vehiculo(
            uuid = uuidVehiculo,
            modelo = "Peugeot",
            kilometros = 123,
            añoMatriculacion = 1976,
            apto = true,
            motor = MotorHibrido(
                uuid = uuidMotor,
                modelo = "Kadilak",
                caballos = 125,
                capacidadGasolina = -129.0,
                capacidadElectrica = 344.5
            ),
            conductorId = uuidConductor
        )
        assertEquals(
            "La capacidad: -129.0, no puede ser menor que 0.0.",
            vehiculoConCantidadIncorrecto.validate().getError()!!.message
        )
    }

    @Test
    fun validatorCapacidadElectricaMotorHibridoIncorrect(){
        val vehiculoConCantidadIncorrecto = Vehiculo(
            uuid = uuidVehiculo,
            modelo = "Peugeot",
            kilometros = 123,
            añoMatriculacion = 1976,
            apto = true,
            motor = MotorHibrido(
                uuid = uuidMotor,
                modelo = "Kadilak",
                caballos = 125,
                capacidadGasolina = 129.0,
                capacidadElectrica = -344.5
            ),
            conductorId = uuidConductor
        )
        assertEquals(
            "La capacidad: -344.5, no puede ser menor que 0.0.",
            vehiculoConCantidadIncorrecto.validate().getError()!!.message
        )
    }

}