-- Tabla que vincula la tabla Comunicado con la tabla BitacoraCartera 
-- Necesaria para identificar las notificaciones de Niyaraky con nuestros comunicados
CREATE TABLE ComunicadoTransversalBitacora (
    ctbId BIGINT IDENTITY(1,1) PRIMARY KEY,
    ctbBitacoraCartera BIGINT NULL,
    ctbComunicado BIGINT NULL,

    CONSTRAINT FK_ComunicadoTransversalBitacora_BitacoraCartera
        FOREIGN KEY (ctbBitacoraCartera) REFERENCES BitacoraCartera(bcaId),
    CONSTRAINT FK_ComunicadoTransversalBitacora_Comunicado
        FOREIGN KEY (ctbComunicado) REFERENCES Comunicado(comId)
);


