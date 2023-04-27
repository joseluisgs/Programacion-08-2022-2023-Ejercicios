package repositorio.motor

import mapper.parseToMototFromResultQuery
import models.motor.*
import mu.KotlinLogging
import service.database.vehiculos.ConfigDatabase
import java.util.*

object MotorRepositoryImpl: MotorRepositoryBase {

    val db = ConfigDatabase

    private val logger = KotlinLogging.logger {  }

    override fun findMotorByModel(modelo: String): List<Motor> {
        logger.debug { "COnseguimos todos los motores del modelo: $modelo" }

        return findAll().filter { it.modelo.lowercase() == modelo.lowercase() }
    }

    override fun findAll(): List<Motor> {
        logger.debug { "Conseguimos todos los motores" }

        val motores = mutableListOf<Motor>()

        val sql = "SELECT * FROM MOTORES"
        db.connection.use {
            it.prepareStatement(sql).use {stm ->
                val result = stm.executeQuery()

                while(result.next()){
                    motores.add(
                        parseToMototFromResultQuery(result)
                    )
                }
            }
        }

        return motores
    }

    override fun findById(uuid: UUID): Motor? {
        logger.debug { "Conseguimos todos los motores con el uuid: $uuid" }

        return findAll().firstOrNull{ it.uuid == uuid }
    }

    override fun save(entity: Motor): Motor {
        logger.debug { "Guardamos/Actualizamos un motor en la DB" }

        return findById(entity.uuid)?.let {
            update(entity)
        }?: run {
            create(entity)
        }
    }

    private fun create(entity: Motor): Motor{
        logger.debug { "Guardamos un nuevo motor en la DB" }

        val sql = "INSERT INTO MOTORES VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
        db.connection.use {
            it.prepareStatement(sql).use {stm ->
                stm.setString(1 , entity.uuid.toString())
                stm.setString(2, entity.modelo)
                stm.setInt(3, entity.caballos)
                stm.setString(4, entity::class.simpleName)
                when(entity){
                    is MotorDiesel -> {
                        stm.setInt(5, entity.cilindradaDiesel)
                        stm.setObject(6, null)
                        stm.setObject(7, null)
                        stm.setObject(8, null)
                    }
                    is MotorGasolina -> {
                        stm.setInt(5, entity.cilindradaGasolina)
                        stm.setObject(6, null)
                        stm.setObject(7, null)
                        stm.setObject(8, null)
                    }
                    is MotorHibrido -> {
                        stm.setObject(5, null)
                        stm.setDouble(6, entity.capacidadGasolina)
                        stm.setDouble(7, entity.capacidadGasolina)
                        stm.setObject(7, null)
                    }
                    is MotorElectrico -> {
                        stm.setObject(5, null)
                        stm.setObject(6, null)
                        stm.setObject(7, null)
                        stm.setDouble(8, entity.porcentajeCargado)
                    }
                }

                stm.executeUpdate()
            }
        }

        return entity
    }

    private fun update(entity: Motor): Motor{
        logger.debug { "Actualizamos un motor ya existente de la DB" }

        val sqlGasolinaODiesel = "UPDATE MOTORES VALUES SET MODELO = ?, CABALLOS = ?, CILINDRADA = ? WHERE UUID = ?"
        val sqlElectrico = "UPDATE MOTORES VALUES SET MODELO = ?, CABALLOS = ?, CARGA = ? WHERE UUID = ?"
        val sqlHibrido = "UPDATE MOTORES VALUES SET MODELO = ?, CABALLOS = ?, CAPACIDAD_GASOLINA = ?, CAPACIDAD_ELECTRICA = ? WHERE UUID = ?"

        db.connection.use {
            when(entity){
                is MotorDiesel -> {
                    db.connection.use {
                        it.prepareStatement(sqlGasolinaODiesel).use { stm ->
                            stm.setString(1, entity.modelo)
                            stm.setInt(2, entity.caballos)
                            stm.setInt(3, entity.cilindradaDiesel)
                            stm.setString(4 , entity.uuid.toString())

                            stm.executeUpdate()
                        }
                    }
                }
                is MotorGasolina -> {
                    db.connection.use {
                        it.prepareStatement(sqlGasolinaODiesel).use { stm ->
                            stm.setString(1, entity.modelo)
                            stm.setInt(2, entity.caballos)
                            stm.setInt(3, entity.cilindradaGasolina)
                            stm.setString(4 , entity.uuid.toString())

                            stm.executeUpdate()
                        }
                    }
                }
                is MotorHibrido -> {
                    db.connection.use {
                        it.prepareStatement(sqlHibrido).use { stm ->
                            stm.setString(1, entity.modelo)
                            stm.setInt(2, entity.caballos)
                            stm.setDouble(3, entity.capacidadGasolina)
                            stm.setDouble(3, entity.capacidadElectrica)
                            stm.setString(4 , entity.uuid.toString())

                            stm.executeUpdate()
                        }
                    }
                }
                else -> {
                    db.connection.use {
                        it.prepareStatement(sqlElectrico).use { stm ->
                            stm.setString(1, entity.modelo)
                            stm.setInt(2, entity.caballos)
                            stm.setDouble(3, (entity as MotorElectrico).porcentajeCargado)
                            stm.setString(4 , entity.uuid.toString())

                            stm.executeUpdate()
                        }
                    }
                }
            }
        }

        return entity
    }

    override fun delete(entity: Motor): Boolean {
        logger.debug { "Se borrar un motor de la BD" }

        return deleteById(entity.uuid)
    }

    override fun deleteById(uuid: UUID): Boolean {
        logger.debug { "Se borrar el motor con id: $uuid" }

        var result = 0
        val sql = "DELETE FROM MOTORES WHERE UUID = ?"
        db.connection.use {
            it.prepareStatement(sql).use {stm ->
                stm.setString(1, uuid.toString())

                result = stm.executeUpdate()
            }
        }

        return result > 0
    }

    override fun deleteAll(dropAll: Boolean) {
        logger.debug { "Se borran todos los motores" }

        val sql = "DELETE FROM MOTORES"
        db.connection.use {
            it.prepareStatement(sql).use {stm ->
                stm.executeUpdate()
            }
        }
    }
}