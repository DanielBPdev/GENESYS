--liquibase formatted sql

--changeset squintero:01
--comment: creacion de tabla auxiliar para gestion de novedades no procesadas desde pila (mantis 259429 - tamanio del bebe)
CREATE TABLE AuxNovedadesRetirosNoAplicadasPila(
id bigint IDENTITY(1,1) PRIMARY KEY, 
fechaInicioNovedad date, 
numeroIdAfiliado varchar(20), 
tipoIdAfiliado varchar(20),
numeroIdEmpleador varchar(20), 
tipoIdEmpleador varchar(20), 
idRolAfiliado bigint, procesado bit);