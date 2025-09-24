--ALTER PARA NUMERO DE RADICADO EN EMPRESAS CON MUCHOS TRABAJADORES
ALTER TABLE Solicitud ALTER COLUMN solNumeroRadicacion VARCHAR(30);
ALTER TABLE Solicitud_aud ALTER COLUMN solNumeroRadicacion VARCHAR(30);


--liquibase formatted sql

--changeset EDWIN TORO
--comment: Se inserta en la tabla VariableComunicado


INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',
(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_BVD_EMP'),'','VARIABLE',0)

INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',
(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ACP_EMP'),'','VARIABLE',0)

INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',
(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_AFL_EMP'),'','VARIABLE',0)


INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',
(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_AFL_EMP_PRE'),'','VARIABLE',0)



INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',
(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB'),'','VARIABLE',0)


INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',
(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP'),'','VARIABLE',0)


INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',
(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG'),'','VARIABLE',0)


INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',
(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'),'','VARIABLE',0)


INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',
(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NVD_EMP'),'','VARIABLE',0)

INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',
(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_NVD_EMP'),'','VARIABLE',0)


INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',
(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE'),'','VARIABLE',0)


INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',
(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CNFR_RET_APRT'),'','VARIABLE',0)


INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',
(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NVD_PERS'),'','VARIABLE',0)


INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',
(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_NVD_PER'),'','VARIABLE',0)


INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',
(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_PERS_PROD_NSUBLE'),'','VARIABLE',0)


INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',
(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_WEB_TRB_REP_POR_EMP_POR_PROD_NSUBLE'),'','VARIABLE',0)



INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',
(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_NVD_WEB_TRB_EMP'),'','VARIABLE',0)

if  exists (select * from sysobjects where name='CreacionUsuariosEmpresasKeycload' )
ALTER TABLE CreacionUsuariosEmpresasKeycload
ADD usuarioAsignado varchar(15);
ALTER TABLE CreacionUsuariosEmpresasKeycload
ADD claveAsignada varchar(15);