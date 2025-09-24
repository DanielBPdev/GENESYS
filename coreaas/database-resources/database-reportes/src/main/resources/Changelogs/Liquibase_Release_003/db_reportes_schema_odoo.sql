--liquibase formatted sql

--changeset ggiraldo:01

CREATE SCHEMA odoo;

CREATE TABLE [odoo].[terceros](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[tipo_identificacion] [varchar](255) NULL,
	[numero_identificacion] [varchar](255) NULL,
	[razon_social] [varchar](255) NULL,
	[primer_nombre] [varchar](255) NULL,
	[segundo_nombre] [varchar](255) NULL,
	[primer_apellido] [varchar](255) NULL,
	[segundo_apellido] [varchar](255) NULL,
	[direccion_fisica] [varchar](255) NULL,
	[pais] [varchar](255) NULL,
	[departamento] [varchar](255) NULL,
	[municipio] [varchar](255) NULL,
	[codigo_municipio] [varchar](255) NULL,
	[telefono_fijo] [varchar](255) NULL,
	[telefono_celular] [varchar](255) NULL,
	[fecha_nacimiento] [varchar](255) NULL,
	[email] [varchar](255) NULL,
	[fecha_contable] [date] NULL,
	[tipo_plantilla] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY];

CREATE TABLE [odoo].[afiliaciones_empresas](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[tipo_aportante] [varchar](255) NULL,
	[fecha_recaudo_pila] [date] NULL,
	[id_aporte] [varchar](50) NULL,
	[tipo_aporte] [varchar](255) NULL,
	[identificacion_aportante] [varchar](50) NULL,
	[tipo_identificacion_aportante] [varchar](50) NULL,
	[valor_total_aporte] [varchar](50) NULL,
	[valor_aporte_sin_intereses] [varchar](50) NULL,
	[valor_intereses_aporte] [varchar](50) NULL,
	[numero_planilla] [varchar](50) NULL,
	[estado_aportante_respecto_ccf] [varchar](100) NULL,
	[fecha_afiliacion_aportante] [date] NULL,
	[estado_aporte] [varchar](100) NULL,
	[fecha_estado_aporte] [date] NULL,
	[fecha_conciliacion] [date] NULL,
	[fecha_contable] [date] NULL,
	[tipo_plantilla] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY];

CREATE TABLE [odoo].[afiliaciones_personas](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[tipo_aportante] [varchar](255) NULL,
	[fecha_recaudo_pila] [date] NULL,
	[id_aporte] [varchar](50) NULL,
	[tipo_aporte] [varchar](255) NULL,
	[identificacion_aportante] [varchar](50) NULL,
	[tipo_identificacion_aportante] [varchar](50) NULL,
	[valor_total_aporte] [varchar](50) NULL,
	[valor_aporte_sin_intereses] [varchar](50) NULL,
	[valor_intereses_aporte] [varchar](50) NULL,
	[numero_planilla] [varchar](50) NULL,
	[estado_aportante_respecto_ccf] [varchar](100) NULL,
	[fecha_afiliacion_aportante] [date] NULL,
	[estado_aporte] [varchar](100) NULL,
	[fecha_estado_aporte] [date] NULL,
	[fecha_conciliacion] [date] NULL,
	[fecha_contable] [date] NULL,
	[tipo_plantilla] [varchar](100) NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY];

CREATE TABLE [odoo].[novedades_empresas_personas](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[tipo_identificacion_tercero] [varchar](255) NULL,
	[numero_identificacion_tercero] [varchar](255) NULL,
	[direccion_tercero] [varchar](255) NULL,
	[pais_tercero] [varchar](255) NULL,
	[departamento_tercero] [varchar](255) NULL,
	[ciudad_tercero] [varchar](255) NULL,
	[telefono_tercero] [varchar](255) NULL,
	[movil_tercero] [varchar](255) NULL,
	[email_tercero] [varchar](255) NULL,
	[fecha_contable] [date] NULL,
	[tipo_plantilla] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY];


--changeset ggiraldo:02

CREATE TABLE [odoo].[aportes_recaudos_pila](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[tipo_aportante] [varchar](255) NULL,
	[fecha_recaudo_PILA] [date] NULL,
	[fecha_transferencia_bancaria_consignacion] [date] NULL,
	[id_aporte] [varchar](255) NULL,
	[tipo_identificacion_aporte] [varchar](255) NULL,
	[num_identificacion_aportante] [varchar](255) NULL,
	[tipo_identificacion_aportante] [varchar](255) NULL,
	[valor_total_aporte] [varchar](255) NULL,
	[valor_aporte_sin_intereses] [varchar](255) NULL,
	[valor_interes_aporte] [varchar](255) NULL,
	[numero_planilla] [varchar](255) NULL,
	[estado_aporte] [varchar](255) NULL,
	[estado_vigencia_aporte] [varchar](255) NULL,
	[estado_conciliacion] [varchar](255) NULL,
	[fecha_estado_aporte] [date] NULL,
	[fecha_conciliacion] [date] NULL,
	[fecha_p_conciliar] [date] NULL,
	[codigo_transaccion_modalidad_pago] [varchar](255) NULL,
	[fecha_contable] [date] NULL,
	[campo_tipo_plantilla] [varchar](255) NULL
	
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY];

CREATE TABLE [odoo].[aportes_recaudo_manual](
	
	[id] [int] IDENTITY(1,1) NOT NULL,
	[tipo_aportante] [varchar](255) NULL,  
	[fecha_recaudo_aporte_manual] [date] NULL,
	[fecha_transferencia_bancaria] [date] NULL, --preguntar, no se tiene en los actuales
	[id_aporte] [varchar](255) NULL, --[No. Operaci�n recaudo]
	[num_identificacion_aportante] [varchar](255) NULL,
	[tipo_identificacion_aportante] [varchar](255) NULL,
	[valor_total_aporte] [varchar](255) NULL,
	[valor_aporte_intereses] [varchar](255) NULL,
	[valor_interes_aporte] [varchar](255) NULL,
	[estado_aporte] [varchar](255) NULL,
    [fecha_estado_aporte] [varchar](255) NULL,
	[numero_convenio] [varchar](255) NULL,
	[numero_cuota_convenio] [varchar](255) NULL,
	[codigo_transaccion] [varchar](255) NULL,
	[fecha_contable] [date] NULL,
	[campo_tipo_plantilla] [varchar](255) NULL

PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY];

CREATE TABLE [odoo].[devolucion_aporte](
	
	[id] [int] IDENTITY(1,1) NOT NULL,
	[numero_solicitud_devolucion] [varchar](255) NULL,
	[id_aporte] [varchar](255) NULL,
	[tipo_aporte] [varchar](255) NULL,
	[numero_identificacion_aportante] [varchar](255) NULL,
	[estado_afiliacion_aportante_respecto_CCF] [varchar](255) NULL,  
	[num_identificacion_quien_devuelve_aporte] [varchar](255) NULL,  
	[valor_total_aporte] [varchar](255) NULL,
	[valor_aporte_sin_intereses] [varchar](255) NULL,  
	[valor_interes_aporte] [varchar](255) NULL,  
	[valor_devolucion] [varchar](255) NULL,  
	[saldo_aporte_disponible] [varchar](255) NULL,  
	[estado_aporte] [varchar](255) NULL,  
	[fecha_recaudo_PILA] [date] NULL, 
	[fecha_devolucion] [date] NULL, 
	[fecha_contable] [date] NULL, 
	[campo_tipo_plantilla] [varchar](255) NULL

PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY];

CREATE TABLE [odoo].[correcciones_aportes](
	
	[id] [int] IDENTITY(1,1) NOT NULL,
	[tipo_aportante] [varchar](255) NULL,
	[fecha_recaudo_PILA] [date] NULL,
	[num_identificacion_aportante] [varchar](255) NULL,
	[tipo_identificacion_aportante] [varchar](255) NULL,
	[valor_total_aporte] [varchar](255) NULL,
	[valor_aporte_sin_intereses] [varchar](255) NULL,
	[valor_interes_aporte] [varchar](255) NULL,
	[numero_planilla] [varchar](255) NULL,
	[id_aporte] [varchar](255) NULL,
	[tipo_identificacion_aporte] [varchar](255) NULL,
	[estado_aporte] [varchar](255) NULL,
	[estado_vigencia_aporte] [varchar](255) NULL,
	[fecha_estado_aporte] [date] NULL,
	[fecha_conciliacion] [date] NULL,
	[fecha_contable] [date] NULL,
	[campo_tipo_plantilla] [varchar](255) NULL

PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY];

CREATE TABLE [odoo].[prescripcion_aportes](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[fecha_procesamiento_prescripcion] [date] NULL,
	[fecha_recaudo_pila] [date] NULL,
	[tipo_identificacion_aportante] [varchar](255) NULL,
	[numero_identificacion_aportante] [varchar](255) NULL,
	[numero_solicitud_prescripcion] [varchar](255) NULL,
	[id_aporte] [varchar](255) NULL,
	[tipo_aporte] [varchar](255) NULL,
	[valor_total_aporte] [varchar](255) NULL,
	[valor_aporte_sin_intereses] [varchar](255) NULL,
	[valor_interes_aporte] [varchar](255) NULL,
	[estado_aporte] [varchar](255) NULL,
	[fecha_estado_aporte] [date] NULL,
	[estado_aportante_respecto_ccf] [varchar](255) NULL,
	[estado_vigencia_aporte] [varchar](255) NULL,
	[fecha_conciliacion] [date] NULL,
	[fecha_contable] [date] NULL,
	[campo_tipo_plantilla] [varchar](255) NULL

PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY];

CREATE TABLE [odoo].[gestion_convenios_pagos_cartera_aportes](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[numero_convenio] [varchar](255) NULL,
	[fecha_convenio] [date] NULL,
	[periodos_mora] [varchar](50) NULL,
	[tipo_identificacion_aportante_en_mora] [varchar](50) NULL,
	[identificacion_aportante_en_mora] [varchar](100) NULL,
	[numero_cuotas_convenio] [varchar](50) NULL,
	[valor_cada_cuota] [varchar](50) NULL,
	[valor_total_convenio] [varchar](50) NULL,
	[fecha_contable] [date] NULL,
	[tipo_plantilla] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY];


--changeset ggiraldo:03

CREATE TABLE [odoo].[liquidacion_pago_especifico_subsidio_monetario](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[fecha_procesamiento_liquidacion] [date] NULL,
	[fecha_liquidacion_oficial] [date] NULL,
	[fecha_dispersion_liquidacion] [date] NULL,
	[id_pago] [varchar](50) NULL,
	[periodo_regular_retroactivo_liquidacion] [varchar](100) NULL,
	[periodo_asociado_a_fecha_procesamiento_liquidacion] [varchar](100) NULL,
	[tipo_identificacion_administrador_subsidio] [varchar](100) NULL,
	[identificacion_administrador_subsidio] [varchar](100) NULL,
	[codigo_identificacion_tercero_pagador] [varchar](100) NULL,
	[modalidad_pago_administrador_subsidio] [varchar](100) NULL,
	[fecha_contable] [date] NULL,
	[campo_tipo_plantilla] [varchar](100) NULL,
	[codigo_entidad_descuento] [varchar](100) NULL,
	[nombre_entidad_descuento] [varchar](255) NULL,
	[tipo_liquidacion] [varchar](255) NULL,
	[valor_asignado] [varchar](50) NULL,
	[valor_con_descuento] [varchar](50) NULL,
	[valor_real_para_pago] [varchar](50) NULL,
	[tipo_identificacion_afiliado_principal] [varchar](100) NULL,
	[identificacion_afiliado_principal] [varchar](100) NULL,
	[cobro_judicial] [varchar](100) NULL,
	[grupo_inembargable] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY];

CREATE TABLE [odoo].[cobro_cuotas_subsidio_monetario](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[fecha_liquidacion_oficial] [date] NULL,
	[fecha_cobro] [date] NULL,
	[fecha_dispersion_liquidacion] [date] NULL,
	[periodo_asociado_fecha_cobro] [varchar](255) NULL,
	[identificacion_abono] [varchar](255) NULL,
	[codigo_identificacion_tercero_pagador] [varchar](255) NULL,
	[modalidad_pago_administrador_subsidio] [varchar](255) NULL,
	[numero_solicitud_cobro] [varchar](255) NULL,
	[tipo_identificacion_administrador_subsidio] [varchar](255) NULL,
	[numero_identificacion_administrador_subsidio] [varchar](255) NULL,
	[valor_cobrado] [varchar](255) NULL,
	[suma_valores_cobrados_ct_monetaria_discriminada_por_dia] [varchar](255) NULL,
	[fecha_contable] [date] NULL,
	[campo_tipo_plantilla] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY];

CREATE TABLE [odoo].[anulacion_subsidio_liquidado](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[fecha_procesamiento_anulacion] [date] NULL,
	[periodo_regular_retroactivo_liquidacion] [varchar](255) NULL,
	[modalidad_pago_subsidio_anulado] [varchar](255) NULL,
	[tipo_identificacion_administrador_subsidio] [varchar](255) NULL,
	[numero_identificacion_administrador_subsidio] [varchar](255) NULL,
	[tipo_beneficiario] [varchar](255) NULL,
	[tipo_identificacion_beneficiario] [varchar](255) NULL,
	[numero_identificacion_beneficiario] [varchar](255) NULL,
	[valor_anulado] [varchar](255) NULL,
	[estado_aporte] [varchar](255) NULL,
	[fecha_contable] [date] NULL,
	[campo_tipo_plantilla] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY];

CREATE TABLE [odoo].[prescripcion_subsidio_monetario](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[fecha_procesamiento_prescripcion] [date] NULL,
	[periodo_asociado_fecha_corte] [varchar](255) NULL,
	[id_registro] [varchar](255) NULL,
	[codigo_identificacion_tercero_pagador] [varchar](255) NULL,
	[modalidad_pago_subsidio_prescrito] [varchar](255) NULL,
	[tipo_identificacion_administrador_subsidio] [varchar](255) NULL,
	[numero_identificacion_administrador_subsidio] [varchar](255) NULL,
	[valor_prescrito] [varchar](255) NULL,
	[estado_aporte] [varchar](255) NULL,
	[fecha_contable] [date] NULL,
	[campo_tipo_plantilla] [varchar](100) NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY];

CREATE TABLE [odoo].[asignacion_fovis](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[resolucion_asignacion_subsidio] [varchar](255) NULL,
	[fecha_asignacion_subsidio] [date] NULL,
	[tipo_identificacion_trabajador_subsidio_asignado] [varchar](255) NULL,
	[numero_identificacion_trabajador_subsidio_asignado] [varchar](255) NULL,
	-- [valor_subsidio_asignado_trabajador] [varchar](255) NULL,
	[valor_total_subsidio_vivienda_asignado] [varchar](255) NULL,
	[tipo_localizacion_subsidio] [varchar](255) NULL,
	[fecha_contable] [date] NULL,
	[campo_tipo_plantilla] [varchar](100) NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY];

CREATE TABLE [odoo].[legalizacion_desembolso_fovis](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[fecha_desembolso_subsidio_asignado] [date] NULL,
	[nombre_proyecto] [varchar](255) NULL,
	[numero_solicitud] [varchar](255) NULL,
	[tipo_identificacion_trabajador_subsidio_asignado] [varchar](255) NULL,
	[numero_identificacion_trabajador_subsidio_asignado] [varchar](255) NULL,
	[nombre_completo_trabajador_subsidio_asignado] [varchar](255) NULL,
	[tipo_identificacion_tercero_proyecto] [varchar](255) NULL,
	[numero_identificacion_tercero_proyecto] [varchar](255) NULL,
	[valor_subsidio_asignado_por_tercero] [varchar](255) NULL,
	[tipo_localizacion_subsidio] [varchar](255) NULL,
	[fecha_contable] [date] NULL,
	[campo_tipo_plantilla] [varchar](100) NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY];

CREATE TABLE [odoo].[novedades_fovis_presencial](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[fecha_procesamiento] [date] NULL,
	[numero_solicitud] [varchar](255) NULL,
	[tipo_identificacion_trabajador] [varchar](255) NULL,
	[numero_identificacion_trabajador] [varchar](255) NULL,
	[valor_subsidio] [varchar](255) NULL,
	[tipo_movimiento] [varchar](255) NULL,
	[numero_resolucion] [varchar](255) NULL,
	[tipo_localizacion_subsidio] [varchar](255) NULL,
	[fecha_consignacion_bancaria_fecha_transferencia] [date] NULL,
	[motivo_reintegro] [varchar](255) NULL,
	[fecha_contable] [date] NULL,
	[campo_tipo_plantilla] [varchar](100) NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY];

