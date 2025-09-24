--liquibase formatted sql

--changeset atoro:01
--comment: Creacion de la tabla BeneficioEmpleador
DROP TABLE Beneficio

CREATE TABLE BeneficioEmpleador
(
 bemId bigint IDENTITY(1,1) NOT NULL,
 bemTipoBeneficio varchar(10) NULL,
 bemBeneficioActivo bit NULL,
 bemFechaBeneficioInicio datetime2(7) NULL,
 bemFechaBeneficioFin datetime2(7) NULL,
 bemMotivoInactivacionBeneficioLey1429 varchar(50) NULL,
 bemMotivoInactivacionBeneficioLey590 varchar(50) NULL,
 bemEmpleador bigint null,
 CONSTRAINT PK_Beneficio_bemId PRIMARY KEY (bemId)
 )
 
 ALTER TABLE BeneficioEmpleador ADD CONSTRAINT FK_BeneficioEmpleador_bemEmpleador FOREIGN KEY (bemEmpleador) REFERENCES Empleador

--changeset atoro:02
--comment: Actualizacion de campo novTipoNovedad en la tabla Novedad
update Novedad set novTipoNovedad='GENERAL' WHERE novTipoTransaccion='AGREGAR_SUCURSAL';

--changeset atoro:03
--comment: Cambio de tipo de dato en la tabla BeneficioEmpleador
ALTER TABLE BeneficioEmpleador ALTER COLUMN bemFechaBeneficioInicio date;
ALTER TABLE BeneficioEmpleador ALTER COLUMN bemFechaBeneficioFin date;




