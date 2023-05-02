package repositories.hamburguesa

import models.Hamburguesa
import models.Ingrediente
import mu.KotlinLogging
import services.storage.database.DataBaseManager
import java.util.*

private val logger = KotlinLogging.logger{}

class HamburguesaRepositoryMemory(
    private val database: DataBaseManager = DataBaseManager
): HamburguesaRepository {

    override fun exportar(): List<Hamburguesa> {
        logger.debug { "HamburguesaRepository -> Exportando datos de la base de datos" }
        return buscarTodos()
    }

    override fun importar(items: List<Hamburguesa>) {
        logger.debug { "HamburguesaRepository -> Importando datos a la base de datos" }
        items.forEach { salvar(it) }
    }

    override fun buscarTodos(): List<Hamburguesa> {
        logger.debug { "HamburguesaRepository -> Buscar todos" }

        val hamburguesas = mutableListOf<Hamburguesa>()
        val sql = "SELECT * FROM THamburguesas"

        database.connection.prepareStatement(sql).use { stm ->
            val result = stm.executeQuery()

            while (result.next()) {
                hamburguesas.add(
                    Hamburguesa(
                        UUID.fromString(result.getString("id")),
                        result.getString("nombre"),
                        emptyList<Ingrediente>().toMutableList(),
                        result.getDouble("precio")
                    )
                )
            }
        }
        return hamburguesas
    }

    override fun buscarPorId(id: UUID): Hamburguesa? {
        logger.debug { "HamburguesaRepository -> Buscar por ID: $id" }

        val sql = "SELECT * FROM THamburguesas WHERE id = ?"

        database.connection.prepareStatement(sql).use { stm ->
            stm.setString(1, id.toString())
            val result = stm.executeQuery()
            if (result.next()) {
                return Hamburguesa(
                    UUID.fromString(result.getString("id")),
                    result.getString("nombre"),
                    emptyList<Ingrediente>().toMutableList(),
                    result.getDouble("precio")
                )
            }
        }
        return null
    }

    override fun eliminarTodos() {
        logger.debug { "HamburguesaRepository -> Eliminar todos" }

        val sql = "DELETE FROM THamburguesas"

        database.connection.prepareStatement(sql).executeUpdate()
    }

    override fun eliminarPorId(id: UUID): Hamburguesa? {
        logger.debug { "HamburguesaRepository -> Eliminar por ID: $id" }

        val sql = "DELETE FROM THamburguesas WHERE id = ?"
        val coche = buscarPorId(id) ?: return null

        database.connection.prepareStatement(sql).use { stm ->
            stm.setString(1, id.toString())
            if (stm.executeUpdate() == 1) { return coche }
        }
        return null
    }

    override fun salvar(item: Hamburguesa): Hamburguesa {
        logger.debug { "HamburguesaRepository -> Salvar hamburguesa: $item" }
        return if (buscarPorId(item.id) == null) {
            guardar(item)
        } else actualizar(item)
    }

    private fun guardar(item: Hamburguesa): Hamburguesa {
        logger.debug { "HamburguesaRepository -> Guardar hamburguesa: $item" }

        val sql = "INSERT INTO THamburguesas VALUES (?, ?, ?)"

        database.connection.prepareStatement(sql).use { stm ->
            stm.setString(1, item.id.toString())
            stm.setString(2, item.nombre)
            stm.setDouble(3, item.precio)
            stm.executeUpdate()
        }
        return item
    }

    private fun actualizar(item: Hamburguesa): Hamburguesa {
        logger.debug { "HamburguesaRepository -> Actualizar hamburguesa: $item" }

        val sql = "UPDATE THamburguesas SET nombre = ?, precio = ? WHERE id = ?"

        database.connection.prepareStatement(sql).use { stm ->
            stm.setString(1, item.nombre)
            stm.setDouble(2, item.precio)
            stm.setString(3, item.id.toString())
            stm.executeUpdate()
        }
        return item
    }
}