--liquibase formatted sql

--changeset dsuesca:01
--comment: 
CREATE TABLE HistoricoAdminsubsidioGrupo (
	hasId bigint IDENTITY(1,1) NOT NULL,	
	hasGrupoFamiliar bigint NOT NULL,
	hasMedioDePago bigint NOT NULL,
	hasTipoIdentificacionAdmin varchar(20) NOT NULL,
	hasNumeroIdentificacionAdmin varchar(16) NOT NULL,
	hasFechaInicio DATE NOT NULL,
	hasFechaFin DATE NULL,
	CONSTRAINT PK_HistoricoAdminsubsidioGrupo_hasId PRIMARY KEY (hasId)
);
