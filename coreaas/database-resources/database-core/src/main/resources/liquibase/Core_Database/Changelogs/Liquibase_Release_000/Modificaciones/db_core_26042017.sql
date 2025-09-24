--liquibase formatted sql

--changeset squintero:01
--comment: Actualizacion en la tabla EscalamientoSolicitud
-- update para la tabla EscalamientoSolicitud en el campo esoResultadoAnalista
UPDATE EscalamientoSolicitud SET esoResultadoAnalista = 'SOLICITUD_PROCEDENTE' WHERE esoResultadoAnalista = 'AFILIACION_PROCEDENTE';
UPDATE EscalamientoSolicitud SET esoResultadoAnalista = 'SOLICITUD_NO_PROCEDENTE' WHERE esoResultadoAnalista = 'AFILIACION_NO_PROCEDENTE';

--changeset mosanchez:02
--comment: Cambio de tamaño en campo por solicitud de julian arevalo de asopagos
ALTER TABLE SucursalEmpresa ALTER COLUMN sueNombre varchar (100) NULL; 

--changeset jusanchez:03
--comment: Se agrega nuevo campo en la tabla CargueMultipleSupervivencia
ALTER TABLE CargueMultipleSupervivencia ADD cmsNombreArchivo varchar (30) NOT NULL;

--changeset squintero:04
--comment: Actualización de la ruta cualificada de los convertidores de personas para cada novedad
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarAfiliadoNovedadPersona' WHERE novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarAfiliadoNovedadPersona';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarBeneficiarioNovedadPersona' WHERE novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarBeneficiarioNovedadPersona';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona' WHERE novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarCondicionInvalidezNovedadPersona';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarGrupoFamiliarNovedadPersona' WHERE novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarGrupoFamiliarNovedadPersona';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarInactivarCuentaWebPersona' WHERE novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarInactivarCuentaWebPersona';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarNovedadPilaPersona' WHERE novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarNovedadPilaPersona';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarPersonaNovedadPersona' WHERE novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarPersonaNovedadPersona';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarRolAfiliadoNovedadPersona' WHERE novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarRolAfiliadoNovedadPersona';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarSolAfiliacionNovedadPersona' WHERE novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarSolAfiliacionNovedadPersona';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarVenciCertificadoEscolaridad' WHERE novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarVenciCertificadoEscolaridad';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarVenciIncapacidades' WHERE novRutaCualificada = 'com.asopagos.convertidores.persona.ActualizarVenciIncapacidades';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ValidarNovedadInactivarCuentaWebPersona' WHERE novRutaCualificada = 'com.asopagos.convertidores.persona.ValidarNovedadInactivarCuentaWebPersona';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ValidarNovedadVencCertificadoEscolaridad' WHERE novRutaCualificada = 'com.asopagos.convertidores.persona.ValidarNovedadVencCertificadoEscolaridad';
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ValidarNovedadVencIncapacidades' WHERE novRutaCualificada = 'com.asopagos.convertidores.persona.ValidarNovedadVencIncapacidades';
