--liquibase formatted sql

--changeset ecastano:01
--comment: Se agrega columna tabla CarteraDependiente columna cadDeudaReal
ALTER TABLE CarteraDependiente add cadDeudaReal NUMERIC(19,5) NULL;

--changeset squintero:02
--comment: Insert tabla Constante
INSERT Constante(cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_CIERRE_RECAUDO_DEPLOYMENTID','com.asopagos.coreaas.bpm.cierre_recaudo:cierre_recaudo:0.0.2-SNAPSHOT','Identificador de versión del proceso BPM para el cierre de recaudo');

--changeset cwaldo:03
--comment: Insert tabla ConjuntoValidacionSubsidio
--beneficiario
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(29,'TRABAJADOR_MAS_DOS_BENEFICIARIOS_TIPO_PADRE');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(30,'TRABAJADOR_TIENE_BENEFICIARIO_TIPO_PADRE_NO_ACTIVO');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(31,'TRABAJADOR_TIENE_BENEFICIARIO_HIJO_NO_ACTIVO');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(32,'BENEFICIARIO_DEL_TRABAJADOR_FALLECIDO');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(33,'BENEFICIARIO_CON_OTROS_INGRESOS');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(34,'BENEFICIARIO_CON_OTROS_INGRESOS_COMO_EMPLEADOR');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(35,'BENEFICIARIO_RECIBIO_2_PERIODOS_ANTERIORES');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(36,'BENEFICIARIO_NO_ES_MAYOR_DE_60_ANIOS');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(37,'BENEFICIARIO_MAYOR_DE_23_ANIOS');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(38,'BENEFICIARIO_MAYOR_DE_18_ANIOS');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(39,'BENEFICIARIO_ENTRE_19_Y_22_ANIOS_NO_ES_ESTUDIANTE');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(40,'BENEFICIARIO_CON_CERTIFICADO_ESCOLAR_NO_VIGENTE');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(41,'HERMANO_HUERFANO_DE_PADRES');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(42,'BENEFICIARIO_MAS_DE_DOS_AFILIACIONES_EN_EL_MOMENTO');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(43,'EMPLEADOR_AFILIADO_BENEFICIARIO_CON_MAYOR_DERECHO');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(44,'AFILIADOS_SUMAN_SALARIOS_SUPERIORES_A_6_SMLMV');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(45,'EMPLEADOR_CON_MAYOR_SALARIO_PARA_EL_AFILIADO_PPAL');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(46,'SE_HA_ASIGNADO_EL_DERECHO_POR_OTRO_EMPLEADOR');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(47,'BENEFICIARIO_NO_TIENE_NOVEDAD_DE_FALLECIMIENTO');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(48,'REPORTAR_FALLECIMIENTO_BENEFICIARIO_PRESENCIAL');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(49,'FECHA_FALLECIMIENTO_BENEFICIARIO_SUPERA_EL_TIEMPO');
--trabajador
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(50,'EL_TRABAJADOR_NO_ESTA_REGISTRADO');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(51,'EL_TRABAJADOR_NO_TIENE_NOVEDAD_FALLECIMEINTO');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(52,'FECHA_FALLECIMIENTO_TRABAJADOR_SUPERA_EL_TIEMPO');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(53,'TRABAJADOR_NO_ACTIVO_DIA_ANTERIOR_FALLECIMIENTO');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(54,'REPORTAR_NOVEDAD_FALLECIMIENTO_PRESENCIALMENTE');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(55,'TRABAJADOR_NO_TIENE_BENEFICIARIO_ACTIVO');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(56,'TRABAJADOR_MISMO_EMPLEADOR');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(57,'TRABAJADOR_SOCIO_EMPRESA');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(58,'TRABAJADOR_MISMO_CONYUGUE_EMPLEADOR');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(59,'CONYUGUE_IGUAL_EMPLEADOR');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(60,'TRABAJADOR_NO_ACTIVO');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(61,'BENEFICIARIO_NO_ACTIVO_TRABAJADOR');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(62,'TRABAJADOR_FALLECIDO');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(63,'TRABAJADOR_REPORTE_NO_OK');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(64,'TRABAJADOR_NO_TIENE_APORTES');
INSERT ConjuntoValidacionSubsidio(cvsId,cvsTipoValidacion)VALUES(65,'TRABAJADOR_REPORTA_SALARIO_MAYOR_4_SMLMV');

--changeset dwaldo:04
--comment: Se crea tabla AplicacionValidacionSubsidioPersona
CREATE TABLE AplicacionValidacionSubsidioPersona
(
	avpId bigint NOT NULL IDENTITY(1,1),
	avpAplicacionValidacionSubsidio BIGINT,
	avpPersonaLiquidacionEspecifica BIGINT,	
	CONSTRAINT PK_AplicacionValidacionSubsidioPersona_avpId PRIMARY KEY (avpId),
    CONSTRAINT FK_avpAplicacionValidacionSubsidio FOREIGN KEY (avpAplicacionValidacionSubsidio)
    REFERENCES [dbo].[AplicacionValidacionSubsidio](avsId),
	CONSTRAINT FK_avpPersonaLiquidacionEspecifica FOREIGN KEY (avpPersonaLiquidacionEspecifica)
    REFERENCES [dbo].[PersonaLiquidacionEspecifica](pleId)
)

--changeset fvasquez:05
--comment: Se crea tabla AplicacionValidacionSubsidioPersona
CREATE TABLE CarteraNovedad(
	canId BIGINT IDENTITY(1,1) NOT NULL,
	canFechaInicio DATE NOT NULL,
	canFechaFin DATE NULL,
	canTipoNovedad VARCHAR(100) NOT NULL,
	canCondicion BIT NOT NULL,
	canAplicar BIT NOT NULL,
	canNovedadFutura BIT NOT NULL,
	canPersona BIGINT NOT NULL,
	canFechaCreacion DATETIME NOT NULL DEFAULT GETDATE(),
	CONSTRAINT PK_CarteraNovedad_canId PRIMARY KEY (canId)
)
