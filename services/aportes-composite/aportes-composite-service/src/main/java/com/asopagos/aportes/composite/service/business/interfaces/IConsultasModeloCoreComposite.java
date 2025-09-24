package com.asopagos.aportes.composite.service.business.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import com.asopagos.aportes.dto.JuegoAporteMovimientoDTO;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.entidades.ccf.aportes.AporteDetallado;
import com.asopagos.entidades.ccf.aportes.MovimientoAporte;
import com.asopagos.dto.modelo.RolAfiliadoModeloDTO;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.dto.ActivacionEmpleadorDTO;
import com.asopagos.dto.RadicarSolicitudAbreviadaDTO;
import com.asopagos.rest.security.dto.UserDTO;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de
 * información en el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@Local
public interface IConsultasModeloCoreComposite {


	/**
     * Método para la persistencia de un paquete de aportes detallados
	 * @param aportesDetallados
	 * @return
	 */
	public List<Long> registrarAportesDetallados(List<JuegoAporteMovimientoDTO> aportesDetallados);
	
	public void updateRolAfiliadoAportes(RolAfiliado rolAfiliadoDTO, Boolean update);

	public void AportesCompositeactualizarEstadoEmpleadorPorAportes(ActivacionEmpleadorDTO datosReintegro);
	
	//public void radicarSolicitudAbreviadaAfiliacionPersonaAfiliados(RadicarSolicitudAbreviadaDTO inDTO) ;
	/**
	 * Método que consulta el numero de aporte detallado anterior
	 * @param idAporteDetallado
	 * @return
	 */
	public List<BigDecimal> consultarSalarioeIbcNuevo(Long idAporteDetallado);

	/**
     * Método que consulta el numero de aporte detallado anterior
     * @param idRegistroDetallado
     * @return
     */
    public BigDecimal consultarAporteObligatorioAnt(Long idRegistroDetallado);

	/**
	 * Método que consulta el numero de aporte detallado anterior
	 * @param idAporteDetallado
	 * @return
	 */
	public BigDecimal consultarAporteObligatorio(Long idAporteDetallado);

	public Boolean validarReintegroDiferenteEmpleador(
		String numeroIdentificacionCotizante,
		TipoIdentificacionEnum tipoIdentificacionCotizante,
		String numeroIdentificacionAportante,
		TipoIdentificacionEnum tipoIdentificacionAportante);
}
