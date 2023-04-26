package models.dto

data class ConductorEmbeddedDto(val uuid: String, val name: String, val listVehicle: List<VehicleDto>?)