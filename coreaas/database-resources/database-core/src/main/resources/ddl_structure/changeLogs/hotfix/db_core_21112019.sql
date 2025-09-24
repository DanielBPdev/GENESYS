--liquibase formatted sql

--changeset mamonroy:01
--comment:
IF EXISTS (SELECT cnsId FROM Constante WHERE cnsNombre = 'CAJA_COMPENSACION_ID' AND cnsValor = 14) AND EXISTS (SELECT cnsId FROM Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO' AND cnsValor = 'CCF16')
BEGIN

INSERT INTO DatoTemporalComunicado (dtcIdTarea,dtcJsonPayload) VALUES
(222916,'{"contexto":{"idInstancia":45327,"idSolicitud":57342,"idTarea":222916},"HU331":[{"plantilla":"NTF_NVD_PERS","processName":"NOVEDADES_PERSONAS_PRESENCIAL","urlRedirect":"pendientes","invocaciones":[{"id":"srvActualizarEstado","peticion":{"method":"POST","url":"http://nginx.asopagos.com:8080/comfacor/NovedadesService/rest/novedades/57342/estadoSolicitud?estadoSolicitud=CERRADA"}}],"avanzarTarea":true,"idInstancia":45327,"idSolicitud":57342}],"uuid":"99e6cb42-8103-436b-a8c0-fc340455913c"}'),
(246770,'{"contexto":{"idInstancia":50121,"idSolicitud":62351,"idTarea":246770},"HU331":[{"plantilla":"NTF_NVD_PERS","processName":"NOVEDADES_PERSONAS_PRESENCIAL","urlRedirect":"pendientes","invocaciones":[{"id":"srvActualizarEstado","peticion":{"method":"POST","url":"http://nginx.asopagos.com:8080/comfacor/NovedadesService/rest/novedades/62351/estadoSolicitud?estadoSolicitud=CERRADA"}}],"avanzarTarea":true,"idInstancia":50121,"idSolicitud":62351}],"uuid":"99e6cb42-8103-436b-a8c0-fc350455913c"}'),
(244785,'{"contexto":{"idInstancia":50255,"idSolicitud":62487,"idTarea":244785},"HU331":[{"plantilla":"NTF_NVD_PERS","processName":"NOVEDADES_PERSONAS_PRESENCIAL","urlRedirect":"pendientes","invocaciones":[{"id":"srvActualizarEstado","peticion":{"method":"POST","url":"http://nginx.asopagos.com:8080/comfacor/NovedadesService/rest/novedades/62487/estadoSolicitud?estadoSolicitud=CERRADA"}}],"avanzarTarea":true,"idInstancia":50255,"idSolicitud":62487}],"uuid":"99e6cb42-8103-436b-a8c0-fc360455913c"}'),
(244783,'{"contexto":{"idInstancia":50259,"idSolicitud":62491,"idTarea":244783},"HU331":[{"plantilla":"NTF_NVD_PERS","processName":"NOVEDADES_PERSONAS_PRESENCIAL","urlRedirect":"pendientes","invocaciones":[{"id":"srvActualizarEstado","peticion":{"method":"POST","url":"http://nginx.asopagos.com:8080/comfacor/NovedadesService/rest/novedades/62491/estadoSolicitud?estadoSolicitud=CERRADA"}}],"avanzarTarea":true,"idInstancia":50259,"idSolicitud":62491}],"uuid":"99e6cb42-8103-436b-a8c0-fc370455913c"}'),
(244782,'{"contexto":{"idInstancia":50260,"idSolicitud":62492,"idTarea":244782},"HU331":[{"plantilla":"NTF_NVD_PERS","processName":"NOVEDADES_PERSONAS_PRESENCIAL","urlRedirect":"pendientes","invocaciones":[{"id":"srvActualizarEstado","peticion":{"method":"POST","url":"http://nginx.asopagos.com:8080/comfacor/NovedadesService/rest/novedades/62492/estadoSolicitud?estadoSolicitud=CERRADA"}}],"avanzarTarea":true,"idInstancia":50260,"idSolicitud":62492}],"uuid":"99e6cb42-8103-436b-a8c0-fc380455913c"}'),
(244781,'{"contexto":{"idInstancia":50261,"idSolicitud":62493,"idTarea":244781},"HU331":[{"plantilla":"NTF_NVD_PERS","processName":"NOVEDADES_PERSONAS_PRESENCIAL","urlRedirect":"pendientes","invocaciones":[{"id":"srvActualizarEstado","peticion":{"method":"POST","url":"http://nginx.asopagos.com:8080/comfacor/NovedadesService/rest/novedades/62493/estadoSolicitud?estadoSolicitud=CERRADA"}}],"avanzarTarea":true,"idInstancia":50261,"idSolicitud":62493}],"uuid":"99e6cb42-8103-436b-a8c0-fc390455913c"}'),
(244780,'{"contexto":{"idInstancia":50267,"idSolicitud":62499,"idTarea":244780},"HU331":[{"plantilla":"NTF_NVD_PERS","processName":"NOVEDADES_PERSONAS_PRESENCIAL","urlRedirect":"pendientes","invocaciones":[{"id":"srvActualizarEstado","peticion":{"method":"POST","url":"http://nginx.asopagos.com:8080/comfacor/NovedadesService/rest/novedades/62499/estadoSolicitud?estadoSolicitud=CERRADA"}}],"avanzarTarea":true,"idInstancia":50267,"idSolicitud":62499}],"uuid":"99e6cb42-8103-436b-a8c0-fc400455913c"}'),
(199354,'{"contexto":{"idInstancia":40714,"idSolicitud":52379,"idTarea":199354},"HU331":[{"plantilla":"NTF_RAD_NVD_PER","processName":"NOVEDADES_PERSONAS_PRESENCIAL","idInstancia":40714,"idSolicitud":52379},{"plantilla":"NTF_NVD_PERS","processName":"NOVEDADES_PERSONAS_PRESENCIAL","urlRedirect":"134-140","avanzarTarea":true,"invocaciones":[],"contexto":{"idSolicitud":52379},"idInstancia":40714,"idSolicitud":52379}],"uuid":"99e6cb42-8103-436b-a8c0-fc342455913c"}'),
(244072,'{"contexto":{"idInstancia":50229,"idSolicitud":62461,"idTarea":244072},"HU331":[{"plantilla":"NTF_RAD_NVD_PER","processName":"NOVEDADES_PERSONAS_PRESENCIAL","idInstancia":50229,"idSolicitud":62461},{"plantilla":"NTF_NVD_PERS","processName":"NOVEDADES_PERSONAS_PRESENCIAL","urlRedirect":"134-140","avanzarTarea":true,"invocaciones":[],"contexto":{"idSolicitud":62461},"idInstancia":50229,"idSolicitud":62461}],"uuid":"99e6cb42-8103-436b-a8c0-fc343455913c"}'),
(246778,'{"contexto":{"idInstancia":50924,"idSolicitud":63182,"idTarea":246778},"HU331":[{"plantilla":"NTF_RAD_NVD_PER","processName":"NOVEDADES_PERSONAS_PRESENCIAL","idInstancia":50924,"idSolicitud":63182},{"plantilla":"NTF_NVD_PERS","processName":"NOVEDADES_PERSONAS_PRESENCIAL","urlRedirect":"134-140","avanzarTarea":true,"invocaciones":[],"contexto":{"idSolicitud":63182},"idInstancia":50924,"idSolicitud":63182}],"uuid":"99e6cb42-8103-436b-a8c0-fc344455913c"}')

END

--changeset mamonroy:02
--comment:
IF EXISTS (SELECT cnsId FROM Constante WHERE cnsNombre = 'CAJA_COMPENSACION_ID' AND cnsValor = 14) AND EXISTS (SELECT cnsId FROM Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO' AND cnsValor = 'CCF16')
BEGIN

UPDATE ped
SET ped.pedGenero = 'MASCULINO'
FROM Persona per
JOIN PersonaDetalle ped ON per.perId = ped.pedPersona
WHERE per.perNumeroIdentificacion = '1552284'
AND per.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
AND per.perId = 54608
AND (ped.pedGenero = 'INDEFINIDO' OR ped.pedGenero IS NULL)

UPDATE ped
SET ped.pedGenero = 'MASCULINO'
FROM Persona per
JOIN PersonaDetalle ped ON per.perId = ped.pedPersona
WHERE per.perNumeroIdentificacion = '1064980719'
AND per.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
AND per.perId = 80358
AND (ped.pedGenero = 'INDEFINIDO' OR ped.pedGenero IS NULL)


UPDATE ped
SET ped.pedGenero = 'FEMENINO'
FROM Persona per
JOIN PersonaDetalle ped ON per.perId = ped.pedPersona
WHERE per.perNumeroIdentificacion = '1067906021'
AND per.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
AND per.perId = 110945
AND (ped.pedGenero = 'INDEFINIDO' OR ped.pedGenero IS NULL)

UPDATE ped
SET ped.pedGenero = 'FEMENINO'
FROM Persona per
JOIN PersonaDetalle ped ON per.perId = ped.pedPersona
WHERE per.perNumeroIdentificacion = '1069483291'
AND per.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
AND per.perId = 58616
AND (ped.pedGenero = 'INDEFINIDO' OR ped.pedGenero IS NULL)

UPDATE ped
SET ped.pedGenero = 'FEMENINO'
FROM Persona per
JOIN PersonaDetalle ped ON per.perId = ped.pedPersona
WHERE per.perNumeroIdentificacion = '1074001563'
AND per.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
AND per.perId = 44585
AND (ped.pedGenero = 'INDEFINIDO' OR ped.pedGenero IS NULL)

UPDATE ped
SET ped.pedGenero = 'MASCULINO'
FROM Persona per
JOIN PersonaDetalle ped ON per.perId = ped.pedPersona
WHERE per.perNumeroIdentificacion = '15646091'
AND per.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
AND per.perId = 24662
AND (ped.pedGenero = 'INDEFINIDO' OR ped.pedGenero IS NULL)

UPDATE ped
SET ped.pedGenero = 'MASCULINO'
FROM Persona per
JOIN PersonaDetalle ped ON per.perId = ped.pedPersona
WHERE per.perNumeroIdentificacion = '15662427'
AND per.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
AND per.perId = 76438
AND (ped.pedGenero = 'INDEFINIDO' OR ped.pedGenero IS NULL)

UPDATE ped
SET ped.pedGenero = 'MASCULINO'
FROM Persona per
JOIN PersonaDetalle ped ON per.perId = ped.pedPersona
WHERE per.perNumeroIdentificacion = '15667195'
AND per.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
AND per.perId = 29368
AND (ped.pedGenero = 'INDEFINIDO' OR ped.pedGenero IS NULL)

UPDATE ped
SET ped.pedGenero = 'MASCULINO'
FROM Persona per
JOIN PersonaDetalle ped ON per.perId = ped.pedPersona
WHERE per.perNumeroIdentificacion = '2819506'
AND per.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
AND per.perId = 25568
AND (ped.pedGenero = 'INDEFINIDO' OR ped.pedGenero IS NULL)

UPDATE ped
SET ped.pedGenero = 'FEMENINO'
FROM Persona per
JOIN PersonaDetalle ped ON per.perId = ped.pedPersona
WHERE per.perNumeroIdentificacion = '26023848'
AND per.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
AND per.perId = 50102
AND (ped.pedGenero = 'INDEFINIDO' OR ped.pedGenero IS NULL)

UPDATE ped
SET ped.pedGenero = 'FEMENINO'
FROM Persona per
JOIN PersonaDetalle ped ON per.perId = ped.pedPersona
WHERE per.perNumeroIdentificacion = '50927401'
AND per.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
AND per.perId = 39360
AND (ped.pedGenero = 'INDEFINIDO' OR ped.pedGenero IS NULL)

UPDATE ped
SET ped.pedGenero = 'MASCULINO'
FROM Persona per
JOIN PersonaDetalle ped ON per.perId = ped.pedPersona
WHERE per.perNumeroIdentificacion = '72286072'
AND per.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
AND per.perId = 66152
AND (ped.pedGenero = 'INDEFINIDO' OR ped.pedGenero IS NULL)

UPDATE ped
SET ped.pedGenero = 'MASCULINO'
FROM Persona per
JOIN PersonaDetalle ped ON per.perId = ped.pedPersona
WHERE per.perNumeroIdentificacion = '78076289'
AND per.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
AND per.perId = 59928
AND (ped.pedGenero = 'INDEFINIDO' OR ped.pedGenero IS NULL)

UPDATE ped
SET ped.pedGenero = 'FEMENINO'
FROM Persona per
JOIN PersonaDetalle ped ON per.perId = ped.pedPersona
WHERE per.perNumeroIdentificacion = '25776703'
AND per.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
AND per.perId = 74831
AND (ped.pedGenero = 'INDEFINIDO' OR ped.pedGenero IS NULL)

UPDATE ped
SET ped.pedGenero = 'FEMENINO'
FROM Persona per
JOIN PersonaDetalle ped ON per.perId = ped.pedPersona
WHERE per.perNumeroIdentificacion = '1063158631'
AND per.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
AND per.perId = 26919
AND (ped.pedGenero = 'INDEFINIDO' OR ped.pedGenero IS NULL)

UPDATE ped
SET ped.pedGenero = 'FEMENINO'
FROM Persona per
JOIN PersonaDetalle ped ON per.perId = ped.pedPersona
WHERE per.perNumeroIdentificacion = '1068420300'
AND per.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
AND per.perId = 61640
AND (ped.pedGenero = 'INDEFINIDO' OR ped.pedGenero IS NULL)

UPDATE ped
SET ped.pedGenero = 'FEMENINO'
FROM Persona per
JOIN PersonaDetalle ped ON per.perId = ped.pedPersona
WHERE per.perNumeroIdentificacion = '1062967244'
AND per.perTipoIdentificacion = 'CEDULA_CIUDADANIA'
AND per.perId = 48773
AND (ped.pedGenero = 'INDEFINIDO' OR ped.pedGenero IS NULL)

END