--liquibase formatted sql

--changeset arocha:01 context:integracion failOnError:false
--comment: Configuracion del ambiente integración
CREATE CREDENTIAL ##xp_cmdshell_proxy_account## with IDENTITY = 'HBT-Asopag-DB\pila-ssis', SECRET = 'Ld3frT2#'

--changeset arocha:02 context:pruebas failOnError:false
--comment: Configuracion del ambiente pruebas
CREATE CREDENTIAL ##xp_cmdshell_proxy_account## with IDENTITY = 'HBT-Asopag-Des1\pila-ssis', SECRET = 'Fe4Wsk&'

--changeset arocha:03 context:pruebas-asopagos failOnError:false
--comment: Configuracion del ambiente pruebas asopagos
CREATE CREDENTIAL ##xp_cmdshell_proxy_account## with IDENTITY = 'HBT-Asopag-Des2\pila-ssis', SECRET = 'w7cV1b.'
