--liquibase formatted sql

--changeset eamaya:01 
--comment: Actualizacion de Novedad
update Novedad set novTipoTransaccion='CAMBIO_DATOS_SUCURSAL_PRESENCIAL' where novTipoTransaccion='CAMBIO_DATOS_SUCURSAL_PRESENCIA';


--changeset ogiral:02 
--comment: Eliminacion de datos en constante y parametro
delete from Constante where cnsNombre='DIAS_REINTEGRO' and cnsValor='30';
delete from Parametro where prmNombre='PLAZO_REINTEGRO' and prmValor='60';
