package utils.mappers

import models.Vehicle
import models.dto.VehicleDto
import models.dto.VehicleListDto
import utils.getTypeMotor
import utils.mappers.base.IDtoMapper
import utils.mappers.base.ListMapper
import java.util.*

class VehicleMapper : IDtoMapper<VehicleDto, Vehicle> {

    override fun toDto(model: Vehicle): VehicleDto {
        return VehicleDto(
            model.uuid.toString(),
            model.model,
            model.motor.toString(),
            model.foreignUuidConductor.toString()
        )
    }

    override fun toModel(dto: VehicleDto): Vehicle {
        return Vehicle(
            UUID.fromString(dto.uuid),
            dto.model,
            getTypeMotor(dto.motor),
            UUID.fromString(dto.foreignUuidConductor)
        )
    }
}

class VehicleListMapper : ListMapper<VehicleDto, Vehicle>(VehicleMapper()) {

    fun toDtoList(modelList: List<Vehicle>): VehicleListDto {
        val dtoList = modelList.map { VehicleMapper().toDto(it) }
        return VehicleListDto().apply { this.dtoList = dtoList }
    }

    fun toModelList(dtoList: VehicleListDto): List<Vehicle> {
        return dtoList.dtoList!!.map { VehicleMapper().toModel(it) }
    }
}


