--liquibase formatted sql

--changeset squintero:01
--comment: Se resuelve mantis #0224059
UPDATE Novedad SET novProceso = 'NOVEDADES_PERSONAS_PRESENCIAL' WHERE novTipoTransaccion = 'CAMBIO_ESTADO_CIVIL_DEPENDIENTE_PRESENCIAL';
UPDATE Novedad SET novProceso = 'NOVEDADES_PERSONAS_WEB' WHERE novTipoTransaccion = 'CAMBIO_ESTADO_CIVIL_DEPENDIENTE_WEB';
UPDATE Novedad SET novProceso = 'NOVEDADES_PERSONAS_PRESENCIAL' WHERE novTipoTransaccion = 'CAMBIO_ESTADO_CIVIL_INDEPENDIENTE_PRESENCIAL';
UPDATE Novedad SET novProceso = 'NOVEDADES_PERSONAS_WEB' WHERE novTipoTransaccion = 'CAMBIO_ESTADO_CIVIL_INDEPENDIENTE_WEB';
UPDATE Novedad SET novProceso = 'NOVEDADES_PERSONAS_PRESENCIAL' WHERE novTipoTransaccion = 'CAMBIO_ESTADO_CIVIL_25_ANIOS_PRESENCIAL';
UPDATE Novedad SET novProceso = 'NOVEDADES_PERSONAS_WEB' WHERE novTipoTransaccion = 'CAMBIO_ESTADO_CIVIL_25_ANIOS_WEB';
UPDATE Novedad SET novProceso = 'NOVEDADES_PERSONAS_PRESENCIAL' WHERE novTipoTransaccion = 'CAMBIO_ESTADO_CIVIL_MAS_1_5SM_0_6_PRESENCIAL';
UPDATE Novedad SET novProceso = 'NOVEDADES_PERSONAS_WEB' WHERE novTipoTransaccion = 'CAMBIO_ESTADO_CIVIL_MAS_1_5SM_0_6_WEB';
UPDATE Novedad SET novProceso = 'NOVEDADES_PERSONAS_PRESENCIAL' WHERE novTipoTransaccion = 'CAMBIO_ESTADO_CIVIL_MAS_1_5SM_0_2_PRESENCIAL';
UPDATE Novedad SET novProceso = 'NOVEDADES_PERSONAS_WEB' WHERE novTipoTransaccion = 'CAMBIO_ESTADO_CIVIL_MAS_1_5SM_0_2_WEB';
UPDATE Novedad SET novProceso = 'NOVEDADES_PERSONAS_PRESENCIAL' WHERE novTipoTransaccion = 'CAMBIO_ESTADO_CIVIL_MENOS_1_5SM_0_PRESENCIAL';
UPDATE Novedad SET novProceso = 'NOVEDADES_PERSONAS_WEB' WHERE novTipoTransaccion = 'CAMBIO_ESTADO_CIVIL_MENOS_1_5SM_0_WEB';
UPDATE Novedad SET novProceso = 'NOVEDADES_PERSONAS_PRESENCIAL' WHERE novTipoTransaccion = 'CAMBIO_ESTADO_CIVIL_MENOS_1_5SM_0_6_PRESENCIAL';
UPDATE Novedad SET novProceso = 'NOVEDADES_PERSONAS_WEB' WHERE novTipoTransaccion = 'CAMBIO_ESTADO_CIVIL_MENOS_1_5SM_0_6_WEB';
UPDATE Novedad SET novProceso = 'NOVEDADES_PERSONAS_PRESENCIAL' WHERE novTipoTransaccion = 'CAMBIO_ESTADO_CIVIL_MENOS_1_5SM_2_PRESENCIAL';
UPDATE Novedad SET novProceso = 'NOVEDADES_PERSONAS_WEB' WHERE novTipoTransaccion = 'CAMBIO_ESTADO_CIVIL_MENOS_1_5SM_2_WEB';
UPDATE Novedad SET novProceso = 'NOVEDADES_PERSONAS_PRESENCIAL' WHERE novTipoTransaccion = 'CAMBIO_ESTADO_CIVIL_PENSION_FAMILIAR_PRESENCIAL';
UPDATE Novedad SET novProceso = 'NOVEDADES_PERSONAS_WEB' WHERE novTipoTransaccion = 'CAMBIO_ESTADO_CIVIL_PENSION_FAMILIAR_WEB';
UPDATE Novedad SET novProceso = 'NOVEDADES_PERSONAS_PRESENCIAL' WHERE novTipoTransaccion = 'CAMBIO_ESTADO_CIVIL_CONYUGE_PRESENCIAL';
UPDATE Novedad SET novProceso = 'NOVEDADES_PERSONAS_WEB' WHERE novTipoTransaccion = 'CAMBIO_ESTADO_CIVIL_CONYUGE_WEB';

--changeset atoro:02
--comment: Se resuelve mantis #0224283
INSERT RequisitoCajaClasificacion (rtsEstado,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsRequisito,rtsTextoAyuda) VALUES ('OBLIGATORIO','CONYUGE','CAMBIO_TIPO_NUMERO_DOCUMENTO_BENEFICIARIO_CONYUGE',1,(SELECT reqId FROM Requisito WHERE reqDescripcion='Copia del documento de identidad'),'Tarjeta de identidad para menores de 18 años de edad,cédula de ciudadanía o cédula de extranjería para mayores de 18 años de edad. Se revisa: -No debe tener tachones o enmendaduras -El tipo y número de documento de identidad deben coincidir con el registrado en el formulario de afiliación -Los nombres y apellidos deben corresponder a los registrados en el formulario de afiliación -La fecha de nacimiento debe corresponder a la registrada en el formulario de afiliación');


