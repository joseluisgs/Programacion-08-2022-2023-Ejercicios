
## Planteamiento

Creamos una tabla intermedia llamada Linea_Hamburguesa en la que como clave foránea tendremos el ID de la hamburguesa y el ID del ingrediente, junto a la cantidad de dicho ingrediente.
Asignamos a cada hamburguesa una lista de ingredientes y un precio que será calculado a partir de la lista de ingredientes.
Exportamos los datos a un fichero JSON usando modelos DTO.\

![img_8.png](img_8.png)

## Archivo "config.properties"
En este archivo configuramos las variables del proyecto\
![img_2.png](img_2.png)

## AppConfig
Aqui cargamos la configuración de la aplicación\
![img_3.png](img_3.png)

## Base de datos
Generamos la conexión a la base de datos y ejecutamos el Sript SQL inicial\
![img_4.png](img_4.png)

Tendremos tres tablas. Una para las hamburguesas, otra para los ingredientes y una tabla intermedia para relacionar cada hamburguesa con sus ingredientes.\
\
![img_5.png](img_5.png)

## Repositorios
En los repositorios se implementan las funciones propias del repositorio CRUD (interfaz CrudRepository). Tenemos un repositorio para gestionar cada tabla de la base de datos.
A los repositorios les inyectamos la base de datos para poder hacer las consultas.\
\
![img_6.png](img_6.png)

## Controladores
En los controladores he usado Results para controlar las excepciones y se gestiona la validación de los elementos que resgistramos en la base de datos.
Le inyectamos el repositorio y dos servicios de ficheros de lectura o exportación.\
\
![img_7.png](img_7.png)