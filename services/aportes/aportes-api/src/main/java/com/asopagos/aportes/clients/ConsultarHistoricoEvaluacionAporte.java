package com.asopagos.aportes.clients;

import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import java.lang.Boolean;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.dto.aportes.HistoricoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarHistoricoEvaluacionAporte
 */
public class ConsultarHistoricoEvaluacionAporte extends ServiceClient {
 
  
  	private EstadoProcesoArchivoEnum estadoProcesoArchivo;
  	private EstadoAporteEnum estadoAporte;
  	private Boolean tieneModificaciones;
  	private TipoIdentificacionEnum tipoIdentificacionCotizante;
  	private ModalidadRecaudoAporteEnum modalidadRecaudo;
  	private String numeroIdentificacionCotizante;
  	private String periodoAporte;
  	private EstadoSolicitudAporteEnum estadoSolicitudAporte;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private HistoricoDTO result;
  
 	public ConsultarHistoricoEvaluacionAporte (EstadoProcesoArchivoEnum estadoProcesoArchivo,EstadoAporteEnum estadoAporte,Boolean tieneModificaciones,TipoIdentificacionEnum tipoIdentificacionCotizante,ModalidadRecaudoAporteEnum modalidadRecaudo,String numeroIdentificacionCotizante,String periodoAporte,EstadoSolicitudAporteEnum estadoSolicitudAporte){
 		super();
		this.estadoProcesoArchivo=estadoProcesoArchivo;
		this.estadoAporte=estadoAporte;
		this.tieneModificaciones=tieneModificaciones;
		this.tipoIdentificacionCotizante=tipoIdentificacionCotizante;
		this.modalidadRecaudo=modalidadRecaudo;
		this.numeroIdentificacionCotizante=numeroIdentificacionCotizante;
		this.periodoAporte=periodoAporte;
		this.estadoSolicitudAporte=estadoSolicitudAporte;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("estadoProcesoArchivo", estadoProcesoArchivo)
						.queryParam("estadoAporte", estadoAporte)
						.queryParam("tieneModificaciones", tieneModificaciones)
						.queryParam("tipoIdentificacionCotizante", tipoIdentificacionCotizante)
						.queryParam("modalidadRecaudo", modalidadRecaudo)
						.queryParam("numeroIdentificacionCotizante", numeroIdentificacionCotizante)
						.queryParam("periodoAporte", periodoAporte)
						.queryParam("estadoSolicitudAporte", estadoSolicitudAporte)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (HistoricoDTO) response.readEntity(HistoricoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public HistoricoDTO getResult() {
		return result;
	}

 
  	public void setEstadoProcesoArchivo (EstadoProcesoArchivoEnum estadoProcesoArchivo){
 		this.estadoProcesoArchivo=estadoProcesoArchivo;
 	}
 	
 	public EstadoProcesoArchivoEnum getEstadoProcesoArchivo (){
 		return estadoProcesoArchivo;
 	}
  	public void setEstadoAporte (EstadoAporteEnum estadoAporte){
 		this.estadoAporte=estadoAporte;
 	}
 	
 	public EstadoAporteEnum getEstadoAporte (){
 		return estadoAporte;
 	}
  	public void setTieneModificaciones (Boolean tieneModificaciones){
 		this.tieneModificaciones=tieneModificaciones;
 	}
 	
 	public Boolean getTieneModificaciones (){
 		return tieneModificaciones;
 	}
  	public void setTipoIdentificacionCotizante (TipoIdentificacionEnum tipoIdentificacionCotizante){
 		this.tipoIdentificacionCotizante=tipoIdentificacionCotizante;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacionCotizante (){
 		return tipoIdentificacionCotizante;
 	}
  	public void setModalidadRecaudo (ModalidadRecaudoAporteEnum modalidadRecaudo){
 		this.modalidadRecaudo=modalidadRecaudo;
 	}
 	
 	public ModalidadRecaudoAporteEnum getModalidadRecaudo (){
 		return modalidadRecaudo;
 	}
  	public void setNumeroIdentificacionCotizante (String numeroIdentificacionCotizante){
 		this.numeroIdentificacionCotizante=numeroIdentificacionCotizante;
 	}
 	
 	public String getNumeroIdentificacionCotizante (){
 		return numeroIdentificacionCotizante;
 	}
  	public void setPeriodoAporte (String periodoAporte){
 		this.periodoAporte=periodoAporte;
 	}
 	
 	public String getPeriodoAporte (){
 		return periodoAporte;
 	}
  	public void setEstadoSolicitudAporte (EstadoSolicitudAporteEnum estadoSolicitudAporte){
 		this.estadoSolicitudAporte=estadoSolicitudAporte;
 	}
 	
 	public EstadoSolicitudAporteEnum getEstadoSolicitudAporte (){
 		return estadoSolicitudAporte;
 	}
  
}