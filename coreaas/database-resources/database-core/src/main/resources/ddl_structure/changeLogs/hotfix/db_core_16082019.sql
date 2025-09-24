--liquibase formatted sql

--changeset mamonroy:01
--comment:
DECLARE @sql NVARCHAR(MAX),				
@minRoa bigint,
@maxRoa bigint	

SELECT @minRoa = MIN(roaId), 
@maxRoa = MAX(roaId)
FROM RolAfiliado
WHERE roaEmpleador IS NULL AND roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'              

CREATE TABLE #RolAfiliadoAux(
roaId bigint NULL,
roaCargo varchar(200) ,
roaClaseIndependiente varchar(50) ,
roaClaseTrabajador varchar(20) ,
roaEstadoAfiliado varchar(8) ,
roaEstadoEnEntidadPagadora varchar(20) ,
roaFechaIngreso date NULL,
roaFechaRetiro datetime NULL,
roaHorasLaboradasMes smallint NULL,
roaIdentificadorAnteEntidadPagadora varchar(15) ,
roaPorcentajePagoAportes numeric(5,5) NULL,
roaTipoAfiliado varchar(30) ,
roaTipoContrato varchar(20) ,
roaTipoSalario varchar(10) ,
roaValorSalarioMesadaIngresos numeric(19,2) NULL,
roaAfiliado bigint NULL,
roaEmpleador bigint NULL,
roaPagadorAportes bigint NULL,
roaPagadorPension smallint NULL,
roaSucursalEmpleador bigint NULL,
roaFechaAfiliacion date NULL,
roaMotivoDesafiliacion varchar(50) ,
roaSustitucionPatronal bit NULL,
roaFechaFinPagadorAportes date NULL,
roaFechaFinPagadorPension date NULL,
roaEstadoEnEntidadPagadoraPension varchar(20) ,
roaDiaHabilVencimientoAporte smallint NULL,
roaMarcaExpulsion varchar(22) ,
roaEnviadoAFiscalizacion bit NULL,
roaMotivoFiscalizacion varchar(30) ,
roaFechaFiscalizacion date NULL,
roaOportunidadPago varchar(11) ,
roaCanalReingreso varchar(21) ,
roaReferenciaAporteReingreso bigint NULL,
roaReferenciaSolicitudReingreso bigint NULL,
roaFechaFinContrato date NULL,
 s1 VARCHAR(500))	

SET @sql = '		
SELECT roa.roaId,
roa.roaCargo,
roa.roaClaseIndependiente,
roa.roaClaseTrabajador,
roa.roaEstadoAfiliado,
roa.roaEstadoEnEntidadPagadora,
roa.roaFechaIngreso,
roa.roaFechaRetiro,
roa.roaHorasLaboradasMes,
roa.roaIdentificadorAnteEntidadPagadora,
roa.roaPorcentajePagoAportes,
roa.roaTipoAfiliado,
roa.roaTipoContrato,
roa.roaTipoSalario,
roa.roaValorSalarioMesadaIngresos,
roa.roaAfiliado,
roa.roaEmpleador,
roa.roaPagadorAportes,
roa.roaPagadorPension,
roa.roaSucursalEmpleador,
roa.roaFechaAfiliacion,
roa.roaMotivoDesafiliacion,
roa.roaSustitucionPatronal,
roa.roaFechaFinPagadorAportes,
roa.roaFechaFinPagadorPension,
roa.roaEstadoEnEntidadPagadoraPension,
roa.roaDiaHabilVencimientoAporte,
roa.roaMarcaExpulsion,
roa.roaEnviadoAFiscalizacion,
roa.roaMotivoFiscalizacion,
roa.roaFechaFiscalizacion,
roa.roaOportunidadPago,
roa.roaCanalReingreso,
roa.roaReferenciaAporteReingreso,
roa.roaReferenciaSolicitudReingreso,
roa.roaFechaFinContrato
FROM RolAfiliado_aud roa
INNER JOIN (SELECT MAX(REV) maxRev,roaId
			FROM RolAfiliado_aud
			WHERE roaId BETWEEN @minRoa AND @maxRoa
			  AND roaEmpleador IS NOT NULL
			GROUP BY roaId
			) maxREV ON maxREV.roaId = roa.roaId AND maxREV.maxRev = roa.rev 
WHERE roa.roaId BETWEEN @minRoa AND @maxRoa'		

INSERT INTO #RolAfiliadoAux (roaId,roaCargo,roaClaseIndependiente,roaClaseTrabajador,roaEstadoAfiliado,roaEstadoEnEntidadPagadora,roaFechaIngreso,roaFechaRetiro,roaHorasLaboradasMes,roaIdentificadorAnteEntidadPagadora,roaPorcentajePagoAportes,roaTipoAfiliado,roaTipoContrato,roaTipoSalario,roaValorSalarioMesadaIngresos,roaAfiliado,roaEmpleador,roaPagadorAportes,roaPagadorPension,roaSucursalEmpleador,roaFechaAfiliacion,roaMotivoDesafiliacion,roaSustitucionPatronal,roaFechaFinPagadorAportes,roaFechaFinPagadorPension,roaEstadoEnEntidadPagadoraPension,roaDiaHabilVencimientoAporte,roaMarcaExpulsion,roaEnviadoAFiscalizacion,roaMotivoFiscalizacion,roaFechaFiscalizacion,roaOportunidadPago,roaCanalReingreso,roaReferenciaAporteReingreso,roaReferenciaSolicitudReingreso,roaFechaFinContrato, s1)
EXEC sp_execute_remote CoreAudReferenceData,
@sql,
N'@minRoa bigint, @maxRoa bigint',
@minRoa = @minRoa,@maxRoa = @maxRoa

UPDATE roa
SET roa.roaCargo = CASE WHEN roa.roaCargo IS NULL THEN roaAux.roaCargo ELSE roa.roaCargo END,
roa.roaClaseIndependiente = CASE WHEN roa.roaClaseIndependiente IS NULL THEN roaAux.roaClaseIndependiente ELSE roa.roaClaseIndependiente END,
roa.roaClaseTrabajador = CASE WHEN roa.roaClaseTrabajador IS NULL THEN roaAux.roaClaseTrabajador ELSE roa.roaClaseTrabajador END,
roa.roaEstadoAfiliado = CASE WHEN roa.roaEstadoAfiliado IS NULL THEN roaAux.roaEstadoAfiliado ELSE roa.roaEstadoAfiliado END,
roa.roaEstadoEnEntidadPagadora = CASE WHEN roa.roaEstadoEnEntidadPagadora IS NULL THEN roaAux.roaEstadoEnEntidadPagadora ELSE roa.roaEstadoEnEntidadPagadora END,
roa.roaFechaIngreso = CASE WHEN roa.roaFechaIngreso IS NULL THEN roaAux.roaFechaIngreso ELSE roa.roaFechaIngreso END,
roa.roaFechaRetiro = CASE WHEN roa.roaFechaRetiro IS NULL THEN roaAux.roaFechaRetiro ELSE roa.roaFechaRetiro END,
roa.roaHorasLaboradasMes = CASE WHEN roa.roaHorasLaboradasMes IS NULL THEN roaAux.roaHorasLaboradasMes ELSE roa.roaHorasLaboradasMes END,
roa.roaIdentificadorAnteEntidadPagadora = CASE WHEN roa.roaIdentificadorAnteEntidadPagadora IS NULL THEN roaAux.roaIdentificadorAnteEntidadPagadora ELSE roa.roaIdentificadorAnteEntidadPagadora END,
roa.roaPorcentajePagoAportes = CASE WHEN roa.roaPorcentajePagoAportes IS NULL THEN roaAux.roaPorcentajePagoAportes ELSE roa.roaPorcentajePagoAportes END,
roa.roaTipoAfiliado = CASE WHEN roa.roaTipoAfiliado IS NULL THEN roaAux.roaTipoAfiliado ELSE roa.roaTipoAfiliado END,
roa.roaTipoContrato = CASE WHEN roa.roaTipoContrato IS NULL THEN roaAux.roaTipoContrato ELSE roa.roaTipoContrato END,
roa.roaTipoSalario = CASE WHEN roa.roaTipoSalario IS NULL THEN roaAux.roaTipoSalario ELSE roa.roaTipoSalario END,
roa.roaValorSalarioMesadaIngresos = CASE WHEN roa.roaValorSalarioMesadaIngresos IS NULL THEN roaAux.roaValorSalarioMesadaIngresos ELSE roa.roaValorSalarioMesadaIngresos END,
roa.roaAfiliado = CASE WHEN roa.roaAfiliado IS NULL THEN roaAux.roaAfiliado ELSE roa.roaAfiliado END,
roa.roaEmpleador = CASE WHEN roa.roaEmpleador IS NULL THEN roaAux.roaEmpleador ELSE roa.roaEmpleador END,
roa.roaPagadorAportes = CASE WHEN roa.roaPagadorAportes IS NULL THEN roaAux.roaPagadorAportes ELSE roa.roaPagadorAportes END,
roa.roaPagadorPension = CASE WHEN roa.roaPagadorPension IS NULL THEN roaAux.roaPagadorPension ELSE roa.roaPagadorPension END,
roa.roaSucursalEmpleador = CASE WHEN roa.roaSucursalEmpleador IS NULL THEN roaAux.roaSucursalEmpleador ELSE roa.roaSucursalEmpleador END,
roa.roaFechaAfiliacion = CASE WHEN roa.roaFechaAfiliacion IS NULL THEN roaAux.roaFechaAfiliacion ELSE roa.roaFechaAfiliacion END,
roa.roaMotivoDesafiliacion = CASE WHEN roa.roaMotivoDesafiliacion IS NULL THEN roaAux.roaMotivoDesafiliacion ELSE roa.roaMotivoDesafiliacion END,
roa.roaSustitucionPatronal = CASE WHEN roa.roaSustitucionPatronal IS NULL THEN roaAux.roaSustitucionPatronal ELSE roa.roaSustitucionPatronal END,
roa.roaFechaFinPagadorAportes = CASE WHEN roa.roaFechaFinPagadorAportes IS NULL THEN roaAux.roaFechaFinPagadorAportes ELSE roa.roaFechaFinPagadorAportes END,
roa.roaFechaFinPagadorPension = CASE WHEN roa.roaFechaFinPagadorPension IS NULL THEN roaAux.roaFechaFinPagadorPension ELSE roa.roaFechaFinPagadorPension END,
roa.roaEstadoEnEntidadPagadoraPension = CASE WHEN roa.roaEstadoEnEntidadPagadoraPension IS NULL THEN roaAux.roaEstadoEnEntidadPagadoraPension ELSE roa.roaEstadoEnEntidadPagadoraPension END,
roa.roaDiaHabilVencimientoAporte = CASE WHEN roa.roaDiaHabilVencimientoAporte IS NULL THEN roaAux.roaDiaHabilVencimientoAporte ELSE roa.roaDiaHabilVencimientoAporte END,
roa.roaMarcaExpulsion = CASE WHEN roa.roaMarcaExpulsion IS NULL THEN roaAux.roaMarcaExpulsion ELSE roa.roaMarcaExpulsion END,
roa.roaEnviadoAFiscalizacion = CASE WHEN roa.roaEnviadoAFiscalizacion IS NULL THEN roaAux.roaEnviadoAFiscalizacion ELSE roa.roaEnviadoAFiscalizacion END,
roa.roaMotivoFiscalizacion = CASE WHEN roa.roaMotivoFiscalizacion IS NULL THEN roaAux.roaMotivoFiscalizacion ELSE roa.roaMotivoFiscalizacion END,
roa.roaFechaFiscalizacion = CASE WHEN roa.roaFechaFiscalizacion IS NULL THEN roaAux.roaFechaFiscalizacion ELSE roa.roaFechaFiscalizacion END,
roa.roaOportunidadPago = CASE WHEN roa.roaOportunidadPago IS NULL THEN roaAux.roaOportunidadPago ELSE roa.roaOportunidadPago END,
roa.roaCanalReingreso = CASE WHEN roa.roaCanalReingreso IS NULL THEN roaAux.roaCanalReingreso ELSE roa.roaCanalReingreso END,
roa.roaReferenciaAporteReingreso = CASE WHEN roa.roaReferenciaAporteReingreso IS NULL THEN roaAux.roaReferenciaAporteReingreso ELSE roa.roaReferenciaAporteReingreso END,
roa.roaReferenciaSolicitudReingreso = CASE WHEN roa.roaReferenciaSolicitudReingreso IS NULL THEN roaAux.roaReferenciaSolicitudReingreso ELSE roa.roaReferenciaSolicitudReingreso END,
roa.roaFechaFinContrato = CASE WHEN roa.roaFechaFinContrato IS NULL THEN roaAux.roaFechaFinContrato ELSE roa.roaFechaFinContrato END
FROM RolAfiliado roa
INNER JOIN #RolAfiliadoAux roaAux ON roaAux.roaId = roa.roaId
WHERE roa.roaEmpleador IS NULL AND roa.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE';


ALTER TABLE RolAfiliado ADD CONSTRAINT CK_RolAfiliado_roaTipoAfiliado_roaEmpleador 
CHECK (roaEmpleador IS NOT NULL AND roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
	OR roaEmpleador IS NULL AND roaTipoAfiliado = 'TRABAJADOR_INDEPENDIENTE'
	OR roaEmpleador IS NULL AND roaTipoAfiliado = 'PENSIONADO');