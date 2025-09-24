package com.asopagos.entidaddescuento.composite.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import com.asopagos.entidaddescuento.dto.EntidadDescuentoModeloDTO;

/**
 * <b>Descripcion:</b> Interface que define los metodos de composicion para el modulo
 * de Entidad de Descuento <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */

@Path("EntidadDescuentoComposite")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface EntidadDescuentoCompositeService {

    /**
     * <b>Descripción:</b>Método que se encarga de crear la entidad de descuento si no existe el identificador
     * de negocio en la base de datos; si existe, se editara dicha entidad de descuento.
     * 
     * @param entidadDescuentoModeloDTO
     *        Entidad de descuento para ser creada o editada
     * 
     * @return id de la entidad de descuento.
     */
    @POST
    @Path("gestionarEntidadDescuentoComposite")
    public String gestionarEntidadDescuentoComposite(EntidadDescuentoModeloDTO entidadDescuentoModeloDTO);

    /**
     * <b>Descripción:</b>Método que se encarga de realizar la carga automática de los archivos de descuento
     * @author rlopez
     */
    @GET
    @Path("/cargarAutomaticamenteArchivosEntidadDescuento")
    public void cargarAutomaticamenteArchivosEntidadDescuentoComposite();
    
}
