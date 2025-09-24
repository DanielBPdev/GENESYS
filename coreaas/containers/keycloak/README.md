# Keycloak

Keycloak con SQL Server

### Construcción

    mvn clean install

### Ejecución

Enviar las variables de ambiente para configurar la conexión con SQL Server.

kubectl run keycloak --image=gcr.io/<NOMBRE_PROYECTO_GCLOUD>/keycloak --env="KEYCLOAK_LOGLEVEL=DEBUG" --env="MSSQL_HOST=localhost" --env="MSSQL_DATABASE=keycloak" --env="MSSQL_USER=keycloak" --env="MSSQL_PASSWORD=keycloak" --env="KEYCLOAK_USER=admin" --env="KEYCLOAK_PASSWORD=admin" --port=8080 --generator=run/v1

KEYCLOAK_LOGLEVEL
Nivel de log de keycloak

MSSQL_HOST
Host del SQL Server, variable obligatoria.

MSSQL_PORT
Puerto, variable opcional, por defecto es 1433.

MSSQL_DATABASE
Nombre de la base de datos, opcional, por defecto es keycloak.

MSSQL_USER
Usuario de la base de datos, opcional, por defecto es keycloak.

MSSQL_PASSWORD
Contraseña de la base de datos, opcional, por defecto es keycloak.

KEYCLOAK_USER
KEYCLOAK_PASSWORD
Credenciales para usuario administrador

### Ejecución en Kubernetes

kubectl run keycloak --image=gcr.io/sonorous-dragon-112103/keycloak --env="KEYCLOAK_LOGLEVEL=DEBUG" --env="MSSQL_HOST=laboratoriohbt01sqlserver.database.windows.net" --env="MSSQL_DATABASE=keycloak" --env="MSSQL_USER=keycloak" --env="MSSQL_PASSWORD=Asdf1234$" --env="KEYCLOAK_USER=admin" --env="KEYCLOAK_PASSWORD=keycloakint2017$" --port=8080 --generator=run/v1

### Google Kubernetes Engine

Conexión con cluster usando Google Cloud SDK

gcloud container clusters get-credentials --zone=<REGION> <NOMBRE-CLUSTER>

Habilitar Container builder API 

Ejectutar desde la carpeta que contiene el archivo Dockerfile

gcloud container builds submit --tag gcr.io/[PROJECT_ID]/keycloak .
