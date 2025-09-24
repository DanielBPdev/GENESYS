--liquibase formatted sql

--changeset squintero:01
--comment: 
update Parametro set prmValor = '2d' where prmNombre = 'TIEMPO_INACTIVAR_CUENTA';