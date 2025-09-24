package com.asopagos.archivos.business.publicador.ejb;

import javax.ejb.Local;

import com.asopagos.dto.InformacionArchivoDTO;

@Local
public interface IPublisherGestorDocumentalExternoMdb {

	public void enviarAColaGestorDocumentalExterno(InformacionArchivoDTO informacionArchivoDTO);
}
