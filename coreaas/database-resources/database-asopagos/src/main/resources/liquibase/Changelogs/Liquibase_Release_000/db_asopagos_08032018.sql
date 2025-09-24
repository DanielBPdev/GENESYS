--liquibase formatted sql

--changeset mosanchez:01
--comment:Insercion de registros en la tabla DiasFestivos
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Año Nuevo','2018-01-01');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Festivo por el día de los Reyes Magos','2018-01-08');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Festivo por el día de San José','2018-03-19');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Jueves Santo','2018-03-29');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Viernes Santo','2018-03-30');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Día del trabajo','2018-05-01');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Festivo por el Día de la Ascensión','2018-05-14');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Festivo por el día de Corpus Christi','2018-06-04');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Festivo por el día del Sagrado Corazón de Jesús','2018-06-11');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Festivo por el día de San Pedro y San Pablo','2018-07-02');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Día de la independencia','2018-07-20');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Batalla de Boyacá','2018-08-07');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Festivo por la Asunción de la Virgen','2018-08-20');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Festivo por el Día de la Raza','2018-10-15');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Festivo por el día de todos los Santos','2018-11-05');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Festivo por la Independencia de Cartagena','2018-11-12');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Inmaculada Concepción','2018-12-08');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Navidad','2018-12-25');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Año Nuevo','2019-01-01');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Festivo por el día de los Reyes Magos','2019-01-07');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Festivo por el día de San José','2019-08-25');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Jueves Santo','2018-04-18');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Viernes Santo','2019-04-19');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Día del trabajo','2019-05-01');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Festivo por el Día de la Ascensión','2019-06-03');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Festivo por el día de Corpus Christi','2019-06-24');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Festivo por el día del Sagrado Corazón de Jesús','2019-07-01');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Festivo por el día de San Pedro y San Pablo','2019-07-01');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Día de la independencia','2019-07-20');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Batalla de Boyacá','2019-08-07');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Festivo por la Asunción de la Virgen','2019-08-19');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Festivo por el Día de la Raza','2019-10-14');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Festivo por el día de todos los Santos','2019-11-04');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Festivo por la Independencia de Cartagena','2019-11-11');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Inmaculada Concepción','2019-12-08');
INSERT DiasFestivos (pifConcepto,pifFecha) VALUES ('Navidad','2019-12-25');

--changeset sbrinez:02
--comment:Se modifica tamaño de campo para la tabla Municipio
ALTER TABLE Municipio ALTER COLUMN munCodigo VARCHAR(5) NOT NULL;