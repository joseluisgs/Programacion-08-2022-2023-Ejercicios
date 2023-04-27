package repositories.profesor

import models.Modulo
import models.Profesor
import mu.KotlinLogging
import repositories.modulo.ModuloRepositoryDataBase
import services.database.DataBaseManager
import java.lang.RuntimeException
import java.sql.Statement
import java.time.LocalDate
import java.util.UUID

private val logger = KotlinLogging.logger {  }

object ProfesorRepositoryDataBase: ProfesorRepository {

    override fun findAll(): Iterable<Profesor> {
        logger.debug { "ProfesorRepositoryDataBase ->\tfindAll" }
        val profesores = mutableListOf<Profesor>()

        val sql = """SELECT * FROM tProfesor"""
        DataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            val result = stm.executeQuery()
            while (result.next()){
                profesores.add(
                    Profesor(
                        result.getLong("nIdProfesor"),
                        result.getString("cNombre"),
                        LocalDate.parse(result.getString("dIncorporacion")),
                        getModulos(result.getLong("nIdProfesor"))
                    )
                )
            }
        }

        return profesores.toList()
    }

    // Esto no haría falta con un inner join :(
    private fun getModulos(idProfesor: Long): List<Modulo> {
        logger.debug { "ProfesorRepositoryDataBase ->\tgetModulos" }
        val modulos = mutableListOf<Modulo>()
        val idModulos = mutableListOf<String>()

        val tablaIntermedia = """SELECT cUUID FROM tProfesor_Modulo WHERE nIdProfesor = ?"""
        DataBaseManager.dataBase.prepareStatement(tablaIntermedia).use { stm ->
            stm.setLong(1, idProfesor)
            val result = stm.executeQuery()
            while (result.next()){
                idModulos.add(
                    result.getString("cUUID")
                )
            }
        }

        //Puede hacer la consulta o usar el repo de modulo...
        idModulos.map { UUID.fromString(it) }.forEach {
            modulos.add(ModuloRepositoryDataBase.findById(it) ?: throw RuntimeException("Error al buscar un modulo"))
        }
        /*val sql = """SELECT * FROM tModulo WHERE cUUID = ?"""
        DataBaseManager.dataBase.use {
            idModulos.forEach {  idModulo ->
                it.prepareStatement(sql).use { stm ->
                    stm.setString(1, idModulo)
                    val result = stm.executeQuery()
                    while (result.next()){
                        modulos.add(
                            Modulo(
                                UUID.fromString(result.getString("cUUID")),
                                result.getString("cNombre"),
                                result.getInt("nCurso"),
                                Grado.valueOf(result.getString("cGrado"))
                            )
                        )
                    }
                }
            }
        }*/

        return modulos.toList()
    }

    override fun findById(id: Long): Profesor? {
        logger.debug { "ProfesorRepositoryDataBase ->\tfindById" }
        var profesor: Profesor? = null
        val sql = """SELECT * FROM tProfesor WHERE nIdProfesor = ?"""
        DataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setLong(1, id)
            val result = stm.executeQuery()
            while (result.next()){
                profesor = Profesor(
                    result.getLong("nIdProfesor"),
                    result.getString("cNombre"),
                    LocalDate.parse(result.getString("dIncorporacion")),
                    getModulos(result.getLong("nIdProfesor"))
                )
            }
        }
        return profesor
    }

    override fun save(element: Profesor): Profesor {
        logger.debug { "ProfesorRepositoryDataBase ->\tsave" }
        return if (existsById(element.id)){
            update(element)
        }else{
            create(element)
        }
    }

    private fun update(element: Profesor): Profesor {
        logger.debug { "ProfesorRepositoryDataBase ->\tupdate" }
        val sql = """UPDATE tProfesor SET cNombre = ?, dIncorporacion = ? WHERE nIdProfesor = ?"""

        val deleteIntermedia = """DELETE FROM tProfesor_Modulo WHERE nIdProfesor = ?"""
        val insertIntermedia = """INSERT INTO tProfesor_Modulo (nIdProfesor, cUUID) VALUES (?, ?)"""

        DataBaseManager.dataBase.use {
            it.prepareStatement(sql).use { stm ->
                stm.setString(1, element.nombre)
                stm.setString(2, element.fechaIncorporacion.toString())
                stm.setLong(3, element.id)
                stm.executeUpdate()
            }
            it.prepareStatement(deleteIntermedia).use { stm ->
                stm.setLong(1, element.id)
                stm.executeUpdate()
            }
            element.modulos.forEach { modulo ->
                it.prepareStatement(insertIntermedia).use { stm ->
                    stm.setLong(1, element.id)
                    stm.setString(2, modulo.uuid.toString())
                    stm.executeUpdate()
                }
            }
        }
        return element
    }

    private fun create(element: Profesor): Profesor {
        logger.debug { "ProfesorRepositoryDataBase ->\tcreate" }
        val sql = """INSERT INTO tProfesor (cNombre, dIncorporacion) VALUES (?, ?)"""
        val insertIntermedia = """INSERT INTO tProfesor_Modulo (nIdProfesor, cUUID) VALUES (?, ?)"""
        var newId: Long = -1

        element.modulos.forEach {
            ModuloRepositoryDataBase.save(it) // Si no existe lo crea
        }

        DataBaseManager.dataBase.use {
            it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stm ->
                stm.setString(1, element.nombre)
                stm.setString(2, element.fechaIncorporacion.toString())
                stm.executeUpdate()
                val result = stm.generatedKeys
                if (result.next()){
                    newId = result.getLong(1)
                }
            }
            element.modulos.forEach { modulo ->
                it.prepareStatement(insertIntermedia).use { stm ->
                    stm.setLong(1, newId)
                    stm.setString(2, modulo.uuid.toString())
                    stm.executeUpdate()
                }
            }
        }

        return element.copy(id = newId)
    }

    override fun saveAll(elements: Iterable<Profesor>) {
        logger.debug { "ProfesorRepositoryDataBase ->\tsaveAll" }
        elements.forEach { save(it) }
    }

    override fun deleteById(id: Long): Boolean {
        logger.debug { "ProfesorRepositoryDataBase ->\tdeleteById" }
        var result: Int
        val deleteIntermedia = """DELETE FROM tProfesor_Modulo WHERE nIdProfesor = ?"""
        val sql = """DELETE FROM tProfesor WHERE nIdProfesor = ?"""
        DataBaseManager.dataBase.use {
            it.prepareStatement(deleteIntermedia).use { stm ->
                stm.setLong(1, id)
                stm.executeUpdate()
            }
            it.prepareStatement(sql).use { stm ->
                stm.setLong(1, id)
                result = stm.executeUpdate()
            }
        }
        return result == 1
    }

    override fun delete(element: Profesor): Boolean {
        logger.debug { "ProfesorRepositoryDataBase ->\tdelete" }
        return deleteById(element.id)
    }

    override fun deleteAll() {
        logger.debug { "ProfesorRepositoryDataBase ->\tdeleteAll" }
        val deleteIntermedia = """DELETE FROM tProfesor_Modulo"""
        val sql = """DELETE FROM tProfesor"""
        DataBaseManager.dataBase.use {
            it.prepareStatement(deleteIntermedia).use { stm ->
                stm.executeUpdate()
            }
            it.prepareStatement(sql).use { stm ->
                stm.executeUpdate()
            }
        }
    }

    override fun existsById(id: Long): Boolean {
        logger.debug { "ProfesorRepositoryDataBase ->\texistsById" }
        return findById(id) != null
    }
}