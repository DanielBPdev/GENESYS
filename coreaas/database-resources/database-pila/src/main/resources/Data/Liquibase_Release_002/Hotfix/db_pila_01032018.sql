--liquibase formatted sql

--changeset abaquero:01
--comment:Se adiciona campo en la tabla TemCotizante
ALTER TABLE TemCotizante ADD tctEsTrabajadorReintegrable BIT NULL;