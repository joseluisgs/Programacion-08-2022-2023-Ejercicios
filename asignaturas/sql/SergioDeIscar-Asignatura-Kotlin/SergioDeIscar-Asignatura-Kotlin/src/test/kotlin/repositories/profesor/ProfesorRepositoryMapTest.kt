package repositories.profesor

class ProfesorRepositoryMapTest: ProfesorRepositoryGenericTest() {
    private var repository = ProfesorRepositoryMap()
    override fun getRepository() = repository
}