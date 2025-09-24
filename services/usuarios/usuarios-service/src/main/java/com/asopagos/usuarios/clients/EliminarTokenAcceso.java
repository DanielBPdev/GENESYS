package com.asopagos.usuarios.clients;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio DELETE
 * /rest/autenticacion/expirarToken
 */
public class EliminarTokenAcceso extends ServiceClient {
 
  
    private Short digitoVerificacion;
    private String numeroIdentificacion;
    private TipoIdentificacionEnum tipoIdentificacion;
  
  
    public EliminarTokenAcceso (Short digitoVerificacion,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
        super();
        this.digitoVerificacion=digitoVerificacion;
        this.numeroIdentificacion=numeroIdentificacion;
        this.tipoIdentificacion=tipoIdentificacion;
    }
 
    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                                    .queryParam("digitoVerificacion", digitoVerificacion)
                        .queryParam("numeroIdentificacion", numeroIdentificacion)
                        .queryParam("tipoIdentificacion", tipoIdentificacion)
                        .request(MediaType.APPLICATION_JSON).delete();
        return response;
    }

    @Override
    protected void getResultData(Response response) {
    }
    

 
    public void setDigitoVerificacion (Short digitoVerificacion){
        this.digitoVerificacion=digitoVerificacion;
    }
    
    public Short getDigitoVerificacion (){
        return digitoVerificacion;
    }
    public void setNumeroIdentificacion (String numeroIdentificacion){
        this.numeroIdentificacion=numeroIdentificacion;
    }
    
    public String getNumeroIdentificacion (){
        return numeroIdentificacion;
    }
    public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
        this.tipoIdentificacion=tipoIdentificacion;
    }
    
    public TipoIdentificacionEnum getTipoIdentificacion (){
        return tipoIdentificacion;
    }
  
}