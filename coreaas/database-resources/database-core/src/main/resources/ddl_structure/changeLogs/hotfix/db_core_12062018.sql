--liquibase formatted sql

--changeset jvelandia:01
--comment: Actualizacion tabla VariableComunicado
UPDATE VariableComunicado SET vcoClave='${estadoDeLaSolicitud}' WHERE vcoClave='${fechaYHoraDeAsignacion}' AND vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_13_418_PERSONA');

--changeset mosorio:02
--comment: Se actualizan nits de bancos
UPDATE Banco SET banNit ='860005216-7' WHERE banNombre = 'BANCO DE LA REPUBLICA';
UPDATE Banco SET banNit ='860002964-4' WHERE banNombre = 'BANCO DE BOGOTA';
UPDATE Banco SET banNit ='860007738-9' WHERE banNombre = 'BANCO POPULAR';
UPDATE Banco SET banNit ='890903937-0' WHERE banNombre = 'BANCO CORPBANCA COLOMBIA S.A.';
UPDATE Banco SET banNit ='890903938-8' WHERE banNombre = 'BANCOLOMBIA';
UPDATE Banco SET banNit ='860051135-4' WHERE banNombre = 'BANCO CITYBANK COLOMBIA';
UPDATE Banco SET banNit ='860050750-1' WHERE banNombre = 'BANCO GNB SUDAMERIS';
UPDATE Banco SET banNit ='860003020-1' WHERE banNombre = 'BBVA COLOMBIA S.A.';
UPDATE Banco SET banNit ='860034594-1' WHERE banNombre = 'BANCO COLPATRIA';
UPDATE Banco SET banNit ='890300279-4' WHERE banNombre = 'BANCO DE OCCIDENTE';
UPDATE Banco SET banNit ='860007335-4' WHERE banNombre = 'BANCO CAJA SOCIAL BCSC';
UPDATE Banco SET banNit ='800037800-8' WHERE banNombre = 'BANCO AGRARIO';
UPDATE Banco SET banNit ='860034313-7' WHERE banNombre = 'BANCO DAVIVIENDA';
UPDATE Banco SET banNit ='860035827-5' WHERE banNombre = 'BANCO AV VILLAS';
UPDATE Banco SET banNit ='900200960-9' WHERE banNombre = 'BANCO PROCREDIT';
UPDATE Banco SET banNit ='890200756-7' WHERE banNombre = 'BANCO PICHINCHA S.A';
UPDATE Banco SET banNit ='900406150-5' WHERE banNombre = 'BANCOOMEVA S.A.';
UPDATE Banco SET banNit ='900047981-8' WHERE banNombre = 'BANCO FALABELLA S.A.';
UPDATE Banco SET banNit ='860051894-6' WHERE banNombre = 'BANCO FINANDINA S.A';
UPDATE Banco SET banNit ='900628110-3' WHERE banNombre = 'BANCO SANTANDER DE NEGOCIOS COLOMBIA S.A.';
UPDATE Banco SET banNit ='890203088-9' WHERE banNombre = 'BANCO COOPCENTRAL';
UPDATE Banco SET banNit ='860025971-5' WHERE banNombre = 'BANCO COMPARTIR S.A.';
UPDATE Banco SET banNit ='900688066-3' WHERE banNombre = 'FINANCIERA JURISCOOP CF';
UPDATE Banco SET banNit ='890901176-3' WHERE banNombre = 'COTRAFA COOPERATIVA FINANCIERA';
UPDATE Banco SET banNit ='890981395-1' WHERE banNombre = 'CONFIAR';
UPDATE Banco SET banNit ='890927034-9' WHERE banNombre = 'COLTEFINANCIERA S.A';
