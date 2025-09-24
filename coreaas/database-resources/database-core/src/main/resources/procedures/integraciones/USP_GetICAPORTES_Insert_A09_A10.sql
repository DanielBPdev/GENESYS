CREATE OR ALTER  PROCEDURE  [sap].[USP_GetICAPORTES_Insert_A09_A10] 	 @planilla VARCHAR(20),	@planillaN VARCHAR(20),	@estadoAporte VARCHAR (20),	@apgid BIGINT,	@solNumeroRadicacion VARCHAR (MAX),	
	@solTipoTransaccion VARCHAR (30),	@operadorinformacion INT
AS
BEGIN
SET NOCOUNT ON; 

--set @planilla = '7701657518'
--set @operadorinformacion = '7'
--set @estadoAporte = 'OTROS_INGRESOS'
--IF (SELECT COUNT(*) FROM sap.ic_internoA WITH(NOLOCK)) <= 0 BEGIN
		
		  
--			INSERT sap.ic_internoA
--			SELECT 1

	DECLARE @referenciaNum BIGINT, @consecutivo BIGINT, @regId BIGINT

	CREATE TABLE #IC_Aportes_Det
		(
			[consecutivo] [bigint] NOT NULL,
			[codigoSap] [varchar](10) NULL,
			[importeDocumento] NUMERIC(21,5) NOT NULL,
			[operador] [varchar](1) NULL,
			[claveCont] [varchar](2) NOT NULL,
			[asignacion] [varchar](18) NOT NULL,
			[textoposicion] varchar (50) Null,
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
			[interes]numeric NULL,
			[Tarifa]numeric (7,5) NULL,
			operadorinformacion INT NULL,
			[apgid] VARCHAR (20),
			integrados VARCHAR (20)
			
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

	

		CREATE TABLE #APLICA
		(	[Aplica] [tinyint] NULL,
			[Planilla][varchar](30) NULL,
			[regid][int])

	--7868376920 7, NO APLICA
	-- 9443823541 84,  7870896559 7, A07
	--64066781 83, 60258786 89, A08

	BEGIN TRY 
		BEGIN TRANSACTION



----------------------------------------------------------------------------------------------------------


	INSERT INTO #APLICA
		SELECT  DISTINCT CASE	WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND apdValorIntMora = 0  THEN '1'
						WHEN apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND apdValorIntMora > 0 THEN '1'	 ELSE 0 END AS Aplica, 
						reg.regnumplanilla AS Planilla, regId
		FROM core.pila.RegistroGeneral reg
				INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) on regId = apgRegistroGeneral
				LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgId = apd.apdAporteGeneral
				INNER JOIN core.sap.IC_Aportes_Det det WITH(NOLOCK) ON  reg.regNumPlanilla = det.asignacion OR (asignacion = concat('M', @apgid)) 
				INNER JOIN core.sap.IC_Aportes_Enc enc WITH(NOLOCK) ON  det.consecutivo = enc.consecutivo
				WHERE ((det.asignacion = @planilla AND enc.Tipomovimiento IN('A05', 'A06') AND enc.estadoReg = 'S' )AND
				(reg.regnumplanilla = @planilla  AND reg.regOperadorInformacion = @operadorinformacion)) 
				OR (asignacion = concat('M', @apgid) and tipoMovimiento in ('A05', 'A06') AND enc.estadoReg = 'S' AND apg.apgId = @apgid AND reg.regnumplanilla IS NULL)

		DELETE #APLICA WHERE Aplica = 0				
		--SELECT * FROM #APLICA



	IF (SELECT TOP 1 Planilla FROM #APLICA ) IS NULL BEGIN  SELECT @regid = (SELECT TOP 1 regid FROM #APLICA) END
		
	IF (SELECT MAX(Aplica) FROM #APLICA) = 1
	BEGIN
	print 'Ingresa Aplica'
	DECLARE @regidP varchar (20) = (SELECT TOP 1 regid FROM #APLICA) -- Se adiciona variable para almacenar regid de la planilla con registros en NULL 
		IF(SELECT top 1 regValTotalApoObligatorio FROM  core.pila.RegistroGeneral WHERE (regnumplanilla = @planilla AND regOperadorInformacion = @operadorinformacion) OR (regid = @regId AND regid > 0 ))  > 0  OR  (select top 1 apgValTotalApoObligatorio from core.dbo.AporteGeneral where apgRegistroGeneral = @regidP ) > 0
		BEGIN
			print 'Ingresa insert 1'
			-- DETALLE: reconoce los registros de PILA
			
				SELECT
					0 AS consecutivo,
					'' AS codigoSap, 
					SUM(apd.apdAporteObligatorio)  AS ImporteDocumento,
					--round(ISNULL(apg.apgValTotalApoObligatorio, 0) + ISNULL(reg.regValorIntMora, 0),0) AS ImporteDocumento,
					'' AS operador,			-- Solo aplica para la INTEGRACIoN DE CARTERA
					'40' AS claveCont,
					CASE WHEN reg.regNumPlanilla IS NOT NULL THEN ISNULL(reg.regnumplanilla,'')  ELSE '' /* CONCAT('M',apgId)*/ END AS asignacion,
					'' as textoposicion,
					reg.regNumeroIdentificacionAportante AS ref1,
					'' AS ref2,
					TipoIdHomologado AS tipoDocumento,
					LEFT(reg.regNombreAportante, 20) AS ref3,
					'' AS noIdentificado,
					'' AS adelantado,
					'' AS identificadorDelBanco,
					'' AS codigoBanco,
					'' AS transitoria,
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
					SUM(apdValorIntMora) AS interes,
					apd.apdTarifa AS Tarifa,
					reg.regOperadorInformacion  AS operadorinformacion,
					apg.apgId AS apgid
				INTO #TEMPORAL_DETALLE
				FROM core.pila.RegistroGeneral reg
				INNER JOIN core.sap.MaestraTiposIdentificacion mai ON reg.regTipoIdentificacionAportante = mai.TipoIdGenesys
				INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) ON reg.regId = apgRegistroGeneral
				INNER JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgId = apd.apdAporteGeneral 
				LEFT JOIN core.dbo.CUENTAS_BANCARIAS cb WITH(NOLOCK) ON apg.apgCuentaBancariaRecaudo = cb.ID 
				LEFT JOIN core.sap.MaestracodigoEntidad ce WITH(NOLOCK) ON cb.ID = ce.idCuentasBancarias
				--LEFT JOIN core.sap.PlanillasCtrl ct WITH(NOLOCK) ON apg.apgId = ct.apgid
				--INNER JOIN core.sap.IC_Aportes_Det d WITH(NOLOCK) ON ct.regNumPlanilla = d.asignacion
				--LEFT JOIN core.sap.IC_Aportes_Enc e WITH(NOLOCK) ON d.consecutivo = e.consecutivo and e.tipoMovimiento in ('A09','A10')
 				WHERE  apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS'  AND ((reg.regnumplanilla =  @planilla AND reg.regOperadorInformacion = @operadorinformacion AND  reg.regOUTEstadoArchivo = 'RECAUDO_NOTIFICADO') 
				OR (reg.regid = @regId AND apg.apgId = @apgid))  --AND (apd.apdAporteObligatorio <> 0 AND apd.apdTarifa <> 0) 
				--AND e.Tipomovimiento is null
				GROUP BY reg.regId, reg.regValTotalApoObligatorio,reg.regnumplanilla,reg.regNumeroIdentificacionAportante,TipoIdHomologado,reg.regNombreAportante,apg.apgNumeroCuenta,
				cb.NUMERO_CUENTA,apg.apgCuentaBancariaRecaudo,apg.apgCodEntidadFinanciera,apg.apgCuentaBancariaRecaudo,apg.apgCodEntidadFinanciera,apg.apgModalidadRecaudoAporte,
				apg.apgFechaRecaudo,apg.apgEstadoRegistroAporteAportante,apg.apgTipoSolicitante,apd.apdTarifa,ce.codigoEntidad, reg.regOperadorInformacion, apg.apgId 

				PRINT 'INGRESA AQUI'

						UPDATE	#TEMPORAL_DETALLE SET asignacion = CONCAT('M', @apgid) WHERE asignacion = ''




		select apgid 
		INTO  #integrados
		from #TEMPORAL_DETALLE
		EXCEPT select Distinct ct.apgid from #TEMPORAL_DETALLE dt
						left join sap.PlanillasCtrl ct on dt.apgid = ct.apgid
						left join sap.IC_Aportes_Enc e on ct.Tipomovimiento = e.tipoMovimiento
						where (dt.asignacion = @planilla OR dt.asignacion = CONCAT('M', @apgid))  and ct.Tipomovimiento in ('A09','A10')

	INSERT INTO #IC_Aportes_Det 
	select * from #TEMPORAL_DETALLE d
	inner join #integrados i on d.apgid = i.apgid






--Detectar apgid a integrar

							SELECT apgid, asignacion 
							INTO #apgid
							FROM  #IC_Aportes_Det
							WHERE importeDocumento > 0 

	

			--RECAUDO MANUAL
			IF (SELECT TOP 1 Planilla FROM #APLICA ) IS NULL 
				BEGIN 
					DELETE #IC_Aportes_Det WHERE apgid <> @apgid
				END


			DELETE #IC_Aportes_Det WHERE importeDocumento = 0 	

		
		IF EXISTS (SELECT * FROM #IC_Aportes_Det) 
			BEGIN		 
	
		print 'Ingresa update'
				INSERT INTO #IC_Aportes_Det 
				SELECT [consecutivo], [codigoSap], [ImporteDocumento], [operador], '50' [claveCont], [asignacion], [textoposicion] ,[ref1], [ref2], [tipoDocumento], [ref3], [noIdentificado],
				[adelantado], [identificadorDelBanco], [codigoBanco], [transitoria], [claseDeAporte], [claseDeAportePrescripcion], [tieneIntereses], [tipoInteres], [correccion],
				[tipoMora], [id], [codigoGenesys], [tipo], [claseCuenta], [usuario], [regId],[fechaRecaudo],  [fechaProcesamiento], [EstadoRegistroAporteAportante], [TipoSolicitante], [interes], [Tarifa],[operadorinformacion],[apgid],integrados
				FROM #IC_Aportes_Det  det											 
		

		--Actualizacion de campos clave contable 40 Y 50 para movimiento  A09
				--Actualizacion de campos clave contable 40
				UPDATE #IC_Aportes_Det    SET noIdentificado = CASE WHEN  det.EstadoRegistroAporteAportante = ('OTROS_INGRESOS') AND det.TipoSolicitante = 'EMPLEADOR' AND (det.Tarifa = 0.00 OR det.Tarifa = 0.04 OR det.Tarifa = 0.02 OR det.Tarifa = 0.01 or det.Tarifa = 0.03) THEN 'N1'
																	 WHEN det.EstadoRegistroAporteAportante = ('OTROS_INGRESOS') AND det.TipoSolicitante = 'EMPLEADOR' AND (apd.apdId IS NULL) THEN 'N1'   ---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022   
																	 WHEN det.EstadoRegistroAporteAportante = ('OTROS_INGRESOS') AND det.TipoSolicitante = 'INDEPENDIENTE' AND (det.Tarifa = 0.02 OR det.Tarifa = 0.04) THEN 'N2'
																	 WHEN det.EstadoRegistroAporteAportante = ('OTROS_INGRESOS') AND det.TipoSolicitante = 'PENSIONADO' AND det.Tarifa <> 0.006 THEN 'N3'
																	 WHEN det.EstadoRegistroAporteAportante = ('OTROS_INGRESOS') AND det.TipoSolicitante = 'INDEPENDIENTE' AND (det.Tarifa = 0.006 OR det.Tarifa = 0.01) THEN 'N4'
																	 WHEN det.EstadoRegistroAporteAportante = ('OTROS_INGRESOS') AND det.TipoSolicitante = 'PENSIONADO' AND det.Tarifa = 0.006 THEN 'N5'
																	 ELSE '**' END
					FROM #IC_Aportes_Det  det
					inner join AporteDetallado apd on det.apgid = apd.apdAporteGeneral
					WHERE claveCont = '40' 

				

				--Actualizacion de campos clave contable 50
				UPDATE #IC_Aportes_Det  SET  claseDeAportePrescripcion = (CASE WHEN apg.apgFormaReconocimientoAporte = 'RECONOCIMIENTO_RETROACTIVO_MANUAL' AND apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' THEN '1' ELSE '2' END ),
											 claseDeAporte = (CASE  WHEN det.TipoSolicitante = 'EMPLEADOR' AND (det.Tarifa = 0.00 OR det.Tarifa = 0.04)  THEN 'CA1'
																	  WHEN det.TipoSolicitante = 'EMPLEADOR' AND (det.Tarifa is null)  THEN 'CA1' ---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
																	  WHEN det.TipoSolicitante = 'EMPLEADOR' AND det.Tarifa < 0.04 THEN 'CA1'
																	  WHEN det.TipoSolicitante = 'INDEPENDIENTE' AND (det.Tarifa = 0.02 OR det.Tarifa = 0.04) THEN 'CA2'
																	  WHEN det.TipoSolicitante = 'PENSIONADO' AND det.Tarifa <> 0.006 THEN 'CA3'
																	  WHEN det.TipoSolicitante = 'PENSIONADO' AND det.Tarifa = 0.006 THEN 'CA4'
																	  WHEN det.TipoSolicitante = 'INDEPENDIENTE' AND (det.Tarifa = 0.006 OR det.Tarifa = 0.01) THEN 'CA5'
																	  ELSE ''END)		
									FROM #IC_Aportes_Det det
									INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) on det.apgid = apg.apgId
	 								WHERE  claveCont = '50' 

				UPDATE #IC_Aportes_Det SET	codigoGenesys = per.perId,	tipo = CASE WHEN tipoDocumento = 'NIT' THEN 'E'	ELSE 'P' END								
				FROM core.pila.RegistroGeneral reg
					INNER JOIN core.dbo.Persona per WITH(NOLOCK) ON reg.regNumeroIdentificacionAportante = per.perNumeroIdentificacion
					WHERE (reg.regnumplanilla  = @planilla AND reg.regOperadorInformacion = @operadorinformacion) 

					

				IF (SELECT top 1 Planilla FROM #APLICA) IS NULL
				BEGIN
				UPDATE #IC_Aportes_Det SET	codigoGenesys = apg.apgPersona,	tipo = CASE WHEN tipoDocumento = 'NIT' THEN 'E'	ELSE 'P' END								
					FROM dbo.AporteGeneral  apg
					WHERE apg.apgId = @apgid
				END

	--Actualizacion  campos clave contable 40 Y 50 para movimiento A10

			IF (SELECT TOP 1 regValorIntMora FROM  core.pila.RegistroGeneral WHERE  (regNumPlanilla = @planilla) OR (regId = @regId AND regNumPlanilla IS NULL) ) > 0  OR  (select top 1 apdValorIntMora from core.dbo.AporteGeneral inner join core.dbo.AporteDetallado with (nolock) on apgId = apdAporteGeneral  where apgRegistroGeneral = @regidP  and apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS') > 0
			BEGIN
			
				--Insert de campos clave contable 40

				INSERT INTO #IC_Aportes_Det 
						SELECT [consecutivo], [codigoSap], interes as [ImporteDocumento], [operador], '40' [claveCont], [asignacion], [textoposicion],[ref1], [ref2], [tipoDocumento], [ref3], '',
						[adelantado], [identificadorDelBanco], [codigoBanco], [transitoria], '', '', 1 AS [tieneIntereses],
			 				   -- Intereses por mora
						CASE
							WHEN EstadoRegistroAporteAportante = 'OTROS_INGRESOS'  AND TipoSolicitante = 'EMPLEADOR' AND (Tarifa = 0.00 OR Tarifa = 0.04) THEN 'IN1'
							WHEN EstadoRegistroAporteAportante = 'OTROS_INGRESOS'  AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.02 OR Tarifa = 0.04) THEN 'IN2'
							WHEN EstadoRegistroAporteAportante = 'OTROS_INGRESOS'  AND TipoSolicitante = 'PENSIONADO' AND Tarifa <> 0.006 THEN 'IN3'
							WHEN EstadoRegistroAporteAportante = 'OTROS_INGRESOS'  AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.006 OR Tarifa = 0.01) THEN 'IN4'
							WHEN EstadoRegistroAporteAportante = 'OTROS_INGRESOS'  AND TipoSolicitante = 'PENSIONADO' AND Tarifa = 0.006 THEN 'IN5'
							ELSE '*'	END AS [tipoInteres], 
						[correccion],	[tipoMora], [id], [codigoGenesys], [tipo], [claseCuenta], [usuario], [regId],[fechaRecaudo],  [fechaProcesamiento], [EstadoRegistroAporteAportante], [TipoSolicitante], [interes], [tarifa],[operadorinformacion],[apgid],integrados
						FROM #IC_Aportes_Det  det	WHERE claveCont = '40'

	
				--Insert de campos clave contable 50

				INSERT INTO #IC_Aportes_Det 
						SELECT [consecutivo], [codigoSap], interes as [ImporteDocumento], [operador], '50' [claveCont], [asignacion],[textoposicion], [ref1], [ref2], [tipoDocumento], [ref3], '',
						[adelantado], [identificadorDelBanco], [codigoBanco], [transitoria], '', '', [tieneIntereses], 
			 				   CASE WHEN tipoInteres = 'IN1' THEN 'RP1'
									WHEN tipoInteres = 'IN2' THEN 'RP2'
									WHEN tipoInteres = 'IN3' THEN 'RP3'
									WHEN tipoInteres = 'IN4' THEN 'RP4'
									WHEN tipoInteres = 'IN5' THEN 'RP5'
									END
						[tipoInteres], 
						[correccion],	[tipoMora], [id], [codigoGenesys], [tipo], [claseCuenta], [usuario], [regId],[fechaRecaudo],  [fechaProcesamiento], [EstadoRegistroAporteAportante], [TipoSolicitante], [interes], [tarifa],[OperadorInformacion],[apgid],integrados
						FROM #IC_Aportes_Det  det	WHERE claveCont = '40' AND tieneIntereses = 1

			END

------------------------------------- Agrupacion de 40 (APGID'S)-----------------------------------------------------------------------------------------------
				
				
				
				IF (SELECT TOP 1 Planilla FROM #APLICA) IS NOT NULL 
			BEGIN			 

				DELETE FROM #IC_Aportes_Det where asignacion <> @planilla

			--select Distinct SUM(d.importeDocumento),d.importeDocumento,d.noIdentificado,d.claseDeAporte,d.consecutivo,d.claveCont from #IC_Aportes_Det d
			--inner join #IC_Aportes_Det dt on d.asignacion = dt.asignacion
			--where d.noIdentificado = dt.noIdentificado and d.apgid = dt.apgid and d.claseDeAporte = dt.claseDeAporte
			--group by d.importeDocumento,d.noIdentificado,d.claseDeAporte,d.consecutivo,d.claveCont 
			
				INSERT INTO #IC_Aportes_Det
				SELECT 1 AS consecutivo, d.[codigoSap], SUM(d.[ImporteDocumento]), d.[operador], d.[claveCont], d.[asignacion],d.[textoposicion], d.[ref1], d.[ref2], d.[tipoDocumento], d.[ref3], d.[noIdentificado],
				d.[adelantado], d.[identificadorDelBanco], d.[codigoBanco], d.[transitoria], d.[claseDeAporte], d.[claseDeAportePrescripcion], d.[tieneIntereses], d.[tipoInteres], d.[correccion],
				d.[tipoMora], d.[id], d.[codigoGenesys], d.[tipo], d.[claseCuenta], d.[usuario], d.[regId],d.[fechaRecaudo],  d.[fechaProcesamiento], d.[EstadoRegistroAporteAportante], d.[TipoSolicitante], SUM(d.[interes])as Intereses , d.[Tarifa],d.[operadorinformacion],max(d.apgid),''
				FROM #IC_Aportes_Det  d
				inner join #IC_Aportes_Det dt on d.asignacion = dt.asignacion
				where d.noIdentificado = dt.noIdentificado and d.apgid = dt.apgid and d.claseDeAporte = dt.claseDeAporte and d.tipoInteres = dt.tipoInteres
				GROUP BY d.[codigoSap], d.[operador],d.[claveCont], d.[asignacion],d.[textoposicion], d.[ref1], d.[ref2], d.[tipoDocumento], d.[ref3], d.[noIdentificado],
				d.[adelantado], d.[identificadorDelBanco], d.[codigoBanco], d.[transitoria],d.[claseDeAporte], d.[claseDeAportePrescripcion], d.[tieneIntereses], d.[tipoInteres], d.[correccion],
				d.[tipoMora], d.[id], d.[codigoGenesys], d.[tipo], d.[claseCuenta], d.[usuario], d.[regId],d.[fechaRecaudo],  d.[fechaProcesamiento], d.[EstadoRegistroAporteAportante], d.[TipoSolicitante], d.[Tarifa],d.[operadorinformacion]
		
				
				Delete From #IC_Aportes_Det where consecutivo = '0'
			END
				update d set d.consecutivo =dt.consecutivo
				--select dt.consecutivo,*
				from #IC_Aportes_Det d
				inner join
				(select ROW_NUMBER() OVER(ORDER BY dt.apgId ASC) AS consecutivo,dt.apgId from #IC_Aportes_Det dt GROUP BY Dt.APGID) dt on dt.apgId=d.apgId

				

			


	--------- Encabezado	
		
			
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
						CASE det.interes WHEN 0 THEN 'A09' ELSE 'A10' END  AS tipoMovimiento,
						ROW_NUMBER() OVER(ORDER BY regId ASC) AS consecutivo,
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
		
	
	--------------------------------------------------------------------------------------------------------------------------------------------

		IF (SELECT COUNT(claveCont) FROM #IC_Aportes_Det WHERE claveCont = '40') > 1 AND (SELECT tipoMovimiento FROM #IC_Aportes_Enc) <> 'A10'
		BEGIN
				Declare @N1 Varchar (2)
				Declare @N2	Varchar (2)
				Select  @N1 = (Select noIdentificado from #IC_Aportes_Det WHERE clavecont = '40' and consecutivo = (SELECT  MAX(consecutivo) FROM  #IC_Aportes_Det))   -- agregado para A09 que tiene diferente NI 10/11/2023 Camilo GOmez 
				Select  @N2 = (Select noIdentificado from #IC_Aportes_Det WHERE clavecont = '40' and consecutivo = (SELECT  MIN(consecutivo) FROM  #IC_Aportes_Det))   -- agregado para A09 que tiene diferente NI 10/11/2023 Camilo GOmez 

			DELETE #IC_Aportes_Det WHERE  claveCont = '40'  AND consecutivo = (SELECT  MAX(consecutivo) FROM  #IC_Aportes_Det) AND @N1 = @N2
			UPDATE #IC_Aportes_Det SET  importeDocumento = (SELECT SUM(apd.apdAporteObligatorio) 
															FROM core.pila.RegistroGeneral reg 
																 INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) ON regId = apgRegistroGeneral
																 LEFT JOIN  core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgId = apd.apdAporteGeneral
																 WHERE apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' AND
																  (reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @operadorinformacion) OR (reg.regid = @regId AND reg.regid <> 0 )
															) + interes WHERE claveCont = '40' AND @N1 = @N2
			UPDATE #IC_Aportes_Det SET  consecutivo = (SELECT  MIN(consecutivo) FROM  #IC_Aportes_Det)
		END
		----------------------------------------------------------------------------------------------------------------------------------------------------


---------- numero de identificacion de la persona de la afiliacion  para actualizacion de textoposicion
		declare @identi varchar (12)
		
		
					select @identi =(select Distinct per.perNumeroIdentificacion From pila.RegistroGeneral reg
					inner join AporteGeneral apg on reg.regid = apg.apgRegistroGeneral
					inner join AporteDetallado apd on apg.apgId = apd.apdaportegeneral
					inner join #IC_Aportes_Det d on reg.regId = d.regId
					inner join persona per on apd.apdpersona = per.perid
					where apg.apgId = d.apgid)

		Print @identi 	
	

--------------Actualizacion campo texto posicion ----------------------------------------------------------------------------------------------------------------------------------



		Declare @origen  varchar(16)
		
		Select @origen = (select Distinct asignacion from #IC_Aportes_Det)


		declare @planillaNadp varchar (100)
		declare @resultado  varchar (max)
		declare @planillaN1 varchar(100)

		select distinct @planillaNadp=planillaN 
				from dbo.aporteDetalladoRegistroControlN with (nolock)
				where planillaAntes = @origen

		select distinct  @planillaN1=planillaN 
		from dbo.aporteDetalladoRegistroControlN with (nolock)
		where planillaAntes = @planillaNadp

		select @resultado=STRING_AGG(planillaN, '\') 
		from (
		    select distinct planillaN 
		    from dbo.aporteDetalladoRegistroControlN 
			where planillaAntes = @planillaNadp AND redNumeroIdentificacionCotizante = @identi
			union
			select distinct planillaN 
			from dbo.aporteDetalladoRegistroControlN 
			where planillaAntes = @planillaN1 AND redNumeroIdentificacionCotizante = @identi
			) as Subconsulta


		if (select @resultado) is null
			begin
				Update #IC_Aportes_Det set textoPosicion = planillaN
				from aporteDetalladoRegistroControlN where asignacion not like '%M%' and planillaAntes = asignacion
			end
			Else 
				begin
				update  #IC_Aportes_Det set textoPosicion = (select @planillaNadp  + '\' + @resultado)
				end 


--SELECT STRING_AGG(CONVERT(NVARCHAR(max), planillaN), '\') AS textoposicion 
--		FROM aporteDetalladoRegistroControlN 
--		WHERE redNumeroIdentificacionCotizante = @identi and planillaAntes = '123000789'


------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------




		--IF @referenciaNum -1 = (select top 1  max(CONVERT(bigint, referencia)) from sap.IC_Aportes_Enc INNER JOIN sap.IC_Referencia ON (CONVERT(bigint, referencia)+1)  = valorActual AND estado = 'A'and comentario ='A')
		--	BEGIN

			IF (SELECT COUNT(*) FROM #IC_Aportes_Det) >=1
			BEGIN

					SELECT @referenciaNum = valorActual 
					FROM sap.IC_Referencia 
					WHERE comentario ='P'
					AND estado = 'A';


					UPDATE #IC_Aportes_Enc
						SET referencia =  @referenciaNum
	--comentarear
			   		
					UPDATE sap.ic_referencia
						SET valorActual = @referenciaNum + 1
						WHERE comentario ='P'
					AND estado = 'A';	
			

				END
	
			END
			
		END
	
	END
	

		--comentarear	
	
	
		IF ((SELECT referencia FROM #IC_Aportes_Enc ) IS NOT NULL )
		BEGIN

		
			IF EXISTS (SELECT enca.consecutivo FROM #IC_Aportes_Enc enca INNER JOIN #IC_Aportes_Det deta on enca.consecutivo = deta.consecutivo where deta.importeDocumento <> 0)
			BEGIN
	

		
				INSERT INTO IC_Aportes_Enc(
					[fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo],[referencia],[tipoMovimiento],[observacion],
					[moneda],[documentoContable],[sociedad],[ejercicio],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario])
				SELECT distinct [fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo],[referencia],[tipoMovimiento],[observacion],
					[moneda],[documentoContable],[sociedad],[ejercicio],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario]
				FROM #IC_Aportes_Enc WITH(NOLOCK) ; 
 
			 SELECT @consecutivo=MAX(consecutivo) FROM IC_Aportes_Enc

					UPDATE #IC_Aportes_Det
					SET consecutivo = @consecutivo

				INSERT INTO sap.IC_Aportes_Det([consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
						[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
						[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[codigoGenesys],[tipo],
						[claseCuenta],[usuario],[fechaEjecucion])
				SELECT [consecutivo],[codigoSap],CAST(CAST([importeDocumento] AS BIGINT) AS VARCHAR(18)),[operador],[claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
						[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
						[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[codigoGenesys],[tipo],
						[claseCuenta],[usuario], GETDATE()-'05:00:00' 
				FROM #IC_Aportes_Det  WITH(NOLOCK)
				WHERE importeDocumento <> 0  ---  para cuando el registro viene sin detalle y que no traiga lineas en valores 0 08/10/2022
		
				UPDATE  #IC_Aportes_Det
					SET consecutivo =(SELECT consecutivo FROM #IC_Aportes_Enc)
			
				-- INSERCIoN EN LA TABLA DE CONTROL DE PLANILLAS
				
				--declare @apgid bigint = '2593868'
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
				ORDER BY consecutivo

				DROP TABLE IF EXISTS #IC_Aportes_Enc;
				DROP TABLE IF EXISTS #IC_Aportes_Det;				
				DROP TABLE IF EXISTS #APLICA 
				DROP TABLE IF EXISTS #apgid
	
	
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
	SELECT 'ok ejec',GETDATE() 
	--DELETE FROM	sap.ic_internoA
	--END
	--ELSE BEGIN

	--	SELECT 'El proceso ya se encuentra en ejecucion'
	
	--END
END