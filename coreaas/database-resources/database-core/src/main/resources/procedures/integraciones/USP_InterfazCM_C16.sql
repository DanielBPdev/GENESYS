
-- ====================================================================================================
-- Last modification: Yesika Bernal
-- Actualizado: Septiembre 09 DE 2023
-- Description: Insertar concatenado mov y asignacion en tabla de control
-- =====================================================================================================
 
CREATE OR 
ALTER  PROCEDURE  [sap].[USP_InterfazCM_C16] @liq varchar(20),@tipdoc varchar(20),@doc varchar(20) AS 

--DECLARE @liq varchar(20),@tipdoc varchar(20),@doc varchar(20)
BEGIN
SET NOCOUNT ON;
DECLARE	 @referenciaNum BIGINT, @consecutivo BIGINT;


--SET @liq = '120235227794'
--SET @tipdoc = 'CEDULA_CIUDADANIA'
--SET @doc = '1027998737'
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
						[estadoReg] [varchar](1) NOT NULL,
						[grupoFamiliar] [varchar](20) NULL,
						[usuario][varchar](50))

CREATE TABLE #IC_CM_Det([consecutivo] [bigint] NOT NULL,
						[codigoSap] [varchar](10) NULL,
						[importeDocumento] [varchar](18) NOT NULL,
						[claveCont] [varchar](2) NOT NULL,
						[claseCuenta] [varchar](1) NOT NULL,
						[asignacion] [varchar](18) NOT NULL,
						[periodoliquidado][date],
						[ref1] [varchar](16) NOT NULL, --SE AMPLIO ESTE CAMPO POR REGISTROS CON MAYOR LONGITUD DE 12 A 16
						[ref2] [varchar](12) NOT NULL,
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
						[grupoFamiliar] [varchar](20) NULL,
						[usuario][varchar](50))


BEGIN TRY 
	BEGIN TRANSACTION
	
		--SELECT @consecutivo = MAX(consecutivo)+1 
		--  FROM sap.IC_CM_Enc
--------------------------------50-----------------------------------------------------			
		INSERT INTO #IC_CM_Det 
		
		SELECT  1 AS Consecutivo,
				'' as codigosap,--No aplica para este movimiento
				sum(dsa.dsaValorTotal)AS Importedocumento,--valor pago
				'50' AS claveCont, -- credito
				'S' as claseCuenta, --segun archivo excel 40 y 50 = 'S'
				cas.casid AS asignacion,  --Id unico
				dsa.dsaPeriodoLiquidado AS periodoliquidado,
				per.pernumeroidentificacion AS ref1,---adm de subsidio 
				'' AS ref2,--No aplica para este movimiento 
				TipoIdHomologado AS tipoDocumento,-- Tipo de documento del adm
				LEFT(CONCAT(per.perPrimerNombre,' ',per.perSegundoNombre,' ',per.perPrimerApellido,' ',per.perSegundoApellido),20) AS ref3 ,--endNombre ó endCodigo (Interna se debe relacionar con Afiliación y las externas en la misma tabla)endTipo
				'' AS tipoCuotaMonetaria,--dsaTipoCuotaSubsidio 							
				'' AS idTipoCm,
				mfp.Codigo AS formaPago,
				'' AS codigoEntidadDescuento,--
				'' AS bancoDispersionDevolucion, -- No aplica para este movimiento
				'' AS cajaPago, -- No aplica para este movimiento
				per.perid as codigoGenesys,
				'=' as tipo,
				casFechaHoraCreacionRegistro AS fechaDocumento, 
				max(dsa.dsaPeriodoLiquidado) AS periodoAnioLiquidacion,
				dsa.dsaGrupoFamiliar as grupoFamiliar,
				dsa.dsaUsuarioTransaccionAnulacion as usuario
		FROM dbo.CuentaAdministradorSubsidio cas  with (nolock) 
		INNER JOIN dbo.DetalleSubsidioAsignado dsa with (nolock) ON (cas.casid = dsa.dsaCuentaAdministradorSubsidio) AND dsa.dsaEstado = 'ANULADO'
		AND dsa.dsaMotivoAnulacion = 'PRESCRIPCION'
		LEFT JOIN dbo.DescuentosSubsidioAsignado as dcsa with (nolock) ON (dsa.dsaId = dcsa.desDetalleSubsidioAsignado)
		LEFT JOIN dbo.EntidadDescuento as entd with (nolock) ON (entd.endId = dcsa.desEntidadDescuento)
		INNER JOIN dbo.SolicitudLiquidacionSubsidio sls with (nolock)  ON (sls.slsid = cas.casSolicitudLiquidacionSubsidio)
		INNER JOIN dbo.Solicitud sol with (nolock)  ON (sol.solId = slsSolicitudGlobal)
		INNER JOIN dbo.AdministradorSubsidio ads with (nolock)  ON (ads.asuId = dsa.dsaAdministradorSubsidio)
		INNER JOIN dbo.Persona per with (nolock)  ON (ads.asuPersona = per.perId)
		INNER JOIN sap.MaestraTiposIdentificacion mti with (nolock)  ON (mti.TipoIdGenesys = per.perTipoIdentificacion)
		INNER JOIN sap.MaestraFormasPago mfp with (nolock) ON (cas.casMedioDePagoTransaccion = mfp.nombre)
		INNER JOIN dbo.PeriodoLiquidacion pel with (nolock) ON pel.pelSolicitudLiquidacionSubsidio = sls.slsId
		INNER JOIN sap.MaestraTipoCuotaSubsidio mtc with (nolock)  ON mtc.subsidio = dsa.dsaTipoCuotaSubsidio
		INNER JOIN dbo.Periodo pri with (nolock)  ON pel.pelPeriodo = pri.priId
		WHERE cas.casValorRealTransaccion > 0
		AND sol.solNumeroRadicacion = @liq
		AND per.perTipoIdentificacion = @tipdoc
		AND per.perNumeroIdentificacion = @doc		
		GROUP BY cas.casid, cas.casValorRealTransaccion, per.pernumeroidentificacion, TipoIdHomologado,per.perPrimerNombre,per.perSegundoNombre,per.perPrimerApellido,
				per.perSegundoApellido,mfp.Codigo,per.perid, casFechaHoraCreacionRegistro,  dsa.dsaGrupoFamiliar, dsa.dsaPeriodoLiquidado, dsa.dsaValorTotal,
				dsa.dsaUsuarioTransaccionAnulacion 

---------------------------------------40------------------------------
insert into #IC_CM_Det 
select [consecutivo],[codigoSap],[importeDocumento],'40' as [claveCont],[claseCuenta],[asignacion], [periodoliquidado],[ref1],[ref2],[tipoDocumento],[ref3]
,[tipoCuotaMonetaria],[idTipoCm],[formaPago],[codigoEntidadDescuento],[bancoDispersionDevolucion],[cajaPago],[codigoGenesys], det.tipo, det.fechaDocumento, 
det.periodoAnioLiquidacion, det.grupoFamiliar, det.[usuario]
from #IC_CM_Det det


--LLENADO DE ENCABEZADO

		INSERT INTO #IC_CM_Enc

		SELECT TOP 1 CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
				CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
				fechaDocumento AS fechaDocumento, 
				CONVERT(varchar, SAP.GetLocalDate(), 111) AS fechaContabilizacion, 
				MONTH(CONVERT(varchar, SAP.GetLocalDate(), 111)) AS periodo, 
				periodoAnioLiquidacion AS periodoAnioLiquidacion,
				'' AS referencia, --validar que referencia se va usar
				'C16' AS tipoMovimiento, 
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
				grupoFamiliar as grupoFamiliar,
				det.usuario as usuario
			FROM #IC_CM_Det det
			group by fechaDocumento,periodoAnioLiquidacion,grupoFamiliar,usuario

/*
--Actualizacion asignación original

	UPDATE #IC_CM_Det SET  asignacion = cas.casId  FROM dbo.CuentaAdministradorSubsidio cas  with (nolock) 
			INNER JOIN dbo.DetalleSubsidioAsignado dsa with (nolock) ON (cas.casid = dsa.dsaCuentaAdministradorSubsidio)
			LEFT JOIN  dbo.DescuentosSubsidioAsignado as dcsa with (nolock) ON (dsa.dsaId = dcsa.desDetalleSubsidioAsignado)
			LEFT JOIN  dbo.EntidadDescuento as entd with (nolock) ON (entd.endId = dcsa.desEntidadDescuento)
			INNER JOIN dbo.SolicitudLiquidacionSubsidio sls with (nolock)  ON (sls.slsid = cas.casSolicitudLiquidacionSubsidio)
			INNER JOIN dbo.Solicitud sol with (nolock)  ON (sol.solId = sls.slsSolicitudGlobal)
			INNER JOIN dbo.AdministradorSubsidio ads with (nolock)  ON (ads.asuId = dsa.dsaAdministradorSubsidio)
			INNER JOIN dbo.Persona per with (nolock)  ON (ads.asuPersona = per.perId)
			--INNER JOIN #IC_CM_Enc enc with (nolock)  ON (sol.solNumeroRadicacion = enc.radicadoLiquidacion)
			WHERE  --endEstado = 'ACTIVA'
			 casIdTransaccionOriginal IS NULL
			AND per.perTipoIdentificacion = @tipdoc
			AND per.perNumeroIdentificacion = @doc
			AND sol.solNumeroRadicacion = @liq
			--AND dsa.dsaPeriodoLiquidado = enc.fechaDocumento
			--AND dsa.dsaGrupoFamiliar = enc.grupoFamiliar
			

	*/				
------------------------------------------------------------------------------------------------------------------------------------------


		SELECT @referenciaNum = valorActual 
		FROM sap.IC_Referencia 
		WHERE comentario = 'LP' 
		AND estado = 'A';
					
			
		SELECT @referenciaNum = valorActual 
		FROM sap.IC_Referencia 
		WHERE comentario = 'LP' 
		AND estado = 'A';
	
		UPDATE #IC_CM_Enc
		SET referencia = @referenciaNum
	
 
			UPDATE sap.IC_Referencia
			SET valorActual = @referenciaNum + 1
			WHERE comentario = 'LP' 
			AND estado = 'A';

	
	


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
	--UPDATE #IC_CM_Det SET CONSECUTIVO = E.CONSECUTIVO FROM #IC_CM_Det D INNER JOIN IC_CM_Enc E ON D.consecutivo=E.USUARIO INNER JOIN #IC_CM_Enc ET ON E.referencia = ET.referencia
	
	UPDATE #IC_CM_Det SET formaPago = '', codigoEntidadDescuento = ''   WHERE claveCont = '50'
	UPDATE #IC_CM_Det SET codigoEntidadDescuento = ''   WHERE claveCont = '40' 
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
	  ,[fechaEjecucion]
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
      ,case when [usuario] is null then 1 else [usuario] end
	   ,GETDATE()-'05:00:00'
	  ,[periodoliquidado]
	  FROM #IC_CM_Det

	  INSERT INTO sap.ContablesCtrl
			SELECT @liq, 1, @doc, @tipdoc, getdate(), 'CM', concat(asignacion, 'C16')
			FROM #IC_CM_Det WHERE claveCont = '50'
	
	END




------------------------ Eliminar registros en cero----------------------------------------------


 DELETE FROM #IC_CM_Det WHERE importeDocumento < '0'

--------------------------------------------------------------------------------------------------

		SELECT *
		FROM #IC_CM_Enc;

		SELECT *
		FROM #IC_CM_Det
		WHERE convert (numeric, importeDocumento) > 0;

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
			SELECT  GETDATE() -'05:00:00', @liq, @doc, @tipdoc,'C16', ERROR_NUMBER(),ERROR_SEVERITY(),ERROR_STATE(),ERROR_PROCEDURE(),ERROR_LINE(),ERROR_MESSAGE()
 
	END CATCH
END