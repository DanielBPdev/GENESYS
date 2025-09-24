	CREATE OR ALTER PROCEDURE [dbo].[ReporteDetalladoTrabajadoresSectorAgropecuario]  (
					@fechaInicio DATETIME,
						@fechaFin DATETIME
		) AS
	

	BEGIN
	
	SET NOCOUNT ON

	SET ANSI_NULLS ON
 
	SET QUOTED_IDENTIFIER ON
	
	
	
	SELECT  DISTINCT
			dsaId,	dsaFechaHoraCreacion,	dsaEstado,	dsaMotivoAnulacion,
			dsaOrigenRegistroSubsidio,	dsaTipoliquidacionSubsidio,	dsaTipoCuotaSubsidio,
			dsaValorSubsidioMonetario,	dsaValorDescuento,	dsaValorOriginalAbonado,	dsaValorTotal,
			dsaPeriodoLiquidado	,
								pe.perTipoIdentificacion as TipoidentificacionEmpresa,
								    pe.perNumeroIdentificacion as NumeroidentificacionEmpresa,
								    pe.perDigitoVerificacion as DigitoVerificacionEmpresa,
								    pe.perRazonSocial as RazonSocialEmpresa,
								    pa.perTipoIdentificacion as TipoidentificacionAfiliado,
								    pa.perNumeroIdentificacion as NumeroidentificacionAfiliado,
								    pa.perPrimerNombre as PrimerNombreAfiliado,
								    pa.perSegundoNombre as SegundoNombreAfiliado,
								    pa.perPrimerApellido as PrimerApellidoAfiliado,
								    pa.perSegundoApellido as SegundoApellidoAfiliado,
									pb.perTipoIdentificacion as TipoidentificacionBen,
								    pb.perNumeroIdentificacion as NumeroidentificacionBen,
								    pb.perPrimerNombre as PrimerNombreBen,
								    pb.perSegundoNombre as SegundoNombreBen,
								    pb.perPrimerApellido as PrimerApellidoBen,
								    pb.perSegundoApellido as SegundoApellidoBen

	 
			          FROM SolicitudLiquidacionSubsidio sls WITH(NOLOCK)
						INNER JOIN Solicitud s WITH(NOLOCK)
								ON sls.slsSolicitudGlobal = s.solid
						INNER JOIN DetalleSubsidioAsignado dsa WITH(NOLOCK)
								ON dsa.dsaSolicitudLiquidacionSubsidio = sls.slsid
						INNER JOIN Cuentaadministradorsubsidio cas WITH(NOLOCK)
								ON dsa.dsaCuentaAdministradorSubsidio = cas.casid
						INNER JOIN Empleador em WITH(NOLOCK)
								ON em.empid = dsa.dsaEmpleador 
						INNER JOIN Empresa e WITH(NOLOCK)
								ON em.empempresa  = e.empid
						INNER JOIN persona pe WITH(NOLOCK)
								ON pe.perid = e.empPersona
						 LEFT JOIN Beneficioempleador be WITH(NOLOCK)
								ON be.bemEmpleador = em.empid
						 LEFT JOIN Beneficio bef WITH(NOLOCK)
								ON bef.befId = be.bemBeneficio
						INNER JOIN Afiliado A WITH(NOLOCK)
								ON a.afiid = dsaAfiliadoPrincipal
						INNER JOIN Persona pa WITH(NOLOCK)
								ON pa.perid = a.afipersona 
	 					INNER JOIN GrupoFamiliar g WITH(NOLOCK)
								ON g.grfId = dsa.dsaGrupoFamiliar
						INNER JOIN Beneficiario ben WITH(NOLOCK)
								ON ben.benbeneficiariodetalle = dsa.dsaBeneficiarioDetalle and ben.benAfiliado = dsa.dsaAfiliadoPrincipal
						INNER JOIN persona pb WITH(NOLOCK) 
								ON pb.perid = ben.benpersona 
						 LEFT JOIN condicioninvalidez ci WITH(NOLOCK)
								ON ci.coipersona = ben.benpersona 
						INNER JOIN AdministradorSubsidio AdminSub WITH(NOLOCK) 
								ON dsa.dsaAdministradorSubsidio =  AdminSub.asuId
						INNER JOIN Persona padmin WITH(NOLOCK)
								ON padmin.perid = AdminSub.asupersona 
						INNER JOIN Mediodepago mdp WITH(NOLOCK)
								ON mdp.mdpid = cas.casMedioDePago
						LEFT JOIN (select  case when b.benPersona = max(b.benPersona) over (partition by b.benPersona, b.benAfiliado) then b.benpersona else null end as benpersona
						                  ,case when b.benPersona = max(b.benPersona) over (partition by b.benPersona, b.benAfiliado) then b.benafiliado else null end  as benafiliado
								          ,case when b.benPersona = max(b.benPersona) over (partition by b.benPersona, b.benAfiliado) then b.benBeneficiarioDetalle else null end as benBeneficiarioDetalle
								          ,case when b.benPersona = max(b.benPersona) over (partition by b.benPersona, b.benAfiliado) then b.benTipoBeneficiario else null end as benTipoBeneficiario
								     from Beneficiario as b with(nolock)
							     ) as bentipo ON bentipo.benafiliado = ben.benAfiliado and bentipo.benpersona = ben.benPersona and bentipo.benpersona is not null
						

		WHERE 
			dsaEstado = 'DERECHO_ASIGNADO'
			AND dsaTipoCuotaSubsidio LIKE '%AGRICOLA'
			AND dsaFechaHoraCreacion >= @fechaInicio
			AND dsaFechaHoraCreacion <= @fechafin
		 
 END