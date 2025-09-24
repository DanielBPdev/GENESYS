if not exists (select * from PlantillaComunicado where pcoEtiqueta in ('NOTI_IN_RE_APORTE') and pcoNombre = 'Notificación de Inconsistencias en el Recaudo de Aportes')
BEGIN
insert into PlantillaComunicado(pcoAsunto, pcoCuerpo, pcoEncabezado, pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta)
values ('Inconsistencias recaudo aporte',
'<p>Se&ntilde;or (a)</p>
<p>Afiliado Principal</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>',
'<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>Se&ntilde;or (a)</p>
<p>Afiliado Principal</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p><strong>Asunto:&nbsp;</strong>Aviso de incumplimiento&nbsp;}</p>',
'<p>&nbsp;</p>
<p>,&nbsp;&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>',
'Notificación de Inconsistencias en el Recaudo de Aportes',
'<p>.&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p>',
'NOTI_IN_RE_APORTE'
)
END

-- =========================================
declare @id_NOTI_INC_REC_APOR smallInt = (select top 1 pcoId from PlantillaComunicado where pcoEtiqueta in ('NOTI_IN_RE_APORTE') and pcoNombre = 'Notificación de Inconsistencias en el Recaudo de Aportes')
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @id_NOTI_INC_REC_APOR)
BEGIN
insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoTipoVariableComunicado) values

	('${cargoResponsableCcf}', 'Cargo del responsable del envío del comunicado en la caja', 'Cargo responsable CCF', @id_NOTI_INC_REC_APOR, 'CONSTANTE'),
	('${cargoResponsableCarteraCcf}', 'Cargo responsable del proceso de cartera', 'Cargo responsable del proceso de cartera', @id_NOTI_INC_REC_APOR, 'CONSTANTE'),
	('${ciudad}', 'Ciudad donde se encuentra el representante legal', 'Ciudad Representante Legal', @id_NOTI_INC_REC_APOR, 'VARIABLE'),
	('${contenido}}', 'En este lugar se remplazará el contenido que se agrega al editar el comunicado.', 'Contenido edición comunicado', @id_NOTI_INC_REC_APOR, 'VARIABLE'),
	('${direccion}', 'Dirección de la empresa relacionada al representante legal', 'Dirección del Representante Legal', @id_NOTI_INC_REC_APOR, 'VARIABLE'),
	('${fechaCorte}', 'Fecha de corte para evaluar periodo en mora', 'Fecha Corte', @id_NOTI_INC_REC_APOR, 'VARIABLE'),
	('${fechaDelSistema}', 'dd/mm/aaaa proporcionado por el sistema al generar el comunicado', 'Fecha del sistema', @id_NOTI_INC_REC_APOR, 'VARIABLE'),
	('${firmaResponsableProcesoCartera}', 'Firma del funcionario responsable del proceso de cartera', 'Firma del responsable de proceso de cartera', @id_NOTI_INC_REC_APOR, 'CONSTANTE'),
	('${firmaResponsableCcf}', 'Imagen de la firma del responsable del envío del comunicado en la caja', 'Firma Responsable CCF', @id_NOTI_INC_REC_APOR, 'CONSTANTE'),
	('${nombreYApellidosRepresentanteLegal}}', 'Nombres y Apellidos del representante legal de la empresa', 'Nombre y Apellidos Representante Legal', @id_NOTI_INC_REC_APOR, 'VARIABLE'),
	('${numeroIdentificacionCotizante)', 'Número de identifiación del Cotizante', 'Número de identificación del cotizante', @id_NOTI_INC_REC_APOR, 'VARIABLE'),
	('${numeroIdentificacionEmpleador}', 'Número de identifiación del empleador', 'Número de identificación del empleador', @id_NOTI_INC_REC_APOR, 'VARIABLE'),
	('${numeroIdentificacionRepresentanteLegal}', 'Número de identificación del representante legal', 'Número de identificación del representante legal', @id_NOTI_INC_REC_APOR, 'VARIABLE'),
	('${periodos}', 'Periodo inconsistente en formato AAAA-MM', 'Periodos', @id_NOTI_INC_REC_APOR, 'VARIABLE'),
	('${responsableCcf}', 'responsable caja de compensación', 'Responsable CCF', @id_NOTI_INC_REC_APOR, 'CONSTANTE'),
	('${responsableCarteraCcf}', 'Responsable del proceso de cartera', 'Responsable del proceso de cartera', @id_NOTI_INC_REC_APOR, 'CONSTANTE'),
	('${telefonoAfiliado}', 'Teléfono fijo o Celular capturado en Información de ubicación y correspondencia (uno de los dos)', 'Teléfono del afiliado', @id_NOTI_INC_REC_APOR, 'VARIABLE'),
	('${telefonoEmpleador}', 'Teléfono fijo o Celular capturado en Información de ubicación y correspondencia (uno de los dos)', 'Teléfono del empleador', @id_NOTI_INC_REC_APOR, 'VARIABLE'),
	('${tipoIdentificacionCotizante}', 'Tipo de identificación del cotizante', 'Tipo de identificacion del cotizante', @id_NOTI_INC_REC_APOR, 'VARIABLE'),
	('${tipoIdentificacionEmpleador}', 'Tipo de identificación del empleador', 'Tipo de identificación del empleador', @id_NOTI_INC_REC_APOR, 'VARIABLE'),
	('${tipoIdentificacionRepresentanteLegal}', 'Tipo de identificación del representante legal', 'Tipo de identificación representante legal', @id_NOTI_INC_REC_APOR, 'VARIABLE'),
	('${tipoDeInconsistenca)', 'IBC, Número de dias, % de tarifa, Estado del cotizante', 'Tipo de inconsistencia ', @id_NOTI_INC_REC_APOR, 'VARIABLE'),
	('${valorDeudaPresunta}', 'Valor deuda presunta', 'Valor deuda presunta', @id_NOTI_INC_REC_APOR, 'VARIABLE'),
	('${razonSocial/Nombre}', 'Razón social o Nombres y Apellidos del empleador, independiente o pensionado', 'Razón social/Nombre', @id_NOTI_INC_REC_APOR, 'VARIABLE')
END


declare @id_SEG_AVI_COB_PRS smallInt = (select top 1 pcoId from PlantillaComunicado where pcoEtiqueta in ('SEG_AVI_COB_PRS') and pcoNombre = 'Segundo aviso cobro persuasivo')
if not exists (select * from VariableComunicado where vcoPlantillaComunicado = @id_SEG_AVI_COB_PRS)
BEGIN
    insert into VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoTipoVariableComunicado) values
	('${fechaFirmezaTitulo}', 'Fecha de Firmeza del Titulo PTE DESARROLLO CC CARTERA', 'Fecha de Firmeza del Titulo', @id_SEG_AVI_COB_PRS, 'VARIABLE')

END

-- =============================
if not exists (select * from DestinatarioComunicado where dcoProceso = 'GESTION_COBRO_ELECTRONICO' and dcoEtiquetaPlantilla = 'NOTI_IN_RE_APORTE')
BEGIN
INSERT INTO DestinatarioComunicado(dcoProceso,dcoEtiquetaPlantilla) values
('GESTION_COBRO_ELECTRONICO', 'NOTI_IN_RE_APORTE')
END

-- =============================
if not exists (select * from DestinatarioComunicado where dcoProceso = 'GESTION_CARTERA_FISICA_GENERAL' and dcoEtiquetaPlantilla = 'NOTI_IN_RE_APORTE')
BEGIN
INSERT INTO DestinatarioComunicado(dcoProceso,dcoEtiquetaPlantilla) values
('GESTION_CARTERA_FISICA_GENERAL', 'NOTI_IN_RE_APORTE')
END