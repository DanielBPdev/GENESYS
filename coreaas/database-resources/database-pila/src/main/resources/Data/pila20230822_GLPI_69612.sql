--liquibase formatted sql

--changeset rcastillo
--comment: Actualizacion clase aportantes. 
update p set stpValorParametro = '3,4,16,33,34,35,36,53,57,59,60,61,63,64,66,67,69'
from staging.StagingParametros as p
where p.stpNombreParametro = 'SOLICITANTE_INDEPENDIENTE'