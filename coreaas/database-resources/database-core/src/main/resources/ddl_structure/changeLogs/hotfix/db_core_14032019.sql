--liquibase formatted sql

--changeset dsuesca:01
--comment: 
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND OBJECT_ID = OBJECT_ID('dbo.USP_SM_GET_CrearRevision'))
   exec('DROP PROCEDURE [dbo].[USP_SM_GET_CrearRevision]')
