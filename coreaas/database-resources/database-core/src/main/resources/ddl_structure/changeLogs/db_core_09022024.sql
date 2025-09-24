
IF not exists
(
    SELECT *
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE COLUMN_NAME = 'plsFechaInicioConyugeCuidador' AND TABLE_NAME = 'ParametrizacionLiquidacionSubsidio' and TABLE_SCHEMA = 'dbo'
)begin
ALTER TABLE ParametrizacionLiquidacionSubsidio
ADD plsFechaInicioConyugeCuidador DATE

end


