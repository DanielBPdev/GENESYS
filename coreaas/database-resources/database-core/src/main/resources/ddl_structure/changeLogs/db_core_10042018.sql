--liquibase formatted sql

--changeset alquintero:01
--comment: Creacion de tabla ActoAceptacionProrrogaFovis

CREATE TABLE ActoAceptacionProrrogaFovis(
	aapId bigint IDENTITY(1,1) NOT NULL,
	aapSolicitudNovedadFovis bigint NOT NULL,
	aapNumeroActoAdministrativo varchar(50) NOT NULL,
	aapFechaAprobacionConsejo date NOT NULL,
    CONSTRAINT PK_ActoAceptacionProrrogaFovis_aapId PRIMARY KEY CLUSTERED
(
	aapId ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
)
SET ANSI_PADDING OFF;

ALTER TABLE ActoAceptacionProrrogaFovis  WITH CHECK ADD CONSTRAINT FK_ActoAceptacionProrrogaFovis_aapSolicitudNovedadFovis FOREIGN KEY(aapSolicitudNovedadFovis) REFERENCES SolicitudNovedadFovis (snfId);


