
--HIJO_ADOPTIVO
			
insert into RequisitoCajaClasificacion (rtsestado, rtsrequisito, rtsclasificacion,rtsTipoTransaccion, rtscajacompensacion, rtstextoayuda, rtstiporequisito)
			values ('OPCIONAL',(select reqId from requisito where reqDescripcion = 'Declaración juramentada de no trabajo no ingresos'),'HIJO_ADOPTIVO','ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_DEPWEB',(select cnsValor from Constante where cnsNombre = 'CAJA_COMPENSACION_ID'),'pEdad entre 0 y 6 años cumplidos: OPCIONAL<br />Edad entre 7 y 17 años cumplidos: Tarjeta de identidad (OBLIGATORIO)<br />Edad igual o mayor a 18 años: Cédula de ciudadanía o Cédula de extranjería (OBLIGATORIO)<br />Se revisa:<br />-No debe tener tachones o enmendaduras<br />-El tipo y número de documento de identidad deben coincidir con el registrado en el formulario de afiliación<br />-Los nombres y apellidos deben corresponder a los registrados en el formulario de afiliación<br />-La fecha de nacimiento debe corresponder a la registrada en el formulario de afiliación','ESTANDAR')

insert into RequisitoCajaClasificacion (rtsestado, rtsrequisito, rtsclasificacion,rtsTipoTransaccion, rtscajacompensacion, rtstextoayuda, rtstiporequisito)
			values ('OPCIONAL',(select reqId from requisito where reqDescripcion = 'Certificación medica EPS-IPS'),'HIJO_ADOPTIVO','ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_DEPWEB',(select cnsValor from Constante where cnsNombre = 'CAJA_COMPENSACION_ID'),'pEdad entre 0 y 6 años cumplidos: OPCIONAL<br />Edad entre 7 y 17 años cumplidos: Tarjeta de identidad (OBLIGATORIO)<br />Edad igual o mayor a 18 años: Cédula de ciudadanía o Cédula de extranjería (OBLIGATORIO)<br />Se revisa:<br />-No debe tener tachones o enmendaduras<br />-El tipo y número de documento de identidad deben coincidir con el registrado en el formulario de afiliación<br />-Los nombres y apellidos deben corresponder a los registrados en el formulario de afiliación<br />-La fecha de nacimiento debe corresponder a la registrada en el formulario de afiliación','ESTANDAR')

		

--PADRE


			
insert into RequisitoCajaClasificacion (rtsestado, rtsrequisito, rtsclasificacion,rtsTipoTransaccion, rtscajacompensacion, rtstextoayuda, rtstiporequisito)
			values ('OPCIONAL',(select reqId from requisito where reqDescripcion = 'Declaración juramentada de no trabajo no ingresos'),'PADRE','ACTIVAR_BENEFICIARIO_PADRE_DEPWEB',(select cnsValor from Constante where cnsNombre = 'CAJA_COMPENSACION_ID'),'pEdad entre 0 y 6 años cumplidos: OPCIONAL<br />Edad entre 7 y 17 años cumplidos: Tarjeta de identidad (OBLIGATORIO)<br />Edad igual o mayor a 18 años: Cédula de ciudadanía o Cédula de extranjería (OBLIGATORIO)<br />Se revisa:<br />-No debe tener tachones o enmendaduras<br />-El tipo y número de documento de identidad deben coincidir con el registrado en el formulario de afiliación<br />-Los nombres y apellidos deben corresponder a los registrados en el formulario de afiliación<br />-La fecha de nacimiento debe corresponder a la registrada en el formulario de afiliación','ESTANDAR')

insert into RequisitoCajaClasificacion (rtsestado, rtsrequisito, rtsclasificacion,rtsTipoTransaccion, rtscajacompensacion, rtstextoayuda, rtstiporequisito)
			values ('OPCIONAL',(select reqId from requisito where reqDescripcion = 'Certificación medica EPS-IPS'),'PADRE','ACTIVAR_BENEFICIARIO_PADRE_DEPWEB',(select cnsValor from Constante where cnsNombre = 'CAJA_COMPENSACION_ID'),'pEdad entre 0 y 6 años cumplidos: OPCIONAL<br />Edad entre 7 y 17 años cumplidos: Tarjeta de identidad (OBLIGATORIO)<br />Edad igual o mayor a 18 años: Cédula de ciudadanía o Cédula de extranjería (OBLIGATORIO)<br />Se revisa:<br />-No debe tener tachones o enmendaduras<br />-El tipo y número de documento de identidad deben coincidir con el registrado en el formulario de afiliación<br />-Los nombres y apellidos deben corresponder a los registrados en el formulario de afiliación<br />-La fecha de nacimiento debe corresponder a la registrada en el formulario de afiliación','ESTANDAR')

			

--HIJO BIOLOGICO

			
insert into RequisitoCajaClasificacion (rtsestado, rtsrequisito, rtsclasificacion,rtsTipoTransaccion, rtscajacompensacion, rtstextoayuda, rtstiporequisito)
			values ('OPCIONAL',(select reqId from requisito where reqDescripcion = 'Declaración juramentada de no trabajo no ingresos'),'HIJO_BIOLOGICO','ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_DEPWEB',(select cnsValor from Constante where cnsNombre = 'CAJA_COMPENSACION_ID'),'pEdad entre 0 y 6 años cumplidos: OPCIONAL<br />Edad entre 7 y 17 años cumplidos: Tarjeta de identidad (OBLIGATORIO)<br />Edad igual o mayor a 18 años: Cédula de ciudadanía o Cédula de extranjería (OBLIGATORIO)<br />Se revisa:<br />-No debe tener tachones o enmendaduras<br />-El tipo y número de documento de identidad deben coincidir con el registrado en el formulario de afiliación<br />-Los nombres y apellidos deben corresponder a los registrados en el formulario de afiliación<br />-La fecha de nacimiento debe corresponder a la registrada en el formulario de afiliación','ESTANDAR')

insert into RequisitoCajaClasificacion (rtsestado, rtsrequisito, rtsclasificacion,rtsTipoTransaccion, rtscajacompensacion, rtstextoayuda, rtstiporequisito)
			values ('OPCIONAL',(select reqId from requisito where reqDescripcion = 'Certificación medica EPS-IPS'),'HIJO_BIOLOGICO','ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_DEPWEB',(select cnsValor from Constante where cnsNombre = 'CAJA_COMPENSACION_ID'),'pEdad entre 0 y 6 años cumplidos: OPCIONAL<br />Edad entre 7 y 17 años cumplidos: Tarjeta de identidad (OBLIGATORIO)<br />Edad igual o mayor a 18 años: Cédula de ciudadanía o Cédula de extranjería (OBLIGATORIO)<br />Se revisa:<br />-No debe tener tachones o enmendaduras<br />-El tipo y número de documento de identidad deben coincidir con el registrado en el formulario de afiliación<br />-Los nombres y apellidos deben corresponder a los registrados en el formulario de afiliación<br />-La fecha de nacimiento debe corresponder a la registrada en el formulario de afiliación','ESTANDAR')

			
--MADRE 


			
insert into RequisitoCajaClasificacion (rtsestado, rtsrequisito, rtsclasificacion,rtsTipoTransaccion, rtscajacompensacion, rtstextoayuda, rtstiporequisito)
			values ('OPCIONAL',(select reqId from requisito where reqDescripcion = 'Declaración juramentada de no trabajo no ingresos'),'MADRE','ACTIVAR_BENEFICIARIO_MADRE_DEPWEB',(select cnsValor from Constante where cnsNombre = 'CAJA_COMPENSACION_ID'),'pEdad entre 0 y 6 años cumplidos: OPCIONAL<br />Edad entre 7 y 17 años cumplidos: Tarjeta de identidad (OBLIGATORIO)<br />Edad igual o mayor a 18 años: Cédula de ciudadanía o Cédula de extranjería (OBLIGATORIO)<br />Se revisa:<br />-No debe tener tachones o enmendaduras<br />-El tipo y número de documento de identidad deben coincidir con el registrado en el formulario de afiliación<br />-Los nombres y apellidos deben corresponder a los registrados en el formulario de afiliación<br />-La fecha de nacimiento debe corresponder a la registrada en el formulario de afiliación','ESTANDAR')

insert into RequisitoCajaClasificacion (rtsestado, rtsrequisito, rtsclasificacion,rtsTipoTransaccion, rtscajacompensacion, rtstextoayuda, rtstiporequisito)
			values ('OPCIONAL',(select reqId from requisito where reqDescripcion = 'Certificación medica EPS-IPS'),'MADRE','ACTIVAR_BENEFICIARIO_MADRE_DEPWEB',(select cnsValor from Constante where cnsNombre = 'CAJA_COMPENSACION_ID'),'pEdad entre 0 y 6 años cumplidos: OPCIONAL<br />Edad entre 7 y 17 años cumplidos: Tarjeta de identidad (OBLIGATORIO)<br />Edad igual o mayor a 18 años: Cédula de ciudadanía o Cédula de extranjería (OBLIGATORIO)<br />Se revisa:<br />-No debe tener tachones o enmendaduras<br />-El tipo y número de documento de identidad deben coincidir con el registrado en el formulario de afiliación<br />-Los nombres y apellidos deben corresponder a los registrados en el formulario de afiliación<br />-La fecha de nacimiento debe corresponder a la registrada en el formulario de afiliación','ESTANDAR')

			
--HIJASRTO


insert into RequisitoCajaClasificacion (rtsestado, rtsrequisito, rtsclasificacion,rtsTipoTransaccion, rtscajacompensacion, rtstextoayuda, rtstiporequisito)
			values ('OPCIONAL',(select reqId from requisito where reqDescripcion = 'Declaración juramentada de no trabajo no ingresos'),'HIJASTRO','ACTIVAR_BENEFICIARIO_HIJASTRO_DEPWEB',(select cnsValor from Constante where cnsNombre = 'CAJA_COMPENSACION_ID'),'pEdad entre 0 y 6 años cumplidos: OPCIONAL<br />Edad entre 7 y 17 años cumplidos: Tarjeta de identidad (OBLIGATORIO)<br />Edad igual o mayor a 18 años: Cédula de ciudadanía o Cédula de extranjería (OBLIGATORIO)<br />Se revisa:<br />-No debe tener tachones o enmendaduras<br />-El tipo y número de documento de identidad deben coincidir con el registrado en el formulario de afiliación<br />-Los nombres y apellidos deben corresponder a los registrados en el formulario de afiliación<br />-La fecha de nacimiento debe corresponder a la registrada en el formulario de afiliación','ESTANDAR')

insert into RequisitoCajaClasificacion (rtsestado, rtsrequisito, rtsclasificacion,rtsTipoTransaccion, rtscajacompensacion, rtstextoayuda, rtstiporequisito)
			values ('OPCIONAL',(select reqId from requisito where reqDescripcion = 'Certificación medica EPS-IPS'),'HIJASTRO','ACTIVAR_BENEFICIARIO_HIJASTRO_DEPWEB',(select cnsValor from Constante where cnsNombre = 'CAJA_COMPENSACION_ID'),'pEdad entre 0 y 6 años cumplidos: OPCIONAL<br />Edad entre 7 y 17 años cumplidos: Tarjeta de identidad (OBLIGATORIO)<br />Edad igual o mayor a 18 años: Cédula de ciudadanía o Cédula de extranjería (OBLIGATORIO)<br />Se revisa:<br />-No debe tener tachones o enmendaduras<br />-El tipo y número de documento de identidad deben coincidir con el registrado en el formulario de afiliación<br />-Los nombres y apellidos deben corresponder a los registrados en el formulario de afiliación<br />-La fecha de nacimiento debe corresponder a la registrada en el formulario de afiliación','ESTANDAR')

			
--HUERFANO


			
insert into RequisitoCajaClasificacion (rtsestado, rtsrequisito, rtsclasificacion,rtsTipoTransaccion, rtscajacompensacion, rtstextoayuda, rtstiporequisito)
			values ('OPCIONAL',(select reqId from requisito where reqDescripcion = 'Declaración juramentada de no trabajo no ingresos'),'HERMANO_HUERFANO_DE_PADRES','ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_DEPWEB',(select cnsValor from Constante where cnsNombre = 'CAJA_COMPENSACION_ID'),'pEdad entre 0 y 6 años cumplidos: OPCIONAL<br />Edad entre 7 y 17 años cumplidos: Tarjeta de identidad (OBLIGATORIO)<br />Edad igual o mayor a 18 años: Cédula de ciudadanía o Cédula de extranjería (OBLIGATORIO)<br />Se revisa:<br />-No debe tener tachones o enmendaduras<br />-El tipo y número de documento de identidad deben coincidir con el registrado en el formulario de afiliación<br />-Los nombres y apellidos deben corresponder a los registrados en el formulario de afiliación<br />-La fecha de nacimiento debe corresponder a la registrada en el formulario de afiliación','ESTANDAR')

insert into RequisitoCajaClasificacion (rtsestado, rtsrequisito, rtsclasificacion,rtsTipoTransaccion, rtscajacompensacion, rtstextoayuda, rtstiporequisito)
			values ('OPCIONAL',(select reqId from requisito where reqDescripcion = 'Certificación medica EPS-IPS'),'HERMANO_HUERFANO_DE_PADRES','ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_DEPWEB',(select cnsValor from Constante where cnsNombre = 'CAJA_COMPENSACION_ID'),'pEdad entre 0 y 6 años cumplidos: OPCIONAL<br />Edad entre 7 y 17 años cumplidos: Tarjeta de identidad (OBLIGATORIO)<br />Edad igual o mayor a 18 años: Cédula de ciudadanía o Cédula de extranjería (OBLIGATORIO)<br />Se revisa:<br />-No debe tener tachones o enmendaduras<br />-El tipo y número de documento de identidad deben coincidir con el registrado en el formulario de afiliación<br />-Los nombres y apellidos deben corresponder a los registrados en el formulario de afiliación<br />-La fecha de nacimiento debe corresponder a la registrada en el formulario de afiliación','ESTANDAR')
