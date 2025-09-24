--liquibase formatted sql

--changeset rarboleda:01
--comment: Se adicionan campos a la tabla empleador
ALTER TABLE dbo.Empleador ADD empCantIngresoBandejaCeroTrabajadores smallint;
ALTER TABLE dbo.Empleador ADD empFechaRetiroTotalTrabajadores date;
ALTER TABLE dbo.Empleador ADD empFechaGestionDesafiliacion date;

--changeset edelgado:02
--comment: Se actualiza registros en la tabla parametrizacionmetodoasignacion
UPDATE ParametrizacionMetodoAsignacion SET pmaGrupo = 'BacAfiEmp' WHERE pmaGrupo = 'back_afiliacion';
UPDATE ParametrizacionMetodoAsignacion SET pmaGrupo = 'BacAfiPer' WHERE pmaGrupo = 'back_afiliacion_personas';
UPDATE ParametrizacionMetodoAsignacion SET pmaGrupo = 'BacNoveEmp' WHERE pmaGrupo = 'back_novedades_empleador';
UPDATE ParametrizacionMetodoAsignacion SET pmaGrupo = 'BacNovPer' WHERE pmaGrupo = 'back_novedades_personas';
UPDATE ParametrizacionMetodoAsignacion SET pmaGrupo = 'AnaApo' WHERE pmaGrupo = 'analista_aportes';
UPDATE ParametrizacionMetodoAsignacion SET pmaMetodoAsignacion = 'NUMERO_SOLICITUDES', pmaUsuario = NULL WHERE pmaUsuario IS NOT NULL;

--changeset abaquero:03
--comment: Se actualiza registros en la ValidatorParamValue
UPDATE ValidatorParamValue SET value = '1,2,3,4,12,15,16,18,19,20,21,22,23,30,31,32,33,34,35,36,40,42,43,44,45,47,51,52,53,54,55,56,57,58,59' WHERE id = 2110215;

--changeset flopez:04
--comment: Creacion de las tablas de medio de pago, AdministradorSubsidio, MedioDePago, MedioEfectivo, MedioTarjeta, MedioTransferencia, AdminSubsidioGrupo, MediosPagoCCF y MedioPagoPersona
CREATE TABLE AdministradorSubsidio(
	asuId BIGINT NOT NULL IDENTITY(1,1),
	asuPersona BIGINT NOT NULL,
	CONSTRAINT PK_AdministradorSubsidio_asuId PRIMARY KEY (asuId)
);
ALTER TABLE AdministradorSubsidio ADD CONSTRAINT FK_AdministradorSubsidio_asuPersona FOREIGN KEY (asuPersona) REFERENCES Persona(perId);

CREATE TABLE MedioDePago(
	mdpId BIGINT NOT NULL IDENTITY(1,1),
	mdpTipo VARCHAR(30) NOT NULL,
	CONSTRAINT PK_MedioDePago_mdpId PRIMARY KEY (mdpId)
);

CREATE TABLE MedioEfectivo(
	mdpId BIGINT NOT NULL,
	mefEfectivo BIT NOT NULL,
	CONSTRAINT PK_MedioEfectivo_mdpId PRIMARY KEY (mdpId)
);
ALTER TABLE MedioEfectivo ADD CONSTRAINT FK_MedioEfectivo_mefId FOREIGN KEY (mdpId) REFERENCES MedioDePago(mdpId);

CREATE TABLE MedioTarjeta(
	mdpId BIGINT NOT NULL,
	mtrNumeroTarjeta VARCHAR(50) NOT NULL,
	mtrDisponeTarjeta BIT NOT NULL,
	mtrEstadoTarjetaMultiservicios VARCHAR(30) NOT NULL,
	mtrSolicitudTarjeta VARCHAR(30) NOT NULL,
	CONSTRAINT PK_MedioTarjeta_mdpId PRIMARY KEY (mdpId)
);
ALTER TABLE MedioTarjeta ADD CONSTRAINT FK_MedioTarjeta_mtrId FOREIGN KEY (mdpId) REFERENCES MedioDePago(mdpId);
 
CREATE TABLE MedioTransferencia(
	mdpId BIGINT NOT NULL,
	metBanco BIGINT NOT NULL,
	metTipoCuenta VARCHAR(30) NOT NULL,
	metNumeroCuenta VARCHAR(30) NOT NULL,
	metTipoIdentificacionTitular VARCHAR(20) NULL,
	metNumeroIdentificacionTitular VARCHAR(16) NULL,
	metDigitoVerificacionTitular SMALLINT NULL,
	metNombreTitularCuenta VARCHAR (200) NULL,	
	CONSTRAINT PK_MedioTransferencia_mdpId PRIMARY KEY (mdpId)
);
ALTER TABLE MedioTransferencia ADD CONSTRAINT FK_MedioTransferencia_metId FOREIGN KEY (mdpId) REFERENCES MedioDePago(mdpId);
ALTER TABLE MedioTransferencia ADD CONSTRAINT FK_MedioTransferencia_metBanco FOREIGN KEY (metBanco) REFERENCES Banco(banId);

CREATE TABLE AdminSubsidioGrupo(
	asgId BIGINT NOT NULL IDENTITY(1,1),
	asgGrupoFamiliar BIGINT NOT NULL,
	asgAdministradorSubsidio BIGINT NULL,
	asgMedioDePago BIGINT NULL,
	asgAfiliadoEsAdminSubsidio bit NOT NULL,
	asgMedioPagoActivo bit NOT NULL,
	asgRelacionGrupoFamiliar smallint null,
	CONSTRAINT PK_AdminSubsidioGrupo_asgId PRIMARY KEY (asgId)
);
ALTER TABLE AdminSubsidioGrupo ADD CONSTRAINT FK_AdminSubsidioGrupo_asgGrupoFamiliar FOREIGN KEY (asgGrupoFamiliar) REFERENCES GrupoFamiliar(grfId);
ALTER TABLE AdminSubsidioGrupo ADD CONSTRAINT FK_AdminSubsidioGrupo_asgAdministradorSubsidio FOREIGN KEY (asgAdministradorSubsidio) REFERENCES AdministradorSubsidio(asuId);
ALTER TABLE AdminSubsidioGrupo ADD CONSTRAINT FK_AdminSubsidioGrupo_asgMedioDePago FOREIGN KEY (asgMedioDePago) REFERENCES MedioDePago(mdpId);
ALTER TABLE AdminSubsidioGrupo ADD CONSTRAINT FK_AdminSubsidioGrupo_asgRelacionGrupoFamiliar FOREIGN KEY (asgRelacionGrupoFamiliar) REFERENCES RelacionGrupoFamiliar(rgfId);

CREATE TABLE MediosPagoCCF(
	mpcId BIGINT NOT NULL IDENTITY(1,1),
	mpcCajaCompensacion INT NOT NULL,
	mpcMedioPago VARCHAR(30) NOT NULL,
	mpcMedioPreferente BIT NOT NULL,
	CONSTRAINT PK_MediosPagoCCF_mpcId PRIMARY KEY (mpcId)
);
ALTER TABLE MediosPagoCCF ADD CONSTRAINT FK_MediosPagoCCF_mpcCajaCompensacion FOREIGN KEY (mpcCajaCompensacion) REFERENCES CajaCompensacion(ccfId);

CREATE TABLE MedioPagoPersona(
	mppId BIGINT NOT NULL IDENTITY(1,1),
	mppMedioPago BIGINT NOT NULL,
	mppPersona BIGINT NOT NULL,
	mppMedioActivo BIT NOT NULL,
	CONSTRAINT PK_MedioPagoPersona_mppId PRIMARY KEY (mppId)
);
ALTER TABLE MedioPagoPersona ADD CONSTRAINT FK_MedioPagoPersona_mppMedioPago FOREIGN KEY (mppMedioPago) REFERENCES MedioDePago(mdpId);
ALTER TABLE MedioPagoPersona ADD CONSTRAINT FK_MedioPagoPersona_mppPersona FOREIGN KEY (mppPersona) REFERENCES Persona(perId);

--Modificación PersonaDetalle
ALTER TABLE PersonaDetalle DROP CONSTRAINT FK_PersonaDetalle_pedMedioPago;
ALTER TABLE PersonaDetalle DROP COLUMN pedMedioPago;

--Modificación Empleador
ALTER TABLE Empleador DROP CONSTRAINT FK_Empleador_empMedioPagoSubsidioMonetario;
ALTER TABLE Empleador DROP COLUMN empMedioPagoSubsidioMonetario;
ALTER TABLE Empleador ADD empMedioPagoSubsidioMonetario VARCHAR(30) NULL;

-- Modificación SucursalEmpresa
ALTER TABLE SucursalEmpresa DROP CONSTRAINT FK_SucursalEmpresa_sueMedioPagoSubsidioMonetario;
ALTER TABLE SucursalEmpresa DROP COLUMN sueMedioPagoSubsidioMonetario;
ALTER TABLE SucursalEmpresa ADD sueMedioPagoSubsidioMonetario VARCHAR(30) NULL;

-- Modificación Grupo Familiar
ALTER TABLE GrupoFamiliar DROP CONSTRAINT FK_GrupoFamiliar_grfParametrizacionMedioPago;
ALTER TABLE GrupoFamiliar DROP CONSTRAINT FK_GrupoFamiliar_grfAdministradorSubsidio;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_GrupoFamiliar_grfRelacionGrupoFamiliar')) ALTER TABLE GrupoFamiliar DROP CONSTRAINT CK_GrupoFamiliar_grfRelacionGrupoFamiliar;
ALTER TABLE GrupoFamiliar DROP COLUMN grfParametrizacionMedioPago;
ALTER TABLE GrupoFamiliar DROP COLUMN grfAdministradorSubsidio;
ALTER TABLE GrupoFamiliar DROP COLUMN grfRelacionGrupoFamiliar;

-- Eliminación de Tabla ParametrizacionMedioDePago
DROP TABLE ParametrizacionMedioDePago;

--changeset flopez:05
--comment: Se actualiza el campo novRutaCualificada de la tabla ParametrizacionNovedad 
--NOVEDAD 181: Cambio Medio de Pago Trabajador Dependiente
UPDATE ParametrizacionNovedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarMedioDePagoNovedadPersona' WHERE novTipoTransaccion = 'CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_PRESENCIAL';
UPDATE ParametrizacionNovedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarMedioDePagoNovedadPersona' WHERE novTipoTransaccion = 'CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_WEB';
UPDATE ParametrizacionNovedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarMedioDePagoNovedadPersona' WHERE novTipoTransaccion = 'CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_DEPWEB';

--NOVEDAD 182: Cambio Medio de Pago Administrador del Subsidio
UPDATE ParametrizacionNovedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarMedioDePagoNovedadPersona' WHERE novTipoTransaccion = 'CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_PRESENCIAL';
UPDATE ParametrizacionNovedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarMedioDePagoNovedadPersona' WHERE novTipoTransaccion = 'CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_WEB';
UPDATE ParametrizacionNovedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarMedioDePagoNovedadPersona' WHERE novTipoTransaccion = 'CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_DEPWEB';

--NOVEDAD 183: Cambio Administrador del Subsidio - Trabajador Dependiente
UPDATE ParametrizacionNovedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarMedioDePagoNovedadPersona' WHERE novTipoTransaccion = 'CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_TRABAJADOR_DEPENDIENTE';

--NOVEDAD 185: Cambio Administrador del Subsidio - Administrador del Subsidio
UPDATE ParametrizacionNovedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarMedioDePagoNovedadPersona' WHERE novTipoTransaccion = 'CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_ADMINISTRADOR_SUBSIDIO';

--NOVEDAD 198: Cambio titular de la cuenta,tipo cuenta,numero cuenta - Trabajador Dependiente
UPDATE ParametrizacionNovedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarMedioDePagoNovedadPersona' WHERE novTipoTransaccion = 'CAMBIO_DATOS_DE_CUENTA_TRABAJADOR_DEPENDIENTE_PRESENCIAL';
UPDATE ParametrizacionNovedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarMedioDePagoNovedadPersona' WHERE novTipoTransaccion = 'CAMBIO_DATOS_DE_CUENTA_TRABAJADOR_DEPENDIENTE_WEB';
UPDATE ParametrizacionNovedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarMedioDePagoNovedadPersona' WHERE novTipoTransaccion = 'CAMBIO_DATOS_DE_CUENTA_TRABAJADOR_DEPENDIENTE_DEPWEB';

--NOVEDAD 199: Cambio titular de la cuenta,tipo cuenta,numero cuenta - Administrador del Subsidio.
UPDATE ParametrizacionNovedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarMedioDePagoNovedadPersona' WHERE novTipoTransaccion = 'CAMBIO_DATOS_DE_CUENTA_ADMINISTRADOR_SUBSIDIO';

