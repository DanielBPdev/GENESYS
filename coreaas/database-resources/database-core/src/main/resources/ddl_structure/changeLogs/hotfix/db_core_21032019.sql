--liquibase formatted sql

--changeset mamonroy:01
--comment: 
--3
DELETE FROM PrioridadDestinatario WHERE prdDestinatarioComunicado = 315 AND prdGrupoPrioridad IN (54,55);
DELETE FROM DestinatarioComunicado WHERE dcoProceso = 'AFILIACION_EMPRESAS_PRESENCIAL' AND dcoEtiquetaPlantilla = 'NTF_INT_AFL_DEP';

--44
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (306,70,4);

--45
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (305,70,4);

--49
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (311,70,4);

--50
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (308,70,4);

--51
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (307,70,4);

--52
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (310,70,4);

--53
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (309,70,4);

--54
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (313,70,4);

--55
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (312,70,4);

--61
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (319,55,3);

--62
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (325,72,3);

--63
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (324,72,3);

--66
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (322,54,4);

--67
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (327,72,3);
DELETE FROM PrioridadDestinatario WHERE prdDestinatarioComunicado = 327 AND prdGrupoPrioridad IN (56,57,58);

--68
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (323,72,3);

--77
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (328,70,3);

--79
DELETE FROM PrioridadDestinatario WHERE prdDestinatarioComunicado = 330 AND prdGrupoPrioridad IN (54,55);
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (330,70,1);

--80
DELETE FROM PrioridadDestinatario WHERE prdDestinatarioComunicado = 331 AND prdGrupoPrioridad IN (54,55);
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (331,70,1);

--89
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (527,70,1);

--92
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (526,70,1);

--93
DELETE FROM PrioridadDestinatario WHERE prdDestinatarioComunicado = 442 AND prdGrupoPrioridad IN (70);
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (442,57,3);

--99
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (348,57,4);

--100
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (349,57,4);

--105
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (371,63,5);

--106
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (374,63,5);

--107
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (372,63,5);

--108
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (375,63,5);

--109
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (373,63,5);

--118
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (531,76,1);

--134
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (355,74,4);
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (355,72,5);

--135
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (356,74,4);
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (356,72,5);

--137
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (357,74,0);
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (357,72,0);

--138
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (358,74,4);
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (358,72,5);

--139
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (359,74,4);
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (359,72,5);

--160
DELETE FROM PrioridadDestinatario WHERE prdDestinatarioComunicado = 378 AND prdGrupoPrioridad IN (63);
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (378,71,1);

--162
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (377,63,2);
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (377,62,3);
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (377,54,4);
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (377,55,5);

--163
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (376,63,2);
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (376,62,3);
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (376,54,4);
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (376,55,5);


