package repositories.modulo

class ModuloRepositoryDataBaseTest: ModuloRepositoryGenericTest() {
    private var repository = ModuloRepositoryDataBase
    override fun getRepository() = repository
}