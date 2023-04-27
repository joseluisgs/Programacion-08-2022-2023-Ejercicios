package repository.alumnos

import models.Alumno
import repository.base.CrudRepository

interface AlumnoRepositoryBase: CrudRepository<Alumno, Long> {
    fun findByEdad(edad: Int): List<Alumno>
}