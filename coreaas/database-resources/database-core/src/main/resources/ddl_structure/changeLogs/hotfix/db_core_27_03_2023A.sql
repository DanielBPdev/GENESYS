DELETE VariableComunicado WHERE vcoPlantillaComunicado=(select p.pcoId from PlantillaComunicado p where p.pcoEtiqueta='COM_GEN_CER_APO_EMP')
and (vcoClave!='${logoDeLaCcf}' 
and vcoClave!='${logoDeLaCcf}'
and vcoClave!='${ciudadSolicitud}'
and vcoClave!='${fechaGeneracion}'
and vcoClave!='${nombreRazonSocialEmpleador}'
and vcoClave!='${tipoSolicitante}'
and vcoClave!='${direccionPrincipal}'
and vcoClave!='${municipio}'
and vcoClave!='${departamento}'
and vcoClave!='${telefono}'
and vcoClave!='${nombreCcf}'
and vcoClave!='${tipoIdentificacion}'
and vcoClave!='${numeroIdentificacion}'
and vcoClave!='${anio}'
and vcoClave!='${tabla}'
and vcoClave!='${sumAportes}'
and vcoClave!='${responsableCcf}'
and vcoClave!='${cargoResponsableCcf}'
and vcoClave!='${contenido}'
and vcoClave!='${responsableAfiliacionEmpresas}'
and vcoClave!='${cargoRespAfiEmpresas}'
and vcoClave!='${firmaRespAfiEmpresas}'
and vcoClave!='${correoCcf}'
and vcoClave!='${departamentoCcf}'
and vcoClave!='${ciudadCcf}'
and vcoClave!='${direccionCcf}'
and vcoClave!='${telefonoCcf}'
and vcoClave!='${tipoIdCcf}'
and vcoClave!='${numeroIdCcf}'
and vcoClave!='${webCcf}'
and vcoClave!='${logoSupersubsidio}'
and vcoClave!='${cargoResponsableAportesCcf}'
and vcoClave!='${firmaResponsableAportesCcf}'
and vcoClave!='${responsableAportesCcf}'
and vcoClave!='${usuarioGenerador}')

insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'CRT_ACP_EMP'

insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'RCHZ_AFL_EMP'

insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'RCHZ_AFL_EMP_PRE'

insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB'

insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP'

 
insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'REC_PLZ_LMT_PAG'

insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'

insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'NTF_NVD_EMP'

insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'NTF_RAD_NVD_EMP'

insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE'

insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'CNFR_RET_APRT'


insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'NTF_NVD_PERS'

insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'NTF_RAD_NVD_PER'

insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'RCHZ_NVD_PERS_PROD_NSUBLE'

insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'RCHZ_NVD_WEB_TRB_REP_POR_EMP_POR_PROD_NSUBLE'


insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'NTF_RAD_NVD_WEB_TRB_EMP'
