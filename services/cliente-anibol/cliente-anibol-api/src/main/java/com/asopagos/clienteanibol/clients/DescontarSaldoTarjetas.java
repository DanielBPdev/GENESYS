package com.asopagos.clienteanibol.clients;

import java.util.List;
import com.asopagos.clienteanibol.dto.ResultadoAnibolDTO;
import com.asopagos.clienteanibol.dto.SaldoTarjetaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/anibol/descuentoSaldoTarjeta
 */
public class DescontarSaldoTarjetas extends ServiceClient { 
    	private List<SaldoTarjetaDTO> saldoTarjetasDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoAnibolDTO result;
  
 	public DescontarSaldoTarjetas (List<SaldoTarjetaDTO> saldoTarjetasDTO){
 		super();
		this.saldoTarjetasDTO=saldoTarjetasDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(saldoTarjetasDTO == null ? null : Entity.json(saldoTarjetasDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ResultadoAnibolDTO) response.readEntity(ResultadoAnibolDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ResultadoAnibolDTO getResult() {
		return result;
	}

 
  
  	public void setSaldoTarjetasDTO (List<SaldoTarjetaDTO> saldoTarjetasDTO){
 		this.saldoTarjetasDTO=saldoTarjetasDTO;
 	}
 	
 	public List<SaldoTarjetaDTO> getSaldoTarjetasDTO (){
 		return saldoTarjetasDTO;
 	}
  
}