--liquibase formatted sql

--changeset squintero:01
--comment: 
update Parametro set prmCargaInicio = 1 where prmNombre = 'TAMANO_ARCHIVOS_LISTA_CHEQUEO_EN_MEGABYTES';