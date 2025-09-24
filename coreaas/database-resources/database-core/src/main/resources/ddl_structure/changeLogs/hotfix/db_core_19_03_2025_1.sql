UPDATE h
SET h.hbeBeneficiario = b.benId
FROM HistoricoBeneficiario h
INNER JOIN Persona per ON per.perNumeroIdentificacion = h.hbeNumeroIdentificacionBeneficiario AND per.perTipoIdentificacion = h.hbeTipoIdentificacionBeneficiario
INNER JOIN Beneficiario b ON b.benPersona = per.perId;