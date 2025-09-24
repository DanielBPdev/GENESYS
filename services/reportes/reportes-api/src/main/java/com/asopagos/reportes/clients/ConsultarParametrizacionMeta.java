package com.asopagos.reportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.reportes.PeriodicidadMetaEnum;
import com.asopagos.reportes.dto.DatosParametrizacionMetaDTO;
import com.asopagos.enumeraciones.reportes.MetaEnum;
import java.lang.String;
import com.asopagos.enumeraciones.reportes.FrecuenciaMetaEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/reportes/consultarParametrizacionMeta
 */
public class ConsultarParametrizacionMeta extends ServiceClient {
 
  
  	private PeriodicidadMetaEnum periodicidad;
  	private FrecuenciaMetaEnum frecuencia;
  	private String periodo;
  	private MetaEnum meta;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DatosParametrizacionMetaDTO> result;
  
 	public ConsultarParametrizacionMeta (PeriodicidadMetaEnum periodicidad,FrecuenciaMetaEnum frecuencia,String periodo,MetaEnum meta){
 		super();
		this.periodicidad=periodicidad;
		this.frecuencia=frecuencia;
		this.periodo=periodo;
		this.meta=meta;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("periodicidad", periodicidad)
						.queryParam("frecuencia", frecuencia)
						.queryParam("periodo", periodo)
						.queryParam("meta", meta)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<DatosParametrizacionMetaDTO>) response.readEntity(new GenericType<List<DatosParametrizacionMetaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<DatosParametrizacionMetaDTO> getResult() {
		return result;
	}

 
  	public void setPeriodicidad (PeriodicidadMetaEnum periodicidad){
 		this.periodicidad=periodicidad;
 	}
 	
 	public PeriodicidadMetaEnum getPeriodicidad (){
 		return periodicidad;
 	}
  	public void setFrecuencia (FrecuenciaMetaEnum frecuencia){
 		this.frecuencia=frecuencia;
 	}
 	
 	public FrecuenciaMetaEnum getFrecuencia (){
 		return frecuencia;
 	}
  	public void setPeriodo (String periodo){
 		this.periodo=periodo;
 	}
 	
 	public String getPeriodo (){
 		return periodo;
 	}
  	public void setMeta (MetaEnum meta){
 		this.meta=meta;
 	}
 	
 	public MetaEnum getMeta (){
 		return meta;
 	}
  
}