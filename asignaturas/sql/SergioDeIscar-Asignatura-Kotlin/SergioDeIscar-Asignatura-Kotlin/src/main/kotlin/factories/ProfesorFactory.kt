package factories

import models.Profesor
import java.time.LocalDate

object ProfesorFactory {
    fun getProfesoresDefault() = listOf(
        Profesor(
            1,
            "Juan",
            LocalDate.parse("2009-11-20"),
            ModuloFactory.getModulosDefault().subList(0, 2)
        ),
        Profesor(
            2,
            "Pepe",
            LocalDate.parse("2011-07-01"),
            ModuloFactory.getModulosDefault().subList(1, 3)
        ),
        Profesor(
            3,
            "Luis",
            LocalDate.parse("2015-09-01"),
            ModuloFactory.getModulosDefault().subList(0, 1)
        )
    )
}