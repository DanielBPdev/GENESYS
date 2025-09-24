-- CREAR NUEVAS CLAVES PARA CARGO, FIRMA Y NOMBRE
-- GLPI 74927
------------Cargo Gerente Financiera-----------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'CRT_ASIG_FOVIS'
	and v.vcoClave='${cargogerentefinanciera}')
BEGIN
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct 
  '${cargogerentefinanciera}',
  'Corresponde al cargo que tiene la gerente financiera de  fovis',
  'Cargo Gerente Financiera',
  pcoId,
  'CARGO_GERENTE_FINANCIERA',
  'CONSTANTE',
  0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'CRT_ASIG_FOVIS'
END

------------Firma Gerente Financiera-----------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'CRT_ASIG_FOVIS'
	and v.vcoClave='${firmagerentefinanciera}')
BEGIN
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct 
  '${firmagerentefinanciera}',
  'Firma del gerente financiero',
  'Firma Gerente Financiera',
  pcoId,
  'FIRMA_GERENTE_FINANCIERA',
  'CONSTANTE',
  0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'CRT_ASIG_FOVIS'
END

------------Gerente Financiera-----------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'CRT_ASIG_FOVIS'
	and v.vcoClave='${gerentefinanciera}')
BEGIN
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct 
  '${gerentefinanciera}',
  'Gerente financiero',
  'Gerente Financiera',
  pcoId,
  'FIRMA_GERENTE_FINANCIERA',
  'CONSTANTE',
  0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'CRT_ASIG_FOVIS'
END

------------Cargo Gerente Comercial-----------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'CRT_BVD_EMP'
	and v.vcoClave='${cargogerentecomercial}')
BEGIN
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct 
  '${cargogerentecomercial}',
  'Corresponde al cargo que tiene el gerente comercial de el proceso de afiliación empresa',
  'Cargo Gerente Comercial',
  pcoId,
  'CARGO_GERENTE_COMERCIAL',
  'CONSTANTE',
  0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'CRT_BVD_EMP'
END

------------Firma Gerente Comercial-----------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'CRT_BVD_EMP'
	and v.vcoClave='${firmagerentecomercial}')
BEGIN
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct 
  '${firmagerentecomercial}',
  'Firma de gerente comercial',
  'Firma Gerente Comercial',
  pcoId,
  'FIRMA_GERENTE_COMERCIAL',
  'CONSTANTE',
  0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'CRT_BVD_EMP'
END

------------Gerente Comercial-----------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'CRT_BVD_EMP'
	and v.vcoClave='${gerentecomercial}')
BEGIN
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct 
  '${gerentecomercial}',
  'Gerente Comercial',
  'Gerente Comercial',
  pcoId,
  'GERENTE_COMERCIAL',
  'CONSTANTE',
  0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'CRT_BVD_EMP'
END

------------Cargo Secretaria General Empresa-----------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'CAR_EMP_EXP'
	and v.vcoClave='${cargosecretariageneral}')
BEGIN
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct 
  '${cargosecretariageneral}',
  'Corresponde al cargo que tiene la secretaria general en cartera',
  'Cargo Secretaria General',
  pcoId,
  'CARGO_SECRETARIA_GENERAL',
  'CONSTANTE',
  0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'CAR_EMP_EXP'
END

------------Firma Secretaria  Empresa-----------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'CAR_EMP_EXP'
	and v.vcoClave='${firmasecretariageneral}')
BEGIN
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct 
  '${firmasecretariageneral}',
  'Firma secretaria general',
  'Firma Secretaria General',
  pcoId,
  'FIRMA_SECRETARIA_GENERAL',
  'CONSTANTE',
  0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'CAR_EMP_EXP'
END

------------Secretaria General Empresa-----------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'CAR_EMP_EXP'
	and v.vcoClave='${secretariageneral}')
BEGIN
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct 
  '${secretariageneral}',
  'Secretaria General',
  'Secretaria General',
  pcoId,
  'SECRETARIA_GENERAL',
  'CONSTANTE',
  0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'CAR_EMP_EXP'
END

------------Cargo Secretaria General Persona-----------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'CAR_PER_EXP'
	and v.vcoClave='${cargosecretariageneral}')
BEGIN
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct 
  '${cargosecretariageneral}',
  'Corresponde al cargo que tiene la secretaria general en cartera',
  'Cargo Secretaria General',
  pcoId,
  'CARGO_SECRETARIA_GENERAL',
  'CONSTANTE',
  0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'CAR_PER_EXP'
END

------------Firma Secretaria  Persona-----------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'CAR_PER_EXP'
	and v.vcoClave='${firmasecretariageneral}')
BEGIN
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct 
  '${firmasecretariageneral}',
  'Firma secretaria general',
  'Firma Secretaria General',
  pcoId,
  'FIRMA_SECRETARIA_GENERAL',
  'CONSTANTE',
  0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'CAR_PER_EXP'
END

------------Secretaria General Persona-----------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'CAR_PER_EXP'
	and v.vcoClave='${secretariageneral}')
BEGIN
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct 
  '${secretariageneral}',
  'Secretaria General',
  'Secretaria General',
  pcoId,
  'SECRETARIA_GENERAL',
  'CONSTANTE',
  0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'CAR_PER_EXP'
END