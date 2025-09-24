alter table AccionCobro1D
alter column acdcomunicado varchar(50)

delete V from VariableComunicado V
inner join PlantillaComunicado on V.vcoPlantillaComunicado = pcoId
where V.vcoClave = '${membreteEncabezadoDeLaCcf}'
or V.vcoClave = '${membretePieDePaginaDeLaCcf}'
and pcoEtiqueta like '%HU_PROCESO%'