--liquibase formatted sql

--changeset fvasquez:01
--comment: se crea tabla CarteraAgrupadora
CREATE TABLE CarteraAgrupadora(
      cagId bigint identity(1,1) not null, -- Llave primaria
      cagNumeroOperacion bigint not null, -- Número de operación
      cagCartera bigint not null, -- Referencia al campo carId en Cartera
      CONSTRAINT PK_CarteraAgrupadora_cagId PRIMARY KEY (cagId)
)

 --changeset fvasquez:02
--comment: se crea tabla CarteraAgrupadora
CREATE TABLE aud.CarteraAgrupadora_aud(
      cagId bigint not null, -- Llave primaria
      REV bigint NOT NULL,
	  REVTYPE smallint,
      cagNumeroOperacion bigint not null, -- Número de operación
      cagCartera bigint not null, -- Referencia al campo carId en Cartera
      CONSTRAINT FK_CarteraAgrupadora_aud_REV FOREIGN KEY (REV) REFERENCES aud.Revision(revId)
)

--changeset abaquero:01
--comment: CC 0244937, se cambia la obligatoriedad de los campos de nombre de rep. legal de archivos A y AP de PILA
update FieldDefinitionLoad set required = 0 where id in (2110021,2110023,2110055,2110057)