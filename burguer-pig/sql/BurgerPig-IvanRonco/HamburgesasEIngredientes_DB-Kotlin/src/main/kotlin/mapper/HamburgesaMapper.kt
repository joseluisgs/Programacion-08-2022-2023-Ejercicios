package mapper

import dto.hamburgesa.HamburgesaDto
import dto.hamburgesa.ListaHamburgesasDto
import model.Hamburgesa
import java.util.*

fun Hamburgesa.toDto(): HamburgesaDto {
    return HamburgesaDto(
        id = this.id.toString().trim(),
        nombre = this.nombre.trim(),
        ingredientes = this.ingredientes.map { it.toDto() }
    )
}

fun HamburgesaDto.toHamburgesa(): Hamburgesa {
    return Hamburgesa(
        id = UUID.fromString(this.id),
        nombre = this.nombre,
        ingredientes = this.ingredientes.map { it.toIngrediente() }.toMutableList()
    )
}

fun List<Hamburgesa>.toDtos(): ListaHamburgesasDto{
    return ListaHamburgesasDto(map { it.toDto() })
}

fun ListaHamburgesasDto.toHamburgesas(): List<Hamburgesa>{
    return this.hamburgesasDto.map { it.toHamburgesa() }
}