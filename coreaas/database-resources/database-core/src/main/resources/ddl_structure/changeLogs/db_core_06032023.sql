--liquibase formatted sql

--changeset EDWIN TORO
--comment: Se inserta en la tabla VariableComunicado


  declare @pcoId4 int;
  select @pcoId4 = pcoId from PlantillaComunicado where pcoEtiqueta = 'CRT_BVD_EMP'
  INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
                         values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',@pcoId4,'','VARIABLE',0)

  declare @pcoId5 int;
  select @pcoId5 = pcoId from PlantillaComunicado where pcoEtiqueta = 'CRT_ACP_EMP'
  INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
                         values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',@pcoId5,'','VARIABLE',0)


  declare @pcoId6 int;
  select @pcoId6 = pcoId from PlantillaComunicado where pcoEtiqueta = 'RCHZ_AFL_EMP'
  INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
                         values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',@pcoId6,'','VARIABLE',0)

  declare @pcoId127 int;
  select @pcoId127 = pcoId from PlantillaComunicado where pcoEtiqueta = 'RCHZ_AFL_EMP_PRE'
  INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
                         values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',@pcoId127,'','VARIABLE',0)


  declare @pcoId21 int;
  select @pcoId21 = pcoId from PlantillaComunicado where pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB'
  INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
                         values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',@pcoId21,'','VARIABLE',0)


  declare @pcoId22 int;
  select @pcoId22 = pcoId from PlantillaComunicado where pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP'
  INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
                         values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',@pcoId22,'','VARIABLE',0)

  declare @pcoId136 int;
  select @pcoId136 = pcoId from PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG'
  INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
                         values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',@pcoId136,'','VARIABLE',0)

 declare @pcoId137 int;
 select @pcoId137 = pcoId from PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'
 INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
 values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',@pcoId137,'','VARIABLE',0)

  declare @pcoId47 int;
  select @pcoId47 = pcoId from PlantillaComunicado where pcoEtiqueta = 'NTF_NVD_EMP'
  INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
                         values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',@pcoId47,'','VARIABLE',0)

  declare @pcoId48 int;
  select @pcoId48 = pcoId from PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_NVD_EMP'
  INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
                         values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',@pcoId48,'','VARIABLE',0)

  declare @pcoId344 int;
  select @pcoId344 = pcoId from PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE'
  INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
                         values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',@pcoId344,'','VARIABLE',0)

  declare @pcoId49 int;
  select @pcoId49 = pcoId from PlantillaComunicado where pcoEtiqueta = 'CNFR_RET_APRT'
  INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
                         values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',@pcoId49,'','VARIABLE',0)

  declare @pcoId58 int;
  select @pcoId58 = pcoId from PlantillaComunicado where pcoEtiqueta = 'NTF_NVD_PERS'
  INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
                         values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',@pcoId58,'','VARIABLE',0)


  declare @pcoId59 int;
  select @pcoId59 = pcoId from PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_NVD_PER'
  INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
                         values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',@pcoId59,'','VARIABLE',0)

  declare @pcoId60 int;
  select @pcoId60 = pcoId from PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_PERS_PROD_NSUBLE'
  INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
                         values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',@pcoId60,'','VARIABLE',0)

  declare @pcoId62 int;
  select @pcoId62 = pcoId from PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_WEB_TRB_REP_POR_EMP_POR_PROD_NSUBLE'
  INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
                         values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',@pcoId62,'','VARIABLE',0)


  declare @pcoId67 int;
  select @pcoId67 = pcoId from PlantillaComunicado where pcoEtiqueta = 'NTF_RAD_NVD_WEB_TRB_EMP'
  INSERT INTO VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
                         values('${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',67,'','VARIABLE',0)