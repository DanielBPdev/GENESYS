--liquibase formatted sql


--changeset  abaquero:01
--comment: Pila Archivo Registro3

drop table dbo.PilaArchivoIRegistro3; 

create table dbo.PilaArchivoIRegistro3(
pi3Id bigint NOT NULL IDENTITY,
pi3IdIndice bigint NOT NULL,
pi3ValorTotalIBC bigint,
pi3ValorTotalAporteObligatorio bigint,
pi3DiasMora smallint,
pi3ValorMora bigint,
pi3ValorTotalAportes bigint,
CONSTRAINT PK_PilaArchivoIRegistro3_pi3Id PRIMARY KEY (pi3Id)
);