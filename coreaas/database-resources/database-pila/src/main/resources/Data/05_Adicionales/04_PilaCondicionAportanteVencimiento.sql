--liquibase formatted sql

--changeset Heinsohn:01
--comment: Inserción de dbo.PilaCondicionAportanteVencimiento
INSERT PilaCondicionAportanteVencimiento (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('I','26','20','MAYOR_IGUAL');
INSERT PilaCondicionAportanteVencimiento (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('I','26','20','MENOR');
INSERT PilaCondicionAportanteVencimiento (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('I','35','I','IGUAL');
INSERT PilaCondicionAportanteVencimiento (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('I','35','A','IGUAL');
INSERT PilaCondicionAportanteVencimiento (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('I','35','B','IGUAL');
INSERT PilaCondicionAportanteVencimiento (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('I','35','C','IGUAL');
INSERT PilaCondicionAportanteVencimiento (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('I','35','D','IGUAL');
INSERT PilaCondicionAportanteVencimiento (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('IP','26','20','MAYOR_IGUAL');
INSERT PilaCondicionAportanteVencimiento (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('IP','26','20','MENOR');
INSERT PilaCondicionAportanteVencimiento (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('IP','35','I','IGUAL');
INSERT PilaCondicionAportanteVencimiento (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('IP','35','A','IGUAL');
INSERT PilaCondicionAportanteVencimiento (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('IP','35','B','IGUAL');
INSERT PilaCondicionAportanteVencimiento (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('IP','35','C','IGUAL');
INSERT PilaCondicionAportanteVencimiento (pcaTipoArchivo,pcaCampo,pcaValor,pcaComparacion) VALUES ('IP','35','D','IGUAL');