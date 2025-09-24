delete from FieldDefinitionLoad
where id in (300000727,
300000728,300000741,
300000742,
300000743,
300000744,
300000745,
300000746,
300000747,
300000748
)

delete from FieldLoadCatalog
where id in (300000727,
300000728,300000741,
300000742,
300000743,
300000744,
300000745,
300000746,
300000747,
300000748
)



insert into FieldLoadCatalog (id,dataType, description, name, fieldOrder) values
(300000741,'STRING', 'Direccion afiliado a cargo', 'trasladoMasivosEmpresasCCFCargoDireccionAfiliadoCargo',13),
(300000742,'STRING', 'Telefono celular afiliado a cargo', 'trasladoMasivosEmpresasCCFCargoTelefonoCelularAfiliadoCargo',14),
(300000743,'STRING', 'Telefono fijo afiliado a cargo', 'trasladoMasivosEmpresasCCFCargoTelefonoFijoAfiliadoCargo',15),
(300000744,'STRING', 'Conyuge labora', 'trasladoMasivosEmpresasCCFCargoConyugeLabora',16),
(300000745,'STRING', 'Valor ingreso mensual a cargo', 'trasladoMasivosEmpresasCCFCargoIngresoMensual',17),
(300000746,'STRING', 'Fecha de inicio de la unión con el afiliado principal', 'trasladoMasivosEmpresasCCFCargoFechaInicioUnionAfiliado',18)

insert into FieldDefinitionLoad (id,finalPosition, formatDate, initialPosition, label, identifierLine, removeFormat, required,fieldLoadCatalog_id, lineDefinition_id) values
	(300000741,NULL,NULL,NULL, 'Direccion afiliado a cargo', 0,0,0,300000741,1701),
	(300000742,NULL,NULL,NULL, 'Telefono celular afiliado a cargo', 0,0,0,300000742,1701),--
	(300000743,NULL,NULL,NULL, 'Telefono fijo afiliado a cargo', 0,0,0,300000743,1701),
	(300000744,NULL,NULL,NULL, 'Conyuge labora', 0,0,0,300000744,1701),
	(300000745,NULL,NULL,NULL, 'Valor ingreso mensual a cargo', 0,0,0,300000745,1701),
	(300000746,NULL,NULL,NULL, 'Fecha de inicio de la unión con el afiliado principal', 0,0,0,300000746,1701)

update FieldLoadCatalog
set fieldOrder = fieldOrder-2
where id < 300000741 and id >300000726


update FieldDefinitionLoad
set required = 1
where id in (300000729,
300000730,
300000731,
300000732,
300000733,
300000735,
300000737,
300000738,
300000739,
300000740,
300000741,
300000742,
300000743
)

delete from FieldDefinitionLoad
where id in (300000718,
300000719,
300000720,
300000721,
300000722,
300000723,
300000724,
300000725,
300000726)

delete from FieldLoadCatalog
where id in (300000718,
300000719,
300000720,
300000721,
300000722,
300000723,
300000724,
300000725,
300000726)

insert into FieldLoadCatalog (id,dataType, description, name, fieldOrder) values
	(300000718,'STRING', 'Reside sector rural', 'trasladoMasivosEmpresasCCFResideSectorRural',19),
	(300000719,'STRING', 'Codigo municipio labor afiliado', 'trasladoMasivosEmpresasCCFCodigoMunicipioLaborAfiliado',20),
	(300000720,'STRING', 'Salario mensual', 'trasladoMasivosEmpresasCCFSalarioMensual',21),
	(300000721,'STRING', 'Tipo Solicitante', 'trasladoMasivosEmpresasCCFTipoSolicitante',22),
	(300000722,'STRING', 'Direccion afiliado', 'trasladoMasivosEmpresasCCFDireccionAfiliado',23),
	(300000723,'STRING', 'Telefono celular', 'trasladoMasivosEmpresasCCFTelefonoCelular',24),
	(300000724,'STRING', 'Telefono fijo', 'trasladoMasivosEmpresasCCFTelefonoFijo',25),
	(300000725,'STRING', 'Correo electronico', 'trasladoMasivosEmpresasCCFCorreoElectronico',26),
	(300000726,'STRING', 'Categoria', 'trasladoMasivosEmpresasCCFCategoria',27),
	(300000727,'STRING', 'Beneficiario de cuota monetaria', 'trasladoMasivosEmpresasCCFBeneficiarioCuota',28);

	insert into FieldDefinitionLoad (id,finalPosition, formatDate, initialPosition, label, identifierLine, removeFormat, required,fieldLoadCatalog_id, lineDefinition_id) values
	(300000718,NULL,NULL,NULL, 'Reside sector rural', 0,0,0,300000718,1700),
	(300000719,NULL,NULL,NULL, 'Codigo municipio labor afiliado', 0,0,0,300000719,1700),
	(300000720,NULL,NULL,NULL, 'Salario mensual', 0,0,0,300000720,1700),
	(300000721,NULL,NULL,NULL, 'Tipo Solicitante', 0,0,0,300000721,1700),
	(300000722,NULL,NULL,NULL, 'Direccion afiliado', 0,0,0,300000722,1700),
	(300000723,NULL,NULL,NULL, 'Telefono celular', 0,0,0,300000723,1700),
	(300000724,NULL,NULL,NULL, 'Telefono fijo', 0,0,0,300000724,1700),
	(300000725,NULL,NULL,NULL, 'Correo electronico', 0,0,0,300000725,1700),
	(300000726,NULL,NULL,NULL, 'Categoria', 0,0,0,300000726,1700),
	(300000727,NULL,NULL,NULL, 'Beneficiario de cuota monetaria', 0,0,0,300000727,1700);


update FieldDefinitionLoad
set required = 0
where id in (300000700,
300000701,
300000710,
300000711,
300000712,
300000713,
300000715,
300000716)

update FieldDefinitionLoad
set required = 1
where id in (300000717,
300000718,
300000720,
300000721,
300000722,
300000723,
300000724)