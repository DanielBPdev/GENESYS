--liquibase formatted sql

--changeset Heinsohn:01
--comment: Inserción de dbo.PilaClasificacionAportante
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('I', '26', '20', 'MAYOR_IGUAL');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('I', '26', '20', 'MENOR');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('I', '35', 'I', 'IGUAL');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('I', '35', 'A', 'IGUAL');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('I', '35', 'B', 'IGUAL');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('I', '35', 'C', 'IGUAL');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('I', '35', 'D', 'IGUAL');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('IP', '26', '20', 'MAYOR_IGUAL');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('IP', '26', '20', 'MENOR');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('IP', '35', 'I', 'IGUAL');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('IP', '35', 'A', 'IGUAL');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('IP', '35', 'B', 'IGUAL');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('IP', '35', 'C', 'IGUAL');
insert into PilaClasificacionAportante (pcaTipoArchivo, pcaCampo, pcaValor, pcaComparacion) values ('IP', '35', 'D', 'IGUAL');