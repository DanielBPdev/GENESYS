--liquibase formatted sql

--changeset Heinsohn:1 stripComments:true

-- Creación de ARLs

-- Inserción de las ARL
INSERT [dbo].[ARL] ([arlNombre]) VALUES ('Alfa')
INSERT [dbo].[ARL] ([arlNombre]) VALUES ('Aurora')
INSERT [dbo].[ARL] ([arlNombre]) VALUES ('Axa Colpatria')
INSERT [dbo].[ARL] ([arlNombre]) VALUES ('Colmena')
INSERT [dbo].[ARL] ([arlNombre]) VALUES ('La Equidad')
INSERT [dbo].[ARL] ([arlNombre]) VALUES ('Liberty')
INSERT [dbo].[ARL] ([arlNombre]) VALUES ('Mapfre')
INSERT [dbo].[ARL] ([arlNombre]) VALUES ('Positiva')
INSERT [dbo].[ARL] ([arlNombre]) VALUES ('Seguros Bolivar')
INSERT [dbo].[ARL] ([arlNombre]) VALUES ('Sura')
INSERT [dbo].[ARL] ([arlNombre]) VALUES ('Sin ARL')
GO