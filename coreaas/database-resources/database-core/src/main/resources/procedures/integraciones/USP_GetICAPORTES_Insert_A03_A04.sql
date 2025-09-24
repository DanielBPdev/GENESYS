CREATE OR ALTER    PROCEDURE [sap].[USP_GetICAPORTES_Insert_A03_A04] 
	@planilla VARCHAR(20),	@planillaN VARCHAR(20),	@estadoAporte VARCHAR (20),	@apgid BIGINT,@solNumeroRadicacion VARCHAR (30),	
	@solTipoTransaccion VARCHAR (30),	@operadorinformacion INT
AS
BEGIN
SET NOCOUNT ON; 

--IF (SELECT COUNT(*) FROM sap.ic_internoA WITH(NOLOCK)) <= 0 BEGIN
		
		  
--			INSERT sap.ic_internoA
--			SELECT 1

	DECLARE @planillaA BIGINT,@referenciaNum BIGINT, @consecutivo BIGINT, @regId BIGINT


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
			[Tarifa]numeric (7,5)NULL,
			operadorinformacion INT NULL,
			apgid VARCHAR(20)
			
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
		(
			[Aplica] [INT] NULL,
			[Planilla][varchar](30) NULL,
			[regid][BIGINT])

	--9441144187 84, A03
	--9442030758 84, 9445867669 24, 9442037308 84, A04


	BEGIN TRY 
		BEGIN TRANSACTION

	
	
			-- Validacion recaudo Manual
		
				INSERT INTO #APLICA
				SELECT TOP 1 1 as aplica,  '' as planilla, apg1.apgRegistroGeneral  from sap.IC_Aportes_Det det
				INNER JOIN core.sap.IC_Aportes_Enc enc WITH(NOLOCK) ON  det.consecutivo = enc.consecutivo
				INNER JOIN core.dbo.AporteGeneral  apg1 WITH(NOLOCK) ON apg1.apgId = @apgid
				WHERE  asignacion = concat('M', @apgid) and tipoMovimiento in ('A05', 'A06')
				AND apg1.apgEstadoRegistroAporteAportante = 'REGISTRADO'   AND apg1.apgMarcaPeriodo IN ('PERIODO_REGULAR','PERIODO_RETROACTIVO')
				AND apg1.apgEstadoAportante <> 'ACTIVO'
		
		  -- Validacion planilla
				INSERT INTO #APLICA
				SELECT  TOP 1  CASE WHEN apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' --AND apg.apgEstadoRegistroAporteAportante = @estadoAporte
							   AND apgMarcaPeriodo IN ('PERIODO_REGULAR','PERIODO_RETROACTIVO')
							   --AND apgEstadoAportante = 'ACTIVO'
							   THEN 1 ELSE 0 END AS Aplica,  reg.regnumplanilla AS Planilla, regId
				FROM core.pila.RegistroGeneral reg
				INNER JOIN core.sap.IC_Aportes_Det det WITH(NOLOCK) ON  reg.regNumPlanilla = det.asignacion
				INNER JOIN core.sap.IC_Aportes_Enc enc WITH(NOLOCK) ON  det.consecutivo = enc.consecutivo
				INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) ON reg.regId = apg.apgRegistroGeneral
				WHERE (det.asignacion = @planilla AND enc.Tipomovimiento IN('A05', 'A06') AND enc.estadoReg = 'S' )  AND
				reg.regnumplanilla =@planilla  AND reg.regOperadorInformacion = @operadorinformacion
				AND apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' 


			
	  DELETE #APLICA WHERE Aplica = 0

	SELECT @regid = (SELECT TOP 1 regid FROM #APLICA) 
	IF (SELECT MAX(Aplica) FROM #APLICA) = 1
	BEGIN

		IF(SELECT  MAX(regValTotalApoObligatorio) FROM  core.pila.RegistroGeneral WHERE ( regNumPlanilla = @planilla AND regOperadorInformacion = @operadorinformacion) OR (regid = @regId AND regid <> 0 )) <> 0
		BEGIN
		-- DETALLE: reconoce los registros de PILA
			INSERT INTO #IC_Aportes_Det 
			 SELECT 
					1 AS consecutivo,
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
				FROM core.pila.RegistroGeneral reg
				INNER JOIN core.sap.MaestraTiposIdentificacion mai ON reg.regTipoIdentificacionAportante = mai.TipoIdGenesys
				INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) on reg.regId = apgRegistroGeneral AND apg.apgEstadoRegistroAporteAportante = 'REGISTRADO'
				INNER JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgid = apd.apdAporteGeneral	
				LEFT JOIN core.dbo.CUENTAS_BANCARIAS cb WITH(NOLOCK) on apg.apgCuentaBancariaRecaudo = cb.ID 
				LEFT JOIN core.sap.MaestracodigoEntidad ce WITH(NOLOCK) on cb.ID = ce.idCuentasBancarias 
				WHERE  (regId = @regId AND apg.apgid = @apgid) AND (apd.apdAporteObligatorio <> 0 AND apd.apdTarifa <> 0)  
				GROUP BY reg.regId, reg.regValTotalApoObligatorio,reg.regnumplanilla,reg.regNumeroIdentificacionAportante,TipoIdHomologado,reg.regNombreAportante,apg.apgNumeroCuenta,
				cb.NUMERO_CUENTA,apg.apgCuentaBancariaRecaudo,apg.apgCodEntidadFinanciera,apg.apgCuentaBancariaRecaudo,apg.apgCodEntidadFinanciera,apg.apgModalidadRecaudoAporte,
				apg.apgFechaRecaudo,apg.apgEstadoRegistroAporteAportante,apg.apgTipoSolicitante,apd.apdTarifa,ce.codigoEntidad, reg.regOperadorInformacion, apgId, apdId

	
		DELETE FROM #IC_Aportes_Det  WHERE noIdentificado = ''
	
		IF (SELECT Planilla FROM #APLICA ) IS NULL 
			BEGIN 
				DELETE #IC_Aportes_Det WHERE apgid <> @apgid
			END
		

			   		 
		IF (SELECT Planilla FROM #APLICA ) IS NOT NULL 
		BEGIN
		UPDATE #IC_Aportes_Det SET ref2 = apgid
		END 



		DELETE #IC_Aportes_Det WHERE importeDocumento = 0 AND Tarifa = 0	

		
		--Detectar apgid a integrar
		SELECT apgid, asignacion 
		INTO #apgid
		FROM  #IC_Aportes_Det WHERE importeDocumento > 0 
		-------------------------------------------------------------------------------------------------------------------------------
		-------------------------------------------------------------------------------------------------------------------------------
						  	
	
		IF EXISTS (SELECT * FROM #IC_Aportes_Det) 
			BEGIN
		
				INSERT INTO #IC_Aportes_Det 
				SELECT [consecutivo], [codigoSap], [ImporteDocumento], [operador], '50' [claveCont], [asignacion],[textoposicion], [ref1], [ref2], [tipoDocumento], [ref3], [noIdentificado],
				[adelantado], [identificadorDelBanco], [codigoBanco], [transitoria], [claseDeAporte], [claseDeAportePrescripcion], [tieneIntereses], [tipoInteres], [correccion],
				[tipoMora], [id], [codigoGenesys], [tipo], [claseCuenta], [usuario], [regId],[fechaRecaudo],  [fechaProcesamiento], [EstadoRegistroAporteAportante], [TipoSolicitante], [interes], [Tarifa],[operadorinformacion],''
				FROM #IC_Aportes_Det  det											 
																		 
				--Actualizacion de campos clave contable 50
				UPDATE #IC_Aportes_Det    SET   claseDeAportePrescripcion = (CASE WHEN apg.apgFormaReconocimientoAporte = 'RECONOCIMIENTO_RETROACTIVO_MANUAL' AND apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' THEN '1' ELSE '2' END ),
												claseDeAporte = (CASE	WHEN apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa = 0.00 OR apd.apdTarifa = 0.04)  THEN 'CA1'
																		WHEN apg.apgTipoSolicitante = 'EMPLEADOR' AND (apd.apdTarifa is null)  THEN 'CA1' ---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
																		WHEN apg.apgTipoSolicitante = 'EMPLEADOR' AND apd.apdTarifa < 0.04 THEN 'CA1'
																		WHEN apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.02 OR apd.apdTarifa = 0.04) THEN 'CA2'
																		WHEN apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa <> 0.006 THEN 'CA3'
																		WHEN apg.apgTipoSolicitante = 'PENSIONADO' AND apd.apdTarifa = 0.006 THEN 'CA4'
																		WHEN apg.apgTipoSolicitante = 'INDEPENDIENTE' AND (apd.apdTarifa = 0.006 OR apd.apdTarifa = 0.01) THEN 'CA5'
																		ELSE ''	END),
											fechaRecaudo = apg.apgFechaRecaudo, 
											fechaProcesamiento   = CASE WHEN [SAP].[calcularPrimerDiaHabilMes]() = [SAP].[calcularSiguienteDiaHabil]() AND DAY(DATEADD(HOUR,-5,GETDATE())) >= [SAP].[calcularPrimerDiaHabilMes]() AND DATEDIFF(DAY, apg.apgFechaRecaudo, DATEADD(HOUR,-5,GETDATE())) > 0
													THEN EOMONTH(DATEADD(HOUR,-5,GETDATE()),-1)
													ELSE DATEADD(HOUR,-5,GETDATE()) END, 
											EstadoRegistroAporteAportante = apg.apgEstadoRegistroAporteAportante,
											TipoSolicitante = apgTipoSolicitante,
											Tarifa= apd.apdTarifa
					FROM core.pila.RegistroGeneral reg			
					INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) ON reg.regId = apgRegistroGeneral AND apg.apgEstadoRegistroAporteAportante = 'REGISTRADO'
					INNER JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgid = apd.apdAporteGeneral			
					WHERE claveCont = '50' AND (reg.regid = @regId AND apg.apgId = @apgid)
			

		--------------------------------------------------------------------------------------------------------------------------------------------	
				UPDATE #IC_Aportes_Det SET	codigoGenesys = per.perId,	tipo = CASE WHEN tipoDocumento = 'NIT' THEN 'E'	ELSE 'P' END								
				FROM core.pila.RegistroGeneral reg
					INNER JOIN Persona per WITH(NOLOCK) ON reg.regNumeroIdentificacionAportante = per.perNumeroIdentificacion
					WHERE (reg.regid =@regId AND reg.regid <> 0 )


			
				IF (SELECT Planilla FROM #APLICA) IS NULL
				BEGIN
				UPDATE #IC_Aportes_Det SET	codigoGenesys = apg.apgPersona,	tipo = CASE WHEN tipoDocumento = 'NIT' THEN 'E'	ELSE 'P' END								
					FROM dbo.AporteGeneral  apg
					WHERE apg.apgId = @apgid
				END


			-- Validacion para crear otra detalle con clave contable si es tipo movimiento A04

					IF (SELECT TOP 1 apgValorIntMora FROM  core.pila.RegistroGeneral inner join AporteGeneral on regid = apgRegistroGeneral
						WHERE ( apgValorIntMora > 0 AND (regNumPlanilla = @planilla) OR (regId = @regId AND regNumPlanilla IS NULL)) ) > 0
					BEGIN

					INSERT INTO #IC_Aportes_Det 
						SELECT [consecutivo], [codigoSap], interes AS [ImporteDocumento], [operador], '50' [claveCont], [asignacion],[textoposicion], [ref1], [ref2], [tipoDocumento], [ref3], '',
						[adelantado], [identificadorDelBanco], [codigoBanco], [transitoria], '', '', 1 AS [tieneIntereses], 
			 			CASE
							WHEN EstadoRegistroAporteAportante = 'REGISTRADO'  AND TipoSolicitante = 'EMPLEADOR' AND (Tarifa = 0.00 OR Tarifa = 0.04) THEN 'IM1'
							WHEN EstadoRegistroAporteAportante = 'REGISTRADO'  AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.02 OR Tarifa = 0.04) THEN 'IM2'
							WHEN EstadoRegistroAporteAportante = 'REGISTRADO'  AND TipoSolicitante = 'PENSIONADO' AND Tarifa <> 0.006 THEN 'IM3'
							WHEN EstadoRegistroAporteAportante = 'REGISTRADO'  AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.006 OR Tarifa = 0.01) THEN 'IM4'
							WHEN EstadoRegistroAporteAportante = 'REGISTRADO'  AND TipoSolicitante = 'PENSIONADO' AND Tarifa = 0.006 THEN 'IM5'
							ELSE ''	END AS [tipoInteres], 
							[correccion],	[tipoMora], [id], [codigoGenesys], [tipo], [claseCuenta], [usuario], [regId],[fechaRecaudo],  [fechaProcesamiento], [EstadoRegistroAporteAportante], [TipoSolicitante], [interes], [tarifa],[operadorinformacion],''
						FROM #IC_Aportes_Det  det	WHERE claveCont = '50'

						INSERT INTO #IC_Aportes_Det 
						SELECT [consecutivo], [codigoSap], interes AS [ImporteDocumento], [operador], '40' [claveCont], [asignacion],[textoposicion], [ref1], [ref2], [tipoDocumento], [ref3], '',
						[adelantado], [identificadorDelBanco], [codigoBanco], [transitoria], '', '', 1 AS [tieneIntereses], 
			 			CASE WHEN tipoInteres = 'IM1' THEN 'IN1' 
							 WHEN tipoInteres = 'IM2' THEN 'IN2' 
							 WHEN tipoInteres = 'IM3' THEN 'IN3'
							 WHEN tipoInteres = 'IM4' THEN 'IN4'
							 WHEN tipoInteres = 'IM5' THEN 'IN5'
							 ELSE '' END AS [tipoInteres], 
							[correccion],	[tipoMora], [id], [codigoGenesys], [tipo], [claseCuenta], [usuario], [regId],[fechaRecaudo],  [fechaProcesamiento], [EstadoRegistroAporteAportante], [TipoSolicitante], [interes], [tarifa],[operadorinformacion],''
						FROM #IC_Aportes_Det  det	WHERE claveCont = '50' AND tieneIntereses = 1
					END
		
	
		-- Validacion para crear otra detalle con clave contable si es tipo movimiento A34


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
					CASE det.interes WHEN 0 THEN 'A03' ELSE 'A04' END  AS tipoMovimiento,
					consecutivo AS consecutivo,
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
				
		


	  UPDATE #IC_Aportes_Det SET asignacion = CONCAT('M', @apgid) WHERE asignacion = ''
		
---------- numero de identificacion de la persona de la afiliacion  para actualizacion de textoposicion
		declare @identi varchar (12)
		If (Select Distinct TipoSolicitante from #IC_Aportes_Det) = 'EMPLEADOR'
			BEGIN
				select @identi =(select perNumeroIdentificacion From pila.RegistroGeneral
				inner join AporteGeneral on regid = apgRegistroGeneral
				inner join Empresa on apgEmpresa = empId
				inner join Persona on empPersona = perId
				where regid = @regid and apgid = @apgid)
			END
			ELSE
				BEGIN
					select @identi =(select perNumeroIdentificacion From pila.RegistroGeneral
					inner join AporteGeneral on regid = apgRegistroGeneral
					inner join AporteDetallado on apgid = apdaportegeneral
					inner join persona on apdpersona = perid
					where regid = @regid and apgid = @apgid)
				END

		Print @identi 	
--------------Actualizacion campo texto posicion ----------------------------------------------------------------------------------------------------------------------------------


		--Update #IC_Aportes_Det set textoPosicion = planillaN
		--from aporteDetalladoRegistroControlN where asignacion not like '%M%' and planillaAntes = asignacion

		Declare @origen  varchar(16)
		
		Select @origen = (select Distinct planillaAntes from aporteDetalladoRegistroControlN where planillaAntes not in 
		(select planillaN From aporteDetalladoRegistroControlN where redNumeroIdentificacionCotizante = @identi and apdAporteGeneral = @apgid) 
		and redNumeroIdentificacionCotizante = @identi and apdAporteGeneral = @apgid)


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

		if (select @resultado) is null
			begin
				Update #IC_Aportes_Det set textoPosicion = planillaN
				from aporteDetalladoRegistroControlN where asignacion not like '%M%' and planillaAntes = asignacion
			end
			Else 
				begin
				update  #IC_Aportes_Det set textoPosicion = (select @resultado + '\' + @planillaNadp)
				end 


--SELECT STRING_AGG(CONVERT(NVARCHAR(max), planillaN), '\') AS textoposicion 
--		FROM aporteDetalladoRegistroControlN 
--		WHERE redNumeroIdentificacionCotizante = @identi and planillaAntes = '123000789'


------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


		--------------------------------------------------------------------------------------------------------------------------------------------

		IF (SELECT COUNT(claveCont) FROM #IC_Aportes_Det WHERE claveCont = '40') > 1
		BEGIN

			UPDATE #IC_Aportes_Det  SET noIdentificado = '' WHERE  claveCont = '50'  AND claseDeAporte <> ''

		END
		----------------------------------------------------------------------------------------------------------------------------------------------------
		IF (SELECT tipoMovimiento FROM #IC_Aportes_Enc) = 'A03'
		BEGIN
		UPDATE #IC_Aportes_Det SET noIdentificado = '' WHERE claveCont = '50'
		END



			SELECT @referenciaNum = valorActual 
				FROM sap.IC_Referencia 
				WHERE comentario ='A'
				AND estado = 'A';

		--IF @referenciaNum -1 = (select top 1  max(CONVERT(bigint, referencia)) from sap.IC_Aportes_Enc INNER JOIN sap.IC_Referencia ON (CONVERT(bigint, referencia)+1)  = valorActual AND estado = 'A'and comentario ='A')
		--	BEGIN	

				UPDATE #IC_Aportes_Enc
					SET referencia =  @referenciaNum
				
----- comentarear				
						   		
				UPDATE sap.ic_referencia
					SET valorActual = @referenciaNum + 1
					WHERE comentario ='A'
					AND estado = 'A';		
			

--- fin comentareo

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
						[claseCuenta],[usuario],GETDATE()-'05:00:00' 
				FROM #IC_Aportes_Det  WITH(NOLOCK)
				WHERE importeDocumento <> 0  ---  para cuando el registro viene sin detalle y que no traiga lineas en valores 0 08/10/2022
		
	
				-- INSERCIoN EN LA TABLA DE CONTROL DE PLANILLAS

				UPDATE  #IC_Aportes_Det
					SET consecutivo =(SELECT consecutivo FROM #IC_Aportes_Enc)

				INSERT INTO core.sap.PlanillasCtrl
				SELECT DISTINCT LEFT(det.asignacion, 12), 1, det.ref1, det.tipoDocumento, SAP.GetLocalDate(), 'APORTES', det.ref3 + enc.observacion, enc.tipoMovimiento,estadoaporteaportantetercero,operadorinformacion,case when @apgid is null or @apgid = '' then ap.apgid else @apgid end  
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

				DROP TABLE #IC_Aportes_Enc;
				DROP TABLE #IC_Aportes_Det;				
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

	SELECT 'ok ejec',GETDATE() 
	--DELETE FROM	sap.ic_internoA
	--END
	--ELSE BEGIN

	--	SELECT 'El proceso ya se encuentra en ejecucion'
	
	--END
END