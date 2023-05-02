package repositories.conductor

import models.Coche
import models.Conductor
import mu.KotlinLogging
import services.storage.database.DataBaseManager
import java.time.LocalDate
import java.util.*

private val logger = KotlinLogging.logger{}

class ConductoresRepositoryMemory(
    val database: DataBaseManager = DataBaseManager
): ConductoresRepository {

    override fun exportar(): List<Conductor> {
        logger.debug { "ConductoresRepository -> Exportando datos de la base de datos" }
        return buscarTodos()
    }

    override fun importar(items: List<Conductor>) {
        logger.debug { "ConductoresRepository -> Importando datos a la base de datos" }
        items.forEach { guardar(it) }
    }

    override fun buscarTodos(): List<Conductor> {
        logger.debug { "ConductoresRepository -> Buscar todos" }

        val conductores = mutableListOf<Conductor>()
        val sqlScript = "SELECT * FROM TConductores"

        database.connection.prepareStatement(sqlScript).use {stm ->
            val result = stm.executeQuery()

            while (result.next()) {
                conductores.add(
                    Conductor(
                        UUID.fromString(result.getString("id")),
                        result.getString("nombre"),
                        LocalDate.parse(result.getString("fechaCarnet")),
                        emptyList<Coche>().toMutableList()
                    )
                )
            }
        }
        return conductores
    }

    override fun buscarPorId(id: UUID): Conductor? {
        logger.debug { "ConductoresRepository -> Buscar por ID: $id" }

        val sqlScript = "SELECT * FROM TConductores WHERE id = ?"

        database.connection.prepareStatement(sqlScript).use { stm ->
            stm.setString(1, id.toString())
            val result = stm.executeQuery()
            if (result.next()) {
                return Conductor(
                    UUID.fromString(result.getString("id")),
                    result.getString("nombre"),
                    LocalDate.parse(result.getString("fechaCarnet")),
                    emptyList<Coche>().toMutableList()
                )
            }
        }
        return null
    }

    override fun eliminarTodos() {
        logger.debug { "ConductoresRepository -> Eliminar todos" }

        val sqlScript = "DELETE FROM TConductores"

        database.connection.prepareStatement(sqlScript).executeUpdate()
    }

    override fun eliminarPorId(id: UUID): Conductor? {
        logger.debug { "ConductoresRepository -> Eliminar por ID: $id" }

        val sqlScript = "DELETE FROM TConductores WHERE id = ?"
        val conductor = buscarPorId(id) ?: return null

        database.connection.prepareStatement(sqlScript).use { stm ->
            stm.setString(1, id.toString())
            if (stm.executeUpdate() == 1) { return conductor }
        }
        return null
    }

    override fun salvar(item: Conductor) {
        logger.debug { "ConductoresRepository -> Salvar conductor: $item" }
        if (buscarPorId(item.id) == null) {
            guardar(item)
        } else actualizar(item)
    }

    private fun guardar(item: Conductor): Conductor? {
        logger.debug { "ConductoresRepository -> Guardar conductor: $item" }

        var nId = 0L
        val sqlScript = "INSERT INTO TConductores VALUES (?, ?, ?)"

        database.connection.prepareStatement(sqlScript).use { stm ->
            stm.setString(1, item.id.toString())
            stm.setString(2, item.nombre)
            stm.setString(3, item.fechaCarnet.toString())
            stm.executeUpdate()
        }

        return item
    }

    private fun actualizar(item: Conductor): Conductor? {
        logger.debug { "ConductoresRepository -> Actualizar conductor: $item" }

        val sqlScript = "UPDATE TConductores SET nombre = ?, fechaCarnet = ? WHERE id = ?"

        database.connection.prepareStatement(sqlScript).use { stm ->
            stm.setString(1, item.nombre)
            stm.setString(2, item.fechaCarnet.toString())
            stm.setString(3, item.id.toString())
            stm.executeUpdate()
        }
        return item
    }
}