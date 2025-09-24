package com.asopagos.afiliaciones.clients;

import com.asopagos.afiliaciones.dto.InfoPersonasEnRelacionHijosOutDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliaciones/ObtenerPersonasEnRelacionHijos
 */
public class ObtenerPersonasEnRelacionHijos extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoIdentificacionAfiliado;
  	private String numeroIdentificacionAfiliado;
	private Boolean tipo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InfoPersonasEnRelacionHijosOutDTO result;
  
 	public ObtenerPersonasEnRelacionHijos (TipoIdentificacionEnum tipoIdentificacionAfiliado,String numeroIdentificacionAfiliado,
	Boolean tipo){
 		super();
		this.tipoIdentificacionAfiliado=tipoIdentificacionAfiliado;
		this.numeroIdentificacionAfiliado=numeroIdentificacionAfiliado;
		this.tipo=tipo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdentificacionAfiliado", tipoIdentificacionAfiliado)
						.queryParam("numeroIdentificacionAfiliado", numeroIdentificacionAfiliado)
						.queryParam("tipo", tipo)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (InfoPersonasEnRelacionHijosOutDTO) response.readEntity(InfoPersonasEnRelacionHijosOutDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public InfoPersonasEnRelacionHijosOutDTO getResult() {
		return result;
	}


    public TipoIdentificacionEnum getTipoIdentificacionAfiliado() {
        return this.tipoIdentificacionAfiliado;
    }

    public void setTipoIdentificacionAfiliado(TipoIdentificacionEnum tipoIdentificacionAfiliado) {
        this.tipoIdentificacionAfiliado = tipoIdentificacionAfiliado;
    }

    public String getNumeroIdentificacionAfiliado() {
        return this.numeroIdentificacionAfiliado;
    }

    public void setNumeroIdentificacionAfiliado(String numeroIdentificacionAfiliado) {
        this.numeroIdentificacionAfiliado = numeroIdentificacionAfiliado;
    }
    public void setResult(InfoPersonasEnRelacionHijosOutDTO result) {
        this.result = result;
    }

	public Boolean isTipo() {
		return this.tipo;
	}

	public Boolean getTipo() {
		return this.tipo;
	}

	public void setTipo(Boolean tipo) {
		this.tipo = tipo;
	}
  
}