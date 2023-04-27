package repositorio.conductor

import models.conductor.Conductor
import mu.KotlinLogging
import repositorio.vehiculos.VehiculosRepositoryImpl
import service.database.vehiculos.ConfigDatabase
import java.util.*

object ConductorRepositoryImpl: ConductorRepositoryBase {

    val db = ConfigDatabase

    val logger = KotlinLogging.logger {  }

    override fun findByDni(dni: String): Conductor? {
        logger.debug { "Buscamos el conductor con el dni: $dni" }

        return findAll().firstOrNull{ it.dni == dni }
    }

    override fun findByEdad(edad: Int): List<Conductor> {
        logger.debug { "Buscamos los conductores con la edad: $edad" }

        return findAll().filter { it.edad == edad }
    }

    override fun findByNombre(nombre: String): List<Conductor> {
        logger.debug { "Buscamos los conductores con el nombre: $nombre" }

        return findAll().filter{ it.nombre == nombre }
    }

    override fun findAll(): List<Conductor> {
        logger.debug { "Conseguimos todos los conductores" }

        val conductores = mutableListOf<Conductor>()

        val sql = "SELECT * FROM CONDUCTORES"
        db.connection.use {
            it.prepareStatement(sql).use { stm ->
                val result = stm.executeQuery()

                while(result.next()){
                    val conductorId = UUID.fromString(result.getString("UUID"))
                    conductores.add(
                        Conductor(
                            conductorId,
                            result.getString("DNI"),
                            result.getString("NOMBRE"),
                            result.getString("APELLIDOS"),
                            result.getInt("EDAD"),
                            VehiculosRepositoryImpl.findAll().filter { it.conductorId == conductorId }
                        )
                    )
                }
            }
        }

        return conductores
    }

    override fun findById(uuid: UUID): Conductor? {
        logger.debug { "Buscamos el conductor con el uuid: $uuid" }

        return findAll().firstOrNull{ it.uuid == uuid }
    }

    override fun save(entity: Conductor): Conductor {
        logger.debug { "Guardamos/Actualizamos un conductor" }

        return findById(entity.uuid)?.let {
            update(entity)
        }?: run {
            create(entity)
        }
    }

    private fun create(entity: Conductor): Conductor{
        logger.debug { "Guardamos un conductor en la DB" }

        val sql = "INSERT INTO CONDUCTORES VALUES (?, ?, ?, ?, ?)"

        db.connection.use {
            it.prepareStatement(sql).use { stm ->
                stm.setString(1, entity.uuid.toString())
                stm.setString(2, entity.dni)
                stm.setString(3, entity.nombre)
                stm.setString(4, entity.apellidos)
                stm.setInt(5, entity.edad)

                stm.executeUpdate()
            }

            entity.vehiculos.forEach {
                VehiculosRepositoryImpl.save(it)
            }
        }
        return entity
    }

    private fun update(entity: Conductor): Conductor{
        logger.debug { "Actualizamos un conductor de la DB" }

        val sql = "UPDATE CONDUCTORES SET NOMBRE = ?, APELLIDOS = ?, EDAD = ? WHERE UUID = ?"

        val sqlDeleteAlVehiculos = "DELETE FROM VEHICULOS WHERE CONDUCTOR_ID = ?"

        db.connection.use {
            it.prepareStatement(sql).use { stm ->
                stm.setString(1, entity.nombre)
                stm.setString(2, entity.apellidos)
                stm.setInt(3, entity.edad)
                stm.setString(4, entity.uuid.toString())

                stm.executeUpdate()
            }

            it.prepareStatement(sqlDeleteAlVehiculos).use { stm ->
                stm.setString(1, entity.uuid.toString())

                stm.executeUpdate()
            }

            entity.vehiculos.forEach {
                VehiculosRepositoryImpl.save(it)
            }
        }
        return entity
    }

    override fun delete(entity: Conductor): Boolean {
        logger.debug { "Borramos un conductor" }

        return deleteById(entity.uuid)
    }

    override fun deleteById(uuid: UUID): Boolean {
        logger.debug { "Borramos el conductor con el uuid: $uuid" }

        var result = 0
        val sql = "DELETE FROM CONDUCTORES WHERE UUID = ?"
        db.connection.use {
            it.prepareStatement(sql).use { stm ->
                stm.setString(1, uuid.toString())

                result = stm.executeUpdate()
            }
        }

        return result > 0
    }

    override fun deleteAll(dropAll: Boolean) {
        logger.debug { "Borramos todos los conductores" }

        if(dropAll){
            VehiculosRepositoryImpl.deleteAll(false)
        }

        val sql = "DELETE FROM CONDUCTORES"
        db.connection.use {
            it.prepareStatement(sql).use { stm ->
                stm.executeUpdate()
            }
        }
    }
}