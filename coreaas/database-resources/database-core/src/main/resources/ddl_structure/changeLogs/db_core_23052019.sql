--liquibase formatted sql

--changeset clmarin:01
--comment: 
DELETE FROM DocumentoBitacora; 
ALTER TABLE DocumentoSoporte ADD dosBitacoraCartera bigint NULL;
ALTER TABLE aud.DocumentoSoporte_aud ADD dosBitacoraCartera bigint NULL;
ALTER TABLE DocumentoSoporte ADD CONSTRAINT FK_DocumentoSoporte_dosBitacoraCartera 
FOREIGN KEY (dosBitacoraCartera) REFERENCES BitacoraCartera(bcaId);

--changeset abaquero:01
--comment: Activación de validadores para ordenamiento de registros de planillas de corrección en FileProcessing
update ValidatorDefinition set state = 1 where id in (2110089, 2110106, 2110109, 2110312, 2110313)

--changeset dsuesca:01
--comment: 
ALTER TABLE EstadoCondicionValidacionSubsidio ADD ecvSinEstado BIT;
--changeset mamonroy:01
--comment:
DELETE FROM ValidacionProceso
WHERE vapProceso = 'NOVEDADES_PERSONAS_PRESENCIAL'
AND vapBloque = 'CONYUGE_LABORA_PRESENCIAL'
AND vapValidacion = 'VALIDACION_PER_ACTIVA_GRUPO_AFILIADO';

DELETE FROM ValidacionProceso
WHERE vapProceso = 'NOVEDADES_PERSONAS_WEB'
AND vapBloque = 'CONYUGE_LABORA_WEB'
AND vapValidacion = 'VALIDACION_PER_ACTIVA_GRUPO_AFILIADO';

DELETE FROM ValidacionProceso
WHERE vapProceso = 'NOVEDADES_DEPENDIENTE_WEB'
AND vapBloque = 'CONYUGE_LABORA_DEPWEB'
AND vapValidacion = 'VALIDACION_PER_ACTIVA_GRUPO_AFILIADO';

DELETE FROM ValidacionProceso
WHERE vapProceso = 'NOVEDADES_PERSONAS_PRESENCIAL'
AND vapBloque = 'VALOR_INGRESO_MENSUAL_CONYUGE_PRESENCIAL'
AND vapValidacion = 'VALIDACION_PER_ACTIVA_GRUPO_AFILIADO';

DELETE FROM ValidacionProceso
WHERE vapProceso = 'NOVEDADES_PERSONAS_WEB'
AND vapBloque = 'VALOR_INGRESO_MENSUAL_CONYUGE_WEB'
AND vapValidacion = 'VALIDACION_PER_ACTIVA_GRUPO_AFILIADO';

DELETE FROM ValidacionProceso
WHERE vapProceso = 'NOVEDADES_DEPENDIENTE_WEB'
AND vapBloque = 'VALOR_INGRESO_MENSUAL_CONYUGE_DEPWEB'
AND vapValidacion = 'VALIDACION_PER_ACTIVA_GRUPO_AFILIADO';

DELETE FROM ValidacionProceso
WHERE vapBloque IN ('ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_BIOLOGICO_PRESENCIAL',
'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_BIOLOGICO_WEB',
'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_BIOLOGICO_DEPWEB',
'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJASTRO_PRESENCIAL',
'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJASTRO_WEB',
'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJASTRO_DEPWEB',
'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HERMANO_HUERFANO_PRESENCIAL',
'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HERMANO_HUERFANO_WEB',
'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HERMANO_HUERFANO_DEPWEB',
'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_ADOTIVO_PRESENCIAL',
'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_ADOTIVO_WEB',
'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_ADOTIVO_DEPWEB',
'ACTUALIZACION_CERTIFICADO_ESTUDIOS_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL',
'ACTUALIZACION_CERTIFICADO_ESTUDIOS_BENEFICIARIO_EN_CUSTODIA_WEB',
'ACTUALIZACION_CERTIFICADO_ESTUDIOS_BENEFICIARIO_EN_CUSTODIA_DEPWEB',
'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_BIOLOGICO_PRESENCIAL',
'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_BIOLOGICO_WEB',
'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_BIOLOGICO_DEPWEB',
'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJASTRO_PRESENCIAL',
'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJASTRO_WEB',
'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJASTRO_DEPWEB',
'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HERMANO_HUERFANO_PRESENCIAL',
'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HERMANO_HUERFANO_WEB',
'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HERMANO_HUERFANO_DEPWEB',
'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_ADOPTIVO_PRESENCIAL',
'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_ADOPTIVO_WEB',
'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_ADOPTIVO_DEPWEB',
'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL',
'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_BENEFICIARIO_EN_CUSTODIA_WEB',
'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_BENEFICIARIO_EN_CUSTODIA_DEPWEB')
AND vapValidacion = 'VALIDACION_PER_ACTIVA_GRUPO_AFILIADO';

--changeset mamonroy:02
--comment:
ALTER TABLE BeneficiarioDetalle ADD bedTipoUnionConyugal VARCHAR(11);
ALTER TABLE aud.BeneficiarioDetalle_aud ADD bedTipoUnionConyugal VARCHAR(11);

--Parametrización de la novedad
INSERT INTO ParametrizacionNovedad (novTipoTransaccion,novPuntoResolucion,novRutaCualificada,novTipoNovedad,novProceso,novAplicaTodosRoles) VALUES ('ACTUALIZACION_TIPO_UNION_CONYUGE_PRESENCIAL','FRONT','com.asopagos.novedades.convertidores.persona.ActualizarBeneficiarioNovedadPersona','BENEFICIARIO','NOVEDADES_PERSONAS_PRESENCIAL',NULL);

INSERT INTO ParametrizacionNovedad (novTipoTransaccion,novPuntoResolucion,novRutaCualificada,novTipoNovedad,novProceso,novAplicaTodosRoles) VALUES ('ACTUALIZACION_TIPO_UNION_CONYUGE_WEB','FRONT','com.asopagos.novedades.convertidores.persona.ActualizarBeneficiarioNovedadPersona','BENEFICIARIO','NOVEDADES_PERSONAS_WEB',NULL);

--Creacion de validaciones de negocio

INSERT INTO ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('NOVEDAD_ACTUALIZACION_TIPO_UNION_CONYUGE_PRESENCIAL',NULL,'NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'CONYUGE',0);

INSERT INTO ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('ACTUALIZACION_TIPO_UNION_CONYUGE_PRESENCIAL','VALIDACION_PERSONA_FALLECIDA','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'CONYUGE',0);

INSERT INTO ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('ACTUALIZACION_TIPO_UNION_CONYUGE_PRESENCIAL','VALIDACION_PERSONA_HIJO_ACTIVO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'CONYUGE',0);

INSERT INTO ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('ACTUALIZACION_TIPO_UNION_CONYUGE_PRESENCIAL','VALIDACION_PERSONA_PADRE_ACTIVO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'CONYUGE',0);

INSERT INTO ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('ACTUALIZACION_TIPO_UNION_CONYUGE_PRESENCIAL','VALIDACION_PERSONA_DEPENDIENTE_ACTIVO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'CONYUGE',0);

INSERT INTO ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('ACTUALIZACION_TIPO_UNION_CONYUGE_PRESENCIAL','VALIDACION_PERSONA_INDEPENDIENTE_ACTIVO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'CONYUGE',0);

INSERT INTO ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('ACTUALIZACION_TIPO_UNION_CONYUGE_PRESENCIAL','VALIDACION_PERSONA_CONYUGE_SOCIO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'CONYUGE',0);

INSERT INTO ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('ACTUALIZACION_TIPO_UNION_CONYUGE_PRESENCIAL','VALIDACION_PERSONA_TIENE_NOVEDAD_PERSONA_EN_CURSO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'CONYUGE',0);

INSERT INTO ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('NOVEDAD_ACTUALIZACION_TIPO_UNION_CONYUGE_WEB',NULL,'NOVEDADES_PERSONAS_WEB','ACTIVO',1,'CONYUGE',0);

INSERT INTO ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('ACTUALIZACION_TIPO_UNION_CONYUGE_WEB','VALIDACION_PERSONA_FALLECIDA','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'CONYUGE',0);

INSERT INTO ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('ACTUALIZACION_TIPO_UNION_CONYUGE_WEB','VALIDACION_PERSONA_HIJO_ACTIVO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'CONYUGE',0);

INSERT INTO ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('ACTUALIZACION_TIPO_UNION_CONYUGE_WEB','VALIDACION_PERSONA_PADRE_ACTIVO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'CONYUGE',0);

INSERT INTO ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('ACTUALIZACION_TIPO_UNION_CONYUGE_WEB','VALIDACION_PERSONA_DEPENDIENTE_ACTIVO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'CONYUGE',0);

INSERT INTO ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('ACTUALIZACION_TIPO_UNION_CONYUGE_WEB','VALIDACION_PERSONA_INDEPENDIENTE_ACTIVO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'CONYUGE',0);

INSERT INTO ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('ACTUALIZACION_TIPO_UNION_CONYUGE_WEB','VALIDACION_PERSONA_CONYUGE_SOCIO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'CONYUGE',0);

INSERT INTO ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('ACTUALIZACION_TIPO_UNION_CONYUGE_WEB','VALIDACION_PERSONA_TIENE_NOVEDAD_PERSONA_EN_CURSO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'CONYUGE',0);

--Parametrizacion requisitos documentales

INSERT INTO RequisitoCajaClasificacion (rtsEstado, rtsRequisito, rtsClasificacion, rtsTipoTransaccion, rtsCajaCompensacion, rtsTextoAyuda, rtsTipoRequisito) VALUES ('OBLIGATORIO', (SELECT reqId FROM Requisito WHERE reqDescripcion = 'Documento evidencia de sociedad conyugal o unión marital de hecho'), 'CONYUGE', 'ACTUALIZACION_TIPO_UNION_CONYUGE_PRESENCIAL', 1, 'Copia del registro civil de matrimonio o declaración juramentada para acreditar la unión libre (formato Ministerio de Trabajo). Se revisa: <br> -No debe tener tachones o enmendaduras <br> -El tipo y número de documento de identidad del afiliado principal y su cónyuge deben coincidir con los registrados en los demás documentos soporte <br> -Firmado por el solicitante (en el caso de declaración juramentada)', 'ESTANDAR');

INSERT INTO RequisitoCajaClasificacion (rtsEstado, rtsRequisito, rtsClasificacion, rtsTipoTransaccion, rtsCajaCompensacion, rtsTextoAyuda, rtsTipoRequisito) VALUES ('OBLIGATORIO', (SELECT reqId FROM Requisito WHERE reqDescripcion = 'Documento evidencia de sociedad conyugal o unión marital de hecho'), 'CONYUGE', 'ACTUALIZACION_TIPO_UNION_CONYUGE_WEB', 1, 'Copia del registro civil de matrimonio o declaración juramentada para acreditar la unión libre (formato Ministerio de Trabajo). Se revisa: <br> -No debe tener tachones o enmendaduras <br> -El tipo y número de documento de identidad del afiliado principal y su cónyuge deben coincidir con los registrados en los demás documentos soporte <br> -Firmado por el solicitante (en el caso de declaración juramentada)', 'ESTANDAR');
