--liquibase formatted sql

--changeset jocampo:01 stripComments:false
ALTER TABLE Solicitud ADD solObservacion VARCHAR(500);
ALTER TABLE SolicitudAfiliaciEmpleador DROP COLUMN saeObservacion;
ALTER TABLE SolicitudAfiliacionPersona DROP COLUMN perObservacion;


--changeset sbrinez:02 stripComments:false
ALTER TABLE AsesorResponsableEmpleador DROP CONSTRAINT FK_AsesorResponsableEmpleador_areEmpleador;
ALTER TABLE RequisitoAfiliaciEmpleador DROP CONSTRAINT FK_RequisitoAfiliaciEmpleador_raeEmpleador;
ALTER TABLE RolAfiliado DROP CONSTRAINT FK_RolAfiliado_roaEmpleador;
ALTER TABLE RolContactoEmpleador DROP CONSTRAINT FK_RolContactoEmpleador_rceEmpleador;
ALTER TABLE SocioEmpleador DROP CONSTRAINT FK_SocioEmpleador_semEmpleador;
ALTER TABLE SolicitudAfiliaciEmpleador DROP CONSTRAINT FK_SolicitudAfiliaciEmpleador_saeEmpleador;
ALTER TABLE SucursalEmpleador DROP CONSTRAINT FK_SucursalEmpleador_sueEmpleador;
ALTER TABLE UbicacionEmpleador DROP CONSTRAINT FK_UbicacionEmpleador_ubeEmpleador;


ALTER TABLE Empleador DROP CONSTRAINT PK_Empleador_empId;
ALTER TABLE Empleador DROP FK_Empleador_empId;
ALTER TABLE Empleador ADD empEmpresa BIGINT;
ALTER TABLE Empleador ADD CONSTRAINT PK_Empleador_empId PRIMARY KEY(empId);
ALTER TABLE Empleador ADD CONSTRAINT FK_Empleador_empEmpresa FOREIGN KEY(empEmpresa) REFERENCES Empresa;

ALTER TABLE DocumentoEntidadPagadora DROP CONSTRAINT FK_DocumentoEntidadPagadora_dpgEntidadPagadora;
ALTER TABLE RolAfiliado DROP CONSTRAINT FK_RolAfiliado_roaPagadorAportes;



ALTER TABLE EntidadPagadora DROP CONSTRAINT PK_TipoSolicitante_empId;
ALTER TABLE EntidadPagadora DROP CONSTRAINT FK_EntidadPagadora_empId;
ALTER TABLE EntidadPagadora DROP COLUMN empId;
ALTER TABLE EntidadPagadora ADD epaId BIGINT IDENTITY NOT NULL;
ALTER TABLE EntidadPagadora ADD CONSTRAINT PK_Empresa_epaid PRIMARY KEY(epaId);
ALTER TABLE EntidadPagadora ADD epaEmpresa BIGINT;
ALTER TABLE EntidadPagadora ADD CONSTRAINT FK_Empleador_epaEmpresa FOREIGN KEY(epaEmpresa) REFERENCES Empresa;

ALTER TABLE AsesorResponsableEmpleador ADD CONSTRAINT FK_AsesorResponsableEmpleador_areEmpleador FOREIGN KEY (areEmpleador) REFERENCES Empleador;
ALTER TABLE RequisitoAfiliaciEmpleador ADD CONSTRAINT FK_RequisitoAfiliaciEmpleador_raeEmpleador FOREIGN KEY (raeEmpleador) REFERENCES Empleador;
ALTER TABLE RolAfiliado ADD CONSTRAINT FK_RolAfiliado_roaEmpleador FOREIGN KEY (roaEmpleador) REFERENCES Empleador;
ALTER TABLE RolContactoEmpleador ADD CONSTRAINT FK_RolContactoEmpleador_rceEmpleador FOREIGN KEY (rceEmpleador) REFERENCES Empleador;
ALTER TABLE SocioEmpleador ADD CONSTRAINT FK_SocioEmpleador_semEmpleador FOREIGN KEY (semEmpleador) REFERENCES Empleador;
ALTER TABLE SolicitudAfiliaciEmpleador ADD CONSTRAINT FK_SolicitudAfiliaciEmpleador_saeEmpleador FOREIGN KEY (saeEmpleador) REFERENCES Empleador;
ALTER TABLE SucursalEmpleador ADD CONSTRAINT FK_SucursalEmpleador_sueEmpleador FOREIGN KEY (sueEmpleador) REFERENCES Empleador;
ALTER TABLE UbicacionEmpleador ADD CONSTRAINT FK_UbicacionEmpleador_ubeEmpleador FOREIGN KEY (ubeEmpleador) REFERENCES Empleador;

--changeset alopez:03 stripComments:false
DELETE FROM Parametro where prmNombre='SERVICIOS_ENDPOINT';

--changeset jcamargo:04 stripComments:false
insert into ValidacionProceso (vapProceso,vapBloque,vapValidacion,vapOrden,vapEstadoProceso) values('AFILIACION_EMPRESAS_WEB','112-110-1','VALIDACION_CUENTA_WEB',4,'ACTIVO');

--changeset mgiraldo:08
ALTER TABLE SOLICITUD ALTER COLUMN SOLCLASIFICACION VARCHAR(100);

--changeset mgiraldo:10
UPDATE Parametro set prmvalor='Hz8gYkQbCZ0LbwcmiaXUug==' where prmNombre= 'mail.smtp.password';
