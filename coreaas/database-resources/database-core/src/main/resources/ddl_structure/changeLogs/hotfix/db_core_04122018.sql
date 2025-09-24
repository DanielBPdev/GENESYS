--liquibase formatted sql

--changeset abaquero:01
--comment: Adici�n de configuraci�n de validaci�n de archivo de aporte manual para pensionados
insert into ValidatorCatalog values 
(12121, 'com.asopagos.aportes.validator.PagoManualAportePensionadoLineValidator', 'validador de l�nea de pago aportes Manuales pensionados', 'Validador l�nea carga aportes manuales pensionados', 'LINE', NULL)

insert into ValidatorDefinition values (12121, 1, 0, 1, NULL, NULL, 12121, 12121, NULL, NULL)

--changeset abaquero:02
--comment: Adici�n de Constante para la definici�n de lectura de archivo de cotizantes para aporte manual de pensionados
insert into Constante (cnsNombre, cnsValor, cnsDescripcion)
values ('FILE_DEFINITION_ID_PAGO_MANUAL_APORTES_PEN', '12121', 'Identificador de definici�n de archivos del componente FileProcessing para Archivo Solicitud de pago manual�de aportes pensioados')

--changeset fvasquez:03
--comment: 
INSERT diasfestivos(pifconcepto,piffecha) VALUES('D�a de los Reyes Magos','2019-01-07');

--changeset clmarin:04
--comment: 
DELETE FROM PrioridadDestinatario WHERE prdDestinatarioComunicado in 
(select dcoid from destinatariocomunicado where dcoEtiquetaPlantilla='RCHZ_AFL_PER_POR_PROD_NSUBLE' 
and dcoProceso='AFILIACION_PERSONAS_PRESENCIAL');

INSERT INTO GrupoPrioridad(gprNombre) VALUES('USUARIO_FRONT');

INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
VALUES((select dcoid from destinatariocomunicado where dcoEtiquetaPlantilla='RCHZ_AFL_PER_POR_PROD_NSUBLE' 
and dcoProceso='AFILIACION_PERSONAS_PRESENCIAL'), (select gprid from grupoprioridad where gprNombre='USUARIO_FRONT'), 1);

--changeset clmarin:05
--comment: 
UPDATE PrioridadDestinatario SET prdGrupoPrioridad=(select gprid from grupoprioridad where gprNombre='AFILIADO_PRINCIPAL')
WHERE prdDestinatarioComunicado = (select dcoid from destinatariocomunicado 
where dcoEtiquetaPlantilla='NTF_INT_AFL_PNS' and dcoProceso='AFILIACION_PERSONAS_PRESENCIAL')
and prdPrioridad=1;
UPDATE PrioridadDestinatario SET prdGrupoPrioridad=(select gprid from grupoprioridad where gprNombre='AFILIADO_PRINCIPAL')
WHERE prdDestinatarioComunicado = (select dcoid from destinatariocomunicado 
where dcoEtiquetaPlantilla='RCHZ_AFL_PER_INC_VAL' and dcoProceso='AFILIACION_PERSONAS_PRESENCIAL')
and prdPrioridad=1;	
UPDATE PrioridadDestinatario SET prdGrupoPrioridad=(select gprid from grupoprioridad where gprNombre='AFILIADO_PRINCIPAL')
WHERE prdDestinatarioComunicado = (select dcoid from destinatariocomunicado 
where dcoEtiquetaPlantilla='RCHZ_AFL_PER_POR_PROD_NSUB' and dcoProceso='AFILIACION_PERSONAS_PRESENCIAL')
and prdPrioridad=1;