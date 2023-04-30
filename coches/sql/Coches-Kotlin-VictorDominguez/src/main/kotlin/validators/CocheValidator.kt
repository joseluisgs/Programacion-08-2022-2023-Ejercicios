package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import exceptions.CocheException
import exceptions.CocheNoValidoException
import models.Coche
import mu.KotlinLogging

private val logger = KotlinLogging.logger{}

fun Coche.validar(): Result<Coche, CocheException> {
    logger.debug { "Validando coche: $this" }
    require(this.id > 0) { return Err(CocheNoValidoException("El ID no puede ser negativo ni 0")) }
    require(this.idCond.toString().isNotBlank()) { return Err(CocheNoValidoException("Requiere tener el UUID del conductor")) }
    require(this.marca.isNotBlank()) { return Err(CocheNoValidoException("La marca no puede estar vacía")) }
    require(this.modelo.isNotBlank()) { return Err(CocheNoValidoException("El modelo no puede estar vacío")) }
    require(this.precio >= 0.0) { return Err(CocheNoValidoException("El precio no puede ser menor a 0.0")) }
    return Ok(this)
}