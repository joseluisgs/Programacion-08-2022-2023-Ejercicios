package models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Profesor {
    long id;
    String nombre;
    LocalDateTime fechaIncorporacion;
    List<Modulo> modulos;
}
