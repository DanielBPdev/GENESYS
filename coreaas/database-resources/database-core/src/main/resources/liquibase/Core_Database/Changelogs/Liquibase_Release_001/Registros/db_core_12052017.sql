--liquibase formatted sql

--changeset lzarate:01
--comment: Insercion en la tabla ParametrizacionEjecucionProgramada
INSERT ParametrizacionEjecucionProgramada (pepAnio,pepDiaMes,pepDiaSemana,pepFechaFin,pepFechaInicio,pepFrecuencia,pepHoras,pepMes,pepMinutos,pepProceso,pepSegundos) VALUES (null,null,null,null,null,'DIARIO','00',null,'00','GENERAR_AVISO_VENCIMIENTO_CLAVE_USUARIO',null);

--changeset clmarin:02
--comment: Insercion en la tabla PlantillaComunicado
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoIdentificadorImagenPie,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Notificación vencimiento clave de usuario sistema GENESYS','<p>Señor(a)<br />${nombresYApellidosDelAfiliadoPrincipal} <br /> Su contraseña de usuario se vencerá en ${diasVencimiento} días, por favor actualizarla.</p>','${fechaDelSistema}',null,'Aviso de vencimiento para clave de usuario','Notificación vencimiento clave de usuario sistema GENESYS','<p>Cordialmente, Administrador GENESYS</p>','NTF_VEN_CON_USR');  