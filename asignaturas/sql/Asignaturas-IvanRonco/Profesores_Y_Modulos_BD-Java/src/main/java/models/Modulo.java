package models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Modulo {
    UUID uuid;
    String nombre;
    int curso;
    GradoModulo grado;
}
