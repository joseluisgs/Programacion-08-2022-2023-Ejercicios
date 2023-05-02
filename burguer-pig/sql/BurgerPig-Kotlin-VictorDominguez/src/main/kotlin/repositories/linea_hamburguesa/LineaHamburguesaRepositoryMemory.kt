package repositories.linea_hamburguesa

import models.Ingrediente
import models.LineaHamburguesa
import mu.KotlinLogging
import services.storage.database.DataBaseManager
import java.sql.Statement
import java.util.*
import javax.sound.sampled.Line

private val logger = KotlinLogging.logger{}

class LineaHamburguesaRepositoryMemory(
    private val database: DataBaseManager = DataBaseManager
): LineaHamburguesaRepository {

    override fun exportar(): List<LineaHamburguesa> {
        logger.debug { "LineaHamburguesaRepository -> Exportar datos de la base de datos" }
        return buscarTodos()
    }

    override fun importar(items: List<LineaHamburguesa>) {
        logger.debug { "LineaHamburguesaRepository -> Importar datos a la base de datos" }
        items.forEach { guardar(it) }
    }

    override fun buscarTodos(): List<LineaHamburguesa> {
        logger.debug { "LineaHamburguesaRepository -> Buscar todos" }

        val lineas = mutableListOf<LineaHamburguesa>()
        val sql = "SELECT * FROM TLinea_Hamburguesa"

        database.connection.prepareStatement(sql).use { stm ->
            val result = stm.executeQuery()

            while (result.next()) {
                lineas.add(
                    LineaHamburguesa(
                        result.getLong("id"),
                        UUID.fromString(result.getString("id_ham")),
                        result.getLong("id_ing"),
                        result.getInt("cantidad")
                    )
                )
            }
        }
        return lineas
    }

    override fun buscarPorId(id: Long): LineaHamburguesa? {
        logger.debug { "LineaHamburguesaRepository -> Buscar por ID: $id" }

        val sql = "SELECT * FROM TLinea_Hamburguesa WHERE id = ?"

        database.connection.prepareStatement(sql).use { stm ->
            stm.setLong(1, id)
            val result = stm.executeQuery()
            if (result.next()) {
                return LineaHamburguesa(
                    result.getLong("id"),
                    UUID.fromString(result.getString("id_ham")),
                    result.getLong("id_ing"),
                    result.getInt("cantidad")
                )
            }
        }
        return null
    }

    override fun eliminarTodos() {
        logger.debug { "LineaHamburguesaRepository -> Eliminar todos" }
        val sql = "DELETE FROM TLinea_Hamburguesa"
        database.connection.prepareStatement(sql).executeUpdate()
    }

    override fun eliminarPorId(id: Long): LineaHamburguesa? {
        logger.debug { "LineaHamburguesaRepository -> Eliminar por ID: $id" }

        val sql = "DELETE FROM TLinea_Hamburguesa WHERE id = ?"
        val linea = buscarPorId(id) ?: return null

        database.connection.prepareStatement(sql).use { stm ->
            stm.setLong(1, id)
            if (stm.executeUpdate() == 1) { return linea }
        }
        return null
    }

    override fun salvar(item: LineaHamburguesa): LineaHamburguesa {
        logger.debug { "LineaHamburguesaRepository -> Salvar linea: $item" }
        return if (buscarPorId(item.id) == null) {
            guardar(item)
        } else actualizar(item)
    }

    private fun guardar(item: LineaHamburguesa): LineaHamburguesa {
        logger.debug { "LineaHamburguesaRepository -> Guardar linea: $item" }

        var nId = 0L
        val sql = "INSERT INTO TLinea_Hamburguesa VALUES (null, ? , ?, ?)"

        database.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stm ->
            stm.setString(1, item.id_ham.toString())
            stm.setLong(2, item.id_ing)
            stm.setInt(3, item.cantidad)
            stm.executeUpdate()

            val result = stm.generatedKeys
            if (result.next()) { nId = result.getLong(1) }
        }
        return item.copy(id = nId)
    }

    private fun actualizar(item: LineaHamburguesa): LineaHamburguesa {
        logger.debug { "LineaHamburguesaRepository -> Actualizar linea: $item" }

        val sql = "UPDATE TLinea_Hamburguesa SET id_ham = ?, id_ing = ?, cantidad = ? WHERE id = ?"

        database.connection.prepareStatement(sql).use { stm ->
            stm.setString(1, item.id_ham.toString())
            stm.setLong(2, item.id_ing)
            stm.setInt(3, item.cantidad)
            stm.setLong(4, item.id)

            stm.executeUpdate()
        }
        return item
    }

}