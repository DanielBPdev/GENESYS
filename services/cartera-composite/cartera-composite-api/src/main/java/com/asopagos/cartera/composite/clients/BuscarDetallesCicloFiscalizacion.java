package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.cartera.SimulacionDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/carteraComposite/buscarDetallesCicloFiscalizacion/{idCiclo}
 */
public class BuscarDetallesCicloFiscalizacion extends ServiceClient {
 
  	private Long idCiclo;
  
  	private boolean gestionManual;
  	private boolean esSupervisor;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SimulacionDTO> result;
  
 	public BuscarDetallesCicloFiscalizacion (Long idCiclo,boolean gestionManual,boolean esSupervisor){
 		super();
		this.idCiclo=idCiclo;
		this.gestionManual=gestionManual;
		this.esSupervisor=esSupervisor;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idCiclo", idCiclo)
									.queryParam("gestionManual", gestionManual)
						.queryParam("esSupervisor", esSupervisor)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SimulacionDTO>) response.readEntity(new GenericType<List<SimulacionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SimulacionDTO> getResult() {
		return result;
	}

 	public void setIdCiclo (Long idCiclo){
 		this.idCiclo=idCiclo;
 	}
 	
 	public Long getIdCiclo (){
 		return idCiclo;
 	}
  
  	public void setGestionManual (boolean gestionManual){
 		this.gestionManual=gestionManual;
 	}
 	
 	public boolean getGestionManual (){
 		return gestionManual;
 	}
  	public void setEsSupervisor (boolean esSupervisor){
 		this.esSupervisor=esSupervisor;
 	}
 	
 	public boolean getEsSupervisor (){
 		return esSupervisor;
 	}
  
}