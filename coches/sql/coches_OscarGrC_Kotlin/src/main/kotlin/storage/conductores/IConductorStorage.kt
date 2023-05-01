package storage.conductores

import models.Coche
import models.Conductor
import storage.IStorage

interface IConductorStorage:IStorage<Conductor> {
    fun exportToCsv(data: List<Conductor>)
    fun exportToJson(data:List<Conductor>)
    fun exportToXml(data:List<Conductor>)
}
