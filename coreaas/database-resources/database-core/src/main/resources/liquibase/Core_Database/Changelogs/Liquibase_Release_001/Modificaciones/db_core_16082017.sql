--liquibase formatted sql

--changeset hhernandez:01
--comment: Se elimina la tabla ActualizacionDatosEmpleador y se crea la tabla GestionNotiNoEnviadas
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ActualizacionDatosEmpleador_adeCanalContacto')) ALTER TABLE ActualizacionDatosEmpleador DROP CONSTRAINT CK_ActualizacionDatosEmpleador_adeCanalContacto;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ActualizacionDatosEmpleador_adeEstadoGestion')) ALTER TABLE ActualizacionDatosEmpleador DROP CONSTRAINT CK_ActualizacionDatosEmpleador_adeTipoInconsistencia;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ActualizacionDatosEmpleador_adeTipoInconsistencia')) ALTER TABLE ActualizacionDatosEmpleador DROP CONSTRAINT CK_ActualizacionDatosEmpleador_adeTipoInconsistencia;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'ActualizacionDatosEmpleador')) DROP TABLE ActualizacionDatosEmpleador;