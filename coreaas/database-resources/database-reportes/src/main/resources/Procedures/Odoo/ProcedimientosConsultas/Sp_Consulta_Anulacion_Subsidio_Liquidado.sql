-- =======================================================
-- Create Stored Procedure Template for Azure SQL Database
-- =======================================================

-- =============================================
-- Author:      Gustavo Giraldo
-- Create Date: 2021-07-29
-- Description: Procedimiento almacenado que se encarga de consultar los registros de liquidación originados o
-- actualizados por la ejecución del proceso de anulación / bloqueo de Subsidio Familiar liquidado
-- =============================================

CREATE PROCEDURE [odoo].[ConsultaAnulacionSubsidioLiquidado] (
	@fecha_procesamiento DATE = NULL
)

AS
BEGIN
	IF @fecha_procesamiento IS NULL
	BEGIN
		SET @fecha_procesamiento = CONVERT(DATE, DATEADD(DAY, -1, DATEADD(HH, -5, GETDATE())))
	END

	INSERT INTO [odoo].[anulacion_subsidio_liquidado] (fecha_procesamiento_anulacion, periodo_regular_retroactivo_liquidacion, modalidad_pago_subsidio_anulado, tipo_identificacion_administrador_subsidio,
		numero_identificacion_administrador_subsidio, tipo_beneficiario, tipo_identificacion_beneficiario, numero_identificacion_beneficiario, valor_anulado,
		estado_aporte, fecha_contable, campo_tipo_plantilla)
	SELECT * FROM ( 
		SELECT 
			casFechaHoraultimamodificacion [Fecha de procesamiento de anulación.],
			--case when dsa.dsaperiodoliquidado=cast(casFechaHoraTransaccion as date) then 'Regular' else 'Retroactivo' end 
			dsatipocuotasubsidio [Periodo regular / retroactivo de liquidación],
			casmediodepagotransaccion [Modalidad de Pago de Subsidio anulado.],
			replace(padm.pertipoidentificacion,'_',' ') [Tipo de Identificación del Administrador de Subsidio],
			padm.pernumeroidentificacion [No. de Identificación del Administrador de Subsidio],
			isnull(replace(benTipoBeneficiario ,'_',' '),'')as [Tipo de Identificación del Beneficiario],
			isnull(replace(pb.pertipoIdentificacion,'_',' '),'') [Tipo identificación beneficiario],
			isnull(pb.pernumeroIdentificacion,'') [Número de Identificación del Beneficiario],
			casvalorrealtransaccion[Valor anulado.],
			casestadotransaccionsubsidio [Estado del aporte.],
			casFechaHoraultimamodificacion [Fecha contable],
			'ACT_LS_ANU_BLO'[Campo tipo plantilla]

			FROM CuentaAdministradorSubsidio casx
			left join detallesubsidioasignado dsa on casx.casId = dsa.dsaCuentaAdministradorSubsidio
			left join SolicitudLiquidacionSubsidio sls on dsa.dsasolicitudliquidacionsubsidio=sls.slsId
			left JOIN Solicitud s WITH(NOLOCK) ON sls.slsSolicitudGlobal = s.solid
			left join grupofamiliar g on g.grfid=casx.casGrupoFamiliar
			left join afiliado ax on ax.afiId=casx.casAfiliadoPrincipal
			LEFT join beneficiarioDetalle as bd with(nolock) on bd.bedId = dsa.dsaBeneficiarioDetalle
			LEFT join Beneficiario as b with(nolock) on b.benafiliado = aX.afiid and b.benBeneficiarioDetalle = bd.bedId
			LEFT join Persona as pb with(nolock) on pb.perid = b.benPersona  
			left join administradorsubsidio adm on casx.casadministradorsubsidio= adm.asuId
			left join persona padm on padm.perid=adm.asuPersona
			where (casx.casestadotransaccionsubsidio='ANULADO' or casx.castipotransaccionsubsidio='ANULACION')
				and cast(casFechaHoraultimamodificacion as DATE) =@fecha_procesamiento	
	) as x
		where cast(x.[Fecha Contable] as DATE) =@fecha_procesamiento
END
