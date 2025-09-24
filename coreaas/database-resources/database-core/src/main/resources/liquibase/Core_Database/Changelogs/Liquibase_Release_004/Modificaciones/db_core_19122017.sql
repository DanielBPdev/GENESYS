--liquibase formatted sql

--changeset abaquero:01
--comment:Se adiciona campo en la tabla AporteGeneral
ALTER TABLE AporteGeneral ADD apgEmpresaTramitadoraAporte BIGINT NULL;
ALTER TABLE AporteGeneral ADD CONSTRAINT FK_AporteGeneral_apgEmpresaTramitadoraAporte FOREIGN KEY (apgEmpresaTramitadoraAporte) REFERENCES Empresa (empId);