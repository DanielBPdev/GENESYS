package com.asopagos.aportes.composite.service;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import com.asopagos.aportes.composite.dto.ProcesoIngresoUtilitarioDTO;
import com.asopagos.aportes.composite.dto.ProcesoNovedadIngresoDTO;
import com.asopagos.aportes.composite.dto.RegistrarNovedadesPilaServiceDTO;
import com.asopagos.aportes.dto.AporteDTO;
import com.asopagos.aportes.dto.DatosComunicadoPlanillaDTO;
import com.asopagos.aportes.dto.ModificarTasaInteresMoraDTO;
import com.asopagos.aportes.dto.NovedadesProcesoAportesDTO;
import com.asopagos.dto.ActivacionEmpleadorDTO;
import com.asopagos.dto.NovedadPilaDTO;
import com.asopagos.dto.cartera.NovedadCarteraDTO;
import com.asopagos.dto.modelo.TasasInteresMoraModeloDTO;
import com.asopagos.enumeraciones.aportes.PilaAccionTransitorioEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.aportes.composite.dto.RegistrarNovedadConTransaccionDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con el proceso de registro o relación de aportes
 * 
 * <b>Módulo:</b> Asopagos - HU-211-397, HU-211-403, HU-211-404, HU-211-405, HU-211-399, HU-211-392<br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
@Path("AportesComposite")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface AportesCompositeService {

    /**
     * permite realizar el registro de los aportes desde el ESB
     * DTO <code>registrarRelacionarAporte</code>
     * 
     * @param List<AporteDTO>
     *        el listado de aportes a registrar.
     * 
     * @return el id del aporte registrado.
     */
    @POST
    @Path("registrarRelacionarListadoAportes")
    public List<Long> registrarRelacionarListadoAportes(@NotNull @Size(min = 1) List<AporteDTO> aportes);

    /**
     * realiza una serie de validaciones previas al envio del comunicado por planilla
     * 
     * @param idPlanilla
     *        el id de la planilla procesada
     * @return int con la confirmación de envío.
     */
    @POST
    @Path("enviarComunicadoPila")
    public Long enviarComunicadoPila(DatosComunicadoPlanillaDTO datosComunicadoPlanillaDTO);

    /**
     * Registra las novedades, tanto futuras como huerfanas, para el proceso de pila.
     * 
     * @param novedadesProcesoAportesDTO
     *        DTO que contiene las novedades a registrar y la información necesaria para realizar dicho proceso.
     * 
     * @return el listado de id's de las novedades procesadas.
     */
    @POST
    @Path("registrarNovedadesFuturasIndependientes")
    public List<Long> registrarNovedadesFuturasIndependientes(List<NovedadesProcesoAportesDTO> novedadesProcesoAportes,@Context UserDTO userDTO);

    /**
     * Servicio que registra las novedades incluidas para un cotizante
     * @param listaNovedades
     *        Información de las novedades a registrar
     */
    @POST
    @Path("registrarNovedadesCartera")
    void registrarNovedadesCartera(@NotNull NovedadCarteraDTO listaNovedades);

    /**
     * Método encargado de evaluar los beneficiarios que serán reactivados junto con el afiliado reintegrado
     * @param tipoIdCotizante
     *        número de documento de identificación del afiliado
     * @param numeroIdCotizante
     *        tipo de documento de identificación del afiliado
     */
    @POST
    @Path("reintegrarGrupoFamiliar")
    public void reintegrarGrupoFamiliar(@NotNull @QueryParam("tipoIdCotizante") TipoIdentificacionEnum tipoIdCotizante,
            @NotNull @QueryParam("numeroIdCotizante") String numeroIdCotizante,
            @NotNull @QueryParam("fechaRetiroAfiliado") Date fechaRetiroAfiliado,
            @NotNull @QueryParam("fechaIngresoAfiliado") Date fechaIngresoAfiliado,
            @NotNull @QueryParam("tipoCotizante") TipoAfiliadoEnum tipoCotizante,
            @QueryParam("tipoIdEmpleador") TipoIdentificacionEnum tipoIdEmpleador, 
            @QueryParam("numeroIdEmpleador") String numeroIdEmpleador,
            @Context UserDTO userDTO);

    /**
     * Servicio encargado de procesar las novedades de ingreso por aportes (PILA, Aporte Manual, Devolución, Corrección
     * @param datosProcesoIng
     *        DTO que contiene los datos para el procesamiento del reintegro
     */
    @POST
    @Path("procesarNovedadIngresoAporte")
    public Boolean procesarNovedadIngresoAporte(ProcesoNovedadIngresoDTO datosProcesoIng,@Context UserDTO userDTO);

    /**
     * Servicio encargado de procesar el reintegro de un empleador ya existente en el sistema
     * @param aporte
     * @param idEmpleador
     * @return
     */
    @POST
    @Path("procesarActivacionEmpleador")
    public Boolean procesarActivacionEmpleador(ActivacionEmpleadorDTO datosReintegro);

    /**
     * Servicio encargado de la preparación de los datos temporales de aportes para su respectivo registro
     */
    @POST
    @Path("prepararYProcesarPlanillas")
    public void prepararYProcesarPlanillas();
    
    /**
     * Metodo encargado del procesamiento de los aportes y las novedades por cada planilla enviada
     * este metodo es sincrono para procesar planillas de correccion.
     * @param indicePlanilla Indice de planilla para procesar sus aportes y novedades
     * @param userDTO 
     */
    @GET
    @Path("procesarAportesNovedadesByIdPlanillaSincrono")
    public void procesarAportesNovedadesByIdPlanillaSincrono(@NotNull @QueryParam("indicePlanilla") Long indicePlanilla);
    
    /**
     * Metodo encargado del procesamiento de los aportes y las novedades por cada planilla enviada.
     * @param indicePlanilla Indice de planilla para procesar sus aportes y novedades
     * @param userDTO 
     */
    @GET
    @Path("procesarAportesNovedadesByIdPlanilla")
    public void procesarAportesNovedadesByIdPlanilla(@NotNull @QueryParam("indicePlanilla") Long indicePlanilla);

    /**
     * 
     * @return
     */
    @POST
    @Path("/consultarTasasInteresMoraAportesComposite")
    public List<TasasInteresMoraModeloDTO> consultarTasasInteresMoraAportesComposite();
    
    @POST
    @Path("/modificarTasaInteresMoraComposite")
    public Boolean modificarTasaInteresMoraComposite(ModificarTasaInteresMoraDTO tasaModificada);
    
    @POST
    @Path("/crearTasaInteresInteresMoraComposite")
    public Boolean crearTasaInteresInteresMoraComposite(ModificarTasaInteresMoraDTO nuevaTasa);

    @POST
    @Path("/procesarNovedadIngresoUtilitario")
	public Boolean procesarNovedadIngresoUtilitario(ProcesoIngresoUtilitarioDTO datosProcesoIng);

    @POST
    @Path("/procesarListaIngresoUtilitario")
	public void procesarListaIngresoUtilitario(List<ProcesoIngresoUtilitarioDTO> listaPersonasIngresar);

    @POST
    @Path("/procesarNovedadRetiroUtilitario")
	public Boolean procesarNovedadRetiroUtilitario(ProcesoIngresoUtilitarioDTO datosProcesoIng);

    @GET
    @Path("/procesarNovedadesFuturasProcessSchedule")
	void procesarNovedadesFuturasProcessSchedule();

    /**
     * @param indicePlanilla
     * @param accion
     * @param userDTO
     */ 
    @POST
    @Path("/procesarPlanillaBandejaTransitoria")
	public void procesarPlanillaBandejaTransitoria(
			@NotNull @QueryParam("indicePlanilla") Long indicePlanilla,
			@NotNull @QueryParam("accion") PilaAccionTransitorioEnum accion,
			@Context UserDTO userDTO);
    
    /**
     * @param tipoIdentificacion 
     * @param numeroIdentificacion 
     * @param numeroPlanilla 
     * @param fechaInicio 
     * @param fechaFin 
     * @param userDTO
     */
    @POST
    @Path("/procesarTodosPlanillaBandejaTransitoria")
	public void procesarTodosPlanillaBandejaTransitoria(
            @QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, 
            @QueryParam("numeroIdentificacion") String numeroIdentificacion, 
            @QueryParam("numeroPlanilla") String numeroPlanilla,
            @QueryParam("fechaInicio") Long fechaInicio, 
            @QueryParam("fechaFin") Long fechaFin,
            @Context UserDTO userDTO);
    
    
    /**
     * @param regNovDTO
     * @param userDTO
     * @return a
     */
    @POST
    @Path("/registrarNovedadesPilaService")
    public List<Long> registrarNovedadesPilaService(
    		RegistrarNovedadesPilaServiceDTO regNovDTO, 
			@Context UserDTO userDTO
    		);
    
    /**
     * @param novedadPilaDTO 
     * @param canal 
     * @param tipoIdAportante 
     * @param numeroIdAportante 
     * @param personaCotizante 
     * @param esTrabajadorReintegrable 
     * @param esEmpleadorReintegrable 
     * @param transaccionNovedadDTO 
     * @param idsNovedadesProcesadas 
     * @param regNovDTO
     * @param userDTO
     * @throws Exception 
     */
    @POST
    @Path("/transaccionRegistrarNovedadService") 
    public void transaccionRegistrarNovedadService(           
            @QueryParam("canal") @NotNull CanalRecepcionEnum canal,
            @QueryParam("tipoIdAportante") @NotNull TipoIdentificacionEnum tipoIdAportante, 
            @QueryParam("numeroIdAportante") @NotNull String numeroIdAportante,           
            @QueryParam("esTrabajadorReintegrable") @NotNull Boolean esTrabajadorReintegrable, 
            @QueryParam("esEmpleadorReintegrable") @NotNull Boolean esEmpleadorReintegrable,            
            NovedadPilaDTO novedadPilaDTO,
            @Context UserDTO userDTO
            ) throws Exception;
    
    
    /**
     * Servicio encargado de procesar el reintegro de un empleador ya existente en el sistema
     * @param nombreAfiliado    
     * @param ti 
     * @param ni 
     * @return a
     */
    @POST
    @Path("crearAfiliado")
    public Long crearAfiliado(@QueryParam("nombreAfiliado") String nombreAfiliado,@QueryParam("ti")String ti,@QueryParam("ni") String ni);

    /**
     * Servicio para liberar planillas que se bloquean en bloque 9 
     * @param procesos
     *        Lista de procesos a los que se debe consultar la programación.
     * @return Lista de las parametrizaciones de ejecucion
     * @see com.asopagos.dto.modelo.ParametrizacionEjecucionProgramadaModeloDTO
     */
    @POST
    @Path("/liberarPlanillasBloque9Process")
    public void liberarPlanillasBloque9Process();
    
    @POST
    @Path("/registrarNovedadConTipoTransaccionAportesComposite")
    public void registrarNovedadConTipoTransaccionAportesComposite(RegistrarNovedadConTransaccionDTO registrarNovedadConTransaccionDTO, @QueryParam("canal") CanalRecepcionEnum canal,
            @QueryParam("tipoIdAportante") TipoIdentificacionEnum tipoIdAportante, @QueryParam("numeroIdAportante") String numeroIdAportante,
            @QueryParam("esTrabajadorReintegrable") Boolean esTrabajadorReintegrable, @QueryParam("esEmpleadorReintegrable") Boolean esEmpleadorReintegrable);
    @POST
    @Path("/registrarNovedadConTipoTransaccionAportesCompositeAsync")
    public void registrarNovedadConTipoTransaccionAportesCompositeAsync(RegistrarNovedadConTransaccionDTO registrarNovedadConTransaccionDTO, @QueryParam("canal") CanalRecepcionEnum canal,
            @QueryParam("tipoIdAportante") TipoIdentificacionEnum tipoIdAportante, @QueryParam("numeroIdAportante") String numeroIdAportante,
            @QueryParam("esTrabajadorReintegrable") Boolean esTrabajadorReintegrable, @QueryParam("esEmpleadorReintegrable") Boolean esEmpleadorReintegrable);
}
