package com.asopagos.afiliaciones.clients;

import java.lang.Long;
import com.asopagos.novedades.dto.ArchivoSupervivenciaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.entidades.ccf.afiliaciones.ArchivosTrasladosEmpresasCCF;

import com.asopagos.services.common.ServiceClient;



public class CrearArchivoTrasladosCFF extends ServiceClient {
      /** Atributo que almacena los datos resultado del llamado al servicio */
      private ArchivosTrasladosEmpresasCCF result;
      private Long IdCargueC;
      private Long idEmpleadorE;
      private String datosTemporales;

      public CrearArchivoTrasladosCFF( Long IdCargueC,Long idEmpleadorE, String datosTemporales) {
          super();
          this.IdCargueC = IdCargueC;
          this.idEmpleadorE = idEmpleadorE;
          this.datosTemporales = datosTemporales;
      }
  
      @Override
      protected Response invoke(WebTarget webTarget, String path) {
          Response response = webTarget.path(path)
                    .queryParam("IdCargueC", IdCargueC)
                    .queryParam("idEmpleadorE", idEmpleadorE )
                    .request(MediaType.APPLICATION_JSON).post(Entity.json(datosTemporales));
          return response;
      }
  
      @Override
      protected void getResultData(Response response) {
          result = (ArchivosTrasladosEmpresasCCF) response.readEntity(ArchivosTrasladosEmpresasCCF.class);
      }

    public ArchivosTrasladosEmpresasCCF getResult() {
        return this.result;
    }
      
    
}
