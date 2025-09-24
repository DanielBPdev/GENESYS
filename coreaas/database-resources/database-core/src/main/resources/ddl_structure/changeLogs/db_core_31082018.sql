--liquibase formatted sql

--changeset jvelandia:01
--comment: Actualizaciones tabla PlantillaComunicado
UPDATE PlantillaComunicado SET pcoCuerpo='<p style=''text-align:justify;line-height:normal''>S - AP</p>
	<p style=''text-align:justify;line-height:normal''>FU - SUB</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>(<i style=''mso-bidi-font-style:normal''>Ciudad</i>), ${fechaGeneracion}</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Señor (a)</p>
	<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${nombreCompleto}</b></p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Certificado de Paz y Salvo para desafiliación y cambio de caja</p>
	<p>&nbsp;</p>
	<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>${nombreCcf}</b></p>
	<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>Sección de afiliación ${seccionAfiliacion}</b></p>
	<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>Certifica</b></p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Que ${tipoTrabajador} <b style=''mso-bidi-font-weight:normal''>${nombreCompleto} ${tipoIdentificacion} número ${numeroIdentificacion} </b>
	Solicito su desafiliación de la caja como ${tipoAfiliado} , pagando sus aportes correspondientes según el tipo de afiliación hasta el mes de ${mesFinAporteCertificado} </p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Se expide la presente certificación a ${fechaGeneracion} y es válida como Paz y Salvo.</p>
	<p style=''text-align:justify''>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>${contenido}</p>
	<p><span style=''color:black;mso-themecolor:text1''>NOS RESERVAMOS EL DERECHO DE REALZAR AUDITORIA POSTERIOR.</span></p>
	<p><span style=''color:black;mso-themecolor:text1''>&nbsp;</span></p>
	<p><span style=''color:black;mso-themecolor:text1''>Se expide a solicitud del interesado.</span></p>
	<p>&nbsp;</p>'
WHERE pcoEtiqueta='COM_GEN_CER_PYS';


UPDATE PlantillaComunicado SET pcoCuerpo='<p style=''text-align:justify;line-height:normal''>S - AP</p>
	<p style=''text-align:justify;line-height:normal''>FU - SUB</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>(<i style=''mso-bidi-font-style:normal''>Ciudad</i>), ${fechaGeneracion}</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Señor (a)</p>
	<p style=''text-align:justify;line-height:normal''><b style=''mso-bidi-font-weight:normal''>${nombreCompleto}</b></p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Certificado de Paz y Salvo para desafiliación y cambio de caja</p>
	<p>&nbsp;</p>
	<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>${nombreCcf}</b></p>
	<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>Sección de afiliación ${seccionAfiliacion}</b></p>
	<p style=''text-align:center''><b style=''mso-bidi-font-weight:normal''>Certifica</b></p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Que ${tipoTrabajador} <b style=''mso-bidi-font-weight:normal''>${nombreCompleto} ${tipoIdentificacion} número ${numeroIdentificacion} </b>
	Solicito su desafiliación para afiliación a otra Caja de Compensación Familiar, pagando sus aportes correspondientes al 4% a ésta corporación hasta el mes de ${mesFinAporteCertificado} </p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Los aportes correspondientes al mes de ${mesInAporteCertificado} deberán cancelarlos con la nueva Caja de Compensación a la que se afilien</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>Se expide la presente certificación a ${fechaGeneracion} y es válida como Paz y Salvo para cambio de Caja en el Departamento ${departamentoCerPazySalvo}</p>
	<p style=''text-align:justify''>&nbsp;</p>
	<p style=''text-align:justify;line-height:normal''>${contenido}</p>
	<p><span style=''color:black;mso-themecolor:text1''>NOS RESERVAMOS EL DERECHO DE REALZAR AUDITORIA POSTERIOR.</span></p>
	<p><span style=''color:black;mso-themecolor:text1''>&nbsp;</span></p>
	<p><span style=''color:black;mso-themecolor:text1''>Se expide a solicitud del interesado.</span></p>
	<p>&nbsp;</p>'
where pcoEtiqueta='COM_GEN_CER_PYS_EMP';