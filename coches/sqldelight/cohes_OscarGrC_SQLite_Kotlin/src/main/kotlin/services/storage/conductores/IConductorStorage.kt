package services.storage.conductores

import models.Conductor
import services.base.IStorage

interface IConductorStorage:IStorage<Conductor> {
    fun exportToCsv(data: List<Conductor>)
    fun exportToJson(data:List<Conductor>)
    fun exportToXml(data:List<Conductor>)
}
