package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.subsidiomonetario.dto.EspecieLiquidacionManualDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consultarSubsidioEspecieLiquidacionManual
 */
public class ConsultarSubsidioEspecieLiquidacionManual extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoIdentificacionAfiliado;
  	private String numeroIdentificacionAfiliado;
  	private String Periodo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<EspecieLiquidacionManualDTO> result;
  
 	public ConsultarSubsidioEspecieLiquidacionManual (TipoIdentificacionEnum tipoIdentificacionAfiliado,String numeroIdentificacionAfiliado,String Periodo){
 		super();
		this.tipoIdentificacionAfiliado=tipoIdentificacionAfiliado;
		this.numeroIdentificacionAfiliado=numeroIdentificacionAfiliado;
		this.Periodo=Periodo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdentificacionAfiliado", tipoIdentificacionAfiliado)
						.queryParam("numeroIdentificacionAfiliado", numeroIdentificacionAfiliado)
						.queryParam("Periodo", Periodo)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<EspecieLiquidacionManualDTO>) response.readEntity(new GenericType<List<EspecieLiquidacionManualDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<EspecieLiquidacionManualDTO> getResult() {
		return result;
	}

 
  	public void setTipoIdentificacionAfiliado (TipoIdentificacionEnum tipoIdentificacionAfiliado){
 		this.tipoIdentificacionAfiliado=tipoIdentificacionAfiliado;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacionAfiliado (){
 		return tipoIdentificacionAfiliado;
 	}
  	public void setNumeroIdentificacionAfiliado (String numeroIdentificacionAfiliado){
 		this.numeroIdentificacionAfiliado=numeroIdentificacionAfiliado;
 	}
 	
 	public String getNumeroIdentificacionAfiliado (){
 		return numeroIdentificacionAfiliado;
 	}
  	public void setPeriodo (String Periodo){
 		this.Periodo=Periodo;
 	}
 	
 	public String getPeriodo (){
 		return Periodo;
 	}
  
}