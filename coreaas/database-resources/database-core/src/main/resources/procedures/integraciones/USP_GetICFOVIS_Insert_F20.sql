
-- ====================================================================================================
-- Author: VILLAMARIN JULIAN
-- Create date: MAYO 10 DE 2021
-- Description: Extrae la informacion de los movimientos contables para la integracion de FOVIS 
-- Modificado: Yesika Bernal, adicion tabla generacion de errores 
-- =====================================================================================================
CREATE OR ALTER PROCEDURE [sap].[USP_GetICFOVIS_Insert_F20] 
	@solNumeroRadicacion VARCHAR(MAX), @estado BIT, @documentoId nvarchar(20), @tipoDocumento nvarchar(20), @fechaIngreso DATETIME, @tipo VARCHAR(10), @observaciones VARCHAR(150)
AS
BEGIN
SET NOCOUNT ON;
 
DECLARE	@referencia VARCHAR(16), @referenciaNum BIGINT, @consecutivo BIGINT, @pofID BIGINT,  @valorlegalizado BIGINT, @ImporteDocumento BIGINT;
DECLARE @revTimeStamp BIGINT, @fechaContabilizacion DATETIME;
DECLARE @documentoIdNuevo nvarchar(20), @tipoDocumentoNuevo nvarchar(20), @ref3Nuevo VARCHAR(40), @codigoGenesysNuevo VARCHAR(15)

CREATE TABLE #revTimeStamp
(
	revTimeStamp bigint NULL,
	shardName VARCHAR (100) NULL,
)

CREATE TABLE #IC_FOVIS_Enc
(
	[fecIng] DATE NOT NULL,
	[horaIng] TIME NOT NULL,
	[fechaDocumento] DATE NOT NULL,
	[fechaContabilizacion] DATE NOT NULL,
	[periodo] VARCHAR(2) NOT NULL,
	[referencia] VARCHAR(16) NOT NULL,
	[tipoMovimiento] VARCHAR(3) NOT NULL,
	[observacion] VARCHAR(2000) NULL,
	[moneda] VARCHAR(5) NOT NULL,
	[documentoContable] VARCHAR(10) NULL,
	[sociedad] VARCHAR(4) NOT NULL,
	[ejercicio] VARCHAR(4) NULL,
	[nroIntentos] SMALLINT NOT NULL,
	[fecProceso] DATE NULL,
	[horaProceso] TIME NULL,
	[estadoReg] VARCHAR (1) NOT NULL,
	consecutivoTemp BIGINT NOT NULL,
	usuario VARCHAR(50) NULL,
	Componente VARCHAR(2)
)

CREATE TABLE #IC_FOVIS_Det
(
	[consecutivo] BIGINT NOT NULL,
	[codigoSap] VARCHAR (10) NULL,
	[claveCont] VARCHAR(2) NOT NULL,
	[claseCuenta] VARCHAR(1) NULL,
	[importeDocumento] VARCHAR(18) NOT NULL,
	[ref1] VARCHAR(12) NOT NULL,
	[tipoDocumento] VARCHAR(3) NOT NULL,
	[ref3] VARCHAR(20) NOT NULL,
	[tipoSector] VARCHAR(1) NOT NULL,
	[anulacion] BIT NOT NULL,
	[idProyecto] BIGINT NULL,
	[nombreProyecto] VARCHAR(250) NULL,
	[proyectoPropio] BIT NOT NULL,
	[rendimientoFinanciero] BIT NOT NULL,
	[asignacion] VARCHAR(18) NOT NULL,
	[clavePaisBanco] VARCHAR(3) NULL,
	[claveBanco] VARCHAR(15) NULL,
	[nroCuentaBancaria] VARCHAR(18) NULL,
	[titularCuenta] VARCHAR(60) NULL,
	[claveControlBanco] VARCHAR(2) NULL,
	[tipoBancoInter] VARCHAR(4) NULL,
	[referenciaBancoCuenta] VARCHAR(20) NULL,
	[codigoGenesys] BIGINT NULL,
	[tipo] VARCHAR(1) NULL,
	solFechaRadicacion DATE NOT NULL,
	fechaContabilizacion DATE NOT NULL,
	usuario VARCHAR(50) NULL,
	pofId BIGINT NULL,
	Componente VARCHAR(2)
)

	--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	-- F20: Cambio de Jefe de Hogar por fallecimiento
	--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
BEGIN TRY 
	BEGIN TRANSACTION

	
	-- DETALLE: reconce los registros
	INSERT INTO #IC_FOVIS_Det ([consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
		,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
		,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo]
		, solFechaRadicacion, fechaContabilizacion, usuario, pofId,[Componente])
SELECT DISTINCT
		ROW_NUMBER() OVER(ORDER BY pof.pofid) consecutivo,
		'' AS codigoSAP,
		'40' AS claveCont,
		'S' AS claseCuenta,
		pof.pofValorAsignadoSFV  AS importeDocumento,
		per.pernumeroidentificacion ref1,
		mi.tipoDocumentoCaja AS tipoDocumento,
		LEFT(CONCAT_WS(' ', per.perPrimerApellido, per.perSegundoApellido, per.perPrimerNombre, per.perSegundoNombre), 20) AS ref3,
		ms.tipoSectorCaja AS tipoSector,
		0 AS anulacion,		-- GAP
		'' AS idProyecto,
		'' AS nombreProyecto,
		'' AS proyectoPropio,
		0 AS rendimientoFinanciero,		--GAP
		sol.solNumeroRadicacion AS asignacion,
		'' AS clavePaisBanco,
		'' AS claveBanco,
		'' AS nroCuentaBancaria,
		'' AS titularCuenta,
		'' AS claveControlBanco,
		'0' AS tipoBancoInter,
		'' AS referenciaBancoCuenta,
		per.perid AS codigoGenesys,
		CASE WHEN per.perTipoIdentificacion = 'NIT' THEN 'E'
			ELSE 'P' END AS tipo,  
		sol.solFechaRadicacion AS solFechaRadicacion,
		CONVERT(varchar, SAP.GetLocalDate(), 111) AS fechaContabilizacion,
		solUsuarioRadicacion AS usuario,
		pof.pofId,
		CASE	WHEN pas.pasRecursoPrioridad IS NULL OR pas.pasRecursoPrioridad='RECURSO_POR_APROPIACION' THEN '1' 
				WHEN pas.pasRecursoPrioridad ='RECURSO_SEGUNDA_PRIORIDAD' THEN '2'
				WHEN pas.pasRecursoPrioridad ='RECURSO_TERCERA_PRIORIDAD' THEN '3' END AS Componente
		FROM Solicitud  sol 
		INNER JOIN SolicitudNovedadFovis solf ON sol.solId = solf.snfSolicitudGlobal
		INNER JOIN SolicitudNovedadPersonaFovis snf ON solf.snfId =snf.spfSolicitudNovedadFovis
		INNER JOIN Persona per ON  snf.spfPersona = per.perId
		INNER JOIN core.dbo.afiliado afi WITH (NOLOCK)  ON per.perId = afi.afiPersona
		INNER JOIN core.dbo.jefehogar jeh WITH (NOLOCK)  ON afi.afiId = jeh.jehAfiliado
		INNER JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK)  ON jeh.jehId = pof.pofJefeHogar
		AND pofEstadoHogar IN ('ASIGNADO_SIN_PRORROGA','ASIGNADO_CON_PRIMERA_PRORROGA', 'ASIGNADO_CON_SEGUNDA_PRORROGA', 'SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO')
		INNER JOIN IntegranteHogar inh WITH (NOLOCK)   ON jeh.jehid = inh.inhJefeHogar AND inh.inhIntegranteValido = 1 AND inh.inhIntegranteReemplazaJefeHogar = 1
		INNER JOIN PersonaDetalle ped WITH (NOLOCK)  ON per.perId = ped.pedPersona AND ped.pedFallecido = 1
		LEFT JOIN core.dbo.ProyectoSolucionVivienda psv WITH (NOLOCK)  ON pof.pofProyectoSolucionVivienda = psv.psvId
		LEFT JOIN core.sap.MaestraIdentificacion mi WITH (NOLOCK) ON per.perTipoIdentificacion = mi.tipoIdGenesys
		LEFT JOIN core.sap.MaestraSector ms WITH (NOLOCK)  ON pof.pofModalidad = ms.tipoSectorGenesys
		LEFT JOIN PostulacionAsignacion pas WITH (NOLOCK) ON  pof.pofId = pas.pasPostulacionFovis
		WHERE sol.solNumeroRadicacion = @solNumeroRadicacion
		AND solResultadoProceso = 'APROBADA'
		AND sol.solFechaRadicacion >= '2023-02-27'		
		AND per.perNumeroIdentificacion = @documentoId
		AND per.perTipoIdentificacion =   @tipoDocumento

		

	IF EXISTS (SELECT * FROM #IC_FOVIS_Det) BEGIN

		set @pofID = (select pofID from #IC_FOVIS_Det)
		-- Extrae el numero de solicitud original de la asignacion.
		UPDATE #IC_FOVIS_Det
		SET asignacion = (
				SELECT TOP 1 sol.solNumeroRadicacion 
				FROM core.dbo.PostulacionFOVIS pof WITH (NOLOCK) 
				INNER JOIN core.dbo.jefehogar jeh WITH (NOLOCK)  ON pof.pofJefeHogar = jeh.jehId
				INNER JOIN core.dbo.afiliado afi WITH (NOLOCK)  ON jehAfiliado = afi.afiid
				INNER JOIN persona per WITH (NOLOCK)  ON per.perId = afi.afiPersona
				INNER JOIN SolicitudPostulacion spo WITH (NOLOCK)  ON pof.pofId = spo.spoPostulacionFOVIS
				INNER JOIN Solicitud sol WITH (NOLOCK)  ON sol.solId = spo.spoSolicitudGlobal
				WHERE per.perNumeroIdentificacion = @documentoId
				AND per.perTipoIdentificacion = @tipoDocumento
				AND pofID = @pofID
				ORDER BY pofId DESC)
	

	IF (SELECT TOP 1 pofEstadoHogar FROM Persona per 
		INNER JOIN core.dbo.afiliado afi WITH (NOLOCK)  ON per.perId = afi.afiPersona
		INNER JOIN core.dbo.jefehogar jeh WITH (NOLOCK)  ON afi.afiId = jeh.jehAfiliado
		INNER JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK)  ON jeh.jehId = pof.pofJefeHogar
		WHERE per.perNumeroIdentificacion = @documentoId AND per.perTipoIdentificacion = @tipoDocumento 
		ORDER BY pofId DESC) = 'SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO'

	BEGIN
	 
	 SELECT @valorlegalizado = (SELECT SUM(lgd.lgdValorDesembolsar) FROM Solicitud sol
		INNER JOIN core.dbo.SolicitudLegalizacionDesembolso sld WITH (NOLOCK) ON sol.solid = sld.sldSolicitudGlobal
		INNER JOIN core.dbo.LegalizacionDesembolso lgd WITH (NOLOCK) ON sld.sldLegalizacionDesembolso = lgd.lgdId
		INNER JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK) ON pof.pofId = sld.sldPostulacionFOVIS
		INNER JOIN core.dbo.jefehogar jeh WITH (NOLOCK) ON pof.pofJefeHogar = jeh.jehId
		INNER JOIN core.dbo.afiliado afi WITH (NOLOCK) ON jehAfiliado = afi.afiid
		INNER JOIN Persona per WITH (NOLOCK) ON per.perId = afi.afiPersona
		WHERE per.perNumeroIdentificacion = @documentoId
		AND per.perTipoIdentificacion =     @tipoDocumento)

     SELECT @ImporteDocumento = (SELECT TOP 1 pofValorSFVSolicitado FROM Solicitud sol
		INNER JOIN core.dbo.SolicitudLegalizacionDesembolso sld WITH (NOLOCK) ON sol.solid = sld.sldSolicitudGlobal
		INNER JOIN core.dbo.LegalizacionDesembolso lgd WITH (NOLOCK) ON sld.sldLegalizacionDesembolso = lgd.lgdId
		INNER JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK) ON pof.pofId = sld.sldPostulacionFOVIS
		INNER JOIN core.dbo.jefehogar jeh WITH (NOLOCK) ON pof.pofJefeHogar = jeh.jehId
		INNER JOIN core.dbo.afiliado afi WITH (NOLOCK) ON jehAfiliado = afi.afiid
		INNER JOIN Persona per WITH (NOLOCK) ON per.perId = afi.afiPersona
		WHERE per.perNumeroIdentificacion = @documentoId
		AND per.perTipoIdentificacion =     @tipoDocumento)

		UPDATE #IC_FOVIS_Det SET importeDocumento = @ImporteDocumento -  @valorlegalizado

	END



		-- ENCABEZADO: crea el encabezado por cada detalle
		INSERT INTO #IC_FOVIS_Enc ([fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo]
			,[referencia],[tipoMovimiento],[observacion],[moneda],[documentoContable],[sociedad],[ejercicio]
			,[nroIntentos],[fecProceso],[horaProceso],[estadoReg], consecutivoTemp, usuario,[Componente])
		SELECT CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
			CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
			solFechaRadicacion AS fechaDocumento,  -- revisar fecha 
			fechaContabilizacion AS fechaContabilizacion, 
			MONTH(fechaContabilizacion) AS periodo, 
			'' AS referencia, 
			'F20' AS tipoMovimiento, 
			'' AS observacion, 
			'COP' AS moneda, 
			'' AS documentoContable, 
			'COMF' AS sociedad, 
			'' AS ejercicio, 
			'' AS nrointentos, 
			'' AS fecproceso, 
			'' AS horaproceso, 
			'P' AS estadoreg,
			consecutivo AS consecutivoTemp,
			usuario AS usuario,
			Componente AS Componente
		FROM #IC_FOVIS_Det

		-- CONSULTAR LOS DATOS DEL NUEVO JEFE DE HOGAR
		SELECT 
		@tipoDocumentoNuevo = mi.tipoDocumentoCaja,
		@documentoIdNuevo = per2.perNumeroIdentificacion,
		@ref3Nuevo = LEFT(CONCAT_WS(' ', per2.perPrimerApellido, per2.perSegundoApellido, per2.perPrimerNombre, per2.perSegundoNombre), 20), 
		@codigoGenesysNuevo = per2.perid
		FROM core.dbo.jefehogar jeh  WITH (NOLOCK) 
		INNER JOIN Afiliado afi WITH (NOLOCK)  ON jeh.jehAfiliado = afi.afiId
		INNER JOIN Persona per WITH (NOLOCK)  ON afi.afiPersona = per.perid
		INNER JOIN PersonaDetalle ped WITH (NOLOCK)  ON per.perId = ped.pedPersona 	AND ped.pedFallecido = 1
		INNER JOIN IntegranteHogar inh WITH (NOLOCK)  ON jeh.jehid = inh.inhJefeHogar
		INNER JOIN Persona per2 WITH (NOLOCK)  ON inh.inhPersona = per2.perId AND inh.inhIntegranteReemplazaJefeHogar = 1
		AND inh.inhIntegranteValido = 1
		LEFT JOIN core.sap.MaestraIdentificacion mi WITH (NOLOCK)  ON per2.perTipoIdentificacion = mi.tipoIdGenesys
		LEFT JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK)  ON jeh.jehId=pof.pofJefeHogar
		AND pofEstadoHogar IN ('ASIGNADO_SIN_PRORROGA','ASIGNADO_CON_PRIMERA_PRORROGA', 'ASIGNADO_CON_SEGUNDA_PRORROGA', 'SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO')
		WHERE per.perNumeroIdentificacion = @documentoId
		AND per.perTipoIdentificacion = @tipoDocumento
		--AND inh.inhEstadoHogar = 'ACTIVO' --Revisar caso que pueden reclamar subsidio hasta despues de 5 años  <=5 años select distinct spoEstadoSolicitud from SolicitudPostulacion
			
	
	
		-- REGISTRO PARA COMPLETAR EL MOVIMIENTO
		INSERT INTO #IC_FOVIS_Det([consecutivo],[codigoSap],[claveCont], [claseCuenta], [importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
			,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
			,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo]
			, solFechaRadicacion,fechaContabilizacion, usuario)
		SELECT DISTINCT
			consecutivo,
			codigoSAP,
			'50' AS claveCont,
			'S' AS claseCuenta,
			importeDocumento,
			@documentoIdNuevo,
			@tipoDocumentoNuevo,
			@ref3Nuevo,
			tipoSector,
			anulacion,		-- GAP
			idProyecto,
			nombreProyecto,
			proyectoPropio,
			rendimientoFinanciero,		--GAP
			asignacion,
			clavePaisBanco,
			claveBanco,
			nroCuentaBancaria,
			titularCuenta,
			claveControlBanco,
			tipoBancoInter,
			referenciaBancoCuenta,
			@codigoGenesysNuevo,
			tipo,
			solFechaRadicacion,
			fechaContabilizacion,
			usuario
		FROM #IC_FOVIS_Det
		WHERE claveCont = '40'
	
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		-- F20: Cambio de Jefe de Hogar por fallecimiento
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		-- INSERCION DE REGISTROS EN LAS TABLAS DE INTEGRACIoN
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		-- Calcular el valor del campo referencia	
		SELECT @referenciaNum = valorActual 
		FROM sap.IC_Referencia 
		WHERE comentario = 'AVR'
		AND estado = 'A';
	
		UPDATE #IC_FOVIS_Enc
		SET referencia = @referenciaNum;

		UPDATE sap.IC_Referencia
		SET valorActual = @referenciaNum + 1
		WHERE comentario = 'AVR'
		AND estado = 'A';

	IF @referenciaNum IS NOT NULL
	BEGIN
		INSERT INTO IC_FOVIS_Enc([fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo]
			,[referencia],[tipoMovimiento],[observacion],[moneda],[documentoContable],[sociedad],[ejercicio]
			,[nroIntentos],[fecProceso],[horaProceso],[estadoReg], usuario, consecutivoTemp,[Componente])
		SELECT [fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo]
			,[referencia],[tipoMovimiento],[observacion],[moneda],[documentoContable],[sociedad],[ejercicio]
			,[nroIntentos],[fecProceso],[horaProceso],[estadoReg], usuario, consecutivoTemp,[Componente]
		FROM #IC_FOVIS_Enc;
		
		-- Actualizar el consecutivo definitivo en la tabla de integracion de encabezado con el detalle
		SELECT @consecutivo=MAX(consecutivo) FROM IC_FOVIS_Enc

		UPDATE #IC_FOVIS_Det
		SET consecutivo = @consecutivo
	
		INSERT INTO IC_FOVIS_Det([consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
			,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
			,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo],[fechaEjecucion])
		SELECT [consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
			,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
			,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo], GETDATE()-'05:00:00'
		FROM #IC_FOVIS_Det;

		-- Insercion en la tabla de control
		INSERT INTO sap.ContablesCtrl
		SELECT @solNumeroRadicacion, @estado, @documentoId, @tipoDocumento, @fechaIngreso, @tipo, @observaciones
	END

	
		-- Detalle
		SELECT * FROM #IC_FOVIS_Enc;
		SELECT * FROM #IC_FOVIS_Det;
	
		DROP TABLE #IC_FOVIS_Enc;
		DROP TABLE #IC_FOVIS_Det;
		DROP TABLE #revTimeStamp;

		

	END --IF

	COMMIT TRANSACTION
END TRY  

BEGIN CATCH

	ROLLBACK TRANSACTION

	SELECT  ERROR_NUMBER() AS ErrorNumber  ,ERROR_SEVERITY() AS ErrorSeverity  
    ,ERROR_STATE() AS ErrorState      ,ERROR_PROCEDURE() AS ErrorProcedure  
    ,ERROR_LINE() AS ErrorLine      ,ERROR_MESSAGE() AS ErrorMessage;  

	INSERT INTO core.sap.LogErrorFovis 
			SELECT  GETDATE() -'05:00:00', @solNumeroRadicacion, @documentoId, @tipoDocumento,'F20', ERROR_NUMBER(),ERROR_SEVERITY(),ERROR_STATE(),ERROR_PROCEDURE(),ERROR_LINE(),ERROR_MESSAGE()

 
END CATCH

END