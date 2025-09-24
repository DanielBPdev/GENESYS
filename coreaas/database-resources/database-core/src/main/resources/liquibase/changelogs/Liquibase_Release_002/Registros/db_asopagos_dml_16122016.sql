--liquibase formatted sql

--changeset  mrobayo:01 
--comment: Actualización ccfSocioAsopagos=0 excepto CONFAMILIARES
UPDATE CajaCompensacion set ccfSocioAsopagos=0 where ccfNombre !='CONFAMILIARES';

--changeset  atoro:02 
--comment: Tílde arlNombre='Seguros Bolívar'
UPDATE ARL SET arlNombre='Seguros Bolívar' where arlNombre='Seguros Bolivar';

--changeset  lzarate:03 
--comment: Eliminación parametro USERS_SERVICES
DELETE FROM PARAMETRO WHERE prmNombre='USERS_SERVICES';

--changeset  jcamargo:04 
--comment: Actualización PlantillaComunicado
update PlantillaComunicado set pcoAsunto='Bienvenido a su caja de compensación', pcoMensaje='Señores <br/>[razonSocial]<br/><br/><br/>Reciban un cordial saludo.<br/><br/>En nombre de la [nombreCCF] le damos la bienvenida. Lo invitamos a comenzar a gozar de los distintos servicios que le ofrecemos.<br/><br/><br/>Cordialmente,<br/><br/>[nombreCCF]' where pcoEtiqueta='CARTA_BIENVENIDA_EMPLEADOR';
update PlantillaComunicado set pcoAsunto='Aceptación de afiliación', pcoMensaje='Señores <br/>[razonSocial]<br/><br/><br/>Reciban un cordial saludo.<br/><br/>Le informamos que la afiliación a la [nombreCCF] fue aceptada satisfactoriamente.<br/><br/><br/>Cordialmente,<br/><br/>[nombreCCF]' where pcoEtiqueta='CARTA_ACEPTACION_EMPLEADOR';