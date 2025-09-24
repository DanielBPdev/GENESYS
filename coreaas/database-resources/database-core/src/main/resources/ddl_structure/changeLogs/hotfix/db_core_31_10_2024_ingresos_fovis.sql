IF NOT EXISTS (select * from INFORMATION_SCHEMA.TABLES T WHERE TABLE_NAME = 'CalculoIngresosFovis')
BEGIN
    CREATE TABLE CalculoIngresosFovis (
        cifId INT IDENTITY(1, 1),
        cifPersona BIGINT,
        cifSumaSalario numeric(19,2),
        cifFechaCalculo DATETIME,
        cifIdPostulacion BIGINT,
        CONSTRAINT FK_CalculoIngresosFovis_Persona FOREIGN KEY (cifPersona)
            REFERENCES Persona(perId),
            CONSTRAINT FK_CalculoIngresosFovis_PostulacionFovis FOREIGN KEY (cifIdPostulacion)
            REFERENCES PostulacionFOVIS(pofId)
    );
END