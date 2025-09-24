package com.asopagos.novedades.clients;

import com.asopagos.services.common.ServiceClient;

import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity; 
import java.util.List;
import com.asopagos.enumeraciones.core.EstadoDesafiliacionMasivaEnum;

public class EmpleadoresProcesar extends ServiceClient{

    private List<Long> idEmpleadores;

    private String numeroRadicado;

    private EstadoDesafiliacionMasivaEnum estado;

    private int intentosDiarios;

    public EmpleadoresProcesar(List<Long> idEmpleadores, String numeroRadicado,EstadoDesafiliacionMasivaEnum estado, int intentosDiarios) {
        super();
        this.idEmpleadores = idEmpleadores;
        this.numeroRadicado = numeroRadicado;
        this.estado = estado;
        this.intentosDiarios = intentosDiarios;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("numeroRadicado", numeroRadicado)
                .queryParam("estado", estado)
                .queryParam("intentosDiarios", intentosDiarios)
                .request(MediaType.APPLICATION_JSON)
                .post(idEmpleadores == null ? null : Entity.json(idEmpleadores));
        return response;
    }

    @Override
	protected void getResultData(Response response) {}
	
    
}
