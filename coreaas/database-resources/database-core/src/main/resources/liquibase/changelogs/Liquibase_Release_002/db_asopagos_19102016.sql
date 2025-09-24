--liquibase formatted sql

--changeset atoro:01
ALTER TABLE ValidacionProceso ALTER COLUMN vapValidacion VARCHAR (100);

--changeset halzate:02
ALTER TABLE EntidadPagadora ADD epaSucursalPagadora bigint;
ALTER TABLE EntidadPagadora ADD CONSTRAINT FK_EntidadPagadora_epaSucursalPagadora FOREIGN KEY (epaSucursalPagadora) REFERENCES SucursalEmpresa (sueId);
 
--changeset jcamargo:03
UPDATE PARAMETRO SET prmNombre='112_ABRIR_LINK_TIMER' WHERE prmNombre='112_ABRIR_LINK_TIME';
UPDATE PARAMETRO SET prmNombre='112_DILIGENCIAR_FORMULARIO_TIMER' WHERE prmNombre='112_DILIGENCIAR_FORMULARIO_TIMER ';

--changeset jcamargo:04
UPDATE PlantillaComunicado
	SET pcoMensaje='Señores <br/>[razonSocialEmpleador]<br/>Las validaciones preliminares han sido exitosas. Para continuar con el proceso de afiliación por favor seguir el siguiente vínculo:<br/><br/><a href="[linkRegistro]">[linkRegistro]</a><br/><br/>Cordialmente, <br/><br/>[nombreSedeCCF]',
	pcoAsunto='Continuación proceso de afiliación  de empleador'
	where pcoEtiqueta='NOTIFICACION_ENROLAMIENTO_AFILIACION_EMPLEADOR_WEB';

--changeset lzarate:05
CREATE TABLE ListaEspecialRevision
(
   lerId bigint NOT NULL,
   lerTipoIdentificacion varchar(20) not null,
   lerNumeroIdentificacion varchar(16)not null,
   lerDigitoVerificacion tinyint,
   lerCajaCompensacion int ,
   lerNombreEmpleador varchar(200),
   lerFechaInicioInclusion datetime,
   lerFechaFinInclusion datetime,
   lerRazonInclusion varchar(20),
   lerEstado varchar(20),
   lerComentario varchar(255)
   CONSTRAINT PK_ListaEspecialRevision_lerId PRIMARY KEY (lerId)
);

ALTER TABLE ListaEspecialRevision ADD CONSTRAINT
FK_ListaEspecialRevision_lerCajaCompensacion
 FOREIGN KEY (lerCajaCompensacion) REFERENCES CajaCompensacion;
 
 
 --changeset lzarate:06
  INSERT [Parametro] ([prmNombre], [prmValor]) VALUES ('FUNCIONARIO_BACK','funcionarios_back');
  
  --changeset mgiraldo:08
  drop table ListaEspecialRevision;
  
CREATE TABLE ListaEspecialRevision
(
   lerId bigint IDENTITY(1,1) NOT NULL,
   lerTipoIdentificacion varchar(20) not null,
   lerNumeroIdentificacion varchar(16)not null,
   lerDigitoVerificacion tinyint,
   lerCajaCompensacion int ,
   lerNombreEmpleador varchar(200),
   lerFechaInicioInclusion datetime,
   lerFechaFinInclusion datetime,
   lerRazonInclusion varchar(20),
   lerEstado varchar(20),
   lerComentario varchar(255)
   CONSTRAINT PK_ListaEspecialRevision_lerId PRIMARY KEY (lerId)
);

ALTER TABLE ListaEspecialRevision ADD CONSTRAINT
FK_ListaEspecialRevision_lerCajaCompensacion
 FOREIGN KEY (lerCajaCompensacion) REFERENCES CajaCompensacion;
