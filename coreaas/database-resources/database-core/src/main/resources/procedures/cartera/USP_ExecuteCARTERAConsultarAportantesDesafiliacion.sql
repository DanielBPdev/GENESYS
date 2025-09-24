-- =============================================
-- Author:		Claudia Milena Marín Hincapié
-- Create date: 2019/06/13
-- Description:	Procedimiento almacenado encargado de consultar los aportantes que aplican para desafiliar  
-- HU - 225 - 197 Ejecutar gestión de cobro de aportes Expulsión
-- =============================================
CREATE PROCEDURE [dbo].[USP_ExecuteCARTERAConsultarAportantesDesafiliacion]
	@tipoSolicitante VARCHAR(13),	--Tipo de solicitante (EMPLEADOR, INDEPENDIENTE, PENSIONADO)
	@lineaCobro VARCHAR(3),			--Linea de cobro para el aportante
	@estadoOperacion VARCHAR(10),	--Estado de operación de la cartera 'VIGENTE'
	@estadoAfiliado VARCHAR(41) 	--Estado afiliación de la entidad 'ACTIVO'
AS
SET NOCOUNT ON

DECLARE @Resultado TABLE(
	tipoIdentificacion VARCHAR(20),
	numeroIdentificacion VARCHAR(16),
	razonSocial VARCHAR(250),
	tipoSolicitante VARCHAR(13),
	estadoCartera VARCHAR(6),
	deuda VARCHAR(20),
	monto NUMERIC(19,5),
	tipoDeuda VARCHAR(14),
	lineaCobro VARCHAR(3),
	idPersona BIGINT,
	numeroOperacion BIGINT 
)

DECLARE @AportantesConvenioExclusion TABLE(
	perId BIGINT
)
	
INSERT INTO @AportantesConvenioExclusion (perId)
SELECT DISTINCT exc.excPersona FROM ExclusionCartera exc WHERE exc.excTipoSolicitante = @tipoSolicitante
	AND exc.excTipoExclusionCartera IN ('ACLARACION_MORA')
	AND exc.excEstadoExclusionCartera IN ('ACTIVA')

INSERT INTO @AportantesConvenioExclusion (perId)
SELECT DISTINCT cop.copPersona FROM  ConvenioPago cop WHERE cop.copTipoSolicitante = @tipoSolicitante
	AND cop.copEstadoConvenioPago in ('ACTIVO')

INSERT INTO @AportantesConvenioExclusion (perId)
SELECT DISTINCT dap.deaPersona FROM DesafiliacionAportante dap INNER JOIN SolicitudDesafiliacion sde ON dap.deaSolicitudDesafiliacion = sde.sodId
AND dap.deaTipoSolicitante = @tipoSolicitante
AND sde.sodEstadoSolicitud NOT IN ('APROBADA','RADICADO','CERRADA')	

IF @tipoSolicitante = 'EMPLEADOR' 
	BEGIN
		INSERT INTO @Resultado (
			tipoIdentificacion, numeroIdentificacion, razonSocial, tipoSolicitante, estadoCartera, deuda, monto, tipoDeuda, lineaCobro, idPersona, numeroOperacion
		)
		SELECT DISTINCT per.perTipoIdentificacion, per.perNumeroIdentificacion, 
		CASE WHEN per.perRazonSocial IS NOT NULL THEN per.perRazonSocial ELSE '' END AS razonSocial, 'EMPLEADOR',
			'MOROSO', 'Deuda presunta', 
			(SELECT SUM(cart.carDeudaPresunta) FROM Cartera cart WHERE cart.carPersona=per.perId AND car.carEstadoOperacion=@estadoOperacion 
			AND car.carTipoLineaCobro =@lineaCobro AND car.carEstadoCartera='MOROSO') AS monto, 
			'POR_MORA', 'LC1', per.perId, cag.cagNumeroOperacion
		FROM Persona per, Cartera car, Empleador empl, Empresa empr, ExclusionCartera exc, CarteraAgrupadora cag
		WHERE empl.empEmpresa=empr.empId AND empr.empPersona=per.perId AND empl.empEstadoEmpleador=@estadoAfiliado AND empl.empMarcaExpulsion ='CANDIDATA_A_EXPULSAR'
		AND car.carEstadoOperacion=@estadoOperacion AND car.carTipoLineaCobro =@lineaCobro
		AND car.carEstadoCartera='MOROSO' AND car.carPersona=per.perId AND per.perId NOT IN (SELECT perId FROM @AportantesConvenioExclusion)
		AND exc.excPersona = per.perId
		AND exc.excTipoSolicitante = @tipoSolicitante
		AND exc.excTipoExclusionCartera IN ('RIESGO_INCOBRABILIDAD')
		AND exc.excEstadoExclusionCartera IN ('ACTIVA')
		AND cag.cagCartera = car.carId
		AND car.carDeudaPresunta >0
		
		INSERT INTO @Resultado (
			tipoIdentificacion, numeroIdentificacion, razonSocial, tipoSolicitante, estadoCartera, deuda, monto, tipoDeuda, lineaCobro, idPersona, numeroOperacion
		)
		SELECT DISTINCT per.perTipoIdentificacion, per.perNumeroIdentificacion,
			CASE WHEN per.perRazonSocial IS NOT NULL THEN per.perRazonSocial ELSE '' END AS razonSocial, 'EMPLEADOR',
			'MOROSO', 'Deuda presunta', 
			(SELECT SUM(cart.carDeudaPresunta) FROM Cartera cart WHERE cart.carPersona=per.perId AND car.carEstadoOperacion=@estadoOperacion 
			AND car.carTipoLineaCobro =@lineaCobro AND car.carEstadoCartera='MOROSO') AS monto, 
			'POR_MORA', 'LC1', per.perId, cag.cagNumeroOperacion
		FROM Persona per, Cartera car, Empleador empl, Empresa empr, CarteraAgrupadora cag
		WHERE empl.empEmpresa=empr.empId AND empr.empPersona=per.perId AND empl.empEstadoEmpleador=@estadoAfiliado AND empl.empMarcaExpulsion ='CANDIDATA_A_EXPULSAR'
		AND car.carEstadoOperacion=@estadoOperacion AND car.carTipoLineaCobro =@lineaCobro
		AND car.carEstadoCartera='MOROSO' AND car.carPersona=per.perId AND per.perId NOT IN (SELECT perId FROM @AportantesConvenioExclusion)
		AND car.carTipoAccionCobro IN ('F1','H2') 
		AND cag.cagCartera = car.carId
		AND car.carDeudaPresunta >0
	END
ELSE IF @tipoSolicitante = 'INDEPENDIENTE' 
	BEGIN
		INSERT INTO @Resultado (
			tipoIdentificacion, numeroIdentificacion, razonSocial, tipoSolicitante, estadoCartera, deuda, monto, tipoDeuda, lineaCobro, idPersona, numeroOperacion
		)
		SELECT DISTINCT per.perTipoIdentificacion, per.perNumeroIdentificacion,
			CASE WHEN per.perRazonSocial IS NOT NULL THEN per.perRazonSocial ELSE '' END AS razonSocial, 'INDEPENDIENTE',
			'MOROSO', 'Deuda presunta', 
			(SELECT SUM(cart.carDeudaPresunta) FROM Cartera cart WHERE cart.carPersona=per.perId AND car.carEstadoOperacion=@estadoOperacion 
			AND car.carTipoLineaCobro =@lineaCobro AND car.carEstadoCartera='MOROSO') AS monto, 
			'INEXACTITUD', 'LC4', per.perId, cag.cagNumeroOperacion 
		FROM Persona per, Cartera car, RolAfiliado roa, Afiliado afi, ExclusionCartera exc, CarteraAgrupadora cag
		WHERE roa.roaAfiliado=afi.afiId AND afi.afiPersona=per.perId AND roa.roaEstadoAfiliado=@estadoAfiliado AND roa.roaMarcaExpulsion ='CANDIDATA_A_EXPULSAR'
		AND car.carEstadoOperacion=@estadoOperacion AND car.carTipoLineaCobro =@lineaCobro
		AND car.carEstadoCartera='MOROSO' AND car.carPersona=per.perId AND per.perId NOT IN (SELECT perId FROM @AportantesConvenioExclusion)
		AND exc.excPersona = per.perId
		AND exc.excTipoSolicitante = @tipoSolicitante
		AND exc.excTipoExclusionCartera IN ('RIESGO_INCOBRABILIDAD')
		AND exc.excEstadoExclusionCartera IN ('ACTIVA')
		AND cag.cagCartera = car.carId
		AND car.carDeudaPresunta >0
		
		INSERT INTO @Resultado (
			tipoIdentificacion, numeroIdentificacion, razonSocial, tipoSolicitante, estadoCartera, deuda, monto, tipoDeuda, lineaCobro, idPersona, numeroOperacion
		)
		SELECT DISTINCT per.perTipoIdentificacion, per.perNumeroIdentificacion,
			CASE WHEN per.perRazonSocial IS NOT NULL THEN per.perRazonSocial ELSE '' END AS razonSocial, 'INDEPENDIENTE',
			'MOROSO', 'Deuda presunta', 
			(SELECT SUM(cart.carDeudaPresunta) FROM Cartera cart WHERE cart.carPersona=per.perId AND car.carEstadoOperacion=@estadoOperacion 
			AND car.carTipoLineaCobro =@lineaCobro AND car.carEstadoCartera='MOROSO') AS monto, 
			'INEXACTITUD', 'LC4', per.perId, cag.cagNumeroOperacion 
		FROM Persona per, Cartera car, RolAfiliado roa, Afiliado afi, CarteraAgrupadora cag
		WHERE roa.roaAfiliado=afi.afiId AND afi.afiPersona=per.perId AND roa.roaEstadoAfiliado=@estadoAfiliado AND roa.roaMarcaExpulsion ='CANDIDATA_A_EXPULSAR'
		AND car.carEstadoOperacion=@estadoOperacion AND car.carTipoLineaCobro =@lineaCobro
		AND car.carEstadoCartera='MOROSO' AND car.carPersona=per.perId AND per.perId NOT IN (SELECT perId FROM @AportantesConvenioExclusion)
		AND car.carTipoAccionCobro IN ('LC4C')
		AND cag.cagCartera = car.carId
		AND car.carDeudaPresunta >0
	END 
ELSE IF @tipoSolicitante = 'PENSIONADO' 
	BEGIN
		INSERT INTO @Resultado (
			tipoIdentificacion, numeroIdentificacion, razonSocial, tipoSolicitante, estadoCartera, deuda, monto, tipoDeuda, lineaCobro, idPersona, numeroOperacion
		)
		SELECT DISTINCT per.perTipoIdentificacion, per.perNumeroIdentificacion,
			CASE WHEN per.perRazonSocial IS NOT NULL THEN per.perRazonSocial ELSE '' END AS razonSocial, 'PENSIONADO',
			'MOROSO', 'Deuda presunta', 
			(SELECT SUM(cart.carDeudaPresunta) FROM Cartera cart WHERE cart.carPersona=per.perId AND car.carEstadoOperacion=@estadoOperacion 
			AND car.carTipoLineaCobro =@lineaCobro AND car.carEstadoCartera='MOROSO') AS monto, 
			'INEXACTITUD', 'LC5', per.perId, cag.cagNumeroOperacion 
		FROM Persona per, Cartera car, RolAfiliado roa, Afiliado afi, ExclusionCartera exc, CarteraAgrupadora cag
		WHERE roa.roaAfiliado=afi.afiId AND afi.afiPersona=per.perId AND roa.roaEstadoAfiliado=@estadoAfiliado AND roa.roaMarcaExpulsion ='CANDIDATA_A_EXPULSAR'
		AND car.carEstadoOperacion=@estadoOperacion AND car.carTipoLineaCobro =@lineaCobro
		AND car.carEstadoCartera='MOROSO' AND car.carPersona=per.perId AND per.perId NOT IN (SELECT perId FROM @AportantesConvenioExclusion)
		AND exc.excPersona = per.perId
		AND exc.excTipoSolicitante = @tipoSolicitante
		AND exc.excTipoExclusionCartera IN ('RIESGO_INCOBRABILIDAD')
		AND exc.excEstadoExclusionCartera IN ('ACTIVA')
		AND cag.cagCartera = car.carId
		AND car.carDeudaPresunta >0
		
		INSERT INTO @Resultado (
			tipoIdentificacion, numeroIdentificacion, razonSocial, tipoSolicitante, estadoCartera, deuda, monto, tipoDeuda, lineaCobro, idPersona, numeroOperacion
		)
		SELECT DISTINCT per.perTipoIdentificacion, per.perNumeroIdentificacion,
			CASE WHEN per.perRazonSocial IS NOT NULL THEN per.perRazonSocial ELSE '' END AS razonSocial, 'PENSIONADO',
			'MOROSO', 'Deuda presunta', 
			(SELECT SUM(cart.carDeudaPresunta) FROM Cartera cart WHERE cart.carPersona=per.perId AND car.carEstadoOperacion=@estadoOperacion 
			AND car.carTipoLineaCobro =@lineaCobro AND car.carEstadoCartera='MOROSO') AS monto, 
			'INEXACTITUD', 'LC5', per.perId, cag.cagNumeroOperacion 
		FROM Persona per, Cartera car, RolAfiliado roa, Afiliado afi, CarteraAgrupadora cag
		WHERE roa.roaAfiliado=afi.afiId AND afi.afiPersona=per.perId AND roa.roaEstadoAfiliado=@estadoAfiliado AND roa.roaMarcaExpulsion ='CANDIDATA_A_EXPULSAR'
		AND car.carEstadoOperacion=@estadoOperacion AND car.carTipoLineaCobro =@lineaCobro
		AND car.carEstadoCartera='MOROSO' AND car.carPersona=per.perId AND per.perId NOT IN (SELECT perId FROM @AportantesConvenioExclusion)
		AND car.carTipoAccionCobro IN ('LC5C')
		AND cag.cagCartera = car.carId
		AND car.carDeudaPresunta >0
	END
	
SELECT
	tipoIdentificacion, numeroIdentificacion, razonSocial, tipoSolicitante, estadoCartera, deuda, monto, tipoDeuda, lineaCobro, idPersona, numeroOperacion
FROM @Resultado