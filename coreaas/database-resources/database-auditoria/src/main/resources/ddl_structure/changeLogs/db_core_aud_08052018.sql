--liquibase formatted sql

--changeset abaquero:01
--comment: Creacion tabla DescuentoInteresMora_aud
 CREATE TABLE DescuentoInteresMora_aud (
	dimId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint,
	dimPerfilLecturaPila varchar(40) NOT NULL,
	dimIndicadorUGPP smallint,
	dimFechaPagoInicial date,
	dimFechaPagoFinal date,
	dimPeriodoPagoInicial varchar(7),
	dimPeriodoPagoFinal varchar(7),
	dimPorcentajeDescuento numeric(19,5) NOT NULL,
	dimExclusionTipoCotizante varchar(30),
 CONSTRAINT FK_DescuentoInteresMora_aud_REV FOREIGN KEY (REV) REFERENCES Revision(revId)
)