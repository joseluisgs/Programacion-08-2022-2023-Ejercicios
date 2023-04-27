package repositories.modulo

import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ModuloRepositoryMapTest: ModuloRepositoryGenericTest() {
    private var repository = ModuloRepositoryMap()
    override fun getRepository() = repository
}