--liquibase formatted sql

--changeset arocha:01
--comment: Creacion indice pila.aporte
CREATE INDEX IX_pila_Aporte_appTransaccion ON pila.Aporte(appTransaccion)
