package com.asopagos.constantes.parametros.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.asopagos.constantes.parametros.dto.AreaCajaCompensacionDTO;
import com.asopagos.constantes.parametros.dto.ConstantesCajaCompensacionDTO;
import com.asopagos.constantes.parametros.dto.ConstantesParametroDTO;
import com.asopagos.constantes.parametros.dto.ParametrizacionGapsDTO;
import com.asopagos.dto.ConexionOperadorInformacionDTO;
import com.asopagos.entidades.ccf.core.OperadorInformacion;
import com.asopagos.entidades.ccf.general.Parametro;
import com.asopagos.entidades.transversal.core.ParametrizacionGaps;
import com.asopagos.enumeraciones.core.SubCategoriaParametroEnum;
import com.asopagos.constantes.parametros.dto.ConstantesValorUVTDTO;
import com.asopagos.entidades.ccf.general.ParametrizacionValorUVT;
import com.asopagos.rest.security.dto.UserDTO;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;

/**
 * 
 */
@Path("constantesparametros")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface ConstantesParametrosService {

	/**
	 * <b>Descripción</b>Método empleado para establecer una constante de la
	 * caja <code>usuario es el usuario que se va a autenticar</code><br/>
	 *
	 * @param inDTO
	 *            objeto con los valores de los datos de las constantes
	 */
	@POST
	@Path("/constantesCaja")
	public void establecerConstantesCaja(@NotNull ConstantesCajaCompensacionDTO inDTO);

	/**
	 * <b>Descripción</b>Método que se encarga de obtener los valores
	 * UVT
	 *
	 * * @return ConstantesValorUVTDTO objeto con las constantes
	 * obtenidas
	 *
	 */
	@GET
	@Path("/consultarValorUVT")
	public List<ConstantesValorUVTDTO> consultarValorUVT();

	/**
     * Metodo encargado de editar el Valor UVT
	 * 
     * @return ConstantesValorUVTDTO objeto con las constantes
	 * obtenidas
     */
	@POST
	@Path("/modificarValorUVT")
	public void modificarValorUVT(ConstantesValorUVTDTO constantesValorUVT);

	/**
     * Metodo encargado de agregar un Valor UVT
	 * 
     * @return ConstantesValorUVTDTO objeto con las constantes
	 * obtenidas
     */
	@POST
	@Path("/crearValorUVT")
	public Boolean crearValorUVT(ParametrizacionValorUVT parametrizacionValorUVT);

	/**
	 * <b>Descripción</b>Método que se encarga de obtener las constantes
	 * parametro de una caja de compensación familiar
	 *
	 * * @return ConstantesCajaCompensacionDTO objeto con las constantes
	 * obtenidas
	 *
	 */

	@GET
	@Path("/constantesCaja")
	public ConstantesCajaCompensacionDTO consultarConstantesCaja();

	/**
	 * <b>Descripción</b>Método que se encarga de obtener usuarios de una caja
	 * de compensación familiar
	 *
	 * * @return se retorna la lista con los usuarios obtenidos
	 *
	 */
	@GET
	@Path("/sincronizar")
	public void sincronizarParametrosYConstantes();

    /**
     * Metodo encargado de consultar las areas o dependencias de la caja de compensacion familiar
     * @return listado de areas o dependencias
     */
	@GET
	@Path("/dependenciasCaja")
	public List<AreaCajaCompensacionDTO> consultarDependenciasCCF(@QueryParam("idDependencia") Short idDependencia);

	/**
	 * <b>Descripción</b>Método que se encarga de obtener el salario minimo
	 * mensual legal vigente del año presente
	 * 
	 * @return se retorna el valor del salario minimo mensual legal vigente
	 */
	@GET
	@Path("/smmlv")
	public Long consultarSMMLV();

	/**
	 * <b>Descripción</b>Método que se encarga de consultar las constantes y los
	 * parametros
	 * 
	 * @return se retorna las contantes
	 */
	@GET
	@Path("/consultarConstantesParametros")
	public Map<String, String> consultarConstantesParametros();

	/**
	 * <b>Descripción</b>Método que se encarga de consultar la fecha dle sistema
	 * 
	 * @return se retorna la fecha
	 */
	@GET
	@Path("/consultarFechaSistema")
	public Date consultarFechaSistema();

	/**
	 * Servicio encargado de consultar las constantes parametros del sistema
	 * 
	 * @param nombre,
	 *            nombre del parametro el cual se puede consultar
	 * @param valor,
	 *            valor que contiene el parametro
	 * @param subCategoria,
	 *            subCategoria con la que cuenta el parametro a consultar
	 * @param cargaInicio,
	 *            identificador que muestra si los parámetros deben cargarse al
	 *            inicio
	 * @return retorna una lista de ConstantesParametroDTO
	 */
	@GET
	@Path("/consultarConstantesParametro")
	public List<ConstantesParametroDTO> consultarConstantesParametro(@QueryParam("nombre") String nombre,
			@QueryParam("valor") String valor, @QueryParam("subCategoria") SubCategoriaParametroEnum subCategoria,
			@QueryParam("cargaInicio") Boolean cargaInicio);

	/**
	 * Servicio encargado de crear o modificar una constante parametro
	 * 
	 * @param constanteParametroDTO,
	 *            constanteParametro dto que contiene la información que se va
	 *            crear o modificar
	 */
	@POST
	@Path("/crearModificarConstanteParametro")
	public void crearModificarConstanteParametro(@Valid @NotNull ConstantesParametroDTO constanteParametroDTO);
	
	
	/**
	 * Retorna la constante identificada por key definida en la tabla de 
     * parametro.
	 * 
	 * @param key
	 * 			llave que referencia en cache el valor buscado.
	 * 
	 * @return Object con el valor buscado.
	 */
	@GET
	@Path("/obtenerParametro/{dato}")
	public String obtenerParametro(@NotNull @PathParam("dato") String key);
	
	/**
	 * Retorna la constante identificada por key definida en la tabla de 
     * constante.
	 * 
	 * @param key
	 * 			llave que referencia en cache el valor buscado.
	 * 
	 * @return Object con el valor buscado.
	 */
	@GET
	@Path("/obtenerConstante/{dato}")
	public String obtenerConstante(@NotNull @PathParam("dato") String key);
	
	
	/**
	 * Retorna la lista de datos almacenados en las tablasConstante y Parametro
	 * 
	 * @return Map<String, String> con los datos obtenidos
	 */
	@GET
	@Path("/obtenerListadoConstantesParametros")
	public Map<String, String> obtenerListadoConstantesParametros();

	/**
	 * Para probar la consulta repetidamente a la base de datos
	 * 
	 * @return String para saber que terminó
	 */
	@GET
	@Path("/probarConsultaConstante/{iteraciones}")
	public String probarConsultaConstante(@NotNull @PathParam("iteraciones") Long iteraciones);
	
	/**
	 * Servicio encargado de consultar los datos de conexión al FTP de PILA para un operador de información dados
	 * los parámetros de consulta.
	 * 
	 * @param nombre
	 * @param host
	 * @param url
	 * @param puerto
	 * @param idOperadorInformacion
	 * 
	 * @return List<ConexionOperadorInformacionDTO> con la lista de conexiones que se ajustan a los parámetros de búsqueda.
	 */
	@GET
	@Path("/consultarConexionOperadorInfo")
	public List<ConexionOperadorInformacionDTO> consultarConexionOperadorInfo(@QueryParam("nombre") String nombre,
			@QueryParam("host") String host,
			@QueryParam("url") String url,
			@QueryParam("puerto") Short puerto,
			@QueryParam("idOperadorInformacion") Long idOperadorInformacion);
	
	/**
	 * Servicio encargado de crear o modificar una conexión a un operador de información
	 * 
	 * @param conexionOperadorInformacionDTO,
	 *            conexionOperadorInformacion dto que contiene la información que se va
	 *            crear o modificar
	 */
	@POST
	@Path("/crearModificarConexionOperadorInfo")
	public void crearModificarConexionOperadorInfo(@Valid @NotNull ConexionOperadorInformacionDTO conexionOperadorInformacionDTO);
	
	/**
	 * Servicio encargado de consultar los operadores de información registrados en genesys
	 * 
	 * @return List<OperadorInformacion> con la lista de operadores de información.
	 */
	@GET
	@Path("/consultarOperadoresInformacionInfo")
	public List<OperadorInformacion> consultarOperadoresInformacionInfo();
	
	/**
	 * Servicio encargado de consultar el valor para el parámetro de Margen de tolerancia aportes
	 * 
	 * @param isPila 
	 * 			define si se quiere buscar el parámetro para PILA o para aportes manuales
	 * 
	 * @return Parametro con la información encontrada, null en caso contrario.
	 */
	@GET
	@Path("/consultarMargeToleranciaMoraAporte")
	public Parametro consultarMargeToleranciaMoraAporte(@NotNull @QueryParam("isPila") Boolean isPila);
	
	@POST
	@Path("/crearModificarMargeToleranciaMoraAporte")
	public void crearModificarMargeToleranciaMoraAporte(@Valid @NotNull Parametro parametro); 

	@POST
	@Path("/actualizarParametrizacionGaps")
	public void parametrizacionGapS(ParametrizacionGapsDTO parametrizacionGapsDTO);

	@GET
	@Path("/consultarParametrizacionGaps")
	public ParametrizacionGaps consultarParametrizacionGaps(@NotNull @QueryParam("parametro") String parametro);

	@POST
	@Path("/actualizarDummy")
	public Response actualizarDummy(@NotNull @QueryParam("fecha") String fecha, @NotNull @QueryParam("user") String user, @NotNull @QueryParam("pass") String pass, @Context UserDTO userDTO);

}