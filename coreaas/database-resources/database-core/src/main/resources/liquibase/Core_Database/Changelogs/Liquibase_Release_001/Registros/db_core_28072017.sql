--liquibase formatted sql

--changeset clmarin:01
--comment: Se realiza insercion de registro en la tabla VariableComunicado
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'ACRDS_TRMS_CDNES_WEB'));

--changeset flopez:02
--comment: Insercion de registro en la tabla ValidacionProceso
--Agrega Validación si Trabajador Dependiente es Admin Subsidio - Habilitador Novedad 178
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('NOVEDAD_CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_PRESENCIAL','VALIDACION_DEPENDIENTE_ADMIN_SUBSIDIO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('NOVEDAD_CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_WEB','VALIDACION_DEPENDIENTE_ADMIN_SUBSIDIO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('NOVEDAD_CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_DEPWEB','VALIDACION_DEPENDIENTE_ADMIN_SUBSIDIO','NOVEDADES_DEPENDIENTE_WEB','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);

--Agrega Validación si Trabajador Dependiente no  es Admin Subsidio - Habilitador Novedad 179
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('NOVEDAD_CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_PRESENCIAL','VALIDACION_DEPENDIENTE_NO_ADMIN_SUBSIDIO_GRUPO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('NOVEDAD_CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_WEB','VALIDACION_DEPENDIENTE_NO_ADMIN_SUBSIDIO_GRUPO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('NOVEDAD_CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_DEPWEB','VALIDACION_DEPENDIENTE_NO_ADMIN_SUBSIDIO_GRUPO','NOVEDADES_DEPENDIENTE_WEB','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);

--Agrega Validación si Trabajador Dependiente es Admin Subsidio - Habilitador Novedad 180
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('NOVEDAD_CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_TRABAJADOR_DEPENDIENTE','VALIDACION_DEPENDIENTE_ADMIN_SUBSIDIO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0)
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('NOVEDAD_CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_TRABAJADOR_DEPENDIENTE_WEB','VALIDACION_DEPENDIENTE_ADMIN_SUBSIDIO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0)

--Agrega Validación si Trabajador Dependiente no  es Admin Subsidio - Habilitador Novedad 182
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('NOVEDAD_CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_ADMINISTRADOR_SUBSIDIO','VALIDACION_DEPENDIENTE_NO_ADMIN_SUBSIDIO_GRUPO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('NOVEDAD_CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_ADMINISTRADOR_SUBSIDIO_WEB','VALIDACION_DEPENDIENTE_NO_ADMIN_SUBSIDIO_GRUPO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);

--Agrega Validación si Trabajador Dependiente es Admin Subsidio - Habilitador Novedad 194
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('NOVEDAD_CAMBIO_DATOS_DE_CUENTA_TRABAJADOR_DEPENDIENTE_PRESENCIAL','VALIDACION_DEPENDIENTE_ADMIN_SUBSIDIO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('NOVEDAD_CAMBIO_DATOS_DE_CUENTA_TRABAJADOR_DEPENDIENTE_WEB','VALIDACION_DEPENDIENTE_ADMIN_SUBSIDIO','NOVEDADES_PERSONAS_WEB','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('NOVEDAD_CAMBIO_DATOS_DE_CUENTA_TRABAJADOR_DEPENDIENTE_DEPWEB','VALIDACION_DEPENDIENTE_ADMIN_SUBSIDIO','NOVEDADES_DEPENDIENTE_WEB','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);

--Agrega Validación si Trabajador Dependiente no  es Admin Subsidio - Habilitador Novedad 195
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('NOVEDAD_CAMBIO_DATOS_DE_CUENTA_ADMINISTRADOR_SUBSIDIO','VALIDACION_DEPENDIENTE_NO_ADMIN_SUBSIDIO_GRUPO','NOVEDADES_PERSONAS_PRESENCIAL','ACTIVO',1,'TRABAJADOR_DEPENDIENTE',0);