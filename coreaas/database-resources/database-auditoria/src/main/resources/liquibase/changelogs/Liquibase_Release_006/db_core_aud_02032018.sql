--liquibase formatted sql

--changeset ecastno:01
--comment: Se modifica tamaño de campos en la tabla PostulacionFOVIS_aud
ALTER TABLE PostulacionFOVIS_aud ALTER COLUMN pofEstadoHogar VARCHAR(58);

--changeset borozco:02
--comment:Se modifican tamaño de campos en las tablas SolicitudDesafiliacion_aud y DocumentoSoporte_aud
ALTER TABLE SolicitudDesafiliacion_aud ALTER COLUMN sodComentarioCoordinador VARCHAR(500);
ALTER TABLE DocumentoSoporte_aud ALTER COLUMN dosTipoDocumento VARCHAR(23);

--changeset flopez:03
--comment:Se adicionan campos a la tabla PostulacionFovis
ALTER TABLE PostulacionFOVIS_aud ADD pofMotivoDesistimiento VARCHAR(29) NULL;
ALTER TABLE PostulacionFOVIS_aud ADD pofMotivoRechazo VARCHAR(51) NULL;
ALTER TABLE PostulacionFOVIS_aud ADD pofMotivoHabilitacion VARCHAR(38) NULL;
ALTER TABLE PostulacionFOVIS_aud ADD pofRestituidoConSancion BIT NULL;
ALTER TABLE PostulacionFOVIS_aud ADD pofTiempoSancion VARCHAR(10) NULL;
ALTER TABLE PostulacionFOVIS_aud ADD pofMotivoRestitucion VARCHAR(45) NULL;
ALTER TABLE PostulacionFOVIS_aud ADD pofMotivoEnajenacion VARCHAR(40) NULL;
ALTER TABLE PostulacionFOVIS_aud ADD pofValorAjusteIPCSFV NUMERIC(19,5) NULL;

--changeset fvasquez:04
--comment:Se adiciona campo en la tabla Cartera_aud
ALTER TABLE Cartera_aud ADD carFechaAsignacionAccion DATETIME NULL;

--changeset flopez:05
--comment:Se modifica tamaño de campo de la tabla DocumentoSoporte_aud
ALTER TABLE DocumentoSoporte_aud ALTER COLUMN dosTipoDocumento VARCHAR(24) NULL;
