--liquibase formatted sql

--changeset jzambrano:01
--comment: Se alteran tamaño de campos de las tablas Ubicacion y ProductoNoConforme
ALTER TABLE Ubicacion ALTER COLUMN ubiDireccionFisica varchar(300) NULL; 
ALTER TABLE ProductoNoConforme ALTER COLUMN pncValorCampoBack varchar(300) NULL; 
ALTER TABLE ProductoNoConforme ALTER COLUMN pncValorCampoFront varchar(300) NULL; 

--changeset mosanchez:02
--comment: Se adiciona campo en la tabla SucursalEmpresa
ALTER TABLE SucursalEmpresa ADD sueSucursalPrincipal bit NULL;
ALTER TABLE SucursalEmpresa ALTER COLUMN sueCodigo varchar(10) NULL;

--changeset ogiral:03
--comment: Se adiciona campo en la tabla SucursalEmpresa
ALTER TABLE Empleador ADD empValidarSucursalPila bit NULL;

--changeset clmarin:04
--comment: Eliminacion de registros en la tabla PlantillaComunicado y Variable comunicado de las etiquetas 'NTF_REG_BNF_WEB_TRB','NTF_REG_BNF_WEB_EMP','NTF_ENRL_AFL_IDPE_WEB','NTF_ENRL_AFL_PNS_WEB','NTF_ENRL_AFL_EMP_WEB','CNFR_RET_APRT','NTF_PARA_SBC_NVD_PERS'
DELETE vc FROM Plantillacomunicado pc INNER JOIN VariableComunicado vc ON pc.pcoId=vc.vcoPlantillaComunicado WHERE pcoEtiqueta in ('NTF_REG_BNF_WEB_TRB','NTF_REG_BNF_WEB_EMP','NTF_ENRL_AFL_IDPE_WEB','NTF_ENRL_AFL_PNS_WEB','NTF_ENRL_AFL_EMP_WEB','CNFR_RET_APRT','NTF_PARA_SBC_NVD_PERS');

--changeset clmarin:05
--comment: Se actualiza campos de la tabla Plantilla Comunicado
UPDATE Plantillacomunicado SET pcoCuerpo = 'Cuerpo', pcoMensaje = 'Mensaje' WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE Plantillacomunicado SET pcoCuerpo = 'Cuerpo', pcoMensaje = 'Mensaje' WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE Plantillacomunicado SET pcoCuerpo = 'Cuerpo', pcoMensaje = 'Mensaje' WHERE pcoEtiqueta = 'NTF_ENRL_AFL_IDPE_WEB';
UPDATE Plantillacomunicado SET pcoCuerpo = 'Cuerpo', pcoMensaje = 'Mensaje' WHERE pcoEtiqueta = 'NTF_ENRL_AFL_PNS_WEB';
UPDATE Plantillacomunicado SET pcoCuerpo = 'Cuerpo', pcoMensaje = 'Mensaje' WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';
UPDATE Plantillacomunicado SET pcoCuerpo = 'Cuerpo', pcoMensaje = 'Mensaje' WHERE pcoEtiqueta = 'CNFR_RET_APRT';
UPDATE Plantillacomunicado SET pcoCuerpo = 'Cuerpo', pcoMensaje = 'Mensaje' WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_PERS';