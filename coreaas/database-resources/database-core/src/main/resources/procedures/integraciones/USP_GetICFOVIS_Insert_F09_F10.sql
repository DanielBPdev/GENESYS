
-- ====================================================================================================
-- Author: VILLAMARIN JULIAN
-- Create date: MAYO 10 DE 2021
-- Description: Extrae la informacion de los movimientos contables para la integracion de FOVIS 
-- Modificado: Yesika Bernal, adicion tabla generacion de errores 
-- =====================================================================================================
CREATE OR ALTER PROCEDURE [sap].[USP_GetICFOVIS_Insert_F09_F10] 
	@solNumeroRadicacion VARCHAR(20), @estado BIT, @documentoId nvarchar(20), @tipoDocumento nvarchar(20), @fechaIngreso DATETIME, @tipo VARCHAR(10), @observaciones VARCHAR(150)
AS
BEGIN
SET NOCOUNT ON;
 
DECLARE	@referencia VARCHAR(16), @referenciaNum BIGINT, @consecutivo BIGINT, @usuario VARCHAR(50), @pofID BIGINT;
DECLARE @revTimeStamp BIGINT, @fechaContabilizacion DATETIME;
-- Declaracion de variables para el calculo de informacion de desembolso
DECLARE @idOferente AS VARCHAR(10), @psvid AS VARCHAR(10), @idbanco AS INT, @tipocuenta AS varchar(20), @medioTransferencia AS VARCHAR(800);
DECLARE @claveBanco AS VARCHAR(4), @nroCuentaBancaria AS VARCHAR(30), @titularCuenta AS VARCHAR(200), @claveControlBanco AS VARCHAR(2), @referenciaBancoCuenta AS VARCHAR(20), @tipoDocumentoRef AS VARCHAR(20), @codigoGenesys AS BIGINT
declare @json nvarchar(max)
                set @json = (select dtsJsonPayload from core.dbo.Solicitud sol INNER JOIN DatoTemporalSolicitud ds ON sol.solId =ds.dtsSolicitud  where solNumeroRadicacion = @solNumeroRadicacion)


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
	[Componente] VARCHAR(2)
)

CREATE TABLE #IC_FOVIS_Enc_F10
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
	fechaContabilizacion DATE NOT NULL,
	usuario VARCHAR(50) NULL,
	pofId BIGINT NULL,
	[Componente] VARCHAR(2)
)

CREATE TABLE #IC_FOVIS_Det_F10
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
	[Componente] VARCHAR(2)
)

	--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	-- F09: Pago por traslado a proyectos de vivienda rural/urbana
	--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

BEGIN TRY 
	BEGIN TRANSACTION

	-- DETALLE: reconoce los registros
	INSERT INTO #IC_FOVIS_Det ([consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
		,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
		,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo]
		, solFechaRadicacion, fechaContabilizacion, usuario, pofId,[Componente])
	SELECT DISTINCT
		ROW_NUMBER() OVER(ORDER BY pof.pofid) consecutivo,
		'' AS codigoSAP,
		'31' AS claveCont,
		'K' AS claseCuenta,
		pof.pofValorAsignadoSFV importeDocumento,
		per.pernumeroidentificacion ref1,
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
		CASE WHEN LEN(ban.banCodigoPILA - 1000) = 1 THEN CONCAT('0', CAST(ban.banCodigoPILA - 1000 AS VARCHAR(4))) 
			WHEN LEN(ban.banCodigoPILA - 1000) = 2 THEN CAST(ban.banCodigoPILA - 1000 AS VARCHAR(4))
			WHEN LEN(ban.banCodigoPILA - 1000) = 3 THEN CAST(ban.banCodigoPILA AS VARCHAR(4))
			ELSE CAST(ban.banCodigoPILA AS VARCHAR(4)) END AS claveBanco,
		ofe.ofeCuentaBancaria AS nroCuentaBancaria,
		LEFT(ofe.ofeNombreTitularCuenta,60) AS titularCuenta,
		CASE WHEN ofe.ofeTipoCuenta = 'CORRIENTE' THEN '01'
		WHEN ofe.ofeTipoCuenta = 'AHORROS' THEN '02'
		WHEN ofe.ofeTipoCuenta = 'DAVIPLATA' THEN '03'
		ELSE '00' END AS claveControlBanco,
		'' AS tipoBancoInter,
		per.perNUmeroIdentificacion AS referenciaBancoCuenta,
		per.perid AS codigoGenesys,
		CASE WHEN per.perTipoIdentificacion = 'NIT' THEN 'E'
		ELSE 'P' END AS tipo,
		sol.solFechaRadicacion AS solFechaRadicacion, 
		sld.sldFechaOperacion AS fechaContabilizacion,
		sol.solUsuarioRadicacion AS usuario,
		pof.pofId,
		CASE	WHEN pas.pasRecursoPrioridad IS NULL OR pas.pasRecursoPrioridad='RECURSO_POR_APROPIACION' THEN '1' 
				WHEN pas.pasRecursoPrioridad ='RECURSO_SEGUNDA_PRIORIDAD' THEN '2'
				WHEN pas.pasRecursoPrioridad ='RECURSO_TERCERA_PRIORIDAD' THEN '3' END AS Componente
	FROM core.dbo.Solicitud sol WITH (NOLOCK) 
	INNER JOIN core.dbo.SolicitudLegalizacionDesembolso sld WITH (NOLOCK)  ON sol.solid = sld.sldSolicitudGlobal
	AND sld.sldEstadoSolicitud IN ('DESEMBOLSO_FOVIS_SOLICITADO_A_AREA_FINANCIERA')
	INNER JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK)  ON pof.pofId = sld.sldPostulacionFOVIS AND pof.pofEstadoHogar IN ('SUBSIDIO_LEGALIZADO')
	AND pof.pofMotivoDesistimiento IS NULL
	INNER JOIN core.dbo.jefehogar jeh WITH (NOLOCK)  ON pof.pofJefeHogar = jeh.jehId
	INNER JOIN core.dbo.afiliado afi WITH (NOLOCK)  ON jehAfiliado = afi.afiid
	INNER JOIN core.dbo.Persona per WITH (NOLOCK)  ON per.perId = afi.afiPersona
	LEFT JOIN core.dbo.ProyectoSolucionVivienda psv WITH (NOLOCK)  ON pof.pofProyectoSolucionVivienda = psv.psvId
	LEFT JOIN core.dbo.Oferente ofe WITH (NOLOCK)  ON ofe.ofeId = psv.psvOferente
	LEFT JOIN Persona perOfe WITH (NOLOCK)  ON perOfe.perId = ofe.ofePersona
	LEFT JOIN core.dbo.Banco ban WITH (NOLOCK) ON ofe.ofeBanco = ban.banId
	INNER JOIN core.dbo.LegalizacionDesembolso lgd WITH (NOLOCK) ON sld.sldLegalizacionDesembolso = lgd.lgdId
	AND lgd.lgdFormaPago IN ('PAGO_CONTRA_ESCRITURA')
	LEFT JOIN core.sap.MaestraIdentificacion mi WITH (NOLOCK) ON per.perTipoIdentificacion = mi.tipoIdGenesys
	LEFT JOIN core.sap.MaestraSector ms WITH (NOLOCK) ON pof.pofModalidad = ms.tipoSectorGenesys		
	LEFT JOIN PostulacionAsignacion pas WITH (NOLOCK) ON  pof.pofId = pas.pasPostulacionFovis
	WHERE  solTipoTransaccion  LIKE  '%ADQUISICION_VIVIENDA%'	
	AND NOT EXISTS (SELECT ref1 FROM [sap].[IC_FOVIS_Enc] enc WITH (NOLOCK)  INNER JOIN [sap].[IC_FOVIS_Det] det WITH (NOLOCK)  ON enc.consecutivo = det.consecutivo WHERE ref1 = @documentoId AND tipoMovimiento = 'F09')
	AND YEAR(sol.solFechaRadicacion) > 2013 -- ENERO 17: REVISAR ESTE FRILTRO DEL AÑO
	AND per.perNumeroIdentificacion = @documentoId
	AND per.perTipoIdentificacion = @tipoDocumento

	IF EXISTS (select * FROM #IC_FOVIS_Det) BEGIN
		-- Extraer el usuario que realiza la core.dbo.Solicitud
		SELECT @usuario = usuario
		FROM #IC_FOVIS_Det

		-- Campos en NULL de acuerdo al documento de homologacion de campos
		UPDATE #IC_FOVIS_Det
		SET rendimientoFinanciero = ''

		SELECT @pofID = pofId
		FROM #IC_FOVIS_Det;
	   
		-- Extrae el numero de core.dbo.Solicitud original de la asignacion.
		UPDATE #IC_FOVIS_Det
		SET asignacion = (
		SELECT top 1 sol.solNumeroRadicacion
		FROM core.dbo.PostulacionFOVIS pof WITH (NOLOCK) 
		INNER JOIN core.dbo.jefehogar jeh	WITH (NOLOCK) 	ON pof.pofJefeHogar = jeh.jehId
		INNER JOIN core.dbo.afiliado afi	WITH (NOLOCK) 	ON jehAfiliado = afi.afiid
		INNER JOIN persona per	WITH (NOLOCK) 	ON per.perId = afi.afiPersona
		INNER JOIN SolicitudPostulacion spo WITH (NOLOCK)  ON pof.pofId = spo.spoPostulacionFOVIS
		INNER JOIN core.dbo.Solicitud sol WITH (NOLOCK)  ON sol.solId = spo.spoSolicitudGlobal
		WHERE pof.pofEstadoHogar IN ('SUBSIDIO_LEGALIZADO')
		AND per.perNumeroIdentificacion = @documentoId
		AND per.perTipoIdentificacion = @tipoDocumento
		AND pofid = @pofID
		ORDER BY pofid desc	)

		-- INICIO: Calcular la fecha de contabilizacion (FECHA DE APROBACION) desde la base de datos de auditoria ya sea local en CORE o posteriormente en COREAUD
	

		INSERT INTO #revTimeStamp
		SELECT revTimeStamp, ''
		FROM aud.PostulacionFOVIS_aud WITH (NOLOCK) 
		INNER JOIN aud.Revision WITH (NOLOCK) ON revid = rev 
		WHERE pofEstadoHogar = 'SUBSIDIO_LEGALIZADO' 
		AND pofId = @pofID 

		IF NOT EXISTS (SELECT * FROM #revTimeStamp) BEGIN
			INSERT INTO #revTimeStamp
			EXEC sp_execute_remote @data_source_name = N'CoreAudReferenceData',  
			@stmt = N'SELECT revTimeStamp FROM PostulacionFOVIS_aud INNER JOIN Revision on revid = rev WHERE pofEstadoHogar = ''SUBSIDIO_LEGALIZADO'' AND pofid = @pofid',
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

	
		select @idOferente = (select idOferente from openjson(@json, '$.oferenteLegalizacion.oferente')
                with(
                        idOferente varchar(20) '$.idOferente'
                )),
		@psvid = (select idProyectoVivienda from openjson(@json, '$.proyectoSolucionViviendaLegalizacion')
                with(
                        idProyectoVivienda varchar(20) '$.idProyectoVivienda'
                )),
		@medioTransferencia = (select tipoMedioDePago from openjson(@json, '$.proyectoSolucionViviendaLegalizacion.medioPago')
                with(
                        tipoMedioDePago varchar(20) '$.tipoMedioDePago'
                )),
		@idbanco = (select idBanco from openjson(@json, '$.proyectoSolucionViviendaLegalizacion.medioPago')
                with(
                        idBanco varchar(20) '$.idBanco'
                )),
		@tipocuenta = (select tipoCuenta from openjson(@json, '$.proyectoSolucionViviendaLegalizacion.medioPago')
                with(
                        tipoCuenta varchar(20) '$.tipoCuenta'
                )),
		@nroCuentaBancaria = (select numeroCuenta from openjson(@json, '$.proyectoSolucionViviendaLegalizacion.medioPago')
                with(
                        numeroCuenta varchar(20) '$.numeroCuenta'
                )),
		@referenciaBancoCuenta = (select numeroIdentificacionTitular from openjson(@json, '$.proyectoSolucionViviendaLegalizacion.medioPago')
                with(
                        numeroIdentificacionTitular varchar(20) '$.numeroIdentificacionTitular'
                )),
		@tipoDocumentoRef = (select tipoIdentificacionTitular from openjson(@json, '$.proyectoSolucionViviendaLegalizacion.medioPago')
                with(
                        tipoIdentificacionTitular varchar(20) '$.tipoIdentificacionTitular'
                )),
		@titularCuenta = (select nombreTitularCuenta from openjson(@json, '$.proyectoSolucionViviendaLegalizacion.medioPago')
                with(
                        nombreTitularCuenta varchar(20) '$.nombreTitularCuenta'
                ))
		IF CHARINDEX('TRANSFERENCIA', @medioTransferencia) > 0 BEGIN
			SELECT @claveBanco = CASE WHEN LEN(ban.banCodigoPILA - 1000) = 1 THEN CONCAT('0', CAST(ban.banCodigoPILA - 1000 AS VARCHAR(4))) 
					WHEN LEN(ban.banCodigoPILA - 1000) = 2 THEN CAST(ban.banCodigoPILA - 1000 AS VARCHAR(4))
					WHEN LEN(ban.banCodigoPILA - 1000) = 3 THEN CAST(ban.banCodigoPILA AS VARCHAR(4))
					ELSE CAST(ban.banCodigoPILA AS VARCHAR(4)) END
			FROM core.dbo.Banco ban
			WHERE ban.banId = @idbanco;

			SELECT @claveControlBanco = CASE @tipocuenta WHEN 'CORRIENTE' THEN '01'
				WHEN 'AHORROS' THEN '02'
				WHEN 'DAVIPLATA' THEN '03'
				ELSE '00' END;
	
	
		END
		-- Traer el codigo Genesys
		SELECT @codigoGenesys = per.perId
		FROM Persona per 
		WHERE per.perNumeroIdentificacion = @referenciaBancoCuenta
		AND per.perTipoIdentificacion = @tipoDocumentoRef;
	
	
		-- Verificar el movimiento F06 VS F09, cuando es Proyecto propio de la Comfenalco y el core.dbo.Oferente y el titular de la cuenta, elimina el registro para no ser integrado
		IF NOT EXISTS (SELECT per.perNumeroIdentificacion 
		FROM core.dbo.Oferente ofe WITH (NOLOCK) 
		INNER JOIN Persona per WITH (NOLOCK)  ON ofe.ofePersona = per.perId
		INNER JOIN core.dbo.ProyectoSolucionVivienda psv WITH (NOLOCK)  ON ofe.ofeId = psv.psvOferente
		WHERE ofe.ofeId = @idOferente
		AND per.perNumeroIdentificacion = (SELECT CAST(SUBSTRING(prmValor, 1, CASE WHEN CHARINDEX('-', prmValor) > 0 THEN CHARINDEX('-', prmValor)-1 ELSE LEN(prmValor) END) AS INT) FROM Parametro WHERE prmNombre = 'NUMERO_ID_CCF')
		AND per.perNumeroIdentificacion = @referenciaBancoCuenta
		AND psv.psvId = @psvid) BEGIN

			DELETE #IC_FOVIS_Det;

		END
		ELSE BEGIN
	
				-- Actualizacion de datos en la tabla temporal desde el objeto JSON
			UPDATE #IC_FOVIS_Det
			SET claveBanco = CASE WHEN LEN(@claveBanco - 1000) = 1 THEN CONCAT('0', CAST(@claveBanco - 1000 AS VARCHAR(4))) 
					WHEN LEN(@claveBanco - 1000) = 2 THEN CAST(@claveBanco - 1000 AS VARCHAR(4))
					WHEN LEN(@claveBanco - 1000) = 3 THEN CAST(@claveBanco AS VARCHAR(4))
					ELSE ISNULL(CAST(@claveBanco AS VARCHAR(15)),'') END,
				nroCuentaBancaria = ISNULL(@nroCuentaBancaria,''),
				titularCuenta = ISNULL(@titularCuenta,''),
				claveControlBanco = ISNULL(@claveControlBanco,''),
				referenciaBancoCuenta = ISNULL(@referenciaBancoCuenta,''),
				idProyecto = ISNULL(@psvid,''),
				nombreProyecto = ISNULL((SELECT psvNombreProyecto FROM core.dbo.ProyectoSolucionVivienda WHERE psvId = @psvid),''),
				proyectoPropio = CASE WHEN @referenciaBancoCuenta = (SELECT CAST(SUBSTRING(prmValor, 1, CASE WHEN CHARINDEX('-', prmValor) > 0 THEN CHARINDEX('-', prmValor)-1 ELSE LEN(prmValor) END) AS INT) FROM Parametro WHERE prmNombre = 'NUMERO_ID_CCF') THEN 1 ELSE 0 END --NIT DE COMFENALCO PARA DETERMINAR QUE ES PROYECTO PROPIO '890900842'
			
			-- FIN CALCULO DE LA POSTULACIoN CON LAS CONDICIONES DE DESEMBOLSO

			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			-- ENVIAR EL ACREEDOR SI NO EXISTE PARA EL MOVIMIENTO 31
			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			IF NOT EXISTS (SELECT acre.codigoSAP FROM [sap].[CodSAPGenesysAcreedor] acre INNER JOIN Persona per ON acre.codigoGenesys = per.perId WHERE per.perNumeroIdentificacion =  @referenciaBancoCuenta) BEGIN
				EXEC [sap].[USP_GetAcreedoresFOVIS_DocumentoId] @referenciaBancoCuenta, @tipoDocumentoRef, @usuario
			END

			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			-- FIN ENVIAR EL ACREEDOR SI NO EXISTE PARA EL MOVIMIENTO 31
			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

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
				'F09' AS tipoMovimiento, 
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
				, solFechaRadicacion, fechaContabilizacion, usuario, pofId)
			SELECT DISTINCT
				consecutivo,
				codigoSAP,
				'40' AS claveCont,
				'S' AS claseCuenta,
				importeDocumento,
				ref1,
				tipoDocumento,
				ref3,
				tipoSector,
				anulacion,		-- GAP
				idProyecto,
				nombreProyecto,
				proyectoPropio,
				rendimientoFinanciero,		--GAP
				asignacion,
				'', --clavePaisBanco,
				'', --claveBanco,
				'', --nroCuentaBancaria,
				'', --titularCuenta,
				'', --claveControlBanco,
				'', --tipoBancoInter,
				'', --referenciaBancoCuenta,
				codigoGenesys, --codigoGenesys,
				tipo,
				solFechaRadicacion,
				fechaContabilizacion,
				usuario,
				pofId
			FROM #IC_FOVIS_Det
			WHERE claveCont = '31'

			-- Calcula el codigo Genesys del oferente, ref1, tipodocumnento y ref3 para el movimiento 31
			UPDATE A31
			SET codigoGenesys = per.perid,
			tipoBancoInter = 'F1',
			ref1 = @referenciaBancoCuenta,
			tipodocumento = mi.tipoDocumentoCaja ,
			ref3 = LEFT(@titularCuenta, 20),
			tipo = CASE WHEN @tipoDocumentoRef = 'NIT' THEN 'E'
				ELSE 'P' END
			FROM Persona per, #IC_FOVIS_Det A31 WITH (NOLOCK) 
			LEFT JOIN SAP.MaestraIdentificacion mi WITH (NOLOCK)  ON  @tipoDocumentoRef = mi.tipoIdGenesys
			WHERE per.perNumeroIdentificacion = A31.referenciaBancoCuenta
			AND per.perNumeroIdentificacion = @referenciaBancoCuenta
			AND A31.claveCont = '31'

			-- Calcula el codigo SAP para el movimiento 31
			UPDATE A31
			SET codigoSAP = codsap.codigoSAP
			FROM [sap].[CodSAPGenesysAcreedor] codsap, #IC_FOVIS_Det A31
			WHERE codsap.codigoGenesys = A31.codigoGenesys
			AND A31.claveCont = '31'

			-- Verifica si el oferente cuenta con la cuenta bancaria en caso contrario se dejan en vacio los campos
			IF @nroCuentaBancaria IS NULL BEGIN
				UPDATE #IC_FOVIS_Det
				SET clavePaisBanco = '',
					claveBanco = '',
					nroCuentaBancaria = '',
					titularCuenta = '',
					claveControlBanco = '',
					tipoBancoInter = '',
					referenciaBancoCuenta = ''
				WHERE claveCont = '31'
			END --IF

			
--------------------------------------CONDICIoN PARA ACTUALIZAR CAMPOS NUEVOS DEL REMPLAZANTE DE HOGAR--------------------------------
IF (SELECT top 1 inh.inhIntegranteReemplazaJefeHogar FROM core.dbo.jefehogar jeh   WITH (NOLOCK) 
		INNER JOIN Afiliado afi WITH (NOLOCK)  ON jeh.jehAfiliado = afi.afiId
		INNER JOIN Persona per WITH (NOLOCK)  ON afi.afiPersona = per.perid
		INNER JOIN IntegranteHogar inh WITH (NOLOCK)  ON jeh.jehid = inh.inhJefeHogar
		LEFT JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK)  ON jeh.jehId=pof.pofJefeHogar 
		WHERE per.perNumeroIdentificacion = @documentoId AND per.perTipoIdentificacion = @tipoDocumento AND  inh.inhIntegranteReemplazaJefeHogar = 1
		and pofid = @pofID
		ORDER BY pofid desc	) = 1

BEGIN 
 
 UPDATE #IC_FOVIS_Det SET ref1 = per2.perNumeroIdentificacion , tipoDocumento = mi.tipoDocumentoCaja, 
						  ref3 = LEFT(CONCAT_WS(' ', per2.perPrimerApellido, per2.perSegundoApellido, per2.perPrimerNombre, per2.perSegundoNombre), 20)  
		FROM core.dbo.jefehogar jeh  WITH (NOLOCK) 
		INNER JOIN Afiliado afi WITH (NOLOCK)  ON jeh.jehAfiliado = afi.afiId
		INNER JOIN Persona per WITH (NOLOCK)  ON afi.afiPersona = per.perid
		INNER JOIN PersonaDetalle ped WITH (NOLOCK)  ON per.perId = ped.pedPersona AND ped.pedFallecido = 1
		INNER JOIN IntegranteHogar inh WITH (NOLOCK)  ON jeh.jehid = inh.inhJefeHogar AND inh.inhIntegranteReemplazaJefeHogar = 1
		AND inh.inhIntegranteValido = 1
		INNER JOIN Persona per2 WITH (NOLOCK)  ON inh.inhPersona = per2.perId
		LEFT JOIN core.sap.MaestraIdentificacion mi WITH (NOLOCK)  ON per2.perTipoIdentificacion = mi.tipoIdGenesys
		LEFT JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK)  ON jeh.jehId=pof.pofJefeHogar
		WHERE per.perNumeroIdentificacion = @documentoId
		AND per.perTipoIdentificacion = @tipoDocumento
		AND claveCont = '40'


END

			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			-- FIN F09: Pago por traslado a proyectos de vivienda rural/urbana
			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			-- INICIO INSERCION DE REGISTROS EN LAS TABLAS DE INTEGRACIoN
			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			-- Calcular el valor del campo referencia	
			SELECT @referenciaNum = valorActual 
			FROM sap.IC_Referencia 
			WHERE comentario = 'PT'
			AND estado = 'A';
	
			UPDATE #IC_FOVIS_Enc
			SET referencia = @referenciaNum;

			UPDATE sap.IC_Referencia
			SET valorActual = @referenciaNum + 1
			WHERE comentario = 'PT'
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
			SELECT @consecutivo = MAX(consecutivo) FROM IC_FOVIS_Enc
	
			UPDATE #IC_FOVIS_Det
			SET consecutivo = @consecutivo
	
			INSERT INTO IC_FOVIS_Det([consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
				,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
				,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo])
			SELECT [consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
				,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
				,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo]
			FROM #IC_FOVIS_Det;
		
			INSERT INTO sap.ContablesCtrl
			SELECT @solNumeroRadicacion, @estado, @documentoId, @tipoDocumento, @fechaIngreso, @tipo, @observaciones
		
	END
			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			-- FIN INSERCION DE REGISTROS EN LAS TABLAS DE INTEGRACIoN
			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			-- INICIO DE MOVIMIENTO CONTABLE F10
			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
			INSERT INTO #IC_FOVIS_Enc_F10([fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo]
				,[referencia],[tipoMovimiento],[observacion],[moneda],[documentoContable],[sociedad],[ejercicio]
				,[nroIntentos],[fecProceso],[horaProceso],[estadoReg], usuario, consecutivoTemp,Componente)
			SELECT [fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo]
				,[referencia],'F10',[observacion],[moneda],[documentoContable],[sociedad],[ejercicio]
				,[nroIntentos],[fecProceso],[horaProceso],[estadoReg], usuario, consecutivoTemp + 1,Componente
			FROM #IC_FOVIS_Enc;
	
			INSERT INTO #IC_FOVIS_Det_F10([consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
				,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
				,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo],solFechaRadicacion,fechaContabilizacion)
			SELECT consecutivo + 1,[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
				,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
				,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo],solFechaRadicacion,fechaContabilizacion
			FROM #IC_FOVIS_Det;

			-- ACTUALIZACION DE MOVIMIENTOS CONTABLES, CLASE CUENTA Y CAMPOS PARA F10

			UPDATE #IC_FOVIS_Det_F10
			SET ClaveCont = 19, 
			claseCuenta = 'D',
			tipoSector = '',
			clavePaisBanco = '',
			claveBanco = '',
			nroCuentaBancaria = '',
			titularCuenta = '',
			claveControlBanco = '',
			tipoBancoInter = '',
			referenciaBancoCuenta = ''
			WHERE claveCont = 40

			UPDATE #IC_FOVIS_Det_F10
			SET codigoSap = (SELECT codigoSAP FROM [sap].[CodSAPGenesysDeudor] WHERE codigoGenesys = (SELECT codigoGenesys FROM #IC_FOVIS_Det_F10 where claveCont = 19 ))

			UPDATE #IC_FOVIS_Det_F10
			SET ClaveCont = 40, 
			claseCuenta = 'S', 
			codigoSap = '',
			ref1 = (SELECT ref1 FROM #IC_FOVIS_Det_F10 WHERE claveCont = 19), 
			ref3 = (SELECT ref3 FROM #IC_FOVIS_Det_F10 WHERE claveCont = 19),
			tipoDocumento = (SELECT tipoDocumento FROM #IC_FOVIS_Det_F10 WHERE claveCont = 19),
			tipoSector = '',
			clavePaisBanco = '',
			claveBanco = '',
			nroCuentaBancaria = '',
			titularCuenta = '',
			claveControlBanco = '',
			tipoBancoInter = '',
			referenciaBancoCuenta = '',
			codigoGenesys = (SELECT codigoGenesys FROM #IC_FOVIS_Det_F10 WHERE claveCont = 19),
			tipo = (SELECT tipo FROM #IC_FOVIS_Det_F10 WHERE claveCont = 19)
			WHERE claveCont = 31

--------------------------------------CONDICIoN PARA ACTUALIZAR CAMPOS NUEVOS DEL REMPLAZANTE DE HOGAR--------------------------------
IF (SELECT TOP 1 inh.inhIntegranteReemplazaJefeHogar FROM core.dbo.jefehogar jeh   WITH (NOLOCK) 
		INNER JOIN Afiliado afi WITH (NOLOCK)  ON jeh.jehAfiliado = afi.afiId
		INNER JOIN Persona per WITH (NOLOCK)  ON afi.afiPersona = per.perid
		INNER JOIN IntegranteHogar inh WITH (NOLOCK)  ON jeh.jehid = inh.inhJefeHogar
		LEFT JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK)  ON jeh.jehId=pof.pofJefeHogar 
		WHERE per.perNumeroIdentificacion = @documentoId AND per.perTipoIdentificacion = @tipoDocumento AND  inh.inhIntegranteReemplazaJefeHogar = 1
		and pofid = @pofID
		ORDER BY pofid desc	) = 1

BEGIN 

		UPDATE #IC_FOVIS_Det_F10 SET  codigoSap = (SELECT deu.codigoSAP 	 FROM core.dbo.jefehogar jeh  WITH (NOLOCK) 
		INNER JOIN Afiliado afi WITH (NOLOCK)  ON jeh.jehAfiliado = afi.afiId
		INNER JOIN Persona per WITH (NOLOCK)  ON afi.afiPersona = per.perid
		INNER JOIN PersonaDetalle ped WITH (NOLOCK)  ON per.perId = ped.pedPersona
		INNER JOIN IntegranteHogar inh WITH (NOLOCK)  ON jeh.jehid = inh.inhJefeHogar
		INNER JOIN Persona per2 WITH (NOLOCK)  ON inh.inhPersona = per2.perId
		INNER JOIN  [sap].[CodSAPGenesysDeudor] deu WITH (NOLOCK)  ON per2.perId = deu.codigoGenesys
		WHERE   per.perNumeroIdentificacion = @documentoId AND per.perTipoIdentificacion = @tipoDocumento
		AND  inh.inhIntegranteReemplazaJefeHogar = 1 AND claveCont = 19)
END
	
			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			-- INICIO INSERCION DE REGISTROS EN LAS TABLAS DE INTEGRACIoN F10
			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
			-- Calcular el valor del campo referencia para el F10
			SELECT @referenciaNum = valorActual 
			FROM sap.IC_Referencia 
			WHERE comentario = 'PT'
			AND estado = 'A';
	
			UPDATE #IC_FOVIS_Enc_F10
			SET referencia = @referenciaNum;

			UPDATE sap.IC_Referencia
			SET valorActual = @referenciaNum + 1
			WHERE comentario = 'PT'
			AND estado = 'A';

	IF @referenciaNum IS NOT NULL
		BEGIN
			INSERT INTO IC_FOVIS_Enc([fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo]
				,[referencia],[tipoMovimiento],[observacion],[moneda],[documentoContable],[sociedad],[ejercicio]
				,[nroIntentos],[fecProceso],[horaProceso],[estadoReg], usuario, consecutivoTemp,Componente)
			SELECT [fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo]
				,[referencia],[tipoMovimiento],[observacion],[moneda],[documentoContable],[sociedad],[ejercicio]
				,[nroIntentos],[fecProceso],[horaProceso],[estadoReg], usuario, consecutivoTemp,Componente
			FROM #IC_FOVIS_Enc_F10;
	
			-- Actualizar el consecutivo definitivo en la tabla de integracion de encabezado con el detalle
			SELECT @consecutivo = MAX(consecutivo) FROM IC_FOVIS_Enc
	
			UPDATE #IC_FOVIS_Det_F10
			SET consecutivo = @consecutivo
	
			INSERT INTO IC_FOVIS_Det([consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
				,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
				,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo],fechaEjecucion)
			SELECT [consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
				,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
				,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo],GETDATE() -'05:00:00'
			FROM #IC_FOVIS_Det_F10;

			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			-- FIN INSERCION DE REGISTROS EN LAS TABLAS DE INTEGRACIoN F10
			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
			-- Insercion en la tabla de control
			
			INSERT INTO sap.ContablesCtrl
			SELECT @solNumeroRadicacion, @estado, @documentoId, @tipoDocumento, @fechaIngreso, @tipo, @observaciones
			
	END

		END --ELSE
			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			-- FIN DE MOVIMIENTO CONTABLE F10
			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
		-- Detalle
		SELECT * FROM #IC_FOVIS_Enc;
		SELECT * FROM #IC_FOVIS_Det;
		SELECT * FROM #IC_FOVIS_Enc_F10;
		SELECT * FROM #IC_FOVIS_Det_F10;
	
		DROP TABLE #IC_FOVIS_Enc;
		DROP TABLE #IC_FOVIS_Det;
		DROP TABLE #IC_FOVIS_Enc_F10;
		DROP TABLE #IC_FOVIS_Det_F10;
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
			SELECT  GETDATE() -'05:00:00', @solNumeroRadicacion, @documentoId, @tipoDocumento,'F09_F10', ERROR_NUMBER(),ERROR_SEVERITY(),ERROR_STATE(),ERROR_PROCEDURE(),ERROR_LINE(),ERROR_MESSAGE()
 

 
END CATCH
END