--liquibase formatted sql

--changeset mosorio:01
--comment: creación de la tablas para pago de subsidios monetarios, para dar soporte a los requisitos definidos en las historias de usuario de liquidación masiva

CREATE TABLE DocumentoSoporteConvenio  (
	dscId BIGINT NOT NULL IDENTITY(1,1),
	dscConvenio  BIGINT NOT NULL,
	dscDocumentoSoporte BIGINT NOT NULL,
	
CONSTRAINT PK_DocumentoSoporteConvenio_dscId PRIMARY KEY (dscId)
)
ALTER TABLE DocumentoSoporteConvenio ADD CONSTRAINT FK_DocumentoSoporteConvenio_doscConvenio
 FOREIGN KEY (doscConvenio) REFERENCES ConvenioTerceroPagador(conId);
ALTER TABLE DocumentoSoporteConvenio ADD CONSTRAINT FK_DocumentoSoporteConvenio_doscDocumentoSoporte
 FOREIGN KEY (dscDocumentoSoporte) REFERENCES DocumentoSoporte(dosId);

--changeset hhernandez:02
--comment:Se elimina y adiciona campos en la tabla RetiroPersonaAutorizadaCobroSubsidio
ALTER TABLE RetiroPersonaAutorizadaCobroSubsidio DROP COLUMN rpaIdDocumentoSoporte;
ALTER TABLE RetiroPersonaAutorizadaCobroSubsidio DROP COLUMN rpaVersionDocumentoSoporte;
ALTER TABLE RetiroPersonaAutorizadaCobroSubsidio ADD rpaDocumentoSoporte BIGINT NOT NULL;
ALTER TABLE RetiroPersonaAutorizadaCobroSubsidio ADD CONSTRAINT FK_RetiroPersonaAutorizadaCobroSubsidio_rpaDocumentoSoporte FOREIGN KEY (rpaDocumentoSoporte) REFERENCES DocumentoSoporte(dosId);

--changeset hhernandez:03
--comment:Modificacion de nombre de campos en las tablas asociadas a SubsidioMonetarioPagos 
--ArchivoRetiroTerceroPagador
EXEC sp_rename 'ArchivoRetiroTerceroPagador.arrIdentificacionDocumento', 'arrIdentificadorDocumento ', 'COLUMN';

--RegistroArchivoRetiroTerceroPagador
EXEC sp_rename 'RegistroArchivoRetiroTerceroPagador.rarIdCuentaAdminSubsidio', 'rarCuentaAdministradorSubsidio', 'COLUMN';

--DocumentoSoporteConvenio
ALTER TABLE DocumentoSoporteConvenio DROP CONSTRAINT FK_DocumentoSoporteConvenio_doscDocumentoSoporte;
EXEC sp_rename 'DocumentoSoporteConvenio.dscConvenio', 'dscConvenioTerceroPagador', 'COLUMN';
ALTER TABLE DocumentoSoporteConvenio ADD CONSTRAINT FK_DocumentoSoporteConvenio_dscConvenioTerceroPagador FOREIGN KEY (dscConvenioTerceroPagador) REFERENCES ConvenioTerceroPagador(conId);

--DocumentoSoporteConvenio
ALTER TABLE CuentaAdministradorSubsidio DROP CONSTRAINT FK_CuentaAdministradorSubsidio_casIdAdministradorSubsidio;
ALTER TABLE CuentaAdministradorSubsidio DROP CONSTRAINT FK_CuentaAdministradorSubsidio_casIdSitioDePago;
ALTER TABLE CuentaAdministradorSubsidio DROP CONSTRAINT FK_CuentaAdministradorSubsidio_casIdSitioDeCobro;
ALTER TABLE CuentaAdministradorSubsidio DROP CONSTRAINT FK_CuentaAdministradorSubsidio_casIdMedioDePago;
EXEC sp_rename 'CuentaAdministradorSubsidio.casIdAdministradorSubsidio', 'casAdministradorSubsidio', 'COLUMN';
EXEC sp_rename 'CuentaAdministradorSubsidio.casIdSitioDePago', 'casSitioDePago', 'COLUMN'; 
EXEC sp_rename 'CuentaAdministradorSubsidio.casIdSitioDeCobro', 'casSitioDeCobro', 'COLUMN'; 
EXEC sp_rename 'CuentaAdministradorSubsidio.casIdMedioDePago', 'casMedioDePago', 'COLUMN';
ALTER TABLE CuentaAdministradorSubsidio ADD CONSTRAINT FK_CuentaAdministradorSubsidio_casAdministradorSubsidio FOREIGN KEY (casAdministradorSubsidio) REFERENCES AdministradorSubsidio(asuId);
ALTER TABLE CuentaAdministradorSubsidio ADD CONSTRAINT FK_CuentaAdministradorSubsidio_casSitioDePago FOREIGN KEY (casSitioDePago) REFERENCES SitioPago(sipId);
ALTER TABLE CuentaAdministradorSubsidio ADD CONSTRAINT FK_CuentaAdministradorSubsidio_casSitioDeCobro FOREIGN KEY (casSitioDeCobro) REFERENCES SitioPago(sipId);
ALTER TABLE CuentaAdministradorSubsidio ADD CONSTRAINT FK_CuentaAdministradorSubsidio_casMedioDePago FOREIGN KEY (casMedioDePago) REFERENCES MedioDePago(mdpId);

--DetalleSubsidioAsignado
ALTER TABLE dbo.DetalleSubsidioAsignado DROP CONSTRAINT FK_DetalleSubsidioAsignado_dsaIdSolicitudLiquidacionSubsidio;
ALTER TABLE dbo.DetalleSubsidioAsignado DROP CONSTRAINT FK_DetalleSubsidioAsignado_dsaIdEmpleador;
ALTER TABLE dbo.DetalleSubsidioAsignado DROP CONSTRAINT FK_DetalleSubsidioAsignado_dsaIdAfiliadoPrincipal;
ALTER TABLE dbo.DetalleSubsidioAsignado DROP CONSTRAINT FK_DetalleSubsidioAsignado_dsaIdGrupoFamiliar;
ALTER TABLE dbo.DetalleSubsidioAsignado DROP CONSTRAINT FK_DetalleSubsidioAsignado_dsaIdBeneficiarioDetalle;
ALTER TABLE dbo.DetalleSubsidioAsignado DROP CONSTRAINT FK_DetalleSubsidioAsignado_dsaIdAdministradorSubsidio;
ALTER TABLE dbo.DetalleSubsidioAsignado DROP CONSTRAINT FK_DetalleSubsidioAsignado_dsaIdCuentaAdministradorSubsidio;
EXEC sp_rename 'DetalleSubsidioAsignado.dsaIdSolicitudLiquidacionSubsidio', 'dsaSolicitudLiquidacionSubsidio', 'COLUMN';
EXEC sp_rename 'DetalleSubsidioAsignado.dsaIdEmpleador', 'dsaEmpleador', 'COLUMN'; 
EXEC sp_rename 'DetalleSubsidioAsignado.dsaIdAfiliadoPrincipal', 'dsaAfiliadoPrincipal', 'COLUMN'; 
EXEC sp_rename 'DetalleSubsidioAsignado.dsaIdGrupoFamiliar', 'dsaGrupoFamiliar', 'COLUMN';
EXEC sp_rename 'DetalleSubsidioAsignado.dsaIdBeneficiarioDetalle', 'dsaBeneficiarioDetalle', 'COLUMN';
EXEC sp_rename 'DetalleSubsidioAsignado.dsaIdAdministradorSubsidio', 'dsaAdministradorSubsidio', 'COLUMN';
EXEC sp_rename 'DetalleSubsidioAsignado.dsaIdCuentaAdministradorSubsidio', 'dsaCuentaAdministradorSubsidio', 'COLUMN';
EXEC sp_rename 'DetalleSubsidioAsignado.dsaIdResultadoValidacionLiquidacion', 'dsaResultadoValidacionLiquidacion', 'COLUMN';
ALTER TABLE DetalleSubsidioAsignado ADD CONSTRAINT FK_DetalleSubsidioAsignado_dsaSolicitudLiquidacionSubsidio FOREIGN KEY (dsaSolicitudLiquidacionSubsidio) REFERENCES SolicitudLiquidacionSubsidio(slsId);
ALTER TABLE DetalleSubsidioAsignado ADD CONSTRAINT FK_DetalleSubsidioAsignado_dsaEmpleador FOREIGN KEY (dsaEmpleador) REFERENCES Empleador(empId);
ALTER TABLE DetalleSubsidioAsignado ADD CONSTRAINT FK_DetalleSubsidioAsignado_dsaAfiliadoPrincipal FOREIGN KEY (dsaAfiliadoPrincipal) REFERENCES Afiliado(afiId);
ALTER TABLE DetalleSubsidioAsignado ADD CONSTRAINT FK_DetalleSubsidioAsignado_dsaGrupoFamiliar FOREIGN KEY (dsaGrupoFamiliar) REFERENCES GrupoFamiliar(grfId);
ALTER TABLE DetalleSubsidioAsignado ADD CONSTRAINT FK_DetalleSubsidioAsignado_dsaBeneficiarioDetalle FOREIGN KEY (dsaBeneficiarioDetalle) REFERENCES BeneficiarioDetalle(bedId);
ALTER TABLE DetalleSubsidioAsignado ADD CONSTRAINT FK_DetalleSubsidioAsignado_dsaAdministradorSubsidio FOREIGN KEY (dsaAdministradorSubsidio) REFERENCES AdministradorSubsidio(asuId);
ALTER TABLE DetalleSubsidioAsignado ADD CONSTRAINT FK_DetalleSubsidioAsignado_dsaCuentaAdministradorSubsidio FOREIGN KEY (dsaCuentaAdministradorSubsidio) REFERENCES CuentaAdministradorSubsidio(casId); 

--TransaccionesFallidasSubsidio
ALTER TABLE TransaccionesFallidasSubsidio DROP CONSTRAINT FK_TransaccionesFallidasSubsidio_tfsIdCuentaAdmonSubsidio;
DROP INDEX UK_TransaccionesFallidasSubsidio_tfsIdCuentaAdmonSubsidio ON TransaccionesFallidasSubsidio;
EXEC sp_rename 'TransaccionesFallidasSubsidio.tfsIdCuentaAdmonSubsidio', 'tfsCuentaAdministradorSubsidio', 'COLUMN';
ALTER TABLE TransaccionesFallidasSubsidio ADD CONSTRAINT FK_TransaccionesFallidasSubsidio_tfsCuentaAdministradorSubsidio FOREIGN KEY (tfsCuentaAdministradorSubsidio) REFERENCES CuentaAdministradorSubsidio(casId);
CREATE UNIQUE INDEX UK_TransaccionesFallidasSubsidio_tfsCuentaAdministradorSubsidio ON TransaccionesFallidasSubsidio(tfsCuentaAdministradorSubsidio);

--RegistroOperacionesSubsidio
ALTER TABLE RegistroOperacionesSubsidio DROP CONSTRAINT FK_RegistroOperacionesSubsidio_rosIdAdministradorSubsidio;
EXEC sp_rename 'RegistroOperacionesSubsidio.rosIdAdministradorSubsidio', 'rosAdministradorSubsidio', 'COLUMN';
ALTER TABLE RegistroOperacionesSubsidio ADD CONSTRAINT FK_RegistroOperacionesSubsidio_rosAdministradorSubsidio FOREIGN KEY (rosAdministradorSubsidio) REFERENCES AdministradorSubsidio(asuId);

--RetiroPersonaAutorizadaCobroSubsidio
ALTER TABLE RetiroPersonaAutorizadaCobroSubsidio DROP COLUMN rosTipoOperacion;
ALTER TABLE RetiroPersonaAutorizadaCobroSubsidio DROP CONSTRAINT FK_RetiroPersonaAutorizadaCobroSubsidio_rpaIdPersonaAutorizada;
ALTER TABLE RetiroPersonaAutorizadaCobroSubsidio DROP CONSTRAINT FK_RetiroPersonaAutorizadaCobroSubsidio_rpaIdCuentaAdministradorSubsidio;
EXEC sp_rename 'RetiroPersonaAutorizadaCobroSubsidio.rpaIdPersonaAutorizada', 'rpaPersonaAutorizada', 'COLUMN';
EXEC sp_rename 'RetiroPersonaAutorizadaCobroSubsidio.rpaIdCuentaAdministradorSubsidio', 'rpaCuentaAdministradorSubsidio', 'COLUMN';
ALTER TABLE RetiroPersonaAutorizadaCobroSubsidio ADD CONSTRAINT FK_RetiroPersonaAutorizadaCobroSubsidio_rpaPersonaAutorizada FOREIGN KEY (rpaPersonaAutorizada) REFERENCES Persona(perId);
ALTER TABLE RetiroPersonaAutorizadaCobroSubsidio ADD CONSTRAINT FK_RetiroPersonaAutorizadaCobroSubsidio_rpaCuentaAdministradorSubsidio FOREIGN KEY (rpaCuentaAdministradorSubsidio) REFERENCES CuentaAdministradorSubsidio(casId);

-- Cambio DescuentosSubsidioAsignado
ALTER TABLE DescuentosSubsidioAsignado DROP CONSTRAINT FK_DescuentosSubsidioAsignado_desIdDetalleSubsidioAsignado;
ALTER TABLE DescuentosSubsidioAsignado DROP CONSTRAINT FK_DescuentosSubsidioAsignado_desIdEntidadDescuento;
EXEC sp_rename 'DescuentosSubsidioAsignado.desIdDetalleSubsidioAsignado', 'desDetalleSubsidioAsignado', 'COLUMN';
EXEC sp_rename 'DescuentosSubsidioAsignado.desIdEntidadDescuento', 'desEntidadDescuento', 'COLUMN';
ALTER TABLE DescuentosSubsidioAsignado ADD CONSTRAINT FK_DescuentosSubsidioAsignado_desDetalleSubsidioAsignado FOREIGN KEY (desDetalleSubsidioAsignado) REFERENCES DetalleSubsidioAsignado(dsaId);
ALTER TABLE DescuentosSubsidioAsignado ADD CONSTRAINT FK_DescuentosSubsidioAsignado_desEntidadDescuento FOREIGN KEY (desEntidadDescuento) REFERENCES EntidadDescuento (endId);

-- Campo DetalleSolicitudAnulacionSubsidioCobrado
ALTER TABLE DetalleSolicitudAnulacionSubsidioCobrado DROP CONSTRAINT FK_DetalleSolicitudAnulacionSubsidioCobrado_dssIdSolicitudAnulacionSubsidio;
ALTER TABLE DetalleSolicitudAnulacionSubsidioCobrado DROP CONSTRAINT FK_DetalleSolicitudAnulacionSubsidioCobrado_dssIdCuentaAdministradorSubisdio;
EXEC sp_rename 'DetalleSolicitudAnulacionSubsidioCobrado.dssIdSolicitudAnulacionSubsidio', 'dssSolicitudAnulacionSubsidioCobrado', 'COLUMN';
EXEC sp_rename 'DetalleSolicitudAnulacionSubsidioCobrado.dssIdCuentaAdministradorSubisdio', 'dssCuentaAdministradorSubisdio', 'COLUMN';
ALTER TABLE DetalleSolicitudAnulacionSubsidioCobrado ADD CONSTRAINT FK_DetalleSolicitudAnulacionSubsidioCobrado_dssSolicitudAnulacionSubsidioCobrado FOREIGN KEY (dssSolicitudAnulacionSubsidioCobrado) REFERENCES SolicitudAnulacionSubsidioCobrado(sasId);
ALTER TABLE DetalleSolicitudAnulacionSubsidioCobrado ADD CONSTRAINT FK_DetalleSolicitudAnulacionSubsidioCobrado_dssCuentaAdministradorSubisdio FOREIGN KEY (dssCuentaAdministradorSubisdio) REFERENCES CuentaAdministradorSubsidio(casId);
