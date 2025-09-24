--- GAP 53849 - Adición Nivel Bloqueo y idBen
-- Modelo Core_AUD
if not exists (select 1 from  information_schema.columns  where table_schema = 'dbo'and table_name = 'BloqueoBeneficiarioCuotaMonetaria_aud' and column_name = 'bbcBeneficiario')
begin
alter table BloqueoBeneficiarioCuotaMonetaria_aud add bbcBeneficiario bigint;
end

if not exists (select 1 from  information_schema.columns  where table_schema = 'dbo'and table_name = 'BloqueoBeneficiarioCuotaMonetaria_aud' and column_name = 'bbcNivelBloqueo')
begin
alter table BloqueoBeneficiarioCuotaMonetaria_aud add bbcNivelBloqueo varchar(50);
end

