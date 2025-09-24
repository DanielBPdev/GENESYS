--liquibase formatted sql

--changeset jroa:01
--comment:Creacion de la tabla InicioTarea
CREATE TABLE InicioTarea(
    itaId BIGINT IDENTITY(1,1) NOT NULL,
    itaFecha BIGINT NULL,
    itaProceso VARCHAR(255) NULL,
    itaTarea VARCHAR(255) NULL,
    itaUsuario VARCHAR(255) NULL,
CONSTRAINT PK_InicioTarea_idaId PRIMARY KEY (itaId)
);

--changeset jocorrea:02
--comment:Insercion de registros en la tabla Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('NIT_CONFA','890806490',0,'VALOR_GLOBAL_NEGOCIO','Numero de identificacion de la CCF CONFA');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('NIT_COLPENSIONES','900336004',0,'VALOR_GLOBAL_NEGOCIO','Numero de identificacion de la entidad COLPENSIONES');
 