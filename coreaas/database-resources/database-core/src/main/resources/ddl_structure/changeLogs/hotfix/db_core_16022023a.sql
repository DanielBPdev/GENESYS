--GLPI 44895 adicionar ciudadRepresentanteLegal
---------NTF_GST_INF_FLT_APT
DECLARE @uno SMALLINT = (select pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'NTF_GST_INF_FLT_APT')
if @uno is not NULL
begin
INSERT INTO variablecomunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden	) 
values ('${ciudadRepresentanteLegal}','Ciudad donde se encuentra el representante legal','Ciudad Representante Legal',@uno,'','VARIABLE',0)
end

--------NTF_RCHZ_DVL_APT
DECLARE @dos SMALLINT = (select pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'NTF_RCHZ_DVL_APT')
if @dos is not NULL
begin
INSERT INTO variablecomunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden	) 
values ('${ciudadRepresentanteLegal}','Ciudad donde se encuentra el representante legal','Ciudad Representante Legal',@dos,'','VARIABLE',0)
end

--------NTF_APR_DVL_APT
DECLARE @tres SMALLINT = (select pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'NTF_APR_DVL_APT')
if @tres is not NULL
begin
INSERT INTO variablecomunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden	) 
values ('${ciudadRepresentanteLegal}','Ciudad donde se encuentra el representante legal','Ciudad Representante Legal',@tres,'','VARIABLE',0)
end

--------NTF_PAG_DVL_APT
DECLARE @cuatro SMALLINT = (select pcoId FROM PlantillaComunicado WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT')
if @cuatro is not NULL
begin
INSERT INTO variablecomunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden	) 
values ('${ciudadRepresentanteLegal}','Ciudad donde se encuentra el representante legal','Ciudad Representante Legal',@cuatro,'','VARIABLE',0)
end