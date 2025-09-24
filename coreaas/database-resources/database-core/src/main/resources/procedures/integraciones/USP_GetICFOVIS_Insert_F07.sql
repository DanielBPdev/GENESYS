
-- ====================================================================================================
-- Author: YESIKA BERNAL
-- Create date: DIC 14 DE 2022
-- Description: Extrae la informaciin de los movimientos contables para la integraciin de FOVIS 
-- Modificado: Yesika Bernal, adicion tabla generacion de errores , Modificado2 glpi 77243 16022024
-- =====================================================================================================
CREATE OR ALTER PROCEDURE [sap].[USP_GetICFOVIS_Insert_F07] @solNumeroRadicacion VARCHAR(20), @estado BIT, @documentoId nvarchar(20), @tipoDocumento nvarchar(20), @fechaIngreso DATETIME, @tipo VARCHAR(10), @observaciones VARCHAR(150)

AS
BEGIN
SET NOCOUNT ON;
 
DECLARE	@referencia VARCHAR(16), @referenciaNum BIGINT, @consecutivo BIGINT, @usuario VARCHAR(50), @pofID BIGINT;
DECLARE @revTimeStamp BIGINT, @fechaContabilizacion DATETIME;
-- Declaraciin de variables para el calculo de informaciin de desembolso
DECLARE @idOferente AS VARCHAR(10), @psvid AS VARCHAR(10), @inicio AS INT, @fin AS INT, @medioTransferencia AS VARCHAR(8000);
DECLARE @claveBanco AS VARCHAR(4), @nroCuentaBancaria AS VARCHAR(30), @titularCuenta AS VARCHAR(200), @claveControlBanco AS VARCHAR(2), @referenciaBancoCuenta AS VARCHAR(20), @tipoDocumentoRef AS VARCHAR(20), @codigoGenesys AS BIGINT
DECLARE @idbanco AS INT,  @tipocuenta AS VARCHAR(20), @consecutivoTemp  BIGINT, @acreedorTemp VARCHAR(50),   @usuarioA VARCHAR(50);
	


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
	[tipoDocumentoC] VARCHAR(20) NOT NULL,
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
	-- F07: Pagos parciales Mejoramiento y/o Construcciin
	--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

BEGIN TRY 
	BEGIN TRANSACTION

	-- DETALLE: reconoce los registros

-------------------------------------------------------------------------31------------------------------------------------------------------------------------------------
	DECLARE @json NVARCHAR(max)
                SET @json = (SELECT dtsJsonPayload FROM core.dbo.Solicitud sol WITH (NOLOCK) INNER JOIN DatoTemporalSolicitud ds WITH (NOLOCK) ON sol.solId =ds.dtsSolicitud  WHERE solNumeroRadicacion = @solNumeroRadicacion)


-- Condicional para identificar si es Oferente o Proveedor para registrar los pagos parciales, recordar que se pueden integrar N numeros de mov con diferentes proveedores/Oferentes a una misma persona 
	if (select count(*) from openjson(@json,'$.legalizacionProveedor')
			with (
			numeroIdentificacion varchar(20) '$.proveedor.persona.numeroIdentificacion') iden
						)<=0
	begin
	print 'entra Oferente'
	
	INSERT INTO #IC_FOVIS_Det ([consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[tipoDocumentoC],[ref3],[tipoSector]
		,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
		,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo]
		, solFechaRadicacion, fechaContabilizacion, usuario, pofId,Componente)

	
	SELECT 
			row_number() over (order by pof.pofid ) consecutivo ,
			 '' codigosap,
		   '31' clavecont,
		   'K' clasecuenta,
		   valorADesembolsar AS importedocumento,
		   LEFT(numeroIdentificacion, 12 )ref1,
			tipoDocumentoCaja as tipodocumento,
			tipoIdentificacion as tipoDocumentoC,
		   CASE WHEN PrimerApellido IS NULL THEN razonSocial ELSE LEFT(CONCAT_WS(' ', PrimerApellido,SegundoApellido, PrimerNombre, SegundoNombre), 20) END  ref3,
		   tipoSectorCaja tiposector,
		   0 anulacion,
		   0 idproyecto,
		   0 nombreproyecto,
		   0 proyectopropio,
		   0 rendimientofinanciero,
		   solNumeroRadicacion as asignacion,
		   'CO' clavepaisbanco,
		   CASE WHEN LEN(banCodigoPILA - 1000) = 1 THEN CONCAT('0', CAST(banCodigoPILA - 1000 AS VARCHAR(4))) 
				WHEN LEN(banCodigoPILA - 1000) = 2 THEN CAST(banCodigoPILA - 1000 AS VARCHAR(4))
				WHEN LEN(banCodigoPILA - 1000) = 3 THEN CAST(banCodigoPILA AS VARCHAR(4))
				ELSE CAST(banCodigoPILA AS VARCHAR(4)) END AS claveBanco,
			numeroCuenta AS nroCuentaBancaria,
			LEFT(ofeNombreTitularCuenta,60) AS titularCuenta,
			CASE WHEN ofeTipoCuenta = 'CORRIENTE' THEN '01'
			WHEN ofeTipoCuenta = 'AHORROS' THEN '02'
			WHEN ofeTipoCuenta = 'DAVIPLATA' THEN '03'
			ELSE '00' END AS claveControlBanco,
			'F1' AS tipoBancoInter,
			numeroIdentificacion AS referenciaBancoCuenta,
			idPersona codigoGenesys,
			CASE WHEN tipoIdGenesys = 'NIT' THEN 'E'
			ELSE 'P' END AS tipo,
			solFechaRadicacion AS solFechaRadicacion, 
			sldFechaOperacion AS fechaContabilizacion,
			solUsuarioRadicacion AS usuario,
			pof.pofid AS pofid,
			CASE	WHEN pas.pasRecursoPrioridad IS NULL OR pas.pasRecursoPrioridad='RECURSO_POR_APROPIACION' THEN '1' 
					WHEN pas.pasRecursoPrioridad ='RECURSO_SEGUNDA_PRIORIDAD' THEN '2'
					WHEN pas.pasRecursoPrioridad ='RECURSO_TERCERA_PRIORIDAD' THEN '3' END AS Componente
	
		   from (select  * from openjson(@json,'$.legalizacionDesembolso')
					with(
							valorADesembolsar varchar(20) '$.valorADesembolsar'
						)) valorADesembolsar,
				(select  * from openjson(@json,'$.oferenteLegalizacion')
					with(
							numeroIdentificacion varchar(20) '$.oferente.persona.numeroIdentificacion',
							tipoIdentificacion varchar(20) '$.oferente.persona.tipoIdentificacion',
							primerApellido varchar(20) '$.oferente.persona.primerApellido',
							segundoApellido varchar(20) '$.oferente.persona.segundoApellido',
							primerNombre varchar(20) '$.oferente.persona.primerNombre',
							segundoNombre varchar(20) '$.oferente.persona.segundoNombre',
							razonSocial varchar(20) '$.oferente.persona.razonSocial',
							idOferente varchar(20) '$.oferente.idOferente',
							idPersona varchar(20) '$.oferente.persona.idPersona',
							idbanco  varchar(5) '$.oferente.banco.id',
							numeroCuenta varchar(20) '$.oferente.numeroCuenta'
					)) legalizacionProveedor
						LEFT join core.dbo.Oferente  on ofeId = idOferente
						LEFT JOIN core.dbo.Banco  ON idbanco = banId
						LEFT JOIN core.sap.MaestraIdentificacion mi on tipoIdentificacion = mi.tipoIdGenesys
					,
				(select   * from openjson(@json,'$.datosPostulacionFovis.postulacion')
					with(
							pofid varchar (20) '$.idPostulacion',
							idModalidad varchar(100) '$.idModalidad'
						)) postulacion 
						INNER JOIN core.sap.MaestraSector WITH (NOLOCK) ON idModalidad = tipoSectorGenesys
						INNER JOIN core.dbo.solicitud sol WITH (NOLOCK) ON solnumeroradicacion = @solNumeroRadicacion
						AND solTipoTransaccion NOT LIKE  '%ADQUISICION_VIVIENDA%' AND YEAR(sol.solFechaRadicacion) >=2022
						INNER JOIN core.dbo.SolicitudLegalizacionDesembolso sld WITH (NOLOCK) ON sol.solid = sld.sldSolicitudGlobal
						AND sld.sldEstadoSolicitud IN ('DESEMBOLSO_FOVIS_SOLICITADO_A_AREA_FINANCIERA')
						INNER JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK) ON pof.pofId = sld.sldPostulacionFOVIS AND pof.pofEstadoHogar IN ('SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO')
						AND pof.pofMotivoDesistimiento IS NULL						
						INNER JOIN core.dbo.LegalizacionDesembolso lgd WITH (NOLOCK) ON sld.sldLegalizacionDesembolso = lgd.lgdId 
						AND lgd.lgdFormaPago IN ('GIRO_ANTICIPADO_CONVENIO_CCF') -- revisar segun mejoramiento de vivienda
						LEFT JOIN PostulacionAsignacion pas WITH (NOLOCK) ON  pof.pofId = pas.pasPostulacionFovis						
			WHERE  numeroIdentificacion <> '890900842' -- VALIDACIiN NO TOMA PAGOS A COMFENALCO
	end
	else
	begin
	print 'entra Proveedor'
	INSERT INTO #IC_FOVIS_Det ([consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[tipoDocumentoC],[ref3],[tipoSector]
		,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
		,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo]
		, solFechaRadicacion, fechaContabilizacion, usuario, pofId,Componente)
	SELECT 
			row_number() over (order by pof.pofid ) consecutivo ,
			 '' codigosap,
		   '31' clavecont,
		   'K' clasecuenta,
		   valorADesembolsar AS importedocumento,
		   LEFT(numeroIdentificacion, 12 )ref1,
			tipoDocumentoCaja as tipodocumento,
			tipoIdentificacion as tipoDocumentoC,
		   CASE WHEN PrimerApellido IS NULL THEN razonSocial ELSE LEFT(CONCAT_WS(' ', PrimerApellido,SegundoApellido, PrimerNombre, SegundoNombre), 20) END  ref3,
		   tipoSectorCaja tiposector,
		   0 anulacion,
		   0 idproyecto,
		   0 nombreproyecto,
		   0 proyectopropio,
		   0 rendimientofinanciero,
		   solNumeroRadicacion as asignacion,
		   'CO' clavepaisbanco,
		   CASE WHEN LEN(banCodigoPILA - 1000) = 1 THEN CONCAT('0', CAST(banCodigoPILA - 1000 AS VARCHAR(4))) 
				WHEN LEN(banCodigoPILA - 1000) = 2 THEN CAST(banCodigoPILA - 1000 AS VARCHAR(4))
				WHEN LEN(banCodigoPILA - 1000) = 3 THEN CAST(banCodigoPILA AS VARCHAR(4))
				ELSE CAST(banCodigoPILA AS VARCHAR(4)) END AS claveBanco,
			numeroCuenta AS nroCuentaBancaria,
			LEFT(provNombreTitularCuenta,60) AS titularCuenta,
			CASE WHEN provTipoCuenta = 'CORRIENTE' THEN '01'
			WHEN provTipoCuenta = 'AHORROS' THEN '02'
			WHEN provTipoCuenta = 'DAVIPLATA' THEN '03'
			ELSE '00' END AS claveControlBanco,
			'F1' AS tipoBancoInter,
			numeroIdentificacion AS referenciaBancoCuenta,
			idPersona codigoGenesys,
			CASE WHEN tipoIdGenesys = 'NIT' THEN 'E'
			ELSE 'P' END AS tipo,
			solFechaRadicacion AS solFechaRadicacion, 
			sldFechaOperacion AS fechaContabilizacion,
			solUsuarioRadicacion AS usuario,
			pof.pofid AS pofid,
			CASE	WHEN pas.pasRecursoPrioridad IS NULL OR pas.pasRecursoPrioridad='RECURSO_POR_APROPIACION' THEN '1' 
					WHEN pas.pasRecursoPrioridad ='RECURSO_SEGUNDA_PRIORIDAD' THEN '2'
					WHEN pas.pasRecursoPrioridad ='RECURSO_TERCERA_PRIORIDAD' THEN '3' END AS Componente
		   from (select  * from openjson(@json,'$.legalizacionProveedor')
					with(
							valorADesembolsar varchar(20) '$.valorADesembolsar',
							numeroIdentificacion varchar(20) '$.proveedor.persona.numeroIdentificacion',
							tipoIdentificacion varchar(20) '$.proveedor.persona.tipoIdentificacion',
							primerApellido varchar(20) '$.proveedor.persona.primerApellido',
							segundoApellido varchar(20) '$.proveedor.persona.segundoApellido',
							primerNombre varchar(20) '$.proveedor.persona.primerNombre',
							segundoNombre varchar(20) '$.proveedor.persona.segundoNombre',
							razonSocial varchar(20) '$.proveedor.persona.razonSocial',
							--idOferente varchar(20) '$.proveedor.idOferente',
							idPersona varchar(20) '$.proveedor.persona.idPersona',
							idbanco  varchar(5) '$.proveedor.banco.id',
							numeroCuenta varchar(20) '$.proveedor.numeroCuenta'
					)) legalizacionProveedor
						--inner join core.dbo.Oferente  on ofeId = idOferente
						LEFT JOIN core.dbo.proveedor ON idPersona = provPersona
						LEFT JOIN core.dbo.Banco  ON idbanco = banId
						LEFT JOIN core.sap.MaestraIdentificacion mi on tipoIdentificacion = mi.tipoIdGenesys
					,
				(select   * from openjson(@json,'$.datosPostulacionFovis.postulacion')
					with(
							pofid varchar (20) '$.idPostulacion',
							idModalidad varchar(100) '$.idModalidad'
						)) postulacion 
						INNER JOIN core.sap.MaestraSector WITH (NOLOCK) ON idModalidad = tipoSectorGenesys
						INNER JOIN core.dbo.solicitud sol WITH (NOLOCK) ON solnumeroradicacion = @solNumeroRadicacion
						AND solTipoTransaccion NOT LIKE  '%ADQUISICION_VIVIENDA%' AND YEAR(sol.solFechaRadicacion) >=2022
						INNER JOIN core.dbo.SolicitudLegalizacionDesembolso sld WITH (NOLOCK) ON sol.solid = sld.sldSolicitudGlobal
						AND sld.sldEstadoSolicitud IN ('DESEMBOLSO_FOVIS_SOLICITADO_A_AREA_FINANCIERA')
						INNER JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK) ON pof.pofId = sld.sldPostulacionFOVIS AND pof.pofEstadoHogar IN ('SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO')
						AND pof.pofMotivoDesistimiento IS NULL
						INNER JOIN core.dbo.LegalizacionDesembolso lgd WITH (NOLOCK) ON sld.sldLegalizacionDesembolso = lgd.lgdId 
						AND lgd.lgdFormaPago IN ('GIRO_ANTICIPADO_CONVENIO_CCF') -- revisar segun mejoramiento de vivienda
						LEFT JOIN PostulacionAsignacion pas WITH (NOLOCK) ON  pof.pofId = pas.pasPostulacionFovis						
			WHERE  numeroIdentificacion <> '890900842' -- VALIDACIiN NO TOMA PAGOS A COMFENALCO
	
		end

		select * FROM #IC_FOVIS_Det
-----------------------------------------------------------------------------------------Fin 31-------------------------------------------------------------------------------
	IF EXISTS (select * FROM #IC_FOVIS_Det) BEGIN
		-- Extraer el usuario que realiza la solicitud
		SELECT @usuarioA = usuario
		FROM #IC_FOVIS_Det

		-- Campos en vacio de acuerdo al documento de homologaciin de campos
		UPDATE #IC_FOVIS_Det
		SET idProyecto = '', nombreProyecto = '', proyectoPropio = '', rendimientoFinanciero = ''
	

	
		-- INICIO: Calcular la fecha de contabilizaciin (FECHA DE APROBACION) desde la base de datos de auditoria ya sea local en CORE o posteriormente en COREAUD

		SELECT @pofID = pofId
		FROM #IC_FOVIS_Det;

		--declare @pofID bigint =1138
		--DECLARE @revTimeStamp BIGINT, @fechaContabilizacion DATETIME;
		INSERT INTO #revTimeStamp
		SELECT revTimeStamp, ''
		FROM aud.PostulacionFOVIS_aud WITH (NOLOCK) 
		INNER JOIN aud.Revision WITH (NOLOCK) ON revid = rev 
		WHERE pofEstadoHogar = 'SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO' 
		AND pofId = @pofID 

		IF NOT EXISTS (SELECT * FROM #revTimeStamp) BEGIN
			INSERT INTO #revTimeStamp
			EXEC sp_execute_remote @data_source_name = N'CoreAudReferenceData',  
			@stmt = N'SELECT distinct max(REV.revTimeStamp) FROM PostulacionFOVIS_aud AS POF
					inner JOIN SolicitudLegalizacionDesembolso_aud as sld on sld.sldPostulacionFOVIS = POF.pofId 
					inner join Revision as rev on sld.REV = rev.revId 
					inner join Solicitud_aud as sol on sld.sldSolicitudGlobal = sol.solId
				WHERE pofEstadoHogar = ''SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO'' AND pofid = @pofID',
			@params = N'@pofid BIGINT',
			@pofID = @pofID;

		END

		SELECT @revTimeStamp =  max(revTimeStamp) 
		FROM #revTimeStamp
		print @revTimeStamp

		SELECT @fechaContabilizacion = CAST(DATEADD(ms, CAST(RIGHT(@revTimeStamp,3) AS SMALLINT), DATEADD(s, @revTimeStamp / 1000, '1970-01-01'))-'05:00:00' AS DATETIME2(3))
		Print @fechaContabilizacion

		UPDATE #IC_FOVIS_Det
		SET fechaContabilizacion = @fechaContabilizacion
		WHERE @fechaContabilizacion IS NOT NULL
		-- fIN: Calcular la fecha de contabilizaciin (FECHA DE APROBACION) desde la base de datos de auditoria



				


--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
-- ENVIAR EL ACREEDOR SI NO EXISTE PARA EL MOVIMIENTO 31
--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			DECLARE cursor_acreedor CURSOR FOR 
			SELECT referenciaBancoCuenta, tipoDocumentoC,usuario
			FROM #IC_FOVIS_Det
							
		
			OPEN cursor_acreedor
			FETCH NEXT FROM cursor_acreedor INTO @acreedorTemp, @tipoDocumentoRef,  @usuarioA
	
		WHILE @@FETCH_STATUS = 0 
		BEGIN

			IF NOT EXISTS (SELECT acre.codigoSAP FROM core.[sap].[CodSAPGenesysAcreedor] acre WITH (NOLOCK) INNER JOIN core.dbo.Persona per WITH (NOLOCK) ON acre.codigoGenesys = per.perId WHERE per.perNumeroIdentificacion =  @acreedorTemp)BEGIN
				SELECT @acreedorTemp, @tipoDocumentoRef, @usuarioA
				EXEC [sap].[USP_GetAcreedoresFOVIS_DocumentoId] @acreedorTemp, @tipoDocumentoRef, @usuarioA
			END


	
		FETCH NEXT FROM cursor_acreedor INTO @acreedorTemp, @tipoDocumentoRef,  @usuarioA
		END 
			CLOSE cursor_acreedor 
			DEALLOCATE cursor_acreedor


		
			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			-- FIN ENVIAR EL ACREEDOR SI NO EXISTE PARA EL MOVIMIENTO 31
			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			-- ENCABEZADO: crea el encabezado por cada detalle
			INSERT INTO #IC_FOVIS_Enc ([fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo]
				,[referencia],[tipoMovimiento],[observacion],[moneda],[documentoContable],[sociedad],[ejercicio]
				,[nroIntentos],[fecProceso],[horaProceso],[estadoReg], consecutivoTemp, usuario, Componente)
			SELECT CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
				CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
				det.solFechaRadicacion AS fechaDocumento, 
				sld.sldFechaOperacion AS fechaContabilizacion, 
				MONTH(fechaContabilizacion) AS periodo, 
				'' AS referencia, 
				'F07' AS tipoMovimiento, 
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
				FROM #IC_FOVIS_Det det
					INNER JOIN core.dbo.Solicitud sol WITH (NOLOCK) ON det.asignacion = sol.solNumeroRadicacion
					INNER JOIN core.dbo.SolicitudLegalizacionDesembolso sld WITH (NOLOCK) ON sol.solid = sld.sldSolicitudGlobal
					WHERE asignacion = @solNumeroRadicacion
	-- Se actualiza si el periodo no coincide con el de auditoria 				
	update #IC_FOVIS_Enc set periodo = MONTH(fechaContabilizacion)
	where MONTH(fechaContabilizacion) <> periodo

			-- REGISTRO PARA COMPLETAR EL MOVIMIENTO
--------------------------------------------------------------------40-----------------------------------------------------------------------------------
			INSERT INTO #IC_FOVIS_Det([consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[tipoDocumentoC],[ref3],[tipoSector]
				,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
				,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo]
				, solFechaRadicacion, fechaContabilizacion, usuario, pofId)
			SELECT 
				consecutivo,
				codigoSAP,
				'40' AS claveCont,
				'S' AS claseCuenta,
				importeDocumento,
				per.perNumeroIdentificacion as  ref1,
				tipoDocumentoCaja as tipoDocumento,
				pertipoIdentificacion as tipoDocumentoC,
				LEFT(CONCAT_WS(' ', per.perPrimerApellido, per.perSegundoApellido, per.perPrimerNombre, per.perSegundoNombre), 20) as ref3,
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
				per.perid AS codigoGenesys,
				CASE WHEN per.perTipoIdentificacion = 'NIT' THEN 'E'
				ELSE 'P' END AS tipo,
				sol.solFechaRadicacion,
				det.fechaContabilizacion,
				usuario,
				pof.pofId
			FROM #IC_FOVIS_Det det WITH (NOLOCK) 
			INNER JOIN core.dbo.solicitud sol WITH (NOLOCK) ON  asignacion = sol.solNumeroRadicacion 
			INNER JOIN core.dbo.SolicitudLegalizacionDesembolso sld WITH (NOLOCK) ON  sol.solid = sld.sldSolicitudGlobal
			INNER JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK) ON  pof.pofId = sld.sldPostulacionFOVIS
			INNER JOIN core.dbo.jefehogar jeh WITH (NOLOCK) ON  pof.pofJefeHogar = jeh.jehId
			INNER JOIN core.dbo.afiliado afi WITH (NOLOCK) ON jehAfiliado = afi.afiid
			INNER JOIN core.dbo.Persona per WITH (NOLOCK) ON per.perId = afi.afiPersona
			LEFT JOIN core.sap.MaestraIdentificacion mi WITH (NOLOCK) ON pertipoIdentificacion = mi.tipoIdGenesys
			WHERE claveCont = '31'


		-- Calcula el cidigo SAP para el movimiento 31
			UPDATE A31
			SET codigoSAP = codsap.codigoSAP
			FROM [sap].[CodSAPGenesysAcreedor] codsap, #IC_FOVIS_Det A31
			WHERE codsap.codigoGenesys = A31.codigoGenesys
			AND A31.claveCont = '31'


		-- Extrae N°  asignacion 
		UPDATE #IC_FOVIS_Det
		SET asignacion = (
		SELECT top 1 sol.solNumeroRadicacion
		FROM core.dbo.PostulacionFOVIS pof 
		INNER JOIN core.dbo.jefehogar jeh WITH (NOLOCK)  ON pof.pofJefeHogar = jeh.jehId
		INNER JOIN core.dbo.afiliado afi WITH (NOLOCK)  ON jehAfiliado = afi.afiid
		INNER JOIN core.dbo.persona per WITH (NOLOCK)  ON per.perId = afi.afiPersona
		INNER JOIN core.dbo.SolicitudPostulacion spo WITH (NOLOCK)  ON pof.pofId = spo.spoPostulacionFOVIS
		INNER JOIN core.dbo.Solicitud sol WITH (NOLOCK)  ON sol.solId = spo.spoSolicitudGlobal
		WHERE pof.pofEstadoHogar IN ('SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO')
		AND per.perNumeroIdentificacion = @documentoId
		AND per.perTipoIdentificacion = @tipoDocumento
		AND pofID = @pofid
		ORDER BY pofid desc	)

		
	-- Si el oferente cuenta con la cuenta bancaria en caso contrario se dejan en vacio los campos

		UPDATE #IC_FOVIS_Det SET clavePaisBanco = '' , claveControlBanco = '', tipoBancoInter = '', referenciaBancoCuenta = '',titularCuenta = ''
		WHERE nroCuentaBancaria IS NULL AND claveCont = '31'

	  ---------------------------------------------------------------------------------------------------------------
	  
--------------------------------------CONDICIiN PARA ACTUALIZAR CAMPOS NUEVOS DEL REMPLAZANTE DE HOGAR--------------------------------
IF (SELECT TOP 1 inh.inhIntegranteReemplazaJefeHogar FROM core.dbo.jefehogar jeh   WITH (NOLOCK) 
		INNER JOIN core.dbo.Afiliado afi WITH (NOLOCK)  ON jeh.jehAfiliado = afi.afiId
		INNER JOIN core.dbo.Persona per WITH (NOLOCK)  ON afi.afiPersona = per.perid
		INNER JOIN core.dbo.IntegranteHogar inh WITH (NOLOCK)  ON jeh.jehid = inh.inhJefeHogar
		LEFT JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK)  ON jeh.jehId=pof.pofJefeHogar 
		WHERE per.perNumeroIdentificacion = @documentoId AND per.perTipoIdentificacion = @tipoDocumento AND  inh.inhIntegranteReemplazaJefeHogar = 1
		AND pofID = @pofid
		ORDER BY pofid desc	) = 1

BEGIN 
 
 UPDATE #IC_FOVIS_Det SET ref1 = per2.perNumeroIdentificacion , tipoDocumento = mi.tipoDocumentoCaja, 
						  ref3 = LEFT(CONCAT_WS(' ', per2.perPrimerApellido, per2.perSegundoApellido, per2.perPrimerNombre, per2.perSegundoNombre), 20)  
		FROM core.dbo.jefehogar jeh  WITH (NOLOCK) 
		INNER JOIN core.dbo.Afiliado afi WITH (NOLOCK)  ON jeh.jehAfiliado = afi.afiId
		INNER JOIN core.dbo.Persona per WITH (NOLOCK)  ON afi.afiPersona = per.perid
		INNER JOIN core.dbo.PersonaDetalle ped WITH (NOLOCK)  ON per.perId = ped.pedPersona
		INNER JOIN core.dbo.IntegranteHogar inh WITH (NOLOCK)  ON jeh.jehid = inh.inhJefeHogar
		INNER JOIN core.dbo.Persona per2 WITH (NOLOCK)  ON inh.inhPersona = per2.perId
		LEFT JOIN core.sap.MaestraIdentificacion mi WITH (NOLOCK)  ON per2.perTipoIdentificacion = mi.tipoIdGenesys
		LEFT JOIN core.dbo.PostulacionFOVIS pof WITH (NOLOCK)  ON jeh.jehId=pof.pofJefeHogar
		WHERE inh.inhIntegranteReemplazaJefeHogar = 1
		AND inh.inhIntegranteValido = 1
		AND ped.pedFallecido = 1
		AND  inh.inhIntegranteReemplazaJefeHogar = 1
		AND per.perNumeroIdentificacion = @documentoId
		AND per.perTipoIdentificacion = @tipoDocumento
		AND claveCont = '40'

END

-----------------------------------------------------------------FIN---------------------------------------------------------------------------------------------------

	
---------------------------------------------------------------FIN 40-------------------------------------------------------------------------------------------------------------------
			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			-- FIN F07
			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			-- INSERCION DE REGISTROS EN LAS TABLAS DE INTEGRACIiN
			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			-- Calcular el valor del campo referencia	

		DECLARE cursor_documentoC CURSOR FOR 
			SELECT consecutivoTemp
			FROM #IC_FOVIS_Enc
							
		
			OPEN cursor_documentoC 
			FETCH NEXT FROM cursor_documentoC INTO @consecutivoTemp
	
		WHILE @@FETCH_STATUS = 0 
		BEGIN

			SELECT @referenciaNum = valorActual 
			FROM sap.IC_Referencia 
			WHERE comentario = 'PT'
			AND estado = 'A';

	
			UPDATE #IC_FOVIS_Enc
			SET referencia = @referenciaNum
			WHERE consecutivoTemp = @consecutivoTemp;

	
		UPDATE sap.IC_Referencia
			SET valorActual = @referenciaNum + 1
			WHERE comentario = 'PT'
			AND estado = 'A';

 
  
		FETCH NEXT FROM cursor_documentoC INTO @consecutivoTemp
		END 
			CLOSE cursor_documentoC 
			DEALLOCATE cursor_documentoC


	IF @referenciaNum IS NOT NULL 

		BEGIN

			
			DECLARE cursor_consecutivo CURSOR FOR 
			SELECT consecutivoTemp
			FROM #IC_FOVIS_Enc
							
		
			OPEN cursor_consecutivo
			FETCH NEXT FROM cursor_consecutivo INTO @consecutivoTemp
	
		WHILE @@FETCH_STATUS = 0 
		BEGIN

		INSERT INTO IC_FOVIS_Enc([fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo]
				,[referencia],[tipoMovimiento],[observacion],[moneda],[documentoContable],[sociedad],[ejercicio]
				,[nroIntentos],[fecProceso],[horaProceso],[estadoReg], usuario, consecutivoTemp,Componente)
			SELECT [fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo]
				,[referencia],[tipoMovimiento],[observacion],[moneda],[documentoContable],[sociedad],[ejercicio]
				,[nroIntentos],[fecProceso],[horaProceso],[estadoReg], usuario, consecutivoTemp,Componente
			FROM #IC_FOVIS_Enc
			WHERE consecutivoTemp = @consecutivoTemp ;
	
			-- Actualizar el consecutivo definitivo en la tabla de integraciin de encabezado con el detalle
			SELECT @consecutivo = MAX(consecutivo) FROM IC_FOVIS_Enc 

			UPDATE #IC_FOVIS_Det
			SET consecutivo = @consecutivo
			WHERE consecutivo = @consecutivoTemp
			 
	
			INSERT INTO IC_FOVIS_Det([consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
				,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
				,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo],fechaEjecucion)
			SELECT [consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
				,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
				,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo], GETDATE() -'05:00:00'
			FROM #IC_FOVIS_Det
			WHERE consecutivo = @consecutivo;

		FETCH NEXT FROM cursor_consecutivo INTO @consecutivoTemp
		END 
			CLOSE cursor_consecutivo
			DEALLOCATE cursor_consecutivo

			
			--Inserciin en la tabla de control
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
			SELECT  GETDATE() -'05:00:00', @solNumeroRadicacion, @documentoId, @tipoDocumento,'F07', ERROR_NUMBER(),ERROR_SEVERITY(),ERROR_STATE(),ERROR_PROCEDURE(),ERROR_LINE(),ERROR_MESSAGE()
 

 
END CATCH
END