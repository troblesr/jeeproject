Testing
- Tests unitarios (en cada proyecto)
- Tests de integraci�n (en proyecto creado a esos efectos)

Trazas
- Las trazas se generar�n utilizando la API slf4j y el framework logback
- Se implementar� un aspecto mediante la clase CustomizableTraceInterceptor que genere trazas de todos los m�todos

Dependencias y versionado
- Todos los n�meros de versi�n de las dependencias, deben gestionarse desde el parent POM
- Para cada tarea que se pase a la columna Pending Corrections, se deber� generar un tag en GIT, y subir el �ltimo d�gito del n�mero de versi�n (0.0.1, 0.0.2, etc.)

Crear modelo de datos
- Se deber�n considerar las tablas empresa y empleado.
- Mapear tablas normalizadas en clases mediante Hibernate Tools
- Realizar tests de integraci�n

Crear API de servicios
- Crear un servicio por cada m�todo de CRUD de cada una de las entidades correspondientes a las tablas normalizadas (esto no incluye categor�as y subcategor�as)
- Crear un servicio que permita establecer una variaci�n de sueldo en base a un porcentaje que se recibir� como par�metro.

Crear implementaci�n de servicios

Implementar un frontend de servicios
- Ofrecer todos los servicios mediante una API REST
- Ofrecer el servicio de aumento de salarios tambi�n mediante una API SOAP

Implementar un frontend Web

Securizar aplicaciones
- Securizar el frontend Web mediante Spring Security.