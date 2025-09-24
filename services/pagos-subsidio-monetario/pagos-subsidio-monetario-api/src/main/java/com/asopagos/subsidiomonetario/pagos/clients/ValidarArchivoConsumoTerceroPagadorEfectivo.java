package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Long;
import com.asopagos.dto.InformacionArchivoDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/validarArchivoConsumoTerceroPagadorEfectivo/{idConvenio}/{nombreUsuario}/{idArchivoTerceroPagadorEfectivo}
 */
public class ValidarArchivoConsumoTerceroPagadorEfectivo extends ServiceClient { 
  	private Long idConvenio;
  	private String nombreUsuario;
  	private Long idArchivoTerceroPagadorEfectivo;
    	private InformacionArchivoDTO informacionArchivoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public ValidarArchivoConsumoTerceroPagadorEfectivo (Long idConvenio,String nombreUsuario,Long idArchivoTerceroPagadorEfectivo,InformacionArchivoDTO informacionArchivoDTO){
 		super();
		this.idConvenio=idConvenio;
		this.nombreUsuario=nombreUsuario;
		this.idArchivoTerceroPagadorEfectivo=idArchivoTerceroPagadorEfectivo;
		this.informacionArchivoDTO=informacionArchivoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idConvenio", idConvenio)
			.resolveTemplate("nombreUsuario", nombreUsuario)
			.resolveTemplate("idArchivoTerceroPagadorEfectivo", idArchivoTerceroPagadorEfectivo)
			.request(MediaType.APPLICATION_JSON)
			.post(informacionArchivoDTO == null ? null : Entity.json(informacionArchivoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 	public void setIdConvenio (Long idConvenio){
 		this.idConvenio=idConvenio;
 	}
 	
 	public Long getIdConvenio (){
 		return idConvenio;
 	}
  	public void setNombreUsuario (String nombreUsuario){
 		this.nombreUsuario=nombreUsuario;
 	}
 	
 	public String getNombreUsuario (){
 		return nombreUsuario;
 	}
  	public void setIdArchivoTerceroPagadorEfectivo (Long idArchivoTerceroPagadorEfectivo){
 		this.idArchivoTerceroPagadorEfectivo=idArchivoTerceroPagadorEfectivo;
 	}
 	
 	public Long getIdArchivoTerceroPagadorEfectivo (){
 		return idArchivoTerceroPagadorEfectivo;
 	}
  
  
  	public void setInformacionArchivoDTO (InformacionArchivoDTO informacionArchivoDTO){
 		this.informacionArchivoDTO=informacionArchivoDTO;
 	}
 	
 	public InformacionArchivoDTO getInformacionArchivoDTO (){
 		return informacionArchivoDTO;
 	}
  
}