--liquibase formatted sql

--changeset flopez:01 
--comment: Actualizacion en la tabla Novedad HU 035-134-424, 135-448, 136-454
--Novedades Persona
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_TRABAJADOR_DEPENDIENTE_DEPWEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_TRABAJADOR_DEPENDIENTE_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_TRABAJADOR_DEPENDIENTE_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_TRABAJADOR_INDEPENDIENTE_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_TRABAJADOR_INDEPENDIENTE_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_FIDELIDAD_25ANIOS_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_FIDELIDAD_25ANIOS_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_MAYOR_1_5SM_0_6_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_MAYOR_1_5SM_2_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_MENOR_1_5SM_0_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_MENOR_1_5SM_0_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_MENOR_1_5SM_0_6_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_MENOR_1_5SM_2_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_CONYUGE_DEPWEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_CONYUGE_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_CONYUGE_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HIJO_BIOLOGICO_DEPWEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HIJO_BIOLOGICO_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HIJO_BIOLOGICO_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HIJASTRO_DEPWEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HIJASTRO_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HIJASTRO_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HERMANO_HUERFANO_DEPWEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HERMANO_HUERFANO_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HERMANO_HUERFANO_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HIJO_ADOPTIVO_DEPWEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HIJO_ADOPTIVO_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HIJO_ADOPTIVO_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_BENEFICIARIO_CUSTODIA_DEPWEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_BENEFICIARIO_CUSTODIA_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_BENEFICIARIO_CUSTODIA_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PADRE_DEPWEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PADRE_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PADRE_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_MADRE_DEPWEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_MADRE_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_MADRE_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarGrupoFamiliarNovedadPersona' WHERE novTipoTransaccion = 'DESACTIVAR_ACTIVAR_GRUPO_FAMILIAR_INEMBARGABLE_GRUPO_FAMILIAR';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarNovedadPilaPersona' WHERE novTipoTransaccion = 'ACTUALIZACION_VACACIONES_TRABAJADOR_DEPENDIENTE';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarNovedadPilaPersona' WHERE novTipoTransaccion = 'INCAPACIDAD_POR_ACCIDENTE_TRABAJO';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarNovedadPilaPersona' WHERE novTipoTransaccion = 'LICENCIA_NO_REMUNERADA_SUSPENSION_CONTRATO_TRABAJADOR_DEPENDIENTE'
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarNovedadPilaPersona' WHERE novTipoTransaccion = 'REPORTE_LICENCIA_REMUNERADA_TRABAJADOR_DEPENDIENTE'
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarNovedadPilaPersona' WHERE novTipoTransaccion = 'VARIACION_SALARIO_TRABAJADOR_DEPENDIENTE'
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_TRABAJADOR_DEPENDIENTE_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_TRABAJADOR_DEPENDIENTE_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_TRABAJADOR_DEPENDIENTE_DEPWEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_TRABAJADOR_INDEPENDIENTE_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_TRABAJADOR_INDEPENDIENTE_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_FIDELIDAD_25ANIOS_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_FIDELIDAD_25ANIOS_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_MAYOR_1_5SM_0_6_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_MAYOR_1_5SM_2_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_MENOR_1_5SM_0_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_MENOR_1_5SM_0_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_MENOR_1_5SM_0_6_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_MENOR_1_5SM_2_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_PENSION_FAMILIAR_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PENSIONADO_PENSION_FAMILIAR_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_CONYUGE_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_CONYUGE_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_CONYUGE_DEPWEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HIJO_BIOLOGICO_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HIJO_BIOLOGICO_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HIJO_BIOLOGICO_DEPWEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HIJASTRO_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HIJASTRO_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HIJASTRO_DEPWEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HERMANO_HUERFANO_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HERMANO_HUERFANO_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HERMANO_HUERFANO_DEPWEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HIJO_ADOPTIVO_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HIJO_ADOPTIVO_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_HIJO_ADOPTIVO_DEPWEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_BENEFICIARIO_CUSTODIA_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_BENEFICIARIO_CUSTODIA_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_BENEFICIARIO_CUSTODIA_DEPWEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PADRE_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PADRE_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_PADRE_DEPWEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_MADRE_PRESENCIAL';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_MADRE_WEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novTipoTransaccion = 'REPORTE_INVALIDEZ_MADRE_DEPWEB';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarAfiliadoNovedadPersona' WHERE novTipoTransaccion = 'SOLICITUD_RETENCION_SUBSIDIO_TRABAJADOR_DEPENDIENTE';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarAfiliadoNovedadPersona' WHERE novTipoTransaccion = 'ACTIVACION_CESION_SUBSIDIO_TRABAJADOR_DEPENDIENTE';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarAfiliadoNovedadPersona' WHERE novTipoTransaccion = 'DESACTIVACION_CESION_SUBSIDIO_TRABAJADOR_DEPENDIENTE';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarAfiliadoNovedadPersona' WHERE novTipoTransaccion = 'PIGNORACION_DEL_SUBSIDIO_TRABAJADOR_DEPENDIENTE';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarAfiliadoNovedadPersona' WHERE novTipoTransaccion = 'SOLICITUD_SERVICIOS_SIN_AFILIACION_TRABAJADOR_DEPENDIENTE';

--changeset squintero:02
--comment: Actualizacion y borrado en ValidacionProceso
--update para nuevas validaciones
UPDATE ValidacionProceso SET vapValidacion = 'VALIDACION_PERSONA_EMPLEADOR_ACTIVO' WHERE vapValidacion = 'VALIDACION_PERSONA_EMPLEADOR' AND vapBloque NOT LIKE 'NOVEDAD_%' AND vapProceso IN ('NOVEDADES_PERSONAS_PRESENCIAL','NOVEDADES_PERSONAS_WEB','NOVEDADES_DEPENDIENTE_WEB');
UPDATE ValidacionProceso SET vapValidacion = 'VALIDACION_PERSONA_HIJO_ACTIVO' WHERE vapValidacion = 'VALIDACION_HIJO_ACTIVO' AND vapBloque NOT LIKE 'NOVEDAD_%' AND vapProceso IN ('NOVEDADES_PERSONAS_PRESENCIAL','NOVEDADES_PERSONAS_WEB','NOVEDADES_DEPENDIENTE_WEB');
UPDATE ValidacionProceso SET vapValidacion = 'VALIDACION_PERSONA_PADRE_ACTIVO' WHERE vapValidacion = 'VALIDACION_PADRE_ACTIVO' AND vapBloque NOT LIKE 'NOVEDAD_%' AND vapProceso IN ('NOVEDADES_PERSONAS_PRESENCIAL','NOVEDADES_PERSONAS_WEB','NOVEDADES_DEPENDIENTE_WEB');
UPDATE ValidacionProceso SET vapValidacion = 'VALIDACION_PERSONA_EMPLEADOR_ACTIVO' WHERE vapValidacion = 'VALIDACION_CONYUGE_ACTIVO' AND vapBloque NOT LIKE 'NOVEDAD_%' AND vapProceso IN ('NOVEDADES_PERSONAS_PRESENCIAL','NOVEDADES_PERSONAS_WEB','NOVEDADES_DEPENDIENTE_WEB');
UPDATE ValidacionProceso SET vapValidacion = 'VALIDACION_PERSONA_PENSIONADO_ACTIVO' WHERE vapValidacion = 'VALIDACION_PERSONA_PENSIONADO' AND vapBloque NOT LIKE 'NOVEDAD_%' AND vapProceso IN ('NOVEDADES_PERSONAS_PRESENCIAL','NOVEDADES_PERSONAS_WEB','NOVEDADES_DEPENDIENTE_WEB');
UPDATE ValidacionProceso SET vapValidacion = 'VALIDACION_PERSONA_DEPENDIENTE_ACTIVO' WHERE vapValidacion = 'VALIDACION_AFILIADO_TRABAJADOR_DEPENDIENTE' AND vapBloque NOT LIKE 'NOVEDAD_%' AND vapProceso IN ('NOVEDADES_PERSONAS_PRESENCIAL','NOVEDADES_PERSONAS_WEB','NOVEDADES_DEPENDIENTE_WEB');
UPDATE ValidacionProceso SET vapValidacion = 'VALIDACION_PERSONA_INDEPENDIENTE_ACTIVO' WHERE vapValidacion = 'VALIDACION_AFILIADO_TRABAJADOR_INDEPENDIENTE' AND vapBloque NOT LIKE 'NOVEDAD_%' AND vapProceso IN ('NOVEDADES_PERSONAS_PRESENCIAL','NOVEDADES_PERSONAS_WEB','NOVEDADES_DEPENDIENTE_WEB');
--eliminacion de validaciones VALIDACION_REQUIERE_INACTIVAR_CUENTA_WEB y VALIDACION_CUENTA_WEB
DELETE FROM ValidacionProceso WHERE vapValidacion = 'VALIDACION_REQUIERE_INACTIVAR_CUENTA_WEB';
--eliminacion de validacion VALIDACION_CAMBIO_TIPO_DOC_IDENTIDAD en novedades incorrectas y cambio de restantes a RN
DELETE FROM ValidacionProceso WHERE vapValidacion = 'VALIDACION_CAMBIO_TIPO_DOC_IDENTIDAD';

--changeset atoro:03
--comment: Actualizacion en la tabla Novedad
UPDATE Novedad SET novRutaCualificada ='com.asopagos.novedades.convertidores.empleador.DesafiliarEmpleadorAfiliadosNovedad' WHERE novtipotransaccion='DESAFILIACION';

--changeset eamaya:04
--comment: Actualizacion en la tabla empresa
UPDATE Empresa SET empfechaconstitucion='2011-11-16' WHERE empid=42; 

