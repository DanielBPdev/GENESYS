/****** Object:  StoredProcedure [dbo].[reporteAsigPagoReinFovis]    Script Date: 12/12/2024 3:14:42 p. m. ******/
-- =============================================
-- Author:      Gustavo Giraldo
-- Create Date: 6 Marzo 2021
-- Description: Procedimiento almacenado encargado de ejecutar la consulta Asignación, pago y reintegro de subsidios de viviendas FOVIS con sus rangos de fechas
-- Reporte Normativo 16 ---5433 A
---EXEC [reporteAsigPagoReinFovis] '2024-10-01','2024-10-31'
-- =============================================
CREATE OR ALTER   procedure [dbo].[reporteAsigPagoReinFovis]
(
    @fechaInicio DATE,
	@fechaFin DATE
)
AS
begin	

--declare     @fechaInicio DATE ='2024-10-01'
--declare @fechaFin DATE ='2024-10-31'
	drop table if exists #Temp
	create table #Temp(
		perTipoCodIdentificacion varchar(20)
		,perTipoIdentificacion varchar(20))

	insert into #Temp values
	('1','CEDULA_CIUDADANIA')
	,('2','TARJETA_IDENTIDAD')
	,('3','REGISTRO_CIVIL')
	,('4','CEDULA_EXTRANJERIA')
	,('6','PASAPORTE')
	,('7','NIT')
	,('8','CARNE_DIPLOMATICO')	 
	,('9','PERM_ESP_PERMANENCIA')
		

	select 
		h.hamAnioVigenciaAsignacionSubsidio as [Año vigencia de asignación del subsidio]
		,'7' as [Fuente de financiamiento]
		,case 
			when pof.pofmodalidad = 'ADQUISICION_VIVIENDA_NUEVA_URBANA'	then 1	
			when pof.pofmodalidad = 'CONSTRUCCION_SITIO_PROPIO_URBANO'	then 2
			when pof.pofmodalidad = 'MEJORAMIENTO_VIVIENDA_URBANA'		then 3
			when pof.pofmodalidad = 'ADQUISICION_VIVIENDA_USADA_URBANA'	then 4
			when pof.pofmodalidad = 'MEJORAMIENTO_VIVIENDA_SALUDABLE'	then 5
			when pof.pofmodalidad = 'CONSTRUCCION_SITIO_PROPIO_RURAL'	then 6
			when pof.pofmodalidad = 'ADQUISICION_VIVIENDA_NUEVA_RURAL'	then 7
			when pof.pofmodalidad = 'MEJORAMIENTO_VIVIENDA_RURAL'		then 8
		end [Código tipo plan de vivienda]
		,(select mun.munCodigo 
			from Municipio as mun
				inner join Ubicacion as ubi on ubi.ubiMunicipio = mun.munId
				inner join Persona as per2 on per2.perUbicacionPrincipal = ubi.ubiId
			where per2.perid = per.perId) as [Código municipio]
		,case ped.pedGenero
			when 'MASCULINO' then 1
			when 'FEMENINO' then 2
			else 4
		end [Sexo]
		,case 
			when 60 <= (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int))	then 22			
			when 50 <= (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) and (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) < 60	then 21			
			when 29 <= (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) and (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) < 50	then 20			
			when 22 <= (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) and (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) < 29	then 19			
			when 18 <= (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) and (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) < 22	then 18			
			when 16 <= (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) and (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) < 18	then 17	
			when 13 <= (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) and (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) < 16	then 16			
			when 10 <= (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) and (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) < 13	then 15			
			when 7  <= (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) and (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) < 10	then 14			
			when 3  <= (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) and (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) < 7	then 13			
			when 1  <= (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) and (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) < 3	then 12
			when 0  <= (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) and (cast(datediff(dd, ped.pedfechanacimiento, getdate()) / 365.25 as int)) < 1	then 11
			else 1
		end	as [Rango de edad] 
		,h.hamNivelIngreso as [Nivel de ingreso]
		,case 
			when h.hamComponente = 1 then 1
			when h.hamComponente = 2 then 2
			when h.hamComponente = 5 then 7
			when h.hamComponente = 6 then 8
		end as [Componente]
		,case h.hamEstadoSubsidio
			when 1 then 1
			when 2 then 4
			when 3 then 5
			when 4 then 6
			when 5 then 7
			when 6 then 8
		end as [Estado del subsidio]
		,count(*) as [Cantidad de subsidios]
		,sum(h.hamValorSubsidios) as [Valor subsidios]
	from rno.HistoricoAsignacionEntregaReintegroMicrodatoFOVIS as h
		inner join PostulacionFOVIS as pof on h.hamPofid = pof.pofId
		inner join #Temp as t on t.perTipoCodIdentificacion = h.hamTipoIdentificacion
		inner join Persona as per 
			on per.perTipoIdentificacion = t.perTipoIdentificacion
			and per.perNumeroIdentificacion = h.hamNumeroIdentificacion
		inner join PersonaDetalle as ped on ped.pedPersona = per.perId
	where h.hamComponente <> 4
	group by 
		h.hamAnioVigenciaAsignacionSubsidio
		,h.hamFuenteFinanciamiento
		,h.hamCodigoTipoPlanVivienda
		,per.perId
		,ped.pedGenero
		,ped.pedfechanacimiento
		,h.hamPofId
		,h.hamNivelIngreso
		,h.hamComponente
		,h.hamEstadoSubsidio
		,pof.pofmodalidad

end
