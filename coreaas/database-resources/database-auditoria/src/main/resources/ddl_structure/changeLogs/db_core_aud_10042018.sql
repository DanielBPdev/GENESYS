--liquibase formatted sql

--changeset alquintero:01
--comment: Creacion de tabla ActoAceptacionProrrogaFovis_aud

CREATE TABLE ActoAceptacionProrrogaFovis_aud(
	aapId bigint IDENTITY(1,1) NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	aapSolicitudNovedadFovis bigint NOT NULL,
	aapNumeroActoAdministrativo varchar(50) NOT NULL,
	aapFechaAprobacionConsejo date NOT NULL
)

ALTER TABLE ActoAceptacionProrrogaFovis_aud  WITH CHECK ADD  CONSTRAINT FK_ActoAceptacionProrrogaFovis_aud_REV FOREIGN KEY(REV) REFERENCES Revision (revId);

ALTER TABLE ActoAceptacionProrrogaFovis_aud CHECK CONSTRAINT FK_ActoAceptacionProrrogaFovis_aud_REV;
