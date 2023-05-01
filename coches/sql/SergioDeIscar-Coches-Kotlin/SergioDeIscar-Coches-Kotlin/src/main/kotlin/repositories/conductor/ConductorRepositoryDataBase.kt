package repositories.conductor

import controllers.coche.CocheController
import models.Conductor
import mu.KotlinLogging
import repositories.coche.CocheRepositoryDataBase
import services.database.DataBaseManager
import services.storage.coche.CocheFileCsv
import java.time.LocalDate
import java.util.*

private val logger = KotlinLogging.logger {  }

object ConductorRepositoryDataBase : ConductorRepository{
    private val cocheController = CocheController(
        CocheRepositoryDataBase,
        CocheFileCsv
    )

    override fun findAll(): Iterable<Conductor> {
        logger.debug { "ConductorRepositoryDatabase ->\tfindAll" }
        val conductores = mutableListOf<Conductor>()
        val sql = """SELECT * FROM tConductor"""
        DataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            val result = stm.executeQuery()
            while (result.next()){
                val uuid = UUID.fromString(result.getString("cIdConductor"))
                conductores.add(
                    Conductor(
                        uuid,
                        result.getString("cNombre"),
                        LocalDate.parse(result.getString("dCartnet")),
                        cocheController.findAll().filter { it.idConductor == uuid }
                    )
                )
            }
        }
        return conductores.toList()
    }

    override fun findById(id: UUID): Conductor? {
        logger.debug { "ConductorRepositoryDatabase ->\tfindById" }
        var conductor: Conductor? = null
        val sql = """SELECT * FROM tConductor WHERE cIdConductor = ?"""
        DataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setString(1, id.toString())
            val result = stm.executeQuery()
            if (result.next()){
                conductor = Conductor(
                    id,
                    result.getString("cNombre"),
                    LocalDate.parse(result.getString("dCartnet")),
                    cocheController.findAll().filter { it.idConductor == id }
                )
            }
        }
        return conductor
    }

    override fun save(element: Conductor): Conductor {
        logger.debug { "ConductorRepositoryDatabase ->\tsave" }
        return if (existsById(element.uuid)){
            update(element)
        } else {
            create(element)
        }
    }

    private fun create(element: Conductor): Conductor {
        logger.info { "ConductorRepositoryDatabase ->\tcreate" }
        val sql = """INSERT INTO tConductor(cIdConductor, cNombre, dCartnet) VALUES (?, ?, ?)"""
        DataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setString(1, element.uuid.toString())
            stm.setString(2, element.nombre)
            stm.setString(3, element.fechaCarnet.toString())
            stm.executeUpdate()
        }
        element.coches.forEach { cocheController.save(it) }
        return element
    }

    private fun update(element: Conductor): Conductor {
        logger.info { "ConductorRepositoryDatabase ->\tupdate" }
        val sql = """UPDATE tConductor SET cNombre = ?, dCartnet = ? WHERE cIdConductor = ?"""
        DataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setString(1, element.nombre)
            stm.setString(2, element.fechaCarnet.toString())
            stm.setString(3, element.uuid.toString())
            stm.executeUpdate()
        }
        cocheController.findAll()
            .filter { it.idConductor == element.uuid }
            .forEach { cocheController.deleteById(it.id) }
        cocheController.saveAll(element.coches)
        return element
    }

    override fun saveAll(elements: Iterable<Conductor>) {
        logger.debug { "ConductorRepositoryDatabase ->\tsaveAll" }
        elements.forEach { save(it) }
    }

    override fun deleteById(id: UUID): Boolean {
        logger.debug { "ConductorRepositoryDatabase ->\tdeleteById" }
        var result = 0
        val sql = """DELETE FROM tConductor WHERE cIdConductor = ?"""
        cocheController.findAll()
            .filter { it.idConductor == id }
            .forEach { cocheController.deleteById(it.id) }
        DataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setString(1, id.toString())
            result = stm.executeUpdate()
        }
        return result == 1
    }

    override fun delete(element: Conductor): Boolean {
        logger.debug { "ConductorRepositoryDatabase ->\tdelete" }
        return deleteById(element.uuid)
    }

    override fun deleteAll() {
        logger.debug { "ConductorRepositoryDatabase ->\tdeleteAll" }
        val sql = """DELETE FROM tConductor"""
        cocheController.deleteAll()
        DataBaseManager.dataBase.prepareStatement(sql).use {stm ->
            stm.executeUpdate()
        }
    }

    override fun existsById(id: UUID): Boolean {
        logger.debug { "ConductorRepositoryDatabase ->\texistsById" }
        return findById(id) != null
    }
}