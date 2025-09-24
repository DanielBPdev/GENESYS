-- =============================================
-- Author:      <Juan C. Avila Meza>
-- Create Date: <23/01/2023>
-- Description: <Reporte Empleadores Giass 2.0>
-- EXEC dbo.sp_ReporteGiass_Empleadores
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[sp_ReporteGiass_Empleadores]
AS
BEGIN
    SET NOCOUNT ON

--truncate table ReporteEmpleadoresGiass

--create table ReporteEmpleadoresGiass(
--repempgId bigInt IDENTITY(1,1) PRIMARY KEY,
--tipo_identificacion_id INT,
--numero_identificacion nvarchar(60),	
--digito_verificacion	int,
--razon_social nvarchar(200),
--Sigla nvarchar(5),	
--primer_nombre nvarchar(100),	
--segundo_nombre nvarchar(100),	
--primer_apellido nvarchar(100),	
--segundo_apellido nvarchar(100),	
--clase_empleador_id int,
--tipo_sociedad_id INT,	
--fecha_inicio_actividad_economica date,
--numero_matricula_mercantil nvarchar(200),
--fecha_matricula_mercantil nvarchar(200),
--fecha_renovacion_matricula_mercantil nvarchar(200),
--fecha_cancelacion_matricula_mercantil nvarchar(200),
--pais_id VARCHAR(10),
--departamento_id	int,
--municipio_id int,	
--centro_poblado_id int,	
--telefono1 nvarchar(15),	
--telefono2 nvarchar(15),	
--Celular nvarchar(15),
--correo_electronico nvarchar(200),	
--tipo_sector_id nvarchar(100),
--ciiu_clase_ppal_id int,
--ciiu_clase_secundaria_id int,
--tipo_identificacion_id_novedad int,	
--numero_identificacion_novedad nvarchar(60),
--tipo_cargue	 int,
--fecha_novedad date)


-- Creamos una tabla temporal y la llenamos con la información de las empresas.
DROP TABLE IF EXISTS #tmpDatos
SELECT distinct 
(Case when P.perTipoIdentificacion = 'CEDULA_CIUDADANIA' then 1
	  when P.perTipoIdentificacion = 'TARJETA_IDENTIDAD' then 2
	  when P.perTipoIdentificacion = 'REGISTRO_CIVIL' then 3
	  when P.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' then 4
	  when P.perTipoIdentificacion = 'PASAPORTE' then 6
	  when P.perTipoIdentificacion = 'NIT' then 7
	  when P.perTipoIdentificacion = 'CARNE_DIPLOMATICO' then 8
	  when P.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' then 9
	  when P.perTipoIdentificacion = 'PERM_PROT_TEMPORAL' then 15
	  end) as [tipo_identificacion_id]
,p.perNumeroIdentificacion [numero_identificacion]
,p.perDigitoVerificacion [digito_verificacion]
,p.perRazonSocial [razon_social]
,NULL [Sigla]
,p.perPrimerNombre [primer_nombre]
,p.perSegundoNombre [segundo_nombre]
,p.perPrimerApellido [primer_apellido]
,p.perSegundoApellido [segundo_apellido]
,isnull((select  TOP 1 CASE WHEN  s.solClasificacion = 'PERSONA_JURIDICA' THEN 1 
			                 WHEN s.solClasificacion = 'PERSONA_NATURAL'  THEN 2
							 WHEN s.solClasificacion = 'EMPLEADOR_DE_SERVICIO_DOMESTICO' THEN 2
							 WHEN s.solClasificacion = 'ORGANIZACION_RELIGIOSA_O_PARROQUIA' THEN 1
							 WHEN s.solClasificacion = 'PROPIEDAD_HORIZONTAL'  THEN 1
							 WHEN s.solClasificacion = 'COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO' THEN 1
			 ELSE NULL END
			 from SolicitudAfiliaciEmpleador se WITH(NOLOCK) 
		     inner join solicitud s  WITH(NOLOCK) ON se.saeSolicitudGlobal = s.solid 
			 where    se.saeEmpleador = empl.empId order by solFechaRadicacion desc  ) ,'') AS  [clase_empleador_id]
--,ISNULL((select top 1 s.solClasificacion from SolicitudAfiliaciEmpleador se WITH(NOLOCK) 
--						inner join solicitud s  WITH(NOLOCK) ON se.saeSolicitudGlobal = s.solid 
--						where    se.saeEmpleador = empl.empid order by solFechaRadicacion desc ),'') as [tipo_sociedad_id]

,((select  TOP 1 CASE WHEN  s.solClasificacion = 'PERSONA_JURIDICA' THEN NULL 
			          WHEN s.solClasificacion = 'PERSONA_NATURAL'  THEN 3
					  WHEN s.solClasificacion = 'EMPLEADOR_DE_SERVICIO_DOMESTICO' THEN 8
					  WHEN s.solClasificacion = 'ENTIDAD_SIN_ANIMO_DE_LUCRO' THEN 9
					  WHEN s.solClasificacion = 'MIXTA'  THEN 11
					  WHEN s.solClasificacion = 'COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO' THEN 1
			 ELSE NULL END
			 from SolicitudAfiliaciEmpleador se WITH(NOLOCK) 
		     inner join solicitud s  WITH(NOLOCK) ON se.saeSolicitudGlobal = s.solid 
			 where    se.saeEmpleador = empl.empId order by solFechaRadicacion desc  )) as [tipo_sociedad_id]

,cast(empr.empFechaConstitucion AS date)  [fecha_inicio_actividad_economica]
,NULL [numero_matricula_mercantil]
,NULL [fecha_matricula_mercantil]
,NULL [fecha_renovacion_matricula_mercantil]
,NULL [fecha_cancelacion_matricula_mercantil]
,'170' [pais_id]  --GLPI 86238
,dep.depId [departamento_id]
,mun.munId [municipio_id]
,NULL [centro_poblado_id]
,ubi.ubiTelefonoFijo [telefono1]
,ubi.ubiTelefonoFijo [telefono2]
,ubi.ubiTelefonoCelular [Celular]
,ubi.ubiEmail [correo_electronico]
,(CASE WHEN empr.empNaturalezaJuridica = 'PUBLICA' THEN 1
       WHEN empr.empNaturalezaJuridica = 'PRIVADA' THEN 2
	   WHEN empr.empNaturalezaJuridica = 'MIXTA' THEN 3 END) AS [tipo_sector_id]
,(SELECT top 1 CIIU.ciiCodigo FROM Empresa empre left join SucursalEmpresa sce on empre.empId = sce.sueEmpresa left join CodigoCIIU CIIU ON CIIU.ciiId = empre.empCodigoCIIU
  where empre.empId = empr.empId  order by sce.sueCodigo asc) [ciiu_clase_ppal_id]
, CASE WHEN (SELECT top 1 CIIU.ciiCodigo FROM Empresa empre left join SucursalEmpresa sce on empre.empId = sce.sueEmpresa left join CodigoCIIU CIIU ON CIIU.ciiId = empre.empCodigoCIIU
  where empre.empId = empr.empId  order by sce.sueCodigo desc) = (SELECT top 1 CIIU.ciiCodigo FROM Empresa empre left join SucursalEmpresa sce on empre.empId = sce.sueEmpresa left join CodigoCIIU CIIU ON CIIU.ciiId = empre.empCodigoCIIU
  where empre.empId = empr.empId  order by sce.sueCodigo asc) THEN NULL ELSE
  (SELECT top 1 CIIU.ciiCodigo FROM Empresa empre left join SucursalEmpresa sce on empre.empId = sce.sueEmpresa left join CodigoCIIU CIIU ON CIIU.ciiId = empre.empCodigoCIIU
  where empre.empId = empr.empId  order by sce.sueCodigo desc) END   [ciiu_clase_secundaria_id]
into #tmpDatos
FROM Persona p
left join Empresa empr on p.perId = empr.empPersona
left join Empleador empl on empl.empEmpresa = empr.empId
left join UbicacionEmpresa ubie on ubie.ubeEmpresa = empr.empId and ubie.ubeTipoUbicacion = 'UBICACION_PRINCIPAL'
left join Ubicacion ubi on ubi.ubiId = ubie.ubeUbicacion
left join Municipio mun on mun.munId = ubi.ubiMunicipio
left join Departamento dep on dep.depId = mun.munDepartamento
Where empl.empEstadoEmpleador in('ACTIVO','INACTIVO')-- and p.perNumeroIdentificacion in ('890801137')           
order by 2,1


-- Consulta para verificar los cambios individual por numero de cedulas
insert into dbo.ReporteEmpleadoresGiass
SELECT distinct 
tmpd.[tipo_identificacion_id],tmpd.[numero_identificacion],tmpd.[digito_verificacion],tmpd.[razon_social],tmpd.[Sigla],tmpd.[primer_nombre],
tmpd.[segundo_nombre],tmpd.[primer_apellido],tmpd.[segundo_apellido],tmpd.[clase_empleador_id],tmpd.[tipo_sociedad_id],tmpd.[fecha_inicio_actividad_economica],
tmpd.[numero_matricula_mercantil],tmpd.[fecha_matricula_mercantil],tmpd.[fecha_renovacion_matricula_mercantil],tmpd.[fecha_cancelacion_matricula_mercantil],
tmpd.[pais_id],tmpd.[departamento_id],tmpd.[municipio_id],tmpd.[centro_poblado_id],tmpd.[telefono1],tmpd.[telefono2],tmpd.[Celular],tmpd.[correo_electronico],tmpd.[tipo_sector_id],	tmpd.[ciiu_clase_ppal_id],
tmpd.[ciiu_clase_secundaria_id]
,(select TOP 1 
	   MIN(case when p3.perTipoIdentificacion='CEDULA_CIUDADANIA' then '1' 
	   when p3.perTipoIdentificacion='TARJETA_IDENTIDAD' then '2'  
	   when p3.perTipoIdentificacion='REGISTRO_CIVIL' then '3' 
	   when p3.perTipoIdentificacion='CEDULA_EXTRANJERIA' then '4'
	   when p3.perTipoIdentificacion='PASAPORTE' then '6'
	   when p3.perTipoIdentificacion='NIT' then '7' 
	   when p3.perTipoIdentificacion='CARNE_DIPLOMATICO' then '8' 
	   when p3.perTipoIdentificacion='PERM_ESP_PERMANENCIA' then '9'
	   when p3.perTipoIdentificacion='PERM_PROT_TEMPORAL' then '15'
	   else NULL end) as tipo_identificacion_id
	   from Persona p3 where p3.perNumeroIdentificacion = tmpd.numero_identificacion group by p3.perId order by p3.perId asc) as [tipo_identificacion_id_novedad],
(select distinct p3.perNumeroIdentificacion from Persona p3 where p3.perNumeroIdentificacion = tmpd.numero_identificacion)  as [numero_identificacion_novedad]
,1 as [tipo_cargue],
convert(date,getdate()) as [fecha_novedad] 
From #tmpDatos tmpd 
except
select tipo_identificacion_id,numero_identificacion,digito_verificacion,razon_social,Sigla,primer_nombre,segundo_nombre
,primer_apellido,segundo_apellido,clase_empleador_id,tipo_sociedad_id,fecha_inicio_actividad_economica,numero_matricula_mercantil
,fecha_matricula_mercantil,fecha_renovacion_matricula_mercantil,fecha_cancelacion_matricula_mercantil,pais_id,departamento_id
,municipio_id,centro_poblado_id,telefono1,telefono2,Celular,correo_electronico,tipo_sector_id,ciiu_clase_ppal_id
,ciiu_clase_secundaria_id,tipo_identificacion_id_novedad,numero_identificacion_novedad,tipo_cargue,fecha_novedad from dbo.ReporteEmpleadoresGiass


-- visualizamos el reporte
select tipo_identificacion_id,numero_identificacion,digito_verificacion,razon_social,Sigla,primer_nombre,segundo_nombre
,primer_apellido,segundo_apellido,clase_empleador_id,tipo_sociedad_id,fecha_inicio_actividad_economica,numero_matricula_mercantil
,fecha_matricula_mercantil,fecha_renovacion_matricula_mercantil,fecha_cancelacion_matricula_mercantil,pais_id,departamento_id
,municipio_id,centro_poblado_id,telefono1,telefono2,Celular,correo_electronico,tipo_sector_id,ciiu_clase_ppal_id
,ciiu_clase_secundaria_id,tipo_identificacion_id_novedad,numero_identificacion_novedad,tipo_cargue,fecha_novedad 
from dbo.ReporteEmpleadoresGiass

END
