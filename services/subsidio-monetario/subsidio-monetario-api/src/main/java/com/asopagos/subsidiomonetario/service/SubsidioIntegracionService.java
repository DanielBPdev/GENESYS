package com.asopagos.subsidiomonetario.service;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;
import com.asopagos.dto.subsidiomonetario.pagos.InformacionAdminSubsidioDTO;
import com.asopagos.dto.subsidiomonetario.pagos.InformacionLiquidacionFallecimientoDTO;
import com.asopagos.dto.subsidiomonetario.pagos.SubsidioAfiliadoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import javax.ws.rs.core.Response; 
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import com.asopagos.rest.security.dto.UserDTO;


/**
 * <b>Descripcion:</b> Clase que define los servicios de integración para el proceso de SubsidioMonetario<br/>
 * <b>Módulo:</b> Asopagos - HU-<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@Path("externalAPI/subsidio")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface SubsidioIntegracionService {

    /**
     * Método encargado de obtener la información asociada a la Cuota Monetaria por Afiliado.
     * @author mosorio
     * @param tipoIdPersona
     *        <code>TipoIdentificacionEnum</code>
     *        Tipo de identificación del afiliado o del beneficiario
     * @param numeroIdPersona
     *        <code>String</code>
     *        Número de identificación del afiliado o del beneficiario
     * @param periodo
     *        <code>String</code>
     * @return La información de la cuota monetaria por afiliado o beneficiario
     */
    @GET
    @Path("/obtenerCuotaMonetaria")
    public Response obtenerCuotaMonetaria(@NotNull @QueryParam("tipoIdPersona") TipoIdentificacionEnum tipoIdPersona,
            @QueryParam("numeroIdPersona") String numeroIdPersona , @NotNull @QueryParam("periodo") String periodo,
            @Context HttpServletRequest requestContext,
            @Context UserDTO userDTO);

    /**
     * Metodo encargado de obtener la información del subsidio por Afiliado
     * @author mosorio
     * @param tipoIdPersona
     *        <code>TipoIdentificacionEnum</code>
     *        Tipo de identificación del afiliado o beneficiario
     * @param numeroIdAfiliado
     *        <code>String</code>
     *        Número de identificación del afiliado
     * @param numeroIdBeneficiario
     *        <code>String</code>
     *        Número de identificación del beneficiario        
     * @return información del subsidio por afiliado o beneficiario
     */
    @GET
    @Path("/obtenerInfoSubsidio")
    public Response obtenerInfoSubsidio(@NotNull @QueryParam("tipoIdPersona") TipoIdentificacionEnum tipoIdPersona,
            @QueryParam("numeroIdAfiliado") String numeroIdAfiliado, @QueryParam("numeroIdBeneficiario") String numeroIdBeneficiario,
            @Context HttpServletRequest requestContex,
            @Context UserDTO userDTO);


    /**
     * Metodo encargado de obtener la información
     * @author mosorio
     * @param tipoIdAdminSubsidio
     *        <code>TipoIdentificacionEnum</code>
     *        Tipo de identificación del administrador del subsidio
     * @param numeroIdAdminSubsidio
     *        <code>String</code>
     *        Número de identificación del administrador del subsidio
     * @return información del administrador del subsidio y sus grupos familiares
     */
    @GET
    @Path("/obtenerInfoAdministradorSubsidio")
    public Response obtenerInfoAdministradorSubsidio(
            @NotNull @QueryParam("tipoIdAdminSubsidio") TipoIdentificacionEnum tipoIdAdminSubsidio,
            @QueryParam("numeroIdAdminSubsidio") String numeroIdAdminSubsidio,
            @Context HttpServletRequest requestContext,
            @Context UserDTO userDTO);
    
    
    
    @GET
    @Path("/consultarLiquidacionFallecimiento")
    public List<InformacionLiquidacionFallecimientoDTO> consultarLiquidacionFallecimiento(
            @QueryParam("fechaInicio") Long fechaInicio,
            @QueryParam("fechaFin") Long fechaFin,
            @QueryParam("identificacion") String identificacion,
            @QueryParam("periodoRegular") Long periodoRegular,
            @QueryParam("numeroOperacion") String numeroOperacion,
            @QueryParam("medioPago") String medioPago,
            @QueryParam("tipoIdentificacion") String tipoIdentificacion,
            @QueryParam("fechaProgramada") Long fechaProgramada);  
}
