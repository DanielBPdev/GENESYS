package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;

import com.asopagos.services.common.ServiceClient;

public class ConsultarGruposFamiliaresConMarcaYAdmin extends ServiceClient{

    private String tipoIdentificacion, numeroIdentificacion, numeroTarjeta ;

	private Boolean expedicion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public ConsultarGruposFamiliaresConMarcaYAdmin (String tipoIdentificacion, String numeroIdentificacion, String numeroTarjeta, Boolean expedicion){
 		super();
		this.tipoIdentificacion=tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.numeroTarjeta = numeroTarjeta;
		this.expedicion = expedicion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdentificacion", tipoIdentificacion)
                                    .queryParam("numeroIdentificacion", numeroIdentificacion)
                                    .queryParam("numeroTarjeta", numeroTarjeta)
									.queryParam("expedicion", expedicion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		result = (List<Long>) response.readEntity(new GenericType<List<Long>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
    public List<Long> getResult() {
		return result;
	}
    
}