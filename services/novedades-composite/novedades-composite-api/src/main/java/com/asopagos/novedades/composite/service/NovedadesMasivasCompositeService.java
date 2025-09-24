package com.asopagos.novedades.composite.service;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.asopagos.dto.CargueArchivoActualizacionDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.enumeraciones.TipoArchivoRespuestaEnum;
import com.asopagos.novedades.dto.RespuestaValidacionArchivoDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import com.asopagos.rest.security.dto.UserDTO;
import javax.ws.rs.PathParam;
import java.io.IOException;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la gestión de novedades de una persoona o empleador GLPI 55721<b>Historia de
 * Usuario:</b> Proceso 1.3
 * 
 * @author Maria Cuellar <maria.cuellar@eprocess.com.co>
 */
@Path("novedadesMasivasComposite")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface NovedadesMasivasCompositeService {

    /**
     * Valida la estructura y contenido del archivo de retiro de trabajadores
     * @param tipoArchivo
     *        Indica el tipo de archivo de respuesta
     * @param cargue
     *        Contiene la informacion del cargue realizado
     * @param userDTO
     *        Usuario que realiza el cargue y el procesamiento del archivo
     */
    @POST
    @Path("/validarArchivoRetiroTrabajadores")
    public void validarArchivoRetiroTrabajadores(@NotNull @QueryParam("tipoArchivo") TipoArchivoRespuestaEnum tipoArchivo,
            @NotNull CargueArchivoActualizacionDTO cargue, @Context UserDTO userDTO);
    
    /**
     * Valida la estructura y contenido del archivo de reintegro de trabajadores
     * @param tipoArchivo
     *        Indica el tipo de archivo de respuesta
     * @param cargue
     *        Contiene la informacion del cargue realizado
     * @param userDTO
     *        Usuario que realiza el cargue y el procesamiento del archivo
     */
    @POST
    @Path("/validarArchivoReintegroTrabajadores")
    public void validarArchivoReintegroTrabajadores(@NotNull @QueryParam("tipoArchivo") TipoArchivoRespuestaEnum tipoArchivo,
            @NotNull CargueArchivoActualizacionDTO cargue, @Context UserDTO userDTO);
    
    @POST
    @Path("/procesarArchivoReintegroTrabajadorIndividual")
    public SolicitudNovedadDTO procesarArchivoReintegroTrabajadorIndividual(InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO,
            @Context UserDTO userDTO);
    
    @POST
    @Path("/procesarArchivoRetiroTrabajadorIndividual")
    public SolicitudNovedadDTO procesarArchivoRetiroTrabajadorIndividual(InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO,
            @Context UserDTO userDTO);
    
    @POST
    @Path("/procesarArchivoRetiroBenficiarioIndividual")
    public SolicitudNovedadDTO procesarArchivoRetiroBenficiarioIndividual(InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO,
            @Context UserDTO userDTO);
    
    /**
     * Valida la estructura y contenido del archivo de actualizar sucursal
     * @param tipoArchivo
     *        Indica el tipo de archivo de respuesta
     * @param cargue
     *        Contiene la informacion del cargue realizado
     * @param userDTO
     *        Usuario que realiza el cargue y el procesamiento del archivo
     */
    @POST
    @Path("/validarArchivoActualizarSucursal")
    public void validarArchivoActualizarSucursal(@NotNull @QueryParam("tipoArchivo") TipoArchivoRespuestaEnum tipoArchivo,
            @NotNull CargueArchivoActualizacionDTO cargue, @Context UserDTO userDTO);  
    
    @POST
    @Path("/procesarArchivoActualizacionSucursal")
    public SolicitudNovedadDTO procesarArchivoActualizacionSucursal(InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO,
            @Context UserDTO userDTO);
    
    /**
     * Valida la estructura y contenido del archivo de confirmación abonos bancarios
     * @param tipoArchivo
     *        Indica el tipo de archivo de respuesta
     * @param cargue
     *        Contiene la informacion del cargue realizado
     * @param userDTO
     *        Usuario que realiza el cargue y el procesamiento del archivo
     */
    @POST
    @Path("/validarArchivoConfirmacionAbonosBancario")
    public void validarArchivoConfirmacionAbonosBancario(@NotNull @QueryParam("tipoArchivo") TipoArchivoRespuestaEnum tipoArchivo,
            @NotNull CargueArchivoActualizacionDTO cargue, @Context UserDTO userDTO);
    
    @POST
    @Path("/procesarArchivoConfirmacionAbonosBancario")
    public void procesarArchivoConfirmacionAbonosBancario(InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO,
            @Context UserDTO userDTO);
    
    /**
     * Valida la estructura y contenido del archivo de sustitucion patronal - GLPI 62260
     * @param tipoArchivo
     *        Indica el tipo de archivo de respuesta
     * @param cargue
     *        Contiene la informacion del cargue realizado
     * @param userDTO
     *        Usuario que realiza el cargue y el procesamiento del archivo
     */
    @POST
    @Path("/validarArchivoSustitucionPatronal")
    public void validarArchivoSustitucionPatronal(@NotNull @QueryParam("tipoArchivo") TipoArchivoRespuestaEnum tipoArchivo,
            @NotNull CargueArchivoActualizacionDTO cargue, @Context UserDTO userDTO);
    
    @POST
    @Path("/procesarArchivoSustitucionPatronal")
    public SolicitudNovedadDTO procesarArchivoSustitucionPatronal(InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO,
            @Context UserDTO userDTO);
			
    /**
     * Valida la estructura y contenido del archivo de confirmación abonos bancarios
     * @param tipoArchivo
     *        Indica el tipo de archivo de respuesta
     * @param cargue
     *        Contiene la informacion del cargue realizado
     * @param userDTO
     *        Usuario que realiza el cargue y el procesamiento del archivo
     * @return SolicitudNovedadDTO
    */
	@POST
	@Path("/validarNameArchivoConfirmacionAbonosBancario")
		public RespuestaValidacionArchivoDTO validarNameArchivoConfirmacionAbonosBancario(
				@NotNull @QueryParam("tipoArchivo") TipoArchivoRespuestaEnum tipoArchivo,
				@NotNull CargueArchivoActualizacionDTO cargue,
				@Context UserDTO userDTO
		);
    
    /**
     * Valida el nombre del archivo de Sustitucion Patronal
     * @param tipoArchivo
     * @param cargue
     * @param userDTO
     * @return 
     */
    @POST
    @Path("/validarNameArchivoSustitucionPatronal")
    public RespuestaValidacionArchivoDTO validarNameArchivoSustitucionPatronal(
                    @NotNull @QueryParam("tipoArchivo") TipoArchivoRespuestaEnum tipoArchivo,
                    @NotNull CargueArchivoActualizacionDTO cargue,
                    @Context UserDTO userDTO
    );

        /**
         * Valida la estructura y contenido del archivo de confirmación abonos bancarios
         * @param tipoArchivo
         *        Indica el tipo de archivo de respuesta
         * @param cargue
         *        Contiene la informacion del cargue realizado
         * @param userDTO
         *        Usuario que realiza el cargue y el procesamiento del archivo
         * @return SolicitudNovedadDTO
         */
        @POST
        @Path("/validarNombreArchivoCambioMasivoTransferencia")
                public RespuestaValidacionArchivoDTO validarNombreArchivoCambioMasivoTransferencia(
                                @NotNull @QueryParam("tipoArchivo") TipoArchivoRespuestaEnum tipoArchivo,
                                @NotNull CargueArchivoActualizacionDTO cargue,
                                @Context UserDTO userDTO
                );

        @POST
	@Path("/VerificarEstructuraANovedadMasivaCMDT")
	public void validarArchivoCambioMasivoTransferencia(
                @NotNull @QueryParam("tipoArchivo") TipoArchivoRespuestaEnum tipoArchivo,@NotNull CargueArchivoActualizacionDTO cargue,@Context UserDTO userDTO);

                
        @POST
        @Path("/validarNombreArchivoCertificadosMasivos")
                public RespuestaValidacionArchivoDTO validarNombreArchivoCertificadosMasivos(
                                @NotNull @QueryParam("tipoArchivo") TipoArchivoRespuestaEnum tipoArchivo,
                                @NotNull CargueArchivoActualizacionDTO cargue,
                                @Context UserDTO userDTO
        );

        @POST
	@Path("{idEmpleador}/validarArchivoCertificadosMasivos")
	public void validarArchivoCertificadosMasivos(
                @NotNull @PathParam("idEmpleador") Long idEmpleador,
                @NotNull @QueryParam("tipoArchivo") TipoArchivoRespuestaEnum tipoArchivo,
                @NotNull CargueArchivoActualizacionDTO cargue,@Context UserDTO userDTO)throws IOException;
}
