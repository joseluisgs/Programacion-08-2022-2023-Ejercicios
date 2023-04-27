package repositories.Coches

import models.Coche
import mu.KotlinLogging
import repositories.base.DataBaseFunctions
import services.database.CochesDataBaseService.db
import java.sql.Statement.RETURN_GENERATED_KEYS

object CocheRepository: DataBaseFunctions<Long, Coche> {

    private val logger = KotlinLogging.logger {  }


    override fun saveIntoDataBase(item: Coche): Coche {

        val sql = """INSERT INTO coches VALUES(null, ?,?,?,?)""".trimIndent()

        db.use{it.prepareStatement(sql, RETURN_GENERATED_KEYS).use {
            stm ->
            stm.setString(1, item.marca)
            stm.setString(2, item.modelo)
            stm.setDouble(3, item.precio)
            stm.setString(4, item.tipoMotor.toString())

            stm.executeUpdate()
        }}

        return item
    }

    override fun clearTables(): Boolean {
        val sql = """DELETE FROM coches""".trimIndent()

        db.use{it.prepareStatement(sql).use {
            stm -> stm.executeUpdate()
        }}
        return true

    }

    override fun deleteFromDatabaseById(id: Long): Boolean {
        val sql = """DELETE FROM coches WHERE id = ?""".trimIndent()

        db.use {
            it.prepareStatement(sql).use {
                stm -> stm.setLong(1, id)

                stm.executeUpdate()
            }
        }

        return true
        }
    }
