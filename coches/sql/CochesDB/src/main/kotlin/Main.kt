import java.io.File
import java.sql.DriverManager
import java.sql.PreparedStatement

fun main() {
    val url = "jdbc:sqlite:test.db"
    val connection = DriverManager.getConnection(url)
    val PreparedStatement = connection.createStatement()
    PreparedStatement.execute("CREATE TABLE Coche (id INTEGER PRIMARY KEY AUTOINCREMENT, marca TEXT, modelo TEXT, precio REAL, tipoMotor TEXT)")
    PreparedStatement.execute("CREATE TABLE Conductor (uuid TEXT PRIMARY KEY, nombre TEXT, fechaCarnet TEXT)")

    val csvFile = File("conductores.csv")
    csvFile.bufferedReader().forEachLine { line ->
        val data = line.split(",")
        PreparedStatement.execute("INSERT INTO Conductor (uuid, nombre, fechaCarnet) VALUES ('${data[0]}', '${data[1]}', '${data[2]}')")

    }
    PreparedStatement.execute("INSERT INTO Coche (marca, modelo, precio, tipoMotor) VALUES ('Toyota', 'Corolla', 15000.0, 'gasolina')")
    PreparedStatement.execute("INSERT INTO Coche (marca, modelo, precio, tipoMotor) VALUES ('Tesla', 'Model S', 75000.0, 'eléctrico')")
    PreparedStatement.execute("INSERT INTO Coche (marca, modelo, precio, tipoMotor) VALUES ('Honda', 'Civic', 22000.0, 'híbrido')")

    PreparedStatement.execute("UPDATE Coche SET conductor_uuid = '1234' WHERE id = 1")

    val resultSet = PreparedStatement.executeQuery("SELECT * FROM Coche")
    while (resultSet.next()) {
        val coche = Coche(
            resultSet.getInt("id"),
            resultSet.getString("marca"),
            resultSet.getString("modelo"),
            resultSet.getDouble("precio"),
            resultSet.getString("tipoMotor")
        )


        val resulSet = PreparedStatement.executeQuery("SELECT * FROM Conductor")

        val conductor = Conductor(
            resultSet.getString("conductor_uuid"),
            resultSet.getString("conductor_nombre"),
            resultSet.getString("fechaCarnet")
        )

        println("$coche - $conductor")

        connection.close()
    }

}
