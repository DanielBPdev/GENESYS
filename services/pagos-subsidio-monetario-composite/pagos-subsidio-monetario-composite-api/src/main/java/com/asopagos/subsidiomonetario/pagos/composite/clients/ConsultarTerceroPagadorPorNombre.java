package com.asopagos.subsidiomonetario.pagos.composite.clients;

import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/PagosSubsidioMonetarioComposite/consultar/empresaConvenioTerceroPagador
 */
public class ConsultarTerceroPagadorPorNombre extends ServiceClient {


  	private String nombreTerceroPagador;

  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EmpresaModeloDTO result;

 	public ConsultarTerceroPagadorPorNombre(String nombreTerceroPagador){
 		super();
		this.nombreTerceroPagador=nombreTerceroPagador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("nombreTerceroPagador", nombreTerceroPagador)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (EmpresaModeloDTO) response.readEntity(EmpresaModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public EmpresaModeloDTO getResult() {
		return result;
	}


	public String getNombreTerceroPagador() {
		return nombreTerceroPagador;
	}

	public void setNombreTerceroPagador(String nombreTerceroPagador) {
		this.nombreTerceroPagador = nombreTerceroPagador;
	}
}