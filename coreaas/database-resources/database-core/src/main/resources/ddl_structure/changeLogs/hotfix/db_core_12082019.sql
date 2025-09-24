--liquibase formatted sql

--changeset jocorrea:02
--comment:
DELETE cat FROM Categoria cat
JOIN Beneficiario ben ON (cat.catIdBeneficiario = ben.benId)
JOIN Persona per ON (ben.benPersona = per.perId)
WHERE catIdBeneficiario IN (8051, 8052, 8053, 8056)
AND ben.benTipoBeneficiario = 'HIJASTRO'
AND per.perNumeroIdentificacion = '1003206730'
AND per.perTipoIdentificacion = 'TARJETA_IDENTIDAD'
AND ben.benGrupoFamiliar = 3667

DELETE ben
FROM Beneficiario ben
JOIN Persona per ON (ben.benPersona = per.perId)
WHERE 1=1
and ben.benId IN (8051, 8052, 8053, 8056)
AND ben.benTipoBeneficiario = 'HIJASTRO'
AND per.perNumeroIdentificacion = '1003206730'
AND per.perTipoIdentificacion = 'TARJETA_IDENTIDAD'
AND ben.benGrupoFamiliar = 3667

DELETE cat
FROM aud.Categoria_aud cat
JOIN aud.Beneficiario_aud ben ON (cat.catIdBeneficiario = ben.benId AND ben.REVTYPE = 0)
JOIN aud.Persona_aud per ON (ben.benPersona = per.perId AND per.REVTYPE = 0)
WHERE catIdBeneficiario IN (8051, 8052, 8053, 8056)
AND ben.benTipoBeneficiario = 'HIJASTRO'
AND per.perNumeroIdentificacion = '1003206730'
AND per.perTipoIdentificacion = 'TARJETA_IDENTIDAD'
AND ben.benGrupoFamiliar = 3667

DELETE ben
FROM aud.Beneficiario_aud ben
JOIN aud.Persona_aud per ON ben.benPersona = per.perId and per.REVTYPE = 0
WHERE ben.benId IN (8051, 8052, 8053, 8056)
AND ben.benTipoBeneficiario = 'HIJASTRO'
AND per.perNumeroIdentificacion = '1003206730'
AND per.perTipoIdentificacion = 'TARJETA_IDENTIDAD'
AND ben.benGrupoFamiliar = 3667;