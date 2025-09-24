--liquibase formatted sql

--changeset jroa:01
--comment: Se llena la tabla ParametrizacionTablaAuditable 
INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'AFP', 'com.asopagos.entidades.transversal.personas.AFP')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ARL', 'com.asopagos.entidades.transversal.personas.ARL')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'AccionCobro1C', 'com.asopagos.entidades.ccf.cartera.AccionCobro1C')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'AccionCobro1D1E', 'com.asopagos.entidades.ccf.cartera.AccionCobro1D1E')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'AccionCobro1F', 'com.asopagos.entidades.ccf.cartera.AccionCobro1F')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'AccionCobro2C', 'com.asopagos.entidades.ccf.cartera.AccionCobro2C')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'AccionCobro2D', 'com.asopagos.entidades.ccf.cartera.AccionCobro2D')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'AccionCobro2E', 'com.asopagos.entidades.ccf.cartera.AccionCobro2E')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'AccionCobro2F2G', 'com.asopagos.entidades.ccf.cartera.AccionCobro2F2G')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'AccionCobro2H', 'com.asopagos.entidades.ccf.cartera.AccionCobro2H')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'AccionCobroA', 'com.asopagos.entidades.ccf.cartera.AccionCobroA')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'AccionCobroB', 'com.asopagos.entidades.ccf.cartera.AccionCobroB')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ActividadCartera', 'com.asopagos.entidades.ccf.cartera.ActividadCartera')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ActividadDocumento', 'com.asopagos.entidades.ccf.cartera.ActividadDocumento')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'AdminSubsidioGrupo', 'com.asopagos.entidades.ccf.personas.AdminSubsidioGrupo')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'AdministradorSubsidio', 'com.asopagos.entidades.ccf.personas.AdministradorSubsidio')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'Afiliado', 'com.asopagos.entidades.ccf.personas.Afiliado')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'AgendaCartera', 'com.asopagos.entidades.ccf.cartera.AgendaCartera')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'AporteDetallado', 'com.asopagos.entidades.ccf.aportes.AporteDetallado')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'AporteGeneral', 'com.asopagos.entidades.ccf.aportes.AporteGeneral')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'AreaCajaCompensacion', 'com.asopagos.entidades.ccf.core.AreaCajaCompensacion')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'AsesorResponsableEmpleador', 'com.asopagos.entidades.ccf.afiliaciones.AsesorResponsableEmpleador')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'Banco', 'com.asopagos.entidades.ccf.core.Banco')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'Beneficiario', 'com.asopagos.entidades.ccf.personas.Beneficiario')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'BeneficiarioDetalle', 'com.asopagos.entidades.ccf.personas.BeneficiarioDetalle')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'Beneficio', 'com.asopagos.entidades.ccf.core.Beneficio')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'BeneficioEmpleador', 'com.asopagos.entidades.ccf.core.BeneficioEmpleador')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'BitacoraCartera', 'com.asopagos.entidades.ccf.cartera.BitacoraCartera')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'CajaCompensacion', 'com.asopagos.entidades.transversal.core.CajaCompensacion')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'CajaCorrespondencia', 'com.asopagos.entidades.ccf.core.CajaCorrespondencia')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'CargueArchivoActualizacion', 'com.asopagos.entidades.ccf.novedades.CargueArchivoActualizacion')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'CargueMultiple', 'com.asopagos.entidades.ccf.afiliaciones.CargueMultiple')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'CargueMultipleSupervivencia', 'com.asopagos.entidades.ccf.novedades.CargueMultipleSupervivencia')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'Cartera', 'com.asopagos.entidades.ccf.cartera.Cartera')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'CarteraAgrupadora', 'com.asopagos.entidades.ccf.cartera.CarteraAgrupadora')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'CarteraDependiente', 'com.asopagos.entidades.ccf.cartera.CarteraDependiente')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'Categoria', 'com.asopagos.entidades.ccf.personas.Categoria')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'Certificado', 'com.asopagos.entidades.ccf.personas.Certificado')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'CertificadoEscolarBeneficiario', 'com.asopagos.entidades.ccf.personas.CertificadoEscolarBeneficiario')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'CicloAportante', 'com.asopagos.entidades.ccf.cartera.CicloAportante')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'CicloCartera', 'com.asopagos.entidades.ccf.cartera.CicloCartera')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'CodigoCIIU', 'com.asopagos.entidades.transversal.core.CodigoCIIU')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'Comunicado', 'com.asopagos.entidades.ccf.comunicados.Comunicado')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'CondicionEspecialPersona', 'com.asopagos.entidades.ccf.personas.CondicionEspecialPersona')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'CondicionInvalidez', 'com.asopagos.entidades.ccf.personas.CondicionInvalidez')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ConexionOperadorInformacion', 'com.asopagos.entidades.ccf.general.ConexionOperadorInformacion')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ConsolaEstadoCargueMasivo', 'com.asopagos.entidades.transversal.core.ConsolaEstadoCargueMasivo')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'Constante', 'com.asopagos.entidades.ccf.general.Constante')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ConvenioPago', 'com.asopagos.entidades.ccf.cartera.ConvenioPago')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ConvenioPagoDependiente', 'com.asopagos.entidades.ccf.cartera.ConvenioPagoDependiente')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'Correccion', 'com.asopagos.entidades.ccf.aportes.Correccion')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'CuentaAdministradorSubsidio', 'com.asopagos.entidades.subsidiomonetario.pagos.CuentaAdministradorSubsidio')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'Departamento', 'com.asopagos.entidades.transversal.core.Departamento')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'DesafiliacionAportante', 'com.asopagos.entidades.ccf.cartera.DesafiliacionAportante')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'DescuentoInteresMora', 'com.asopagos.entidades.ccf.aportes.DescuentoInteresMora')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'DestinatarioComunicado', 'com.asopagos.entidades.ccf.comunicados.DestinatarioComunicado')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'DestinatarioGrupo', 'com.asopagos.entidades.ccf.comunicados.DestinatarioGrupo')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'DetalleSolicitudGestionCobro', 'com.asopagos.entidades.ccf.cartera.DetalleSolicitudGestionCobro')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'DetalleSubsidioAsignado', 'com.asopagos.entidades.subsidiomonetario.pagos.DetalleSubsidioAsignado')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'DevolucionAporte', 'com.asopagos.entidades.ccf.aportes.DevolucionAporte')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'DevolucionAporteDetalle', 'com.asopagos.entidades.ccf.aportes.DevolucionAporteDetalle')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'DiasFestivos', 'com.asopagos.entidades.transversal.core.DiasFestivos')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'DiferenciasCargueActualizacion', 'com.asopagos.entidades.ccf.novedades.DiferenciasCargueActualizacion')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'DocumentoAdministracionEstadoSolicitud', 'com.asopagos.entidades.ccf.general.DocumentoAdministracionEstadoSolicitud')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'DocumentoBitacora', 'com.asopagos.entidades.ccf.cartera.DocumentoBitacora')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'DocumentoCartera', 'com.asopagos.entidades.ccf.cartera.DocumentoCartera')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'DocumentoDesafiliacion', 'com.asopagos.entidades.ccf.cartera.DocumentoDesafiliacion')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'DocumentoEntidadPagadora', 'com.asopagos.entidades.ccf.afiliaciones.DocumentoEntidadPagadora')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'DocumentoSoporte', 'com.asopagos.entidades.ccf.core.DocumentoSoporte')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'EjecucionProcesoAsincrono', 'com.asopagos.entidades.ccf.general.EjecucionProcesoAsincrono')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'EjecucionProgramada', 'com.asopagos.entidades.ccf.general.EjecucionProgramada')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ElementoDireccion', 'com.asopagos.entidades.ccf.core.ElementoDireccion')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'Empleador', 'com.asopagos.entidades.ccf.personas.Empleador')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'Empresa', 'com.asopagos.entidades.ccf.personas.Empresa')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'EntidadPagadora', 'com.asopagos.entidades.ccf.personas.EntidadPagadora')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'EscalamientoSolicitud', 'com.asopagos.entidades.ccf.afiliaciones.EscalamientoSolicitud')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'EtiquetaCorrespondenciaRadicado', 'com.asopagos.entidades.ccf.core.EtiquetaCorrespondenciaRadicado')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ExpulsionSubsanada', 'com.asopagos.entidades.ccf.personas.ExpulsionSubsanada')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'GestionNotiNoEnviadas', 'com.asopagos.entidades.ccf.aportes.ActualizacionDatosEmpleador')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'GradoAcademico', 'com.asopagos.entidades.transversal.personas.GradoAcademico')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'GrupoFamiliar', 'com.asopagos.entidades.ccf.personas.GrupoFamiliar')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'GrupoPrioridad', 'com.asopagos.entidades.ccf.comunicados.GrupoPrioridad')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'GrupoRequisito', 'com.asopagos.entidades.ccf.core.GrupoRequisito')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'InformacionFaltanteAportante', 'com.asopagos.entidades.ccf.aportes.InformacionFaltanteAportante')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'IntentoAfiliRequisito', 'com.asopagos.entidades.ccf.afiliaciones.IntentoAfiliacionRequisito')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'IntentoAfiliacion', 'com.asopagos.entidades.ccf.afiliaciones.IntentoAfiliacion')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'IntentoNoveRequisito', 'com.asopagos.entidades.ccf.novedades.IntentoNovedadRequisito')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'IntentoNovedad', 'com.asopagos.entidades.ccf.novedades.IntentoNovedad')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ItemChequeo', 'com.asopagos.entidades.ccf.afiliaciones.ItemChequeo')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'LineaCobro', 'com.asopagos.entidades.ccf.cartera.LineaCobro')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ListaEspecialRevision', 'com.asopagos.entidades.transversal.personas.ListaEspecialRevision')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'MedioCheque', 'com.asopagos.entidades.ccf.personas.MedioCheque')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'MedioConsignacion', 'com.asopagos.entidades.ccf.personas.MedioConsignacion')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'MedioDePago', 'com.asopagos.entidades.ccf.personas.MedioDePago')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'MedioEfectivo', 'com.asopagos.entidades.ccf.personas.MedioEfectivo')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'MedioPagoPersona', 'com.asopagos.entidades.ccf.personas.MedioPagoPersona')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'MedioTarjeta', 'com.asopagos.entidades.ccf.personas.MedioTarjeta')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'MedioTransferencia', 'com.asopagos.entidades.ccf.personas.MedioTransferencia')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'MediosPagoCCF', 'com.asopagos.entidades.ccf.personas.MediosPagoCCF')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'MovimientoAporte', 'com.asopagos.entidades.ccf.aportes.MovimientoAporte')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'Municipio', 'com.asopagos.entidades.transversal.core.Municipio')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'NotificacionDestinatario', 'com.asopagos.entidades.transversal.notificaciones.NotificacionDestinatario')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'NotificacionEnviada', 'com.asopagos.entidades.transversal.notificaciones.NotificacionEnviada')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'NotificacionPersonal', 'com.asopagos.entidades.ccf.cartera.NotificacionPersonal')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'NotificacionPersonalDocumento', 'com.asopagos.entidades.ccf.cartera.NotificacionPersonalDocumento')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'NovedadDetalle', 'com.asopagos.entidades.ccf.personas.NovedadDetalle')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'OcupacionProfesion', 'com.asopagos.entidades.transversal.personas.OcupacionProfesion')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'OperadorInformacion', 'com.asopagos.entidades.ccf.core.OperadorInformacion')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'OperadorInformacionCcf', 'com.asopagos.entidades.ccf.core.OperadorInformacionCcf')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'PagoPeriodoConvenio', 'com.asopagos.entidades.ccf.cartera.PagoPeriodoConvenio')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ParametrizaEnvioComunicado', 'com.asopagos.entidades.ccf.comunicados.ParametrizacionEnvioComunicado')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ParametrizacionCartera', 'com.asopagos.entidades.ccf.cartera.ParametrizacionCartera')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ParametrizacionConveniosPago', 'com.asopagos.entidades.ccf.cartera.ParametrizacionConveniosPago')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ParametrizacionCriterioGestionCobro', 'com.asopagos.entidades.ccf.cartera.ParametrizacionCriterioGestionCobro')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ParametrizacionDesafiliacion', 'com.asopagos.entidades.ccf.cartera.ParametrizacionDesafiliacion')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ParametrizacionEjecucionProgramada', 'com.asopagos.entidades.ccf.general.ParametrizacionEjecucionProgramada')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ParametrizacionExclusiones', 'com.asopagos.entidades.ccf.cartera.ParametrizacionExclusiones')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ParametrizacionFiscalizacion', 'com.asopagos.entidades.ccf.cartera.ParametrizacionFiscalizacion')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ParametrizacionGestionCobro', 'com.asopagos.entidades.ccf.cartera.ParametrizacionGestionCobro')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ParametrizacionMetodoAsignacion', 'com.asopagos.entidades.ccf.general.ParametrizacionMetodoAsignacion')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ParametrizacionNovedad', 'com.asopagos.entidades.ccf.core.ParametrizacionNovedad')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ParametrizacionPreventiva', 'com.asopagos.entidades.ccf.cartera.ParametrizacionPreventiva')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'Parametro', 'com.asopagos.entidades.ccf.general.Parametro')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'PeriodoBeneficio', 'com.asopagos.entidades.ccf.core.PeriodoBeneficio')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'Persona', 'com.asopagos.entidades.ccf.personas.Persona')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'PersonaDetalle', 'com.asopagos.entidades.ccf.personas.PersonaDetalle')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'PlantillaComunicado', 'com.asopagos.entidades.ccf.comunicados.PlantillaComunicado')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'PrioridadDestinatario', 'com.asopagos.entidades.ccf.comunicados.PrioridadDestinatario')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ProductoNoConforme', 'com.asopagos.entidades.ccf.afiliaciones.ProductoNoConforme')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'RegistroEstadoAporte', 'com.asopagos.entidades.ccf.aportes.RegistroEstadoAporte')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'RegistroNovedadFutura', 'com.asopagos.entidades.ccf.novedades.RegistroNovedadFutura')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'RegistroPersonaInconsistente', 'com.asopagos.entidades.ccf.novedades.RegistroPersonaInconsistente')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'RelacionGrupoFamiliar', 'com.asopagos.entidades.ccf.personas.RelacionGrupoFamiliar')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'Requisito', 'com.asopagos.entidades.transversal.core.Requisito')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'RequisitoCajaClasificacion', 'com.asopagos.entidades.transversal.core.RequisitoCajaClasificacion')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ResultadoEjecucionProgramada', 'com.asopagos.entidades.ccf.general.ResultadoEjecucionProgramada')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'RolAfiliado', 'com.asopagos.entidades.ccf.personas.RolAfiliado')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'RolContactoEmpleador', 'com.asopagos.entidades.ccf.personas.RolContactoEmpleador')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SedeCajaCompensacion', 'com.asopagos.entidades.ccf.core.SedeCajaCompensacion')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SocioEmpleador', 'com.asopagos.entidades.ccf.personas.SocioEmpleador')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'Solicitud', 'com.asopagos.entidades.ccf.general.Solicitud')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SolicitudAfiliaciEmpleador', 'com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SolicitudAfiliacionPersona', 'com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionPersona')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SolicitudAporte', 'com.asopagos.entidades.ccf.aportes.SolicitudAporte')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SolicitudAsociacionPersonaEntidadPagadora', 'com.asopagos.entidades.ccf.afiliaciones.SolicitudAsociacionPersonaEntidadPagadora')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SolicitudCierreRecaudo', 'com.asopagos.entidades.ccf.aportes.SolicitudCierreRecaudo')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SolicitudCorreccionAporte', 'com.asopagos.entidades.ccf.aportes.SolicitudCorreccionAporte')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SolicitudDesafiliacion', 'com.asopagos.entidades.ccf.cartera.SolicitudDesafiliacion')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SolicitudDevolucionAporte', 'com.asopagos.entidades.ccf.aportes.SolicitudDevolucionAporte')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SolicitudFiscalizacion', 'com.asopagos.entidades.ccf.cartera.SolicitudFiscalizacion')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SolicitudGestionCobroElectronico', 'com.asopagos.entidades.ccf.cartera.SolicitudGestionCobroElectronico')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SolicitudGestionCobroFisico', 'com.asopagos.entidades.ccf.cartera.SolicitudGestionCobroFisico')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SolicitudGestionCobroManual', 'com.asopagos.entidades.ccf.cartera.SolicitudGestionCobroManual')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SolicitudNovedad', 'com.asopagos.entidades.ccf.novedades.SolicitudNovedad')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SolicitudNovedadEmpleador', 'com.asopagos.entidades.ccf.novedades.SolicitudNovedadEmpleador')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SolicitudNovedadPersona', 'com.asopagos.entidades.ccf.novedades.SolicitudNovedadPersona')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SolicitudNovedadPila', 'com.asopagos.entidades.ccf.novedades.SolicitudNovedadPila')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SolicitudPreventiva', 'com.asopagos.entidades.ccf.cartera.SolicitudPreventiva')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SolicitudPreventivaAgrupadora', 'com.asopagos.entidades.ccf.cartera.SolicitudPreventivaAgrupadora')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SucursaRolContactEmpleador', 'com.asopagos.entidades.ccf.core.SucursalRolContactoEmpleador')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'SucursalEmpresa', 'com.asopagos.entidades.ccf.core.SucursalEmpresa')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'Tarjeta', 'com.asopagos.entidades.ccf.core.Tarjeta')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'TasasInteresMora', 'com.asopagos.entidades.ccf.aportes.TasasInteresMora')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'TipoVia', 'com.asopagos.entidades.ccf.core.TipoVia')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'Ubicacion', 'com.asopagos.entidades.ccf.core.Ubicacion')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'UbicacionEmpresa', 'com.asopagos.entidades.ccf.core.UbicacionEmpresa')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'ValidacionProceso', 'com.asopagos.entidades.transversal.core.ValidacionProceso')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'VariableComunicado', 'com.asopagos.entidades.ccf.comunicados.VariableComunicado')

INSERT INTO [dbo].[ParametrizacionTablaAuditable]
           ([ptaActualizar]
           ,[ptaConsultar]
           ,[ptaCrear]
		   ,[ptaNombreTabla]
           ,[ptaEntityClassName])
     VALUES
           ('true',
           'true',
           'true',
			'glosaComentarioNovedad', 'com.asopagos.entidades.ccf.novedades.GlosaComentarioNoveda')

