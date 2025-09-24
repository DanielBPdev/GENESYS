--liquibase formatted sql

--changeset fvasquez:01
--comment: Adición de campos acrCartera y agrCartera
ALTER TABLE aud.ActividadCartera_aud ALTER column acrCicloAportante BIGINT NULL;
ALTER TABLE aud.AgendaCartera_aud ALTER column agrCicloAportante BIGINT NULL;
ALTER TABLE aud.ActividadCartera_aud add acrCartera BIGINT NULL;
ALTER TABLE aud.AgendaCartera_aud add agrCartera BIGINT NULL;

--changeset mamonroy:02
--comment: Adición de campos JEHINGRESOMENSUAL
ALTER TABLE jefehogar add jehIngresoMensual NUMERIC(19,2);
ALTER TABLE aud.jefehogar_aud add jehIngresoMensual NUMERIC(19,2);

--changeset jvelandia:03
--comment: Configuracione Plantilla Comunicado
UPDATE PlantillaComunicado set pcoCuerpo='<p style=''text-align:justify;line-height:normal''>S - AP</p>
<p style=''text-align:justify;line-height:normal''>FU - SUB</p>
<p>&nbsp;</p>
<p style=''text-align:justify;line-height:normal''>(<i style=''mso-bidi-font-style:normal''>Ciudad</i>), ${fechaGeneracion}</p>
<p>&nbsp;</p>
<p style=''text-align:justify;line-height:normal''>Señor (a)</p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${nombreCompleto}</b></p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${tipoSolicitante}</b></p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''><span style=''mso-spacerun:yes''> </span>${direccionPrincipal} – ${municipio} ${departamento}</b></p>
<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${telefono}</b></p>
<p>&nbsp;</p>
<p style=''text-align:justify;line-height:normal''>Ref.: Certificado de historia de afiliación ${refCertificado}</p>
<p>&nbsp;</p>
<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>${nombreCcf}</b></p>
<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>Sección de afiliación ${seccionAfiliacion}</b></p>
<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>Certifica</b></p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p style=''text-align:justify;line-height:normal''>Que ${tipoTrabajador} <b style=''mso-bidi-font-weight:normal''>${nombreCompleto} ${tipoIdentificacion} número ${numeroIdentificacion} 
a la fecha ${fechaGeneracion} presenta la siguiente historia de afiliación a esta CAJA DE COMPENSACION FAMILIAR.</b></p>
<p>&nbsp;</p>
${tabla}
<p>&nbsp;</p>
<p><span style=''color:black;mso-themecolor:text1''>Esta certificación no es válida para retirarse de <b style=''mso-bidi-font-weight:normal''>${nombreCcf}</b>, ni afiliarse a otra Caja de Compensación.</span></p>
<p>&nbsp;</p>
<p><span style=''color:black;mso-themecolor:text1''>Se expide a solicitud del interesado.</span></p>
<p><span style=''color:black;mso-themecolor:text1''>&nbsp;</span></p>
<p><span style=''color:black;mso-themecolor:text1''>NOS RESERVAMOS EL DERECHO DE REALZAR AUDITORIA POSTERIOR.</span></p>
<p style=''text-align:justify''>&nbsp;</p>
<p style=''text-align:justify;line-height:normal''>${contenido}</p>
<p>&nbsp;</p> 
<p><i style=''mso-bidi-font-style:normal''>Cordialmente, </i></p>  <p><span>&nbsp;</span></p>  <p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>
${responsableCcf}</b></p>  <p style=''text-align:justify;line-height:normal''>
<b style=''mso-bidi-font-weight:normal''>${cargoResponsableCcf}</b></p>'
WHERE pcoEtiqueta='COM_GEN_CER_HIS_AFI';

UPDATE PlantillaComunicado set pcoCuerpo = '<p>${logoDeLaCcf}</p> <p>${fechaDelSistema}</p> <p>&nbsp;</p> <p class="MsoNormal"><em><span lang="ES">&ldquo;Se&ntilde;or(es)</span></em></p> <p class="MsoNormal"><em>${razonSocial/Nombre}</em><br /><em>${tipoIdentificacion}&nbsp;${numeroIdentificacion}</em></p> <p class="MsoNormal"><br /><em><span lang="ES" style="font-size: 10.0pt; mso-bidi-font-size: 11.0pt; line-height: 150%; font-family: ''Trebuchet MS'',sans-serif; mso-fareast-font-family: Calibri; mso-bidi-font-family: Arial; mso-ansi-language: ES; mso-fareast-language: ZH-CN; mso-bidi-language: AR-SA;">Nos permitimos informarle que la Planilla Integrada de Aportes No.&nbsp;&nbsp;</span>${numeroPlanilla}&nbsp;<span lang="ES" style="font-size: 10.0pt; mso-bidi-font-size: 11.0pt; line-height: 150%; font-family: ''Trebuchet MS'',sans-serif; mso-fareast-font-family: Calibri; mso-bidi-font-family: Arial; mso-ansi-language: ES; mso-fareast-language: ZH-CN; mso-bidi-language: AR-SA;">correspondiente al per&iacute;odo&nbsp;&nbsp;</span>${periodoAporte}&nbsp;<span lang="ES" style="font-size: 10.0pt; mso-bidi-font-size: 11.0pt; line-height: 150%; font-family: ''Trebuchet MS'',sans-serif; mso-fareast-font-family: Calibri; mso-bidi-font-family: Arial; mso-ansi-language: ES; mso-fareast-language: ZH-CN; mso-bidi-language: AR-SA;">con Fecha&nbsp;&nbsp;</span>${fechaPagoAporte}&nbsp;<span lang="ES">ha sido procesada con los siguientes resultados:&rdquo;</span></em></p> <p class="MsoNormal">&nbsp;</p> <p class="MsoNormal"><em><strong><span lang="ES">&ldquo;</span></strong><span lang="ES">Se&ntilde;or empleador, usted ha realizado pago de aportes a la Caja de compensaci&oacute;n familiar de Caldas &ndash; Confa, sin haber realizado previamente una afiliaci&oacute;n. Le invitamos a que realice la respectiva afiliaci&oacute;n para que sus trabajadores puedan acceder a los servicios que nuestra caja les brinda y para que usted pueda solicitar la generaci&oacute;n de certificados con fines tributarios o legales.</span></em></p> <p class="MsoNormal">&nbsp;</p> <p class="MsoNormal"><em><span lang="ES">&ldquo;En la planilla han sido reportados&nbsp;</span><span lang="ES">&nbsp;${numeroTrabajadores}<span style="color: #0070c0;">&nbsp;</span>trabajadores, de los cuales,<span style="color: #0070c0;">&nbsp;</span>${numeroTrabajadoresActivo}<span style="color: #0070c0;">&nbsp;<span style="background: yellow; mso-highlight: yellow;">cotizantes</span></span>&nbsp;afiliados a la caja y${numeroTrabajadoresNoActivo}&nbsp;<span style="color: #0070c0;"><span style="background: yellow; mso-highlight: yellow;">cotizantes</span></span>&nbsp;pendientes por afiliar a la caja.</span></em></p> <p class="MsoNormal"><em><span lang="ES" style="font-size: 10.0pt; mso-bidi-font-size: 11.0pt; line-height: 150%; font-family: ''Trebuchet MS'',sans-serif; mso-fareast-font-family: Calibri; mso-bidi-font-family: Arial; mso-ansi-language: ES; mso-fareast-language: ZH-CN; mso-bidi-language: AR-SA;">Para mayor informaci&oacute;n comun&iacute;quese en Manizales al tel&eacute;fono (6) 878 3111 Ext. 1369-1370</span></em></p> <p class="MsoNormal"><span lang="ES" style="font-size: 10.0pt; mso-bidi-font-size: 11.0pt; line-height: 150%; font-family: ''Trebuchet MS'',sans-serif; mso-fareast-font-family: Calibri; mso-bidi-font-family: Arial; mso-ansi-language: ES; mso-fareast-language: ZH-CN; mso-bidi-language: AR-SA;">&ldquo;<em>Todos los aportes han sido recibidos satisfactoriamente</em>&rdquo;.</span></p> <p class="MsoNormal">&nbsp;</p> <p class="MsoNormal"><span lang="ES" style="font-size: 10.0pt; mso-bidi-font-size: 11.0pt; line-height: 150%; font-family: ''Trebuchet MS'',sans-serif; mso-fareast-font-family: Calibri; mso-bidi-font-family: Arial; mso-ansi-language: ES; mso-fareast-language: ZH-CN; mso-bidi-language: AR-SA;"><span lang="ES" style="font-size: 10pt; line-height: 150%;">&ldquo;<em>Para mayor informaci&oacute;n consulte su portal privado en la p&aacute;gina web (</em>p&aacute;gina web parametrizada para la caja<em>) donde encontrar&aacute; el detalle del procesamiento de cada novedad</em>&rdquo;.</span></span></p> <p class="MsoNormal">No9 se reportaron novedades<br />${nombreCcf}<br />${departamentoCcf}<br />${ciudadCcf}<br />${direccionCcf}<br />${telefonoCcf}<br />${logoSuperservicios}<br />${responsableCcf}<br />${cargoResponsableCcf}</p><br  /> <p>${cotizantes}</p>'
WHERE pcoEtiqueta='NTF_REC_APT_PLA_DEP12';

INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) 
VALUES ('${cotizantes}',null,'Tipo, numero y nombre del cotizante como lista','Tipo, numero y nombre del cotizante como lista','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_REC_APT_PLA_DEP12'));

--changeset fvasquez:04
--comment: 
ALTER TABLE AccionCobro2F2G add aofDiasRegistro BIGINT NULL;
ALTER TABLE AccionCobro2F2G add aofDiasParametrizados BIGINT NULL;
ALTER TABLE aud.AccionCobro2F2G_aud add aofDiasRegistro BIGINT NULL;
ALTER TABLE aud.AccionCobro2F2G_aud add aofDiasParametrizados BIGINT NULL;

--changeset clmarin:05
--comment: 
EXEC sp_rename 'aud.SolicitudAporte_aud.soaAporteGeneral', 'soaRegistroGeneral', 'COLUMN';  

--changeset clmarin:06
--comment: 
ALTER TABLE SolicitudAporte DROP FK_SolicitudAporte_soaAporteGeneral;

--changeset abaquero:07
--comment: Adición de marcas de trazabilidad para reintegro por aportes
ALTER TABLE aud.Empleador_aud ADD empCanalReingreso varchar(21);
ALTER TABLE aud.Empleador_aud ADD empReferenciaAporteReingreso bigint;

--changeset abaquero:08
--comment: Inactivación de validaciones por CC Ajustes4 Genesys
UPDATE dbo.ValidatorDefinition SET state=0 WHERE id=2110087
UPDATE dbo.ValidatorDefinition SET state=0 WHERE id=2110205
UPDATE dbo.FieldDefinitionLoad SET required=0 WHERE id=2110076
UPDATE dbo.FieldDefinitionLoad SET required=0 WHERE id=2110050
