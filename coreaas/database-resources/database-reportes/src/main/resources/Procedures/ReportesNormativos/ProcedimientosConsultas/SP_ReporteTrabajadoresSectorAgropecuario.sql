/****** Object:  StoredProcedure [dbo].[reporteTrabajadoresSectorAgropecuario]    Script Date: 2023-07-13 9:21:06 PM ******/
/****** Object:  StoredProcedure [dbo].[reporteTrabajadoresSectorAgropecuario]    Script Date: 09/08/2022 11:16:16 a. m. ******/ 
/****** Script for SelectTopNRows command from SSMS  ******/

CREATE OR ALTER PROCEDURE [dbo].[reporteTrabajadoresSectorAgropecuario](
	@FECHA_INICIAL DATE,
	@FECHA_FINAL DATE
)

AS
BEGIN
	-- SET ANSI_WARNINGS OFF
	SET NOCOUNT ON
	SET ANSI_NULLS ON
SET QUOTED_IDENTIFIER ON
 
	
	SELECT 
		----------AÑo----------
		CONSOLIDADO.dsaFechaHoraCreacionAnio AS [AÑo],

		----------Mes----------
		CONSOLIDADO.dsaFechaHoraCreacionMes AS [Mes],

		---------Numero de Trabajadores----------
		COUNT(*) AS [Número de trabajadores del sector agropecuario a los cuales se les está realizando el pago adicional]

	FROM
	(
		SELECT 
			[dsaAfiliadoPrincipal],
			[dsaEmpleador],
			MONTH([dsaFechaHoraCreacion])  AS [dsaFechaHoraCreacionMes],
			YEAR([dsaFechaHoraCreacion])  AS [dsaFechaHoraCreacionAnio]
		FROM 
			[dbo].[DetalleSubsidioAsignado] d
			inner join Empleador emp on emp.empId = d.dsaEmpleador
			inner join Empresa empresa on empresa.empId = emp.empEmpresa
			inner join Persona perEmp on empresa.empPersona = perEmp.perId

			left join Afiliado afi on afi.afiId = d.dsaAfiliadoPrincipal
			left join RolAfiliado roa on roa.roaAfiliado = afi.afiId
		WHERE 
			dsaEstado = 'DERECHO_ASIGNADO'
			AND dsaTipoCuotaSubsidio LIKE '%AGRICOLA'
			AND convert(date,dsaFechaHoraCreacion) >= @FECHA_INICIAL
			AND convert(date,dsaFechaHoraCreacion) <= @FECHA_FINAL
			/*AND EMP.empEstadoEmpleador <> 'INACTIVO'
			and roa.roaEstadoAfiliado <> 'INACTIVO'*/
		GROUP BY
			dsaAfiliadoPrincipal,
			dsaEmpleador,
			MONTH( dsaFechaHoraCreacion ),
			YEAR( dsaFechaHoraCreacion )
			
			--select top 10 * from DetalleSubsidioAsignado WHERE dsaEstado = 'DERECHO_ASIGNADO' AND dsaTipoCuotaSubsidio LIKE '%AGRICOLA'
			
	)AS CONSOLIDADO

	group by 
		CONSOLIDADO.dsaFechaHoraCreacionAnio,
		CONSOLIDADO.dsaFechaHoraCreacionMes
	    order by 1,2
END