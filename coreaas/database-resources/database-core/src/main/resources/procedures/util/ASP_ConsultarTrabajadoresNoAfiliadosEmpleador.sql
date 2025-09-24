create or ALTER procedure ASP_ConsultarTrabajadoresNoAfiliadosEmpleador
	@idEmpleador Bigint

as
SET NOCOUNT ON;

;with afi as (SELECT afi.afiPersona
			FROM RolAfiliado roa
			JOIN Afiliado afi ON (roa.roaAfiliado = afi.afiId)
			where roa.roaEmpleador = @idEmpleador)
SELECT per.perTipoIdentificacion as perTipoIdentificacion,
per.perNumeroIdentificacion as perNumeroIdentificacion,
per.perRazonSocial as perRazonSocial,
'NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES' AS roaEstadoAfiliado,
'' AS nombreComercial,
per.perPrimerNombre as perPrimerNombre,
per.perSegundoNombre as perSegundoNombre,
per.perPrimerApellido as perPrimerApellido,
per.perSegundoApellido as perSegundoApellido,
per.perId as perId,
per.perId as idEmpresa,
GETDATE() AS fechaIngreso,
GETDATE() AS fechaRetiro
from dbo.AporteGeneral as apg with (nolock)
inner join dbo.aporteDetallado as apd with (nolock) on apg.apgId = apd.apdAporteGeneral
inner join dbo.persona as per with (nolock) on per.perId = apd.apdPersona
left join afi on afi.afiPersona = apd.apdPersona
/*
left join (SELECT afi.afiPersona
			FROM RolAfiliado roa
			JOIN Afiliado afi ON (roa.roaAfiliado = afi.afiId)
			where roa.roaEmpleador = 18) as af on af.afiPersona = per.perId
*/
where apg.apgEmpresa = (select empEmpresa from dbo.Empleador with (nolock) where empId = @idEmpleador)
and afi.afiPersona is null
group by per.perPrimerNombre,
per.perSegundoNombre,
per.perPrimerApellido,
per.perSegundoApellido,
per.perId,
per.perTipoIdentificacion,
per.perNumeroIdentificacion,
per.perRazonSocial

return