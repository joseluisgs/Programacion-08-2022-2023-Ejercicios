package repositorio.vehiculos

import models.vehiculo.Vehiculo
import mu.KotlinLogging
import repositorio.motor.MotorRepositoryImpl
import service.database.vehiculos.ConfigDatabase
import java.util.*

object VehiculosRepositoryImpl: VehiculoRepositoryBase {

    val db = ConfigDatabase

    private val logger = KotlinLogging.logger {  }

    override fun findByAñoMatriculacion(añoMatriculacion: Int): List<Vehiculo> {
        logger.debug { "Buscamos segun el año de matriculacion: $añoMatriculacion" }

        return findAll().filter { it.añoMatriculacion == añoMatriculacion }
    }

    override fun findByApto(apto: Boolean): List<Vehiculo> {
        logger.debug { "Buscamos según apto: $apto" }

        return findAll().filter { it.apto == apto }
    }

    override fun findByModelo(modelo: String): List<Vehiculo> {
        logger.debug { "Buscamos segun modelo: $modelo" }

        return findAll().filter { it.modelo.lowercase().contains(modelo.lowercase()) }
    }

    override fun findAll(): List<Vehiculo> {
        logger.debug { "Buscamos todos los vehiculos" }

        val vehiculos = mutableListOf<Vehiculo>()
        db.connection.use {
            it.prepareStatement("SELECT * FROM VEHICULOS").use { stm ->
                val result = stm.executeQuery()
                while(result.next()){
                    vehiculos.add(
                        Vehiculo(
                            UUID.fromString(result.getString("UUID")),
                            result.getString("MODELO"),
                            result.getInt("KILOMETRO"),
                            result.getInt("ANYO_MATRICULACION"),
                            result.getBoolean("APTO"),
                            MotorRepositoryImpl.findById(UUID.fromString(result.getString("MOTOR_ID")))!!,
                            UUID.fromString(result.getString("CONDUCTOR_ID"))
                        )
                    )
                }
            }
        }
        return vehiculos
    }

    override fun findById(uuid: UUID): Vehiculo? {
        logger.debug { "Buscamos segun uuid: $uuid" }

        return findAll().firstOrNull { it.uuid == uuid }
    }

    override fun save(entity: Vehiculo): Vehiculo {
        logger.debug { "Guardamos/actualizamos un vehiculo" }

        return findById(entity.uuid)?.let {
            update(entity)
        }?: run {
            create(entity)
        }
    }

    private fun create(entity: Vehiculo): Vehiculo {
        logger.debug { "Se inserta un nuevo vehiculo en la base de datos" }

        val sql = """INSERT INTO VEHICULOS VALUES (?, ?, ?, ?, ?, ?, ?)"""
        db.connection.use {
            MotorRepositoryImpl.findById(entity.motor.uuid)?:run {
                MotorRepositoryImpl.save(entity.motor)
            }

            it.prepareStatement(sql).use { stm ->
                stm.setString(1, entity.uuid.toString())
                stm.setString(2, entity.modelo)
                stm.setInt(3, entity.kilometros)
                stm.setInt(4, entity.añoMatriculacion)
                stm.setBoolean(5, entity.apto)
                stm.setString(6, entity.motor.uuid.toString())
                stm.setString(7, entity.conductorId.toString())

                stm.executeUpdate()
            }
        }
        return entity
    }

    private fun update(entity: Vehiculo): Vehiculo {
        logger.debug { "Se actualiza un vehiculo de la base de datos" }

        val sql = "UPDATE VEHICULOS SET MODELO = ?, KILOMETRO = ?, ANYO_MATRICULACION = ?, APTO = ?, MOTOR_ID = ?, CONDUCTOR_ID = ? WHERE UUID = ?"
        db.connection.use {
            MotorRepositoryImpl.findById(entity.motor.uuid)?:run {
                MotorRepositoryImpl.save(entity.motor)
            }

            it.prepareStatement(sql).use {stm ->
                stm.setString(1, entity.modelo)
                stm.setInt(2, entity.kilometros)
                stm.setInt(3, entity.añoMatriculacion)
                stm.setBoolean(4, entity.apto)
                stm.setString(5, entity.motor.uuid.toString())
                stm.setString(6, entity.conductorId.toString())
                stm.setString(7, entity.uuid.toString())

                stm.executeUpdate()
            }
        }
        return entity
    }

    override fun delete(entity: Vehiculo): Boolean {
        logger.debug { "Eliminamos un vehiculo" }
        return deleteById(entity.uuid)
    }

    override fun deleteById(uuid: UUID): Boolean {
        logger.debug { "Eliminamos un vehiculo según su uuid: $uuid" }
        var result = 0
        val sql = "DELETE FROM VEHICULOS WHERE UUID = ?"
        db.connection.use {
            it.prepareStatement(sql).use { stm ->
                stm.setString(1, uuid.toString())

                result = stm.executeUpdate()
            }
        }
        return result > 0
    }

    override fun deleteAll(dropMotores: Boolean) {
        logger.debug { "Eliminamos todos los vehiculos" }

        if(dropMotores){
            MotorRepositoryImpl.deleteAll(true)
        }

        val sqlVehiculos = "DELETE FROM VEHICULOS"
        db.connection.use {
            it.prepareStatement(sqlVehiculos).use { stm ->
                stm.executeUpdate()
            }
        }
    }
}