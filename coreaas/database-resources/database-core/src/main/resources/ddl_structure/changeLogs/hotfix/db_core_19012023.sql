/**
  * GLPI-57123 Se crea tabla de PreRegistroEmpresaDescentralizada ;
 */
CREATE TABLE core.dbo.PreRegistroEmpresaDescentralizada (
	prdId bigint IDENTITY(1,1) NOT NULL,
	prdTipoIdentificacion varchar(20) COLLATE Latin1_General_CI_AI NULL,
	prdNumeroIdentificacionSerial varchar(16) COLLATE Latin1_General_CI_AI NULL,
	prdCodigoSucursal varchar(10) COLLATE Latin1_General_CI_AI NULL,
	prdNombreSucursalPila varchar(250) COLLATE Latin1_General_CI_AI NULL,
	prdNumeroIdentificacion varchar(16) COLLATE Latin1_General_CI_AI NULL,
	CONSTRAINT PK__PreRegistroEmpresaDescentralizada PRIMARY KEY (prdId)
);