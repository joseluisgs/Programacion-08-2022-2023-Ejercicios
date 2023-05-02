package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import exceptions.ConductorException
import exceptions.ConductorNoValidoException
import models.Conductor
import mu.KotlinLogging
import java.time.LocalDate

private val logger = KotlinLogging.logger{}

fun Conductor.validar(): Result<Conductor, ConductorException> {
    logger.debug { "Validando conductor: $this" }
    require(this.nombre.isNotBlank()) { return Err(ConductorNoValidoException("El nombre no puede estar vacío")) }
    require(this.fechaCarnet < LocalDate.now()) { return Err(ConductorNoValidoException("La fecha debe ser inferior a la actual")) }
    return Ok(this)
}