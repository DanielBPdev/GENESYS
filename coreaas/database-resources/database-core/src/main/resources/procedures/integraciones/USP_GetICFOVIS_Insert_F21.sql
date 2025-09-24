-- ====================================================================================================
-- Author: VILLAMARIN JULIAN
-- Create date: MAYO 10 DE 2021
-- Description: Extrae la informacion de los movimientos contables para la integracion de FOVIS 
-- Modificado: Yesika Bernal, adicion tabla generacion de errores 
-- =====================================================================================================
CREATE OR ALTER PROCEDURE [sap].[USP_GetICFOVIS_Insert_F21] 
	@solNumeroRadicacion VARCHAR(20), @estado BIT, @documentoId nvarchar(20), @tipoDocumento nvarchar(20), @fechaIngreso DATETIME, @tipo VARCHAR(10), @observaciones VARCHAR(150)


AS
BEGIN
SET NOCOUNT ON;
 
DECLARE	@referencia VARCHAR(16), @referenciaNum BIGINT, @consecutivo BIGINT

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
	[Componente] VARCHAR(2)
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
	tipo VARCHAR(1) NULL,
	solFechaRadicacion DATE NOT NULL,
	aafFechaOficio DATE NULL,
	usuario VARCHAR(50) NULL,
	[Componente] VARCHAR(2)
)

	--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	-- F05: Registro Restitucion o Reembolso con pago Anticipado
	--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
BEGIN TRY 
	BEGIN TRANSACTION

	-- DETALLE: reconoce los registros
	INSERT INTO #IC_FOVIS_Det ([consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
		,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
		,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],tipo
		, solFechaRadicacion, aafFechaOficio, usuario,[Componente])
	SELECT DISTINCT
		ROW_NUMBER() OVER(ORDER BY pof.pofid) consecutivo,
		'' AS codigoSAP,
		'40' AS claveCont,
		'S' AS claseCuenta,
		pof.pofValorSFVAjustado-pof.pofValorAsignadoSFV AS importeDocumento, --mayor: psfVasigando o ajustado
		LEFT (per.pernumeroidentificacion, 12) ref1,
		mi.tipoDocumentoCaja AS tipoDocumento,
		LEFT(CONCAT_WS(' ', per.perPrimerApellido, per.perSegundoApellido, per.perPrimerNombre, per.perSegundoNombre), 20) AS ref3,
		ms.tipoSectorCaja AS tipoSector,
		0 AS anulacion,		-- GAP
		psv.psvId AS idProyecto,
		LEFT(psv.psvNombreProyecto, 250) AS nombreProyecto,
		CASE WHEN perOfe.perNumeroIdentificacion = (SELECT CAST(SUBSTRING(prmValor, 1, CASE WHEN CHARINDEX('-', prmValor) > 0 THEN CHARINDEX('-', prmValor)-1 ELSE LEN(prmValor) END) AS INT) FROM Parametro WHERE prmNombre = 'NUMERO_ID_CCF') THEN 1 --NIT DE COMFENALCO PARA DETERMINAR QUE ES PROYECTO PROPIO '890900842'
		ELSE 0 END AS proyectoPropio,
		0 AS rendimientoFinanciero,		--GAP
		sol.solNumeroRadicacion AS asignacion,
		'CO' AS clavePaisBanco,
		ban.banCodigoPILA - 1000 AS claveBanco,
		ofe.ofeCuentaBancaria AS nroCuentaBancaria,
		LEFT(ofe.ofeNombreTitularCuenta,60) AS titularCuenta,
		CASE WHEN ofe.ofeTipoCuenta = 'CORRIENTE' THEN '01'
		WHEN ofe.ofeTipoCuenta = 'AHORROS' THEN '02'
		WHEN ofe.ofeTipoCuenta = 'DAVIPLATA' THEN '03'
		END AS claveControlBanco,
		'0' AS tipoBancoInter,
		per.perNumeroIdentificacion AS referenciaBancoCuenta,
		per.perid AS codigoGenesys,
		CASE mi.tipoDocumentoCaja WHEN 'NIT' then 'E' ELSE 'P' END AS tipo,
		sol.solFechaRadicacion AS solFechaRadicacion,
		CONVERT (varchar, SAP.GetLocalDate(), 111)  AS aafFechaOficio,
		sol.solUsuarioRadicacion AS usuario,
		CASE	WHEN pas.pasRecursoPrioridad IS NULL OR pas.pasRecursoPrioridad='RECURSO_POR_APROPIACION' THEN '1' 
				WHEN pas.pasRecursoPrioridad ='RECURSO_SEGUNDA_PRIORIDAD' THEN '2'
				WHEN pas.pasRecursoPrioridad ='RECURSO_TERCERA_PRIORIDAD' THEN '3' END AS Componente
		FROM Solicitud sol 
		INNER JOIN SolicitudNovedadFovis snf WITH (NOLOCK) ON sol.solid = snf.snfSolicitudGlobal
		INNER JOIN SolicitudNovedadPersonaFovis spf WITH (NOLOCK) ON snf.snfId = spf.spfSolicitudNovedadFovis
		INNER JOIN PostulacionFOVIS pof WITH (NOLOCK) ON pof.pofId = spf.spfPostulacionFovis 
		AND pof.pofEstadoHogar IN ('ASIGNADO_SIN_PRORROGA','ASIGNADO_CON_PRIMERA_PRORROGA', 'ASIGNADO_CON_SEGUNDA_PRORROGA')
		INNER JOIN jefehogar jeh WITH (NOLOCK)  ON pof.pofJefeHogar = jeh.jehId
		INNER JOIN afiliado afi WITH (NOLOCK)  ON jehAfiliado = afi.afiid
		INNER JOIN persona per WITH (NOLOCK)  ON per.perId = afi.afiPersona
		LEFT JOIN ProyectoSolucionVivienda psv WITH (NOLOCK)  ON pof.pofProyectoSolucionVivienda = psv.psvId
		LEFT JOIN Oferente ofe WITH (NOLOCK)  ON ofe.ofeId = psv.psvOferente
		LEFT JOIN Persona perOfe WITH (NOLOCK)  ON perOfe.perId = ofe.ofePersona
		LEFT JOIN Banco ban WITH (NOLOCK) ON ofe.ofeBanco = ban.banId
		LEFT JOIN SolicitudAsignacion saf WITH (NOLOCK)  ON pof.pofSolicitudAsignacion = saf.safId
		LEFT JOIN ActaAsignacionFovis aaf WITH (NOLOCK)  ON saf.safid = aaf.aafSolicitudAsignacion
		LEFT JOIN sap.MaestraIdentificacion mi WITH (NOLOCK)  ON per.perTipoIdentificacion = mi.tipoIdGenesys
		LEFT JOIN sap.MaestraSector ms WITH (NOLOCK)  ON pof.pofModalidad = ms.tipoSectorGenesys
		LEFT JOIN PostulacionAsignacion pas WITH (NOLOCK) ON  pof.pofId = pas.pasPostulacionFovis
		WHERE per.perNumeroIdentificacion = @documentoId
		AND per.perTipoIdentificacion = @tipoDocumento
		AND YEAR(sol.solFechaRadicacion) >= 2022
		AND pof.pofValorSFVAjustado-pof.pofValorAsignadoSFV > 0

	IF EXISTS (select * FROM #IC_FOVIS_Det) BEGIN
		-- Campos en NULL de acuerdo al documento de homologacion de campos
		UPDATE #IC_FOVIS_Det
		SET idProyecto = '', nombreProyecto = '', proyectoPropio = '', rendimientoFinanciero = '', clavePaisBanco = '', 
		nrocuentabancaria = '', titularcuenta = '', clavecontrolbanco = '', claveBanco = '', tipoBancoInter = '', referenciaBancoCuenta = ''
	
		-- ENCABEZADO: crea el encabezado por cada detalle
		INSERT INTO #IC_FOVIS_Enc ([fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo]
			,[referencia],[tipoMovimiento],[observacion],[moneda],[documentoContable],[sociedad],[ejercicio]
			,[nroIntentos],[fecProceso],[horaProceso],[estadoReg], consecutivoTemp, usuario,[Componente])
		SELECT CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
			CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
			solFechaRadicacion AS fechaDocumento, 
			aafFechaOficio AS fechaContabilizacion, 
			MONTH(aafFechaOficio) AS periodo, 
			'' AS referencia, 
			'F21' AS tipoMovimiento, 
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
		INSERT INTO #IC_FOVIS_Det([consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
			,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
			,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo]
			,solFechaRadicacion, aafFechaOficio, usuario)
		SELECT DISTINCT
			consecutivo,
			codigoSAP,
			'50' AS claveCont,
			'S' AS claseCuenta,
			importeDocumento,
			ref1,
			tipoDocumento,
			ref3,
			tipoSector,
			anulacion,					-- GAP
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
			aafFechaOficio,
			usuario
		FROM #IC_FOVIS_Det
		WHERE claveCont = '40'

			-- Extrae el número de solicitud original de la asignacion.
		UPDATE #IC_FOVIS_Det
		SET asignacion = (
		SELECT top 1 sol.solNumeroRadicacion
		FROM postulacionFOVIS pof 
		INNER JOIN jefehogar jeh WITH (NOLOCK) ON pof.pofJefeHogar = jeh.jehId
		INNER JOIN afiliado afi WITH (NOLOCK) ON jehAfiliado = afi.afiid
		INNER JOIN persona per WITH (NOLOCK) ON per.perId = afi.afiPersona
		INNER JOIN SolicitudPostulacion spo WITH (NOLOCK) ON pof.pofId = spo.spoPostulacionFOVIS
		INNER JOIN Solicitud sol WITH (NOLOCK) ON sol.solId = spo.spoSolicitudGlobal
		WHERE pof.pofEstadoHogar IN ('ASIGNADO_SIN_PRORROGA','ASIGNADO_CON_PRIMERA_PRORROGA', 'ASIGNADO_CON_SEGUNDA_PRORROGA')
		AND per.perNumeroIdentificacion = @documentoId
		AND per.perTipoIdentificacion = @tipoDocumento
		ORDER BY pofid desc	)

	
	--select * from #IC_FOVIS_Enc
	--select * FROM #IC_FOVIS_Det
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		-- FIN F05: Registro Restitucion o Reembolso con pago Anticipado
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
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		-- INSERCION DE REGISTROS EN LAS TABLAS DE INTEGRACIoN
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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

	END --IF

	COMMIT TRANSACTION
END TRY  

BEGIN CATCH

	ROLLBACK TRANSACTION

	SELECT  ERROR_NUMBER() AS ErrorNumber  ,ERROR_SEVERITY() AS ErrorSeverity  
    ,ERROR_STATE() AS ErrorState,ERROR_PROCEDURE() AS ErrorProcedure  
    ,ERROR_LINE() AS ErrorLine,ERROR_MESSAGE() AS ErrorMessage;  

	INSERT INTO core.sap.LogErrorFovis 
			SELECT  GETDATE() -'05:00:00', @solNumeroRadicacion, @documentoId, @tipoDocumento,'F21', ERROR_NUMBER(),ERROR_SEVERITY(),ERROR_STATE(),ERROR_PROCEDURE(),ERROR_LINE(),ERROR_MESSAGE()

 
END CATCH
END