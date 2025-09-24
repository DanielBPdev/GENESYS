-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/07/09
-- Description:	Carga la tabla de hechos FactNovedadPersona para la ultima 
-- dimension de Periodo cargada
-- =============================================
CREATE PROCEDURE USP_REP_MERGE_FactNovedadPersona
AS
	--FactNovedadPersona
	DECLARE @FechaInicioRevision AS DATETIME
	DECLARE @FechaFinRevision AS DATETIME
	DECLARE @DimPeriodoId AS BIGINT
	DECLARE @DimPeriodoAnteriorId AS BIGINT
	DECLARE @RevisionAuditoriaId BIGINT
		
	SELECT  @FechaInicioRevision =MIN(rar.rarRevisionTimeInicio), @FechaFinRevision =MAX(rar.rarRevisionTimeFin)
	FROM RevisionAuditoriaReportes rar
	WHERE rar.rarEncolaProceso = 1


	SELECT @DimPeriodoId = dipId FROM DimPeriodo WHERE dipMes = MONTH(@FechaFinRevision) AND dipAnio = YEAR(@FechaFinRevision);
	SELECT @DimPeriodoAnteriorId = dipId FROM DimPeriodo WHERE dipMes = MONTH(DATEADD(mm,-1,@FechaFinRevision)) AND dipAnio = YEAR(DATEADD(mm,-1,@FechaFinRevision));

	CREATE TABLE #TmpNovedadPersona(
		perIdPersona bigint,
		perIdEmpleador bigint,
		perIdBeneficiario bigint,
		TipoIdentificacionPersona varchar(20) COLLATE Latin1_General_CI_AI,
		NumeroIdentificacionPersona varchar(16) COLLATE Latin1_General_CI_AI,
		TipoIdentificacionEmpleador varchar(20) COLLATE Latin1_General_CI_AI,
		NumeroIdentificacionEmpleador varchar(16) COLLATE Latin1_General_CI_AI,
		TipoIdentificacionBeneficiario varchar(20) COLLATE Latin1_General_CI_AI,
		NumeroIdentificacionBeneficiario varchar(16) COLLATE Latin1_General_CI_AI,
		CanalRecepcion varchar(21) COLLATE Latin1_General_CI_AI,
		SedeDestinatario varchar(2) COLLATE Latin1_General_CI_AI,
		EstadoNovedad varchar(9) COLLATE Latin1_General_CI_AI,
		TipoNovedad varchar(78) COLLATE Latin1_General_CI_AI,
		FechaRadicacion datetime,
		FechaModificacionSolicitud datetime,
		ResultadoProceso varchar(22) COLLATE Latin1_General_CI_AI,
		FechaModificacionNovedad datetime,
		EstadoSolicitudNovedad varchar(50) COLLATE Latin1_General_CI_AI,
		EstadoAfiliacionPersona varchar(50) COLLATE Latin1_General_CI_AI,
		TipoBeneficiario varchar(30) COLLATE Latin1_General_CI_AI,
		EstadoAfiliacionEmpleador varchar(50) COLLATE Latin1_General_CI_AI,
		EstadoAfiliacionBeneficiario varchar(50) COLLATE Latin1_General_CI_AI,
		NaturalezaPersona varchar(30) COLLATE Latin1_General_CI_AI,
		Solicitud bigint
	);

	WITH cteTipoNovedad
	AS
	(	
		SELECT TipoNovedad, TipoTransaccion 
		FROM (VALUES 
		--'Activar o Inactivar beneficiarios', 
		('Activar o Inactivar beneficiarios', 'ACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL'),
		('Activar o Inactivar beneficiarios', 'ACTIVAR_BENEFICIARIO_CONYUGE_WEB'),
		('Activar o Inactivar beneficiarios', 'ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_PRESENCIAL'),
		('Activar o Inactivar beneficiarios', 'ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_WEB'),
		('Activar o Inactivar beneficiarios', 'ACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL'),
		('Activar o Inactivar beneficiarios', 'ACTIVAR_BENEFICIARIO_HIJASTRO_WEB'),
		('Activar o Inactivar beneficiarios', 'ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL'),
		('Activar o Inactivar beneficiarios', 'ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_WEB'),
		('Activar o Inactivar beneficiarios', 'ACTIVAR_BENEFICIARIO_MADRE_PRESENCIAL'),
		('Activar o Inactivar beneficiarios', 'ACTIVAR_BENEFICIARIO_MADRE_WEB'),
		('Activar o Inactivar beneficiarios', 'ACTIVAR_BENEFICIARIO_PADRE_PRESENCIAL'),
		('Activar o Inactivar beneficiarios', 'ACTIVAR_BENEFICIARIO_PADRE_WEB'),
		('Activar o Inactivar beneficiarios', 'ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_PRESENCIAL'),
		('Activar o Inactivar beneficiarios', 'ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_WEB'),
		('Activar o Inactivar beneficiarios', 'INACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL'),
		('Activar o Inactivar beneficiarios', 'INACTIVAR_BENEFICIARIO_CONYUGE_WEB'),
		('Activar o Inactivar beneficiarios', 'INACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL'),
		('Activar o Inactivar beneficiarios', 'INACTIVAR_BENEFICIARIO_HIJASTRO_WEB'),
		('Activar o Inactivar beneficiarios', 'INACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_PRESENCIAL'),
		('Activar o Inactivar beneficiarios', 'INACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_WEB'),
		('Activar o Inactivar beneficiarios', 'INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL'),
		('Activar o Inactivar beneficiarios', 'INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_WEB'),
		('Activar o Inactivar beneficiarios', 'INACTIVAR_BENEFICIARIO_HUERFANO_PRESENCIAL'),
		('Activar o Inactivar beneficiarios', 'INACTIVAR_BENEFICIARIO_HUERFANO_WEB'),
		('Activar o Inactivar beneficiarios', 'INACTIVAR_BENEFICIO_EN_CUSTODIA_PRESENCIAL'),
		('Activar o Inactivar beneficiarios', 'INACTIVAR_BENEFICIO_EN_CUSTODIA_WEB'),
		('Activar o Inactivar beneficiarios', 'INACTIVAR_BENEFICIOS_MADRE_PRESENCIAL'),
		('Activar o Inactivar beneficiarios', 'INACTIVAR_BENEFICIOS_MADRE_WEB'),
		('Activar o Inactivar beneficiarios', 'INACTIVAR_BENEFICIOS_PADRE_PRESENCIAL'),
		('Activar o Inactivar beneficiarios', 'INACTIVAR_BENEFICIOS_PADRE_WEB'),
		--'Actualización de datos básicos', 
		('Actualización de datos básicos', 'ACTUALIZACION_CERTIFICADO_ESTUDIOS_BENEFICIARIO_EN_CUSTODIA_DEPWEB'),
		('Actualización de datos básicos', 'ACTUALIZACION_CERTIFICADO_ESTUDIOS_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL'),
		('Actualización de datos básicos', 'ACTUALIZACION_CERTIFICADO_ESTUDIOS_BENEFICIARIO_EN_CUSTODIA_WEB'),
		('Actualización de datos básicos', 'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HERMANO_HUERFANO_DEPWEB'),
		('Actualización de datos básicos', 'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HERMANO_HUERFANO_PRESENCIAL'),
		('Actualización de datos básicos', 'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HERMANO_HUERFANO_WEB'),
		('Actualización de datos básicos', 'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJASTRO_DEPWEB'),
		('Actualización de datos básicos', 'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJASTRO_PRESENCIAL'),
		('Actualización de datos básicos', 'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJASTRO_WEB'),
		('Actualización de datos básicos', 'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_ADOTIVO_DEPWEB'),
		('Actualización de datos básicos', 'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_ADOTIVO_PRESENCIAL'),
		('Actualización de datos básicos', 'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_ADOTIVO_WEB'),
		('Actualización de datos básicos', 'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_BIOLOGICO_DEPWEB'),
		('Actualización de datos básicos', 'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_BIOLOGICO_PRESENCIAL'),
		('Actualización de datos básicos', 'ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_BIOLOGICO_WEB'),
		('Actualización de datos básicos', 'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_BENEFICIARIO_EN_CUSTODIA_DEPWEB'),
		('Actualización de datos básicos', 'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL'),
		('Actualización de datos básicos', 'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_BENEFICIARIO_EN_CUSTODIA_WEB'),
		('Actualización de datos básicos', 'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HERMANO_HUERFANO_DEPWEB'),
		('Actualización de datos básicos', 'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HERMANO_HUERFANO_PRESENCIAL'),
		('Actualización de datos básicos', 'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HERMANO_HUERFANO_WEB'),
		('Actualización de datos básicos', 'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJASTRO_DEPWEB'),
		('Actualización de datos básicos', 'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJASTRO_PRESENCIAL'),
		('Actualización de datos básicos', 'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJASTRO_WEB'),
		('Actualización de datos básicos', 'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_ADOPTIVO_DEPWEB'),
		('Actualización de datos básicos', 'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_ADOPTIVO_PRESENCIAL'),
		('Actualización de datos básicos', 'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_ADOPTIVO_WEB'),
		('Actualización de datos básicos', 'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_BIOLOGICO_DEPWEB'),
		('Actualización de datos básicos', 'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_BIOLOGICO_PRESENCIAL'),
		('Actualización de datos básicos', 'ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_BIOLOGICO_WEB'),
		('Actualización de datos básicos', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS'),
		('Actualización de datos básicos', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB'),
		('Actualización de datos básicos', 'CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB'),
		('Actualización de datos básicos', 'CAMBIO_GENERO_PERSONAS'),
		('Actualización de datos básicos', 'CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS'),
		('Actualización de datos básicos', 'CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS_WEB'),
		('Actualización de datos básicos', 'ACTIVAR_DESACTIVAR_CONDICION_CABEZA_HOGAR_PERSONAS'),
		('Actualización de datos básicos', 'ACTIVAR_DESACTIVAR_CONDICION_CABEZA_HOGAR_PERSONAS_WEB'),
		('Actualización de datos básicos', 'CAMBIO_ESTADO_CIVIL_PERSONAS'),
		('Actualización de datos básicos', 'CAMBIO_ESTADO_CIVIL_PERSONAS_WEB'),
		('Actualización de datos básicos', 'ACTUALIZACION_GRADO_CURSADO_PERSONAS'),
		('Actualización de datos básicos', 'ACTUALIZACION_GRADO_CURSADO_PERSONAS_WEB'),
		--'Actualización de información laboral', 
		('Actualización de información laboral', 'CAMBIO_CLASE_TRABAJADOR_TRABAJADOR_DEPENDIENTE_PRESENCIAL'),
		('Actualización de información laboral', 'ACTUALIZAR_VALOR_TIPO_SALARIO_CONTRATO_TRABAJADOR_DEPENDIENTE_PRESENCIAL'),
		('Actualización de información laboral', 'CAMBIO_SUCURSA_TRABAJADOR_DEPENDIENTE_DEPWEB'),
		('Actualización de información laboral', 'ACTUALIZACION_VACACIONES_TRABAJADOR_DEPENDIENTE'),
		('Actualización de información laboral', 'INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_DEPWEB'),
		('Actualización de información laboral', 'INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL'),
		('Actualización de información laboral', 'INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_WEB'),
		('Actualización de información laboral', 'INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_DEPWEB'),
		('Actualización de información laboral', 'INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_PRESENCIAL'),
		('Actualización de información laboral', 'INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_WEB'),
		('Actualización de información laboral', 'SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_DEPWEB'),
		('Actualización de información laboral', 'SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PRESENCIAL'),
		('Actualización de información laboral', 'SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_PRESENCIAL'),
		('Actualización de información laboral', 'SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_WEB'),
		('Actualización de información laboral', 'LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_DEPWEB'),
		('Actualización de información laboral', 'LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PRESENCIAL'),
		('Actualización de información laboral', 'LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_WEB'),
		('Actualización de información laboral', 'VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_DEPWEB'),
		('Actualización de información laboral', 'VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_PRESENCIAL'),
		('Actualización de información laboral', 'VACACIONES_LICENCIA_REMUNERADA_VAC_INDEPENDIENTE_PRESENCIAL'),
		('Actualización de información laboral', 'VACACIONES_LICENCIA_REMUNERADA_VAC_INDEPENDIENTE_WEB'),
		('Actualización de información laboral', 'VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_DEPWEB'),
		('Actualización de información laboral', 'VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_PRESENCIAL'),
		('Actualización de información laboral', 'VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_PRESENCIAL'),
		('Actualización de información laboral', 'VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_WEB'),
		('Actualización de información laboral', 'VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_DEPWEB'),
		('Actualización de información laboral', 'VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_PRESENCIAL'),
		('Actualización de información laboral', 'VARIACION_TRANSITORIA_SALARIO_VST_INDEPENDIENTE_PRESENCIAL'),
		('Actualización de información laboral', 'VARIACION_TRANSITORIA_SALARIO_VST_INDEPENDIENTE_WEB'),
		--'Actualizar información relativa al pago de aportes para afiliados facultativos', 
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MAYOR_1_5SM_0_6_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MAYOR_1_5SM_2_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MENOR_1_5SM_0_6_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MENOR_1_5SM_2_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_PENSION_FAMILIAR_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_PENSION_FAMILIAR_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_GRUPO_FAMILIAR_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_GRUPO_FAMILIAR_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MAYOR_1_5SM_0_6_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MAYOR_1_5SM_2_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MENOR_1_5SM_0_6_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MENOR_1_5SM_2_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'CAMBIO_TIPO_PENSIONADO_PENSION_FAMILIAR_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'CAMBIO_TIPO_PENSIONADO_PENSION_FAMILIAR_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'CAMBIO_TIPO_PENSIONADO_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'CAMBIO_TIPO_PENSIONADO_PENSIONADO_MAYOR_1_5SM_0_6_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'CAMBIO_TIPO_PENSIONADO_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'CAMBIO_TIPO_PENSIONADO_PENSIONADO_MAYOR_1_5SM_2_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_0_6_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_0_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_0_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_2_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'CAMBIO_TIPO_INDEPENDIENTE_SEGUN_VALOR_APORTES_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'CAMBIO_TIPO_INDEPENDIENTE_SEGUN_VALOR_APORTES_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTIVACION_ENTIDAD_PAGADORA_APORTES_INDEPENDIENTES_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTIVACION_ENTIDAD_PAGADORA_APORTES_INDEPENDIENTES_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'CAMBIO_CLASE_DE_INDEPENDIENTE_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'CAMBIO_CLASE_DE_INDEPENDIENTE_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTIVACION_ENTIDAD_PAGADORA_APORTES_INDEPENDIENTES_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'ACTIVACION_ENTIDAD_PAGADORA_APORTES_INDEPENDIENTES_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'DESACTIVAR_ENTIDA_PAGADORA_APORTES_INDEPENDIENTES_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'DESACTIVAR_ENTIDA_PAGADORA_APORTES_INDEPENDIENTES_WEB'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'CAMBIAR_VALOR_DE_INGRESOS_MENSUALES_INDEPENDIENTE_PRESENCIAL'),
		('Actualizar información relativa al pago de aportes para afiliados facultativos', 'CAMBIAR_VALOR_DE_INGRESOS_MENSUALES_INDEPENDIENTE_WEB'),
		--'Cambio de medio de pago', 
		('Cambio de medio de pago', 'CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_DEPWEB'),
		('Cambio de medio de pago', 'CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_PRESENCIAL'),
		('Cambio de medio de pago', 'CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_WEB'),
		('Cambio de medio de pago', 'CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_DEPWEB'),
		('Cambio de medio de pago', 'CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_PRESENCIAL'),
		('Cambio de medio de pago', 'CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_WEB'),
		('Cambio de medio de pago', 'CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_ADMINISTRADOR_SUBSIDIO'),
		('Cambio de medio de pago', 'CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_ADMINISTRADOR_SUBSIDIO_WEB'),
		('Cambio de medio de pago', 'CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_TRABAJADOR_DEPENDIENTE'),
		('Cambio de medio de pago', 'CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_TRABAJADOR_DEPENDIENTE_WEB'),
		--'Fallecimiento', 
		('Fallecimiento', 'REPORTE_FALLECIMIENTO_PERSONAS'),
		('Fallecimiento', 'REPORTE_FALLECIMIENTO_PERSONAS_DEPWEB'),
		('Fallecimiento', 'REPORTE_FALLECIMIENTO_PERSONAS_WEB'),
		--'Grupo familiar', 
	
		--'Invalidez', 
		('Invalidez', 'REPORTE_INVALIDEZ_PERSONAS'),
		('Invalidez', 'REPORTE_INVALIDEZ_PERSONAS_DEPWEB'),
		('Invalidez', 'REPORTE_INVALIDEZ_PERSONAS_WEB'),
		--'Medios de pago', 
		('Medios de pago', 'ACTUALIZAR_DATOS_TARJETA_ADMINISTRADOR_SUBSIDIO'),
		('Medios de pago', 'ACTUALIZAR_DATOS_TARJETA_TRABAJADOR_DEPENDIENTE'),
		('Medios de pago', 'BLOQUE_TARJETA_ADMINISTRADOR_SUBSIDIO'),
		('Medios de pago', 'EXPEDICION_PRIMERA_VEZ_TARJETA_ADMINISTRADOR_SUBSIDIO'),
		('Medios de pago', 'EXPEDICION_PRIMERA_VEZ_TARJETA_TRABAJADOR_DEPENDIENTE'),
		('Medios de pago', 'RE_EXPEDICION_TARJETA_ADMINISTRADOR_SUBSIDIO'),
		('Medios de pago', 'RE_EXPEDICION_TARJETA_TRABAJADOR_DEPENDIENTE'),
		--'Otras novedades', 
		('Otras novedades', 'CAMBIO_FECHA_EXPEDICION_DOCUMENTO_IDENTIDAD_PERSONA_DEPWEB'),
		('Otras novedades', 'CAMBIO_FECHA_EXPEDICION_DOCUMENTO_IDENTIDAD_PERSONA_PRESENCIAL'),
		('Otras novedades', 'CAMBIO_FECHA_EXPEDICION_DOCUMENTO_IDENTIDAD_PERSONA_WEB'),
		('Otras novedades', 'RETIRO_AUTOMATICO_POR_MORA'),
		('Otras novedades', 'VENCIMIENTO_AUTOMATICO_CERTIFICADOS'),
		('Otras novedades', 'VENCIMIENTO_AUTOMATICO_INCAPACIDADES'),
		--'Pignoración del subsidio', 
		('Pignoración del subsidio', 'PIGNORACION_DEL_SUBSIDIO_TRABAJADOR_DEPENDIENTE'),
		--'Retiro y anulación de afiliación', 
		('Retiro y anulación de afiliación', 'RETIRO_PENSIONADO_25ANIOS'),
		('Retiro y anulación de afiliación', 'RETIRO_PENSIONADO_MAYOR_1_5SM_0_6'),
		('Retiro y anulación de afiliación', 'RETIRO_PENSIONADO_MAYOR_1_5SM_2'),
		('Retiro y anulación de afiliación', 'RETIRO_PENSIONADO_MENOR_1_5SM_0'),
		('Retiro y anulación de afiliación', 'RETIRO_PENSIONADO_MENOR_1_5SM_0_6'),
		('Retiro y anulación de afiliación', 'RETIRO_PENSIONADO_MENOR_1_5SM_2'),
		('Retiro y anulación de afiliación', 'RETIRO_PENSIONADO_PENSION_FAMILIAR'),
		('Retiro y anulación de afiliación', 'RETIRO_TRABAJADOR_DEPENDIENTE'),
		('Retiro y anulación de afiliación', 'RETIRO_TRABAJADOR_INDEPENDIENTE'),
		('Retiro y anulación de afiliación', 'RETIRO_AUTOMATICO_POR_MORA'),
		('Retiro y anulación de afiliación', 'RETIRO_BENEFICIARIO_CUSTODIA'),
		('Retiro y anulación de afiliación', 'RETIRO_CONYUGE'),
		('Retiro y anulación de afiliación', 'RETIRO_HERMANO_HUERFANO'),
		('Retiro y anulación de afiliación', 'RETIRO_HIJASTRO'),
		('Retiro y anulación de afiliación', 'RETIRO_HIJO_ADOPTIVO'),
		('Retiro y anulación de afiliación', 'RETIRO_HJO_BIOLOGICO'),
		('Retiro y anulación de afiliación', 'RETIRO_MADRE'),
		('Retiro y anulación de afiliación', 'RETIRO_PADRE'),
		--'Solicitud de activación/desactivación de cesión del subsidio monetario', 
		('Solicitud de activación/desactivación de cesión del subsidio monetario', 'ACTIVACION_CESION_SUBSIDIO_TRABAJADOR_DEPENDIENTE'),
		('Solicitud de activación/desactivación de cesión del subsidio monetario', 'DESACTIVACION_CESION_SUBSIDIO_TRABAJADOR_DEPENDIENTE'),
		--'Solicitud de activación/desactivación de retención del subsidio monetario', 
		('Solicitud de activación/desactivación de retención del subsidio monetario', 'DESACTIVACION_RETENCION_SUBSIDIO_TRABAJADOR_DEPENDIENTE'),	
		('Solicitud de activación/desactivación de retención del subsidio monetario', 'SOLICITUD_RETENCION_SUBSIDIO_TRABAJADOR_DEPENDIENTE')
		) param (TipoNovedad, TipoTransaccion)	
	),
	cteEstadoNovedad AS (
		SELECT *
		FROM (VALUES
		('RADICADA', 'Radicada'),
		('APROBADA', 'Aprobado'),
		('CERRADA', 'Cerrada'),
		('RECHAZADA', 'Rechazada')
		) EN(EstadoCore, EstadoNovedad)
	),
	cteNovedadPersona AS
	(
		SELECT 
			per.perId AS perIdPersona,
			perE.perId AS perIdEmpleador,
			perB.perId AS perIdBeneficiario,
			per.perTipoIdentificacion AS TipoIdentificacionPersona,
			per.perNumeroIdentificacion AS NumeroIdentificacionPersona,
			perE.perTipoIdentificacion AS TipoIdentificacionEmpleador,
			perE.perNumeroIdentificacion AS NumeroIdentificacionEmpleador,
			perB.perTipoIdentificacion AS TipoIdentificacionBeneficiario,
			perB.perNumeroIdentificacion AS NumeroIdentificacionBeneficiario,
			sol.solCanalRecepcion AS CanalRecepcion,
			sol.solSedeDestinatario AS SedeDestinatario,
			cen.EstadoNovedad,
			ctn.TipoNovedad,
			sol.solFechaRadicacion AS FechaRadicacion,
			wmrSolicitud.wmrLastRevisionDateTime AS FechaModificacionSolicitud,
			sol.solResultadoProceso AS ResultadoProceso,
			wmrSolicitudNovedad.wmrLastRevisionDateTime AS FechaModificacionNovedad,
			sno.snoEstadoSolicitud AS EstadoSolicitudNovedad,
			CASE WHEN roa.roaId IS NOT NULL THEN
				CASE roa.roaTipoAfiliado
					WHEN 'TRABAJADOR_INDEPENDIENTE' THEN (SELECT TOP 1 eaiEstadoAfiliacion FROM EstadoAfiliacionPersonaIndependiente WHERE eaiPersona = per.perId AND eaiFechaCambioEstado <= wmrSolicitud.wmrLastRevisionDateTime ORDER BY eaiId DESC)
					WHEN 'TRABAJADOR_DEPENDIENTE' THEN (SELECT TOP 1 eaeEstadoAfiliacion FROM EstadoAfiliacionPersonaEmpresa WHERE eaePersona = per.perId AND eaeEmpleador = empl.empId AND eaeFechaCambioEstado <= wmrSolicitud.wmrLastRevisionDateTime ORDER BY eaeId DESC)
					WHEN 'PENSIONADO' THEN (SELECT TOP 1 eapEstadoAfiliacion FROM EstadoAfiliacionPersonaPensionado WHERE eapPersona = per.perId AND eapFechaCambioEstado <= wmrSolicitud.wmrLastRevisionDateTime ORDER BY eapId DESC)
				END
				ELSE (SELECT TOP 1 eacEstadoAfiliacion FROM EstadoAfiliacionPersonaCaja WHERE eacPersona = per.perId AND eacFechaCambioEstado <= wmrSolicitud.wmrLastRevisionDateTime ORDER BY eacId DESC)
			END AS EstadoAfiliacionPersona,
			ben.benTipoBeneficiario AS TipoBeneficiario,
			CASE WHEN perE.perId IS NOT NULL THEN (SELECT TOP 1 eecEstadoAfiliacion FROM EstadoAfiliacionEmpleadorCaja WHERE eecPersona = perE.perId AND eecFechaCambioEstado <= wmrSolicitud.wmrLastRevisionDateTime ORDER BY eecId DESC) END AS EstadoAfiliacionEmpleador,
			CASE WHEN perB.perId IS NOT NULL THEN (SELECT TOP 1 eabEstadoAfiliacion FROM EstadoAfiliacionBeneficiario WHERE eabPersona = perB.perId AND eabFechaCambioEstado <= wmrSolicitud.wmrLastRevisionDateTime ORDER BY eabId DESC) END AS EstadoAfiliacionBeneficiario,
			roa.roaTipoAfiliado AS NaturalezaPersona,
			sol.solId AS Solicitud
		FROM Solicitud sol
		INNER JOIN SolicitudNovedad sno ON sol.solId = sno.snoSolicitudGlobal
		INNER JOIN SolicitudNovedadPersona snp ON sno.snoId = snp.snpSolicitudNovedad
		INNER JOIN Persona per ON snp.snpPersona = per.perId
		INNER JOIN (
					SELECT wmrKeyRowValue, MAX(wmrId) wmrId
					FROM WaterMarkedRows
					WHERE 1=1 
					AND wmrTable = 'Solicitud'
					GROUP BY wmrKeyRowValue) wmrS ON wmrS.wmrKeyRowValue = sol.solId
		INNER JOIN WaterMarkedRows wmrSolicitud ON wmrSolicitud.wmrId = wmrS.wmrId AND sol.solId = wmrSolicitud.wmrKeyRowValue		
		INNER JOIN (
					SELECT wmrKeyRowValue, MAX(wmrId) wmrId
					FROM WaterMarkedRows
					WHERE 1=1 
					AND wmrTable = 'SolicitudNovedad'
					GROUP BY wmrKeyRowValue) wmrSN ON wmrSN.wmrKeyRowValue = sno.snoId
		INNER JOIN WaterMarkedRows wmrSolicitudNovedad ON wmrSolicitudNovedad.wmrId = wmrSN.wmrId AND sno.snoId = wmrSolicitudNovedad.wmrKeyRowValue		
		LEFT JOIN cteTipoNovedad ctn ON ctn.TipoTransaccion = sol.solTipoTransaccion
		LEFT JOIN cteEstadoNovedad cen ON cen.EstadoCore = ISNULL(solResultadoProceso, snoEstadoSolicitud)
		LEFT JOIN (
			RolAfiliado roa
			LEFT JOIN (
				Empleador empl 
				INNER JOIN Empresa emp ON empl.empEmpresa = emp.empId
				INNER JOIN Persona perE ON emp.empPersona = perE.perId
				) ON roa.roaEmpleador = empl.empId
		) ON snp.snpRolAfiliado = roa.roaId
		LEFT JOIN (
			Beneficiario ben
			INNER JOIN Persona perB ON ben.benPersona = perB.perId
		) ON snp.snpBeneficiario = ben.benId		
		WHERE 
		EXISTS (SELECT 1 FROM WaterMarkedRows wmr WHERE wmr.wmrTable = 'Solicitud' AND wmr.wmrKeyRowValue = sol.solId AND wmr.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision)
		OR EXISTS (SELECT 1 FROM WaterMarkedRows wmr WHERE wmr.wmrTable = 'SolicitudNovedad' AND wmr.wmrKeyRowValue = sno.snoId AND wmr.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision)
		OR EXISTS (SELECT 1 FROM WaterMarkedRows wmr WHERE wmr.wmrTable = 'SolicitudNovedadPersona' AND wmr.wmrKeyRowValue = snp.snpId AND wmr.wmrLastRevisionDateTime BETWEEN @FechaInicioRevision AND @FechaFinRevision)
	)
	
	INSERT INTO #TmpNovedadPersona (perIdPersona,perIdEmpleador,perIdBeneficiario,TipoIdentificacionPersona,NumeroIdentificacionPersona,TipoIdentificacionEmpleador,NumeroIdentificacionEmpleador,TipoIdentificacionBeneficiario,NumeroIdentificacionBeneficiario,CanalRecepcion,SedeDestinatario,EstadoNovedad,TipoNovedad,FechaRadicacion,FechaModificacionSolicitud,ResultadoProceso,FechaModificacionNovedad,EstadoSolicitudNovedad,EstadoAfiliacionPersona,TipoBeneficiario,EstadoAfiliacionEmpleador,EstadoAfiliacionBeneficiario,NaturalezaPersona,Solicitud)
	SELECT perIdPersona,perIdEmpleador,perIdBeneficiario,TipoIdentificacionPersona,NumeroIdentificacionPersona,TipoIdentificacionEmpleador,NumeroIdentificacionEmpleador,TipoIdentificacionBeneficiario,NumeroIdentificacionBeneficiario,CanalRecepcion,SedeDestinatario,EstadoNovedad,TipoNovedad,FechaRadicacion,FechaModificacionSolicitud,ResultadoProceso,FechaModificacionNovedad,EstadoSolicitudNovedad,EstadoAfiliacionPersona,TipoBeneficiario,EstadoAfiliacionEmpleador,EstadoAfiliacionBeneficiario,NaturalezaPersona,Solicitud 	
	FROM cteNovedadPersona;

	WITH cteDimPersona AS
	(
		SELECT DISTINCT
			per.perTipoIdentificacion AS TipoIdentificacion, 
			per.perNumeroIdentificacion AS NumeroIdentificacion, 
			per.perPrimerNombre AS PrimerNombre,
			per.perSegundoNombre AS SegundoNombre,
			per.perPrimerApellido AS PrimerApellido,
			per.perSegundoApellido AS SegundoApellido
		FROM #TmpNovedadPersona tmp
		INNER JOIN Persona per ON tmp.perIdPersona = per.perId
	)

	MERGE DimPersona AS T
	USING cteDimPersona AS S
	ON (	T.dpeTipoIdentificacion = S.TipoIdentificacion AND
			T.dpeNumeroIdentificacion = S.NumeroIdentificacion )
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (dpeTipoIdentificacion,dpeNumeroIdentificacion,dpePrimerNombre,dpeSegundoNombre,dpePrimerApellido,dpeSegundoApellido)
			VALUES (S.TipoIdentificacion,S.NumeroIdentificacion,S.PrimerNombre,S.SegundoNombre,S.PrimerApellido,S.SegundoApellido)
	WHEN MATCHED
		THEN UPDATE SET dpePrimerNombre = S.PrimerNombre, dpeSegundoNombre = S.SegundoNombre, dpePrimerApellido = S.PrimerApellido, dpeSegundoApellido = S.SegundoApellido;

	WITH cteDimEmpleador AS
	(
		SELECT DISTINCT
			per.perTipoIdentificacion AS TipoIdentificacion, 
			per.perNumeroIdentificacion AS NumeroIdentificacion, 
			per.perPrimerNombre AS PrimerNombre,
			per.perSegundoNombre AS SegundoNombre,
			per.perPrimerApellido AS PrimerApellido,
			per.perSegundoApellido AS SegundoApellido,
			per.perDigitoVerificacion AS DigitoVerificacion,
			per.perRazonsocial AS RazonSocial
		FROM #TmpNovedadPersona tmp
		INNER JOIN Persona per ON tmp.perIdEmpleador = per.perId
	)

	MERGE DimEmpleador AS T
	USING cteDimEmpleador AS S
	ON (	T.demTipoIdentificacion = S.TipoIdentificacion AND
			T.demNumeroIdentificacion = S.NumeroIdentificacion )
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (demTipoIdentificacion,demNumeroIdentificacion,demDigitoVerificacion,demRazonSocial,demPrimerNombre,demSegundoNombre,demPrimerApellido,demSegundoApellido)
			VALUES (S.TipoIdentificacion,S.NumeroIdentificacion,S.DigitoVerificacion,S.RazonSocial,S.PrimerNombre,S.SegundoNombre,S.PrimerApellido,S.SegundoApellido)
	WHEN MATCHED
		THEN UPDATE SET demDigitoVerificacion = S.DigitoVerificacion, demRazonSocial = S.RazonSocial,demPrimerNombre = S.PrimerNombre, demSegundoNombre = S.SegundoNombre, demPrimerApellido = S.PrimerApellido, demSegundoApellido = S.SegundoApellido;

	WITH cteDimBeneficiario AS
	(
		SELECT DISTINCT
			per.perTipoIdentificacion AS TipoIdentificacion, 
			per.perNumeroIdentificacion AS NumeroIdentificacion, 
			per.perPrimerNombre AS PrimerNombre,
			per.perSegundoNombre AS SegundoNombre,
			per.perPrimerApellido AS PrimerApellido,
			per.perSegundoApellido AS SegundoApellido
		FROM #TmpNovedadPersona tmp
		INNER JOIN Persona per ON tmp.perIdBeneficiario = per.perId
	)

	MERGE DimBeneficiario AS T
	USING cteDimBeneficiario AS S
	ON (	T.dbeTipoIdentificacion = S.TipoIdentificacion AND
			T.dbeNumeroIdentificacion = S.NumeroIdentificacion )
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (dbeTipoIdentificacion,dbeNumeroIdentificacion,dbePrimerNombre,dbeSegundoNombre,dbePrimerApellido,dbeSegundoApellido)
			VALUES (S.TipoIdentificacion,S.NumeroIdentificacion,S.PrimerNombre,S.SegundoNombre,S.PrimerApellido,S.SegundoApellido)
	WHEN MATCHED
		THEN UPDATE SET dbePrimerNombre = S.PrimerNombre, dbeSegundoNombre = S.SegundoNombre, dbePrimerApellido = S.PrimerApellido, dbeSegundoApellido = S.SegundoApellido;


		
	WITH cteHomologEstados AS
	(
		SELECT *
		FROM (
		VALUES
			(NULL,4),
			('ACTIVO',1),
			('INACTIVO', 2),
			('NO_FORMALIZADO_RETIRADO_CON_APORTES',3),
			('NO_FORMALIZADO_SIN_AFILIACION_CON_APORTES',3),
			('NO_FORMALIZADO_CON_INFORMACION',3)
		) h (estadoCore, depId)
	),
	
	cteHomologNaturalezaPersona AS
	(
		SELECT *
		FROM (
		VALUES
			('TRABAJADOR_DEPENDIENTE',1),
			('TRABAJADOR_INDEPENDIENTE', 2),
			('PENSIONADO',3)
		) h (naturalezaPersona, dnpId)
	),

	cteHomologTipoBeneficiario AS
	(
		SELECT *
		FROM (
		VALUES
			('BENEFICIARIO_EN_CUSTODIA',5),
			('HERMANO_HUERFANO_DE_PADRES', 3),
			('HIJASTRO', 4),
			('HIJO_ADOPTIVO', 4),
			('HIJO_BIOLOGICO', 4),
			('MADRE', 2),
			('PADRE', 2),
			('CONYUGE',1)
		) h (tipoBeneficiario, dtbId)
	),

	cteFactNovedadPersona AS
	(
		SELECT 
			dpe.dpeId AS fnpDimPersona,
			dem.demId AS fnpDimEmpleador,
			dbe.dbeId AS fnpDimBeneficiario,
			@DimPeriodoId AS fnpDimPeriodo,
			dic.dicId AS fnpDimCanal,
			CAST(t.SedeDestinatario AS INT) AS fnpDimSede,
			den.denId AS fnpDimEstadoNovedad,
			dno.dnoId AS fnpDimTipoNovedadPersona,						
			CASE WHEN t.FechaRadicacion IS NOT NULL AND t.ResultadoProceso IN ('APROBADA','RECHAZADA') THEN
				CASE DATEDIFF(HH,FechaRadicacion, t.FechaModificacionSolicitud) / 24
				WHEN 0 THEN 1
				WHEN 1 THEN 2
				WHEN 2 THEN 3
				ELSE 4
				END		
			END	AS fnpDimRangoTiempoRespuestaGestion,
			CASE WHEN t.FechaRadicacion IS NOT NULL AND t.EstadoSolicitudNovedad = 'CERRADA' THEN
				CASE DATEDIFF(HH,FechaRadicacion, t.FechaModificacionNovedad) / 24
				WHEN 0 THEN 1
				WHEN 1 THEN 2
				WHEN 2 THEN 3
				ELSE 4
				END		
			END	AS fnpDimRangoTiempoRespuestaNovedad,
			(SELECT cte.depId FROM cteHomologEstados cte WHERE cte.estadoCore = t.EstadoAfiliacionPersona) AS fnpDimEstadoPersona,
			(SELECT cte.dtbId FROM cteHomologTipoBeneficiario cte WHERE cte.tipoBeneficiario = t.TipoBeneficiario) AS fnpDimTipoBeneficiario,
			(SELECT cte.depId FROM cteHomologEstados cte WHERE cte.estadoCore = t.EstadoAfiliacionEmpleador) AS fnpDimEstadoEmpleador,
			(SELECT cte.depId FROM cteHomologEstados cte WHERE cte.estadoCore = t.EstadoAfiliacionBeneficiario) AS fnpDimEstadoBeneficiario,
			(SELECT cte.dnpId FROM cteHomologNaturalezaPersona cte WHERE cte.naturalezaPersona = t.NaturalezaPersona) AS fnpDimNaturalezaPersona,
			t.Solicitud AS fnpSolicitud
		FROM #TmpNovedadPersona t	
		INNER JOIN DimPersona dpe ON t.TipoIdentificacionPersona = dpe.dpeTipoIdentificacion AND t.NumeroIdentificacionPersona = dpe.dpeNumeroIdentificacion
		LEFT JOIN DimEmpleador dem ON t.TipoIdentificacionEmpleador = dem.demTipoIdentificacion AND t.NumeroIdentificacionEmpleador = dem.demNumeroIdentificacion
		LEFT JOIN DimBeneficiario dbe ON t.TipoIdentificacionBeneficiario = dbe.dbeTipoIdentificacion AND t.NumeroIdentificacionBeneficiario = dbe.dbeNumeroIdentificacion
		LEFT JOIN DimCanal dic ON t.CanalRecepcion = dic.dicDescripcion
		LEFT JOIN DimEstadoNovedad den ON t.EstadoNovedad = den.denDescripcion
		LEFT JOIN DimTipoNovedadPersona dno ON t.TipoNovedad = dno.dnoDescripcion
	), cteFactNovedadPersonaD AS (SELECT DISTINCT * FROM cteFactNovedadPersona)
	
	MERGE FactNovedadPersona AS T
	USING cteFactNovedadPersonaD AS S
	ON (T.fnpSolicitud = S.fnpSolicitud AND
		T.fnpDimPeriodo = S.fnpDimPeriodo AND
		T.fnpDimPersona = S.fnpDimPersona AND
		T.fnpDimEmpleador = S.fnpDimEmpleador AND
		T.fnpDimBeneficiario = S.fnpDimBeneficiario)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT (fnpDimPersona,fnpDimEmpleador,fnpDimBeneficiario,fnpDimPeriodo,fnpDimCanal,fnpDimSede,fnpDimEstadoNovedad,fnpDimTipoNovedadPersona,fnpDimRangoTiempoRespuestaNovedad,fnpDimRangoTiempoRespuestaGestion,fnpDimEstadoPersona,fnpDimTipoBeneficiario,fnpDimEstadoEmpleador,fnpDimEstadoBeneficiario,fnpDimNaturalezaPersona,fnpSolicitud)
		VALUES (S.fnpDimPersona,S.fnpDimEmpleador,S.fnpDimBeneficiario,S.fnpDimPeriodo,S.fnpDimCanal,S.fnpDimSede,S.fnpDimEstadoNovedad,S.fnpDimTipoNovedadPersona,S.fnpDimRangoTiempoRespuestaNovedad,S.fnpDimRangoTiempoRespuestaGestion,S.fnpDimEstadoPersona,S.fnpDimTipoBeneficiario,S.fnpDimEstadoEmpleador,S.fnpDimEstadoBeneficiario,S.fnpDimNaturalezaPersona,S.fnpSolicitud)
	WHEN MATCHED
		THEN UPDATE SET T.fnpDimEstadoNovedad = S.fnpDimEstadoNovedad, T.fnpDimRangoTiempoRespuestaNovedad = S.fnpDimRangoTiempoRespuestaNovedad, T.fnpDimRangoTiempoRespuestaGestion = S.fnpDimRangoTiempoRespuestaGestion,T.fnpDimEstadoPersona = S.fnpDimEstadoPersona,T.fnpDimEstadoEmpleador = S.fnpDimEstadoEmpleador,T.fnpDimEstadoBeneficiario = S.fnpDimEstadoBeneficiario	
		
;