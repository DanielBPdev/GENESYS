DECLARE @idPlantillaComunicado SMALLINT = (select pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'NOTI_IN_RE_APORTE')
if @idPlantillaComunicado is not NULL
begin
	DECLARE @idVarcargoResponsableCcf SMALLINT = (select vcoId from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave = '${cargoResponsableCcf}')
	DECLARE @idVarcargoResponsableCarteraCcf SMALLINT = (select vcoId from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave = '${cargoResponsableCarteraCcf}')
	DECLARE @idVarresponsableCcff SMALLINT = (select vcoId from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave = '${responsableCcf}')
	DECLARE @idVarresponsableCarteraCcf SMALLINT = (select vcoId from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave = '${responsableCarteraCcf}')

	update VariableComunicado set vcoTipoVariableComunicado = 'VARIABLE'
	where vcoId in (@idVarcargoResponsableCcf, @idVarcargoResponsableCarteraCcf, @idVarresponsableCcff, @idVarresponsableCarteraCcf) -- ${cargoResponsableCcf} ${cargoResponsableCarteraCcf} ${responsableCcf} ${responsableCarteraCcf}
	and vcoPlantillaComunicado = @idPlantillaComunicado--CARTERA_NOTIFICACION_INCONSISITENCIAS_RECAUDO_APORTE

	DECLARE @idVarnombreYApellidosRepresentanteLegal SMALLINT = (select vcoId from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave = '${nombreYApellidosRepresentanteLegal}}')
	DECLARE @idVarcontenido SMALLINT = (select vcoId from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave = '${contenido}}')
	DECLARE @idVartipoDeInconsistenca SMALLINT = (select vcoId from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave = '${tipoDeInconsistenca)')
	DECLARE @idVarperiodos SMALLINT = (select vcoId from VariableComunicado where vcoPlantillaComunicado = @idPlantillaComunicado and vcoClave = '${periodos}')

	update VariableComunicado set vcoClave = '${nombreYApellidosRepresentanteLegal}'
	where vcoId = @idVarnombreYApellidosRepresentanteLegal -- ${nombreYApellidosRepresentanteLegal}}
	and vcoPlantillaComunicado = @idPlantillaComunicado--CARTERA_NOTIFICACION_INCONSISITENCIAS_RECAUDO_APORTE

	update VariableComunicado set vcoClave = '${contenido}'
	where vcoId = @idVarcontenido -- ${contenido}}
	and vcoPlantillaComunicado = @idPlantillaComunicado--CARTERA_NOTIFICACION_INCONSISITENCIAS_RECAUDO_APORTE

	update VariableComunicado set vcoClave = '${tipoDeInconsistencia}'
	where vcoId = @idVartipoDeInconsistenca -- ${tipoDeInconsistenca)
	and vcoPlantillaComunicado = @idPlantillaComunicado--CARTERA_NOTIFICACION_INCONSISITENCIAS_RECAUDO_APORTE

	update VariableComunicado set vcoTipoVariableComunicado = 'REPORTE_VARIABLE'
	where vcoId = @idVarperiodos -- ${periodos}
	and vcoPlantillaComunicado = @idPlantillaComunicado--CARTERA_NOTIFICACION_INCONSISITENCIAS_RECAUDO_APORTE

	insert into VariableComunicado (vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoTipoVariableComunicado)
	values
	('${ciudadCCF}', 'Ciudad de la caja de la caja de compensación', 'Ciudad de la caja de la caja de compensación', @idPlantillaComunicado, 'VARIABLE'),
	('${valorCiudadCCF}', 'Valor Ciudad de la caja de la caja de compensación', 'Valor Ciudad de la caja de la caja de compensación', @idPlantillaComunicado, 'VARIABLE'),
	('${logoDeLaCCF}', 'Logo de la caja de la caja de compensación', 'Logo de la caja de la caja de compensación', @idPlantillaComunicado, 'VARIABLE'),
	('${valorLogoDeLaCCF}', 'Valor Logo de la caja de la caja de compensación', 'Valor Logo de la caja de la caja de compensación', @idPlantillaComunicado, 'VARIABLE'),
	('${valorCargoResponsableCcf}', 'Valor Cargo del responsable del envío del comunicado en la caja', 'Valor Cargo responsable CCF', @idPlantillaComunicado, 'VARIABLE'),
	('${valorCargoResponsableCarteraCcf}', 'Valor Cargo responsable del proceso de cartera', 'Valor Cargo responsable del proceso de cartera', @idPlantillaComunicado, 'VARIABLE'),
	('${valorCiudad}', 'Valor Ciudad donde se encuentra el representante legal', 'Valor Ciudad Representante Legal', @idPlantillaComunicado, 'VARIABLE'),
	('${valorContenido}', 'Valor En este lugar se remplazará el contenido que se agrega al editar el comunicado.', 'Valor Contenido edición comunicado', @idPlantillaComunicado, 'VARIABLE'),
	('${valorDireccion}', 'Valor Dirección de la empresa relacionada al representante legal', 'Valor Dirección del Representante Legal', @idPlantillaComunicado, 'VARIABLE'),
	('${valorFechaCorte}', 'Valor Fecha de corte para evaluar periodo en mora', 'Valor Fecha Corte', @idPlantillaComunicado, 'VARIABLE'),
	('${valorFechaDelSistema}', 'Valor dd/mm/aaaa proporcionado por el sistema al generar el comunicado', 'Valor Fecha del sistema', @idPlantillaComunicado, 'VARIABLE'),
	('${valorFirmaResponsableProcesoCartera}', 'Valor Firma del funcionario responsable del proceso de cartera', 'Valor Firma del responsable de proceso de cartera', @idPlantillaComunicado, 'VARIABLE'),
	('${valorFirmaResponsableCcf}', 'Valor Imagen de la firma del responsable del envío del comunicado en la caja', 'Valor Firma Responsable CCF', @idPlantillaComunicado, 'VARIABLE'),
	('${valorNombreYApellidosRepresentanteLegal}', 'Valor Nombres y Apellidos del representante legal de la empresa', 'Valor Nombre y Apellidos Representante Legal', @idPlantillaComunicado, 'VARIABLE'),
	('${valorNumeroIdentificacionCotizante}', 'Valor Número de identifiación del Cotizante', 'Valor Número de identificación del cotizante', @idPlantillaComunicado, 'VARIABLE'),
	('${valorNumeroIdentificacionEmpleador}', 'Valor Número de identifiación del empleador', 'Valor Número de identificación del empleador', @idPlantillaComunicado, 'VARIABLE'),
	('${valorNumeroIdentificacionRepresentanteLegal}', 'Valor Número de identificación del representante legal', 'Valor Número de identificación del representante legal', @idPlantillaComunicado, 'VARIABLE'),
	('${valorPeriodos}', 'Valor Periodo inconsistente en formato AAAA-MM', 'Valor Periodos', @idPlantillaComunicado, 'VARIABLE'),
	('${valorResponsableCcf}', 'Valor responsable caja de compensación', 'Valor Responsable CCF', @idPlantillaComunicado, 'VARIABLE'),
	('${valorResponsableCarteraCcf}', 'Valor Responsable del proceso de cartera', 'Valor Responsable del proceso de cartera', @idPlantillaComunicado, 'VARIABLE'),
	('${valorTelefonoAfiliado}', 'Valor Teléfono fijo o Celular capturado en Información de ubicación y correspondencia (uno de los dos)', 'Valor Teléfono del afiliado', @idPlantillaComunicado, 'VARIABLE'),
	('${valorTelefonoEmpleador}', 'Valor Teléfono fijo o Celular capturado en Información de ubicación y correspondencia (uno de los dos)', 'Valor Teléfono del empleador', @idPlantillaComunicado, 'VARIABLE'),
	('${valorTipoIdentificacionCotizante}', 'Valor Tipo de identificación del cotizante', 'Valor Tipo de identificacion del cotizante', @idPlantillaComunicado, 'VARIABLE'),
	('${valorTipoIdentificacionEmpleador}', 'Valor Tipo de identificación del empleador', 'Valor Tipo de identificación del empleador', @idPlantillaComunicado, 'VARIABLE'),
	('${valorTipoIdentificacionRepresentanteLegal}', 'Valor Tipo de identificación del representante legal', 'Valor Tipo de identificación representante legal', @idPlantillaComunicado, 'VARIABLE'),
	('${valorTipoDeInconsistenca}', 'Valor IBC, Número de dias, % de tarifa, Estado del cotizante', 'Valor Tipo de inconsistencia ', @idPlantillaComunicado, 'VARIABLE'),
	('${valorRazonSocial/Nombre}', 'Valor Razón social o Nombres y Apellidos del empleador, independiente o pensionado', 'Valor Razón social/Nombre', @idPlantillaComunicado, 'VARIABLE'),
	--
	('${deudaPresunta}', 'Deuda presunta ', 'Deuda Presunta', @idPlantillaComunicado, 'VARIABLE')
	
end
