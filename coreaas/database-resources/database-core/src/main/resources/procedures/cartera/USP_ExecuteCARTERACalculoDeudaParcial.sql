-- =============================================
-- Author:      <Juan Carlos Avila Meza>
-- Create Date: <02-05-2025>
-- Description: <Procedimiento Almacenado que realiza el calculo para el estado de la columna carDeudaParcial en la Tabla Cartera>
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ExecuteCARTERACalculoDeudaParcial]
(
    -- Add the parameters for the stored procedure here
	@Carid bigInt
)
AS
BEGIN


IF (select distinct c.carTipoSolicitante from Cartera c where c.carId = @carId) IN ('PENSIONADO','INDEPENDIENTE')
BEGIN --Realiza el calculo de la columna DeudaParcial para INDEPENDIENTES y PENSIONADOS
	
	UPDATE c set c.carDeudaParcial =  CASE WHEN c.carTipoDeuda = 'INEXACTITUD' THEN 'MORA_PARCIAL' ELSE 'MORA_TOTAL' END
	from cartera c WHERE c.carId = @carId

END
	ELSE --##  CASO CONTRARIO  ##
BEGIN  -- Realiza el calculo de la columna DeudaParcial para empleadores
	-- ## TODO: Proceso para actualizar la columna DeudaParcial para empleadores
	update c set c.carDeudaParcial = CASE WHEN 
			 (select count(cadDeudaPresunta) from CarteraDependiente cd where cd.cadCartera = carId and cadDeudaPresunta = 0) > 0 AND -- pagados
			 ((select count(ra.roaId) from RolAfiliado ra where ra.roaEmpleador = epl.empId and ((cast(ra.roaFechaAfiliacion as varchar(7)) ) <= cast(c.carPeriodoDeuda as varchar(7)) or (cast(cast(ra.roaFechaRetiro as date) as varchar(7))) <= cast(c.carPeriodoDeuda as varchar(7)))) 
			  > (select count(cadDeudaPresunta) from CarteraDependiente cd where cd.cadCartera = carId and cadDeudaPresunta > 0) )  THEN 'MORA_PARCIAL'
		  WHEN 
			 (select count(cadDeudaPresunta) from CarteraDependiente cd where cd.cadCartera = carId and cadDeudaPresunta = 0) = 0 AND -- pagados
			 ((select count(ra.roaId) from RolAfiliado ra where ra.roaEmpleador = epl.empId and ((cast(ra.roaFechaAfiliacion as varchar(7)) ) <= cast(c.carPeriodoDeuda as varchar(7)) or (cast(cast(ra.roaFechaRetiro as date) as varchar(7))) <= cast(c.carPeriodoDeuda as varchar(7)))) 
			  > (select count(cadDeudaPresunta) from CarteraDependiente cd where cd.cadCartera = carId and cadDeudaPresunta > 0) )  THEN 'MORA_PARCIAL'	
		  WHEN 
			 (select count(cadDeudaPresunta) from CarteraDependiente cd where cd.cadCartera = carId and cadDeudaPresunta = 0) > 0 AND -- pagados
			 ((select count(ra.roaId) from RolAfiliado ra where ra.roaEmpleador = epl.empId and ((cast(ra.roaFechaAfiliacion as varchar(7)) ) <= cast(c.carPeriodoDeuda as varchar(7)) or (cast(cast(ra.roaFechaRetiro as date) as varchar(7))) <= cast(c.carPeriodoDeuda as varchar(7)))) 
			  <= (select count(cadDeudaPresunta) from CarteraDependiente cd where cd.cadCartera = carId and cadDeudaPresunta > 0) )  THEN 'MORA_TOTAL'		  
		  WHEN 
			 (select count(cadDeudaPresunta) from CarteraDependiente cd where cd.cadCartera = carId and cadDeudaPresunta = 0) = 0 AND -- pagados
			 ((select count(ra.roaId) from RolAfiliado ra where ra.roaEmpleador = epl.empId and ((cast(ra.roaFechaAfiliacion as varchar(7)) ) <= cast(c.carPeriodoDeuda as varchar(7)) or (cast(cast(ra.roaFechaRetiro as date) as varchar(7))) <= cast(c.carPeriodoDeuda as varchar(7)))) 
			  <= (select count(cadDeudaPresunta) from CarteraDependiente cd where cd.cadCartera = carId and cadDeudaPresunta > 0) )  THEN 'MORA_TOTAL'	 END
	from Persona p
		inner join Empresa epr WITH(NOLOCK)  on p.perId = epr.empPersona
		inner join Empleador epl WITH(NOLOCK)  on epl.empEmpresa = epr.empId
		inner join Cartera c WITH(NOLOCK)  on c.carPersona = p.perId
	where c.carId in (@Carid)
END

END
