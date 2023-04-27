package models

abstract class Persona (
    //Este id será un autonumérco asignado por la DB
    val id: Long = 0,
    val nombre: String
)