--liquibase formatted sql

--changeset clmarin:01
--comment:
ALTER TABLE agendacartera_aud ADD agrEsVigente BIT;
ALTER TABLE actividadcartera_aud ADD acrEsVigente BIT;