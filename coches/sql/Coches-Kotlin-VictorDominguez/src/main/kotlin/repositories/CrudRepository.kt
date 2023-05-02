package repositories

interface CrudRepository<T, ID> {
    fun exportar(): List<T>
    fun importar(items: List<T>)
    fun buscarTodos(): List<T>
    fun buscarPorId(id: ID): T?
    fun eliminarTodos()
    fun eliminarPorId(id: ID): T?
    fun salvar(item: T)
}