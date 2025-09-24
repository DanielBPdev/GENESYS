
-- ====================================================================================================
-- Last modification: Yesika Bernal
-- Actualizado: Septiembre 09 DE 2023
-- Description: Insertar concatenado mov y asignacion en tabla de control
-- =====================================================================================================
 
CREATE OR ALTER  PROCEDURE  [sap].[USP_InterfazCM_C07] @liq varchar(20),@tipdoc varchar(20),@doc varchar(20) AS 
--DECLARE @liq varchar(20),@tipdoc varchar(20),@doc varchar(20)

BEGIN
SET NOCOUNT ON;
DECLARE	 @referenciaNum BIGINT, @consecutivo BIGINT;


--SET @liq = '21492813'
--SET @tipdoc = 'CEDULA_CIUDADANIA'
--SET @doc = '15490700'


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
						[usuario][varchar](50))

CREATE TABLE #IC_CM_Det([consecutivo] [bigint] NOT NULL,
						[codigoSap] [varchar](10) NULL,
						[importeDocumento] [numeric](18,0) NOT NULL,
						[claveCont] [varchar](2) NOT NULL,
						[claseCuenta] [varchar](1) NOT NULL,
						[asignacion] [varchar](18) NOT NULL,
						[periodoliquidado][date],
						[ref1] [varchar](16) NOT NULL, 
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
						[fechaDocumento] [DATE] NOT NULL,
						[periodoAnioLiquidacion] [DATE] NOT NULL,
						[usuario][varchar](50)
						)


BEGIN TRY 
	BEGIN TRANSACTION
	
		
--------------------------------40-----------------------------------------------------			
		INSERT INTO #IC_CM_Det 		
		SELECT   1 AS Consecutivo,
				csga.codigoSAP as codigosap,--No aplica para este movimiento
				cas.casValorRealTransaccion AS Importedocumento,--valor pago
				'40' AS claveCont, -- credito
				'S' as claseCuenta, --segun archivo excel 40 y 50 = 'S'
				cas.casId AS asignacion,  --Id unico
				dsa.dsaPeriodoLiquidado AS periodoliquidado,
				per.pernumeroidentificacion AS ref1,---adm de subsidio 
				'' AS ref2,--no aplica para este movimiento
				TipoIdHomologado AS tipoDocumento,-- Tipo de documento del adm
				LEFT(CONCAT(per.perPrimerNombre,' ',per.perSegundoNombre,' ',per.perPrimerApellido,' ',per.perSegundoApellido),20) AS ref3 ,--endNombre ó endCodigo (Interna se debe relacionar con Afiliación y las externas en la misma tabla)endTipo
				'' AS tipoCuotaMonetaria,--no aplica para este movimiento							
				'' AS idTipoCm, -- no aplica para este movimiento
				 mfp.codigo AS formaPago,
				'' AS codigoEntidadDescuento,-- no aplica para este movimiento
				'' AS bancoDispersionDevolucion, -- no aplica para este movimiento
				'' AS cajaPago, --no aplica para este movimiento
				per.perid AS codigoGenesys,
				'=' AS tipo,
				casFechaHoraCreacionRegistro AS fechaDocumento,
				dsa.dsaPeriodoLiquidado AS periodoAnioLiquidacion,
				casUsuarioUltimaModificacion as usuario
		FROM dbo.CuentaAdministradorSubsidio cas  with (nolock) 
		INNER JOIN	dbo.DetalleSubsidioAsignado dsa with (nolock) ON (cas.casid = dsa.dsaCuentaAdministradorSubsidio)
		LEFT JOIN	dbo.DescuentosSubsidioAsignado as dcsa with (nolock) ON (dsa.dsaId = dcsa.desDetalleSubsidioAsignado)
		LEFT JOIN	dbo.EntidadDescuento as entd with (nolock) ON (entd.endId = dcsa.desEntidadDescuento)
		INNER JOIN	dbo.SolicitudLiquidacionSubsidio sls with (nolock)  ON (sls.slsid = cas.casSolicitudLiquidacionSubsidio)
		INNER JOIN	dbo.Solicitud sol with (nolock)  ON (sol.solId = slsSolicitudGlobal)
		INNER JOIN	dbo.AdministradorSubsidio ads with (nolock)  ON (ads.asuId = dsa.dsaAdministradorSubsidio)
		INNER JOIN	dbo.Persona per with (nolock)  ON (ads.asuPersona = per.perId)
		INNER JOIN	sap.MaestraTiposIdentificacion mti with (nolock)  ON (mti.TipoIdGenesys = per.perTipoIdentificacion)
		INNER JOIN	sap.MaestraFormasPago mfp with (nolock) ON (cas.casMedioDePagoTransaccion = mfp.nombre)
		LEFT JOIN	sap.CodSAPGenesysAcreedor csga ON per.perId = csga.codigoGenesys
		WHERE cas.casTipoTransaccionSubsidio = 'ABONO'
		AND cas.casEstadoTransaccionSubsidio = 'COBRADO'
		AND cas.casValorRealTransaccion > 0
		AND cas.casMedioDePagoTransaccion = 'TARJETA'
		AND cas.casIdCuentaAdmonSubsidioRelacionado =  @liq
		AND per.perTipoIdentificacion = @tipdoc
		AND per.perNumeroIdentificacion = @doc

		group by cas.casid,casValorRealTransaccion,per.perNumeroIdentificacion,mti.TipoIdHomologado,per.perPrimerNombre,per.perSegundoNombre
		,per.perPrimerApellido,per.perSegundoApellido,mfp.Codigo,per.perId,cas.casCodigoBanco,cas.casIdTransaccionOriginal,csga.codigoSAP
		,cas.casSitioDePago, cas.casFechaHoraCreacionRegistro, dsa.dsaPeriodoLiquidado,casUsuarioUltimaModificacion 

	---------------------------------------31------------------------------
INSERT INTO #IC_CM_Det 
SELECT [consecutivo],csga.codigoSAP [codigoSap],SUM([importeDocumento]),'31' as [claveCont],'K' as[claseCuenta],'' as asignacion,PeriodoLiquidado,per.perNumeroIdentificacion as [ref1],[ref2],'NIT' as [tipoDocumento],left(perRazonSocial,20) as[ref3]
,[tipoCuotaMonetaria],[idTipoCm],[formaPago],[codigoEntidadDescuento],[bancoDispersionDevolucion],[cajaPago],PER.perId [codigoGenesys],det.tipo, fechaDocumento, '', [usuario]
FROM #IC_CM_Det det  
INNER JOIN CuentaAdministradorSubsidio cas  WITH (NOLOCK)  ON asignacion = cas.casId
INNER JOIN CuentaAdministradorSubsidio cas2 WITH (NOLOCK)  ON cas.casIdCuentaAdmonSubsidioRelacionado = cas2.casId
INNER JOIN EstablecimientosMediosPago est WITH (NOLOCK)  ON cas2.casNombreTerceroPagado = est.estCodigo
INNER JOIN Persona per WITH (NOLOCK)  ON est.estPersona = per.perId
LEFT JOIN sap.CodSAPGenesysAcreedor csga WITH (NOLOCK)  ON per.perId = csga.codigoGenesys
GROUP BY [consecutivo],csga.codigoSAP,perNumeroIdentificacion,ref2,perRazonSocial,tipoCuotaMonetaria,[idTipoCm],[formaPago],[codigoEntidadDescuento],[bancoDispersionDevolucion],[cajaPago],PER.perId ,det.tipo,PeriodoLiquidado,fechaDocumento, usuario

UPDATE #IC_CM_Det SET codigoSap = '' WHERE claveCont = '40'  

-- Fin update vaciar campo codigo SAP para la clave de contabilización 40

--------------------------------------------------------------------LLENADO DE ENCABEZADO ----------------------------------------------------
 if exists(select * from #IC_CM_Det where claveCont = '31')

	BEGIN

		INSERT INTO #IC_CM_Enc

		SELECT TOP 1 CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
				CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
				max(det.fechaDocumento)  AS fechaDocumento, 
				CONVERT(varchar, SAP.GetLocalDate(), 111) AS fechaContabilizacion, 
				MONTH(CONVERT(varchar, SAP.GetLocalDate(), 111)) AS periodo, 
				max(det.periodoAnioLiquidacion) AS periodoAnioLiquidacion,
				'0' AS referencia, --validar que referencia se va usar
				'C07' AS tipoMovimiento, 
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
				'P' AS estadoreg,
				det.usuario as usuario
		FROM #IC_CM_Det det WHERE claveCont = '40'
		group by det.usuario

			
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
	
	
	END



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
	

	--SE QUITAN DATOS NO NECESARIOS EN CADA MOVIMIENTO
	UPDATE #IC_CM_Det SET ref2 = '' , formaPago = '' WHERE claveCont = '31'
	UPDATE #IC_CM_Det SET cajaPago = ''  WHERE claveCont = '40'

	----- update para el campo Tipo-----
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

	

		  INSERT INTO sap.ContablesCtrl
			SELECT top 1 @liq, 1, @doc, @tipdoc, getdate(), 'CM', concat(@liq, 'C07')
			from #IC_CM_Det WHERE claveCont = '40'
	END
	

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
			SELECT  GETDATE() -'05:00:00', @liq, @doc, @tipdoc,'C07', ERROR_NUMBER(),ERROR_SEVERITY(),ERROR_STATE(),ERROR_PROCEDURE(),ERROR_LINE(),ERROR_MESSAGE()
 
	END CATCH
END