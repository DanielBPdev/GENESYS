--liquibase formatted sql

--changeset Heinsohn:01
--comment: Inserción de PilaClasificacionAportante
INSERT PilaClasificacionAportante (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('I',NULL,NULL,NULL);
INSERT PilaClasificacionAportante (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('I','26','20','MAYOR_IGUAL');
INSERT PilaClasificacionAportante (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('I','26','20','MENOR');
INSERT PilaClasificacionAportante (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('I','35','I','IGUAL');
INSERT PilaClasificacionAportante (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('I','35','A','IGUAL');
INSERT PilaClasificacionAportante (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('I','35','B','IGUAL');
INSERT PilaClasificacionAportante (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('IP',NULL,NULL,NULL);
INSERT PilaClasificacionAportante (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('IP','26','20','MAYOR_IGUAL');
INSERT PilaClasificacionAportante (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('IP','26','20','MENOR');
INSERT PilaClasificacionAportante (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('IP','35','I','IGUAL');
INSERT PilaClasificacionAportante (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('IP','35','A','IGUAL');
INSERT PilaClasificacionAportante (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('IP','35','B','IGUAL');