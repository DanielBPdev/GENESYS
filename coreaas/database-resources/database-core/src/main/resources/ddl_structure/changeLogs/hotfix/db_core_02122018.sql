--liquibase formatted sql

--changeset abaquero:01
--comment: Adición de parametrización de lectura para archivos de aporte manual de pensionados

Insert into FileDefinitionLoadType values (12121,'Carga de archivo de solicitud de pago manual (Pensionados)','Plantilla solicitud de pago de aportes manuales (Pensionados)');
Insert into LineLoadCatalog values (12121,'com.asopagos.aportes.load.PagoManualAportePensionadoPersistLine','Información de la solicitud de pago manual de aportes pensionados', 'Detalle de Pensionados', NULL, 1, '|', NULL);
Insert into FileDefinitionLoad values (12121,'.','Solicitud de pago de aportes manuales (Pensionados)', NULL, NULL, 0, NULL, NULL, 1, 12121);
Insert into LineDefinitionLoad values (12121,0, NULL, 1, NULL, 1, 12121, 12121, NULL);
Insert into FieldDefinitionLoad values (100046, 1, null, 0, 'Tipo de registro.', 0, 0, 0, 2110176, 12121);
Insert into FieldDefinitionLoad values (100047, 8, null, 1, 'Secuencia.', 0, 0, 0, 2110177, 12121);
Insert into FieldDefinitionLoad values (100048, 10, null, 8, 'Tipo identificación del pensionado.', 0, 0, 1, 2110178, 12121);
Insert into FieldDefinitionLoad values (100049, 26, null, 10, 'No. de identificación del pensionado.', 0, 0, 1, 2110179, 12121);
Insert into FieldDefinitionLoad values (100050, 46, null, 26, 'Primer apellido del pensionado.', 0, 0, 1, 2110180, 12121);
Insert into FieldDefinitionLoad values (100051, 76, null, 46, 'Segundo apellido del pensionado.', 0, 0, 0, 2110181, 12121);
Insert into FieldDefinitionLoad values (100052, 96, null, 76, 'Primer nombre del pensionado.', 0, 0, 1, 2110182, 12121);
Insert into FieldDefinitionLoad values (100053, 126, null, 96, 'Segundo nombre del pensionado.', 0, 0, 0, 2110183, 12121);
Insert into FieldDefinitionLoad values (100054, 128, null, 126, 'Código del departamento de la ubicación de residencia', 0, 0, 0, 2110184, 12121);
Insert into FieldDefinitionLoad values (100055, 131, null, 128, 'Código del municipio de la ubicación de residencia', 0, 0, 0, 2110185, 12121);
Insert into FieldDefinitionLoad values (100056, 138, null, 131, 'Tarifa.', 0, 0, 0, 2110186, 12121);
Insert into FieldDefinitionLoad values (100057, 147, null, 138, 'Valor aporte', 0, 0, 0, 2110187, 12121);
Insert into FieldDefinitionLoad values (100058, 156, null, 147, 'Valor de la mesada pensional ', 0, 0, 0, 2110188, 12121);
Insert into FieldDefinitionLoad values (100059, 159, null, 156, 'Número de días cotizados', 0, 0, 0, 2110189, 12121);
Insert into FieldDefinitionLoad values (100060, 160, null, 159, 'ING: Ingreso.', 0, 0, 0, 2110190, 12121);
Insert into FieldDefinitionLoad values (100061, 161, null, 160, 'RET: Retiro.', 0, 0, 0, 2110191, 12121);
Insert into FieldDefinitionLoad values (100062, 162, null, 161, 'VSP: Variación permanente de la mesada pensional.', 0, 0, 0, 2110192, 12121);
Insert into FieldDefinitionLoad values (100063, 163, null, 162, 'SUS: Suspensión.', 0, 0, 0, 2110193, 12121);
Insert into FieldDefinitionLoad values (100064, 173, 'yyyy-MM-dd', 163, 'Fecha de ingreso formato (AAAA-MM-DD).', 0, 0, 0, 2110194, 12121);
Insert into FieldDefinitionLoad values (100065, 183, 'yyyy-MM-dd', 173, 'Fecha de retiro. formato (AAAA-MM-DD).', 0, 0, 0, 2110195, 12121);
Insert into FieldDefinitionLoad values (100066, 193, 'yyyy-MM-dd', 183, 'Fecha inicio  VSP formato (AAAA-MM-DD).', 0, 0, 0, 2110196, 12121);
Insert into FieldDefinitionLoad values (100067, 203, 'yyyy-MM-dd', 193, 'Fecha inicio de la Suspensión formato (AAAA-MM-DD).', 0, 0, 0, 2110240, 12121);
Insert into FieldDefinitionLoad values (100068, 213, 'yyyy-MM-dd', 203, 'Fecha Fin de la Suspensión formato (AAAA-MM-DD).', 0, 0, 0, 2110241, 12121);