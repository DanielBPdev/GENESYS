BEGIN
   IF NOT EXISTS (SELECT vcoId FROM VariableComunicado WHERE vcoClave = '${numeroDeRadicacion}' and vcoPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoEtiqueta = 'DSTMTO_NVD_RET_APRT'))
   BEGIN
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) 
VALUES ('${numeroDeRadicacion}', 'Número de radicación','Número de radicado de la solicitud','Número de radicado de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DSTMTO_NVD_RET_APRT'));
 END
END