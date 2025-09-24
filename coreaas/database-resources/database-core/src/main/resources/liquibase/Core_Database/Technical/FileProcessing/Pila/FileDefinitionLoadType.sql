--liquibase formatted sql

--changeset Heinsohn:01
--comment: Inserción de FILEDEFINITIONLOADTYPE
INSERT FILEDEFINITIONLOADTYPE (ID,DESCRIPTION,NAME) VALUES (1,'Información del aportante independiente o  dependiente','infoAporDep.txt');
INSERT FILEDEFINITIONLOADTYPE (ID,DESCRIPTION,NAME) VALUES (2,'Información del aportante pensionado','infoAporPen.txt');
INSERT FILEDEFINITIONLOADTYPE (ID,DESCRIPTION,NAME) VALUES (3,'Detalle de planilla de aportes de independientes o dependientes','detPlanillaDep.txt');
INSERT FILEDEFINITIONLOADTYPE (ID,DESCRIPTION,NAME) VALUES (4,'Detalle de planilla de aportes de pensionados','detPlanillaPen.txt');
INSERT FILEDEFINITIONLOADTYPE (ID,DESCRIPTION,NAME) VALUES (5,'Archivo del Operador Financiero','operadorFinanciero.txt');
