# Ejercicio de Modulos-Profesor

Resuelve usando SQlDeLight nada más.

- Profesor (id autonumérico, nombre, fehcaIncorpracion)
- Módulo (uuid, nombre, curso (1,2), grado (DAM, DAW, ASIR, SMR))

Debes tener en cuenta que un profesor puede dar varios módulos y un módulo puede ser impartido por varios profesores (si hay varios grupos)
Te recomiendo que rompas la relación muchos a muchos con una tabla docencia, donde se tenga en cuenta profesor, módulo y grupo

Lee los datos del profesor los leerás del CSV
Los datos del módulo los leeras de un CSV

Debes sacar la docencia completa en JSON con los datos embebidos

NOTA: Si te cuesta esto imagina que un módulo solo puede estar impartido por un profesor
