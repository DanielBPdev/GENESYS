--GLPI 69422
IF NOT EXISTS(SELECT 1 FROM sys.columns
              WHERE Name = 'excPeriodoDeuda'
                AND Object_ID = Object_ID('ExclusionCartera_aud'))
BEGIN
alter table dbo.ExclusionCartera_aud ADD  excPeriodoDeuda  Date
END

IF not exists
(
SELECT *
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'ParametrizacionExclusiones_aud' AND COLUMN_NAME = 'pexEclusionMora'  and TABLE_SCHEMA = 'dbo'
)
BEGIN
ALTER TABLE dbo.ParametrizacionExclusiones_aud ADD pexEclusionMora bit
END

-- ajuste para core_aud
if not exists
(
select *
from information_schema.columns
where table_name = 'BitacoraCartera_aud' and column_name = 'bcaNumeroOperacion'  and table_schema = 'dbo'
)
begin
alter table dbo.bitacoracartera_aud add bcanumerooperacion varchar(20)
end