--liquibase formatted sql

--changeset jusanchez:01
--comment: Se modifica tamaño del prmDescripcion campo en la tabla Parametro
ALTER TABLE Parametro_aud ADD prmDescripcion varchar(250) NULL;

--changeset silopez:02
--comment: Se adiciona campo en la tabla ParametrizacionMetodoAsignacion_aud
ALTER TABLE ParametrizacionMetodoAsignacion_aud ADD pmaSedeCajaDestino BIGINT NULL;

--changeset mosanchez:03
--comment: Se eliminan tablas asociadas con pila
ALTER TABLE dbo.PilaPasoValores_aud DROP CONSTRAINT FK_PilaPasoValores_aud_REV;
DROP TABLE dbo.PilaPasoValores_aud;

ALTER TABLE dbo.PilaProceso_aud DROP CONSTRAINT FK_PilaProceso_aud_REV;
DROP TABLE dbo.PilaProceso_aud;

ALTER TABLE dbo.PilaOportunidadPresentacionPlanilla_aud DROP CONSTRAINT FK_PilaOportunidadPresentacionPlanilla_aud_REV;
DROP TABLE dbo.PilaOportunidadPresentacionPlanilla_aud;

ALTER TABLE dbo.AporteGeneralSimulado_aud DROP CONSTRAINT FK_AporteGeneralSimulado_aud_REV;
DROP TABLE dbo.AporteGeneralSimulado_aud;

ALTER TABLE dbo.AporteDetalladoSimulado_aud DROP CONSTRAINT FK_AporteDetalladoSimulado_aud_REV;
DROP TABLE dbo.AporteDetalladoSimulado_aud;

ALTER TABLE dbo.PilaErrorValidacionLog_aud DROP CONSTRAINT FK_PilaErrorValidacionLog_aud_REV;
DROP TABLE dbo.PilaErrorValidacionLog_aud;

ALTER TABLE dbo.Aportante_aud DROP CONSTRAINT FK_Aportante_aud_REV;
DROP TABLE dbo.Aportante_aud;

ALTER TABLE dbo.PilaNormatividadFechaVencimiento_aud DROP CONSTRAINT FK_PilaNormatividadFechaVencimiento_aud_REV;
DROP TABLE dbo.PilaNormatividadFechaVencimiento_aud;

ALTER TABLE dbo.PilaIndiceCorreccionPlanilla_aud DROP CONSTRAINT FK_PilaIndiceCorreccionPlanilla_aud_REV;
DROP TABLE dbo.PilaIndiceCorreccionPlanilla_aud;

ALTER TABLE dbo.PilaEstadoBloqueOF_aud DROP CONSTRAINT FK_PilaEstadoBloqueOF_aud_REV;
DROP TABLE dbo.PilaEstadoBloqueOF_aud;

ALTER TABLE dbo.PilaEstadoBloque_aud DROP CONSTRAINT FK_PilaEstadoBloque_aud_REV;
DROP TABLE dbo.PilaEstadoBloque_aud;

ALTER TABLE dbo.PilaArchivoIRegistro3_aud DROP CONSTRAINT FK_PilaArchivoIRegistro3_aud_REV;
DROP TABLE dbo.PilaArchivoIRegistro3_aud;

ALTER TABLE dbo.PilaArchivoIRegistro2_aud DROP CONSTRAINT FK_PilaArchivoIRegistro2_aud_REV;
DROP TABLE dbo.PilaArchivoIRegistro2_aud;

ALTER TABLE dbo.PilaArchivoIRegistro1_aud DROP CONSTRAINT FK_PilaArchivoIRegistro1_aud_REV;
DROP TABLE dbo.PilaArchivoIRegistro1_aud;

ALTER TABLE dbo.PilaArchivoIPRegistro3_aud DROP CONSTRAINT FK_PilaArchivoIPRegistro3_aud_REV;
DROP TABLE dbo.PilaArchivoIPRegistro3_aud;

ALTER TABLE dbo.PilaArchivoIPRegistro2_aud DROP CONSTRAINT FK_PilaArchivoIPRegistro2_aud_REV;
DROP TABLE dbo.PilaArchivoIPRegistro2_aud;

ALTER TABLE dbo.PilaArchivoIPRegistro1_aud DROP CONSTRAINT FK_PilaArchivoIPRegistro1_aud_REV;
DROP TABLE dbo.PilaArchivoIPRegistro1_aud;

ALTER TABLE dbo.PilaArchivoFRegistro9_aud DROP CONSTRAINT FK_PilaArchivoFRegistro9_aud_REV;
DROP TABLE dbo.PilaArchivoFRegistro9_aud;

ALTER TABLE dbo.PilaArchivoFRegistro8_aud DROP CONSTRAINT FK_PilaArchivoFRegistro8_aud_REV;
DROP TABLE dbo.PilaArchivoFRegistro8_aud;

ALTER TABLE dbo.PilaArchivoFRegistro6_aud DROP CONSTRAINT FK_PilaArchivoFRegistro6_aud_REV;
DROP TABLE dbo.PilaArchivoFRegistro6_aud;

ALTER TABLE dbo.PilaArchivoFRegistro5_aud DROP CONSTRAINT FK_PilaArchivoFRegistro5_aud_REV;
DROP TABLE dbo.PilaArchivoFRegistro5_aud;

ALTER TABLE dbo.PilaArchivoFRegistro1_aud DROP CONSTRAINT FK_PilaArchivoFRegistro1_aud_REV;
DROP TABLE dbo.PilaArchivoFRegistro1_aud;

ALTER TABLE dbo.PilaArchivoARegistro1_aud DROP CONSTRAINT FK_PilaArchivoARegistro1_aud_REV;
DROP TABLE dbo.PilaArchivoARegistro1_aud;

ALTER TABLE dbo.PilaArchivoAPRegistro1_aud DROP CONSTRAINT FK_PilaArchivoAPRegistro1_aud_REV;
DROP TABLE dbo.PilaArchivoAPRegistro1_aud;

ALTER TABLE dbo.Cotizante_aud DROP CONSTRAINT FK_Cotizante_aud_REV;
DROP TABLE dbo.Cotizante_aud;

ALTER TABLE dbo.PilaClasificacionAportante_aud DROP CONSTRAINT FK_PilaClasificacionAportante_aud_REV;
DROP TABLE dbo.PilaClasificacionAportante_aud;

ALTER TABLE dbo.PilaIndicePlanillaOF_aud DROP CONSTRAINT FK_PilaIndicePlanillaOF_aud_REV;
DROP TABLE dbo.PilaIndicePlanillaOF_aud;

ALTER TABLE dbo.MovimientoAjusteAporte_aud DROP COLUMN maaIndicePlanillaOriginal;
ALTER TABLE dbo.MovimientoAjusteAporte_aud DROP COLUMN maaIndicePlanillaCorregida;

ALTER TABLE dbo.PilaIndicePlanilla_aud DROP CONSTRAINT FK_PilaIndicePlanilla_aud_REV;
DROP TABLE dbo.PilaIndicePlanilla_aud;

ALTER TABLE dbo.Pregunta_aud DROP CONSTRAINT FK_Pregunta_aud_REV;
DROP TABLE dbo.Pregunta_aud;

ALTER TABLE dbo.ReferenciaToken_aud DROP CONSTRAINT FK_ReferenciaToken_aud_REV;
DROP TABLE dbo.ReferenciaToken_aud;

DROP TABLE dbo.PilaTasasInteresMora_aud;

--changeset mosanchez:04
--comment: Se elimina campo de la tabla Parametro
ALTER TABLE Parametro_aud DROP COLUMN prmEstado;
