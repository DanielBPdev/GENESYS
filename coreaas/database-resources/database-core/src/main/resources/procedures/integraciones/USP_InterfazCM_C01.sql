
-- ====================================================================================================
-- Last modification: Yesika Bernal
-- Actualizado: SEPTIEMBRE 27  DE 2023
-- Description: Adicion de condicionales para liquidaciones especificas,
-- =====================================================================================================
 
CREATE OR ALTER  PROCEDURE  [sap].[USP_InterfazCM_C01] @liq varchar(30),@tipdoc varchar(20),@doc varchar(20), @casid BIGINT 
AS
BEGIN
--declare @liq varchar(30),@tipdoc varchar(20),@doc varchar(20), @casid BIGINT 
SET NOCOUNT ON;
DECLARE	  @referenciaNum BIGINT, @consecutivo BIGINT,  @peiodomin varchar (20), @Observacion varchar(20);

--set @liq = '032024007650'
--set @tipdoc = 'CEDULA_CIUDADANIA'
--set @doc = '39176239'
--set @casid = '24649143'

IF (SELECT TOP 1 solNumeroRadicacion FROM SAP.ContablesCtrl WHERE solNumeroRadicacion = @liq AND perTipoIdentificacion = @tipdoc AND perNumeroIdentificacion = @doc AND observaciones LIKE CONCAT (@casid, '%C01%') AND fechaIngreso >='2023-08-15') IS NULL
	
	BEGIN

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
							[radicadoLiquidacion] [varchar](30) NOT NULL,
							[nroIntentos] [smallint] NOT NULL,
							[fecProceso] [date] NULL,
							[horaProceso] [time](7) NULL,
							[estadoReg] [varchar](1) NOT NULL,
							[usuario][varchar](50))

	CREATE TABLE #IC_CM_Det([consecutivo] [bigint] NOT NULL,
							[codigoSap] [varchar](10) NULL,
							[importeDocumento] INT NOT NULL,
							[claveCont] [varchar](2) NOT NULL,
							[claseCuenta] [varchar](1) NOT NULL,
							[asignacion] [varchar](18) NOT NULL,
							[periodoliquidado][date],
							[ref1] [varchar](16) NOT NULL, 
							[ref2] [varchar](12)  NULL,
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
							[fechaDocumento] [datetime] NOT NULL,
							[periodoAnioLiquidacion] [date] NOT NULL,
							[tipoliquidacion] [VARCHAR](60),
							[liquidaEspec][VARCHAR](40), 
							[dsa] [VARCHAR](10),
							[grupoFamiliar] [VARCHAR](20),
							[usuario][varchar](50)
							)


	BEGIN TRY 
		BEGIN TRANSACTION
	
		
	--------------------------------------------------------------------50------------------------------------------------------------------------------------------------------------			

			INSERT INTO #IC_CM_Det 		
			SELECT DISTINCT 1 AS Consecutivo,
					'' as codigosap,--No aplica para este movimiento
					dsa.dsaValorTotal AS Importedocumento,--valor pago
					'50' AS claveCont, -- credito
					'S' as claseCuenta, --segun archivo excel 40 y 50 = 'S'
					casId AS asignacion,  --Id unico
					dsaPeriodoLiquidado AS periodoliquidado,
					left(per.pernumeroidentificacion,12) AS ref1,---adm de subsidio 
					casIdTransaccionOriginal AS ref2,--No aplica para este movimiento 
					TipoIdHomologado AS tipoDocumento,-- Tipo de documento del adm
					LEFT(CONCAT(per.perPrimerNombre,' ',per.perSegundoNombre,' ',per.perPrimerApellido,' ',per.perSegundoApellido),20) AS ref3 ,--endNombre ó endCodigo (Interna se debe relacionar con Afiliación y las externas en la misma tabla)endTipo
					CASE					
					when   dsa.dsaPeriodoLiquidado < CONVERT(date, DATEADD(month, -1, solFechaRadicacion)) and dsa.dsaTipoCuotaSubsidio = 'AGRICOLA'  --and slsTipoLiquidacionEspecifica = ''
						THEN 'TC11'

					when dsa.dsaPeriodoLiquidado < CONVERT(date, DATEADD(month, -1, solFechaRadicacion)) and dsa.dsaTipoCuotaSubsidio = 'DISCAPACIDAD'  
						THEN 'TC05'

					when dsa.dsaPeriodoLiquidado < CONVERT(date, DATEADD(month, -1, solFechaRadicacion)) and dsa.dsaTipoCuotaSubsidio = 'DISCAPACIDAD_AGRICOLA'  
						THEN 'TC12'

					when dsa.dsaPeriodoLiquidado < CONVERT(date, DATEADD(month, -1, solFechaRadicacion)) and dsa.dsaTipoCuotaSubsidio = 'REGULAR'  
						then 'TC02'						
					else 
					mtc.Homologacion  END  AS tipoCuotaMonetaria,				
					mtc.Tipo AS idTipoCm,
					 mfp.codigo AS formaPago,
					 ''  AS codigoEntidadDescuento,
					'' AS bancoDispersionDevolucion, -- No aplica para este movimiento
					'' AS cajaPago, -- No aplica para este movimiento
					per.perid as codigoGenesys,
					'=' as tipo,
					sls.slsFechaDispersion as fechaDocumento,
					dsaPeriodoLiquidado as periodoAnioLiquidacion,
					dsaTipoliquidacionSubsidio AS tipoliquidacion,
					slsTipoLiquidacionEspecifica AS liquidaEspec,
					dsaBeneficiarioDetalle,
					dsaGrupoFamiliar as grupoFamiliar,
					slsUsuarioEvaluacionSegundoNivel as usuario
			FROM core.dbo.CuentaAdministradorSubsidio cas  with (nolock) 
			INNER JOIN core.dbo.DetalleSubsidioAsignado dsa with (nolock) ON (cas.casid = dsa.dsaCuentaAdministradorSubsidio)
			INNER JOIN core.dbo.SolicitudLiquidacionSubsidio sls with (nolock)  ON (sls.slsid = cas.casSolicitudLiquidacionSubsidio)
			INNER JOIN core.dbo.Solicitud sol with (nolock)  ON (sol.solId = slsSolicitudGlobal)
			INNER JOIN core.dbo.AdministradorSubsidio ads with (nolock)  ON (ads.asuId = dsa.dsaAdministradorSubsidio)
			INNER JOIN core.dbo.Persona per with (nolock)  ON (ads.asuPersona = per.perId)
			INNER JOIN core.sap.MaestraTiposIdentificacion mti with (nolock)  ON (mti.TipoIdGenesys = per.perTipoIdentificacion)
			INNER JOIN core.sap.MaestraFormasPago mfp with (nolock) ON (cas.casMedioDePagoTransaccion = mfp.nombre)
			INNER JOIN core.sap.MaestraTipoCuotaSubsidio mtc with (nolock)  ON mtc.subsidio = dsa.dsaTipoCuotaSubsidio
			INNER JOIN core.dbo.PeriodoLiquidacion pel with (nolock) ON pel.pelSolicitudLiquidacionSubsidio = sls.slsId
		
			WHERE dsa.dsaEstado = 'DERECHO_ASIGNADO'
			AND sol.solResultadoProceso = 'DISPERSADA'
			AND sol.solNumeroRadicacion = @liq
			AND per.perTipoIdentificacion = @tipdoc
			AND per.perNumeroIdentificacion =@doc
			AND casId = @casid
			AND dsa.dsaValorTotal > 0
			--AND casEstadoTransaccionSubsidio IN ('APLICADO', 'ENVIADO')
				
	
			--DELETE FROM #IC_CM_Det     SELECT * FROM #IC_CM_Det
			--DROP TABLE #casidanterior  SELECT * FROM #casidanterior

	-- Agrupamiento importe de documento sin afectar grupo familiar	
	INSERT INTO #IC_CM_Det 		
		SELECT 2,[codigoSap], SUM([importeDocumento]) AS importeDocumento ,'50' as [claveCont],'S' [claseCuenta], [asignacion], [periodoliquidado], [ref1],[ref2], [tipoDocumento],[ref3]
		,[tipoCuotaMonetaria],[idTipoCm],[formaPago],'',[bancoDispersionDevolucion],[cajaPago],[codigoGenesys],det.tipo,fechaDocumento,periodoAnioLiquidacion,'', '', '','',[usuario]
		FROM #IC_CM_Det det

		GROUP BY [codigoSap], asignacion,[periodoliquidado], ref1,ref2, tipoDocumento, ref3,[tipoCuotaMonetaria],[idTipoCm],
		[bancoDispersionDevolucion],[cajaPago],[codigoGenesys],det.tipo,fechaDocumento,periodoAnioLiquidacion,[formaPago],[usuario]
		
		delete #IC_CM_Det where consecutivo = 1
	----------------------------------------------------------------------------------------50D------------------------------------------------------------------------------------------------

		SELECT  dsaValorDescuento 
			INTO  #DESCUENTO FROM core.dbo.CuentaAdministradorSubsidio cas
			INNER JOIN core.dbo.DetalleSubsidioAsignado dsa with (nolock) ON (cas.casid = dsa.dsaCuentaAdministradorSubsidio)
			INNER JOIN core.dbo.SolicitudLiquidacionSubsidio sls with (nolock)  ON (sls.slsid = cas.casSolicitudLiquidacionSubsidio)
			INNER JOIN core.dbo.Solicitud sol with (nolock)  ON (sol.solId = sls.slsSolicitudGlobal)
			INNER JOIN core.dbo.AdministradorSubsidio ads with (nolock)  ON (ads.asuId = dsa.dsaAdministradorSubsidio)
			INNER JOIN core.dbo.Persona per with (nolock)  ON (ads.asuPersona = per.perId)
			WHERE  sol.solNumeroRadicacion = @liq
			AND	   per.perTipoIdentificacion = @tipdoc
			AND	   per.perNumeroIdentificacion = @doc
			AND	   casId = @casid
			--AND casEstadoTransaccionSubsidio IN ('APLICADO', 'ENVIADO')
			DELETE #DESCUENTO WHERE dsaValorDescuento = 0

		
		
		IF (SELECT TOP 1 dsavalordescuento FROM  #descuento)  > 0
		BEGIN
			--SELECT * from #IC_CM_Det
			--SELECT * FROM #DESCUENTO
			INSERT INTO #IC_CM_Det	
			SELECT  1 AS Consecutivo,
					'' as codigosap,--No aplica para este movimiento
					case  when SUM (dcsa.desMontoDescontado) is null then sum(dsa.dsavalordescuento) else SUM (dcsa.desMontoDescontado) end AS Importedocumento,--valor pago
					'50' AS claveCont, -- credito
					'S' as claseCuenta, --segun archivo excel 40 y 50 = 'S'
					casId AS asignacion,  --Id unico
					dsaPeriodoLiquidado AS periodoliquidado,
					left(per.pernumeroidentificacion,12) AS ref1,---adm de subsidio 
					'' AS ref2,--No aplica para este movimiento 
					TipoIdHomologado AS tipoDocumento,-- Tipo de documento del adm
					LEFT(CONCAT(per.perPrimerNombre,' ',per.perSegundoNombre,' ',per.perPrimerApellido,' ',per.perSegundoApellido),20) AS ref3 ,--endNombre ó endCodigo (Interna se debe relacionar con Afiliación y las externas en la misma tabla)endTipo
					CASE					
					when   dsa.dsaPeriodoLiquidado < CONVERT(date, DATEADD(month, -1, solFechaRadicacion)) and dsa.dsaTipoCuotaSubsidio = 'AGRICOLA'  --and slsTipoLiquidacionEspecifica = ''
						THEN 'TC11'

					when dsa.dsaPeriodoLiquidado < CONVERT(date, DATEADD(month, -1, solFechaRadicacion)) and dsa.dsaTipoCuotaSubsidio = 'DISCAPACIDAD'  
						THEN 'TC05'

					when dsa.dsaPeriodoLiquidado < CONVERT(date, DATEADD(month, -1, solFechaRadicacion)) and dsa.dsaTipoCuotaSubsidio = 'DISCAPACIDAD_AGRICOLA'  
						THEN 'TC12'

					when dsa.dsaPeriodoLiquidado < CONVERT(date, DATEADD(month, -1, solFechaRadicacion)) and dsa.dsaTipoCuotaSubsidio = 'REGULAR'  
						then 'TC02'						
					else					 
					mtc.Homologacion  END  AS tipoCuotaMonetaria,					
					mtc.Tipo AS idTipoCm,
					 mfp.codigo AS formaPago,
					 entd.endCodigo   AS codigoEntidadDescuento,
					'' AS bancoDispersionDevolucion, -- No aplica para este movimiento
					'' AS cajaPago, -- No aplica para este movimiento
					per.perid as codigoGenesys,
					'=' as tipo,
					sls.slsFechaDispersion as fechaDocumento,
					pri.priperiodo as periodoAnioLiquidacion,
					dsaTipoliquidacionSubsidio AS tipoliquidacion,
					'',
					'' AS dsa,
					'',
					slsUsuarioEvaluacionSegundoNivel as usuario
			FROM core.dbo.CuentaAdministradorSubsidio cas  with (nolock) 
			INNER JOIN core.dbo.DetalleSubsidioAsignado dsa with (nolock) ON (cas.casid = dsa.dsaCuentaAdministradorSubsidio)
			LEFT JOIN core.dbo.DescuentosSubsidioAsignado as dcsa with (nolock) ON (dsa.dsaId = dcsa.desDetalleSubsidioAsignado)
			LEFT JOIN core.dbo.EntidadDescuento as entd with (nolock) ON (entd.endId = dcsa.desEntidadDescuento)
			INNER JOIN core.dbo.SolicitudLiquidacionSubsidio sls with (nolock)  ON (sls.slsid = cas.casSolicitudLiquidacionSubsidio)
			INNER JOIN core.dbo.Solicitud sol with (nolock)  ON (sol.solId = slsSolicitudGlobal)
			INNER JOIN core.dbo.AdministradorSubsidio ads with (nolock)  ON (ads.asuId = dsa.dsaAdministradorSubsidio)
			INNER JOIN core.dbo.Persona per with (nolock)  ON (ads.asuPersona = per.perId)
			INNER JOIN core.sap.MaestraTiposIdentificacion mti with (nolock)  ON (mti.TipoIdGenesys = per.perTipoIdentificacion)
			INNER JOIN core.sap.MaestraFormasPago mfp with (nolock) ON (cas.casMedioDePagoTransaccion = mfp.nombre)
			INNER JOIN core.sap.MaestraTipoCuotaSubsidio mtc with (nolock) ON mtc.subsidio = dsa.dsaTipoCuotaSubsidio
			INNER JOIN core.dbo.PeriodoLiquidacion pel with (nolock)  ON pel.pelSolicitudLiquidacionSubsidio = sls.slsId
			INNER JOIN core.dbo.Periodo pri with (nolock)  ON pel.pelPeriodo = pri.priId

			WHERE dsa.dsaEstado = 'DERECHO_ASIGNADO'
			AND sol.solResultadoProceso = 'DISPERSADA'
			--AND casEstadoTransaccionSubsidio IN ('APLICADO' ,'ENVIADO', 'GENERADO')
			AND (dsa.dsaValorDescuento > 0 )
			AND sol.solNumeroRadicacion = @liq
			AND per.perTipoIdentificacion = @tipdoc
			AND per.perNumeroIdentificacion = @doc
			AND casId = @casid
			--AND casEstadoTransaccionSubsidio IN ('APLICADO', 'ENVIADO')
		
			GROUP BY casId,perNumeroIdentificacion,TipoIdHomologado,perPrimerNombre,perSegundoNombre,perPrimerApellido,perSegundoApellido,Homologacion,tipo,Codigo,endCodigo,perId,dsaPeriodoLiquidado,dsaTipoCuotaSubsidio,priperiodo,slsTipoLiquidacionEspecifica,
			sls.slsFechaDispersion,pri.priperiodo, dsaTipoliquidacionSubsidio, solFechaRadicacion, slsUsuarioEvaluacionSegundoNivel
		END	

	------------------------------------------------------------------FIN-----------------------------------------------------------------------------------------------------------
	----------------------------------------------------------------------------------40--------------------------------------------------------------------------------------------			

	INSERT INTO #IC_CM_Det 		
	SELECT 1,[codigoSap], SUM([importeDocumento]) AS importeDocumento ,'40' as [claveCont],'S' [claseCuenta], [asignacion], [periodoliquidado], [ref1],[ref2], [tipoDocumento],[ref3]
	,[tipoCuotaMonetaria],[idTipoCm],'','',[bancoDispersionDevolucion],[cajaPago],[codigoGenesys],det.tipo,fechaDocumento,periodoAnioLiquidacion,'', '', '','',[usuario]
	FROM #IC_CM_Det det

	GROUP BY [codigoSap], asignacion,[periodoliquidado], ref1,ref2, tipoDocumento, ref3,[tipoCuotaMonetaria],[idTipoCm],
	[bancoDispersionDevolucion],[cajaPago],[codigoGenesys],det.tipo,fechaDocumento,periodoAnioLiquidacion,[usuario]


	--LLENADO DE ENCABEZADO

			INSERT INTO #IC_CM_Enc 
			SELECT TOP 1  CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing,                    
					CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
					det.fechaDocumento AS fechaDocumento, 
					CONVERT(varchar, SAP.GetLocalDate(), 111) AS fechaContabilizacion, 
					MONTH(CONVERT(varchar, SAP.GetLocalDate(), 111)) AS periodo, 
					det.periodoAnioLiquidacion AS periodoAnioLiquidacion,
					'0' AS referencia, 
					'C01' AS tipoMovimiento, 
					1 AS consecutivo,
					'' AS observacion, 
					'COP' AS moneda, 
					'' AS documentoContable, 
					'COMF' AS sociedad, 
					'' AS ejercicio, 
					@liq as radicadoliquidacion,
					'' AS nrointentos, 
					'' AS fecproceso, 
					'' AS horaproceso, 
					'P' AS estadoreg,
					det.usuario as usuario
				FROM #IC_CM_Det det WHERE claveCont = '50'

			

	
	---------------------------fin--------------------------------------------------------------
		
			SELECT @referenciaNum = valorActual 
			FROM sap.IC_Referencia 
			WHERE comentario = 'LP'
			AND estado = 'A';
	

			UPDATE #IC_CM_Enc
			SET referencia = @referenciaNum 
			where consecutivo = 1;


	  --Inicio Comentariado para no enviar 
  	  
				UPDATE sap.IC_Referencia
				SET valorActual = @referenciaNum + 1
				WHERE comentario = 'LP'
				AND estado = 'A';
	 
		 
		--Fin Comentariado para no enviar


	--Inicio comentariado 
	
	
	
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
		  ,case when [usuario] is null then 1 else [usuario] end
			FROM #IC_CM_Enc;


	
		UPDATE #IC_CM_Det SET CONSECUTIVO = (SELECT MAX(E.CONSECUTIVO) FROM  SAP.IC_CM_Enc E  INNER JOIN #IC_CM_Enc ET with (nolock) ON E.referencia = ET.referencia)
 
	 
	--- Fin de comentario 

	 --SE QUITAN DATOS NO NECESARIOS EN CADA MOVIMIENTO
		UPDATE #IC_CM_Det SET tipoCuotaMonetaria = '' , idTipoCm = '' WHERE claveCont = '50'
		UPDATE #IC_CM_Det SET formaPago = '' , codigoEntidadDescuento = '' WHERE claveCont = '40'
		UPDATE #IC_CM_Det SET formaPago ='' WHERE claveCont ='50' AND (codigoEntidadDescuento <> 0) 



		----- UPDATE para el campo Tipo-----
			UPDATE #IC_CM_Det 
			SET tipo = CASE WHEN tipoDocumento = 'NIT' THEN 'E'
				ELSE 'P' END
		-----------------------------------

	--INICIO COMENTARIO
	
	
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
		  ,[fechaEjecucion]
		  ,[periodoliquidado])

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
		  ,case when [usuario] is null then 1 else [usuario] end
		  ,GETDATE()-'05:00:00'
		  ,[periodoliquidado]
		  FROM #IC_CM_Det

	  

	----- Inserción tabla de control 

	SELECT @observacion = (SELECT TOP 1 CONCAT(min(asignacion), 'C01') FROM #IC_CM_Det)

		  INSERT INTO sap.ContablesCtrl
			SELECT @liq, 1, @doc, @tipdoc, getdate(), 'CM', @Observacion
		IF (SELECT TOP 1 CONCAT (asignacion,'C01')  FROM #IC_CM_Det WHERE CONCAT (asignacion,'C01') <> @Observacion)  <>
			@Observacion
			BEGIN
			SELECT @liq, 1, @doc, @tipdoc, getdate(), 'CM', (SELECT TOP 1 CONCAT(max(asignacion), 'C01') FROM #IC_CM_Det)
			END
	END
	
		 
  
	--Fin Comentariado

			SELECT *
			FROM #IC_CM_Enc;

			SELECT *
			FROM #IC_CM_Det
			ORDER BY claveCont DESC;

			DROP TABLE IF EXISTS #IC_CM_Enc;
			DROP TABLE IF EXISTS #IC_CM_Det;
			DROP TABLE IF EXISTS #DESCUENTO;
			DROP TABLE IF EXISTS #casidanterior
		
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
			SELECT  GETDATE() -'05:00:00', @liq, @doc, @tipdoc,'C01', ERROR_NUMBER(),ERROR_SEVERITY(),ERROR_STATE(),ERROR_PROCEDURE(),ERROR_LINE(),ERROR_MESSAGE()
 

		END CATCH
	END
END