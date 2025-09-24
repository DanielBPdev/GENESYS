package com.asopagos.afiliaciones.empleadores.composite.clients;

import com.asopagos.dto.AnalizarSolicitudAfiliacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import com.asopagos.services.common.ServiceClient;
import javax.ws.rs.core.GenericType;
/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudAfiliacionEmpleador/creacionUsuriosEmpresasMasivos
 */
public class CreacionUsuriosEmpresasMasivos extends ServiceClient { 
			private List<String> result;
 	public CreacionUsuriosEmpresasMasivos (){
 		super();
 	}
  
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<String>) response.readEntity(new GenericType<List<String>>(){});
	}
	
	 public List<String>  getResult() {
		return result;
	}

}