--- GAP 53849 - Adición Causal Bloqueo
-- Migracion
UPDATE BloqueoBeneficiarioCuotaMonetaria
SET bbcCausalBloqueo = CASE
    WHEN bbcBeneficiarioComoAfiliadoOtraCCF = 1 THEN 'BENEFICIARIO_AFILIADO_OTRA_CCF'
    WHEN bbcAsignacionCuotaPorOtraCCF = 1 THEN 'SUBSIDIO_ASIGNADO_OTRA_CCF'
    ELSE NULL
END
