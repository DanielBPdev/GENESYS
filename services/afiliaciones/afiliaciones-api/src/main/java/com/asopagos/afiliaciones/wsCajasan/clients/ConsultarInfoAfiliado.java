package com.asopagos.afiliaciones.wsCajasan.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.afiliaciones.wsCajasan.dto.ConsultaAfiliadoInDTO;
import com.asopagos.afiliaciones.wsCajasan.dto.ConsultaAfiliadoCajaSanOutDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;

import com.asopagos.services.common.ServiceClient;
import com.asopagos.dto.webservices.ResponseDTO;

public class ConsultarInfoAfiliado extends ServiceClient{
    private TipoIdentificacionEnum tipoIdentificacionAfiliado;
  	private String numeroIdentificacionAfiliado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ConsultaAfiliadoCajaSanOutDTO> result;
  
 	public ConsultarInfoAfiliado (TipoIdentificacionEnum tipoIdentificacionAfiliado,String numeroIdentificacionAfiliado){
 		super();
		this.tipoIdentificacionAfiliado=tipoIdentificacionAfiliado;
		this.numeroIdentificacionAfiliado=numeroIdentificacionAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		ConsultaAfiliadoInDTO input = new ConsultaAfiliadoInDTO();
		input.setTipoDto(tipoIdentificacionAfiliado);
		input.setNumeroIdentificacion(numeroIdentificacionAfiliado);
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(Entity.entity(input, MediaType.APPLICATION_JSON));

		return response;
	}

	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ConsultaAfiliadoCajaSanOutDTO>) response.readEntity(new GenericType<List<ConsultaAfiliadoCajaSanOutDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
     * @return 
	 */
	 public List<ConsultaAfiliadoCajaSanOutDTO> getResult() {
		return result;
	}

    public TipoIdentificacionEnum getTipoIdentificacionAfiliado() {
        return tipoIdentificacionAfiliado;
    }

    public void setTipoIdentificacionAfiliado(TipoIdentificacionEnum tipoIdentificacionAfiliado) {
        this.tipoIdentificacionAfiliado = tipoIdentificacionAfiliado;
    }

    public String getNumeroIdentificacionAfiliado() {
        return numeroIdentificacionAfiliado;
    }

    public void setNumeroIdentificacionAfiliado(String numeroIdentificacionAfiliado) {
        this.numeroIdentificacionAfiliado = numeroIdentificacionAfiliado;
    }
}
