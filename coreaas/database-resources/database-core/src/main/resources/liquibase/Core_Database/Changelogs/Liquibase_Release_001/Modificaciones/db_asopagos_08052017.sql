--liquibase formatted sql

--changeset Heinsohn:01
--comment: Creación de tabla  [ParametrizacionServicioCaja]  
CREATE TABLE [ParametrizacionServicioCaja](
	[pscId] [smallint] IDENTITY(1,1) NOT NULL,
	[pscServicio] [varchar](50) NULL,
	[pscURL] [varchar](256) NULL,
	[pscCajaCompensacion] [int] NULL
)
GO

ALTER TABLE [ParametrizacionServicioCaja]  WITH CHECK ADD  CONSTRAINT [FK_ParametrizacionServicioCaja_pscCajaCompensacion] FOREIGN KEY([pscCajaCompensacion])
REFERENCES [CajaCompensacion] ([ccfId])
GO
ALTER TABLE [ParametrizacionServicioCaja] CHECK CONSTRAINT [FK_ParametrizacionServicioCaja_pscCajaCompensacion]
GO
