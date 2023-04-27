/*Creo las tablas*/
CREATE TABLE IF NOT EXISTS MOTORES(
                                      UUID TEXT PRIMARY KEY ,
                                      MODELO TEXT NOT NULL,
                                      CABALLOS INTEGER NOT NULL,
                                      TIPO TEXT NOT NULL,
                                      CILINDRADA INTEGER,
                                      CAPACIDAD_GASOLINA DOUBLE PRECISION,
                                      CAPACIDAD_ELECTRICA DOUBLE PRECISION,
                                      CARGA DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS VEHICULOS(
                                        UUID TEXT PRIMARY KEY,
                                        MODELO TEXT NOT NULL,
                                        KILOMETRO INTEGER NOT NULL,
                                        ANYO_MATRICULACION INTEGER NOT NULL,
                                        APTO INTEGER,
                                        MOTOR_ID TEXT NOT NULL,
                                        CONDUCTOR_ID TEXT NOT NULL,
                                        FOREIGN KEY (MOTOR_ID) REFERENCES MOTORES ("UUID")
    FOREIGN KEY (CONDUCTOR_ID) REFERENCES CONDUCTORES ("UUID"),
    );

CREATE TABLE IF NOT EXISTS CONDUCTORES(
                                          UUID TEXT PRIMARY KEY,
                                          DNI TEXT NOT NULL UNIQUE,
                                          NOMBRE TEXT NOT NULL,
                                          APELLIDOS TEXT NOT NULL,
                                          EDAD INTEGER NOT NULL
);