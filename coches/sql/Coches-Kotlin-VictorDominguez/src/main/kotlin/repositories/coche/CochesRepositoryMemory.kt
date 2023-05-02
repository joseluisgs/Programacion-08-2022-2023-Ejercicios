package repositories.coche

import enums.TipoMotor
import models.Coche
import mu.KotlinLogging
import services.storage.database.DataBaseManager
import java.sql.Statement
import java.util.*

private val logger = KotlinLogging.logger{}

class CochesRepositoryMemory(
    val database: DataBaseManager = DataBaseManager
): CochesRepository {

    override fun exportar(): List<Coche> {
        logger.debug { "CochesRepository -> Exportando datos de la base de datos" }
        return buscarTodos()
    }

    override fun importar(items: List<Coche>) {
        logger.debug { "CochesRepository -> Importando datos a la base de datos" }
        items.forEach { guardar(it) }
    }

    override fun buscarTodos(): List<Coche> {
        logger.debug { "CochesRepository -> Buscar todos" }

        val coches = mutableListOf<Coche>()
        val sqlScript = "SELECT * FROM TCoches"

        database.connection.prepareStatement(sqlScript).use {stm ->
            val result = stm.executeQuery()

            while (result.next()) {
                coches.add(
                    Coche(
                        result.getLong("id"),
                        UUID.fromString(result.getString("idCond")),
                        result.getString("marca"),
                        result.getString("modelo"),
                        result.getDouble("precio"),
                        TipoMotor.valueOf(result.getString("motor"))
                    )
                )
            }
        }
        return coches
    }

    override fun salvar(item: Coche) {
        logger.debug { "CochesRepository -> Salvar coche: $item" }
        if (buscarPorId(item.id) == null) {
            guardar(item)
        } else actualizar(item)
    }

    private fun actualizar(item: Coche): Coche {
        logger.debug { "CochesRepository -> Actualizar coche $item" }

        val sqlScript = "UPDATE TCoches SET idCond = ?, marca = ?, modelo = ?, precio = ?, motor = ? WHERE id = ?"

        database.connection.prepareStatement(sqlScript).use { stm ->
            stm.setString(1, item.idCond.toString())
            stm.setString(2, item.marca)
            stm.setString(3, item.modelo)
            stm.setDouble(4, item.precio)
            stm.setString(5, item.tipoMotor.toString())
            stm.setLong(6, item.id)

            stm.executeUpdate()
        }
        return item
    }

    private fun guardar(item: Coche): Coche {
        logger.debug { "CochesRepository -> Guardar coche: $item" }

        var nId = 0L
        val sqlScript = "INSERT INTO TCoches VALUES (null, ?, ?, ?, ?, ?)"

        database.connection.prepareStatement(sqlScript, Statement.RETURN_GENERATED_KEYS).use { stm ->
            stm.setString(1, item.idCond.toString())
            stm.setString(2, item.marca)
            stm.setString(3, item.modelo)
            stm.setDouble(4, item.precio)
            stm.setString(5, item.tipoMotor.toString())
            stm.executeUpdate()

            val result = stm.generatedKeys
            if (result.next()) { nId = result.getLong(1) }
        }
        return item.copy(id = nId)
    }

    override fun eliminarPorId(id: Long): Coche? {
        logger.debug { "CochesRepository -> Eliminar por ID: $id" }

        val sqlScript = "DELETE FROM TCoches WHERE id = ?"
        val coche = buscarPorId(id) ?: return null

        database.connection.prepareStatement(sqlScript).use { stm ->
            stm.setLong(1, id)
            if (stm.executeUpdate() == 1) { return coche }
        }
        return null
    }

    override fun buscarPorId(id: Long): Coche? {
        logger.debug { "CochesRepository -> Buscar por ID: $id" }

        val sqlScript = "SELECT * FROM TCoches WHERE id = ?"

        database.connection.prepareStatement(sqlScript).use { stm ->
            stm.setLong(1, id)
            val result = stm.executeQuery()
            if (result.next()) {
                return Coche(
                    result.getLong("id"),
                    UUID.fromString(result.getString("idCond")),
                    result.getString("marca"),
                    result.getString("modelo"),
                    result.getDouble("precio"),
                    TipoMotor.valueOf(result.getString("motor"))
                )
            }
        }
        return null
    }

    override fun eliminarTodos() {
        logger.debug { "CochesRepository -> Eliminar todos" }

        val sqlScript = "DELETE FROM TCoches"

        database.connection.prepareStatement(sqlScript).executeUpdate()
    }
}