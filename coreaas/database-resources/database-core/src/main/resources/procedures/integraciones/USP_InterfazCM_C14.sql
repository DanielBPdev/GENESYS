-- ====================================================================================================
-- Last modification: Yesika Bernal
-- Actualizado: Septiembre 09 DE 2023
-- Description: Insertar concatenado mov y asignacion en tabla de control
-- =====================================================================================================
 
CREATE OR ALTER PROCEDURE  [sap].[USP_InterfazCM_C14] @liq varchar(20),@tipdoc varchar(20),@doc varchar(20) AS 

--DECLARE @liq varchar(20),@tipdoc varchar(20),@doc varchar(20)
BEGIN
SET NOCOUNT ON;
DECLARE	 @referenciaNum BIGINT, @consecutivo BIGINT;

--SET @liq = '23232916'
--SET @tipdoc = 'CEDULA_CIUDADANIA'
--SET @doc = '35586482'


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
						[tipo] [varchar](1) NULL)


BEGIN TRY 
	BEGIN TRANSACTION



	CREATE TABLE #REFDESCUENTO(
	smvcodigoreferencia   VARCHAR(20),
	smvNumeroRadicado  VARCHAR(30),
	smvTipoIdentificacionAdministrador  VARCHAR(30),
	smvNumeroIdentificacionAdministrador  VARCHAR(30),
	ShardName VARCHAR(100)
	)

INSERT INTO #REFDESCUENTO
	EXEC sp_execute_remote SubsidioReferenceData, N'
	select distinct smvcodigoreferencia, smvNumeroRadicado, smvTipoIdentificacionAdministrador,
	smvNumeroIdentificacionAdministrador  from [dbo].subsidiomonetariovalorpignorado 
	WHERE  smvcodigoreferencia IS NOT NULL'

	

	--  EXEC sp_execute_remote SubsidioReferenceData, N'
	--select distinct smvcodigoreferencia, smvTipoIdentificacionAdministrador,
	--smvNumeroIdentificacionAdministrador,*  from [dbo].subsidiomonetariovalorpignorado 
	--WHERE smvNumeroIdentificacionAdministrador = ''1003125259'' and smvcodigoreferencia IS NOT NULL'
	 
--SELECT * FROM #REFDESCUENTO WHERE smvTipoIdentificacionAdministrador = 'CEDULA_CIUDADANIA' AND smvNumeroIdentificacionAdministrador = '1003125259'



--------------------------------50-----------------------------------------------------			
		INSERT INTO #IC_CM_Det 
		SELECT   ROW_NUMBER() OVER(ORDER BY cas.casid desc) AS Consecutivo,
				csga.codigoSAP as codigosap,--No aplica para este movimiento
				sum(desMontoDescontado)  AS Importedocumento,--valor pago
				'50' AS claveCont, -- credito
				'S' as claseCuenta, --segun archivo excel 40 y 50 = 'S'
				cas.casId AS asignacion,  --Id unico
				dsa.dsaPeriodoLiquidado as periodoliquidado,
				per.pernumeroidentificacion AS ref1,---adm de subsidio 
				smvcodigoreferencia AS ref2,--relación con una transacion anterior
				TipoIdHomologado AS tipoDocumento,-- Tipo de documento del adm
				LEFT(CONCAT(per.perPrimerNombre,' ',per.perSegundoNombre,' ',per.perPrimerApellido,' ',per.perSegundoApellido),20) AS ref3 ,--endNombre ó endCodigo (Interna se debe relacionar con Afiliación y las externas en la misma tabla)endTipo
				CASE					
					when   dsa.dsaPeriodoLiquidado < CONVERT(date, DATEADD(month, -2, solFechaRadicacion)) and dsa.dsaTipoCuotaSubsidio = 'AGRICOLA'  --and slsTipoLiquidacionEspecifica = ''
						THEN 'TC11'

					when dsa.dsaPeriodoLiquidado < CONVERT(date, DATEADD(month, -2, solFechaRadicacion)) and dsa.dsaTipoCuotaSubsidio = 'DISCAPACIDAD'  
						THEN 'TC05'

					when dsa.dsaPeriodoLiquidado < CONVERT(date, DATEADD(month, -2, solFechaRadicacion)) and dsa.dsaTipoCuotaSubsidio = 'DISCAPACIDAD_AGRICOLA'  
						THEN 'TC12'

					when dsa.dsaPeriodoLiquidado < CONVERT(date, DATEADD(month, -2, solFechaRadicacion)) and dsa.dsaTipoCuotaSubsidio = 'REGULAR'  
						then 'TC02'						
					else 
					mtc.Homologacion  END  AS tipoCuotaMonetaria,  --dsaTipoCuotaSubsidio 							
				mtc.Tipo AS idTipoCm, 
				'' AS formaPago,-- no aplica para este movimiento
				entd.endCodigo AS codigoEntidadDescuento,
				'' AS bancoDispersionDevolucion, -- no aplica para este movimiento
				'' AS cajaPago, 
				per.perid as codigoGenesys,
				'=' as tipo
		FROM dbo.CuentaAdministradorSubsidio cas  with (nolock) 
		Inner JOIN dbo.DetalleSubsidioAsignado dsa with (nolock) ON (cas.casid = dsa.dsaCuentaAdministradorSubsidio)
		INNER JOIN dbo.DescuentosSubsidioAsignado as dcsa with (nolock) ON (dsa.dsaId = dcsa.desDetalleSubsidioAsignado)
		INNER JOIN dbo.EntidadDescuento as entd with (nolock) ON (entd.endId = dcsa.desEntidadDescuento)
		INNER JOIN dbo.SolicitudLiquidacionSubsidio sls with (nolock)  ON (sls.slsid = cas.casSolicitudLiquidacionSubsidio)
		INNER JOIN dbo.Solicitud sol with (nolock)  ON (sol.solId = slsSolicitudGlobal)
		INNER JOIN dbo.AdministradorSubsidio ads with (nolock)  ON (ads.asuId = dsa.dsaAdministradorSubsidio)
		INNER JOIN dbo.Persona per with (nolock)  ON (ads.asuPersona = per.perId)
		INNER JOIN sap.MaestraTiposIdentificacion mti with (nolock)  ON (mti.TipoIdGenesys = per.perTipoIdentificacion)
		INNER JOIN sap.MaestraFormasPago mfp with (nolock) ON (cas.casMedioDePagoTransaccion = mfp.nombre)
		LEFT JOIN  sap.CodSAPGenesysAcreedor csga ON per.perId = csga.codigoGenesys
		INNER JOIN sap.MaestraTipoCuotaSubsidio mtc ON mtc.subsidio = dsa.dsaTipoCuotaSubsidio
		INNER JOIN #REFDESCUENTO  ON perNumeroIdentificacion = smvNumeroIdentificacionAdministrador AND solNumeroRadicacion = smvNumeroRadicado
		WHERE cas.casTipoTransaccionSubsidio = 'ABONO'
		AND cas.casEstadoTransaccionSubsidio IN ('COBRADO', 'APLICADO')
		AND desMontoDescontado > 0
		AND entd.endTipo = 'INTERNA'
		AND cas.casId = @liq
		AND per.perTipoIdentificacion = @tipdoc
		AND per.perNumeroIdentificacion = @doc

		group by cas.casid,casValorRealTransaccion,per.perNumeroIdentificacion,mti.TipoIdHomologado,per.perPrimerNombre,per.perSegundoNombre
		,per.perPrimerApellido,per.perSegundoApellido,mfp.Codigo,entd.endCodigo,per.perId,cas.casCodigoBanco,cas.casIdTransaccionOriginal,csga.codigoSAP
		,cas.casSitioDePago,mtc.Homologacion,mtc.Tipo, slsTipoLiquidacionEspecifica, dsaTipoCuotaSubsidio, dsaPeriodoLiquidado,solFechaRadicacion, smvcodigoreferencia
		
---------------------------------------40------------------------------
insert into #IC_CM_Det 
select [consecutivo],[codigoSap],[importeDocumento],'40' as [claveCont],[claseCuenta],[asignacion],[periodoliquidado],[ref1],[ref2],[tipoDocumento],[ref3]
,[tipoCuotaMonetaria],[idTipoCm],[formaPago],[codigoEntidadDescuento],[bancoDispersionDevolucion],[cajaPago],[codigoGenesys], det.tipo
from #IC_CM_Det det

--LLENADO DE ENCABEZADO --- pendiente

		INSERT INTO #IC_CM_Enc

		SELECT  CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
				CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
				casFechaHoraCreacionRegistro  AS fechaDocumento, 
				CONVERT(varchar, SAP.GetLocalDate(), 111) AS fechaContabilizacion, 
				MONTH(CONVERT(varchar, SAP.GetLocalDate(), 111)) AS periodo, 
				max(dsa.dsaPeriodoLiquidado) AS periodoAnioLiquidacion,
				'0' AS referencia, --validar que referencia se va usar
				'C14' AS tipoMovimiento, 
				ROW_NUMBER() OVER(ORDER BY cas.casid desc) AS consecutivo,
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

		FROM dbo.CuentaAdministradorSubsidio cas  with (nolock) 
		INNER JOIN dbo.DetalleSubsidioAsignado dsa with (nolock) ON (cas.casid = dsa.dsaCuentaAdministradorSubsidio)
		INNER JOIN dbo.DescuentosSubsidioAsignado as dcsa with (nolock) ON (dsa.dsaId = dcsa.desDetalleSubsidioAsignado)
		INNER JOIN dbo.EntidadDescuento as entd with (nolock) ON (entd.endId = dcsa.desEntidadDescuento)
		INNER JOIN dbo.SolicitudLiquidacionSubsidio sls with (nolock)  ON (sls.slsid = cas.casSolicitudLiquidacionSubsidio)
		INNER JOIN dbo.Solicitud sol with (nolock)  ON (sol.solId = slsSolicitudGlobal)
		INNER JOIN dbo.AdministradorSubsidio ads with (nolock)  ON (ads.asuId = dsa.dsaAdministradorSubsidio)
		INNER JOIN dbo.Persona per with (nolock)  ON (ads.asuPersona = per.perId)
		INNER JOIN sap.MaestraTiposIdentificacion mti with (nolock)  ON (mti.TipoIdGenesys = per.perTipoIdentificacion)
		INNER JOIN sap.MaestraFormasPago mfp with (nolock) ON (cas.casMedioDePagoTransaccion = mfp.nombre)
		LEFT JOIN  sap.CodSAPGenesysAcreedor csga ON per.perId = csga.codigoGenesys

		WHERE cas.casTipoTransaccionSubsidio = 'ABONO'
		AND cas.casEstadoTransaccionSubsidio IN ('COBRADO', 'APLICADO')
		AND desMontoDescontado > 0
		AND entd.endTipo = 'INTERNA'
		AND cas.casId = @liq
		AND per.perTipoIdentificacion = @tipdoc
		AND per.perNumeroIdentificacion = @doc

		group by cas.casId,codigoSAP,per.perNumeroIdentificacion,TipoIdHomologado,per.perPrimerNombre,per.perSegundoNombre,per.perPrimerApellido,
		per.perSegundoApellido,entd.endCodigo,per.perId,casFechaHoraCreacionRegistro

						
			
		SELECT @referenciaNum = valorActual 
		FROM sap.IC_Referencia 
		WHERE comentario = 'CT' 
		AND estado = 'A';
	
		UPDATE #IC_CM_Enc
		SET referencia = @referenciaNum
		where consecutivo = @consecutivo;

		/*
			UPDATE sap.IC_Referencia
			SET valorActual = @referenciaNum + 1
			WHERE comentario = 'CT' 
			AND estado = 'A';
		*/
			

/*

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

	*/
	UPDATE #IC_CM_Det SET codigoEntidadDescuento = '' WHERE claveCont = '50'
	UPDATE #IC_CM_Det SET codigoSap = '' , tipoCuotaMonetaria = '', idTipoCm = '' WHERE claveCont = '40'

	----- UPDATE para el campo Tipo-----
		UPDATE #IC_CM_Det 
		SET tipo = CASE WHEN tipoDocumento = 'NIT' THEN 'E'
			ELSE 'P' END
	------------
 /*
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
      ,'SOPORTE'
	  ,GETDATE()-'05:00:00'
	  ,[periodoliquidado]
	  FROM #IC_CM_Det

  INSERT INTO sap.ContablesCtrl
		SELECT DISTINCT @liq, 1, @doc, @tipdoc, getdate(), 'CM', concat(det.asignacion,'C14') FROM #IC_CM_Det  det  WHERE claveCont = '40'
	
	
	END
	*/
		SELECT *
		FROM #IC_CM_Enc;

		SELECT *
		FROM #IC_CM_Det;

		DROP TABLE IF EXISTS #IC_CM_Enc;
		DROP TABLE IF EXISTS #IC_CM_Det;
		DROP TABLE IF EXISTS #REFDESCUENTO;
		
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
	END CATCH
END
