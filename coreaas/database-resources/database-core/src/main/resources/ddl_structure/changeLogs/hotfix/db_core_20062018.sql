--liquibase formatted sql

--changeset jocorrea:01
--comment: Plantillas etiqueta COM_SUB_DIS_FAL_PRO_TRA
INSERT DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) VALUES('ASIGNACION_FOVIS', 'CRT_ASIG_FOVIS'),('ASIGNACION_FOVIS', 'ACT_ASIG_FOVIS');

--changeset jocorrea:02
--comment: Plantillas etiqueta COM_SUB_DIS_FAL_PRO_TRA
UPDATE Solicitud SET solCanalRecepcion = 'PRESENCIAL', solTipoTransaccion = 'ASIGNACION_SUBSIDIO_FOVIS' WHERE solId IN (SELECT safSolicitudGlobal FROM SolicitudAsignacion);

--changeset jocorrea:03
--comment: Actualizacion tabla ValidacionProceso
UPDATE ValidacionProceso SET vapInversa = 1 WHERE vapValidacion = 'VALIDACION_BENEFICIARIO_MADRE' AND vapBloque = 'CAMBIO_NOMBRES_APELLIDOS_MADRE';
UPDATE ValidacionProceso SET vapInversa = 1 WHERE vapValidacion = 'VALIDACION_BENEFICIARIO_HIJO_ADOPTIVO' AND vapBloque = 'CAMBIO_NOMBRES_APELLIDOS_ADOPTIVO';
UPDATE ValidacionProceso SET vapInversa = 1 WHERE vapValidacion = 'VALIDACION_BENEFICIARIO_PADRE' AND vapBloque = 'CAMBIO_NOMBRES_APELLIDOS_PADRE';

--changeset jocampo:04
--comment: Actualizacion tabla ValidacionProceso
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo <br /> <p>${ciudadSolicitud}</p><br /> <p>${fechaDelSistema}</p><br /> <p>${nombreDelAdministradorDelSubsidio}</p><br /> <p>${tipoIdentificacionAdmin}</p><br /> <p>${numeroIdentificacionAdmin}</p><br /> <p>${direccion}</p><br /> <p>${municipio}</p><br /> <p>${departamento}</p><br /> <p>${telefono}</p><br /> <p>${periodosLiquidados}</p><br /> <p>${montoLiquidado}</p><br /> <p>${numeroDeBeneficiarios}</p><br /> <p>${reporteDeBeneficiarios}</p> <br /> <p>${nombreCcf}</p><br /> <p>${logoDeLaCcf}</p><br /> <p>${departamentoCcf}</p><br /> <p>${ciudadCcf}</p><br /> <p>${direccionCcf}</p><br /> <p>${telefonoCcf}</p><br /> <p>${webCcf}</p><br /> <p>${logoSuperservicios}</p><br /> <p>${firmaResponsableCcf}</p><br /> <p>${responsableCcf}</p><br /> <p>${cargoResponsableCcf}</p><br /> ',pcoMensaje = 'Mensaje <br /> <p>${ciudadSolicitud}</p><br /> <p>${fechaDelSistema}</p><br /> <p>${nombreDelAdministradorDelSubsidio}</p><br /> <p>${tipoIdentificacionAdmin}</p><br /> <p>${numeroIdentificacionAdmin}</p><br /> <p>${direccion}</p><br /> <p>${municipio}</p><br /> <p>${departamento}</p><br /> <p>${telefono}</p><br /> <p>${periodosLiquidados}</p><br /> <p>${montoLiquidado}</p><br /> <p>${numeroDeBeneficiarios}</p><br /> <p>${reporteDeBeneficiarios}</p> <br /> <p>${nombreCcf}</p><br /> <p>${logoDeLaCcf}</p><br /> <p>${departamentoCcf}</p><br /> <p>${ciudadCcf}</p><br /> <p>${direccionCcf}</p><br /> <p>${telefonoCcf}</p><br /> <p>${webCcf}</p><br /> <p>${logoSuperservicios}</p><br /> <p>${firmaResponsableCcf}</p><br /> <p>${responsableCcf}</p><br /> <p>${cargoResponsableCcf}</p><br /> ' WHERE pcoEtiqueta = 'COM_SUB_DIS_PAG_ADM_SUB';

--changeset fvasquez:05
--comment: Cambio tipo de dato columna dosVersionDocumento
ALTER TABLE DocumentoSoporte ALTER COLUMN dosVersionDocumento varchar(10) NOT NULL;
ALTER TABLE aud.DocumentoSoporte_aud ALTER COLUMN dosVersionDocumento varchar(10) NOT NULL;

