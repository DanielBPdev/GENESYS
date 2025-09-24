package com.asopagos.aportes.composite.clients;

import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import java.util.Date;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/AportesComposite/reintegrarGrupoFamiliar
 */
public class ReintegrarGrupoFamiliar extends ServiceClient { 
   	private TipoIdentificacionEnum tipoIdEmpleador;
  	private String numeroIdCotizante;
  	private Date fechaRetiroAfiliado;
  	private TipoIdentificacionEnum tipoIdCotizante;
  	private String numeroIdEmpleador;
  	private Date fechaIngresoAfiliado;
  	private TipoAfiliadoEnum tipoCotizante;
   
  
 	public ReintegrarGrupoFamiliar (TipoIdentificacionEnum tipoIdEmpleador,String numeroIdCotizante,Date fechaRetiroAfiliado,TipoIdentificacionEnum tipoIdCotizante,String numeroIdEmpleador,Date fechaIngresoAfiliado,TipoAfiliadoEnum tipoCotizante){
 		super();
		this.tipoIdEmpleador=tipoIdEmpleador;
		this.numeroIdCotizante=numeroIdCotizante;
		this.fechaRetiroAfiliado=fechaRetiroAfiliado;
		this.tipoIdCotizante=tipoIdCotizante;
		this.numeroIdEmpleador=numeroIdEmpleador;
		this.fechaIngresoAfiliado=fechaIngresoAfiliado;
		this.tipoCotizante=tipoCotizante;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("tipoIdEmpleador", tipoIdEmpleador)
			.queryParam("numeroIdCotizante", numeroIdCotizante)
			.queryParam("fechaRetiroAfiliado", fechaRetiroAfiliado)
			.queryParam("tipoIdCotizante", tipoIdCotizante)
			.queryParam("numeroIdEmpleador", numeroIdEmpleador)
			.queryParam("fechaIngresoAfiliado", fechaIngresoAfiliado)
			.queryParam("tipoCotizante", tipoCotizante)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setTipoIdEmpleador (TipoIdentificacionEnum tipoIdEmpleador){
 		this.tipoIdEmpleador=tipoIdEmpleador;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdEmpleador (){
 		return tipoIdEmpleador;
 	}
  	public void setNumeroIdCotizante (String numeroIdCotizante){
 		this.numeroIdCotizante=numeroIdCotizante;
 	}
 	
 	public String getNumeroIdCotizante (){
 		return numeroIdCotizante;
 	}
  	public void setFechaRetiroAfiliado (Date fechaRetiroAfiliado){
 		this.fechaRetiroAfiliado=fechaRetiroAfiliado;
 	}
 	
 	public Date getFechaRetiroAfiliado (){
 		return fechaRetiroAfiliado;
 	}
  	public void setTipoIdCotizante (TipoIdentificacionEnum tipoIdCotizante){
 		this.tipoIdCotizante=tipoIdCotizante;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdCotizante (){
 		return tipoIdCotizante;
 	}
  	public void setNumeroIdEmpleador (String numeroIdEmpleador){
 		this.numeroIdEmpleador=numeroIdEmpleador;
 	}
 	
 	public String getNumeroIdEmpleador (){
 		return numeroIdEmpleador;
 	}
  	public void setFechaIngresoAfiliado (Date fechaIngresoAfiliado){
 		this.fechaIngresoAfiliado=fechaIngresoAfiliado;
 	}
 	
 	public Date getFechaIngresoAfiliado (){
 		return fechaIngresoAfiliado;
 	}
  	public void setTipoCotizante (TipoAfiliadoEnum tipoCotizante){
 		this.tipoCotizante=tipoCotizante;
 	}
 	
 	public TipoAfiliadoEnum getTipoCotizante (){
 		return tipoCotizante;
 	}
  
  
}