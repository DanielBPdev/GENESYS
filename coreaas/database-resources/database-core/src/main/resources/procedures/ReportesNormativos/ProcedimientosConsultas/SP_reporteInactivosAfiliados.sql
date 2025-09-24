
CREATE PROCEDURE [dbo].[reporteInactivosAfiliados](
	@FECHA_INICIAL DATE,
	@FECHA_FINAL DATE
)

AS

/*DECLARE  @FECHA_INICIAL DATE,
	@FECHA_FINAL DATE

SET @FECHA_INICIAL = '02-01-2021';
SET @FECHA_FINAL = '02-28-2021';*/


BEGIN
-- SET ANSI_WARNINGS OFF
SET NOCOUNT ON

	----------Tipo Identificacion Aportante----------
	SELECT 
		CASE PE.perTipoIdentificacion  
			WHEN 'CEDULA_CIUDADANIA' THEN '1' 
			WHEN 'TARJETA_IDENTIDAD' THEN '2' 
			WHEN 'REGISTRO_CIVIL' THEN '3'
			WHEN 'CEDULA_EXTRANJERIA' THEN '4'
			WHEN 'NUIP' THEN '5'
			WHEN 'PASAPORTE' THEN '6'
			WHEN 'NIT' THEN '7' 
			WHEN 'CARNE_DIPLOMATICO' THEN '8'
			WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
		END AS [Tipo de identificación de la empresa],

		----------Numero Identificacion Aportante----------
		PE.perNumeroIdentificacion AS [Número de identificación Empresa],

		----------Tipo Identificacion Afiliado----------
		CASE PA.perTipoIdentificacion  
			WHEN 'CEDULA_CIUDADANIA' THEN '1' 
			WHEN 'TARJETA_IDENTIDAD' THEN '2' 
			WHEN 'REGISTRO_CIVIL' THEN '3'
			WHEN 'CEDULA_EXTRANJERIA' THEN '4'
			WHEN 'NUIP' THEN '5'
			WHEN 'PASAPORTE' THEN '6'
			WHEN 'NIT' THEN '7' 
			WHEN 'CARNE_DIPLOMATICO' THEN '8'
			WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
		END AS [Tipo de Identificación Afiliado],

		----------Numero Identificacion Afiliado----------
		LEFT (PA.perNumeroIdentificacion,15) AS [Número de identificación afiliado],
		
		----------Tipo Identificacion Beneficiario----------
		CASE PB.perTipoIdentificacion  
			WHEN 'CEDULA_CIUDADANIA' THEN '1' 
			WHEN 'TARJETA_IDENTIDAD' THEN '2' 
			WHEN 'REGISTRO_CIVIL' THEN '3'
			WHEN 'CEDULA_EXTRANJERIA' THEN '4'
			WHEN 'NUIP' THEN '5'
			WHEN 'PASAPORTE' THEN '6'
			WHEN 'NIT' THEN '7' 
			WHEN 'CARNE_DIPLOMATICO' THEN '8'
			WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
		END AS [Tipo de Identificación de la persona a cargo],

		----------Numero de Identificacion Beneficiario----------
		LEFT (PB.perNumeroIdentificacion,15) AS [Número de identificación de la persona a cargo],
		
		----------Primer Nombre----------
		CASE 
			WHEN PB.perPrimerNombre is null THEN '' 
			ELSE  LEFT (PB.perPrimerNombre,30)  
		END AS [Primer Nombre de la persona a cargo],

		----------Segundo Nombre----------
		CASE 
			WHEN PB.perSegundoNombre is null THEN '' 
			ELSE  LEFT (PB.perSegundoNombre,30)  
		END  AS [Segundo Nombre de la persona a cargo],

		----------Primer Apellido----------
		CASE 
			WHEN PB.perPrimerApellido is null THEN '' 
			ELSE  LEFT (PB.perPrimerApellido,30)  
		END  AS [Primer Apellido de la persona a cargo],

		----------Segundo Apellido----------
		CASE 
			WHEN PB.perSegundoApellido is null THEN '' 
			ELSE  LEFT (PB.perSegundoApellido,30)  
		END  AS [Segundo Apellido de la persona a cargo],

		----------Fecha Nacimiento----------
		CONVERT (VARCHAR,PDB.pedFechaNacimiento,112) AS [Fecha de Nacimiento de la persona a cargo],

		----------Genero----------
		ISNULL (
			CASE PDB.pedGenero
				WHEN 'FEMENINO' THEN '2'
				WHEN 'MASCULINO' THEN '1'
				WHEN 'INDEFINIDO' THEN'4' 
			END, '3') AS [Sexo de la persona a cargo],
	
		----------Tipo Beneficiario----------
		CASE BEN.benTipoBeneficiario
			WHEN 'HIJO_BIOLOGICO' THEN '1'
			WHEN 'MADRE' THEN '2'
			WHEN 'PADRE' THEN '2'
			WHEN 'HERMANO_HUERFANO_DE_PADRES' THEN '3'
			WHEN 'HIJASTRO' THEN '4'
			WHEN 'CONYUGE' THEN '5'
			WHEN 'BENEFICIARIO_EN_CUSTODIA' THEN '6'
			WHEN 'HIJO_ADOPTIVO' THEN '6'
		END AS [Parentesco de la persona a cargo],

		----------Codigo Municipio----------
		m.munCodigo AS [Código municipio de residencia de la persona a cargo],

		----------Area Geografica----------
		CASE 
			WHEN pda.pedResideSectorRural=1 THEN '2' 
			ELSE '1'
		END  AS [Área Geográfica de Residencia de la persona a cargo],

		---------- Invalides ? ----------
		CASE coiInvalidez
			WHEN '0' THEN '2'
			WHEN '1' THEN '1' 
			ELSE '2'
		END AS [Condición de discapacidad de la persona a cargo],

		----------Tipo de Cuota Monetria----------
		CASE 
			WHEN D.dsatipocuotasubsidio = 'REGULAR' THEN '1'
			WHEN D.dsatipocuotasubsidio = 'DISCAPACIDAD' THEN '2'
			WHEN D.dsatipocuotasubsidio = 'AGRICOLA' THEN '3'
			WHEN D.dsatipocuotasubsidio = 'DISCAPACIDAD_AGRICOLA' THEN '4'

			WHEN D.dsatipocuotasubsidio 
				IN( 
					'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO', 
					'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO'
				) OR D.dsaTipoliquidacionSubsidio = 'ESPECIFICA_FALLECIMIENTO'
			THEN '5' 

			WHEN D.dsatipocuotasubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO_AGRICOLA' THEN '7'
			WHEN D.dsatipocuotasubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_AGRICOLA' THEN '7'
			WHEN D.dsatipocuotasubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_DISCAPACITADO' THEN '8'
			WHEN D.dsatipocuotasubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_DISCAPACITADO_AGRICOLA' THEN '9'
			WHEN D.dsatipocuotasubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO_AGRICOLA_BENEFICIARIO_DISCAPACITADO' THEN '9' 
			ELSE '6'
		END AS [Tipo de cuota monetaria pagado a la persona a cargo],
		
		---------Valor Subsidio----------
		CASE 
			WHEN D.dsaValorTOTAL IS NULL
			THEN 0
			ELSE REPLACE ( SUM(D.dsaValorTOTAL), '.00000','')

		END AS [Valor de la cuota monetaria pagada a la persona a cargo],

		---------Cuotas Pagadas----------
		ISNULL (
			CASE D.dsatipocuotasubsidio
				WHEN 'AGRICOLA' THEN '01'
				WHEN 'REGULAR' THEN '01'
				WHEN 'DISCAPACIDAD' THEN '02'
				WHEN 'DISCAPACIDAD_AGRICOLA' THEN '02'
				WHEN 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO' THEN '01' 
				WHEN 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO_AGRICOLA' THEN '01'
				WHEN 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO' THEN '01'
				WHEN 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_AGRICOLA' THEN '01'
				WHEN 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_DISCAPACITADO' THEN '02'
				WHEN 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_DISCAPACITADO_AGRICOLA' THEN '02'
				WHEN 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO_AGRICOLA_BENEFICIARIO_DISCAPACITADO' THEN '02' 
			END,
		'0') AS [Número de cuotas pagadas],

		----------Periodos Liquidados----------
		ISNULL (CANT.canperiodosliquidados, '0') AS [Numero de periodos pagados]

		--CANT.dsaafiliadoprincipal
	FROM 
		persona pe
		INNER JOIN empresa emp WITH (nolock) on emp.emppersona=pe.perid
		INNER JOIN empleador empl WITH (nolock) on empl.empempresa=emp.empid
		INNER JOIN rolafiliado roa WITH (nolock) on roa.roaempleador=empl.empid
		INNER JOIN afiliado ap WITH (nolock) on ap.afiid=roa.roaafiliado
		INNER JOIN persona pa WITH (nolock) on pa.perid=ap.afipersona
		INNER JOIN personadetalle pda WITH (nolock) on pa.perid=pda.pedpersona
		INNER JOIN ubicacion u on ubiid=pa.perubicacionprincipal
		INNER JOIN municipio m on m.munid=u.ubimunicipio
		INNER JOIN beneficiario ben WITH (nolock) on ben.benafiliado=ap.afiid
		INNER JOIN persona pb WITH (nolock) on pb.perId=ben.benPersona
		INNER JOIN PersonaDetalle pdb WITH (nolock) on pdb.pedPersona=pb.perId
		INNER JOIN BeneficiarioDetalle WITH (nolock) on bedPersonaDetalle=pdb.pedId
		LEFT JOIN CondicionInvalidez WITH (nolock) ON PB.PERID=coiPersona
		INNER JOIN DetalleSubsidioAsignado D WITH (nolock) on bedid=dsaBeneficiarioDetalle 
			AND d.dsaEmpleador=empl.empid 
			AND d.dsaAfiliadoPrincipal = ap.afiid

		LEFT JOIN (
			
			SELECT  
				dsaafiliadoprincipal, 
				dsabeneficiariodetalle, 
				COUNT (DSAPERIODOLIQUIDADO) AS canperiodosliquidados,
				dsatipocuotasubsidio,
				dsaTipoliquidacionSubsidio
			
			FROM DetalleSubsidioAsignado  

			WHERE 
				
				dsaFechaHoraCreacion
				BETWEEN @FECHA_INICIAL AND @FECHA_FINAL 
				--dsaEstado = 'DERECHO_ASIGNADO' 

			GROUP BY 
				dsaafiliadoprincipal, 
				dsabeneficiariodetalle,
				dsatipocuotasubsidio,
				dsaTipoliquidacionSubsidio

		) CANT ON 

			CANT.dsaAfiliadoPrincipal=D.dsaAfiliadoPrincipal AND 
			CANT.dsaBeneficiarioDetalle=D.dsaBeneficiarioDetalle AND
			CANT.dsatipocuotasubsidio  = D.dsatipocuotasubsidio AND
			CANT.dsaTipoliquidacionSubsidio = d.dsaTipoliquidacionSubsidio 

		WHERE
		--CONDICION LIQUIDADOS
			D.dsaFechaHoraCreacion > @FECHA_INICIAL AND  
			D.dsaFechaHoraCreacion < @FECHA_FINAL

		GROUP BY 
			PE.perTipoIdentificacion, 
			PE.perNumeroIdentificacion,
			PA.perTipoIdentificacion,
			PA.perNumeroIdentificacion,
			PB.perTipoIdentificacion,
			PB.perNumeroIdentificacion,
			PB.perPrimerApellido,
			PB.perSegundoApellido,
			PB.perPrimerNombre,
			PB.perSegundoNombre,
			PDB.pedFechaNacimiento, 
			PDB.pedGenero, 
			BEN.benTipoBeneficiario,
			M.munCodigo,
			pda.pedResideSectorRural,
			coiInvalidez,
			D.dsatipocuotasubsidio,
			D.dsaValorTOTAL,
			D.dsatipocuotasubsidio, 
			d.dsaTipoliquidacionSubsidio,
			CANT.canperiodosliquidados, 
			d.dsaid

union


select 

	--------------------

	CASE PE.perTipoIdentificacion  
		WHEN 'CEDULA_CIUDADANIA' THEN '1' 
		WHEN 'TARJETA_IDENTIDAD' THEN '2' 
		WHEN 'REGISTRO_CIVIL' THEN '3'
		WHEN 'CEDULA_EXTRANJERIA' THEN '4'
		WHEN 'NUIP' THEN '5'
		WHEN 'PASAPORTE' THEN '6'
		WHEN 'NIT' THEN '7' 
		WHEN 'CARNE_DIPLOMATICO' THEN '8'
		WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
	END AS TIPOID_EMP,

	----------
	PE.perNumeroIdentificacion AS NUMEROID_EMP,

	----------
	CASE PA.perTipoIdentificacion  
		WHEN 'CEDULA_CIUDADANIA' THEN '1' 
		WHEN 'TARJETA_IDENTIDAD' THEN '2' 
		WHEN 'REGISTRO_CIVIL' THEN '3'
		WHEN 'CEDULA_EXTRANJERIA' THEN '4'
		WHEN 'NUIP' THEN '5'
		WHEN 'PASAPORTE' THEN '6'
		WHEN 'NIT' THEN '7' 
		WHEN 'CARNE_DIPLOMATICO' THEN '8'
		WHEN 'PERM_ESP_PERMANENCIA' THEN '9'  
	END AS TIPOID_AFI,

	----------
	LEFT (PA.perNumeroIdentificacion,15) AS NUMEROID_AFI,

	----------
	CASE PB.perTipoIdentificacion  
		WHEN 'CEDULA_CIUDADANIA' THEN '1' 
		WHEN 'TARJETA_IDENTIDAD' THEN '2' 
		WHEN 'REGISTRO_CIVIL' THEN '3'
		WHEN 'CEDULA_EXTRANJERIA' THEN '4'
		WHEN 'NUIP' THEN '5'
		WHEN 'PASAPORTE' THEN '6'
		WHEN 'NIT' THEN '7' 
		WHEN 'CARNE_DIPLOMATICO' THEN '8'
		WHEN 'PERM_ESP_PERMANENCIA' THEN '9'  
	END AS TIPOID_BEN,

	----------
	LEFT (PB.perNumeroIdentificacion,15) AS NUMEROID_BEN,

	----------
	CASE 
		when PB.perPrimerNombre is null THEN '' 
		ELSE  LEFT (PB.perPrimerNombre,30)  
	END  AS PRIMNOMBBEN,

	----------
	CASE 
		when PB.perSegundoNombre is null THEN '' 
		ELSE  LEFT (PB.perSegundoNombre,30)  
	END  AS SEGNOMBBEN,

	----------
	CASE 
		when PB.perPrimerApellido is null THEN '' 
		ELSE  LEFT (PB.perPrimerApellido,30)  
	END  AS PRIMAPELBEN,

	----------
	CASE 
		when PB.perSegundoApellido is null THEN '' 
		ELSE  LEFT (PB.perSegundoApellido,30)  
	END  AS SEGAPELBEN,

	----------
	CONVERT (VARCHAR,PDB.pedFechaNacimiento,112) AS FECHANACIMIENTOCARG,

	----------
	ISNULL (
		CASE PDB.pedGenero
			WHEN 'FEMENINO' THEN '2'
			WHEN 'MASCULINO' THEN '1'
			WHEN 'INDEFINIDO' THEN'4' 
		END, '3') AS GENERO,

	----------
	CASE benTipoBeneficiario
		WHEN 'HIJO_BIOLOGICO' THEN '1'
		WHEN 'MADRE' THEN '2'
		WHEN 'PADRE' THEN '2'
		WHEN 'HERMANO_HUERFANO_DE_PADRES' THEN '3'
		WHEN 'HIJASTRO' THEN '4'
		WHEN 'CONYUGE' THEN '5'
		WHEN 'BENEFICIARIO_EN_CUSTODIA' THEN '6'
		WHEN 'HIJO_ADOPTIVO' THEN '6'END AS PARENTESCOCARGO,
	
	----------Codigo Municipio----------
	munCodigo AS [Código municipio de residencia de la persona a cargo],

	----------Area Geografica----------
	CASE 
		WHEN pda.pedResideSectorRural=1 THEN '2' 
		ELSE '1'
	END  AS [Área Geográfica de Residencia de la persona a cargo],

	----------
	ISNULL (
		CASE coiInvalidez
			WHEN '0' THEN '2'
			WHEN '1' THEN '1' 
		END, '2') AS CONDICIONDISCAPACIDAD,

	----------
	CASE d.dsatipocuotasubsidio
		when 'AGRICOLA' then '3'
		when 'REGULAR' then '1'
		when 'DISCAPACIDAD' then '2'
		when 'DISCAPACIDAD_AGRICOLA' then '4'
		when 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO' then '5' 
		when 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO_AGRICOLA' then '7'
		when 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO' then '5'
		when 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_AGRICOLA' then '7'
		when 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_DISCAPACITADO' then '8'
		when 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_DISCAPACITADO_AGRICOLA' then '9'
		when 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO_AGRICOLA_BENEFICIARIO_DISCAPACITADO' then '9'
	ELSE '6'
	end AS TIPOCUOTA,

	----------
	ISNULL( REPLACE (d.dsaValorSubsidioMonetario,'.00000', ''), '0') AS VALORCUOTAPAGADA,
	
	----------
	ISNULL(
		CASE d.dsatipocuotasubsidio
			when 'AGRICOLA' then '01'
			when 'REGULAR' then '01'
			when 'DISCAPACIDAD' then '02'
			when 'DISCAPACIDAD_AGRICOLA' then '02'
			when 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO' then '01' 
			when 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO_AGRICOLA' then '01'
			when 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO' then '01'
			when 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_AGRICOLA' then '01'
			when 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_DISCAPACITADO' then '02'
			when 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_DISCAPACITADO_AGRICOLA' then '02'
			when 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO_AGRICOLA_BENEFICIARIO_DISCAPACITADO' then '02' 
		end, '0') as CUOTASPAGADAS,

	----------
	ISNULL( CANT.canperiodosliquidados, '0') as cantidad

from 
	ROLAFILIADO
	inner join afiliado  WITH (nolock) on afiid=roaafiliado
	inner join persona pa WITH (nolock)  on pa.perid=afipersona
	inner join personadetalle pda WITH (nolock) on pa.perid=pda.pedpersona
	left join ocupacionprofesion op WITH (nolock) on op.ocuid=pedOcupacionProfesion
	INNER JOIN Ubicacion WITH (nolock) on PA.perUbicacionPrincipal=ubiId
	INNER JOIN Municipio WITH (NOLOCK) ON MUNid=ubiMunicipio
	--inner join SolicitudAfiliacionPersona WITH (NOLOCK) on sapRolAfiliado=roaid
	--inner join solicitud with (nolock) on solid=sapSolicitudGlobal
	inner join ( select  * from CategoriaAfiliado c 
where c.ctafechacambiocategoria =(select max(m.ctafechacambiocategoria) from
CategoriaAfiliado m with (nolock) 
where m.ctaafiliado=c.ctaafiliado and m.ctaEstadoAfiliacion = 'ACTIVO'))
 cat on ctaafiliado=afiid and ctaEstadoAfiliacion = 'ACTIVO'
LEFT join beneficiario ben WITH (nolock) on ben.benafiliado=afiid
LEFT join persona pb WITH (nolock) on pb.perId=ben.benPersona
LEFT join PersonaDetalle pdb WITH (nolock) on pdb.pedPersona=pb.perId
inner join CondicionInvalidez with(nolock) on pb.perid=coiPersona
LEFT JOIN BeneficiarioDetalle WITH (nolock) on bedPersonaDetalle=pdb.pedId
LEFT JOIN DetalleSubsidioAsignado D WITH (nolock) on bedid=dsaBeneficiarioDetalle and d.dsaFechaHoraCreacion > @FECHA_INICIAL and d.dsaFechaHoraCreacion < @FECHA_FINAL

left join empleador emp WITH (nolock) on roaempleador=emp.empid
inner join empresa e WITH (nolock) on emp.empempresa=e.empid
left join persona pe on pe.perid=e.emppersona

LEFT join (
	SELECT 
		dsaafiliadoprincipal, 
		dsabeneficiariodetalle, 
		COUNT (DSAPERIODOLIQUIDADO) as canperiodosliquidados
	FROM 
		DetalleSubsidioAsignado 
	WHERE
		dsaFechaHoraCreacion BETWEEN @FECHA_INICIAL AND @FECHA_FINAL
	GROUP BY 
		dsaafiliadoprincipal, 
		dsabeneficiariodetalle
) CANT ON 
	CANT.dsaAfiliadoPrincipal=D.dsaAfiliadoPrincipal 
	AND CANT.dsaBeneficiarioDetalle=D.dsaBeneficiarioDetalle

	where 
	d.dsaid is null AND ROAESTADOAFILIADO = 'ACTIVO' AND ROAFECHAAFILIACION < @FECHA_FINAL
	 
--	 D  
--persona pe
--inner join empresa emp WITH (nolock) on emp.emppersona=pe.perid
--inner join empleador empl WITH (nolock) on empl.empempresa=emp.empid
--inner join rolafiliado roa WITH (nolock) on roa.roaempleador=empl.empid
--inner join afiliado ap WITH (nolock) on ap.afiid=roa.roaafiliado
--inner join persona pa WITH (nolock) on pa.perid=ap.afipersona
--inner join personadetalle pda WITH (nolock) on pa.perid=pda.pedpersona
----inner join beneficiario ben WITH (nolock) on ben.benafiliado=ap.afiid
----inner join persona pb WITH (nolock) on pb.perId=ben.benPersona
----inner join PersonaDetalle pdb WITH (nolock) on pdb.pedPersona=pb.perId
----inner JOIN BeneficiarioDetalle WITH (nolock) on bedPersonaDetalle=pdb.pedId
----LEFT JOIN CondicionInvalidez WITH (nolock) ON PB.PERID=coiPersona
----LEFT JOIN DetalleSubsidioAsignado D WITH (nolock) on bedid=dsaBeneficiarioDetalle

END
