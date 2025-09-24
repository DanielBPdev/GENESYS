package com.asopagos.comunicados.service;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.asopagos.comunicados.dto.CertificadoDTO;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.TipoCertificadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción:</b> Clase que define los servicios relacionados con la generación de certificados para empleadores y personas
 * <b>Módulo:</b> Asopagos - Vistas 360<br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero D.</a>
 */
@Path("certificados")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface CertificadosService {

    /**
     * Método que valida y genera los certificados.
     * @param tipoIdentificacion
     *        Tipo de identificación de la persona.
     * @param numeroIdentificacion
     *        Número de identificación de la persona.
     * @param tipoCertificado
     *        Tipo de certificado a generar.
     * @param dirigidoA
     *        Caja o entidad a la que va dirigido.
     * @param empleador
     *        Indica si es como empleador.
     * @param tipoAfiliado
     *        Tipo de afiliado para cuando el certificado es de afiliacion personas
     * @param idEmpleador
     *        Identificador del empleador paa cuando el certificado es por afilacion personas dependientes
     * @param anio
     *        Año del certificado para cuando es de aportes por año.
     * @return Listado con los datos de los certificados generados.
     */
    @POST
    @Path("generar")
    public CertificadoDTO generarCertificado(@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("idComunicado") Long idComunicado,
            @NotNull @QueryParam("tipoCertificado") TipoCertificadoEnum tipoCertificado, @NotNull @QueryParam("dirigidoA") String dirigidoA,
            @NotNull @QueryParam("empleador") Boolean empleador, @QueryParam("tipoAfiliado") TipoAfiliadoEnum tipoAfiliado,
            @QueryParam("idEmpleador") Long idEmpleador, @QueryParam("anio") Short anio, @QueryParam("etiqueta") EtiquetaPlantillaComunicadoEnum etiqueta, @QueryParam("idCertificado") Long idCertificado,
            @QueryParam("validaEstadoCartera") Boolean validaEstadoCartera, @QueryParam("tipoSolicitud") String tipoSolicitud);

    /**
     * Método que consulta los certificados generados.
     * @param tipoIdentificacion
     *        Tipo de identificación de la persona.
     * @param numeroIdentificacion
     *        Número de identificación de la persona.
     * @param empleador
     *        Indica si es como empleador.
     * @param tipoCertificado
     *        Tipo de certificado a consultar, si no se envia valor se toma como TODOS.
     * @return Listado con los datos de los certificados generados.
     */
    @GET
    @Path("consultar")
    public List<CertificadoDTO> consultarCertificados(@NotNull @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
            @NotNull @QueryParam("numeroIdentificacion") String numeroIdentificacion, @NotNull @QueryParam("empleador") Boolean empleador,
            @QueryParam("tipoCertificado") TipoCertificadoEnum tipoCertificado, @QueryParam("fechaInicio") Long fechaInicio, @QueryParam("fechaFin") Long fechaFinal);

    /**
     * Método que registra el envío de un comunicado de certificado.
     * @param idCertificado
     *        Identificador del registro de certificado generado.
     * @param idComunicado
     *        Identificador del comunicado enviado.
     */
    @POST
    @Path("enviar")
    public void registrarEnvioCertificado(@NotNull @QueryParam("idCertificado") Long idCertificado,
            @NotNull @QueryParam("idComunicado") Long idComunicado);

    //    /**
    //     * Método que registra el envío de un comunicado.
    //     * @param idCertificado
    //     *        Identificador del registro de certificado generado.
    //     * @param email
    //     *        Correo al cuál se envía el certificado.
    //     */
    //    @POST
    //    @Path("reenviar")
    //    public void registrarReenvioCertificado(@NotNull @QueryParam("idCertificado") Long idCertificado,
    //            @NotNull @QueryParam("email") String email);

    /**
     * Servicio encargado de actualuzar un certificado
     * 
     * @param etiqueta
     * @param idCertificado
     */
    @POST
    @Path("actualizarCertificado")
    public void actualizarCertificado(@QueryParam("etiqueta") EtiquetaPlantillaComunicadoEnum etiqueta, @QueryParam("idCertificado") Long idCertificado);
}
