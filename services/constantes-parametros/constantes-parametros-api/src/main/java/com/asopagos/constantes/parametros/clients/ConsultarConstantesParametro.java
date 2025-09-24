package com.asopagos.constantes.parametros.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.core.SubCategoriaParametroEnum;
import com.asopagos.constantes.parametros.dto.ConstantesParametroDTO;
import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/constantesparametros/consultarConstantesParametro
 */
public class ConsultarConstantesParametro extends ServiceClient {
 
  
  	private Boolean cargaInicio;
  	private String nombre;
  	private String valor;
  	private SubCategoriaParametroEnum subCategoria;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ConstantesParametroDTO> result;
  
 	public ConsultarConstantesParametro (Boolean cargaInicio,String nombre,String valor,SubCategoriaParametroEnum subCategoria){
 		super();
		this.cargaInicio=cargaInicio;
		this.nombre=nombre;
		this.valor=valor;
		this.subCategoria=subCategoria;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("cargaInicio", cargaInicio)
						.queryParam("nombre", nombre)
						.queryParam("valor", valor)
						.queryParam("subCategoria", subCategoria)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ConstantesParametroDTO>) response.readEntity(new GenericType<List<ConstantesParametroDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ConstantesParametroDTO> getResult() {
		return result;
	}

 
  	public void setCargaInicio (Boolean cargaInicio){
 		this.cargaInicio=cargaInicio;
 	}
 	
 	public Boolean getCargaInicio (){
 		return cargaInicio;
 	}
  	public void setNombre (String nombre){
 		this.nombre=nombre;
 	}
 	
 	public String getNombre (){
 		return nombre;
 	}
  	public void setValor (String valor){
 		this.valor=valor;
 	}
 	
 	public String getValor (){
 		return valor;
 	}
  	public void setSubCategoria (SubCategoriaParametroEnum subCategoria){
 		this.subCategoria=subCategoria;
 	}
 	
 	public SubCategoriaParametroEnum getSubCategoria (){
 		return subCategoria;
 	}
  
}