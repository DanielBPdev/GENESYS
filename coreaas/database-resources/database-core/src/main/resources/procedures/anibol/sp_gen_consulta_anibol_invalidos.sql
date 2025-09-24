CREATE OR ALTER PROCEDURE [dbo].[sp_gen_consulta_anibol_invalidos] 
@TipoIdentEmpl VARCHAR(20),
@IdentEmpl VARCHAR(16),
@TipoDependiente VARCHAR(30),
@TipoIndependiente VARCHAR(30),
@TipoPensionado VARCHAR(30),
@TipoBeneficiario VARCHAR(30),
@TipoAdminSubsidio VARCHAR(30),
@FechaIni VARCHAR(30),
@FechaFin VARCHAR(30),
@TipoTransaccion VARCHAR(500),
@IdNov INT,
@identTransaccion INT,--0 sin filtro, 1 afiliacion y 2 novedad,
@categoriaAB INT,
@sinBeneficiarios INT,
@conBeneficiarios INT,
@categoriaB INT,
@pagos2 INT,
@aportesMin INT,
@independienteConGrupoFamiliar INT,
@IndependienteSinGrupoFamiliar  INT,
@sqlResultadoOblig VARCHAR(MAX) OUT,
@sqlResultadoCount VARCHAR(MAX) OUT,
@sqlResultadoObligUnido VARCHAR(MAX) OUT
AS

DECLARE @vNombreCCF VARCHAR(200) = '';
DECLARE @vResponsableCCF VARCHAR(200) = '';
DECLARE @vDireccionCCF VARCHAR(200) = '';
DECLARE @vCiudadCCF VARCHAR(200) = '';
DECLARE @vDepartamentoCCF VARCHAR(200) = '';
DECLARE @vTelefonoCCF VARCHAR(200) = '';
DECLARE @expedicion VARCHAR(1000) = '';

SELECT @vNombreCCF = prmValor FROM parametro WHERE prmNombre='NOMBRE_CCF'; 
SELECT @vResponsableCCF = prmValor FROM parametro WHERE prmNombre='RESPONSABLE_CCF'; 
SELECT @vDireccionCCF = prmValor FROM parametro WHERE prmNombre='DIRECCION_CCF'; 
SELECT @vCiudadCCF = prmValor FROM parametro WHERE prmNombre='CIUDAD_CCF'; 
SELECT @vDepartamentoCCF = prmValor FROM parametro WHERE prmNombre='DEPARTAMENTO_CCF'; 
SELECT @vTelefonoCCF = prmValor FROM parametro WHERE prmNombre='TELEFONO_CCF'; 


DECLARE @s_query VARCHAR(MAX)='';
DECLARE @s_queryTotal VARCHAR(MAX)='
	SELECT TOP 10000 a.*,
			(SELECT dbo.LimpiarCaracteres(prmValor) 
				FROM parametro 
				WHERE prmNombre=''NOMBRE_CCF'') NOMBRE_CCF,
			(SELECT dbo.LimpiarCaracteres(prmValor) 
				FROM parametro 
				WHERE prmNombre=''RESPONSABLE_CCF'') RESPONSABLE_CCF,
			(SELECT dbo.LimpiarCaracteres(prmValor) 
				FROM parametro 
				WHERE prmNombre=''DIRECCION_CCF'') DIRECCION_CCF,
			(SELECT dbo.LimpiarCaracteres(prmValor) 
				FROM parametro 
				WHERE prmNombre=''CIUDAD_CCF'') CIUDAD_CCF,
			(SELECT dbo.LimpiarCaracteres(prmValor) 
				FROM parametro 
				WHERE prmNombre=''DEPARTAMENTO_CCF'') DEPARTAMENTO_CCF,
			(SELECT dbo.LimpiarCaracteres(prmValor) 
				FROM parametro 
				WHERE prmNombre=''TELEFONO_CCF'') TELEFONO_CCF 
		FROM (
';
DECLARE @s_queryTotalUnido_original VARCHAR(MAX)='
	SELECT TOP 15000 
			ISNULL(perTipoIdentificacion,'''') +''|''+ 
			ISNULL(perNumeroIdentificacion,'''') +''|''+ 
			dbo.LimpiarCaracteres(ISNULL(perPrimerNombre,''''))+''|''+
			COALESCE(dbo.LimpiarCaracteres(ISNULL(perSegundoNombre,'''')),'''')+''|''+
			dbo.LimpiarCaracteres(ISNULL(perPrimerApellido,''''))+''|''+
			COALESCE(dbo.LimpiarCaracteres(ISNULL(perSegundoApellido,'''')),'''')+''|''+
			dbo.LimpiarCaracteres(ISNULL(ubiDireccionFisica,''''))+''|''+
			ISNULL(Telefono,'''')+''|''+
			ISNULL(depCodigo,'''')+''|''+
			ISNULL(munCodigo,'''')+''|''+
			CAST(ISNULL(pedFechaNacimiento,'''') AS VARCHAR)+''|''+
			ISNULL(pedEstadoCivil,'''')+''|''+
			ISNULL(pedGenero,'''') +''|''+
			ISNULL(perRazonSocial,'''') AS registro 
		FROM (
';
DECLARE @s_queryCount VARCHAR(MAX)='SELECT COUNT(1) AS cantidadRegistros FROM (';
DECLARE @s_queryWhereEstado VARCHAR(100) = '';
DECLARE @s_queryWhereRequeridos VARCHAR(300) = '';
DECLARE @TipoTransaccionBen VARCHAR(2000) = '';
DECLARE @s_queryTotalUnido VARCHAR(MAX) = '';
DECLARE @complementoSql VARCHAR(2000) = '';
DECLARE @filtrosEspecificos VARCHAR(2000) = '';

--- CAMPOS OBLIGATORIOS POR NOVEDAD *********************************************************************
IF (@IdNov = 1 or @IdNov = 6)
BEGIN
	SET @s_queryTotalUnido = '
		SELECT TOP 1500 
			CASE 
				WHEN perTipoIdentificacion IS NULL THEN ''| Tipo identificacion Vacio'' 
				ELSE dbo.LimpiarCaracteres(perTipoIdentificacion) +'' | '' 
			END + 
			CASE 
				WHEN perNumeroIdentificacion IS NULL THEN ''| Numero Identificacion Vacio'' 
				ELSE perNumeroIdentificacion 
			END + 
			CASE 
				WHEN perPrimerNombre IS NULL THEN '' | Primer Nombre vacio '' 
				ELSE '''' 
			END + 
			CASE 
				WHEN perPrimerApellido IS NULL THEN '' | Primer apellido vacio '' 
				ELSE '''' 
			END +
			CASE 
				WHEN ubiDireccionFisica IS NULL THEN '' | Direccion vacio '' 
				ELSE '''' 
			END + 
			CASE 
				WHEN Telefono IS NULL THEN '' | Telefono vacio '' 
				ELSE '''' 
			END +
			CASE 
				WHEN depCodigo IS NULL THEN '' | Departamento vacio '' 
				ELSE '''' 
			END+
			CASE 
				WHEN munCodigo IS NULL THEN '' | Municipio vacio '' 
				ELSE '''' 
			END+
			CASE 
				WHEN pedFechaNacimiento IS NULL THEN '' | Fecha nacimiento vacio '' 
				ELSE '''' 
			END +
			CASE 
				WHEN pedEstadoCivil IS NULL THEN '' | Estado civil vacio '' 
				ELSE '''' 
			END +
			CASE 
				WHEN pedGenero IS NULL THEN '' | Genero vacio '' 
				ELSE '''' 
			END  
			AS registro 
		FROM (
	';
END;

IF (@IdNov = 2)
BEGIN
	SET @s_queryTotalUnido = '
		SELECT TOP 1500 
			CASE 
				WHEN perTipoIdentificacion IS NULL THEN ''| Tipo identificacion Vacio'' 
				ELSE dbo.LimpiarCaracteres(perTipoIdentificacion) +'' | '' 
			END + 
			CASE 
				WHEN perNumeroIdentificacion IS NULL THEN ''| Numero Identificacion Vacio'' 
				ELSE perNumeroIdentificacion 
			END + 
			CASE 
				WHEN perPrimerNombre IS NULL THEN '' | Primer Nombre vacio '' 
				ELSE '''' 
			END + 
			CASE 
				WHEN perPrimerApellido IS NULL THEN '' | Primer apellido vacio '' 
				ELSE '''' 
			END +
			CASE 
				WHEN ubiDireccionFisica IS NULL THEN '' | Direccion vacio '' 
				ELSE '''' 
			END + 
			CASE 
				WHEN Telefono IS NULL THEN '' | Telefono vacio '' 
				ELSE '''' 
			END +
			CASE 
				WHEN depCodigo IS NULL THEN '' | Departamento vacio '' 
				ELSE '''' 
			END+
			CASE 
				WHEN munCodigo IS NULL THEN '' | Municipio vacio '' 
				ELSE '''' 
			END+
			CASE 
				WHEN pedFechaNacimiento IS NULL THEN '' | Fecha nacimiento vacio '' 
				ELSE '''' 
			END +
			CASE 
				WHEN pedEstadoCivil IS NULL THEN '' | Estado civil vacio '' 
				ELSE '''' 
			END +
			CASE 
				WHEN pedGenero IS NULL THEN '' | Genero vacio '' 
				ELSE '''' 
			END  
			AS registro 
		FROM (
	';
END;
IF (@IdNov = 4)
BEGIN
	SET @s_queryTotalUnido = '
		SELECT TOP 1500 
			CASE 
				WHEN perTipoIdentificacion IS NULL THEN ''| Tipo identificacion Vacio'' 
				ELSE dbo.LimpiarCaracteres(perTipoIdentificacion) +'' | '' 
			END + 
			CASE 
				WHEN perNumeroIdentificacion IS NULL THEN ''| Numero Identificacion Vacio'' 
				ELSE perNumeroIdentificacion 
			END + 
			CASE 
				WHEN perPrimerNombre IS NULL THEN '' | Primer Nombre vacio '' 
				ELSE '''' 
			END + 
			CASE 
				WHEN perPrimerApellido IS NULL THEN '' | Primer apellido vacio '' 
				ELSE '''' 
			END +
			CASE 
				WHEN Telefono IS NULL THEN '' | Telefono vacio '' 
				ELSE '''' 
			END +
			CASE 
				WHEN depCodigo IS NULL THEN '' | Departamento vacio '' 
				ELSE '''' 
			END+
			CASE 
				WHEN munCodigo IS NULL THEN '' | Municipio vacio '' 
				ELSE '''' 
			END+
			CASE 
				WHEN pedEstadoCivil IS NULL THEN ''| Estado civil vacio '' 
				ELSE '''' 
			END +
			CASE 
				WHEN pedGenero IS NULL THEN '' | Genero vacio '' 
				ELSE '''' 
			END  
			AS registro 
		FROM ( 
	';
END;

-- FIN ENCABEZADO CAMPOS OBLIGATORIOS X NOVEDAD ****************************************************

SET @s_queryWhereEstado='(roa.roaEstadoAfiliado = ''ACTIVO''';

IF(@IdNov = 4 OR @IdNov = 6)-- Novedad de Bloqueo o Modificación de información
BEGIN
	SET @s_queryWhereEstado= @s_queryWhereEstado + ' OR roa.roaEstadoAfiliado = ''INACTIVO''';
END;

SET @s_queryWhereEstado= @s_queryWhereEstado + ')';


--Si es tipo trabajador
IF (@TipoDependiente <> '-1' OR @TipoIndependiente <> '-1' OR @TipoPensionado <> '-1')--Por aqui trabajador Dependiente, independiente o pensionado.
BEGIN
	--1. 'Filtro segun la novedad para saber si debe o no tener tarjeta'
    IF(@IdNov = 1)
    BEGIN
        --Sin tarjeta
       	SET @expedicion = '
			AND p.perid NOT IN (
				SELECT mpp.mppPersona
				FROM MedioPagoPersona mpp
				INNER JOIN MedioDePago mdp ON mdp.mdpId = mpp.mppMedioPago
				INNER JOIN MedioTarjeta mpt ON mpt.mdpId = mdp.mdpId
				WHERE mpp.mppMedioActivo = 1
				AND mdp.mdpTipo = ''TARJETA''
				AND mpt.mtrEstadoTarjetaMultiservicios = ''ACTIVA'')
			AND p.perid NOT IN (
				SELECT asu.asuPersona
				FROM AdministradorSubsidio asu
				INNER JOIN AdminSubsidioGrupo asg ON asg.asgAdministradorSubsidio = asu.asuId
				INNER JOIN MedioDePago mdp ON mdp.mdpId = asg.asgMedioDePago
				INNER JOIN MedioTarjeta mpt ON mpt.mdpId = mdp.mdpId
				WHERE asgMedioPagoActivo = 1
				AND mdp.mdpTipo = ''TARJETA''
				AND mpt.mtrEstadoTarjetaMultiservicios = ''ACTIVA'')
			'; 
	END
    ELSE
    BEGIN
        --Con Tarjeta
		SET @expedicion = '
			AND p.perid IN (
				SELECT mpp.mppPersona
				FROM MedioPagoPersona mpp
				INNER JOIN MedioDePago mdp ON mdp.mdpId = mpp.mppMedioPago
				INNER JOIN MedioTarjeta mpt ON mpt.mdpId = mdp.mdpId
				WHERE mpp.mppMedioActivo = 1
				AND mdp.mdpTipo = ''TARJETA''
				AND mpt.mtrEstadoTarjetaMultiservicios = ''ACTIVA'')
			OR p.perid IN (
				SELECT asu.asuPersona
				FROM AdministradorSubsidio asu
				INNER JOIN AdminSubsidioGrupo asg ON asg.asgAdministradorSubsidio = asu.asuId
				INNER JOIN MedioDePago mdp ON mdp.mdpId = asg.asgMedioDePago
				INNER JOIN MedioTarjeta mpt ON mpt.mdpId = mdp.mdpId
				WHERE asgMedioPagoActivo = 1
				AND mdp.mdpTipo = ''TARJETA''
				AND mpt.mtrEstadoTarjetaMultiservicios = ''ACTIVA'')
			'; 

    END;    
    
    --'2. Query base para empleado'
	IF (@TipoDependiente <> '-1')
	BEGIN
		SET @s_query = '
			SELECT DISTINCT 
						p.perTipoIdentificacion,
						p.perNumeroIdentificacion,
						p.perPrimerNombre,
						p.perSegundoNombre,
						p.perPrimerApellido,
						p.perSegundoApellido,
						u.ubiDireccionFisica,
						COALESCE(u.ubiTelefonoFijo, u.ubiTelefonoCelular) AS Telefono,
						d.depCodigo,
						m.munCodigo,
						pd.pedFechaNacimiento,
						pd.pedEstadoCivil,
						pd.pedGenero,
						p.perRazonSocial
				FROM persona p WITH(NOLOCK) 	
				INNER JOIN Afiliado afi  WITH(NOLOCK)
						ON afi.afiPersona = p.perId
				INNER JOIN RolAfiliado roa  WITH(NOLOCK) 
						ON roa.roaAfiliado = afi.afiId	
				INNER JOIN Empleador emp WITH(NOLOCK)
						ON roa.roaEmpleador=emp.empId
				INNER JOIN Empresa empr WITH(NOLOCK)
						ON empr.empId=emp.empEmpresa
				INNER JOIN Persona per2 WITH(NOLOCK)
						ON per2.perId=empr.empPersona
				INNER JOIN PersonaDetalle pd WITH(NOLOCK) 
						ON p.perid = pd.pedPersona
				LEFT JOIN Ubicacion u WITH(NOLOCK) 
						ON p.perUbicacionPrincipal = u.ubiId
				LEFT JOIN Municipio m WITH(NOLOCK) 
						ON u.ubiMunicipio =m.munId  
				LEFT JOIN departamento d  WITH(NOLOCK) 
						ON m.mundepartamento = d.depid
		';


		-- **********************************************************
		-- Filtros para DEPENDIENTE
		-- **********************************************************
		
		IF (@categoriaAB <> -1)
		BEGIN
			-- SET @complementoSql  = ' 
			-- 	INNER JOIN(
			-- 		SELECT DISTINCT afiId 
			-- 			FROM persona
			-- 		INNER JOIN Afiliado afi1
			-- 				ON afiPersona = perId
			-- 		INNER JOIN RolAfiliado 
			-- 				ON roaAfiliado = afiId AND roaTipoAfiliado = ''TRABAJADOR_DEPENDIENTE''
			-- 		INNER JOIN CategoriaAfiliado cta ON cta.ctaAfiliado = afi1.afiId
			-- 		-- INNER JOIN (SELECT MAX(ctaId) AS ctrId, ctaAfiliado 
			-- 		-- 				FROM CategoriaAfiliado GROUP BY ctaAfiliado) AS maxCategoria
			-- 		-- INNER JOIN CategoriaAfiliado cta 
			-- 		-- 		ON cta.ctaId = maxCategoria.ctrId
			-- 		-- 		ON maxCategoria.ctaAfiliado = afiId
			-- 		WHERE ctaCategoria IN (''A'', ''B'')) filtroUno ON filtroUno.afiId = afi.afiId
			-- ';

			SET @complementoSql  = ' 
				INNER JOIN CategoriaCalculada2 cca WITH(NOLOCK) ON cca.ctaAfiliado = afi.afiId and cca.origen = ''Afiliado''
			';

			SET @filtrosEspecificos ='
				AND cta.ctaCategoria IN (''A'',''B'')
			';

			SET @s_query = CONCAT(@s_query, @complementoSql);
		END;
		
		IF (@sinBeneficiarios<> -1)
		BEGIN
			-- SET @complementoSql  = ' 
			-- 	INNER JOIN(
			-- 		SELECT DISTINCT afiId 
			-- 			FROM Afiliado
			-- 		LEFT JOIN Beneficiario 
			-- 			ON benAfiliado = afiId
			-- 		INNER JOIN AporteDetallado 
			-- 				ON apdPersona = afiPersona AND apdTipoCotizante = ''TRABAJADOR_DEPENDIENTE''
			-- 		INNER JOIN AporteGeneral 
			-- 				ON apgId = apdAporteGeneral AND (apgFechaProcesamiento >= (DATEADD (day, -30, getDate())))
			-- 		WHERE benAfiliado IS NULL) filtroTres ON filtroTres.afiId = A.afiId
			-- ';
			SET @complementoSql  = ' 
				INNER JOIN AporteDetallado apd ON apd.apdPersona = p.perId
				INNER JOIN AporteGeneral apg ON apg.apgId = apd.apdAporteGeneral
				LEFT JOIN GrupoFamiliar grf ON grf.grfAfiliado = afi.afiId
			';

			SET @filtrosEspecificos ='
				AND apd.apdTipoCotizante = ''TRABAJADOR_DEPENDIENTE''
				AND grf.grfId IS NULL
				AND p.perId IN (
                SELECT apd.apdPersona FROM AporteDetallado apd)
			';

			SET @s_query = CONCAT(@s_query, @complementoSql);
		END;

		IF (@conBeneficiarios <> -1)
		BEGIN
			-- SET @complementoSql  = '
			-- 	INNER JOIN(
			-- 		SELECT DISTINCT afiId 
			-- 			FROM Afiliado
			-- 		INNER JOIN RolAfiliado 
			-- 				ON roaAfiliado = afiId AND roaTipoAfiliado = ''TRABAJADOR_DEPENDIENTE''
			-- 		LEFT JOIN Beneficiario 
			-- 				ON benAfiliado = afiId
			-- 		WHERE benAfiliado IS NOT NULL AND benEstadoBeneficiarioAfiliado = ''ACTIVO'') filtroDos ON filtroDos.afiId = A.afiId
			-- 	';
			SET @complementoSql  = '
					INNER JOIN GrupoFamiliar grf ON grf.grfAfiliado = afi.afiId
				';
			SET @s_query = CONCAT(@s_query, @complementoSql);
		END;


		-- Inicio del where
		SET @s_query = @s_query + '
				WHERE '+@s_queryWhereEstado +' 
				AND (roa.roaTipoAfiliado = '''+@TipoDependiente+''')
			';

		-- filtros especificos
		SET @s_query = CONCAT(@s_query, @filtrosEspecificos);

		-- Filtro de tarjeta
		SET @s_query = @s_query + @expedicion;

		-- filtros extra
		IF (@IdentEmpl <> '-1' AND @TipoIdentEmpl <> '-1')
		BEGIN 
			SET @s_query = @s_query + '
				AND (per2.pernumeroIdentificacion='''+@IdentEmpl+''')
				AND (per2.pertipoidentificacion='''+@TipoIdentEmpl+''')
			';
		END;

	END; --END IF DEPENDIENTE

	
	--'2.1 Query base para INDEPENDIENTE O PENSIONADO'
	IF (@TipoIndependiente <> '-1' OR @TipoPensionado <> '-1')
	BEGIN
		IF (@s_query <> '' )
		BEGIN
			SET @s_query = @s_query + '
				UNION
			';
		END;
		
		SET @s_query = @s_query + '		
			SELECT DISTINCT 
				p.perTipoIdentificacion,
				p.perNumeroIdentificacion,
				p.perPrimerNombre,
				p.perSegundoNombre,
				p.perPrimerApellido,
				p.perSegundoApellido,
				u.ubiDireccionFisica,
				COALESCE(u.ubiTelefonoFijo, u.ubiTelefonoCelular) AS Telefono,
				d.depCodigo,
				m.munCodigo,
				pd.pedFechaNacimiento,
				pd.pedEstadoCivil,
				pd.pedGenero,
				p.perRazonSocial
			FROM persona p WITH(NOLOCK) 	
			INNER JOIN Afiliado afi  WITH(NOLOCK)
					ON afi.afiPersona = p.perId
			INNER JOIN RolAfiliado roa  WITH(NOLOCK) 
					ON roa.roaAfiliado = afi.afiId
		   ';

		IF (@TipoIndependiente <> '-1')
		BEGIN 
			SET @s_query = @s_query + '
			INNER JOIN SolicitudAfiliacionPersona as sap WITH(NOLOCK)
					ON sap.sapRolAfiliado = roa.roaId
			INNER JOIN Solicitud as sol WITH(NOLOCK) 
					ON sap.sapSolicitudGlobal = sol.solId
					';
		END
		/*
		ELSE IF (@TipoPensionado <> '-1')
		BEGIN
			SET @s_query = @s_query + '(roa.roaTipoAfiliado = '''+@TipoPensionado+''') AND '+ @s_queryWhereEstado;
		END;*/

		SET @s_query = @s_query + '	
			INNER JOIN PersonaDetalle pd WITH(NOLOCK) 
					ON p.perid = pd.pedPersona
			LEFT JOIN Ubicacion u WITH(NOLOCK) 
					ON p.perUbicacionPrincipal = u.ubiId
			LEFT JOIN Municipio m WITH(NOLOCK) 
					ON u.ubiMunicipio =m.munId  
			LEFT JOIN departamento d  WITH(NOLOCK) 
					ON m.mundepartamento = d.depid 
		';

			/* SE COMENTAREA ESTAS UNIONES X RENDIMIENTO -- 25/09/2020
			
			INNER JOIN SolicitudAfiliacionPersona SAP WITH(NOLOCK)
			ON SAP.sapRolAfiliado = RA.roaId
			INNER JOIN Solicitud SolAFILPer WITH(NOLOCK)
			ON SolAFILPer.solId = SAP.sapSolicitudGlobal  */

		-- **********************************************************
		-- Filtros para INDEPENDIENTE
		-- **********************************************************
		--Filtros solo para INDEPENDIENTE, No pensionados
		IF (@TipoIndependiente <> '-1')
		BEGIN
			IF (@categoriaB <> -1)
			BEGIN
				SET @complementoSql  = ' 
					INNER JOIN CategoriaCalculada2 cca WITH(NOLOCK) ON cca.ctaAfiliado = afi.afiId and cca.origen = ''Afiliado''
				';

				SET @filtrosEspecificos ='
					AND cta.ctaCategoria = ''B''
				';
				
				SET @s_query = CONCAT(@s_query, @complementoSql);
			END;
			
			IF (@pagos2 <> -1)
			BEGIN
				SET @complementoSql  = '';

				SET @filtrosEspecificos ='
					AND sol.solClasificacion = ''TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO''
				';
				SET @s_query = CONCAT(@s_query, @complementoSql);
			END;
			
			IF (@aportesMin <> -1)
			BEGIN
				SET @complementoSql  = '
					INNER JOIN AporteDetallado apd ON apd.apdPersona = p.perId
					INNER JOIN AporteGeneral apg ON apg.apgId = apd.apdAporteGeneral
				';

				SET @filtrosEspecificos ='
					AND apd.apdTipoCotizante = ''TRABAJADOR_INDEPENDIENTE''
					AND apg.apgFechaRecaudo >= (DATEADD (day, -30, getDate()))
				';
				SET @s_query = CONCAT(@s_query, @complementoSql);
			END;
		
			IF (@independienteConGrupoFamiliar <> -1)
			BEGIN
				SET @complementoSql  = '
					INNER JOIN GrupoFamiliar grf ON grf.grfAfiliado = afi.afiId
				';

				SET @filtrosEspecificos ='';

				SET @s_query = CONCAT(@s_query, @complementoSql);
			END;
			
			IF (@IndependienteSinGrupoFamiliar <> -1)
			BEGIN

				SET @complementoSql  = '
					LEFT JOIN GrupoFamiliar grf ON grf.grfAfiliado = afi.afiId
				';

				SET @filtrosEspecificos ='
					AND grf.grfId IS NULL
				';

				SET @s_query = CONCAT(@s_query, @complementoSql);
			END;
		END;

		-- Inicio del where
		SET @s_query = @s_query + '
					WHERE '+@s_queryWhereEstado;

		IF(@TipoIndependiente <> '-1')
		BEGIN
			SET @s_query = @s_query + '
					AND (roa.roaTipoAfiliado = '''+@TipoIndependiente+''')
				';
		END
		ELSE
		BEGIN
			SET @s_query = @s_query + '
					AND (roa.roaTipoAfiliado = '''+@TipoPensionado+''')
				';
		END;
		

		-- filtros especificos
		SET @s_query = CONCAT(@s_query, @filtrosEspecificos);

		-- Filtro de tarjeta
		SET @s_query = @s_query + @expedicion;

		-- filtros extra
		IF (@IdentEmpl <> '-1' AND @TipoIdentEmpl <> '-1')
		BEGIN 
			SET @s_query = @s_query + '
				AND (p.pernumeroIdentificacion='''+@IdentEmpl+''')
				AND (p.pertipoidentificacion='''+@TipoIdentEmpl+''')
			';
		END;

	END;-- END PENSIONADO INDEPENDIENTE
	 
END;--END TRABAJADOR

IF (@TipoBeneficiario <> '-1' OR @identTransaccion = 3)
BEGIN
	IF (@s_query <> '' )
	BEGIN
		SET @s_query = @s_query + '
			UNION
		';
	END;

	SET @s_queryWhereEstado='(benEstadoBeneficiarioAfiliado = ''ACTIVO'''

	IF(@IdNov = 4 OR @IdNov = 6)-- Novedad de Bloqueo o Modificación de información
	BEGIN
		SET @s_queryWhereEstado= @s_queryWhereEstado + ' OR benEstadoBeneficiarioAfiliado = ''INACTIVO''';
	END;

	SET @s_queryWhereEstado= @s_queryWhereEstado + ')';

;

	SET @TipoTransaccionBen='
		''ACTIVA_BENEFICIARIO_EN_CUSTODIA_DEPWEB'',
		''ACTIVA_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL'',
		''ACTIVA_BENEFICIARIO_EN_CUSTODIA_WEB'',
		''ACTIVAR_BENEFICIARIO_CONYUGE_DEPWEB'',
		''ACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL'',
		''ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_DEPWEB'',
		''ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_PRESENCIAL'',
		''ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_WEB'',
		''ACTIVAR_BENEFICIARIO_HIJASTRO_DEPWEB'',
		''ACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL'',
		''ACTIVAR_BENEFICIARIO_HIJASTRO_WEB'',
		''ACTIVA_BENEFICIARIO_HIJASTRO_DEPWEB'',
		''ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_DEPWEB'',
		''ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL'',
		''ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_WEB'',
		''ACTIVAR_BENEFICIARIO_MADRE_DEPWEB'',
		''ACTIVAR_BENEFICIARIO_MADRE_PRESENCIAL'',
		''ACTIVAR_BENEFICIARIO_MADRE_WEB'',
		''ACTIVAR_BENEFICIARIO_PADRE_DEPWEB'',
		''ACTIVAR_BENEFICIARIO_PADRE_PRESENCIAL'',
		''ACTIVAR_BENEFICIARIO_PADRE_WEB'',
		''ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_DEPWEB'',
		''ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_PRESENCIAL'',
		''ACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_WEB''
	';
	
	
	--1. 'Filtro segun la novedad para saber si debe o no tener tarjeta'
    IF(@IdNov = 1)
    BEGIN
        --Sin tarjeta
       SET @expedicion = '
			AND  pben.perid NOT IN (
				SELECT mpp.mppPersona
				FROM MedioPagoPersona mpp
				INNER JOIN MedioDePago mdp ON mdp.mdpId = mpp.mppMedioPago
				INNER JOIN MedioTarjeta mpt ON mpt.mdpId = mdp.mdpId
				WHERE mpp.mppMedioActivo = 1
				AND mdp.mdpTipo = ''TARJETA''
				AND mpt.mtrEstadoTarjetaMultiservicios = ''ACTIVA'')
			AND	pben.perid NOT IN (
				SELECT asu.asuPersona
				FROM AdministradorSubsidio asu
				INNER JOIN AdminSubsidioGrupo asg ON asg.asgAdministradorSubsidio = asu.asuId
				INNER JOIN MedioDePago mdp ON mdp.mdpId = asg.asgMedioDePago
				INNER JOIN MedioTarjeta mpt ON mpt.mdpId = mdp.mdpId
				WHERE asgMedioPagoActivo = 1
				AND mdp.mdpTipo = ''TARJETA''
				AND mpt.mtrEstadoTarjetaMultiservicios = ''ACTIVA'')
			'; 
	END
    ELSE
    BEGIN
        --Con Tarjeta
		SET @expedicion = '
			AND  pben.perid IN (
				SELECT mpp.mppPersona
				FROM MedioPagoPersona mpp
				INNER JOIN MedioDePago mdp ON mdp.mdpId = mpp.mppMedioPago
				INNER JOIN MedioTarjeta mpt ON mpt.mdpId = mdp.mdpId
				WHERE mpp.mppMedioActivo = 1
				AND mdp.mdpTipo = ''TARJETA''
				AND mpt.mtrEstadoTarjetaMultiservicios = ''ACTIVA'')
			OR	pben.perid IN (
				SELECT asu.asuPersona
				FROM AdministradorSubsidio asu
				INNER JOIN AdminSubsidioGrupo asg ON asg.asgAdministradorSubsidio = asu.asuId
				INNER JOIN MedioDePago mdp ON mdp.mdpId = asg.asgMedioDePago
				INNER JOIN MedioTarjeta mpt ON mpt.mdpId = mdp.mdpId
				WHERE asgMedioPagoActivo = 1
				AND mdp.mdpTipo = ''TARJETA''
				AND mpt.mtrEstadoTarjetaMultiservicios = ''ACTIVA'')
			'; 

    END;   
    
    --'2. Query base para beneficiario'

    SET @s_query = '
			SELECT DISTINCT 
						pben.perTipoIdentificacion,
						pben.perNumeroIdentificacion,
						pben.perPrimerNombre,
						pben.perSegundoNombre,
						pben.perPrimerApellido,
						pben.perSegundoApellido,
						u.ubiDireccionFisica,
						COALESCE(u.ubiTelefonoFijo, u.ubiTelefonoCelular) AS Telefono,
						d.depCodigo,
						m.munCodigo,
						pd.pedFechaNacimiento,
						pd.pedEstadoCivil,
						pd.pedGenero,
						pben.perRazonSocial
				FROM Beneficiario ben	
				INNER JOIN Persona pben 
						ON pben.perId = ben.benPersona
				INNER JOIN PersonaDetalle pd WITH(NOLOCK) 
						ON pben.perid = pd.pedPersona
				LEFT JOIN Ubicacion u WITH(NOLOCK) 
						ON pben.perUbicacionPrincipal = u.ubiId
				LEFT JOIN Municipio m WITH(NOLOCK) 
						ON u.ubiMunicipio =m.munId  
				LEFT JOIN departamento d  WITH(NOLOCK) 
						ON m.mundepartamento = d.depid
		';

	IF(@TipoTransaccion IS NOT NULL AND @TipoTransaccion <> '' AND @TipoTransaccion <> '''''')
    BEGIN
		SET @filtrosEspecificos ='
			AND ben.benAfiliado IN (
				SELECT afi.afiId
				FROM Solicitud sol
				INNER JOIN SolicitudNovedad sno ON sno.snoSolicitudGlobal = sol.solId
				INNER JOIN SolicitudNovedadPersona snp ON snp.snpSolicitudNovedad = sno.snoId
				INNER JOIN Persona pafi ON pafi.perId = snpPersona
				INNER JOIN Afiliado afi ON afi.afiPersona = pafi.perId
				WHERE sol.solTipoTransaccion IN ('+@TipoTransaccion+')
				AND sol.solResultadoProceso = ''APROBADA''
				AND sno.snoEstadoSolicitud = ''CERRADA''
				AND sol.solFechaRadicacion BETWEEN '''+@FechaIni+''' AND '''+@FechaFin+''')
		';
    END;

	IF(@identTransaccion = 3)
	BEGIN
		SET @filtrosEspecificos ='
			AND ben.benAfiliado IN (
				SELECT afi.afiId
				FROM Solicitud sol
				INNER JOIN SolicitudNovedad sno ON sno.snoSolicitudGlobal = sol.solId
				INNER JOIN SolicitudNovedadPersona snp ON snp.snpSolicitudNovedad = sno.snoId
				INNER JOIN Persona pafi ON pafi.perId = snpPersona
				INNER JOIN Afiliado afi ON afi.afiPersona = pafi.perId
				WHERE sol.solTipoTransaccion IN ('+@TipoTransaccionBen+')
				AND sol.solResultadoProceso = ''APROBADA''
				AND sno.snoEstadoSolicitud = ''CERRADA''
				AND sol.solFechaRadicacion BETWEEN '''+@FechaIni+''' AND '''+@FechaFin+''')
		';
	END;

    --'3. Agregamos los filtros dinamicos'    		
	SET @s_query = @s_query + '
		WHERE '+@s_queryWhereEstado;


	-- filtros especificos
	SET @s_query = CONCAT(@s_query, @filtrosEspecificos);

	-- Filtro de tarjeta
	SET @s_query = @s_query + @expedicion;


END;--END BENEFICIARIO


IF (@TipoAdminSubsidio <> '-1')
BEGIN
	IF (@s_query <> '' )
	BEGIN
		SET @s_query = @s_query + '
			UNION
		';
	END;

    --1. 'Filtro segun la novedad para saber si debe o no tener tarjeta'
    IF(@IdNov = 1)
    BEGIN
        --Sin tarjeta
       SET @expedicion = '
			WHERE asu.asuId NOT IN (
				SELECT asg.asgAdministradorSubsidio
				FROM AdminSubsidioGrupo asg
				INNER JOIN MedioDePago mdp ON mdp.mdpId = asg.asgMedioDePago
				INNER JOIN MedioTarjeta mpt ON mpt.mdpId = mdp.mdpId
				WHERE asgMedioPagoActivo = 1
				AND mdp.mdpTipo = ''TARJETA''
				AND mpt.mtrEstadoTarjetaMultiservicios = ''ACTIVA'')
			'; 
	END
    ELSE
    BEGIN
        --Con Tarjeta
		SET @expedicion = '
			WHERE asu.asuId IN (
				SELECT asg.asgAdministradorSubsidio
				FROM AdminSubsidioGrupo asg
				INNER JOIN MedioDePago mdp ON mdp.mdpId = asg.asgMedioDePago
				INNER JOIN MedioTarjeta mpt ON mpt.mdpId = mdp.mdpId
				WHERE asgMedioPagoActivo = 1
				AND mdp.mdpTipo = ''TARJETA''
				AND mpt.mtrEstadoTarjetaMultiservicios = ''ACTIVA'')
			'; 

    END;

    --'2. Query base para administrador'
	SET @s_query = '
			SELECT DISTINCT 
						p.perTipoIdentificacion,
						p.perNumeroIdentificacion,
						p.perPrimerNombre,
						p.perSegundoNombre,
						p.perPrimerApellido,
						p.perSegundoApellido,
						u.ubiDireccionFisica,
						COALESCE(u.ubiTelefonoFijo, u.ubiTelefonoCelular) AS Telefono,
						d.depCodigo,
						m.munCodigo,
						pd.pedFechaNacimiento,
						pd.pedEstadoCivil,
						pd.pedGenero,
						p.perRazonSocial
				FROM Persona p	
				INNER JOIN AdministradorSubsidio asu 
						ON asu.asuPersona = p.perId 
				LEFT JOIN PersonaDetalle pd WITH(NOLOCK) 
						ON p.perid = pd.pedPersona
				LEFT JOIN Ubicacion u WITH(NOLOCK) 
						ON p.perUbicacionPrincipal = u.ubiId
				LEFT JOIN Municipio m WITH(NOLOCK) 
						ON u.ubiMunicipio =m.munId  
				LEFT JOIN departamento d  WITH(NOLOCK) 
						ON m.mundepartamento = d.depid
		';
    
    
    SET @s_query = @s_query + @expedicion;

END;--END ADMIN SUBSIDIO

-- INICIO NOVEDADES
IF (@identTransaccion = 2 AND @TipoBeneficiario = '-1')
BEGIN
	IF (@s_query <> '' )
	BEGIN
		SET @s_query = @s_query + '
			UNION
		';
	END;

    --1. 'Filtro segun la novedad para saber si debe o no tener tarjeta'
	IF(@IdNov = 1)
    BEGIN
		SET @expedicion = '
				AND p.perid NOT IN (
					SELECT mpp.mppPersona
					FROM MedioPagoPersona mpp
					INNER JOIN MedioDePago mdp ON mdp.mdpId = mpp.mppMedioPago
					INNER JOIN MedioTarjeta mpt ON mpt.mdpId = mdp.mdpId
					WHERE mpp.mppMedioActivo = 1
					AND mdp.mdpTipo = ''TARJETA''
					AND mpt.mtrEstadoTarjetaMultiservicios = ''ACTIVA'')
				AND p.perid NOT IN (
					SELECT asu.asuPersona
					FROM AdministradorSubsidio asu
					INNER JOIN AdminSubsidioGrupo asg ON asg.asgAdministradorSubsidio = asu.asuId
					INNER JOIN MedioDePago mdp ON mdp.mdpId = asg.asgMedioDePago
					INNER JOIN MedioTarjeta mpt ON mpt.mdpId = mdp.mdpId
					WHERE asgMedioPagoActivo = 1
					AND mdp.mdpTipo = ''TARJETA''
					AND mpt.mtrEstadoTarjetaMultiservicios = ''ACTIVA'')
				'; 
	END
	ELSE
	BEGIN
		--Con Tarjeta
		SET @expedicion = '
			AND p.perid IN (
				SELECT mpp.mppPersona
				FROM MedioPagoPersona mpp
				INNER JOIN MedioDePago mdp ON mdp.mdpId = mpp.mppMedioPago
				INNER JOIN MedioTarjeta mpt ON mpt.mdpId = mdp.mdpId
				WHERE mpp.mppMedioActivo = 1
				AND mdp.mdpTipo = ''TARJETA''
				AND mpt.mtrEstadoTarjetaMultiservicios = ''ACTIVA'')
			AND p.perid IN (
				SELECT asu.asuPersona
				FROM AdministradorSubsidio asu
				INNER JOIN AdminSubsidioGrupo asg ON asg.asgAdministradorSubsidio = asu.asuId
				INNER JOIN MedioDePago mdp ON mdp.mdpId = asg.asgMedioDePago
				INNER JOIN MedioTarjeta mpt ON mpt.mdpId = mdp.mdpId
				WHERE asgMedioPagoActivo = 1
				AND mdp.mdpTipo = ''TARJETA''
				AND mpt.mtrEstadoTarjetaMultiservicios = ''ACTIVA'')
			';
	END;

    --'2. Query base para cambio ipo y numero transaccion'
	SET @s_query = @s_query + '
		SELECT DISTINCT 
			p.perTipoIdentificacion, 
			p.perNumeroIdentificacion,
			p.perPrimerNombre,
			p.perSegundoNombre,
			p.perPrimerApellido,
			p.perSegundoApellido,
			u.ubiDireccionFisica,
			COALESCE(u.ubiTelefonoFijo, u.ubiTelefonoCelular) AS Telefono,
			d.depCodigo,
			m.munCodigo,
			pd.pedFechaNacimiento,
			pd.pedEstadoCivil,
			pd.pedGenero,
			p.perRazonSocial
		FROM solicitud sol
		INNER JOIN  solicitudnovedad  sno
				ON sno.snoSolicitudGlobal = sol.solId
		INNER JOIN  SolicitudNovedadPersona snp
				ON snp.snpSolicitudNovedad = sno.snoId
		INNER JOIN Persona p WITH(NOLOCK)
				ON p.perId = snp.snpPersona
		INNER JOIN PersonaDetalle pd WITH(NOLOCK) 
				ON p.perId = pd.pedPersona
		LEFT JOIN Ubicacion u WITH(NOLOCK) 
				ON p.perUbicacionPrincipal = u.ubiId
		LEFT JOIN Municipio m WITH(NOLOCK) 
				ON u.ubiMunicipio = m.munId
		LEFT JOIN departamento d WITH(NOLOCK) 
				ON m.munDepartamento = d.depId 
				
	';
    
    --'3. Agregamos los filtros dinamicos'    
	SET @s_query = @s_query + '
		WHERE sol.solTipoTransaccion IN ('+@TipoTransaccion+')
				AND sol.solResultadoProceso = ''APROBADA''
				AND sno.snoEstadoSolicitud = ''CERRADA''
				AND sol.solFechaRadicacion BETWEEN '''+@FechaIni+''' AND '''+@FechaFin+'''
	';
    
    -- filtros tarjetas
    SET @s_query = @s_query + @expedicion;

END;--END NODEVADES


-- INICIO AFILIACION
IF (@identTransaccion = '1')
BEGIN
	IF (@s_query <> '' )
	BEGIN
		SET @s_query = @s_query + '
			UNION
		';
	END;

    --1. 'Filtro segun la novedad para saber si debe o no tener tarjeta'
	IF(@IdNov = 1)
    BEGIN
		SET @expedicion = '
				AND p.perid NOT IN (
					SELECT mpp.mppPersona
					FROM MedioPagoPersona mpp
					INNER JOIN MedioDePago mdp ON mdp.mdpId = mpp.mppMedioPago
					INNER JOIN MedioTarjeta mpt ON mpt.mdpId = mdp.mdpId
					WHERE mpp.mppMedioActivo = 1
					AND mdp.mdpTipo = ''TARJETA''
					AND mpt.mtrEstadoTarjetaMultiservicios = ''ACTIVA'')
				AND p.perid NOT IN (
					SELECT asu.asuPersona
					FROM AdministradorSubsidio asu
					INNER JOIN AdminSubsidioGrupo asg ON asg.asgAdministradorSubsidio = asu.asuId
					INNER JOIN MedioDePago mdp ON mdp.mdpId = asg.asgMedioDePago
					INNER JOIN MedioTarjeta mpt ON mpt.mdpId = mdp.mdpId
					WHERE asgMedioPagoActivo = 1
					AND mdp.mdpTipo = ''TARJETA''
					AND mpt.mtrEstadoTarjetaMultiservicios = ''ACTIVA'')
				'; 
	END
	ELSE
	BEGIN
		--Con Tarjeta
		SET @expedicion = '
			AND p.perid IN (
				SELECT mpp.mppPersona
				FROM MedioPagoPersona mpp
				INNER JOIN MedioDePago mdp ON mdp.mdpId = mpp.mppMedioPago
				INNER JOIN MedioTarjeta mpt ON mpt.mdpId = mdp.mdpId
				WHERE mpp.mppMedioActivo = 1
				AND mdp.mdpTipo = ''TARJETA''
				AND mpt.mtrEstadoTarjetaMultiservicios = ''ACTIVA'')
			AND p.perid IN (
				SELECT asu.asuPersona
				FROM AdministradorSubsidio asu
				INNER JOIN AdminSubsidioGrupo asg ON asg.asgAdministradorSubsidio = asu.asuId
				INNER JOIN MedioDePago mdp ON mdp.mdpId = asg.asgMedioDePago
				INNER JOIN MedioTarjeta mpt ON mpt.mdpId = mdp.mdpId
				WHERE asgMedioPagoActivo = 1
				AND mdp.mdpTipo = ''TARJETA''
				AND mpt.mtrEstadoTarjetaMultiservicios = ''ACTIVA'')
			';
	END;

    --'2. Query base para afiliacion'
	SET @s_query = @s_query + '
		SELECT DISTINCT 
				p.perTipoIdentificacion, 
				p.perNumeroIdentificacion,
				p.perPrimerNombre,
				p.perSegundoNombre,
				p.perPrimerApellido,
				p.perSegundoApellido,
				u.ubiDireccionFisica,
				COALESCE(u.ubiTelefonoFijo, u.ubiTelefonoCelular) AS Telefono,
				d.depCodigo,
				m.munCodigo,
				pd.pedFechaNacimiento,
				pd.pedEstadoCivil,
				pd.pedGenero,
				p.perRazonSocial
			FROM Persona p  WITH(NOLOCK)
			INNER JOIN Afiliado afi 
					ON afi.afiPersona = p.perId
			INNER JOIN RolAfiliado roa 
					ON roa.roaAfiliado = afi.afiId
			INNER JOIN SolicitudAfiliacionPersona sap 
					ON sap.sapRolAfiliado = roa.roaId
			INNER JOIN Solicitud sol 
					ON sol.solId = sap.sapSolicitudGlobal
			INNER JOIN PersonaDetalle pd WITH(NOLOCK) 
					ON p.perid = pd.pedPersona
			LEFT JOIN Ubicacion u WITH(NOLOCK) 
					ON p.perUbicacionPrincipal = u.ubiId
			LEFT JOIN Municipio m WITH(NOLOCK) 
					ON u.ubiMunicipio =m.munId  
			LEFT JOIN departamento d  WITH(NOLOCK) 
					ON m.mundepartamento = d.depid 		

	';

	--'3. Agregamos los filtros dinamicos'   
	SET @s_query = @s_query + '
		WHERE sap.sapEstadoSolicitud = ''CERRADA''
			AND sol.solResultadoProceso = ''APROBADA''
			AND sol.solFechaRadicacion BETWEEN '''+@FechaIni+''' AND '''+@FechaFin+'''
	';

	
	SET @s_query = @s_query + @expedicion;
    
END;--END NOVEDAD AFILIACION


SET @s_queryTotal = @s_queryTotal + @s_query + ')a';

SET @s_queryTotalUnido = @s_queryTotalUnido + @s_query + ')a';

SET @s_queryCount = @s_queryCount + @s_query + ')a'

IF (@IdNov = 1 or @IdNov = 6)
BEGIN
	SET @s_queryTotal = @s_queryTotal + '
		WHERE (perNumeroIdentificacion IS NULL 
			OR perPrimerNombre IS NULL 
			OR perPrimerApellido IS NULL 
			OR ubiDireccionFisica IS NULL 
			OR Telefono IS NULL 
			OR depCodigo IS NULL 
			OR munCodigo  IS NULL 
			OR pedFechaNacimiento IS NULL 
			OR pedEstadoCivil IS NULL 
			OR pedGenero IS NULL)
	';

	SET @s_queryTotalUnido = @s_queryTotalUnido + '
		WHERE (perNumeroIdentificacion IS NULL 
			OR perPrimerNombre IS NULL 
			OR perPrimerApellido IS NULL 
			OR ubiDireccionFisica IS NULL 
			OR Telefono IS NULL 
			OR depCodigo IS NULL 
			OR munCodigo  IS NULL 
			OR pedFechaNacimiento IS NULL 
			OR pedEstadoCivil IS NULL 
			OR pedGenero IS NULL)
	';
END;
IF (@IdNov = 2)
BEGIN
	SET @s_queryTotal = @s_queryTotal + '
		WHERE (perNumeroIdentificacion IS NULL 
			OR perPrimerNombre IS NULL 
			OR perPrimerApellido IS NULL 
			OR ubiDireccionFisica IS NULL 
			OR Telefono IS NULL 
			OR depCodigo IS NULL 
			OR munCodigo  IS NULL 
			OR pedFechaNacimiento IS NULL 
			OR pedEstadoCivil IS NULL 
			OR pedGenero IS NULL)
	';

	SET @s_queryTotalUnido = @s_queryTotalUnido + '
		WHERE (perNumeroIdentificacion IS NULL 
			OR perPrimerNombre IS NULL 
			OR perPrimerApellido IS NULL 
			OR ubiDireccionFisica IS NULL 
			OR Telefono IS NULL 
			OR depCodigo IS NULL 
			OR munCodigo  IS NULL 
			OR pedFechaNacimiento IS NULL 
			OR pedEstadoCivil IS NULL 
			OR pedGenero IS NULL)
	';
END;

IF (@IdNov = 4)
BEGIN
	SET @s_queryTotal = @s_queryTotal + '
		WHERE (perNumeroIdentificacion IS NULL 
			OR perPrimerNombre IS NULL 
			OR perPrimerApellido IS NULL 
			OR Telefono IS NULL 
			OR depCodigo IS NULL 
			OR munCodigo  IS NULL 
			OR pedEstadoCivil IS NULL 
			OR pedGenero IS NULL)
	';

	SET @s_queryTotalUnido = @s_queryTotalUnido + '
		WHERE (perNumeroIdentificacion IS NULL 
		OR perPrimerNombre IS NULL 
		OR perPrimerApellido IS NULL 
		OR Telefono IS NULL 
		OR depCodigo IS NULL 
		OR munCodigo  IS NULL 
		OR pedEstadoCivil IS NULL 
		OR pedGenero IS NULL)
	';
END;

SET @sqlResultadoOblig=@s_queryTotal;
SET @sqlResultadoCount=@s_queryCount;
SET @sqlResultadoObligUnido=@s_queryTotalUnido;

RETURN 0;