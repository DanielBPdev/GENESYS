--liquibase formatted sql

--changeset atoro:01
--comment:Cambios en la tabla novedades
--Novedad 9      Cambios  en representante legal, su suplente o socios 
UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarRepresentantesNovedad' WHERE novTipoTransaccion='CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_PRESENCIAL' ; 
UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarRepresentantesNovedad' WHERE novTipoTransaccion='CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_WEB' ; 
                
--Novedad 18 Cambios en roles de contacto 
UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizaRolesContactoNovedad' WHERE novTipoTransaccion='CAMBIOS_ROLES_CONTACTO_PRESENCIAL' ; 
UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizaRolesContactoNovedad' WHERE novTipoTransaccion='CAMBIOS_ROLES_CONTACTO_WEB' ; 

--Novedad 19 Cambio de responsable de contacto CCF 
UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarResponsablesCajaNovedad' WHERE novTipoTransaccion='CAMBIO_RESPONSABLE_CONTACTOS_CFF' ; 
