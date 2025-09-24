package com.asopagos.afiliaciones.service;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.asopagos.afiliaciones.dto.*;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response; 
import javax.ws.rs.core.Context;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.dto.webservices.AfiliacionEmpleadorDTO;
import com.asopagos.dto.webservices.ResponseDTO;
import com.asopagos.afiliaciones.wsCajasan.dto.*;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import com.asopagos.dto.webservices.AfiliarPersonaACargoDTO;
import com.asopagos.dto.webservices.UsuarioDTO;
import com.asopagos.dto.webservices.AfiliaTrabajadorDepDTO;
import com.asopagos.dto.webservices.AfiliarTrabajadorIndDTO;
import com.asopagos.dto.webservices.AfiliaPensionadoDTO;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import javax.ws.rs.core.MediaType;
import com.asopagos.dto.webservices.ConsultaCargueDTO;
import com.asopagos.dto.InformacionArchivoDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con los servicios de integración para afiliación-afiliados y afiliación-empleadores
 *
 * @author Steven Quintero González <squintero@heinsohn.com.co>
 *
 */

@Path("externalAPI/afiliacion")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface IntegracionAfiliacionService {

    /**
     * Retorna información básica de una persona dada.
     * 
     * @param tipoID: Tipo de identificación del afiliado.
     * @param identificacion: Número de identificación del afiliado.
     *
     * @return List<InfoBasicaPersonaOutDTO> con la información solicitada.
     * 
     * @author squintero
     */
    @GET
    @Path("/obtenerInfoBasicaPersona")
    public Response obtenerInfoBasicaPersona(
            @NotNull @QueryParam("tipoID")TipoIdentificacionEnum tipoID,
            @NotNull @QueryParam("identificacion")String identificacion,
            @Context HttpServletRequest requestContext,
            @Context UserDTO userDTO);
    
    /**
     * Retorna información total de una persona dada.
     * 
     * @param tipoID: Tipo de identificación del afiliado.
     * @param identificacionAfiliado: Número de identificación del afiliado.
     * @param identificacionBeneficario: Número de identificación del beneficario.
     *
     * @return List<InfoTotalAfiliadoOutDTO> con la información solicitada.
     * 
     * @author squintero
     */
    @GET
    @Path("/obtenerInfoTotalAfiliado")
    public Response  obtenerInfoTotalAfiliado(
            @NotNull @QueryParam("tipoID")TipoIdentificacionEnum tipoID,
            @QueryParam("identificacionAfiliado")String identificacionAfiliado,
            @QueryParam("identificacionBeneficiario")String identificacionBeneficiario,
            @Context HttpServletRequest requestContext,
            @Context UserDTO userDTO);
    

    @GET
    @Path("/consultarAfiliadosPrincipalesPorBeneficiario")
    public List<InfoAfiliadosPrincipalesOutDTO> consultarAfiliadosPrincipalesPorBeneficiario(
            @NotNull @QueryParam("tipoID")TipoIdentificacionEnum tipoID,
            @NotNull @QueryParam("identificacion")String identificacion
            );

    /**
     * Retorna la información del estado de la categoria de la persona.
     * 
     * @param tipoID: Tipo de identificación de la persona.
     * @param identificacion: Número de identificación de la persona.
     * @param fechaInicio: Por defecto: Inicio del día actual.
     * @param fechaFinal: Por defecto: Final del día actual.
     *          
     * @return EstadoCategoriaPersonaOutDTO con la información solicitada.
     * 
     * @author squintero
     */
    @GET
    @Path("/obtenerEstadoCategoriaPersona")
    public Response obtenerEstadoCategoriaPersona(
            @NotNull @QueryParam("tipoID") TipoIdentificacionEnum tipoID,
            @NotNull @QueryParam("identificacion") String identificacion,
            @QueryParam("fechaInicio") String fechaInicio,
            @QueryParam("fechaFinal") String fechaFinal,
            @Context HttpServletRequest requestContext,
            @Context UserDTO userDTO);
    
    /**
     * Retorna la información del grupo familiar de la persona.
     *
     
     

     * @param tipoID: Tipo de identificación del afiliado.
     * @param identificacionAfiliado: Número de identificación del afiliado.
     * @param identificacionBeneficario: Número de identificación del beneficario.
     *          
     * @return GrupoFamiliarOutDTO con la información solicitada.
     *
     * 


     * 

     * 
     * 
     * 
     * @author squintero
     */
    @GET
    @Path("/obtenerGrupoFamiliar")
    public Response obtenerGrupoFamiliar(
            @NotNull @QueryParam("tipoID") TipoIdentificacionEnum tipoID,
            @QueryParam("identificacionAfiliado") String identificacionAfiliado,
            @QueryParam("identificacionBeneficiario") String identificacionBeneficiario,
            @Context HttpServletRequest requestContext,
            @Context UserDTO userDTO
            );
    
    /**
     * Retorna la información básica del empleador
     * 
     * @param tipoID: Tipo de identificación del empleador.
     * @param identificacion: Número de identificación del empleador. 
     *          
     * @return InfoBasicaEmpleadorOutDTO con la información solicitada.
     * 
     * @author squintero
     */
    @GET
    @Path("/obtenerInfoBasicaEmpleador")
    public Response obtenerInfoBasicaEmpleador(
            @NotNull @QueryParam("tipoID") TipoIdentificacionEnum tipoID,
            @NotNull @QueryParam("identificacion") String identificacion,
            @Context HttpServletRequest requestContext,
            @Context UserDTO userDTO
            );
    
    /**
     * Retorna la información completa del empleador
     * 
     * @param tipoID: Tipo de identificación del empleador.
     * @param identificacionEmpleador: Número de identificación del empleador. 
     * @param identificacionAfiliado: Número de identificación del afiliado.
     * @param codigoSucursal: Código de la sucursal.
     *          
     * @return InfoTotalEmpleadorOutDTO con la información solicitada.
     * 
     * @author squintero
     */
    @GET
    @Path("/obtenerInfoTotalEmpleador")
    public Response obtenerInfoTotalEmpleador(
            @NotNull @QueryParam("tipoID") TipoIdentificacionEnum tipoID,
            @QueryParam("identificacionEmpleador") String identificacionEmpleador,
            @QueryParam("identificacionAfiliado") String identificacionAfiliado,
            @QueryParam("codigoSucursal") String codigoSucursal,
            @Context HttpServletRequest requestContext,
            @Context UserDTO userDTO
            );
    
    /**
     * Retorna los datos de empleador y su información de contacto.
     * 
     * @param tipoID: Tipo de identificación del empleador.
     * @param identificacion: Número de identificación del empleador.
     * @param codigoSucursal: Código de la sucursal.
     *          
     * @return List<ContactosEmpleadorOutDTO> con la información solicitada.
     * 
     * @author squintero
     */
    @GET
    @Path("/obtenerContactosEmpleador")
    public Response obtenerContactosEmpleador(
            @NotNull @QueryParam("tipoID") TipoIdentificacionEnum tipoID,
            @NotNull @QueryParam("identificacion") String identificacion,
            @QueryParam("codigoSucursal") String codigoSucursal,
            @Context HttpServletRequest requestContext,
            @Context UserDTO userDTO
            );
    
    /**
     * Retorna la información respecto a un afiliado para el servicio Caja Sin Fronteras
     * 
     * @param tipoID: Tipo de identificación del afiliado.
     * @param identificacion: Número de identificación del afiliado.
     * @param codigoCaja: Códigode identificación nacional de la caja de compensación.
     * @param tipoAfiliado: Tipo de afiliado (TRABAJADOR_INDEPENDIENTE, TRABAJADOR_DEPENDIENTE, PENSIONADO)
     *          
     * @return List<InfoAfiliadoOutDTO> con la información solicitada.
     * 
     * @author squintero
     */
    @GET
    @Path("/obtenerInfoAfiliado")
    public Response obtenerInfoAfiliado(
            @NotNull @QueryParam("tipoID") TipoIdentificacionEnum tipoID,
            @NotNull @QueryParam("identificacion") String identificacion,
            @NotNull @QueryParam("codigoCaja") String codigoCaja,
            @NotNull @QueryParam("tipoAfiliado") String tipoAfiliado,
            @Context HttpServletRequest requestContext,
            @Context UserDTO userDTO);
    
    /**
     * Retorna la información total respecto a un beneficiario
     * 
     * @param tipoID: Tipo de identificación de la persona.
     * @param identificacionBeneficiario: Número de identificación del beneficiario.
     * @param identificacionAfiliado: Número de identificación de la persona.
     *          
     * @return List<InfoTotalBeneficiarioOutDTO> con la información solicitada.
     * 
     * @author squintero
     */
    @GET
    @Path("/obtenerInfoTotalBeneficiario")
    public Response obtenerInfoTotalBeneficiario(
            @NotNull @QueryParam("tipoID") TipoIdentificacionEnum tipoID,
            @QueryParam("identificacionBeneficiario") String identificacionBeneficiario,
            @QueryParam("identificacionAfiliado") String identificacionAfiliado,
            @Context HttpServletRequest requestContext,
            @Context UserDTO userDTO);
    
    
    
    /**
     * @param tipoID: Tipo de identificación de la persona.
     * @param identificacion: Número de identificación de la persona.
     * 
     * @return DatoHistoricoAfiliadoOutDTO
     * 
     * @author squintero
     */
    @GET
    @Path("/obtenerHistoricoAfiliado")
    public Response obtenerHistoricoAfiliado( @NotNull @QueryParam("tipoID") TipoIdentificacionEnum tipoID,
            @NotNull @QueryParam("identificacion") String identificacion, 
            @Context HttpServletRequest requestContext,
            @Context UserDTO userDTO);

    
    /**
     * @param tipoID: Tipo de identificación de la persona.
     * @param identificacionAfiliado : Número de identificación de la persona.
     * @param periodo: Número de identificación del beneficiario.
     * 
     * @return UltimoSalarioAfiliadoOutDTO
     * 
     * @author squintero
     */
    @GET
    @Path("/obtenerUltimoSalarioActivo")
    public Response obtenerUltimoSalarioActivo( @NotNull @QueryParam("tipoID") TipoIdentificacionEnum tipoID,
    		@NotNull @QueryParam("identificacionAfiliado") String identificacionAfiliado, 
    		@QueryParam("periodo") String periodo,@Context HttpServletRequest requestContext,
                @Context UserDTO userDTO);
    
    /**
     * @param ciudadID: Código del municipio. 
     * @param departamentoID: Código del departamento.
     * 
     * @return InfoCiudadOutDTO
     * 
     * @author squintero
     */
    @GET
    @Path("/obtenerInfoCiudad")
    public Response obtenerInfoCiudad(@NotNull @QueryParam("ciudadID") Integer ciudadID,
    		@QueryParam("departamentoID") Integer departamentoID,
                @Context HttpServletRequest requestContext,
                @Context UserDTO userDTO);
    
    /**
     * @param periodo: Periodo al que corresponde el salario
     * 
     * @return InfoSalarioMinimoOutDTO
     * 
     * @author squintero
     */
    @GET
    @Path("/obtenerSalarioMinimoLegal")
    public Response obtenerSalarioMinimoLegal(@NotNull @QueryParam("periodo") String periodo,
    @Context HttpServletRequest requestContext,
    @Context UserDTO userDTO);

    /**
     * @param tipoID: Tipo de identificación. 
     * @param identificacion: Número de identificación del afiliado.
     * 
     * @return InfoPadresBiologicosOutDTO
     * 
     * @author squintero
     */
    @GET
    @Path("/obtenerPadresBiologicosBeneficiario")
    public Response obtenerPadresBiologicosBeneficiario(@NotNull @QueryParam("tipoID") TipoIdentificacionEnum tipoID,
            @NotNull @QueryParam("identificacion") String identificacion,
            @Context HttpServletRequest requestContext,
            @Context UserDTO userDTO);


    @GET
    @Path("/consultaAfiliado")
    public ConsultaAfiliadoRecaudosPagosDTO consultaAfiliadoRecaudosPagos(@NotNull @QueryParam("TransactionId") String  idTransaccion,
    @NotNull @QueryParam("IdentificacionType") String tipoIdentificacion, 
    @NotNull @QueryParam("Identification") String numeroIdentificacion,
    @QueryParam("AdditionalData") String additionalData); 

        
    @POST
    @Path("/consultarInfoAfiliado")
    public Response consultarInfoAfiliado(
        ConsultaAfiliadoInDTO input,
        @Context HttpServletRequest requestContext,
        @Context UserDTO userDTO
    );

    @POST
    @Path("/consultarIdentificacionDinamico")
    public Response consultarIdentificacionDinamico(
           ConsultarInformacionDinamicoInDTO ConsultarInformacionDinamicoInDTO,
            @Context HttpServletRequest requestContext,
            @Context UserDTO userDTO
            );

        @POST
        @Path("/afiliaEmpresa")
        public Response orquestarAfiliacionEmpresa(@Valid AfiliacionEmpleadorDTO empleador, @Context HttpServletRequest requestContext,
                @Context UserDTO userDTO);

        @POST
        @Path("/buscarTarjeta")
        public Response buscarTarjeta(
                BuscarTarjetaInDTO input,
                @Context HttpServletRequest requestContext,
                @Context UserDTO userDTO
                );

        @POST
        @Path("/consultarAportes")
        public Response consultarAportes(
                ConsultarAportesInDTO consultante,
                @Context HttpServletRequest requestContext,
                @Context UserDTO userDTO
        );

        @POST
        @Path("/getMunicipiosCalendario")
        public Response getMunicipiosCalendario(
                GetMunicipiosCalendarioInDTO input,
                @Context HttpServletRequest requestContext,
                @Context UserDTO userDTO
        );

        @POST
        @Path("/afiliaPC")
        public Response afiliacionPC(@Valid AfiliarPersonaACargoDTO dataIn, @Context HttpServletRequest requestContext,
                @Context UserDTO userDTO);

        @POST
        @Path("/consultarPagosPC")
        public Response consultarPagosPC(
                ConsultarPagosPCInDTO input, 
                @Context HttpServletRequest requestContext,
                @Context UserDTO userDTO
        );

        @POST
        @Path("/actualizarDatos")
        public Response actualizarDatos(ActualizarDatosDTO dataIn, @Context HttpServletRequest requestContext,
                @Context UserDTO userDTO);

        @POST
        @Path("/validarEmpresa")
        public Response validarEmpresa(
                ValidaEmpresaInDTO input,
                @Context HttpServletRequest requestContext,
                @Context UserDTO userDTO
        );

        @POST
        @Path("/reestablecerClaveUsuario")
        public Response reestablecerClaveUsuario(UsuarioDTO usuario,@Context HttpServletRequest requestContext,@Context UserDTO userDTO);

        @POST
        @Path("/certificadoFosfec")
        public Response certificadoFosfec(
                ConsultaAfiliadoInDTO dataIn,
                @Context HttpServletRequest requestContext,
                @Context UserDTO userDTO
        );

        @POST
        @Path("/creacionUsuario")
        public Response orquestadorCreacionUsuario(UsuarioDTO usuario,@Context HttpServletRequest requestContext,@Context UserDTO userDTO);
        
        @POST
        @Path("/actualizarDatosUsuario")
        public Response actualizarDatosUsuario(ActualizarDatosUsuarioDTO dataIn,@Context HttpServletRequest requestContext,@Context UserDTO userDTO);

        @POST
        @Path("/redireccionamientoAutenticado")
        public Response redireccionamientoAutenticado(RedireccionarInDTO dataIn,@Context HttpServletRequest requestContext, @Context UserDTO userDTO);

        @POST
        @Path("/afiliaTrabajador")
        public Response afiliaTrabajadorDependiente(AfiliaTrabajadorDepDTO afiliadoIn,@Context HttpServletRequest requestContext, @Context UserDTO userDTO);

        @POST
        @Path("/aceptaEmpresa")
        public Response consultarSolicitudAfiliacionEmpresa(String numeroRadicado,@Context HttpServletRequest requestContext, @Context UserDTO userDTO);

        @POST
        @Path("/consultarEstadoCargue")
        public Response consultarEstadoCargueMasivo(ConsultaCargueDTO datosConsulta,@Context HttpServletRequest requestContext, @Context UserDTO userDTO);

        @POST
        @Path("/actualizarDatosEmpleadorWS")
        public Response actualizarDatosEmpleador(ActualizarDatosEmpleadorInDTO dataIn,@Context HttpServletRequest requestContext, @Context UserDTO userDTO);

        @POST
        @Path("/afiliarTrabajadorIndependiente")
        public Response orquestarAfiliacionIndependiente(AfiliarTrabajadorIndDTO afiliadoIn ,@Context HttpServletRequest requestContext, @Context UserDTO userDTO);

        @POST
        @Path("/afiliarPensionado")
        public Response afiliaPensionado(AfiliaPensionadoDTO afiliaPensionadoIn ,@Context HttpServletRequest requestContext, @Context UserDTO userDTO);

        @POST
        @Path("/actualizarDatosIdentificacion")
        @Consumes({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
        public Response actualizarDatosIdentificacion(@MultipartForm ActualizarDatosIdentificacionDTO dataIn ,@Context HttpServletRequest requestContext, @Context UserDTO userDTO);

        @POST
        @Path("/anexarArchivosAfiliacion")
        @Consumes({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON })
        public Response anexarArchivosAfiliacion(@MultipartForm InformacionArchivoDTO dataIn,@Context HttpServletRequest requestContext, @Context UserDTO userDTO);
}
