## BASE DE DATOS PLANTEADA

Es una relación Muchos a Muchos con composición (una burguer puede tener muchos ingredientes, un Ingrediente puede estar en muchas burguer.

Por ello realizamos una tabla intermedia (LineaBurguer) donde se pasará como clave foranea las Claves Primarias de Burguer e ingrediente, por ello una burguer tendrá distintas líneas con un ingrediente en cada Línea.

Burguer tiene como campo una lista de LineasBurguer con lo anterior explicado (ahí se detalla cada ingrediente junto a su burguer)

--- 

&nbsp;

## ARQUITECTURA PLANTEADA

Empleo de Modelo-Controlador, donde disponemos de dos servicios:

1. DataBase: donde se gestionarán los lenguajes DML y DDL (se debe carga `DataBaseManagerGeneralDDL` para utilizarla)
2. Storage: donde se gestionará la escritura y lectura de ficheros en formato JSON y CSV

Dispongo de un único controlador hamburguesa, ya que solo nos piden hamburguesas:

1. BurguerController: donde realizará la escritura y lectura pedida en el ejercicio

En el controlador se validarán los items y posibles errores mediante Railway Orientated Programming (ROP)

--- 

&nbsp;

## CONFIG.PROPIERTIES

Al iniciar el programa en `Main` se debe cargar `ConfigApp` para poder cargar la configuración
de `resources/config.properties`

Con ello mantenemos nuestra aplicación con configuraciones por defecto personalizable

--- 

&nbsp;