update ValidatorParameter  
set description = 'Rango de valores'
where id= 211006

update ValidatorParameter 
set name = 'rangoValor'
where id = 211006

update ValidatorCatalog  
set name = 'Range validator'
where id= 211002

update ValidatorCatalog  
set className = 'com.asopagos.pila.validadores.transversales.ValidadorRangoValor'
where id= 211002

update ValidatorCatalog  
set description = 'Validador de valor en rango habilitado'
where id= 211002

update 
ValidatorParamValue  
set value = 'X,L'
where id= 2110346