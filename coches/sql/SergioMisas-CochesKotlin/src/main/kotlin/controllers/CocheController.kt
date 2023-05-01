package controllers

import models.Coche
import models.Conductor
import repositories.CocheRepositoryImpl
import java.util.*

class CocheController {
    val repo = CocheRepositoryImpl

    fun leerCsv(): List<Coche> {
        println(repo.leerCsv().forEach { it.toString() + "\n" })
        return repo.leerCsv()
    }

    fun escribirJson() {
        repo.escribirJson()
    }

    fun findAll() {
        println(repo.findAll().forEach { it.toString() + "\n" })
    }

    fun findById(id: Long) {
        val res = repo.findById(id)
        if (res != null) println(res.toString())
        else throw RuntimeException("No se encontro el coche con id: $id")
    }

    fun save(obj: Coche) {
        println(repo.save(obj))
    }

    fun delete(obj: Coche) {
        if (repo.delete(obj)) println(obj.toString())
        else throw RuntimeException("No se ha podido borrar el coche con id: {${obj.id}}")
    }

    fun update(obj: Coche) {
        val res = repo.update(obj)
        if (res != null) println("Se ha actualizado el coche")
        else throw RuntimeException("No se ha podido actualizar el coche con id: {${obj.id}}")
    }
}
