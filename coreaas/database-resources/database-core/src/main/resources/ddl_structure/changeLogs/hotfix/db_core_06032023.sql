--liquibase formatted sql

--changeset EDWIN TORO
--comment: Se inserta en la tabla VariableComunicado

insert dbo.VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
select distinct '${numeroDeRadicacion}','Número de radicado de la solicitud','Número de radicación',b.pcoId,'','VARIABLE',0
from dbo.VariableComunicado as a
inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
where b.pcoEtiqueta = 'CRT_BVD_EMP'