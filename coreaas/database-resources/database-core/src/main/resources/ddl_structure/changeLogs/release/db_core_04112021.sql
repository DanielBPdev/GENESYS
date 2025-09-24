--liquibase formatted sql

--changeset jamartinez:03
--comment: Creación sentencias tablas ajustadas Cruces   

IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name ='SEC_CargueArchivoCruceFovisUnidos')
CREATE SEQUENCE [dbo].[SEC_CargueArchivoCruceFovisUnidos] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 

IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name ='SEC_CargueArchivoCruceFovisReunidos')
CREATE SEQUENCE [dbo].[SEC_CargueArchivoCruceFovisReunidos] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 

IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name ='SEC_CargueArchivoCruceFovisSisben')
CREATE SEQUENCE [dbo].[SEC_CargueArchivoCruceFovisSisben] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 

IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name ='SEC_CargueArchivoCruceFovisCatastros')
CREATE SEQUENCE [dbo].[SEC_CargueArchivoCruceFovisCatastros] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 

IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name ='SEC_CargueArchivoCruceFovisBeneficiarioArriendo')
CREATE SEQUENCE [dbo].[SEC_CargueArchivoCruceFovisBeneficiarioArriendo] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 

IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name ='SEC_CargueArchivoCruceFovisBeneficiario')
CREATE SEQUENCE [dbo].[SEC_CargueArchivoCruceFovisBeneficiario] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 

IF NOT EXISTS (SELECT * FROM sys.sequences WHERE name ='SEC_CargueArchivoCruceFovisAfiliado')
CREATE SEQUENCE [dbo].[SEC_CargueArchivoCruceFovisAfiliado] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
