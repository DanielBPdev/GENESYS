--liquibase formatted sql

--changeset fvasquez:01
--comment: se crea tabla CarteraAgrupadora
CREATE TABLE CarteraAgrupadora_aud(
      cagId bigint not null,
      REV bigint NOT NULL,
	  REVTYPE smallint,
      cagNumeroOperacion bigint not null,
      cagCartera bigint not null      
);

