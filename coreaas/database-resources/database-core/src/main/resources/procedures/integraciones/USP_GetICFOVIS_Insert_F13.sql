
-- ====================================================================================================
-- Author: VILLAMARIN JULIAN
-- Create date: MAYO 10 DE 2021
-- Description: Extrae la informacion de los movimientos contables para la integracion de FOVIS 
-- Modificado: Yesika Bernal, adicion tabla generacion de errores 
-- =====================================================================================================

-- ====================================================================================================
-- FALTA DEFINIR LA DIFERENCIA CAMPO DE INTERESES, TIPO DE PAGO, EN:
-- Enero 18 de 2022: Seguimiento de Julian Villamarin GLPI 51321 (GAP034 - Novedad de restitucion o 
-- reembolso con rendimientos financieros), esta pendiente por documentacion por parte de Jackson.
-- =====================================================================================================

CREATE OR ALTER PROCEDURE [sap].[USP_GetICFOVIS_Insert_F13] 
	@solNumeroRadicacion VARCHAR(20), @estado BIT, @documentoId nvarchar(20), @tipoDocumento nvarchar(20), @fechaIngreso DATETIME, @tipo VARCHAR(10), @observaciones VARCHAR(150)
AS
BEGIN
SET NOCOUNT ON;
 
DECLARE	@referencia VARCHAR(16), @referenciaNum BIGINT, @consecutivo BIGINT, @pofID BIGINT;
DECLARE @revTimeStamp BIGINT, @fechaContabilizacion DATETIME;
declare @json nvarchar(max)
                set @json = (select dtsJsonPayload from Solicitud sol INNER JOIN DatoTemporalSolicitud ds ON sol.solId =ds.dtsSolicitud  where solNumeroRadicacion =@solNumeroRadicacion)



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
	tipo VARCHAR(1) NULL,
	solFechaRadicacion DATE NOT NULL,
	fechaContabilizacion DATE NOT NULL,
	usuario VARCHAR(50) NULL,
	pofId BIGINT NULL,
	Componente VARCHAR(2)
)

	--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	-- F13: Reintegros (Restitucion) mediante consignacion bancaria
	--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

BEGIN TRY 
	BEGIN TRANSACTION

	-- DETALLE: reconoce los registros
	INSERT INTO #IC_FOVIS_Det ([consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
		,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
		,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo]
		, solFechaRadicacion, fechaContabilizacion, usuario, pofId,Componente)
	SELECT DISTINCT
                ROW_NUMBER() OVER(ORDER BY pof.pofid) consecutivo,
                '' AS codigoSAP,
                '50' AS claveCont,
                'S' AS claseCuenta,
                (select importeDocumento from openjson(@json, '$.datosNovedadRegularFovisDTO')
                with(
                        importeDocumento BIGINT '$.valorRestituir'
                ))
				
				AS importeDocumento, 
                per.pernumeroidentificacion ref1,
				mi.tipoDocumentoCaja AS tipoDocumento,
				LEFT(CONCAT_WS(' ', per.perPrimerApellido, per.perSegundoApellido, per.perPrimerNombre, per.perSegundoNombre), 20) AS ref3,
                ms.tipoSectorCaja AS tipoSector,
                0 AS anulacion,                -- GAP
                psv.psvId AS idProyecto,
                LEFT(psv.psvNombreProyecto, 250) AS nombreProyecto,
                CASE WHEN perOfe.perNumeroIdentificacion = (SELECT CAST(SUBSTRING(prmValor, 1, CASE WHEN CHARINDEX('-', prmValor) > 0 THEN CHARINDEX('-', prmValor)-1 ELSE LEN(prmValor) END) AS INT) FROM Parametro WHERE prmNombre = 'NUMERO_ID_CCF') THEN 1 --NIT DE COMFENALCO PARA DETERMINAR QUE ES PROYECTO PROPIO '890900842'
                        ELSE 0 END AS proyectoPropio,
                0 AS rendimientoFinanciero,                --GAP
                '' AS asignacion,
                'CO' AS clavePaisBanco,
                ban.banCodigoPILA - 1000 AS claveBanco,
                ofe.ofeCuentaBancaria AS nroCuentaBancaria,
                LEFT(ofe.ofeNombreTitularCuenta,60) AS titularCuenta,
                CASE WHEN ofe.ofeTipoCuenta = 'CORRIENTE' THEN '01'
                WHEN ofe.ofeTipoCuenta = 'AHORROS' THEN '02'
                WHEN ofe.ofeTipoCuenta = 'DAVIPLATA' THEN '03'
                END AS claveControlBanco,
                '0' AS tipoBancoInter,
                per.perNUmeroIdentificacion AS referenciaBancoCuenta,
                per.perid AS codigoGenesys,
                'P' AS tipo,
                sol.solFechaRadicacion AS solFechaRadicacion,
                sol.solFechaRadicacion AS fechaContabilizacion,
                sol.solUsuarioRadicacion AS usuario,
                pof.pofId,
		CASE	WHEN pas.pasRecursoPrioridad IS NULL OR pas.pasRecursoPrioridad='RECURSO_POR_APROPIACION' THEN '1' 
				WHEN pas.pasRecursoPrioridad ='RECURSO_SEGUNDA_PRIORIDAD' THEN '2'
				WHEN pas.pasRecursoPrioridad ='RECURSO_TERCERA_PRIORIDAD' THEN '3' END AS Componente
        FROM Solicitud sol 
        INNER JOIN SolicitudNovedadFovis snf ON sol.solid = snf.snfSolicitudGlobal
        INNER JOIN SolicitudNovedadPersonaFovis spf ON snf.snfId = spf.spfSolicitudNovedadFovis
        INNER JOIN Persona per ON per.perId = spf.spfPersona
        INNER JOIN PostulacionFOVIS pof ON pof.pofId = spf.spfPostulacionFovis
		AND pof.pofEstadoHogar IN ('RESTITUIDO_SIN_SANCION','RESTITUIDO_CON_SANCION','SUBSIDIO_REEMBOLSADO')
		AND pof.pofMotivoDesistimiento IS NULL
        INNER JOIN DatoTemporalSolicitud ds ON sol.solId =ds.dtsSolicitud  
        LEFT JOIN ProyectoSolucionVivienda psv ON pof.pofProyectoSolucionVivienda = psv.psvId
        LEFT JOIN Oferente ofe ON ofe.ofeId = psv.psvOferente
        LEFT JOIN Persona perOfe ON perOfe.perId = ofe.ofePersona
        LEFT JOIN Banco ban ON ofe.ofeBanco = ban.banId
        LEFT JOIN sap.MaestraIdentificacion mi on per.perTipoIdentificacion = mi.tipoIdGenesys
        LEFT JOIN sap.MaestraSector ms on pof.pofModalidad = ms.tipoSectorGenesys
		LEFT JOIN PostulacionAsignacion pas WITH (NOLOCK) ON  pof.pofId = pas.pasPostulacionFovis
        WHERE YEAR(sol.solFechaRadicacion) >= 2023 -- VERIFICAR AÑO
        AND per.perNumeroIdentificacion = @documentoId
        AND per.perTipoIdentificacion = @tipoDocumento
		AND solNumeroRadicacion =@solNumeroRadicacion
        AND (select medioPago from openjson(@json, '$.datosNovedadRegularFovisDTO')
                with(
                        medioPago varchar(20) '$.medioPago'
                )) <> 'EFECTIVO'
		AND (select importeDocumento from openjson(@json, '$.datosNovedadRegularFovisDTO')
                with(
                        importeDocumento BIGINT '$.valorRestituir'
                ))
= (SELECT pof.pofValorAsignadoSFV
								FROM PostulacionFOVIS pof 
								INNER JOIN jefehogar jeh ON pof.pofJefeHogar = jeh.jehId
								INNER JOIN afiliado afi ON jehAfiliado = afi.afiid
								INNER JOIN Persona per ON per.perId = afi.afiPersona
								WHERE pof.pofMotivoDesistimiento IS NULL
								AND per.perNumeroIdentificacion = @documentoId
								AND per.perTipoIdentificacion = @tipoDocumento)
	


	IF EXISTS (select * FROM #IC_FOVIS_Det) BEGIN

		SELECT @pofID = pofId
		FROM #IC_FOVIS_Det;

		-- Campos en NULL de acuerdo al documento de homologacion de campos
		UPDATE #IC_FOVIS_Det
		SET idProyecto = '', nombreProyecto = '', clavePaisBanco = '',
		nrocuentabancaria = '', titularcuenta = '', clavecontrolbanco = '', claveBanco = '', tipoBancoInter = '', referenciaBancoCuenta = ''

		-- Extrae el numero de solicitud original de la asignacion.
		UPDATE #IC_FOVIS_Det
		SET asignacion = (
		SELECT top 1 sol.solNumeroRadicacion
		FROM postulacionFOVIS pof 
		INNER JOIN jefehogar jeh ON pof.pofJefeHogar = jeh.jehId
		INNER JOIN afiliado afi ON jehAfiliado = afi.afiid
		INNER JOIN persona per ON per.perId = afi.afiPersona
		INNER JOIN SolicitudPostulacion spo ON pof.pofId = spo.spoPostulacionFOVIS
		INNER JOIN Solicitud sol ON sol.solId = spo.spoSolicitudGlobal
		WHERE pof.pofEstadoHogar IN ('RESTITUIDO_SIN_SANCION','RESTITUIDO_CON_SANCION','SUBSIDIO_REEMBOLSADO')
		AND per.perNumeroIdentificacion = @documentoId
		AND per.perTipoIdentificacion = @tipoDocumento
		AND pofID = @pofID
		ORDER BY pofid desc	)

			-- INICIO: Calcular la fecha de contabilizacion (FECHA DE APROBACION) desde la base de datos de auditoria ya sea local en CORE o posteriormente en COREAUD
		

		INSERT INTO #revTimeStamp
		SELECT revTimeStamp, ''
		FROM aud.PostulacionFOVIS_aud
		INNER JOIN aud.Revision on revid = rev 
		WHERE pofEstadoHogar IN ('RESTITUIDO_SIN_SANCION','RESTITUIDO_CON_SANCION','SUBSIDIO_REEMBOLSADO') 
		AND pofId = @pofID 

		IF NOT EXISTS (SELECT * FROM #revTimeStamp) BEGIN
			INSERT INTO #revTimeStamp
			EXEC sp_execute_remote @data_source_name = N'CoreAudReferenceData',  
			@stmt = N'SELECT revTimeStamp FROM PostulacionFOVIS_aud INNER JOIN Revision on revid = rev WHERE pofEstadoHogar = ''SUBSIDIO_REEMBOLSADO'' AND pofid = @pofid',
			@params = N'@pofid BIGINT',
			@pofID = @pofID;
		END

		SELECT @revTimeStamp = revTimeStamp 
		FROM #revTimeStamp

		SELECT @fechaContabilizacion = CAST(DATEADD(ms, CAST(RIGHT(@revTimeStamp,3) AS SMALLINT), DATEADD(s, @revTimeStamp / 1000, '1970-01-01'))-'05:00:00' AS DATETIME2(3))

		UPDATE #IC_FOVIS_Det
		SET fechaContabilizacion = @fechaContabilizacion
		WHERE @fechaContabilizacion IS NOT NULL

		-- fIN: Calcular la fecha de contabilizacion (FECHA DE APROBACION) desde la base de datos de auditoria
	
		-- ENCABEZADO: crea el encabezado por cada detalle
		INSERT INTO #IC_FOVIS_Enc ([fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo]
			,[referencia],[tipoMovimiento],[observacion],[moneda],[documentoContable],[sociedad],[ejercicio]
			,[nroIntentos],[fecProceso],[horaProceso],[estadoReg], consecutivoTemp, usuario,Componente)
		SELECT CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
			CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
			solFechaRadicacion AS fechaDocumento, 
			fechaContabilizacion AS fechaContabilizacion, 
			MONTH(fechaContabilizacion) AS periodo, 
			'' AS referencia, 
			'F13' AS tipoMovimiento, 
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
			Componente  AS Componente
		FROM #IC_FOVIS_Det
	
		-- REGISTRO PARA COMPLETAR EL MOVIMIENTO CON EL VALOR
		INSERT INTO #IC_FOVIS_Det([consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
			,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
			,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo]
			, solFechaRadicacion, fechaContabilizacion, usuario, pofId)
		SELECT DISTINCT
			consecutivo,
			codigoSAP,
			'50' AS claveCont,
			'S' AS claseCuenta,
			(select rendimientoFinanciero from openjson(@json, '$.datosNovedadRegularFovisDTO')
                with(
                        rendimientoFinanciero BIGINT '$.rendimientoFinanciero'
                )) as importeDocumento,	
			ref1,
			tipoDocumento,
			ref3,
			tipoSector,
			anulacion,		-- GAP
			idProyecto,
			nombreProyecto,
			proyectoPropio,
			1 as rendimientoFinanciero,		--GAP
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
			usuario, 
			pofId
		FROM #IC_FOVIS_Det
		WHERE claveCont = '50'

		-- REGISTRO PARA COMPLETAR EL MOVIMIENTO CON EL VALOR DE INTERESES
		INSERT INTO #IC_FOVIS_Det([consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
			,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
			,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo]
			, solFechaRadicacion, fechaContabilizacion, usuario, pofId)
		SELECT DISTINCT
			consecutivo,
			codigoSAP,
			'40' AS claveCont,
			'S' AS claseCuenta,
			(select importeDocumento from openjson(@json, '$.datosNovedadRegularFovisDTO')
                with(
                        importeDocumento BIGINT '$.valorRestituir'
                ))
				+
				(select rendimientoFinanciero from openjson(@json, '$.datosNovedadRegularFovisDTO')
                with(
                        rendimientoFinanciero BIGINT '$.rendimientoFinanciero'
                )) AS importeDocumento,		--GAP MODIFICAR LOS VALORES EN LAS 3 CLAVES DE CONTABILIZACION
			(select ref1 from openjson(@json, '$.datosNovedadRegularFovisDTO')
                with(
                        ref1 BIGINT '$.numeroIdQuienHaceDevolucion'
                )) AS ref1,
                (select tipoDocumentoCaja from sap.MaestraIdentificacion
				where tipoIdGenesys = (select tipoDocumento from openjson(@json, '$.datosNovedadRegularFovisDTO')
                with(
                        tipoDocumento varchar(20) '$.tipoIdQuienHaceDevolucion'
                ))) AS tipoDocumento,
                (select ref3 from openjson(@json, '$.datosNovedadRegularFovisDTO')
                with(
                        ref3 varchar(20) '$.nombreCompleto'
                )) AS ref3,
			tipoSector,
			anulacion,		-- GAP
			idProyecto,
			nombreProyecto,
			proyectoPropio,
			0 as rendimientoFinanciero,				--GAP
			asignacion,
			clavePaisBanco,
			claveBanco,
			nroCuentaBancaria,
			titularCuenta,
			claveControlBanco,
			tipoBancoInter,
			referenciaBancoCuenta,
			(select perid from persona where perNumeroIdentificacion = (select ref1 from openjson(@json, '$.datosNovedadRegularFovisDTO')
                with(
                        ref1 varchar (max) '$.numeroIdQuienHaceDevolucion'
                )))codigoGenesys,
			tipo,
			solFechaRadicacion,
			fechaContabilizacion,
			usuario, 
			pofId
		FROM #IC_FOVIS_Det
		WHERE claveCont = '50'
	
		--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		-- F13: Reintegros (Restitucion) mediante consignacion bancaria
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
			,[nroIntentos],[fecProceso],[horaProceso],[estadoReg], usuario, consecutivoTemp,Componente)
		SELECT [fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo]
			,[referencia],[tipoMovimiento],[observacion],[moneda],[documentoContable],[sociedad],[ejercicio]
			,[nroIntentos],[fecProceso],[horaProceso],[estadoReg], usuario, consecutivoTemp,Componente
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
			,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo],GETDATE()-'05:00:00'
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
	,ERROR_STATE() AS ErrorState,ERROR_PROCEDURE() AS ErrorProcedure  
    	,ERROR_LINE() AS ErrorLine,ERROR_MESSAGE() AS ErrorMessage;
		
			INSERT INTO core.sap.LogErrorFovis 
			SELECT  GETDATE() -'05:00:00', @solNumeroRadicacion, @documentoId, @tipoDocumento,'F13', ERROR_NUMBER(),ERROR_SEVERITY(),ERROR_STATE(),ERROR_PROCEDURE(),ERROR_LINE(),ERROR_MESSAGE()
 

 
END CATCH
END