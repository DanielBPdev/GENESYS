--liquibase formatted sql

--changeset jocampo:01
--comment:Se adiciona campo en la tabla Banco y Default
ALTER TABLE Banco ADD banCodigo VARCHAR(6) NULL;
ALTER TABLE Banco ADD CONSTRAINT DF_Banco_banCodigo DEFAULT '000000' FOR banCodigo; 

--changeset jocampo:02
--comment:Se actualizan campos en la tabla Banco
UPDATE Banco SET banCodigo = '000000';
UPDATE Banco SET banCodigo = '570160' WHERE banNombre = 'BANCO AV VILLAS';
UPDATE Banco SET banCodigo = '560011' WHERE banNombre = 'BANCO AGRARIO';
UPDATE Banco SET banCodigo = '560007' WHERE banNombre = 'BANCOLOMBIA';
UPDATE Banco SET banCodigo = '001013' WHERE banNombre = 'BBVA COLOMBIA S.A.';
UPDATE Banco SET banCodigo = '001001' WHERE banNombre = 'BANCO DE BOGOTA';
UPDATE Banco SET banCodigo = '001032' WHERE banNombre = 'BANCO CAJA SOCIAL BCSC';
UPDATE Banco SET banCodigo = '560009' WHERE banNombre = 'BANCO CITYBANK COLOMBIA';
UPDATE Banco SET banCodigo = '560019' WHERE banNombre = 'BANCO COLPATRIA';
UPDATE Banco SET banCodigo = '600001' WHERE banNombre = 'BANCOOMEVA S.A.';
UPDATE Banco SET banCodigo = '560014' WHERE banNombre = 'HELM BANK';
UPDATE Banco SET banCodigo = '589514' WHERE banNombre = 'BANCO DAVIVIENDA';
UPDATE Banco SET banCodigo = '001062' WHERE banNombre = 'BANCO FALABELLA S.A.';
UPDATE Banco SET banCodigo = '001060' WHERE banNombre = 'BANCO PICHINCHA S.A';
UPDATE Banco SET banCodigo = '560023' WHERE banNombre = 'BANCO DE OCCIDENTE';
UPDATE Banco SET banCodigo = '560002' WHERE banNombre = 'BANCO POPULAR';
UPDATE Banco SET banCodigo = '560006' WHERE banNombre = 'BANCO CORPBANCA COLOMBIA S.A.';
UPDATE Banco SET banCodigo = '560012' WHERE banNombre = 'BANCO GNB SUDAMERIS';

--changeset jocampo:03
--comment:Insercion de registor en la tabla Banco
INSERT Banco (banCodigoPILA,banNombre,banMedioPago,banCodigo) VALUES ('0000','COLMENA',0,'570180');
INSERT Banco (banCodigoPILA,banNombre,banMedioPago,banCodigo) VALUES ('0000','HSBC',0,'560010');
