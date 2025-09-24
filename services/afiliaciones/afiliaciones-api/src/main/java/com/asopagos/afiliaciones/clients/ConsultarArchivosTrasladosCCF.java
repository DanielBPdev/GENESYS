package com.asopagos.afiliaciones.clients;

import java.lang.Long;
import com.asopagos.novedades.dto.ArchivoSupervivenciaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.entidades.ccf.afiliaciones.ArchivosTrasladosEmpresasCCF;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesCargueMultiple/actualizarCargueSupervivencia
 */
public class ConsultarArchivosTrasladosCCF extends ServiceClient {

    /** Atributo que almacena los datos resultado del llamado al servicio */
    private String numeroDocumentoEmpleador;
    private TipoIdentificacionEnum tipoDocumentoEmpleador;
    private List<ArchivosTrasladosEmpresasCCF> result;

    public ConsultarArchivosTrasladosCCF(String numeroDocumentoEmpleador,
    TipoIdentificacionEnum tipoDocumentoEmpleador) {
        super();
        this.numeroDocumentoEmpleador = numeroDocumentoEmpleador;
        this.tipoDocumentoEmpleador = tipoDocumentoEmpleador;
    }

    @Override
    protected Response invoke(WebTarget webTarget, String path) {
        Response response = webTarget.path(path)
                .queryParam("numeroDocumentoEmpleador", numeroDocumentoEmpleador)
                .queryParam("tipoDocumentoEmpleador", tipoDocumentoEmpleador )
                .request(MediaType.APPLICATION_JSON).get();
        return response;
    }

    @Override
    protected void getResultData(Response response) {
        result = (List<ArchivosTrasladosEmpresasCCF>) response.readEntity(new GenericType<List<ArchivosTrasladosEmpresasCCF>>(){});
    }

    /**
     * Retorna el resultado del llamado al servicio
     */
    public List<ArchivosTrasladosEmpresasCCF> getResult() {
        return result;
    }



}