--liquibase formatted sql

--changeset jocorrea:01
--comment: Actualizaciones tabla ValidacionProceso
UPDATE ValidacionProceso
SET vapValidacion = 'VALIDACION_PERSONA_MENOR_JEFE'
WHERE vapProceso IN ('POSTULACION_FOVIS_PRESENCIAL','POSTULACION_FOVIS_WEB', 'NOVEDADES_FOVIS_ESPECIAL', 'NOVEDADES_FOVIS_REGULAR')
AND vapValidacion = 'VALIDACION_PERSONA_MENOR_AFILIADO';


UPDATE ValidacionProceso
SET vapValidacion = 'VALIDACION_PERSONA_MAYOR_JEFE'
WHERE vapProceso IN ('POSTULACION_FOVIS_PRESENCIAL','POSTULACION_FOVIS_WEB', 'NOVEDADES_FOVIS_ESPECIAL', 'NOVEDADES_FOVIS_REGULAR')
AND vapValidacion = 'VALIDACION_PERSONA_MAYOR_AFILIADO';

--changeset jvelandia:02
--comment: Actualizaciones tabla ValidacionProceso
DELETE FROM PrioridadDestinatario WHERE prdDestinatarioComunicado in (SELECT dcoId from destinatarioComunicado WHERE dcoEtiquetaPlantilla='NTF_SBC_AFL_IDPE');
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_INDEPENDIENTE_WEB' AND des.dcoEtiquetaPlantilla='NTF_SBC_AFL_IDPE'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='AFILIADO_PRINCIPAL'), 1);

--changeset jocampo:03
--comment: Actualizaciones tabla ValidacionProceso
update PlantillaComunicado 
set pcoMensaje = 'Mensaje<br /> <p>${ciudadSolicitud}</p><br /> <p>${fechaDelSistema}</p><br /> <p>${razonSocial/Nombre}</p><br /> <p>${tipoIdentificacionEmpleador}</p><br /> <p>${numeroIdentificacionEmpleador}</p><br /> <p>${direccion}</p><br /> <p>${municipio}</p><br /> <p>${departamento}</p><br /> <p>${telefono}</p><br /> <p>${periodosLiquidados}</p><br /> <p>${montoLiquidado}</p><br />  <p>${numeroDeTrabajadores}</p><br /> <p>${nombreCcf}</p><br /> <p>${logoDeLaCcf}</p><br /> <p>${departamentoCcf}</p><br /> <p>${ciudadCcf}</p><br /> <p>${direccionCcf}</p><br /> <p>${telefonoCcf}</p><br /> <p>${webCcf}</p><br /> <p>${logoSuperservicios}</p><br /> <p>${firmaResponsableCcf}</p><br /> <p>${responsableCcf}</p><br /> <p>${cargoResponsableCcf}</p><br />',
pcoCuerpo = 'Cuerpo<br /> <p>${ciudadSolicitud}</p><br /> <p>${fechaDelSistema}</p><br /> <p>${razonSocial/Nombre}</p><br /> <p>${tipoIdentificacionEmpleador}</p><br /> <p>${numeroIdentificacionEmpleador}</p><br /> <p>${direccion}</p><br /> <p>${municipio}</p><br /> <p>${departamento}</p><br /> <p>${telefono}</p><br /> <p>${periodosLiquidados}</p><br /> <p>${montoLiquidado}</p><br />  <p>${numeroDeTrabajadores}</p><br /> <p>${nombreCcf}</p><br /> <p>${logoDeLaCcf}</p><br /> <p>${departamentoCcf}</p><br /> <p>${ciudadCcf}</p><br /> <p>${direccionCcf}</p><br /> <p>${telefonoCcf}</p><br /> <p>${webCcf}</p><br /> <p>${logoSuperservicios}</p><br /> <p>${firmaResponsableCcf}</p><br /> <p>${responsableCcf}</p><br /> <p>${cargoResponsableCcf}</p><br />'  
where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP';

update VariableComunicado set vcoTipoVariableComunicado = 'VARIABLE' where vcoClave = '${nombreDelAdministradorDelSubsidio}' and vcoPlantillaComunicado = (select pcoId from PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_ADM_SUB');