import Models.Docencia
import Models.Modulo
import Models.Profesor
import com.squareup.moshi.Moshi
import java.io.File
import java.sql.Connection
import java.sql.DriverManager

fun main(args: Array<String>) {}

fun main() {
    // Conexión a la base de datos
    val connection = DriverManager.getConnection("Profesores.db")

    // Obtener datos de profesores del CSV
    val profesores = obtenerDatosDeCSVProfesores()

    // Insertar profesores en la base de datos
    insertarProfesoresEnBD(connection, profesores)

    // Obtener datos de módulos del CSV
    val modulos = obtenerDatosDeCSVModulos()

    // Insertar módulos en la base de datos
    insertarModulosEnBD(connection, modulos)

    // Obtener datos de docencia del CSV
    val docencia = obtenerDatosDeCSVDocencia()

    // Insertar docencia en la base de datos
    insertarDocenciaEnBD(connection, docencia)

    // Obtener la docencia completa en formato JSON
    val docenciaCompletaJSON = obtenerDocenciaCompletaJSON(connection)

    println(docenciaCompletaJSON)

    // Cerrar la conexión a la base de datos
    connection.close()
}




fun obtenerDatosDeCSVProfesores(): List<Profesor> {
    val profesores = mutableListOf<Profesor>()

    // Ruta del archivo CSV de profesores
    val rutaArchivoCSV = "profesores.csv"

    // Leer el archivo CSV
    File(rutaArchivoCSV).forEachLine { linea ->
        // Separar los campos de la línea del CSV
        val campos = linea.split(",")

        // Obtener los valores de los campos
        val id = campos[0].toInt()
        val nombre = campos[1]
        val fechaIncorporacion = campos[2]

        // Crear un objeto Profesor y agregarlo a la lista de profesores
        val profesor = Profesor(id, nombre, fechaIncorporacion)
        profesores.add(profesor)
    }

    return profesores
}
fun obtenerDatosDeCSVModulos(): List<Modulo> {
    val modulos = mutableListOf<Modulo>()
    val rutaArchivoCSV = "modulos.csv"
    File(rutaArchivoCSV).forEachLine() {linea ->
        val campos = linea.split(",")

        val uuid: String = campos[0]
        val nombre = campos[1]
        val curso = campos[2].toInt()
        val grado = campos[3]

        val modulo = Modulo(uuid, nombre, curso, grado)
        modulos.add(modulo)

    }
    return modulos
}
fun obtenerDatosDeCSVDocencia(): List<Docencia>{
    val docencias = mutableListOf<Docencia>()
    val rutaArchivoCSV = "docencia.csv"
    File(rutaArchivoCSV).forEachLine() {linea ->
        val campos = linea.split(",")

        val profesorId = campos[0].toInt()
        val moduloUuid = campos[1]
        val grupo = campos[2]

        val docencia = Docencia(profesorId,moduloUuid,grupo)
        docencias.add(docencia)

    }
    return docencias
}

fun insertarProfesoresEnBD(connection: Connection, profesores: List<Profesor>) {
    val statement = connection.prepareStatement("INSERT INTO profesor (nombre, fechaIncorporacion) VALUES (?, ?)")
    for (profesor in profesores) {
        statement.setString(1, profesor.nombre)
        statement.setString(2, profesor.fechaIncorporacion)
        statement.executeUpdate()
    }
    statement.close()
}
fun insertarModulosEnBD(connection: Connection, modulos: List<Modulo>) {
    val statement = connection.prepareStatement("INSERT INTO modulo (uuid, nombre, curso, grado) VALUES (?, ?, ?, ?)")
    for (modulo in modulos) {
        statement.setString(1, modulo.uuid)
        statement.setString(2, modulo.nombre)
        statement.setInt(3, modulo.curso)
        statement.setString(4, modulo.grado)
        statement.executeUpdate()
    }
    statement.close()
}
fun insertarDocenciaEnBD(connection: Connection, docencia: List<Docencia>) {
    val statement = connection.prepareStatement("INSERT INTO docencia (profesorId, moduloUuid, grupo) VALUES (?, ?, ?)")
    for (docencia in docencia ) {
        statement.setString(1, docencia.profesorId.toString())
        statement.setString(2, docencia.moduloUuid)
        statement.setString(3, docencia.grupo)
        statement.executeUpdate()

    }
    statement.close()
}
fun obtenerDocenciaCompletaJSON(connection: Connection): Any {
    val docencia = mutableListOf<Docencia>()

    val consulta = "SELECT * FROM docencia"

    val statement = connection.prepareStatement(consulta)
    val resultSet = statement.executeQuery()

    while (resultSet.next()) {
        val profesorId = resultSet.getInt("profesorId")
        val profesorNombre = resultSet.getString("profesorNombre")
        val fechaIncorporacion = resultSet.getString("fechaIncorporacion")
        val moduloUuid = resultSet.getString("moduloUuid")
        val moduloNombre = resultSet.getString("moduloNombre")
        val curso = resultSet.getInt("curso")
        val grado = resultSet.getString("grado")
        val grupo = resultSet.getString("grupo")

        val profesor = Profesor(profesorId, profesorNombre, fechaIncorporacion)
        val modulo = Modulo(moduloUuid, moduloNombre, curso, grado)
        val docente = Docencia(profesorId, moduloUuid, grupo)

        docencia.add(docente)
    }
    statement.close()
    resultSet.close()

    val listaDocencia = obtenerDocenciaCompletaJSON(connection)
    val moshi = Moshi.Builder().build()
    val adapter = moshi.adapter(List::class.java)
    return adapter.toJson(listOf(listaDocencia))
}