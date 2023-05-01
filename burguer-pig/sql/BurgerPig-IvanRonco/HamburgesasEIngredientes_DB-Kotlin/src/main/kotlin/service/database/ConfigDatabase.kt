package service.database

import config.ConfigApp
import model.Hamburgesa
import model.Ingrediente
import mu.KotlinLogging
import org.koin.core.annotation.Single
import java.io.FileInputStream
import java.sql.DriverManager
import java.sql.Statement
import java.util.*

@Single
class ConfigDatabase {

    private val logger = KotlinLogging.logger {  }

    private val properties = Properties()

    val APP_INIT_DATABASE by lazy {
        properties.getProperty("APP_INIT_DATABASE") == "true"
    }
    val APP_URL by lazy {
        properties.getProperty("APP_URL") ?: "jdbc:h2:~/HamburgesasEIngredientes_DB-Kotlin/database.db"
    }

    val connection get() = DriverManager.getConnection(APP_URL)

    private val initDatabase = APP_INIT_DATABASE

    init {
        initProperties()
    }

    fun initProperties(){
        logger.debug { "Cargamos las properties relacionadas con la DB" }
        properties.load(FileInputStream(ClassLoader.getSystemResource("config.properties").file))
    }

    fun initDatabase(){
        logger.debug { "Creamos la database y sus tablas" }

        if(initDatabase){
            val sqlHamburgesa = """DROP TABLE IF EXISTS HAMBURGESAS;"""

            val sqlIngrediente = """DROP TABLE IF EXISTS INGREDIENTES;"""

            val sqlHamburgesaIngrediente = """DROP TABLE IF EXISTS HAMBURGESAS_INGREDIENTES;"""

            connection.use {
                it.prepareStatement(sqlHamburgesaIngrediente).executeUpdate()

                it.prepareStatement(sqlHamburgesa).executeUpdate()

                it.prepareStatement(sqlIngrediente).executeUpdate()
            }
        }

        val sqlHamburgesa = """
            CREATE TABLE IF NOT EXISTS HAMBURGESAS (
                UUID VARCHAR(40) PRIMARY KEY,
                NOMBRE VARCHAR(40) NOT NULL,
                PRECIO DECIMAL(6,2) NOT NULL
            );
        """.trimIndent()

        val sqlIngrediente = """
            CREATE TABLE IF NOT EXISTS INGREDIENTES (
                ID INTEGER PRIMARY KEY AUTO_INCREMENT,
                NOMBRE VARCHAR(40) NOT NULL,
                PRECIO DECIMAL(6,2) NOT NULL
            );
        """.trimIndent()

        val sqlHamburgesaIngrediente = """
            CREATE TABLE IF NOT EXISTS HAMBURGESAS_INGREDIENTES (
                ID INTEGER NOT NULL REFERENCES INGREDIENTES(ID),
                UUID VARCHAR(40) NOT NULL REFERENCES HAMBURGESAS(UUID),
                PRIMARY KEY (ID, UUID)
            );
        """.trimIndent()

        connection.use {
            it.prepareStatement(sqlHamburgesa).executeUpdate()

            it.prepareStatement(sqlIngrediente).executeUpdate()

            it.prepareStatement(sqlHamburgesaIngrediente).executeUpdate()
        }

    }

    fun getAllHamburgesas(): List<Hamburgesa>{
        logger.debug { "Consigo todas las hamburgesas" }
        val sql = """SELECT * FROM HAMBURGESAS;"""

        val hamburgesas = mutableListOf<Hamburgesa>()
        connection.use {
            it.prepareStatement(sql).use { stm ->
                val result = stm.executeQuery()

                while(result.next()){
                    val id = UUID.fromString(result.getString("UUID"))
                    hamburgesas.add(
                        Hamburgesa(
                            id = id,
                            nombre = result.getString("NOMBRE"),
                            ingredientes = mutableListOf()
                        )
                    )
                }
            }
        }
        return hamburgesas
    }

    fun getAllIngredientesIdByHamburgesaId(id: UUID): List<Long> {
        logger.debug { "Recupero todos los id de ingredientes de la hamburgesa con id: $id" }

        val ingredientes = mutableListOf<Long>()

        val sql = """SELECT ID FROM HAMBURGESAS_INGREDIENTES WHERE UUID = ?;"""
        connection.use {
            it.prepareStatement(sql).use { stm ->
                stm.setString(1, id.toString())

                val result = stm.executeQuery()

                while (result.next()){
                    ingredientes.add(
                        result.getLong(1)
                    )
                }
            }
        }
        return ingredientes
    }

    fun createHamburgesa(hamburgesa: Hamburgesa): Hamburgesa{
        logger.debug { "Se almacena una nueva hamburgesa con id: ${hamburgesa.id}" }

        val sql = """INSERT INTO HAMBURGESAS VALUES (?, ?, ?);"""

        connection.use {
            it.prepareStatement(sql).use { stm ->
                stm.setString(1, hamburgesa.id.toString())
                stm.setString(2, hamburgesa.nombre)
                stm.setDouble(3, hamburgesa.precio)

                stm.executeUpdate()

                val sqlInsertEnTablaConjunta = """INSERT INTO HAMBURGESAS_INGREDIENTES VALUES (?, ?);"""
                hamburgesa.ingredientes.map { it.id }.forEach { id ->
                    it.prepareStatement(sqlInsertEnTablaConjunta).use { stm ->
                        stm.setLong(1, id)
                        stm.setString(2, hamburgesa.id.toString())

                        stm.executeUpdate()
                    }
                }
            }
        }
        return hamburgesa
    }

    fun updateHamburgesa(hamburgesa: Hamburgesa): Hamburgesa{
        logger.debug { "Se actualiza la hamburgesa con id: ${hamburgesa.id}" }

        val sql = """UPDATE HAMBURGESAS SET NOMBRE = ?, PRECIO = ? WHERE UUID = ?;"""

        val sqlDestroyAlreadyExistingRelations = """DELETE FROM HAMBURGESAS_INGREDIENTES WHERE UUID = ?;"""

        connection.use {
            it.prepareStatement(sqlDestroyAlreadyExistingRelations).use { stm ->
                stm.setString(1, hamburgesa.id.toString())

                stm.executeUpdate()
            }

            it.prepareStatement(sql).use { stm ->
                stm.setString(1, hamburgesa.nombre)
                stm.setDouble(2, hamburgesa.precio)
                stm.setString(3, hamburgesa.id.toString())

                stm.executeUpdate()
            }

            val sqlInsertEnTablaConjunta = """INSERT INTO HAMBURGESAS_INGREDIENTES VALUES (?, ?);"""
            hamburgesa.ingredientes.map { it.id }.forEach { id ->
                it.prepareStatement(sqlInsertEnTablaConjunta).use { stm ->
                    stm.setLong(1, id)
                    stm.setString(2, hamburgesa.id.toString())

                    stm.executeUpdate()
                }
            }
        }
        return hamburgesa
    }

    fun deleteHamburgesasById(id: UUID): Boolean{
        logger.debug { "Elimino la hamburgesa con id: $id" }
        val sql = """DELETE FROM HAMBURGESAS WHERE UUID = ?;"""

        val sqlDestroyAlreadyExistingRelations = """DELETE FROM HAMBURGESAS_INGREDIENTES WHERE UUID = ?;"""

        var result = 0
        connection.use {
            it.prepareStatement(sqlDestroyAlreadyExistingRelations).use { stm ->
                stm.setString(1, id.toString())
            }

            it.prepareStatement(sql).use { stm ->
                stm.setString(1, id.toString())

                result = stm.executeUpdate()
            }
        }

        return result > 0
    }

    fun deleteAllHamburgesas(): Boolean {
        logger.debug { "Elimino todas las hamburgesas" }
        val sql = """DELETE FROM HAMBURGESAS;"""

        val sqlDestroyAlreadyExistingRelations = """DELETE FROM HAMBURGESAS_INGREDIENTES;"""

        var result = 0
        connection.use {
            it.prepareStatement(sqlDestroyAlreadyExistingRelations).executeUpdate()

            result = it.prepareStatement(sql).executeUpdate()
        }

        return result > 0
    }

    fun getAllIngredientes(): List<Ingrediente> {
        logger.debug { "Consigo todos los ingredientes" }

        val sql = """SELECT * FROM INGREDIENTES;"""

        var ingredientes = mutableListOf<Ingrediente>()
        connection.use {
            it.prepareStatement(sql).use { stm ->

                val result = stm.executeQuery()

                while (result.next()){
                    ingredientes.add(
                        Ingrediente(
                            id = result.getLong("ID"),
                            nombre = result.getString("NOMBRE"),
                            precio = result.getDouble("PRECIO")
                        )
                    )
                }
            }
        }
        return ingredientes
    }

    fun getIngredienteById(id: Long): Ingrediente?{
        logger.debug { "Busco al ingrediente con id: $id" }

        val sql = """SELECT * FROM INGREDIENTES WHERE ID = ?;"""

        var ingrediente: Ingrediente? = null
        connection.use {
            it.prepareStatement(sql).use { stm ->
                stm.setLong(1, id)

                val result = stm.executeQuery()

                if (result.next()){
                    ingrediente = Ingrediente(
                        id = result.getLong("ID"),
                        nombre = result.getString("NOMBRE"),
                        precio = result.getDouble("PRECIO")
                    )
                }
            }
        }
        return ingrediente
    }

    fun createIngrediente(ingrediente: Ingrediente): Ingrediente{
        logger.debug { "Introduzco un nuevo ingrediente en la BD" }

        val sql = """INSERT INTO INGREDIENTES (NOMBRE, PRECIO) VALUES (?, ?);"""

        var id = 0L
        connection.use {
            it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stm ->
                stm.setString(1, ingrediente.nombre)
                stm.setDouble(2, ingrediente.precio)

                stm.executeUpdate()

                val result = stm.generatedKeys
                if(result.next()){
                    id = result.getLong(1)
                }
            }
        }

        return ingrediente.copy(id = id)
    }

    fun updateIngrediente(ingrediente: Ingrediente): Ingrediente{
        logger.debug { "Actualizo un ingrediente de la BD" }

        val sql = """UPDATE INGREDIENTES SET NOMBRE = ?, PRECIO = ? WHERE ID = ?;"""

        connection.use {
            it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stm ->
                stm.setString(1, ingrediente.nombre)
                stm.setDouble(2, ingrediente.precio)
                stm.setLong(3, ingrediente.id)

                stm.executeUpdate()
            }
        }

        return ingrediente
    }

    fun deleteIngredienteById(id: Long): Boolean {
        logger.debug { "Elimino todos los ingredientes" }
        val sql = """DELETE FROM INGREDIENTES WHERE ID = ?;"""

        var result = 0
        connection.use {
            it.prepareStatement(sql).use { stm ->
                stm.setLong(1, id)

                result = stm.executeUpdate()
            }
        }

        return result > 0
    }

    fun deleteAllIngredientes(): Boolean {
        logger.debug { "Elimino todos los ingredientes" }
        val sql = """DELETE FROM INGREDIENTES;"""

        var result = 0
        connection.use {
            result = it.prepareStatement(sql).executeUpdate()
        }

        return result > 0
    }

}