-- =============================================
-- Author:		
-- Create date: 
-- Description:	Se realiza ajuste para insertar la auditoria, cuando es reintegro. 
-- 2022-11-16: Se realiza ajuste para actualización de la fecha de ingreso, historicoRolAfiliado y Categorias por reintegro por otra empresa. 
-- =============================================
CREATE OR ALTER TRIGGER [dbo].[TRG_AF_INS_SolicitudAfiliacionPersona]
ON [dbo].[SolicitudAfiliacionPersona]
AFTER INSERT AS
BEGIN
	SET NOCOUNT ON;	
	DECLARE @afiId BIGINT
    DECLARE @solId BIGINT   
	DECLARE @rolAfiliadoId BIGINT  
	DECLARE @soltipoTransaccion varchar(50)  
	DECLARE @solresultadoProceso varchar(50) 
	DECLARE @fechaNovedadPila DATE
	DECLARE @tipoAfiliado varchar(50) 
	DECLARE @idEmpleador BIGINT
	DECLARE @solcanal varchar(50)
	DECLARE @fechaNovedadPilaResul date
	DECLARE @fechaRetiroAfiliado date
	DECLARE @idBeneficiario bigInt
	declare @registroDetallado bigInt
	declare @benid table (benid bigint)

		SELECT @solId = sap.sapSolicitudGlobal, @afiId = roa.roaAfiliado,@rolAfiliadoId=roa.roaId,@tipoAfiliado=s.solClasificacion
		,@solcanal=s.solCanalRecepcion,@afiId=roaAfiliado,
		@solresultadoProceso=s.solResultadoProceso,@soltipoTransaccion=s.solTipoTransaccion,@idEmpleador=roa.roaEmpleador, @fechaRetiroAfiliado = roa.roaFechaRetiro
		FROM RolAfiliado roa
		inner join Empleador as em on roa.roaEmpleador = em.empId
        INNER JOIN inserted sap ON sap.sapRolAfiliado = roa.roaId 
		INNER JOIN Solicitud s on s.solId=sap.sapSolicitudGlobal
		where s.soltipoTransaccion='NOVEDAD_REINTEGRO' --and roa.roaFechaRetiro is not null

		EXEC fechaNovedadPila @solRegistroDetallado=@solId,@fechaNovedadPilaResul=@fechaNovedadPila OUTPUT
		

		IF @soltipoTransaccion='NOVEDAD_REINTEGRO' and @solresultadoProceso='APROBADA' and (@solcanal='PILA' or @solcanal='APORTE_MANUAL') --and @fechaRetiroAfiliado is not null
		BEGIN
			

					select @registroDetallado = spiRegistroDetallado 
					from solicitudNovedad n with (nolock)
					inner join SolicitudNovedadPila sp with (nolock) on sp.spiSolicitudNovedad=n.snoId
					where n.snoSolicitudGlobal = @solId
					
					--================================================================
					--================================================================
					--=== Se agrega update para los GLPI 69335, 69142 fecha 2023-08-07
					--================================================================
					--================================================================
					declare @tipoCot varchar(70), @salbasic numeric(19,5), @fechaIni date, @sucursal bigInt, @redHorasLaboradas int;
					declare @datosRolPila as table (redTipoCotizante tinyInt, redSalarioBasico numeric(19,5), fechaIngreso date, redHorasLaboradas int, origen varchar(200))
					insert @datosRolPila
					execute sp_execute_remote pilaReferenceData, 
					N'select redTipoCotizante, redSalarioBasico, isnull(redFechaIngreso, convert(date,concat(r.regPeriodoAporte, N''-01''))) as fechaIngreso, redHorasLaboradas
					from staging.registroGeneral as r with (nolock)
					inner join staging.registroDetallado as rd with (nolock) on r.regId = rd.redRegistroGeneral 
					where redId = @registroDetallado', N'@registroDetallado bigInt', @registroDetallado = @registroDetallado
					;with tiposCotPila as (select *
					from ( values 
					('TIPO_COTIZANTE_DEPENDIENTE',1, 'Dependiente', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_SERVICIO_DOMESTICO',2, 'Servicio doméstico', 'TRABAJADOR_DEPENDIENTE', 'SERVICIO_DOMESTICO'),
					('TIPO_COTIZANTE_INDEPENDIENTE',3, 'Independiente', 'TRABAJADOR_INDEPENDIENTE', 'INDEPENDIENTE_REGULAR'),
					('TIPO_COTIZANTE_MADRE_SUSTITUTA',4, 'Madre sustituta', 'TRABAJADOR_INDEPENDIENTE', 'INDEPENDIENTE_REGULAR'),
					('TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA',12, 'Aprendices en etapa lectiva', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_DESEMPLEADO_SCCF',15, 'Desempleado con subsidio de Caja de Compensación Familiar', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO',16, 'Independiente agremiado o asociado', 'TRABAJADOR_INDEPENDIENTE', 'INDEPENDIENTE_REGULAR'),
					('TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC',18, 'Funcionarios públicos sin tope máximo de IBC', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA',19, 'Aprendices en etapa productiva', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_ESTUDIANTES_REG_ESP',20, 'Estudiantes Régimen especial ley 789 de 2002', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD',21, 'Estudiantes de postgrado en salud Decreto 2376 de 2010', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR',22, 'Profesor de establecimiento particular', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL',23, 'Estudiantes aporte solo riesgos laborales', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI',30, 'Dependiente entidades o universidades públicas de los regímenes especial y de excepción', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO',31, 'Cooperados o precooperativas de trabajo asociado', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL',32, 'Cotizante miembro de la carrera diplomática o consular de un país extranjero o funcionario de organismo multilateral', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN',33, 'Beneficiario del fondo de solidaridad pensional', 'TRABAJADOR_INDEPENDIENTE', 'INDEPENDIENTE_REGULAR'),
					('TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD',34, 'Concejal o edil de Junta Administradora Local del Distrito Capital de Bogotá amparado por póliza de salud', 'TRABAJADOR_INDEPENDIENTE', 'INDEPENDIENTE_REGULAR'),
					('TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD',35, 'Concejal municipal o distrital no amparado con póliza de salud', 'TRABAJADOR_INDEPENDIENTE', 'INDEPENDIENTE_REGULAR'),
					('TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP',36, 'Concejal municipal o distrital o edil de Junta Administradora Local no amparado con póliza de salud beneficiario del Fondo de Solidaridad Pensional', 'TRABAJADOR_INDEPENDIENTE', 'INDEPENDIENTE_REGULAR'),
					('TIPO_COTIZANTE_BENEFICIARIO_UPC',40, 'Beneficiario UPC adicional', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD',42, 'Cotizante independiente pago solo salud.', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_PAGO_POR_TERCERO',43, 'Cotizante a pensiones con pago por tercero.', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES',44, 'Cotizante dependiente de empleo de emergencia con duración mayor o igual a un mes', 'TRABAJADOR_DEPENDIENTE', 'TRABAJADOR_POR_DIAS'),
					('TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES',45, 'Cotizante dependiente de empleo de emergencia con duración menor a un mes', 'TRABAJADOR_DEPENDIENTE', 'TRABAJADOR_POR_DIAS'),
					('TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI',47, 'Trabajador dependiente de entidad beneficiaria del sistema general de participaciones - Aportes patronales', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL',51, 'Trabajador de tiempo parcial ', 'TRABAJADOR_DEPENDIENTE', 'TRABAJADOR_POR_DIAS'),
					('TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE',52, 'Beneficiario del Mecanismo de Protección al Cesante', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_AFILIADO_PARTICIPE',53, 'Afiliado Participe', 'TRABAJADOR_INDEPENDIENTE', 'INDEPENDIENTE_REGULAR'),
					('TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ',54, 'Prepensionado de entidad en liquidación', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE',55, 'Afiliado participe dependiente', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_PREPENSIONADO_AVS',56, 'Prepensionado con aporte voluntario a Salud', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_IND_VOLUNTARIO_ARL',57, 'Independiente voluntario a Riesgos Laborales', 'TRABAJADOR_INDEPENDIENTE', 'INDEPENDIENTE_REGULAR'),
					('TIPO_COTIZANTE_ESTUDIANTE_PRACTICA_LABORAL_SECTOR_PUBLICO',58,'Estudiantes de prácticas laborales en el sector público', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES',59,'Independiente con contrato de prestación de servicios superior a 1 mes', 'TRABAJADOR_INDEPENDIENTE', 'INDEPENDIENTE_REGULAR'),
					('TIPO_COTIZANTE_EDIL_JAL ',60, 'Edil Junta Administradora Local no beneficiario del Fondo de Solidaridad Pensional', 'TRABAJADOR_INDEPENDIENTE', 'INDEPENDIENTE_REGULAR'),
					('TIPO_COTIZANTE_PROGRAMA_REINCORPORACION ',61, 'Beneficiario programa de reincorporación', 'TRABAJADOR_INDEPENDIENTE', 'INDEPENDIENTE_REGULAR'), 
					('TIPO_COTIZANTE_PERSONAL_DEL_MAGISTERIO ',62, 'Personal del Magisterio', 'TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA',63,'Beneficiario de prestación humanitaria','TRABAJADOR_INDEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_TRABAJADOR_PENITENCIARIO',64,'Trabajador penitenciario','TRABAJADOR_INDEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL',65,'Dependiente vinculado al piso de protección social','TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL',66,'Independiente vinculado al piso de protección social','TRABAJADOR_INDEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL',67,'Voluntario en primera respuesta aporte solo riesgos laborales','TRABAJADOR_INDEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA',68,'Dependiente Veterano de la Fuerza Publica','TRABAJADOR_DEPENDIENTE', 'REGULAR'),
					('TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO',69,'Contribuyente solidario','TRABAJADOR_INDEPENDIENTE', 'REGULAR')) as t (tipo, tipoCot, descri, tipotra, otro))
					select @tipoCot = a.otro, @salbasic = p.redSalarioBasico, @fechaIni = p.fechaIngreso, @redHorasLaboradas = redHorasLaboradas
					from @datosRolPila as p
					inner join tiposCotPila as a on p.redTipoCotizante = a.tipoCot
					
					--==== Se obtiene la sucursal. 

					select @sucursal = se.sueId
					from dbo.RolAfiliado as r with (nolock)
					inner join dbo.Empleador as em with (nolock) on r.roaEmpleador = em.empId
					inner join dbo.SucursalEmpresa as se with (nolock) on se.sueEmpresa = em.empEmpresa
					where r.roaId = @rolAfiliadoId and se.sueSucursalPrincipal = 1 and r.roaSucursalEmpleador is null
					--================================================================
					--================================================================
					--=== Finaliza variables para los GLPI 69335, 69142 fecha 2023-08-07
					--================================================================
					--================================================================

					;with act as (select b.benFechaRetiro, b.benId, r.roaId, r.roaFechaRetiro, case when r.roaFechaRetiro = b.benFechaRetiro then b.benId else null end as activar, b.benEstadoBeneficiarioAfiliado
					from rolAfiliado as r with (nolock)
					inner join beneficiario as b with (nolock) on r.roaAfiliado = b.benAfiliado
					where r.roaAfiliado = @afiId),
					act2 as (
					select *
					from act
					where activar is not null and benEstadoBeneficiarioAfiliado = 'INACTIVO')
					
					insert into @benid
					select benId from act2

					update b set 
					benEstadoBeneficiarioAfiliado = 'ACTIVO'
					,benFechaRetiro =  NULL 
					,benFechaAfiliacion = @fechaNovedadPila
					,benMotivoDesafiliacion = null
					from Beneficiario as b
					inner join @benId as c on b.benId = c.benId


					update r set r.roaSucursalEmpleador = se.sueId
				    from AporteDetallado as apd with (nolock)
				    inner join AporteGeneral as apg with (nolock) on apd.apdAporteGeneral = apg.apgId
				    inner join Empleador as e with (nolock) on e.empEmpresa = apg.apgEmpresa
				    inner join Afiliado as a with (nolock) on apd.apdPersona = a.afiPersona
				    inner join RolAfiliado as r with (nolock) on r.roaAfiliado = a.afiId and e.empId = r.roaEmpleador
				    inner join SucursalEmpresa se with (nolock) on e.empEmpresa = se.sueEmpresa and se.sueId = apg.apgSucursalEmpresa
				    where apd.apdRegistroDetallado = @registroDetallado and se.sueId != r.roaSucursalEmpleador


				    Update RolAfiliado set roaEstadoAfiliado='ACTIVO',roaFechaRetiro=NULL,roaMotivoDesafiliacion=NULL, roaFechaAfiliacion = @fechaNovedadPila, 
					--=== Se agrega update para los GLPI 69335, 69142 fecha 2023-08-07
					roaClaseTrabajador = (case when roaClaseTrabajador is null then @tipoCot else roaClaseTrabajador end),
					roaValorSalarioMesadaIngresos = (case when roaValorSalarioMesadaIngresos is null then @salbasic else roaValorSalarioMesadaIngresos end),
					roaFechaIngreso = (case when roaFechaIngreso is null then @fechaIni else roaFechaIngreso end), 
					roaSucursalEmpleador = (case when roaSucursalEmpleador is null then @sucursal else roaSucursalEmpleador end),
					--=== Se agregan las horas laboradas desde pila. 2023-08-14
					roaHorasLaboradasMes = (case when roaHorasLaboradasMes is null then @redHorasLaboradas else roaHorasLaboradasMes end) --===
					where  roaId=@rolAfiliadoId and roaEmpleador = @idEmpleador
			
			--== Actualización de la fecha ingreso en el historico del rolAfiliado. 
			;with actHist as (
			select *, ROW_NUMBER() over (order by hraId desc) as idAct 
			from HistoricoRolAfiliado
			where  hraAfiliado=@afiId and hraEmpleador = @idEmpleador
			)
			Update actHist set hraFechaIngreso = @fechaNovedadPila 
			where idAct = 1

		END	

		begin
			--- INSERTAR LA AUDITORIA DE UPDATE AL ROLAFILIADO. 

			DECLARE
			@millisec BIGINT,
			@iRevision bigInt;
			SELECT @millisec = DATEDIFF_BIG (ms, '1969-12-31 19:00:00', dbo.GetLocalDate())
			INSERT aud.Revision (revNombreUsuario, revTimeStamp) values (null, @millisec)
			SELECT @iRevision = SCOPE_IDENTITY()
			INSERT aud.RevisionEntidad (reeEntityClassName,reeRevisionType,reeRevision) VALUES ('com.asopagos.entidades.ccf.personas.RolAfiliado',1,@iRevision)
			INSERT INTO aud.RolAfiliado_aud(roaId ,REV ,REVTYPE ,roaCargo ,roaClaseIndependiente ,roaClaseTrabajador ,roaEstadoAfiliado ,roaEstadoEnEntidadPagadora ,roaFechaIngreso ,roaFechaRetiro ,roaHorasLaboradasMes 
						,roaIdentificadorAnteEntidadPagadora ,roaPorcentajePagoAportes ,roaTipoAfiliado ,roaTipoContrato ,roaTipoSalario ,roaValorSalarioMesadaIngresos ,roaAfiliado ,roaEmpleador 
						,roaPagadorAportes ,roaPagadorPension ,roaSucursalEmpleador ,roaFechaAfiliacion ,roaMotivoDesafiliacion ,roaSustitucionPatronal ,roaFechaFinPagadorAportes ,roaFechaFinPagadorPension 
						,roaEstadoEnEntidadPagadoraPension ,roaDiaHabilVencimientoAporte ,roaMarcaExpulsion ,roaEnviadoAFiscalizacion ,roaMotivoFiscalizacion ,roaFechaFiscalizacion ,roaOportunidadPago 
						,roaCanalReingreso ,roaReferenciaAporteReingreso ,roaReferenciaSolicitudReingreso ,roaFechaFinContrato ,roaMunicipioDesempenioLabores)
				SELECT roaId ,@iRevision ,1 ,roaCargo ,roaClaseIndependiente ,roaClaseTrabajador ,roaEstadoAfiliado ,roaEstadoEnEntidadPagadora ,roaFechaIngreso ,roaFechaRetiro ,roaHorasLaboradasMes 
						,roaIdentificadorAnteEntidadPagadora ,roaPorcentajePagoAportes ,roaTipoAfiliado ,roaTipoContrato ,roaTipoSalario ,roaValorSalarioMesadaIngresos ,roaAfiliado ,roaEmpleador 
						,roaPagadorAportes ,roaPagadorPension ,roaSucursalEmpleador ,roaFechaAfiliacion ,roaMotivoDesafiliacion ,roaSustitucionPatronal ,roaFechaFinPagadorAportes ,roaFechaFinPagadorPension 
						,roaEstadoEnEntidadPagadoraPension ,roaDiaHabilVencimientoAporte ,roaMarcaExpulsion ,roaEnviadoAFiscalizacion ,roaMotivoFiscalizacion ,roaFechaFiscalizacion ,roaOportunidadPago 
						,roaCanalReingreso ,roaReferenciaAporteReingreso ,roaReferenciaSolicitudReingreso ,roaFechaFinContrato ,roaMunicipioDesempenioLabores
				FROM RolAfiliado with (nolock)
			WHERE roaId = @rolAfiliadoId


			--- INSERTAR LA AUDITORIA DE UPDATE AL BENEFICIARIO. 

			insert into aud.Beneficiario_aud (benId ,REV ,REVTYPE ,benEstadoBeneficiarioAfiliado ,benEstudianteTrabajoDesarrolloHumano ,benFechaAfiliacion ,benTipoBeneficiario ,benGrupoFamiliar ,benPersona ,benAfiliado 
						,benGradoAcademico ,benMotivoDesafiliacion ,benFechaRetiro ,benFechaInicioSociedadConyugal ,benFechaFinSociedadConyugal ,benRolAfiliado ,benBeneficiarioDetalle ,benOmitirValidaciones)
				select benId ,@iRevision ,1 ,benEstadoBeneficiarioAfiliado ,benEstudianteTrabajoDesarrolloHumano ,benFechaAfiliacion ,benTipoBeneficiario ,benGrupoFamiliar ,benPersona ,benAfiliado 
						,benGradoAcademico ,benMotivoDesafiliacion ,benFechaRetiro ,benFechaInicioSociedadConyugal ,benFechaFinSociedadConyugal ,benRolAfiliado ,benBeneficiarioDetalle ,benOmitirValidaciones
				from Beneficiario
				where benId in (select * from @benid)

		end	
END