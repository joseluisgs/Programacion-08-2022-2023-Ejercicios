package Repository

import java.sql.*

class IngredientesRepository {
    companion object {
        const val DATABASE_NAME = "ingredientes.db"
        const val TABLE_NAME = "ingredientes"
        const val COLUMN_ID = "id"
        const val COLUMN_UUID = "uuid"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_PRECIO = "precio"
        const val COLUMN_CANTIDAD = "cantidad"
        const val COLUMN_CREATED_AT = "createdAt"
        const val COLUMN_UPDATED_AT = "updatedAt"
        const val COLUMN_DISPONIBLE = "disponible"
    }

    private val connection: Connection

    init {
        Class.forName("org.sqlite.JDBC")
        connection = DriverManager.getConnection("jdbc:sqlite:$DATABASE_NAME")
        createTableIfNotExists()
    }

    private fun createTableIfNotExists() {
        val statement: Statement = connection.createStatement()
        statement.execute(
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_UUID TEXT UNIQUE," +
                    "$COLUMN_NOMBRE TEXT," +
                    "$COLUMN_PRECIO REAL," +
                    "$COLUMN_CANTIDAD INTEGER," +
                    "$COLUMN_CREATED_AT TEXT," +
                    "$COLUMN_UPDATED_AT TEXT," +
                    "$COLUMN_DISPONIBLE INTEGER" +
                    ")"
        )
    }
}
