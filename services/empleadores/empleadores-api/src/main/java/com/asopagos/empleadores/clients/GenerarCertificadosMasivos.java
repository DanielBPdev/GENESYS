package com.asopagos.empleadores.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.core.TipoCertificadoMasivoEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Map;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

public class GenerarCertificadosMasivos extends ServiceClient{

    private List<Map<String,Object>> afiliados;

    private Long idEmpleador;

    private String dirigidoA;

    private TipoCertificadoMasivoEnum tipoCertificado;

    private Boolean esCargue ;

    private String nombreArchivo;

    private Response result;

    public GenerarCertificadosMasivos(List<Map<String,Object>> afiliados, Long idEmpleador, String dirigidoA, TipoCertificadoMasivoEnum tipoCertificado,Boolean esCargue,String nombreArchivo) {
        super();
        this.afiliados = afiliados;
        this.idEmpleador = idEmpleador;
        this.dirigidoA = dirigidoA;
        this.tipoCertificado = tipoCertificado;
        this.esCargue = esCargue;
        this.nombreArchivo = nombreArchivo;
    } 

    @Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
            .resolveTemplate("tipoCertificado",tipoCertificado)
            .queryParam("dirigido",dirigidoA)
            .queryParam("idEmpleador",idEmpleador)
            .queryParam("esCargue",esCargue)
            .queryParam("nombreArchivo",nombreArchivo)
			.request(MediaType.APPLICATION_JSON)
			.post(afiliados == null ? null : Entity.json(afiliados));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
		result = response;
	}

    public Response getResult(){
        return result;
    }
}
