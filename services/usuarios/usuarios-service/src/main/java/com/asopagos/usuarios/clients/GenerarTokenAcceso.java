package com.asopagos.usuarios.clients;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.services.common.ServiceClient;
import com.asopagos.usuarios.dto.TokenDTO;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/autenticacion/generarToken
 */
public class GenerarTokenAcceso extends ServiceClient { 
    private Short digitoVerificacion;
    private String numeroIdentificacion;
    private TipoIdentificacionEnum tipoIdentificacion;
   
    /** Atributo que almacena los datos resultado del llamado al servicio */
    private TokenDTO result;
  
    public GenerarTokenAcceso (Short digitoVerificacion,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
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
            .request(MediaType.APPLICATION_JSON)
            .post(null);
        return response;
    }
    
    @Override
    protected void getResultData(Response response) {
        result = (TokenDTO) response.readEntity(TokenDTO.class);
    }
    
    /**
     * Retorna el resultado del llamado al servicio
     */
    public TokenDTO getResult() {
        return result;
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