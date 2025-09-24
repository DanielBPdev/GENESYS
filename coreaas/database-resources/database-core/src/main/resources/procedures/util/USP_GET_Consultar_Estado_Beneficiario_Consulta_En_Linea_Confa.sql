-- =============================================
-- Author:      Juan Montaña
-- Create date: 24/6/2022
-- Description: Return values for SERVICIO WEB Estado_Afiliado_Consulta_En_Linea_Confa
--
-- Parameters:
--   @tipoDocumento - Tipo identificación del Beneficiario. Accepts nulls
--   @numeroDocumento - N° id del Beneficiario. Accepts nulls
-- Returns:     Value of discount expressed as % (0-100)
-- =============================================
 
CREATE OR ALTER PROCEDURE [dbo].[USP_GET_Consultar_Estado_Beneficiario_Consulta_En_Linea_Confa]
				(@tipoDocumento VARCHAR (20),
				@numeroDocumento VARCHAR (16))

AS

	SET NOCOUNT ON;   

		IF EXISTS (SELECT TOP 1 1
					FROM Persona as per with (nolock)
					INNER JOIN Beneficiario as ben with (nolock) ON ben.benPersona = per.perId
					WHERE per.perTipoIdentificacion = @tipoDocumento
					AND per.perNumeroIdentificacion = @numeroDocumento)

		BEGIN
			drop table if exists #BeneficiarioT
			CREATE TABLE #BeneficiarioT (perId bigInt, benId BIGINT, benAfiliado bigInt, bedId bigInt, perTipoIdentificacion varchar(50), perNumeroIdentificacion varchar(50),
			perPrimerNombre varchar(50), perSegundoNombre varchar(50), perPrimerApellido varchar(50), perSegundoApellido varchar(50), pedPersonaMadre bigInt, pedPersonaPadre bigInt,
			benGrupoFamiliar bigInt, benGradoAcademico bigInt, benEstadoBeneficiarioAfiliado varchar(20), benTipoBeneficiario varchar(30), pedFechaNacimiento date, pedNivelEducativo varchar(100));

--DECLARE @personaBen BIGINT;
--SET @personaBen = (SELECT TOP 1 perId FROM Persona with (nolock) WHERE perNumeroIdentificacion = @numeroDocumento AND perTipoIdentificacion = @tipoDocumento);

			INSERT INTO #BeneficiarioT  
			select p.perId, ben.benId, ben.benAfiliado, bed.bedId, p.perTipoIdentificacion, p.perNumeroIdentificacion, p.perPrimerNombre, p.perSegundoNombre, p.perPrimerApellido, p.perSegundoApellido, ped.pedPersonaMadre, ped.pedPersonaPadre
			, ben.benGrupoFamiliar, ben.benGradoAcademico, ben.benEstadoBeneficiarioAfiliado, ben.benTipoBeneficiario, ped.pedFechaNacimiento, ped.pedNivelEducativo
			from dbo.Persona as p  with (nolock)
			inner join dbo.Beneficiario as ben with (nolock) on p.perId = ben.benPersona 
			INNER JOIN BeneficiarioDetalle bed with (nolock) ON bed.bedId = ben.benBeneficiarioDetalle
			INNER JOIN PersonaDetalle ped with (nolock) ON ped.pedPersona = p.perId
			where p.perTipoIdentificacion = @tipoDocumento and p.perNumeroIdentificacion = @numeroDocumento


			;WITH certificadoEscolar (cebId, cebBeneficiarioDetalle)
			AS (SELECT MAX (cebId), cebBeneficiarioDetalle
					from #BeneficiarioT as b
					inner join dbo.CertificadoEscolarBeneficiario ceb on b.bedId = ceb.cebBeneficiarioDetalle
					/*
					FROM CertificadoEscolarBeneficiario with (nolock)
					INNER JOIN beneficiarioDetalle with (nolock) ON bedId = cebBeneficiarioDetalle
					INNER JOIN beneficiario with (nolock) ON benBeneficiarioDetalle = bedId
					*/
					WHERE cebFechaCreacion <= dbo.getlocaldate()
					AND cebFechaVencimiento >= dbo.getlocaldate()
					AND cebFechaRecepcion <= dbo.getlocaldate()
					--AND (benId IN (SELECT benId FROM #BeneficiarioT ))
					GROUP BY cebBeneficiarioDetalle
			),Liquidacion (dsaPeriodoLiquidado, benAfiliado, benId)
			AS (select MAX (dsa.dsaPeriodoLiquidado) AS dsaPeriodoLiquidado, dsa.dsaAfiliadoPrincipal, dsa.dsaBeneficiarioDetalle
				from dbo.DetalleSubsidioAsignado as dsa with (nolock)
				where exists (	select 1 from #BeneficiarioT as b where dsa.dsaBeneficiarioDetalle = b.bedId and dsa.dsaAfiliadoPrincipal = b.benAfiliado)
				GROUP BY dsa.dsaAfiliadoPrincipal, dsa.dsaBeneficiarioDetalle
				)


			SELECT 'OK' resultado_consulta
			, ISNULL (ben.perTipoIdentificacion, '') AS tipo_doc_ben
			, ISNULL (ben.perNumeroIdentificacion, '') AS num_doc_ben
			, ISNULL (ben.perPrimerNombre, '') AS pri_nom_ben
			, ISNULL (ben.perSegundoNombre, '') AS seg_nom_ben
			, ISNULL (ben.perPrimerApellido, '') AS pri_ape_ben
			, ISNULL (ben.perSegundoApellido, '') AS seg_ape_ben
			, ISNULL (ben.benEstadoBeneficiarioAfiliado, '') AS estado_beneficiario
			, ISNULL (coi.coiInvalidez, '') AS condicion_inv
			, ISNULL ((DATEDIFF (YEAR,ben.pedFechaNacimiento ,dbo.getlocaldate())), '') AS edad
			, ISNULL (CONVERT (VARCHAR (10), ben.pedFechaNacimiento), '') AS fecha_nacimiento
			, ISNULL (grf.grfNumero, '') AS num_grupo_fam
			, ISNULL (gra.graNombre, '') AS grado
			, ISNULL (ben.pedNivelEducativo, '') AS nivel_educativo
			, ISNULL (sip.sipNombre, '') AS sitio_pago
			, ISNULL (ben.benEstadoBeneficiarioAfiliado, '') AS estado_grupo_fam
			, ISNULL (ben.benTipoBeneficiario, '') AS parentesco
			, ISNULL (perPadre.perTipoIdentificacion, '') AS tipo_doc_otro_padre
			, ISNULL (perPadre.perNumeroIdentificacion, '') AS num_doc_otro_padre
			, ISNULL ((CASE WHEN cer.cebId IS NOT NULL THEN 1 ELSE 0 END), '') AS certificado_vigente
			, ISNULL (CONVERT (VARCHAR (10), ceb.cebFechaRecepcion), '') AS fecha_ini_cert
			, ISNULL (CONVERT (VARCHAR (10), ceb.cebFechaVencimiento), '') AS fecha_fin_cert
			, ISNULL (liq.dsaPeriodoLiquidado, '') AS fecha_liquidacion
			, ISNULL (perAfi.perTipoIdentificacion, '') AS tipo_doc_afi
			, ISNULL (perAfi.perNumeroIdentificacion, '') AS num_doc_afi
			/*
			FROM beneficiario ben with (nolock)
			INNER JOIN Persona pBen with (nolock) ON pBen.perId = ben.benPersona
			INNER JOIN PersonaDetalle pedBen with (nolock) ON pedBen.pedPersona = pBen.perId
			*/
			from #BeneficiarioT as ben
			LEFT JOIN CondicionInvalidez coi with (nolock) ON coi.coiPersona = ben.perId
			--Datos Grupo familiar
			LEFT JOIN GrupoFamiliar grf with (nolock) ON grf.grfId = ben.benGrupoFamiliar
			--Grado academico
			LEFT JOIN gradoAcademico gra with (nolock) ON graId = ben.benGradoAcademico
			-- Otros padres-Padre
			LEFT JOIN Persona perPadre with (nolock) ON perPadre.perId = ben.pedPersonaMadre OR perPadre.perId = ben.pedPersonaPadre
			--Sitio Pago
			LEFT JOIN AdminSubsidioGrupo asg with (nolock) ON asg.asgGrupoFamiliar = grf.grfId AND asg.asgMedioPagoActivo = 1
			LEFT JOIN MedioDePago mdp with (nolock) ON mdp.mdpId = asg.asgMedioDePago
			LEFT JOIN MedioEfectivo mef with (nolock) ON mef.mdpId = mdp.mdpId
			LEFT JOIN SitioPago sip with (nolock) ON sip.sipId = mef.mefSitioPago
			--Certificado Escolar
			--LEFT JOIN BeneficiarioDetalle bed with (nolock) ON bed.bedId = ben.benBeneficiarioDetalle
			LEFT JOIN certificadoEscolar cer with (nolock) ON cer.cebBeneficiarioDetalle = ben.bedId
			LEFT JOIN CertificadoEscolarBeneficiario ceb with (nolock) ON ceb.cebId = cer.cebId
			--Datos afiliados
			LEFT JOIN afiliado afi with (nolock) ON afi.afiId = ben.benAfiliado
			LEFT JOIN persona perAfi with (nolock) ON perAfi.perId = afi.afiPersona
			--Liquidacion
			LEFT JOIN Liquidacion liq with (nolock) ON liq.benAfiliado = afi.afiId AND liq.benId = ben.benId
			/*
			WHERE (pBen.perTipoIdentificacion = @tipoDocumento OR '0' = ISNULL(@tipoDocumento, 0))
			AND (pBen.perNumeroIdentificacion = @numeroDocumento OR '0' = ISNULL (@numeroDocumento, 0))
			*/
			;

		END
	ELSE
		BEGIN
		SELECT 'NO_EXISTE_EL_BENEFICIARIO' AS resultado_consulta
		, ' ' AS tipo_doc_ben
		, ' ' AS num_doc_ben
		, ' ' AS pri_nom_ben
		, ' ' AS seg_nom_ben
		, ' ' AS pri_ape_ben
		, ' ' AS seg_ape_ben
		, ' ' AS estado_beneficiario
		, ' ' AS condicion_inv
		, ' ' AS edad
		, ' ' AS fecha_nacimiento
		, ' ' AS num_grupo_fam
		, ' ' AS grado
		, ' ' AS nivel_educativo
		, ' ' AS sitio_pago
		, ' ' AS estado_grupo_fam
		, ' ' AS parentesco
		, ' ' AS tipo_doc_otro_padre
		, ' ' AS num_doc_otro_padre
		, ' ' as certificado_vigente
		, ' ' AS fecha_ini_cert
		, ' ' AS fecha_fin_cert
		, ' ' AS fecha_liquidacion
		, ' ' AS tipo_doc_afi
		, ' ' AS num_doc_afi
		END;