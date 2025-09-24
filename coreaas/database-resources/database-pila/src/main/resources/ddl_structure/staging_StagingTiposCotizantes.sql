-- GLPI 70520
if not exists (select 1 from information_schema.tables where table_schema = 'staging' and table_name = 'StagingTiposCotizantes')
	begin
		select *
		into staging.StagingTiposCotizantes
		from ( values 
		('TIPO_COTIZANTE_DEPENDIENTE',1,'Dependiente','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_SERVICIO_DOMESTICO',2,'Servicio doméstico','TRABAJADOR_DEPENDIENTE','SERVICIO_DOMESTICO'),
		('TIPO_COTIZANTE_INDEPENDIENTE',3,'Independiente','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_MADRE_SUSTITUTA',4,'Madre sustituta','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA',12,'Aprendices en etapa lectiva','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_DESEMPLEADO_SCCF',15,'Desempleado con subsidio de Caja de Compensación Familiar','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO',16,'Independiente agremiado o asociado','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC',18,'Funcionarios públicos sin tope máximo de IBC','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA',19,'Aprendices en etapa productiva','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_ESTUDIANTES_REG_ESP',20,'Estudiantes Régimen especial ley 789 de 2002','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD',21,'Estudiantes de postgrado en salud Decreto 2376 de 2010','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR',22,'Profesor de establecimiento particular','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL',23,'Estudiantes aporte solo riesgos laborales','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI',30,'Dependiente entidades o universidades públicas de los regímenes especial y de excepción','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO',31,'Cooperados o precooperativas de trabajo asociado','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL',32,'Cotizante miembro de la carrera diplomática o consular de un país extranjero o funcionario de organismo multilateral','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN',33,'Beneficiario del fondo de solidaridad pensional','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD',34,'Concejal o edil de Junta Administradora Local del Distrito Capital de Bogotá amparado por póliza de salud','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD',35,'Concejal municipal o distrital no amparado con póliza de salud','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP',36,'Concejal municipal o distrital o edil de Junta Administradora Local no amparado con póliza de salud beneficiario del Fondo de Solidaridad Pensional','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_BENEFICIARIO_UPC',40,'Beneficiario UPC adicional','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD',42,'Cotizante independiente pago solo salud.','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_PAGO_POR_TERCERO',43,'Cotizante a pensiones con pago por tercero.','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES',44,'Cotizante dependiente de empleo de emergencia con duración mayor o igual a un mes','TRABAJADOR_DEPENDIENTE','TRABAJADOR_POR_DIAS'),
		('TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES',45,'Cotizante dependiente de empleo de emergencia con duración menor a un mes','TRABAJADOR_DEPENDIENTE','TRABAJADOR_POR_DIAS'),
		('TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI',47,'Trabajador dependiente de entidad beneficiaria del sistema general de participaciones - Aportes patronales','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL',51,'Trabajador de tiempo parcial ','TRABAJADOR_DEPENDIENTE','TRABAJADOR_POR_DIAS'),
		('TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE',52,'Beneficiario del Mecanismo de Protección al Cesante','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_AFILIADO_PARTICIPE',53,'Afiliado Participe','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ',54,'Prepensionado de entidad en liquidación','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE',55,'Afiliado participe dependiente','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_PREPENSIONADO_AVS',56,'Prepensionado con aporte voluntario a Salud','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_IND_VOLUNTARIO_ARL',57,'Independiente voluntario a Riesgos Laborales','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_ESTUDIANTE_PRACTICA_LABORAL_SECTOR_PUBLICO',58,'Estudiantes de prácticas laborales en el sector público','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES',59,'Independiente con contrato de prestación de servicios superior a 1 mes','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_EDIL_JAL ',60,'Edil Junta Administradora Local no beneficiario del Fondo de Solidaridad Pensional','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_PROGRAMA_REINCORPORACION ',61,'Beneficiario programa de reincorporación','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_PERSONAL_DEL_MAGISTERIO ',62,'Personal del Magisterio','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA',63,'Beneficiario de prestación humanitaria','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_TRABAJADOR_PENITENCIARIO',64,'Trabajador penitenciario','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL',65,'Dependiente vinculado al piso de protección social','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL',66,'Independiente vinculado al piso de protección social','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL',67,'Voluntario en primera respuesta aporte solo riesgos laborales','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA',68,'Dependiente Veterano de la Fuerza Publica','TRABAJADOR_DEPENDIENTE','VETERANO_FUERZA_PUBLICA'),
		('TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO',69,'Contribuyente solidario','TRABAJADOR_DEPENDIENTE','REGULAR')) as t
		(tipo, tipoCot, descri, tipotra, otro)
	end
else
	begin
		;with tblTiposCot as (
		select *
		from ( values 
		('TIPO_COTIZANTE_DEPENDIENTE',1,'Dependiente','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_SERVICIO_DOMESTICO',2,'Servicio doméstico','TRABAJADOR_DEPENDIENTE','SERVICIO_DOMESTICO'),
		('TIPO_COTIZANTE_INDEPENDIENTE',3,'Independiente','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_MADRE_SUSTITUTA',4,'Madre sustituta','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_APRENDICES_ETAP_ACTIVA',12,'Aprendices en etapa lectiva','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_DESEMPLEADO_SCCF',15,'Desempleado con subsidio de Caja de Compensación Familiar','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_INDEPEND_AGRE_ASOCIADO',16,'Independiente agremiado o asociado','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_FUNCIONARIO_PUBLICO_SIBC',18,'Funcionarios públicos sin tope máximo de IBC','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_APRENDICES_ETAP_PRODUCTIVA',19,'Aprendices en etapa productiva','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_ESTUDIANTES_REG_ESP',20,'Estudiantes Régimen especial ley 789 de 2002','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_ESTUDIANTES_POST_SALUD',21,'Estudiantes de postgrado en salud Decreto 2376 de 2010','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_PROFESOR_EST_PARTICULAR',22,'Profesor de establecimiento particular','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_ESTUDIANTES_APORTES_RL',23,'Estudiantes aporte solo riesgos laborales','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_DEPENDIENTE_ENT_UNI',30,'Dependiente entidades o universidades públicas de los regímenes especial y de excepción','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_COOPERADOS_TRABAJO_ASO',31,'Cooperados o precooperativas de trabajo asociado','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_FUNCIONARIO_ORG_MULTILATERAL',32,'Cotizante miembro de la carrera diplomática o consular de un país extranjero o funcionario de organismo multilateral','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_BENEFI_FON_SOLIDARIDAD_PEN',33,'Beneficiario del fondo de solidaridad pensional','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_CONSEJAL_AMP_POLI_SALUD',34,'Concejal o edil de Junta Administradora Local del Distrito Capital de Bogotá amparado por póliza de salud','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_CONSEJAL_MUN_NO_AMP_POLI_SALUD',35,'Concejal municipal o distrital no amparado con póliza de salud','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_CONSEJAL_NO_AMP_POLI_SALUD_FSP',36,'Concejal municipal o distrital o edil de Junta Administradora Local no amparado con póliza de salud beneficiario del Fondo de Solidaridad Pensional','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_BENEFICIARIO_UPC',40,'Beneficiario UPC adicional','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_INDEPENDIENTE_SOLO_SALUD',42,'Cotizante independiente pago solo salud.','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_PAGO_POR_TERCERO',43,'Cotizante a pensiones con pago por tercero.','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_DEPENDIENTE_EMER_MAS_MES',44,'Cotizante dependiente de empleo de emergencia con duración mayor o igual a un mes','TRABAJADOR_DEPENDIENTE','TRABAJADOR_POR_DIAS'),
		('TIPO_COTIZANTE_DEPENDIENTE_EMER_MENOS_MES',45,'Cotizante dependiente de empleo de emergencia con duración menor a un mes','TRABAJADOR_DEPENDIENTE','TRABAJADOR_POR_DIAS'),
		('TIPO_COTIZANTE_DEPENDIENTE_ENT_BENEFI',47,'Trabajador dependiente de entidad beneficiaria del sistema general de participaciones - Aportes patronales','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL',51,'Trabajador de tiempo parcial ','TRABAJADOR_DEPENDIENTE','TRABAJADOR_POR_DIAS'),
		('TIPO_COTIZANTE_BENEFICIARIO_MP_CESANTE',52,'Beneficiario del Mecanismo de Protección al Cesante','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_AFILIADO_PARTICIPE',53,'Afiliado Participe','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_PREPENSIONADO_ENTI_LIQ',54,'Prepensionado de entidad en liquidación','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_AFILIADO_PARTI_DEPENDIENTE',55,'Afiliado participe dependiente','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_PREPENSIONADO_AVS',56,'Prepensionado con aporte voluntario a Salud','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_IND_VOLUNTARIO_ARL',57,'Independiente voluntario a Riesgos Laborales','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_ESTUDIANTE_PRACTICA_LABORAL_SECTOR_PUBLICO',58,'Estudiantes de prácticas laborales en el sector público','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_IND_SERVICIOS_SUPERIOR_1_MES',59,'Independiente con contrato de prestación de servicios superior a 1 mes','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_EDIL_JAL ',60,'Edil Junta Administradora Local no beneficiario del Fondo de Solidaridad Pensional','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_PROGRAMA_REINCORPORACION ',61,'Beneficiario programa de reincorporación','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_PERSONAL_DEL_MAGISTERIO ',62,'Personal del Magisterio','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_BENEFICIARIO_PRESTACIO_HUMANITARIA',63,'Beneficiario de prestación humanitaria','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_TRABAJADOR_PENITENCIARIO',64,'Trabajador penitenciario','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_DEPENDIENTE_VINC_PISO_PROCT_SOCIAL',65,'Dependiente vinculado al piso de protección social','TRABAJADOR_DEPENDIENTE','REGULAR'),
		('TIPO_COTIZANTE_INDEPEND_VINC_PISO_PROCT_SOCIAL',66,'Independiente vinculado al piso de protección social','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_VOLUNTARIO_PRIM_RESPUESTA_APORTE_PROCT_SOCIAL',67,'Voluntario en primera respuesta aporte solo riesgos laborales','TRABAJADOR_INDEPENDIENTE','INDEPENDIENTE_REGULAR'),
		('TIPO_COTIZANTE_DEPENDIENTE_VETERANO_FUERZA_PUBLICA',68,'Dependiente Veterano de la Fuerza Publica','TRABAJADOR_DEPENDIENTE','VETERANO_FUERZA_PUBLICA'),
		('TIPO_COTIZANTE_CONTRIBUYENTE_SOLIDARIO',69,'Contribuyente solidario','TRABAJADOR_DEPENDIENTE','REGULAR')) as t
		(tipo, tipoCot, descri, tipotra, otro))
		merge staging.StagingTiposCotizantes as d
		using tblTiposCot as o on d.tipoCot = o.tipoCot
		when matched then update set d.tipo = o.tipo, d.descri = o.descri, d.tipotra = o.tipotra, d.otro = o.otro
		when not matched by target then insert (tipo, tipoCot, descri, tipotra, otro)
		values (o.tipo, o.tipoCot, o.descri, o.tipotra, o.otro)
		when not matched by source then delete;
	end