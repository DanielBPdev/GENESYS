--liquibase formatted sql

--changeset jzambrano:01
--comment: Cambio en el tipo de dato en campos de la tabla ConsolaEstadoCargueMasivo
ALTER TABLE ConsolaEstadoCargueMasivo ALTER COLUMN cecFechaInicio datetime2(7) NULL  
ALTER TABLE ConsolaEstadoCargueMasivo ALTER COLUMN cecFechaFin datetime2(7) NULL 

--changeset flopez:02
--comment: Actualizacion en la tabla Novedad
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarRetiroNovedadPersona' WHERE novTipoTransaccion IN ('RETIRO_TRABAJADOR_DEPENDIENTE','RETIRO_TRABAJADOR_INDEPENDIENTE','RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','RETIRO_PENSIONADO_MAYOR_1_5SM_2','RETIRO_PENSIONADO_MENOR_1_5SM_0','RETIRO_PENSIONADO_MENOR_1_5SM_0_6','RETIRO_PENSIONADO_MENOR_1_5SM_2','RETIRO_PENSIONADO_PENSION_FAMILIAR')