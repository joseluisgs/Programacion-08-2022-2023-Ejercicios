package services.storage.modulo

import models.Modulo
import services.base.IStorage

interface IModuloStorage: IStorage<Modulo> {
    fun exportToCsv(data: List<Modulo>)
    fun exportToJson(data:List<Modulo>)
    fun exportToXml(data:List<Modulo>)
}
