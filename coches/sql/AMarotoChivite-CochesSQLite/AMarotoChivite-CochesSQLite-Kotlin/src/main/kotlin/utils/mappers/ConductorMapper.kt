package utils.mappers

import models.Conductor
import models.dto.ConductorDto
import models.dto.ConductorListDto
import utils.mappers.base.IDtoMapper
import utils.mappers.base.ListMapper
import java.util.*

class ConductorMapper : IDtoMapper<ConductorDto, Conductor> {

    override fun toDto(model: Conductor): ConductorDto {
        return ConductorDto(
            model.uuid.toString(),
            model.name
        )
    }

    override fun toModel(dto: ConductorDto): Conductor {
        return Conductor(
            UUID.fromString(dto.uuid),
            dto.name
        )
    }
}

class ConductorListMapper : ListMapper<ConductorDto, Conductor>(ConductorMapper()) {

    fun toDtoList(modelList: List<Conductor>): ConductorListDto {
        val dtoList = modelList.map { ConductorMapper().toDto(it) }
        ConductorListDto().dtoList = dtoList
        return ConductorListDto()
    }

    fun toModelList(dtoList: ConductorListDto): List<Conductor> {
        return dtoList.dtoList!!.map { ConductorMapper().toModel(it) }
    }
}


