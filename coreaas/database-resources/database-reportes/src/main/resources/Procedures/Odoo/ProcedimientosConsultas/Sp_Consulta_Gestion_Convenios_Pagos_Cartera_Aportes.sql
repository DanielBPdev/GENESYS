-- =======================================================
-- Create Stored Procedure Template for Azure SQL Database
-- =======================================================

-- =============================================
-- Author:      Gustavo Giraldo
-- Create Date: 2021-06-26
-- Description: Procedimiento almacenado que se encarga 
-- de consultar la gestión de convenios de pagos de cartera de aportes
-- =============================================

CREATE PROCEDURE [odoo].[ConsultaGestionConveniosPagosCarteraAportes](
	@fecha_procesamiento DATE = NULL
)

AS
BEGIN
	IF @fecha_procesamiento IS NULL
	BEGIN
		SET @fecha_procesamiento = CONVERT(DATE, DATEADD(DAY, -1, DATEADD(HH, -5, GETDATE())))
	END

	INSERT INTO [odoo].[gestion_convenios_pagos_cartera_aportes] (numero_convenio, fecha_convenio, periodos_mora, tipo_identificacion_aportante_en_mora,
        identificacion_aportante_en_mora, numero_cuotas_convenio, valor_cada_cuota, valor_total_convenio, fecha_contable, tipo_plantilla)
    --10.Consulta gestión de convenios de pagos de carteradeaportes
	SELECT 
		copId as [Número del Convenio],
		copFechaRegistro as [Fecha del convenio],
		ISNULL((select count(distinct substring(cast(carperiododeuda as varchar),0,8)) from cartera c1 
			where c1.carpersona =c.carpersona),'') as [Periodos en Mora],
		replace(Pdep.pertipoidentificacion,'_',' ') as [Tipo de identificación del Aportante en Mora],
		Pdep.PERNUMEROIDENTIFICACION as [Número de identificación del Aportante en Mora.],
		copCuotasPorPagar as [Número de cuotas del convenio],
		ppcvalorcuota as [Valor de cada cuota],----Pendiente No se encontró
		copDeudaRealRegistrada as [Valor total del convenio],
		CONVERT(DATE, copFechaRegistro) as [Fecha contable],
		'CRE_CO' as [Campo tipo plantilla]

		FROM Cartera  c 
		INNER JOIN Persona P WITH(NOLOCK) ON P.PERID=C.CARPERSONA
		INNER JOIN conveniopago con WITH(NOLOCK) ON con.coppersona=p.perid
		LEFT JOIN conveniopagodependiente d on d.cpdPagoPeriodoConvenio=con.copId
		INNER JOIN persona pdep on pdep.perid=d.cpdPersona
		LEFT JOIN Exclusioncartera ex  WITH(NOLOCK) ON excpersona =c.carpersona 
		INNER JOIN pagoperiodoconvenio ppc on  ppcConvenioPago=con.copId
		where cast(copFechaRegistro as DATE) =@fecha_procesamiento	
END
