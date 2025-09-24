
-- ====================================================================================================
-- Author: MARLON VALBUENA
-- Create date: MAYO 10 DE 2021
-- Description: Extrae la informacion de los movimientos contables para la integracion de FOVIS 
-- Modificado: Yesika Bernal, adicion tabla generacion de errores , 
-- Nota Yesika Bernal,  30 ABRIL 2024 ajuste GAP 0040 glpi 80014 
-- =====================================================================================================
CREATE OR ALTER   PROCEDURE [sap].[USP_GetICFOVIS_Insert_F03] 
	@solNumeroRadicacion VARCHAR(20), @estado BIT, @documentoId nvarchar(20), @tipoDocumento nvarchar(20), @fechaIngreso DATETIME, @tipo VARCHAR(10), @observaciones VARCHAR(150)
AS
BEGIN
SET NOCOUNT ON;
 
DECLARE	@referencia VARCHAR(16), @referenciaNum BIGINT, @consecutivo BIGINT, @pofID BIGINT;
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
	-- F03: Registro Vencido a subsidio de vivienda
	--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
BEGIN TRY 
	BEGIN TRANSACTION




	-- DETALLE: reconce los registros
	INSERT INTO #IC_FOVIS_Det ([consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
		,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
		,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo]
		, solFechaRadicacion, fechaContabilizacion, usuario, pofId, Componente)
SELECT DISTINCT
		ROW_NUMBER() OVER(ORDER BY pof.pofid) consecutivo,
		'' AS codigoSAP,
		'40' AS claveCont,
		'S' AS claseCuenta,
		pof.pofValorAsignadoSFV importeDocumento,
		LEFT (per.pernumeroidentificacion,12) ref1,
		mi.tipoDocumentoCaja AS tipoDocumento,
		LEFT(CONCAT_WS(' ', per.perPrimerApellido, per.perSegundoApellido, per.perPrimerNombre, per.perSegundoNombre), 20) AS ref3,
		ms.tipoSectorCaja AS tipoSector,
		0 AS anulacion,		-- GAP
		NULL AS idProyecto,
		NULL AS nombreProyecto,
		'' AS proyectoPropio,
		0 AS rendimientoFinanciero,		--GAP
		sol.solNumeroRadicacion AS asignacion,
		'CO' AS clavePaisBanco,
		'' AS claveBanco,
		'' AS nroCuentaBancaria,
		'' AS titularCuenta,
		'' AS claveControlBanco,
		'0' AS tipoBancoInter,
		per.perNumeroIdentificacion AS referenciaBancoCuenta,
		per.perid AS codigoGenesys,
		CASE mi.tipoDocumentoCaja WHEN 'NIT' then 'E' ELSE 'P' END AS tipo, 
		(select top 1	case when pafPlazoVencimiento ='MESES' then 
						case when pofEstadoHogar in ('VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA','VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA') then try_cast(concat(year(dateadd(month,1, dateadd (day,pafValorAdicional, dateadd(month,pafValorNumerico,aafFechaPublicacion)))) , '-', month(dateadd(month,1, dateadd (day, pafValorAdicional, dateadd(month,pafValorNumerico,aafFechaPublicacion)))),'-01')as datetime)
						else try_cast(concat(year(dateadd(month,1,dateadd(month,pafValorNumerico,aafFechaPublicacion))) , '-', month(dateadd(month,1,dateadd(month,pafValorNumerico,aafFechaPublicacion))),'-01')as datetime) end
						when pafPlazoVencimiento ='AÑOS' then try_cast(concat(year(dateadd(month,1,dateadd(year,pafValorNumerico,aafFechaPublicacion))) , '-', month(dateadd(month,1,dateadd(month,pafValorNumerico,aafFechaPublicacion))),'-01')as datetime)
	 					end
								from ActaAsignacionFovis
		where aafSolicitudAsignacion=saf.safid order by aaffechaactaasignacionfovis desc
	 )as solFechaRadicacion, --aafFinVigencia AS solFechaRadicacion,
		sol.solFechaRadicacion AS fechaContabilizacion,
		sol.solUsuarioRadicacion AS usuario,
		pof.pofId,
		CASE	WHEN pas.pasRecursoPrioridad IS NULL OR pas.pasRecursoPrioridad='RECURSO_POR_APROPIACION' THEN '1' 
				WHEN pas.pasRecursoPrioridad ='RECURSO_SEGUNDA_PRIORIDAD' THEN '2'
				WHEN pas.pasRecursoPrioridad ='RECURSO_TERCERA_PRIORIDAD' THEN '3' END AS Componente
	FROM core.dbo.Persona PER WITH (NOLOCK)
	INNER JOIN core.dbo.Afiliado AFI WITH (NOLOCK) ON  PER.perId = AFI.afiPersona
	INNER JOIN core.dbo.jefehogar JEH WITH (NOLOCK) ON AFI.afiId = JEH.jehAfiliado
	INNER JOIN core.dbo.PostulacionFOVIS POF WITH (NOLOCK) ON JEH.jehId = POF.pofJefeHogar 
	--AND pof.pofEstadoHogar IN ('VENCIMIENTO_POR_ASIGNACION_SIN_SEGUNDA_PRORROGA','VENCIMIENTO_POR_ASIGNACION_CON_SEGUNDA_PRORROGA_CADUCADA','VENCIMIENTO_POR_ASIGNACION_SIN_PRORROGA')
	INNER JOIN PostulacionAsignacion pas WITH (NOLOCK) ON  pof.pofId = pas.pasPostulacionFovis
	left join ParametrizacionFOVIS WITH (NOLOCK) ON pafnombre  like '%VENCIMIENTO%' and  pofEstadoHogar like '%VENCIMIENTO%' 
	and case when substring (pofEstadoHogar,28,25) <> substring(pafNombre,19,30)  THEN  substring (pofEstadoHogar,32,16)  else
	substring(pofEstadoHogar,28,25) END =   substring(pafNombre,19,30) 
	INNER JOIN core.dbo.SolicitudAsignacion saf WITH (NOLOCK) ON saf.safId = pof.pofSolicitudAsignacion
	INNER JOIN core.dbo.ActaAsignacionFovis aaf WITH (NOLOCK) ON aaf.aafSolicitudAsignacion = saf.safId
	INNER JOIN core.dbo.SolicitudNovedadPersonaFovis SPF WITH (NOLOCK) ON POF.pofId = SPF.spfPostulacionFovis
	INNER JOIN core.dbo.SolicitudNovedadFovis SNF WITH (NOLOCK) ON SPF.spfSolicitudNovedadFovis = SNF.snfId
	INNER JOIN core.dbo.Solicitud SOL WITH (NOLOCK) ON SNF.snfSolicitudGlobal = SOL.solId AND YEAR(sol.solFechaRadicacion) >= 2022
	INNER JOIN core.SAP.MaestraIdentificacion MI WITH (NOLOCK) ON PER.perTipoIdentificacion = MI.tipoIdGenesys
	INNER JOIN core.SAP.MaestraSector MS WITH (NOLOCK) ON POF.pofModalidad = MS.tipoSectorGenesys
	WHERE  per.perNumeroIdentificacion = @documentoId
	AND per.perTipoIdentificacion = @tipoDocumento
	AND sol.solNumeroRadicacion = @solNumeroRadicacion
	AND aaf.aafFinVigencia <= sol.solFechaRadicacion


	SET @pofID =(SELECT top 1 pofId FROM #IC_FOVIS_Det)
	IF EXISTS (select * FROM #IC_FOVIS_Det) BEGIN
		-- Extrae el numero de solicitud original de la asignacion.
		UPDATE #IC_FOVIS_Det
		SET asignacion = (
				SELECT TOP 1 sol.solNumeroRadicacion 
				FROM core.dbo.PostulacionFOVIS pof WITH (NOLOCK)
				INNER JOIN core.dbo.jefehogar jeh WITH (NOLOCK) ON pof.pofJefeHogar = jeh.jehId
				INNER JOIN core.dbo.afiliado afi WITH (NOLOCK) ON jehAfiliado = afi.afiid
				INNER JOIN core.dbo.persona per WITH (NOLOCK) ON per.perId = afi.afiPersona
				INNER JOIN core.dbo.SolicitudPostulacion spo WITH (NOLOCK) ON pof.pofId = spo.spoPostulacionFOVIS
				INNER JOIN core.dbo.Solicitud sol WITH (NOLOCK) ON sol.solId = spo.spoSolicitudGlobal
				WHERE per.perNumeroIdentificacion = @documentoId
				AND per.perTipoIdentificacion = @tipoDocumento
				AND pofId = @pofID)
		-- Campos en NULL de acuerdo al documento de homologacion de campos
		UPDATE #IC_FOVIS_Det
		SET idProyecto = '', nombreProyecto = '', proyectoPropio = '', rendimientoFinanciero = '', clavePaisBanco = '',
		nrocuentabancaria = '', titularcuenta = '', clavecontrolbanco = '', claveBanco = '', tipoBancoInter = '', referenciaBancoCuenta = ''

		-- ENCABEZADO: crea el encabezado por cada detalle
		INSERT INTO #IC_FOVIS_Enc ([fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo]
			,[referencia],[tipoMovimiento],[observacion],[moneda],[documentoContable],[sociedad],[ejercicio]
			,[nroIntentos],[fecProceso],[horaProceso],[estadoReg], consecutivoTemp, usuario, Componente)
		SELECT CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
			CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
			solFechaRadicacion AS fechaDocumento, 
			fechaContabilizacion AS fechaContabilizacion, 
			MONTH(fechaContabilizacion) AS periodo, 
			'' AS referencia, 
			'F03' AS tipoMovimiento, 
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
			@documentoId,
			tipoDocumento,
			ref3,
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
			codigoGenesys,
			tipo,
			solFechaRadicacion,
			fechaContabilizacion,
			usuario
		FROM #IC_FOVIS_Det
		WHERE claveCont = '40'
	
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		-- F03: Registro Vencido a subsidio de vivienda
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

	/*
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
			,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo],fechaEjecucion)
		SELECT [consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
			,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
			,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo],GETDATE() -'05:00:00'
		FROM #IC_FOVIS_Det;

		-- Insercion en la tabla de control
		INSERT INTO sap.ContablesCtrl
		SELECT @solNumeroRadicacion, @estado, @documentoId, @tipoDocumento, @fechaIngreso, @tipo, @observaciones

	END
	*/
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
			SELECT  GETDATE() -'05:00:00', @solNumeroRadicacion, @documentoId, @tipoDocumento,'F03', ERROR_NUMBER(),ERROR_SEVERITY(),ERROR_STATE(),ERROR_PROCEDURE(),ERROR_LINE(),ERROR_MESSAGE()

 
END CATCH

END