--liquibase formatted sql

--changeset jocampo:01
--comment: 
INSERT Parametro
(prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion)
VALUES('VALOR_MINIMO_RETIRO_VENTANILLA','0',0,'CAJA_COMPENSACION','valor mínimo solicitado para generar un retiro por ventanilla de subisidio');

--changeset mamonroy:02
--comment: 
alter table DetalleDatosRegistroValidacion alter column ddrValorDetalle VARCHAR(250);