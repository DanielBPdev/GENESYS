package com.asopagos.novedades.composite.dto;

import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.dto.modelo.SolicitudNovedadModeloDTO;

public class ResolverNovedadDTO {

	private SolicitudNovedadDTO solicitudNovedad;

	private SolicitudNovedadModeloDTO solicitudNovedadModelo;


	public SolicitudNovedadDTO getSolicitudNovedad() {
		return this.solicitudNovedad;
	}

	public void setSolicitudNovedad(SolicitudNovedadDTO solicitudNovedad) {
		this.solicitudNovedad = solicitudNovedad;
	}

	public SolicitudNovedadModeloDTO getSolicitudNovedadModelo() {
		return this.solicitudNovedadModelo;
	}

	public void setSolicitudNovedadModelo(SolicitudNovedadModeloDTO solicitudNovedadModelo) {
		this.solicitudNovedadModelo = solicitudNovedadModelo;
	}

	@Override
	public String toString() {
		return "{" +
			" solicitudNovedad='" + getSolicitudNovedad() + "'" +
			", solicitudNovedadModelo='" + getSolicitudNovedadModelo() + "'" +
			"}";
	}
}
