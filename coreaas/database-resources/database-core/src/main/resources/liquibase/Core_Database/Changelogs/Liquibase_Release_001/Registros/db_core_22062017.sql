--liquibase formatted sql

--changeset rarboleda:01
--comment: Se inserta datos a la tabla Banco
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1000','BANCO DE LA REPUBLICA',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1001','BANCO DE BOGOTA',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1002','BANCO POPULAR',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1006','BANCO CORPBANCA COLOMBIA S.A.',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1007','BANCOLOMBIA',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1008','SCOTIABANK COLOMBIA S.A.',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1009','BANCO CITYBANK COLOMBIA',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1012','BANCO GNB SUDAMERIS',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1013','BBVA COLOMBIA S.A.',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1014','HELM BANK',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1019','BANCO COLPATRIA',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1022','BANCO UNION COLOMBIANO',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1023','BANCO DE OCCIDENTE',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1032','BANCO CAJA SOCIAL BCSC',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1034','BANSUPERIOR',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1036','MEGABANCO S.A.',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1040','BANCO AGRARIO',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1050','BANCAFE',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1051','BANCO DAVIVIENDA',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1052','BANCO AV VILLAS',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1058','BANCO PROCREDIT',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1060','BANCO PICHINCHA S.A',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1061','BANCOOMEVA S.A.',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1062','BANCO FALABELLA S.A.',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1063','BANCO FINANDINA S.A',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1065','BANCO SANTANDER DE NEGOCIOS COLOMBIA S.A.',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1066','BANCO COOPCENTRAL',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1068','BANCO COMPARTIR S.A.',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1076','COOPCENTRAL SA',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1121','FINANCIERA JURISCOOP CF',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1289','COTRAFA COOPERATIVA FINANCIERA',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1292','CONFIAR',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1296','FINANCIERA JURISCOOP',0);
INSERT dbo.Banco (banCodigoPILA,banNombre,banMedioPago) VALUES ('1370','COLTEFINANCIERA S.A',0);

--changeset jocorrea:02 
--comment: Se adiciona parametro
INSERT dbo.Parametro (PRMNOMBRE,PRMVALOR,PRMCARGAINICIO,PRMSUBCATEGORIAPARAMETRO,PRMESTADO) VALUES ('BPMS_PROCESS_PAGO_MAN_APOR_DEPLOYMENTID','com.asopagos.coreaas.bpm.pago_manual_aportes:pago_manual_aportes:0.0.2-SNAPSHOT',0,'BPM_PROCESS',1);

