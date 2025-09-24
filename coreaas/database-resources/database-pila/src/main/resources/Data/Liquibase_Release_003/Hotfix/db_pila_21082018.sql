--liquibase formatted sql

--changeset abaquero:01
--comment:Se añaden los campos de fecha para novedad VST para aportes manuales en RegistroDetallado
ALTER TABLE staging.RegistroDetallado ADD redFechaInicioVST date
ALTER TABLE staging.RegistroDetallado ADD redFechaFinVST date

--changeset abaquero:02
--comment:Se añaden los campos de fecha de actualización de cada bloque de validación
ALTER TABLE dbo.PilaEstadoBloque ADD pebFechaBloque0 DATETIME
ALTER TABLE dbo.PilaEstadoBloque ADD pebFechaBloque1 DATETIME
ALTER TABLE dbo.PilaEstadoBloque ADD pebFechaBloque2 DATETIME
ALTER TABLE dbo.PilaEstadoBloque ADD pebFechaBloque3 DATETIME
ALTER TABLE dbo.PilaEstadoBloque ADD pebFechaBloque4 DATETIME
ALTER TABLE dbo.PilaEstadoBloque ADD pebFechaBloque5 DATETIME
ALTER TABLE dbo.PilaEstadoBloque ADD pebFechaBloque6 DATETIME
ALTER TABLE dbo.PilaEstadoBloque ADD pebFechaBloque7 DATETIME
ALTER TABLE dbo.PilaEstadoBloque ADD pebFechaBloque8 DATETIME
ALTER TABLE dbo.PilaEstadoBloque ADD pebFechaBloque9 DATETIME
ALTER TABLE dbo.PilaEstadoBloque ADD pebFechaBloque10 DATETIME
ALTER TABLE dbo.PilaEstadoBloqueOF ADD peoFechaBloque0 DATETIME
ALTER TABLE dbo.PilaEstadoBloqueOF ADD peoFechaBloque1 DATETIME
ALTER TABLE dbo.PilaEstadoBloqueOF ADD peoFechaBloque6 DATETIME

--changeset abaquero:03
--comment:Se elimina el campo peoIdLogLecturaBloque1 de PilaEstadoBloqueOF por no uso
ALTER TABLE dbo.PilaEstadoBloqueOF DROP COLUMN peoIdLogLecturaBloque1