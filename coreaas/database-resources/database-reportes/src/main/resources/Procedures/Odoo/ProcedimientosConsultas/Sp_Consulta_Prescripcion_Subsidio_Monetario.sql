-- =======================================================
-- Create Stored Procedure Template for Azure SQL Database
-- =======================================================

-- =============================================
-- Author:      Gustavo Giraldo
-- Create Date: 2021-07-30
-- Description: Procedimiento almacenado que se encarga de consultar los registros de liquidación originados por la
-- ejecución del proceso 3.1.8 Prescripción de Subsidio Familiar
-- =============================================

CREATE PROCEDURE [odoo].[ConsultaPrescripcionSubsidioMonetario](
	@fecha_procesamiento DATE = NULL
)

AS
BEGIN
	IF @fecha_procesamiento IS NULL
	BEGIN
		SET @fecha_procesamiento = CONVERT(DATE, DATEADD(DAY, -1, DATEADD(HH, -5, GETDATE())))
	END

	INSERT INTO [odoo].[prescripcion_subsidio_monetario] (fecha_procesamiento_prescripcion, periodo_asociado_fecha_corte, id_registro, codigo_identificacion_tercero_pagador,
		modalidad_pago_subsidio_prescrito, tipo_identificacion_administrador_subsidio, numero_identificacion_administrador_subsidio, valor_prescrito, estado_aporte, fecha_contable, campo_tipo_plantilla)
	SELECT * FROM ( 
		SELECT
			--dateadd(day,1095,slsFechaInicio) [Fecha de procesamiento de prescripción],--casFechaHoraTransaccion, casfechahoracreacionregistro, dsafechahoraultimamodificacion
			casFechaHoraultimamodificacion [Fecha de procesamiento de prescripción],
			--cast(cast(dateadd(day,1095,slsFechaInicio) as date)as varchar(7)) [Periodo asociado a Fecha de corte de prescripción],---Cuales fechas se evalúan?
			cast(cast(casFechaHoraultimamodificacion as date)as varchar(7)) [Periodo asociado a Fecha de corte de prescripción],
			casid as [id del registro],
			null [Código o identificación del tercero pagador],---En la reunión del reporte 11 se aclara que este campo debe identificar si el subsidio viene por Acertemos, o Jueguemos, sin embargo en GENESYS aún hay un control de cambio en curso para diferenciar estos dos medios de pago, Deisy llevara la duda a ASOPAGOS
			casmediodepagotransaccion [Modalidad de Pago de Subsidio Prescrito],
			replace(padm.pertipoidentificacion,'_',' ') [Tipo de Identificación del Administrador de Subsidio],
			padm.pernumeroidentificacion [No. de Identificación del Administrador de Subsidio],
			casvalorrealtransaccion[Valor prescrito],
			casestadotransaccionsubsidio [Estado del aporte.],
			cast(casFechaHoraultimamodificacion as date) [Fecha contable],
			'CRE_LS_PRE_SUB'[Campo tipo plantilla]

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
			
			where cast(casFechaHoraultimamodificacion as date) =@fecha_procesamiento	
			----Faltan las condiciones 
	) as x
		where cast(x.[Fecha Contable] as DATE) =@fecha_procesamiento
END
