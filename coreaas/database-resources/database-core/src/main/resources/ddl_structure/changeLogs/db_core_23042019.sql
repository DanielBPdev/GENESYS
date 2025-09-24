--liquibase formatted sql

--changeset squintero:01
--comment: 
INSERT Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato)
VALUES ('FTP_ZENITH_URL_ARCHIVOS_ENTRADA', '/home/adminftp/ZENITH/Entrada', 0, 'FTP_ARCHIVOS_ZENITH', 'Ruta para zenith donde se disponen los archivos de entrada', 'ROUTE');
INSERT Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato)
VALUES ('FTP_ZENITH_URL_ARCHIVOS_SALIDA', '/home/adminftp/ZENITH/Salida', 0, 'FTP_ARCHIVOS_ZENITH', 'Ruta para zenith donde se disponen los archivos de salida', 'ROUTE');
INSERT Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato)
VALUES ('FTP_ZENITH_URL_ARCHIVOS_ERRORES', '/home/adminftp/ZENITH/Errores', 0, 'FTP_ARCHIVOS_ZENITH', 'Ruta para zenith donde se disponen los archivos de error', 'ROUTE');
INSERT Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato)
VALUES ('FTP_ZENITH_NOMBRE_HOST', '35.224.202.68', 0, 'FTP_ARCHIVOS_ZENITH', 'Nombre del host correspondiente al servidor FTP de Zenith', 'IP');
INSERT Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato)
VALUES ('FTP_ZENITH_PUERTO', '22', 0, 'FTP_ARCHIVOS_ZENITH', 'Puerto del servidor FTP de Zenith', 'NUMBER');
INSERT Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato)
VALUES ('FTP_ZENITH_NOMBRE_USUARIO', 'Dg6+mVOTB8UFM3LX4XHiXw==', 0, 'FTP_ARCHIVOS_ZENITH', 'Nombre de usuario para acceder al servidor FTP de Zenith', 'TEXT');
INSERT Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato)
VALUES ('FTP_ZENITH_CONTRASENA', 'xPnc/tNg4jD9DEMLrTSY1g==', 0, 'FTP_ARCHIVOS_ZENITH', 'Contraseña para acceder al servidor FTP de Zenith', 'TEXT');
INSERT Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato)
VALUES ('FTP_ZENITH_PROTOCOLO', 'SFTP', 0, 'FTP_ARCHIVOS_ZENITH', 'Protocolo  de conexión al servidor FTP de Zenith', 'TEXT');