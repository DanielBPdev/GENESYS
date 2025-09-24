package com.asopagos.afiliados.clients;

import com.asopagos.dto.ConsultaBeneficiarioRequestDTO;
import com.asopagos.dto.InformacionBeneficiarioCompletaDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/obtenerInfoBeneficiario
 */
public class ObtenerInfoBeneficiario extends ServiceClient {
    private ConsultaBeneficiarioRequestDTO request;
    private InformacionBeneficiarioCompletaDTO result;
    
 	public ObtenerInfoBeneficiario(ConsultaBeneficiarioRequestDTO request) {
        super();
        this.request = request;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
        return webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(Entity.entity(request, MediaType.APPLICATION_JSON));
	}
	
	@Override
	protected void getResultData(Response response) {
        this.result = response.readEntity(InformacionBeneficiarioCompletaDTO.class);
	}
	
	public InformacionBeneficiarioCompletaDTO getResult() {
		return result;
	}
}