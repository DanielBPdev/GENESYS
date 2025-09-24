package com.asopagos.fovis.clients;


import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.fovis.dto.TareasHeredadasDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarTareasHeredadas
 */
public class ConsultarTareasHeredadas extends ServiceClient {


    private String numeroRadicacion;
    private String usuario;
    private String tipoTransaccion;

    /** Atributo que almacena los datos resultado del llamado al servicio */
    private List<TareasHeredadasDTO> result;

    public ConsultarTareasHeredadas(String numeroRadicacion, String usuario, String tipoTransaccion) {
        super();
        this.numeroRadicacion = numeroRadicacion;
        this.usuario = usuario;
        this.tipoTransaccion = tipoTransaccion;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("numeroRadicacion", numeroRadicacion)
                .queryParam("usuario", usuario)
                .queryParam("tipoTransaccion", tipoTransaccion)
                .request(MediaType.APPLICATION_JSON).get();
        return response;
    }


    @Override
    protected void getResultData(Response response) {
        this.result = (List<TareasHeredadasDTO>) response.readEntity(new GenericType<List<TareasHeredadasDTO>>(){});
    }

    /**
     * Retorna el resultado del llamado al servicio
     */
    public List<TareasHeredadasDTO> getResult() {
        return result;
    }


    public void setNumeroRadicacion (String numeroRadicacion){
        this.numeroRadicacion=numeroRadicacion;
    }

    public String getNumeroRadicacion(){
        return numeroRadicacion;
    }

    public void setUsuario (String usuario){
        this.usuario=usuario;
    }

    public String getUsuario(){
        return usuario;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }
}