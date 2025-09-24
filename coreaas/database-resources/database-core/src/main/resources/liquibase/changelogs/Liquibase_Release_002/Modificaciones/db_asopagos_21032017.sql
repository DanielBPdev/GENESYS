--liquibase formatted sql

--changeset flopez:01
--comment: Eliminacion de constraints en la tabla empleador 
ALTER TABLE Empleador DROP CONSTRAINT CK_Empleador_empMotivoInactivacionBeneficioLey1429;
ALTER TABLE Empleador DROP CONSTRAINT CK_Empleador_empMotivoInactivacionBeneficioLey590;

--changeset flopez:02
--comment: Eliminacion de columnas en la tabla empleador 
ALTER TABLE Empleador DROP COLUMN empBeneficioLey1429Activo;
ALTER TABLE Empleador DROP COLUMN empBeneficioLey590Activo;
ALTER TABLE Empleador DROP COLUMN empAnoInicioBeneficioLey1429;
ALTER TABLE Empleador DROP COLUMN empAnoFinBeneficioLey1429;
ALTER TABLE Empleador DROP COLUMN empPeriodoInicioBeneficioLey590;
ALTER TABLE Empleador DROP COLUMN empPeriodoFinBeneficioLey590;
ALTER TABLE Empleador DROP COLUMN empMotivoInactivacionBeneficioLey1429;
ALTER TABLE Empleador DROP COLUMN empMotivoInactivacionBeneficioLey590;

--changeset halzate:03
--comment: Actualizacion en VariableComunicado BDAT-Solicitud ejecución e integración scripts por mantis 0220984 
UPDATE VariableComunicado SET vcoTipoVariableComunicado = 'USUARIO' WHERE vcoPlantillaComunicado = (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de bienvenida para empleador') AND (vcoClave = '${responsableCcf}' OR vcoClave = '${responsableAfiliacionCcf}' OR vcoClave = '${cargoResponsableCcf}');