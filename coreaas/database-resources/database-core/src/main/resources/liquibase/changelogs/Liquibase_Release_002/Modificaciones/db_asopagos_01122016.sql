--liquibase formatted sql

--changeset abaquero:01 
--comment: Eliminación y creación del campo PilaTasasInteresMora

drop table dbo.PilaTasasInteresMora;
create table PilaTasasInteresMora(
	ptiId bigint NOT NULL IDENTITY,
	ptiFechaInicioTasa date,
	ptiFechaFinTasa date,
	ptiNumeroPeriodoTasa smallint,
	ptiPorcentajeTasa numeric(4,4),
	ptiNormativa varchar(100),
	ptiTipoInteres varchar(20),
	CONSTRAINT PK_PilaTasasInteresMora_ptiId PRIMARY KEY (ptiId)
); 
