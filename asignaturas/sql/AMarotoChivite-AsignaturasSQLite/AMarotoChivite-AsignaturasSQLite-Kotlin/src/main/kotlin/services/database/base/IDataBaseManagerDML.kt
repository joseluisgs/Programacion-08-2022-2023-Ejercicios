package services.database.base

interface IDataBaseManagerDML<Model> {
    fun executeQuery(
        querySql: String,
        whereQuery: Any?,
        whereQuery2: Any?
    ): List<Model> //Ejecuta una consulta general, devolviendo una lista de los items

    fun insertRecord(newEntity: Model): Model //Crea si no existe (INSERT)
    fun updateRecord(newEntity: Model): Model //Actualiza si existe (UPDATE)
    fun deleteRecord(entityToDelete: Model): Boolean //Elimina si existe (DELETE), WHERE id = entityToDelete.id
    fun deleteAllRecord(): Boolean

}