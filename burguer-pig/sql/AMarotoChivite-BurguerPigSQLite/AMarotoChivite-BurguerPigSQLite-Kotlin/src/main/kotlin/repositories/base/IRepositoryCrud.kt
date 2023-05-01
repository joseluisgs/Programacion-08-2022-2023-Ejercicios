package repositories.base

interface IRepositoryCrud<Model> {

    // Básicas
    fun getAll(): List<Model>
    fun getById(item: Model): Model?
    fun saveItem(item: Model): Model // Se encuentra tanto INSERT com UPDATE (si existe)
    fun deleteItem(item: Model): Boolean
    fun existItem(item: Model): Boolean // Comprobaremos por el ID en la consulta
    fun deleteAll(): Boolean // En este caso borrando todos los conductores se borran todos los coches

    // Consulta más específicas
    // fun findByLoQueSeMePida(ByAlgo: Any): List<Model>

}