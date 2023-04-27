package controllers

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import errors.CocheError
import models.Coche
import repositories.CocheRepository
import validators.validate

class CocheController {
    fun exportToJson() {
        CocheRepository.exportToJson()
    }

    fun findById(id: Long): Result<Coche, CocheError> {
        return CocheRepository.findById(id)?.let {
            Ok(it)
        } ?: Err(CocheError.CocheNotFound("No existe el coche con id $id"))
    }

    fun findAll() = CocheRepository.findAll()

    fun deleteById(id: Long): Result<Unit, CocheError> {
        return when (CocheRepository.deleteById(id)) {
            true -> Ok(Unit)
            false -> Err(CocheError.CocheNotFound("No existe el coche con id $id"))
        }
    }

    fun delete(entity: Coche): Result<Unit, CocheError> {
        return when (CocheRepository.delete(entity)) {
            true -> Ok(Unit)
            false -> Err(CocheError.CocheNotFound("No existe el coche introducido"))
        }
    }

    fun update(entity: Coche): Result<Coche, CocheError> {
        return entity.validate().andThen {
            CocheRepository.update(it)?.let { Ok(it) }
                ?: Err(CocheError.CocheNotFound("No existe el coche introducido"))
        }
    }

    fun create(entity: Coche): Result<Coche, CocheError> {
        return entity.validate().andThen { Ok(CocheRepository.create(it)) }
    }
}
