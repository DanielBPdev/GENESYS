--liquibase formatted sql

--changeset fhoyos:01
--comment: 
INSERT INTO VariableComunicado(
	vcoClave,
	vcoDescripcion,
	vcoNombre,
	vcoPlantillaComunicado,
	vcoTipoVariableComunicado,
	vcoOrden
)
VALUES
('${direccionUbiPrincipal}', 'Dirección asociada a la ubicación principal del empleador', 'Dirección Ubicación Principal Empleador', 56, 'VARIABLE',0),
('${codigoPostalUbiPrincipal}', 'Código Postal asociado a la ubicación principal del empleador', 'Código Postal Ubicación Principal Empleador', 56, 'VARIABLE',0),
('${emailUbiPrincipal}', 'Email asociado a la ubicación principal del empleador', 'Email Ubicación Principal Empleador', 56, 'VARIABLE',0),
('${indicativoUbiPrincipal}', 'Indicativo teléfono fijo asociado a la ubicación principal del empleador', 'Indicativo Teléfono Fijo Ubicación Principal Empleador', 56, 'VARIABLE',0),
('${telefonoCelularUbiPrincipal}', 'Teléfono celular asociado a la ubicación principal del empleador', 'Teléfono Celular Ubicación Principal Empleador', 56, 'VARIABLE',0),
('${telefonoFijoUbiPrincipal}', 'Indicativo teléfono fijo asociado a la ubicación principal del empleador', 'Teléfono Fijo Ubicación Principal Empleador', 56, 'VARIABLE',0),
('${municipioUbiPrincipal}', 'Municipio asociado a la ubicación principal del empleador', 'Municipio Ubicación Principal Empleador', 56, 'VARIABLE',0),
('${descripcionIndicacionUbiPrincipal}', 'Descripción indicación asociada a la ubicación principal del empleador', 'Descripción Indicación Ubicación Principal Empleador', 56, 'VARIABLE',0);