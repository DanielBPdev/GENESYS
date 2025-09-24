--- GAP 53849 - Adición Nivel Bloqueo e idBen
-- Modelo Core
if not exists (select 1 from  information_schema.columns  where table_schema = 'dbo' and table_name = 'BloqueoBeneficiarioCuotaMonetaria' and column_name = 'bbcBeneficiario')
begin
alter table BloqueoBeneficiarioCuotaMonetaria add bbcBeneficiario bigint;
alter table aud.BloqueoBeneficiarioCuotaMonetaria_aud add bbcBeneficiario bigint;
end


if not exists (select 1 from  information_schema.columns  where table_schema = 'dbo' and table_name = 'BloqueoBeneficiarioCuotaMonetaria' and column_name = 'bbcNivelBloqueo')
begin
alter table BloqueoBeneficiarioCuotaMonetaria add bbcNivelBloqueo varchar(50);
alter table aud.BloqueoBeneficiarioCuotaMonetaria_aud add bbcNivelBloqueo varchar(50);
end

