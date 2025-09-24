--liquibase formatted sql

--changeset squintero:01
--comment: Se adiciona campo en la tabla AporteGeneral
ALTER TABLE AporteGeneral ADD apgEmailAportante varchar(255) NULL;