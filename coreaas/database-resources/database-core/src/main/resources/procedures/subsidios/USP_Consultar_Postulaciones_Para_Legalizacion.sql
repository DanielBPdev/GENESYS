-- =============================================
-- Author: Valentina Arias
-- Create date: 23/01/2025
-- Description:	Consultar las postulaciones aptas para legalizar
-- =============================================
create or alter procedure [dbo].[USP_Consultar_Postulaciones_Para_Legalizacion] (@numeroRadicadoSolicitud varchar(30), @tipoIdentificacion varchar(20), @numeroIdentificacion varchar(20))

as
begin
set nocount on;
	
	declare @perId bigint 
	;with per as 
	(select perId
	from Persona
	where perTipoIdentificacion = @tipoIdentificacion and 
	perNumeroIdentificacion = @numeroIdentificacion)
	select @perId = perId 
	from per

	declare @solId bigint 
	;with sol as 
	(select solId
	from Solicitud
	where solNumeroRadicacion = @numeroRadicadoSolicitud)
	select @solId = solId 
	from sol

	
	if @perId is not null
		begin
			select top 1
				sol.solNumeroRadicacion AS numeroRadicacion,
				per.perTipoIdentificacion AS tipoIdentificacion,
				per.perNumeroIdentificacion AS numeroIdentificacion,
				per.perRazonSocial AS nombreCompletoJefeHogar,
				pof.pofEstadoHogar AS estadoHogar,
				aas.aafFinVigencia AS fechaFinVigencia,
				sol.solId AS idSolicitudGlobalPostulacion,
				spo.spoId AS idSolicitudPostulacion,
				pof.pofId AS idPostulacionFovis,
				sld.sldId as solicitudLegalizacionEnCusrso,
				solleg.solId as idSolicitudGlobalLegalizacionDesembolso,
				aas.aafInicioVigencia as fechaInicioVigencia
			from SolicitudPostulacion spo with(nolock)
				inner join Solicitud sol with(nolock) on sol.solId = spo.spoSolicitudGlobal
				inner join PostulacionFOVIS pof with(nolock) on pof.pofId = spo.spoPostulacionFOVIS
				inner join JefeHogar jeh with(nolock) on jeh.jehId = pof.pofJefeHogar
				inner join Afiliado afi with(nolock) on afi.afiId = jeh.jehAfiliado
				inner join Persona per with(nolock) on per.perId = afi.afiPersona
				inner join ActaAsignacionFovis aas with(nolock) on aas.aafSolicitudAsignacion = pof.pofSolicitudasignacion
				left join SolicitudLegalizaciondesembolso sld with(nolock)on pof.pofId = sld.sldPostulacionFOVIS and isnull(sldEstadoSolicitud,'LEGALIZACION_Y_DESEMBOLSO_CERRADO') != 'LEGALIZACION_Y_DESEMBOLSO_CERRADO'
				left join Solicitud solleg with(nolock) on sld.sldSolicitudGlobal = solleg.solId
			where per.perid = @perId
			order by sol.solid desc
		end 
	else
		begin
				select
				sol.solNumeroRadicacion as numeroRadicacion,
				per.perTipoIdentificacion as tipoIdentificacion,
				per.perNumeroIdentificacion as numeroIdentificacion,
				per.perRazonSocial as nombreCompletoJefeHogar,
				pof.pofEstadoHogar as estadoHogar,
				aas.aafFinVigencia as fechaFinVigencia,
				sol.solId as idSolicitudGlobalPostulacion,
				spo.spoId as idSolicitudPostulacion,
				pof.pofId as idPostulacionFovis,
				sld.sldId as solicitudLegalizacionEnCusrso,
				solleg.solId as idSolicitudGlobalLegalizacionDesembolso,
				aas.aafInicioVigencia as fechaInicioVigencia
			from SolicitudPostulacion spo with(nolock)
				inner join Solicitud sol with(nolock) on sol.solId = spo.spoSolicitudGlobal
				inner join PostulacionFOVIS pof with(nolock) on pof.pofId = spo.spoPostulacionFOVIS
				inner join JefeHogar jeh with(nolock) on jeh.jehId = pof.pofJefeHogar
				inner join Afiliado afi with(nolock) on afi.afiId = jeh.jehAfiliado
				inner join Persona per with(nolock) on per.perId = afi.afiPersona
				inner join ActaAsignacionFovis aas with(nolock) on aas.aafSolicitudAsignacion = pof.pofSolicitudasignacion
				left join SolicitudLegalizaciondesembolso sld with(nolock)on pof.pofId = sld.sldPostulacionFOVIS and isnull(sldEstadoSolicitud,'LEGALIZACION_Y_DESEMBOLSO_CERRADO') != 'LEGALIZACION_Y_DESEMBOLSO_CERRADO'
				left join Solicitud solleg with(nolock) on sld.sldSolicitudGlobal = solleg.solId
			where sol.solId = @solId
		end


end