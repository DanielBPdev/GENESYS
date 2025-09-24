--liquibase formatted sql

--changeset  jzambrano:01
--comment: Registros de la tabla  TipoVia

INSERT INTO TipoVia VALUES('Autopista');
INSERT INTO TipoVia VALUES('Avenida');
INSERT INTO TipoVia VALUES('Avenida calle');
INSERT INTO TipoVia VALUES('Avenida carrera');
INSERT INTO TipoVia VALUES('Bulevar');
INSERT INTO TipoVia VALUES('Calle');
INSERT INTO TipoVia VALUES('Carrera');
INSERT INTO TipoVia VALUES('Carretera');
INSERT INTO TipoVia VALUES('Circular');
INSERT INTO TipoVia VALUES('Circunvalar');
INSERT INTO TipoVia VALUES('Comuna');
INSERT INTO TipoVia VALUES('Diagonal');
INSERT INTO TipoVia VALUES('Pasaje');
INSERT INTO TipoVia VALUES('Paseo');
INSERT INTO TipoVia VALUES('Peatonal');
INSERT INTO TipoVia VALUES('Transversal');
INSERT INTO TipoVia VALUES('Troncal');
INSERT INTO TipoVia VALUES('Variante');
INSERT INTO TipoVia VALUES('Vía');

--changeset  jzambrano:02
--comment: Registros de la tabla ElementoDireccion

INSERT INTO ElementoDireccion VALUES('Apartamento');
INSERT INTO ElementoDireccion VALUES('Administración');
INSERT INTO ElementoDireccion VALUES('Aeropuerto');
INSERT INTO ElementoDireccion VALUES('Agrupación');
INSERT INTO ElementoDireccion VALUES('Altillo');
INSERT INTO ElementoDireccion VALUES('Bloque');
INSERT INTO ElementoDireccion VALUES('Bodega');
INSERT INTO ElementoDireccion VALUES('Camino');
INSERT INTO ElementoDireccion VALUES('Carretera');
INSERT INTO ElementoDireccion VALUES('Casa');
INSERT INTO ElementoDireccion VALUES('Célula');
INSERT INTO ElementoDireccion VALUES('Centro comercial');
INSERT INTO ElementoDireccion VALUES('Ciudadela');
INSERT INTO ElementoDireccion VALUES('Conjunto');
INSERT INTO ElementoDireccion VALUES('Conjunto residencial');
INSERT INTO ElementoDireccion VALUES('Comuna');
INSERT INTO ElementoDireccion VALUES('Consultorio');
INSERT INTO ElementoDireccion VALUES('Depósito');
INSERT INTO ElementoDireccion VALUES('Edificio');
INSERT INTO ElementoDireccion VALUES('Entrada');
INSERT INTO ElementoDireccion VALUES('Esquina');
INSERT INTO ElementoDireccion VALUES('Estación');
INSERT INTO ElementoDireccion VALUES('Etapa');
INSERT INTO ElementoDireccion VALUES('Exterior');
INSERT INTO ElementoDireccion VALUES('Finca');
INSERT INTO ElementoDireccion VALUES('Garaje');
INSERT INTO ElementoDireccion VALUES('Garaje sótano');
INSERT INTO ElementoDireccion VALUES('Glorieta');
INSERT INTO ElementoDireccion VALUES('Indicación');
INSERT INTO ElementoDireccion VALUES('Interior');
INSERT INTO ElementoDireccion VALUES('Kilómetro');
INSERT INTO ElementoDireccion VALUES('Local');
INSERT INTO ElementoDireccion VALUES('Local mezanine');
INSERT INTO ElementoDireccion VALUES('Lote');
INSERT INTO ElementoDireccion VALUES('Manzana');
INSERT INTO ElementoDireccion VALUES('Mezanine');
INSERT INTO ElementoDireccion VALUES('Módulo');
INSERT INTO ElementoDireccion VALUES('Oficina');
INSERT INTO ElementoDireccion VALUES('Parcela');
INSERT INTO ElementoDireccion VALUES('Parque');
INSERT INTO ElementoDireccion VALUES('Parqueadero');
INSERT INTO ElementoDireccion VALUES('Paseo');
INSERT INTO ElementoDireccion VALUES('Penthhouse');
INSERT INTO ElementoDireccion VALUES('Piso');
INSERT INTO ElementoDireccion VALUES('Planta');
INSERT INTO ElementoDireccion VALUES('Portería');
INSERT INTO ElementoDireccion VALUES('Predio');
INSERT INTO ElementoDireccion VALUES('Puente');
INSERT INTO ElementoDireccion VALUES('Puesto');
INSERT INTO ElementoDireccion VALUES('Salón Comunal');
INSERT INTO ElementoDireccion VALUES('Sector');
INSERT INTO ElementoDireccion VALUES('Semi sótano');
INSERT INTO ElementoDireccion VALUES('Solar');
INSERT INTO ElementoDireccion VALUES('Sótano');
INSERT INTO ElementoDireccion VALUES('Suite');
INSERT INTO ElementoDireccion VALUES('Súper manzana');
INSERT INTO ElementoDireccion VALUES('Terraza');
INSERT INTO ElementoDireccion VALUES('Torre');
INSERT INTO ElementoDireccion VALUES('Unidad');
INSERT INTO ElementoDireccion VALUES('Unidad residencial');
INSERT INTO ElementoDireccion VALUES('Urbanización');
INSERT INTO ElementoDireccion VALUES('Vereda');
INSERT INTO ElementoDireccion VALUES('Vacío');
INSERT INTO ElementoDireccion VALUES('Zona');

--changeset  lzarate:03
--comment: [HU-122-369]Entidad Parametro
INSERT INTO Parametro (prmNombre,prmValor) VALUES ('122_CORREGIR_INFORMACION_TIMER','1d');

--changeset  alopez:04
--comment: Configuración sedeCajaCompensacion y metodos de asignacion
ALTER TABLE parametrizacionMetodoAsignacion NOCHECK CONSTRAINT FK_ParametrizacionMetodoAsignacion_pmaSedeCajaCompensacion; 
UPDATE parametrizacionMetodoAsignacion SET pmaSedeCajaCompensacion=99  where pmaSedeCajaCompensacion=1
UPDATE parametrizacionMetodoAsignacion SET pmaSedeCajaCompensacion=1  where pmaSedeCajaCompensacion=2;
ALTER TABLE parametrizacionMetodoAsignacion DROP CONSTRAINT FK_ParametrizacionMetodoAsignacion_pmaSedeCajaCompensacion;
truncate table SedeCajaCompensacion;

SET IDENTITY_INSERT [SedeCajaCompensacion] ON;
-- Creación de SedeCajaCompensacion
INSERT [dbo].[SedeCajaCompensacion] ([sccfId],[sccfNombre],[sccfVirtual],[sccCodigo]) VALUES (99,'Sede Virtual',1,'01');
INSERT [dbo].[SedeCajaCompensacion] ([sccfId],[sccfNombre],[sccfVirtual],[sccCodigo]) VALUES (1,'Sede Principal',0,'02');
SET IDENTITY_INSERT [SedeCajaCompensacion] OFF;
GO

ALTER TABLE parametrizacionMetodoAsignacion ADD CONSTRAINT FK_ParametrizacionMetodoAsignacion_pmaSedeCajaCompensacion FOREIGN KEY (pmaSedeCajaCompensacion) REFERENCES SedeCajaCompensacion;

