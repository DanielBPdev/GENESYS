--liquibase formatted sql

--changeset sbriñez:02
drop table ubicacionempleador;

--changeset ogiral:03
/*cambio de columnas por objeto validación. Se adiciona interface que agrupa las enumeraciones de tipo beneficiario y clasificación*/
ALTER TABLE ValidacionProceso DROP COLUMN vapTipoAfiliado ;
ALTER TABLE ValidacionProceso DROP COLUMN vapTipoBeneficiario  ;
ALTER TABLE ValidacionProceso ADD vapObjetoValidacion  VARCHAR(60);

--changeset sbriñez:04
EXEC sp_rename 'dbo.SucursalEmpleador', 'SucursalEmpresa';
EXEC sp_rename 'dbo.SucursalEmpresa.sueEmpleador' , 'sueEmpresa', 'COLUMN';
ALTER TABLE SucursalEmpresa DROP CONSTRAINT FK_SucursalEmpleador_sueEmpleador;
ALTER TABLE SucursalEmpresa ADD CONSTRAINT FK_SucursalEmpresa_sueEmpresa FOREIGN KEY(sueEmpresa) REFERENCES Empresa;
EXEC sp_rename 'FK_SucursalEmpleador_sueCodigoCIIU' , 'FK_SucursalEmpresa_sueCodigoCIIU';
EXEC sp_rename 'FK_SucursalEmpleador_sueMedioPagoSubsidioMonetario' , 'FK_SucursalEmpresa_sueMedioPagoSubsidioMonetario';
EXEC sp_rename 'FK_SucursalEmpleador_sueUbicacion' , 'FK_SucursalEmpresa_sueUbicacion';
EXEC sp_rename 'PK_SucursalEmpleador_sueId' , 'PK_SucursalEmpresa_sueId';
