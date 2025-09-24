ALTER TABLE Categoria_aud
ALTER COLUMN catClasificacion VARCHAR(50) NULL;
ALTER TABLE Categoria_aud
ALTER COLUMN catTotalIngresoMesada NUMERIC(9,0) NULL;

ALTER TABLE Categoria_aud
ALTER COLUMN catFechaCambioCategoria DATETIME NOT NULL;