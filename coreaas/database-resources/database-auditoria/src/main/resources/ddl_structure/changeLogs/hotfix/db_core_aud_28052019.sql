--liquibase formatted sql

--changeset mamonroy:01
--comment:
ALTER TABLE BeneficioEmpleador_aud ADD bemPerteneceDepartamento bit;