--GLPI 44895 adicionar ciudadRepresentanteLegal
---------NTF_GST_INF_FLT_APT
insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${ciudadRepresentanteLegal}','Ciudad donde se encuentra el representante legal','Ciudad Representante Legal',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'NTF_GST_INF_FLT_APT' and a.vcoClave != '${ciudadRepresentanteLegal}' 


--------NTF_RCHZ_DVL_APT
insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${ciudadRepresentanteLegal}','Ciudad donde se encuentra el representante legal','Ciudad Representante Legal',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'NTF_RCHZ_DVL_APT' and a.vcoClave != '${ciudadRepresentanteLegal}'

--------NTF_APR_DVL_APT
insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${ciudadRepresentanteLegal}','Ciudad donde se encuentra el representante legal','Ciudad Representante Legal',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'NTF_APR_DVL_APT' and a.vcoClave != '${ciudadRepresentanteLegal}'


--------NTF_PAG_DVL_APT
insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${ciudadRepresentanteLegal}','Ciudad donde se encuentra el representante legal','Ciudad Representante Legal',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'NTF_PAG_DVL_APT' and a.vcoClave != '${ciudadRepresentanteLegal}'

