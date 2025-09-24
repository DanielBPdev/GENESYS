--liquibase formatted sql

--changeset jocorrea:01
--comment: CHANGESET PARA CONTROL DE CAMBIO FOVIS
UPDATE Parametro
SET prmDescripcion = 'Fórmula dinámica para calcular el valor de "Parte 1" - HU-048 Calificar postulaciones FOVIS'
WHERE prmNombre = 'PUNTAJE_FOVIS_PARTE_1';
--- Parte 1 - B1
UPDATE Parametro
SET prmDescripcion = 'Fórmula dinámica para calcular el valor de "B1" - HU-048 Calificar postulaciones FOVIS'
WHERE prmNombre = 'PUNTAJE_FOVIS_PARTE_1_B1';
--- Parte 2
UPDATE Parametro
SET prmDescripcion = 'Fórmula dinámica para calcular el valor de "Parte 2" - HU-048 Calificar postulaciones FOVIS'
WHERE prmNombre = 'PUNTAJE_FOVIS_PARTE_2';
--- Parte 3
UPDATE Parametro
SET prmDescripcion = 'Fórmula dinámica para calcular el valor de "Parte 3" - HU-048 Calificar postulaciones FOVIS'
WHERE prmNombre = 'PUNTAJE_FOVIS_PARTE_3';
--- Parte 4
UPDATE Parametro
SET prmValor = '4.24*(B4/10000)',
prmDescripcion = 'Fórmula dinámica para calcular el valor de "Parte 4" - HU-048 Calificar postulaciones FOVIS'
WHERE prmNombre = 'PUNTAJE_FOVIS_PARTE_4'
--- Parte 4 - B4
UPDATE Parametro 
SET prmValor = '(totalRecursosHogar/(totalIngresosHogar/39980))',
prmDescripcion = 'Fórmula dinámica para calcular el valor de "B4" - HU-048 Calificar postulaciones FOVIS'
WHERE prmNombre = 'PUNTAJE_FOVIS_PARTE_4_B4'
--- Parte 5
UPDATE Parametro
SET prmDescripcion = 'Fórmula dinámica para calcular el valor de "Parte 5" - HU-048 Calificar postulaciones FOVIS'
WHERE prmNombre = 'PUNTAJE_FOVIS_PARTE_5';
--- Parte 6
UPDATE Parametro
SET prmDescripcion = 'Fórmula dinámica para calcular el valor de "Parte 6" - HU-048 Calificar postulaciones FOVIS'
WHERE prmNombre = 'PUNTAJE_FOVIS_PARTE_6';
--- Parte 7
UPDATE Parametro 
SET prmValor = 'puntaje*0.03',
prmDescripcion = 'Fórmula dinámica para calcular el valor de "Parte 7" - HU-048 Calificar postulaciones FOVIS'
WHERE prmNombre = 'PUNTAJE_FOVIS_PARTE_7'
--- Parte 8
UPDATE Parametro 
SET prmValor = 'puntaje*0.15',
prmDescripcion = 'Fórmula dinámica para calcular el valor por defecto de "Parte 8" - HU-048 Calificar postulaciones FOVIS',
prmNombre = 'PUNTAJE_FOVIS_PARTE_8_A'
WHERE prmNombre = 'PUNTAJE_FOVIS_PARTE_8'
--- Parte 8 A
UPDATE Parametro 
SET prmValor = 'puntaje*(1+(1-(SFVSolicitado/SFVCalculado)))',
prmDescripcion = 'Fórmula dinámica para calcular el valor de la variable R relacionada con "Parte 8" - HU-048 Calificar postulaciones FOVIS',
prmNombre = 'PUNTAJE_FOVIS_PARTE_8_R'
WHERE prmNombre = 'PUNTAJE_FOVIS_PARTE_8_PF'
--- Parte 8 B
INSERT INTO Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato) VALUES
('PUNTAJE_FOVIS_PARTE_8_B', 'puntajeR-puntaje', 0, 'VALOR_GLOBAL_NEGOCIO', 'Fórmula dinámica para calcular el valor de "Parte 8" cuando aplica condición - HU-048 Calificar postulaciones FOVIS', 'FORMULA'),
('PUNTAJE_FOVIS_PARTE_8_PA', 'puntaje*1.15', 0, 'VALOR_GLOBAL_NEGOCIO', 'Fórmula dinámica para calcular el valor con porcentaje adicional relacionado a "Parte 8" - HU-048 Calificar postulaciones FOVIS', 'FORMULA'),
('PUNTAJE_FOVIS_ADICION_PARTES', 'puntaje*0.12', 0, 'VALOR_GLOBAL_NEGOCIO', 'Fórmula dinámica para calcular de valor máximo permitido de "Parte 8" - HU-048 Calificar postulaciones FOVIS', 'FORMULA');


--changeset jocorrea:02
--comment: 
ALTER TABLE PostulacionFOVIS ADD pofInfoParametrizacion VARCHAR(MAX) NULL
ALTER TABLE PostulacionFOVIS ADD pofSalarioAsignacion NUMERIC(12,6) NULL
ALTER TABLE PostulacionFOVIS ADD pofValorSFVAjustado NUMERIC(19,5) NULL

ALTER TABLE aud.PostulacionFOVIS_aud ADD pofInfoParametrizacion VARCHAR(MAX) NULL
ALTER TABLE aud.PostulacionFOVIS_aud ADD pofSalarioAsignacion NUMERIC(12,6) NULL
ALTER TABLE aud.PostulacionFOVIS_aud ADD pofValorSFVAjustado NUMERIC(19,5) NULL

--changeset jocorrea:03
--comment: 
CREATE TABLE DetalleNovedadFovis (
	dnfId BIGINT IDENTITY (1,1) NOT NULL,
	dnfSolicitudNovedad BIGINT NOT NULL,
	dnfValorSFVSimuladoAjuste NUMERIC(19,5) NULL,
	dnfValorEquiSFVSimuladoAjuste NUMERIC(5,2) NULL,
	dnfValorDiferenciaAjuste NUMERIC(19,5) NULL,
	dnfValorEquiDiferenciaAjuste NUMERIC(5,2) NULL,
	dnfValorSFVSimuladoAdicion NUMERIC(19,5) NULL,
	dnfValorEquiSFVSimuladoAdicion NUMERIC(5,2) NULL,
	dnfValorDiferenciaAdicion NUMERIC(19,5) NULL,
	dnfValorEquiDiferenciaAdicion NUMERIC(5,2) NULL,
	dnfValorTotalDiferencia NUMERIC(19,5) NULL,
	dnfValorEquiTotalDiferencia NUMERIC(5,2) NULL,
	dnfValorSFVAjusteAdicion NUMERIC(19,5) NULL,
	dnfValorEquiSFVAjusteAdicion NUMERIC(5,2) NULL,
	dnfIdentificadorDocumentoSoporte VARCHAR(255) NULL,
	dnfInfoCondicionHogar VARCHAR(MAX) NULL
	CONSTRAINT PK_DetalleNovedadFovis_dnfId PRIMARY KEY (dnfId)
);

ALTER TABLE DetalleNovedadFovis  WITH CHECK ADD  CONSTRAINT FK_DetalleNovedadFovis_dnfSolicitudNovedad FOREIGN KEY(dnfSolicitudNovedad)
REFERENCES SolicitudNovedadFovis (snfId);
