-- =============================================
-- Author:      <Sergio Bayona>
-- Create Date: <23/01/2024>
-- Description: <Reporte giass personas>
--EXEC dbo.ReporteGiass_Personas
-- =============================================
CREATE OR ALTER     PROCEDURE [dbo].[ReporteGiass_Personas]

AS
BEGIN

SET NOCOUNT ON

--drop table ReportePersonas_Giass
--create table ReportePersonas_Giass (
--tipo_identificacion_id varchar(50),
--numero_identificacion varchar(25),
--fecha_expedicion varchar(10),
--pais_expedicion_id varchar(30),
--departamento_expedicion_id varchar(100),
--municipio_expedicion_id varchar(100),
--centro_poblado_expedicion_id varchar(50),
--nacionalidad_id varchar(100),
--primer_nombre varchar(100),
--segundo_nombre varchar(100),
--primer_apellido varchar(100),
--segundo_apellido varchar(100),
--genero_id int,
--fecha_nacimiento varchar(10),
--pais_nacimiento_id varchar(50),
--departamento_nacimiento_id varchar(60),
--municipio_nacimiento_id varchar(100),
--centro_poblado_nacimiento_id varchar(25),
--pais_residencia_id varchar(60),
--departamento_residencia_id varchar(100),
--municipio_residencia_id varchar(100),
--centro_poblado_residencia_id varchar(100),
--fuente_validacion varchar(100),
--fecha_validacion_fuente varchar(10),
--codigo_verificacion_rnce varchar(20),
--fecha_defuncion varchar(10),
--fecha_reporte_novedad_defuncion varchar(10),
--tipo_identificacion_padre_biologico_id varchar(50),
--numero_identificacion_padre_biologico varchar (60),
--tipo_identificacion_madre_biologico_id varchar(50),
--numero_identificacion_madre_biologico varchar(60),
--indicador_discapacidad varchar(20),
--fecha_inicio_discapacidad varchar(10),
--tipo_discapacidad_id bit,
--tipo_identificacion_id_novedad varchar (60),
--numero_identificacion_novedad varchar(60), 
----Estado_Afiliado_Principal varchar(150),
--tipo_cargue int,
--fecha_novedad varchar(10))

drop table if exists #Personas_reporte
SELECT distinct
	case  when p.perTipoIdentificacion='CEDULA_CIUDADANIA' then '1' 
		when p.perTipoIdentificacion='TARJETA_IDENTIDAD' then '2'  
		when p.perTipoIdentificacion='REGISTRO_CIVIL' then '3' 
		when p.perTipoIdentificacion='CEDULA_EXTRANJERIA' then '4'
		when p.perTipoIdentificacion='PASAPORTE' then '6'
		when p.perTipoIdentificacion='NIT' then '7' 
		when p.perTipoIdentificacion='CARNE_DIPLOMATICO' then '8' 
		when p.perTipoIdentificacion='PERM_ESP_PERMANENCIA' then '9'
		when p.perTipoIdentificacion='PERM_PROT_TEMPORAL' then '15'
		end as [tipo_identificacion_id],
	p.perNumeroIdentificacion [numero_identificacion],
	isnull(cast(cast(pedFechaExpedicionDocumento as date) as nvarchar),'') [fecha_expedicion],
	'' as [pais_expedicion_id],
	'' as [departamento_expedicion_id],
	'' as [municipio_expedicion_id],
	'' as [centro_poblado_expedicion_id],
	'' as [nacionalidad_id],
	p.perPrimerNombre    as [primer_nombre],
	p.perSegundoNombre   as [segundo_nombre],
	p.perPrimerApellido  as [primer_apellido],
	p.perSegundoApellido as [segundo_apellido],
	case when pedGenero='MASCULINO' then '1' 
	     when pedGenero='FEMENINO'  then '2' 
	     when pedGenero='INDEFINIDO' then '4' else '3' end as [genero_id],
	pedFechaNacimiento as [fecha_nacimiento],
	pai.paiCodigo as [pais_nacimiento_id],
	'' as [departamento_nacimiento_id],
	'' as [municipio_nacimiento_id],
	'' as [centro_poblado_nacimiento_id],
	pai.paiCodigo as [pais_residencia_id],
	dep.depCodigo as [departamento_residencia_id],
	mun.munCodigo as [municipio_residencia_id],
	'' as [centro_poblado_residencia_id],
	'' as [fuente_validacion],
	'' as [fecha_validacion_fuente],
	'' as [codigo_verificacion_rnce],
	isnull(cast(pedFechaDefuncion as varchar(10)),'') 'fecha_defuncion',
	isnull(cast(pedFechaFallecido as varchar(10)),'') 'fecha_reporte_novedad_defuncion',
	case  when ppb.perTipoIdentificacion = 'CEDULA_CIUDADANIA' then '1' 
		  when ppb.perTipoIdentificacion = 'TARJETA_IDENTIDAD' then '2'  
		  when ppb.perTipoIdentificacion = 'REGISTRO_CIVIL' then '3' 
		  when ppb.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' then '4'
		  when ppb.perTipoIdentificacion = 'PASAPORTE' then '6'
		  when ppb.perTipoIdentificacion = 'NIT' then '7' 
		  when ppb.perTipoIdentificacion = 'CARNE_DIPLOMATICO' then '8' 
		  when ppb.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' then '9'
		  when ppb.perTipoIdentificacion = 'PERM_PROT_TEMPORAL' then '15'
		  else '' end as [tipo_identificacion_padre_biologico_id],
	isnull(ppb.perNumeroIdentificacion,'') as [numero_identificacion_padre_biologico],
	case  when ppm.perTipoIdentificacion = 'CEDULA_CIUDADANIA' then '1' 
		  when ppm.perTipoIdentificacion = 'TARJETA_IDENTIDAD' then '2'  
		  when ppm.perTipoIdentificacion = 'REGISTRO_CIVIL' then '3' 
		  when ppm.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' then '4'
		  when ppm.perTipoIdentificacion = 'PASAPORTE' then '6'
		  when ppm.perTipoIdentificacion = 'NIT' then '7' 
		  when ppm.perTipoIdentificacion = 'CARNE_DIPLOMATICO' then '8' 
		  when ppm.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' then '9'
		  when ppm.perTipoIdentificacion = 'PERM_PROT_TEMPORAL' then '15'
		  else '' end as [tipo_identificacion_madre_biologico_id],
	isnull(ppm.perNumeroIdentificacion,'') as [numero_identificacion_madre_biologico],
	case when (select top 1 ci.coiInvalidez from Persona pinv inner join CondicionInvalidez ci on p.perId = ci.coiPersona where ci.coiInvalidez != 0 and pinv.perId = p.perId) IS NOT NULL THEN 1 ELSE 0 END   as 'indicador_discapacidad',
	isnull(cast(cast(coiFechaInicioInvalidez as date) as varchar),'') as  [fecha_inicio_discapacidad],
	case when coiInvalidez = 1 then 1 else 0 end as [tipo_discapacidad_id],
	case  when p.perTipoIdentificacion=p.perTipoIdentificacion AND p.perTipoIdentificacion='CEDULA_CUIDADANIA' THEN '1' 
		when p.perTipoIdentificacion=p.perTipoIdentificacion AND p.perTipoIdentificacion='TARJETA_IDENTIDAD' then '2' 
		when p.perTipoIdentificacion=p.perTipoIdentificacion AND p.perTipoIdentificacion='REGISTRO_CIVIL' then '3' 
		when p.perTipoIdentificacion=p.perTipoIdentificacion AND p.perTipoIdentificacion='CEDULA_EXTRANJERIA' then '4' 
		when p.perTipoIdentificacion=p.perTipoIdentificacion AND p.perTipoIdentificacion='PASAPORTE'then '6'
		when p.perTipoIdentificacion=p.perTipoIdentificacion AND p.perTipoIdentificacion='NIT' then '7' 
		when p.perTipoIdentificacion=p.perTipoIdentificacion AND p.perTipoIdentificacion='CARNE_DIPLOMATICO' then '8'
		when p.perTipoIdentificacion=p.perTipoIdentificacion AND p.perTipoIdentificacion='PERM_ESP_PERMANENCIA' then '9'
		when p.perTipoIdentificacion=p.perTipoIdentificacion AND p.perTipoIdentificacion='PERM_ESP_PERMANENCIA' then '15'
		else '' end as [tipo_identificacion_id_novedad],
	case  when p.perNumeroIdentificacion=p.perNumeroIdentificacion AND p.perTipoIdentificacion='CEDULA_CUIDADANIA' then p.perNumeroIdentificacion 
		when p.perTipoIdentificacion=p.perTipoIdentificacion AND p.perTipoIdentificacion='TARJETA_IDENTIDAD' then p.perNumeroIdentificacion  
		when p.perTipoIdentificacion=p.perTipoIdentificacion AND p.perTipoIdentificacion='REGISTRO_CIVIL' then p.perNumeroIdentificacion 
		when p.perTipoIdentificacion=p.perTipoIdentificacion AND p.perTipoIdentificacion='CEDULA_EXTRANJERIA' then p.perNumeroIdentificacion 
		when p.perTipoIdentificacion=p.perTipoIdentificacion AND p.perTipoIdentificacion='PASAPORTE'then p.perNumeroIdentificacion 
		when p.perTipoIdentificacion=p.perTipoIdentificacion AND p.perTipoIdentificacion='NIT' then p.perNumeroIdentificacion 
		when p.perTipoIdentificacion=p.perTipoIdentificacion AND p.perTipoIdentificacion='CARNE_DIPLOMATICO' then p.perNumeroIdentificacion 
		when p.perTipoIdentificacion=p.perTipoIdentificacion AND p.perTipoIdentificacion='PERM_ESP_PERMANENCIA' then p.perNumeroIdentificacion 
		when p.perTipoIdentificacion=p.perTipoIdentificacion AND p.perTipoIdentificacion='PERM_ESP_PERMANENCIA' then p.perNumeroIdentificacion 
		else '' end as [numero_identificacion_novedad],
		2 'tipo_cargue',
		cast(getdate() as date)'fecha_novedad'
into #Personas_reporte
FROM Persona p
	INNER join PersonaDetalle pd on p.perId = pd.pedPersona
	INNER join Pais pai on pai.paiId = pd.pedPaisResidencia
	INNER join Ubicacion ubi on p.perUbicacionPrincipal = ubi.ubiId
	INNER join Municipio mun on mun.munId = ubi.ubiMunicipio
	INNER join Departamento dep on dep.depId = mun.munDepartamento
	LEFT JOIN  Persona ppb on ppb.perId = pd.pedPersonaPadre
	LEFT JOIN  Persona ppm on ppm.perId = pd.pedPersonaMadre
	LEFT join CondicionInvalidez with(nolock) on coiPersona=p.perId
where p.perTipoIdentificacion NOT IN('SALVOCONDUCTO','NIT') and (p.perPrimerNombre IS NOT NULL and p.perPrimerApellido IS NOT NULL) 
create clustered index idex_per on #Personas_reporte (numero_identificacion) 



insert into ReportePersonas_Giass 
select 
	tipo_identificacion_id,numero_identificacion ,fecha_expedicion ,pais_expedicion_id ,departamento_expedicion_id ,municipio_expedicion_id ,centro_poblado_expedicion_id,nacionalidad_id ,primer_nombre ,segundo_nombre,primer_apellido ,segundo_apellido ,genero_id ,fecha_nacimiento ,pais_nacimiento_id,
	departamento_nacimiento_id ,municipio_nacimiento_id ,centro_poblado_nacimiento_id ,pais_residencia_id ,departamento_residencia_id ,municipio_residencia_id ,centro_poblado_residencia_id ,fuente_validacion ,fecha_validacion_fuente ,codigo_verificacion_rnce ,fecha_defuncion ,
	fecha_reporte_novedad_defuncion ,tipo_identificacion_padre_biologico_id ,numero_identificacion_padre_biologico ,tipo_identificacion_madre_biologico_id ,numero_identificacion_madre_biologico ,indicador_discapacidad ,fecha_inicio_discapacidad ,tipo_discapacidad_id ,tipo_identificacion_id_novedad ,
	numero_identificacion_novedad , tipo_cargue ,fecha_novedad 
from #Personas_reporte 
except 
select 
	tipo_identificacion_id,numero_identificacion ,fecha_expedicion ,pais_expedicion_id ,departamento_expedicion_id ,municipio_expedicion_id ,centro_poblado_expedicion_id,nacionalidad_id ,primer_nombre ,segundo_nombre,primer_apellido ,segundo_apellido ,genero_id ,fecha_nacimiento ,pais_nacimiento_id,
	departamento_nacimiento_id ,municipio_nacimiento_id ,centro_poblado_nacimiento_id ,pais_residencia_id ,departamento_residencia_id ,municipio_residencia_id ,centro_poblado_residencia_id ,fuente_validacion ,fecha_validacion_fuente ,codigo_verificacion_rnce ,fecha_defuncion ,
	fecha_reporte_novedad_defuncion ,tipo_identificacion_padre_biologico_id ,numero_identificacion_padre_biologico ,tipo_identificacion_madre_biologico_id ,numero_identificacion_madre_biologico ,indicador_discapacidad ,fecha_inicio_discapacidad ,tipo_discapacidad_id ,tipo_identificacion_id_novedad ,
	numero_identificacion_novedad , tipo_cargue ,fecha_novedad
from ReportePersonas_Giass


select
	tipo_identificacion_id,numero_identificacion ,fecha_expedicion ,pais_expedicion_id ,departamento_expedicion_id ,municipio_expedicion_id ,centro_poblado_expedicion_id,nacionalidad_id ,primer_nombre ,segundo_nombre,primer_apellido ,segundo_apellido ,genero_id ,fecha_nacimiento 
	,pais_nacimiento_id,departamento_nacimiento_id ,municipio_nacimiento_id ,centro_poblado_nacimiento_id 
	,pais_residencia_id ,departamento_residencia_id ,municipio_residencia_id ,centro_poblado_residencia_id ,fuente_validacion ,fecha_validacion_fuente ,codigo_verificacion_rnce ,
	CASE WHEN fecha_defuncion = '1900-01-01' THEN NULL ELSE fecha_defuncion END [fecha_defuncion],
	CASE WHEN fecha_reporte_novedad_defuncion = '1900-01-01' THEN NULL ELSE fecha_reporte_novedad_defuncion END [fecha_reporte_novedad_defuncion],tipo_identificacion_padre_biologico_id ,numero_identificacion_padre_biologico ,tipo_identificacion_madre_biologico_id ,numero_identificacion_madre_biologico 
	,ISNULL(indicador_discapacidad,'0')  [indicador_discapacidad] 
	,CASE WHEN fecha_inicio_discapacidad = '1900-01-01' THEN NULL ELSE fecha_inicio_discapacidad END [fecha_inicio_discapacidad] 
	,CAST(ISNULL(tipo_discapacidad_id,'0') AS INT) [tipo_discapacidad_id] ,tipo_identificacion_id_novedad ,
	numero_identificacion_novedad , tipo_cargue ,fecha_novedad
from ReportePersonas_Giass



END
