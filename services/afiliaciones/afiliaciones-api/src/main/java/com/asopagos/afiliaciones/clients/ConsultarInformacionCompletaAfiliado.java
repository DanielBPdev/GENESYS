package com.asopagos.afiliaciones.clients;

import com.asopagos.afiliaciones.dto.ConsultarInformacionCompletaAfiliadoDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.services.common.ServiceClient;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Alexander.camelo
 */
/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/afiliacion/consultarInformacionCompletaAfiliado
 */
public class ConsultarInformacionCompletaAfiliado extends ServiceClient {
    
  
  	private TipoIdentificacionEnum tipoIdentificacionAfiliado;
  	private String numeroIdentificacionAfiliado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ConsultarInformacionCompletaAfiliadoDTO> result;
  
 	public ConsultarInformacionCompletaAfiliado (TipoIdentificacionEnum tipoIdentificacionAfiliado,String numeroIdentificacionAfiliado){
 		super();
		this.tipoIdentificacionAfiliado=tipoIdentificacionAfiliado;
		this.numeroIdentificacionAfiliado=numeroIdentificacionAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
                .queryParam("tipoIdentificacionAfiliado", tipoIdentificacionAfiliado)
                .queryParam("numeroIdentificacionAfiliado", numeroIdentificacionAfiliado)
                .request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ConsultarInformacionCompletaAfiliadoDTO>) response.readEntity(new GenericType<List<ConsultarInformacionCompletaAfiliadoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
     * @return 
	 */
	 public List<ConsultarInformacionCompletaAfiliadoDTO> getResult() {
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
