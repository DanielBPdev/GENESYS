--liquibase formatted sql

--changeset clamarin:01
--comment:
ALTER TABLE RolAfiliado ADD roaMunicipioDesempenioLabores smallint NULL;

ALTER TABLE RolAfiliado ADD CONSTRAINT FK_RolAfiliado_roaMunicipioDesempenioLabores FOREIGN KEY (roaMunicipioDesempenioLabores) REFERENCES Municipio(munId);

ALTER TABLE aud.RolAfiliado_aud ADD roaMunicipioDesempenioLabores smallint NULL;

--changeset dsuesca:01
--comment:
INSERT ValidatorParameter (id,dataType,description,mask,name,validatorCatalog_id)
					VALUES (211380, 'STRING', 'Campo Novedad VAC', null, 'campoNovedadVAC', 211032);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id)
					VALUES (2111769, 'archivoIregistro2campo22', 2110085, 211171);
