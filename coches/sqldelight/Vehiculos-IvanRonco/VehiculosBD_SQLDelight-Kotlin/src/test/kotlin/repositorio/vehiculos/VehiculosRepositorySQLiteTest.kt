package repositorio.vehiculos

import models.motor.MotorElectrico
import models.motor.MotorGasolina
import models.vehiculo.Vehiculo
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Assertions.assertAll
import java.nio.file.Files
import java.util.*
import kotlin.io.path.Path

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class VehiculosRepositorySQLiteTest {

    val repository = VehiculosRepositorySQLite

    val uuidVehiculo1 = UUID.fromString("bf169610-5c15-4c99-bcb0-c52c35ddc724")
    val uuidMotor1 = UUID.fromString("62d0cd6f-04db-46e5-993c-49a3af2707c1")

    val uuidVehiculo2 = UUID.fromString("36cd96e9-f06b-4a0e-9375-3725459b576a")
    val uuidMotor2 = UUID.fromString("f5d1ddd5-64d5-4a79-9f4b-0afe496d16d1")

    val vehiculos = listOf<Vehiculo>(
        Vehiculo(
            uuid = uuidVehiculo1,
            modelo = "Peugeot",
            kilometros = 123,
            añoMatriculacion = 1976,
            apto = true,
            motor = MotorGasolina(
                uuid = uuidMotor1,
                modelo = "Kadilak",
                caballos = 125,
                cilindradaGasolina = 8
            )
        ),
        Vehiculo(
            uuid = uuidVehiculo2,
            modelo = "Alfa Romeo",
            kilometros = 653,
            añoMatriculacion = 2004,
            apto = false,
            motor = MotorElectrico(
                uuid = uuidMotor2,
                modelo = "Kadilak",
                caballos = 274,
                porcentajeCargado = 99.99990
            )
        )
    )

    @AfterAll
    fun setUpService(){
        Files.deleteIfExists(Path("vehiculos_test.db"))
    }

    @BeforeEach
    fun setUp(){
        repository.deleteAll(true)

        vehiculos.forEach {
            repository.save(it)
        }
    }

    @Test
    fun findByAñoDeMatriculacion(){
        val resultList = repository.findByAñoMatriculacion(2004)
        assertAll(
            { assertEquals(1, resultList.size) },
            { assertEquals(vehiculos[1], resultList[0]) }
        )
    }

    @Test
    fun findByApto() {
        val resultList = repository.findByApto(true)
        assertAll(
            { assertEquals(1, resultList.size) },
            { assertEquals(vehiculos[0], resultList[0]) }
        )
    }

    @Test
    fun findByModelo() {
        val resultList = repository.findByModelo("Peu")
        assertAll(
            { assertEquals(1, resultList.size) },
            { assertEquals(vehiculos[0], resultList[0]) }
        )
    }

    @Test
    fun findAll() {
        val resultList = repository.findAll()
        assertAll(
            { assertEquals(2, resultList.size) },
            { assertEquals(vehiculos[0], resultList[0]) },
            { assertEquals(vehiculos[1], resultList[1]) }
        )
    }

    @Test
    fun findById() {
        val result = repository.findById(uuidVehiculo1)
        assertEquals(vehiculos[0], result)
    }

    @Test
    fun saveComoCreate() {
        val uuid = UUID.fromString("78554f5b-d08e-49f2-96e8-1bf0f2494040")
        val vehiculo = Vehiculo(
            uuid = uuid,
            modelo = "Alfa Romeo",
            kilometros = 653,
            añoMatriculacion = 2004,
            apto = false,
            motor = MotorElectrico(
                uuid = uuid,
                modelo = "Kadilak",
                caballos = 274,
                porcentajeCargado = 99.99990
            )
        )
        val result = repository.save(vehiculo)
        assertAll(
            { assertEquals(3, repository.findAll().size) },
            { assertEquals(vehiculo, result) }
        )
    }

    @Test
    fun saveComoUpdate() {
        val vehiculo = repository.findAll()[0].copy(modelo = "Citroen")
        val result = repository.save(vehiculo)
        assertAll(
            { assertEquals(2, repository.findAll().size) },
            { assertEquals(vehiculo, result) }
        )
    }

    @Test
    fun delete() {
        val result = repository.delete(vehiculos[0])
        assertAll(
            { assertTrue(result) },
            { assertEquals(1, repository.findAll().size) }
        )
    }

    @Test
    fun deleteById() {
        val result = repository.deleteById(uuidVehiculo1)
        assertAll(
            { assertTrue(result) },
            { assertEquals(1, repository.findAll().size) }
        )
    }

    @Test
    fun deleteAll() {
        repository.deleteAll(false)
        assertEquals(0, repository.findAll().size)
    }
}