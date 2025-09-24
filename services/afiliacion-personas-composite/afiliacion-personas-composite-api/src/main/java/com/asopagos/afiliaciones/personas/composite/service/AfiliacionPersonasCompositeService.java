package com.asopagos.afiliaciones.personas.composite.service;

import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import com.asopagos.afiliaciones.dto.IntentoAfiliacionInDTO;
import com.asopagos.afiliaciones.personas.composite.dto.AsignarSolicitudAfiliacionPersonaDTO;
import com.asopagos.dto.AfiliadoInDTO;
import com.asopagos.dto.AfiliarBeneficiarioDTO;
import com.asopagos.dto.CargueArchivoActualizacionDTO;
import com.asopagos.dto.GestionarProductoNoConformeSubsanableDTO;
import com.asopagos.dto.RadicarSolicitudAbreviadaDTO;
import com.asopagos.dto.VerificarProductoNoConformeDTO;
import com.asopagos.dto.VerificarRequisitosDocumentalesDTO;
import com.asopagos.dto.VerificarSolicitudAfiliacionPersonaDTO;
import com.asopagos.enumeraciones.ResultadoRegistroContactoEnum;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.dto.RadicarSolicitudAbreviadaDTO;
import com.asopagos.enumeraciones.TipoArchivoRespuestaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import javax.ws.rs.QueryParam;
import java.util.List;
import com.asopagos.dto.ResultadoArchivo25AniosDTO;
import com.asopagos.dto.afiliaciones.Afiliado25AniosDTO;
import com.asopagos.dto.afiliaciones.Afiliado25AniosExistenteDTO;
import com.asopagos.afiliaciones.personas.composite.dto.ListasPensionadosDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.dto.ResultadoValidacionArchivoTrasladoDTO;

/**
 * <b>Descripción:</b> Interfaz de servicios Web REST para composición de
 * afiliación de personas <b>Historia de Usuario:</b> HU-TRA-104
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Path("solicitudAfiliacionPersona")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface AfiliacionPersonasCompositeService {

    /**
     * Método que permite capturar los datos básicos de la persona e iniciar una
     * nueva instancia del proceso
     * 
     * @param inDTO
     * @return Mapa que contiene las variables de contexto
     */

    @POST
    @Path("validacionAfiliacionPersonasMasivas")
    public ResultadoValidacionArchivoTrasladoDTO validacionAfiliacionPersonasMasivas( @NotNull @QueryParam("tipoArchivo") TipoArchivoRespuestaEnum tipoArchivo, @NotNull @QueryParam("idEmpleador") String idEmpleador,
                                                     @NotNull CargueArchivoActualizacionDTO cargue, @Context UserDTO userDTO);


    @POST
    @Path("afilarPersonaMasivamente")
    public Map<String, Object> afilarPersonaMasivamente(AfiliadoInDTO afiliado,
                                                        @Context UserDTO userDTO, String archivo);

    @POST
    @Path("radicarPersonasmasivas")
    public ResultadoRegistroContactoEnum radicarSolicitudAbreviadaAfiliacionPersonasMasivas(RadicarSolicitudAbreviadaDTO inDTO,@Context UserDTO userDTO, Long idInstanciaProceso,String numeroRadicado);


    @POST
    @Path("digitarDatosIdentificacionPersona")
    public Map<String, Object> digitarDatosIdentificacionPersona(AfiliadoInDTO inDTO, @Context UserDTO userDTO);

    /**
     * Método que permite verificar los requisitos Documentales para la afiliación de personas
     * 
     * @param inDTO
     */
    @POST
    @Path("/verificarRequisitosDocumentales")
    public void verificarRequisitosDocumentalesPersona(VerificarRequisitosDocumentalesDTO inDTO, @Context UserDTO userDTO);


    
    /**
     * Método que permite radicar la solicitud de afiliación de personas de
     * forma abreviaba
     * 
     * @param inDTO
     */
    @POST
    @Path("radicarSolicitudAbreviadaAfiliacionPersona")
    public ResultadoRegistroContactoEnum radicarSolicitudAbreviadaAfiliacionPersona(RadicarSolicitudAbreviadaDTO inDTO, @Context UserDTO userDTO);

    /**
     * Método que permite registrar la afiliación de beneficiarios
     * 
     * @param inDTO
     * @return <code>Long</code> El identificador del beneficiario
     */
    @POST
    @Path("afiliarBeneficiario")
    public Long afiliarBeneficiario(AfiliarBeneficiarioDTO inDTO, @Context UserDTO userDTO);

    /**
     * Método que permite realizar la asignación de solicitudes de afiliación de
     * personas al back
     * 
     * @param inDTO
     */
    @POST
    @Path("asignarSolicitud")
    public void asignarSolicitudAfiliacionPersona(AsignarSolicitudAfiliacionPersonaDTO inDTO, @Context UserDTO userDTO);

    /**
     * Método que permite realizar la verificación en el back de una solicitud
     * de afiliación de personas
     * 
     * @param inDTO
     */
    @POST
    @Path("verificarSolicitud")
    public void verificarInformacionSolicitud(VerificarSolicitudAfiliacionPersonaDTO inDTO, @Context UserDTO userDTO);

    /**
     * Método que permite realizar la validación de beneficiarios de un afiliado
     * principal
     * 
     * @param inDTO
     * @return
     */
    @POST
    @Path("validarBeneficiario")
    public Object validarBeneficiario(@Context UserDTO userDTO);

    /**
     * Método que permite realizar la afiliación del afiliado principal
     * 
     * @param inDTO
     * @return
     */
    @POST
    @Path("afiliarAfiliadoPrincipal")
    public Object afiliarAfiliadoPrincipal(@Context UserDTO userDTO);

    /**
     * Método que permite realizar la productos no conformes en el front
     * 
     * @param inDTO
     */
    @POST
    @Path("gestionarProductoNoConforme")
    public void gestionarProductoNoConformeSubsanable(GestionarProductoNoConformeSubsanableDTO inDTO, @Context UserDTO userDTO);

    /**
     * Método que permite al back verificar los resultados de la gestión de
     * producto no conforme
     * 
     * @param inDTO
     */
    @POST
    @Path("verificarResultadoProductoNoConforme")
    public void verificarResultadosProductoNoConforme(VerificarProductoNoConformeDTO inDTO, @Context UserDTO userDTO);
    
    /**
     * Método que permite terminar una afiliacion por un intento de solicitud en beneficiario abreviado
     * 
     * @param IdTarea
     * @param inDTO
     */
    @POST
    @Path("terminarTareaAfiliacionPersonasIntento/{idTarea}")
    public void terminarTareaAfiliacionPersonasIntento(@NotNull @PathParam("idTarea") Long idTarea, IntentoAfiliacionInDTO intentoAfiliacionInDTO, @Context UserDTO userDTO);

    @POST
    @Path("/radicarSolicitudAbreviadaAfiliacionPersonaAfiliados")
    public void radicarSolicitudAbreviadaAfiliacionPersonaAfiliados(RadicarSolicitudAbreviadaDTO radicarSolicitudAbreviada,@Context UserDTO userDTO);

    @POST
    @Path("cargueArchivoPensionados25Anios")
    public ResultadoArchivo25AniosDTO cargueArchivoPensionados25Anios( @NotNull @QueryParam("tipoArchivo") TipoArchivoRespuestaEnum tipoArchivo,
                                                     @NotNull CargueArchivoActualizacionDTO cargue, @Context UserDTO userDTO);
    @POST
    @Path("/afiliacionPensionados25AniosMasivo")

    public void afiliacionPensionados25AniosMasivo(ListasPensionadosDTO listas, @Context UserDTO userDTO,
    @QueryParam("idCargue") Long idCargue);

    @POST
    @Path("/validacionAfiliacionPersonasMasivasNegocio")
   
    public void validacionAfiliacionPersonasMasivasNegocio(ResultadoValidacionArchivoTrasladoDTO resultDTO, @Context UserDTO userDTO);




}
