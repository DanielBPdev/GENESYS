package com.asopagos.entidaddescuento.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.String;
import com.asopagos.entidaddescuento.dto.EntidadDescuentoModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/entidadDescuento/consultarEntidadDescuentoPorNombreCodigo
 */
public class ConsultarEntidadDescuentoPorNombreCodigo extends ServiceClient {
 
  
  	private String nombre;
  	private String codigo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<EntidadDescuentoModeloDTO> result;
  
 	public ConsultarEntidadDescuentoPorNombreCodigo (String nombre,String codigo){
 		super();
		this.nombre=nombre;
		this.codigo=codigo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("nombre", nombre)
						.queryParam("codigo", codigo)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<EntidadDescuentoModeloDTO>) response.readEntity(new GenericType<List<EntidadDescuentoModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<EntidadDescuentoModeloDTO> getResult() {
		return result;
	}

 
  	public void setNombre (String nombre){
 		this.nombre=nombre;
 	}
 	
 	public String getNombre (){
 		return nombre;
 	}
  	public void setCodigo (String codigo){
 		this.codigo=codigo;
 	}
 	
 	public String getCodigo (){
 		return codigo;
 	}
  
}