--liquibase formatted sql


--changeset  abaquero:01
--comment:Adición de pepIdCcf de PilaEjecucionProgramada
ALTER TABLE PilaEjecucionProgramada ADD pepIdCcf int;
ALTER TABLE PilaEjecucionProgramada ADD CONSTRAINT [FK_PilaEjecucionProgramada_pepIdCcf] FOREIGN KEY([pepIdCcf]) REFERENCES [CajaCompensacion] ([ccfId]); 