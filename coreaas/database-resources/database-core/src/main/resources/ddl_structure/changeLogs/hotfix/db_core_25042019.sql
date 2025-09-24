--liquibase formatted sql

--changeset dsuesca:01
--comment: 
ALTER TABLE CuentaAdministradorSubsidio ADD casEmpleador bigint;
ALTER TABLE CuentaAdministradorSubsidio ADD casAfiliadoPrincipal bigint;
ALTER TABLE CuentaAdministradorSubsidio ADD casBeneficiarioDetalle bigint;
ALTER TABLE aud.CuentaAdministradorSubsidio_aud ADD casEmpleador bigint;
ALTER TABLE aud.CuentaAdministradorSubsidio_aud ADD casAfiliadoPrincipal bigint;
ALTER TABLE aud.CuentaAdministradorSubsidio_aud ADD casBeneficiarioDetalle bigint;

--changeset mamonroy:02
--comment: 
UPDATE vco
SET vco.vcoclave = '${contrasenia}'
FROM variableComunicado vco
JOIN PlantillaComunicado pco on pco.pcoId = vco.vcoPlantillaComunicado
WHERE pco.pcoEtiqueta = 'NTF_RES_CTRS' AND vco.vcoClave = '${contraseña}';

--changeset squintero:03
--comment: 
UPDATE Parametro SET prmValor = '30' WHERE prmNombre = 'TIEMPO_DESISTIR_CARGA_MULTIPLE_NOVEDADES';
UPDATE Parametro SET prmValor = '60' WHERE prmNombre = 'TIEMPO_ADICIONAL_SERVICIOS_CAJA';
