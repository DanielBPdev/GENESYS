--liquibase formatted sql

--changeset jzambrano:01
--comment: Se actualizan registros de la tabla ValidacionProceso
--VALIDACIONES PARA SOLICITUDES SIMULTANEAS PERSONAS 122-361 -Dependiente 
UPDATE ValidacionProceso SET vapProceso='AFILIACION_DEPENDIENTE_WEB' WHERE vapBloque LIKE '122-361-1%';

--VALIDACIONES PARA SOLICITUDES SIMULTANEAS PERSONAS 122-359 -Dependiente 
UPDATE ValidacionProceso SET vapProceso='AFILIACION_DEPENDIENTE_WEB' WHERE vapBloque LIKE '122-359-1%';

--VALIDACIONES PARA SOLICITUDES SIMULTANEAS PERSONAS 123-379 -PENSIONADOS-INDEPENDIENTES BLOQUES AFECTADOS reintegro
DELETE ValidacionProceso WHERE vapBloque='123-379-1' AND vapProceso='AFILIACION_PERSONAS_PRESENCIAL';

--VALIDACIONES PARA SOLICITUDES SIMULTANEAS PERSONAS 122-363-1 -Dependiente
DELETE ValidacionProceso WHERE vapBloque LIKE '122-363-1%' AND vapProceso='AFILIACION_PERSONAS_PRESENCIAL';

--changeset hhernandez:02
--comment: Se agrega campo y altera en la tabla AporteDetallado y se actualiza registros
ALTER TABLE AporteDetallado ADD apdUsuarioAprobadorAporte VARCHAR(50);
UPDATE AporteDetallado SET apdUsuarioAprobadorAporte = 'SISTEMA';
ALTER TABLE AporteDetallado ALTER COLUMN apdUsuarioAprobadorAporte VARCHAR(50) NOT NULL;
ALTER TABLE AporteDetallado ADD apdEstadoRegistroAporteArchivo VARCHAR(60);
UPDATE AporteDetallado SET apdEstadoRegistroAporteArchivo = 'OK';
ALTER TABLE aporteDetallado ALTER COLUMN apdEstadoRegistroAporteArchivo VARCHAR(60) NOT NULL;
