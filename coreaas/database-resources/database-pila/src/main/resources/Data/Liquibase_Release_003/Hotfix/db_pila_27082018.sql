--liquibase formatted sql

--changeset abaquero:01
--comment:Gestión retroactiva de inconsistencias PILA M1 no gestionadas de planillas ya anuladas 
update pev
set pev.pevEstadoInconsistencia = 'INCONSISTENCIA_GESTIONADA'
from PilaErrorValidacionLog pev
inner join PilaIndicePlanilla pip on pev.pevIndicePlanilla = pip.pipId
where pev.pevEstadoInconsistencia = 'PENDIENTE_GESTION'
and pip.pipEstadoArchivo = 'ANULADO'