package com.asopagos.afiliaciones.clients;

import com.asopagos.afiliaciones.dto.ConsultarInformacionCompletaBeneficiarioDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.afiliaciones.dto.InfoBasicaPersonaOutDTO;
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
 * /rest/externalAPI/afiliacion/consultarInformacionCompletaBeneficiario
 */
public class ConsultarInformacionCompletaBeneficiario extends ServiceClient {
    
  
  	private TipoIdentificacionEnum tipoIdentificacionBeneficiario;
  	private String numeroIdentificacionBeneficiario;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ConsultarInformacionCompletaBeneficiarioDTO> result;
  
 	public ConsultarInformacionCompletaBeneficiario (TipoIdentificacionEnum tipoIdentificacionBeneficiario,String numeroIdentificacionBeneficiario){
 		super();
		this.tipoIdentificacionBeneficiario=tipoIdentificacionBeneficiario;
		this.numeroIdentificacionBeneficiario=numeroIdentificacionBeneficiario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
                .queryParam("tipoIdentificacionBeneficiario", tipoIdentificacionBeneficiario)
                .queryParam("numeroIdentificacionBeneficiario", numeroIdentificacionBeneficiario)
                .request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ConsultarInformacionCompletaBeneficiarioDTO>) response.readEntity(new GenericType<List<ConsultarInformacionCompletaBeneficiarioDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
     * @return 
	 */
	 public List<ConsultarInformacionCompletaBeneficiarioDTO> getResult() {
		return result;
	}

    public TipoIdentificacionEnum getTipoIdentificacionBeneficiario() {
        return tipoIdentificacionBeneficiario;
    }

    public void setTipoIdentificacionBeneficiario(TipoIdentificacionEnum tipoIdentificacionBeneficiario) {
        this.tipoIdentificacionBeneficiario = tipoIdentificacionBeneficiario;
    }

    public String getNumeroIdentificacionBeneficiario() {
        return numeroIdentificacionBeneficiario;
    }

    public void setNumeroIdentificacionBeneficiario(String numeroIdentificacionBeneficiario) {
        this.numeroIdentificacionBeneficiario = numeroIdentificacionBeneficiario;
    }

 

}
