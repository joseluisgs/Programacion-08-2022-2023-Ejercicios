package storage.modulo

import models.Modulo
import storage.IStorage

interface IModuloStorage:IStorage<Modulo> {
    fun exportToCsv(data: List<Modulo>)
    fun exportToJson(data:List<Modulo>)
    fun exportToXml(data:List<Modulo>)
}
