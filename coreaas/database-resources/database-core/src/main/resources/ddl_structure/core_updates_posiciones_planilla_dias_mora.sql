--liquibase formatted sql
--changeset rcastillo:01 runAlways:true runOnChange:true
--comment:  Se ajusta para el update creado por Alexander Camelo. 
	--Actualizacion campo de mora ajuste a los demas campos ya que se corre desde dias mora una posicion
	----antes 19 y termina en 32
	--FieldDefinitionLoad.id --2110148
update FieldDefinitionLoad set initialPosition=19 where label='archivoIregistro3-1campo4' and initialPosition=19 and finalPosition=32
update FieldDefinitionLoad set finalPosition=32 where label='archivoIregistro3-1campo4' and initialPosition=19 and finalPosition=32

	---dias de mora  
	--pensionados Archivo Tipo IP - Registro 3 - Campo 3: D�as de mora 
	--id 2110199
update FieldDefinitionLoad set initialPosition=14 where label='archivoIPregistro3campo3' and initialPosition=14 and finalPosition=18
update FieldDefinitionLoad set finalPosition=19 where label='archivoIPregistro3campo3' and initialPosition=14 and finalPosition=18

	  ---Archivo archivoIPregistro3campo4
	    --actual inicia en 18 finaliza 31
		--id 2110200
 update FieldDefinitionLoad set initialPosition=19 where  label='archivoIPregistro3campo4' and initialPosition=18 and finalPosition=31
 update FieldDefinitionLoad set finalPosition=32 where  label='archivoIPregistro3campo4' and initialPosition=18 and finalPosition=31
   ---Archivo archivoIPregistro3campo4
	    --actual inicia en 18 finaliza 31
		--id 2110201
update FieldDefinitionLoad set initialPosition=32 where  label='archivoIPregistro3campo5' and initialPosition=31 and finalPosition=44
update FieldDefinitionLoad set finalPosition=45 where  label='archivoIPregistro3campo5' and initialPosition=31 and finalPosition=44

	  ---numero de registros de salida tipo 2
	  --independientes
	  --id 2110091
update FieldDefinitionLoad set initialPosition=483 where  label='archivoIregistro1campo30' and initialPosition=483 and finalPosition=487
update FieldDefinitionLoad set finalPosition=488 where  label='archivoIregistro1campo30' and initialPosition=483 and finalPosition=487

	  	  ---numero de registros tipo 2
	  --actual inicia en 487 finaliza 495
	  --id 2110092
update FieldDefinitionLoad set initialPosition=488 where label='archivoIregistro1campo31' and initialPosition=487 and finalPosition=495
update FieldDefinitionLoad set finalPosition=496 where label='archivoIregistro1campo31' and initialPosition=487 and finalPosition=495

	 	  ---fecha de matrucula
	  --actual inicia en 505 finaliza 495
	  --id 2110093
 update FieldDefinitionLoad set initialPosition=496 where label='archivoIregistro1campo32' and initialPosition=495 and finalPosition=505
 update FieldDefinitionLoad set finalPosition=506 where label='archivoIregistro1campo32' and initialPosition=495 and finalPosition=505

 	   	  ---codigo del departamento
	  --actual inicia en 507 finaliza 505
	  --id 2110094
update FieldDefinitionLoad set initialPosition=506 where label='archivoIregistro1campo33' and initialPosition=505 and finalPosition=507
update FieldDefinitionLoad set finalPosition=508 where label='archivoIregistro1campo33' and initialPosition=505 and finalPosition=507

---codigo del Archivo Tipo I - Registro 1 - Campo 34: Aportante que se acoge a los beneficios del art. 5 de la ley 1429 de 2010 con respecto a aporte para las cajas de compensaci�n familiar
--actual inicia en 507 finaliza 508
--id 2110095
update FieldDefinitionLoad set initialPosition=508 where label='archivoIregistro1campo34' and initialPosition=507 and finalPosition=508
update FieldDefinitionLoad set finalPosition=509 where label='archivoIregistro1campo34' and initialPosition=507 and finalPosition=508
 
---Archivo Tipo I - Registro 1 - Campo 35: Clase de aportante
--actual inicia en 508 finaliza 509
--id 2110096
update FieldDefinitionLoad set initialPosition=509 where label='archivoIregistro1campo35' and initialPosition=508 and finalPosition=509
update FieldDefinitionLoad set finalPosition=510 where label='archivoIregistro1campo35' and initialPosition=508 and finalPosition=509

--Archivo Tipo I - Registro 1 - Campo 36: Naturaleza jur�dica
--actual inicia en 509 finaliza 510
--id 2110097
update FieldDefinitionLoad set initialPosition=510 where label='archivoIregistro1campo36' and initialPosition=509 and finalPosition=510
update FieldDefinitionLoad set finalPosition=511 where label='archivoIregistro1campo36' and initialPosition=509 and finalPosition=510

--Archivo Tipo I - Registro 1 - Campo 37: Tipo de persona
--actual inicia en 510 finaliza 511
--id 2110098
update FieldDefinitionLoad set initialPosition=511 where label='archivoIregistro1campo37' and initialPosition=510 and finalPosition=511
update FieldDefinitionLoad set finalPosition=512 where label='archivoIregistro1campo37' and initialPosition=510 and finalPosition=511

--Archivo Tipo I - Registro 2 - Campo 1: Secuencia
--actual inicia en 511 finaliza 521
--id 2110238
update FieldDefinitionLoad set initialPosition=512 where label='archivoIregistro1campo38' and initialPosition=511 and finalPosition=521
update FieldDefinitionLoad set finalPosition=522 where label='archivoIregistro1campo38' and initialPosition=511 and finalPosition=521

--Archivo Tipo I - Registro 3 Rengl�n 36 - Campo 3: Nu�mero de di�as de mora liquidado
--actual inicia en 6 finaliza 10
--id 2110151
update FieldDefinitionLoad set initialPosition=6 where label='archivoIregistro3-2campo3' and initialPosition=6 and finalPosition=10
update FieldDefinitionLoad set finalPosition=11 where label='archivoIregistro3-2campo3' and initialPosition=6 and finalPosition=10

--archivoIregistro3-2campo4
--actual inicia en 10 finaliza 21
--id 2110152
update FieldDefinitionLoad set initialPosition=11 where label='archivoIregistro3-2campo4' and initialPosition=10 and finalPosition=21
update FieldDefinitionLoad set finalPosition=22 where label='archivoIregistro3-2campo4' and initialPosition=10 and finalPosition=21

----pensionado registro tipo 1 que tiene que ver con lo demora
 --archivoIPregistro1campo18
--actual inicia en 295 finaliza 300
--id 2110173
update FieldDefinitionLoad set  initialPosition=295 where label='archivoIPregistro1campo18' and initialPosition=295 and finalPosition=298
update FieldDefinitionLoad set finalPosition=300 where label='archivoIPregistro1campo18' and initialPosition=295 and finalPosition=298

  --archivoIPregistro1campo19
--actual inicia en 298 finaliza 300
--id 2110174
update FieldDefinitionLoad set  initialPosition=300,finalPosition=302 where label='archivoIPregistro1campo19' and initialPosition=298 and finalPosition=300

 --archivoIPregistro1campo20
--actual inicia en 300 finaliza 308
--id 2110175
update FieldDefinitionLoad set initialPosition=302 where label='archivoIPregistro1campo20' and initialPosition=300 and finalPosition=308
update FieldDefinitionLoad set finalPosition=310 where label='archivoIPregistro1campo20' and initialPosition=300 and finalPosition=308

 --archivoIPregistro1campo20
--actual inicia en 308 finaliza 318
--id 2110239
update FieldDefinitionLoad set initialPosition=310 where label='archivoIPregistro1campo21' and initialPosition=308 and finalPosition=318
update FieldDefinitionLoad set finalPosition=320 where label='archivoIPregistro1campo21' and initialPosition=308 and finalPosition=318
--tipos de cotizantes permitidos
--antes 1,2,3,4,12,15,16,18,19,20,21,22,23,30,31,32,33,34,35,36,40,42,43,44,45,47,51,52,53,54,55,56,57,58,59,60,61,62
update ValidatorParamValue set value='1,2,3,4,12,15,16,18,19,20,21,22,23,30,31,32,33,34,35,36,40,42,43,44,45,47,51,52,53,54,55,56,57,58,59,60,61,62' where id=2110211

 /*CAMBIO FULL--consulta para sacar todas las rtelaciones d evalidaciones
  select * from ValidatorParamValue v
  inner join ValidatorDefinition d on d.id=v.validatorDefinition_id
  inner join ValidatorParameter p on p.id=v.validatorParameter_id
  left join ValidatorCatalog c on c.id=p.validatorCatalog_id
  left join FileDefinition f on f.id=d.fieldDefinition_id
  left join FIELDLOADCATALOG fg on fg.id=f.fileLocation_id
  left join LineDefinition l on l.id=d.fieldDefinition_id
  left join LineCatalog  lc on lc.id=l.lineCatalog_id
  left join FieldDefinition fi on fi.id=d.fieldDefinition_id
  left join FieldDefinitionLoad fl on fl.id=d.fieldDefinition_id
  where 
--fl.initialPosition=19
  --fl.id=2110103
  value like '%mora%' 
  or fl.initialPosition=33
  --tabla donde estan las posiciones de cada planilla
 select * from FieldDefinitionLoad where label like '%registro2campo5%'*/