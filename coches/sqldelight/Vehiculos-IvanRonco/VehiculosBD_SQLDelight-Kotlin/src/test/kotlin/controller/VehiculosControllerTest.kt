package controller

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import error.VehiculoError
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import models.motor.MotorElectrico
import models.motor.MotorGasolina
import models.vehiculo.Vehiculo
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import repositorio.vehiculos.VehiculoRepositoryBase
import java.util.*

internal class VehiculosControllerTest {

    @MockK
    lateinit var repository : VehiculoRepositoryBase

    @InjectMockKs
    lateinit var controller : VehiculosController

    init {
        MockKAnnotations.init(this)
    }

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
            ))
    )

    @Test
    fun findByAñoDeMatriculacion() {
        every { repository.findByAñoMatriculacion(2004) } returns vehiculos.filter { it.añoMatriculacion == 2004 }

        val resultList = controller.findByAñoMatriculacion(2004)
        assertAll(
            { assertEquals(1, resultList.size) },
            { assertEquals(vehiculos[1], resultList[0]) }
        )
    }

    @Test
    fun findByApto() {
        every { repository.findByApto(true) } returns vehiculos.filter { it.apto }

        val resultList = controller.findByApto(true)
        assertAll(
            { assertEquals(1, resultList.size) },
            { assertEquals(vehiculos[0], resultList[0]) }
        )
    }

    @Test
    fun findByUuid() {
        every { repository.findById(uuidVehiculo1) } returns vehiculos.firstOrNull { it.uuid == uuidVehiculo1 }

        val result = controller.findById(uuidVehiculo1)
        assertEquals(vehiculos[0], result.get())
    }

    @Test
    fun findByUuidButNotFound() {
        every { repository.findById(uuidVehiculo1) } returns null

        val result = controller.findById(uuidVehiculo1)
        assertEquals(
            VehiculoError.VehiculoNotFound(uuidVehiculo1.toString()).message,
            result.getError()!!.message
        )
    }

    @Test
    fun findByName() {
        every { repository.findByModelo("Peu") } returns listOf(vehiculos[0])

        val resultList = controller.findByModelo("Peu")
        assertAll(
            { assertEquals(1, resultList.size) },
            { assertEquals(vehiculos[0], resultList[0]) }
        )
    }

    @Test
    fun findAll() {
        every { repository.findAll() } returns vehiculos

        val resultList = controller.findAll()
        assertAll(
            { assertEquals(2, resultList.size) },
            { assertEquals(vehiculos[0], resultList[0]) },
            { assertEquals(vehiculos[1], resultList[1]) }
        )
    }

    @Test
    fun saveComoCreate() {
        val uuid = UUID.fromString("78554f5b-d08e-49f2-96e8-1bf0f2494040")
        val ingrediente = vehiculos[0].copy(uuid = uuid)
        every { repository.save(ingrediente) } returns ingrediente

        val result = controller.save(ingrediente)
        assertEquals(ingrediente, result.get() )
    }

    @Test
    fun saveComoUpdate() {
        val ingrediente = vehiculos[0].copy(modelo = "Citroen")
        every { repository.save(ingrediente) } returns ingrediente

        val result = controller.save(ingrediente)
        assertEquals(ingrediente, result.get() )
    }

    @Test
    fun delete() {
        every { repository.delete(vehiculos[0]) } returns true

        val result = controller.delete(vehiculos[0])
        assertTrue(result.get()!!)
    }

    @Test
    fun deleteButNotFound() {
        every { repository.delete(vehiculos[0]) } returns false

        val result = controller.delete(vehiculos[0])
        assertEquals(
            VehiculoError.VehiculoNotFound("$uuidVehiculo1").message,
            result.getError()!!.message
        )
    }

    @Test
    fun deleteById() {
        every { repository.deleteById(uuidVehiculo1) } returns true

        val result = controller.deleteById(uuidVehiculo1)
        assertTrue(result.get()!!)
    }

    @Test
    fun deleteByIdButNotFound() {
        every { repository.deleteById(uuidVehiculo1) } returns false

        val result = controller.deleteById(uuidVehiculo1)
        assertEquals(
            VehiculoError.VehiculoNotFound("$uuidVehiculo1").message,
            result.getError()!!.message
        )
    }
}