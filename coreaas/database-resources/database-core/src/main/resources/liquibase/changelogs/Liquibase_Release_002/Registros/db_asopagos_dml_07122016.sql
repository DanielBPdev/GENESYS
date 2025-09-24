--liquibase formatted sql

--changeset eamaya:01 
--comment: areNombreUsuario 255
ALTER TABLE AsesorResponsableEmpleador ALTER COLUMN areNombreUsuario VARCHAR(255);

--changeset lzarate:02 
--comment: [HU-112-110] actualizacion campo pmaGrupo ParametrizacionMetodoAsignacion
update ParametrizacionMetodoAsignacion set pmaGrupo = 'back_afiliacion' where pmaProceso = 'AFILIACION_EMPRESAS_WEB';