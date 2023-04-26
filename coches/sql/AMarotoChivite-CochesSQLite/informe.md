## BASE DE DATOS PLANTEADA

Asignamos a cada Vehiculo como clave foránea el uuid del Conductor (en caso de no guardar el vehículo, no haría falta
arrastar la clave foránea):

- Caso 1: No podemos tener Vehículos sin un Conductor asociado (cuando borremos un conductor en primer
  lugar borramos todos sus coches delegándolo al programa, así prescindimos de "ON CASCADE")
- Caso 2: Podemos tener Vehículos sin un Conductor asociado (podremos borrar conductor sin borrar coches, sólo dejamos
  fk_conductor a null)

En mi planteamiento, tomo el Caso 1

--- 

&nbsp;

## ARQUITECTURA PLANTEADA

Empleo de Modelo-Controlador, donde disponemos de dos servicios:

1. DataBase: donde se gestionarán los lenguajes DML y DDL (se debe carga `DataBaseManagerGeneralDDL` para utilizarla)
2. Storage: donde se gestionará la escritura y lectura de ficheros en formato JSON, XML y CSV

Dispongo de dos tipos de controlador:

1. FileController: donde realizará la escritura y lectura pedida en el ejercicio
2. Por cada modelo (vehiculo y conductor) he implementado la escritura y lectura por si necesitamos los ficheros
   independientes

En el controlador se validarán los items y posibles errores mediante Railway Orientated Programming (ROP)

--- 

&nbsp;

## CONFIG.PROPIERTIES

Al iniciar el programa en `Main` se debe cargar `ConfigApp` para poder cargar la configuración
de `resources/config.properties`

Con ello mantenemos nuestra aplicación con configuraciones por defecto personalizable

--- 

&nbsp;