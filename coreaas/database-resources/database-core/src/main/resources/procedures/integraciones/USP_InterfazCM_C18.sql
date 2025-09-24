-- ====================================================================================================
-- Last modification: Yesika Bernal
-- Actualizado: Septiembre 09 DE 2023
-- Description: Insertar concatenado mov y asignacion en tabla de control
-- =====================================================================================================
 
CREATE OR ALTER   PROCEDURE  [sap].[USP_InterfazCM_C18] @liq varchar(20),@tipdoc varchar(20),@doc varchar(20) AS 
--DECLARE @liq varchar(20),@tipdoc varchar(20),@doc varchar(20)
BEGIN
SET NOCOUNT ON;
DECLARE	 @referenciaNum BIGINT, @consecutivo BIGINT,   @tipoMovimiento varchar(5);


--SET @liq= '23331746'
--SET @tipdoc = 'CEDULA_CIUDADANIA'
--SET @doc = '1020402157'

--CREACION DE TABLAS TEMPORALES PARA PRUEBAS 
CREATE TABLE #IC_CM_Enc([fecIng] [date] NOT NULL,
                        [horaIng] [time](7) NOT NULL,
						[fechaDocumento] [datetime] NOT NULL,
						[fechaContabilizacion] [date] NOT NULL,
						[periodo] [varchar](2) NOT NULL,
						[periodoAnioLiquidacion] [date] NOT NULL,
						[referencia] [varchar](16) NOT NULL,
						[tipoMovimiento] [varchar](3) NOT NULL,
						[consecutivo] [bigint] NOT NULL,
						[observacion] [varchar](2000) NULL,
						[moneda] [varchar](5) NOT NULL,
						[documentoContable] [varchar](10) NULL,
						[sociedad] [varchar](4) NOT NULL,
						[ejercicio] [varchar](4) NULL,
						[radicadoLiquidacion] [varchar](12) NOT NULL,
						[nroIntentos] [smallint] NOT NULL,
						[fecProceso] [date] NULL,
						[horaProceso] [time](7) NULL,
						[estadoReg] [varchar](1) NOT NULL)

CREATE TABLE #IC_CM_Det([consecutivo] [bigint] NOT NULL,
						[codigoSap] [varchar](10) NULL,
						[importeDocumento] [varchar](18) NOT NULL,
						[claveCont] [varchar](2) NOT NULL,
						[claseCuenta] [varchar](1) NOT NULL,
						[asignacion] [varchar](18) NOT NULL,
						[periodoliquidado][date],
						[ref1] [varchar](16) NOT NULL, 
						[ref2] [varchar](12) NULL,
						[tipoDocumento] [varchar](3) NOT NULL,
						[ref3] [varchar](20) NOT NULL,
						[tipoCuotaMonetaria] [varchar](4) NULL,
						[idTipoCm] [varchar](10) NULL,
						[formaPago] [varchar](2) NULL,
						[codigoEntidadDescuento] [bigint] NULL,
						[bancoDispersionDevolucion] [varchar](2) NULL,
						[cajaPago] [varchar](13) NULL,
						[codigoGenesys] [bigint] NULL,
						[tipo] [varchar](1) NULL,
						fechaDocumento [datetime] NOT NULL,
						[periodoAnioLiquidacion] [date] NOT NULL)


BEGIN TRY 
	BEGIN TRANSACTION
	
		
--------------------------------50-----------------------------------------------------			
		INSERT INTO #IC_CM_Det 
		
		SELECT   1 AS Consecutivo,
				'' as codigosap,--No aplica para este movimiento
				SUM(abs(dsa.dsaValorTotal)) AS Importedocumento,--valor pago
				'50' AS claveCont, -- credito
				'S' as claseCuenta, --segun archivo excel 40 y 50 = 'S'
				cas.casId AS asignacion,  --Id unico
				dsa.dsaPeriodoLiquidado AS periodoliquidado,
				LEFT (per.pernumeroidentificacion, 12) AS ref1,---adm de subsidio 
				casIdTransaccionOriginal AS ref2,--relación con una transacion anterior
				TipoIdHomologado AS tipoDocumento,-- Tipo de documento del adm
				LEFT(CONCAT(per.perPrimerNombre,' ',per.perSegundoNombre,' ',per.perPrimerApellido,' ',per.perSegundoApellido),20) AS ref3 ,--endNombre ó endCodigo (Interna se debe relacionar con Afiliación y las externas en la misma tabla)endTipo
				'' AS tipoCuotaMonetaria,--no aplica para este movimiento							
				'' AS idTipoCm, -- no aplica para este movimiento
				'01' AS formaPago, -- La novedad de cambio de medio de pago siempre va a efectivo
				'' AS codigoEntidadDescuento,-- no aplica para este movimiento
				'' AS bancoDispersionDevolucion,--case cas.casCodigoBanco when '1051' then 'DV' ELSE 'B1' END -- Banco al que va el pago
				cas.casSitioDePago AS cajaPago, 
				per.perid as codigoGenesys,
				'=' as tipo,
				casFechaHoraCreacionRegistro AS fechaDocumento, 
				max(dsa.dsaPeriodoLiquidado) AS periodoAnioLiquidacion
		FROM dbo.CuentaAdministradorSubsidio cas  with (nolock) 
		INNER JOIN dbo.DetalleSubsidioAsignado dsa with (nolock) ON (cas.casIdTransaccionOriginal = dsa.dsaCuentaAdministradorSubsidio)
		--AND dsa.dsaMotivoAnulacion = 'CAMBIO_MEDIO_DE_PAGO'
		LEFT JOIN dbo.DescuentosSubsidioAsignado as dcsa with (nolock) ON (dsa.dsaId = dcsa.desDetalleSubsidioAsignado)
		LEFT JOIN dbo.EntidadDescuento as entd with (nolock) ON (entd.endId = dcsa.desEntidadDescuento)
		INNER JOIN dbo.SolicitudLiquidacionSubsidio sls with (nolock)  ON (sls.slsid = cas.casSolicitudLiquidacionSubsidio)
		INNER JOIN dbo.Solicitud sol with (nolock)  ON (sol.solId = slsSolicitudGlobal)
		INNER JOIN dbo.AdministradorSubsidio ads with (nolock)  ON (ads.asuId = dsa.dsaAdministradorSubsidio)
		INNER JOIN dbo.Persona per with (nolock)  ON (ads.asuPersona = per.perId)
		INNER JOIN sap.MaestraTiposIdentificacion mti with (nolock)  ON (mti.TipoIdGenesys = per.perTipoIdentificacion)
		WHERE  cas.casId = @liq
		AND per.perTipoIdentificacion = @tipdoc
		AND per.perNumeroIdentificacion = @doc

		GROUP BY cas.casid,casValorRealTransaccion,perNumeroIdentificacion,casIdTransaccionOriginal,TipoIdHomologado,perPrimerNombre,perSegundoNombre,perPrimerApellido,perSegundoApellido
		,casSitioDePago,perId, casFechaHoraCreacionRegistro,dsa.dsaPeriodoLiquidado
		
			
--------------------------------------------------------------------------------40------------------------------------------------------------------------------------------------------------------
INSERT INTO #IC_CM_Det
SELECT		 1 AS Consecutivo,
				'' as codigosap,--No aplica para este movimiento
				SUM(abs(dsa.dsaValorTotal))  AS Importedocumento,--valor pago
				'40' AS claveCont, -- credito
				'S' as claseCuenta, --segun archivo excel 40 y 50 = 'S'
				cas.casId AS asignacion,  --Id unico
				dsa.dsaPeriodoLiquidado AS periodoliquidado,
				per.pernumeroidentificacion AS ref1,---adm de subsidio 
				casIdTransaccionOriginal AS ref2,--relación con una transacion anterior
				TipoIdHomologado AS tipoDocumento,-- Tipo de documento del adm
				LEFT(CONCAT(per.perPrimerNombre,' ',per.perSegundoNombre,' ',per.perPrimerApellido,' ',per.perSegundoApellido),20) AS ref3 ,--endNombre ó endCodigo (Interna se debe relacionar con Afiliación y las externas en la misma tabla)endTipo
				'' AS tipoCuotaMonetaria,--no aplica para este movimiento							
				'' AS idTipoCm, -- no aplica para este movimiento
				 mfp.codigo AS formaPago,
				'' AS codigoEntidadDescuento,-- no aplica para este movimiento
				'' AS bancoDispersionDevolucion,--case cas.casCodigoBanco when '1051' then 'DV' ELSE 'B1' END -- Banco al que va el pago
				cas.casSitioDePago AS cajaPago, 
				per.perid as codigoGenesys,
				'=' as tipo,
				casFechaHoraCreacionRegistro AS fechaDocumento,
				max(dsa.dsaPeriodoLiquidado) AS periodoAnioLiquidacion
		FROM dbo.CuentaAdministradorSubsidio cas  with (nolock) 
		INNER JOIN dbo.DetalleSubsidioAsignado dsa with (nolock) ON (cas.casId = dsa.dsaCuentaAdministradorSubsidio)
		LEFT JOIN dbo.DescuentosSubsidioAsignado as dcsa with (nolock) ON (dsa.dsaId = dcsa.desDetalleSubsidioAsignado)
		LEFT JOIN dbo.EntidadDescuento as entd with (nolock) ON (entd.endId = dcsa.desEntidadDescuento)
		INNER JOIN dbo.SolicitudLiquidacionSubsidio sls with (nolock)  ON (sls.slsid = cas.casSolicitudLiquidacionSubsidio)
		INNER JOIN dbo.Solicitud sol with (nolock)  ON (sol.solId = slsSolicitudGlobal)
		INNER JOIN dbo.AdministradorSubsidio ads with (nolock)  ON (ads.asuId = dsa.dsaAdministradorSubsidio)
		INNER JOIN dbo.Persona per with (nolock)  ON (ads.asuPersona = per.perId)
		INNER JOIN sap.MaestraTiposIdentificacion mti with (nolock)  ON (mti.TipoIdGenesys = per.perTipoIdentificacion)
		INNER JOIN sap.MaestraFormasPago mfp with (nolock) ON (cas.casMedioDePagoTransaccion = mfp.nombre)
		WHERE cas.casId = (SELECT TOP 1 ref2 FROM #IC_CM_Det)
		GROUP BY cas.casid,casValorRealTransaccion,perNumeroIdentificacion,casIdTransaccionOriginal,TipoIdHomologado,perPrimerNombre,perSegundoNombre,perPrimerApellido,perSegundoApellido
		,codigo,casSitioDePago,perId,dsa.dsaPeriodoLiquidado,casFechaHoraCreacionRegistro

		UPDATE  #IC_CM_Det SET asignacion = (SELECT TOP 1 ref2 FROM #IC_CM_Det) WHERE claveCont ='50'
		UPDATE  #IC_CM_Det SET ref2 = @liq




--insert into #IC_CM_Det 
--select [consecutivo],[codigoSap],[importeDocumento],'40' as [claveCont],[claseCuenta],[asignacion],[ref1],[ref2],[tipoDocumento],[ref3]
-- ,[tipoCuotaMonetaria],[idTipoCm],[formaPago],'' AS [codigoEntidadDescuento],[bancoDispersionDevolucion],[cajaPago],[codigoGenesys], det.tipo
--from #IC_CM_Det det



--LLENADO DE ENCABEZADO --- pendiente

		INSERT INTO #IC_CM_Enc

	SELECT  CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
				CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
				MIN(fechaDocumento) AS fechaDocumento, 
				CONVERT(varchar, SAP.GetLocalDate(), 111) AS fechaContabilizacion, 
				MONTH(CONVERT(varchar, SAP.GetLocalDate(), 111)) AS periodo, 
				MIN(periodoAnioLiquidacion) AS periodoAnioLiquidacion,
				'0' AS referencia, --validar que referencia se va usar
				'C18' AS tipoMovimiento, 
				1 AS consecutivo,
				'' AS observacion, 
				'COP' AS moneda, 
				'' AS documentoContable, 
				'COMF' AS sociedad, 
				'' AS ejercicio, 
				'' as radicadoliquidacion,
				'' AS nrointentos, 
				'' AS fecproceso, 
				'' AS horaproceso, 
				'P' AS estadoreg
		FROM #IC_CM_Det 
		WHERE claveCont = '50'


------------------------------------------------------------------------------------------------------------------------------------------
				   		 	  
-------------------------------------------------------------------------------------------------------------------------------------------


		
		SELECT @referenciaNum = valorActual 
		FROM sap.IC_Referencia 
		WHERE comentario = 'CT' 
		AND estado = 'A';
	
		UPDATE #IC_CM_Enc
		SET referencia = @referenciaNum
		

		
			UPDATE sap.IC_Referencia
			SET valorActual = @referenciaNum + 1
			WHERE comentario = 'CT' 
			AND estado = 'A';
     
	
---- Inicio comentar


IF @referenciaNum IS NOT NULL 

	BEGIN		

			INSERT INTO SAP.IC_CM_Enc ([fecIng],[horaIng]
      ,[fechaDocumento]
      ,[fechaContabilizacion]
      ,[periodo]
      ,[periodoAnioLiquidacion]
      ,[referencia]
      ,[tipoMovimiento]
      ,[observacion]
      ,[moneda]
      ,[documentoContable]
      ,[sociedad]
      ,[ejercicio]
      ,[radicadoLiquidacion]
      ,[nroIntentos]
      ,[fecProceso]
      ,[horaProceso]
      ,[estadoReg]
      ,[usuario])
	  
	  SELECT [fecIng],[horaIng]
      ,[fechaDocumento]
      ,[fechaContabilizacion]
      ,[periodo]
      ,[periodoAnioLiquidacion]
      ,[referencia]
      ,[tipoMovimiento]
      ,[observacion]
      ,[moneda]
      ,[documentoContable]
      ,[sociedad]
      ,[ejercicio]
      ,[radicadoLiquidacion]
      ,[nroIntentos]
      ,[fecProceso]
      ,[horaProceso]
      ,[estadoReg]
      ,consecutivo
		FROM #IC_CM_Enc;

	 UPDATE #IC_CM_Det SET CONSECUTIVO = (SELECT MAX(E.CONSECUTIVO) FROM  SAP.IC_CM_Enc E  INNER JOIN #IC_CM_Enc ET with (nolock) ON E.referencia = ET.referencia)	
	--SE QUITAN DATOS NO NECESARIOS EN CADA MOVIMIENTO
	
		----- UPDATE para el campo Tipo-----
		UPDATE #IC_CM_Det 
		SET tipo = CASE WHEN tipoDocumento = 'NIT' THEN 'E'
			ELSE 'P' END
	------------

	
	INSERT INTO SAP.IC_CM_Det ([consecutivo]
      ,[codigoSap]
      ,[importeDocumento]
      ,[claveCont]
      ,[asignacion]
      ,[ref1]
      ,[ref2]
      ,[tipoDocumento]
      ,[ref3]
      ,[tipoCuotaMonetaria]
      ,[idTipoCm]
      ,[formaPago]
      ,[codigoEntidadDescuento]
      ,[bancoDispersionDevolucion]
      ,[cajaPago]
      ,[codigoGenesys]
      ,[tipo]
      ,[claseCuenta]
      ,[usuario]
	  ,[FechaEjecucion]
	  ,[periodoliquidado]
	  )

	  SELECT [consecutivo]
      ,[codigoSap]
      ,[importeDocumento]
      ,[claveCont]
      ,[asignacion]
      ,[ref1]
      ,[ref2]
      ,[tipoDocumento]
      ,[ref3]
      ,[tipoCuotaMonetaria]
      ,[idTipoCm]
      ,[formaPago]
      ,[codigoEntidadDescuento]
      ,[bancoDispersionDevolucion]
      ,[cajaPago]
      ,[codigoGenesys]
      ,[tipo]
      ,[claseCuenta]
      ,'Genesys'
	   ,GETDATE()-'05:00:00'
	  ,[periodoliquidado]
	  FROM #IC_CM_Det

	  INSERT INTO sap.ContablesCtrl
			SELECT @liq, 1, @doc, @tipdoc, getdate(), 'CM', concat(@liq, 'C18')
			from #IC_CM_Det WHERE claveCont = '50'
	
	END
	
 --Fin comentar 
		SELECT *
		FROM #IC_CM_Enc;

		SELECT *
		FROM #IC_CM_Det;

		DROP TABLE IF EXISTS #IC_CM_Enc;
		DROP TABLE IF EXISTS #IC_CM_Det;
		
	COMMIT TRANSACTION 
END TRY
	BEGIN CATCH
		ROLLBACK TRANSACTION
			SELECT  ERROR_NUMBER() AS ErrorNumber,
			        ERROR_SEVERITY() AS ErrorSeverity,
					ERROR_STATE() AS ErrorState,
					ERROR_PROCEDURE() AS ErrorProcedure,
					ERROR_LINE() AS ErrorLine,
					ERROR_MESSAGE() AS ErrorMessage;  

					
			INSERT INTO core.sap.LogErrorCm 
			SELECT  GETDATE() -'05:00:00', @liq, @doc, @tipdoc,'C18', ERROR_NUMBER(),ERROR_SEVERITY(),ERROR_STATE(),ERROR_PROCEDURE(),ERROR_LINE(),ERROR_MESSAGE()
 
	END CATCH
END