--liquibase formatted sql

--changeset lzarate:01 stripComments:false  
/*28/09/2016-lzarate-HU-121-108*/
CREATE TABLE RelacionGrupoFamiliar (
	rgfId smallint IDENTITY(1,1) NOT NULL,
	rgfNombre varchar(15) NULL,
    CONSTRAINT PK_RelacionGrupoFamiliar_rgfId PRIMARY KEY (rgfId) 
);

ALTER TABLE GrupoFamiliar ADD CONSTRAINT FK_GrupoFamiliar_grfRelacionGrupoFamiliar FOREIGN KEY(grfRelacionGrupoFamiliar) REFERENCES RelacionGrupoFamiliar(rgfId);


--changeset sbriñez:02 stripComments:false  
/*28/09/2016-sbriñez*/
EXEC sp_rename 'beneficiario.benRolAfiliado', 'benAfiliado';


--changeset sbriñez:03 stripComments:false  
/*28/09/2016-sbrinez-HU-121-109*/
ALTER TABLE SolicitudAsociacionPersonaEntidadPagadora ADD soaUsuarioGestion VARCHAR(255);
    
	
--changeset sbriñez:04 stripComments:false  
/*28/09/2016-sbriñez*/
 UPDATE Parametro   SET prmValor = 'com.asopagos.coreaas.bpm.afiliacion_empresas_presencial:Afiliacion_empresas_presencial:0.0.2-SNAPSHOT' WHERE prmNombre = 'BPMS_PROCESS_DEPLOYMENT_ID';
 
 --changeset sbriñez:05 stripComments:false  
alter table beneficiario drop  FK_Beneficiario_benRolAfiliado;
ALTER TABLE beneficiario ADD CONSTRAINT FK_Beneficiario_benAfiliado  FOREIGN KEY (benAfiliado) REFERENCES Afiliado (afiId);