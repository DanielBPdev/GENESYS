if not exists (select * from PlantillaComunicado where pcoEtiqueta in ('NOTIFI_MORA_DESAF') and pcoNombre = 'Notificacion por mora en desafiliacion')
begin
insert into PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre,pcoPie, pcoEtiqueta)
values ('NOTIFICACION MORA DESAFILIACION', '<p>Se&ntilde;or (a)</p>
<p>Representante Legal</p>
<p>${nombreYApellidosRepresentanteLegal}</p>
<p>Empresa</p>
<p>${razonSocial/Nombre}</p>
<p>${direccion}</p>
<p>${telefono}</p>
<p>${ciudad}</p>
<p>&nbsp;</p>
<p><strong>Asunto:&nbsp;Notificacion de mora en desafiliacion&nbsp;</strong>&nbsp;${periodoMora}&nbsp;&nbsp;</p>
<p>&nbsp;</p>
<p>Atentamente nos permitimos comunicarle que con corte a ${fechaCorte}, el&nbsp;aporte a favor de CONFA, por el periodo de cotizaci&oacute;n ${periodoMora} no aparece en nuestro sistema.</p>
<p>Si su incumplimiento en el pago obedece a una novedad que no ha registrado, le agradecemos nos reporte la novedad en un plazo no mayor de ocho d&iacute;as contados a partir de la fecha de recibo de esta comunicaci&oacute;n a trav&eacute;s de la planilla pila o en su defecto enviarnos comunicaci&oacute;n escrita firmada por el representante legal de la empresa, adjuntando los soportes id&oacute;neos para subsanar la inconsistencia presentada. Los siguientes son ejemplos de novedades: cancelaci&oacute;n c&aacute;mara de comercio, liquidaci&oacute;n de consorcio o uni&oacute;n temporal, cambio de empleador, retiro de los trabajadores, traslado de trabajadores a otro departamento o sin n&ograve;mina temporalmente en Caldas, cambios permanentes en el ingreso base de cotizaci&oacute;n, incapacidades o licencias de maternidad durante todo el per&iacute;odo de cotizaci&oacute;n referido, suspensi&oacute;n temporal del contrato o licencias no remuneradas durante todo el per&iacute;odo de cotizaci&oacute;n.</p>
<p>Si el incumplimiento en el pago no corresponde a una novedad, le sugerimos realice inmediatamente&nbsp;el pago del&nbsp;monto que adeuda. Para hacerlo debe realizar el pago&nbsp;a trav&eacute;s de la planilla integrada de aportes PILA utilizando el&nbsp;operador de su elecci&oacute;n, el cual al momento del pago incluir&aacute; en la liquidaci&oacute;n los intereses de mora generados por el no pago oportuno y enviar as&iacute; el inicio de cobro administrativo judicial correspondiente, el cual generar&aacute; un costo adicional al final del proceso, as&iacute; como la pr&aacute;ctica de medidas cautelares como el embargo, secuestro y remate de los bienes o actos primordiales en el caso de no aclarar la mora presentada.</p>
<p>Debe tener en cuenta que es responsabilidad de la empresa reportar oportunamente las novedades de ingreso de sus trabajadores al Sistema de la Protecci&oacute;n Social; al igual de las dem&aacute;s novedades que se generan cada mes y que hacen parte integral al momento del pago. Si al momento de recibir este comunicado usted ya realiz&oacute; el pago del per&iacute;odo relacionado hacer caso omiso a este requerimiento; si por el contrario tiene alguna inquietud debe ser canalizada con: El &aacute;rea de Aportes en el tel&eacute;fono 8783111 ext 1369 - 1370. Esta comunicaci&oacute;n se env&iacute;a dando cumplimiento a los lineamientos establecidos por la Unidad de Gesti&oacute;n Pensional y Parafiscal UGPP en la Resoluci&oacute;n 2082 de Octubre 6 de 2016.</p>
<p>&nbsp;</p>
<p>Atentamente,</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>Yarledy Bland&oacute;n Bland&oacute;n&nbsp;</p>
<p>Subgerente Subsidio Familiar</p>',
        '<p>${logoDeLaCcf}</p> <p>&nbsp;</p> <p>${ciudadCcf},&nbsp;${fechaDelSistema}&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p>',
        NULL,
        '<p>${logoDeLaCcf}</p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p>${ciudadCcf},&nbsp;${fechaDelSistema}&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p>Se&ntilde;or (a)</p>  <p>Representante Legal</p>  <p>${nombreYApellidosRepresentanteLegal}</p>  <p>Empresa</p>  <p>${razonSocial/Nombre}</p>  <p>${direccion}</p>  <p>${telefono}</p>  <p>${ciudad}</p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p><strong>Asunto:&nbsp;</strong>Aviso de incumplimiento&nbsp;${periodoMora}&nbsp;</p>',
        'Notificacion por mora en desafiliacion',
        '<p>.&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p>',
        'NOTIFI_MORA_DESAF')
end
--================================
if not exists (select * from PlantillaComunicado where pcoEtiqueta in ('CARTA_APOR_EXPUL') and pcoNombre = 'Cartera a aportante expulsado')
begin
insert into PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie,pcoEtiqueta)
values ('CARTA A APORTANTE EXPULSADO'
        , '<p>Se&ntilde;or (a)</p>
<p>Representante Legal</p>
<p>${nombreYApellidosRepresentanteLegal}</p>
<p>Empresa</p>
<p>${razonSocial/Nombre}</p>
<p>${direccion}</p>
<p>${telefono}</p>
<p>${ciudad}</p>
<p>&nbsp;</p>
<p><strong>Asunto:&nbsp;Notificacion de mora en desafiliacion&nbsp;</strong>&nbsp;${periodoMora}&nbsp;&nbsp;</p>
<p>&nbsp;</p>
<p>Atentamente nos permitimos comunicarle que con corte a ${fechaCorte}, el&nbsp;aporte a favor de CONFA, por el periodo de cotizaci&oacute;n ${periodoMora} no aparece en nuestro sistema.</p>
<p>Si su incumplimiento en el pago obedece a una novedad que no ha registrado, le agradecemos nos reporte la novedad en un plazo no mayor de ocho d&iacute;as contados a partir de la fecha de recibo de esta comunicaci&oacute;n a trav&eacute;s de la planilla pila o en su defecto enviarnos comunicaci&oacute;n escrita firmada por el representante legal de la empresa, adjuntando los soportes id&oacute;neos para subsanar la inconsistencia presentada. Los siguientes son ejemplos de novedades: cancelaci&oacute;n c&aacute;mara de comercio, liquidaci&oacute;n de consorcio o uni&oacute;n temporal, cambio de empleador, retiro de los trabajadores, traslado de trabajadores a otro departamento o sin n&ograve;mina temporalmente en Caldas, cambios permanentes en el ingreso base de cotizaci&oacute;n, incapacidades o licencias de maternidad durante todo el per&iacute;odo de cotizaci&oacute;n referido, suspensi&oacute;n temporal del contrato o licencias no remuneradas durante todo el per&iacute;odo de cotizaci&oacute;n.</p>
<p>Si el incumplimiento en el pago no corresponde a una novedad, le sugerimos realice inmediatamente&nbsp;el pago del&nbsp;monto que adeuda. Para hacerlo debe realizar el pago&nbsp;a trav&eacute;s de la planilla integrada de aportes PILA utilizando el&nbsp;operador de su elecci&oacute;n, el cual al momento del pago incluir&aacute; en la liquidaci&oacute;n los intereses de mora generados por el no pago oportuno y enviar as&iacute; el inicio de cobro administrativo judicial correspondiente, el cual generar&aacute; un costo adicional al final del proceso, as&iacute; como la pr&aacute;ctica de medidas cautelares como el embargo, secuestro y remate de los bienes o actos primordiales en el caso de no aclarar la mora presentada.</p>
<p>Debe tener en cuenta que es responsabilidad de la empresa reportar oportunamente las novedades de ingreso de sus trabajadores al Sistema de la Protecci&oacute;n Social; al igual de las dem&aacute;s novedades que se generan cada mes y que hacen parte integral al momento del pago. Si al momento de recibir este comunicado usted ya realiz&oacute; el pago del per&iacute;odo relacionado hacer caso omiso a este requerimiento; si por el contrario tiene alguna inquietud debe ser canalizada con: El &aacute;rea de Aportes en el tel&eacute;fono 8783111 ext 1369 - 1370. Esta comunicaci&oacute;n se env&iacute;a dando cumplimiento a los lineamientos establecidos por la Unidad de Gesti&oacute;n Pensional y Parafiscal UGPP en la Resoluci&oacute;n 2082 de Octubre 6 de 2016.</p>
<p>&nbsp;</p>
<p>Atentamente,</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>Yarledy Bland&oacute;n Bland&oacute;n&nbsp;</p>
<p>Subgerente Subsidio Familiar</p>',
    '<p>${logoDeLaCcf}</p> <p>&nbsp;</p> <p>${ciudadCcf},&nbsp;${fechaDelSistema}&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p>',
    NULL, '<p>${logoDeLaCcf}</p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p>${ciudadCcf},&nbsp;${fechaDelSistema}&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p>Se&ntilde;or (a)</p>  <p>Representante Legal</p>  <p>${nombreYApellidosRepresentanteLegal}</p>  <p>Empresa</p>  <p>${razonSocial/Nombre}</p>  <p>${direccion}</p>  <p>${telefono}</p>  <p>${ciudad}</p>  <p>&nbsp;</p>  <p>&nbsp;</p>  <p><strong>Asunto:&nbsp;</strong>Aviso de incumplimiento&nbsp;${periodoMora}&nbsp;</p>', 'Cartera a aportante expulsado', '<p>.&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p>'
	, 'CARTA_APOR_EXPUL')
end
--=================================
declare @id_NOTIFI_MORA_DESAF smallInt = (select pcoId from PlantillaComunicado where pcoEtiqueta in ('NOTIFI_MORA_DESAF') and pcoNombre = 'Notificacion por mora en desafiliacion')
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @id_NOTIFI_MORA_DESAF)
begin
INSERT INTO VariableComunicado
( vcoClave
, vcoDescripcion
, vcoNombre
, vcoPlantillaComunicado
, vcoNombreConstante
, vcoTipoVariableComunicado
, vcoOrden)
VALUES
    ('${responsableCarteraCcf}',' Responsable del proceso de cartera',' Responsable del proceso de cartera',@id_NOTIFI_MORA_DESAF,'RESPONSABLE_CARTERA_CCF','CONSTANTE',''),
    ('${cargoResponsableCarteraCcf}',' Cargo responsable del proceso de cartera',' Cargo responsable del proceso de cartera',@id_NOTIFI_MORA_DESAF,'CARGO_RESPONSABLE_CARTERA_CCF','CONSTANTE',''),
    ('${fechaDelSistema}',' dd/mm/aaaa proporcionado por el sistema al generar el comunicado',' Fecha del sistema',@id_NOTIFI_MORA_DESAF,'','VARIABLE', 0),
    ('${nombreYApellidosRepresentanteLegal}',' Nombres y apellidos del representante legal asociado a la solicitud',' Nombre y Apellidos Representante Legal',@id_NOTIFI_MORA_DESAF,'','VARIABLE', 0),
    ('${responsableCarteraCcf}',' Responsable del proceso de cartera',' Responsable del proceso de cartera',@id_NOTIFI_MORA_DESAF,'RESPONSABLE_CARTERA_CCF','CONSTANTE',''),
    ('${razonSocial/Nombre}',' Razón social de la empresa a la cual se encuentra asociado el representante legal',' Razón social / Nombre',@id_NOTIFI_MORA_DESAF,'','VARIABLE', 0),
    ('${direccion}',' Dirección de la empresa relacionada al representante legal',' Dirección',@id_NOTIFI_MORA_DESAF,'','VARIABLE', 0),
    ('${telefono}',' Teléfono fijo o Celular capturado en Información de representante legal',' Teléfono',@id_NOTIFI_MORA_DESAF,'','VARIABLE', 0),
    ('${ciudad}',' Ciudad donde se encuentra el representante legal',' Ciudad',@id_NOTIFI_MORA_DESAF,'','VARIABLE', 0),
    ('${periodoMora}',' Periodos para los cuales esta en mora',' Periodo Mora',@id_NOTIFI_MORA_DESAF,'','VARIABLE', 0),
    ('${fechaCorte}',' Fecha de corte para evaluar periodo en mora',' Fecha Corte',@id_NOTIFI_MORA_DESAF,'','VARIABLE', 0),
    ('${responsableCcf}',' responsable caja de compensación',' Responsable CCF',@id_NOTIFI_MORA_DESAF,'RESPONSABLE_CCF','CONSTANTE',''),
    ('${cargoResponsableCcf}',' Cargo del responsable del envío del comunicado en la caja',' Cargo responsable CCF',@id_NOTIFI_MORA_DESAF,'CARGO_RESPONSABLE_CCF','CONSTANTE',''),
    ('${firmaResponsableCcf}',' Imagen de la firma del responsable del envío del comunicado en la caja',' Firma Responsable CCF',@id_NOTIFI_MORA_DESAF,'FIRMA_RESPONSABLE_CCF','CONSTANTE',''),
    ('${firmaResponsableProcesoCartera}',' Firma del responsable de proceso de cartera',' Firma del responsable de proceso de cartera',@id_NOTIFI_MORA_DESAF,'FIRMA_RESPONSABLE_CARTERA_CCF','CONSTANTE',''),
    ('${numeroIdentificacionEmpleador}',' Número de identificación del empleador',' Número de identificación del empleador',@id_NOTIFI_MORA_DESAF,'','VARIABLE',''),
    ('${numeroIdentificacionRepresentanteLegal}',' Número de identificación del representante legal',' Número de identificación del representante legal',@id_NOTIFI_MORA_DESAF,'','VARIABLE',''),
    ('${periodos}',' Periodo-año / Monto adeudado (en miles sin decimales)',' Periodos',@id_NOTIFI_MORA_DESAF,'','REPORTE_VARIABLE',''),
    ('${tipoIdentificacionEmpleador}',' Tipo de identificación del empleador',' Tipo de identificación del empleador',@id_NOTIFI_MORA_DESAF,'','VARIABLE',''),
    ('${tipoIdentificacionRepresentanteLegal}',' Tipo de identificación del representante legal',' Tipo de identificación del representante legal',@id_NOTIFI_MORA_DESAF,'','VARIABLE',''),
    ('${valorDeudaPresunta}',' Valor deuda presunta',' Valor deuda presunta',@id_NOTIFI_MORA_DESAF,'','VARIABLE','')
end

declare @id_CARTA_APOR_EXPUL smallInt = (select pcoId from PlantillaComunicado where pcoEtiqueta in ('CARTA_APOR_EXPUL') and pcoNombre = 'Cartera a aportante expulsado')
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @id_CARTA_APOR_EXPUL)
begin
INSERT INTO VariableComunicado
( vcoClave
, vcoDescripcion
, vcoNombre
, vcoPlantillaComunicado
, vcoNombreConstante
, vcoTipoVariableComunicado
, vcoOrden)
VALUES
    ('${cargoResponsableCarteraCcf}',' Cargo responsable del proceso de cartera',' Cargo responsable del proceso de cartera',@id_CARTA_APOR_EXPUL,'CARGO_RESPONSABLE_CARTERA_CCF','CONSTANTE',''),
    ('${fechaDelSistema}',' dd/mm/aaaa proporcionado por el sistema al generar el comunicado',' Fecha del sistema',@id_CARTA_APOR_EXPUL,'','VARIABLE', 0),
    ('${nombreYApellidosRepresentanteLegal}',' Nombres y apellidos del representante legal asociado a la solicitud',' Nombre y Apellidos Representante Legal',@id_CARTA_APOR_EXPUL,'','VARIABLE', 0),
    ('${razonSocial/Nombre}',' Razón social de la empresa a la cual se encuentra asociado el representante legal',' Razón social / Nombre',@id_CARTA_APOR_EXPUL,'','VARIABLE', 0),
    ('${direccion}',' Dirección de la empresa relacionada al representante legal',' Dirección',@id_CARTA_APOR_EXPUL,'','VARIABLE', 0),
    ('${telefono}',' Teléfono fijo o Celular capturado en Información de representante legal',' Teléfono',@id_CARTA_APOR_EXPUL,'','VARIABLE', 0),
    ('${ciudad}',' Ciudad donde se encuentra el representante legal',' Ciudad',@id_CARTA_APOR_EXPUL,'','VARIABLE', 0),
    ('${responsableCcf}',' responsable caja de compensación',' Responsable CCF',@id_CARTA_APOR_EXPUL,'RESPONSABLE_CCF','CONSTANTE',''),
    ('${cargoResponsableCcf}',' Cargo del responsable del envío del comunicado en la caja',' Cargo responsable CCF',@id_CARTA_APOR_EXPUL,'CARGO_RESPONSABLE_CCF','CONSTANTE',''),
    ('${firmaResponsableCcf}',' Imagen de la firma del responsable del envío del comunicado en la caja',' Firma Responsable CCF',@id_CARTA_APOR_EXPUL,'FIRMA_RESPONSABLE_CCF','CONSTANTE',''),
    ('${firmaResponsableProcesoCartera}',' Firma del responsable de proceso de cartera',' Firma del responsable de proceso de cartera',@id_CARTA_APOR_EXPUL,'FIRMA_RESPONSABLE_CARTERA_CCF','CONSTANTE',''),
    ('${numeroIdentificacionEmpleador}',' Número de identificación del empleador',' Número de identificación del empleador',@id_CARTA_APOR_EXPUL,'','VARIABLE',''),
    ('${numeroIdentificacionRepresentanteLegal}',' Número de identificación del representante legal',' Número de identificación del representante legal',@id_CARTA_APOR_EXPUL,'','VARIABLE',''),
    ('${periodos}',' Periodo-año / Monto adeudado (en miles sin decimales)',' Periodos',@id_CARTA_APOR_EXPUL,'','REPORTE_VARIABLE',''),
    ('${tipoIdentificacionEmpleador}',' Tipo de identificación del empleador',' Tipo de identificación del empleador',@id_CARTA_APOR_EXPUL,'','VARIABLE',''),
    ('${tipoIdentificacionRepresentanteLegal}',' Tipo de identificación del representante legal',' Tipo de identificación del representante legal',@id_CARTA_APOR_EXPUL,'','VARIABLE',''),
    ('${contenido}',' Texto que se adiciona al comunicado por parte del usuario',' Contenido',@id_CARTA_APOR_EXPUL,'','VARIABLE',''),
    ('${cargoResponsableCcf}',' Cargo del responsable del envío del comunicado en la caja',' Cargo responsable CCF',@id_CARTA_APOR_EXPUL,'CARGO_RESPONSABLE_CCF','CONSTANTE',''),
    ('${consecutivoLiquidacion}',' Número consecutivo de la liquidación',' Consecutivo Liquidación',@id_CARTA_APOR_EXPUL,'','VARIABLE', 0),
    ('${totalLiquidacion}',' El valor total de la liquidación para el aportante',' Total Liquidación',@id_CARTA_APOR_EXPUL,'','REPORTE_VARIABLE', 0),
    ('${responsableCarteraCcf}',' Responsable del proceso de cartera',' Responsable del proceso de cartera',@id_CARTA_APOR_EXPUL,' RESPONSABLE_CARTERA_CCF','CONSTANTE',''),
    ('${numeroIdentificacionAportante}',' Número de identificación',' Número de identificación',@id_CARTA_APOR_EXPUL,'','VARIABLE','')
end
--=======================================
if not exists (select * from DestinatarioComunicado where dcoProceso = 'GESTION_COBRO_ELECTRONICO' and dcoEtiquetaPlantilla = 'NOTIFI_MORA_DESAF')
begin
insert into DestinatarioComunicado(dcoProceso,dcoEtiquetaPlantilla)values('GESTION_COBRO_ELECTRONICO','NOTIFI_MORA_DESAF')
end
--=======================================
if not exists (select * from DestinatarioComunicado where dcoProceso = 'DESAFILIACION_APORTANTES' and dcoEtiquetaPlantilla = 'CARTA_APOR_EXPUL')
begin
insert into DestinatarioComunicado(dcoProceso,dcoEtiquetaPlantilla)values('DESAFILIACION_APORTANTES','CARTA_APOR_EXPUL')
end