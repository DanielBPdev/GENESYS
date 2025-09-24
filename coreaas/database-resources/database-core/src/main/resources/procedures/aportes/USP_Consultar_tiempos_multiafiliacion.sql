CREATE OR ALTER PROCEDURE [dbo].[USP_Consultar_tiempos_multiafiliacion]
(
	@numeroIdentificacion varchar(30)
	,@estadoAfiliado varchar(30)

)
as 
BEGIN
select sum(datediff(day, hraFechaIngreso, case when hraFechaRetiro is null then GETDATE() else hraFechaRetiro end))
						FROM RolAfiliado roa
						inner join Afiliado afi on afiId = roa.roaAfiliado
						inner join Persona perAfi on perAfi.perId = afi.afiPersona
						LEFT JOIN Empleador empl ON roa.roaEmpleador = empl.empId
						LEFT JOIN Empresa emp ON empl.empEmpresa = emp.empId
						LEFT JOIN Persona perEmp ON emp.empPersona = perEmp.perId
						LEFT JOIN SolicitudAfiliacionPersona sap ON roa.roaId = sap.sapRolAfiliado
					    LEFT JOIN Solicitud sl on sap.sapSolicitudGlobal = sl.solId 
						inner join HistoricoRolAfiliado hra on hra.hraAfiliado = roa.roaAfiliado and sl.solId = hra.hraSolicitud
						WHERE perAfi.perNumeroIdentificacion = @numeroIdentificacion
						--and roaTipoAfiliado = 'PENSIONADO' and hraTipoAfiliado = 'PENSIONADO'
	END;