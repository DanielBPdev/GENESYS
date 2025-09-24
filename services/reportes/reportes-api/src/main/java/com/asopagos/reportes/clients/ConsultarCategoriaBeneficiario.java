package com.asopagos.reportes.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.reportes.dto.CategoriaDTO;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/reportes/consultarCategoriaBeneficiario
 */
public class ConsultarCategoriaBeneficiario extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoIdBeneficiario;
  	private String numeroIdBeneficiario;
  	private Long idAfiliado;
  	private TipoBeneficiarioEnum tipoBeneficiario;
  	private Long idBenDetalle;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CategoriaDTO> result;
  
 	public ConsultarCategoriaBeneficiario (TipoIdentificacionEnum tipoIdBeneficiario,String numeroIdBeneficiario,Long idAfiliado,TipoBeneficiarioEnum tipoBeneficiario,Long idBenDetalle){
 		super();
		this.tipoIdBeneficiario=tipoIdBeneficiario;
		this.numeroIdBeneficiario=numeroIdBeneficiario;
		this.idAfiliado=idAfiliado;
		this.tipoBeneficiario=tipoBeneficiario;
		this.idBenDetalle=idBenDetalle;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdBeneficiario", tipoIdBeneficiario)
						.queryParam("numeroIdBeneficiario", numeroIdBeneficiario)
						.queryParam("idAfiliado", idAfiliado)
						.queryParam("tipoBeneficiario", tipoBeneficiario)
						.queryParam("idBenDetalle", idBenDetalle)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CategoriaDTO>) response.readEntity(new GenericType<List<CategoriaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CategoriaDTO> getResult() {
		return result;
	}

 
  	public void setTipoIdBeneficiario (TipoIdentificacionEnum tipoIdBeneficiario){
 		this.tipoIdBeneficiario=tipoIdBeneficiario;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdBeneficiario (){
 		return tipoIdBeneficiario;
 	}
  	public void setNumeroIdBeneficiario (String numeroIdBeneficiario){
 		this.numeroIdBeneficiario=numeroIdBeneficiario;
 	}
 	
 	public String getNumeroIdBeneficiario (){
 		return numeroIdBeneficiario;
 	}
  	public void setIdAfiliado (Long idAfiliado){
 		this.idAfiliado=idAfiliado;
 	}
 	
 	public Long getIdAfiliado (){
 		return idAfiliado;
 	}
  	public void setTipoBeneficiario (TipoBeneficiarioEnum tipoBeneficiario){
 		this.tipoBeneficiario=tipoBeneficiario;
 	}
 	
 	public TipoBeneficiarioEnum getTipoBeneficiario (){
 		return tipoBeneficiario;
 	}
  	public void setIdBenDetalle (Long idBenDetalle){
 		this.idBenDetalle=idBenDetalle;
 	}
 	
 	public Long getIdBenDetalle (){
 		return idBenDetalle;
 	}
  
}