--liquibase formatted sql

--changeset Heinsohn:1 stripComments:true


INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Carta entidad pagadora','CARTA_ENTIDAD_PAGADORA');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificación de radicación de solicitud de afiliación de persona','NOTIFICACION_RADICACION_SOLICITUD_AFILIACION_PERSONA');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificación de intento de afiliación','NOTIFICACION_INTENTO_AFILIACION');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Carta de bienvenida para empleador','CARTA_BIENVENIDA_EMPLEADOR');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Carta de aceptación de empleador','CARTA_ACEPTACION_EMPLEADOR');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Rechazo de solicitud de afiliación de empresa','RECHAZO_SOLICITUD_AFILIACION_EMPRESA');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Rechazo de solicitud de afiliación de empleador inconsistencia validación','RECHAZO_SOLICITUD_AFILIACION_EMPLEADOR_INCONSITENCIA_VALIDACION');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificación para requerir subsanación de solicitud de afiliación de empleador','NOTIFICACION_PARA_REQUERIR_SUBSANACION_SOLICITUD_AFILIACION_EMPLEADOR');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanado','RECHAZO_SOLICITUD_AFILIACION_EMPLEADOR_POR_PRODUCTO_NO_CONFORME_NO_SUBSANADO');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable','RECHAZO_SOLICITUD_AFILIACION_EMPLEADOR_POR_PRODUCTO_NO_CONFORME_NO_SUBSANABLE');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Rechazo de solicitud de afiliación de trabajador dependiente inconsistencia validación caso trabajador','RECHAZO_SOLICITUD_AFILIACION_TRABAJADOR_DEPENDIENTE_INCONSISTENCIA_VALIDACION_CASO_TRABAJADOR');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Rechazo de solicitud de afiliación de trabajador dependiente inconsistencia validación caso empleador','RECHAZO_SOLICITUD_AFILIACION_TRABAJADOR_DEPENDIENTE_INCONSISTENCIA_VALIDACION_CASO_EMPLEADOR');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Rechazo de solicitud de afiliación de trabajador dependiente por producto no conforme no subsanado dirigida a trabajador','RECHAZO_SOLICI_AFILIACI_TRABAJADOR_DEPENDIENTE_PRODUCTO_NO_CONFORME_NO_SUBSANADO_DIRIGIDA_TRABAJADOR');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Rechazo de solicitud de afiliación de trabajador dependiente por producto no conforme no subsanado dirigida a empleador','RECHAZO_SOLICI_AFILIACI_TRABAJADOR_DEPENDIENTE_PRODUCTO_NO_CONFORME_NO_SUBSANADO_DIRIGIDA_EMPLEADOR');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Rechazo de solicitud de afiliación de trabajador dependiente por producto no conforme no subsanable dirigida a trabajador','RECHAZO_SOLICI_AFILIAC_TRABAJADOR_DEPENDIENTE_PRODUCTO_NO_CONFORME_NO_SUBSANABLE_DIRIGIDA_TRABAJADOR');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Rechazo de solicitud de afiliación de trabajador dependiente por producto no conforme no subsanable dirigida a empleador','RECHAZO_SOLICI_AFILIACI_TRABAJADOR_DEPENDIENTE_PRODUCTO_NO_CONFORME_NO_SUBSANABLE_DIRIGIDA_EMPLEADOR');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificación para empleador de resultados de afiliación múltiple trabajadores web','NOTIFICACION_EMPLEADOR_RESULTADOS_AFILIACION_MULTIPLE_TRABAJADORES_WEB');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificación individual de resultado de afiliación trabajador web (trabajador)','NOTIFICACION_INDIVIDUAL_RESULTADO_AFILIACION_TRABAJADOR_WEB_TRABAJADOR');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificación para requerir subsanación de solicitud de afiliación de trabajador dependiente','NOTIFICACION_REQUERIR_SUBSANACION_SOLICITUD_AFILIACION_TRABAJADOR_DEPENDIENTE');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificación individual de resultados de afiliación de trabajadores web (empleador)','NOTIFICACION_INDIVIDUAL_RESULTADOS_AFILIACION_TRABAJADORES_WEB_EMPLEADOR');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificación de resultados del registro de beneficiarios en la solicitud web de una trabajador dirigida al trabajador','NOTIFICACION_RESULTADOS_REGISTRO_BENEFICIARIOS_SOLICITUD_WEB_UNA_TRABAJADOR_DIRIGIDA_TRABAJADOR');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificación de resultados del registro de beneficiarios en la solicitud web de una trabajador dirigida al empleador','NOTIFICACION_RESULTADOS_REGISTRO_BENEFICIARIOS_SOLICITUD_WEB_UNA_TRABAJADOR_DIRIGIDA_EMPLEADOR');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificación de enrolamiento afiliación independiente web','NOTIFICACION_ENROLAMIENTO_AFILIACION_INDEPENDIENTE_WEB');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificación de enrolamiento afiliación pensionado web','NOTIFICACION_ENROLAMIENTO_AFILIACION_PENSIONADO_WEB');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificacion de aceptación de afiliacion pensionado despues de subsanacion','NOTIFICACION_ACEPTACION_AFILIACION_PENSIONADO_DESPUES_SUBSANACION');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificacion de rechazo de afiliacion pensionado despues de subsanacion','NOTIFICACION_RECHAZO_AFILIACION_PENSIONADO_DESPUES_SUBSANACION');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificacion de aceptación de afiliacion de independiente despues de subsanacion','NOTIFICACION_ACEPTACION_AFILIACION_INDEPENDIENTE_DESPUES_SUBSANACION');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificacion de rechazo de afiliacion de independiente despues de subsanacion','NOTIFICACION_RECHAZO_AFILIACION_INDEPENDIENTE_DESPUES_SUBSANACION');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Rechazo de solicitud de afiliación de pensionado inconsistencia validación','RECHAZO_SOLICITUD_AFILIACION_PENSIONADO_INCONSISTENCIA_VALIDACION');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Rechazo de solicitud de afiliación de independiente inconsistencia validación','RECHAZO_SOLICITUD_AFILIACION_INDEPENDIENTE_INCONSISTENCIA_VALIDACION');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Rechazo de solicitud de afiliación de pensionado por producto no conforme no subsanable','RECHAZO_SOLICITUD_AFILIACION_PENSIONADO_PRODUCTO_NO_CONFORME_NO_SUBSANABLE');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Rechazo de solicitud de afiliación de independiente por producto no conforme no subsanable','RECHAZO_SOLICITUD_AFILIACION_INDEPENDIENTE_PRODUCTO_NO_CONFORME_NO_SUBSANABLE');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificación para requerir subsanación de solicitud de afiliación de pensionado','NOTIFICACION_REQUERIR_SUBSANACION_SOLICITUD_AFILIACION_PENSIONADO');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificación para requerir subsanación de solicitud de afiliación de independiente','NOTIFICACION_REQUERIR_SUBSANACION_SOLICITUD_AFILIACION_INDEPENDIENTE');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Rechazo de solicitud de afiliación de pensionado por producto no conforme no subsanado','RECHAZO_SOLICITUD_AFILIACION_PENSIONADO_PRODUCTO_NO_CONFORME_NO_SUBSANADO');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Rechazo de solicitud de afiliación de independiente por producto no conforme no subsanado','RECHAZO_SOLICITUD_AFILIACION_INDEPENDIENTE_PRODUCTO_NO_CONFORME_NO_SUBSANADO');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificación de radicación solicitud de afiliación independiente web','NOTIFICACION_RADICACION_SOLICITUD_AFILIACION_INDEPENDIENTE_WEB');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificación de radicación solicitud de afiliación pensionado web','NOTIFICACION_RADICACION_SOLICITUD_AFILIACION_PENSIONADO_WEB');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Carta de bienvenida para independiente','CARTA_BIENVENIDA_INDEPENDIENTE');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Carta de bienvenida para pensionado','CARTA_BIENVENIDA_PENSIONADO');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Carta de aceptación de independiente','CARTA_ACEPTACION_INDEPENDIENTE');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Carta de aceptación de pensionado','CARTA_ACEPTACION_PENSIONADO');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificación de radicación solicitud de afiliación empleador web','NOTIFICACION_RADICACION_SOLICITUD_AFILIACION_EMPLEADOR_WEB');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificación de enrolamiento afiliación empleador web','NOTIFICACION_ENROLAMIENTO_AFILIACION_EMPLEADOR_WEB');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificación de resultados del registro de beneficiarios en la solicitud web para independiente','NOTIFICACION_RESULTADOS_REGISTRO_BENEFICIARIOS_SOLICITUD_WEB_INDEPENDIENTE');
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta) VALUES ('Notificación de resultados del registro de beneficiarios en la solicitud web para pensionado','NOTIFICACION_RESULTADOS_REGISTRO_BENEFICIARIOS_SOLICITUD_WEB_PENSIONADO');

GO