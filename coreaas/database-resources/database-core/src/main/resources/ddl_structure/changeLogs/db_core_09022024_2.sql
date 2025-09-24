
IF  exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'plsFechaInicioConyugeCuidador' AND TABLE_NAME = 'ParametrizacionLiquidacionSubsidio' and TABLE_SCHEMA = 'dbo'
)begin


update ParametrizacionLiquidacionSubsidio
set plsFechaInicioConyugeCuidador = '2022-06-30'
end
