package com.asopagos.personas.clients;

import com.asopagos.dto.modelo.MedioDePagoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/personas/guardarMedioDePago
 */
public class RegistrarTarjetaExpedicion extends ServiceClient{

    private MedioDePagoModeloDTO medioDePagoModeloDTO;

    private MedioDePagoModeloDTO result;

    public RegistrarTarjetaExpedicion (MedioDePagoModeloDTO medioDePagoModeloDTO){
        super();
       this.medioDePagoModeloDTO=medioDePagoModeloDTO;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
       Response response = webTarget.path(path)
           .request(MediaType.APPLICATION_JSON)
           .post(medioDePagoModeloDTO == null ? null : Entity.json(medioDePagoModeloDTO));
       return response;
    }
   
    @Override
    protected void getResultData(Response response) {
       result = (MedioDePagoModeloDTO) response.readEntity(MedioDePagoModeloDTO.class);
    }
   
   /**
    * Retorna el resultado del llamado al servicio
    */
    public MedioDePagoModeloDTO getResult() {
       return result;
    }


 
    public void setMedioDePagoModeloDTO (MedioDePagoModeloDTO medioDePagoModeloDTO){
        this.medioDePagoModeloDTO=medioDePagoModeloDTO;
    }
    
    public MedioDePagoModeloDTO getMedioDePagoModeloDTO (){
        return medioDePagoModeloDTO;
    }
    
}
