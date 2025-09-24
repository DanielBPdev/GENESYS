# Perfiles

## fabric8

Se puede realizar la creación de imagenes y configuraciones de despliegue en Openshift 
para cada microservicio usando maven mediante el plugin creado por fabric8 de la siguiente manera.

### Google Kubernetes Engine (GKE)

1. Seguir los pasos del Quickstart de la siguiente página https://cloud.google.com/kubernetes-engine/docs/quickstart,
   luego de esto debe quedar instalado el shell para ejecutar los comandos de google sdk y kubectl.
2. Ejectuar el siguiente comando para autenticarse en el cluster de kubernetes 
```
gcloud container clusters get-credentials [CLUSTER_NAME] 
```
3. Generar el archivo .war y los archivos Dockerfile y kubernetes.yml
```
mvn clean package -Pfabric8
```
4. Construir la imagen del contenedor, este utiliza el archivo Dockerfile y el archivo 
   .war generado en el paso anterior, lo que sucede aquí es que se envían estos archivos 
   a Google Cloud dentro de un archivo temporal .tar y desde allá se crea la imagen, debido 
   a esto no es necesario tener instalado Docker localmente.
```
gcloud container builds submit --tag gcr.io/sonorous-dragon-112103/listas-service target\docker-build
```
gcr = google container registry, gcr.io es el registro de imagenes de Google.
sonorous-dragon-112103 = nombre del proyecto en Google Cloud.
listas-service = nombre con el que quedará la imagen Docker.
target\docker-build = es la carpeta donde se generan los archivos requeridos para construir 
la imagen docker.
5. Desplegar el microservicio
```
mvn fabric8:deploy -Pfabric8
```
En este caso el plugin envia el archivo kubernetes.yml generado en el paso 3 para su 
ejecución. Este archivo contiene la informacion para crear: 

>
  * __deployment__: contiene la información de número de replicas e imagen a utilizar 
    para ejecutar el pod del servicio el cual contiene un solo contenedor Docker.
  * __service__: abstracción de kubernetes para acceder a el(los) pod(s) definido(s) en 
    el deployment.
>

### Openshift

1. Descargar el cliente de herramientas de Openshift https://github.com/openshift/origin/releases/download/v3.6.0/openshift-origin-client-tools-v3.6.0-c4dd4cf-windows.zip
2. Descomprimir el archivo y agregar la ruta a la variable de entorno PATH, para poder 
   utilizar el archivo oc.exe desde la línea de comandos.
3. Autenticarse en Openshift
```
oc login -u developer -p developer openshift.master.heinsohn.com.co:8443
```
4. Generar el archivo .war y los archivos Dockerfile y openshift.yml
```
mvn clean package -Pfabric8
```
5. Construir la imagen del contenedor, este utiliza el archivo Dockerfile y el archivo 
   .war generado en el paso anterior, lo que sucede aquí es que se envían estos archivos 
   a Openshift dentro de un archivo temporal .tar y desde allá se crea la imagen, debido 
   a esto no es necesario tener instalado Docker localmente.
```
mvn fabric8:build -Pfabric8 -Dkubernetes-namespace=openshift
```
Se envía la propiedad kubernetes-namespace=openshift para que las imagenes queden centralizadas 
en el proyecto llamado openshift.
6. Desplegar el microservicio. El proyecto comfasucre es tomado como ejemplo
```
mvn fabric8:deploy -Pfabric8 -Dkubernetes-namespace=comfasucre
```
En este caso el plugin envia el archivo openshift.yml generado en el paso 4 para su 
ejecución. Este archivo contiene la informacion para crear: 

>
  * __deploymentConfig__: contiene la información de número de replicas e imagen a utilizar 
    para ejecutar el pod del servicio el cual contiene un solo contenedor Docker.
    En kubernetes este controlador se llama deployment, deploymentConfig es propio de 
    Openshift.
  * __service__: abstracción de kubernetes para acceder a el(los) pod(s) definido(s) en 
    el deploymentConfig.
>

### Acerca de la configuración del perfil fabric8

El perfil fabric8 se compone de los siguientes plugins

  * __maven-antrun-plugin__: se utiliza para copiar el archivo .war en la carpeta docker-build.
  * __maven-remote-resources-plugin__: se utiliza para copiar los archivos del proyecto 
    `plantillas-despliegue` (anteriormente llamado shared-resource), el cual contiene 
    las plantillas para la generación de los archivos `Dockerfile` y `openshift.yml`, dicho 
    proyecto está como dependencia en el perfil.
  * __replacer__: se utiliza para remplazar tokens en la plantilla del Dockerfile.
  * __fabric8-maven-plugin__: para generar el archivo openshift.yml e interactuar con la 
    plataforma Openshift, para crear las imagenes y desplegar los microservicios.

## clean_clients

Con este perfil se eliminan los clientes generados en los proyectos apis. 