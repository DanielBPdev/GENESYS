--liquibase formatted sql

--changeset mamonroy:02
--comment: Creación de indices
CREATE INDEX IX_PilaArchivoIRegistro4_pi4IndicePlanilla ON PilaArchivoIRegistro4 (pi4IndicePlanilla) WITH (ONLINE = ON);
CREATE INDEX IX_PilaEstadoBloqueOF_peoIndicePlanillaOF ON PilaEstadoBloqueOF (peoIndicePlanillaOF) WITH (ONLINE = ON);
CREATE INDEX IX_PilaNormatividadFechaVencimiento_pfvClasificacionAportante ON PilaNormatividadFechaVencimiento (pfvClasificacionAportante) WITH (ONLINE = ON);
CREATE INDEX IX_PilaSolicitudCambioNumIdentAportante_pscPilaIndicePlanilla ON PilaSolicitudCambioNumIdentAportante (pscPilaIndicePlanilla) WITH (ONLINE = ON);
CREATE INDEX IX_NovedadSituacionPrimaria_Transaccion ON staging.NovedadSituacionPrimaria (nspTransaccion) WITH (ONLINE = ON);
CREATE INDEX IX_AportePeriodo_Transaccion ON staging.AportePeriodo (appTransaccion) WITH (ONLINE = ON);
CREATE INDEX IX_RegistroDetalladoNovedad_rdnRegistroDetallado ON staging.RegistroDetalladoNovedad (rdnRegistroDetallado) WITH (ONLINE = ON);
CREATE INDEX IX_RegistroGeneral_Transaccion ON staging.RegistroGeneral (regTransaccion) WITH (ONLINE = ON);
CREATE INDEX IX_Novedad_Transaccion ON staging.Novedad (novTransaccion) WITH (ONLINE = ON);
CREATE INDEX IX_RegistroAfectacionAnalisisIntegral_RegistroDetallado ON staging.RegistroAfectacionAnalisisIntegral (raiRegistroDetalladoAfectado) WITH (ONLINE = ON);
CREATE INDEX IX_Aportante_Transaccion ON staging.Aportante (apoTransaccion) WITH (ONLINE = ON);
CREATE INDEX IX_SucursalEmpresa_Transaccion ON staging.SucursalEmpresa (sueTransaccion) WITH (ONLINE = ON);
CREATE INDEX IX_BeneficioEmpresaPeriodo_Transaccion ON staging.BeneficioEmpresaPeriodo (bepTransaccion) WITH (ONLINE = ON);
CREATE INDEX IX_Cotizante_Transaccion ON staging.Cotizante (cotTransaccion) WITH (ONLINE = ON);
CREATE INDEX IX_HistorialEstadoBloque_hebIdIndicePlanillaOF ON HistorialEstadoBloque (hebIdIndicePlanillaOF) WITH (ONLINE = ON);
CREATE INDEX IX_HistorialEstadoBloque_hebIdIndicePlanilla ON HistorialEstadoBloque (hebIdIndicePlanilla) WITH (ONLINE = ON);
CREATE INDEX IX_PilaArchivoAPRegistro1_ap1IndicePlanilla ON PilaArchivoAPRegistro1 (ap1IndicePlanilla) WITH (ONLINE = ON);
CREATE INDEX IX_PilaArchivoFRegistro1_pf1IndicePlanillaOF ON PilaArchivoFRegistro1 (pf1IndicePlanillaOF) WITH (ONLINE = ON);
CREATE INDEX IX_PilaArchivoFRegistro5_pf5IndicePlanillaOF ON PilaArchivoFRegistro5 (pf5IndicePlanillaOF) WITH (ONLINE = ON);
CREATE INDEX IX_PilaArchivoFRegistro6_pf6PilaArchivoFRegistro5 ON PilaArchivoFRegistro6 (pf6PilaArchivoFRegistro5) WITH (ONLINE = ON);
CREATE INDEX IX_PilaArchivoFRegistro6_pf6IndicePlanillaOF ON PilaArchivoFRegistro6 (pf6IndicePlanillaOF) WITH (ONLINE = ON);
CREATE INDEX IX_PilaArchivoFRegistro8_pf8PilaArchivoFRegistro5 ON PilaArchivoFRegistro8 (pf8PilaArchivoFRegistro5) WITH (ONLINE = ON);
CREATE INDEX IX_PilaArchivoFRegistro8_pf8IndicePlanillaOF ON PilaArchivoFRegistro8 (pf8IndicePlanillaOF) WITH (ONLINE = ON);
CREATE INDEX IX_PilaArchivoFRegistro9_pf9IndicePlanillaOF ON PilaArchivoFRegistro9 (pf9IndicePlanillaOF) WITH (ONLINE = ON);
CREATE INDEX IX_PilaArchivoIPRegistro2_ip2IndicePlanilla ON PilaArchivoIPRegistro2 (ip2IndicePlanilla) WITH (ONLINE = ON);
