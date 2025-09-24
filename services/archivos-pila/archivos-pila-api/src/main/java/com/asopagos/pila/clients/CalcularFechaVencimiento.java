package com.asopagos.pila.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import java.lang.String;
import com.asopagos.enumeraciones.pila.PeriodoPagoPlanillaEnum;
import java.lang.Integer;
import com.asopagos.enumeraciones.aportes.ClaseAportanteEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/archivosPILA/calcularFechaVencimiento
 */
public class CalcularFechaVencimiento extends ServiceClient {
 
  
  	private NaturalezaJuridicaEnum naturalezaJuridica;
  	private Integer cantidadPersonas;
  	private TipoArchivoPilaEnum tipoArchivo;
  	private PeriodoPagoPlanillaEnum oportunidad;
  	private ClaseAportanteEnum claseAportante;
  	private String periodo;
  	private String numeroDocumentoAportante;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CalcularFechaVencimiento (NaturalezaJuridicaEnum naturalezaJuridica,Integer cantidadPersonas,TipoArchivoPilaEnum tipoArchivo,PeriodoPagoPlanillaEnum oportunidad,ClaseAportanteEnum claseAportante,String periodo,String numeroDocumentoAportante){
 		super();
		this.naturalezaJuridica=naturalezaJuridica;
		this.cantidadPersonas=cantidadPersonas;
		this.tipoArchivo=tipoArchivo;
		this.oportunidad=oportunidad;
		this.claseAportante=claseAportante;
		this.periodo=periodo;
		this.numeroDocumentoAportante=numeroDocumentoAportante;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("naturalezaJuridica", naturalezaJuridica)
						.queryParam("cantidadPersonas", cantidadPersonas)
						.queryParam("tipoArchivo", tipoArchivo)
						.queryParam("oportunidad", oportunidad)
						.queryParam("claseAportante", claseAportante)
						.queryParam("periodo", periodo)
						.queryParam("numeroDocumentoAportante", numeroDocumentoAportante)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Long getResult() {
		return result;
	}

 
  	public void setNaturalezaJuridica (NaturalezaJuridicaEnum naturalezaJuridica){
 		this.naturalezaJuridica=naturalezaJuridica;
 	}
 	
 	public NaturalezaJuridicaEnum getNaturalezaJuridica (){
 		return naturalezaJuridica;
 	}
  	public void setCantidadPersonas (Integer cantidadPersonas){
 		this.cantidadPersonas=cantidadPersonas;
 	}
 	
 	public Integer getCantidadPersonas (){
 		return cantidadPersonas;
 	}
  	public void setTipoArchivo (TipoArchivoPilaEnum tipoArchivo){
 		this.tipoArchivo=tipoArchivo;
 	}
 	
 	public TipoArchivoPilaEnum getTipoArchivo (){
 		return tipoArchivo;
 	}
  	public void setOportunidad (PeriodoPagoPlanillaEnum oportunidad){
 		this.oportunidad=oportunidad;
 	}
 	
 	public PeriodoPagoPlanillaEnum getOportunidad (){
 		return oportunidad;
 	}
  	public void setClaseAportante (ClaseAportanteEnum claseAportante){
 		this.claseAportante=claseAportante;
 	}
 	
 	public ClaseAportanteEnum getClaseAportante (){
 		return claseAportante;
 	}
  	public void setPeriodo (String periodo){
 		this.periodo=periodo;
 	}
 	
 	public String getPeriodo (){
 		return periodo;
 	}
  	public void setNumeroDocumentoAportante (String numeroDocumentoAportante){
 		this.numeroDocumentoAportante=numeroDocumentoAportante;
 	}
 	
 	public String getNumeroDocumentoAportante (){
 		return numeroDocumentoAportante;
 	}
  
}