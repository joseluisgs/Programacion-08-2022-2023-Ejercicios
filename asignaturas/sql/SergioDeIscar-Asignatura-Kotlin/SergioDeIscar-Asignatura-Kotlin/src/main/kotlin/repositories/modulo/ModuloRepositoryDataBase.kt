package repositories.modulo

import models.Grado
import models.Modulo
import mu.KotlinLogging
import services.database.DataBaseManager
import java.util.*

private val logger = KotlinLogging.logger {  }

object ModuloRepositoryDataBase: ModuloRepository{
    override fun findAll(): Iterable<Modulo> {
        logger.debug { "ModuloRepositoryDataBase ->\tfindAll" }
        val modulos = mutableListOf<Modulo>()

        val sql = """SELECT * FROM tModulo"""
        DataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            val result = stm.executeQuery()
            while (result.next()){
                modulos.add(
                    Modulo(
                        UUID.fromString(result.getString("cUUID")),
                        result.getString("cNombre"),
                        result.getInt("nCurso"),
                        Grado.valueOf(result.getString("cGrado"))
                    )
                )
            }
        }
        return modulos.toList()
    }

    override fun findById(id: UUID): Modulo? {
        logger.debug { "ModuloRepositoryDataBase ->\tfindById" }
        var modulo: Modulo? = null

        val sql = """SELECT * FROM tModulo WHERE cUUID = ?"""
        DataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setString(1, id.toString())
            val result = stm.executeQuery()
            if (result.next()){
                modulo = Modulo(
                    UUID.fromString(result.getString("cUUID")),
                    result.getString("cNombre"),
                    result.getInt("nCurso"),
                    Grado.valueOf(result.getString("cGrado"))
                )
            }
        }
        return modulo
    }

    override fun save(element: Modulo): Modulo {
        logger.debug { "ModuloRepositoryDataBase ->\tsave" }
        return if (existsById(element.uuid)){
            update(element)
        }else{
            create(element)
        }
    }

    private fun update(element: Modulo): Modulo {
        logger.info { "ModuloRepositoryDataBase ->\tupdate" }
        val sql = """UPDATE tModulo SET cNombre = ?, nCurso = ?, cGrado = ? WHERE cUUID = ?"""
        DataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setString(1, element.nombre)
            stm.setInt(2, element.curso)
            stm.setString(3, element.grado.toString())
            stm.setString(4, element.uuid.toString())

            stm.executeUpdate()
        }
        return element
    }

    private fun create(element: Modulo): Modulo {
        logger.info { "ModuloRepositoryDataBase ->\tcreate" }
        val sql = """INSERT INTO tModulo(cUUID, cNombre, nCurso, cGrado) VALUES (?, ?, ?, ?)"""
        DataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setString(1, element.uuid.toString())
            stm.setString(2, element.nombre)
            stm.setInt(3, element.curso)
            stm.setString(4, element.grado.toString())

            stm.executeUpdate()
        }
        return element
    }

    override fun saveAll(elements: Iterable<Modulo>) {
        logger.debug { "ModuloRepositoryDataBase ->\tsaveAll" }
        elements.forEach { save(it) }
    }

    override fun deleteById(id: UUID): Boolean {
        logger.debug { "ModuloRepositoryDataBase ->\tdeleteById" }
        var result = 0;
        val deleteIntermedia = """DELETE FROM tProfesor_Modulo WHERE cUUID = ?"""
        val sql = """DELETE FROM tModulo WHERE cUUID = ?"""
        DataBaseManager.dataBase.use {
            it.prepareStatement(deleteIntermedia).use { stm ->
                stm.setString(1, id.toString())
                result = stm.executeUpdate()
            }
            it.prepareStatement(sql).use { stm ->
                stm.setString(1, id.toString())
                result = stm.executeUpdate()
            }
        }
        return result == 1
    }

    override fun delete(element: Modulo): Boolean {
        logger.debug { "ModuloRepositoryDataBase ->\tdelete" }
        return deleteById(element.uuid)
    }

    override fun deleteAll() {
        logger.debug { "ModuloRepositoryDataBase ->\tdeleteAll" }
        val deleteIntermedia = """DELETE FROM tProfesor_Modulo"""
        val sql = """DELETE FROM tModulo"""
        DataBaseManager.dataBase.use {
            it.prepareStatement(deleteIntermedia).use { stm ->
                stm.executeUpdate()
            }
            it.prepareStatement(sql).use { stm ->
                stm.executeUpdate()
            }
        }
    }

    override fun existsById(id: UUID): Boolean {
        logger.debug { "ModuloRepositoryDataBase ->\texistsById" }
        return findById(id) != null
    }

}