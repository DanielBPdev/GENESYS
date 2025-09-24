--liquibase formatted sql

--changeset ecastano:01
--comment:Se adicionan campos en la tabla ProyectoSolucionVivienda
ALTER TABLE ProyectoSolucionVivienda ADD psvDisponeCuentaBancaria bit NULL;
ALTER TABLE ProyectoSolucionVivienda ADD psvComparteCuentaOferente bit NULL;

--changeset flopez:02
--comment:Insercion de registros en la tabla 
INSERT ParametrizacionMetodoAsignacion (pmaSedeCajaCompensacion,pmaProceso,pmaMetodoAsignacion,pmaUsuario,pmaGrupo) VALUES (1,'LEGALIZACION_DESEMBOLSO_FOVIS','PREDEFINIDO','edgarnotocar@heinsohn.com.co','BacLegDesFov');
