--liquibase formatted sql

--changeset jvelandia:01
--comment: Inserciones tabla VariableComunicado
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${estadoDeCartera}','5','Estado de Cartera','Estado de Cartera ','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_223_171') );
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${estadoDeCartera}','5','Estado de Cartera','Estado de Cartera ','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_223_179') );

--changeset abaquero:02
--comment: Actualizacion tabla ValidatorParamValue
UPDATE ValidatorParamValue SET value='A17' WHERE id=2110801;
UPDATE ValidatorParamValue SET value='Archivo Tipo A - Registro 1 - Campo 7: Clase de aportante' WHERE id=2110797;

--changeset flopez:03
--comment: Eliminacion de campo mpcCajaCompensacion
ALTER TABLE MediosPagoCCF DROP CONSTRAINT FK_MediosPagoCCF_mpcCajaCompensacion;
ALTER TABLE MediosPagoCCF DROP COLUMN mpcCajaCompensacion;
 
--changeset flopez:04
--comment: Ajuste eliminar campos repetidos tabla MediosPagoCCF
DELETE FROM MediosPagoCCF WHERE mpcId IN(
SELECT MAX(mpcId) FROM MediosPagoCCF
WHERE mpcMedioPago IN (
SELECT  mpcMedioPago FROM MediosPagoCCF
GROUP BY mpcMedioPago
HAVING count (*) = 2)
GROUP BY mpcMedioPago);

--changeset flopez:05
--comment: Se elemina columna mpcCajaCompensacion
ALTER TABLE aud.MediosPagoCCF_aud DROP COLUMN mpcCajaCompensacion;

--changeset rlopez:06
--comment: Ajuste campos CuentaAdministradorSubsidio
ALTER TABLE aud.CuentaAdministradorSubsidio_aud ADD casSolicitudLiquidacionSubsidio BIGINT NULL;
ALTER TABLE aud.CuentaAdministradorSubsidio_aud ALTER COLUMN casCodigoBanco varchar(6) NULL;
ALTER TABLE aud.CuentaAdministradorSubsidio_aud ALTER COLUMN casNombreBanco varchar(255) NULL;