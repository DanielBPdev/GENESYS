if NOT EXISTS (select * from Constante where cnsNombre = 'CONEXION_ANIBOL')
BEGIN
    INSERT INTO Constante (cnsNombre, cnsValor, cnsDescripcion, cnsTipoDato)
    VALUES ('CONEXION_ANIBOL', 'FALSE', 'Identificador de conexion a cliente Anibol', 'TEXT');
END