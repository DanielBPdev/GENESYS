CREATE OR ALTER    PROCEDURE [sap].[USP_GetICAPORTES_Insert_A34] 	@planilla VARCHAR(20),	@planillaN VARCHAR(20),	@estadoAporte VARCHAR (20), @apgid BIGINT,	@solNumeroRadicacion VARCHAR (30),	
	@solTipoTransaccion VARCHAR (30),	@operadorinformacion INT, @regid BIGINT
AS
BEGIN
SET NOCOUNT ON; 

DECLARE @referenciaNum BIGINT, @consecutivo BIGINT, @PerNumD VARCHAR(16), @perTipD VARCHAR(10)

	SELECT regid, regNumPlanilla, regOperadorInformacion,regValTotalApoObligatorio, regValorIntMora,regTipoIdentificacionAportante,regNumeroIdentificacionAportante,
			regTipoPlanilla,regDateTimeInsert,regOUTEstadoArchivo,regNumPlanillaAsociada,regNombreAportante,regPeriodoAporte
	INTO #registrogeneralA34			
	FROM core.pila.RegistroGeneral	
	WHERE (regid = @regid AND regid <> 0) 	

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
		[operadorinformacion] INT NULL,
		[apgid] VARCHAR (20)
			
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




BEGIN TRY 
	BEGIN TRANSACTION

		
	--SET @regid = '2360498'
	--SET @apgid = '2595616'


IF @regid IS NULL OR @regid = ''
BEGIN
SELECT @regid = (SELECT apgRegistroGeneral FROM core.dbo.AporteGeneral where apgid = @apgid)
END

set @planilla = (select regNumPlanilla from #registrogeneralA34)
set @operadorinformacion =(select regOperadorInformacion from #registrogeneralA34)

	-- DETALLE: reconoce los registros de PILA
	----------------------------------------------------------Insert planila-------------------------------------------------------------------------
		INSERT INTO #IC_Aportes_Det 
		 SELECT DISTINCT
				ROW_NUMBER() OVER(ORDER BY reg.regId ASC) AS consecutivo,
				'' AS codigoSap, 
				SUM(apd.apdAporteObligatorio)  AS ImporteDocumento,
				--round(ISNULL(apg.apgValTotalApoObligatorio, 0) + ISNULL(reg.regValorIntMora, 0),0) AS ImporteDocumento,
				'' AS operador,			-- Solo aplica para la INTEGRACIoN DE CARTERA
				'40' AS claveCont,
				ISNULL(reg.regnumplanilla,'') AS asignacion,
				'' as textoposicion,
				reg.regNumeroIdentificacionAportante AS ref1,
				'' AS ref2,
				TipoIdHomologado AS tipoDocumento,
				LEFT(reg.regNombreAportante, 20) AS ref3,
				CASE WHEN  apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04 OR apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.01 or apd.apdTarifa = 0.03) THEN 'N1'
					 WHEN  apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null) THEN 'N1'  
					 WHEN  apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'N2'
					 WHEN  apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'N3'
					 WHEN  apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'N4'
					 WHEN  apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'N5'
				ELSE '' 
				END  noIdentificado,
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
				SUM(apd.apdValorIntMora) AS interes, 
				apd.apdTarifa AS Tarifa,
				reg.regOperadorInformacion  AS operadorinformacion,
				apg.apgId AS apgid
			FROM #registrogeneralA34 reg
			INNER JOIN core.sap.MaestraTiposIdentificacion mai ON reg.regTipoIdentificacionAportante = mai.TipoIdGenesys
			INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) on reg.regId = apgRegistroGeneral
			INNER JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgid = apd.apdAporteGeneral	
			LEFT JOIN core.dbo.CUENTAS_BANCARIAS cb WITH(NOLOCK) on apg.apgCuentaBancariaRecaudo = cb.ID 
			LEFT JOIN core.sap.MaestracodigoEntidad ce WITH(NOLOCK) on cb.ID = ce.idCuentasBancarias 
			--LEFT JOIN core.dbo.AporteGeneral apg2 WITH(NOLOCK) ON reg.regId = apg2.apgRegistroGeneral 
			--LEFT JOIN core.dbo.AporteDetallado apd2 WITH(NOLOCK) ON  apg2.apgId = apd2.apdAporteGeneral
			--LEFT JOIN #APLICA WITH (NOLOCK) ON reg.regnumplanilla = Planilla 
			WHERE  (regId = @regid AND apg.apgId = @apgid) AND apg.apgEstadoRegistroAporteAportante = 'RELACIONADO'
			GROUP BY reg.regId, reg.regValTotalApoObligatorio,reg.regnumplanilla,reg.regNumeroIdentificacionAportante,TipoIdHomologado,reg.regNombreAportante,apg.apgNumeroCuenta,
			cb.NUMERO_CUENTA,apg.apgCuentaBancariaRecaudo,apg.apgCodEntidadFinanciera,apg.apgCuentaBancariaRecaudo,apg.apgCodEntidadFinanciera,apg.apgModalidadRecaudoAporte,
			apg.apgFechaRecaudo,apg.apgEstadoRegistroAporteAportante,apg.apgTipoSolicitante,reg.regValorIntMora,apd.apdTarifa,ce.codigoEntidad, --apg2.apgEstadoRegistroAporteAportante,
			/*apg2.apgTipoSolicitante, apd2.apdTarifa,apd2.apdId,*/reg.regOperadorInformacion,apg.apgEstadoRegistroAporteAportante,apg.apgTipoSolicitante,apd.apdTarifa,apd.apdId,apg.apgId
			
	

	DELETE FROM #IC_Aportes_Det where asignacion <> @planilla 	

	
	IF (SELECT TOP 1 ASIGNACION FROM #IC_Aportes_Det) = ''
	BEGIN
	DELETE #IC_Aportes_Det WHERE apgid <> @apgid
	END

	
	DELETE #IC_Aportes_Det WHERE importeDocumento = 0 	

	IF (SELECT TOP 1 asignacion FROM #IC_Aportes_Det) IS NOT NULL 
			BEGIN			 


				UPDATE #IC_Aportes_Det  SET  consecutivo = 3 	

					
				INSERT INTO #IC_Aportes_Det
				SELECT ROW_NUMBER() OVER(ORDER BY regId ASC) AS Consecutivo, [codigoSap], SUM([ImporteDocumento]), [operador], '40' [claveCont], [asignacion],[textoposicion],[ref1], [ref2], [tipoDocumento], [ref3], [noIdentificado],
				[adelantado], [identificadorDelBanco], [codigoBanco], [transitoria], [claseDeAporte], [claseDeAportePrescripcion], [tieneIntereses], [tipoInteres], [correccion],
				[tipoMora], [id], [codigoGenesys], [tipo], [claseCuenta], [usuario], [regId],[fechaRecaudo],  [fechaProcesamiento], [EstadoRegistroAporteAportante], [TipoSolicitante], SUM([interes]), [Tarifa],[operadorinformacion],''
				FROM #IC_Aportes_Det  det	
				GROUP BY [codigoSap], [operador], [asignacion],[textoposicion], [ref1], [ref2], [tipoDocumento], [ref3], [noIdentificado],
				[adelantado], [identificadorDelBanco], [codigoBanco], [transitoria],[claseDeAporte], [claseDeAportePrescripcion], [tieneIntereses], [tipoInteres], [correccion],
				[tipoMora], [id], [codigoGenesys], [tipo], [claseCuenta], [usuario], [regId],[fechaRecaudo],  [fechaProcesamiento], [EstadoRegistroAporteAportante], [TipoSolicitante], [Tarifa],[operadorinformacion]
	

				DELETE FROM #IC_Aportes_Det  WHERE consecutivo > 2 
		
			END
		
	IF EXISTS (SELECT * FROM #IC_Aportes_Det) 
		BEGIN
		/*
			SELECT  apg.apgEstadoRegistroAporteAportante, apg.apgFormaReconocimientoAporte, apg.apgMarcaPeriodo, apd.apdTarifa,  sum(ROUND(apgValTotalApoObligatorio, 0)) AS  Valoraporte, sum(ROUND(apgValorIntMora, 0))AS ValorInteres   
			FROM  core.pila.RegistroGeneral reg
			--INNER JOIN core.pila.RegistroGeneral reg  ON iad.regId = reg.regId
			INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) on reg.regId = apg.apgRegistroGeneral
			LEFT JOIN  core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgId = apd.apdAporteGeneral
			WHERE (reg.regValTotalApoObligatorio <> 0 OR reg.regValorIntMora <> 0 ) and regNumPlanilla = @planilla --AND apg.apgEstadoRegistroAporteAportante = @estadoAporte
			GROUP BY  apg.apgEstadoRegistroAporteAportante, apg.apgFormaReconocimientoAporte, apg.apgMarcaPeriodo, apd.apdTarifa
			*/

			
			INSERT INTO #IC_Aportes_Det 
			SELECT [consecutivo], [codigoSap], [ImporteDocumento], [operador], '50' [claveCont], [asignacion],[textoposicion], [ref1], [ref2], [tipoDocumento], [ref3], '' AS [noIdentificado],
			[adelantado], '', '', '', [claseDeAporte], [claseDeAportePrescripcion], [tieneIntereses], [tipoInteres], [correccion],
			[tipoMora], [id], [codigoGenesys], [tipo], [claseCuenta], [usuario], [regId],[fechaRecaudo],  [fechaProcesamiento], [EstadoRegistroAporteAportante], [TipoSolicitante], [interes], [Tarifa], [operadorinformacion],''
			FROM #IC_Aportes_Det  det											 
																	 
			--Actualizacion de campos clave contable 50 ------------- Planilla-----------------------------------------------------
			UPDATE #IC_Aportes_Det    SET  adelantado = CASE WHEN apd.apdMarcaPeriodo = 'PERIODO_FUTURO' AND	TipoSolicitante = 'EMPLEADOR' AND (Tarifa = 0.00 OR Tarifa = 0.04 OR Tarifa = 0.02 OR Tarifa = 0.01 or Tarifa = 0.03) THEN 'A1'
															 WHEN apd.apdMarcaPeriodo = 'PERIODO_FUTURO' AND TipoSolicitante = 'EMPLEADOR' AND (apd.apdId is null) THEN 'A1'---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
															 WHEN apd.apdMarcaPeriodo = 'PERIODO_FUTURO' AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.02 OR Tarifa = 0.04) THEN 'A2'   
															 WHEN apd.apdMarcaPeriodo = 'PERIODO_FUTURO' AND TipoSolicitante = 'PENSIONADO' AND Tarifa <> 0.006 THEN 'A3'
															 WHEN apd.apdMarcaPeriodo = 'PERIODO_FUTURO' AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.006 OR Tarifa = 0.01) THEN 'A4'
															 WHEN apd.apdMarcaPeriodo = 'PERIODO_FUTURO' AND TipoSolicitante = 'PENSIONADO' AND Tarifa = 0.006 THEN 'A5'
															 ELSE '**' 	END,
										fechaRecaudo = apg.apgFechaRecaudo, 
										fechaProcesamiento   = CASE WHEN [SAP].[calcularPrimerDiaHabilMes]() = [SAP].[calcularSiguienteDiaHabil]() AND DAY(DATEADD(HOUR,-5,GETDATE())) >= [SAP].[calcularPrimerDiaHabilMes]() AND DATEDIFF(DAY, apg.apgFechaRecaudo, DATEADD(HOUR,-5,GETDATE())) > 0
												THEN EOMONTH(DATEADD(HOUR,-5,GETDATE()),-1)
												ELSE DATEADD(HOUR,-5,GETDATE()) END
											FROM #registrogeneralA34 reg
				INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) ON regId = apgRegistroGeneral
				LEFT JOIN #IC_Aportes_Det det WITH(NOLOCK) ON reg.regId = det.regId
				LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgId = apd.apdAporteGeneral 
				WHERE det.claveCont = '50' AND (apg.apgId = @apgid) 
			
	

			UPDATE #IC_Aportes_Det SET	codigoGenesys = apg.apgPersona,	tipo = CASE WHEN tipoDocumento = 'NIT' THEN 'E'	ELSE 'P' END								
				FROM dbo.AporteGeneral  apg
				WHERE apg.apgId = @apgid
		

	
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
					'A34'  AS tipoMovimiento,
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
		END		

		
	--------------------------------------------------------------------------------------------------------------------------------------------
	IF (SELECT COUNT(claveCont) FROM #IC_Aportes_Det WHERE claveCont = '40') > 1
	BEGIN

		DELETE #IC_Aportes_Det WHERE  claveCont = '40'  AND consecutivo = (SELECT  MAX(consecutivo) FROM  #IC_Aportes_Det)
		UPDATE #IC_Aportes_Det SET  consecutivo = (SELECT  MIN(consecutivo) FROM  #IC_Aportes_Det)
		UPDATE #IC_Aportes_Det SET  importeDocumento = (SELECT SUM(apd.apdAporteObligatorio) 
														FROM core.pila.RegistroGeneral reg 
															 INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) ON regId = apgRegistroGeneral
															 LEFT JOIN  core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgId = apd.apdAporteGeneral
															 WHERE apg.apgEstadoRegistroAporteAportante = 'RELACIONADO' AND
															  (reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @operadorinformacion) OR (reg.regid = @regid AND reg.regid <> 0 )
														) + interes WHERE claveCont = '40' 
	END
	----------------------------------------------------------------------------------------------------------------------------------------------------

	UPDATE  #IC_Aportes_Det SET importeDocumento = importeDocumento + interes WHERE claveCont = '40'  
	UPDATE	#IC_Aportes_Det SET asignacion = CONCAT('M', @apgid) WHERE asignacion = ''

	---------- numero de identificacion de la persona de la devolucion  para actualizacion de textoposicion
		declare @identi varchar (12)
		If (Select Distinct TipoSolicitante from #IC_Aportes_Det) = 'EMPLEADOR'
			BEGIN
				select @identi =(select perNumeroIdentificacion From #registrogeneralA34
				inner join AporteGeneral on regid = apgRegistroGeneral
				inner join Empresa on apgEmpresa = empId
				inner join Persona on empPersona = perId
				where regid = @regid and apgid = @apgid)
			END
			ELSE
				BEGIN
					select @identi =(select perNumeroIdentificacion From #registrogeneralA34
					inner join AporteGeneral on regid = apgRegistroGeneral
					inner join AporteDetallado on apgid = apdaportegeneral
					inner join persona on apdpersona = perid
					where regid = @regid and apgid = @apgid)
				END

		Print @identi 
		
	--------------Actualizacion campo texto posicion ----------------------------------------------------------------------------------------------------------------------------------
		Declare @origen  varchar(16)
	IF(select Distinct tipoSolicitante from #IC_Aportes_Det) = 'EMPLEADOR'
			BEGIN
				Select @origen = (select Distinct asignacion from #IC_Aportes_Det)
			
				--select @Origen as origen
			
				declare @resultadoE  varchar (max)
				select @resultadoE = (select dbo.ASP_BuscarPlanillasN (@origen))
			
				if (select @resultadoE) is null
					begin
						Update #IC_Aportes_Det set textoPosicion = planillaN
						from aporteDetalladoRegistroControlN where asignacion not like '%M%' and planillaAntes = asignacion
					end
					Else 
						begin
						update  #IC_Aportes_Det set textoPosicion = (select dbo.ASP_BuscarPlanillasN (@origen)) where asignacion not like '%M%' and @origen = asignacion
						end 
			END
			ELSE
				BEGIN
					Select @origen = (select Distinct asignacion from #IC_Aportes_Det)
			
					print @Origen
			
					declare @planillaNadp varchar (100)
					declare @resultado  varchar (max)
			
					
					select Distinct @planillaNadp = apdn.planillaN
					from dbo.aporteDetalladoRegistroControlN apdn
					where planillaAntes = @origen
					select @resultado = STRING_AGG(CONVERT(NVARCHAR(max), planillaN), '\') 
					from (
							select distinct planillaN
							from dbo.aporteDetalladoRegistroControlN 
							where planillaAntes = @planillaNadp AND redNumeroIdentificacionCotizante = @identi
						) as Subconsulta
					
					--select @resultado + '\' + @planillaNadp as textoposicion
			
			
					if (select @resultado) is null
						begin
							Update #IC_Aportes_Det set textoPosicion = planillaN
							from aporteDetalladoRegistroControlN where asignacion not like '%M%' and planillaAntes = asignacion
						end
						Else 
							begin
							update  #IC_Aportes_Det set textoPosicion = (select @resultado + '\' + @planillaNadp) where asignacion not like '%M%'
							end 
				END
		--Update #IC_Aportes_Det set textoPosicion = planillaN
		--from aporteDetalladoRegistroControlN where asignacion not like '%M%' and planillaAntes = asignacion


--SELECT STRING_AGG(CONVERT(NVARCHAR(max), planillaN), '\') AS textoposicion 
--		FROM aporteDetalladoRegistroControlN 
--		WHERE redNumeroIdentificacionCotizante = @identi and planillaAntes = '123000789'


------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Inicio comentario 


		SELECT @referenciaNum = valorActual 
			FROM sap.ic_referencia
			WHERE comentario ='A'
			AND estado = 'A';

			UPDATE #IC_Aportes_Enc
				SET referencia =  @referenciaNum
	
	
		UPDATE sap.ic_referencia
			SET valorActual = @referenciaNum + 1
			WHERE comentario ='A'
			AND estado = 'A';		
    
  
		


	
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

      
			INSERT INTO sap.IC_Aportes_Det([consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
					[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
					[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[codigoGenesys],[tipo],
					[claseCuenta],[usuario], [fechaEjecucion])
			SELECT [consecutivo],[codigoSap],CAST(CAST([importeDocumento] AS BIGINT) AS VARCHAR(18)),[operador],[claveCont],[asignacion],[textoposicion],[ref1],[ref2],[tipoDocumento],[ref3],
					[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
					[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[codigoGenesys],[tipo],
					[claseCuenta],[usuario],  GETDATE()-'05:00:00' 
			FROM #IC_Aportes_Det  WITH(NOLOCK)
			WHERE importeDocumento <> 0  ---  para cuando el registro viene sin detalle y que no traiga lineas en valores 0 08/10/2022
		
			UPDATE  #IC_Aportes_Det
				SET consecutivo =(SELECT consecutivo FROM #IC_Aportes_Enc)
		
			-- INSERCIoN EN LA TABLA DE CONTROL DE PLANILLAS
 
 
			INSERT INTO sap.PlanillasCtrl
			SELECT DISTINCT LEFT(det.asignacion, 12), 1, det.ref1, det.tipoDocumento, SAP.GetLocalDate(), 'APORTES', det.ref3 + enc.observacion, enc.tipoMovimiento,estadoaporteaportantetercero,operadorinformacion,@apgid 
			FROM #IC_Aportes_Det det WITH(NOLOCK)
			INNER JOIN #IC_Aportes_Enc enc WITH(NOLOCK) ON enc.consecutivo = det.consecutivo
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

			DROP TABLE IF EXISTS #IC_Aportes_Det;	
			DROP TABLE IF EXISTS #IC_Aportes_Enc;					
			DROP TABLE IF EXISTS #APLICA 
			DROP TABLE IF EXISTS #registrogeneralA34
			

	
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
END