package services.storages.base

interface IStorageGeneral<Model> {
    fun readFileOfJson(): List<Model>
    fun readFileOfCsv(): List<Model>
    fun readFileOfXml(): List<Model>?
    fun writeFileToJson(data: List<Model>): Boolean
    fun writeFileToCsv(data: List<Model>): Boolean
    fun writeFileToXml(data: List<Model>): Boolean
}