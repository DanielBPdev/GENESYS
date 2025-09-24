--liquibase formatted sql

--changeset jvelandia:01
--comment: Insert tabla DestinatarioComunicado
INSERT DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) VALUES('NOVEDADES_PERSONAS_PRESENCIAL', 'NTF_PARA_SBC_NVD_EMP');
INSERT DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) VALUES('AFILIACION_INDEPENDIENTE_WEB', 'RCHZ_AFL_PNS_PROD_NSUB');
INSERT DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) VALUES('AFILIACION_INDEPENDIENTE_WEB', 'RCHZ_AFL_IDPE_PROD_NSUB');

--changeset fvasquez:02
--comment: Se crea tabla DatoTemporalCartera
CREATE TABLE DatoTemporalCartera(
dtaId bigint IDENTITY(1,1) NOT NULL,
dtaCartera bigint NULL,
dtaJsonPayload text NULL,
CONSTRAINT PK_DatoTemporalCartera_dtaId PRIMARY KEY CLUSTERED (dtaId ASC)
);

--changeset fvasquez:03
--comment: Se agregan constraints tabla DatoTemporalCartera
ALTER TABLE DatoTemporalCartera  WITH CHECK ADD  CONSTRAINT FK_DatoTemporalCartera_Cartera FOREIGN KEY(dtaCartera) REFERENCES Cartera (carId);
ALTER TABLE DatoTemporalCartera CHECK CONSTRAINT FK_DatoTemporalCartera_Cartera;