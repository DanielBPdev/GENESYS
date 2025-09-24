-- =============================================
-- Author:		Diego Suesca
-- Create date: 2020/10/21
-- Description:	
-- =============================================
CREATE PROCEDURE USP_GET_TrabajadorSubsidioFechasAfiliacion
(
	@sNumeroRadicado varchar(23),
	@fechaFin date,
	@dPeriodoIni DATE
)
AS
BEGIN
	CREATE TABLE #TempRoaId (roaId bigint,s1 varchar(500));

	INSERT #TempRoaId (roaId,s1)
	EXEC sp_execute_remote SubsidioReferenceData, 
	N'SELECT DISTINCT roa.roaId
		FROM dbo.ResultadoValidacionLiquidacion rvl
		JOIN dbo.CondicionEmpleador cem ON rvl.rvlCondicionEmpleador = cem.cemId AND rvl.rvlNumeroRadicado=cem.cemNumeroRadicado
		JOIN dbo.CondicionPersona cpeEmp on cpeEmp.cpeId = cem.cemCondicionPersona AND cpeEmp.cpeNumeroRadicado = cem.cemNumeroRadicado 
		JOIN CondicionTrabajador ctr on ctr.ctrNumeroRadicado = rvl.rvlNumeroRadicado AND ctr.ctrId = rvl.rvlCondicionTrabajador
		JOIN CondicionPersona cpeTra on cpeTra.cpeId = ctr.ctrCondicionPersona AND cpeTra.cpeNumeroRadicado = ctr.ctrNumeroRadicado
		JOIN Afiliado afi on afi.afiPersona = cpeTra.cpePersona 
		JOIN Empresa emp on emp.empPersona = cpeEmp.cpePersona 
		JOIN Empleador empl on empl.empEmpresa = emp.empId 
		JOIN RolAfiliado roa ON roa.roaEmpleador = empl.empId AND roa.roaAfiliado = afi.afiId 
		WHERE 1=1
		  and rvl.rvlEstadoDerechoLiquidacion = ''ASIGNAR_DERECHO''
		  and rvl.rvlNumeroRadicado = @sNumeroRadicado
		  and ctr.ctrPeriodo = @dPeriodoIni',
	  	N'@sNumeroRadicado varchar(23),@dPeriodoIni DATE',
 		@sNumeroRadicado = @sNumeroRadicado,
 		@dPeriodoIni = @dPeriodoIni;

	SELECT roa.roaId, roa.roaFechaAfiliacion, maxFecha.maxRev
	FROM RolAfiliado_aud roa
	JOIN #TempRoaId t on t.roaId = roa.roaId
	JOIN (
			SELECT roa.roaId,  MAX(roa.REV) maxRev
			FROM RolAfiliado_aud roa
			WHERE roa.roaFechaAfiliacion <= @fechaFin      
			GROUP BY roa.roaId
			) maxFecha ON maxFecha.maxRev = roa.REV
					  AND maxFecha.roaId = roa.roaId
	
END

/*
CREATE DATABASE SCOPED CREDENTIAL SubsidioCredential  WITH IDENTITY = 'integracion', Secret = 'Pa$$w0rd'

CREATE EXTERNAL DATA SOURCE SubsidioReferenceData
WITH
(
   TYPE=RDBMS,
   LOCATION='asopagosintegracionhtb01.database.windows.net',
   DATABASE_NAME='subsidio',
   CREDENTIAL= SubsidioCredential 
);

*/