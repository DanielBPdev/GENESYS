--liquibase formatted sql

--changeset dsuesca:01
--comment:
CREATE TABLE CarteraNovedad_aud (
	canId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	canFechaInicio date NOT NULL,
	canFechaFin date NULL,
	canTipoNovedad varchar(100) NOT NULL,
	canCondicion bit NOT NULL,
	canAplicar bit NOT NULL,
	canNovedadFutura bit NOT NULL,
	canPersona bigint NOT NULL,
	canFechaCreacion datetime NOT NULL
)