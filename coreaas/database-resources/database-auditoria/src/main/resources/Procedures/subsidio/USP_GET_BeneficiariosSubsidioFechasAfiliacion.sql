-- =============================================
-- Author:		Diego Suesca
-- Create date: 2020/10/21
-- Description:	
-- =============================================
CREATE PROCEDURE USP_GET_BeneficiariosSubsidioFechasAfiliacion
(
	@sNumeroRadicado varchar(23),
	@fechaFin date,
	@dPeriodoIni DATE
)
AS
BEGIN
	CREATE TABLE #TempBenId (benId bigint,s1 varchar(500));

	INSERT #TempBenId (benId,s1)
	EXEC sp_execute_remote SubsidioReferenceData, 
	N'SELECT DISTINCT ben.benId
		FROM dbo.ResultadoValidacionLiquidacion rvl
		JOIN dbo.CondicionBeneficiario cbe ON rvl.rvlCondicionBeneficiario = cbe.cbeId AND rvl.rvlNumeroRadicado=cbe.cbeNumeroRadicado
		JOIN dbo.CondicionPersona cpeBen on cpeBen.cpeId=cbe.cbeCondicionPersona AND cpeBen.cpeNumeroRadicado =cbe.cbeNumeroRadicado 
		JOIN CondicionTrabajador ctr on ctr.ctrNumeroRadicado = rvl.rvlNumeroRadicado AND ctr.ctrId = rvl.rvlCondicionTrabajador
		JOIN CondicionPersona cpeTra on cpeTra.cpeId = ctr.ctrCondicionPersona AND cpeTra.cpeNumeroRadicado = ctr.ctrNumeroRadicado
		JOIN Afiliado afi on afi.afiPersona = cpeTra.cpePersona 
		JOIN Beneficiario ben on ben.benAfiliado = afi.afiId and ben.benPersona =cpeben.cpePersona 
		WHERE 1=1
		  and rvl.rvlEstadoDerechoLiquidacion = ''ASIGNAR_DERECHO''
		  and rvl.rvlNumeroRadicado = @sNumeroRadicado
		  and cbe.cbePeriodo = @dPeriodoIni',
	  	N'@sNumeroRadicado varchar(23),@dPeriodoIni DATE',
 		@sNumeroRadicado=@sNumeroRadicado,
 		@dPeriodoIni = @dPeriodoIni;	

	SELECT ben.benId, ben.benFechaAfiliacion, maxRev.maxRev, ben.benEstadoBeneficiarioAfiliado
	FROM Beneficiario_aud ben
	join #TempBenId t on t.benId = ben.benId
	JOIN (SELECT ben.benId, MAX(ben.REV) maxRev
			FROM Beneficiario_aud ben			
			WHERE ben.benFechaAfiliacion <= @fechaFin    
			GROUP BY ben.benId
		) maxRev ON maxRev.maxRev = ben.REV  
			    AND maxRev.benId = ben.benId
	
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