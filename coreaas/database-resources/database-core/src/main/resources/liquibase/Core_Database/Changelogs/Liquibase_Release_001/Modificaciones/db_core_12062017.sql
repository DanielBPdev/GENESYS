--liquibase formatted sql

--changeset jusanchez:01
--comment: Modificaciones en las tablas Destinatario, DestinatarioGrupo, DestinatarioComunicado, PrioridadDestinatario y GrupoPrioridad
 
--Eliminar llave Foranea FK_DestinatarioGrupo_dgrDestinatario de la tabla DestinatarioGrupo
ALTER TABLE DestinatarioGrupo DROP CONSTRAINT FK_DestinatarioGrupo_dgrDestinatario;
ALTER TABLE DestinatarioGrupo DROP CONSTRAINT FK_DestinatarioGrupo_dgrGrupoPrioridad;
ALTER TABLE DestinatarioComunicado DROP CONSTRAINT UK_DestinatarioComunicado_dcoProceso_dcoEtiquetaPlantilla;
ALTER TABLE PrioridadDestinatario DROP CONSTRAINT FK_PrioridadDestinatario_prdDestinatarioComunicado;
ALTER TABLE PrioridadDestinatario DROP CONSTRAINT FK_PrioridadDestinatario_prdGrupoPrioridad;

--Borrar datos de las tablas GrupoPrioridad, DestinatarioGrupo, PrioridadDestinatario, DestinatarioComunicado, Destinatario
TRUNCATE TABLE DestinatarioGrupo;
TRUNCATE TABLE PrioridadDestinatario;
TRUNCATE TABLE Destinatario;
TRUNCATE TABLE DestinatarioComunicado;
TRUNCATE TABLE GrupoPrioridad;

--Agregar llaves foraneas a las tablas DestinatarioGrupo, DestinatarioComunicado y PrioridadDestinatario
ALTER TABLE DestinatarioGrupo ADD CONSTRAINT FK_DestinatarioGrupo_dgrGrupoPrioridad FOREIGN KEY (dgrGrupoPrioridad) REFERENCES GrupoPrioridad(gprId);
ALTER TABLE DestinatarioComunicado ADD CONSTRAINT UK_DestinatarioComunicado_dcoProceso_dcoEtiquetaPlantilla UNIQUE (dcoProceso,dcoEtiquetaPlantilla);
ALTER TABLE PrioridadDestinatario ADD CONSTRAINT FK_PrioridadDestinatario_prdDestinatarioComunicado FOREIGN KEY (prdDestinatarioComunicado) REFERENCES DestinatarioComunicado(dcoId);
ALTER TABLE PrioridadDestinatario ADD CONSTRAINT FK_PrioridadDestinatario_prdGrupoPrioridad FOREIGN KEY (prdGrupoPrioridad) REFERENCES GrupoPrioridad(gprId);

--Eliminación de la columna dgrDestinatario de la tabla GrupoPrioridad
ALTER TABLE DestinatarioGrupo DROP COLUMN dgrDestinatario;

--Eliminación de la tabla destinatario
DROP TABLE Destinatario;

-- Agregar la columna dgrRolContacto de la tabla DestinatarioGrupo
ALTER TABLE DestinatarioGrupo ADD dgrRolContacto varchar (60) NOT NULL;


--changeset clmarin:01
--comment: Modificaciones en las tablas VariableComunicado y PlantillaComunicado

--Borrar registros de la tabla VariableComunicado
TRUNCATE TABLE VariableComunicado;

--Actualización de la tabla Plantillacomunicado
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_RAD_AFL_PER';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_INT_AFL';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'CRT_BVD_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'CRT_ACP_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_AFL_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_INC_VAL';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_SBC_AFL_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PROD_NSUB';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_AFL_DPT_INC_VAL_TRAB';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_AFL_DPT_INC_VAL_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_AFL_DPT_PROD_NSUB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_AFL_DPT_PROD_NSUB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_AFL_DPT_PROD_NSUBLE_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_AFL_DPT_PROD_NSUBLE_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_EMP_AFL_MLT_TRBW';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_INVL_AFL_TRBW';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_SUB_AFL_TRB_DPT';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_INVL_AFL_TRBW_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_ENRL_AFL_IDPE_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_ENRL_AFL_PNS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_ACPT_AFL_PNS_DSP_SUB';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_RCHZ_AFL_PNS_DSP_SUB';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_ACPT_AFL_IDPE_DSP_SUB';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_RCHZ_AFL_IDPE_DSP_SUB';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_AFL_PNS_INC_VAL';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_AFL_IDPE_INC_VAL';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_AFL_PNS_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_AFL_IDPE_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_SBC_AFL_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_SBC_AFL_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_AFL_PNS_PROD_NSUB';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_AFL_IDPE_PROD_NSUB';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_RAD_AFL_IDPE_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_RAD_AFL_PNS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'CRT_BVD_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'CRT_BVD_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'CRT_ACP_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'CRT_ACP_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_RAD_AFL_EMP_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_NVD_PERS';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_NVD_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_RAD_NVD_PER';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_RAD_NVD_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_NVD_PERS_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'CNFR_RET_APRT';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'DSTMTO_NVD_RET_APRT';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_APRB_NVD_WEB_TRB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_NVD_WEB_TRB_REP_POR_EMP_POR_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_PERS';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_NVD_WEB_TRB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_WEB_TRB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'NTF_RAD_NVD_WEB_TRB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_AFL_PER_POR_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_AFL_PER_POR_PROD_NSUB';
UPDATE PlantillaComunicado SET pcoCuerpo = 'Cuerpo', pcoEncabezado = 'Encabezado', pcoMensaje = 'Mensaje', pcoPie = 'Pie' WHERE pcoEtiqueta = 'RCHZ_AFL_PER_INC_VAL';

