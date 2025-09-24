-- =============================================
-- Author:      Robinson Castillo
-- Description: Reporte GIASS - Aportes GLPI 50018
--188	Consulta estado de los servicios para independientes y pensionados a la fecha
-- =============================================
CREATE OR ALTER  PROCEDURE [dbo].[USP_REP_REPGIASS188]
as
select  CASE p.perTipoIdentificacion
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
	END as [Tipo de identificación del autorizado a reportar (empleador)]
, p.perNumeroIdentificacion as [Número de identificación del autorizado a reportar (empleador)]
,  CASE p2.perTipoIdentificacion

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
	END as [Tipo de identificación del trabajador o cotizante o cabeza de familia]
, p2.perNumeroIdentificacion as [Número de identificación del trabajador o cotizante o cabeza de familia]
, concat(apg.apgPeriodoAporte,N'-01') as [Período de pago de los aportes]
--, c.catCategoriaPersona as [Categoría de afiliación en el período reportado]
, CASE WHEN (c.catCategoriaPersona NOT IN('A','B','C','D','E') OR c.catCategoriaPersona IS NULL) THEN 'N' ELSE c.catCategoriaPersona END as [Categoría de afiliación en el período reportado]
, apd.apdDiasCotizados as [Días cotizados]
-------------------- ************************ -----------------------------
--************* Inicia la unión de las tablas ----------------------------
-------------------- ************************ -----------------------------
from AporteGeneral as apg (nolock)
inner join aporteDetallado as apd on apg.apgId = apd.apdAporteGeneral
inner join Persona as p with (nolock) on p.perId = apg.apgPersona
inner join persona as p2 with (nolock) on apd.apdPersona = p2.perId
left join afiliado as a with (nolock) on p2.perId = a.afiPersona
left join categoria as c with (nolock) on a.afiId = c.catIdAfiliado
where apg.apgTipoSolicitante in ('INDEPENDIENTE', 'PENSIONADO') --and ra.tipoAfi != 'TRABAJADOR_DEPENDIENTE'

------------------------------------------
--- Para empleadores 
------------------------------------------

union

select  CASE p.perTipoIdentificacion

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
	END as [Tipo de identificación del autorizado a reportar (empleador)]
, p.perNumeroIdentificacion as [Número de identificación del autorizado a reportar (empleador)]
,  CASE p2.perTipoIdentificacion

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
	END as [Tipo de identificación del trabajador o cotizante o cabeza de familia]
, p2.perNumeroIdentificacion as [Número de identificación del trabajador o cotizante o cabeza de familia]
, concat(apg.apgPeriodoAporte,N'-01') as [Período de pago de los aportes]
--, c.catCategoriaPersona as [Categoría de afiliación en el período reportado]
,CASE WHEN (c.catCategoriaPersona NOT IN('A','B','C','D','E') OR c.catCategoriaPersona IS NULL) THEN 'N' ELSE c.catCategoriaPersona END as [Categoría de afiliación en el período reportado]
, apd.apdDiasCotizados as [Días cotizados]
-------------------- ************************ -----------------------------
--************* Inicia la unión de las tablas ----------------------------
-------------------- ************************ -----------------------------
from AporteGeneral as apg with (nolock)
inner join aporteDetallado as apd on apg.apgId = apd.apdAporteGeneral
left join Empresa as emp  with (nolock) on apg.apgEmpresa = emp.empId
left join Empleador as empl with (nolock) on empl.empEmpresa = emp.empId
left join Persona as p with (nolock) on emp.empPersona = p.perId
inner join persona as p2 with (nolock) on apd.apdPersona = p2.perId
left join afiliado as a with (nolock) on p2.perId = a.afiPersona
left join categoria as c with (nolock) on a.afiId = c.catIdAfiliado
where apg.apgTipoSolicitante = 'EMPLEADOR'

------------------------------------------
---- para empresas tramitadoras del aporte
------------------------------------------

union 
   
select  CASE p.perTipoIdentificacion

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
	END as [Tipo de identificación del autorizado a reportar (empleador)]
, p.perNumeroIdentificacion as [Número de identificación del autorizado a reportar (empleador)]
, CASE p2.perTipoIdentificacion

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
	END as [Tipo de identificación del trabajador o cotizante o cabeza de familia]
, p2.perNumeroIdentificacion as [Número de identificación del trabajador o cotizante o cabeza de familia]
, concat(apg.apgPeriodoAporte,N'-01') as [Período de pago de los aportes]
--, c.catCategoriaPersona as [Categoría de afiliación en el período reportado]
,CASE WHEN (c.catCategoriaPersona NOT IN('A','B','C','D','E') OR c.catCategoriaPersona IS NULL) THEN 'N' ELSE c.catCategoriaPersona END as [Categoría de afiliación en el período reportado]
, apd.apdDiasCotizados as [Días cotizados]
-------------------- ************************ -----------------------------
--************* Inicia la unión de las tablas ----------------------------
-------------------- ************************ -----------------------------
from AporteGeneral as apg with (nolock)
inner join aporteDetallado as apd on apg.apgId = apd.apdAporteGeneral
inner join Empresa as emp with (nolock) on emp.empId = apg.apgEmpresa
inner join Persona as p with (nolock) on p.perId = emp.empPersona
left join Empleador as empl with (nolock) on empl.empEmpresa = emp.empId
inner join persona as p2 with (nolock) on apd.apdPersona = p2.perId
left join afiliado as a with (nolock) on p2.perId = a.afiPersona
left join categoria as c with (nolock) on a.afiId = c.catIdAfiliado
where apg.apgTipoSolicitante in ('INDEPENDIENTE', 'PENSIONADO')

return