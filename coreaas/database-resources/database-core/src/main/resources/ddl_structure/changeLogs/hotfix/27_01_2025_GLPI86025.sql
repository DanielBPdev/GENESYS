--BEGIN TRANSACTION  
-- CAR_EXP_INP INDEPENDIENTE
IF NOT EXISTS (SELECT 1 FROM PlantillaComunicado param WHERE  pcoEtiqueta IN ('CAR_EXP_INP'))
    BEGIN
        INSERT [PlantillaComunicado] ([pcoAsunto], [pcoCuerpo], [pcoEncabezado], [pcoIdentificadorImagenPie], [pcoMensaje], [pcoNombre], [pcoPie], [pcoEtiqueta]) 
        VALUES ( N'Carta a Independiente expulsado',
        N'<p>&nbsp;</p> <p>Se&ntilde;or (a)</p> <p>${direccion}</p> <p>${telefono}</p> <p>${ciudad}</p> <p>&nbsp;</p> <p><strong>Asunto:&nbsp;</strong>Informaci&oacute;n sobre expulsi&oacute;n</p> <p>&nbsp;</p> <p>Nos permitimos informarle que el Consejo Directivo de la Caja de Compensaci&oacute;n Familiar de Caldas - Confa, seg&uacute;n consta en el acta <strong>[N&uacute;mero del acta del Consejo Directivo, cuando se tomo la decisi&oacute;n de expulsar]</strong> de <strong>[Fecha del acta de Consejo&nbsp;Directivo, cuando se tomo la decisi&oacute;n de expulsar]</strong>, tom&oacute; la decisi&oacute;n de expulsar&nbsp;la empresa que usted representa como afiliada a la Caja, debido a la reincidencia&nbsp;en mora en el pago de los aportes al Subsidio Familiar.</p> <p>Lo anterior se produjo luego de haber agotado el procedimiento definido&nbsp;en el Reglamento para la suspensi&oacute;n y expulsi&oacute;n de empresas y dem&aacute;s afiliados a la Caja de Compensaci&oacute;n Familiar aprobado por el Consejo Directivo seg&uacute;n acta 559 de enero 14 de 2014,&nbsp;concordante&nbsp;con lo establecido en el art&iacute;culo 21, numeral 19, par&aacute;grafo 4 de la Ley 789 de&nbsp;2002.</p> <p>Le informamos que se dar&aacute; conocimiento de lo acontecido a la Superintendencia&nbsp;del Subsidio Familiar, SENA, ICBF, DIAN, Ministerio del Trabajo y a la&nbsp;Unidad Administrativa Especial de Gesti&oacute;n Pensional y&nbsp;Parafiscal de la Protecci&oacute;n Social &shy;UGPP, para que adopten las medidas del caso.</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>Cualquier inquietud debe ser canalizada a trav&eacute;s del&nbsp;&aacute;rea de Aportes en el tel&eacute;fono 8783111 ext 1369 - 1370&nbsp;o en su defecto comunicaci&oacute;n escrita firmada&nbsp;por el representante legal de la empresa, adjuntando los soportes id&oacute;neos.</p> <p>&nbsp;</p> <p>Atentamente,</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>JUAN EDUARDO ZULUAGA PERNA</p> <p>Director</p>',
        N'Encabezado', NULL, N'Mensaje<br /> ${fechaDelSistema}<br /> ${nombreYApellidosRepresentanteLegal}<br /> ${razonSocial/Nombre}<br /> ${direccion}<br /> ${telefono}<br /> ${ciudad}<br /> ${nombreCcf}<br /> ${logoDeLaCcf}<br /> ${departamentoCcf}<br /> ${ciudadCcf}<br /> ${direccionCcf}<br /> ${telefonoCcf}<br /> ${webCcf}<br /> ${logoSuperservicios}<br /> ${responsableCcf}<br /> ${cargoResponsableCcf}', N'Carta a empresa expulsada', N'Pie', N'CAR_EXP_INP')
    END

  IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'))
    BEGIN  
        INSERT INTO VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
        VALUES

        ('${responsableCarteraCcf}','Responsable del proceso de cartera','Responsable del proceso de cartera',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'RESPONSABLE_CARTERA_CCF','CONSTANTE',''),
        ('${cargoResponsableCarteraCcf}','Cargo responsable del proceso de cartera','Cargo responsable del proceso de cartera',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'CARGO_RESPONSABLE_CARTERA_CCF','CONSTANTE',''),
        ('${fechaDelSistema}','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','Fecha del sistema',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'','FECHA_LARGA','0'),
        ('${direccion}','Dirección de la empresa relacionada al representante legal','Dirección',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'','VARIABLE','0'),
        ('${telefono}','Teléfono fijo o Celular capturado en Información de representante legal','Teléfono',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'','VARIABLE','0'),
        ('${ciudad}','Ciudad de empresa','Ciudad',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'','LUGAR_MAYUS','0'),
        ('${nombreCcf}','Nombre de la caja de Compensación','Nombre CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'NOMBRE_CCF','CONSTANTE',''),
        ('${logoDeLaCcf}','Logotipo de la Caja de Compensación','Logo de la CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'LOGO_DE_LA_CCF','CONSTANTE',''),
        ('${departamentoCcf}','Departamento de la caja de Compensación','Departamento CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'DEPARTAMENTO_CCF','CONSTANTE',''),
        ('${ciudadCcf}','Ciudad de la caja de Compensación','Ciudad CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'CIUDAD_CCF','CONSTANTE',''),
        ('${direccionCcf}','Dirección de la caja de Compensación','Dirección CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'DIRECCION_CCF','CONSTANTE',''),
        ('${telefonoCcf}','Teléfono de la caja de Compensación','Teléfono CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'TELEFONO_CCF','CONSTANTE',''),
        ('${webCcf}','Web de la caja de Compensación','Web CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'WEB_CCF','CONSTANTE',''),
        ('${logoSupersubsidio}','Logo de la Superintendencia del Subsidio Familiar','Logo Supersubsidio',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'LOGO_SUPERSUBSIDIO','CONSTANTE',''),
        ('${responsableCcf}','responsable caja de compensación','Responsable CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'RESPONSABLE_CCF','CONSTANTE',''),
        ('${cargoResponsableCcf}','Cargo del responsable del envío del comunicado en la caja','Cargo responsable CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'CARGO_RESPONSABLE_CCF','CONSTANTE',''),
        ('${correoCcf}','Correo electronico de la caja de Compensación','Correo CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'CORREO_CCF','CONSTANTE','0'),
        ('${tipoIdCcf}','Tipo documento de la Caja de Compensación','Tipo ID CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'TIPO_ID_CCF','CONSTANTE',''),
        ('${numeroIdCcf}','Número de documento de la Caja de Compensación','Número ID CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'NUMERO_ID_CCF','CONSTANTE',''),
        ('${consecutivoLiquidacion}','Número consecutivo de la liquidación','Consecutivo Liquidación ',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'','VARIABLE',''),
        ('${firmaResponsableCcf}','Imagen de la firma del responsable del envío del comunicado en la caja','Firma Responsable CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'FIRMA_RESPONSABLE_CCF','CONSTANTE',''),
        ('${firmaResponsableProcesoCartera}','Firma del responsable de proceso de cartera','Firma del responsable de proceso de cartera',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'FIRMA_RESPONSABLE_CARTERA_CCF','CONSTANTE',''),
        ('${numeroIdentificacionIndependiente}','Número de identificación del Independiente','Número de identificación del Independiente',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'','VARIABLE',''),
        ('${periodos}','Periodos para los cuales esta en mora','Periodos',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'','REPORTE_VARIABLE',''),
        ('${tipoIdentificacionIndependiente}','Tipo de identificación del Independiente','Tipo de identificación del Independiente',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'','VARIABLE',''),
        ('${valorDeudaPresunta}','Valor deuda presunta','Valor deuda presunta',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'','VARIABLE',''),
        ('${membretePieDePaginaDeLaCcf}','Membrete pie de pagina de la Caja de Compensación','Membrete pie de pagina de la CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'MEMBRETE_PIE_DE_PAGINA_DE_LA_CCF','CONSTANTE',''),
        ('${membreteEncabezadoDeLaCcf}','Membrete encabezado de la Caja de Compensación','Membrete encabezado de la CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'MEMBRETE_ENCABEZADO_DE_LA_CCF','CONSTANTE',''),
        ('${cargosecretariageneral}','Corresponde al cargo que tiene la secretaria general en cartera','Cargo Secretaria General',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'CARGO_SECRETARIA_GENERAL','CONSTANTE','0'),
        ('${firmasecretariageneral}','Firma secretaria general','Firma Secretaria General',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'FIRMA_SECRETARIA_GENERAL','CONSTANTE','0'),
        ('${secretariageneral}','Secretaria General','Secretaria General',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'),'SECRETARIA_GENERAL','CONSTANTE','0');

END
IF NOT EXISTS (SELECT 1 FROM DestinatarioComunicado param WHERE  dcoEtiquetaPlantilla ='CAR_EXP_INP')
    BEGIN
    INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_COBRO_ELECTRONICO','CAR_EXP_INP');
END
IF NOT EXISTS (SELECT 1 FROM ModuloPlantillaComunicado param WHERE  mpcPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'))
    BEGIN
  INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_INP'), 0, 1, null);
END
   
   -- CAR_EXP_PEN PENSIONADO
IF NOT EXISTS (SELECT 1 FROM PlantillaComunicado param WHERE  pcoEtiqueta IN ('CAR_EXP_PEN'))
    BEGIN
        INSERT [PlantillaComunicado] ( [pcoAsunto], [pcoCuerpo], [pcoEncabezado], [pcoIdentificadorImagenPie], [pcoMensaje], [pcoNombre], [pcoPie], [pcoEtiqueta]) 
        VALUES ( N'Carta a Independiente expulsado',
        N'<p>&nbsp;</p> <p>Se&ntilde;or (a)</p> <p>${direccion}</p> <p>${telefono}</p> <p>${ciudad}</p> <p>&nbsp;</p> <p><strong>Asunto:&nbsp;</strong>Informaci&oacute;n sobre expulsi&oacute;n</p> <p>&nbsp;</p> <p>Nos permitimos informarle que el Consejo Directivo de la Caja de Compensaci&oacute;n Familiar de Caldas - Confa, seg&uacute;n consta en el acta <strong>[N&uacute;mero del acta del Consejo Directivo, cuando se tomo la decisi&oacute;n de expulsar]</strong> de <strong>[Fecha del acta de Consejo&nbsp;Directivo, cuando se tomo la decisi&oacute;n de expulsar]</strong>, tom&oacute; la decisi&oacute;n de expulsar&nbsp;la empresa que usted representa como afiliada a la Caja, debido a la reincidencia&nbsp;en mora en el pago de los aportes al Subsidio Familiar.</p> <p>Lo anterior se produjo luego de haber agotado el procedimiento definido&nbsp;en el Reglamento para la suspensi&oacute;n y expulsi&oacute;n de empresas y dem&aacute;s afiliados a la Caja de Compensaci&oacute;n Familiar aprobado por el Consejo Directivo seg&uacute;n acta 559 de enero 14 de 2014,&nbsp;concordante&nbsp;con lo establecido en el art&iacute;culo 21, numeral 19, par&aacute;grafo 4 de la Ley 789 de&nbsp;2002.</p> <p>Le informamos que se dar&aacute; conocimiento de lo acontecido a la Superintendencia&nbsp;del Subsidio Familiar, SENA, ICBF, DIAN, Ministerio del Trabajo y a la&nbsp;Unidad Administrativa Especial de Gesti&oacute;n Pensional y&nbsp;Parafiscal de la Protecci&oacute;n Social &shy;UGPP, para que adopten las medidas del caso.</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>Cualquier inquietud debe ser canalizada a trav&eacute;s del&nbsp;&aacute;rea de Aportes en el tel&eacute;fono 8783111 ext 1369 - 1370&nbsp;o en su defecto comunicaci&oacute;n escrita firmada&nbsp;por el representante legal de la empresa, adjuntando los soportes id&oacute;neos.</p> <p>&nbsp;</p> <p>Atentamente,</p> <p>&nbsp;</p> <p>&nbsp;</p> <p>JUAN EDUARDO ZULUAGA PERNA</p> <p>Director</p>',
        N'Encabezado', NULL, N'Mensaje<br /> ${fechaDelSistema}<br /> ${nombreYApellidosRepresentanteLegal}<br /> ${razonSocial/Nombre}<br /> ${direccion}<br /> ${telefono}<br /> ${ciudad}<br /> ${nombreCcf}<br /> ${logoDeLaCcf}<br /> ${departamentoCcf}<br /> ${ciudadCcf}<br /> ${direccionCcf}<br /> ${telefonoCcf}<br /> ${webCcf}<br /> ${logoSuperservicios}<br /> ${responsableCcf}<br /> ${cargoResponsableCcf}', N'Carta a empresa expulsada', N'Pie', N'CAR_EXP_PEN')
    END

  IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'))
    BEGIN  
        INSERT INTO VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
        VALUES

        ('${responsableCarteraCcf}','Responsable del proceso de cartera','Responsable del proceso de cartera',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'RESPONSABLE_CARTERA_CCF','CONSTANTE',''),
        ('${cargoResponsableCarteraCcf}','Cargo responsable del proceso de cartera','Cargo responsable del proceso de cartera',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'CARGO_RESPONSABLE_CARTERA_CCF','CONSTANTE',''),
        ('${fechaDelSistema}','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','Fecha del sistema',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'','FECHA_LARGA','0'),
        ('${direccion}','Dirección de la empresa relacionada al representante legal','Dirección',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'','VARIABLE','0'),
        ('${telefono}','Teléfono fijo o Celular capturado en Información de representante legal','Teléfono',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'','VARIABLE','0'),
        ('${ciudad}','Ciudad de empresa','Ciudad',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'','LUGAR_MAYUS','0'),
        ('${nombreCcf}','Nombre de la caja de Compensación','Nombre CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'NOMBRE_CCF','CONSTANTE',''),
        ('${logoDeLaCcf}','Logotipo de la Caja de Compensación','Logo de la CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'LOGO_DE_LA_CCF','CONSTANTE',''),
        ('${departamentoCcf}','Departamento de la caja de Compensación','Departamento CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'DEPARTAMENTO_CCF','CONSTANTE',''),
        ('${ciudadCcf}','Ciudad de la caja de Compensación','Ciudad CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'CIUDAD_CCF','CONSTANTE',''),
        ('${direccionCcf}','Dirección de la caja de Compensación','Dirección CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'DIRECCION_CCF','CONSTANTE',''),
        ('${telefonoCcf}','Teléfono de la caja de Compensación','Teléfono CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'TELEFONO_CCF','CONSTANTE',''),
        ('${webCcf}','Web de la caja de Compensación','Web CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'WEB_CCF','CONSTANTE',''),
        ('${logoSupersubsidio}','Logo de la Superintendencia del Subsidio Familiar','Logo Supersubsidio',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'LOGO_SUPERSUBSIDIO','CONSTANTE',''),
        ('${responsableCcf}','responsable caja de compensación','Responsable CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'RESPONSABLE_CCF','CONSTANTE',''),
        ('${cargoResponsableCcf}','Cargo del responsable del envío del comunicado en la caja','Cargo responsable CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'CARGO_RESPONSABLE_CCF','CONSTANTE',''),
        ('${correoCcf}','Correo electronico de la caja de Compensación','Correo CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'CORREO_CCF','CONSTANTE','0'),
        ('${tipoIdCcf}','Tipo documento de la Caja de Compensación','Tipo ID CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'TIPO_ID_CCF','CONSTANTE',''),
        ('${numeroIdCcf}','Número de documento de la Caja de Compensación','Número ID CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'NUMERO_ID_CCF','CONSTANTE',''),
        ('${consecutivoLiquidacion}','Número consecutivo de la liquidación','Consecutivo Liquidación ',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'','VARIABLE',''),
        ('${firmaResponsableCcf}','Imagen de la firma del responsable del envío del comunicado en la caja','Firma Responsable CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'FIRMA_RESPONSABLE_CCF','CONSTANTE',''),
        ('${firmaResponsableProcesoCartera}','Firma del responsable de proceso de cartera','Firma del responsable de proceso de cartera',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'FIRMA_RESPONSABLE_CARTERA_CCF','CONSTANTE',''),
        ('${numeroIdentificacionPensionado}','Número de identificación del Pensionado','Número de identificación del Pensionado',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'','VARIABLE',''),
        ('${periodos}','Periodos para los cuales esta en mora','Periodos',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'','REPORTE_VARIABLE',''),
        ('${tipoIdentificacionPensionado}','Tipo de identificación del Pensionado','Tipo de identificación del Pensionado',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'','VARIABLE',''),
        ('${valorDeudaPresunta}','Valor deuda presunta','Valor deuda presunta',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'','VARIABLE',''),
        ('${membretePieDePaginaDeLaCcf}','Membrete pie de pagina de la Caja de Compensación','Membrete pie de pagina de la CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'MEMBRETE_PIE_DE_PAGINA_DE_LA_CCF','CONSTANTE',''),
        ('${membreteEncabezadoDeLaCcf}','Membrete encabezado de la Caja de Compensación','Membrete encabezado de la CCF',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'MEMBRETE_ENCABEZADO_DE_LA_CCF','CONSTANTE',''),
        ('${cargosecretariageneral}','Corresponde al cargo que tiene la secretaria general en cartera','Cargo Secretaria General',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'CARGO_SECRETARIA_GENERAL','CONSTANTE','0'),
        ('${firmasecretariageneral}','Firma secretaria general','Firma Secretaria General',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'FIRMA_SECRETARIA_GENERAL','CONSTANTE','0'),
        ('${secretariageneral}','Secretaria General','Secretaria General',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'),'SECRETARIA_GENERAL','CONSTANTE','0');

END
    IF NOT EXISTS (SELECT 1 FROM DestinatarioComunicado  WHERE  dcoEtiquetaPlantilla ='CAR_EXP_PEN')
        BEGIN
        INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_COBRO_ELECTRONICO','CAR_EXP_PEN');
    END
    IF NOT EXISTS (SELECT 1 FROM ModuloPlantillaComunicado  WHERE  mpcPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'))
        BEGIN
    INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CAR_EXP_PEN'), 0, 1, null);
    END
   
--    NTF_NO_REC_APO_IND
	IF NOT EXISTS (SELECT 1 FROM PlantillaComunicado param WHERE  pcoEtiqueta IN ('NTF_NO_REC_APO_IND'))
	BEGIN 
	   INSERT [PlantillaComunicado] ( [pcoAsunto], [pcoCuerpo], [pcoEncabezado], [pcoIdentificadorImagenPie], [pcoMensaje], [pcoNombre], [pcoPie], [pcoEtiqueta]) 
	   SELECT pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, 'NTF_NO_REC_APO_IND' FROM PlantillaComunicado WHERE pcoEtiqueta='NTF_NO_REC_APO'
	END

	IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_IND'))
    BEGIN 
		 INSERT INTO VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante)
		 SELECT  REPLACE(vcoClave, 'empleador', 'Independiente') as vcoClave, REPLACE(vcoDescripcion, 'empleador', 'Independiente') as vcoDescripcion ,REPLACE(vcoNombre, 'empleador', 'Independiente') as   vcoNombre,
         (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_IND'),vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante FROM VariableComunicado 
		 WHERE vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO' AND vcoClave not LIKE '%RepresentanteLegal%')
	 END
	 IF NOT EXISTS (SELECT 1 FROM DestinatarioComunicado  WHERE  dcoEtiquetaPlantilla ='NTF_NO_REC_APO_IND')
    BEGIN
		 INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_COBRO_ELECTRONICO','NTF_NO_REC_APO_IND');
	END
	IF NOT EXISTS (SELECT 1 FROM ModuloPlantillaComunicado  WHERE  mpcPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_IND'))
		BEGIN
		 INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_IND'), 0, 1, null);
	END
	
    --    NTF_NO_REC_APO_PEN
	IF NOT EXISTS (SELECT 1 FROM PlantillaComunicado param WHERE  pcoEtiqueta IN ('NTF_NO_REC_APO_PEN'))
	BEGIN 
	   INSERT [PlantillaComunicado] ( [pcoAsunto], [pcoCuerpo], [pcoEncabezado], [pcoIdentificadorImagenPie], [pcoMensaje], [pcoNombre], [pcoPie], [pcoEtiqueta]) 
	   SELECT pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, 'NTF_NO_REC_APO_PEN' FROM PlantillaComunicado WHERE pcoEtiqueta='NTF_NO_REC_APO'
	END

	IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PEN'))
    BEGIN 
		 INSERT INTO VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante)
         SELECT  REPLACE(vcoClave, 'empleador', 'Pensionado') as vcoClave, REPLACE(vcoDescripcion, 'empleador', 'Pensionado') as vcoDescripcion ,REPLACE(vcoNombre, 'empleador', 'Pensionado') as   vcoNombre,
		(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PEN'),vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante FROM VariableComunicado 
		 WHERE vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO' AND vcoClave not LIKE '%RepresentanteLegal%')
	 END
	 IF NOT EXISTS (SELECT 1 FROM DestinatarioComunicado  WHERE  dcoEtiquetaPlantilla ='NTF_NO_REC_APO_PEN')
    BEGIN
		 INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_COBRO_ELECTRONICO','NTF_NO_REC_APO_PEN');
	END
	IF NOT EXISTS (SELECT 1 FROM ModuloPlantillaComunicado  WHERE  mpcPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PEN'))
		BEGIN
		 INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PEN'), 0, 1, null);
	END 
        ----LIQ_APO_MOR_IND
	IF NOT EXISTS (SELECT 1 FROM PlantillaComunicado param WHERE  pcoEtiqueta IN ('LIQ_APO_MOR_IND'))
	BEGIN 
	   INSERT [PlantillaComunicado] ( [pcoAsunto], [pcoCuerpo], [pcoEncabezado], [pcoIdentificadorImagenPie], [pcoMensaje], [pcoNombre], [pcoPie], [pcoEtiqueta]) 
	   SELECT pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, 'LIQ_APO_MOR_IND' FROM PlantillaComunicado WHERE pcoEtiqueta='LIQ_APO_MOR'
	END 
	IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'LIQ_APO_MOR_IND'))
    BEGIN 
		 INSERT INTO VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante)
		 SELECT REPLACE(vcoClave, 'empleador', 'Independiente') as vcoClave, REPLACE(vcoDescripcion, 'empleador', 'Independiente') as vcoDescripcion ,REPLACE(vcoNombre, 'empleador', 'Independiente') as   vcoNombre,(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'LIQ_APO_MOR_IND'),vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante FROM VariableComunicado 
		 WHERE vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'LIQ_APO_MOR' AND vcoClave not LIKE '%RepresentanteLegal%' )
	 END
	 IF NOT EXISTS (SELECT 1 FROM DestinatarioComunicado  WHERE  dcoEtiquetaPlantilla ='LIQ_APO_MOR_IND')
    BEGIN
		 INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_COBRO_ELECTRONICO','LIQ_APO_MOR_IND');
	END
	IF NOT EXISTS (SELECT 1 FROM ModuloPlantillaComunicado  WHERE  mpcPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'LIQ_APO_MOR_IND'))
		BEGIN
		 INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'LIQ_APO_MOR_IND'), 0, 1, null);
	END

        ----LIQ_APO_MOR_PEN
	IF NOT EXISTS (SELECT 1 FROM PlantillaComunicado param WHERE  pcoEtiqueta IN ('LIQ_APO_MOR_PEN'))
	BEGIN 
	   INSERT [PlantillaComunicado] ( [pcoAsunto], [pcoCuerpo], [pcoEncabezado], [pcoIdentificadorImagenPie], [pcoMensaje], [pcoNombre], [pcoPie], [pcoEtiqueta]) 
	   SELECT pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, 'LIQ_APO_MOR_PEN' FROM PlantillaComunicado WHERE pcoEtiqueta='LIQ_APO_MOR'
	END

	IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'LIQ_APO_MOR_PEN'))
    BEGIN 
		 INSERT INTO VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante)
		 SELECT REPLACE(vcoClave, 'empleador', 'Pensionado') as vcoClave, REPLACE(vcoDescripcion, 'empleador', 'Pensionado') as vcoDescripcion ,REPLACE(vcoNombre, 'empleador', 'Pensionado') as   vcoNombre,(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'LIQ_APO_MOR_PEN'),vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante FROM VariableComunicado 
		 WHERE vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'LIQ_APO_MOR' AND vcoClave not LIKE '%RepresentanteLegal%' )
	 END
	 IF NOT EXISTS (SELECT 1 FROM DestinatarioComunicado  WHERE  dcoEtiquetaPlantilla ='LIQ_APO_MOR_PEN')
    BEGIN
		 INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_COBRO_ELECTRONICO','LIQ_APO_MOR_PEN');
	END
	IF NOT EXISTS (SELECT 1 FROM ModuloPlantillaComunicado  WHERE  mpcPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'LIQ_APO_MOR_PEN'))
		BEGIN
		 INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'LIQ_APO_MOR_PEN'), 0, 1, null);
	END
    
        ----CIT_NTF_PER_IND
	IF NOT EXISTS (SELECT 1 FROM PlantillaComunicado param WHERE  pcoEtiqueta IN ('CIT_NTF_PER_IND'))
	BEGIN 
	   INSERT [PlantillaComunicado] ( [pcoAsunto], [pcoCuerpo], [pcoEncabezado], [pcoIdentificadorImagenPie], [pcoMensaje], [pcoNombre], [pcoPie], [pcoEtiqueta]) 
	   SELECT pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, 'CIT_NTF_PER_IND' FROM PlantillaComunicado WHERE pcoEtiqueta='CIT_NTF_PER'
	END

	IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CIT_NTF_PER_IND'))
    BEGIN 
		 INSERT INTO VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante)
		 SELECT REPLACE(vcoClave, 'empleador', 'Independiente') as vcoClave, REPLACE(vcoDescripcion, 'empleador', 'Independiente') as vcoDescripcion ,REPLACE(vcoNombre, 'empleador', 'Independiente') as   vcoNombre,(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CIT_NTF_PER_IND'),vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante FROM VariableComunicado 
		 WHERE vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CIT_NTF_PER' AND vcoClave not LIKE '%RepresentanteLegal%' )
	 END
	 IF NOT EXISTS (SELECT 1 FROM DestinatarioComunicado  WHERE  dcoEtiquetaPlantilla ='CIT_NTF_PER_IND')
    BEGIN
		 INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_COBRO_ELECTRONICO','CIT_NTF_PER_IND');
	END
	IF NOT EXISTS (SELECT 1 FROM ModuloPlantillaComunicado  WHERE  mpcPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CIT_NTF_PER_IND'))
		BEGIN
		 INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CIT_NTF_PER_IND'), 0, 1, null);
	END

        ----CIT_NTF_PER_PEN
	IF NOT EXISTS (SELECT 1 FROM PlantillaComunicado param WHERE  pcoEtiqueta IN ('CIT_NTF_PER_PEN'))
	BEGIN 
	   INSERT [PlantillaComunicado] ( [pcoAsunto], [pcoCuerpo], [pcoEncabezado], [pcoIdentificadorImagenPie], [pcoMensaje], [pcoNombre], [pcoPie], [pcoEtiqueta]) 
	   SELECT pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, 'CIT_NTF_PER_PEN' FROM PlantillaComunicado WHERE pcoEtiqueta='CIT_NTF_PER'
	END

	IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CIT_NTF_PER_PEN'))
    BEGIN 
		 INSERT INTO VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante)
		 SELECT REPLACE(vcoClave, 'empleador', 'Pensionado') as vcoClave, REPLACE(vcoDescripcion, 'empleador', 'Pensionado') as vcoDescripcion ,REPLACE(vcoNombre, 'empleador', 'Pensionado') as   vcoNombre,(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CIT_NTF_PER_PEN'),vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante FROM VariableComunicado 
		 WHERE vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CIT_NTF_PER' AND vcoClave not LIKE '%RepresentanteLegal%' )
	 END
	 IF NOT EXISTS (SELECT 1 FROM DestinatarioComunicado  WHERE  dcoEtiquetaPlantilla ='CIT_NTF_PER_PEN')
    BEGIN
		 INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_COBRO_ELECTRONICO','CIT_NTF_PER_PEN');
	END
	IF NOT EXISTS (SELECT 1 FROM ModuloPlantillaComunicado  WHERE  mpcPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CIT_NTF_PER_PEN'))
		BEGIN
		 INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CIT_NTF_PER_PEN'), 0, 1, null);
	END


    ----SEG_AVI_COB_PRS_INDEP
	IF NOT EXISTS (SELECT 1 FROM PlantillaComunicado param WHERE  pcoEtiqueta IN ('SEG_AVI_COB_PRS_INDEP'))
	BEGIN 
	   INSERT [PlantillaComunicado] ( [pcoAsunto], [pcoCuerpo], [pcoEncabezado], [pcoIdentificadorImagenPie], [pcoMensaje], [pcoNombre], [pcoPie], [pcoEtiqueta]) 
	   SELECT pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, 'SEG_AVI_COB_PRS_INDEP' FROM PlantillaComunicado WHERE pcoEtiqueta='SEG_AVI_COB_PRS'
	END

	IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SEG_AVI_COB_PRS_INDEP'))
    BEGIN 
		 INSERT INTO VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante)
		 SELECT REPLACE(vcoClave, 'empleador', 'Independiente') as vcoClave, REPLACE(vcoDescripcion, 'empleador', 'Independiente') as vcoDescripcion ,REPLACE(vcoNombre, 'empleador', 'Independiente') as   vcoNombre,(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SEG_AVI_COB_PRS_INDEP'),vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante FROM VariableComunicado 
		 WHERE vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SEG_AVI_COB_PRS' AND vcoClave not LIKE '%RepresentanteLegal%' )
	 END
	 IF NOT EXISTS (SELECT 1 FROM DestinatarioComunicado  WHERE  dcoEtiquetaPlantilla ='SEG_AVI_COB_PRS_INDEP')
    BEGIN
		 INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_COBRO_ELECTRONICO','SEG_AVI_COB_PRS_INDEP');
	END
	IF NOT EXISTS (SELECT 1 FROM ModuloPlantillaComunicado  WHERE  mpcPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SEG_AVI_COB_PRS_INDEP'))
		BEGIN
		 INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SEG_AVI_COB_PRS_INDEP'), 0, 1, null);
	END
    --SEG_AVI_COB_PRS_PENSIONADO
    	IF NOT EXISTS (SELECT 1 FROM PlantillaComunicado param WHERE  pcoEtiqueta IN ('SEG_AVI_COB_PRS_PENSIONADO'))
	BEGIN 
	   INSERT [PlantillaComunicado] ( [pcoAsunto], [pcoCuerpo], [pcoEncabezado], [pcoIdentificadorImagenPie], [pcoMensaje], [pcoNombre], [pcoPie], [pcoEtiqueta]) 
	   SELECT pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, 'SEG_AVI_COB_PRS_PENSIONADO' FROM PlantillaComunicado WHERE pcoEtiqueta='SEG_AVI_COB_PRS'
	END

	IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SEG_AVI_COB_PRS_PENSIONADO'))
    BEGIN 
		 INSERT INTO VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante)
		 SELECT REPLACE(vcoClave, 'empleador', 'Pensionado') as vcoClave, REPLACE(vcoDescripcion, 'empleador', 'Pensionado') as vcoDescripcion ,REPLACE(vcoNombre, 'empleador', 'Pensionado') as   vcoNombre,(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SEG_AVI_COB_PRS_PENSIONADO'),vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante FROM VariableComunicado 
		 WHERE vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SEG_AVI_COB_PRS' AND vcoClave not LIKE '%RepresentanteLegal%' )
	 END
	 IF NOT EXISTS (SELECT 1 FROM DestinatarioComunicado  WHERE  dcoEtiquetaPlantilla ='SEG_AVI_COB_PRS_PENSIONADO')
    BEGIN
		 INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_COBRO_ELECTRONICO','SEG_AVI_COB_PRS_PENSIONADO');
	END
	IF NOT EXISTS (SELECT 1 FROM ModuloPlantillaComunicado  WHERE  mpcPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SEG_AVI_COB_PRS_PENSIONADO'))
		BEGIN
		 INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SEG_AVI_COB_PRS_PENSIONADO'), 0, 1, null);
	END
    ----PRI_AVI_COB_PRS_INDEPENDIENTE
	IF NOT EXISTS (SELECT 1 FROM PlantillaComunicado param WHERE  pcoEtiqueta IN ('PRI_AVI_COB_PRS_INDEPENDIENTE'))
	BEGIN 
	   INSERT [PlantillaComunicado] ( [pcoAsunto], [pcoCuerpo], [pcoEncabezado], [pcoIdentificadorImagenPie], [pcoMensaje], [pcoNombre], [pcoPie], [pcoEtiqueta]) 
	   SELECT pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, 'PRI_AVI_COB_PRS_INDEPENDIENTE' FROM PlantillaComunicado WHERE pcoEtiqueta='PRI_AVI_COB_PRS'
	END

	IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'PRI_AVI_COB_PRS_INDEPENDIENTE'))
    BEGIN 
		 INSERT INTO VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante)
		 SELECT REPLACE(vcoClave, 'empleador', 'Independiente') as vcoClave, REPLACE(vcoDescripcion, 'empleador', 'Independiente') as vcoDescripcion ,REPLACE(vcoNombre, 'empleador', 'Independiente') as   vcoNombre,(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SEG_AVI_COB_PRS_INDEP'),vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante FROM VariableComunicado 
		 WHERE vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'PRI_AVI_COB_PRS' AND vcoClave not LIKE '%RepresentanteLegal%' )
	 END
	 IF NOT EXISTS (SELECT 1 FROM DestinatarioComunicado  WHERE  dcoEtiquetaPlantilla ='PRI_AVI_COB_PRS_INDEPENDIENTE')
    BEGIN
		 INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_COBRO_ELECTRONICO','PRI_AVI_COB_PRS_INDEPENDIENTE');
	END
	IF NOT EXISTS (SELECT 1 FROM ModuloPlantillaComunicado  WHERE  mpcPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'PRI_AVI_COB_PRS_INDEPENDIENTE'))
		BEGIN
		 INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SEG_AVI_COB_PRS_INDEP'), 0, 1, null);
	END
    --PRI_AVI_COB_PRS_PENSIONADO
    	IF NOT EXISTS (SELECT 1 FROM PlantillaComunicado param WHERE  pcoEtiqueta IN ('PRI_AVI_COB_PRS_PENSIONADO'))
	BEGIN 
	   INSERT [PlantillaComunicado] ( [pcoAsunto], [pcoCuerpo], [pcoEncabezado], [pcoIdentificadorImagenPie], [pcoMensaje], [pcoNombre], [pcoPie], [pcoEtiqueta]) 
	   SELECT pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, 'PRI_AVI_COB_PRS_PENSIONADO' FROM PlantillaComunicado WHERE pcoEtiqueta='PRI_AVI_COB_PRS'
	END

	IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'PRI_AVI_COB_PRS_PENSIONADO'))
    BEGIN 
		 INSERT INTO VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante)
		 SELECT REPLACE(vcoClave, 'empleador', 'Pensionado') as vcoClave, REPLACE(vcoDescripcion, 'empleador', 'Pensionado') as vcoDescripcion ,REPLACE(vcoNombre, 'empleador', 'Pensionado') as   vcoNombre,(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SEG_AVI_COB_PRS_PENSIONADO'),vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante FROM VariableComunicado 
		 WHERE vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'PRI_AVI_COB_PRS' AND vcoClave not LIKE '%RepresentanteLegal%' )
	 END
	 IF NOT EXISTS (SELECT 1 FROM DestinatarioComunicado  WHERE  dcoEtiquetaPlantilla ='PRI_AVI_COB_PRS_PENSIONADO')
    BEGIN
		 INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_COBRO_ELECTRONICO','PRI_AVI_COB_PRS_PENSIONADO');
	END
	IF NOT EXISTS (SELECT 1 FROM ModuloPlantillaComunicado  WHERE  mpcPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'PRI_AVI_COB_PRS_PENSIONADO'))
		BEGIN
		 INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SEG_AVI_COB_PRS_PENSIONADO'), 0, 1, null);
	END
    ----NTF_AVI_IND
	IF NOT EXISTS (SELECT 1 FROM PlantillaComunicado param WHERE  pcoEtiqueta IN ('NTF_AVI_IND'))
	BEGIN 
	   INSERT [PlantillaComunicado] ( [pcoAsunto], [pcoCuerpo], [pcoEncabezado], [pcoIdentificadorImagenPie], [pcoMensaje], [pcoNombre], [pcoPie], [pcoEtiqueta]) 
	   SELECT pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, 'NTF_AVI_IND' FROM PlantillaComunicado WHERE pcoEtiqueta='NTF_AVI'
	END

	IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_AVI_IND'))
    BEGIN 
		 INSERT INTO VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante)
		 SELECT REPLACE(vcoClave, 'empleador', 'Independiente') as vcoClave, REPLACE(vcoDescripcion, 'empleador', 'Independiente') as vcoDescripcion ,REPLACE(vcoNombre, 'empleador', 'Independiente') as   vcoNombre,(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_AVI_IND'),vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante FROM VariableComunicado 
		 WHERE vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_AVI' AND vcoClave not LIKE '%RepresentanteLegal%' )
	 END
	 IF NOT EXISTS (SELECT 1 FROM DestinatarioComunicado  WHERE  dcoEtiquetaPlantilla ='NTF_AVI_IND')
    BEGIN
		 INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_COBRO_ELECTRONICO','NTF_AVI_IND');
	END
	IF NOT EXISTS (SELECT 1 FROM ModuloPlantillaComunicado  WHERE  mpcPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_AVI_IND'))
		BEGIN
		 INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_AVI_IND'), 0, 1, null);
	END

        ----NTF_AVI_PEN
	IF NOT EXISTS (SELECT 1 FROM PlantillaComunicado param WHERE  pcoEtiqueta IN ('NTF_AVI_PEN'))
	BEGIN 
	   INSERT [PlantillaComunicado] ( [pcoAsunto], [pcoCuerpo], [pcoEncabezado], [pcoIdentificadorImagenPie], [pcoMensaje], [pcoNombre], [pcoPie], [pcoEtiqueta]) 
	   SELECT pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, 'NTF_AVI_PEN' FROM PlantillaComunicado WHERE pcoEtiqueta='NTF_AVI'
	END

	IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_AVI_PEN'))
    BEGIN 
		 INSERT INTO VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante)
		 SELECT REPLACE(vcoClave, 'empleador', 'Pensionado') as vcoClave, REPLACE(vcoDescripcion, 'empleador', 'Pensionado') as vcoDescripcion ,REPLACE(vcoNombre, 'empleador', 'Pensionado') as   vcoNombre,(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_AVI_PEN'),vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante FROM VariableComunicado 
		 WHERE vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_AVI' AND vcoClave not LIKE '%RepresentanteLegal%' )
	 END
	 IF NOT EXISTS (SELECT 1 FROM DestinatarioComunicado  WHERE  dcoEtiquetaPlantilla ='NTF_AVI_PEN')
    BEGIN
		 INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_COBRO_ELECTRONICO','NTF_AVI_PEN');
	END
	IF NOT EXISTS (SELECT 1 FROM ModuloPlantillaComunicado  WHERE  mpcPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_AVI_PEN'))
		BEGIN
		 INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_AVI_PEN'), 0, 1, null);
	END

    ----AVI_INC_IND
	IF NOT EXISTS (SELECT 1 FROM PlantillaComunicado param WHERE  pcoEtiqueta IN ('AVI_INC_IND'))
	BEGIN 
	   INSERT [PlantillaComunicado] ( [pcoAsunto], [pcoCuerpo], [pcoEncabezado], [pcoIdentificadorImagenPie], [pcoMensaje], [pcoNombre], [pcoPie], [pcoEtiqueta]) 
	   SELECT pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, 'AVI_INC_IND' FROM PlantillaComunicado WHERE pcoEtiqueta='AVI_INC'
	END
	
	IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_IND'))
    BEGIN 
		 INSERT INTO VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante)
		 SELECT REPLACE(vcoClave, 'empleador', 'Independiente') as vcoClave, REPLACE(vcoDescripcion, 'empleador', 'Independiente') as vcoDescripcion ,REPLACE(vcoNombre, 'empleador', 'Independiente') as   vcoNombre,(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_IND'),vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante FROM VariableComunicado 
		 WHERE vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC' AND vcoClave not LIKE '%RepresentanteLegal%' )
	 END
	 IF NOT EXISTS (SELECT 1 FROM DestinatarioComunicado  WHERE  dcoEtiquetaPlantilla ='AVI_INC_IND')
    BEGIN
		 INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_COBRO_ELECTRONICO','AVI_INC_IND');
	END
	IF NOT EXISTS (SELECT 1 FROM ModuloPlantillaComunicado  WHERE  mpcPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_IND'))
		BEGIN
		 INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_IND'), 0, 1, null);
	END

    ----AVI_INC_PEN
	IF NOT EXISTS (SELECT 1 FROM PlantillaComunicado param WHERE  pcoEtiqueta IN ('AVI_INC_PEN'))
	BEGIN 
	   INSERT [PlantillaComunicado] ( [pcoAsunto], [pcoCuerpo], [pcoEncabezado], [pcoIdentificadorImagenPie], [pcoMensaje], [pcoNombre], [pcoPie], [pcoEtiqueta]) 
	   SELECT pcoAsunto, pcoCuerpo, pcoEncabezado, pcoIdentificadorImagenPie, pcoMensaje, pcoNombre, pcoPie, 'AVI_INC_PEN' FROM PlantillaComunicado WHERE pcoEtiqueta='AVI_INC'
	END

	IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PEN'))
    BEGIN 
		 INSERT INTO VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante)
		 SELECT REPLACE(vcoClave, 'empleador', 'Independiente') as vcoClave, REPLACE(vcoDescripcion, 'empleador', 'Independiente') as vcoDescripcion ,REPLACE(vcoNombre, 'empleador', 'Independiente') as   vcoNombre,(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PEN'),vcoTipoVariableComunicado,vcoOrden,vcoNombreConstante FROM VariableComunicado 
		 WHERE vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC' AND vcoClave not LIKE '%RepresentanteLegal%' )
	 END
	 IF NOT EXISTS (SELECT 1 FROM DestinatarioComunicado  WHERE  dcoEtiquetaPlantilla ='AVI_INC_PEN')
    BEGIN
		 INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_COBRO_ELECTRONICO','AVI_INC_PEN');
	END
	IF NOT EXISTS (SELECT 1 FROM ModuloPlantillaComunicado  WHERE  mpcPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PEN'))
		BEGIN
		 INSERT INTO dbo.ModuloPlantillaComunicado (mpcModulo, mpcPlantillaComunicado, mpcCertificacionComunicado, mpcBloqueoEnvioComunicado, mpcMetodo) VALUES (N'CARTERA', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PEN'), 0, 1, null);
	END
    