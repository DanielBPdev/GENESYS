-- ====================================================================================================
-- Author: YESIKA BERNAL
-- Create date: DIC 14 DE 2022
-- Description: Extrae la informacion de los movimientos contables para la integracion de FOVIS 
-- Modificado: Yesika Bernal, adicion tabla generacion de errores 
-- =====================================================================================================
CREATE OR ALTER   PROCEDURE [sap].[USP_GetICFOVIS_Insert_F08] 
	@solNumeroRadicacion VARCHAR(20), @estado BIT, @documentoId nvarchar(20), @tipoDocumento nvarchar(20), @fechaIngreso DATETIME, @tipo VARCHAR(10), @observaciones VARCHAR(150)
AS
BEGIN
SET NOCOUNT ON;
 
DECLARE	@referencia VARCHAR(16), @referenciaNum BIGINT, @consecutivo BIGINT, @usuario VARCHAR(50), @pofID BIGINT;
DECLARE @revTimeStamp BIGINT, @fechaContabilizacion DATETIME;
-- Declaracion de variables para el calculo de informacion de desembolso

DECLARE @nroCuentaBancaria AS VARCHAR(30), @titularCuenta AS VARCHAR(200),  @referenciaBancoCuenta AS VARCHAR(20), @tipoDocumentoRef AS VARCHAR(20)
DECLARE @idbanco AS INT,  @tipocuenta AS VARCHAR(20), @consecutivoTemp  BIGINT;
	

--DECLARE @json NVARCHAR(max)
--                SET @json = (SELECT dtsJsonPayload FROM Solicitud sol WITH (NOLOCK) INNER JOIN DatoTemporalSolicitud ds WITH (NOLOCK) ON sol.solId =ds.dtsSolicitud  WHERE solNumeroRadicacion = @solNumeroRadicacion)


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
	[tipoDocumento] VARCHAR(20) NOT NULL,
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
	-- F07: Pagos parciales Mejoramiento y/o Construccion
	--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

BEGIN TRY 
	BEGIN TRANSACTION

	-- DETALLE: reconoce los registros

-------------------------------------------------------------------------40------------------------------------------------------------------------------------------------
	INSERT INTO #IC_FOVIS_Det ([consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
		,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
		,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo]
		,[solFechaRadicacion], [fechaContabilizacion], [usuario], [pofId],[Componente])

	SELECT DISTINCT TOP 1 ROW_NUMBER() OVER(ORDER BY pof.pofid) consecutivo,
			'' AS codigoSAP,
			'40' AS claveCont,
			'S' AS claseCuenta,
			lgd.lgdValorDesembolsar AS importeDocumento,
			LEFT (per.pernumeroidentificacion, 12) ref1,
			tipoDocumentoCaja AS tipoDocumento,
			LEFT(CONCAT_WS(' ', per.perPrimerApellido, per.perSegundoApellido, per.perPrimerNombre, per.perSegundoNombre), 20) AS ref3,
			ms.tipoSectorCaja AS tipoSector,
			0 AS anulacion,		-- GAP
			0 AS idProyecto,
			'' AS nombreProyecto,
			0 AS proyectoPropio,
			0 AS rendimientoFinanciero,		--GAP
			solNumeroRadicacion AS asignacion,
			'' AS clavePaisBanco,
			'' claveBanco,
			'' AS nroCuentaBancaria,
			'' AS titularCuenta,
			'' claveControlBanco,
			'' AS tipoBancoInter,
			'' AS referenciaBancoCuenta,
			per.perid AS codigoGenesys,
			CASE WHEN per.perTipoIdentificacion = 'NIT' THEN 'E'
			ELSE 'P' END AS tipo,
			sol.solFechaRadicacion AS solFechaRadicacion, 
			sld.sldFechaOperacion AS fechaContabilizacion,
			sol.solUsuarioRadicacion AS usuario,
			pof.pofId AS pofId,
			CASE WHEN pas.pasRecursoPrioridad IS NULL OR pas.pasRecursoPrioridad='RECURSO_POR_APROPIACION' THEN '1' 
				 WHEN pas.pasRecursoPrioridad ='RECURSO_SEGUNDA_PRIORIDAD' THEN '2'
				 WHEN pas.pasRecursoPrioridad ='RECURSO_TERCERA_PRIORIDAD' THEN '3' END AS Componente
			FROM dbo.Solicitud sol
				INNER JOIN dbo.SolicitudLegalizacionDesembolso sld WITH (NOLOCK) ON sol.solid = sld.sldSolicitudGlobal
				AND sld.sldEstadoSolicitud IN ('DESEMBOLSO_FOVIS_SOLICITADO_A_AREA_FINANCIERA')
				INNER JOIN dbo.LegalizacionDesembolso lgd WITH (NOLOCK) ON sld.sldLegalizacionDesembolso = lgd.lgdId
				AND lgd.lgdFormaPago IN ('GIRO_ANTICIPADO_CONVENIO_CCF','GIRO_ANTICIPADO_AVAL_BANCARIO','GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO')
				AND lgd.lgdValorDesembolsar <= 80000.00000  --- se debe evaluar 
				INNER JOIN dbo.PostulacionFOVIS pof WITH (NOLOCK) ON pof.pofId = sld.sldPostulacionFOVIS
				AND	pof.pofEstadoHogar IN ('SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO') AND pof.pofMotivoDesistimiento IS NULL
				INNER JOIN dbo.jefehogar jeh WITH (NOLOCK) ON pof.pofJefeHogar = jeh.jehId
				INNER JOIN dbo.afiliado afi WITH (NOLOCK) ON jeh.jehAfiliado = afi.afiid
				INNER JOIN dbo.Persona per WITH (NOLOCK) ON per.perId = afi.afiPersona
				LEFT JOIN sap.MaestraIdentificacion mi WITH (NOLOCK) ON per.perTipoIdentificacion = mi.tipoIdGenesys
				LEFT JOIN sap.MaestraSector ms WITH (NOLOCK) ON pof.pofModalidad = ms.tipoSectorGenesys			
				LEFT JOIN PostulacionAsignacion pas WITH (NOLOCK) ON  pof.pofId = pas.pasPostulacionFovis
			WHERE	sol.solNumeroRadicacion =	@solNumeroRadicacion					
				AND solTipoTransaccion NOT LIKE  '%ADQUISICION_VIVIENDA%'
				
						

		
			set @pofID = (select pofID from #IC_FOVIS_Det)


		-- Extrae el numero de solicitud original de la asignacion.
		UPDATE #IC_FOVIS_Det
		SET asignacion = (
		SELECT top 1 sol.solNumeroRadicacion
		FROM dbo.postulacionFOVIS pof 
		INNER JOIN dbo.jefehogar jeh WITH (NOLOCK) ON pof.pofJefeHogar = jeh.jehId
		INNER JOIN dbo.afiliado afi WITH (NOLOCK) ON jehAfiliado = afi.afiid
		INNER JOIN dbo.persona per WITH (NOLOCK) ON per.perId = afi.afiPersona
		INNER JOIN dbo.SolicitudPostulacion spo WITH (NOLOCK) ON pof.pofId = spo.spoPostulacionFOVIS
		INNER JOIN dbo.Solicitud sol WITH (NOLOCK) ON sol.solId = spo.spoSolicitudGlobal
		WHERE pof.pofEstadoHogar IN ('SUBSIDIO_CON_ANTICIPO_DESEMBOLSADO')
		AND per.perNumeroIdentificacion = @documentoId
		AND per.perTipoIdentificacion = @tipoDocumento
		AND pofID = @pofID	)


-----------------------------------------------------------------------------------------Fin 40-------------------------------------------------------------------------------
	

-----------------------------------------------------------------------------------------Encabezado del detalle-------------------------------------------------------------------------------
	
			INSERT INTO #IC_FOVIS_Enc ([fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo]
				,[referencia],[tipoMovimiento],[observacion],[moneda],[documentoContable],[sociedad],[ejercicio]
				,[nroIntentos],[fecProceso],[horaProceso],[estadoReg], consecutivoTemp, usuario,[componente])
			SELECT CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
				CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
				solFechaRadicacion AS fechaDocumento, 
				fechaContabilizacion AS fechaContabilizacion, 
				MONTH(fechaContabilizacion) AS periodo, 
				'' AS referencia, 
				'F08' AS tipoMovimiento, 
				'' AS observacion, 
				'COP' AS moneda, 
				'' AS documentoContable, 
				'COMF' AS sociedad, 
				'' AS ejercicio, 
				'' AS nrointentos, 
				'' AS fecproceso, 
				'' AS horaproceso, 
				'P' AS estadoreg,
				consecutivo  AS consecutivoTemp,
				usuario AS usuario,
				Componente AS Componente
			FROM #IC_FOVIS_Det

	
--------------------------------------------------------------------50-----------------------------------------------------------------------------------------------------------------------
			INSERT INTO #IC_FOVIS_Det
			SELECT [consecutivo],[codigoSap],'50' AS [claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
				,[anulacion],[idProyecto],[nombreProyecto],[proyectoPropio],[rendimientoFinanciero],[asignacion],[clavePaisBanco],[claveBanco]
				,[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],[referenciaBancoCuenta],[codigoGenesys],[tipo]
				, solFechaRadicacion, fechaContabilizacion, usuario, pofId,Componente
			FROM #IC_FOVIS_Det
			

			
			
---------------------------------------------------------------FIN 40-------------------------------------------------------------------------------------------------------------------

			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			-- INSERCION DE REGISTROS EN LAS TABLAS DE INTEGRACIoN
			--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			-- Calcular el valor del campo referencia	

		DECLARE cursor_documentoF CURSOR FOR 
			SELECT consecutivoTemp
			FROM #IC_FOVIS_Enc
							
		
			OPEN cursor_documentoF 
			FETCH NEXT FROM cursor_documentoF INTO @consecutivoTemp
	
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
	
		FETCH NEXT FROM cursor_documentoF INTO @consecutivoTemp
		END 
			CLOSE cursor_documentoF 
			DEALLOCATE cursor_documentoF


	IF @referenciaNum IS NOT NULL 

		BEGIN

			
			INSERT INTO SAP.IC_FOVIS_Enc([fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo]
				,[referencia],[tipoMovimiento],[observacion],[moneda],[documentoContable],[sociedad],[ejercicio]
				,[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario], [consecutivoTemp],[Componente])
			SELECT [fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo]
				,[referencia],[tipoMovimiento],[observacion],[moneda],[documentoContable],[sociedad],[ejercicio]
				,[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario], [consecutivoTemp],[Componente]
			FROM #IC_FOVIS_Enc;
	
			-- Actualizar el consecutivo definitivo en la tabla de integracion de encabezado con el detalle
			SELECT @consecutivo = MAX(consecutivo) FROM IC_FOVIS_Enc
	
			UPDATE #IC_FOVIS_Det
			SET consecutivo = @consecutivo
	
			INSERT INTO SAP.IC_FOVIS_Det([consecutivo],[codigoSap],[claveCont],[claseCuenta],[importeDocumento],[ref1],[tipoDocumento],[ref3],[tipoSector]
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


	
		
		-- Detalle
		SELECT * FROM #IC_FOVIS_Enc;
		SELECT * FROM #IC_FOVIS_Det;
	
		DROP TABLE #IC_FOVIS_Enc;
		DROP TABLE #IC_FOVIS_Det;




	COMMIT TRANSACTION
END TRY  

BEGIN CATCH

	ROLLBACK TRANSACTION

		SELECT  ERROR_NUMBER() AS ErrorNumber  ,ERROR_SEVERITY() AS ErrorSeverity  
    ,ERROR_STATE() AS ErrorState      ,ERROR_PROCEDURE() AS ErrorProcedure  
    ,ERROR_LINE() AS ErrorLine      ,ERROR_MESSAGE() AS ErrorMessage;  

	INSERT INTO core.sap.LogErrorFovis 
			SELECT  GETDATE() -'05:00:00', @solNumeroRadicacion, @documentoId, @tipoDocumento,'F08', ERROR_NUMBER(),ERROR_SEVERITY(),ERROR_STATE(),ERROR_PROCEDURE(),ERROR_LINE(),ERROR_MESSAGE()
 
 
END CATCH
END