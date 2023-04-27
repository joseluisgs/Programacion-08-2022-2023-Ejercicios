package repositories

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class RepositoryGenericTest<T, ID> {
    internal lateinit var repository: CrudRepository<T, ID>

    abstract fun getRepository(): CrudRepository<T, ID>
    abstract fun getDefault(): List<T>

    abstract fun getUpdated(): T
    abstract fun getNew(): T

    abstract fun getOkId(): ID
    abstract fun getFailId(): ID

    @BeforeEach
    open fun setUp() {
        repository = getRepository()
        repository.deleteAll()
        repository.saveAll(getDefault())
        val paco = repository.findAll()
        println()
    }

    @Test
    fun findAllTest(){
        val result = repository.findAll()
        assertAll(
            { assertEquals(getDefault().size, result.toList().size) },
            { assertEquals(getDefault()[0], result.toList()[0]) },
            { assertEquals(getDefault()[1], result.toList()[1]) },
            { assertEquals(getDefault()[2], result.toList()[2]) },
        )
    }

    @Test
    fun findByIdTest(){
        val result = repository.findById(getOkId())
        val resultFail = repository.findById(getFailId())
        assertAll(
            { assertEquals(getDefault()[0], result) },
            { assertEquals(null, resultFail) },
        )
    }

    @Test
    fun createTest(){
        val entity = getNew()
        repository.save(entity)
        assertAll(
            { assertEquals(getDefault().size+1, repository.findAll().toList().size) },
            { assertEquals(entity, repository.findAll().last()) },
        )
    }

    @Test
    fun updateTest(){
        val entity = getUpdated()
        repository.save(entity)
        assertAll(
            { assertEquals(getDefault().size, repository.findAll().toList().size) },
            { assertEquals(entity, repository.findById(getOkId())) },
        )
    }

    @Test
    fun deleteTest(){
        repository.deleteById(getOkId())
        assertAll(
            { assertEquals(getDefault().size-1, repository.findAll().toList().size) },
            { assertEquals(null, repository.findById(getOkId())) },
        )
    }

    @Test
    fun deleteAllTest(){
        repository.deleteAll()
        assertAll(
            { assertEquals(0, repository.findAll().toList().size) },
        )
    }
}