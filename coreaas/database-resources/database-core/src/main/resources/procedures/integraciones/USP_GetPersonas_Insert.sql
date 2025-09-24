

-- ====================================================================================================
-- Author: VILLAMARIN JULIAN
-- Create date: MAYO 10 DE 2021
-- Description: Llama el SP [SAP].[USP_GetPersonas_documentoId] el cual realiza el ingreso de registros 
-- por numero y tipo de identificacion cuando se presenta una novedad o una afiliacion a la tabla de
-- integracion.
--Ajuste de rendimiento: Keyner Vides
-- =====================================================================================================
CREATE OR ALTER   PROCEDURE [sap].[USP_GetPersonas_Insert] AS

BEGIN
SET NOCOUNT ON;
 

declare	@perNumeroIdentificacion varchar(20), @perTipoIdentificacion varchar(20), @solNumeroRadicacion varchar(30), @tipo varchar(10)
declare	@documentoIdBeneficiario varchar(20), @tipoDocumentoBeneficiario varchar(20)

drop table if exists #tmp_filaPersonas
create table #tmp_filaPersonas (perNumeroIdentificacion varchar(16),perTipoIdentificacion varchar(20),solNumeroRadicacion varchar(30),tipo varchar(30))
create index INX_Documento on #tmp_filaPersonas (perNumeroIdentificacion, perTipoIdentificacion,solNumeroRadicacion);

drop table if exists #tmp_filaPersonas2
create table #tmp_filaPersonas2 (perNumeroIdentificacion varchar(16),perTipoIdentificacion varchar(20),solNumeroRadicacion varchar(30),tipo varchar(30))
create index INX_Documento on #tmp_filaPersonas2 (perNumeroIdentificacion, perTipoIdentificacion,solNumeroRadicacion);

-- inserta todas las solicitudes apartir de la fecha entrega del ambiente
select * 
into #solicitud
from Solicitud
where solFechaRadicacion >= '2024-03-12'


-- Registro en bitacora de registros a ser integrados y que no hayan sido integrados previamente en registro de la tabla PersonasCtrl.
-- Selecciono las novedades y las afiliaciones realizadas


	
    insert into #tmp_filaPersonas
	select top 50 per.perNumeroIdentificacion, per.perTipoIdentificacion, sol.solNumeroRadicacion, 'AFILIACION' as tipo
	from core.dbo.SolicitudAfiliacionPersona sap with (nolock)
	inner join core.dbo.RolAfiliado roa with (nolock) on sap.sapEstadoSolicitud = 'CERRADA' and sap.sapRolAfiliado = roa.roaid
	inner join core.dbo.Afiliado afi with (nolock) on  roa.roaAfiliado = afi.afiId
	inner join core.dbo.Persona per with (nolock) on  afi.afiPersona = per.perId
	inner join #solicitud sol with (nolock) on sol.solResultadoProceso = 'APROBADA' and sap.sapSolicitudGlobal = sol.solId 
	left join sap.PersonasCtrl PCTR with (nolock) on PCTR.solNumeroRadicacion=SOL.solNumeroRadicacion and 
	PCTR.perNumeroIdentificacion = PER.perNumeroIdentificacion and PCTR.perTipoIdentificacion = PER.perTipoIdentificacion -- Cambio Yesika Bernal REMPLAZO DE NOT EXISTS
	where PCTR.solNumeroRadicacion  IS NULL --Cambio Yesika Bernal REMPLAZO DE NOT EXISTS
	print(concat('1. #tmp_filaPersonas ',@@ROWCOUNT))
	

declare @novedad table (novedad varchar(max))
insert into @novedad
values
('ACTIVAR_BENEFICIARIO_MADRE_DEPWEB'),('ACTIVAR_BENEFICIARIO_MADRE_WEB'),('ACTIVAR_BENEFICIARIO_MADRE_PRESENCIAL'),('ACTIVAR_BENEFICIARIO_PADRE_DEPWEB'),('ACTIVAR_BENEFICIARIO_PADRE_WEB'),('ACTIVAR_BENEFICIARIO_PADRE_PRESENCIAL')
,('ACTIVA_BENEFICIARIO_EN_CUSTODIA_DEPWEB'),('ACTIVA_BENEFICIARIO_EN_CUSTODIA_WEB'),('ACTIVA_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL'),('ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_DEPWEB'),('ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_WEB')
,('ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_PRESENCIAL'),('ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_DEPWEB'),('ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_WEB'),('ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_PRESENCIAL'),('ACTIVAR_BENEFICIARIO_HIJASTRO_DEPWEB')
,('ACTIVAR_BENEFICIARIO_HIJASTRO_WEB'),('ACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL'),('ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_DEPWEB'),('ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_WEB'),('ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL')
,('ACTIVAR_BENEFICIARIO_CONYUGE_DEPWEB'),('ACTIVAR_BENEFICIARIO_CONYUGE_WEB'),('ACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL'),('ACTIVAR_INACTIVAR_AUTORIZACION_ENVIO_CORREO_DATOS_PERSONALES_PERSONAS'),('ACTIVAR_INACTIVAR_AUTORIZACION_ENVIO_CORREO_DATOS_PERSONALES_PERSONAS_DEPWEB')
,('ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_DEPWEB'),('ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_WEB'),('ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_PRESENCIAL'),('ACTUALIZAR_INFORMACION_PADRE_MADRE_BIOLOGICO_PERSONA_DEPWEB')
,('ACTUALIZAR_INFORMACION_PADRE_MADRE_BIOLOGICO_PERSONA_WEB'),('ACTUALIZAR_INFORMACION_PADRE_MADRE_BIOLOGICO_PERSONA_PRESENCIAL'),('ACTUALIZACION_GRADO_CURSADO_PERSONAS_WEB'),('ACTUALIZACION_GRADO_CURSADO_PERSONAS')
,('ACTUALIZACION_GRADO_CURSADO_PERSONAS_DEPWEB'),('AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO'),('AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION'),('AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO')
,('AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION'),('AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO'),('AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION'),('AFILIACION_EMPLEADORES_WEB_REINTEGRO'),('AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION')
,('AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO'),('AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION'),('AFILIACION_PERSONAS_PRESENCIAL'),('CAMBIO_GENERO_PERSONAS'),('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB'),('CAMBIO_NOMBRE_APELLIDOS_PERSONAS')
,('CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB'),('CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS'),('CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS_DEPWEB'),('CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS'),('CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS_WEB'),('CAMBIO_TIPO_NUMERO_DOCUMENTOS_IDENTIDAD')
,('CAMBIO_ESTADO_CIVIL_PERSONAS_WEB'),('CAMBIO_ESTADO_CIVIL_PERSONAS'),('CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS_WEB'),('CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS'),('CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS_DEPWEB'),('CAMBIO_ORIENTACION_SEXUAL_PERSONAS_WEB')
,('CAMBIO_ORIENTACION_SEXUAL_PERSONAS'),('CAMBIO_ORIENTACION_SEXUAL_PERSONAS_DEPWEB'),('CAMBIO_FECHA_NACIMIENTO_PERSONA_DEPWEB'),('CAMBIO_FECHA_NACIMIENTO_PERSONA_WEB'),('CAMBIO_FECHA_NACIMIENTO_PERSONA_PRESENCIAL'),('CAMBIO_FECHA_EXPEDICION_DOCUMENTO_IDENTIDAD_PERSONA_DEPWEB')
,('CAMBIO_FECHA_EXPEDICION_DOCUMENTO_IDENTIDAD_PERSONA_WEB'),('CAMBIO_FECHA_EXPEDICION_DOCUMENTO_IDENTIDAD_PERSONA_PRESENCIAL'),('CAMBIO_DATOS_CORRESPONDENCIA_GRUPO_FAMILIAR_PRESENCIAL'),('REPORTE_FALLECIMIENTO_PERSONAS_WEB'),('REPORTE_FALLECIMIENTO_PERSONAS'),('REPORTE_FALLECIMIENTO_PERSONAS_DEPWEB')
,('INACTIVAR_BENEFICIOS_MADRE_DEPWEB'),('INACTIVAR_BENEFICIOS_MADRE_WEB'),('INACTIVAR_BENEFICIOS_MADRE_PRESENCIAL'),('INACTIVAR_BENEFICIOS_PADRE_DEPWEB'),('INACTIVAR_BENEFICIOS_PADRE_WEB'),('INACTIVAR_BENEFICIOS_PADRE_PRESENCIAL'),('INACTIVAR_BENEFICIO_EN_CUSTODIA_DEPWEB')
,('INACTIVAR_BENEFICIO_EN_CUSTODIA_WEB'),('INACTIVAR_BENEFICIO_EN_CUSTODIA_PRESENCIAL'),('INACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_DEPWEB'),('INACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_WEB'),('INACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_PRESENCIAL'),('INACTIVAR_BENEFICIARIO_HUERFANO_DEPWEB')
,('INACTIVAR_BENEFICIARIO_HUERFANO_WEB'),('INACTIVAR_BENEFICIARIO_HUERFANO_PRESENCIAL'),('INACTIVAR_BENEFICIARIO_HIJASTRO_DEPWEB'),('INACTIVAR_BENEFICIARIO_HIJASTRO_WEB'),('INACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL'),('INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_DEPWEB')
,('INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_WEB'),('INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL'),('INACTIVAR_BENEFICIARIO_CONYUGE_DEPWEB'),('INACTIVAR_BENEFICIARIO_CONYUGE_WEB'),('INACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL'),('NOVEDAD_REINTEGRO'),('RETIRO_MADRE')
,('RETIRO_PADRE'),('RETIRO_BENEFICIARIO_CUSTODIA'),('RETIRO_HIJO_ADOPTIVO'),('RETIRO_HERMANO_HUERFANO'),('RETIRO_HIJASTRO'),('RETIRO_HJO_BIOLOGICO'),('RETIRO_CONYUGE'),('RETIRO_PENSIONADO_PENSION_FAMILIAR'),('RETIRO_PENSIONADO_MENOR_1_5SM_2'),('RETIRO_PENSIONADO_MENOR_1_5SM_0_6'),('RETIRO_PENSIONADO_MENOR_1_5SM_0')
,('RETIRO_PENSIONADO_MAYOR_1_5SM_2'),('RETIRO_PENSIONADO_MAYOR_1_5SM_0_6'),('RETIRO_PENSIONADO_25ANIOS'),('RETIRO_TRABAJADOR_INDEPENDIENTE'),('RETIRO_TRABAJADOR_DEPENDIENTE'),('TRASLADO_BENEFICIARIO_GRUPO_FAMILIAR_AFILIADO_PERSONAS'),('REPORTE_INVALIDEZ_PERSONAS')
,('ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_BIOLOGICO_PRESENCIAL'),('ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_ADOPTIVO_PRESENCIAL'),('ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HERMANO_HUERFANO_DEPWEB')
,('ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HERMANO_HUERFANO_WEB'),('ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HERMANO_HUERFANO_PRESENCIAL'),('ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJASTRO_DEPWEB')
,('ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJASTRO_WEB'),('ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJASTRO_PRESENCIAL'),('ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_BIOLOGICO_DEPWEB'),('ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_BIOLOGICO_WEB')
,('ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_BIOLOGICO_PRESENCIAL'),('ACTUALIZACION_CERTIFICADO_ESTUDIOS_BENEFICIARIO_EN_CUSTODIA_DEPWEB'),('ACTUALIZACION_CERTIFICADO_ESTUDIOS_BENEFICIARIO_EN_CUSTODIA_WEB'),('ACTUALIZACION_CERTIFICADO_ESTUDIOS_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL')
,('ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_ADOTIVO_DEPWEB'),('ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_ADOTIVO_WEB'),('ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_ADOTIVO_PRESENCIAL'),('ACTUALIZACION_CERTIFICADO_ESTUDIOS_HERMANO_HUERFANO_DEPWEB'),('ACTUALIZACION_CERTIFICADO_ESTUDIOS_HERMANO_HUERFANO_WEB')
,('ACTUALIZACION_CERTIFICADO_ESTUDIOS_HERMANO_HUERFANO_PRESENCIAL'),('ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJASTRO_DEPWEB'),('ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJASTRO_WEB'),('ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_ADOPTIVO_DEPWEB'),('ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_ADOPTIVO_WEB')
,('ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJASTRO_PRESENCIAL'),('ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_BIOLOGICO_DEPWEB'),('ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_BIOLOGICO_WEB'),('ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_BIOLOGICO_PRESENCIAL'),('RETIRAR_AUTOMATICAMENTE_BENEFICIARIO_X_EDAD'),('RETIRAR_BENEFICIARIO_EDAD_MAYOR_IGUAL_18_NO_TIENE_CEDULA')
,('ACTUALIZAR_VALOR_TIPO_SALARIO_CONTRATO_TRABAJADOR_DEPENDIENTE_PRESENCIAL'),('CAMBIAR_AUTOMATICAMENTE_CATEGORIA_Z_BENEFICIARIOS_CUMPLEN_X_EDAD'),('CAMBIO_AUTOMATICO_CATEGORIA_BENEFICIARIO_CIRCULAR_UNICA'),('CAMBIO_AUTOMATICO_CATEGORIA_BENEFICIARIO_CIRCULAR_UNICA_II')

insert into #tmp_filaPersonas
select  top 150 per.perNumeroIdentificacion, per.perTipoIdentificacion, sol.solNumeroRadicacion, 'NOVEDAD' as tipo
	from core.dbo.SolicitudNovedadPersona snp with (nolock)
	inner join core.dbo.SolicitudNovedad sno with (nolock) on  snp.snpSolicitudNovedad = sno.snoId
	inner join #solicitud sol with (nolock) on sol.solTipoTransaccion IN (select novedad from @novedad)  and sol.solResultadoProceso = 'APROBADA' and sno.snoEstadoSolicitud = 'CERRADA' and sno.snoSolicitudGlobal = sol.solId
	left join core.dbo.Beneficiario  with (nolock) on  snp.snpBeneficiario = benid ----cambio 20220203 olga vega ---20220207 PARA CUANDO SOLO ESTA EL ID DE PERSONA O CUANDO ESTA EL BENEFICARIO
	left join core.dbo.Afiliado  with (nolock) ON  snp.snppersona = afipersona  and  snp.snpBeneficiario is null ----cambio 20220203 olga vega ---20220208 PARA CUANDO SOLO ESTA EL ID DE PERSONA  del afiliado CAMBIO OLFA VEGA 20220217 DEL OR PORQUE NO ESTABA TOMANDO
	inner join core.dbo.Persona per with (nolock) on per.perId =  coalesce(benpersona, afipersona) -----cambio 20220208 olga vega 
	left join sap.PersonasCtrl PCTR with (nolock) on PCTR.solNumeroRadicacion=SOL.solNumeroRadicacion and 
	PCTR.perNumeroIdentificacion = PER.perNumeroIdentificacion and PCTR.perTipoIdentificacion = PER.perTipoIdentificacion --Cambio Yesika Bernal REMPLAZO DE NOT EXISTS
	where  PCTR.solNumeroRadicacion IS NULL	--Cambio Yesika Bernal REMPLAZO DE NOT EXISTS 
	order by  sol.solFechaRadicacion 

	print(concat('2. ##tmp_filaPersonas ',@@ROWCOUNT)) 

	insert into #tmp_filaPersonas
	select top 20 per.perNumeroIdentificacion, per.perTipoIdentificacion, sol.solNumeroRadicacion, 'AFILIACION_BENEFICIARIO' as tipo 
	from core.dbo.SolicitudAfiliacionPersona sap with (nolock)
	inner join core.dbo.RolAfiliado roa with (nolock) ON  sap.sapRolAfiliado = roa.roaid
	inner join core.dbo.Afiliado afi with (nolock) ON roa.roaAfiliado = afi.afiId
	inner join #solicitud sol with (nolock) ON sap.sapSolicitudGlobal =  sol.solId
	inner join core.dbo.Beneficiario with (nolock) ON  afiid = benAfiliado
	inner join core.dbo.Persona per with (nolock) ON  benPersona = per.perId
	left join sap.PersonasCtrl PCTR with (nolock) ON PCTR.solNumeroRadicacion=sol.solNumeroRadicacion and 
	PCTR.perNumeroIdentificacion = PER.perNumeroIdentificacion and PCTR.perTipoIdentificacion = PER.perTipoIdentificacion --Cambio Yesika Bernal REMPLAZO DE NOT EXISTS
	where sap.sapEstadoSolicitud = 'CERRADA'
	and sol.solResultadoProceso = 'APROBADA'
	and sol.solTipoTransaccion IN (
			'NOVEDAD_REINTEGRO','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO','AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION',
			'AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO',
			'AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_EMPLEADORES_WEB_REINTEGRO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION',
			'AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_PERSONAS_PRESENCIAL',
			'CAMBIO_DATOS_CORRESPONDENCIA_GRUPO_FAMILIAR_PRESENCIAL','REPORTE_INVALIDEZ_PERSONAS'
	)
	and PCTR.solNumeroRadicacion IS NULL 
	and solUsuarioRadicacion <>'migracion@asopagos.com'--Cambio Yesika Bernal REMPLAZO DE NOT EXISTS
	print(concat('3. ###tmp_filaPersonas ',@@ROWCOUNT)) 

	insert into #tmp_filaPersonas
		select distinct 
		persona.perNumeroIdentificacion,  persona.perTipoIdentificacion
		,CategoriaAfiliado.ctaId
		, 'NOVEDAD' as tipo
					  from core.dbo.CategoriaAfiliado with (nolock)
				inner join core.dbo.afiliado with (nolock) ON  ctaAfiliado = afiid
				inner join core.dbo.persona with (nolock) ON  afipersona  = perid
				inner join core.dbo.rolAfiliado with (nolock) ON afiId = roaAfiliado and roaEstadoAfiliado = 'ACTIVO'
				inner join (select ca.ctaId, ca.ctaAfiliado, ca.ctaCategoria
										from core.dbo.CategoriaAfiliado ca with (nolock)
										inner join core.dbo.afiliado a with (nolock) on ca.ctaAfiliado = a.afiid
										inner join core.dbo.persona p with (nolock) on a.afipersona = p.perid
										and ca.ctaId = (select max(ctaId)
														from core.dbo.CategoriaAfiliado with (nolock)
														where ctaAfiliado = ca.ctaAfiliado and ctaMotivoCambioCategoria != 'APORTE_RECIBIDO_AFILIADO_CAJA')) catant
							on catant.ctaAfiliado = CategoriaAfiliado.ctaAfiliado and  catant.ctaCategoria != CategoriaAfiliado.ctaCategoria 
				left join sap.PersonasCtrl with (nolock) on solNumeroRadicacion = convert(varchar,CategoriaAfiliado.ctaId)
					 where ctaMotivoCambioCategoria = 'APORTE_RECIBIDO_AFILIADO_CAJA'
					   and ctaFechaCambioCategoria >= getdate()-1 and solNumeroRadicacion is null
					   			print(concat('4. ####tmp_filaPersonas ',@@ROWCOUNT)) 								


 ---ADICIoN OLGA VEGA NOVEDAD POR CAMBIO DE CATEGORIA PARA LOS BENEFICIARIOS PORQUE CUMPLEN MAS DE 1 AÑO SU AFILIADO PPAL DE MUERTO 20220519


	insert into #tmp_filaPersonas2
 	 select distinct top 2 pb.perNumeroIdentificacion, pb.perTipoIdentificacion, pb.perNumeroIdentificacion as solNumeroRadicacion, 'NOVEDAD' as tipo
				   from core.dbo.persona pa with (nolock)
					 inner join core.dbo.personadetalle pda with (nolock) on pa.perId = pda.pedPersona
					 inner join core.dbo.afiliado with (nolock) on  perid = afipersona
					 inner join core.dbo.RolAfiliado with (nolock) on  afiid = roaAfiliado
					 inner join core.dbo.Beneficiario with (nolock) on  roaAfiliado = benAfiliado
					 inner join core.dbo.personadetalle pbd with (nolock) on pbd.pedPersona = benpersona
					 inner join core.dbo.persona pb with (nolock) on benPersona = pb.perid
					 inner join core.dbo.SolicitudNovedadPersona with (nolock) ON  pa.perid = snpPersona
					 inner join core.dbo.solicitudnovedad with (nolock) ON snpSolicitudNovedad = snoId
					 inner join #solicitud s with (nolock) ON snoSolicitudGlobal =  s.solid 
					 left join (select * from	sap.PersonasCtrl with (nolock)) ctrl
			         on  pb.perNumeroIdentificacion = ctrl.perNumeroIdentificacion
					and  pb.perNumeroIdentificacion = ctrl.solNumeroRadicacion
				  where roaEstadoAfiliado = 'INACTIVO' and benEstadoBeneficiarioAfiliado = 'INACTIVO'
					and solTipoTransaccion = 'REPORTE_FALLECIMIENTO_PERSONAS' and solClasificacion = 'TRABAJADOR_DEPENDIENTE'
					and solResultadoProceso = 'APROBADA' and snpBeneficiario is null
					and pda.pedFechaFallecido is not null and datediff(day, pda.pedFechaDefuncion,getdate()) = 365
					and ctrl.solNumeroRadicacion IS NULL
					and  not exists (  select perid 
											from persona with (nolock)
										inner join Afiliado with (nolock) on  perid = afipersona
										inner join RolAfiliado with (nolock) ON  afiid = roaAfiliado
										     where roaEstadoAfiliado = 'ACTIVO' and perid = pb.perId 
											 union
											 select perid 
											from persona with (nolock)
										inner join Beneficiario with (nolock) ON  perid = benPersona
										     where benEstadoBeneficiarioAfiliado = 'ACTIVO' and perid = pb.perId )----adicion para que no lo haga con los activos en cualquier otro perfil
			print(concat('5. #tmp_filaPersonas2 ',@@ROWCOUNT)) 
						
		if CONVERT(varchar,dbo.GetLocalDate(),8) between '08:00:00' and '16:30:00' --adicionado John Sotelo (ddmmyyyy) 03062022. GLPI 57577
			BEGIN
			insert into #tmp_filaPersonas
			select tfp.perNumeroIdentificacion, tfp.perTipoIdentificacion, tfp.solNumeroRadicacion, tfp.tipo
			  from #tmp_filaPersonas2 tfp with (nolock)
			end

-- Limita los numero de radicacion a uno solo cuando es en cascada la novedad porque se duplica al buscar los beneficiarios.

declare @Afiliado table (perNumeroIdentificacion varchar(16),perTipoIdentificacion varchar(20), solNumeroRadicacion varchar(30),tipo varchar(30))	
	insert @Afiliado
	select distinct perNumeroIdentificacion, perTipoIdentificacion, solNumeroRadicacion, tipo ------NO PUEDO TRAER LA MININA CAMBIO OLGA VEGA 20220218
	from #tmp_filaPersonas with (nolock)

declare @t int = (select count(*) from #tmp_filaPersonas)
	print (concat(@t,' Ciclos'))
-- Listar los numeros de radicado asociados	 <


	--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	-- INICIO DE LLAMADO AL PROCEDIMIENTO PARA ACTUALIZAR LOS BENEFICIARIOS DE UN TRABAJADOR ACTIVO
	--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
declare @countAfi int = 0
while (@countAfi < (@t))

begin
		
		select top 1 @perNumeroIdentificacion = perNumeroIdentificacion, @perTipoIdentificacion =  perTipoIdentificacion, @solNumeroRadicacion = solNumeroRadicacion from @Afiliado
		print(concat(@countAfi+1,' - Afiliado ',@perNumeroIdentificacion,' ',@perTipoIdentificacion,' ',@solNumeroRadicacion))
		set @countAfi = @countAfi+1
		print(concat('Afiliado ',@countAfi))
	-- Verifica los beneficiarios del trabajador activo
		declare @tt int = (select distinct count(*)
												from core.dbo.Persona per with (nolock)
												inner join core.dbo.Afiliado afi with (nolock) ON per.perId = afi.afiPersona 
												inner join beneficiario ben on afi.afiid = ben.benafiliado 
												where per.perNumeroIdentificacion = @perNumeroIdentificacion
												and per.perTipoIdentificacion = @perTipoIdentificacion)
	print (concat('Beneficiarios ',@tt))

								declare @Beneficiario table (perNumeroIdentificacion varchar(16),perTipoIdentificacion varchar(20))
								
								insert @Beneficiario
								select  distinct per2.perNumeroIdentificacion, per2.perTipoIdentificacion
								  from core.dbo.Persona per with (nolock)
									inner join core.dbo.Afiliado afi with (nolock) on per.perId = afi.afiPersona
									inner join core.dbo.GrupoFamiliar grf with (nolock) on afi.afiId = grf.grfAfiliado
									inner join core.dbo.Beneficiario ben with (nolock) on grf.grfId = ben.benGrupoFamiliar
									inner join core.dbo.Persona per2 with (nolock) on ben.benPersona = per2.perId
									left join #tmp_filaPersonas2 per3 with (nolock) on per2.perTipoIdentificacion = per3.perTipoIdentificacion and per2.perNumeroIdentificacion = per3.perNumeroIdentificacion
								 where per.perNumeroIdentificacion = @perNumeroIdentificacion and per.perTipoIdentificacion = @perTipoIdentificacion
										and per3.perNumeroIdentificacion is null

					declare @countben2 int = @tt
					declare @countben int = 0
					

					while (@countben < (@countben2))

					begin

						print('Ciclo Beneficiario')
								
								select top 1 @documentoIdBeneficiario = perNumeroIdentificacion , @tipoDocumentoBeneficiario = perTipoIdentificacion from @Beneficiario 


							-- Busca cada beneficiario que no sea a la vez un trabajador activo y lo envia a traves de la integracion de personas.
							if exists (select pertipoIdentificacion, perNumeroIdentificacion, roaEstadoAfiliado, Tipo
										 from (select per.pertipoIdentificacion, per.perNumeroIdentificacion, roa.roaEstadoAfiliado, roa.roaid, per.perid,
													  case when roa.roaFechaAfiliacion = min(roa.roaFechaAfiliacion) over (partition by roa.roaafiliado) then roa.roaFechaAfiliacion
													  else null 
													  end as [FechaAfiliacion],
													  'Trabajador' AS 'Tipo'
												 from core.dbo.Persona per with (nolock)
												inner join core.dbo.Afiliado afi with (nolock) on per.perID = afi.afiPersona
												inner join core.dbo.RolAfiliado roa with (nolock) on  afi.afiId = roa.roaAfiliado
												where per.perNumeroIdentificacion = @documentoIdBeneficiario
												  and per.perTipoIdentificacion = @tipoDocumentoBeneficiario --and roa.roaEstadoAfiliado IN ('ACTIVO' )
											  ) ti
										
									   ) 
											begin 
												print ('El beneficiario es trabajador activo')
												delete @Beneficiario where perNumeroIdentificacion = @documentoIdBeneficiario and perTipoIdentificacion = @tipoDocumentoBeneficiario
												set @countben = @countben+1
												print(concat(@countben,'- Beneficiario ',@documentoIdBeneficiario,' ',@tipoDocumentoBeneficiario,': NO'))
											end 
							else 
											begin 
												print ('Es beneficiario: ' + @documentoIdBeneficiario + ' ' + @tipoDocumentoBeneficiario)
												insert into #tmp_filaPersonas 
												values (@documentoIdBeneficiario, @tipoDocumentoBeneficiario, @solNumeroRadicacion, @tipo)
												delete @Beneficiario where perNumeroIdentificacion = @documentoIdBeneficiario and perTipoIdentificacion = @tipoDocumentoBeneficiario
												set @countben = @countben+1
												print(concat(@countben,'- Beneficiario ',@documentoIdBeneficiario,' ',@tipoDocumentoBeneficiario, ': SI'))
											end

					end
	--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	-- FIN DE LLAMADO AL PROCEDIMIENTO PARA ACTUALIZAR LOS BENEFICIARIOS DE UN TRABAJADOR ACTIVO
	--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	delete @Afiliado where perNumeroIdentificacion = @perNumeroIdentificacion and perTipoIdentificacion = @perTipoIdentificacion and solNumeroRadicacion = @solNumeroRadicacion
end
--select top 30 perNumeroIdentificacion, perTipoIdentificacion, solNumeroRadicacion, max(tipo) 
--from #tmp_filaPersonas  tm with (nolock)
--where solNumeroRadicacion IS NOT NULL 
--and not exists (select solNumeroRadicacion 
--										from sap.PersonasCtrl PCTR with (nolock) where PCTR.solNumeroRadicacion=tm.solNumeroRadicacion and PCTR.perNumeroIdentificacion = tm.perNumeroIdentificacion and PCTR.perTipoIdentificacion = tm.perTipoIdentificacion )
--group by perNumeroIdentificacion, perTipoIdentificacion, solNumeroRadicacion;

declare @count bigint = 0
declare @count2 bigint = (select count(*) from #tmp_filaPersonas)
print (concat(@count2,' Ciclos USP_GetPersonas_documentoId'))
while (@count < @count2)
    BEGIN TRY
		BEGIN TRANSACTION
        -- Obtener el primer registro de la tabla temporal
        select top 1 @solNumeroRadicacion = tm.solNumeroRadicacion, @perTipoIdentificacion = tm.perTipoIdentificacion, @perNumeroIdentificacion = tm.perNumeroIdentificacion
        from #tmp_filaPersonas tm
		left join sap.PersonasCtrl PCTR with (nolock) on PCTR.solNumeroRadicacion=tm.solNumeroRadicacion and PCTR.perNumeroIdentificacion = tm.perNumeroIdentificacion and PCTR.perTipoIdentificacion = tm.perTipoIdentificacion
		where tm.solNumeroRadicacion is not null and PCTR.id is null
		print(concat('[SAP].[USP_GetPersonas_documentoId] ',@perNumeroIdentificacion,' ',@perTipoIdentificacion,' ',@solNumeroRadicacion))
		
        -- Logica para procesar los parametros y realizar las operaciones necesarias
		EXEC [SAP].[USP_GetPersonas_documentoId] @perNumeroIdentificacion, @perTipoIdentificacion, @solNumeroRadicacion;

		insert into sap.PersonasCtrl
		select @solNumeroRadicacion, 1, @perNumeroIdentificacion, @perTipoIdentificacion

        -- Eliminar el registro procesado de la tabla temporal
        delete from #tmp_filaPersonas where solNumeroRadicacion = @solNumeroRadicacion and perTipoIdentificacion = @perTipoIdentificacion and perNumeroIdentificacion = @perNumeroIdentificacion; 
	
		set @count = @count+1
		print (@count)

		COMMIT TRANSACTION
	END TRY
	
	BEGIN CATCH
		ROLLBACK TRANSACTION
	  SELECT
		ERROR_NUMBER() AS ErrorNumber,
		ERROR_STATE() AS ErrorState,
		ERROR_SEVERITY() AS ErrorSeverity,
		ERROR_PROCEDURE() AS ErrorProcedure,
		ERROR_LINE() AS ErrorLine,
		ERROR_MESSAGE() AS ErrorMessage,
		@perNumeroIdentificacion AS PerNumeroIdentificacion,
		@perTipoIdentificacion AS perTipoIdentificacion,
		@solNumeroRadicacion AS solNumeroRadicacion;
	END CATCH;
print('Integra')
END