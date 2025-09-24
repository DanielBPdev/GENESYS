--liquibase formatted sql

--changeset rlopez:01
--comment: Se eliminan registros duplicados en la tabla GRAPHICFEATURE
DELETE FROM GRAPHICFEATURE WHERE NAME='delimitador' and DEFAULTVALUE=',' and DESCRIPTION='delimitador de campo' and DATATYPE='CHAR' and FILEFORMAT='DELIMITED_TEXT_PLAIN' and RESTRICTIONS=null;

--changeset rlopez:02
--comment: Se inserta registro en la tabla GRAPHICFEATURE
INSERT GRAPHICFEATURE (NAME,DEFAULTVALUE,DESCRIPTION,DATATYPE,FILEFORMAT,RESTRICTIONS) VALUES ('delimitador',',','delimitador de campo','CHAR','DELIMITED_TEXT_PLAIN',null);

--changeset rlopez:03
--comment: Se eliminan registros duplicados en la tabla GRAPHICFEATURE
DELETE FROM GRAPHICFEATURE WHERE NAME='delimitador' and DESCRIPTION='delimitador de campo' and DATATYPE='CHAR' and FILEFORMAT='DELIMITED_TEXT_PLAIN';

--changeset rlopez:04
--comment: Se inserta registro en la tabla GRAPHICFEATURE
INSERT GRAPHICFEATURE (NAME,DEFAULTVALUE,DESCRIPTION,DATATYPE,FILEFORMAT,RESTRICTIONS) VALUES ('delimitador',',','delimitador de campo','CHAR','DELIMITED_TEXT_PLAIN',null);