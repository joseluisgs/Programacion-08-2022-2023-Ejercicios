package controller

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import error.IngredienteError
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import models.Ingrediente
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import repositorio.ingredientes.IngredientesRepositoryBase
import service.storage.ingredientes.IngredienteStorageService
import java.time.LocalDate
import java.util.*

internal class IngredientesControllerTest {

    @MockK
    lateinit var repository : IngredientesRepositoryBase

    @MockK
    lateinit var storage : IngredienteStorageService

    @InjectMockKs
    lateinit var controller : IngredientesController

    init {
        MockKAnnotations.init(this)
    }

    val uuid1 = "bf169610-5c15-4c99-bcb0-c52c35ddc724"
    val uuid2 = "62d0cd6f-04db-46e5-993c-49a3af2707c1"

    val ingredientes get() = listOf<Ingrediente>(
        Ingrediente(id = 1, uuid = UUID.fromString(uuid1), nombre = "Carne", precio = 3.46, cantidad = 14),
        Ingrediente(id = 2, uuid = UUID.fromString(uuid2), nombre = "Cebolla", precio = 1.56, cantidad = 35, disponible = false)
    )

    @Test
    fun findByDisponible() {
        every { repository.findByDisponible(true) } returns ingredientes.filter { it.disponible }

        val resultList = controller.findByDisponible(true)
        assertAll(
            { assertEquals(1, resultList.size) },
            { assertEquals(ingredientes[0], resultList[0]) }
        )
    }

    @Test
    fun findByUuid() {
        every { repository.findByUuid(UUID.fromString(uuid1)) } returns ingredientes.firstOrNull { it.uuid == UUID.fromString(uuid1) }

        val result = controller.findByUuid(UUID.fromString(uuid1))
        assertEquals(ingredientes[0], result.get())
    }

    @Test
    fun findByUuidButNotFound() {
        every { repository.findByUuid(UUID.fromString(uuid1)) } returns null

        val result = controller.findByUuid(UUID.fromString(uuid1))
        assertEquals(
            IngredienteError.IngredienteNotFound(uuid1).message,
            result.getError()!!.message
        )
    }

    @Test
    fun findByName() {
        every { repository.findByName("Car") } returns ingredientes.filter { it.nombre.lowercase().contains("car") }

        val resultList = controller.findByName("Car")
        assertAll(
            { assertEquals(1, resultList.size) },
            { assertEquals(ingredientes[0], resultList[0]) }
        )
    }

    @Test
    fun findAll() {
        every { repository.findAll() } returns ingredientes

        val resultList = controller.findAll()
        assertAll(
            { assertEquals(2, resultList.size) },
            { assertEquals(ingredientes[0], resultList[0]) },
            { assertEquals(ingredientes[1], resultList[1]) }
        )
    }

    @Test
    fun findById() {
        every { repository.findById(1) } returns ingredientes[0]

        val result = controller.findById(1)
        assertEquals(ingredientes[0], result.get())
    }

    @Test
    fun findByIdButNotFound() {
        every { repository.findById(1) } returns null

        val result = controller.findById(1)
        assertEquals(
            IngredienteError.IngredienteNotFound("1").message,
            result.getError()!!.message
        )
    }

    @Test
    fun saveComoCreate() {
        val timeNow = LocalDate.now()
        val ingrediente = ingredientes[0].copy(id = 3)
        every { repository.save(ingrediente) } returns ingrediente.copy(createAt = timeNow, updatedAt = timeNow)

        val result = controller.save(ingrediente)
        assertEquals(ingrediente.copy(createAt = timeNow, updatedAt = timeNow), result.get() )
    }

    @Test
    fun saveComoUpdate() {
        val timeNow = LocalDate.now()
        val ingrediente = ingredientes[0].copy(nombre = "Pan")
        every { repository.save(ingrediente) } returns ingrediente.copy(updatedAt = timeNow)

        val result = controller.save(ingrediente)
        assertEquals(ingrediente.copy(updatedAt = timeNow), result.get() )
    }

    @Test
    fun delete() {
        every { repository.delete(ingredientes[0]) } returns true

        val result = controller.delete(ingredientes[0])
        assertEquals(
            true,
            result.get()
        )
    }

    @Test
    fun deleteButNotFound() {
        every { repository.delete(ingredientes[0]) } returns false

        val result = controller.delete(ingredientes[0])
        assertEquals(
            IngredienteError.IngredienteNotFound("1").message,
            result.getError()!!.message
        )
    }

    @Test
    fun deleteById() {
        every { repository.deleteById(1) } returns true

        val result = controller.deleteById(1)
        assertEquals(
            true,
            result.get()
        )
    }

    @Test
    fun deleteByIdButNotFound() {
        every { repository.deleteById(1) } returns false

        val result = controller.deleteById(1)
        assertEquals(
            IngredienteError.IngredienteNotFound("1").message,
            result.getError()!!.message
        )
    }
}