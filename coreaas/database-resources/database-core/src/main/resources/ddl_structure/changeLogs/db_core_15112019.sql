--liquibase formatted sql

--changeset squintero:01
--comment:
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado ,pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) 
VALUES ('Notificación - Fallo en el envío de Información a Folium','Cuerpo','Encabezado',NULL,'<p>${contenido}</p>','Notificación - Fallo en el envío de Información a Folium','Pie','NTF_FLL_ENV_INFO_FOL');

INSERT INTO Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato)
VALUES ('EMAIL_ADMINISTRADOR_DEL_SISTEMA', 'stevenquintgonz@asopagos.com.co', 0, 'CAJA_COMPENSACION', 'Correo del administrador del sistema. ', 'EMAIL');
