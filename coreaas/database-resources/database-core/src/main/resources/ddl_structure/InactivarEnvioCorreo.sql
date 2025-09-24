--liquibase formatted sql
--changeset rcastillo:01
--comment: Se agrega, para dejar inactivo el parametro de envío de correo, cada vez que sucede un despliegue. 
if exists (select * from Parametro where prmNombre = 'ENVIO_CORREOS_ACTIVO' and prmValor = 'TRUE')
	begin
		update Parametro set prmValor = 'FALSE' from Parametro where prmNombre = 'ENVIO_CORREOS_ACTIVO'
	end