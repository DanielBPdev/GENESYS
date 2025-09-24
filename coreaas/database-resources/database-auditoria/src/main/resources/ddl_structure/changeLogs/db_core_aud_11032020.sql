--liquibase formatted sql

--changeset tuestrella:01
--comment: CC Novedades
CREATE TABLE Pais_aud (
    paiId BIGINT,
    REV bigint NOT NULL,
	REVTYPE smallint NULL,
    paiCodigo VARCHAR(10),
    paiDescripcion VARCHAR(70),
    CONSTRAINT FK_Pais_aud_REV FOREIGN KEY (REV) REFERENCES Revision(revId)
);

ALTER TABLE PersonaDetalle_aud ADD pedOrientacionSexual varchar(30);
ALTER TABLE PersonaDetalle_aud ADD pedFactorVulnerabilidad varchar(60);
ALTER TABLE PersonaDetalle_aud ADD pedPertenenciaEtnica varchar(70);
ALTER TABLE PersonaDetalle_aud ADD pedPaisResidencia bigint;
ALTER TABLE Ubicacion_aud ADD ubiSectorUbicacion varchar(10);