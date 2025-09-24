-- ====================================================================================================
-- Last modification: Yesika Bernal 
-- Actualizado: 14 DE noviembre 2023
-- Description: Adicion de creacion de tablas temporales,
--  Script Date: 11/08/2023 9:41:51 a.m. 
-- =====================================================================================================
 
CREATE OR 
ALTER     PROCEDURE  [sap].[USP_InterfazCM_C20] @liq varchar(20),@tipdoc varchar(20),@doc varchar(20) AS 

BEGIN
SET NOCOUNT ON;
DECLARE	 @referenciaNum BIGINT, @consecutivo BIGINT;

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
						[usuario] [varchar](50) NULL,)

	CREATE TABLE #IC_CM_Det([consecutivo] [bigint] NOT NULL,
						[codigoSap] [varchar](10) NULL,
						[importeDocumento] [varchar](18) NOT NULL,
						[claveCont] [varchar](2) NOT NULL,
						[claseCuenta] [varchar](1) NOT NULL,
						[asignacion] [varchar](18) NOT NULL,
						[ref1] [varchar](16) NOT NULL, 
						[ref2] [varchar](12)  NULL,
						[tipoDocumento] [varchar](3) NOT NULL,
						[ref3] [varchar](20) NOT NULL,
						[tipoCuotaMonetaria] [varchar](4) NULL,
						[idTipoCm] [varchar](10) NULL,
						[formaPago] [varchar](2) NULL,
						[codigoEntidadDescuento] [bigint] NULL,
						[bancoDispersionDevolucion] [varchar](6) NULL,
						[cajaPago] [varchar](13) NULL,
						[codigoGenesys] [bigint] NULL,
						[tipo] [varchar](1) NULL,
						[usuario] [varchar](50) NULL,
						[fechaDocumento]  DATETIME,
						[periodoAnioLiquidacion] [date] NOT NULL,
						[radicadoLiquidacion] [varchar](30) NOT NULL,
						)


BEGIN TRY 
	BEGIN TRANSACTION
	
		
--------------------------------40-----------------------------------------------------			
		INSERT INTO #IC_CM_Det		
		SELECT   1 AS Consecutivo,
				'' as codigosap,--No aplica para este movimiento
				sum (cas.casValorRealTransaccion) AS Importedocumento,--valor pago
				'40' AS claveCont, -- credito
				'S' as claseCuenta, --segun archivo excel 40 y 50 = 'S'
				cas.casId AS asignacion,  --Id unico
				per.pernumeroidentificacion AS ref1,---adm de subsidio 
				casIdTransaccionOriginal AS ref2,--relacion con una transacion anterior
				TipoIdHomologado AS tipoDocumento,-- Tipo de documento del adm
				LEFT(CONCAT(per.perPrimerNombre,' ',per.perSegundoNombre,' ',per.perPrimerApellido,' ',per.perSegundoApellido),20) AS ref3 ,--endNombre o endCodigo (Interna se debe relacionar con Afiliacion y las externas en la misma tabla)endTipo
				'' AS tipoCuotaMonetaria,--no aplica para este movimiento							
				'' AS idTipoCm, -- no aplica para este movimiento
				 mfp.codigo AS formaPago,
				'' AS codigoEntidadDescuento,-- no aplica para este movimiento
				 cas.casCodigoBanco AS bancoDispersionDevolucion,
				'' AS cajaPago, -- No aplica para este movimiento
				per.perid as codigoGenesys,
				'' as tipo,
				casUsuarioCreacionRegistro as usuario,
				casFechaHoraCreacionRegistro AS fechaDocumento, 
				max(dsa.dsaPeriodoLiquidado) AS periodoAnioLiquidacion,
				sol.solNumeroRadicacion AS radicadoLiquidacion
		FROM dbo.CuentaAdministradorSubsidio cas  with (nolock) 
		INNER JOIN dbo.DetalleSubsidioAsignado dsa with (nolock) ON (cas.casid = dsa.dsaCuentaAdministradorSubsidio)
		LEFT JOIN dbo.DescuentosSubsidioAsignado as dcsa with (nolock) ON (dsa.dsaId = dcsa.desDetalleSubsidioAsignado)
		LEFT JOIN dbo.EntidadDescuento as entd with (nolock) ON (entd.endId = dcsa.desEntidadDescuento)
		INNER JOIN dbo.SolicitudLiquidacionSubsidio sls with (nolock)  ON (sls.slsid = cas.casSolicitudLiquidacionSubsidio)
		INNER JOIN dbo.Solicitud sol with (nolock)  ON (sol.solId = slsSolicitudGlobal)
		INNER JOIN dbo.AdministradorSubsidio ads with (nolock)  ON (ads.asuId = dsa.dsaAdministradorSubsidio)
		INNER JOIN dbo.Persona per with (nolock)  ON (ads.asuPersona = per.perId)
		INNER JOIN sap.MaestraTiposIdentificacion mti with (nolock)  ON (mti.TipoIdGenesys = per.perTipoIdentificacion)
		INNER JOIN sap.MaestraFormasPago mfp with (nolock) ON (cas.casMedioDePagoTransaccion = mfp.nombre)
		INNER JOIN dbo.MedioDePago mp with (nolock) on mdpid = casmediodepago
		INNER JOIN dbo.MedioTransferencia mt with (nolock) on mp.mdpid = mt.mdpid
		WHERE cas.casTipoTransaccionSubsidio = 'ABONO'
		AND cas.casEstadoTransaccionSubsidio = 'COBRADO'
		AND cas.casValorRealTransaccion > 0
		AND cas.casMedioDePagoTransaccion = 'TRANSFERENCIA'
		AND mt.metCobroJudicial =1
		AND cas.casId = @liq
		AND per.perTipoIdentificacion = @tipdoc
		AND per.perNumeroIdentificacion = @doc
		group by cas.casid,casValorRealTransaccion,per.perNumeroIdentificacion,mti.TipoIdHomologado,per.perPrimerNombre,per.perSegundoNombre
		,per.perPrimerApellido,per.perSegundoApellido,mfp.Codigo,entd.endCodigo,per.perId,cas.casCodigoBanco,cas.casIdTransaccionOriginal, casUsuarioCreacionRegistro,
		casFechaHoraCreacionRegistro, dsa.dsaPeriodoLiquidado,solNumeroRadicacion
		
---------------------------------------31------------------------------
insert into #IC_CM_Det 
select [consecutivo],  cod.codigoSAP AS [codigoSap],[importeDocumento],'31' AS [claveCont], 'K' AS [claseCuenta],[asignacion], perNumeroIdentificacion AS ref1,[ref2], 'NIT' as [tipoDocumento],  perRazonSocial AS [ref3]
,[tipoCuotaMonetaria],[idTipoCm],'' AS [formaPago],[codigoEntidadDescuento],'' AS [bancoDispersionDevolucion],[cajaPago],perid AS [codigoGenesys],det.tipo,[usuario],[fechaDocumento], [periodoAnioLiquidacion]
[usuario], [radicadoLiquidacion]
from #IC_CM_Det det
INNER JOIN Banco ON bancoDispersionDevolucion = banCodigoPILA
INNER JOIN Persona ON SUBSTRING (banNit, 1,09) = perNumeroIdentificacion
INNER JOIN sap.CodSAPGenesysAcreedor cod ON perId = cod.codigoGenesys



--LLENADO DE ENCABEZADO --- pendiente

		INSERT INTO #IC_CM_Enc
		SELECT  CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
				CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
				fechaDocumento AS fechaDocumento, 
				CONVERT(varchar, SAP.GetLocalDate(), 111) AS fechaContabilizacion, 
				MONTH(CONVERT(varchar, SAP.GetLocalDate(), 111)) AS periodo, 
				periodoAnioLiquidacion AS periodoAnioLiquidacion,
				'0' AS referencia, --validar que referencia se va usar
				'C20' AS tipoMovimiento, 
				1 AS consecutivo,
				'' AS observacion, 
				'COP' AS moneda, 
				'' AS documentoContable, 
				'COMF' AS sociedad, 
				'' AS ejercicio, 
				radicadoLiquidacion as radicadoliquidacion,
				'' AS nrointentos, 
				'' AS fecproceso, 
				'' AS horaproceso, 
				'P' AS estadoreg,
				usuario AS usuario
		FROM #IC_CM_Det WHERE claveCont ='40'
		group by fechaDocumento,periodoAnioLiquidacion,radicadoLiquidacion,usuario
		
	
-----------------------------------Actualizaciones--------------------------------------------------------------


		update #IC_CM_Det set tipo =  case tipoDocumento when 'NIT' then 'E'  else 'P' END 
		update #IC_CM_Det set bancoDispersionDevolucion = '', ref2 = ''

----------------------------------------------------------------------------------------------------------------

			
		SELECT @referenciaNum = valorActual 
		FROM sap.IC_Referencia 
		WHERE clase = 'SK'
		AND estado = 'A';
	
		UPDATE #IC_CM_Enc
		SET referencia = @referenciaNum
		where consecutivo = 1;
		

		
			UPDATE sap.IC_Referencia
			SET valorActual = @referenciaNum + 1
			WHERE clase = 'SK'
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

	--UPDATE #IC_CM_Det SET CONSECUTIVO = E.CONSECUTIVO FROM #IC_CM_Det D INNER JOIN IC_CM_Enc E ON D.consecutivo=E.USUARIO INNER JOIN #IC_CM_Enc ET ON E.referencia = ET.referencia
	 UPDATE #IC_CM_Det SET CONSECUTIVO = (SELECT MAX(E.CONSECUTIVO) FROM  SAP.IC_CM_Enc E  INNER JOIN #IC_CM_Enc ET with (nolock) ON E.referencia = ET.referencia)	

	--SE QUITAN DATOS NO NECESARIOS EN CADA MOVIMIENTO
		
	UPDATE #IC_CM_Det SET bancoDispersionDevolucion = ''  WHERE claveCont = '31'

	
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
	  ,[fechaEjecucion])

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
	  , getdate()-'05:00:00'
	  FROM #IC_CM_Det

	  INSERT INTO sap.ContablesCtrl
		SELECT @liq, 1, @doc, @tipdoc, getdate(), 'CM', concat(@liq,'C20')
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
	END CATCH
END