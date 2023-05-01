package repositories.base

interface DataBaseFunctions<ID, T> {

    fun saveIntoDataBase(item: T): T

    fun deleteFromDatabaseById(id: ID): Boolean

    fun clearTables(): Boolean
}