BEGIN
   IF NOT EXISTS (SELECT vcoId FROM VariableComunicado WHERE vcoClave = '${numeroDeRadicacion}')
   BEGIN
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) 
VALUES ('${numeroDeRadicacion}', 'Número de radicación','NUMERO_RADICACION','Número de radicado de la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'DSTMTO_NVD_RET_APRT'));
 END
END