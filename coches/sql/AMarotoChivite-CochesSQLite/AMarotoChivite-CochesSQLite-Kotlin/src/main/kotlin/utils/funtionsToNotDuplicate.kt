package utils

import models.Vehicle

fun getTypeMotor(typeMotoString: String): Vehicle.TypeMotor {
    // Diferenciamos los tipos de motor
    var finalTypeMotor: Vehicle.TypeMotor = Vehicle.TypeMotor.NO_ASIGNED
    if (typeMotoString == Vehicle.TypeMotor.GASOLINA.toString()) {
        finalTypeMotor = Vehicle.TypeMotor.GASOLINA
    } else if (typeMotoString == Vehicle.TypeMotor.DIESEL.toString()) {
        finalTypeMotor = Vehicle.TypeMotor.GASOLINA
    } else if (typeMotoString == Vehicle.TypeMotor.HIBRIDO.toString()) {
        finalTypeMotor = Vehicle.TypeMotor.HIBRIDO
    } else if (typeMotoString == Vehicle.TypeMotor.ELECTRICO.toString()) {
        finalTypeMotor = Vehicle.TypeMotor.ELECTRICO
    }
    return finalTypeMotor
}