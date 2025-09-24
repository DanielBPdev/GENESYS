if not exists (select * from ParametrizacionEjecucionProgramada where pepProceso = 'AVISIO_PRESCRIPCION_SUBSIDIO')
	begin
		INSERT INTO ParametrizacionEjecucionProgramada
		VALUES ('AVISIO_PRESCRIPCION_SUBSIDIO','10','50','00',NULL,NULL,NULL,NULL,NULL,NULL,'DIARIO','ACTIVO');
	end

if not exists (select * from Parametro where prmNombre = 'ENVIO_COMUNICADO_PRESCRIPCION_PAGOS_PRIMER_AVISO')
	begin
		INSERT INTO Parametro
		VALUES ('ENVIO_COMUNICADO_PRESCRIPCION_PAGOS_PRIMER_AVISO','90',0,'VALOR_GLOBAL_NEGOCIO','Parámetro de días para el envío del primer aviso de prescripción de cuotas monetarias','TIME')
	end

if not exists (select * from Parametro where prmNombre = 'ENVIO_COMUNICADO_PRESCRIPCION_PAGOS_SEGUNDO_AVISO')
	begin
		INSERT INTO Parametro
		values ('ENVIO_COMUNICADO_PRESCRIPCION_PAGOS_SEGUNDO_AVISO','60',0,'VALOR_GLOBAL_NEGOCIO','Parámetro de días para el envío del segundo aviso de prescripción de cuotas monetarias','TIME')
	end

if not exists (select * from Parametro where prmNombre = 'ENVIO_COMUNICADO_PRESCRIPCION_PAGOS_TERCER_AVISO')
	begin
		INSERT INTO Parametro
		values ('ENVIO_COMUNICADO_PRESCRIPCION_PAGOS_TERCER_AVISO','30',0,'VALOR_GLOBAL_NEGOCIO','Parámetro de días para el envío del tercer aviso de prescripción de cuotas monetarias','TIME')
	end

if not exists (select * from DestinatarioComunicado where dcoProceso = 'PAGOS_SUBSIDIO_MONETARIO' and dcoEtiquetaPlantilla = 'COM_SUB_AVISO_TRES_PRE_PAG_TRA')
	begin
		INSERT INTO DestinatarioComunicado
		VALUES ('PAGOS_SUBSIDIO_MONETARIO','COM_SUB_AVISO_TRES_PRE_PAG_TRA')
	end


if not exists (select * from DestinatarioComunicado where dcoProceso = 'PAGOS_SUBSIDIO_MONETARIO' and dcoEtiquetaPlantilla = 'COM_SUB_AVISO_DOS_PRE_PAG_TRA')
	begin
		INSERT INTO DestinatarioComunicado
		VALUES ('PAGOS_SUBSIDIO_MONETARIO','COM_SUB_AVISO_DOS_PRE_PAG_TRA')
	end

if not exists (select * from DestinatarioComunicado where dcoProceso = 'PAGOS_SUBSIDIO_MONETARIO' and dcoEtiquetaPlantilla = 'COM_SUB_AVISO_UNO_PRE_PAG_TRA')
	begin
		INSERT INTO DestinatarioComunicado
		VALUES ('PAGOS_SUBSIDIO_MONETARIO','COM_SUB_AVISO_UNO_PRE_PAG_TRA')
	end


if not exists (select * from PlantillaComunicado where pcoNombre = 'Notificación del pimer aviso de la prescripción de un subsidio monetario' and pcoEtiqueta = 'COM_SUB_AVISO_UNO_PRE_PAG_TRA')
	begin
		INSERT INTO PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoIdentificadorImagenPie,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta)
		VALUES ('NOTIFICACION PRIMER AVISO PRESCRIPCION SUBSIDIO MONETARIO ADMINISTRADOR SUBSIDIO',
		'<p>Se&ntilde;or (a)</p> <p>Afiliado Principal</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>${cargoResponsableCcf}</p> <p>${ciudadCcf}</p> <p>${ciudadSolicitud}</p> <p>${departamento}</p> <p>${direccion}</p> <p>${fechaDelSistema}</p> <p>${firmaResponsableCcf}</p> <p>${montoPrescrito}</p> <p>${municipio}</p> <p>${nombreDelAdministradorDelSubsidio}</p> <p>${numeroIdentificacionAdmin}</p> <p>${periodosLiquidados}</p> <p>${responsableCcf}</p> <p>${telefono}</p> <p>${tipoIdentificacionAdmin}</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>Asunto:&nbsp;Liquidaci&oacute;n Subsidio Monetario&nbsp;</p> <p>&nbsp;</p> <p>Por medio de la presente nos permitimos informarle que se ha programado la dispersi&oacute;n de la liquidaci&oacute;n de subsidio monetario especifico&nbsp;el ${fechaDelSistema}&nbsp;</p> <p>Cualquier inquietud comunicarse al tel&eacute;fono 8783111 extensiones 1377 - 1393 o acercarse a nuestras oficinas Cra 25 Clle 50 esquina, Manizales, Caldas.</p> <p>&nbsp;</p> <p>El valor a prescribir es de:&nbsp;</p> <p>Cordial Saludo,</p> <p>&nbsp;</p> <p>&nbsp;</p>',
		'<p>${logoDeLaCcf}</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>Comunicado de prescripci&oacute;n de subsidio monetarios&nbsp;</p>',
		NULL, 
		'<p>${cargoResponsableCcf}</p> <p>${ciudadCcf}&nbsp;</p> <p>${ciudadSolicitud}</p> <p>&nbsp;${departamento}&nbsp;</p> <p>${direccion}&nbsp;</p> <p>${fechaDelSistema}</p> <p>&nbsp;${firmaResponsableCcf}</p> <p>&nbsp;${montoPrescrito}&nbsp;</p> <p>${municipio}</p> <p>&nbsp;${nombreDelAdministradorDelSubsidio}</p> <p>&nbsp;${numeroDeBeneficiarios}&nbsp;${telefono}&nbsp;</p> <p>&nbsp;</p> <p>Mensaje del correo.</p>',
		'Notificación del pimer aviso de la prescripción de un subsidio monetario',
		'Pie',
		'COM_SUB_AVISO_UNO_PRE_PAG_TRA')
	end

if not exists (select * from PlantillaComunicado where pcoNombre = 'Notificación del segundo aviso de la prescripción de un subsidio monetario' and pcoEtiqueta = 'COM_SUB_AVISO_DOS_PRE_PAG_TRA')
	begin
		INSERT INTO PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoIdentificadorImagenPie,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta)
		VALUES ('NOTIFICACION SEGUNDO AVISO PRESCRIPCION SUBSIDIO MONETARIO ADMINISTRADOR SUBSIDIO',
		'<p>Se&ntilde;or (a)</p> <p>Afiliado Principal</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>${cargoResponsableCcf}</p> <p>${ciudadCcf}</p> <p>${ciudadSolicitud}</p> <p>${departamento}</p> <p>${direccion}</p> <p>${fechaDelSistema}</p> <p>${firmaResponsableCcf}</p> <p>${montoPrescrito}</p> <p>${municipio}</p> <p>${nombreDelAdministradorDelSubsidio}</p> <p>${numeroIdentificacionAdmin}</p> <p>${periodosLiquidados}</p> <p>${responsableCcf}</p> <p>${telefono}</p> <p>${tipoIdentificacionAdmin}</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>Asunto:&nbsp;Liquidaci&oacute;n Subsidio Monetario&nbsp;</p> <p>&nbsp;</p> <p>Por medio de la presente nos permitimos informarle que se ha programado la dispersi&oacute;n de la liquidaci&oacute;n de subsidio monetario especifico&nbsp;el ${fechaDelSistema}&nbsp;</p> <p>Cualquier inquietud comunicarse al tel&eacute;fono 8783111 extensiones 1377 - 1393 o acercarse a nuestras oficinas Cra 25 Clle 50 esquina, Manizales, Caldas.</p> <p>&nbsp;</p> <p>El valor a prescribir es de:&nbsp;</p> <p>Cordial Saludo,</p> <p>&nbsp;</p> <p>&nbsp;</p>',
		'<p>${logoDeLaCcf}</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>Comunicado de prescripci&oacute;n de subsidio monetarios&nbsp;</p>',
		NULL, 
		'<p>${cargoResponsableCcf}</p> <p>${ciudadCcf}&nbsp;</p> <p>${ciudadSolicitud}</p> <p>&nbsp;${departamento}&nbsp;</p> <p>${direccion}&nbsp;</p> <p>${fechaDelSistema}</p> <p>&nbsp;${firmaResponsableCcf}</p> <p>&nbsp;${montoPrescrito}&nbsp;</p> <p>${municipio}</p> <p>&nbsp;${nombreDelAdministradorDelSubsidio}</p> <p>&nbsp;${numeroDeBeneficiarios}&nbsp;${telefono}&nbsp;</p> <p>&nbsp;</p> <p>Mensaje del correo.</p>',
		'Notificación del segundo aviso de la prescripción de un subsidio monetario',
		'Pie',
		'COM_SUB_AVISO_DOS_PRE_PAG_TRA')
	end

if not exists (select * from PlantillaComunicado where pcoNombre = 'Notificación del tercer aviso de la prescripción de un subsidio monetario' and pcoEtiqueta = 'COM_SUB_AVISO_TRES_PRE_PAG_TRA')
	begin
		INSERT INTO PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoIdentificadorImagenPie,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta)
		VALUES ('NOTIFICACION TERCER AVISO PRESCRIPCION SUBSIDIO MONETARIO ADMINISTRADOR SUBSIDIO',
		'<p>Se&ntilde;or (a)</p> <p>Afiliado Principal</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>${cargoResponsableCcf}</p> <p>${ciudadCcf}</p> <p>${ciudadSolicitud}</p> <p>${departamento}</p> <p>${direccion}</p> <p>${fechaDelSistema}</p> <p>${firmaResponsableCcf}</p> <p>${montoPrescrito}</p> <p>${municipio}</p> <p>${nombreDelAdministradorDelSubsidio}</p> <p>${numeroIdentificacionAdmin}</p> <p>${periodosLiquidados}</p> <p>${responsableCcf}</p> <p>${telefono}</p> <p>${tipoIdentificacionAdmin}</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>Asunto:&nbsp;Liquidaci&oacute;n Subsidio Monetario&nbsp;</p> <p>&nbsp;</p> <p>Por medio de la presente nos permitimos informarle que se ha programado la dispersi&oacute;n de la liquidaci&oacute;n de subsidio monetario especifico&nbsp;el ${fechaDelSistema}&nbsp;</p> <p>Cualquier inquietud comunicarse al tel&eacute;fono 8783111 extensiones 1377 - 1393 o acercarse a nuestras oficinas Cra 25 Clle 50 esquina, Manizales, Caldas.</p> <p>&nbsp;</p> <p>El valor a prescribir es de:&nbsp;</p> <p>Cordial Saludo,</p> <p>&nbsp;</p> <p>&nbsp;</p>',
		'<p>${logoDeLaCcf}</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>Comunicado de prescripci&oacute;n de subsidio monetarios&nbsp;</p>',
		NULL, 
		'<p>${cargoResponsableCcf}</p> <p>${ciudadCcf}&nbsp;</p> <p>${ciudadSolicitud}</p> <p>&nbsp;${departamento}&nbsp;</p> <p>${direccion}&nbsp;</p> <p>${fechaDelSistema}</p> <p>&nbsp;${firmaResponsableCcf}</p> <p>&nbsp;${montoPrescrito}&nbsp;</p> <p>${municipio}</p> <p>&nbsp;${nombreDelAdministradorDelSubsidio}</p> <p>&nbsp;${numeroDeBeneficiarios}&nbsp;${telefono}&nbsp;</p> <p>&nbsp;</p> <p>Mensaje del correo.</p>',
		'Notificación del tercer aviso de la prescripción de un subsidio monetario',
		'Pie',
		'COM_SUB_AVISO_TRES_PRE_PAG_TRA')
	end


DECLARE @idPlantillaComunicadoUno SMALLINT = (select pcoId FROM PlantillaComunicado WHERE pcoAsunto = 'NOTIFICACION PRIMER AVISO PRESCRIPCION SUBSIDIO MONETARIO ADMINISTRADOR SUBSIDIO')
if not exists (select * from VariableComunicado  where vcoPlantillaComunicado = @idPlantillaComunicadoUno)
	begin
	
		INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
		VALUES ('${ciudadSolicitud}', 'Ciudad de la sede donde se realiza la solicitud', 'Ciudad solicitud', @idPlantillaComunicadoUno, 'CIUDAD_CCF', 'CONSTANTE',''),
				('${contenido}', 'Texto que se adiciona al comunicado por parte del usuario', 'Contenido', @idPlantillaComunicadoUno, '', 'VARIABLE',''),
				('${departamento}', 'Departamento capturada en Información de ubicación y correspondencia del trabajador', 'Departamento', @idPlantillaComunicadoUno, '', 'VARIABLE',''),
				('${direccion}', 'Dirección capturada en Información de ubicación y correspondencia del empleador', 'Dirección', @idPlantillaComunicadoUno, '', 'VARIABLE',''),
				('${fechaDelSistema}', 'dd/mm/aaaa proporcionado por el sistema al generar el comunicado', 'Fecha del sistema', @idPlantillaComunicadoUno, '', 'VARIABLE',''),
				('${firmaResponsableCcf}', 'Imagen de la firma del responsable del envío del comunicado en la caja', 'Firma Responsable CCF', @idPlantillaComunicadoUno, 'FIRMA_RESPONSABLE_CCF', 'CONSTANTE',''),
				('${montoPrescrito}', 'Monto a preescribir', 'Monto a preescribir', @idPlantillaComunicadoUno, '', 'VARIABLE',''),
				('${municipio}', 'Municipio asociado a la direccion de la persona', 'Municipio', @idPlantillaComunicadoUno, '', 'VARIABLE',''),
				('${nombreDelAdministradorDelSubsidio}', 'Nombre del administrador del administrador del subsidio', 'Nombre del administrador del subsidio', @idPlantillaComunicadoUno, '', 'VARIABLE',''),
				('${numeroDeBeneficiarios}', 'Número de beneficiarios en los periodos liquidados', 'Número de beneficiarios', @idPlantillaComunicadoUno, '', 'VARIABLE',''),
				('${numeroIdentificacionAdmin}', 'Número de identificación del admin del subsidio que realizó la solicitud', 'Número identificación del admin del subsidio', @idPlantillaComunicadoUno, '', 'VARIABLE',''),
				('${periodosLiquidados}', 'Periodos presentes en la liquidación', 'Periodos liquidados', @idPlantillaComunicadoUno, '', 'VARIABLE',''),
				('${reporteDeBeneficiarios}', 'Lista de beneficiarios en los periodos liquidadosn', 'Reporte de beneficiarios', @idPlantillaComunicadoUno, '', 'VARIABLE',''),
				('${responsableCcf}', 'Nombre del responsable del envío del comunicado en la caja', 'Responsable CCF', @idPlantillaComunicadoUno, 'RESPONSABLE_CCF', 'CONSTANTE',''),
				('${telefono}', 'Teléfono fijo o Celular capturado en Información de ubicación y correspondencia (uno de los dos)', 'Teléfono', @idPlantillaComunicadoUno, '', 'VARIABLE',''),
				('${tipoIdentificacionAdmin}', 'Tipo de identificación del administrador del subsidio que realizó la solicitud', 'Tipo Identificacion Administrador Del Subsidio', @idPlantillaComunicadoUno, '', 'VARIABLE',''),
				('${logoDeLaCcf}', 'Logotipo de la Caja de Compensación', 'Logo de la CCF', @idPlantillaComunicadoUno, 'LOGO_DE_LA_CCF', 'CONSTANTE',''),
				('${ciudadCcf}', 'Ciudad de Ubicación de la Caja de Compensación', 'Ciudad CCF', @idPlantillaComunicadoUno, 'CIUDAD_CCF', 'CONSTANTE',''),
				--('${ciudadCcf}', 'Ciudad de Ubicación de la Caja de Compensación', 'Ciudad CCF', @idPlantillaComunicadoUno, 'CIUDAD_CCF', 'CONSTANTE',''),
				('${AvisoPrescripcionCcf}', 'Días parametrizados restantes a la prescripción del subsidio monetario', 'Aviso prescripción', @idPlantillaComunicadoUno, '', 'VARIABLE','')
	end	


DECLARE @idPlantillaComunicadoDos SMALLINT = (select pcoId FROM PlantillaComunicado WHERE pcoAsunto = 'NOTIFICACION SEGUNDO AVISO PRESCRIPCION SUBSIDIO MONETARIO ADMINISTRADOR SUBSIDIO')
if not exists (select * from VariableComunicado  where vcoPlantillaComunicado = @idPlantillaComunicadoDos)
	begin
	
		INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
		VALUES  ('${ciudadSolicitud}', 'Ciudad de la sede donde se realiza la solicitud', 'Ciudad solicitud', @idPlantillaComunicadoDos, 'CIUDAD_CCF', 'CONSTANTE',''),
				('${contenido}', 'Texto que se adiciona al comunicado por parte del usuario', 'Contenido', @idPlantillaComunicadoDos, '', 'VARIABLE',''),
				('${departamento}', 'Departamento capturada en Información de ubicación y correspondencia del trabajador', 'Departamento', @idPlantillaComunicadoDos, '', 'VARIABLE',''),
				('${direccion}', 'Dirección capturada en Información de ubicación y correspondencia del empleador', 'Dirección', @idPlantillaComunicadoDos, '', 'VARIABLE',''),
				('${fechaDelSistema}', 'dd/mm/aaaa proporcionado por el sistema al generar el comunicado', 'Fecha del sistema', @idPlantillaComunicadoDos, '', 'VARIABLE',''),
				('${firmaResponsableCcf}', 'Imagen de la firma del responsable del envío del comunicado en la caja', 'Firma Responsable CCF', @idPlantillaComunicadoDos, 'FIRMA_RESPONSABLE_CCF', 'CONSTANTE',''),
				('${montoPrescrito}', 'Monto a preescribir', 'Monto a preescribir', @idPlantillaComunicadoDos, '', 'VARIABLE',''),
				('${municipio}', 'Municipio asociado a la direccion de la persona', 'Municipio', @idPlantillaComunicadoDos, '', 'VARIABLE',''),
				('${nombreDelAdministradorDelSubsidio}', 'Nombre del administrador del administrador del subsidio', 'Nombre del administrador del subsidio', @idPlantillaComunicadoDos, '', 'VARIABLE',''),
				('${numeroDeBeneficiarios}', 'Número de beneficiarios en los periodos liquidados', 'Número de beneficiarios', @idPlantillaComunicadoDos, '', 'VARIABLE',''),
				('${numeroIdentificacionAdmin}', 'Número de identificación del admin del subsidio que realizó la solicitud', 'Número identificación del admin del subsidio', @idPlantillaComunicadoDos, '', 'VARIABLE',''),
				('${periodosLiquidados}', 'Periodos presentes en la liquidación', 'Periodos liquidados', @idPlantillaComunicadoDos, '', 'VARIABLE',''),
				('${reporteDeBeneficiarios}', 'Lista de beneficiarios en los periodos liquidadosn', 'Reporte de beneficiarios', @idPlantillaComunicadoDos, '', 'VARIABLE',''),
				('${responsableCcf}', 'Nombre del responsable del envío del comunicado en la caja', 'Responsable CCF', @idPlantillaComunicadoDos, 'RESPONSABLE_CCF', 'CONSTANTE',''),
				('${telefono}', 'Teléfono fijo o Celular capturado en Información de ubicación y correspondencia (uno de los dos)', 'Teléfono', @idPlantillaComunicadoDos, '', 'VARIABLE',''),
				('${tipoIdentificacionAdmin}', 'Tipo de identificación del administrador del subsidio que realizó la solicitud', 'Tipo Identificacion Administrador Del Subsidio', @idPlantillaComunicadoDos, '', 'VARIABLE',''),
				('${logoDeLaCcf}', 'Logotipo de la Caja de Compensación', 'Logo de la CCF', @idPlantillaComunicadoDos, 'LOGO_DE_LA_CCF', 'CONSTANTE',''),
				('${ciudadCcf}', 'Ciudad de Ubicación de la Caja de Compensación', 'Ciudad CCF', @idPlantillaComunicadoDos, 'CIUDAD_CCF', 'CONSTANTE',''),
				('${ciudadCcf}', 'Ciudad de Ubicación de la Caja de Compensación', 'Ciudad CCF', @idPlantillaComunicadoDos, 'CIUDAD_CCF', 'CONSTANTE',''),
				('${AvisoPrescripcionCcf}', 'Días parametrizados restantes a la prescripción del subsidio monetario', 'Aviso prescripción', @idPlantillaComunicadoDos, '', 'VARIABLE','')
	end	


DECLARE @idPlantillaComunicadoTres SMALLINT = (select pcoId FROM PlantillaComunicado WHERE pcoAsunto = 'NOTIFICACION TERCER AVISO PRESCRIPCION SUBSIDIO MONETARIO ADMINISTRADOR SUBSIDIO')
if not exists (select * from VariableComunicado  where vcoPlantillaComunicado = @idPlantillaComunicadoTres)
	begin
	
		INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado, vcoOrden)
		VALUES  ('${ciudadSolicitud}', 'Ciudad de la sede donde se realiza la solicitud', 'Ciudad solicitud', @idPlantillaComunicadoTres, 'CIUDAD_CCF', 'CONSTANTE',''),
				('${contenido}', 'Texto que se adiciona al comunicado por parte del usuario', 'Contenido', @idPlantillaComunicadoTres, '', 'VARIABLE',''),
				('${departamento}', 'Departamento capturada en Información de ubicación y correspondencia del trabajador', 'Departamento', @idPlantillaComunicadoTres, '', 'VARIABLE',''),
				('${direccion}', 'Dirección capturada en Información de ubicación y correspondencia del empleador', 'Dirección', @idPlantillaComunicadoTres, '', 'VARIABLE',''),
				('${fechaDelSistema}', 'dd/mm/aaaa proporcionado por el sistema al generar el comunicado', 'Fecha del sistema', @idPlantillaComunicadoTres, '', 'VARIABLE',''),
				('${firmaResponsableCcf}', 'Imagen de la firma del responsable del envío del comunicado en la caja', 'Firma Responsable CCF', @idPlantillaComunicadoTres, 'FIRMA_RESPONSABLE_CCF', 'CONSTANTE',''),
				('${montoPrescrito}', 'Monto a preescribir', 'Monto a preescribir', @idPlantillaComunicadoTres, '', 'VARIABLE',''),
				('${municipio}', 'Municipio asociado a la direccion de la persona', 'Municipio', @idPlantillaComunicadoTres, '', 'VARIABLE',''),
				('${nombreDelAdministradorDelSubsidio}', 'Nombre del administrador del administrador del subsidio', 'Nombre del administrador del subsidio', @idPlantillaComunicadoTres, '', 'VARIABLE',''),
				('${numeroDeBeneficiarios}', 'Número de beneficiarios en los periodos liquidados', 'Número de beneficiarios', @idPlantillaComunicadoTres, '', 'VARIABLE',''),
				('${numeroIdentificacionAdmin}', 'Número de identificación del admin del subsidio que realizó la solicitud', 'Número identificación del admin del subsidio', @idPlantillaComunicadoTres, '', 'VARIABLE',''),
				('${periodosLiquidados}', 'Periodos presentes en la liquidación', 'Periodos liquidados', @idPlantillaComunicadoTres, '', 'VARIABLE',''),
				('${reporteDeBeneficiarios}', 'Lista de beneficiarios en los periodos liquidadosn', 'Reporte de beneficiarios', @idPlantillaComunicadoTres, '', 'VARIABLE',''),
				('${responsableCcf}', 'Nombre del responsable del envío del comunicado en la caja', 'Responsable CCF', @idPlantillaComunicadoTres, 'RESPONSABLE_CCF', 'CONSTANTE',''),
				('${telefono}', 'Teléfono fijo o Celular capturado en Información de ubicación y correspondencia (uno de los dos,'',', 'Teléfono', @idPlantillaComunicadoTres, '', 'VARIABLE',''),
				('${tipoIdentificacionAdmin}', 'Tipo de identificación del administrador del subsidio que realizó la solicitud', 'Tipo Identificacion Administrador Del Subsidio', @idPlantillaComunicadoTres, '', 'VARIABLE',''),
				('${logoDeLaCcf}', 'Logotipo de la Caja de Compensación', 'Logo de la CCF', @idPlantillaComunicadoTres, 'LOGO_DE_LA_CCF', 'CONSTANTE',''),
				('${ciudadCcf}', 'Ciudad de Ubicación de la Caja de Compensación', 'Ciudad CCF', @idPlantillaComunicadoTres, 'CIUDAD_CCF', 'CONSTANTE',''),
				('${ciudadCcf}', 'Ciudad de Ubicación de la Caja de Compensación', 'Ciudad CCF', @idPlantillaComunicadoTres, 'CIUDAD_CCF', 'CONSTANTE',''),
				('${AvisoPrescripcionCcf}', 'Días parametrizados restantes a la prescripción del subsidio monetario', 'Aviso prescripción', @idPlantillaComunicadoTres, '', 'VARIABLE','')
	end	