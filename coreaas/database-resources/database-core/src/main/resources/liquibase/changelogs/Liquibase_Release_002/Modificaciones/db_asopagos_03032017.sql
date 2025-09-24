--liquibase formatted sql

--changeset  clamarin:01
--comment:Creacion de tabla categoria (HU-TRA-526)
CREATE TABLE Categoria
(
   catId BIGINT IDENTITY(1,1) NOT NULL,
   catTipoAfiliado VARCHAR(30) NOT NULL,
   catCategoriaPersona VARCHAR(50) NOT NULL,
   catTipoBeneficiario VARCHAR(30),
   catClasificacion VARCHAR(30) NOT NULL,
   catTotalIngresoMesada NUMERIC(19) NOT NULL,
   catFechaCambioCategoria DATETIME NOT NULL,
   catMotivoCambioCategoria VARCHAR(50) NOT NULL,
   catAfiliadoPrincipal BIT NOT NULL,
   catIdAfiliado BIGINT NOT NULL,
   catIdBeneficiario BIGINT,
   CONSTRAINT PK_Categoria_catId PRIMARY KEY (catId) 
 )
 
ALTER TABLE Categoria ADD CONSTRAINT FK_Categoria_catIdAfiliado FOREIGN KEY (catIdAfiliado) REFERENCES Afiliado

ALTER TABLE Categoria ADD CONSTRAINT FK_Categoria_catIdBeneficiario FOREIGN KEY (catIdBeneficiario) REFERENCES Beneficiario

--changeset clamarin:02
--comment: Actualizacion del tipo de la columna catFechaCambioCategoria
ALTER TABLE Categoria ALTER COLUMN catFechaCambioCategoria DATE NOT NULL


--changeset lepaez:03
--comment: FK de SolicitudNovedad con Novedad y eliminacion de columna snoObservaciones
ALTER TABLE SolicitudNovedad ADD CONSTRAINT FK_SolicitudNovedad_snoNovedad FOREIGN KEY (snoNovedad) REFERENCES Novedad
ALTER TABLE SolicitudNovedad DROP COLUMN snoObservaciones
