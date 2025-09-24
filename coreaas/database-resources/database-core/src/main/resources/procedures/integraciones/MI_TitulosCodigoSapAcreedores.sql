-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: <Description, , >
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_TitulosCodigoSapAcreedores]

AS
BEGIN

	SELECT Titulo
from
(select 'Codigo Genesys' AS Titulo,1 as orden
union 
select 'Tipo Identificación',2
union 
select 'Numero Identificación',3
union
select 'Nombre',4
union
select 'Codigo Sap',5
union 
select 'ACCION',6) as temp
ORDER BY orden ASC

END