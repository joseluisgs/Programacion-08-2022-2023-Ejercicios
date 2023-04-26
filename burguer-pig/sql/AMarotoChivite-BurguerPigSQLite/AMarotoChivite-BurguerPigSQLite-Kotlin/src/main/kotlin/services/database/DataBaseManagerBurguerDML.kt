package services.database

import config.ConfigApp
import models.Burguer
import models.LineaBurguer
import services.database.base.IDataBaseManagerDML
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement
import java.util.*

private val logger = mu.KotlinLogging.logger { }

class DataBaseManagerBurguerDML : IDataBaseManagerDML<Burguer> {

    private fun db(): Connection {
        return DriverManager.getConnection(ConfigApp.getUrlDbConection())
    }

    // Ejecutador de consulta general, que nos llega del repositorio
    override fun executeQuery(querySql: String, querySql2: String?, whereQuery: Any?, whereQuery2: Any?): List<Burguer> {
        logger.debug { "DataManager: Cargando Consulta" }

        val items = mutableListOf<Burguer>()

        db().use {
            it.prepareStatement(querySql).use { stm ->

                // Pasamos los parámetros en caso de búsquedas personalizadas
                if (whereQuery != null) {
                    stm.setString(1, whereQuery.toString())
                }

                if (whereQuery2 != null) {
                    stm.setString(1, whereQuery2.toString())
                }

                val resulSet = stm.executeQuery()
                while (resulSet.next()) {

                    items.add(
                        Burguer(
                            UUID.fromString(resulSet.getString("uuid")),
                            resulSet.getString("name"),
                            resulSet.getInt("stock")
                        )
                    )
                }
            }
        }

        if (querySql2 != null) {
            if (items.isNotEmpty()){
                items.forEach { burguer ->
                    db().use {
                        it.prepareStatement(querySql2).use { stm ->

                            // Pasamos los parámetros en caso de búsquedas personalizadas
                            if (whereQuery2 != null) {
                                stm.setString(1, whereQuery2.toString())
                            }

                            val resulSet = stm.executeQuery()
                            while (resulSet.next()) {

                                burguer.addLinea(
                                    LineaBurguer(
                                        resulSet.getLong("linea_id"),
                                        UUID.fromString(resulSet.getString("burguer_uuid")),
                                        resulSet.getLong("ingrediente_id"),
                                        resulSet.getInt("ingrediente_quantity"),
                                        resulSet.getDouble("ingrediente_price")
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }


        return items
    }

    override fun insertRecord(newEntity: Burguer): Burguer {
        logger.debug { "DataManager: Insertando/Creando ${newEntity::class.simpleName}" }

        val sql = """
            INSERT INTO ${newEntity::class.simpleName}Table 
            VALUES (?,?,?)
        """.trimIndent()

        db().use {
            it.prepareStatement(sql).use { stm ->

            // Pasamos los parámetros para introducir
            stm.setString(1, newEntity.uuid.toString())
            stm.setString(2, newEntity.name)
            stm.setInt(3, newEntity.stock)
            stm.executeUpdate()
            }
        }

        // Ahora introducimos las Lineas
        val sql1 = """
            INSERT INTO Linea${newEntity::class.simpleName}Table 
            VALUES (null,?,?,?,?)
        """.trimIndent()

        // Para devolver las lineas
        var myId: Long = 0
        val copyLineaBurguer= mutableListOf<LineaBurguer>()

        newEntity.lineaBurguer.forEach { linea ->
            db().use {
                it.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS).use { stm ->
                    // Pasamos los parámetros para introducir
                    stm.setString(1, newEntity.uuid.toString())
                    stm.setLong(2, linea.ingredienteId)
                    stm.setInt(3, linea.ingredienteQuantity)
                    stm.setDouble(4, linea.ingredientePrice)
                    stm.executeUpdate()

                    // Recogemos las claves auto-numericas de la lineas
                    val generatedKeys = stm.generatedKeys
                    if (generatedKeys.next()) {
                        myId = generatedKeys.getLong(1)
                    }

                    copyLineaBurguer.add(linea.copy(lineaId = myId))
                }
            }
        }

        return newEntity.copy(lineaBurguer = copyLineaBurguer)
    }

    override fun updateRecord(newEntity: Burguer): Burguer {
        logger.debug { "DataManager: Actualizando ${newEntity::class.simpleName}" }

        val sql = """
            UPDATE ${newEntity::class.simpleName}Table 
            SET uuid = ?, name = ?, stock = ?
            WHERE uuid = ?
        """.trimIndent()

        db().use {
            it.prepareStatement(sql).use { stm ->

                // Pasamos los parámetros para introducir
                stm.setString(1, newEntity.uuid.toString())
                stm.setString(2, newEntity.name)
                stm.setInt(3, newEntity.stock)
                stm.executeUpdate()
            }
        }

        // Ahora actualizamos las Lineas
        val sql1 = """
            UPDATE Linea${newEntity::class.simpleName}Table 
            SET linea_id = ?, burguer_uuid = ?, ingrediente_id = ?, ingrediente_quantity = ?, ingrediente_price = ?
            WHERE burguer_uuid = ?
        """.trimIndent()

        val copyLineaBurguer= mutableListOf<LineaBurguer>()

        newEntity.lineaBurguer.forEach { linea ->
            db().use {
                it.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS).use { stm ->
                    // Pasamos los parámetros para introducir
                    stm.setString(1, newEntity.uuid.toString())
                    stm.setLong(2, linea.ingredienteId)
                    stm.setInt(3, linea.ingredienteQuantity)
                    stm.setDouble(4, linea.ingredientePrice)
                    stm.executeUpdate()

                    copyLineaBurguer.add(linea.copy())
                }
            }
        }

        return newEntity.copy(lineaBurguer = copyLineaBurguer)
    }

    // Si queremos borrar uno de los registros, borramos primero las Lineas
    override fun deleteRecord(entityToDelete: Burguer): Boolean {
        logger.debug { "DataManager: Borrando ${entityToDelete::class.simpleName}" }

        // Para verificar, si el statement me realiza la actualización
        var res = 0

        // Primero borramos las Lineas
        val sql1 = """
            DELETE FROM Linea${entityToDelete::class.simpleName}Table
            WHERE burguer_uuid = ?
        """.trimIndent()

        db().use {
            it.prepareStatement(sql1).use { stm ->
                // Pasamos los parámetros para introducir
                stm.setString(1, entityToDelete.uuid.toString())
                res = stm.executeUpdate()
            }
        }

        val sql = """
            DELETE FROM ${entityToDelete::class.simpleName}Table
            WHERE uuid = ?
        """.trimIndent()

        db().use {
            it.prepareStatement(sql).use { stm ->
                // Pasamos los parámetros para introducir
                stm.setString(1, entityToDelete.uuid.toString())
                res += stm.executeUpdate()
            }
        }

        return res == 2
    }

    // Si queremos borrar todos los registros, borramos primero las Lineas
    override fun deleteAllRecord(): Boolean {
        logger.debug { "DataManager: Borrado completo de registros" }

        var res = 0

        val sql1 = "DELETE FROM LineaBurguerTable"
        db().use {
            it.prepareStatement(sql1).use { stm ->
                res = stm.executeUpdate()
            }
        }

        val sql = "DELETE FROM BurguerTable "
        db().use {
            it.prepareStatement(sql).use { stm ->
                res += stm.executeUpdate()
            }
        }

        return res == 2
    }
}