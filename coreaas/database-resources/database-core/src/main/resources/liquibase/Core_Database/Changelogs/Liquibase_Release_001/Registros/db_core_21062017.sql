--liquibase formatted sql

--changeset arocha:01 
--comment: Insercion de datos a la tabla StatingParametros
SET IDENTITY_INSERT [staging].[StagingParametros] ON 
INSERT [staging].[StagingParametros] ([stpId], [stpNombreParametro], [stpValorParametro]) VALUES (1, N'MES_CARGA_NOVEDADES', N'36')
INSERT [staging].[StagingParametros] ([stpId], [stpNombreParametro], [stpValorParametro]) VALUES (2, N'SOLICITANTE_INDEPENDIENTE', N'3,16,57,59')
SET IDENTITY_INSERT [staging].[StagingParametros] OFF