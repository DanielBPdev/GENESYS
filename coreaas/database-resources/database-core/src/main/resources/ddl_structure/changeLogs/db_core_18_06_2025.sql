
if exists (select * from Parametro where prmNombre = 'CLIENTID_SAT_RES_AFI_PRIMER_VEZ')
begin
    INSERT INTO Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato) VALUES
    ('CLIENTID_SAT_RES_AFI_PRIMER_VEZ', '80d7d5e8a0fc49c8a0d611e13c9a71dd', 0, 'VALOR_GLOBAL_NEGOCIO', 'Client id respuesta afiliacion primera vez', 'TEXT')
end