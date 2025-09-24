package com.asopagos.pila.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.pila.dto.ArchivosProcesadosFinalizadosOFDTO;
import java.lang.Long;
import java.lang.Short;
import com.asopagos.enumeraciones.aportes.TipoOperadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/buscarArchivosOIProcesadosFinalizados
 */
public class BuscarArchivosOIProcesadosFinalizados extends ServiceClient {
 
  
  	private Short digitoVerificacion;
  	private Long numeroPlanilla;
  	private TipoOperadorEnum tipoOperador;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private Long fechaFin;
  	private String idBanco;
  	private Long fechaInicio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ArchivosProcesadosFinalizadosOFDTO> result;
  
 	public BuscarArchivosOIProcesadosFinalizados (Short digitoVerificacion,Long numeroPlanilla,TipoOperadorEnum tipoOperador,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,Long fechaFin,String idBanco,Long fechaInicio){
 		super();
		this.digitoVerificacion=digitoVerificacion;
		this.numeroPlanilla=numeroPlanilla;
		this.tipoOperador=tipoOperador;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.fechaFin=fechaFin;
		this.idBanco=idBanco;
		this.fechaInicio=fechaInicio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("digitoVerificacion", digitoVerificacion)
						.queryParam("numeroPlanilla", numeroPlanilla)
						.queryParam("tipoOperador", tipoOperador)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("fechaFin", fechaFin)
						.queryParam("idBanco", idBanco)
						.queryParam("fechaInicio", fechaInicio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ArchivosProcesadosFinalizadosOFDTO>) response.readEntity(new GenericType<List<ArchivosProcesadosFinalizadosOFDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ArchivosProcesadosFinalizadosOFDTO> getResult() {
		return result;
	}

 
  	public void setDigitoVerificacion (Short digitoVerificacion){
 		this.digitoVerificacion=digitoVerificacion;
 	}
 	
 	public Short getDigitoVerificacion (){
 		return digitoVerificacion;
 	}
  	public void setNumeroPlanilla (Long numeroPlanilla){
 		this.numeroPlanilla=numeroPlanilla;
 	}
 	
 	public Long getNumeroPlanilla (){
 		return numeroPlanilla;
 	}
  	public void setTipoOperador (TipoOperadorEnum tipoOperador){
 		this.tipoOperador=tipoOperador;
 	}
 	
 	public TipoOperadorEnum getTipoOperador (){
 		return tipoOperador;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  	public void setFechaFin (Long fechaFin){
 		this.fechaFin=fechaFin;
 	}
 	
 	public Long getFechaFin (){
 		return fechaFin;
 	}
  	public void setIdBanco (String idBanco){
 		this.idBanco=idBanco;
 	}
 	
 	public String getIdBanco (){
 		return idBanco;
 	}
  	public void setFechaInicio (Long fechaInicio){
 		this.fechaInicio=fechaInicio;
 	}
 	
 	public Long getFechaInicio (){
 		return fechaInicio;
 	}
  
}