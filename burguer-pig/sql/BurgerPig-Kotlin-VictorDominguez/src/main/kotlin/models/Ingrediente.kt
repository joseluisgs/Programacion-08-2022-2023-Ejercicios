package models

data class Ingrediente(
    val id: Long,
    val nombre: String,
    val precio: Double
) {
    override fun toString(): String {
        return "Ingrediente -> ID: $id, " +
                "Nombre: $nombre, " +
                "Precio: $precio"
    }
}