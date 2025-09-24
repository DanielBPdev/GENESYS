-- =============================================
-- Author: Robinson Castillo
-- Create Date: 2021-08-20
-- Description: Reporte GIASS Relación_Beneficiario_Otro_Padre_Biológico - GLPI 50015
-- =============================================
CREATE OR ALTER     PROCEDURE [dbo].[USP_REP_REPGIASS187]
as


SET ANSI_WARNINGS OFF
SET NOCOUNT ON
SET ANSI_NULLS ON
 
SET QUOTED_IDENTIFIER ON
 

SELECT DISTINCT CASE p.perTipoIdentificacion

  WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
	WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	WHEN 'NIT' THEN 'NI'
	WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
	WHEN 'PASAPORTE' THEN 'PA'
	WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
	WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' 
	WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
	WHEN 'REGISTRO_CIVIL' THEN 'RC'
	WHEN 'SALVOCONDUCTO' THEN 'SC'---SE ADICIONA PERMISO ESPECIAL EL DIA 20210518 Y PORQUE ES OBLIGATORIO
	ELSE ''
	END
 AS C94
 --[Tipo de identificación del beneficiario o miembro del grupo familiar]
, p.perNumeroIdentificacion as C95
---as [Número de identificación del beneficiario o miembro del grupo familiar]
,  CASE pa.perTipoIdentificacion

  WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
	WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	WHEN 'NIT' THEN 'NI'
	WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
	WHEN 'PASAPORTE' THEN 'PA'
	WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
	WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' 
	WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
	WHEN 'REGISTRO_CIVIL' THEN 'RC'
	WHEN 'SALVOCONDUCTO' THEN 'SC'---SE ADICIONA PERMISO ESPECIAL EL DIA 20210518 Y PORQUE ES OBLIGATORIO
	ELSE ''
	END AS C837
	--[Tipo de identificación del padre o madre biológico del beneficiario]
, pa.perNumeroIdentificacion as C838
--as [Número de identificación del padre o madre biológico del beneficiario]
, pa.perPrimerNombre as C839
--as [Primer nombre del padre o madre biológico del beneficiario]
, pa.perSegundoNombre as C840
--as [Segundo nombre del padre o madre biológico del beneficiario]
, pa.perPrimerApellido as C841
--as [Primer apellido del padre o madre biológico del beneficiario]
, CONCAT(pa.perSegundoApellido,';') as C842
--[Segundo apellido del padre o madre biológico del beneficiario ]
--, gb.grfNumero, b.benTipoBeneficiario
from Beneficiario as b with (nolock)
inner join persona as p with (nolock) on p.perId = b.benPersona
inner join GrupoFamiliar as gb with (nolock) on gb.grfId  = b.benGrupoFamiliar
inner join Afiliado  as a with (nolock) on a.afiId = b.benAfiliado and a.afiId = gb.grfAfiliado
inner join persona as pa with (nolock) on pa.perId = a.afiPersona
inner join RolAfiliado as ra on ra.roaAfiliado=a.afiId
inner join SolicitudAfiliacionPersona sap on sap.sapRolAfiliado=ra.roaId
inner join Solicitud s on s.solId=sap.sapSolicitudGlobal
where b.benTipoBeneficiario in ('HIJASTRO','HIJO_BIOLOGICO') 
and b.benEstadoBeneficiarioAfiliado is not null
AND b.benFechaAfiliacion IS NOT NULL
and s.solResultadoProceso='APROBADA' and sap.sapEstadoSolicitud='CERRADA'
order by p.perNumeroIdentificacion