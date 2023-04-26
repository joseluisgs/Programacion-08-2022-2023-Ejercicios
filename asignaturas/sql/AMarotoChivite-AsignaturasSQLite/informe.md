## BASE DE DATOS PLANTEADA

Es una relación Muchos a Muchos estándar, por ello rompemos con una tabla intermedia Docencia.

Docencias será cada registro con la información de las claves primarias de profesor, módulo y Grupo(curso y grado)

Esta tabla intermedia, de verá modificada cuando se realice un cambio tanto en módulos como en profesor.

Si eliminamos un Profesor: 1° se eliminan todos los registros de docencia donde esté ese profesor, 2° se elimina profesor

Si eliminamos un Módulo: 1° se eliminan los registros de docencia donde esté ese módulo y 2° se elimina el módulo.

--- 

&nbsp;

## ARQUITECTURA PLANTEADA

Empleo de Modelo-Controlador, donde disponemos de dos servicios:

1. DataBase: donde se gestionarán los lenguajes DML y DDL (se debe carga `DataBaseManagerGeneralDDL` para utilizarla)
2. Storage: donde se gestionará la escritura y lectura de ficheros en formato JSON y CSV

Dispongo de varios controlador...

1. ProfesorController: donde realizará la escritura y lectura pedida en el ejercicio

En los controladores se validarán los items y posibles errores mediante Railway Orientated Programming (ROP)

--- 

&nbsp;

## CONFIG.PROPIERTIES

Al iniciar el programa en `Main` se debe cargar `ConfigApp` para poder cargar la configuración
de `resources/config.properties`

Con ello mantenemos nuestra aplicación con configuraciones por defecto personalizable

--- 

&nbsp;