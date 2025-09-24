CREATE OR ALTER  PROCEDURE [sap].[USP_GetICAPORTES_Insert_A01_A02] 
	@planilla VARCHAR(20),	@planillaN VARCHAR(20),	@estadoAporte VARCHAR (20), @apgid BIGINT,	@solNumeroRadicacion VARCHAR (30),	
	@solTipoTransaccion VARCHAR (30),	@operadorinformacion INT
AS
BEGIN
SET NOCOUNT ON; 

--IF (SELECT COUNT(*) FROM sap.ic_internoA WITH(NOLOCK)) <= 0 BEGIN
		
		  
			--INSERT sap.ic_internoA
			--SELECT 1
DECLARE @referenciaNum BIGINT, @consecutivo BIGINT, @regId BIGINT

	CREATE  TABLE #IC_Aportes_Det
		(
			[consecutivo] [bigint] NOT NULL,
			[codigoSap] [varchar](10) NULL,
			[importeDocumento] NUMERIC(21,5) NOT NULL,
			[operador] [varchar](1) NULL,
			[claveCont] [varchar](2) NOT NULL,
			[asignacion] [varchar](18) NOT NULL,
			[ref1] [varchar](12) NOT NULL,
			[ref2] [varchar](12) NULL,
			[tipoDocumento] [varchar](3) NOT NULL,
			[ref3] [varchar](20) NOT NULL,
			[noIdentificado] [varchar](2) NULL,
			[adelantado] [varchar](2) NULL,
			[identificadorDelBanco] [varchar](18) NULL,
			[codigoBanco] [varchar](15) NULL,
			[transitoria] [varchar](1) NULL,
			[claseDeAporte] [varchar](3) NULL,
			[claseDeAportePrescripcion] [varchar](1) NULL,
			[tieneIntereses] [bit] NOT NULL,
			[tipoInteres] [varchar](3) NULL,
			[correccion] [bit] NOT NULL,
			[tipoMora] [varchar](2) NULL,
			[id] [bigint] NOT NULL,
			[codigoGenesys] [bigint] NULL,
			[tipo] [varchar](1) NULL,
			[claseCuenta] [varchar](1) NOT NULL,
			[usuario] [varchar](50) NOT NULL,

			regId bigint NULL,
			[fechaRecaudo] [date] NULL,
			[fechaProcesamiento] [date] NULL,
			[EstadoRegistroAporteAportante] [varchar](30) NULL,
			[TipoSolicitante][VARCHAR](30) NULL,
			[interes]numeric (21,5) NULL,
			[Tarifa]numeric (7,5) NULL,
			[operadorinformacion] INT NULL,
			apgid VARCHAR (20)
			
		)

		CREATE TABLE #IC_Aportes_Enc
		(
			[fecIng] [date] NOT NULL,
			[horaIng] [time](7) NOT NULL,
			[fechaDocumento] [date] NOT NULL,
			[fechaContabilizacion] [date] NOT NULL,
			[periodo] [varchar](2) NOT NULL,
			[referencia] [varchar](16) NOT NULL,
			[tipoMovimiento] [varchar](7) NOT NULL,
			[consecutivo] [bigint] NOT NULL,
			[observacion] [varchar](2000) NULL,
			[moneda] [varchar](5) NOT NULL,
			[documentoContable] [varchar](10) NULL,
			[sociedad] [varchar](4) NOT NULL,
			[ejercicio] [varchar](4) NULL,
			[nroIntentos] [smallint] NOT NULL,
			[fecProceso] [date] NULL,
			[horaProceso] [time](7) NULL,
			[estadoReg] [varchar](1) NOT NULL,
			[usuario] [varchar](50) NOT NULL,
			[estadoaporteaportantetercero] [VARCHAR](30) NULL
		)

		CREATE TABLE #MarcaPeriodo
		(
			MesAporte SMALLINT,
			AnAporte INT,
			PeriodoAporte VARCHAR(7),
			MesRecaudo SMALLINT,
			AnRecaudo INT,
			FechaRecaudo DATE,
			Numeroplanilla VARCHAR(30),
			apgid VARCHAR(30)
		)

		CREATE TABLE #APLICA
		(	[Aplica] [tinyint] NULL,
			[Planilla][varchar](30) NULL,
			[regid][int])
	--Ej planilla
	--9445086696  1,  A01
	--5164211218  84, A02
	--Ej  Planilla N
	--9446197702 84, 7867837606 7,  NO CUMPLE
	--9446836595  84, 9446220804 84, A02
	--59001798 89, 9442907248 84, 25014049 86, A01
	--  2708188

	BEGIN TRY 
		BEGIN TRANSACTION

		--SELECT regid, regNumPlanilla, regOperadorInformacion,regValTotalApoObligatorio, regValorIntMora,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,regNombreAportante,
		--regOUTEstadoArchivo
		--INTO #registrogeneralA01
		--FROM core.pila.RegistroGeneral
		--WHERE regDateTimeInsert IS NULL OR regDateTimeInsert >= dateadd(year,-5,getdate())


		SELECT pipIdPlanilla, pipTipoArchivo
		INTO #PilaIndice
		FROM pila.PilaIndicePlanilla
		WHERE pipTipoArchivo = 'ARCHIVO_OI_IP'

		IF (SELECT TOP 1  tipomovimiento FROM sap.IC_Aportes_Det d inner join sap.IC_Aportes_Enc e ON d.consecutivo = e.consecutivo  where (asignacion =@planilla OR asignacion = CONCAT ('M', @apgid )) AND e.tipoMovimiento = 'A07') IS NULL
		BEGIN

				print 'INGRESA 1PR'
				print @apgid
	---- Valida para Manual

			IF ((@apgid IS NOT NULL OR @apgid <> '') AND @apgid > 0 )
					BEGIN
					print 'entra aplica manual'
					
		
						INSERT INTO #APLICA
						SELECT DISTINCT CASE WHEN CONCAT (apgperiodoaporte,'-01') < apgFechaRecaudo AND apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND  apdValorIntMora >= 0
											AND pct.apgEstadoRegistroAporteAportante <> 'REGISTRADO' OR pct.regNumPlanilla is null 
											THEN '1'ELSE 0 END,
						  reg.regnumplanilla AS Planilla, regId AS regid
						 FROM pila.RegistroGeneral reg--#registrogeneralA01 reg			
								LEFT JOIN  #PilaIndice	rei WITH(NOLOCK)  ON reg.regNumPlanilla =  pipIdPlanilla
								INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) ON reg.regId = apgRegistroGeneral
								LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgId = apd.apdAporteGeneral
								LEFT JOIN dbo.Afiliado afi WITH(NOLOCK)  ON apgPersona = afi.afiPersona
								LEFT JOIN dbo.RolAfiliado roa WITH(NOLOCK)  ON  afi.afiId = roa.roaAfiliado	
								LEFT JOIN sap.PlanillasCtrl pct WITH(NOLOCK)  on CONCAT('M',@apgid) = pct.regNumPlanilla
								WHERE apg.apgId = @apgid
					END			
		select * from #APLICA
				IF (@planilla IS NOT NULL OR @planilla <> '')
					BEGIN
						INSERT INTO #APLICA
						SELECT 1, @planilla , ''
					END
				
		DELETE #APLICA WHERE Aplica = 0				
	END

	IF (SELECT Planilla FROM #APLICA ) IS NULL BEGIN  SET @regid = (SELECT regid FROM #APLICA) END
	IF (SELECT MAX(Aplica) FROM #APLICA) = 1

	BEGIN
	print 'INGRESA 2PR'


		--IF(SELECT TOP 1 regValTotalApoObligatorio FROM  core.pila.RegistroGeneral WHERE (regnumplanilla = @planilla  AND regOperadorInformacion = @operadorinformacion) OR (regid = @regid AND regid > 0)) > 0
		--BEGIN
		-- DETALLE: reconoce los registros de PILA
		----------------------------------------------------------Insert planila-------------------------------------------------------------------------
			INSERT INTO #IC_Aportes_Det 
			 SELECT 
					ROW_NUMBER() OVER(ORDER BY reg.regId ASC) AS consecutivo,
					'' AS codigoSap, 
					CASE WHEN apd.apdAporteObligatorio is not null then SUM(apd.apdAporteObligatorio) 
							else apg.apgValTotalApoObligatorio	end AS ImporteDocumento, ---- Cambiado por camilo 14/08/2023 para llenado de campo cuando es aporte a nivel aportante sin detalle
					--round(ISNULL(apg.apgValTotalApoObligatorio, 0) + ISNULL(reg.regValorIntMora, 0),0) AS ImporteDocumento,
					'' AS operador,			-- Solo aplica para la INTEGRACIoN DE CARTERA
					'40' AS claveCont,
					CASE WHEN reg.regNumPlanilla IS NOT NULL THEN ISNULL(reg.regnumplanilla,'')  ELSE '' /* CONCAT('M',apgId)*/ END AS asignacion,
					reg.regNumeroIdentificacionAportante AS ref1,
					'' AS ref2,
					TipoIdHomologado AS tipoDocumento,
					LEFT(reg.regNombreAportante, 20) AS ref3,
					'' AS noIdentificado,
					'' AS adelantado,
					CASE WHEN apg.apgCuentaBancariaRecaudo ='5' THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(cb.NUMERO_CUENTA, '')),'-', ''))
						 WHEN apg.apgCodEntidadFinanciera IN('51','') THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''))
						 WHEN apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '' THEN REPLACE(cb.NUMERO_CUENTA,'-', '')
						 WHEN (apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo IN ('1','2','3','4') THEN REPLACE(REPLACE(UPPER(ISNULL(NUMERO_CUENTA, '')),'-', ''), '000000', '')
						 WHEN ( apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo = ('5') THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(NUMERO_CUENTA, '')),'-', ''))
						 WHEN apg.apgCodEntidadFinanciera IN('7','')  THEN REPLACE(REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''), '000000', '')
						 ELSE apg.apgNumeroCuenta END AS identificadorDelBanco,
					CASE WHEN (apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') and apg.apgCuentaBancariaRecaudo IS NOT NULL AND apg.apgCuentaBancariaRecaudo = 5 THEN ce.codigoEntidad --agregado con tabla de homologacion core.sap.MaestracodigoEntidad 31/10/2022
						 WHEN (apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo IS NOT NULL  THEN CONCAT ( '0', CAST(ce.codigoEntidad AS VARCHAR(2)))
						 WHEN apg.apgCuentaBancariaRecaudo IN ('1','2','3','4') AND apg.apgNumeroCuenta IS NULL THEN '7' -- agregado posterior a gap vuenta bancaria 18/10/2022
						 WHEN apg.apgCuentaBancariaRecaudo = ('5') AND apg.apgNumeroCuenta IS NULL then '51' -- agregado posterior a gap vuenta bancaria 18/10/2022
						 WHEN LEN(apg.apgCodEntidadFinanciera) = 1 THEN CONCAT('0', CAST(apg.apgCodEntidadFinanciera AS VARCHAR(2))) ELSE CAST(apg.apgCodEntidadFinanciera AS VARCHAR(2)) END AS codigoBanco,
					CASE WHEN apg.apgModalidadRecaudoAporte IN('MANUAL', 'PILA', 'PILA_MANUAL') THEN '3' ELSE '9' END AS transitoria,
					'' AS claseDeAporte,
					'' AS claseDeAportePrescripcion,
					0 AS tieneIntereses,
					'' AS tipoInteres,
					0 AS correccion,			-- Se define con los registros en la tabla Movimientoaporte en moaTipoAjuste = ''CORRECCION_A_LA_BAJA' va a ser anulacion.
					'' AS tipoMora,				-- Solo aplica para la INTEGRACIoN DE CARTERA
					0 AS id,
					NULL AS codigoGenesys,
					'' AS tipo,
					'S' AS claseCuenta,
					'soportetecnico@asopagos.com' AS usuario,
					reg.regid as regId,
					apg.apgFechaRecaudo AS fechaRecaudo, 
					CASE WHEN [SAP].[calcularPrimerDiaHabilMes]() = [SAP].[calcularSiguienteDiaHabil]() AND DAY(DATEADD(HOUR,-5,GETDATE())) >= [SAP].[calcularPrimerDiaHabilMes]() AND DATEDIFF(DAY, apg.apgFechaRecaudo, DATEADD(HOUR,-5,GETDATE())) > 0
						THEN EOMONTH(DATEADD(HOUR,-5,GETDATE()),-1)
						ELSE DATEADD(HOUR,-5,GETDATE()) END AS fechaProcesamiento,
					apg.apgEstadoRegistroAporteAportante AS EstadoRegistroAporteAportante,
					apg.apgTipoSolicitante AS TipoSolicitante,
					CASE WHEN apd.apdValorIntMora is not null then apd.apdValorIntMora 
							else apg.apgValorIntMora end AS interes, ---- Cambiado por camilo 14/08/2023 para llenado de campo cuando es aporte a nivel aportante sin detalle
					Case When  apd.apdTarifa is null AND apg.apgTipoSolicitante = 'EMPLEADOR'  then 0.04 Else apd.apdTarifa  End  AS Tarifa, ---- Cambiado por camilo 14/08/2023 para llenado de campo cuando es aporte a nivel aportante sin detalle
					reg.regOperadorInformacion  AS operadorinformacion,
					apg.apgId AS apgid
				FROM pila.RegistroGeneral reg--#registrogeneralA01 reg
				INNER JOIN core.sap.MaestraTiposIdentificacion mai ON reg.regTipoIdentificacionAportante = mai.TipoIdGenesys
				INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) ON reg.regId = apgRegistroGeneral AND apg.apgEstadoRegistroAporteAportante = 'REGISTRADO'
				LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgId = apd.apdAporteGeneral 
				LEFT JOIN core.dbo.CUENTAS_BANCARIAS cb WITH(NOLOCK) ON apg.apgCuentaBancariaRecaudo = cb.ID 
				LEFT JOIN core.sap.MaestracodigoEntidad ce WITH(NOLOCK) ON cb.ID = ce.idCuentasBancarias 
				WHERE  ((reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @operadorinformacion AND  reg.regOUTEstadoArchivo = 'RECAUDO_NOTIFICADO') 
				OR (regId =@regId   AND apg.apgid = @apgid)) 
				GROUP BY reg.regId, reg.regValTotalApoObligatorio,reg.regnumplanilla,reg.regNumeroIdentificacionAportante,TipoIdHomologado,reg.regNombreAportante,apg.apgNumeroCuenta,
				cb.NUMERO_CUENTA,apg.apgCuentaBancariaRecaudo,apg.apgCodEntidadFinanciera,apg.apgCuentaBancariaRecaudo,apg.apgCodEntidadFinanciera,apg.apgModalidadRecaudoAporte,
				apg.apgFechaRecaudo,apg.apgEstadoRegistroAporteAportante,apg.apgTipoSolicitante,apd.apdTarifa,ce.codigoEntidad, reg.regOperadorInformacion, apgId, apdValorIntMora
				,apdAporteObligatorio,apgValorIntMora,apgValTotalApoObligatorio

		
--------------------------------- Actualizacion temporal codigo entidad financiera en planillas mientras solucionan GLPI 68257 29/06/2023 Camilo-----------------------------------------
		IF (select TOP 1 codigoBanco from #IC_Aportes_Det where claveCont = '40') IS NULL
			
			BEGIN 
					print 'INGRESA 3PR'

				Update  iad set	
				iad.codigoBanco =
						 CASE WHEN apg.apgCodEntidadFinanciera IS NULL AND apg.apgnumerocuenta = '00000000292647573' THEN '07'
							 WHEN apg.apgCodEntidadFinanciera IS NULL AND apg.apgnumerocuenta = '265-99174-5' THEN '51'
							 ELSE CAST(apg.apgCodEntidadFinanciera AS VARCHAR(2))
							 END
							 FROM #IC_Aportes_Det iad 
							Inner Join core.dbo.AporteGeneral  apg WITH(NOLOCK) on iad.apgid = apg.apgid
							LEFT JOIN core.dbo.CUENTAS_BANCARIAS cb WITH(NOLOCK) ON apg.apgCuentaBancariaRecaudo = cb.ID
							LEFT JOIN core.sap.MaestracodigoEntidad ce WITH(NOLOCK) ON cb.ID = ce.idCuentasBancarias 
							WHere iad.clavecont = '40'

				Update iad set 

				iad.identificadorDelBanco =
							CASE WHEN iad.codigoBanco IN('51') THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''))
							 WHEN iad.codigoBanco IN('07')  THEN REPLACE(REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''), '000000', '')
							 ELSE apg.apgNumeroCuenta END 
							 FROM #IC_Aportes_Det iad 
							 Inner Join core.dbo.AporteGeneral  apg WITH(NOLOCK) on iad.apgid = apg.apgid
							WHere iad.clavecont = '40'
							
			END		
		--Select * from #IC_Aportes_Det
------------------------------------------------------------- update asignacion cuando es recaudo manual--------------
		UPDATE	#IC_Aportes_Det SET asignacion = CONCAT('M', @apgid) WHERE asignacion = ''   ----- agregado camilo 25/09/2023


-------------------------------------------------------------------------------------------------------------------------------------------------------
		
		

		--RECAUDO MANUAL
		IF (SELECT Planilla FROM #APLICA ) IS NULL 
			BEGIN 
				DELETE #IC_Aportes_Det WHERE apgid <> @apgid
			END

		 -- PLANILLA
			--Detectar apgid a integrar
			SELECT apgid, asignacion 
			INTO #apgid
			FROM  #IC_Aportes_Det WHERE importeDocumento > 0 

			IF (SELECT TOP 1 Planilla FROM #APLICA) IS NOT NULL 
			BEGIN			 
					print 'INGRESA 4PR'
				DELETE FROM #IC_Aportes_Det where asignacion <> @planilla ------ cambiado de posicion 25/09/2023
				UPDATE #IC_Aportes_Det  SET  consecutivo = 3 	
			
				INSERT INTO #IC_Aportes_Det
				SELECT ROW_NUMBER() OVER(ORDER BY regId ASC) AS Consecutivo, [codigoSap], SUM([ImporteDocumento]), [operador], '40' [claveCont], [asignacion], [ref1], [ref2], [tipoDocumento], [ref3], [noIdentificado],
				[adelantado], [identificadorDelBanco], [codigoBanco], [transitoria], [claseDeAporte], [claseDeAportePrescripcion], [tieneIntereses], [tipoInteres], [correccion],
				[tipoMora], [id], [codigoGenesys], [tipo], [claseCuenta], [usuario], [regId],[fechaRecaudo],  [fechaProcesamiento], [EstadoRegistroAporteAportante], [TipoSolicitante], SUM([interes]), [Tarifa],[operadorinformacion],''
				FROM #IC_Aportes_Det  det	
				GROUP BY [codigoSap], [operador], [asignacion], [ref1], [ref2], [tipoDocumento], [ref3], [noIdentificado],
				[adelantado], [identificadorDelBanco], [codigoBanco], [transitoria],[claseDeAporte], [claseDeAportePrescripcion], [tieneIntereses], [tipoInteres], [correccion],
				[tipoMora], [id], [codigoGenesys], [tipo], [claseCuenta], [usuario], [regId],[fechaRecaudo],  [fechaProcesamiento], [EstadoRegistroAporteAportante], [TipoSolicitante], [Tarifa],[operadorinformacion]
	

				DELETE FROM #IC_Aportes_Det  WHERE consecutivo > 2 
		
			END
	
		DELETE #IC_Aportes_Det WHERE importeDocumento = 0 	

		
		IF EXISTS (SELECT * FROM #IC_Aportes_Det) 
			BEGIN
				
				INSERT INTO #IC_Aportes_Det 
				SELECT [consecutivo], [codigoSap], [ImporteDocumento], [operador], '50' [claveCont], [asignacion], [ref1], [ref2], [tipoDocumento], [ref3], [noIdentificado],
				[adelantado], '', '', '', [claseDeAporte], [claseDeAportePrescripcion], [tieneIntereses], [tipoInteres], [correccion],
				[tipoMora], [id], [codigoGenesys], [tipo], [claseCuenta], [usuario], [regId],[fechaRecaudo],  [fechaProcesamiento], [EstadoRegistroAporteAportante], [TipoSolicitante], [interes], [Tarifa], [operadorinformacion],''
				FROM #IC_Aportes_Det  det											 
																	 
				--Actualizacion de campos clave contable 50 ------------- Planilla-----------------------------------------------------
				UPDATE #IC_Aportes_Det    SET  claseDeAportePrescripcion = (CASE WHEN apg.apgFormaReconocimientoAporte = 'RECONOCIMIENTO_RETROACTIVO_MANUAL' AND apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' THEN '1' ELSE '2' END ),
											   claseDeAporte = (CASE  WHEN TipoSolicitante = 'EMPLEADOR' AND (Tarifa = 0.00 OR Tarifa = 0.04)  THEN 'CA1'
																	  WHEN TipoSolicitante = 'EMPLEADOR' AND (Tarifa is null)  THEN 'CA1' ---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
																	  WHEN TipoSolicitante = 'EMPLEADOR' AND Tarifa < 0.04 THEN 'CA1'
																	  WHEN TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.02 OR Tarifa = 0.04) THEN 'CA2'
																	  WHEN TipoSolicitante = 'PENSIONADO' AND Tarifa <> 0.006 THEN 'CA3'
																	  WHEN TipoSolicitante = 'PENSIONADO' AND Tarifa = 0.006 THEN 'CA4'
																	  WHEN TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.006 OR Tarifa = 0.01) THEN 'CA5'
																	  ELSE ''END),
											fechaRecaudo = apg.apgFechaRecaudo, 
											fechaProcesamiento   = CASE WHEN [SAP].[calcularPrimerDiaHabilMes]() = [SAP].[calcularSiguienteDiaHabil]() AND DAY(DATEADD(HOUR,-5,GETDATE())) >= [SAP].[calcularPrimerDiaHabilMes]() AND DATEDIFF(DAY, apg.apgFechaRecaudo, DATEADD(HOUR,-5,GETDATE())) > 0
													THEN EOMONTH(DATEADD(HOUR,-5,GETDATE()),-1)
													ELSE DATEADD(HOUR,-5,GETDATE()) END
												FROM pila.RegistroGeneral reg--#registrogeneralA01 reg
					INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) ON regId = apgRegistroGeneral AND apg.apgEstadoRegistroAporteAportante = 'REGISTRADO'	
					LEFT JOIN #IC_Aportes_Det det WITH(NOLOCK) ON reg.regId = det.regId
					LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgId = apd.apdAporteGeneral 
					WHERE det.claveCont = '50' AND ((reg.regnumplanilla = @planilla  AND reg.regOperadorInformacion = @operadorinformacion) OR (reg.regid = @regId AND apg.apgId = @apgid))
					
				
	

		UPDATE #IC_Aportes_Det SET	codigoGenesys = per.perId,	tipo = CASE WHEN tipoDocumento = 'NIT' THEN 'E'	ELSE 'P' END								
			FROM pila.RegistroGeneral reg--#registrogeneralA01 reg
				INNER JOIN core.dbo.Persona per WITH(NOLOCK) ON reg.regNumeroIdentificacionAportante = per.perNumeroIdentificacion
					WHERE (reg.regnumplanilla =  @planilla AND reg.regOperadorInformacion = @operadorinformacion) OR(reg.regId = @regId)


		IF (SELECT Planilla FROM #APLICA) IS NULL
		BEGIN
			UPDATE #IC_Aportes_Det SET	codigoGenesys = apg.apgPersona,	tipo = CASE WHEN tipoDocumento = 'NIT' THEN 'E'	ELSE 'P' END								
				FROM dbo.AporteGeneral  apg
				where apg.apgId = @apgid
		END
	


			-- Validacion para crear otra detalle con clave contable si es tipo movimiento A02

					IF (SELECT TOP 1 regValorIntMora FROM  core.pila.RegistroGeneral WHERE (regNumPlanilla = @planilla) OR (regId = @regId AND regNumPlanilla IS NULL)) > 0
					BEGIN
						INSERT INTO #IC_Aportes_Det 
						SELECT [consecutivo], [codigoSap], Round(interes,0) as [ImporteDocumento], [operador], '50' [claveCont], [asignacion], [ref1], [ref2], [tipoDocumento], [ref3], [noIdentificado],
						[adelantado], '', '', '', '', '', 1 AS [tieneIntereses], 
			 				   -- Intereses por mora
						CASE
							WHEN EstadoRegistroAporteAportante = 'REGISTRADO'  AND TipoSolicitante = 'EMPLEADOR' AND (Tarifa = 0.00 OR Tarifa = 0.04) THEN 'IM1'
							WHEN EstadoRegistroAporteAportante = 'REGISTRADO'  AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.02 OR Tarifa = 0.04) THEN 'IM2'
							WHEN EstadoRegistroAporteAportante = 'REGISTRADO'  AND TipoSolicitante = 'PENSIONADO' AND Tarifa <> 0.006 THEN 'IM3'
							WHEN EstadoRegistroAporteAportante = 'REGISTRADO'  AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.006 OR Tarifa = 0.01) THEN 'IM4'
							WHEN EstadoRegistroAporteAportante = 'REGISTRADO'  AND TipoSolicitante = 'PENSIONADO' AND Tarifa = 0.006 THEN 'IM5'
							ELSE '*'	END AS [tipoInteres], 
						[correccion],	[tipoMora], [id], [codigoGenesys], [tipo], [claseCuenta], [usuario], [regId],[fechaRecaudo],  [fechaProcesamiento], [EstadoRegistroAporteAportante], [TipoSolicitante], [interes], [tarifa],'',''
						FROM #IC_Aportes_Det  det	WHERE claveCont = '50'
					END


			INSERT INTO #IC_Aportes_Enc(
						[fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo],[referencia],[tipoMovimiento],[consecutivo],[observacion],
						[moneda],[documentoContable],[sociedad],[ejercicio],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario],[estadoaporteaportantetercero]
					)
					SELECT TOP 1
						CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
						CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
						ISNULL(fechaRecaudo, '') AS fechaDocumento,
						ISNULL(fechaProcesamiento, '') AS fechaContabilizacion,
						MONTH(fechaProcesamiento) AS periodo, 
						'' AS referencia,
						CASE det.interes WHEN 0 THEN 'A01' ELSE 'A02' END  AS tipoMovimiento,
						det.consecutivo AS consecutivo,
						'' AS observacion,
						'COP' AS moneda,
						'' AS documentoContable,  
						'COMF' AS sociedad,
						'' AS ejercicio,
						'' AS nroIntentos,
						'' AS fecProceso,
						'' AS horaProceso,
						'P'AS estadoReg,
						 usuario AS usuario,
						 det.EstadoRegistroAporteAportante as estadoaporteaportantetercero
					FROM #IC_Aportes_Det det WITH(NOLOCK)
			

		-- Se debe agrupar todo en una sola clave contable 40
				--------------------------------------------------------------------------------------------------------------------------------------------
		IF --(SELECT MAX(Tarifa) FROM #IC_Aportes_Det WHERE claveCont = '40') = (SELECT MIN(Tarifa) FROM #IC_Aportes_Det WHERE claveCont = '40') 	
		 (SELECT COUNT(*) FROM #IC_Aportes_Det WHERE claveCont = '40') > 1
		BEGIN

			DELETE #IC_Aportes_Det WHERE  claveCont = '40'  AND consecutivo = (SELECT  MAX(consecutivo) FROM  #IC_Aportes_Det)
			UPDATE #IC_Aportes_Det SET  consecutivo = (SELECT  MIN(consecutivo) FROM  #IC_Aportes_Det)
			UPDATE #IC_Aportes_Det SET  importeDocumento = (SELECT SUM(apd.apdAporteObligatorio) 
															FROM pila.RegistroGeneral reg --#registrogeneralA01 reg 
																 INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) ON regId = apgRegistroGeneral AND apg.apgEstadoRegistroAporteAportante = 'REGISTRADO'
																 LEFT JOIN  core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgId = apd.apdAporteGeneral
																 WHERE (reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @operadorinformacion) OR (reg.regid = @regId AND reg.regid <> 0 )
															) WHERE claveCont = '40' 

			----------------------- actualizacion campo interes e importedocumento para TP cuando son 2 tarifas--------------------------------------------------
			UPDATE #IC_Aportes_Det SET  interes = (SELECT ROUND(SUM(apd.apdValorIntMora),0) 
															FROM pila.RegistroGeneral reg --#registrogeneralA01 reg 
																 INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) ON regId = apgRegistroGeneral AND apg.apgEstadoRegistroAporteAportante = 'REGISTRADO'
																 LEFT JOIN  core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgId = apd.apdAporteGeneral
																 WHERE (reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @operadorinformacion) OR (reg.regid = @regId AND reg.regid <> 0 )
															)  WHERE claveCont = '40' 

		END
		----------------------------------------------------------------------------------------------------------------------------------------------------

			UPDATE  #IC_Aportes_Det SET importeDocumento = (select SUM(importeDocumento) from #IC_Aportes_Det d where claveCont = '50')WHERE claveCont = '40'  --CAMBIO POR APROXIMACIONES EN VALOR DE INTERESES CUANDO SE SUMA EN EL IMPORTE DEL DOCUMENTO
		--UPDATE  #IC_Aportes_Det SET importeDocumento = importeDocumento + interes WHERE claveCont = '40'  
		--UPDATE	#IC_Aportes_Det SET asignacion = CONCAT('M', @apgid) WHERE asignacion = ''




			SELECT @referenciaNum = valorActual 
				FROM sap.ic_referencia
				WHERE comentario ='A'
				AND estado = 'A';

			/*IF @referenciaNum -1 = (select top 1  max(CONVERT(bigint, referencia)) from sap.IC_Aportes_Enc INNER JOIN sap.IC_Referencia ON (CONVERT(bigint, referencia)+1)  = valorActual AND estado = 'A'and comentario ='A')
			BEGIN*/
				UPDATE #IC_Aportes_Enc
					SET referencia =  @referenciaNum

--comentarear
	
					
				UPDATE sap.ic_referencia
					SET valorActual = @referenciaNum + 1
					WHERE comentario ='A'
					AND estado = 'A';		
		

-- Fin Comentario
			END

		END
	--END

---- inicio comentario

		IF ((SELECT referencia FROM #IC_Aportes_Enc ) IS NOT NULL )
		BEGIN

	
			IF EXISTS (SELECT enca.consecutivo FROM #IC_Aportes_Enc enca INNER JOIN #IC_Aportes_Det deta on enca.consecutivo = deta.consecutivo where deta.importeDocumento <> 0)
			BEGIN
	      
				INSERT INTO sap.IC_Aportes_Enc(
					[fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo],[referencia],[tipoMovimiento],[observacion],
					[moneda],[documentoContable],[sociedad],[ejercicio],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario])
				SELECT distinct [fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo],[referencia],[tipoMovimiento],[observacion],
					[moneda],[documentoContable],[sociedad],[ejercicio],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario]
				FROM #IC_Aportes_Enc WITH(NOLOCK) ; 
         
					-- Actualizar el consecutivo definitivo en la tabla de integracion de encabezado con el detalle
					SELECT @consecutivo=MAX(consecutivo) FROM IC_Aportes_Enc

					UPDATE #IC_Aportes_Det
					SET consecutivo = @consecutivo

      
				INSERT INTO sap.IC_Aportes_Det([consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[ref1],[ref2],[tipoDocumento],[ref3],
						[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
						[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[codigoGenesys],[tipo],
						[claseCuenta],[usuario],fechaEjecucion)
				SELECT [consecutivo],[codigoSap],CAST(CAST([importeDocumento] AS BIGINT) AS VARCHAR(18)),[operador],[claveCont],[asignacion],[ref1],[ref2],[tipoDocumento],[ref3],
						[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
						[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[codigoGenesys],[tipo],
						[claseCuenta],[usuario], GETDATE()-'05:00:00'
				FROM #IC_Aportes_Det  WITH(NOLOCK)
				WHERE importeDocumento <> 0  ---  para cuando el registro viene sin detalle y que no traiga lineas en valores 0 08/10/2022
		
				UPDATE  #IC_Aportes_Det
					SET consecutivo =(SELECT consecutivo FROM #IC_Aportes_Enc)
		
				-- INSERCIoN EN LA TABLA DE CONTROL DE PLANILLAS
		
 
				INSERT INTO sap.PlanillasCtrl
				SELECT DISTINCT LEFT(det.asignacion, 12), 1, det.ref1, det.tipoDocumento, SAP.GetLocalDate(), 'APORTES', det.ref3 + enc.observacion, enc.tipoMovimiento,estadoaporteaportantetercero,operadorinformacion, case when @apgid is null or @apgid = '' then ap.apgid else @apgid end
				FROM #IC_Aportes_Det det WITH(NOLOCK)
				INNER JOIN #IC_Aportes_Enc enc WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
				INNER JOIN #apgid ap  WITH(NOLOCK) ON  det.asignacion = ap.asignacion
				WHERE det.EstadoRegistroAporteAportante is not null

	
	
	
			END 
		END

		-- Fin Comentario





				--Detalle
				SELECT DISTINCT * FROM #IC_Aportes_Enc
				ORDER BY tipoMovimiento, consecutivo

				SELECT * FROM #IC_Aportes_Det WITH(NOLOCK)
				WHERE importeDocumento <> 0  ---  para cuando el registro viene sin detalle y que no traiga lineas en valores 0 08/10/2022
				ORDER BY claveCont

				DROP TABLE #IC_Aportes_Det;	
				DROP TABLE #IC_Aportes_Enc;					
				DROP TABLE #APLICA 
			
	
	
			COMMIT TRANSACTION
		END TRY

		BEGIN CATCH

			ROLLBACK TRANSACTION

			SELECT  ERROR_NUMBER() AS ErrorNumber  ,ERROR_SEVERITY() AS ErrorSeverity  
			,ERROR_STATE() AS ErrorState,ERROR_PROCEDURE() AS ErrorProcedure  
			,ERROR_LINE() AS ErrorLine,ERROR_MESSAGE() AS ErrorMessage
			,GETDATE() -'05:00:00' as Fechaing;  
 
			INSERT INTO core.sap.LogErrorAportes 
			SELECT @planilla, @planillaN,  @regId,@solNumeroRadicacion,@solTipoTransaccion,@operadorinformacion, ERROR_NUMBER(),ERROR_SEVERITY(),ERROR_STATE(),ERROR_PROCEDURE(),ERROR_LINE(),ERROR_MESSAGE(),GETDATE() -'05:00:00'
 

		END CATCH

	SELECT 'ok ejec A01 A02 TP',GETDATE() 
	--UPDATE sap.ic_internoA SET valoractual = 0 WHERE valoractual = 1
    --DELETE FROM	sap.ic_internoA
	--END
	--ELSE BEGIN

		--SELECT 'El proceso ya se encuentra en ejecucion'
	
	END