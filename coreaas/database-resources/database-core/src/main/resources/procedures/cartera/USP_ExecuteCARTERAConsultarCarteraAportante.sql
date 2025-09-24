-- =============================================
-- Author:		Francisco Alejandro Hoyos Rojas
-- Create date: 2020/12/30
-- Description:	Procedimiento almacenado encargado de 
-- obtener el total de deuda de un aportante registrado en cartera, agrupada por línea de cobro
-- =============================================
CREATE PROCEDURE [dbo].[USP_ExecuteCARTERAConsultarCarteraAportante]
	@tipoIdentificacion VARCHAR(20),	--Tipo de identificación del aportante
	@numeroIdentificacion VARCHAR(16),	--Número de identificación del aportante
	@tipoAportante	VARCHAR(13)			--Tipo de aportante(EMPLEADOR, INDEPENDIENTE, PENSIONADO)
AS
	SET NOCOUNT ON;
BEGIN
	-- Si el aportante es un empleador
	IF @tipoAportante = 'EMPLEADOR'
	BEGIN
		SELECT 
			MIN(car.carFechaCreacion) fechaCreacion, 
	 		MAX(cag.cagNumeroOperacion) numeroOperacion, 
	 		CASE MAX(car.carTipoAccionCobro)
				WHEN 'AB1' THEN 'A1'
				WHEN 'AB2' THEN 'A2'
				WHEN 'BC1' THEN 'B1'
				WHEN 'BC2' THEN 'B2'
				WHEN 'CD1' THEN 'C1'
				WHEN 'CD2' THEN 'C2'
				WHEN 'DE1' THEN 'D1'
				WHEN 'DE2' THEN 'D2'
				WHEN 'EF1' THEN 'E1'
				WHEN 'EF2' THEN 'E2'
				WHEN 'FG2' THEN 'F2'
				WHEN 'GH2' THEN 'G2'
				WHEN 'L4AC' THEN 'LC4'
				WHEN 'L5AC' THEN 'LC5'
				ELSE car.carTipoAccionCobro
			END  tipoAccionCobro, 
	 		car.carTipoLineaCobro tipoLineaCobro, 
	 		SUM(ISNULL(cad.cadDeudaPresunta, 0)) deudaPresunta, 
	 		SUM(ISNULL(cad.cadDeudaReal, 0)) deudaReal,
	 		SUM(CASE WHEN ISNULL(cad.cadDeudaReal, 0)=0 THEN ISNULL(cad.cadDeudaPresunta, 0) ELSE cad.cadDeudaReal  END) deudaTotal
		FROM Cartera car
		JOIN Persona per ON per.perId = car.carPersona
		INNER JOIN CarteraDependiente cad ON cad.cadCartera = car.carId
		INNER JOIN CarteraAgrupadora cag ON cag.cagCartera = car.carId
		WHERE per.perNumeroIdentificacion = @numeroIdentificacion
			AND per.perTipoIdentificacion = @tipoIdentificacion
			AND car.carTipoSolicitante = @tipoAportante
			AND car.carEstadoOperacion = 'VIGENTE'
			AND cad.cadEstadoOperacion = 'VIGENTE'
		GROUP BY car.carTipoLineaCobro, car.carTipoAccionCobro;
	END 
	-- Si el aportante es un Independiente o Pensionado
	ELSE IF @tipoAportante = 'INDEPENDIENTE' OR @tipoAportante = 'PENSIONADO'
	BEGIN
		SELECT 
			MIN(car.carFechaCreacion) fechaCreacion, 
	 		MAX(cag.cagNumeroOperacion) numeroOperacion, 
	 		CASE MAX(car.carTipoAccionCobro)
				WHEN 'AB1' THEN 'A1'
				WHEN 'AB2' THEN 'A2'
				WHEN 'BC1' THEN 'B1'
				WHEN 'BC2' THEN 'B2'
				WHEN 'CD1' THEN 'C1'
				WHEN 'CD2' THEN 'C2'
				WHEN 'DE1' THEN 'D1'
				WHEN 'DE2' THEN 'D2'
				WHEN 'EF1' THEN 'E1'
				WHEN 'EF2' THEN 'E2'
				WHEN 'FG2' THEN 'F2'
				WHEN 'GH2' THEN 'G2'
				WHEN 'L4AC' THEN 'LC4'
				WHEN 'L5AC' THEN 'LC5'
				ELSE car.carTipoAccionCobro
			END  tipoAccionCobro, 
	 		car.carTipoLineaCobro tipoLineaCobro, 
	 		SUM(ISNULL(car.carDeudaPresunta, 0)) deudaPresunta, 
	 		0 deudaReal,
	 		SUM(ISNULL(car.carDeudaPresunta, 0)) deudaTotal
		FROM Cartera car
		JOIN Persona per ON per.perId = car.carPersona
		INNER JOIN CarteraAgrupadora cag ON cag.cagCartera = car.carId
		WHERE per.perNumeroIdentificacion = @numeroIdentificacion
			AND per.perTipoIdentificacion = @tipoIdentificacion
			AND car.carTipoSolicitante = @tipoAportante
			AND car.carEstadoOperacion = 'VIGENTE'
		GROUP BY car.carTipoLineaCobro, car.carTipoAccionCobro;
	END
END;