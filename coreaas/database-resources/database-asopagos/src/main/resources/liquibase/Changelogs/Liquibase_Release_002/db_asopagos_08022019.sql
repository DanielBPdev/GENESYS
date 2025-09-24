--liquibase formatted sql

--changeset jroa:01
--comment: Creacion tabla ListaEspecialRevision
CREATE TABLE ListaEspecialRevision(
	lerId bigint IDENTITY(1,1) NOT NULL,
	lerTipoIdentificacion varchar(20) NOT NULL,
	lerNumeroIdentificacion varchar(16) NOT NULL,
	lerDigitoVerificacion tinyint NULL,
	lerCajaCompensacion int NULL,
	lerNombreEmpleador varchar(200) NULL,
	lerFechaInicioInclusion date NULL,
	lerFechaFinInclusion date NULL,
	lerRazonInclusion varchar(20) NULL,
	lerEstado varchar(20) NULL,
	lerComentario varchar(255) NULL,
 CONSTRAINT PK_ListaEspecialRevision_lerId PRIMARY KEY CLUSTERED 
(
	lerId ASC
)) 


ALTER TABLE ListaEspecialRevision  WITH CHECK ADD  CONSTRAINT FK_ListaEspecialRevision_lerCajaCompensacion FOREIGN KEY(lerCajaCompensacion) REFERENCES CajaCompensacion (ccfId)
ALTER TABLE ListaEspecialRevision CHECK CONSTRAINT FK_ListaEspecialRevision_lerCajaCompensacion
ALTER TABLE ListaEspecialRevision  WITH CHECK ADD  CONSTRAINT CK_ListaEspecialRevision_lerEstado CHECK  ((lerEstado='EXCLUIDO' OR lerEstado='INCLUIDO'))
ALTER TABLE ListaEspecialRevision CHECK CONSTRAINT CK_ListaEspecialRevision_lerEstado
ALTER TABLE ListaEspecialRevision  WITH CHECK ADD  CONSTRAINT CK_ListaEspecialRevision_lerRazonInclusion CHECK  ((lerRazonInclusion='OTRO' OR lerRazonInclusion='MOROSIDAD' OR lerRazonInclusion='FRAUDE_FALSEDAD'))
ALTER TABLE ListaEspecialRevision CHECK CONSTRAINT CK_ListaEspecialRevision_lerRazonInclusion
ALTER TABLE ListaEspecialRevision  WITH CHECK ADD  CONSTRAINT CK_ListaEspecialRevision_lerTipoIdentificacion CHECK  ((lerTipoIdentificacion='PERM_ESP_PERMANENCIA' OR lerTipoIdentificacion='SALVOCONDUCTO' OR lerTipoIdentificacion='NIT' OR lerTipoIdentificacion='CARNE_DIPLOMATICO' OR lerTipoIdentificacion='PASAPORTE' OR lerTipoIdentificacion='CEDULA_EXTRANJERIA' OR lerTipoIdentificacion='CEDULA_CIUDADANIA' OR lerTipoIdentificacion='TARJETA_IDENTIDAD' OR lerTipoIdentificacion='REGISTRO_CIVIL'))
ALTER TABLE ListaEspecialRevision CHECK CONSTRAINT CK_ListaEspecialRevision_lerTipoIdentificacion

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Entidad que representa la lista especial de revisión de los empleadores que han 
 sido negados en la afiliación y puestos en lista <br/>
 <b>Historia de Usuario: </b>121-107' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ListaEspecialRevision'


