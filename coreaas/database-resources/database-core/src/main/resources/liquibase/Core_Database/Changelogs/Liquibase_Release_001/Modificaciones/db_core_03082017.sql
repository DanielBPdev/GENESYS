--liquibase formatted sql

--changeset jusanchez:01
--comment: Se actualiza registros de la tabla FieldDefinitionLoad
UPDATE FieldDefinitionLoad SET label='Secuencia.' WHERE id=100000;
UPDATE FieldDefinitionLoad SET label='Tipo de registro.' WHERE id=100001;
UPDATE FieldDefinitionLoad SET label='Tipo identificación del cotizante.' WHERE id=100002;
UPDATE FieldDefinitionLoad SET label='No. de identificación del cotizante.' WHERE id=100003;
UPDATE FieldDefinitionLoad SET label='Tipo de cotizante' WHERE id=100004;
UPDATE FieldDefinitionLoad SET label='Subtipo de cotizante' WHERE id=100005;
UPDATE FieldDefinitionLoad SET label='Extranjero no obligado a cotizar a pensiones.' WHERE id=100006;
UPDATE FieldDefinitionLoad SET label='Colombiano en el exterior.' WHERE id=100007;
UPDATE FieldDefinitionLoad SET label='Código del Departamento de la ubicación laboral.' WHERE id=100008;
UPDATE FieldDefinitionLoad SET label='Código del Municipio de la ubicación laboral.' WHERE id=100009;
UPDATE FieldDefinitionLoad SET label='Primer apellido.' WHERE id=100010;
UPDATE FieldDefinitionLoad SET label='Segundo apellido.' WHERE id=100011;
UPDATE FieldDefinitionLoad SET label='Primer nombre.' WHERE id=100012;
UPDATE FieldDefinitionLoad SET label='Segundo nombre.' WHERE id=100013;
UPDATE FieldDefinitionLoad SET label='ING: Ingreso.' WHERE id=100014;
UPDATE FieldDefinitionLoad SET label='RET: Retiro.' WHERE id=100015;
UPDATE FieldDefinitionLoad SET label='VSP: Variación permanente de salario.' WHERE id=100016;
UPDATE FieldDefinitionLoad SET label='VST: Variación transitoria del salario.' WHERE id=100017;
UPDATE FieldDefinitionLoad SET label='SLN: Suspensión temporal del contrato,licencia no remunerada' WHERE id=100018;
UPDATE FieldDefinitionLoad SET label='IGE: Incapacidad Temporal por Enfermedad General.' WHERE id=100019;
UPDATE FieldDefinitionLoad SET label='LMA: Licencia de Maternidad o paternidad.' WHERE id=100020;
UPDATE FieldDefinitionLoad SET label='VAC - LR: Vacaciones,Licencia remunerada' WHERE id=100021;
UPDATE FieldDefinitionLoad SET label='IRL: días de incapacidad por accidente de trabajo o enfermedad laboral' WHERE id=100022;
UPDATE FieldDefinitionLoad SET label='Días cotizados.' WHERE id=100023;
UPDATE FieldDefinitionLoad SET label='Salario básico.' WHERE id=100024;
UPDATE FieldDefinitionLoad SET label='Ingreso Base Cotización (IBC)' WHERE id=100025;
UPDATE FieldDefinitionLoad SET label='Tarifa.' WHERE id=100026;
UPDATE FieldDefinitionLoad SET label='Aporte obligatorio.' WHERE id=100027;
UPDATE FieldDefinitionLoad SET label='Correcciones' WHERE id=100028;
UPDATE FieldDefinitionLoad SET label='Salario Integral' WHERE id=100029;
UPDATE FieldDefinitionLoad SET label='Fecha de ingreso formato (AAAA-MM-DD)' WHERE id=100030;
UPDATE FieldDefinitionLoad SET label='Fecha de retiro formato (AAAA-MM-DD)' WHERE id=100031;
UPDATE FieldDefinitionLoad SET label='Fecha inicio VSP formato (AAAA-MM-DD)' WHERE id=100032;
UPDATE FieldDefinitionLoad SET label='Fecha inicio SLN formato (AAAA-MM-DD)' WHERE id=100033;
UPDATE FieldDefinitionLoad SET label='Fecha fin SLN formato (AAAA-MM-DD)' WHERE id=100034;
UPDATE FieldDefinitionLoad SET label='Fecha inicio IGE formato (AAAA-MM-DD)' WHERE id=100035;
UPDATE FieldDefinitionLoad SET label='Fecha fin IGE formato (AAAA-MM-DD)' WHERE id=100036;
UPDATE FieldDefinitionLoad SET label='Fecha inicio LMA formato (AAAA-MM-DD)' WHERE id=100037;
UPDATE FieldDefinitionLoad SET label='Fecha fin LMA formato (AAAA-MM-DD)' WHERE id=100038;
UPDATE FieldDefinitionLoad SET label='Fecha inicio VAC - LR formato (AAAA-MM-DD)' WHERE id=100039;
UPDATE FieldDefinitionLoad SET label='Fecha fin VAC - LR formato (AAAA-MM-DD)' WHERE id=100040;
UPDATE FieldDefinitionLoad SET label='Fecha inicio VCT formato (AAAA-MM-DD)' WHERE id=100041;
UPDATE FieldDefinitionLoad SET label='Fecha fin VCT formato (AAAA-MM-DD)' WHERE id=100042;
UPDATE FieldDefinitionLoad SET label='Fecha inicio IRL formato (AAAA-MM-DD)' WHERE id=100043;
UPDATE FieldDefinitionLoad SET label='Fecha fin IRL formato (AAAA-MM-DD)' WHERE id=100044;
UPDATE FieldDefinitionLoad SET label='Número de horas laboradas' WHERE id=100045;

--changeset arocha:02 
--comment: Se crea la tabla pila.AporteEmpresa
CREATE TABLE pila.AporteEmpresa(
       apeTipoIdentificacion varchar(20) NOT NULL,
       apeNumeroIdentificacion varchar(16) NOT NULL,
       apePeriodoAporte date NOT NULL,
       apeTransaccion bigint NOT NULL
);

--changeset clmarin:03 
--comment: Se actualiza registros en la tabla PlantillaComunicado
UPDATE PlantillaComunicado SET pcoMensaje='Aviso desbloqueo cuenta de usuario <br /> <p>Señor(a) <br />${nombreUsuario} <br />Su cuenta ha sido desbloqueada por favor ingrese con usuario: ${usuario}  y contraseña:${password}</p>' WHERE pcoEtiqueta='NTF_BLQ_CTA_USR';

--changeset flopez:04 
--comment: Se eliminan registros de la tabla ValidacionProceso
DELETE FROM ValidacionProceso WHERE vapValidacion = 'VALIDACION_PERSONA_INCLUIDA_SOLICITUD'	AND vapBloque IN ('INACTIVAR_BENEFICIO_EN_CUSTODIA_PRESENCIAL',  'INACTIVAR_BENEFICIO_EN_CUSTODIA_WEB','INACTIVAR_BENEFICIO_EN_CUSTODIA_DEPWEB','INACTIVAR_BENEFICIOS_PADRE_PRESENCIAL','INACTIVAR_BENEFICIOS_PADRE_WEB','INACTIVAR_BENEFICIOS_PADRE_DEPWEB','INACTIVAR_BENEFICIOS_MADRE_PRESENCIAL','INACTIVAR_BENEFICIOS_MADRE_WEB','INACTIVAR_BENEFICIOS_MADRE_DEPWEB');