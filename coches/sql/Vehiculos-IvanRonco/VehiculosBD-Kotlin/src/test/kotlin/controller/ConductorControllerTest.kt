package controller

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import models.conductor.Conductor
import models.motor.MotorElectrico
import models.motor.MotorGasolina
import models.vehiculo.Vehiculo
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import repositorio.conductor.ConductorRepositoryBase
import java.util.*

internal class ConductorControllerTest {

    @MockK
    private lateinit var repository: ConductorRepositoryBase

    @InjectMockKs
    private lateinit var controller: ConductorController

    init {
        MockKAnnotations.init(this)
    }

    val uuidVehiculo1 = UUID.fromString("bf169610-5c15-4c99-bcb0-c52c35ddc724")
    val uuidMotor1 = UUID.fromString("62d0cd6f-04db-46e5-993c-49a3af2707c1")

    val uuidVehiculo2 = UUID.fromString("36cd96e9-f06b-4a0e-9375-3725459b576a")
    val uuidMotor2 = UUID.fromString("f5d1ddd5-64d5-4a79-9f4b-0afe496d16d1")

    val conductorUuid1 = UUID.fromString("9126fe9b-9a6a-45d8-a613-00313efc6d0e")

    val conductorUuid2 = UUID.fromString("30291446-6317-4e59-80f0-41cb656a3c17")

    val conductores = listOf(
        Conductor(
            uuid = conductorUuid1,
            dni = "56748098D",
            nombre = "Ramón",
            apellidos = "Cajal Y Cajol",
            edad = 24,
            vehiculos = listOf(
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
                    ),
                    conductorId = conductorUuid1
                )
            )
        ),
        Conductor(
            uuid = conductorUuid2,
            dni = "65877345L",
            nombre = "Juan",
            apellidos = "Garsol",
            edad = 64,
            vehiculos = listOf(
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
                    ),
                    conductorId = conductorUuid2
                )
            )
        )
    )

    @Test
    fun findByDni() {
        every { repository.findByDni("65877345L") } returns conductores[1]

        assertEquals(conductores[1], controller.findByDni("65877345L").get())
    }

    @Test
    fun findByDniButNotFound() {
        every { repository.findByDni("65877345L") } returns null

        assertEquals("No se encontró al conductor con el identificador: 65877345L", controller.findByDni("65877345L").getError()!!.message)
    }

    @Test
    fun findByEdad() {
        every { repository.findByEdad(64) } returns listOf(conductores[1])

        val conductoresEdad = controller.findByEdad(64)
        assertAll(
            { assertTrue(conductoresEdad.size == 1) },
            { assertEquals(conductores[1], conductoresEdad[0]) }
        )
    }

    @Test
    fun findByNombre() {
        every { repository.findByNombre("Juan") } returns listOf(conductores[1])

        val conductoresNombre = controller.findByNombre("Juan")
        assertAll(
            { assertTrue(conductoresNombre.size == 1) },
            { assertEquals(conductores[1], conductoresNombre[0]) }
        )
    }

    @Test
    fun findAll() {
        every { repository.findAll() } returns conductores

        val conductoresTodos = controller.findAll()
        assertAll(
            { assertTrue(conductoresTodos.size == 2) },
            { assertEquals(conductores[0], conductoresTodos[0]) },
            { assertEquals(conductores[1], conductoresTodos[1]) },
        )
    }

    @Test
    fun findById() {
        every { repository.findById(conductorUuid2) } returns conductores[1]

        assertEquals(conductores[1], controller.findById(conductorUuid2).get())
    }

    @Test
    fun findByIdButNotFound() {
        every { repository.findById(conductorUuid2) } returns null

        assertEquals("No se encontró al conductor con el identificador: 30291446-6317-4e59-80f0-41cb656a3c17", controller.findById(conductorUuid2).getError()!!.message)
    }

    @Test
    fun saveComoCreate() {
        val conductorUuid = UUID.fromString("0f3b926b-4115-464e-8c10-430e661016db")
        val vehiculoUuid = UUID.fromString("e9d5fc56-02fd-4258-a87e-210205bc9a68")
        val motorUuid = UUID.fromString("cf3da04d-2e0d-4a83-999e-efede0d45b9f")
        val conductor = Conductor(
            uuid = conductorUuid,
            dni = "65746876K",
            nombre = "Ramón",
            apellidos = "Cajal Y Cajol",
            edad = 24,
            vehiculos = listOf(
                Vehiculo(
                    uuid = vehiculoUuid,
                    modelo = "Peugeot",
                    kilometros = 123,
                    añoMatriculacion = 1976,
                    apto = true,
                    motor = MotorGasolina(
                        uuid = motorUuid,
                        modelo = "Kadilak",
                        caballos = 125,
                        cilindradaGasolina = 8
                    ),
                    conductorId = conductorUuid
                )
            )
        )
        every { repository.save(conductor) } returns conductor

        assertEquals(conductor, controller.save(conductor).get())
    }

    @Test
    fun saveComoUpdate() {
        val conductor = conductores[0].copy(dni = "45765097F")
        every { repository.save(conductor) } returns conductor

        assertEquals(conductor, controller.save(conductor).get())
    }

    @Test
    fun delete() {
        every { repository.delete(conductores[0]) } returns true

        assertTrue(controller.delete(conductores[0]).get()!!)
    }

    @Test
    fun deleteButNotFound() {
        every { repository.delete(conductores[0]) } returns false

        assertEquals("No se encontró al conductor con el identificador: 9126fe9b-9a6a-45d8-a613-00313efc6d0e", controller.delete(conductores[0]).getError()!!.message)
    }

    @Test
    fun deleteById() {
        every { repository.deleteById(conductorUuid1) } returns true

        assertTrue(controller.deleteById(conductorUuid1).get()!!)
    }

    @Test
    fun deleteByIdButNotFound() {
        every { repository.deleteById(conductorUuid1) } returns false

        assertEquals("No se encontró al conductor con el identificador: 9126fe9b-9a6a-45d8-a613-00313efc6d0e", controller.deleteById(conductorUuid1).getError()!!.message)
    }
}