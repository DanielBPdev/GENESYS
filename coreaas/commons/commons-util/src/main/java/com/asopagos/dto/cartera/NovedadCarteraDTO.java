package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.dto.NovedadPilaDTO;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.MovimientoAporteModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;
import com.asopagos.entidades.ccf.cartera.Cartera;
import com.asopagos.entidades.ccf.cartera.CicloAportante;
import com.asopagos.entidades.ccf.cartera.SolicitudGestionCobroManual;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoCarteraEnum;
import com.asopagos.enumeraciones.cartera.EstadoFiscalizacionEnum;
import com.asopagos.enumeraciones.cartera.MotivoFiscalizacionAportanteEnum;
import com.asopagos.enumeraciones.cartera.SubTipoDeudaEnum;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.enumeraciones.cartera.TipoDeudaEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.PersonasUtils;

/**
 * <b>Descripción: </b> Datos de una novedad PILA gestionada desde cartera <br/>
 * <b>Historia de Usuario: </b> HU-239
 * 
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez Benavides</a>
 */
@XmlRootElement
public class NovedadCarteraDTO implements Serializable {

    /**
	 * Serial
	 */
	private static final long serialVersionUID = 23445245245L;
	private Long idTransaccion;
    private Boolean isSimulado;
    private Boolean esCotizanteFallecido;
    private Boolean esTrabajadorReintegrable;
    private Boolean esEmpleadorReintegrable;
    private EmpresaModeloDTO empresaAportante;
    private PersonaModeloDTO personaAportante;
    private SucursalEmpresaModeloDTO sucursalEmpresa;
    private PersonaModeloDTO personaCotizante;
    private AporteDetalladoModeloDTO aporteDetallado;
    private AporteGeneralModeloDTO aporteGeneral;
    private List<NovedadPilaDTO> novedades;
    private CanalRecepcionEnum canal;
    private String codigoMunicioAportante;
    private String codigoMunicioCotizante;
    private TipoIdentificacionEnum tipoDocTramitador;
    private String idTramitador;
    private Short digVerTramitador;
    private String nombreTramitador;
    private Boolean enviadoAFiscalizacion;
    private MotivoFiscalizacionAportanteEnum motivoFiscalizacion;
    private Long fechaFiscalizacion;
    private MovimientoAporteModeloDTO movimiento;
	/**Obtiene el valor de idTransaccion
	 * @return El valor de idTransaccion
	 */
	public Long getIdTransaccion() {
		return idTransaccion;
	}
	/** Establece el valor de idTransaccion
	 * @param idTransaccion El valor de idTransaccion por asignar
	 */
	public void setIdTransaccion(Long idTransaccion) {
		this.idTransaccion = idTransaccion;
	}
	/**Obtiene el valor de isSimulado
	 * @return El valor de isSimulado
	 */
	public Boolean getIsSimulado() {
		return isSimulado;
	}
	/** Establece el valor de isSimulado
	 * @param isSimulado El valor de isSimulado por asignar
	 */
	public void setIsSimulado(Boolean isSimulado) {
		this.isSimulado = isSimulado;
	}
	/**Obtiene el valor de esCotizanteFallecido
	 * @return El valor de esCotizanteFallecido
	 */
	public Boolean getEsCotizanteFallecido() {
		return esCotizanteFallecido;
	}
	/** Establece el valor de esCotizanteFallecido
	 * @param esCotizanteFallecido El valor de esCotizanteFallecido por asignar
	 */
	public void setEsCotizanteFallecido(Boolean esCotizanteFallecido) {
		this.esCotizanteFallecido = esCotizanteFallecido;
	}
	/**Obtiene el valor de esTrabajadorReintegrable
	 * @return El valor de esTrabajadorReintegrable
	 */
	public Boolean getEsTrabajadorReintegrable() {
		return esTrabajadorReintegrable;
	}
	/** Establece el valor de esTrabajadorReintegrable
	 * @param esTrabajadorReintegrable El valor de esTrabajadorReintegrable por asignar
	 */
	public void setEsTrabajadorReintegrable(Boolean esTrabajadorReintegrable) {
		this.esTrabajadorReintegrable = esTrabajadorReintegrable;
	}
	/**Obtiene el valor de esEmpleadorReintegrable
	 * @return El valor de esEmpleadorReintegrable
	 */
	public Boolean getEsEmpleadorReintegrable() {
		return esEmpleadorReintegrable;
	}
	/** Establece el valor de esEmpleadorReintegrable
	 * @param esEmpleadorReintegrable El valor de esEmpleadorReintegrable por asignar
	 */
	public void setEsEmpleadorReintegrable(Boolean esEmpleadorReintegrable) {
		this.esEmpleadorReintegrable = esEmpleadorReintegrable;
	}
	/**Obtiene el valor de empresaAportante
	 * @return El valor de empresaAportante
	 */
	public EmpresaModeloDTO getEmpresaAportante() {
		return empresaAportante;
	}
	/** Establece el valor de empresaAportante
	 * @param empresaAportante El valor de empresaAportante por asignar
	 */
	public void setEmpresaAportante(EmpresaModeloDTO empresaAportante) {
		this.empresaAportante = empresaAportante;
	}
	/**Obtiene el valor de personaAportante
	 * @return El valor de personaAportante
	 */
	public PersonaModeloDTO getPersonaAportante() {
		return personaAportante;
	}
	/** Establece el valor de personaAportante
	 * @param personaAportante El valor de personaAportante por asignar
	 */
	public void setPersonaAportante(PersonaModeloDTO personaAportante) {
		this.personaAportante = personaAportante;
	}
	/**Obtiene el valor de sucursalEmpresa
	 * @return El valor de sucursalEmpresa
	 */
	public SucursalEmpresaModeloDTO getSucursalEmpresa() {
		return sucursalEmpresa;
	}
	/** Establece el valor de sucursalEmpresa
	 * @param sucursalEmpresa El valor de sucursalEmpresa por asignar
	 */
	public void setSucursalEmpresa(SucursalEmpresaModeloDTO sucursalEmpresa) {
		this.sucursalEmpresa = sucursalEmpresa;
	}
	/**Obtiene el valor de personaCotizante
	 * @return El valor de personaCotizante
	 */
	public PersonaModeloDTO getPersonaCotizante() {
		return personaCotizante;
	}
	/** Establece el valor de personaCotizante
	 * @param personaCotizante El valor de personaCotizante por asignar
	 */
	public void setPersonaCotizante(PersonaModeloDTO personaCotizante) {
		this.personaCotizante = personaCotizante;
	}
	/**Obtiene el valor de aporteDetallado
	 * @return El valor de aporteDetallado
	 */
	public AporteDetalladoModeloDTO getAporteDetallado() {
		return aporteDetallado;
	}
	/** Establece el valor de aporteDetallado
	 * @param aporteDetallado El valor de aporteDetallado por asignar
	 */
	public void setAporteDetallado(AporteDetalladoModeloDTO aporteDetallado) {
		this.aporteDetallado = aporteDetallado;
	}
	/**Obtiene el valor de aporteGeneral
	 * @return El valor de aporteGeneral
	 */
	public AporteGeneralModeloDTO getAporteGeneral() {
		return aporteGeneral;
	}
	/** Establece el valor de aporteGeneral
	 * @param aporteGeneral El valor de aporteGeneral por asignar
	 */
	public void setAporteGeneral(AporteGeneralModeloDTO aporteGeneral) {
		this.aporteGeneral = aporteGeneral;
	}
	/**Obtiene el valor de novedades
	 * @return El valor de novedades
	 */
	public List<NovedadPilaDTO> getNovedades() {
		return novedades;
	}
	/** Establece el valor de novedades
	 * @param novedades El valor de novedades por asignar
	 */
	public void setNovedades(List<NovedadPilaDTO> novedades) {
		this.novedades = novedades;
	}
	/**Obtiene el valor de canal
	 * @return El valor de canal
	 */
	public CanalRecepcionEnum getCanal() {
		return canal;
	}
	/** Establece el valor de canal
	 * @param canal El valor de canal por asignar
	 */
	public void setCanal(CanalRecepcionEnum canal) {
		this.canal = canal;
	}
	/**Obtiene el valor de codigoMunicioAportante
	 * @return El valor de codigoMunicioAportante
	 */
	public String getCodigoMunicioAportante() {
		return codigoMunicioAportante;
	}
	/** Establece el valor de codigoMunicioAportante
	 * @param codigoMunicioAportante El valor de codigoMunicioAportante por asignar
	 */
	public void setCodigoMunicioAportante(String codigoMunicioAportante) {
		this.codigoMunicioAportante = codigoMunicioAportante;
	}
	/**Obtiene el valor de codigoMunicioCotizante
	 * @return El valor de codigoMunicioCotizante
	 */
	public String getCodigoMunicioCotizante() {
		return codigoMunicioCotizante;
	}
	/** Establece el valor de codigoMunicioCotizante
	 * @param codigoMunicioCotizante El valor de codigoMunicioCotizante por asignar
	 */
	public void setCodigoMunicioCotizante(String codigoMunicioCotizante) {
		this.codigoMunicioCotizante = codigoMunicioCotizante;
	}
	/**Obtiene el valor de tipoDocTramitador
	 * @return El valor de tipoDocTramitador
	 */
	public TipoIdentificacionEnum getTipoDocTramitador() {
		return tipoDocTramitador;
	}
	/** Establece el valor de tipoDocTramitador
	 * @param tipoDocTramitador El valor de tipoDocTramitador por asignar
	 */
	public void setTipoDocTramitador(TipoIdentificacionEnum tipoDocTramitador) {
		this.tipoDocTramitador = tipoDocTramitador;
	}
	/**Obtiene el valor de idTramitador
	 * @return El valor de idTramitador
	 */
	public String getIdTramitador() {
		return idTramitador;
	}
	/** Establece el valor de idTramitador
	 * @param idTramitador El valor de idTramitador por asignar
	 */
	public void setIdTramitador(String idTramitador) {
		this.idTramitador = idTramitador;
	}
	/**Obtiene el valor de digVerTramitador
	 * @return El valor de digVerTramitador
	 */
	public Short getDigVerTramitador() {
		return digVerTramitador;
	}
	/** Establece el valor de digVerTramitador
	 * @param digVerTramitador El valor de digVerTramitador por asignar
	 */
	public void setDigVerTramitador(Short digVerTramitador) {
		this.digVerTramitador = digVerTramitador;
	}
	/**Obtiene el valor de nombreTramitador
	 * @return El valor de nombreTramitador
	 */
	public String getNombreTramitador() {
		return nombreTramitador;
	}
	/** Establece el valor de nombreTramitador
	 * @param nombreTramitador El valor de nombreTramitador por asignar
	 */
	public void setNombreTramitador(String nombreTramitador) {
		this.nombreTramitador = nombreTramitador;
	}
	/**Obtiene el valor de enviadoAFiscalizacion
	 * @return El valor de enviadoAFiscalizacion
	 */
	public Boolean getEnviadoAFiscalizacion() {
		return enviadoAFiscalizacion;
	}
	/** Establece el valor de enviadoAFiscalizacion
	 * @param enviadoAFiscalizacion El valor de enviadoAFiscalizacion por asignar
	 */
	public void setEnviadoAFiscalizacion(Boolean enviadoAFiscalizacion) {
		this.enviadoAFiscalizacion = enviadoAFiscalizacion;
	}
	/**Obtiene el valor de motivoFiscalizacion
	 * @return El valor de motivoFiscalizacion
	 */
	public MotivoFiscalizacionAportanteEnum getMotivoFiscalizacion() {
		return motivoFiscalizacion;
	}
	/** Establece el valor de motivoFiscalizacion
	 * @param motivoFiscalizacion El valor de motivoFiscalizacion por asignar
	 */
	public void setMotivoFiscalizacion(MotivoFiscalizacionAportanteEnum motivoFiscalizacion) {
		this.motivoFiscalizacion = motivoFiscalizacion;
	}
	/**Obtiene el valor de fechaFiscalizacion
	 * @return El valor de fechaFiscalizacion
	 */
	public Long getFechaFiscalizacion() {
		return fechaFiscalizacion;
	}
	/** Establece el valor de fechaFiscalizacion
	 * @param fechaFiscalizacion El valor de fechaFiscalizacion por asignar
	 */
	public void setFechaFiscalizacion(Long fechaFiscalizacion) {
		this.fechaFiscalizacion = fechaFiscalizacion;
	}
	/**Obtiene el valor de movimiento
	 * @return El valor de movimiento
	 */
	public MovimientoAporteModeloDTO getMovimiento() {
		return movimiento;
	}
	/** Establece el valor de movimiento
	 * @param movimiento El valor de movimiento por asignar
	 */
	public void setMovimiento(MovimientoAporteModeloDTO movimiento) {
		this.movimiento = movimiento;
	}
}
