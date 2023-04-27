package storage.coches

import models.Coche
import storage.IStorage

interface ICochesStorage:IStorage<Coche> {
    fun exportToCsv(data: List<Coche>)
    fun exportToJson(data:List<Coche>)
    fun exportToXml(data:List<Coche>)
}
