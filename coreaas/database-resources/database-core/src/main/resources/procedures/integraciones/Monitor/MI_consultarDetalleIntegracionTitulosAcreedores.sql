-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: <Description, , >
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_consultarDetalleIntegracionTitulosAcreedores]
@fechaInicial varchar(200), 
@fechaFinal varchar(200), 
@identificacion varchar(200), 
@estado varchar(200), 
@codigoSap varchar(200), 
@operacion varchar(200), 
@codigoGenesys varchar(200), 
@procesoOrigen varchar(200), 
@observacionesContiene varchar(200)

AS
BEGIN

	select 'consecutivo'
	union all
	select 'estadoReg'
	union all
	select case COLUMN_NAME when 'fecIng' then 'fechaIng' else COLUMN_NAME end as COLUMN_NAME
	from INFORMATION_SCHEMA.COLUMNS
	where 
	TABLE_NAME = 'Acreedores'

END