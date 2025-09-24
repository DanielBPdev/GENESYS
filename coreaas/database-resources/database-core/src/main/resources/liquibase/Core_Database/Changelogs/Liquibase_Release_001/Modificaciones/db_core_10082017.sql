--liquibase formatted sql

--changeset jusanchez:01 context:integracion
--comment: Actualizacion de registros en la tabla parametro
UPDATE Parametro SET prmValor = '80' WHERE prmNombre = 'SERVICIOS_PUERTO';

--changeset jocorrea:02
--comment: Se eliminan registros de la tabla ValidacionProceso
DELETE FROM ValidacionProceso WHERE vapValidacion = 'VALIDACION_PERSONA_TIENE_19' AND vapBloque IN ('ACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL','ACTIVAR_BENEFICIARIO_CONYUGE_WEB','ACTIVAR_BENEFICIARIO_CONYUGE_DEPWEB','INACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL','INACTIVAR_BENEFICIARIO_CONYUGE_WEB','INACTIVAR_BENEFICIARIO_CONYUGE_DEPWEB','INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL','INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_WEB','INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_DEPWEB','INACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL','INACTIVAR_BENEFICIARIO_HIJASTRO_WEB','INACTIVAR_BENEFICIARIO_HIJASTRO_DEPWEB','INACTIVAR_BENEFICIARIO_HUERFANO_PRESENCIAL','INACTIVAR_BENEFICIARIO_HUERFANO_WEB','INACTIVAR_BENEFICIARIO_HUERFANO_DEPWEB','INACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_PRESENCIAL','INACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_WEB','INACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_DEPWEB','INACTIVAR_BENEFICIO_EN_CUSTODIA_PRESENCIAL','INACTIVAR_BENEFICIO_EN_CUSTODIA_WEB','INACTIVAR_BENEFICIO_EN_CUSTODIA_DEPWEB');
DELETE FROM ValidacionProceso WHERE vapValidacion = 'VALIDACION_HIJO_2_GRUPOS_DISTINTOS_AFILIADOS' AND vapBloque IN ('ACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL','ACTIVAR_BENEFICIARIO_CONYUGE_WEB','ACTIVAR_BENEFICIARIO_CONYUGE_DEPWEB');

--changeset jusanchez:03
--comment: Se actualizan registros de la tabla FieldDefinitionLoad
UPDATE FieldDefinitionLoad set required=0 WHERE id>=1221 AND id<=1243;

--changeset hhernandez:04
--comment: Se elimina la tabla ActualizacionDatosEmpleador y se crea la tabla GestionNotiNoEnviadas
ALTER TABLE ActualizacionDatosEmpleador DROP CONSTRAINT FK_ActualizacionDatosEmpleador_adeEmpresa;
DELETE FROM ActualizacionDatosEmpleador;
CREATE TABLE GestionNotiNoEnviadas(
	gneId bigint NOT NULL IDENTITY(1,1),
	gneEmpresa bigint NOT NULL,
	gneTipoInconsistencia varchar(20) NOT NULL,
	gneCanalContacto varchar(20) NOT NULL,
	gneFechaIngreso datetime NOT NULL,
	gneEstadoGestion varchar(20) NOT NULL,
	gneObservaciones varchar(60),
	gneFechaRespuesta datetime,
	CONSTRAINT PK_GestionNotiNoEnviadas_gneId PRIMARY KEY (gneId)
);
ALTER TABLE GestionNotiNoEnviadas ADD CONSTRAINT FK_GestionNotiNoEnviadas_gneEmpresa FOREIGN KEY (gneEmpresa) REFERENCES Empresa(empId);

--changeset atoro:05
--comment: Se adiciona campo en la tabla DocumentoAdministracionEstadoSolicitud
ALTER TABLE DocumentoAdministracionEstadoSolicitud ADD daeActividad varchar(29) NULL;

--changeset ogiral:06
--comment: Se coloca valor por defecto del campo empValidarSucursalPila en la tabla Empleador
ALTER TABLE Empleador ADD CONSTRAINT DF_Empleador_empValidarSucursalPila DEFAULT 0 FOR empValidarSucursalPila;