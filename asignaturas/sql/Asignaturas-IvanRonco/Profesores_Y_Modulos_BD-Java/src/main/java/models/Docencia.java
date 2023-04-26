package models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Docencia {
    long profesor;
    UUID modulo;
    String grupo;
}
