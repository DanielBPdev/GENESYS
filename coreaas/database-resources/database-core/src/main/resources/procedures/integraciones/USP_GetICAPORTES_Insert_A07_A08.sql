CREATE OR ALTER   PROCEDURE [sap].[USP_GetICAPORTES_Insert_A07_A08] 
 @planilla VARCHAR(20),	@planillaN VARCHAR(20),	@estadoAporte VARCHAR (20), @apgid BIGINT,	@solNumeroRadicacion VARCHAR (MAX),	
	@solTipoTransaccion VARCHAR (30),	@operadorinformacion INT
AS
BEGIN
SET NOCOUNT ON;

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
			[interes]numeric(21,5) NULL,
			[Tarifa]numeric (7,5) NULL,
			[operadorinformacion] INT NULL,
			[apgid] VARCHAR (20)
			
		)

		CREATE TABLE #Temporal_detalle
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
			[interes]numeric(21,5) NULL,
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

		IF @estadoAporte = 'REGISTRADO'
			BEGIN
			
		-- Validacion recaudo Manual A08
				INSERT INTO #APLICA
				SELECT TOP 1 1 as aplica,  @planilla as planilla, apg1.apgRegistroGeneral  from sap.IC_Aportes_Det det
				INNER JOIN core.sap.IC_Aportes_Enc enc WITH(NOLOCK) ON  det.consecutivo = enc.consecutivo
				INNER JOIN core.dbo.AporteGeneral  apg1 WITH(NOLOCK) ON apg1.apgId = @apgid
				LEFT JOIN persona p on apg1.apgPersona = p.perId
				LEFT JOIN afiliado af on p.perid = af.afiPersona
				LEFT JOIN RolAfiliado ro on /* ro.roaEstadoAfiliado = 'ACTIVO' AND */af.afiId = ro.roaAfiliado
				LEFT JOIN Empresa e on apgEmpresa= e.empId
				LEFT JOIN Empleador em on e.empId = em.empempresa and empEstadoEmpleador = 'ACTIVO' 
				WHERE  asignacion = concat('M', @apgid) and tipoMovimiento in ('A07', 'A34') AND enc.estadoReg = 'S'
				AND apg1.apgEstadoRegistroAporteAportante = 'REGISTRADO' AND apgValorIntMora = 0  --- apgValorIntMora agregado para que no lleve intereses al ser futuro camilo gomez 29-08-2023
				AND (CONCAT (apgperiodoaporte,'-01')) < apgFechaProcesamiento 
		END


		IF @estadoAporte = 'RELACIONADO'
	BEGIN
		-- Validacion recaudo Manual A07
		INSERT INTO #APLICA
				SELECT TOP 1 1 as aplica,  @planilla as planilla, apg1.apgRegistroGeneral  from sap.IC_Aportes_Det det
				LEFT JOIN core.sap.IC_Aportes_Enc enc WITH(NOLOCK) ON  det.consecutivo = enc.consecutivo
				INNER JOIN core.dbo.AporteGeneral  apg1 WITH(NOLOCK) ON apg1.apgId = @apgid
				LEFT JOIN persona p WITH(NOLOCK) on apg1.apgPersona = p.perId
				LEFT JOIN afiliado af WITH(NOLOCK) on p.perid = af.afiPersona
				LEFT JOIN RolAfiliado ro WITH(NOLOCK) on  ro.roaEstadoAfiliado = 'ACTIVO' AND af.afiId = ro.roaAfiliado
				WHERE   apg1.apgEstadoRegistroAporteAportante = 'RELACIONADO' AND apgValorIntMora = 0   --- apgValorIntMora agregado para que no lleve intereses al ser futuro camilo gomez 29-08-2023
				AND ((asignacion = concat('M', @apgid) and tipoMovimiento <> 'A07' AND enc.estadoReg = 'S') OR (tipoMovimiento is null))
			
		  END
		  
	---------------- Validacion si existe  A34 , elimina la integracion del mov A07----------------------

		if (select distinct concat(p.Tipomovimiento, pc.Tipomovimiento) from sap.PlanillasCtrl p
		inner join sap.PlanillasCtrl pc on p.regNumPlanilla = pc.regNumPlanilla
		where p.regNumPlanilla = @planilla and p.Tipomovimiento = 'A34'
		and p.apgEstadoRegistroAporteAportante = @estadoAporte and pc.Tipomovimiento = 'A07') = 'A34A07'
		Begin
		Print 'Ingresa validacion A34'
			set @planilla ='' set @estadoAporte ='' set @operadorinformacion =''
		end
		
		if(select distinct concat(p.Tipomovimiento, pc.Tipomovimiento) from sap.PlanillasCtrl p
		inner join sap.PlanillasCtrl pc on p.regNumPlanilla = pc.regNumPlanilla
		where p.apgid = @apgid and p.Tipomovimiento = 'A34'
		and p.apgEstadoRegistroAporteAportante = @estadoAporte and pc.Tipomovimiento = 'A07') = 'A34A07'
		begin
			delete from #APLICA
		end
--------------------------------Fin Validacion A34-------------------------------------------------------------------------

		IF (@planilla IS NOT NULL OR @planilla <> '')
		BEGIN
		Print 'Ingresa 1prvalidacion'
			INSERT INTO #APLICA
			SELECT 1, @planilla , ''
		END 
	
	
	DELETE #APLICA WHERE Aplica = 0				

	IF (SELECT TOP 1  Planilla FROM #APLICA)  IS NULL OR (SELECT TOP 1  Planilla FROM #APLICA) = '' BEGIN  SELECT @regid = (SELECT TOP 1 regid FROM #APLICA) print @regid END
		IF (SELECT Distinct MAX(Aplica) FROM #APLICA) = 1
		BEGIN
		Print 'Ingresa 2davalidacion'
			IF(SELECT TOP 1 regValTotalApoObligatorio FROM  core.pila.RegistroGeneral WHERE (regnumplanilla = @planilla  AND regOperadorInformacion = @operadorinformacion) OR (regid = @regId AND regid <> 0 ))  > 0 
			BEGIN print 'entra'
				
				----------------------------------------Llenado de las tablas para A07 -------------------------------------------------------------------------------------------------

				IF (SELECT DISTINCT regTipoPlanilla from pila.RegistroGeneral where regNumPlanilla = @planilla ) <> 'N'
					BEGIN

					INSERT INTO #Temporal_detalle
						SELECT 
								ROW_NUMBER() OVER(ORDER BY reg.regId ASC) AS consecutivo,
								'' AS codigoSap, 
								CASE WHEN apd.apdAporteObligatorio is not null then SUM(apd.apdAporteObligatorio) 
								else apg.apgValTotalApoObligatorio	end AS ImporteDocumento,
											--round(ISNULL(apg.apgValTotalApoObligatorio, 0) + ISNULL(reg.regValorIntMora, 0),0) AS ImporteDocumento,
								'' AS operador,			-- Solo aplica para la INTEGRACIoN DE CARTERA
								'40' AS claveCont,
								 ISNULL(reg.regnumplanilla,'')   AS asignacion,
								 '' as textoposicion,
								reg.regNumeroIdentificacionAportante AS ref1,
								'' AS ref2,
								TipoIdHomologado AS tipoDocumento,
								LEFT(reg.regNombreAportante, 20) AS ref3,
								'' AS noIdentificado,
								'' AS adelantado,
								CASE WHEN apg.apgCuentaBancariaRecaudo ='5' THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(cb.NUMERO_CUENTA, '')),'-', '')) 
									 WHEN apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '' THEN REPLACE(cb.NUMERO_CUENTA,'-', '')
									 WHEN (apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo IN ('1','2','3','4') THEN REPLACE(REPLACE(UPPER(ISNULL(NUMERO_CUENTA, '')),'-', ''), '000000', '')
									 WHEN ( apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo = ('5') THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(NUMERO_CUENTA, '')),'-', ''))
									 WHEN apg.apgCodEntidadFinanciera IN('7','')  THEN REPLACE(REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''), '000000', '')
									 WHEN apg.apgCodEntidadFinanciera IN('51','') THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', '')) ELSE apg.apgNumeroCuenta END AS identificadorDelBanco,
								CASE WHEN (apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') and apg.apgCuentaBancariaRecaudo IS NOT NULL AND apg.apgCuentaBancariaRecaudo = 5 THEN ce.codigoEntidad --agregado con tabla de homologacion sap.maestracodigoentidad 31/10/2022
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
								reg.regId as regId,
								apg.apgFechaRecaudo AS fechaRecaudo, 
								CASE WHEN [SAP].[calcularPrimerDiaHabilMes]() = [SAP].[calcularSiguienteDiaHabil]() AND DAY(DATEADD(HOUR,-5,GETDATE())) >= [SAP].[calcularPrimerDiaHabilMes]() AND DATEDIFF(DAY, apg.apgFechaRecaudo, DATEADD(HOUR,-5,GETDATE())) > 0
									THEN EOMONTH(DATEADD(HOUR,-5,GETDATE()),-1)
									ELSE DATEADD(HOUR,-5,GETDATE()) END AS fechaProcesamiento,
								apg.apgEstadoRegistroAporteAportante AS EstadoRegistroAporteAportante,
								apg.apgTipoSolicitante AS TipoSolicitante,
								CASE WHEN apd.apdValorIntMora is not null then apd.apdValorIntMora 
								else apg.apgValorIntMora end AS interes ,
								apd.apdTarifa AS Tarifa,
								reg.regOperadorInformacion AS [operadorinformacion],
								apg.apgId AS apgid
							FROM core.pila.RegistroGeneral reg
							INNER JOIN core.sap.MaestraTiposIdentificacion mai ON reg.regTipoIdentificacionAportante = mai.TipoIdGenesys
							INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) on reg.regId = apgRegistroGeneral
							INNER JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgId = apd.apdAporteGeneral	
							LEFT JOIN core.dbo.CUENTAS_BANCARIAS cb WITH(NOLOCK) on apg.apgCuentaBancariaRecaudo = cb.ID 
							LEFT JOIN core.sap.MaestracodigoEntidad ce WITH(NOLOCK) on cb.ID = ce.idCuentasBancarias
							LEFT JOIN persona p WITH(NOLOCK) on apg.apgPersona = p.perId
							LEFT JOIN afiliado af WITH(NOLOCK) on p.perid = af.afiPersona
							LEFT JOIN RolAfiliado ro WITH(NOLOCK) on  af.afiId = ro.roaAfiliado  AND apdTipoCotizante = roaTipoAfiliado	
							LEFT JOIN Empresa e WITH(NOLOCK)on apgEmpresa= e.empId
							LEFT JOIN Empleador em WITH(NOLOCK) on e.empId = em.empempresa

							WHERE  apg.apgEstadoRegistroAporteAportante = 'RELACIONADO' AND (ro.roaEstadoAfiliado IN ('ACTIVO') OR empEstadoEmpleador ='ACTIVO')
							AND (reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @operadorinformacion) 
							OR (reg.regid = @regId AND reg.regid <> 0 AND apg.apgid = @apgid )
							AND apdEstadoRegistroAporteArchivo = 'OK'  AND (apd.apdAporteObligatorio <> 0 AND apd.apdTarifa <> 0) 
						
							GROUP BY reg.regId, reg.regnumplanilla,reg.regOperadorInformacion,reg.regNumeroIdentificacionAportante,TipoIdHomologado,reg.regNombreAportante,apg.apgNumeroCuenta,
							cb.NUMERO_CUENTA,apg.apgCuentaBancariaRecaudo,apg.apgCodEntidadFinanciera,apg.apgModalidadRecaudoAporte,apg.apgFechaRecaudo,apg.apgEstadoRegistroAporteAportante,
							apg.apgTipoSolicitante,reg.regValorIntMora,apd.apdTarifa,ce.codigoEntidad, apd.apdMarcaPeriodo,apg.apgId,apd.apdValorIntMora,apd.apdAporteObligatorio,apg.apgValTotalApoObligatorio,apg.apgValorIntMora
							
						END
						ELSE 
						BEGIN

							INSERT INTO #Temporal_detalle
							SELECT 
								ROW_NUMBER() OVER(ORDER BY reg.regId ASC) AS consecutivo,
								'' AS codigoSap, 
								sum(diferenciaAporteCot) AS ImporteDocumento,
								'' AS operador,			-- Solo aplica para la INTEGRACIoN DE CARTERA
								'40' AS claveCont,
								 ISNULL(reg.regnumplanilla,'')   AS asignacion,
								 planillaAntes as textoposicion,
								reg.regNumeroIdentificacionAportante AS ref1,
								'' AS ref2,
								TipoIdHomologado AS tipoDocumento,
								LEFT(reg.regNombreAportante, 20) AS ref3,
								'' AS noIdentificado,
								'' AS adelantado,
								CASE WHEN apg.apgCuentaBancariaRecaudo ='5' THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(cb.NUMERO_CUENTA, '')),'-', '')) 
									 WHEN apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '' THEN REPLACE(cb.NUMERO_CUENTA,'-', '')
									 WHEN (apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo IN ('1','2','3','4') THEN REPLACE(REPLACE(UPPER(ISNULL(NUMERO_CUENTA, '')),'-', ''), '000000', '')
									 WHEN ( apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo = ('5') THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(NUMERO_CUENTA, '')),'-', ''))
									 WHEN apg.apgCodEntidadFinanciera IN('7','')  THEN REPLACE(REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''), '000000', '')
									 WHEN apg.apgCodEntidadFinanciera IN('51','') THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', '')) ELSE apg.apgNumeroCuenta END AS identificadorDelBanco,
								CASE WHEN (apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') and apg.apgCuentaBancariaRecaudo IS NOT NULL AND apg.apgCuentaBancariaRecaudo = 5 THEN ce.codigoEntidad --agregado con tabla de homologacion sap.maestracodigoentidad 31/10/2022
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
								reg.regId as regId,
								apg.apgFechaRecaudo AS fechaRecaudo, 
								CASE WHEN [SAP].[calcularPrimerDiaHabilMes]() = [SAP].[calcularSiguienteDiaHabil]() AND DAY(DATEADD(HOUR,-5,GETDATE())) >= [SAP].[calcularPrimerDiaHabilMes]() AND DATEDIFF(DAY, apg.apgFechaRecaudo, DATEADD(HOUR,-5,GETDATE())) > 0
									THEN EOMONTH(DATEADD(HOUR,-5,GETDATE()),-1)
									ELSE DATEADD(HOUR,-5,GETDATE()) END AS fechaProcesamiento,
								apg.apgEstadoRegistroAporteAportante AS EstadoRegistroAporteAportante,
								apg.apgTipoSolicitante AS TipoSolicitante,
								diferenciaMora  AS interes ,
								apdn.apdTarifa AS Tarifa,
								reg.regOperadorInformacion AS [operadorinformacion],
								apg.apgId AS apgid
							FROM core.pila.RegistroGeneral reg
							INNER JOIN core.sap.MaestraTiposIdentificacion mai ON reg.regTipoIdentificacionAportante = mai.TipoIdGenesys
							LEFT  JOIN core.dbo.aporteDetalladoRegistroControlN	apdn  WITH(NOLOCK) ON reg.regId = apdn.registroGeneralNuevo
							INNER  JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) on apdn.apdAporteGeneral = apg.apgId
							INNER JOIN core.dbo.aportedetallado  apd WITH(NOLOCK) on apg.apgId = apd.apdAporteGeneral
							LEFT JOIN core.dbo.CUENTAS_BANCARIAS cb WITH(NOLOCK) on apg.apgCuentaBancariaRecaudo = cb.ID 
							LEFT JOIN core.sap.MaestracodigoEntidad ce WITH(NOLOCK) on cb.ID = ce.idCuentasBancarias
							LEFT JOIN persona p WITH(NOLOCK) on apg.apgPersona = p.perId
							LEFT JOIN afiliado af WITH(NOLOCK) on p.perid = af.afiPersona
							LEFT JOIN RolAfiliado ro WITH(NOLOCK) on  af.afiId = ro.roaAfiliado  AND apdTipoCotizante = roaTipoAfiliado	
							LEFT JOIN Empresa e WITH(NOLOCK)on apgEmpresa= e.empId
							LEFT JOIN Empleador em WITH(NOLOCK) on e.empId = em.empempresa
							
							WHERE  apg.apgEstadoRegistroAporteAportante = 'RELACIONADO' AND (ro.roaEstadoAfiliado IN ('ACTIVO') OR empEstadoEmpleador ='ACTIVO')
							AND (reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @operadorinformacion) 
							OR (reg.regid = @regId AND reg.regid <> 0 AND apg.apgid = @apgid)
							AND (diferenciaAporteCot <> 0 AND apdn.apdTarifa <> 0) 										
							GROUP BY reg.regId, reg.regnumplanilla,reg.regOperadorInformacion,reg.regNumeroIdentificacionAportante,TipoIdHomologado,reg.regNombreAportante,apg.apgNumeroCuenta,
							cb.NUMERO_CUENTA,apg.apgCuentaBancariaRecaudo,apg.apgCodEntidadFinanciera,apg.apgModalidadRecaudoAporte,apg.apgFechaRecaudo,apg.apgEstadoRegistroAporteAportante,
							apg.apgTipoSolicitante,reg.regValorIntMora,apdn.apdTarifa,ce.codigoEntidad, apg.apgperiodoaporte,apg.apgId,apdn.diferenciaMora,apg.apgValTotalApoObligatorio,apg.apgValorIntMora,
							planillaAntes

							SELECT apdAporteGeneral, planillaN 
							INTO #apgidNA07
							from aporteDetalladoRegistroControlN
							where planillaN = @planillaN

					--------------------------------------Validacion si integro planilla original ------------------------
							IF (SELECT TOP 1 d.asignacion FROM #Temporal_detalle  t
								inner join sap.IC_Aportes_Det d on t.textoposicion = d.asignacion) =''
								BEGIN
									
										INSERT INTO sap.PlanillasCtrl
										SELECT DISTINCT LEFT(det.asignacion, 12), 1, det.ref1, det.tipoDocumento, SAP.GetLocalDate(), 'APORTES', 
										det.ref3 + 'Planilla Original No integrada', 'A07',@estadoaporte,operadorinformacion,apn.apdaportegeneral
										FROM #Temporal_detalle  det WITH(NOLOCK)
										inner join #apgidNA07 apn on det.asignacion = apn.planillaN
									

									DELETE FROM #Temporal_detalle 
								END
							
					------------------------------------------------Fin---------------------------------------------------	

						END
		------------------------------------------Fin llenado detalle para A07---------------------------------------------------------------------------------
		
		------------------------------------------------------------------------------------------------------------------------------------------------------------------------

				----------------------------------------Llenado de las tablas para A08 -------------------------------------------------------------------------------------------------
				IF (SELECT DISTINCT regTipoPlanilla from pila.RegistroGeneral where regNumPlanilla = @planilla ) <> 'N'
									BEGIN
				
									INSERT INTO #Temporal_detalle
										SELECT 
												ROW_NUMBER() OVER(ORDER BY reg.regId ASC) AS consecutivo,
												'' AS codigoSap, 
												CASE WHEN apd.apdAporteObligatorio is not null then SUM(apd.apdAporteObligatorio) 
												else apg.apgValTotalApoObligatorio	end AS ImporteDocumento,
															--round(ISNULL(apg.apgValTotalApoObligatorio, 0) + ISNULL(reg.regValorIntMora, 0),0) AS ImporteDocumento,
												'' AS operador,			-- Solo aplica para la INTEGRACIoN DE CARTERA
												'40' AS claveCont,
												 ISNULL(reg.regnumplanilla,'')   AS asignacion,
												 '' as textoposicion,
												reg.regNumeroIdentificacionAportante AS ref1,
												'' AS ref2,
												TipoIdHomologado AS tipoDocumento,
												LEFT(reg.regNombreAportante, 20) AS ref3,
												'' AS noIdentificado,
												'' AS adelantado,
												CASE WHEN apg.apgCuentaBancariaRecaudo ='5' THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(cb.NUMERO_CUENTA, '')),'-', '')) 
													 WHEN apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '' THEN REPLACE(cb.NUMERO_CUENTA,'-', '')
													 WHEN (apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo IN ('1','2','3','4') THEN REPLACE(REPLACE(UPPER(ISNULL(NUMERO_CUENTA, '')),'-', ''), '000000', '')
													 WHEN ( apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo = ('5') THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(NUMERO_CUENTA, '')),'-', ''))
													 WHEN apg.apgCodEntidadFinanciera IN('7','')  THEN REPLACE(REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''), '000000', '')
													 WHEN apg.apgCodEntidadFinanciera IN('51','') THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', '')) ELSE apg.apgNumeroCuenta END AS identificadorDelBanco,
												CASE WHEN (apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') and apg.apgCuentaBancariaRecaudo IS NOT NULL AND apg.apgCuentaBancariaRecaudo = 5 THEN ce.codigoEntidad --agregado con tabla de homologacion sap.maestracodigoentidad 31/10/2022
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
												reg.regId as regId,
												apg.apgFechaRecaudo AS fechaRecaudo, 
												CASE WHEN [SAP].[calcularPrimerDiaHabilMes]() = [SAP].[calcularSiguienteDiaHabil]() AND DAY(DATEADD(HOUR,-5,GETDATE())) >= [SAP].[calcularPrimerDiaHabilMes]() AND DATEDIFF(DAY, apg.apgFechaRecaudo, DATEADD(HOUR,-5,GETDATE())) > 0
													THEN EOMONTH(DATEADD(HOUR,-5,GETDATE()),-1)
													ELSE DATEADD(HOUR,-5,GETDATE()) END AS fechaProcesamiento,
												apg.apgEstadoRegistroAporteAportante AS EstadoRegistroAporteAportante,
												apg.apgTipoSolicitante AS TipoSolicitante,
												CASE WHEN apd.apdValorIntMora is not null then apd.apdValorIntMora 
												else apg.apgValorIntMora end AS interes ,
												apd.apdTarifa AS Tarifa,
												reg.regOperadorInformacion AS [operadorinformacion],
												apg.apgId AS apgid
											FROM core.pila.RegistroGeneral reg
											INNER JOIN core.sap.MaestraTiposIdentificacion mai ON reg.regTipoIdentificacionAportante = mai.TipoIdGenesys
											INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) on reg.regId = apgRegistroGeneral
											INNER JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgId = apd.apdAporteGeneral	
											LEFT JOIN core.dbo.CUENTAS_BANCARIAS cb WITH(NOLOCK) on apg.apgCuentaBancariaRecaudo = cb.ID 
											LEFT JOIN core.sap.MaestracodigoEntidad ce WITH(NOLOCK) on cb.ID = ce.idCuentasBancarias
											LEFT JOIN persona p WITH(NOLOCK) on apg.apgPersona = p.perId
											LEFT JOIN afiliado af WITH(NOLOCK) on p.perid = af.afiPersona
											LEFT JOIN RolAfiliado ro WITH(NOLOCK) on  af.afiId = ro.roaAfiliado  AND apdTipoCotizante = roaTipoAfiliado	
											LEFT JOIN Empresa e WITH(NOLOCK)on apgEmpresa= e.empId
											LEFT JOIN Empleador em WITH(NOLOCK) on e.empId = em.empempresa
				
											WHERE  apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' --AND (ro.roaEstadoAfiliado IN ('ACTIVO') OR empEstadoEmpleador ='ACTIVO')
											AND (reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @operadorinformacion) 
											OR (reg.regid = @regId AND reg.regid <> 0 AND apg.apgid = @apgid )
											AND apdEstadoRegistroAporteArchivo = 'OK'  AND (apd.apdAporteObligatorio <> 0 AND apd.apdTarifa <> 0) 
										
											GROUP BY reg.regId, reg.regnumplanilla,reg.regOperadorInformacion,reg.regNumeroIdentificacionAportante,TipoIdHomologado,reg.regNombreAportante,apg.apgNumeroCuenta,
											cb.NUMERO_CUENTA,apg.apgCuentaBancariaRecaudo,apg.apgCodEntidadFinanciera,apg.apgModalidadRecaudoAporte,apg.apgFechaRecaudo,apg.apgEstadoRegistroAporteAportante,
											apg.apgTipoSolicitante,reg.regValorIntMora,apd.apdTarifa,ce.codigoEntidad, apd.apdMarcaPeriodo,apg.apgId,apd.apdValorIntMora,apd.apdAporteObligatorio,apg.apgValTotalApoObligatorio,apg.apgValorIntMora
											
										END
										ELSE 
										BEGIN
				
											INSERT INTO #Temporal_detalle
											SELECT 
												ROW_NUMBER() OVER(ORDER BY reg.regId ASC) AS consecutivo,
												'' AS codigoSap, 
												sum(diferenciaAporteCot) AS ImporteDocumento,
												'' AS operador,			-- Solo aplica para la INTEGRACIoN DE CARTERA
												'40' AS claveCont,
												 ISNULL(reg.regnumplanilla,'')   AS asignacion,
												 planillaAntes as textoposicion,
												reg.regNumeroIdentificacionAportante AS ref1,
												'' AS ref2,
												TipoIdHomologado AS tipoDocumento,
												LEFT(reg.regNombreAportante, 20) AS ref3,
												'' AS noIdentificado,
												'' AS adelantado,
												CASE WHEN apg.apgCuentaBancariaRecaudo ='5' THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(cb.NUMERO_CUENTA, '')),'-', '')) 
													 WHEN apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '' THEN REPLACE(cb.NUMERO_CUENTA,'-', '')
													 WHEN (apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo IN ('1','2','3','4') THEN REPLACE(REPLACE(UPPER(ISNULL(NUMERO_CUENTA, '')),'-', ''), '000000', '')
													 WHEN ( apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') AND apg.apgCuentaBancariaRecaudo = ('5') THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(NUMERO_CUENTA, '')),'-', ''))
													 WHEN apg.apgCodEntidadFinanciera IN('7','')  THEN REPLACE(REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', ''), '000000', '')
													 WHEN apg.apgCodEntidadFinanciera IN('51','') THEN CONCAT('0000000', REPLACE(UPPER(ISNULL(apg.apgNumeroCuenta, '')),'-', '')) ELSE apg.apgNumeroCuenta END AS identificadorDelBanco,
												CASE WHEN (apg.apgNumeroCuenta IS NULL OR apg.apgNumeroCuenta = '') and apg.apgCuentaBancariaRecaudo IS NOT NULL AND apg.apgCuentaBancariaRecaudo = 5 THEN ce.codigoEntidad --agregado con tabla de homologacion sap.maestracodigoentidad 31/10/2022
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
												reg.regId as regId,
												apg.apgFechaRecaudo AS fechaRecaudo, 
												CASE WHEN [SAP].[calcularPrimerDiaHabilMes]() = [SAP].[calcularSiguienteDiaHabil]() AND DAY(DATEADD(HOUR,-5,GETDATE())) >= [SAP].[calcularPrimerDiaHabilMes]() AND DATEDIFF(DAY, apg.apgFechaRecaudo, DATEADD(HOUR,-5,GETDATE())) > 0
													THEN EOMONTH(DATEADD(HOUR,-5,GETDATE()),-1)
													ELSE DATEADD(HOUR,-5,GETDATE()) END AS fechaProcesamiento,
												apg.apgEstadoRegistroAporteAportante AS EstadoRegistroAporteAportante,
												apg.apgTipoSolicitante AS TipoSolicitante,
												diferenciaMora  AS interes ,
												apdn.apdTarifa AS Tarifa,
												reg.regOperadorInformacion AS [operadorinformacion],
												apg.apgId AS apgid
											FROM core.pila.RegistroGeneral reg
											INNER JOIN core.sap.MaestraTiposIdentificacion mai ON reg.regTipoIdentificacionAportante = mai.TipoIdGenesys
											LEFT  JOIN core.dbo.aporteDetalladoRegistroControlN	apdn  WITH(NOLOCK) ON reg.regId = apdn.registroGeneralNuevo
											INNER  JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) on apdn.apdAporteGeneral = apg.apgId
											INNER JOIN core.dbo.aportedetallado  apd WITH(NOLOCK) on apg.apgId = apd.apdAporteGeneral
											LEFT JOIN core.dbo.CUENTAS_BANCARIAS cb WITH(NOLOCK) on apg.apgCuentaBancariaRecaudo = cb.ID 
											LEFT JOIN core.sap.MaestracodigoEntidad ce WITH(NOLOCK) on cb.ID = ce.idCuentasBancarias
											LEFT JOIN persona p WITH(NOLOCK) on apg.apgPersona = p.perId
											LEFT JOIN afiliado af WITH(NOLOCK) on p.perid = af.afiPersona
											LEFT JOIN RolAfiliado ro WITH(NOLOCK) on  af.afiId = ro.roaAfiliado  AND apdTipoCotizante = roaTipoAfiliado	
											LEFT JOIN Empresa e WITH(NOLOCK)on apgEmpresa= e.empId
											LEFT JOIN Empleador em WITH(NOLOCK) on e.empId = em.empempresa
											
											WHERE  apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' --AND (ro.roaEstadoAfiliado IN ('ACTIVO') OR empEstadoEmpleador ='ACTIVO')
											AND (reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @operadorinformacion) 
											OR (reg.regid = @regId AND reg.regid <> 0 AND apg.apgid = @apgid)
											AND (diferenciaAporteCot <> 0 AND apdn.apdTarifa <> 0) 										
											GROUP BY reg.regId, reg.regnumplanilla,reg.regOperadorInformacion,reg.regNumeroIdentificacionAportante,TipoIdHomologado,reg.regNombreAportante,apg.apgNumeroCuenta,
											cb.NUMERO_CUENTA,apg.apgCuentaBancariaRecaudo,apg.apgCodEntidadFinanciera,apg.apgModalidadRecaudoAporte,apg.apgFechaRecaudo,apg.apgEstadoRegistroAporteAportante,
											apg.apgTipoSolicitante,reg.regValorIntMora,apdn.apdTarifa,ce.codigoEntidad, apg.apgperiodoaporte,apg.apgId,apdn.diferenciaMora,apg.apgValTotalApoObligatorio,apg.apgValorIntMora,
											planillaAntes
				
											SELECT apdAporteGeneral, planillaN 
											INTO #apgidNA08
											from aporteDetalladoRegistroControlN
											where planillaN = @planillaN
				
									--------------------------------------Validacion si integro planilla original ------------------------
											IF (SELECT TOP 1 d.asignacion FROM #Temporal_detalle  t
												inner join sap.IC_Aportes_Det d on t.textoposicion = d.asignacion) =''
												BEGIN
													
														INSERT INTO sap.PlanillasCtrl
														SELECT DISTINCT LEFT(det.asignacion, 12), 1, det.ref1, det.tipoDocumento, SAP.GetLocalDate(), 'APORTES', 
														det.ref3 + 'Planilla Original No integrada', 'A07',@estadoaporte,operadorinformacion,apn.apdaportegeneral
														FROM #Temporal_detalle  det WITH(NOLOCK)
														inner join #apgidNA08 apn on det.asignacion = apn.planillaN
													
				
													DELETE FROM #Temporal_detalle 
												END
											
									------------------------------------------------Fin---------------------------------------------------	
				
										END


------------------------------------------------------------------------------------------------------------------------------------------------------------------------


			
			---------------------- Actualizacion de campo importe y tarfia cuando se da el A12 cambio de tarifa ----------------------------------------

					update #Temporal_detalle  set importeDocumento = A12.valornuevo, Tarifa = A12.tarifanueva 
					from(select Distinct apd1.apdTarifa,apd2.apdTarifa as tarifanueva,apg2.apgId as nuevo, apg1.apgId as old,apg2.apgValTotalApoObligatorio as valornuevo from AporteGeneral apg1 
								inner join AporteDetallado apd1 on apg1.apgId = apd1.apdAporteGeneral
								inner join Correccion cor on apd1.apdId = cor.corAporteDetallado
								inner join AporteGeneral apg2 on cor.corAporteGeneral = apg2.apgId
								inner join AporteDetallado apd2 on apg2.apgId = apd2.apdAporteGeneral
								inner join  #Temporal_detalle td on apg1.apgId = td.apgid
								WHERE  apg1.apgEstadoAporteAportante = 'ANULADO' and apg2.apgEstadoAporteAportante = 'VIGENTE'
									AND apg2.apgOrigenAporte = 'CORRECCION_APORTE' and apd1.apdTarifa <> apd2.apdTarifa
									AND apg1.apgId = td.apgid
									) as A12 
						Inner join #Temporal_detalle tdp on A12.old = tdp.apgid

	

				--La tabla para el detalle inicialmente es #Temporal_detalle pero se cambia para el proceso de los apgid 
					------------------------------------------------------------------------------------------------------------------------------------------
					--Detectar apgid a integrar
						SELECT apgid,operadorinformacion
						INTO #Integrados
							FROM #Temporal_detalle 
							EXCEPT SELECT DISTINCT ct.apgid,ct.operadorinformacion FROM #TEMPORAL_DETALLE dt
								left join sap.PlanillasCtrl ct ON dt.apgid = ct.apgid and (dt.asignacion = ct.regNumPlanilla)
								left join sap.IC_Aportes_Enc e ON ct.Tipomovimiento = e.tipoMovimiento
								WHERE (dt.asignacion = @planilla OR dt.asignacion = CONCAT('M', @apgid))  AND (ct.Tipomovimiento <> 'A05' OR ct.Tipomovimiento <> 'A07' OR ct.Tipomovimiento <> 'A34')
								And (ct.apgEstadoRegistroAporteAportante = @estadoAporte )

					--select * from #integrados

					-- Actualizacion A08 cuando exista un mov A05, DEBE ELIMINARLO
						
						update #Integrados set operadorinformacion = '0'
						from #Integrados b
						inner join sap.PlanillasCtrl ct on  b.apgId = ct.apgid
						Where ct.Tipomovimiento ='A05' 

						update #Integrados set operadorinformacion = '1'
						from #Integrados b
						inner join sap.PlanillasCtrl ct on  b.apgId = ct.apgid
						Where ct.Tipomovimiento = 'A34'

						Delete from  #Integrados where operadorinformacion = '0'

						INSERT INTO #IC_Aportes_Det 
						SELECT d.* FROM #Temporal_detalle d
						inner join #integrados i ON d.apgid = i.apgid

						UPDATE	#IC_Aportes_Det SET asignacion = CONCAT('M', @apgid) WHERE asignacion = ''
						DELETE #IC_Aportes_Det WHERE importeDocumento = 0 AND Tarifa = 0

						SELECT apgid, asignacion 
						INTO #apgid
						FROM  #IC_Aportes_Det WHERE importeDocumento > 0 and claveCont = '40'
					-------------------------------------------------------------------------------------------------------------------------------------------

					--select * From #apgid

					--RECAUDO MANUAL
					IF (SELECT top 1 Planilla FROM #APLICA ) IS NULL 
					BEGIN 
						DELETE #IC_Aportes_Det WHERE apgid <> @apgid
					END

					--select * From #IC_Aportes_Det	
	
			-- PLANILLA
			


			----  Agrupar Valores
			IF (SELECT TOP 1 Planilla FROM #APLICA) IS NOT NULL 
			BEGIN			 


				UPDATE #IC_Aportes_Det  SET  consecutivo = 3 	
			
				INSERT INTO #IC_Aportes_Det
				SELECT ROW_NUMBER() OVER(ORDER BY regId ASC) AS Consecutivo, [codigoSap], SUM (CONVERT (INT,([ImporteDocumento]))), [operador], '40' [claveCont], [asignacion],[textoposicion], [ref1], [ref2], [tipoDocumento], [ref3], [noIdentificado],
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
			
						INSERT INTO #IC_Aportes_Det 
						SELECT [consecutivo], [codigoSap], [ImporteDocumento], [operador], '50' [claveCont], [asignacion],[textoposicion], [ref1], [ref2], [tipoDocumento], [ref3], [noIdentificado],
						[adelantado] , '', '', '', [claseDeAporte], [claseDeAportePrescripcion], [tieneIntereses], [tipoInteres], [correccion],
						[tipoMora], [id], [codigoGenesys], [tipo], [claseCuenta], [usuario], [regId], [fechaRecaudo],[fechaProcesamiento], [EstadoRegistroAporteAportante], 
						[TipoSolicitante], [interes], [Tarifa],[operadorinformacion],''
						FROM #IC_Aportes_Det  det
				
					if (select Distinct regTipoPlanilla from pila.RegistroGeneral where regNumPlanilla = @planilla ) <> 'N'
					BEGIN														 
						--Actualizacion de campos clave  50 para movimiento  A07
						UPDATE #IC_Aportes_Det  SET  adelantado = CASE WHEN apd.apdMarcaPeriodo = 'PERIODO_FUTURO' AND	TipoSolicitante = 'EMPLEADOR' AND (Tarifa = 0.00 OR Tarifa = 0.04 OR Tarifa = 0.02 OR Tarifa = 0.01 or Tarifa = 0.03) THEN 'A1'
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
														FROM core.pila.RegistroGeneral reg
							INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) on regId = apgRegistroGeneral
							LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgId = apd.apdAporteGeneral
							LEFT JOIN #APLICA apli WITH (NOLOCK) ON reg.regnumplanilla = Planilla OR apli.regid = @regId
							WHERE apgEstadoRegistroAporteAportante ='RELACIONADO' AND  claveCont = '50'  AND ((reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @OperadorInformacion) OR (reg.regid = @regid AND apg.apgId = @apgid))
			
				END
				ELSE
				BEGIN
					UPDATE #IC_Aportes_Det  SET  adelantado = CASE WHEN apd.apdMarcaPeriodo = 'PERIODO_FUTURO' AND	TipoSolicitante = 'EMPLEADOR' AND (Tarifa = 0.00 OR Tarifa = 0.04 OR Tarifa = 0.02 OR Tarifa = 0.01 or Tarifa = 0.03) THEN 'A1'
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
														FROM core.pila.RegistroGeneral reg
							INNER JOIN core.dbo.aporteDetalladoRegistroControlN	apdn  WITH(NOLOCK) ON reg.regId = apdn.registroGeneralNuevo
							INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) on apdn.apdAporteGeneral = apg.apgId
							LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgId = apd.apdAporteGeneral
							LEFT JOIN #APLICA apli WITH (NOLOCK) ON reg.regnumplanilla = Planilla OR apli.regid = @regId
							WHERE apgEstadoRegistroAporteAportante ='RELACIONADO' AND  claveCont = '50'  AND ((reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @OperadorInformacion) OR (reg.regid = @regid AND apg.apgId = @apgid))

				END
							--Actualizacion de campos clave  50 para movimiento  A08	
						UPDATE #IC_Aportes_Det    SET  claseDeAportePrescripcion = (CASE WHEN apg.apgFormaReconocimientoAporte = 'RECONOCIMIENTO_RETROACTIVO_MANUAL' AND apg.apgEstadoRegistroAporteAportante = 'OTROS_INGRESOS' THEN '1' ELSE '2' END ),
													   claseDeAporte = (CASE  WHEN apg.apgTipoSolicitante = 'EMPLEADOR' AND (Tarifa = 0.00 OR Tarifa = 0.04)  THEN 'CA1'
																			  WHEN TipoSolicitante = 'EMPLEADOR' AND (Tarifa is null)  THEN 'CA1' ---modificado para cuando es recaudo a nivel de aportante y no trae detalle 08/10/2022
																			  WHEN TipoSolicitante = 'EMPLEADOR' AND Tarifa < 0.04 THEN 'CA1'
																			  WHEN TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.02 OR Tarifa = 0.04) THEN 'CA2'
																			  WHEN TipoSolicitante = 'PENSIONADO' AND Tarifa <> 0.006 THEN 'CA3'
																			  WHEN TipoSolicitante = 'PENSIONADO' AND Tarifa = 0.006 THEN 'CA4'
																			  WHEN TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.006 OR Tarifa = 0.01) THEN 'CA5'
																			  ELSE ''
																		END),
													EstadoRegistroAporteAportante = apg.apgEstadoRegistroAporteAportante
													FROM core.pila.RegistroGeneral reg
							INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) on regId = apgRegistroGeneral
							LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgId = apd.apdAporteGeneral
							LEFT JOIN #APLICA apli WITH (NOLOCK) ON reg.regnumplanilla = Planilla OR apli.regid = @regId 
							WHERE  apgEstadoRegistroAporteAportante ='REGISTRADO' AND Aplica = 1 AND claveCont = '50' AND ((reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @OperadorInformacion) OR (reg.regid = @regid AND apg.apgId = @apgid ))
			

						------------------------------------------------Actualizacion de campos clave  40 para movimiento  A08-------------

							UPDATE #IC_Aportes_Det  SET	adelantado = CASE WHEN apd.apdMarcaPeriodo = 'PERIODO_FUTURO' AND TipoSolicitante = 'EMPLEADOR' AND (Tarifa = 0.00 OR Tarifa = 0.04 OR Tarifa = 0.02 OR Tarifa = 0.01 or Tarifa = 0.03) THEN 'A1'
														 WHEN apd.apdMarcaPeriodo = 'PERIODO_FUTURO' AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.02 OR Tarifa = 0.04) THEN 'A2'   
														 WHEN apd.apdMarcaPeriodo = 'PERIODO_FUTURO' AND TipoSolicitante = 'PENSIONADO' AND Tarifa <> 0.006 THEN 'A3'
														 WHEN apd.apdMarcaPeriodo = 'PERIODO_FUTURO' AND TipoSolicitante = 'INDEPENDIENTE' AND (Tarifa = 0.006 OR Tarifa = 0.01) THEN 'A4'
														 WHEN apd.apdMarcaPeriodo = 'PERIODO_FUTURO' AND TipoSolicitante = 'PENSIONADO' AND Tarifa = 0.006 THEN 'A5'
														 ELSE '**' 	END,
														 identificadorDelBanco = '',
														 codigoBanco = '',
														 transitoria = ''
													FROM core.pila.RegistroGeneral reg
							INNER JOIN core.dbo.AporteGeneral  apg WITH(NOLOCK) on regId = apgRegistroGeneral
							LEFT JOIN core.dbo.AporteDetallado apd WITH(NOLOCK) ON apg.apgId = apd.apdAporteGeneral
							LEFT JOIN #APLICA apli WITH (NOLOCK) ON reg.regnumplanilla = Planilla OR apli.regid = @regId 
							WHERE apgEstadoRegistroAporteAportante ='REGISTRADO'   AND Aplica = 1 AND claveCont = '40' AND ((reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @OperadorInformacion) OR (reg.regid = @regid AND apg.apgId = @apgid))
			

		

						UPDATE #IC_Aportes_Det SET	codigoGenesys = per.perId,	tipo = CASE WHEN tipoDocumento = 'NIT' THEN 'E'	ELSE 'P' END								
						FROM core.pila.RegistroGeneral reg
							INNER JOIN core.dbo.Persona per WITH(NOLOCK) ON reg.regNumeroIdentificacionAportante = per.perNumeroIdentificacion
							WHERE (reg.regnumplanilla = @planilla AND reg.regOperadorInformacion = @OperadorInformacion) OR (reg.regid = @regId AND reg.regid <> 0 )

						IF (SELECT top 1 Planilla FROM #APLICA) IS NULL
						BEGIN
						UPDATE #IC_Aportes_Det SET	codigoGenesys = apg.apgPersona,	tipo = CASE WHEN tipoDocumento = 'NIT' THEN 'E'	ELSE 'P' END								
							FROM dbo.AporteGeneral  apg
							WHERE apg.apgId = @apgid
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
								CASE  WHEN det.EstadoRegistroAporteAportante = 'RELACIONADO' THEN 'A07' WHEN det.EstadoRegistroAporteAportante = 'REGISTRADO' THEN 'A08' ELSE '' END  AS tipoMovimiento,
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
				
				
					----------------------------------- Completar campos para recaudo manual--------------------------------------------------------------------
					IF(SELECT tipoMovimiento  FROM #IC_Aportes_Enc ) = 'A07' BEGIN	UPDATE #IC_Aportes_Det  SET adelantado = '' WHERE claveCont = '40' END 

					

					IF (SELECT tipoMovimiento  FROM #IC_Aportes_Enc  ) = 'A08' 
					BEGIN 
						UPDATE #IC_Aportes_Det SET claseDeAporte = '' , claseDeAportePrescripcion = '' WHERE claveCont = '40'  
						UPDATE #IC_Aportes_Det SET adelantado = '' WHERE claveCont = '50'  
						UPDATE	#IC_Aportes_Det SET asignacion = CONCAT('M', @apgid) WHERE asignacion = ''
					END

				
				IF (SELECT COUNT(claveCont) FROM #IC_Aportes_Det WHERE claveCont = '40') > 1
				BEGIN


					Declare @A1 Varchar (2)
					Declare @A2	Varchar (2)
					Declare @import NUMERIC(21,5)

						Select  @A1 = (Select adelantado from #IC_Aportes_Det WHERE clavecont = '40' and consecutivo = (SELECT  MAX(consecutivo) FROM  #IC_Aportes_Det))   -- agregado para A09 que tiene diferente NI 10/11/2023 Camilo GOmez 
						Select  @A2 = (Select adelantado from #IC_Aportes_Det WHERE clavecont = '40' and consecutivo = (SELECT  MIN(consecutivo) FROM  #IC_Aportes_Det))   -- agregado para A09 que tiene diferente NI 10/11/2023 Camilo GOmez 
						Select	@import = (select SUM(importeDocumento)from #IC_Aportes_Det where claveCont = '40')

					DELETE #IC_Aportes_Det WHERE @A1 = @A2 AND claveCont = '40'  AND consecutivo = (SELECT  MAX(consecutivo) FROM  #IC_Aportes_Det)
					UPDATE #IC_Aportes_Det SET  consecutivo = (SELECT  MIN(consecutivo) FROM  #IC_Aportes_Det)
					UPDATE #IC_Aportes_Det SET  importeDocumento = CASE WHEN @A1 = @A2 THEN @import Else importedocumento  END from #IC_Aportes_Det WHERE claveCont = '40' 
					
					
																	 
																	
				END
----------------------------------------------------------------------------------------------------------------------------------------------------
--------------Actualizacion campo texto posicion ----------------------------------------------------------------------------------------------------------------------------------
	
		IF (SELECT DISTINCT reg.regtipoplanilla From pila.RegistroGeneral reg inner join #IC_Aportes_Det det on reg.regnumplanilla = det.textoPosicion ) = 'N'
			BEGIN
					
					Declare @origenN  varchar(16)
					
					select @origenN = (select Distinct reg.regNumPlanilla from #IC_Aportes_Det d
								inner join aporteDetalladoRegistroControlN apdn on d.asignacion = apdn.planillaN 
								inner join AporteGeneral apg on apdn.apdAporteGeneral = apg.apgId
								inner join pila.RegistroGeneral reg on reg.regId = apg.apgRegistroGeneral
								where apdn.planillaN = d.asignacion)
					Print @origenN

					declare @texto varchar(max)

					select @texto = (select  dbo.ASP_BuscarPlanillasN(@origenN))

					
					drop table if exists #texto1
					select x.PN 
					into #texto1 from(
					SELECT value as PN FROM STRING_SPLIT(@texto, '\')) as x

					declare @textoF varchar(50)
					Select @textoF = STRING_AGG(t2.PN, '\') 
					from(
					select Distinct t.PN From #texto1 as t
					inner join #IC_Aportes_Det d on t.PN <> d.asignacion) as t2


					UPDATE #IC_Aportes_Det set textoPosicion =CONVERT(VARCHAR(50),Concat(@origenN,'\'+ @textoF))
			END
			ELSE
				BEGIN 


		
					Declare @origen  varchar(16)
					Select @origen = (select Distinct asignacion from #IC_Aportes_Det)

					select @Origen as origen

					declare @resultado  varchar (max)
					select @resultado = (select dbo.ASP_BuscarPlanillasN (@origen))


					if (select @resultado) is null
						begin
							Update #IC_Aportes_Det set textoPosicion = planillaN
							from aporteDetalladoRegistroControlN where asignacion not like '%M%' and planillaAntes = asignacion
						end
						Else 
							begin
							update  #IC_Aportes_Det set textoPosicion = (select dbo.ASP_BuscarPlanillasN (@origen)) where asignacion not like '%M%' and @origen = asignacion
							end 


					----Select Distinct planillaAntes from aporteDetalladoRegistroControlN where planillaN = '65670464' --65470009
					--Update #IC_Aportes_Det set textoPosicion = planillaN
					--from aporteDetalladoRegistroControlN where asignacion not like '%M%' and asignacion = planillaAntes 
				END
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------					



--------------------------------------------validacion de movimiento A08 previo --------------------------------------------------
				IF (select tipomovimiento from #IC_Aportes_Enc) = 'A08'
					Begin 
						IF NOT EXISTS (Select d.asignacion from sap.IC_Aportes_Det d inner join #IC_Aportes_Det iad on d.asignacion = iad.asignacion inner join sap.IC_Aportes_Enc e on d.consecutivo = e.consecutivo where tipoMovimiento = 'A07' and estadoReg = 'S') 
							BEGIN
								DELETE  #IC_Aportes_Det;
								DELETE  #IC_Aportes_Enc;							
								DELETE  #APLICA
							END	
	
					END

-------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
			SELECT @referenciaNum = valorActual 
				FROM sap.ic_referencia
				WHERE comentario ='A'
				AND estado = 'A';

			
		/*
			IF @referenciaNum -1 = (select top 1  max(CONVERT(bigint, referencia)) from sap.IC_Aportes_Enc INNER JOIN sap.IC_Referencia ON (CONVERT(bigint, referencia)+1)  = valorActual AND estado = 'A'and comentario ='A')
			BEGIN*/	

				UPDATE #IC_Aportes_Enc
					SET referencia =  @referenciaNum
		
	--comentarear	

				if (Select count(*) from #IC_Aportes_Det)>0
					BEGIN 
						UPDATE sap.ic_referencia
							SET valorActual = @referenciaNum + 1
							WHERE comentario ='A'
							AND estado = 'A';
				END

	--fin comentario	
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
 
				INSERT INTO sap.PlanillasCtrl
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

				DROP TABLE IF EXISTS #IC_Aportes_Det;
				DROP TABLE IF EXISTS #IC_Aportes_Enc;							
				DROP TABLE IF EXISTS #APLICA 
				DROP TABLE IF EXISTS #MarcaPeriodo
	
	
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
	SELECT 'ok ejec A07 08 TP',GETDATE() 
	--DELETE FROM	sap.ic_internoA
	--UPDATE sap.ic_internoA SET valoractual = 0 WHERE valoractual = 1
	--DELETE FROM	sap.ic_internoA

	--END
	--ELSE BEGIN

	--	SELECT 'El proceso ya se encuentra en ejecucion'
	
	--END
END