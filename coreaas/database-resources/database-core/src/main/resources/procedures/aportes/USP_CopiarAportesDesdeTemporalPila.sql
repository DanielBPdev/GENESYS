-- =============================================
-- Author:		Juan Diego Ocampo Q
-- Create date: 2020/05/07
-- Description:	Procedimiento almacenado encargado copiar los aportes desde pila 
-- Ajustes: Se realiza ajuste, para completar los aportes de independientes y pensionados, de igual forma se realiza ajuste ya que los estaba duplicando. 2022-06-08
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_CopiarAportesDesdeTemporalPila]
	@idRegistroGeneral BIGINT 
AS
BEGIN
set xact_abort on;
SET NOCOUNT ON;

BEGIN TRY

	BEGIN TRAN T1

	INSERT RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.GetLocalDate(),'[USP_CopiarAportesDesdeTemporalPila] ENTRA' ,@idRegistroGeneral);

		-- ----------------------------------------------------------------------------------------------
		-- CONTROL DE CAMBIOS PLANILLA N CON N ----------------------------------------------------------
		--=== Se realiza ajuste para el filtro de las planillas N
		declare @registroGeneralNTabla table (regId bigInt, origen varchar(250))
		insert @registroGeneralNTabla
		execute sp_execute_remote pilaReferenceData, N'
		select r.regId
		from staging.registroGeneral as r
		inner join staging.registroDetallado as rd on r.regId = rd.redRegistroGeneral
		where r.regId = @idRegistroGeneral and rd.redCorrecciones = ''C'' and isnull(redUsuarioAccion,'''')<>''@asopagos_TI'' '
		,N'@idRegistroGeneral bigInt', @idRegistroGeneral = @idRegistroGeneral

		if exists (select regId from @registroGeneralNTabla)
			begin
				declare @idRegistroGeneralN bigInt = (select distinct regId from @registroGeneralNTabla)
				--== Lanzamos la ejecución del sp para planillas N
				execute [dbo].[USP_CopiarAportesDesdeTemporalPilaPlanillasN] @idRegistroGeneralN;
			end
		-- FIN - CONTROL DE CAMBIOS PLANILLA N CON N ----------------------------------------------------------
		-- ----------------------------------------------------------------------------------------------

		else
			--===============
			--===============
			begin --==Inicia proceso para planillas normales. 
			--===============
				--====== Inicia control, para garantizar que si exista registro en la tbl temporales de pila
			if exists (select 1 from pila.registroGeneral where regId = @idRegistroGeneral) and exists (select 1 from pila.TemAporte where temRegistroGeneral = @idRegistroGeneral)
					begin
			--===============
		declare @ajusteVal as table (redId bigInt, redOUTEstadoRegistroAporte varchar(30), origen varchar(250))
		insert @ajusteVal
		execute sp_execute_remote pilaReferenceData, N'
			select red.redId, redOUTEstadoRegistroAporte = 
			case 
				when (isnull(redOUTEstadoValidacionV0,'''') = ''CUMPLE'' AND isnull(redOUTEstadoValidacionV1,'''') = ''OK'' AND ISNULL(redOUTEstadoValidacionV2, '''') = ''OK'' AND isnull(redOUTEstadoValidacionV3,'''')=''OK'') 
				then ''OK''
				when (isnull(redOUTEstadoValidacionV0,'''') != ''CUMPLE'' AND isnull(redOUTEstadoValidacionV1,'''') = ''OK'' AND ISNULL(redOUTEstadoValidacionV2, '''') = ''OK'' AND isnull(redOUTEstadoValidacionV3,'''')=''OK'')
				then ''NO_OK_APROBADO''
				when redOUTEstadoValidacionV0 = ''CUMPLE'' and redOUTEstadoValidacionV1 = ''OK''and redOUTEstadoValidacionV2 = ''NO_OK'' and redOUTEstadoValidacionV3 = ''OK''
				then ''NO_OK_APROBADO''
				when (isnull(redOUTEstadoValidacionV2, '''') = ''NO_VALIDADO_BD'')
				then ''NO_VALIDADO_BD''
				when redOUTEstadoValidacionV0 = ''CUMPLE'' and redOUTEstadoValidacionV1 = ''OK'' and redOUTEstadoValidacionV2 = ''NO_OK'' and redOUTEstadoValidacionV3 = ''NO_OK''
				then ''NO_OK_APROBADO''
				when redOUTEstadoValidacionV0 = ''CUMPLE'' and redOUTEstadoValidacionV1 = ''OK'' and redOUTEstadoValidacionV2 = ''OK'' and redOUTEstadoValidacionV3 = ''NO_OK''
				then ''NO_OK_APROBADO''
				else ''NO_OK''
				end
			FROM staging.RegistroDetallado red with (nolock)
			WHERE red.redRegistroGeneral = @IdRegistroGeneral
			and red.redOUTTipoAfiliado = ''TRABAJADOR_DEPENDIENTE''',N'@idRegistroGeneral bigInt', @idRegistroGeneral = @IdRegistroGeneral

		DECLARE @iRevision BIGINT;
		DECLARE @localDate DATETIME = dbo.getLocalDate()
		DECLARE @periodoAnticipado DATE = DATEADD(DAY,1,EOMONTH(DATEADD(MONTH, 1, @localDate),-1))
        DECLARE @periodoVencido DATE = DATEADD(DAY,1,EOMONTH(DATEADD(MONTH, -1, @localDate),-1))
		
		CREATE TABLE #Tramitadores (
			tramitadorEmpId BIGINT,
			tramitadorIdTransaccion BIGINT
		)
    	CREATE CLUSTERED INDEX ix_Copiar_Aprotes_Tramitadores ON #Tramitadores (tramitadorIdTransaccion);

		
		INSERT INTO #Tramitadores (tramitadorEmpId, tramitadorIdTransaccion)
		SELECT empId, tapIdTransaccion
		FROM pila.TemAporte
		INNER JOIN pila.TemAportante ON tapIdTransaccion = temIdTransaccion
		INNER JOIN Persona with (nolock) ON tapTipoDocTramitador = perTipoIdentificacion AND tapIdTramitador = perNumeroIdentificacion
		INNER JOIN Empresa with (nolock) ON empPersona = perId
		WHERE temRegistroGeneral = @idRegistroGeneral 
		AND tapTipoDocTramitador IS NOT NULL
		AND tapIdTramitador IS NOT NULL
		
		-- TODO: a esto roles afiliados se debe actualizar los datos de fiscalizacion ?
		CREATE TABLE #EstadoAportanteCotizante (
			eacRoaId BIGINT,
			eacEstadoAportante VARCHAR(50) NULL,
			eacEstadoCotizante VARCHAR(60) NULL,
			eacIdTransaccion BIGINT,
			eacMarcaPeriodo	VARCHAR(19),
			eacEmpId BIGINT,
			eacPerid BIGINT
		)
		CREATE CLUSTERED INDEX ix_Copiar_Aprotes_EstadoAportanteCotizante ON #EstadoAportanteCotizante (eacIdTransaccion);
		
		-- TODO: Se agraga control para validar el aporte contra el tipo de aportante en el periodo. 
		;with validarAportanteIndPen as (
        select case when r.roaId = max(r.roaId) over (partition by p.perId, a.afiId, r.roaTipoAfiliado) then p.perId else null end as perId,
        case when r.roaId = max(r.roaId) over (partition by p.perId, a.afiId, r.roaTipoAfiliado) then r.roaEstadoAfiliado else null end as roaEstadoAfiliado,
		case when r.roaId = max(r.roaId) over (partition by p.perId, a.afiId, r.roaTipoAfiliado) then r.roaTipoAfiliado else null end as roaTipoAfiliado
        from persona as p with (nolock)
        inner join pila.TemCotizante as coti on p.perTipoIdentificacion = coti.tctTipoIdCotizante and p.perNumeroIdentificacion = coti.tctIdCotizante
        inner join afiliado as a with (nolock) on p.perId = a.afiPersona
        inner join rolAfiliado as r with (nolock) on a.afiId = r.roaAfiliado
        where r.roaTipoAfiliado in ('TRABAJADOR_INDEPENDIENTE', 'PENSIONADO') and coti.tctIdTransaccion in (select temIdTransaccion from pila.TemAporte where temRegistroGeneral = @idRegistroGeneral))
		

		INSERT INTO #EstadoAportanteCotizante(eacRoaId, eacEstadoAportante, eacEstadoCotizante, eacIdTransaccion, eacMarcaPeriodo,eacEmpId,eacPerid)
		SELECT roaId, --empEstadoEmpleador
		case when tctTipoCotizante in ('TRABAJADOR_INDEPENDIENTE', 'PENSIONADO') 
				then case when (select roaEstadoAfiliado from validarAportanteIndPen where perId = apo.perId and tctTipoCotizante = roaTipoAfiliado group by perId, roaEstadoAfiliado) is null or 
								(select roaEstadoAfiliado from validarAportanteIndPen where perId = apo.perId and tctTipoCotizante = roaTipoAfiliado group by perId, roaEstadoAfiliado) = ''
									then 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' 
									else (select roaEstadoAfiliado from validarAportanteIndPen where perId = apo.perId and tctTipoCotizante = roaTipoAfiliado group by perId, roaEstadoAfiliado) 
									end
				else empEstadoEmpleador end empEstadoEmpleador,
		roaEstadoAfiliado, temIdTransaccion,
			CASE 
				WHEN (roaOportunidadPago IS NULL OR roaOportunidadPago = 'MES_VENCIDO' or roaOportunidadPago = 'MES_ACTUAL') THEN
					CASE 
						WHEN (@periodoVencido > CONVERT(DATE,temPeriodoAporte+'-01',120)) THEN 'PERIODO_RETROACTIVO'
						WHEN (@periodoVencido = CONVERT(DATE,temPeriodoAporte+'-01',120)) THEN 'PERIODO_REGULAR'
						WHEN (@periodoVencido < CONVERT(DATE,temPeriodoAporte+'-01',120)) THEN 'PERIODO_FUTURO'
					END
				WHEN (roaOportunidadPago = 'ANTICIPADO') THEN
					CASE 
						WHEN (@periodoAnticipado > CONVERT(DATE,temPeriodoAporte+'-01',120)) THEN 'PERIODO_RETROACTIVO'
						WHEN (@periodoAnticipado = CONVERT(DATE,temPeriodoAporte+'-01',120)) THEN 'PERIODO_REGULAR'
						WHEN (@periodoAnticipado < CONVERT(DATE,temPeriodoAporte+'-01',120)) THEN 'PERIODO_FUTURO'
					END
			END,
			emp.empId,
			cot.perid
		FROM pila.TemAporte
		INNER JOIN pila.TemCotizante ON tctIdTransaccion = temIdTransaccion
		INNER JOIN Persona apo with (nolock) ON temTipoIdAportante = apo.perTipoIdentificacion AND temNumeroIdAportante = apo.perNumeroIdentificacion
		LEFT JOIN Empresa emp with (nolock) ON emp.empPersona = apo.perId
		LEFT JOIN Empleador epl with (nolock) ON epl.empEmpresa = emp.empId
		INNER JOIN Persona cot with (nolock) ON temTipoIdCotizante = cot.perTipoIdentificacion AND temNumeroIdCotizante = cot.perNumeroIdentificacion
		LEFT JOIN Afiliado with (nolock) ON afiPersona = cot.perid
		LEFT JOIN RolAfiliado with (nolock) ON roaAfiliado = afiId AND roaEmpleador = epl.empId
		WHERE temRegistroGeneral = @idRegistroGeneral
		
		CREATE TABLE #Sucursal (
			sucIdTransaccion BIGINT,
			sucCodSucursal VARCHAR(10) NULL,
			sucNomSucursal VARCHAR(100) NULL,
			sucEsDependiente BIT,
			sucCodSucursalPila VARCHAR(10) NULL
		)
		CREATE INDEX ix_Copiar_Aprotes_Sucursal_sucIdTransaccion ON #Sucursal (sucIdTransaccion);
		
		INSERT INTO #Sucursal (
			sucIdTransaccion,
			sucCodSucursal,
			sucNomSucursal,
			sucEsDependiente,
			sucCodSucursalPILA
		)
		SELECT 
			tem.temIdTransaccion,
			CASE WHEN (tap.tapMarcaSucursal = 1 ) THEN tct.tctCodSucursalPILA ELSE tct.tctCodSucursal END,
			CASE WHEN (tap.tapMarcaSucursal = 1 ) THEN tct.tctNomSucursalPILA ELSE tct.tctNomSucursal END,
			CASE WHEN (tct.tctTipoCotizante = 'TRABAJADOR_DEPENDIENTE') THEN 1 ELSE 0 END,
			tct.tctCodSucursalPILA
		FROM pila.TemAporte tem
		INNER JOIN pila.TemAportante tap ON tap.tapIdTransaccion = tem.temIdTransaccion
		INNER JOIN pila.TemCotizante tct ON tct.tctIdTransaccion = tem.temIdTransaccion
		WHERE temRegistroGeneral = @idRegistroGeneral
		
		DECLARE @codigoSucursalPila VARCHAR(10)
		SELECT TOP 1 @codigoSucursalPila = suc.sucCodSucursalPILA FROM #Sucursal suc
		
		
		CREATE TABLE #AporteGeneralTtemporalControlDuplicados (
			[id] int identity(1,1) not null,
			[apgPeriodoAporte] [varchar](7) NULL,
			[apgValTotalApoObligatorio] [numeric](19, 5) NULL,
			[apgValorIntMora] [numeric](19, 5) NULL,
			[apgFechaRecaudo] [date] NULL,
			[apgFechaProcesamiento] [datetime] NULL,
			[apgCodEntidadFinanciera] [smallint] NULL,
			[apgOperadorInformacion] [bigint] NULL,
			[apgModalidadPlanilla] [varchar](40) NULL,
			[apgModalidadRecaudoAporte] [varchar](40) NULL,
			[apgApoConDetalle] [bit] NULL,
			[apgNumeroCuenta] [varchar](17) NULL,
			[apgRegistroGeneral] [bigint] NOT NULL,
			[apgPersona] [bigint] NULL,
			[apgEmpresa] [bigint] NULL,
			[apgSucursalEmpresa] [bigint] NULL,
			[apgEstadoAportante] [varchar](50) NULL,
			[apgEstadoAporteAportante] [varchar](40) NULL,
			[apgEstadoRegistroAporteAportante] [varchar](30) NULL,
			[apgPagadorPorTerceros] [bit] NULL,
			[apgTipoSolicitante] [varchar](13) NULL,
			[apgOrigenAporte] [varchar](26) NULL,
			[apgCajaCompensacion] [int] NULL,
			[apgEmailAportante] [varchar](255) NULL,
			[apgEmpresaTramitadoraAporte] [bigint] NULL,
			[apgFechaReconocimiento] [datetime] NULL,
			[apgFormaReconocimientoAporte] [varchar](75) NULL,
			[apgMarcaPeriodo] [varchar](19) NULL,
			[apgMarcaActualizacionCartera] [bit] NULL,
			[apgConciliado] [bit] NULL,
			[apgNumeroPlanillaManual] [varchar](10) NULL,
			[apgEnProcesoReconocimiento] [bit] NULL,
			[apgRegistroGeneralUltimo] bigint)
		
	
		INSERT INTO #AporteGeneralTtemporalControlDuplicados(
			apgPeriodoAporte
			,apgValTotalApoObligatorio
			,apgValorIntMora
			,apgFechaRecaudo
			,apgFechaProcesamiento
			,apgCodEntidadFinanciera
			,apgOperadorInformacion
			,apgModalidadPlanilla
			,apgModalidadRecaudoAporte
			,apgApoConDetalle
			,apgNumeroCuenta
			,apgRegistroGeneral
			,apgPersona
			,apgEmpresa
			,apgSucursalEmpresa
			,apgEstadoAportante
			,apgEstadoAporteAportante
			,apgEstadoRegistroAporteAportante
			,apgPagadorPorTerceros
			,apgTipoSolicitante
			,apgOrigenAporte
			,apgCajaCompensacion
			,apgEmailAportante
			,apgEmpresaTramitadoraAporte
			,apgFechaReconocimiento
			,apgFormaReconocimientoAporte
			,apgMarcaPeriodo
			,apgMarcaActualizacionCartera
			,apgConciliado
			,apgNumeroPlanillaManual
			,apgEnProcesoReconocimiento,
			apgRegistroGeneralUltimo
		)
		SELECT DISTINCT
			temPeriodoAporte
			,temValTotalApoObligatorio
			,temValorIntMoraGeneral
			,temFechaRecaudo
			,@localDate
			,temCodEntidadFinanciera
			,(SELECT oinId FROM OperadorInformacion WHERE oinCodigo = temOperadorInformacion)
			,temModalidadPlanilla
			,temModalidadRecaudoAporte
			,temApoConDetalle
			,temNumeroCuenta
			,temRegistroGeneral
			,CASE WHEN (tapTipoSolicitud = 'PERSONA' OR tapTipoSolicitud = 'ENTIDAD_PAGADORA') THEN perId ELSE NULL END 
			,CASE WHEN tapTipoSolicitud = 'EMPLEADOR' THEN (SELECT empId FROM Empresa WHERE empPersona = perId) ELSE NULL END 
			,CASE 
				WHEN tapTipoSolicitud = 'EMPLEADOR' 
				THEN (
					SELECT TOP 1 sueId 
					FROM Empresa emp1
					INNER JOIN SucursalEmpresa sue1 ON sue1.sueEmpresa = emp1.empId
					WHERE emp1.empPersona = per.perId
					AND @codigoSucursalPila = sue1.sueCodigo
					ORDER BY sueId DESC
				) 
				ELSE NULL
			END -- TODO : verificar sucursal PILA que se tiene en TemCotizante y marca tapMarcaSucursal en TemAportante
			,CASE WHEN eacEstadoAportante IS NOT NULL THEN eacEstadoAportante ELSE 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' END -- TODO: verificar porque ISNULL trunca el valor por defecto
			,temEstadoAporteRecaudo
			,temEstadoRegistroAporte
			--,CASE WHEN (temTipoIdCotizante = tapTipoDocAportante AND temNumeroIdCotizante = tapIdAportante) THEN 1 ELSE 0 END
           /*
		   ,(SELECT
                CASE WHEN (tctTipoCotizante = 'TRABAJADOR_INDEPENDIENTE') THEN
                    CASE WHEN (temTipoIdCotizante = tapTipoDocAportante AND temNumeroIdCotizante = tapIdAportante) THEN 0 ELSE 1 END
                ELSE 0 END
             FROM pila.TemCotizante WHERE tctIdTransaccion = temIdTransaccion)
			,(SELECT CASE WHEN (tctTipoCotizante = 'TRABAJADOR_DEPENDIENTE') THEN 'EMPLEADOR' WHEN (tctTipoCotizante = 'TRABAJADOR_INDEPENDIENTE') THEN 'INDEPENDIENTE' ELSE 'PENSIONADO' END FROM pila.TemCotizante WHERE tctIdTransaccion = temIdTransaccion)
			*/
			,CASE WHEN (tctTipoCotizante = 'TRABAJADOR_INDEPENDIENTE') THEN
                    CASE WHEN (temTipoIdCotizante = tapTipoDocAportante AND temNumeroIdCotizante = tapIdAportante) THEN 0 ELSE 1 END
                ELSE 0 END
			,CASE WHEN (tctTipoCotizante = 'TRABAJADOR_DEPENDIENTE') THEN 'EMPLEADOR' WHEN (tctTipoCotizante = 'TRABAJADOR_INDEPENDIENTE') THEN 'INDEPENDIENTE' ELSE 'PENSIONADO' END
			,NULL-- ????
			,NULL-- ????
			,tapEmail
			,tramitadorEmpId
			,CASE WHEN (temFormaReconocimientoAporte IS NOT NULL) THEN @localDate ELSE NULL END
			,temFormaReconocimientoAporte --temEstadoRegistroAporte == 'REGISTRADO' ? RECONOCIMIENTO_AUTOMATICO_OPORTUNO else NULL
			,eacMarcaPeriodo
			,1 --aporte.getAporteGeneral().setMarcaActualizacionCartera(Boolean.TRUE);
			,NULL -- NULL SIEMPRE?
			,temNumeroPlanillaManual
			,0 -- nunca se usa???
			,temRegistroGeneral
		FROM pila.TemAporte tem
		INNER JOIN pila.TemAportante ON tapIdTransaccion = tem.temIdTransaccion
		INNER JOIN Persona per with (nolock) ON temTipoIdAportante = perTipoIdentificacion AND temNumeroIdAportante = perNumeroIdentificacion
		left join pila.TemCotizante on tctIdTransaccion = temIdTransaccion
		LEFT JOIN #EstadoAportanteCotizante ON temIdTransaccion = eacIdTransaccion
		LEFT JOIN #Tramitadores ON tramitadorIdTransaccion = tapIdTransaccion
		WHERE temRegistroGeneral = @idRegistroGeneral
		  
		  
		  -- TODO: Se actualiza el campo oprtunidad de pago, en caso se venir null
		  update apg set apg.apgMarcaPeriodo = 
            CASE 
            	WHEN (roaOportunidadPago IS NULL OR roaOportunidadPago = 'MES_VENCIDO' or roaOportunidadPago = 'MES_ACTUAL') THEN
            		CASE 
            			WHEN (DATEADD(DAY,1,EOMONTH(DATEADD(MONTH, -1, apgFechaProcesamiento),-1)) > CONVERT(DATE,apgPeriodoAporte+'-01',120)) THEN 'PERIODO_RETROACTIVO'
            			WHEN (DATEADD(DAY,1,EOMONTH(DATEADD(MONTH, -1, apgFechaProcesamiento),-1)) = CONVERT(DATE,apgPeriodoAporte+'-01',120)) THEN 'PERIODO_REGULAR'
            			WHEN (DATEADD(DAY,1,EOMONTH(DATEADD(MONTH, -1, apgFechaProcesamiento),-1)) < CONVERT(DATE,apgPeriodoAporte+'-01',120)) THEN 'PERIODO_FUTURO'
            		END
            	WHEN (roaOportunidadPago = 'ANTICIPADO') THEN
            		CASE 
            			WHEN (DATEADD(DAY,1,EOMONTH(DATEADD(MONTH, 1, apgFechaProcesamiento),-1)) > CONVERT(DATE,apgPeriodoAporte+'-01',120)) THEN 'PERIODO_RETROACTIVO'
            			WHEN (DATEADD(DAY,1,EOMONTH(DATEADD(MONTH, 1, apgFechaProcesamiento),-1)) = CONVERT(DATE,apgPeriodoAporte+'-01',120)) THEN 'PERIODO_REGULAR'
            			WHEN (DATEADD(DAY,1,EOMONTH(DATEADD(MONTH, 1, apgFechaProcesamiento),-1)) < CONVERT(DATE,apgPeriodoAporte+'-01',120)) THEN 'PERIODO_FUTURO'
            		END
            END
            from #AporteGeneralTtemporalControlDuplicados as apg
            inner join empresa as e with (nolock) on apg.apgEmpresa = e.empId
            left join Empleador as em with (nolock) on e.empId = em.empEmpresa
            left join RolAfiliado as ra with (nolock) on em.empId = ra.roaEmpleador
            where apg.apgMarcaPeriodo is null


			DECLARE @aporteFuturoGap varchar(20);
			DECLARE @aporteFuturo varchar(10);
			SELECT @aporteFuturoGap =  pg.prgEstado from ParametrizacionGaps pg where pg.prgNombre = 'APORTES_FUTURO'
			SELECT @aporteFuturo =  p.prmValor from Parametro p where p.prmNombre = 'REGISTRO_APORTES_FUTURO'
			
			--== :TODO Ajuste 2023-10-03
			
			update apg set apg.apgEstadoRegistroAporteAportante = 
			case when apg.apgMarcaPeriodo = 'PERIODO_FUTURO' and @aporteFuturoGap = 'ACTIVO' and @aporteFuturo = 'SI' and isnull(apg.apgEstadoAportante,'') in ('ACTIVO','INACTIVO','NO_FORMALIZADO_RETIRADO_CON_APORTES') then 'REGISTRADO'
				else 
					case when apg.apgMarcaPeriodo = 'PERIODO_FUTURO' then 'RELACIONADO'
						else 
							case when apg.apgEstadoAportante in ('ACTIVO', 'NO_FORMALIZADO_RETIRADO_CON_APORTES', 'INACTIVO') and apg.apgEstadoRegistroAporteAportante = 'RELACIONADO' then 'REGISTRADO'
								else 
									case when isnull(apg.apgEstadoAportante,'') in ('','NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_CON_INFORMACION') and apg.apgEstadoRegistroAporteAportante = 'REGISTRADO' then 'RELACIONADO'
									else 
										case when apg.apgEstadoAportante in ('ACTIVO', 'NO_FORMALIZADO_RETIRADO_CON_APORTES', 'INACTIVO') and apg.apgEstadoRegistroAporteAportante is null then 'REGISTRADO'
										else 
											case when isnull(apg.apgEstadoAportante,'') in ('','NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES','NO_FORMALIZADO_CON_INFORMACION') and apg.apgEstadoRegistroAporteAportante is null then 'RELACIONADO'
											else apg.apgEstadoRegistroAporteAportante --=== Finalizamos proceso para esos aportes. 
											end
										end
									end
								end
						end
				end
			from #AporteGeneralTtemporalControlDuplicados as apg


			CREATE TABLE #AporteGeneralTtemporal (
			[apgId] [bigint] NOT NULL,
			[apgPeriodoAporte] [varchar](7) NULL,
			[apgValTotalApoObligatorio] [numeric](19, 5) NULL,
			[apgValorIntMora] [numeric](19, 5) NULL,
			[apgFechaRecaudo] [date] NULL,
			[apgFechaProcesamiento] [datetime] NULL,
			[apgCodEntidadFinanciera] [smallint] NULL,
			[apgOperadorInformacion] [bigint] NULL,
			[apgModalidadPlanilla] [varchar](40) NULL,
			[apgModalidadRecaudoAporte] [varchar](40) NULL,
			[apgApoConDetalle] [bit] NULL,
			[apgNumeroCuenta] [varchar](17) NULL,
			[apgRegistroGeneral] [bigint] NOT NULL,
			[apgPersona] [bigint] NULL,
			[apgEmpresa] [bigint] NULL,
			[apgSucursalEmpresa] [bigint] NULL,
			[apgEstadoAportante] [varchar](50) NULL,
			[apgEstadoAporteAportante] [varchar](40) NULL,
			[apgEstadoRegistroAporteAportante] [varchar](30) NULL,
			[apgPagadorPorTerceros] [bit] NULL,
			[apgTipoSolicitante] [varchar](13) NULL,
			[apgOrigenAporte] [varchar](26) NULL,
			[apgCajaCompensacion] [int] NULL,
			[apgEmailAportante] [varchar](255) NULL,
			[apgEmpresaTramitadoraAporte] [bigint] NULL,
			[apgFechaReconocimiento] [datetime] NULL,
			[apgFormaReconocimientoAporte] [varchar](75) NULL,
			[apgMarcaPeriodo] [varchar](19) NULL,
			[apgMarcaActualizacionCartera] [bit] NULL,
			[apgConciliado] [bit] NULL,
			[apgNumeroPlanillaManual] [varchar](10) NULL,
			[apgEnProcesoReconocimiento] [bit] NULL,
			[apgRegistroGeneralUltimo] bigint
		)
		CREATE INDEX ix_Copiar_Aprotes_AporteGeneralTtemporal_Persona ON #AporteGeneralTtemporal (apgPersona);
		CREATE INDEX ix_Copiar_Aprotes_AporteGeneralTtemporal_Empresa ON #AporteGeneralTtemporal (apgEmpresa);

-----------------------------------------****************************************************
----********* INICIA PROCESO DE CONTROL PARA LAS PLANILLAS DUPLICADAS O PLANILLAS CON APGPERSONA Y APGEMPRESA EN NULL PORQUE NO PASAN LOS DETALLES DEL APORTE.
----********* LOS APORTES QUE ENTRAN A LA TABLA DE CONTROL PERMANECEN EN LA TABLA TEMPORAL DE PILA. 
-----------------------------------------****************************************************

if exists (select * from AporteGeneral as a with (nolock)
				inner join #AporteGeneralTtemporalControlDuplicados as b on  a.apgRegistroGeneral = b.apgRegistroGeneral and a.apgPeriodoAporte = b.apgPeriodoAporte 
				and   isnull(a.apgPersona,1) = isnull(b.apgPersona,1) and isnull(a.apgEmpresa,1) = isnull(b.apgEmpresa,1) and a.apgTipoSolicitante = b.apgTipoSolicitante and a.apgEstadoAporteAportante = b.apgEstadoAporteAportante
				where a.apgTipoSolicitante = 'EMPLEADOR')
	or exists (select * from #AporteGeneralTtemporalControlDuplicados where apgPersona is null and apgEmpresa is null)
   begin

---***************************************************************** -----
---********** CONTROL PARA INSERTAR APORTES DETALLADOS FALTANTES ***** -----
---***************************************************************** -----
		  create table #AporteGeneralDuplica (apgId bigInt, apgPersona bigInt, apgEmpresa bigInt)
		  /*
		  insert #AporteGeneralDuplica
		  select a.apgId, a.apgPersona, a.apgEmpresa 
		  from AporteGeneral as a
		  inner join #AporteGeneralTtemporalControlDuplicados as b on  a.apgRegistroGeneral = b.apgRegistroGeneral and a.apgPeriodoAporte = b.apgPeriodoAporte 
		  and   isnull(a.apgPersona,1) = isnull(b.apgPersona,1) and isnull(a.apgEmpresa,1) = isnull(b.apgEmpresa,1) and a.apgTipoSolicitante = b.apgTipoSolicitante and a.apgEstadoAporteAportante = b.apgEstadoAporteAportante
		  where a.apgMarcaPeriodo is not null
		  */
		  --- CON ESTE CONTROL EVITAMOS MÁS DUPLICADOS Y ASOCIAMOS LOS REGISTROS AL PRIMER APGID QUE INGRESO A LA TABLA DE APORTE GENERAL
		  ;with duplicados as (
		   select a.apgId, a.apgPersona, a.apgEmpresa , dense_rank() over (partition by a.apgRegistroGeneral, a.apgPeriodoAporte, a.apgPersona, a.apgEmpresa, a.apgTipoSolicitante, a.apgEstadoAporteAportante order by a.apgId) as d
		   from AporteGeneral as a with (nolock)
		   inner join #AporteGeneralTtemporalControlDuplicados as b on a.apgRegistroGeneral = b.apgRegistroGeneral and a.apgPeriodoAporte = b.apgPeriodoAporte 
		   and   isnull(a.apgPersona,1) = isnull(b.apgPersona,1) and isnull(a.apgEmpresa,1) = isnull(b.apgEmpresa,1) and a.apgTipoSolicitante = b.apgTipoSolicitante and a.apgEstadoAporteAportante = b.apgEstadoAporteAportante 
		   )
		   insert #AporteGeneralDuplica
		   select apgId, apgPersona, apgEmpresa from duplicados
		   where d = 1


		CREATE TABLE #AporteGeneralIdPorTranaccion_ControlDuplicados (
			aptId BIGINT, 
			aptIdTransaccion BIGINT
		)
		CREATE CLUSTERED INDEX ix_Copiar_Aprotes_AporteGeneralIdPorTranaccion ON #AporteGeneralIdPorTranaccion_ControlDuplicados (aptIdTransaccion);
		
		INSERT INTO #AporteGeneralIdPorTranaccion_ControlDuplicados (
			aptId, 
			aptIdTransaccion
		)
		SELECT apgId, eacIdTransaccion
		FROM #AporteGeneralDuplica
		INNER JOIN #EstadoAportanteCotizante eacPer ON  apgPersona = eacPer.eacPerid
		WHERE apgEmpresa IS NULL
		GROUP BY apgId, eacIdTransaccion
		
		INSERT INTO #AporteGeneralIdPorTranaccion_ControlDuplicados (
			aptId, 
			aptIdTransaccion
		)
		SELECT apgId, eacIdTransaccion
		FROM #AporteGeneralDuplica
		INNER JOIN #EstadoAportanteCotizante ON  apgEmpresa = eacEmpId
		WHERE apgPersona IS NULL
		GROUP BY apgId, eacIdTransaccion
		
		
		CREATE TABLE #AporteDetalladoTemporal_ControlDuplicados (
			[apdAporteGeneral] [bigint] NULL,
			[apdDiasCotizados] [smallint] NULL,
			[apdHorasLaboradas] [smallint] NULL,
			[apdSalarioBasico] [numeric](19, 5) NULL,
			[apdValorIBC] [numeric](19, 5) NULL,
			[apdValorIntMora] [numeric](19, 5) NULL,
			[apdTarifa] [numeric](5, 5) NULL,
			[apdAporteObligatorio] [numeric](19, 5) NULL,
			[apdValorSaldoAporte] [numeric](19, 5) NULL,
			[apdCorrecciones] [varchar](400) NULL,
			[apdEstadoAporteRecaudo] [varchar](50) NULL,
			[apdEstadoAporteAjuste] [varchar](50) NULL,
			[apdEstadoRegistroAporte] [varchar](50) NULL,
			[apdSalarioIntegral] [bit] NULL,
			[apdMunicipioLaboral] [varchar](5) NULL,
			[apdDepartamentoLaboral] [smallint] NULL,
			[apdRegistroDetallado] [bigint] NOT NULL,
			[apdTipoCotizante] [varchar](100) NULL,
			[apdEstadoCotizante] [varchar](60) NULL,
			[apdEstadoAporteCotizante] [varchar](50) NULL,
			[apdEstadoRegistroAporteCotizante] [varchar](40) NULL,
			[apdPersona] [bigint] NULL,
			[apdUsuarioAprobadorAporte] [varchar](50) NOT NULL,
			[apdEstadoRegistroAporteArchivo] [varchar](60) NOT NULL,
			[apdCodSucursal] [varchar](10) NULL,
			[apdNomSucursal] [varchar](100) NULL,
			[apdFechaMovimiento] [date] NULL,
			[apdFechaCreacion] [date] NULL,
			[apdFormaReconocimientoAporte] [varchar](75) NULL,
			[apdMarcaPeriodo] [varchar](19) NULL,
			[apdModalidadRecaudoAporte] [varchar](40) NULL,
			[apdMarcaCalculoCategoria] [bit] NULL,
			[apdModificadoAportesOK] [bit] NULL,
			[apdRegistroDetalladoUltimo] bigint
		)
			insert INTO #AporteDetalladoTemporal_ControlDuplicados(
			apdAporteGeneral,
			apdDiasCotizados,
			apdHorasLaboradas,
			apdSalarioBasico,
			apdValorIBC,
			apdValorIntMora,
			apdTarifa,
			apdAporteObligatorio,
			apdValorSaldoAporte,
			apdCorrecciones,
			apdEstadoAporteRecaudo,
			apdEstadoAporteAjuste,
			apdEstadoRegistroAporte,
			apdSalarioIntegral,
			apdMunicipioLaboral,
			apdDepartamentoLaboral,
			apdRegistroDetallado,
			apdTipoCotizante,
			apdEstadoCotizante,
			apdEstadoAporteCotizante,
			apdEstadoRegistroAporteCotizante,
			apdPersona,
			apdUsuarioAprobadorAporte,
			apdEstadoRegistroAporteArchivo,
			apdCodSucursal,
			apdNomSucursal,
			apdFechaMovimiento,
			apdFechaCreacion,
			apdFormaReconocimientoAporte,
			apdMarcaPeriodo,
			apdModalidadRecaudoAporte,
			apdMarcaCalculoCategoria,
			apdModificadoAportesOK,
			apdRegistroDetalladoUltimo)
		SELECT
			aptId,
			temDiasCotizados,
			temHorasLaboradas,
			temSalarioBasico,
			temValorIBC,
			temValorIntMoraDetalle,
			temTarifa,
			temAporteObligatorio,
			temValorSaldoAporte,
			temCorrecciones,
			temEstadoAporteRecaudo,
			temEstadoAporteAjuste,
			temEstadoRegistroAporte,
			temSalarioIntegral,
			temMunicipioLaboral,
			temDepartamentoLaboral,
			temIdTransaccion,
			tctTipoCotizante,
			CASE WHEN (eacEstadoCotizante IS NOT NULL ) THEN eacEstadoCotizante ELSE 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' END,
			temEstadoAporteRecaudo,
			temEstadoRegistroAporte,
			perId,
			'SISTEMA',
			--temEstadoValRegistroAporte,
			isnull((select redOUTEstadoRegistroAporte from @ajusteVal where redId = tem.temRegistroDetallado), temEstadoValRegistroAporte) as temEstadoValRegistroAporte,
			suc.sucCodSucursal,
			suc.sucNomSucursal,
			@localDate,--TODO: solo si hay movimientos
			@localDate,
			temFormaReconocimientoAporte,
			eacMarcaPeriodo,
			temModalidadRecaudoAporte,
			1,
			NULL, -- SIEMPRE?
			temIdTransaccion
		FROM pila.TemAporte tem
		INNER JOIN pila.TemCotizante ON tctIdTransaccion = tem.temIdTransaccion
		INNER JOIN Persona with (nolock) ON temTipoIdCotizante = perTipoIdentificacion AND temNumeroIdCotizante = perNumeroIdentificacion
		INNER JOIN #AporteGeneralIdPorTranaccion_ControlDuplicados ON aptIdTransaccion = temIdTransaccion
		INNER JOIN #Sucursal suc ON suc.sucIdTransaccion = tem.temIdTransaccion 
		LEFT JOIN #EstadoAportanteCotizante ON temIdTransaccion = eacIdTransaccion
		WHERE temRegistroGeneral = @idRegistroGeneral
		  
		  
		CREATE TABLE #AporteDetalladoTemporal_revision (
			[apdId] [bigint] NOT NULL,
			[apdAporteGeneral] [bigint] NULL,
			[apdDiasCotizados] [smallint] NULL,
			[apdHorasLaboradas] [smallint] NULL,
			[apdSalarioBasico] [numeric](19, 5) NULL,
			[apdValorIBC] [numeric](19, 5) NULL,
			[apdValorIntMora] [numeric](19, 5) NULL,
			[apdTarifa] [numeric](5, 5) NULL,
			[apdAporteObligatorio] [numeric](19, 5) NULL,
			[apdValorSaldoAporte] [numeric](19, 5) NULL,
			[apdCorrecciones] [varchar](400) NULL,
			[apdEstadoAporteRecaudo] [varchar](50) NULL,
			[apdEstadoAporteAjuste] [varchar](50) NULL,
			[apdEstadoRegistroAporte] [varchar](50) NULL,
			[apdSalarioIntegral] [bit] NULL,
			[apdMunicipioLaboral] [varchar](5) NULL,
			[apdDepartamentoLaboral] [smallint] NULL,
			[apdRegistroDetallado] [bigint] NOT NULL,
			[apdTipoCotizante] [varchar](100) NULL,
			[apdEstadoCotizante] [varchar](60) NULL,
			[apdEstadoAporteCotizante] [varchar](50) NULL,
			[apdEstadoRegistroAporteCotizante] [varchar](40) NULL,
			[apdPersona] [bigint] NULL,
			[apdUsuarioAprobadorAporte] [varchar](50) NOT NULL,
			[apdEstadoRegistroAporteArchivo] [varchar](60) NOT NULL,
			[apdCodSucursal] [varchar](10) NULL,
			[apdNomSucursal] [varchar](100) NULL,
			[apdFechaMovimiento] [date] NULL,
			[apdFechaCreacion] [date] NULL,
			[apdFormaReconocimientoAporte] [varchar](75) NULL,
			[apdMarcaPeriodo] [varchar](19) NULL,
			[apdModalidadRecaudoAporte] [varchar](40) NULL,
			[apdMarcaCalculoCategoria] [bit] NULL,
			[apdModificadoAportesOK] [bit] NULL,
			[apdRegistroDetalladoUltimo] bigint
		) 
		  
		  
		merge dbo.aporteDetallado as d
		using #AporteDetalladoTemporal_ControlDuplicados as o on d.apdAporteGeneral = o.apdAporteGeneral and d.apdRegistroDetallado = o.apdRegistroDetallado
		when not matched then insert (apdAporteGeneral,apdDiasCotizados,apdHorasLaboradas,apdSalarioBasico,apdValorIBC,apdValorIntMora,apdTarifa,apdAporteObligatorio,apdValorSaldoAporte,apdCorrecciones,apdEstadoAporteRecaudo,apdEstadoAporteAjuste,
		apdEstadoRegistroAporte,apdSalarioIntegral,apdMunicipioLaboral,apdDepartamentoLaboral,apdRegistroDetallado,apdTipoCotizante,apdEstadoCotizante,apdEstadoAporteCotizante,apdEstadoRegistroAporteCotizante,apdPersona,apdUsuarioAprobadorAporte,
		apdEstadoRegistroAporteArchivo,apdCodSucursal,apdNomSucursal,apdFechaMovimiento,apdFechaCreacion,apdFormaReconocimientoAporte,apdMarcaPeriodo,apdModalidadRecaudoAporte,apdMarcaCalculoCategoria,apdModificadoAportesOK,apdRegistroDetalladoUltimo)
		values (o.apdAporteGeneral,o.apdDiasCotizados,o.apdHorasLaboradas,o.apdSalarioBasico,o.apdValorIBC,o.apdValorIntMora,o.apdTarifa,o.apdAporteObligatorio,o.apdValorSaldoAporte,o.apdCorrecciones,o.apdEstadoAporteRecaudo,o.apdEstadoAporteAjuste,
		o.apdEstadoRegistroAporte,o.apdSalarioIntegral,o.apdMunicipioLaboral,o.apdDepartamentoLaboral,o.apdRegistroDetallado,o.apdTipoCotizante,o.apdEstadoCotizante,o.apdEstadoAporteCotizante,o.apdEstadoRegistroAporteCotizante,o.apdPersona,
		o.apdUsuarioAprobadorAporte,o.apdEstadoRegistroAporteArchivo,o.apdCodSucursal,o.apdNomSucursal,o.apdFechaMovimiento,o.apdFechaCreacion,o.apdFormaReconocimientoAporte,o.apdMarcaPeriodo,o.apdModalidadRecaudoAporte,o.apdMarcaCalculoCategoria,
		o.apdModificadoAportesOK,o.apdRegistroDetalladoUltimo)
				OUTPUT
			INSERTED.apdId,
			INSERTED.apdAporteGeneral,
			INSERTED.apdDiasCotizados,
			INSERTED.apdHorasLaboradas,
			INSERTED.apdSalarioBasico,
			INSERTED.apdValorIBC,
			INSERTED.apdValorIntMora,
			INSERTED.apdTarifa,
			INSERTED.apdAporteObligatorio,
			INSERTED.apdValorSaldoAporte,
			INSERTED.apdCorrecciones,
			INSERTED.apdEstadoAporteRecaudo,
			INSERTED.apdEstadoAporteAjuste,
			INSERTED.apdEstadoRegistroAporte,
			INSERTED.apdSalarioIntegral,
			INSERTED.apdMunicipioLaboral,
			INSERTED.apdDepartamentoLaboral,
			INSERTED.apdRegistroDetallado,
			INSERTED.apdTipoCotizante,
			INSERTED.apdEstadoCotizante,
			INSERTED.apdEstadoAporteCotizante,
			INSERTED.apdEstadoRegistroAporteCotizante,
			INSERTED.apdPersona,
			INSERTED.apdUsuarioAprobadorAporte,
			INSERTED.apdEstadoRegistroAporteArchivo,
			INSERTED.apdCodSucursal,
			INSERTED.apdNomSucursal,
			INSERTED.apdFechaMovimiento,
			INSERTED.apdFechaCreacion,
			INSERTED.apdFormaReconocimientoAporte,
			INSERTED.apdMarcaPeriodo,
			INSERTED.apdModalidadRecaudoAporte,
			INSERTED.apdMarcaCalculoCategoria,
			INSERTED.apdModificadoAportesOK,
			INSERTED.apdRegistroDetalladoUltimo
		INTO #AporteDetalladoTemporal_revision;
	
		
		EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.aportes.AporteDetallado', NULL, '', 'USUARIO_SISTEMA', @iRevision OUTPUT
		
		INSERT INTO aud.AporteDetallado_aud(
			apdId,
			apdAporteGeneral,
			apdDiasCotizados,
			apdHorasLaboradas,
			apdSalarioBasico,
			apdValorIBC,
			apdValorIntMora,
			apdTarifa,
			apdAporteObligatorio,
			apdValorSaldoAporte,
			apdCorrecciones,
			apdEstadoAporteRecaudo,
			apdEstadoAporteAjuste,
			apdEstadoRegistroAporte,
			apdSalarioIntegral,
			apdMunicipioLaboral,
			apdDepartamentoLaboral,
			apdRegistroDetallado,
			apdTipoCotizante,
			apdEstadoCotizante,
			apdEstadoAporteCotizante,
			apdEstadoRegistroAporteCotizante,
			apdPersona,
			apdUsuarioAprobadorAporte,
			apdEstadoRegistroAporteArchivo,
			apdCodSucursal,
			apdNomSucursal,
			apdFechaMovimiento,
			apdFechaCreacion,
			apdFormaReconocimientoAporte,
			apdMarcaPeriodo,
			apdModalidadRecaudoAporte,
			apdMarcaCalculoCategoria,
			apdModificadoAportesOK,
			apdRegistroDetalladoUltimo,
			REV, REVTYPE
		)
		SELECT 
			apdId,
			apdAporteGeneral,
			apdDiasCotizados,
			apdHorasLaboradas,
			apdSalarioBasico,
			apdValorIBC,
			apdValorIntMora,
			apdTarifa,
			apdAporteObligatorio,
			apdValorSaldoAporte,
			apdCorrecciones,
			apdEstadoAporteRecaudo,
			apdEstadoAporteAjuste,
			apdEstadoRegistroAporte,
			apdSalarioIntegral,
			apdMunicipioLaboral,
			apdDepartamentoLaboral,
			apdRegistroDetallado,
			apdTipoCotizante,
			apdEstadoCotizante,
			apdEstadoAporteCotizante,
			apdEstadoRegistroAporteCotizante,
			apdPersona,
			apdUsuarioAprobadorAporte,
			apdEstadoRegistroAporteArchivo,
			apdCodSucursal,
			apdNomSucursal,
			apdFechaMovimiento,
			apdFechaCreacion,
			apdFormaReconocimientoAporte,
			apdMarcaPeriodo,
			apdModalidadRecaudoAporte,
			apdMarcaCalculoCategoria,
			apdModificadoAportesOK,
			apdRegistroDetalladoUltimo,
			@iRevision, 0
		FROM #AporteDetalladoTemporal_revision
		
			CREATE TABLE #MovimientoAporteTemporal_controlDuplicados (
			[moaId] [bigint] NOT NULL,
			[moaTipoAjuste] [varchar](20) NULL,
			[moaTipoMovimiento] [varchar](23) NULL,
			[moaEstadoAporte] [varchar](22) NULL,
			[moaValorAporte] [numeric](19, 5) NULL,
			[moaValorInteres] [numeric](19, 5) NULL,
			[moaFechaActualizacionEstado] [datetime] NULL,
			[moaFechaCreacion] [datetime] NULL,
			[moaAporteDetallado] [bigint] NULL,
			[moaAporteGeneral] [bigint] NOT NULL
		)
		
		INSERT INTO MovimientoAporte (
			moaTipoAjuste,
			moaTipoMovimiento,
			moaEstadoAporte,
			moaValorAporte,
			moaValorInteres,
			moaFechaActualizacionEstado,
			moaFechaCreacion,
			moaAporteDetallado,
			moaAporteGeneral
		)
		OUTPUT
			INSERTED.moaId,
			INSERTED.moaTipoAjuste,
			INSERTED.moaTipoMovimiento,
			INSERTED.moaEstadoAporte,
			INSERTED.moaValorAporte,
			INSERTED.moaValorInteres,
			INSERTED.moaFechaActualizacionEstado,
			INSERTED.moaFechaCreacion,
			INSERTED.moaAporteDetallado,
			INSERTED.moaAporteGeneral
		INTO #MovimientoAporteTemporal_controlDuplicados
		SELECT
	        null,--this.movimiento.setTipoAjuste(null);
			CASE 
	        	WHEN (apdModalidadRecaudoAporte = 'PILA') THEN 'RECAUDO_PILA_AUTOMATICO'
	        	WHEN (apdModalidadRecaudoAporte = 'MANUAL') THEN 'RECAUDO_MANUAL'
	        	WHEN (apdModalidadRecaudoAporte = 'PILA_MANUAL') THEN 'RECAUDO_MANUAL_APORTES'
	        END,--this.movimiento.setTipoMovimiento(aporte.getModalidadRecaudoAporte().getTipoMovimiento());
	        'VIGENTE',--moaEstadoAporte this.movimiento.setEstado(EstadoAporteEnum.VIGENTE);
	        apdAporteObligatorio,--this.movimiento.setAporte(this.getAporteDetallado().getAporteObligatorio());
	        apdValorIntMora,--this.movimiento.setInteres(this.getAporteDetallado().getValorMora());
	        @localDate,--this.movimiento.setFechaActualizacionEstado(new Date());
	        @localDate,--this.movimiento.setFechaCreacion(new Date());
			apdId,
			apdAporteGeneral
    	FROM #AporteDetalladoTemporal_revision
    	
		EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.aportes.MovimientoAporte', NULL, '', 'USUARIO_SISTEMA', @iRevision OUTPUT
		
		INSERT INTO aud.MovimientoAporte_aud(
			moaId,
			moaTipoAjuste,
			moaTipoMovimiento,
			moaEstadoAporte,
			moaValorAporte,
			moaValorInteres,
			moaFechaActualizacionEstado,
			moaFechaCreacion,
			moaAporteDetallado,
			moaAporteGeneral,
			REV, REVTYPE
		)
		SELECT
			moaId,
			moaTipoAjuste,
			moaTipoMovimiento,
			moaEstadoAporte,
			moaValorAporte,
			moaValorInteres,
			moaFechaActualizacionEstado,
			moaFechaCreacion,
			moaAporteDetallado,
			moaAporteGeneral,
			@iRevision, 0
		FROM #MovimientoAporteTemporal_controlDuplicados
		
		if exists (select * from #AporteDetalladoTemporal_revision)
		begin
		insert AportesControlDuplicados (apgPeriodoAporte, apgPersona, apgEmpresa, apgTipoSolicitante, apgEstadoAporteAportante ,apgRegistroGeneral, fechaIntento, comentario)
		select apgPeriodoAporte, apgPersona, apgEmpresa, apgTipoSolicitante, apgEstadoAporteAportante ,apgRegistroGeneral,
		CURRENT_TIMESTAMP AT TIME ZONE 'UTC' AT TIME ZONE 'SA Pacific Standard Time','Se insertaron registros detallados faltantes'
		from #AporteGeneralTtemporalControlDuplicados
		end 
		else 
		begin
		insert AportesControlDuplicados (apgPeriodoAporte, apgPersona, apgEmpresa, apgTipoSolicitante, apgEstadoAporteAportante ,apgRegistroGeneral, fechaIntento, comentario)
		select apgPeriodoAporte, apgPersona, apgEmpresa, apgTipoSolicitante, apgEstadoAporteAportante ,apgRegistroGeneral,
		CURRENT_TIMESTAMP AT TIME ZONE 'UTC' AT TIME ZONE 'SA Pacific Standard Time','registro duplicado ya existe'
		from #AporteGeneralTtemporalControlDuplicados
		end
   end
---***************************************************************** -----
---********** FINALIZA CONTROL PARA INSERTAR APORTES DETALLADOS FALTANTES Y QUE SE SUPONE LA PLANILLA ESTÁ DUPLICADA ***** -----
---***************************************************************** -----
------################################################################################################################################################################################################
---***************************************************************** -----
---********** INICIA CONTROL DUPLICADOS PARA LOS CASOS DE INDEPENDIENTES Y PENSIONADOS FALTANTES
---***************************************************************** -----
else if exists ( select b.apgPersona, ROW_NUMBER() over (partition by a.apgRegistroGeneral, a.apgPeriodoAporte, a.apgPersona, a.apgEmpresa, a.apgTipoSolicitante, a.apgEstadoAporteAportante order by a.apgId) as d, b.apgTipoSolicitante
		   from #AporteGeneralTtemporalControlDuplicados as b
		   left join AporteGeneral as a with (nolock) on a.apgRegistroGeneral = b.apgRegistroGeneral and a.apgPeriodoAporte = b.apgPeriodoAporte 
		   and   isnull(a.apgPersona,1) = isnull(b.apgPersona,1) and isnull(a.apgEmpresa,1) = isnull(b.apgEmpresa,1) and a.apgTipoSolicitante = b.apgTipoSolicitante and a.apgEstadoAporteAportante = b.apgEstadoAporteAportante
		   where b.apgTipoSolicitante <> 'EMPLEADOR')
begin
			;with aportFaltan as (
		   select b.apgPersona, dense_rank() over (partition by a.apgRegistroGeneral, a.apgPeriodoAporte, a.apgPersona, a.apgEmpresa, a.apgTipoSolicitante, a.apgEstadoAporteAportante order by a.apgId) as d, b.apgTipoSolicitante
		   from #AporteGeneralTtemporalControlDuplicados as b
		   left join AporteGeneral as a with (nolock) on a.apgRegistroGeneral = b.apgRegistroGeneral and a.apgPeriodoAporte = b.apgPeriodoAporte 
		   and   isnull(a.apgPersona,1) = isnull(b.apgPersona,1) and isnull(a.apgEmpresa,1) = isnull(b.apgEmpresa,1) and a.apgTipoSolicitante = b.apgTipoSolicitante and a.apgEstadoAporteAportante = b.apgEstadoAporteAportante
		   where b.apgTipoSolicitante <> 'EMPLEADOR' and a.apgId is null)
		   select *
		   into #aportFaltan
		   from aportFaltan
		   where d = 1

		   INSERT INTO AporteGeneral(
			apgPeriodoAporte
			,apgValTotalApoObligatorio
			,apgValorIntMora
			,apgFechaRecaudo
			,apgFechaProcesamiento
			,apgCodEntidadFinanciera
			,apgOperadorInformacion
			,apgModalidadPlanilla
			,apgModalidadRecaudoAporte
			,apgApoConDetalle
			,apgNumeroCuenta
			,apgRegistroGeneral
			,apgPersona
			,apgEmpresa
			,apgSucursalEmpresa
			,apgEstadoAportante
			,apgEstadoAporteAportante
			,apgEstadoRegistroAporteAportante
			,apgPagadorPorTerceros
			,apgTipoSolicitante
			,apgOrigenAporte
			,apgCajaCompensacion
			,apgEmailAportante
			,apgEmpresaTramitadoraAporte
			,apgFechaReconocimiento
			,apgFormaReconocimientoAporte
			,apgMarcaPeriodo
			,apgMarcaActualizacionCartera
			,apgConciliado
			,apgNumeroPlanillaManual
			,apgEnProcesoReconocimiento,
			apgRegistroGeneralUltimo
		)
		OUTPUT 
			INSERTED.apgId
			,INSERTED.apgPeriodoAporte
			,INSERTED.apgValTotalApoObligatorio
			,INSERTED.apgValorIntMora
			,INSERTED.apgFechaRecaudo
			,INSERTED.apgFechaProcesamiento
			,INSERTED.apgCodEntidadFinanciera
			,INSERTED.apgOperadorInformacion
			,INSERTED.apgModalidadPlanilla
			,INSERTED.apgModalidadRecaudoAporte
			,INSERTED.apgApoConDetalle
			,INSERTED.apgNumeroCuenta
			,INSERTED.apgRegistroGeneral
			,INSERTED.apgPersona
			,INSERTED.apgEmpresa
			,INSERTED.apgSucursalEmpresa
			,INSERTED.apgEstadoAportante
			,INSERTED.apgEstadoAporteAportante
			,INSERTED.apgEstadoRegistroAporteAportante
			,INSERTED.apgPagadorPorTerceros
			,INSERTED.apgTipoSolicitante
			,INSERTED.apgOrigenAporte
			,INSERTED.apgCajaCompensacion
			,INSERTED.apgEmailAportante
			,INSERTED.apgEmpresaTramitadoraAporte
			,INSERTED.apgFechaReconocimiento
			,INSERTED.apgFormaReconocimientoAporte
			,INSERTED.apgMarcaPeriodo
			,INSERTED.apgMarcaActualizacionCartera
			,INSERTED.apgConciliado
			,INSERTED.apgNumeroPlanillaManual
			,INSERTED.apgEnProcesoReconocimiento
			,INSERTED.apgRegistroGeneralUltimo
		INTO #AporteGeneralTtemporal
		select apg.apgPeriodoAporte
			,apg.apgValTotalApoObligatorio
			,apg.apgValorIntMora
			,apg.apgFechaRecaudo
			,apg.apgFechaProcesamiento
			,apg.apgCodEntidadFinanciera
			,apg.apgOperadorInformacion
			,apg.apgModalidadPlanilla
			,apg.apgModalidadRecaudoAporte
			,apg.apgApoConDetalle
			,apg.apgNumeroCuenta
			,apg.apgRegistroGeneral
			,apg.apgPersona
			,apg.apgEmpresa
			,apg.apgSucursalEmpresa
			,apg.apgEstadoAportante
			,apg.apgEstadoAporteAportante
			,apg.apgEstadoRegistroAporteAportante
			,apg.apgPagadorPorTerceros
			,apg.apgTipoSolicitante
			,apg.apgOrigenAporte
			,apg.apgCajaCompensacion
			,apg.apgEmailAportante
			,apg.apgEmpresaTramitadoraAporte
			,apg.apgFechaReconocimiento
			,apg.apgFormaReconocimientoAporte
			,apg.apgMarcaPeriodo
			,apg.apgMarcaActualizacionCartera
			,apg.apgConciliado
			,apg.apgNumeroPlanillaManual
			,apg.apgEnProcesoReconocimiento
			,apg.apgRegistroGeneralUltimo
		from #AporteGeneralTtemporalControlDuplicados as apg --where apgPersona = (select apgPersona from #aportFaltan)
		inner join #aportFaltan on apg.apgPersona = #aportFaltan.apgPersona

		EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.aportes.AporteGeneral', NULL, '', 'USUARIO_SISTEMA', @iRevision OUTPUT
		
		INSERT INTO aud.AporteGeneral_aud(
			apgId
			,apgPeriodoAporte
			,apgValTotalApoObligatorio
			,apgValorIntMora
			,apgFechaRecaudo
			,apgFechaProcesamiento
			,apgCodEntidadFinanciera
			,apgOperadorInformacion
			,apgModalidadPlanilla
			,apgModalidadRecaudoAporte
			,apgApoConDetalle
			,apgNumeroCuenta
			,apgRegistroGeneral
			,apgPersona
			,apgEmpresa
			,apgSucursalEmpresa
			,apgEstadoAportante
			,apgEstadoAporteAportante
			,apgEstadoRegistroAporteAportante
			,apgPagadorPorTerceros
			,apgTipoSolicitante
			,apgOrigenAporte
			,apgCajaCompensacion
			,apgEmailAportante
			,apgEmpresaTramitadoraAporte
			,apgFechaReconocimiento
			,apgFormaReconocimientoAporte
			,apgMarcaPeriodo
			,apgMarcaActualizacionCartera
			,apgConciliado
			,apgNumeroPlanillaManual
			,apgEnProcesoReconocimiento
			,apgRegistroGeneralUltimo
			,REV, REVTYPE
		)
		SELECT
			apgid
			,apgPeriodoAporte
			,apgValTotalApoObligatorio
			,apgValorIntMora
			,apgFechaRecaudo
			,apgFechaProcesamiento
			,apgCodEntidadFinanciera
			,apgOperadorInformacion
			,apgModalidadPlanilla
			,apgModalidadRecaudoAporte
			,apgApoConDetalle
			,apgNumeroCuenta
			,apgRegistroGeneral
			,apgPersona
			,apgEmpresa
			,apgSucursalEmpresa
			,apgEstadoAportante
			,apgEstadoAporteAportante
			,apgEstadoRegistroAporteAportante
			,apgPagadorPorTerceros
			,apgTipoSolicitante
			,apgOrigenAporte
			,apgCajaCompensacion
			,apgEmailAportante
			,apgEmpresaTramitadoraAporte
			,apgFechaReconocimiento
			,apgFormaReconocimientoAporte
			,apgMarcaPeriodo
			,apgMarcaActualizacionCartera
			,apgConciliado
			,apgNumeroPlanillaManual
			,apgEnProcesoReconocimiento
			,apgRegistroGeneralUltimo
			,@iRevision, 0
		FROM #AporteGeneralTtemporal
		
		---------------------------------------------------
		---------------------------------------------------
		-- APORTE DETALLADO
		---------------------------------------------------
		---------------------------------------------------
		CREATE TABLE #AporteGeneralIdPorTranaccion_Faltan (
			aptId BIGINT, 
			aptIdTransaccion BIGINT
		)
		CREATE CLUSTERED INDEX ix_Copiar_Aprotes_AporteGeneralIdPorTranaccion ON #AporteGeneralIdPorTranaccion_Faltan (aptIdTransaccion);
		
		INSERT INTO #AporteGeneralIdPorTranaccion_Faltan (
			aptId, 
			aptIdTransaccion
		)
		SELECT apgId, eacIdTransaccion
		FROM #AporteGeneralTtemporal
		INNER JOIN #EstadoAportanteCotizante eacPer ON  apgPersona = eacPer.eacPerid
		WHERE apgEmpresa IS NULL
		GROUP BY apgId, eacIdTransaccion
		
		INSERT INTO #AporteGeneralIdPorTranaccion_Faltan (
			aptId, 
			aptIdTransaccion
		)
		SELECT apgId, eacIdTransaccion
		FROM #AporteGeneralTtemporal
		INNER JOIN #EstadoAportanteCotizante ON  apgEmpresa = eacEmpId
		WHERE apgPersona IS NULL
		GROUP BY apgId, eacIdTransaccion
		
		
		CREATE TABLE #AporteDetalladoTemporal_Faltan (
			[apdId] [bigint] NOT NULL,
			[apdAporteGeneral] [bigint] NULL,
			[apdDiasCotizados] [smallint] NULL,
			[apdHorasLaboradas] [smallint] NULL,
			[apdSalarioBasico] [numeric](19, 5) NULL,
			[apdValorIBC] [numeric](19, 5) NULL,
			[apdValorIntMora] [numeric](19, 5) NULL,
			[apdTarifa] [numeric](5, 5) NULL,
			[apdAporteObligatorio] [numeric](19, 5) NULL,
			[apdValorSaldoAporte] [numeric](19, 5) NULL,
			[apdCorrecciones] [varchar](400) NULL,
			[apdEstadoAporteRecaudo] [varchar](50) NULL,
			[apdEstadoAporteAjuste] [varchar](50) NULL,
			[apdEstadoRegistroAporte] [varchar](50) NULL,
			[apdSalarioIntegral] [bit] NULL,
			[apdMunicipioLaboral] [varchar](5) NULL,
			[apdDepartamentoLaboral] [smallint] NULL,
			[apdRegistroDetallado] [bigint] NOT NULL,
			[apdTipoCotizante] [varchar](100) NULL,
			[apdEstadoCotizante] [varchar](60) NULL,
			[apdEstadoAporteCotizante] [varchar](50) NULL,
			[apdEstadoRegistroAporteCotizante] [varchar](40) NULL,
			[apdPersona] [bigint] NULL,
			[apdUsuarioAprobadorAporte] [varchar](50) NOT NULL,
			[apdEstadoRegistroAporteArchivo] [varchar](60) NOT NULL,
			[apdCodSucursal] [varchar](10) NULL,
			[apdNomSucursal] [varchar](100) NULL,
			[apdFechaMovimiento] [date] NULL,
			[apdFechaCreacion] [date] NULL,
			[apdFormaReconocimientoAporte] [varchar](75) NULL,
			[apdMarcaPeriodo] [varchar](19) NULL,
			[apdModalidadRecaudoAporte] [varchar](40) NULL,
			[apdMarcaCalculoCategoria] [bit] NULL,
			[apdModificadoAportesOK] [bit] NULL,
			[apdRegistroDetalladoUltimo] bigint
		)
		
		INSERT INTO AporteDetallado (
			apdAporteGeneral,
			apdDiasCotizados,
			apdHorasLaboradas,
			apdSalarioBasico,
			apdValorIBC,
			apdValorIntMora,
			apdTarifa,
			apdAporteObligatorio,
			apdValorSaldoAporte,
			apdCorrecciones,
			apdEstadoAporteRecaudo,
			apdEstadoAporteAjuste,
			apdEstadoRegistroAporte,
			apdSalarioIntegral,
			apdMunicipioLaboral,
			apdDepartamentoLaboral,
			apdRegistroDetallado,
			apdTipoCotizante,
			apdEstadoCotizante,
			apdEstadoAporteCotizante,
			apdEstadoRegistroAporteCotizante,
			apdPersona,
			apdUsuarioAprobadorAporte,
			apdEstadoRegistroAporteArchivo,
			apdCodSucursal,
			apdNomSucursal,
			apdFechaMovimiento,
			apdFechaCreacion,
			apdFormaReconocimientoAporte,
			apdMarcaPeriodo,
			apdModalidadRecaudoAporte,
			apdMarcaCalculoCategoria,
			apdModificadoAportesOK,
			apdRegistroDetalladoUltimo
		)
		OUTPUT
			INSERTED.apdId,
			INSERTED.apdAporteGeneral,
			INSERTED.apdDiasCotizados,
			INSERTED.apdHorasLaboradas,
			INSERTED.apdSalarioBasico,
			INSERTED.apdValorIBC,
			INSERTED.apdValorIntMora,
			INSERTED.apdTarifa,
			INSERTED.apdAporteObligatorio,
			INSERTED.apdValorSaldoAporte,
			INSERTED.apdCorrecciones,
			INSERTED.apdEstadoAporteRecaudo,
			INSERTED.apdEstadoAporteAjuste,
			INSERTED.apdEstadoRegistroAporte,
			INSERTED.apdSalarioIntegral,
			INSERTED.apdMunicipioLaboral,
			INSERTED.apdDepartamentoLaboral,
			INSERTED.apdRegistroDetallado,
			INSERTED.apdTipoCotizante,
			INSERTED.apdEstadoCotizante,
			INSERTED.apdEstadoAporteCotizante,
			INSERTED.apdEstadoRegistroAporteCotizante,
			INSERTED.apdPersona,
			INSERTED.apdUsuarioAprobadorAporte,
			INSERTED.apdEstadoRegistroAporteArchivo,
			INSERTED.apdCodSucursal,
			INSERTED.apdNomSucursal,
			INSERTED.apdFechaMovimiento,
			INSERTED.apdFechaCreacion,
			INSERTED.apdFormaReconocimientoAporte,
			INSERTED.apdMarcaPeriodo,
			INSERTED.apdModalidadRecaudoAporte,
			INSERTED.apdMarcaCalculoCategoria,
			INSERTED.apdModificadoAportesOK,
			INSERTED.apdRegistroDetalladoUltimo
		INTO #AporteDetalladoTemporal_Faltan
		SELECT
			aptId,
			temDiasCotizados,
			temHorasLaboradas,
			temSalarioBasico,
			temValorIBC,
			temValorIntMoraDetalle,
			temTarifa,
			temAporteObligatorio,
			temValorSaldoAporte,
			temCorrecciones,
			temEstadoAporteRecaudo,
			temEstadoAporteAjuste,
			temEstadoRegistroAporte,
			temSalarioIntegral,
			temMunicipioLaboral,
			temDepartamentoLaboral,
			temIdTransaccion,
			tctTipoCotizante,
			CASE WHEN (eacEstadoCotizante IS NOT NULL ) THEN eacEstadoCotizante ELSE 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' END,
			temEstadoAporteRecaudo,
			temEstadoRegistroAporte,
			perId,
			'SISTEMA',
			--temEstadoValRegistroAporte,
			isnull((select redOUTEstadoRegistroAporte from @ajusteVal where redId = tem.temRegistroDetallado), temEstadoValRegistroAporte) as temEstadoValRegistroAporte,
			suc.sucCodSucursal,
			suc.sucNomSucursal,
			@localDate,--TODO: solo si hay movimientos
			@localDate,
			temFormaReconocimientoAporte,
			eacMarcaPeriodo,
			temModalidadRecaudoAporte,
			1,
			NULL, -- SIEMPRE?
			temIdTransaccion
		FROM pila.TemAporte tem
		INNER JOIN pila.TemCotizante ON tctIdTransaccion = tem.temIdTransaccion
		INNER JOIN Persona with (nolock) ON temTipoIdCotizante = perTipoIdentificacion AND temNumeroIdCotizante = perNumeroIdentificacion
		INNER JOIN #AporteGeneralIdPorTranaccion_Faltan ON aptIdTransaccion = temIdTransaccion
		INNER JOIN #Sucursal suc ON suc.sucIdTransaccion = tem.temIdTransaccion 
		LEFT JOIN #EstadoAportanteCotizante ON temIdTransaccion = eacIdTransaccion
		WHERE temRegistroGeneral = @idRegistroGeneral
		
		
		EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.aportes.AporteDetallado', NULL, '', 'USUARIO_SISTEMA', @iRevision OUTPUT
		
		INSERT INTO aud.AporteDetallado_aud(
			apdId,
			apdAporteGeneral,
			apdDiasCotizados,
			apdHorasLaboradas,
			apdSalarioBasico,
			apdValorIBC,
			apdValorIntMora,
			apdTarifa,
			apdAporteObligatorio,
			apdValorSaldoAporte,
			apdCorrecciones,
			apdEstadoAporteRecaudo,
			apdEstadoAporteAjuste,
			apdEstadoRegistroAporte,
			apdSalarioIntegral,
			apdMunicipioLaboral,
			apdDepartamentoLaboral,
			apdRegistroDetallado,
			apdTipoCotizante,
			apdEstadoCotizante,
			apdEstadoAporteCotizante,
			apdEstadoRegistroAporteCotizante,
			apdPersona,
			apdUsuarioAprobadorAporte,
			apdEstadoRegistroAporteArchivo,
			apdCodSucursal,
			apdNomSucursal,
			apdFechaMovimiento,
			apdFechaCreacion,
			apdFormaReconocimientoAporte,
			apdMarcaPeriodo,
			apdModalidadRecaudoAporte,
			apdMarcaCalculoCategoria,
			apdModificadoAportesOK,
			apdRegistroDetalladoUltimo,
			REV, REVTYPE
		)
		SELECT 
			apdId,
			apdAporteGeneral,
			apdDiasCotizados,
			apdHorasLaboradas,
			apdSalarioBasico,
			apdValorIBC,
			apdValorIntMora,
			apdTarifa,
			apdAporteObligatorio,
			apdValorSaldoAporte,
			apdCorrecciones,
			apdEstadoAporteRecaudo,
			apdEstadoAporteAjuste,
			apdEstadoRegistroAporte,
			apdSalarioIntegral,
			apdMunicipioLaboral,
			apdDepartamentoLaboral,
			apdRegistroDetallado,
			apdTipoCotizante,
			apdEstadoCotizante,
			apdEstadoAporteCotizante,
			apdEstadoRegistroAporteCotizante,
			apdPersona,
			apdUsuarioAprobadorAporte,
			apdEstadoRegistroAporteArchivo,
			apdCodSucursal,
			apdNomSucursal,
			apdFechaMovimiento,
			apdFechaCreacion,
			apdFormaReconocimientoAporte,
			apdMarcaPeriodo,
			apdModalidadRecaudoAporte,
			apdMarcaCalculoCategoria,
			apdModificadoAportesOK,
			apdRegistroDetalladoUltimo,
			@iRevision, 0
		FROM #AporteDetalladoTemporal_Faltan
		
		
		---------------------------------------------------
		---------------------------------------------------
		-- APORTE DETALLADO
		---------------------------------------------------
		---------------------------------------------------
		CREATE TABLE #MovimientoAporteTemporal_Faltan (
			[moaId] [bigint] NOT NULL,
			[moaTipoAjuste] [varchar](20) NULL,
			[moaTipoMovimiento] [varchar](23) NULL,
			[moaEstadoAporte] [varchar](22) NULL,
			[moaValorAporte] [numeric](19, 5) NULL,
			[moaValorInteres] [numeric](19, 5) NULL,
			[moaFechaActualizacionEstado] [datetime] NULL,
			[moaFechaCreacion] [datetime] NULL,
			[moaAporteDetallado] [bigint] NULL,
			[moaAporteGeneral] [bigint] NOT NULL
		)
		
		INSERT INTO MovimientoAporte (
			moaTipoAjuste,
			moaTipoMovimiento,
			moaEstadoAporte,
			moaValorAporte,
			moaValorInteres,
			moaFechaActualizacionEstado,
			moaFechaCreacion,
			moaAporteDetallado,
			moaAporteGeneral
		)
		OUTPUT
			INSERTED.moaId,
			INSERTED.moaTipoAjuste,
			INSERTED.moaTipoMovimiento,
			INSERTED.moaEstadoAporte,
			INSERTED.moaValorAporte,
			INSERTED.moaValorInteres,
			INSERTED.moaFechaActualizacionEstado,
			INSERTED.moaFechaCreacion,
			INSERTED.moaAporteDetallado,
			INSERTED.moaAporteGeneral
		INTO #MovimientoAporteTemporal_Faltan
		SELECT
	        null,--this.movimiento.setTipoAjuste(null);
			CASE 
	        	WHEN (apdModalidadRecaudoAporte = 'PILA') THEN 'RECAUDO_PILA_AUTOMATICO'
	        	WHEN (apdModalidadRecaudoAporte = 'MANUAL') THEN 'RECAUDO_MANUAL'
	        	WHEN (apdModalidadRecaudoAporte = 'PILA_MANUAL') THEN 'RECAUDO_MANUAL_APORTES'
	        END,--this.movimiento.setTipoMovimiento(aporte.getModalidadRecaudoAporte().getTipoMovimiento());
	        'VIGENTE',--moaEstadoAporte this.movimiento.setEstado(EstadoAporteEnum.VIGENTE);
	        apdAporteObligatorio,--this.movimiento.setAporte(this.getAporteDetallado().getAporteObligatorio());
	        apdValorIntMora,--this.movimiento.setInteres(this.getAporteDetallado().getValorMora());
	        @localDate,--this.movimiento.setFechaActualizacionEstado(new Date());
	        @localDate,--this.movimiento.setFechaCreacion(new Date());
			apdId,
			apdAporteGeneral
    	FROM #AporteDetalladoTemporal_Faltan
    	
		EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.aportes.MovimientoAporte', NULL, '', 'USUARIO_SISTEMA', @iRevision OUTPUT
		
		INSERT INTO aud.MovimientoAporte_aud(
			moaId,
			moaTipoAjuste,
			moaTipoMovimiento,
			moaEstadoAporte,
			moaValorAporte,
			moaValorInteres,
			moaFechaActualizacionEstado,
			moaFechaCreacion,
			moaAporteDetallado,
			moaAporteGeneral,
			REV, REVTYPE
		)
		SELECT
			moaId,
			moaTipoAjuste,
			moaTipoMovimiento,
			moaEstadoAporte,
			moaValorAporte,
			moaValorInteres,
			moaFechaActualizacionEstado,
			moaFechaCreacion,
			moaAporteDetallado,
			moaAporteGeneral,
			@iRevision, 0
		FROM #MovimientoAporteTemporal_Faltan

end
---***************************************************************** -----
---********** FINALIZA CONTROL DUPLICADOS PARA LOS CASOS DE INDEPENDIENTES Y PENSIONADOS FALTANTES
---***************************************************************** -----
------################################################################################################################################################################################################
else
	begin
		insert AportesControlDuplicados (apgPeriodoAporte, apgPersona, apgEmpresa, apgTipoSolicitante, apgEstadoAporteAportante ,apgRegistroGeneral, fechaIntento, comentario)
		select apgPeriodoAporte, apgPersona, apgEmpresa, apgTipoSolicitante, apgEstadoAporteAportante ,apgRegistroGeneral,
		CURRENT_TIMESTAMP AT TIME ZONE 'UTC' AT TIME ZONE 'SA Pacific Standard Time','Borrado de duplicado, para no insertarlo después de la validación.'
		from #AporteGeneralTtemporalControlDuplicados 
		group by apgRegistroGeneral, apgPeriodoAporte, apgPersona, apgEmpresa, apgTipoSolicitante, apgEstadoAporteAportante
		having count(*) > 1

		;with borradoDuplicadosTemporal as (
		select *, dense_rank() over (partition by apgRegistroGeneral, apgPeriodoAporte, apgPersona, apgEmpresa, apgTipoSolicitante, apgEstadoAporteAportante order by id) as d
		from #AporteGeneralTtemporalControlDuplicados
		)
		delete from borradoDuplicadosTemporal where d > 1
		
		if exists (select 1 
				   from #AporteGeneralTtemporalControlDuplicados as a
				   inner join AporteGeneral as apg with(nolock) on apg.apgEmpresa = a.apgEmpresa and apg.apgRegistroGeneral = a.apgRegistroGeneral
				   where a.apgTipoSolicitante = 'EMPLEADOR'
					)
			begin
				   delete a 
				   from #AporteGeneralTtemporalControlDuplicados as a
				   inner join AporteGeneral as apg with(nolock) on apg.apgEmpresa = a.apgEmpresa and apg.apgRegistroGeneral = a.apgRegistroGeneral
				   where a.apgTipoSolicitante = 'EMPLEADOR'
			end

		INSERT INTO AporteGeneral(
			apgPeriodoAporte
			,apgValTotalApoObligatorio
			,apgValorIntMora
			,apgFechaRecaudo
			,apgFechaProcesamiento
			,apgCodEntidadFinanciera
			,apgOperadorInformacion
			,apgModalidadPlanilla
			,apgModalidadRecaudoAporte
			,apgApoConDetalle
			,apgNumeroCuenta
			,apgRegistroGeneral
			,apgPersona
			,apgEmpresa
			,apgSucursalEmpresa
			,apgEstadoAportante
			,apgEstadoAporteAportante
			,apgEstadoRegistroAporteAportante
			,apgPagadorPorTerceros
			,apgTipoSolicitante
			,apgOrigenAporte
			,apgCajaCompensacion
			,apgEmailAportante
			,apgEmpresaTramitadoraAporte
			,apgFechaReconocimiento
			,apgFormaReconocimientoAporte
			,apgMarcaPeriodo
			,apgMarcaActualizacionCartera
			,apgConciliado
			,apgNumeroPlanillaManual
			,apgEnProcesoReconocimiento,
			apgRegistroGeneralUltimo
		)
		OUTPUT 
			INSERTED.apgId
			,INSERTED.apgPeriodoAporte
			,INSERTED.apgValTotalApoObligatorio
			,INSERTED.apgValorIntMora
			,INSERTED.apgFechaRecaudo
			,INSERTED.apgFechaProcesamiento
			,INSERTED.apgCodEntidadFinanciera
			,INSERTED.apgOperadorInformacion
			,INSERTED.apgModalidadPlanilla
			,INSERTED.apgModalidadRecaudoAporte
			,INSERTED.apgApoConDetalle
			,INSERTED.apgNumeroCuenta
			,INSERTED.apgRegistroGeneral
			,INSERTED.apgPersona
			,INSERTED.apgEmpresa
			,INSERTED.apgSucursalEmpresa
			,INSERTED.apgEstadoAportante
			,INSERTED.apgEstadoAporteAportante
			,INSERTED.apgEstadoRegistroAporteAportante
			,INSERTED.apgPagadorPorTerceros
			,INSERTED.apgTipoSolicitante
			,INSERTED.apgOrigenAporte
			,INSERTED.apgCajaCompensacion
			,INSERTED.apgEmailAportante
			,INSERTED.apgEmpresaTramitadoraAporte
			,INSERTED.apgFechaReconocimiento
			,INSERTED.apgFormaReconocimientoAporte
			,INSERTED.apgMarcaPeriodo
			,INSERTED.apgMarcaActualizacionCartera
			,INSERTED.apgConciliado
			,INSERTED.apgNumeroPlanillaManual
			,INSERTED.apgEnProcesoReconocimiento
			,INSERTED.apgRegistroGeneralUltimo
		INTO #AporteGeneralTtemporal
		select apgPeriodoAporte
			,apgValTotalApoObligatorio
			,apgValorIntMora
			,apgFechaRecaudo
			,apgFechaProcesamiento
			,apgCodEntidadFinanciera
			,apgOperadorInformacion
			,apgModalidadPlanilla
			,apgModalidadRecaudoAporte
			,apgApoConDetalle
			,apgNumeroCuenta
			,apgRegistroGeneral
			,apgPersona
			,apgEmpresa
			,apgSucursalEmpresa
			,apgEstadoAportante
			,apgEstadoAporteAportante
			,apgEstadoRegistroAporteAportante
			,apgPagadorPorTerceros
			,apgTipoSolicitante
			,apgOrigenAporte
			,apgCajaCompensacion
			,apgEmailAportante
			,apgEmpresaTramitadoraAporte
			,apgFechaReconocimiento
			,apgFormaReconocimientoAporte
			,apgMarcaPeriodo
			,apgMarcaActualizacionCartera
			,apgConciliado
			,apgNumeroPlanillaManual
			,apgEnProcesoReconocimiento,
			apgRegistroGeneralUltimo
		from #AporteGeneralTtemporalControlDuplicados
		

		EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.aportes.AporteGeneral', NULL, '', 'USUARIO_SISTEMA', @iRevision OUTPUT
		
		INSERT INTO aud.AporteGeneral_aud(
			apgId
			,apgPeriodoAporte
			,apgValTotalApoObligatorio
			,apgValorIntMora
			,apgFechaRecaudo
			,apgFechaProcesamiento
			,apgCodEntidadFinanciera
			,apgOperadorInformacion
			,apgModalidadPlanilla
			,apgModalidadRecaudoAporte
			,apgApoConDetalle
			,apgNumeroCuenta
			,apgRegistroGeneral
			,apgPersona
			,apgEmpresa
			,apgSucursalEmpresa
			,apgEstadoAportante
			,apgEstadoAporteAportante
			,apgEstadoRegistroAporteAportante
			,apgPagadorPorTerceros
			,apgTipoSolicitante
			,apgOrigenAporte
			,apgCajaCompensacion
			,apgEmailAportante
			,apgEmpresaTramitadoraAporte
			,apgFechaReconocimiento
			,apgFormaReconocimientoAporte
			,apgMarcaPeriodo
			,apgMarcaActualizacionCartera
			,apgConciliado
			,apgNumeroPlanillaManual
			,apgEnProcesoReconocimiento
			,apgRegistroGeneralUltimo
			,REV, REVTYPE
		)
		SELECT
			apgid
			,apgPeriodoAporte
			,apgValTotalApoObligatorio
			,apgValorIntMora
			,apgFechaRecaudo
			,apgFechaProcesamiento
			,apgCodEntidadFinanciera
			,apgOperadorInformacion
			,apgModalidadPlanilla
			,apgModalidadRecaudoAporte
			,apgApoConDetalle
			,apgNumeroCuenta
			,apgRegistroGeneral
			,apgPersona
			,apgEmpresa
			,apgSucursalEmpresa
			,apgEstadoAportante
			,apgEstadoAporteAportante
			,apgEstadoRegistroAporteAportante
			,apgPagadorPorTerceros
			,apgTipoSolicitante
			,apgOrigenAporte
			,apgCajaCompensacion
			,apgEmailAportante
			,apgEmpresaTramitadoraAporte
			,apgFechaReconocimiento
			,apgFormaReconocimientoAporte
			,apgMarcaPeriodo
			,apgMarcaActualizacionCartera
			,apgConciliado
			,apgNumeroPlanillaManual
			,apgEnProcesoReconocimiento
			,apgRegistroGeneralUltimo
			,@iRevision, 0
		FROM #AporteGeneralTtemporal
		
		---------------------------------------------------
		---------------------------------------------------
		-- APORTE DETALLADO
		---------------------------------------------------
		---------------------------------------------------
		CREATE TABLE #AporteGeneralIdPorTranaccion (
			aptId BIGINT, 
			aptIdTransaccion BIGINT
		)
		CREATE CLUSTERED INDEX ix_Copiar_Aprotes_AporteGeneralIdPorTranaccion ON #AporteGeneralIdPorTranaccion (aptIdTransaccion);
		
		INSERT INTO #AporteGeneralIdPorTranaccion (
			aptId, 
			aptIdTransaccion
		)
		SELECT apgId, eacIdTransaccion
		FROM #AporteGeneralTtemporal
		INNER JOIN #EstadoAportanteCotizante eacPer ON  apgPersona = eacPer.eacPerid
		WHERE apgEmpresa IS NULL
		GROUP BY apgId, eacIdTransaccion
		
		INSERT INTO #AporteGeneralIdPorTranaccion (
			aptId, 
			aptIdTransaccion
		)
		SELECT apgId, eacIdTransaccion
		FROM #AporteGeneralTtemporal
		INNER JOIN #EstadoAportanteCotizante ON  apgEmpresa = eacEmpId
		WHERE apgPersona IS NULL
		GROUP BY apgId, eacIdTransaccion
		
		
		CREATE TABLE #AporteDetalladoTemporal (
			[apdId] [bigint] NOT NULL,
			[apdAporteGeneral] [bigint] NULL,
			[apdDiasCotizados] [smallint] NULL,
			[apdHorasLaboradas] [smallint] NULL,
			[apdSalarioBasico] [numeric](19, 5) NULL,
			[apdValorIBC] [numeric](19, 5) NULL,
			[apdValorIntMora] [numeric](19, 5) NULL,
			[apdTarifa] [numeric](5, 5) NULL,
			[apdAporteObligatorio] [numeric](19, 5) NULL,
			[apdValorSaldoAporte] [numeric](19, 5) NULL,
			[apdCorrecciones] [varchar](400) NULL,
			[apdEstadoAporteRecaudo] [varchar](50) NULL,
			[apdEstadoAporteAjuste] [varchar](50) NULL,
			[apdEstadoRegistroAporte] [varchar](50) NULL,
			[apdSalarioIntegral] [bit] NULL,
			[apdMunicipioLaboral] [varchar](5) NULL,
			[apdDepartamentoLaboral] [smallint] NULL,
			[apdRegistroDetallado] [bigint] NOT NULL,
			[apdTipoCotizante] [varchar](100) NULL,
			[apdEstadoCotizante] [varchar](60) NULL,
			[apdEstadoAporteCotizante] [varchar](50) NULL,
			[apdEstadoRegistroAporteCotizante] [varchar](40) NULL,
			[apdPersona] [bigint] NULL,
			[apdUsuarioAprobadorAporte] [varchar](50) NOT NULL,
			[apdEstadoRegistroAporteArchivo] [varchar](60) NOT NULL,
			[apdCodSucursal] [varchar](10) NULL,
			[apdNomSucursal] [varchar](100) NULL,
			[apdFechaMovimiento] [date] NULL,
			[apdFechaCreacion] [date] NULL,
			[apdFormaReconocimientoAporte] [varchar](75) NULL,
			[apdMarcaPeriodo] [varchar](19) NULL,
			[apdModalidadRecaudoAporte] [varchar](40) NULL,
			[apdMarcaCalculoCategoria] [bit] NULL,
			[apdModificadoAportesOK] [bit] NULL,
			[apdRegistroDetalladoUltimo] bigint
		)
		
		INSERT INTO AporteDetallado (
			apdAporteGeneral,
			apdDiasCotizados,
			apdHorasLaboradas,
			apdSalarioBasico,
			apdValorIBC,
			apdValorIntMora,
			apdTarifa,
			apdAporteObligatorio,
			apdValorSaldoAporte,
			apdCorrecciones,
			apdEstadoAporteRecaudo,
			apdEstadoAporteAjuste,
			apdEstadoRegistroAporte,
			apdSalarioIntegral,
			apdMunicipioLaboral,
			apdDepartamentoLaboral,
			apdRegistroDetallado,
			apdTipoCotizante,
			apdEstadoCotizante,
			apdEstadoAporteCotizante,
			apdEstadoRegistroAporteCotizante,
			apdPersona,
			apdUsuarioAprobadorAporte,
			apdEstadoRegistroAporteArchivo,
			apdCodSucursal,
			apdNomSucursal,
			apdFechaMovimiento,
			apdFechaCreacion,
			apdFormaReconocimientoAporte,
			apdMarcaPeriodo,
			apdModalidadRecaudoAporte,
			apdMarcaCalculoCategoria,
			apdModificadoAportesOK,
			apdRegistroDetalladoUltimo
		)
		OUTPUT
			INSERTED.apdId,
			INSERTED.apdAporteGeneral,
			INSERTED.apdDiasCotizados,
			INSERTED.apdHorasLaboradas,
			INSERTED.apdSalarioBasico,
			INSERTED.apdValorIBC,
			INSERTED.apdValorIntMora,
			INSERTED.apdTarifa,
			INSERTED.apdAporteObligatorio,
			INSERTED.apdValorSaldoAporte,
			INSERTED.apdCorrecciones,
			INSERTED.apdEstadoAporteRecaudo,
			INSERTED.apdEstadoAporteAjuste,
			INSERTED.apdEstadoRegistroAporte,
			INSERTED.apdSalarioIntegral,
			INSERTED.apdMunicipioLaboral,
			INSERTED.apdDepartamentoLaboral,
			INSERTED.apdRegistroDetallado,
			INSERTED.apdTipoCotizante,
			INSERTED.apdEstadoCotizante,
			INSERTED.apdEstadoAporteCotizante,
			INSERTED.apdEstadoRegistroAporteCotizante,
			INSERTED.apdPersona,
			INSERTED.apdUsuarioAprobadorAporte,
			INSERTED.apdEstadoRegistroAporteArchivo,
			INSERTED.apdCodSucursal,
			INSERTED.apdNomSucursal,
			INSERTED.apdFechaMovimiento,
			INSERTED.apdFechaCreacion,
			INSERTED.apdFormaReconocimientoAporte,
			INSERTED.apdMarcaPeriodo,
			INSERTED.apdModalidadRecaudoAporte,
			INSERTED.apdMarcaCalculoCategoria,
			INSERTED.apdModificadoAportesOK,
			INSERTED.apdRegistroDetalladoUltimo
		INTO #AporteDetalladoTemporal
		SELECT
			aptId,
			temDiasCotizados,
			temHorasLaboradas,
			temSalarioBasico,
			temValorIBC,
			temValorIntMoraDetalle,
			temTarifa,
			temAporteObligatorio,
			temValorSaldoAporte,
			temCorrecciones,
			temEstadoAporteRecaudo,
			temEstadoAporteAjuste,
			temEstadoRegistroAporte,
			temSalarioIntegral,
			temMunicipioLaboral,
			temDepartamentoLaboral,
			temIdTransaccion,
			tctTipoCotizante,
			CASE WHEN (eacEstadoCotizante IS NOT NULL ) THEN eacEstadoCotizante ELSE 'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' END,
			temEstadoAporteRecaudo,
			temEstadoRegistroAporte,
			perId,
			'SISTEMA',
			--temEstadoValRegistroAporte,
			isnull((select redOUTEstadoRegistroAporte from @ajusteVal where redId = tem.temRegistroDetallado), temEstadoValRegistroAporte) as temEstadoValRegistroAporte,
			suc.sucCodSucursal,
			suc.sucNomSucursal,
			@localDate,--TODO: solo si hay movimientos
			@localDate,
			temFormaReconocimientoAporte,
			eacMarcaPeriodo,
			temModalidadRecaudoAporte,
			1,
			NULL, -- SIEMPRE?
			temIdTransaccion
		FROM pila.TemAporte tem
		INNER JOIN pila.TemCotizante ON tctIdTransaccion = tem.temIdTransaccion
		INNER JOIN Persona with (nolock) ON temTipoIdCotizante = perTipoIdentificacion AND temNumeroIdCotizante = perNumeroIdentificacion
		INNER JOIN #AporteGeneralIdPorTranaccion ON aptIdTransaccion = temIdTransaccion
		INNER JOIN #Sucursal suc ON suc.sucIdTransaccion = tem.temIdTransaccion 
		LEFT JOIN #EstadoAportanteCotizante ON temIdTransaccion = eacIdTransaccion
		WHERE temRegistroGeneral = @idRegistroGeneral
		
		
		EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.aportes.AporteDetallado', NULL, '', 'USUARIO_SISTEMA', @iRevision OUTPUT
		
		INSERT INTO aud.AporteDetallado_aud(
			apdId,
			apdAporteGeneral,
			apdDiasCotizados,
			apdHorasLaboradas,
			apdSalarioBasico,
			apdValorIBC,
			apdValorIntMora,
			apdTarifa,
			apdAporteObligatorio,
			apdValorSaldoAporte,
			apdCorrecciones,
			apdEstadoAporteRecaudo,
			apdEstadoAporteAjuste,
			apdEstadoRegistroAporte,
			apdSalarioIntegral,
			apdMunicipioLaboral,
			apdDepartamentoLaboral,
			apdRegistroDetallado,
			apdTipoCotizante,
			apdEstadoCotizante,
			apdEstadoAporteCotizante,
			apdEstadoRegistroAporteCotizante,
			apdPersona,
			apdUsuarioAprobadorAporte,
			apdEstadoRegistroAporteArchivo,
			apdCodSucursal,
			apdNomSucursal,
			apdFechaMovimiento,
			apdFechaCreacion,
			apdFormaReconocimientoAporte,
			apdMarcaPeriodo,
			apdModalidadRecaudoAporte,
			apdMarcaCalculoCategoria,
			apdModificadoAportesOK,
			apdRegistroDetalladoUltimo,
			REV, REVTYPE
		)
		SELECT 
			apdId,
			apdAporteGeneral,
			apdDiasCotizados,
			apdHorasLaboradas,
			apdSalarioBasico,
			apdValorIBC,
			apdValorIntMora,
			apdTarifa,
			apdAporteObligatorio,
			apdValorSaldoAporte,
			apdCorrecciones,
			apdEstadoAporteRecaudo,
			apdEstadoAporteAjuste,
			apdEstadoRegistroAporte,
			apdSalarioIntegral,
			apdMunicipioLaboral,
			apdDepartamentoLaboral,
			apdRegistroDetallado,
			apdTipoCotizante,
			apdEstadoCotizante,
			apdEstadoAporteCotizante,
			apdEstadoRegistroAporteCotizante,
			apdPersona,
			apdUsuarioAprobadorAporte,
			apdEstadoRegistroAporteArchivo,
			apdCodSucursal,
			apdNomSucursal,
			apdFechaMovimiento,
			apdFechaCreacion,
			apdFormaReconocimientoAporte,
			apdMarcaPeriodo,
			apdModalidadRecaudoAporte,
			apdMarcaCalculoCategoria,
			apdModificadoAportesOK,
			apdRegistroDetalladoUltimo,
			@iRevision, 0
		FROM #AporteDetalladoTemporal
		
		
		---------------------------------------------------
		---------------------------------------------------
		-- APORTE DETALLADO
		---------------------------------------------------
		---------------------------------------------------
		CREATE TABLE #MovimientoAporteTemporal (
			[moaId] [bigint] NOT NULL,
			[moaTipoAjuste] [varchar](20) NULL,
			[moaTipoMovimiento] [varchar](23) NULL,
			[moaEstadoAporte] [varchar](22) NULL,
			[moaValorAporte] [numeric](19, 5) NULL,
			[moaValorInteres] [numeric](19, 5) NULL,
			[moaFechaActualizacionEstado] [datetime] NULL,
			[moaFechaCreacion] [datetime] NULL,
			[moaAporteDetallado] [bigint] NULL,
			[moaAporteGeneral] [bigint] NOT NULL
		)
		
		INSERT INTO MovimientoAporte (
			moaTipoAjuste,
			moaTipoMovimiento,
			moaEstadoAporte,
			moaValorAporte,
			moaValorInteres,
			moaFechaActualizacionEstado,
			moaFechaCreacion,
			moaAporteDetallado,
			moaAporteGeneral
		)
		OUTPUT
			INSERTED.moaId,
			INSERTED.moaTipoAjuste,
			INSERTED.moaTipoMovimiento,
			INSERTED.moaEstadoAporte,
			INSERTED.moaValorAporte,
			INSERTED.moaValorInteres,
			INSERTED.moaFechaActualizacionEstado,
			INSERTED.moaFechaCreacion,
			INSERTED.moaAporteDetallado,
			INSERTED.moaAporteGeneral
		INTO #MovimientoAporteTemporal
		SELECT
	        null,--this.movimiento.setTipoAjuste(null);
			CASE 
	        	WHEN (apdModalidadRecaudoAporte = 'PILA') THEN 'RECAUDO_PILA_AUTOMATICO'
	        	WHEN (apdModalidadRecaudoAporte = 'MANUAL') THEN 'RECAUDO_MANUAL'
	        	WHEN (apdModalidadRecaudoAporte = 'PILA_MANUAL') THEN 'RECAUDO_MANUAL_APORTES'
	        END,--this.movimiento.setTipoMovimiento(aporte.getModalidadRecaudoAporte().getTipoMovimiento());
	        'VIGENTE',--moaEstadoAporte this.movimiento.setEstado(EstadoAporteEnum.VIGENTE);
	        apdAporteObligatorio,--this.movimiento.setAporte(this.getAporteDetallado().getAporteObligatorio());
	        apdValorIntMora,--this.movimiento.setInteres(this.getAporteDetallado().getValorMora());
	        @localDate,--this.movimiento.setFechaActualizacionEstado(new Date());
	        @localDate,--this.movimiento.setFechaCreacion(new Date());
			apdId,
			apdAporteGeneral
    	FROM #AporteDetalladoTemporal
    	
		EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.aportes.MovimientoAporte', NULL, '', 'USUARIO_SISTEMA', @iRevision OUTPUT
		
		INSERT INTO aud.MovimientoAporte_aud(
			moaId,
			moaTipoAjuste,
			moaTipoMovimiento,
			moaEstadoAporte,
			moaValorAporte,
			moaValorInteres,
			moaFechaActualizacionEstado,
			moaFechaCreacion,
			moaAporteDetallado,
			moaAporteGeneral,
			REV, REVTYPE
		)
		SELECT
			moaId,
			moaTipoAjuste,
			moaTipoMovimiento,
			moaEstadoAporte,
			moaValorAporte,
			moaValorInteres,
			moaFechaActualizacionEstado,
			moaFechaCreacion,
			moaAporteDetallado,
			moaAporteGeneral,
			@iRevision, 0
		FROM #MovimientoAporteTemporal


		-- ----------------------------------------------------------------------------------------------
		-- CONTROL DE CAMBIOS PLANILLA N CON N ----------------------------------------------------------

	--	EXEC USP_CopiarAportesDesdeTemporalPilaCorrecciones @idRegistroGeneral, @codigoSucursalPila;

		-- FIN - CONTROL DE CAMBIOS PLANILLA N CON N ----------------------------------------------------------
		-- ----------------------------------------------------------------------------------------------
		
end
 
		drop table if exists #categorias
		DROP TABLE IF EXISTS #Tramitadores
		DROP TABLE IF EXISTS #EstadoAportanteCotizante
		DROP TABLE IF EXISTS #Sucursal
		DROP TABLE IF EXISTS #AporteGeneralTtemporal
		DROP TABLE IF EXISTS #AporteGeneralIdPorTranaccion
		DROP TABLE IF EXISTS #AporteDetalladoTemporal
		DROP TABLE IF EXISTS #MovimientoAporteTemporal
		DROP TABLE IF EXISTS #AporteGeneralTtemporalControlDuplicados
		DROP TABLE IF EXISTS #AporteGeneralIdPorTranaccion_ControlDuplicados
		DROP TABLE IF EXISTS #AporteDetalladoTemporal_ControlDuplicados
		DROP TABLE IF EXISTS #AporteGeneralDuplica
		DROP TABLE IF EXISTS #AporteDetalladoTemporal_revision
		DROP TABLE IF EXISTS #MovimientoAporteTemporal_controlDuplicados
		DROP TABLE IF EXISTS #aportFaltan
		DROP TABLE IF EXISTS #AporteGeneralIdPorTranaccion_Faltan
		DROP TABLE IF EXISTS #AporteDetalladoTemporal_Faltan
		DROP TABLE IF EXISTS #MovimientoAporteTemporal_Faltan

	--===============
		end --====== Finaliza control, para garantizar que si exista registro en la tbl temporales de pila
	--===============
	end --=============== Finaliza proceso planillas normales. 
	--===============
	--===============
	
	COMMIT TRAN T1;

		-- ----------------------------------------------------------------------------------------------
		-- ------- INICIA PROCESO CONTROL CATEGORÍAS
		-- ----------------------------------------------------------------------------------------------
	--IF OBJECT_ID('tempdb.dbo.#AporteDetalladoTemporal', 'U') IS NOT NULL
	  begin

	  	declare @periRegularAporte varchar(7);
		set @periRegularAporte = convert(varchar(7),(select dateadd(day, 1, dateadd(month, -1, eomonth(dbo.GetLocalDate())))));

		;with salariomax as (select case when apd.apdSalarioBasico = max(apd.apdSalarioBasico) over (partition by apd.apdPersona, apg.apgEmpresa, apd.apdTipoCotizante) then apd.apdRegistroDetallado else null end as apdRegistroDetallado
		from AporteGeneral as apg with (nolock)
		inner join AporteDetallado as apd with (nolock) on apg.apgId = apd.apdAporteGeneral
		where apg.apgRegistroGeneral = @idRegistroGeneral --and apg.apgMarcaPeriodo <> 'PERIODO_FUTURO' and apg.apgEstadoRegistroAporteAportante <> 'RELACIONADO'
		and apg.apgPeriodoAporte <= @periRegularAporte  --=== Ajuste para el calculo de categorias. 2023-05-29 para que evalue sobre periodos retroactivos o regulares. 
		and apd.apdSalarioBasico > 0
		)
		select apdRegistroDetallado, ROW_NUMBER() over (order by apdRegistroDetallado) as id
		into #categorias
		from salariomax
		where apdRegistroDetallado is not null

			declare @cont int = 1
			while @cont <= (select count(*) from #categorias)
			begin
			
				declare @idDetallado bigInt = (select apdRegistroDetallado from #categorias where id = @cont)
					execute [dbo].[USP_REP_CalcularCategoriaAportesPila] @idDetallado
					set @cont += 1 
			end
		end

				--actualizacion planilla O sbayona
		--declare @idRegistroGeneral bigint=1410229
		drop table if exists #registrosO
		create table #registrosO (regId bigint ,regTipoPlanilla varchar(20),regValTotalApoObligatorio numeric(19,5),regValorIntMora numeric (19,5),shard varchar(max))
		insert into #registrosO
		exec sp_execute_remote PilaReferenceData, N' select regId,regTipoPlanilla,regValTotalApoObligatorio,regValorIntMora from staging.RegistroGeneral 
		where regValTotalApoObligatorio=0.00000 and regTipoPlanilla=''O'' and regId=@IdRegistroGeneral',N'@idRegistroGeneral bigint', @idRegistroGeneral=@idRegistroGeneral

		if exists (select * from #registrosO)
		begin
		update ag set ag.apgValorIntMora=ag2.regValorIntMora 
		from dbo.AporteGeneral ag
		inner join #registrosO ag2 on ag2.regId=ag.apgRegistroGeneral

		update ad set apdValorIntMora=ag.apgValorIntMora 
		from dbo.AporteGeneral ag
		inner join dbo.APorteDetallado ad on ag.apgId=ad.apdAporteGeneral
		where ag.apgRegistroGeneral=@idRegistroGeneral
		end
		-- ----------------------------------------------------------------------------------------------
		-- ------- FINALIZA	 PROCESO CONTROL CATEGORÍAS
		-- ----------------------------------------------------------------------------------------------

--- CONTROL PARA QUE LIMPIE LAS TEMPORALES DE PILA, SOLO CUANDO HAYA PASADO LOS REGISTROS COMPLETOS A LAS TABLAS FINALES. 

		create table #unoControl (idREgistroGeneral bigInt, idRegistroDetallado bigInt, total int)
		insert #unoControl
		select temRegistroGeneral, temIdTransaccion, count(*) over (partition by temRegistroGeneral) as total 
		from pila.TemAporte where temRegistroGeneral = @idRegistroGeneral
		
		
		
		create table #unoContro2 (idREgistroGeneral bigInt, idRegistroDetallado bigInt, total int)
		insert #unoContro2
		select apg.apgRegistroGeneral, apd.apdRegistroDetallado, count(*) over (partition by apg.apgRegistroGeneral) as total
		from AporteDetallado as apd with (nolock)
		inner join AporteGeneral  as apg with (nolock) on apg.apgId = apd.apdAporteGeneral
		where apg.apgRegistroGeneral = @idRegistroGeneral
		
		
		if exists (
		select idREgistroGeneral, idRegistroDetallado from #unoControl
		except
		select idREgistroGeneral, idRegistroDetallado from #unoContro2
		)
		begin 
				insert AportesControlDuplicados (apgPeriodoAporte, apgPersona, apgEmpresa, apgTipoSolicitante, apgEstadoAporteAportante ,apgRegistroGeneral, fechaIntento, comentario)
				select null, null, null, null, null, t.idREgistroGeneral, CURRENT_TIMESTAMP AT TIME ZONE 'UTC' AT TIME ZONE 'SA Pacific Standard Time', concat(N'No se ha movido todos los detalles del aporte registroDetallado Faltante: ', t.idRegistroDetallado)
				from (
				select idREgistroGeneral, idRegistroDetallado from #unoControl
				except
				select idREgistroGeneral, idRegistroDetallado from #unoContro2
				) as t 
		end
		else
			begin
						EXEC sp_execute_remote PilaReferenceData,
						N'EXEC USP_BorrarDatosTemporalesAportes @idRegistroGeneral',
			  			N'@idRegistroGeneral bigint',
		 				@idRegistroGeneral = @idRegistroGeneral;
			end;

 		
drop table if exists #unoControl
drop table if exists #unoContro2


END TRY
BEGIN CATCH
    
	if @@TRANCOUNT > 0 ROLLBACK TRAN T1;

    INSERT RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (dbo.GetLocalDate(),'[USP_CopiarAportesDesdeTemporalPila]' ,ERROR_MESSAGE());

	THROW;
END CATCH

END;