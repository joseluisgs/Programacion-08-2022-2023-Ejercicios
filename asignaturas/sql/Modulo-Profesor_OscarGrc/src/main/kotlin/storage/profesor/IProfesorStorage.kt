package storage.profesor

import models.Profesor
import storage.IStorage

interface IProfesorStorage:IStorage<Profesor> {
    fun exportToCsv(data: List<Profesor>)
    fun exportToJson(data:List<Profesor>)
    fun exportToXml(data:List<Profesor>)
}
