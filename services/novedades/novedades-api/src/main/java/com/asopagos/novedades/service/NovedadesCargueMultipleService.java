/**
 * 
 */
package com.asopagos.novedades.service;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.ResultadoValidacionArchivoTrasladoDTO;
import com.asopagos.dto.ResultadoArchivo25AniosDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.dto.afiliaciones.Afiliado25AniosExistenteDTO;
import com.asopagos.dto.CargueArchivoActualizacionDTO;
import com.asopagos.dto.CargueMultipleDTO;
import com.asopagos.dto.DiferenciasCargueActualizacionDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.empleadorDatosDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.entidades.ccf.novedades.CargueArchivoActualizacion;
import com.asopagos.entidades.ccf.novedades.DiferenciasCargueActualizacion;
import com.asopagos.enumeraciones.TipoArchivoRespuestaEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoCargaMultipleEnum;
import com.asopagos.novedades.dto.ArchivoSupervivenciaDTO;
import com.asopagos.novedades.dto.RespuestaValidacionArchivoDTO;

import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.dto.ResultadoValidacionArchivoGestionUsuariosDTO;


/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la gestión de novedades, mediante el cargue multiple <b>Historia de
 * Usuario:449</b> Proceso 1.3.5
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 */
@Path("novedadesCargueMultiple")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface NovedadesCargueMultipleService {

	/**
	 * Servicio encargado de verificar la estructura del archivo de cargue
	 * multiple novedades
	 * 
	 * @param idEmpleador,
	 *            empleador al que pertenece el cargue
	 * @param idCargueMultiple,
	 *            id del Cargue realizado
	 * @param idSucursalEmpleador,
	 *            sucursal del empleador
	 * @param archivoMultiple,
	 *            archivo cargado
	 * @param userDTO,
	 *            usuario del contexto
	 * @return retorna un dto que contiene los casos si es exitoso el cargue o
	 *         no
	 */
	@POST
	@Path("verificarEstructuraArchivo/{idEmpleador}/{idCargueMultiple}/{idSucursalEmpleador}")
	public ResultadoValidacionArchivoDTO verificarEstructuraArchivo(@NotNull @PathParam("idEmpleador") Long idEmpleador,
			@NotNull @PathParam("idCargueMultiple") Long idCargueMultiple,
			@NotNull @PathParam("idSucursalEmpleador") Long idSucursalEmpleador,
			@NotNull InformacionArchivoDTO archivoMultiple, @Context UserDTO userDTO);

	/**
	 * Servicio para registrar un cargue múltiple
	 * 
	 * @param idEmpleador
	 * @param cargueMultipleDTO
	 */
	@POST
	@Path("/registrarCargue/{idEmpleador}")
	public Long registrarCargue(@NotNull @PathParam("idEmpleador") Long idEmpleador,
			CargueMultipleDTO cargueMultipleDTO, @Context UserDTO userDTO);

	/**
	 * Servicio encargado de actualizar el estado de cargue multiple
	 * 
	 * @param idCargue,
	 *            id de carga a realizar la actualizacion
	 * @param empleadorCargue
	 *            boolean que se encarga de identificar si es un empleador con
	 *            true
	 * @param estadoCargueMultiple,
	 *            estado a realizar la actualizacion
	 */
	@PUT
	@Path("/modificarEstadoCargueMultiple/{identificador}")
	public void modificarEstadoCargueMultiple(@NotNull @PathParam("identificador") Long identificador,
			@QueryParam("empleadorCargue") Boolean empleadorCargue, EstadoCargaMultipleEnum estadoCargueMultiple);

	/**
	 * Servicio encargado buscar de cargue del identificador multiple
	 * 
	 * @param idEmpleador,
	 *            id del empleador
	 * @return retorna el EstadoCargaMultiplePersonaEnum
	 */
	@GET
	@Path("/consultarIdentificadorCargueMultiple/{idEmpleador}")
	public EstadoCargaMultipleEnum consultarIdentificadorCargueMultiple(
			@NotNull @PathParam("idEmpleador") Long idEmpleador);

	/**
	 * Método encargado de verificar el archivo de supervivencia
	 * 
	 * @param archivoSuperVivenciaDTO,
	 *            DTO que contiene la información de los archivos a verificar la
	 *            estructura
	 * @param userDTO,
	 *            usuario del contexto
	 * @return retorna la lista de resultados de validacion
	 */
	@POST
	@Path("/verificarArchivoSupervivencia")
	public ResultadoValidacionArchivoDTO verificarArchivoSupervivencia(
			@NotNull ArchivoSupervivenciaDTO archivoSuperVivenciaDTO, @Context UserDTO userDTO);

	/**
	 * Método encargado de crear o modificar un cargue múltiple de supervivencia
	 * 
	 * @param cargueMultipleDTO,
	 *            Cargue Multiple DTO que se creara
	 * @param userDTO
	 * @return retorna el id del cargue multiple
	 */
	@POST
	@Path("/modificarCrearCargueSupervivencia")
	public Long modificarCrearCargueSupervivencia(ArchivoSupervivenciaDTO archivoSupervivenciaDTO, @Context UserDTO userDTO);
	
	/**
	 * Verifica la estructura del archivo de respuesta de actualizacion
	 * 
	 * @param idCargueMultiple
	 *            Identificador del cargue realizado
	 * @param tipoArchivoRespuesta
	 *            Tipo de archivo enviado de acuerdo a la selecion por pantalla
	 * @param archivo
	 *            Archivo cargado
	 * @return  Archvio verificado
	 */
	@POST
	@Path("/verificarEstructuraArchivoRespuesta/{tipoArchivoRespuesta}")
	public ResultadoValidacionArchivoDTO verificarEstructuraArchivoRespuesta(
			@NotNull @PathParam("tipoArchivoRespuesta") TipoArchivoRespuestaEnum tipoArchivoRespuesta,
			@NotNull InformacionArchivoDTO archivo);
	
    /**
     * Crea o modifica un registro de cargue de archivo actualizacion
     * @param cargueArchivoActualizacionDTO
     *        Informacion archivo
     * @return Identificador del archivo creado o modificado
     */
    @POST
    @Path("/crearCargueArchivoActualizacion")
    public Long crearCargueArchivoActualizacion(CargueArchivoActualizacionDTO cargueArchivoActualizacionDTO);

    /**
     * Crea o modifica el registro de diferencias de archivo de actualizacion
     * @param diferenciasCargueActualizacionDTO
     *        Informacion diferencia archivo
     * @return Identificador diferencia archivo
     */
    @POST
    @Path("/crearDiferenciaArchivo")
    public Long crearDiferenciaArchivoActualizacion(DiferenciasCargueActualizacionDTO diferenciasCargueActualizacionDTO);

    /**
     * Verifica la estructura del archivo que contiene la informacion de loas certificados escolares
     * @param archivo
     *        Informacion archivo
     * @return Informacion archivo verificado
     */
    @POST
    @Path("/verificarEstructuraArchivoCertificadoEscolar")
    public ResultadoValidacionArchivoDTO verificarEstructuraArchivoCertificadoEscolar(@NotNull InformacionArchivoDTO archivo);

    /**
     * Verifica la estructura del archivo que contiene la informacion de los beneficiarios que pueden ser pensionados
     * @param archivo
     *        Archivo a valdar
     * @return Informacion archivo verificado
     */
    @POST
    @Path("/verificarEstructuraArchivoPensionado")
    public ResultadoValidacionArchivoDTO verificarEstructuraArchivoPensionado(@NotNull InformacionArchivoDTO archivo);

    /**
     * Consulta la informacion registrada de un archivo de actualizacion
     * @param idCargue
     *        Identificador del cargue
     * @return Cargue archivo actualizacion
     */
    @GET
    @Path("/consultarCargueArchivoActualizacion")
    public CargueArchivoActualizacionDTO consultarCargueArchivoActualizacion(@QueryParam("idCargue") Long idCargue);

    /**
     * Consulta la informacion registrada de uan diferencia de cargue de actualizacion
     * @param idDiferencia
     *        Identificador diferencia
     * @return Diferencia archivo actualizacion
     */
    @GET
    @Path("/consultarDiferenciaCargueArchivoActualizacion")
    public DiferenciasCargueActualizacionDTO consultarDiferenciaCargueArchivoActualizacion(@QueryParam("idDiferencia") Long idDiferencia);
    
    /**
	 * Verifica la estructura del archivo de retiro trabajadores
	 * 
	 * @param idCargueMultiple
	 *            Identificador del cargue realizado
	 * @param tipoArchivoRespuesta
	 *            Tipo de archivo enviado de acuerdo a la selecion por pantalla
	 * @param archivo
	 *            Archivo cargado
	 * @return  Archvio verificado
	 */
	@POST
	@Path("/verificarEstructuraArchivoRetiroTrabajadores/{tipoArchivoRespuesta}")
	public ResultadoValidacionArchivoDTO verificarEstructuraArchivoRetiroTrabajadores(
			@NotNull @PathParam("tipoArchivoRespuesta") TipoArchivoRespuestaEnum tipoArchivoRespuesta,
			@NotNull InformacionArchivoDTO archivo);

	/**
	 * Verifica la estructura del archivo de reintegro trabajadores
	 * 
	 * @param tipoArchivoRespuesta
	 *            Tipo de archivo enviado de acuerdo a la selecion por pantalla
	 * @param archivo
	 *            Archivo cargado
	 * @return  Archvio verificado
	 */
	@POST
	@Path("/verificarEstructuraArchivoReintegroTrabajadores/{tipoArchivoRespuesta}")
	public ResultadoValidacionArchivoDTO verificarEstructuraArchivoReintegroTrabajadores(
			@NotNull @PathParam("tipoArchivoRespuesta") TipoArchivoRespuestaEnum tipoArchivoRespuesta,
			@NotNull InformacionArchivoDTO archivo);		
        
        /**
	 * Verifica la estructura del archivo de actualizacion sucursal
	 * 
	 * @param tipoArchivoRespuesta
	 *            Tipo de archivo enviado de acuerdo a la selecion por pantalla
	 * @param archivo
	 *            Archivo cargado
	 * @return  Archvio verificado
	 */
	@POST
	@Path("/verificarEstructuraArchivoActualizacionSucursal/{tipoArchivoRespuesta}")
	public ResultadoValidacionArchivoDTO verificarEstructuraArchivoActualizacionSucursal(
			@NotNull @PathParam("tipoArchivoRespuesta") TipoArchivoRespuestaEnum tipoArchivoRespuesta,
			@NotNull InformacionArchivoDTO archivo);
        
        /**
	 * Verifica la estructura del archivo de actualizacion sucursal
	 * 
	 * @param tipoArchivoRespuesta
	 *            Tipo de archivo enviado de acuerdo a la selecion por pantalla
	 * @param archivo
	 *            Archivo cargado
	 * @return  Archvio verificado
	 */
	@POST
	@Path("/verificarEstructuraArchivoConfirmacionAB/{tipoArchivoRespuesta}")
	public ResultadoValidacionArchivoDTO verificarEstructuraArchivoConfirmacionAB(
			@NotNull @PathParam("tipoArchivoRespuesta") TipoArchivoRespuestaEnum tipoArchivoRespuesta,
			@NotNull InformacionArchivoDTO archivo);
        
        /**
	 * Verifica la estructura del archivo de sustitucion patronal
	 * 
	 * @param tipoArchivoRespuesta
	 *            Tipo de archivo enviado de acuerdo a la selecion por pantalla
	 * @param archivo
	 *            Archivo cargado
	 * @return  Archvio verificado
	 */
	@POST
	@Path("/verificarEstructuraArchivoSustitucionPatronal/{tipoArchivoRespuesta}")
	public ResultadoValidacionArchivoDTO verificarEstructuraArchivoSustitucionPatronal(
			@NotNull @PathParam("tipoArchivoRespuesta") TipoArchivoRespuestaEnum tipoArchivoRespuesta,
			@NotNull InformacionArchivoDTO archivo);

			 /**
	 * Verifica la estructura del archivo de retiro trabajadores
	 * 
	 * @param idCargueMultiple
	 *            Identificador del cargue realizado
	 * @param tipoArchivoRespuesta
	 *            Tipo de archivo enviado de acuerdo a la selecion por pantalla
	 * @param archivo
	 *            Archivo cargado
	 * @return  Archvio verificado
	 */
	@POST
	@Path("/VerificarEstructuraArchivoTrasladoAfiliacionMasivoCCF/{tipoArchivo}/{idEmpleador}")
	public ResultadoValidacionArchivoTrasladoDTO VerificarEstructuraArchivoTrasladoAfiliacionMasivoCCF(@NotNull @PathParam("tipoArchivo") TipoArchivoRespuestaEnum tipoArchivo,
	@NotNull @PathParam("idEmpleador") String idEmpleador, @NotNull InformacionArchivoDTO cargue, @Context UserDTO userDTO);

	@GET
	@Path("/ConsultarPersonaPensionado25Anios/{tipoDocumento}/{numeroDocumento}")
	public Afiliado25AniosExistenteDTO consultarPersonaPensionado25Anios(@NotNull @PathParam("tipoDocumento") TipoIdentificacionEnum tipoDocumento, @PathParam("numeroDocumento") String numeroDocumento, @Context UserDTO userDTO);


	/**
	* Verifica la estructura del archivo de retiro trabajadores
	 * 
	 * @param idCargueMultiple
	 *            Identificador del cargue realizado
	 * @param tipoArchivoRespuesta
	 *            Tipo de archivo enviado de acuerdo a la selecion por pantalla
	 * @param archivo
	 *            Archivo cargado
	 * @return  Archvio verificado
	 */
	@POST
	@Path("/VerificarEstructuraArchivoPensionado25Anios/{tipoArchivo}")
	public ResultadoArchivo25AniosDTO VerificarEstructuraArchivoPensionado25Anios(@NotNull @PathParam("tipoArchivo") TipoArchivoRespuestaEnum tipoArchivo, @NotNull InformacionArchivoDTO cargue, @Context UserDTO userDTO);

	/**
     * GLPI 82800 Gestion Crear Usuario Empleador Masivo
     *
     */
    @POST
    @Path("/verificarEstructuraArchivoEmpleador")
    public ResultadoValidacionArchivoGestionUsuariosDTO verificarEstructuraArchivoEmpleador(@NotNull InformacionArchivoDTO archivo);

    /**
     * GLPI 82800 Gestion Crear Usuario Persona Masivo
     *
     */
    @POST
    @Path("/verificarEstructuraArchivoPersona")
    public ResultadoValidacionArchivoGestionUsuariosDTO verificarEstructuraArchivoPersona(@NotNull InformacionArchivoDTO archivo);

    /**
     * GLPI 82800 Gestion Crear Usuario CCF Masivo
     *
     */
    @POST
    @Path("/verificarEstructuraArchivoCcf")
    public ResultadoValidacionArchivoGestionUsuariosDTO verificarEstructuraArchivoCcf(@NotNull InformacionArchivoDTO archivo);

	// ============================ Masiva Transferencia
	@POST
	@Path("/verificarEstructuraArchivoNCMT/{tipoArchivoRespuesta}")
	public ResultadoValidacionArchivoDTO verificarEstructuraArchivoNovedadMasivaCambioMedioDePagoTransferencia(
			@NotNull @PathParam("tipoArchivoRespuesta")TipoArchivoRespuestaEnum tipoArchivo,@NotNull InformacionArchivoDTO archivo);

	@POST
	@Path("/{idEmpleador}/verificarEstructuraArchivoCertificadosMasivos/{tipoArchivoRespuesta}")
	public ResultadoValidacionArchivoDTO verificarEstructuraArchivoCertificadosMasivos(
			@NotNull @PathParam("idEmpleador")Long idEmpleador,
			@NotNull @PathParam("tipoArchivoRespuesta")TipoArchivoRespuestaEnum tipoArchivo,@NotNull InformacionArchivoDTO archivo);

    /**
     * GLPI 96686 Novedad masiva cambio de tipo y número de identificación
     *
     */
    @POST
    @Path("/verificarEstructuraArchivoAfiliado")
    public ResultadoValidacionArchivoDTO verificarEstructuraArchivoAfiliado(@NotNull InformacionArchivoDTO archivo);

    @POST
    @Path("/verificarEstructuraArchivoBeneficiario")
    public ResultadoValidacionArchivoDTO verificarEstructuraArchivoBeneficiario(@NotNull InformacionArchivoDTO archivo);

	}
