UPDATE FileDefinition
SET decimalSeparator = '|'
where id in (select cnsValor from Constante where cnsNombre = 'FILE_DEFINITION_ID_PERSONAS_SIN_DERECHO')

DECLARE @idPlantillaComunicado SMALLINT = (select pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'NOTI_IN_RE_APORTE')



UPDATE vco
SET vcoTipoVariableComunicado = 'REPORTE_VARIABLE'
FROM VariableComunicado
where vcoPlantillaComunicado = @idPlantillaComunicado 
and vcoClave = '${valorPeriodos}'



UPDATE vco
SET vcoTipoVariableComunicado = 'VARIABLE'
FROM VariableComunicado
where vcoPlantillaComunicado = @idPlantillaComunicado 
and vcoClave = '${periodos}'