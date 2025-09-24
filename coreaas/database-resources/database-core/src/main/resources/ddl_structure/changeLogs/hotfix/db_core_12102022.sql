declare @id_PRI_AVI_COB_PRS smallInt = (select pcoId from PlantillaComunicado where pcoEtiqueta in ('PRI_AVI_COB_PRS') and pcoNombre = 'Primer aviso cobro persuasivo')
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @id_PRI_AVI_COB_PRS)
BEGIN
    insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoTipoVariableComunicado) values
   ('${valorConsecutivoLiquidacion}', 'Valor de número consecutivo de la liquidación', 'Valor consecutivo liquidación', @id_PRI_AVI_COB_PRS, 'VARIABLE'),
   ('${valorFechaFirmezaTitulo}', 'valor de fecha de firmeza del Titulo PTE DESARROLLO CC CARTERA', 'Valor fecha firmeza título', @id_PRI_AVI_COB_PRS, 'VARIABLE'),
   ('${fechaFirmezaTitulo}', 'Fecha de firmeza del Titulo PTE DESARROLLO CC CARTERA', 'Fecha firmeza título', @id_PRI_AVI_COB_PRS, 'VARIABLE')
END