# Gestión transversal de cache para el proyecto

## Definición
la clase __com.asopagos.cache.Cache__ es una interface creada con miras a ser una implementación transversal en el proyecto, siendo esta la encargada de obtener valores usados frecuentemente por la aplicación, reduciendo en gran medida los tiempos de espera en las peticiones y por ende el delay reflejado al usuario. 

## Implementación
esta interface es implementada por las clases __com.asopagos.cache.CacheManagerInfinispan__ y __com.asopagos.cache.CacheManagerServicio__ respectivamente, donde estas son instanciadas por la clase __com.asopagos.cache.CacheManagerFactory__ la cual en base a un parámetro definido, decide cual implementación de cache debe usar.

el parámetro puede ser tomado del archivo __cache.properties__ en la carpeta __src/main/resources__ del proyecto service en cada servicio, de una propiedad java definida en el archivo __standalone.xml__ del jboss o de una variable del sistema, usando en el orden definido anteriormente, la primera ocurrencia.

dicha propiedad tiene como nombre gestor-cache y hasta el momento puede ser configurada con los siguientes parámetros:


* __SERVICIO:__ esta propiedad le indica al factory que debe instanciar una implementación de la interface __com.asopagos.cache.Cache__ a través de la clase __com.asopagos.cache.CacheManagerServicio__ la cual realiza la gestión del cache de manera directa entre el sistema y la base de datos, obteniendo los valores guardados en las tablas Constante y Parámetro, poniéndolos en un Map<String, String> para ser accedidos posteriormente cuando sean requeridos.


* __INFINISPAN:__ esta propiedad le indica al factory que debe instanciar una implementación de la interface __com.asopagos.cache.Cache__ a través de la clase __com.asopagos.cache.CacheManagerInfinispan__ la cual realiza la gestión del cache haciendo uso a su vez de la especificación JSR-107 ([JCache](https://www.jcp.org/en/jsr/detail?id=107)). esta configuración requiere compilar el proyecto __constantes-parametros__.


La especificación completa de Infinispan puede ser encontrada en [http://infinispan.org](http://infinispan.org/docs/stable/user_guide/user_guide.html)

## Despliegue

### Preparación
se requiere tener instalado y corriendo el [servidor de infinispan](http://downloads.jboss.org/infinispan/9.1.3.Final/infinispan-server-9.1.3.Final-bin.zip) y un espacio de cache en dicho servidor con el nombre de __parametros__.

si por algún motivo se requiere que en determinado servicio se use una implementación especifica de cache, se debe crear un archivo __cache.properties__ en el directorio __src/main/resources__ y especificar allí la propiedad __gestor-cache__ con los valores __SERVICIO__ o __INFINISPAN__ según sea la implementación a usar.

para obtener un dato que será usado recurrentemente como una constante o un parámetro haciendo uso de la cache basta con obtenerlo mediante alguna de las siguientes líneas según sea el caso:
  
``` 
CacheManager.getParametro(*nombre del parámetro*)
```
```
CacheManager.getConstante(*nombre de la constante*)
```
