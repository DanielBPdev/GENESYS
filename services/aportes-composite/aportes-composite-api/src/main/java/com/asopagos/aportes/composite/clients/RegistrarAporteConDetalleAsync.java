package com.asopagos.aportes.composite.clients;

import com.asopagos.aportes.composite.dto.InformacionSolicitudDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.enumeraciones.aportes.OrigenAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/registrarAporteConDetalleAsync
 */
public class RegistrarAporteConDetalleAsync extends ServiceClient { 
	private OrigenAporteEnum origenAporte;
	private Integer cajaAporte;
	private Long idSolicitud;
	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
	private Boolean pagadorTercero;
	private Long idRegistroGeneral;
	private Long fechaRecaudo;
	private Integer cuentaBancariaRecaudo;
  
  
 	public RegistrarAporteConDetalleAsync(OrigenAporteEnum origenAporte, Integer cajaAporte, Long idSolicitud,
		TipoSolicitanteMovimientoAporteEnum tipoSolicitante, Boolean pagadorTercero, Long idRegistroGeneral,
		Long fechaRecaudo, Integer cuentaBancariaRecaudo){
 		super();
		this.origenAporte = origenAporte;
		this.cajaAporte = cajaAporte;
		this.idSolicitud = idSolicitud;
		this.tipoSolicitante = tipoSolicitante;
		this.pagadorTercero = pagadorTercero;
		this.idRegistroGeneral = idRegistroGeneral;
		this.fechaRecaudo = fechaRecaudo;
		this.cuentaBancariaRecaudo = cuentaBancariaRecaudo;

 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("origenAporte", origenAporte != null ? origenAporte.name() : null)
			.queryParam("cajaAporte", cajaAporte)
			.queryParam("idSolicitud", idSolicitud)
			.queryParam("tipoSolicitante", tipoSolicitante != null ? tipoSolicitante.name() : null)
			.queryParam("pagadorTercero", pagadorTercero)
			.queryParam("idRegistroGeneral", idRegistroGeneral)
			.queryParam("fechaRecaudo", fechaRecaudo)
			.queryParam("cuentaBancariaRecaudo", cuentaBancariaRecaudo)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	
  
}