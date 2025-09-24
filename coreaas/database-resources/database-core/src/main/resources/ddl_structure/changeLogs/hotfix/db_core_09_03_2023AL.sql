if not exists (select * from sysobjects where name='CreacionUsuariosEmpresasKeycload' )
CREATE TABLE CreacionUsuariosEmpresasKeycload
	(email varchar(100) NULL,
	primerapellido varchar(200) NULL,
	primernombre varchar(200) NULL,
	tipoIdentificacion varchar(50) NOT NULL,
	numIdentificacion varchar(15) NULL,
	idSolicitudGlobal bigint NULL,
	Nombre_Razon_social varchar(250) NULL,
	Estado varchar(50) NOT NULL)