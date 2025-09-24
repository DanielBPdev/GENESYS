-- =============================================
-- Author:		Diego Suesca
-- Create date: 2020/10/21
-- Description:	
-- =============================================
CREATE PROCEDURE USP_GET_TrabajadorSubsidioUltimoEstado
(
	@sNumeroRadicado varchar(23),
	@nCorteFinal BIGINT	,
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

 	CREATE TABLE #maxRoaId (
	roaId BIGINT, 
	maxRev BIGINT
	)

	CREATE CLUSTERED INDEX ix_maxRoaId ON #maxRoaId (roaId,maxRev);

	INSERT #maxRoaId(roaId,maxRev)
	SELECT roa.roaId, MAX(REV) maxRev
	FROM RolAfiliado_aud roa WITH(NOLOCK)
	INNER JOIN Revision rev WITH(NOLOCK) ON (rev.revId = roa.REV)
	JOIN #TempRoaId t on t.roaId = roa.roaId		
	WHERE rev.revTimeStamp <= @nCorteFinal
	GROUP BY roa.roaId


	CREATE TABLE #RolAfiliado_aud (
		roaId BIGINT,		
		REV bigint,
		roaEstadoAfiliado varchar(50)			
	)

	--CREATE INDEX ix_RolAfiliado_aud ON #RolAfiliado_aud (roaId);
	CREATE CLUSTERED INDEX ix_RolAfiliado_aud2 ON #RolAfiliado_aud (roaId,REV);
	

	SELECT distinct roaAud.roaId,roaAud.roaEstadoAfiliado,roaAud.REV
	FROM #RolAfiliado_aud roaAud
	INNER JOIN #maxRoaId maxRoa 
								ON maxRoa.roaId = roaAud.roaId 
								AND maxRoa.maxRev = roaAud.REV

/*

 	SELECT distinct roaAud.roaId,roaAud.roaEstadoAfiliado,roaAud.REV
	FROM RolAfiliado_aud roaAud	WITH(NOLOCK)
	JOIN (SELECT roa.roaId, MAX(REV) maxRev
			FROM RolAfiliado_aud roa WITH(NOLOCK)
			INNER JOIN Revision rev WITH(NOLOCK) ON (rev.revId = roa.REV)
			JOIN #TempRoaId t on t.roaId = roa.roaId		
			WHERE rev.revTimeStamp <= @nCorteFinal
			GROUP BY roa.roaId
		  ) maxRoa ON maxRoa.roaId = roaAud.roaId AND maxRoa.maxRev = roaAud.REV
*/
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