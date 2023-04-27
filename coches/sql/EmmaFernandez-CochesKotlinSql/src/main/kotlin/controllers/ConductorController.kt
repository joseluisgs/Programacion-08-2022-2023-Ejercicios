package controllers

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import errors.CocheError
import errors.ConductorError
import models.Conductor
import repositories.ConductorRepository
import validators.validate
import java.util.UUID

class ConductorController {
    fun findById(id: UUID): Result<Conductor, ConductorError> {
        return ConductorRepository.findById(id)?.let {
            Ok(it)
        } ?: Err(ConductorError.ConductorNotFound("No existe el conductor con id $id"))
    }

    fun findAll() = ConductorRepository.findAll()

    fun deleteById(id: UUID): Result<Unit, ConductorError> {
        return when (ConductorRepository.deleteById(id)) {
            true -> Ok(Unit)
            false -> Err(ConductorError.ConductorNotFound("No existe el conductor con id $id"))
        }
    }

    fun delete(entity: Conductor): Result<Unit, ConductorError> {
        return when (ConductorRepository.delete(entity)) {
            true -> Ok(Unit)
            false -> Err(ConductorError.ConductorNotFound("No existe el conductor introducido"))
        }
    }

    fun update(entity: Conductor): Result<Conductor, ConductorError> {
        return entity.validate().andThen {
            ConductorRepository.update(it)?.let { Ok(it) }
                ?: Err(ConductorError.ConductorNotFound("No existe el conductor introducido"))
        }
    }

    fun create(entity: Conductor): Result<Conductor, ConductorError> {
        return entity.validate().andThen { Ok(ConductorRepository.create(it)) }
    }
}
