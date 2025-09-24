package com.asopagos.afiliaciones.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.dto.afiliaciones.Afiliado25AniosDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/afiliacion/consultarClasificacionPensionado
 */
public class ConsultarDatosExistenteNoPensionado extends ServiceClient {
 
  
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;

	private Afiliado25AniosDTO result;
  
 	public ConsultarDatosExistenteNoPensionado (String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion.name())
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
        this.result = (Afiliado25AniosDTO) response.readEntity(Afiliado25AniosDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Afiliado25AniosDTO getResult() {
		return result;
	}



    public String getNumeroIdentificacion() {
        return this.numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public TipoIdentificacionEnum getTipoIdentificacion() {
        return this.tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }
    


}