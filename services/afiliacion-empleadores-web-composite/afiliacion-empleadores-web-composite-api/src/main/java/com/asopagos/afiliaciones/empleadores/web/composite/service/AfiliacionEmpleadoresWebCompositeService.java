package com.asopagos.afiliaciones.empleadores.web.composite.service;

import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import com.asopagos.afiliaciones.empleadores.web.composite.dto.AnalizarSolicitudDTO;
import com.asopagos.afiliaciones.empleadores.web.composite.dto.CancelacionSolicitudDTO;
import com.asopagos.afiliaciones.empleadores.web.composite.dto.ConsultarConceptoEscalamientoDTO;
import com.asopagos.afiliaciones.empleadores.web.composite.dto.CorregirInformacionDTO;
import com.asopagos.afiliaciones.empleadores.web.composite.dto.DiligenciarFormularioAfiliacionDTO;
import com.asopagos.afiliaciones.empleadores.web.composite.dto.GestionarPNCSDTO;
import com.asopagos.afiliaciones.empleadores.web.composite.dto.RadicarSolicitudAfiliacionDTO;
import com.asopagos.afiliaciones.empleadores.web.composite.dto.ReintegroEmpleadorDTO;
import com.asopagos.afiliaciones.empleadores.web.composite.dto.VerificarPNCSDTO;
import com.asopagos.afiliaciones.empleadores.web.composite.dto.VerificarSolicitudDTO;
import com.asopagos.dto.DigitarInformacionContactoDTO;
import com.asopagos.enumeraciones.ResultadoRegistroContactoEnum;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.dto.webservices.AfiliacionEmpleadorDTO;
import java.util.Map;

/**
 * <b>Descripción:</b> Interfaz que define los servicios de composición del
 * proceso de afiliación de empleadores WEB
 *
 * @author Jorge Camargo
 *         <a href="mailto:jcamargo@heinsohn.com.co"> jcamargo@heinsohn.com.co
 *         </a>
 */
@Path("solicitudAfiliacionEmpleador")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface AfiliacionEmpleadoresWebCompositeService {

	/**
	 * @param entrada
	 * @return
	 */
	@POST
	@Path("digitarInformacionContacto")
	public ResultadoRegistroContactoEnum digitarInformacionContacto(DigitarInformacionContactoDTO entrada,
			@Context UserDTO UserDTO);

	/**
	 * @param entrada
	 */
	@POST
	@Path("diligenciarFormularioAfiliacion")
	public ResultadoRegistroContactoEnum diligenciarFormularioAfiliacion(DiligenciarFormularioAfiliacionDTO entrada, @Context UserDTO UserDTO);

	/**
	 * @param entrada
	 */
	@POST
	@Path("radicarSolicitudAfiliacion")
	public Map<String, Object> radicarSolicitudAfiliacion(RadicarSolicitudAfiliacionDTO entrada, @Context UserDTO UserDTO);

	/**
	 * @param entrada
	 */
	@POST
	@Path("verificarSolicitud")
	public void verificarSolicitud(VerificarSolicitudDTO entrada, @Context UserDTO UserDTO);

	/**
	 * @param entrada
	 */
	@POST
	@Path("gestionarPNCS")
	public void gestionarPNCS(GestionarPNCSDTO entrada, @Context UserDTO UserDTO);

	/**
	 * @param entrada
	 */
	@POST
	@Path("verificarPNCS")
	public void verificarPNCS(VerificarPNCSDTO entrada, @Context UserDTO UserDTO);

	/**
	 * @param entrada
	 */
	@POST
	@Path("analizarSolicitud")
	public void analizarSolicitud(AnalizarSolicitudDTO entrada, @Context UserDTO UserDTO);

	/**
	 * @param entrada
	 */
	@POST
	@Path("consultarConceptoEscalamiento")
	public void consultarConceptoEscalamiento(ConsultarConceptoEscalamientoDTO entrada, @Context UserDTO UserDTO);

	/**
	 * @param entrada
	 */
	@POST
	@Path("corregirInformacion")
	public void corregirInformacion(CorregirInformacionDTO entrada, @Context UserDTO UserDTO);

	/**
	 * @param entrada
	 * @param UserDTO
	 */
	@POST
	@Path("reenviarCorreoEnrolamiento")
	public void reenviarCorreoEnrolamiento(DigitarInformacionContactoDTO entrada, @Context UserDTO UserDTO);

	/**
	 * @param entrada
	 * @param UserDTO
	 */
	@POST
	@Path("reintegrarEmpleador")
	public Map<String, Object> reintegrarEmpleador(ReintegroEmpleadorDTO entrada, @Context UserDTO UserDTO);
	
	@POST
	@Path("cancelarSolicitudTimeout")
    public void cancelarSolicitudEmpleadoresWebTimeout(CancelacionSolicitudDTO cancelacion);

	@POST
	@Path("radicarSolicitudAfiliacionWebservice")
	public Map<String,Object> digitarYRadicarSolicitudAfiliacionWS(AfiliacionEmpleadorDTO empleadorDTO,
			@Context UserDTO userDTO);

}
