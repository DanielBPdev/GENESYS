package com.asopagos.afiliaciones.wsCajasan.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import com.asopagos.afiliaciones.wsCajasan.dto.ConsultarPagosPCInDTO;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;
import com.asopagos.dto.webservices.ResponseDTO;
import javax.ws.rs.client.Entity;

public class ConsultarPagosPC extends ServiceClient {

    private TipoIdentificacionEnum tipoDcto;
  	private String documento;

 	private ResponseDTO result;

 	public ConsultarPagosPC (TipoIdentificacionEnum tipoDcto,String documento){
 		super();
		this.tipoDcto = tipoDcto;
		this.documento = documento;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {

		ConsultarPagosPCInDTO input = new ConsultarPagosPCInDTO(tipoDcto, documento);

        return webTarget.path(path)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(input, MediaType.APPLICATION_JSON));
	}
	
    @Override
	protected void getResultData(Response response) {
		this.result = (ResponseDTO) response.readEntity(new GenericType<ResponseDTO>(){});
	}
	
	public ResponseDTO getResult() {
		return result;
	}

  	public void setTipoDcto (TipoIdentificacionEnum tipoDcto){
 		this.tipoDcto=tipoDcto;
 	}
 	
 	public TipoIdentificacionEnum getTipoDcto (){
 		return tipoDcto;
 	}
  	public void setDocumento (String documento){
 		this.documento = documento;
 	}
 	
 	public String getDocumento (){
 		return documento;
 	}
  
}
