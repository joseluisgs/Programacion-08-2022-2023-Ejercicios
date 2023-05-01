package repositories.conductores

import models.Conductor
import mu.KotlinLogging
import repositories.base.DataBaseFunctions
import services.database.CochesDataBaseService.db

object ConductorRepository : DataBaseFunctions<String, Conductor> {

    private val logger = KotlinLogging.logger { }

    override fun saveIntoDataBase(item: Conductor): Conductor {
        logger.debug { "Saving data into conductores" }
        val sql = """INSERT INTO conductores VALUES(?,?,?)""".trimIndent()

        db.use {
            it.prepareStatement(sql).use {
                stm ->
                stm.setString(1, item.uuid.toString())
                stm.setString(2, item.nombre)
                stm.setString(3, item.fechaCarnet.toString())

                stm.executeUpdate()
            }
        }
        return item
    }

    override fun clearTables(): Boolean {
        logger.debug { "Clearing Tables" }
        val sql = """DELETE FROM conductores""".trimIndent()

        db.use{it.prepareStatement(sql).use {
                stm -> stm.executeUpdate()
        }}
        return true
    }

    override fun deleteFromDatabaseById(id: String): Boolean {
        logger.debug { "Deleting element with uuid: $id" }
        val sql = """DELETE FROM conductores WHERE uuid = ?""".trimIndent()

        db.use {
            it.prepareStatement(sql).use {
                    stm -> stm.setString(1, id)

                stm.executeUpdate()
            }
        }
        return true
    }
}

