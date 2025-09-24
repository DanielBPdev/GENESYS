--liquibase formatted sql

--changeset squintero:01
--comment:
INSERT INTO Parametro
(prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato)
VALUES ('CONEXION_SERVICIO_ECM_EXTERNO', 'TRUE', 0, 'CAJA_COMPENSACION', 'Permite activar o desactivar la integración con un ECM externo (actualmente FOLIUM).', 'BOOLEAN');

INSERT INTO Parametro
(prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato)
VALUES ('EMAIL_NOTIFICACION_ERROR_ECM_EXTERNO', 'stevenquintgonz@asopagos.com.co', 0, 'CAJA_COMPENSACION', 'Correo al cual se notifica cuando ocurre un error de comunicación con un ECM externo (actualmente FOLIUM). ', 'EMAIL');