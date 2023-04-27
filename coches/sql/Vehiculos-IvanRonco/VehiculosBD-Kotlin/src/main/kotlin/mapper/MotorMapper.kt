package mapper

import models.motor.MotorDiesel
import models.motor.MotorElectrico
import models.motor.MotorGasolina
import models.motor.MotorHibrido
import java.sql.ResultSet
import java.util.*

fun parseToMototFromResultQuery(result: ResultSet) = when (result.getString("TIPO")) {
    "MotorGasolina" -> {
        MotorGasolina(
            uuid = UUID.fromString(result.getString("UUID")),
            modelo = result.getString("MODELO"),
            caballos = result.getInt("CABALLOS"),
            cilindradaGasolina = result.getInt("CILINDRADA")
        )
    }

    "MotorDiesel" -> {
        MotorDiesel(
            uuid = UUID.fromString(result.getString("UUID")),
            modelo = result.getString("MODELO"),
            caballos = result.getInt("CABALLOS"),
            cilindradaDiesel = result.getInt("CILINDRADA")
        )
    }

    "MotorElectrico" -> {
        MotorElectrico(
            uuid = UUID.fromString(result.getString("UUID")),
            modelo = result.getString("MODELO"),
            caballos = result.getInt("CABALLOS"),
            porcentajeCargado = result.getDouble("CARGA")
        )
    }

    else -> {
        MotorHibrido(
            uuid = UUID.fromString(result.getString("UUID")),
            modelo = result.getString("MODELO"),
            caballos = result.getInt("CABALLOS"),
            capacidadGasolina = result.getDouble("CAPACIDAD_GASOLINA"),
            capacidadElectrica = result.getDouble("CAPACIDAD_ELECTRICA")
        )
    }
}