--liquibase formatted sql

--changeset jocorrea:02
--comment:
DELETE Cat 
FROM Categoria_aud cat
JOIN Beneficiario_aud ben ON (cat.catIdBeneficiario = ben.benId AND ben.REVTYPE = 0)
JOIN Persona_aud per ON (ben.benPersona = per.perId AND per.REVTYPE = 0)
WHERE catIdBeneficiario IN (8051, 8052, 8053, 8056)
AND ben.benTipoBeneficiario = 'HIJASTRO'
AND per.perNumeroIdentificacion = '1003206730'
AND per.perTipoIdentificacion = 'TARJETA_IDENTIDAD'
AND ben.benGrupoFamiliar = 3667

DELETE ben
FROM Beneficiario_aud ben
JOIN Persona_aud per ON ben.benPersona = per.perId and per.REVTYPE = 0
WHERE ben.benId IN (8051, 8052, 8053, 8056)
AND ben.benTipoBeneficiario = 'HIJASTRO'
AND per.perNumeroIdentificacion = '1003206730'
AND per.perTipoIdentificacion = 'TARJETA_IDENTIDAD'
AND ben.benGrupoFamiliar = 3667