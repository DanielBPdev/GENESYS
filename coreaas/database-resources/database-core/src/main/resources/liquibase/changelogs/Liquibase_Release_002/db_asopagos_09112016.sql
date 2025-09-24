--liquibase formatted sql

--changeset  sbrinez:01
--comment: Adición del campo FechaRetiro
ALTER TABLE Empleador add empFechaRetiro DATE; 

--changeset  sbrinez:02
--comment: Adición del campo CausalRetiro
ALTER TABLE Empleador add empCausalRetiro VARCHAR(50); 

--changeset  jocampo:03
--comment: Actualización constraseña
UPDATE Parametro set prmValor='sMCiS34j4U8K/dz38vK7WShbbQ8AShXOfP69oGDrwck=' where prmNombre='mail.smtp.password';

--changeset  jcamargo:04
--comment: Inserción en la tabla ValidacionProceso
INSERT INTO ValidacionProceso(vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden)
VALUES ('112-126-1','VALIDACION_EMPLEADOR_BD_CORE','AFILIACION_EMPRESAS_WEB','ACTIVO',1);

--changeset  jocampo:05
--comment: Actualización constraseña
UPDATE Parametro set prmValor='CYk2V/c6iBt+dzaKsrQPXA==' where prmNombre='mail.smtp.password';





