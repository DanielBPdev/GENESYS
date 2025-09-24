--liquibase formatted sql

--changeset Heinsohn:1 stripComments:true

-- Creación de SedeCajaCompensacion


SET IDENTITY_INSERT [SedeCajaCompensacion] ON;
-- Creación de SedeCajaCompensacion
INSERT [dbo].[SedeCajaCompensacion] ([sccfId],[sccfNombre],[sccfVirtual],[sccCodigo]) VALUES (1,'Sede Virtual',1,'01');
INSERT [dbo].[SedeCajaCompensacion] ([sccfId],[sccfNombre],[sccfVirtual],[sccCodigo]) VALUES (2,'Sede Principal',0,'02');
SET IDENTITY_INSERT [SedeCajaCompensacion] OFF;
GO

-- Creación de ParametrizacionMetodoAsignacion
INSERT [dbo].[ParametrizacionMetodoAsignacion] ([pmaSedeCajaCompensacion],[pmaProceso],[pmaMetodoAsignacion],[pmaUsuario]) VALUES (1,'AFILIACION_EMPRESAS_WEB','CONSECUTIVO_TURNOS',null);
INSERT [dbo].[ParametrizacionMetodoAsignacion] ([pmaSedeCajaCompensacion],[pmaProceso],[pmaMetodoAsignacion],[pmaUsuario]) VALUES (1,'AFILIACION_DEPENDIENTE_WEB','CONSECUTIVO_TURNOS',null);
INSERT [dbo].[ParametrizacionMetodoAsignacion] ([pmaSedeCajaCompensacion],[pmaProceso],[pmaMetodoAsignacion],[pmaUsuario]) VALUES (1,'AFILIACION_INDEPENDIENTE_WEB','CONSECUTIVO_TURNOS',null);
INSERT [dbo].[ParametrizacionMetodoAsignacion] ([pmaSedeCajaCompensacion],[pmaProceso],[pmaMetodoAsignacion],[pmaUsuario]) VALUES (2,'AFILIACION_EMPRESAS_PRESENCIAL','MANUAL',null);
INSERT [dbo].[ParametrizacionMetodoAsignacion] ([pmaSedeCajaCompensacion],[pmaProceso],[pmaMetodoAsignacion],[pmaUsuario]) VALUES (2,'AFILIACION_PERSONAS_PRESENCIAL','CONSECUTIVO_TURNOS',null);
GO