--liquibase formatted sql

--changeset squintero:01
--comment:Creacion de tabla temAporteProcesado
CREATE TABLE TemAporteProcesado (
	tprId BIGINT IDENTITY (1,1) NOT NULL,
	tprAporteGeneral BIGINT,
	tprAporte INT,
	tprNovedadesProcesadas BIT,
	 CONSTRAINT [PK_TemAporteProcesado_tprId] PRIMARY KEY CLUSTERED 
(
	[tprId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
