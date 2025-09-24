----Actualizacion formato clave para los departamentos y municipios---------
UPDATE VariableComunicado
SET vcoTipoVariableComunicado = 'LUGAR_MAYUS'
FROM VariableComunicado vc
INNER JOIN PlantillaComunicado pc ON vc.vcoPlantillaComunicado = pc.pcoId
WHERE vcoClave IN ('${departamentoEmpleador}', '${departamentoEmpresa}', '${departamentoAfiliado}', 
'${departamentoAportante}', '${departamentoRepesentanteLegal}', '${departamentoAsociadoProyecto}','${municipioAsociadoProyecto}'
,'${municipioUbiPrincipal}', '${municipioEmpleador}','${municipioEmpresa}','${municipioAfiliado}', '${municipioRepesentanteLegal}');