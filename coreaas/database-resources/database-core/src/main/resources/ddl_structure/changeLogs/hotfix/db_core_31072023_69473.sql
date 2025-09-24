begin transaction

update vpv set vpv.VALUE = '0,1,2,3,4,5'
from validatorparamvalue  vpv
join validatorParameter vp on vp.id = vpv.validatorParameter_id
join validatorDefinition vd on vd.id = vpv.validatorDefinition_id
join validatorCatalog vc on vc.id = vp.validatorCatalog_id
where vpv.value = '0,1,2,3,4'
and vp.dataType = 'STRING'
and vp.description = 'Rango de valores'
and vc.className = 'com.asopagos.pila.validadores.transversales.ValidadorRangoValor' 
and vc.description = 'Validador de valor en rango habilitado'
and vd.validatorOrder = 19

update vpv set vpv.VALUE = '2,3,4,5'
from validatorparamvalue  vpv
join validatorParameter vp on vp.id = vpv.validatorParameter_id
join validatorDefinition vd on vd.id = vpv.validatorDefinition_id
join validatorCatalog vc on vc.id = vp.validatorCatalog_id
where vpv.value = ''
and vp.dataType = 'STRING'
and vp.description = 'Valor de referencia'
and vc.className = 'com.asopagos.pila.validadores.transversales.ValidadorCruceCampos' 
and vc.description = 'Validador de valores vacíos de un campo A de acuerdo al valor de otro campo B'
and vd.validatorOrder = 20

update vpv set vpv.VALUE = '8'
from validatorparamvalue  vpv
join validatorParameter vp on vp.id = vpv.validatorParameter_id
join validatorDefinition vd on vd.id = vpv.validatorDefinition_id
join validatorCatalog vc on vc.id = vp.validatorCatalog_id
where vpv.value = '9'
and vp.dataType = 'STRING'
and vp.description = 'Caso de comparación'
and vc.className = 'com.asopagos.pila.validadores.transversales.ValidadorCruceCampos' 
and vc.description = 'Validador de valores vacíos de un campo A de acuerdo al valor de otro campo B'
and vd.validatorOrder = 20

commit transaction