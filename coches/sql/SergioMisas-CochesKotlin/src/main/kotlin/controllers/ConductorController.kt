package controllers

import models.Conductor
import repositories.ConductorRepositoryImpl
import java.util.*

class ConductorController {
    val repo = ConductorRepositoryImpl

    fun findAll() {
        println(repo.findAll().forEach { it.toString() })
    }

    fun findById(id: UUID) {
        val res = repo.findById(id)
        if (res != null) println(res.toString())
        else throw RuntimeException("No se encontro el conductor con id: $id")
    }

    fun save(obj: Conductor) {
        println(repo.save(obj))
    }

    fun delete(obj: Conductor) {
        if (repo.delete(obj)) println(obj.toString())
        else throw RuntimeException("No se ha podido borrar el conductor con id: {${obj.uuid}}")
    }

    fun update(obj: Conductor) {
        val res = repo.update(obj)
        if (res != null) println("Se ha actualizado el conductor")
        else throw RuntimeException("No se ha podido actualizar el conductor con id: {${obj.uuid}}")
    }
}
