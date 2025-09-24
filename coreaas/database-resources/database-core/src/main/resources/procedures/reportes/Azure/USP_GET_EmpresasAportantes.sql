-- =============================================
-- Author:		Diego Suesca
-- Create date: 2018/07/09
-- Description:	SP que lista los datos del reporte normativo Empresas y Aportantes 
-- =============================================
CREATE PROCEDURE USP_GET_EmpresasAportantes
	@dFechaInicial DATE,
	@dFechaFin DATE,
	@bCount BIT
with execute as owner
AS

BEGIN
	--print 'Inicia USP_GET_EmpresasAportantes'	
	SET NOCOUNT ON;
	DECLARE @sql NVARCHAR(4000),			
			@minRed bigint,
			@maxRed bigint,
			@minReg bigint,
			@maxReg bigint;	

IF @bCount = 0
BEGIN
	SELECT @minRed = MIN(apd.apdRegistroDetallado), 
		   @maxRed = MAX(apd.apdRegistroDetallado), 
		   @minReg = MIN(apg.apgRegistroGeneral), 
		   @maxReg = MAX(apg.apgRegistroGeneral)
 	FROM AporteGeneral apg
 	INNER JOIN AporteDetallado apd ON apd.apdAporteGeneral = apg.apgId
	WHERE apg.apgPeriodoAporte BETWEEN CAST(YEAR(@dFechaInicial) AS VARCHAR) + '-' +  REPLICATE('0', 2 - DATALENGTH(CAST(MONTH(@dFechaInicial) AS VARCHAR))) + CAST(MONTH(@dFechaInicial) AS VARCHAR)
	                              AND CAST(YEAR(@dFechaFin) AS VARCHAR) + '-' +  REPLICATE('0', 2 - DATALENGTH(CAST(MONTH(@dFechaFin) AS VARCHAR))) + CAST(MONTH(@dFechaFin) AS VARCHAR)

	CREATE TABLE #IdAportesTraReingreso(redId BIGINT,afiId BIGINT, s1 VARCHAR(500))
	CREATE TABLE #IdAportesEmpReingreso(regId BIGINT,empId BIGINT, s1 VARCHAR(500))
		
	SET @sql = '		
			SELECT DISTINCT roa.roaReferenciaAporteReingreso, roaAfiliado
	FROM RolAfiliado_aud roa
	WHERE roa.roaReferenciaAporteReingreso IS NOT NULL
	  AND roa.roaReferenciaAporteReingreso BETWEEN @minRed AND @maxRed'		

	INSERT INTO #IdAportesTraReingreso (redId,afiId, s1)
	EXEC sp_execute_remote CoreAudReferenceData,
	@sql,
	N'@minRed bigint, @maxRed bigint',
	@minRed = @minRed,@maxRed = @maxRed

		SET @sql = '		
			SELECT DISTINCT emp.empReferenciaAporteReingreso, empId
	FROM Empleador_aud emp
	WHERE emp.empReferenciaAporteReingreso IS NOT NULL
	  AND emp.empReferenciaAporteReingreso BETWEEN @minReg AND @maxReg'		

	INSERT INTO #IdAportesEmpReingreso (regId,empId, s1)
	EXEC sp_execute_remote CoreAudReferenceData,
	@sql,
	N'@minReg bigint, @maxReg bigint',
	@minReg = @minReg,@maxReg = @maxReg

	SELECT CASE per.perTipoIdentificacion
            WHEN 'CEDULA_CIUDADANIA' THEN '1'
            WHEN 'TARJETA_IDENTIDAD' THEN '2'
            WHEN 'REGISTRO_CIVIL' THEN '3'
            WHEN 'CEDULA_EXTRANJERIA' THEN '4'
            WHEN 'NUIP' THEN '5'
            WHEN 'PASAPORTE' THEN '6'
            WHEN 'NIT' THEN '7'
            WHEN 'CARNE_DIPLOMATICO' THEN '8' END as tipoIdentificaicon,        
        per.perNumeroIdentificacion,        
        LEFT(CASE WHEN emp.empNaturalezaJuridica IS NULL 
                THEN RTrim(Coalesce(per.perPrimerNombre + ' ','') 
                        + Coalesce(per.perSegundoNombre + ' ', '')
                        + Coalesce(per.perPrimerApellido + ' ', '')
                        + Coalesce(per.perSegundoApellido, ''))
            ELSE per.perRazonSocial END,200) as Nombre,  
        mun.munCodigo,
        LEFT(ubi.ubiDireccionFisica,100) as ubiDireccionFisica,
        CASE 
            WHEN empl.empMarcaExpulsion IS NOT NULL AND empl.empFechaRetiro IS NOT NULL THEN '2'
            WHEN empl.empFechaRetiro IS NOT NULL THEN '4'
            ELSE '1' END estadoVinculacion,
        CASE apg.apgTipoSolicitante
            WHEN 'EMPLEADOR' THEN '1'
            WHEN 'PENSIONADO' THEN '2'
            WHEN 'INDEPENDIENTE' THEN '3'
            WHEN 'FACULTATIVO' THEN '4'                             -- PENDIENTE
            ELSE '5' END tipoDeAportante,   
        CASE emp.empNaturalezaJuridica
            WHEN 'PUBLICA' THEN '1'
            WHEN 'PRIVADA' THEN '2'
            WHEN 'MIXTA' THEN '3'
            ELSE '4' END tipoDeSector,            
            ISNULL(cii.ciiCodigo,'0000') actEconomicaPpl,
        CASE WHEN (bem.bemFechaVinculacion <= @dFechaFin AND (bem.bemFechaDesvinculacion IS NULL OR bem.bemFechaDesvinculacion >= @dFechaInicial))
                    THEN 1
                    ELSE 2 END situacion1429,
        dbo.UFN_ObtenerProgreso1429(emp.empFechaConstitucion,dep.depNombre) AS progresividad1429,
       	CAST(SUM(CASE WHEN aportTrabReingreso.afiId IS NOT NULL OR aportEmplReingreso.empId IS NOT NULL THEN 0 ELSE apd.apdAporteObligatorio END) AS BIGINT) AS aporteTotalMensual,
        CAST(SUM(ISNULL(apd.apdValorIntMora,0)) AS BIGINT) as interesesMora,        
        CAST(SUM(CASE WHEN aportTrabReingreso.afiId IS NOT NULL OR aportEmplReingreso.empId IS NOT NULL THEN apd.apdAporteObligatorio ELSE 0 END) AS BIGINT) AS valorReintegros
	FROM Persona per 
	LEFT JOIN Empresa emp ON per.perId = emp.empPersona
	LEFT JOIN Empleador empl ON empl.empEmpresa = emp.empId
	LEFT JOIN Ubicacion ubi ON ubi.ubiId = per.perUbicacionPrincipal
	LEFT JOIN Municipio mun ON mun.munId = ubi.ubiMunicipio
	LEFT JOIN Departamento dep ON dep.depId = mun.munDepartamento
	LEFT JOIN codigoCIIU cii on cii.ciiId = emp.empCodigoCIIU
	LEFT JOIN BeneficioEmpleador bem ON bem.bemEmpleador = empl.empId
	LEFT JOIN AporteGeneral apg ON apg.apgEmpresa = emp.empId OR apg.apgPersona = per.perId
	LEFT JOIN AporteDetallado apd ON apd.apdAporteGeneral = apg.apgId
	LEFT JOIN Afiliado afi ON apd.apdPersona = afi.afiPersona
	LEFT JOIN #IdAportesTraReingreso aportTrabReingreso ON aportTrabReingreso.redId = apd.apdRegistroDetallado AND aportTrabReingreso.afiId = afi.afiId	
	LEFT JOIN #IdAportesEmpReingreso aportEmplReingreso ON aportEmplReingreso.regId = apg.apgRegistroGeneral AND aportEmplReingreso.empId = empl.empId	
	WHERE apg.apgPeriodoAporte BETWEEN CAST(YEAR(@dFechaInicial) AS VARCHAR) + '-' +  REPLICATE('0', 2 - DATALENGTH(CAST(MONTH(@dFechaInicial) AS VARCHAR))) + CAST(MONTH(@dFechaInicial) AS VARCHAR)
	                              AND CAST(YEAR(@dFechaFin) AS VARCHAR) + '-' +  REPLICATE('0', 2 - DATALENGTH(CAST(MONTH(@dFechaFin) AS VARCHAR))) + CAST(MONTH(@dFechaFin) AS VARCHAR)
	GROUP BY apg.apgPeriodoAporte,
	per.perRazonSocial,
	per.perTipoIdentificacion,
	per.perNumeroIdentificacion,
	per.perPrimerNombre,
	per.perSegundoNombre,
	per.perPrimerApellido,
	per.perSegundoApellido,
	mun.munCodigo,
	ubi.ubiDireccionFisica,
	apg.apgTipoSolicitante,
	emp.empNaturalezaJuridica,
	cii.ciiCodigo,
	emp.empFechaConstitucion,
	bem.bemFechaDesvinculacion,
	empl.empMarcaExpulsion,
	empl.empFechaRetiro,
	bem.bemFechaVinculacion,dep.depNombre
	ORDER BY apg.apgPeriodoAporte	

	DROP TABLE #IdAportesTraReingreso
	DROP TABLE #IdAportesEmpReingreso
END
ELSE
BEGIN
	SELECT COUNT(1) FROM(
	SELECT 1 AS COL
  	FROM Persona per 
	LEFT JOIN Empresa emp ON per.perId = emp.empPersona
	LEFT JOIN Empleador empl ON empl.empEmpresa = emp.empId
	LEFT JOIN Ubicacion ubi ON ubi.ubiId = per.perUbicacionPrincipal
	LEFT JOIN Municipio mun ON mun.munId = ubi.ubiMunicipio
	LEFT JOIN Departamento dep ON dep.depId = mun.munDepartamento
	LEFT JOIN codigoCIIU cii on cii.ciiId = emp.empCodigoCIIU
	LEFT JOIN BeneficioEmpleador bem ON bem.bemEmpleador = empl.empId
	LEFT JOIN AporteGeneral apg ON apg.apgEmpresa = emp.empId OR apg.apgPersona = per.perId	
	WHERE apg.apgPeriodoAporte BETWEEN CAST(YEAR(@dFechaInicial) AS VARCHAR) + '-' +  REPLICATE('0', 2 - DATALENGTH(CAST(MONTH(@dFechaInicial) AS VARCHAR))) + CAST(MONTH(@dFechaInicial) AS VARCHAR)
	                              AND CAST(YEAR(@dFechaFin) AS VARCHAR) + '-' +  REPLICATE('0', 2 - DATALENGTH(CAST(MONTH(@dFechaFin) AS VARCHAR))) + CAST(MONTH(@dFechaFin) AS VARCHAR)
	GROUP BY apg.apgPeriodoAporte,
	per.perRazonSocial,
	per.perTipoIdentificacion,
	per.perNumeroIdentificacion,
	per.perPrimerNombre,
	per.perSegundoNombre,
	per.perPrimerApellido,
	per.perSegundoApellido,
	mun.munCodigo,
	ubi.ubiDireccionFisica,
	apg.apgTipoSolicitante,
	emp.empNaturalezaJuridica,
	cii.ciiCodigo,
	emp.empFechaConstitucion,
	bem.bemFechaDesvinculacion,
	empl.empMarcaExpulsion,
	empl.empFechaRetiro,
	bem.bemFechaVinculacion,
	dep.depNombre) TABLA
END
	--print 'Finaliza USP_GET_EmpresasAportantes'
END;