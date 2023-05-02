package repositories.ingrediente

import models.Ingrediente
import mu.KotlinLogging
import services.storage.database.DataBaseManager
import java.sql.Statement

private val logger = KotlinLogging.logger{}

class IngredienteRepositoryMemory(
    private val database: DataBaseManager = DataBaseManager
): IngredienteRepository {

    override fun exportar(): List<Ingrediente> {
        logger.debug { "IngredienteRepository -> Exportando datos de la base de datos" }
        return buscarTodos()
    }

    override fun importar(items: List<Ingrediente>) {
        logger.debug { "IngredienteRepository -> Importando datos a la base de datos" }
        items.forEach { salvar(it) }
    }

    override fun buscarTodos(): List<Ingrediente> {
        logger.debug { "IngredienteRepository -> Buscar todos" }

        val ingredientes = mutableListOf<Ingrediente>()
        val sql = "SELECT * FROM TIngredientes"

        database.connection.prepareStatement(sql).use { stm ->
            val result = stm.executeQuery()

            while (result.next()) {
                ingredientes.add(
                    Ingrediente(
                        result.getLong("id"),
                        result.getString("nombre"),
                        result.getDouble("precio")
                    )
                )
            }
        }
        return ingredientes
    }

    override fun buscarPorId(id: Long): Ingrediente? {
        logger.debug { "IngredienteRepository -> Buscar por ID: $id" }

        val sql = "SELECT * FROM TIngredientes WHERE id = ?"

        database.connection.prepareStatement(sql).use { stm ->
            stm.setLong(1, id)
            val result = stm.executeQuery()
            if (result.next()) {
                return Ingrediente(
                    result.getLong("id"),
                    result.getString("nombre"),
                    result.getDouble("precio")
                )
            }
        }
        return null
    }

    override fun eliminarTodos() {
        logger.debug { "IngredienteRepository -> Eliminar todos" }
        val sql = "DELETE FROM TIngredientes"
        database.connection.prepareStatement(sql).executeUpdate()
    }

    override fun eliminarPorId(id: Long): Ingrediente? {
        logger.debug { "IngredienteRepository -> Eliminar por ID: $id" }

        val sql = "DELETE FROM TIngredientes WHERE id = ?"
        val ingrediente = buscarPorId(id) ?: return null

        database.connection.prepareStatement(sql).use { stm ->
            stm.setLong(1, id)
            if (stm.executeUpdate() == 1) { return ingrediente }
        }
        return null
    }

    override fun salvar(item: Ingrediente): Ingrediente {
        logger.debug { "IngredienteRepository -> Salvar ingrediente: $item" }
        return if (buscarPorId(item.id) == null) {
            guardar(item)
        } else actualizar(item)
    }

    private fun guardar(item: Ingrediente): Ingrediente {
        logger.debug { "IngredienteRepository -> Guardar ingrediente: $item" }

        var nId = 0L
        val sql = "INSERT INTO TIngredientes VALUES (null, ?, ?)"

        database.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stm ->
            stm.setString(1, item.nombre)
            stm.setDouble(2, item.precio)
            stm.executeUpdate()

            val result = stm.generatedKeys
            if (result.next()) { nId = result.getLong(1) }
        }
        return item.copy(id = nId)
    }

    private fun actualizar(item: Ingrediente): Ingrediente {
        logger.debug { "IngredienteRepository -> Actualizar ingrediente: $item" }

        val sql = "UPDATE TIngredientes SET nombre = ?, precio = ? WHERE id = ?"

        database.connection.prepareStatement(sql).use { stm ->
            stm.setString(1, item.nombre)
            stm.setDouble(2, item.precio)
            stm.setLong(3, item.id)

            stm.executeUpdate()
        }
        return item
    }

}