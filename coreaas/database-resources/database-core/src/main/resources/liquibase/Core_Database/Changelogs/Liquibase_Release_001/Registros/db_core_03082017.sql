--liquibase formatted sql

--changeset jzambrano:01
--comment: Insercion en la tabla ValidacionProceso
--VALIDACIONES PARA SOLICITUDES SIMULTANEAS PERSONAS 104 -PENSIONADOS-DEPENDIENTES-INDEPENDIENTES BLOQUES AFECTADOS
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('121-104-1','VALIDACION_SOLICITUD_PERSONA','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('121-104-2','VALIDACION_SOLICITUD_PERSONA','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('121-104-3','VALIDACION_SOLICITUD_PERSONA','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1,'TRABAJADOR_INDEPENDIENTE',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('121-104-4','VALIDACION_SOLICITUD_PERSONA','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1,'PENSIONADO',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('121-104-6','VALIDACION_SOLICITUD_PERSONA','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1,'TRABAJADOR_INDEPENDIENTE',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('121-104-7','VALIDACION_SOLICITUD_PERSONA','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1,'PENSIONADO',0);

--VALIDACIONES PARA SOLICITUDES SIMULTANEAS PERSONAS 107 -PENSIONADOS-DEPENDIENTES-INDEPENDIENTES BLOQUES AFECTADOS
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('121-107-1','VALIDACION_SOLICITUD_PERSONA','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1,'PENSIONADO',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('121-107-1','VALIDACION_SOLICITUD_PERSONA','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1,'TRABAJADOR_INDEPENDIENTE',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('121-107-1','VALIDACION_SOLICITUD_PERSONA','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);

--VALIDACIONES PARA SOLICITUDES SIMULTANEAS PERSONAS 123-374 -PENSIONADOS-INDEPENDIENTES BLOQUES AFECTADOS
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-374-2','VALIDACION_SOLICITUD_WEB_PERSONA','AFILIACION_INDEPENDIENTE_WEB','ACTIVO',1,'PENSIONADO',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-374-2','VALIDACION_SOLICITUD_WEB_PERSONA','AFILIACION_INDEPENDIENTE_WEB','ACTIVO',1,'TRABAJADOR_INDEPENDIENTE',0);

--VALIDACIONES PARA SOLICITUDES SIMULTANEAS PERSONAS 123-378 -PENSIONADOS-INDEPENDIENTES BLOQUES AFECTADOS
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-378-1','VALIDACION_SOLICITUD_PERSONA','AFILIACION_INDEPENDIENTE_WEB','ACTIVO',1,'PENSIONADO',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-378-1','VALIDACION_SOLICITUD_PERSONA','AFILIACION_INDEPENDIENTE_WEB','ACTIVO',1,'TRABAJADOR_INDEPENDIENTE',0);

--VALIDACIONES PARA SOLICITUDES SIMULTANEAS PERSONAS 123-379 -PENSIONADOS-INDEPENDIENTES BLOQUES AFECTADOS reintegro
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-379-1','VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION','AFILIACION_INDEPENDIENTE_WEB','ACTIVO',1,'PENSIONADO',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-379-1','VALIDACION_PERSONA_FALLECIDA','AFILIACION_INDEPENDIENTE_WEB','ACTIVO',1,'PENSIONADO',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-379-1','VALIDACION_PERSONA_PENSIONADO','AFILIACION_INDEPENDIENTE_WEB','ACTIVO',1,'PENSIONADO',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-379-1','VALIDACION_TIEMPO_REINTEGRO_AFILIADO','AFILIACION_INDEPENDIENTE_WEB','ACTIVO',1,'PENSIONADO',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-379-1','VALIDACION_SOLICITUD_PERSONA','AFILIACION_INDEPENDIENTE_WEB','ACTIVO',1,'PENSIONADO',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-379-1','VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION','AFILIACION_INDEPENDIENTE_WEB','ACTIVO',1,'TRABAJADOR_INDEPENDIENTE',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-379-1','VALIDACION_PERSONA_FALLECIDA','AFILIACION_INDEPENDIENTE_WEB','ACTIVO',1,'TRABAJADOR_INDEPENDIENTE',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-379-1','VALIDACION_PERSONA_INDEPENDIENTE','AFILIACION_INDEPENDIENTE_WEB','ACTIVO',1,'TRABAJADOR_INDEPENDIENTE',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-379-1','VALIDACION_PERSONA_NO_MAYOR_15','AFILIACION_INDEPENDIENTE_WEB','ACTIVO',1,'TRABAJADOR_INDEPENDIENTE',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-379-1','VALIDACION_TIEMPO_REINTEGRO_AFILIADO','AFILIACION_INDEPENDIENTE_WEB','ACTIVO',1,'TRABAJADOR_INDEPENDIENTE',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-379-1','VALIDACION_SOLICITUD_PERSONA','AFILIACION_INDEPENDIENTE_WEB','ACTIVO',1,'TRABAJADOR_INDEPENDIENTE',0);

--VALIDACIONES PARA SOLICITUDES SIMULTANEAS PERSONAS 122-361 -Dependiente 
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('122-361-1','VALIDACION_SOLICITUD_PERSONA','AFILIACION_DEPENDIENTE_WEB','ACTIVO',1,'PENSIONADO',0);

--VALIDACIONES PARA SOLICITUDES SIMULTANEAS PERSONAS 122-359 -Dependiente 
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('122-359-1','VALIDACION_SOLICITUD_PERSONA','AFILIACION_DEPENDIENTE_WEB','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);

--VALIDACIONES PARA SOLICITUDES SIMULTANEAS PERSONAS 122-361-2 -Dependiente
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('122-361-2','VALIDACION_SOLICITUD_PERSONA','AFILIACION_DEPENDIENTE_WEB','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);

--VALIDACIONES PARA SOLICITUDES SIMULTANEAS PERSONAS 122-361-3 -Dependiente
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('122-361-3','VALIDACION_SOLICITUD_PERSONA','AFILIACION_DEPENDIENTE_WEB','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);

--VALIDACIONES PARA SOLICITUDES SIMULTANEAS PERSONAS 122-363-1 -Dependiente
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('122-363-1','VALIDACION_SOLICITUD_PERSONA','AFILIACION_DEPENDIENTE_WEB','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);