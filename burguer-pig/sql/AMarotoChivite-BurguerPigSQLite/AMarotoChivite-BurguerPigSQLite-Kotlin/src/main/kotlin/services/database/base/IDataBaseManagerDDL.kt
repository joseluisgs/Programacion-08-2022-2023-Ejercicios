package services.database.base

interface IDataBaseManagerDDL {
    fun createTables()
    fun dropTables()
}