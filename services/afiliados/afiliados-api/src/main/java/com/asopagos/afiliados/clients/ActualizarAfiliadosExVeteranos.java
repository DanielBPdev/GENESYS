package com.asopagos.afiliados.clients;

import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import java.util.List;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;


public class ActualizarAfiliadosExVeteranos extends ServiceClient{

    private List<Long> rolAfiliados;
  
  
    /** Atributo que almacena los datos resultado del llamado al servicio */
   private List<RolAfiliadoModeloDTO> result;

   public ActualizarAfiliadosExVeteranos (List<Long> ids){
        super();
   }

   @Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idRolAfiliado", rolAfiliados)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<RolAfiliadoModeloDTO>) response.readEntity(RolAfiliadoModeloDTO.class);
	}
  
    /**
     * Retorna el resultado del llamado al servicio
     */
    public List<RolAfiliadoModeloDTO> getResult() {
        return result;
    }

 
    
}
