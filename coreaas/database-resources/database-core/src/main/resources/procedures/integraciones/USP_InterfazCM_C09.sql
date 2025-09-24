-- ====================================================================================================
-- Last modification: Valbuena Marlon
-- Actualizado: AGOSTO 01 DE 2022
-- Description: Adicion de creacion de tablas temporales,
-- Script Date: 11/08/2023 9:36:48 a.m.
-- =====================================================================================================
 
CREATE OR ALTER PROCEDURE  [sap].[USP_InterfazCM_C09] @liq varchar(20),@tipdoc varchar(20),@doc varchar(20) AS 

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
						[periodoAnioLiquidacion] [DATE] NOT NULL						
						)


BEGIN TRY 
	BEGIN TRANSACTION
	
		
--------------------------------31-----------------------------------------------------			
		INSERT INTO #IC_CM_Det 
		
	SELECT   ROW_NUMBER() OVER(ORDER BY cas.casid desc) AS Consecutivo,
				 csga.codigoSAP AS codigosap,--No aplica para este movimiento
				sum(desMontoDescontado) AS Importedocumento,--valor pago
				'31' AS claveCont, -- credito
				'K' AS claseCuenta, --segun archivo excel 40 y 50 = 'S'
				cas.casId AS asignacion,  --Id unico
				ent.pernumeroidentificacion AS ref1,---adm de subsidio 
				'' AS ref2,--no aplica para este movimiento
				ent.perTipoIdentificacion AS tipoDocumento,-- Tipo de documento del adm
				left(ent.perRazonSocial,20) AS ref3 ,--endNombre o endCodigo (Interna se debe relacionar con Afiliacion y las externas en la misma tabla)endTipo
				'' AS tipoCuotaMonetaria,--no aplica para este movimiento							
				'' AS idTipoCm, -- no aplica para este movimiento
				'' AS formaPago, --no aplica para este movimiento
				entd.endCodigo AS codigoEntidadDescuento,
				'' AS bancoDispersionDevolucion, -- no aplica para este movimiento
				'' AS cajaPago, -- no aplica para este movimiento
				ent.perId AS codigoGenesys,
				'=' AS tipo,
				casFechaHoraCreacionRegistro AS fechaDocumento,
				dsa.dsaPeriodoLiquidado AS periodoAnioLiquidacion
FROM dbo.CuentaAdministradorSubsidio cas  with (nolock) 
		INNER JOIN dbo.DetalleSubsidioAsignado dsa with (nolock) ON (cas.casid = dsa.dsaCuentaAdministradorSubsidio)
		INNER JOIN dbo.DescuentosSubsidioAsignado AS dcsa with (nolock) ON (dsa.dsaId = dcsa.desDetalleSubsidioAsignado)
		INNER JOIN dbo.EntidadDescuento AS entd with (nolock) ON (entd.endId = dcsa.desEntidadDescuento)
		INNER JOIN dbo.SolicitudLiquidacionSubsidio sls with (nolock)  ON (sls.slsid = cas.casSolicitudLiquidacionSubsidio)
		INNER JOIN dbo.Solicitud sol with (nolock)  ON (sol.solId = slsSolicitudGlobal)
		INNER JOIN dbo.AdministradorSubsidio ads with (nolock)  ON (ads.asuId = dsa.dsaAdministradorSubsidio)
		INNER JOIN dbo.Persona per with (nolock)  ON (ads.asuPersona = per.perId)
		INNER JOIN dbo.Empresa emp with (nolock) ON (endEmpresa = empId)
		INNER JOIN dbo.persona ent with (nolock) ON (empPersona = ent.perId)
		INNER JOIN sap.MaestraTiposIdentificacion mti2 with (nolock)  ON (mti2.TipoIdGenesys = ent.perTipoIdentificacion)
		INNER JOIN sap.MaestraTiposIdentificacion mti with (nolock)  ON (mti.TipoIdGenesys = per.perTipoIdentificacion)
		INNER JOIN sap.MaestraFormasPago mfp with (nolock) ON (cas.casMedioDePagoTransaccion = mfp.nombre)
		LEFT JOIN  sap.CodSAPGenesysAcreedor csga ON ent.perId = csga.codigoGenesys
		

		WHERE cas.casTipoTransaccionSubsidio = 'ABONO'
		AND cas.casEstadoTransaccionSubsidio IN ('COBRADO', 'ENVIADO','APLICADO')
		AND desMontoDescontado > 0
		AND entd.endTipo = 'EXTERNA'
		AND cas.casId = @liq
		AND per.perTipoIdentificacion = @tipdoc
		AND per.perNumeroIdentificacion = @doc

		GROUP BY cas.casId,csga.codigoSAP,per.perNumeroIdentificacion,ent.perTipoIdentificacion,per.perPrimerNombre,per.perSegundoNombre,per.perPrimerApellido,
		per.perSegundoApellido,entd.endCodigo,ent.perId,ent.pernumeroidentificacion,ent.perRazonSocial,casFechaHoraCreacionRegistro,dsa.dsaPeriodoLiquidado
		
		
---------------------------------------40------------------------------
INSERT INTO #IC_CM_Det 
SELECT [consecutivo],[codigoSap],[importeDocumento],'40' AS [claveCont],'S' AS [claseCuenta],[asignacion],
(SELECT perNumeroIdentificacion FROM persona per WHERE per.perTipoIdentificacion = @tipdoc
		AND per.perNumeroIdentificacion = @doc)[ref1],[ref2],
(SELECT TipoIdHomologado FROM persona per with (nolock) INNER JOIN sap.MaestraTiposIdentificacion mti with (nolock)  ON (mti.TipoIdGenesys = per.perTipoIdentificacion) WHERE per.perTipoIdentificacion = @tipdoc
		AND per.perNumeroIdentificacion = @doc) [tipoDocumento],
(SELECT LEFT(CONCAT(per.perPrimerNombre,' ',per.perSegundoNombre,' ',per.perPrimerApellido,' ',per.perSegundoApellido),20) FROM persona per WHERE per.perTipoIdentificacion = @tipdoc
		AND per.perNumeroIdentificacion = @doc) [ref3]
,[tipoCuotaMonetaria],[idTipoCm],[formaPago],[codigoEntidadDescuento],[bancoDispersionDevolucion],[cajaPago],(SELECT perId FROM persona per WHERE per.perTipoIdentificacion = @tipdoc
		AND per.perNumeroIdentificacion = @doc) [codigoGenesys], det.tipo
FROM #IC_CM_Det det 


--LLENADO DE ENCABEZADO --- pendiente

		INSERT INTO #IC_CM_Enc

		SELECT  CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
				CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
				det.fechaDocumento AS fechaDocumento, 
				CONVERT(varchar, SAP.GetLocalDate(), 111) AS fechaContabilizacion, 
				MONTH(CONVERT(varchar, SAP.GetLocalDate(), 111)) AS periodo, 
				max(det.periodoAnioLiquidacion) AS periodoAnioLiquidacion,
				'0' AS referencia, --validar que referencia se va usar
				'C09' AS tipoMovimiento, 
				ROW_NUMBER() OVER(ORDER BY det.consecutivo desc) AS consecutivo,
				'' AS observacion, 
				'COP' AS moneda, 
				'' AS documentoContable, 
				'COMF' AS sociedad, 
				'' AS ejercicio, 
				'' AS radicadoliquidacion,
				'' AS nrointentos, 
				'' AS fecproceso, 
				'' AS horaproceso, 
				'P' AS estadoreg

		FROM #IC_CM_Det det

		DECLARE cursor_documentoId CURSOR FOR 
			SELECT consecutivo
			FROM #IC_CM_Enc
							
		
		OPEN cursor_documentoId 
		FETCH NEXT FROM cursor_documentoId INTO @consecutivo
	
		WHILE @@FETCH_STATUS = 0 
		BEGIN
					
			
		SELECT @referenciaNum = valorActual 
		FROM sap.IC_Referencia 
		WHERE comentario = 'CT' 
		AND estado = 'A';
	
		UPDATE #IC_CM_Enc
		SET referencia = @referenciaNum
		WHERE consecutivo = @consecutivo;
	
	
	/*
		UPDATE sap.IC_Referencia
		SET valorActual = @referenciaNum + 1
		WHERE comentario = 'CT' 
		AND estado = 'A';
	*/	
	
	FETCH NEXT FROM cursor_documentoId INTO @consecutivo
		END 
		CLOSE cursor_documentoId 
		DEALLOCATE cursor_documentoId

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
 */
	UPDATE #IC_CM_Det SET CONSECUTIVO = E.CONSECUTIVO FROM #IC_CM_Det D INNER JOIN IC_CM_Enc E ON D.consecutivo=E.USUARIO INNER JOIN #IC_CM_Enc ET ON E.referencia = ET.referencia
	
	--SE QUITAN DATOS NO NECESARIOS EN CADA MOVIMIENTO
	UPDATE #IC_CM_Det SET codigoEntidadDescuento = '' WHERE claveCont = '31'
	UPDATE #IC_CM_Det SET codigoSap = ''  WHERE claveCont = '40'

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
      ,[usuario])

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
	  FROM #IC_CM_Det

	  INSERT INTO sap.ContablesCtrl
		SELECT @liq, 1, @doc, @tipdoc, getdate(), 'CM', ''
	
	END	
	*/
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