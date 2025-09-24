--- GAP 53849 - Adición Causal Bloqueo
-- Modelo Core
if not exists (select 1 from  information_schema.columns  where table_schema = 'dbo'and table_name = 'BloqueoBeneficiarioCuotaMonetaria' and column_name = 'bbcCausalBloqueo')
begin
alter table BloqueoBeneficiarioCuotaMonetaria add bbcCausalBloqueo varchar(200);
alter table aud.BloqueoBeneficiarioCuotaMonetaria_aud add bbcCausalBloqueo varchar(200);
end

