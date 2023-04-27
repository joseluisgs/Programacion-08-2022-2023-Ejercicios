package storage

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import services.storage.StorageService
import java.io.File
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class StorageGenericTest<T> {
    abstract fun filePath(): String
    abstract fun getStorage(): StorageService<T>
    abstract fun getDefault(): List<T>

    @AfterAll
    fun cleanFiles(){
        val file = File(filePath())
        if (file.exists()) file.delete()
    }

    @Test
fun saveAllTest(){
        getStorage().saveAll(getDefault())
        // Comprobar que el fichero existe
        val file = File(filePath())
        assertTrue { file.exists() && file.canWrite() }
    }

    @Test
    fun loadAllTest(){
        val file = File(filePath())
        if (!file.exists()) getStorage().saveAll(getDefault())
        assertAll(
            { assertTrue { file.exists() && file.canRead() } },
            { assertEquals(getDefault().size, getStorage().loadAll().size) }
        )
    }
}