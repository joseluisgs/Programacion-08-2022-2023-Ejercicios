import Models.Ingredientes
import Repository.IngredientesRepository
import Repository.IngredientesRepository.Companion.COLUMN_CANTIDAD
import Repository.IngredientesRepository.Companion.COLUMN_CREATED_AT
import Repository.IngredientesRepository.Companion.COLUMN_DISPONIBLE
import Repository.IngredientesRepository.Companion.COLUMN_ID
import Repository.IngredientesRepository.Companion.COLUMN_NOMBRE
import Repository.IngredientesRepository.Companion.COLUMN_PRECIO
import Repository.IngredientesRepository.Companion.COLUMN_UPDATED_AT
import Repository.IngredientesRepository.Companion.COLUMN_UUID
import Repository.IngredientesRepository.Companion.TABLE_NAME
import java.sql.*


val url = "jdbc:sqlite:ingredientes.db"
val conexion = DriverManager.getConnection(url)

fun main() {
}

fun findAll(): List<Ingredientes> {
    val ingrediente = mutableListOf<Ingredientes>()
    val query = "SELECT * FROM ${IngredientesRepository.TABLE_NAME}"
    val statement: Statement = conexion.createStatement()
    val resultSet: ResultSet = statement.executeQuery(query)
    while (resultSet.next()) {
        val id = resultSet.getInt(IngredientesRepository.COLUMN_ID)
        val uuid = resultSet.getString(IngredientesRepository.COLUMN_UUID)
        val nombre = resultSet.getString(IngredientesRepository.COLUMN_NOMBRE)
        val precio = resultSet.getDouble(IngredientesRepository.COLUMN_PRECIO)
        val cantidad = resultSet.getInt(IngredientesRepository.COLUMN_CANTIDAD)
        val createdAt = Date(resultSet.getLong(IngredientesRepository.COLUMN_CREATED_AT))
        val updatedAt = Date(resultSet.getLong(IngredientesRepository.COLUMN_UPDATED_AT))
        val disponible = resultSet.getInt(IngredientesRepository.COLUMN_DISPONIBLE) == 1
        ingrediente.add(
            Ingredientes(
                id,
                uuid,
                nombre,
                precio,
                cantidad,
                createdAt,
                updatedAt,
                disponible
            )
        )
    }
    resultSet.close()
    statement.close()
    return ingrediente
}

fun findById(id: Int): Ingredientes? {
    val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
    val preparedStatement: PreparedStatement = conexion.prepareStatement(query)
    preparedStatement.setInt(1, id)
    val resultSet: ResultSet = preparedStatement.executeQuery()
    var ingrediente: Ingredientes? = null
    if (resultSet.next()) {
    }
    val id = resultSet.getInt(IngredientesRepository.COLUMN_ID)
    val uuid = resultSet.getString(IngredientesRepository.COLUMN_UUID)
    val nombre = resultSet.getString(IngredientesRepository.COLUMN_NOMBRE)
    val precio = resultSet.getDouble(IngredientesRepository.COLUMN_PRECIO)
    val cantidad = resultSet.getInt(IngredientesRepository.COLUMN_CANTIDAD)
    val createdAt = Date(resultSet.getLong(IngredientesRepository.COLUMN_CREATED_AT))
    val updatedAt = Date(resultSet.getLong(IngredientesRepository.COLUMN_UPDATED_AT))
    val disponible = resultSet.getInt(IngredientesRepository.COLUMN_DISPONIBLE) == 1
    ingrediente = Ingredientes(

            id,
            uuid,
            nombre,
            precio,
            cantidad,
            createdAt,
            updatedAt,
            disponible
        )

    resultSet.close()
    preparedStatement.close()
    return ingrediente
}

fun findByUUID(UUID: String): Ingredientes? {
    val query = "SELECT * FROM $TABLE_NAME WHERE ${COLUMN_UUID} = ?"
    val preparedStatement: PreparedStatement = conexion.prepareStatement(query)
    preparedStatement.setString(1, UUID)
    val resultSet: ResultSet = preparedStatement.executeQuery()
    var ingrediente: Ingredientes? = null
    if (resultSet.next()) {
    }
    val id = resultSet.getInt(IngredientesRepository.COLUMN_ID)
    val uuid = resultSet.getString(IngredientesRepository.COLUMN_UUID)
    val nombre = resultSet.getString(IngredientesRepository.COLUMN_NOMBRE)
    val precio = resultSet.getDouble(IngredientesRepository.COLUMN_PRECIO)
    val cantidad = resultSet.getInt(IngredientesRepository.COLUMN_CANTIDAD)
    val createdAt = Date(resultSet.getLong(IngredientesRepository.COLUMN_CREATED_AT))
    val updatedAt = Date(resultSet.getLong(IngredientesRepository.COLUMN_UPDATED_AT))
    val disponible = resultSet.getInt(IngredientesRepository.COLUMN_DISPONIBLE) == 1

    ingrediente = Ingredientes(

            id,
            uuid,
            nombre,
            precio,
            cantidad,
            createdAt,
            updatedAt,
            disponible

        )

    resultSet.close()
    preparedStatement.close()
    return ingrediente


}

fun findByNombre(): List<Ingredientes> {
    val ingrediente = mutableListOf<Ingredientes>()
    val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_NOMBRE = ?"
    val statement: Statement = conexion.createStatement()
    val resultSet: ResultSet = statement.executeQuery(query)
    while (resultSet.next()) {
        val id = resultSet.getInt(IngredientesRepository.COLUMN_ID)
        val uuid = resultSet.getString(IngredientesRepository.COLUMN_UUID)
        val nombre = resultSet.getString(IngredientesRepository.COLUMN_NOMBRE)
        val precio = resultSet.getDouble(IngredientesRepository.COLUMN_PRECIO)
        val cantidad = resultSet.getInt(IngredientesRepository.COLUMN_CANTIDAD)
        val createdAt = Date(resultSet.getLong(IngredientesRepository.COLUMN_CREATED_AT))
        val updatedAt = Date(resultSet.getLong(IngredientesRepository.COLUMN_UPDATED_AT))
        val disponible = resultSet.getInt(IngredientesRepository.COLUMN_DISPONIBLE) == 1
        ingrediente.add(
            Ingredientes(
                id,
                uuid,
                nombre,
                precio,
                cantidad,
                createdAt,
                updatedAt,
                disponible
            )
        )
    }
    resultSet.close()
    statement.close()
    return ingrediente

}

fun findByDisponible(): Boolean {
    val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_DISPONIBLE = true"
    val preparedStatement: PreparedStatement = conexion.prepareStatement(query)
    preparedStatement.setBoolean(1, true)
    val resultSet: ResultSet = preparedStatement.executeQuery()
    var ingrediente: Ingredientes? = null
    if (resultSet.next()) {
        val id = resultSet.getInt(IngredientesRepository.COLUMN_ID)
        val uuid = resultSet.getString(IngredientesRepository.COLUMN_UUID)
        val nombre = resultSet.getString(IngredientesRepository.COLUMN_NOMBRE)
        val precio = resultSet.getDouble(IngredientesRepository.COLUMN_PRECIO)
        val cantidad = resultSet.getInt(IngredientesRepository.COLUMN_CANTIDAD)
        val createdAt = Date(resultSet.getLong(IngredientesRepository.COLUMN_CREATED_AT))
        val updatedAt = Date(resultSet.getLong(IngredientesRepository.COLUMN_UPDATED_AT))
        val disponible = resultSet.getInt(IngredientesRepository.COLUMN_DISPONIBLE) == 1

        ingrediente = Ingredientes(

                id,
                uuid,
                nombre,
                precio,
                cantidad,
                createdAt,
                updatedAt,
                disponible

            )

        resultSet.close()
        preparedStatement.close()
        return true
    }
    return false
}

fun SaveIngredients() {
    fun save(ingrediente: Ingredientes): Int? {
        val query = "INSERT INTO $TABLE_NAME (" +
                "$COLUMN_UUID, " +
                "$COLUMN_NOMBRE, " +
                "$COLUMN_PRECIO, " +
                "$COLUMN_CANTIDAD, " +
                "$COLUMN_CREATED_AT, " +
                "$COLUMN_UPDATED_AT, " +
                "$COLUMN_DISPONIBLE" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?)"
        val preparedStatement: PreparedStatement = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        preparedStatement.setString(1, ingrediente.uuid)
        preparedStatement.setString(2, ingrediente.nombre)
        preparedStatement.setDouble(3, ingrediente.precio)
        preparedStatement.setInt(4, ingrediente.cantidad)
        preparedStatement.setLong(5, ingrediente.createdAt.time)
        preparedStatement.setLong(6, ingrediente.updatedAt.time)
        preparedStatement.setInt(7, if (ingrediente.disponible) 1 else 0)
        val rowsAffected = preparedStatement.executeUpdate()
        if (rowsAffected > 0) {
            val generatedKeys = preparedStatement.generatedKeys
            if (generatedKeys.next()) {
                val id = generatedKeys.getInt(1)
                preparedStatement.close()
                return id
            }
        }
        preparedStatement.close()
        return null
    }
}

fun update(ingrediente: Ingredientes): Boolean {
    val query = "UPDATE $TABLE_NAME SET" +
            "$COLUMN_UUID = ?," +
            "$COLUMN_NOMBRE = ?," +
            "$COLUMN_PRECIO = ?," +
            "$COLUMN_CANTIDAD = ?," +
            "$COLUMN_CREATED_AT = ?" +
            "$COLUMN_UPDATED_AT = ?" +
            "$COLUMN_DISPONIBLE = ?" +
            "WHERE $COLUMN_ID = ? "

    val preparedStatement: PreparedStatement = conexion.prepareStatement(query)
    preparedStatement.setString(1, ingrediente.uuid)
    preparedStatement.setString(2, ingrediente.nombre)
    preparedStatement.setDouble(3, ingrediente.precio)
    preparedStatement.setInt(4, ingrediente.cantidad)
    preparedStatement.setLong(5, ingrediente.createdAt.time)
    preparedStatement.setLong(6, ingrediente.updatedAt.time)
    preparedStatement.setInt(7, if (ingrediente.disponible) 1 else 0)
    preparedStatement.setInt(8, ingrediente.id)
    val rowsAffected = preparedStatement.executeUpdate()
    preparedStatement.close()
    return rowsAffected > 0
}
fun delete(ingrediente: Ingredientes): Boolean {
    return delteteById(ingrediente.id)
}

fun delteteById(id: Int): Boolean {
    val query = "DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
    val preparedStatement: PreparedStatement = conexion.prepareStatement(query)
    preparedStatement.setInt(1, id)
    val rowsAffected = preparedStatement.executeUpdate()
    preparedStatement.close()
    return rowsAffected > 0
}

fun close() {
    conexion.close()
}


















