--liquibase formatted sql

--changeset  jcamargo:01 
--comment: Eliminación de empleador la empCausalRetiro
ALTER TABLE Empleador drop column empCausalRetiro;

--changeset  abaquero:01 
--comment: Adición campos PilaIndicePlanilla
alter table PilaIndicePlanilla add pilapipFechaFtp datetime;
alter table PilaIndicePlanilla add pipCodigoOperadorInformacion varchar(2);

--changeset  abaquero:03 
--comment: Adición campos PilaIndicePlanilla
alter table PilaIndicePlanilla add pipFechaFtp datetime;
alter table PilaIndicePlanilla drop column pilapipFechaFtp;

--changeset  mgiraldo:04 
--comment: Adición UK_Empresa_empPersona
delete from empresa where empId in (select PersonaRepetida.id from 
(select count(emp1.empPersona) cantidad,max(emp1.empId) id ,emp1.empPersona 
 from empresa emp1 group by empPersona  having count(empPersona)>1)   PersonaRepetida);

 ALTER TABLE Empresa ADD CONSTRAINT UK_Empresa_empPersona UNIQUE (empPersona);

--changeset  mgiraldo:05 
--comment: Adición UK_EntidadPagadora_epaEmpresa
delete from entidadpagadora where epaID in (select entidadpagadoraRepetido.id from 
(select count(ent1.epaEmpresa) cantidad,max(ent1.epaId) id ,ent1.epaEmpresa 
 from entidadpagadora ent1 group by epaEmpresa  having count(epaEmpresa)>1)   entidadpagadoraRepetido);

 ALTER TABLE entidadpagadora ADD CONSTRAINT UK_EntidadPagadora_epaEmpresa UNIQUE (epaEmpresa);

--changeset  mgiraldo:06 
--comment: Adición UK_Empleador_empEmpresa
 
delete from empleador where empId in (select EmpleadorRepetido.id from 
(select count(emp1.empEmpresa) cantidad,max(emp1.empId) id ,emp1.empEmpresa 
 from empleador emp1 group by empEmpresa  having count(empEmpresa)>1)   EmpleadorRepetido);

 ALTER TABLE Empleador ADD CONSTRAINT UK_Empleador_empEmpresa UNIQUE (empEmpresa);