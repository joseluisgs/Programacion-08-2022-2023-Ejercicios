package services.storage.coches

import models.Coche
import services.base.IStorage

interface ICochesStorage:IStorage<Coche> {
    fun exportToCsv(data: List<Coche>)
    fun exportToJson(data:List<Coche>)
    fun exportToXml(data:List<Coche>)
}
