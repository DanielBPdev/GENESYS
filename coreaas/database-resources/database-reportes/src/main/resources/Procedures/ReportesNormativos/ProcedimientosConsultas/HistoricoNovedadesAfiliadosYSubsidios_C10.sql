SELECT @fechaFin,
	2 AS tipoRegistro,
	(SELECT cnsValor FROM dbo.Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO') AS codigoCCF,
	CASE perAfiCore.perTipoIdentificacion 
	    WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
	    WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
	    WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	    WHEN 'PASAPORTE' THEN 'PA'
	    WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
	END AS tipoIdAfiliado,
	perAfiCore.perNumeroIdentificacion AS numeroIdAfiliado,
	CASE perDetAfiCore.pedGenero
		WHEN 'MASCULINO' THEN 'M'
		ELSE 'F'
	END AS codigoGeneroAfiliado,
	perDetAfiCore.pedFechaNacimiento AS fechaNacimientoAfiliado,
	SUBSTRING(perAfiCore.perPrimerApellido,0,60) AS primerApellidoAfiliado,
	SUBSTRING(perAfiCore.perSegundoApellido,0,60) AS segundoApellidoAfiliado,
	SUBSTRING(perAfiCore.perPrimerNombre,0,60) AS primerNombreAfiliado,
	SUBSTRING(perAfiCore.perSegundoNombre,0,60) AS segundoNombreAfiliado,
	'C10' codigoNovedad,
	NULL , -- VARIABLE 1
	NULL , -- VARIABLE 2
	NULL , -- VARIABLE 3
	NULL , -- VARIABLE 4
	NULL , -- VARIABLE 5
	NULL , -- VARIABLE 6
	NULL , -- VARIABLE 7
	NULL , -- VARIABLE 8
	NULL , -- VARIABLE 9
	NULL , -- VARIABLE 10
	NULL , -- VARIABLE 11
	NULL , -- VARIABLE 12
	NULL , -- VARIABLE 13
	NULL , -- VARIABLE 14
	NULL , -- VARIABLE 15
	NULL , -- VARIABLE 16
	NULL , -- VARIABLE 17
	NULL , -- VARIABLE 18
	NULL , -- VARIABLE 19
	NULL , -- VARIABLE 20
	NULL , -- VARIABLE 21
	NULL , -- VARIABLE 22
	NULL , -- VARIABLE 23
	CASE benModificacionCore.anteriorTipoId
		WHEN 'REGISTRO_CIVIL' THEN 'RC'
		WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
		WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
        WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
        WHEN 'PASAPORTE' THEN 'PA'
        WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
    end VARIABLE24,
    benModificacionCore.anteriorNumeroId VARIABLE25	,
	NULL , -- VARIABLE 25
	NULL , -- VARIABLE 26
	NULL , -- VARIABLE 27
	NULL , -- VARIABLE 28
	NULL , -- VARIABLE 29
	NULL , -- VARIABLE 30
	NULL , -- VARIABLE 31
	NULL , -- VARIABLE 32
	NULL , -- VARIABLE 33
	NULL , -- VARIABLE 34
	NULL , -- VARIABLE 35
	NULL , -- VARIABLE 36
	NULL , -- VARIABLE 37
	NULL , -- VARIABLE 38
	NULL , -- VARIABLE 39
	NULL , -- VARIABLE 40
	NULL , -- VARIABLE 41
	NULL , -- VARIABLE 42
	NULL , -- VARIABLE 43
	NULL , -- VARIABLE 44
	NULL , -- VARIABLE 45
	NULL , -- VARIABLE 46
	NULL , -- VARIABLE 47
	NULL , -- VARIABLE 48
	NULL , -- VARIABLE 49
	NULL , -- VARIABLE 50
	NULL , -- VARIABLE 51
	NULL , -- VARIABLE 52
	NULL , -- VARIABLE 53
	NULL , -- VARIABLE 54
	NULL , -- VARIABLE 55
	CASE benModificacionCore.actualTipoId
		WHEN 'REGISTRO_CIVIL' THEN 'RC'
		WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
		WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
        WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
        WHEN 'PASAPORTE' THEN 'PA'
        WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
    end VARIABLE56,
	benModificacionCore.actualNumeroId VARIABLE57, -- VARIABLE 57
	NULL -- VARIABLE 58			
	FROM dbo.Afiliado AS afiCore
	INNER JOIN dbo.RolAfiliado AS rolAfiCore ON rolAfiCore.roaAfiliado = afiCore.afiId
	INNER JOIN dbo.Persona AS perAfiCore ON afiCore.afiPersona = perAfiCore.perId
	INNER JOIN dbo.PersonaDetalle AS perDetAfiCore ON perAfiCore.perId = perDetAfiCore.pedPersona
	INNER JOIN dbo.Beneficiario AS benCore ON benCore.benAfiliado = afiCore.afiId
	INNER JOIN dbo.BeneficiarioDetalle AS benDetCore ON benDetCore.bedId = benCore.benBeneficiarioDetalle
	INNER JOIN dbo.Persona AS perBenCore ON perBenCore.perId = benCore.benPersona			
	INNER JOIN #PersonasModificacionDatosBasicos benModificacionCore ON benModificacionCore.perId = perBenCore.perId
    WHERE rolAfiCore.roaEstadoAfiliado = 'ACTIVO'
