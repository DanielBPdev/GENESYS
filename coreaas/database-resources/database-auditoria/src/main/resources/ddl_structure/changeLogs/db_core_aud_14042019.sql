--liquibase formatted sql

--changeset mamonroy:01
--comment: 
ALTER TABLE IntentoAfiliacion_aud ADD iafTipoIdentificacion VARCHAR(20) NULL;
ALTER TABLE IntentoAfiliacion_aud ADD iafNumeroIdentificacion VARCHAR(16) NULL;

--changeset mamonroy:02
--comment: 
ALTER TABLE Parametro_aud ADD prmTipoDato VARCHAR(17);
ALTER TABLE Constante_aud ADD cnsTipoDato VARCHAR(17);