--liquibase formatted sql

--changeset mosorio:01
--comment: Adicion de campo en la tabla ConvenioTerceroPagador
ALTER TABLE ConvenioTerceroPagador ADD conUbicacion BIGINT NULL;
ALTER TABLE ConvenioTerceroPagador ADD CONSTRAINT FK_ConvenioTerceroPagador_conUbicacion FOREIGN KEY (conUbicacion) REFERENCES Ubicacion (ubiId); 

--changeset jzambrano:02
--comment: Insercion tabla Parametro
INSERT INTO Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion) VALUES('TIEMPO_REINTEGRO_AFILIADO', '2592000000',0 , 'EJECUCION_TIMER', 'Tiempo que tiene un afiliado para volverse a afiliar sin realizar un nuevo tramite.');