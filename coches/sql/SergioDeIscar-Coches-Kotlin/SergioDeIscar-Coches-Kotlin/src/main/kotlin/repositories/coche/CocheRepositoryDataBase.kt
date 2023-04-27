package repositories.coche

import com.github.michaelbull.result.onFailure
import controllers.conductor.ConductorController
import models.Coche
import models.TypeMotor
import mu.KotlinLogging
import repositories.conductor.ConductorRepositoryDataBase
import services.database.DataBaseManager
import services.storage.conductor.ConductorFileCsv
import java.lang.RuntimeException
import java.sql.Statement
import java.util.*

private val logger = KotlinLogging.logger {  }

object CocheRepositoryDataBase: CocheRepository{
    private val conductorController = ConductorController(
        ConductorRepositoryDataBase,
        ConductorFileCsv
    )

    override fun findAll(): Iterable<Coche> {
        logger.debug { "CocheRepositoryDataBase ->\tfindAll" }
        val coches = mutableListOf<Coche>()
        val sql = """SELECT * FROM tCoche"""
        DataBaseManager.dataBase.prepareStatement(sql).use {stm ->
            val result = stm.executeQuery()
            while (result.next()){
                coches.add(
                    Coche(
                        result.getLong("nIdCoche"),
                        result.getString("cMarca"),
                        result.getString("cModelo"),
                        result.getFloat("nPrecio"),
                        TypeMotor.valueOf(result.getString("cMotor")),
                        UUID.fromString(result.getString("cIdConductor"))
                    )
                )
            }
        }
        return coches.toList()
    }

    override fun findById(id: Long): Coche? {
        logger.debug { "CocheRepositoryDataBase ->\tfindById" }
        var coche: Coche? = null
        val sql = """SELECT * FROM tCoche WHERE nIdCoche = ?"""
        DataBaseManager.dataBase.prepareStatement(sql).use {stm ->
            stm.setLong(1, id)
            val result = stm.executeQuery()
            if (result.next()){
                coche = Coche(
                    result.getLong("nIdCoche"),
                    result.getString("cMarca"),
                    result.getString("cModelo"),
                    result.getFloat("nPrecio"),
                    TypeMotor.valueOf(result.getString("cMotor")),
                    UUID.fromString(result.getString("cIdConductor"))
                )
            }
        }
        return coche
    }

    override fun save(element: Coche): Coche {
        logger.debug { "CocheRepositoryDataBase ->\tsave" }
        return if (existsById(element.id)){
            update(element)
        }else{
            create(element)
        }
    }

    private fun create(element: Coche): Coche {
        logger.info { "CocheRepositoryDataBase ->\tcreate" }

        //Compruebo que existe el conductor
        conductorController.existsById(element.idConductor).onFailure {
            throw RuntimeException("ERROR: El conductor del coche no se encuentra en la base de datos.")
        }

        var newId = 0L
        val sql = """INSERT INTO tCoche(cMarca, cModelo, nPrecio, cMotor, cIdConductor) VALUES (?, ?, ?, ?, ?)"""
        DataBaseManager.dataBase.use {
            it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stm->
                stm.setString(1, element.marca)
                stm.setString(2, element.modelo)
                stm.setFloat(3, element.precio)
                stm.setString(4, element.motor.toString())
                stm.setString(5, element.idConductor.toString())
                stm.executeUpdate()
                val result = stm.generatedKeys
                if (result.next()){
                    newId = result.getLong(1)
                }
            }
        }
        return element.copy(id = newId)
    }

    private fun update(element: Coche): Coche {
        logger.info { "CocheRepositoryDataBase ->\tupdate" }
        val sql = """UPDATE tCoche SET cMarca = ?,  cModelo = ?, nPrecio = ?, cMotor = ?, cIdConductor = ? WHERE nIdCoche = ?"""
        DataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setString(1, element.marca)
            stm.setString(2, element.modelo)
            stm.setFloat(3, element.precio)
            stm.setString(4, element.motor.toString())
            stm.setString(5, element.idConductor.toString())
            stm.setLong(6, element.id)
            stm.executeUpdate()
        }
        return element
    }

    override fun saveAll(elements: Iterable<Coche>) {
        logger.debug { "CocheRepositoryDataBase ->\tsaveAll" }
        elements.forEach { save(it) }
    }

    override fun deleteById(id: Long): Boolean {
        logger.debug { "CocheRepositoryDataBase ->\tdeleteById" }
        var result = 0
        val sql = """DELETE FROM tCoche WHERE nIdCoche = ?"""
        DataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setLong(1, id)
            result = stm.executeUpdate()
        }
        return result == 1
    }

    override fun delete(element: Coche): Boolean {
        logger.debug { "CocheRepositoryDataBase ->\tdelete" }
        return deleteById(element.id)
    }

    override fun deleteAll() {
        logger.debug { "CocheRepositoryDataBase ->\tdeleteAll" }
        val sql = """DELETE FROM tCoche"""
        DataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.executeUpdate()
        }
    }

    override fun existsById(id: Long): Boolean {
        logger.debug { "CocheRepositoryDataBase ->\texistsById" }
        return findById(id) != null
    }
}