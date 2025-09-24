
--GLPI 68146
ALTER TABLE AccionCobro2F
ALTER COLUMN comunicado varchar(60)

ALTER TABLE aud.AccionCobro2F_aud
ALTER COLUMN comunicado varchar(60)

IF NOT EXISTS (
  SELECT * 
  FROM   sys.columns 
  WHERE  object_id = OBJECT_ID(N'[aud].[AccionCobro2C_aud]') 
         AND name = 'aocDiasGeneracionActa'
)
begin
ALTER TABLE AUD.AccionCobro2C_aud
ADD  aocDiasGeneracionActa BIGINT
end