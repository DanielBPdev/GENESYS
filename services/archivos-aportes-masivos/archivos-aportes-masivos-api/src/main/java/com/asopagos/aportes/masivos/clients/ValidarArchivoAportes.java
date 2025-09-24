package com.asopagos.aportes.masivos.clients;

import com.asopagos.aportes.masivos.dto.ArchivoAportesDTO;
import com.asopagos.aportes.masivos.dto.ResultadoArchivoAporteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



import com.asopagos.services.common.ServiceClient;


/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesComposite/validarArchivoAportes
 */
public class ValidarArchivoAportes extends ServiceClient {

    private ArchivoAportesDTO archivoAporteDTO;

    private ResultadoArchivoAporteDTO result;

    public ValidarArchivoAportes(ArchivoAportesDTO archivoAporteDTO) {
        super();
        this.archivoAporteDTO = archivoAporteDTO;
    }

    @Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(archivoAporteDTO == null ? null : Entity.json(archivoAporteDTO));
		return response;
	}

    @Override
	protected void getResultData(Response response) {
		result = (ResultadoArchivoAporteDTO) response.readEntity(ResultadoArchivoAporteDTO.class);
	}



    public ArchivoAportesDTO getArchivoAporteDTO() {
        return this.archivoAporteDTO;
    }

    public void setArchivoAporteDTO(ArchivoAportesDTO archivoAporteDTO) {
        this.archivoAporteDTO = archivoAporteDTO;
    }

    public ResultadoArchivoAporteDTO getResult() {
        return this.result;
    }

    public void setResult(ResultadoArchivoAporteDTO result) {
        this.result = result;
    }
    
}
