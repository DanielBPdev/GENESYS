/**
 * 
 */
package com.asopagos.cartera.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.DocumentoSoporteModeloDTO;
import com.asopagos.entidades.ccf.cartera.BitacoraCartera;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.MedioCarteraEnum;
import com.asopagos.enumeraciones.cartera.ResultadoBitacoraCarteraEnum;
import com.asopagos.enumeraciones.cartera.TipoActividadBitacoraEnum;
import com.asopagos.entidades.transversal.core.ComunicadoNiyarakyRecepcion;
import com.asopagos.entidades.transversal.core.ComunicadoNiyaraky;

/**
 * Clase DTO con los datos de los filtros de la consulta.
 * 
 * @author Julian Andres Sanchez Bedoya <jusanchez@heinsohn.com.co>
 * 
 */
@XmlRootElement
public class BitacoraCarteraDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 3206876258328114082L;
	/**
	 * Identificador de la bitacora
	 */
	private Long idBitacoraCartera;
	/**
	 * Fecha de la bitacora
	 */
	private Long fecha;
	/**
	 * Actividad de cartera
	 */
	private TipoActividadBitacoraEnum actividad;
	/**
	 * Medio / Canal por donde se realiza la acción de cobro
	 */
	private MedioCarteraEnum medio;
	/**
	 * Resultado realizado sobre el aportante.
	 */
	private ResultadoBitacoraCarteraEnum resultado;
	/**
	 * Usuario que registra las acciones de cobro
	 */
	private String usuario;
	/**
	 * Id que identifica al aportante
	 */
	private Long idPersona;
	/**
	 * Tipo de aportante
	 */
	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
	/**
	 * Lista de documentos de soporte pertenecientes a la bitacora
	 */
	private List<DocumentoSoporteModeloDTO> documentosSoporte;
	/**
	 * Número de operación 
	 */
	private String numeroOperacion;
	
	/**
	 * Usuario sistema
	 */
	private static final String USUARIO_SISTEMA = "Sistema";
	
	/**
	 * Usuario que ejecuta tareas programadas
	 */
	private static final String USUARIO_SERVICE_ACCOUNT_SYSTEM = "service-account-system";

	/**
	 * Identificador de la bitacora
	 */
	private Long idCartera;
	/**
	 * Comentarios
	 */
	private String comentarios;
	
	private Long cnIdMensaje;
	private String cnObservacion;

	private Date cnrFechaEventoRecibidoPorNiyaraky;
	private Long cnrEstado;
	private String cnrDescripcion;
	private Date cnrFechaEventoRecibido;
	private Date cnFechaEnvio;

	/**
	 * Constructor
	 */
	public BitacoraCarteraDTO() {
	}

	// /**
	//  * Método constructor de la bitacora cartera dto
	//  */
	public BitacoraCarteraDTO(Long idBitacoraCartera, Date fecha, String actividad, String medio, String resultado, 
	String usuario, Long idPersona, String tipoSolicitante, String numeroOperacion, 
	String comentarios, Long cnIdMensaje, Date cnFechaEnvio, String cnObservacion, 
	Long cnrEstado, String cnrDescripcion, Date cnrFechaEventoRecibido, 
	Date cnrFechaEventoRecibidoPorNiyaraky) {
		this.idBitacoraCartera = idBitacoraCartera;
		this.fecha = fecha != null ? fecha.getTime() : null;
		this.actividad = TipoActividadBitacoraEnum.valueOf(actividad);
		this.medio = MedioCarteraEnum.valueOf(medio);
		this.resultado = ResultadoBitacoraCarteraEnum.valueOf(resultado);
		this.usuario = verificarUsuarioSistema(usuario);
		this.idPersona = idPersona;
		this.tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.valueOf(tipoSolicitante);
		this.numeroOperacion = numeroOperacion;
		this.comentarios = comentarios;
		this.cnIdMensaje = cnIdMensaje;
		this.cnObservacion = cnObservacion;
		this.cnrEstado = cnrEstado;
		this.cnFechaEnvio = cnFechaEnvio;
		this.cnrDescripcion = (cnrDescripcion != null && !cnrDescripcion.trim().isEmpty()) ? cnrDescripcion : cnObservacion;
		this.cnrFechaEventoRecibido = cnrFechaEventoRecibido;
		this.cnrFechaEventoRecibidoPorNiyaraky = cnrFechaEventoRecibidoPorNiyaraky;
	}
	

	public BitacoraCarteraDTO(BitacoraCartera bitacoraCartera) {
		this.idBitacoraCartera = bitacoraCartera.getIdBitacoraCartera();
		this.fecha = bitacoraCartera.getFecha() != null ? bitacoraCartera.getFecha().getTime() : null;
		this.actividad = bitacoraCartera.getActividad();
		this.medio = bitacoraCartera.getMedio();
		this.resultado = bitacoraCartera.getResultado();
		this.usuario = verificarUsuarioSistema(bitacoraCartera.getUsuario());
		this.idPersona = bitacoraCartera.getIdPersona();
		this.tipoSolicitante = bitacoraCartera.getTipoSolicitante();
		this.numeroOperacion= bitacoraCartera.getNumeroOperacion();
		this.comentarios = bitacoraCartera.getComentarios();
	}

	/**
	 * Método que reemplaza el nombre de usuario del sistema, sólo para visualización
	 */
	private String verificarUsuarioSistema(String usuario){
		if(usuario!=null && usuario.equalsIgnoreCase(USUARIO_SERVICE_ACCOUNT_SYSTEM)){
			return USUARIO_SISTEMA;
		}
		
		return usuario;
	}
	
	/**
	 * Constructor
	 */
	public BitacoraCarteraDTO(Long idBitacoraCartera, Long fecha, TipoActividadBitacoraEnum actividad, MedioCarteraEnum medio, ResultadoBitacoraCarteraEnum resultado, String usuario, Long idPersona, TipoSolicitanteMovimientoAporteEnum tipoSolicitante, List<DocumentoSoporteModeloDTO> documentosSoporte, String numeroOperacion, Long idCartera, String comentarios, Long cnIdMensaje, String cnObservacion, Date cnrFechaEventoRecibidoPorNiyaraky, Long cnrEstado, String cnrDescripcion, Date cnrFechaEventoRecibido) {
		super();
		this.idBitacoraCartera = idBitacoraCartera;
		this.fecha = fecha;
		this.actividad = actividad;
		this.medio = medio;
		this.resultado = resultado;
		this.usuario = verificarUsuarioSistema(usuario);;
		this.idPersona = idPersona;
		this.tipoSolicitante = tipoSolicitante;
		this.documentosSoporte = documentosSoporte;
		this.numeroOperacion = numeroOperacion;
		this.idCartera = idCartera;
		this.comentarios = comentarios;
		this.cnIdMensaje = cnIdMensaje;
		this.cnObservacion = cnObservacion;
		this.cnrFechaEventoRecibidoPorNiyaraky = cnrFechaEventoRecibidoPorNiyaraky;
		this.cnrEstado = cnrEstado;
		this.cnrDescripcion = cnrDescripcion;
		this.cnrFechaEventoRecibido = cnrFechaEventoRecibido;
	}

	public BitacoraCarteraDTO(Long idBitacoraCartera, Long fecha, TipoActividadBitacoraEnum actividad, MedioCarteraEnum medio, ResultadoBitacoraCarteraEnum resultado, String usuario, Long idPersona, TipoSolicitanteMovimientoAporteEnum tipoSolicitante, String numeroOperacion, Long idCartera) {
		super();
		this.idBitacoraCartera = idBitacoraCartera;
		this.fecha = fecha;
		this.actividad = actividad;
		this.medio = medio;
		this.resultado = resultado;
		this.usuario = verificarUsuarioSistema(usuario);
		this.idPersona = idPersona;
		this.tipoSolicitante = tipoSolicitante;
		this.numeroOperacion = numeroOperacion;
		this.idCartera = idCartera;
	}
	public BitacoraCarteraDTO(Long idBitacoraCartera, Long fecha, TipoActividadBitacoraEnum actividad, MedioCarteraEnum medio, ResultadoBitacoraCarteraEnum resultado, String usuario, Long idPersona, TipoSolicitanteMovimientoAporteEnum tipoSolicitante, List<DocumentoSoporteModeloDTO> documentosSoporte, String numeroOperacion, Long idCartera) {
		super();
		this.idBitacoraCartera = idBitacoraCartera;
		this.fecha = fecha;
		this.actividad = actividad;
		this.medio = medio;
		this.resultado = resultado;
		this.usuario = verificarUsuarioSistema(usuario);
		this.idPersona = idPersona;
		this.tipoSolicitante = tipoSolicitante;
		this.documentosSoporte = documentosSoporte;
		this.numeroOperacion = numeroOperacion;
		this.idCartera = idCartera;
	}

	public BitacoraCarteraDTO(Long idBitacoraCartera, Long fecha, TipoActividadBitacoraEnum actividad, MedioCarteraEnum medio, ResultadoBitacoraCarteraEnum resultado, String usuario, Long idPersona, TipoSolicitanteMovimientoAporteEnum tipoSolicitante, List<DocumentoSoporteModeloDTO> documentosSoporte, String numeroOperacion, Long idCartera, String comentarios) {
		super();
		this.idBitacoraCartera = idBitacoraCartera;
		this.fecha = fecha;
		this.actividad = actividad;
		this.medio = medio;
		this.resultado = resultado;
		this.usuario = verificarUsuarioSistema(usuario);
		this.idPersona = idPersona;
		this.tipoSolicitante = tipoSolicitante;
		this.documentosSoporte = documentosSoporte;
		this.numeroOperacion = numeroOperacion;
		this.idCartera = idCartera;
		this.comentarios = comentarios;
	}
	public BitacoraCarteraDTO(Long idBitacoraCartera, Long fecha, String actividad, String medio, String resultado, String usuario, Long idPersona, String tipoSolicitante, String numeroOperacion, String comentarios) {
		super();
		this.idBitacoraCartera = idBitacoraCartera;
		this.fecha = fecha;
		this.actividad = TipoActividadBitacoraEnum.valueOf(actividad);
		this.medio = MedioCarteraEnum.valueOf(medio);
		this.resultado = ResultadoBitacoraCarteraEnum.valueOf(resultado);
		this.usuario = verificarUsuarioSistema(usuario);
		this.idPersona = idPersona;
		this.tipoSolicitante = TipoSolicitanteMovimientoAporteEnum.valueOf(tipoSolicitante);
		this.numeroOperacion = numeroOperacion;
		this.comentarios = comentarios;
	}

	/**
	 * Méotdo encargado de convertir el dto BitacoraCarteraDTO a la entidad
	 * bitacoraCartera
	 * 
	 * @return retorna entidad bitacoraCartera
	 */
	public BitacoraCartera convertToEntity() {
		BitacoraCartera bitacoraCartera = new BitacoraCartera();
		bitacoraCartera.setIdBitacoraCartera(this.idBitacoraCartera);
		bitacoraCartera.setFecha(this.fecha != null ? new Date(this.fecha) : null);
		bitacoraCartera.setActividad(this.actividad);
		bitacoraCartera.setMedio(this.medio);
		bitacoraCartera.setResultado(this.resultado);
		bitacoraCartera.setUsuario(this.usuario!=null && this.usuario.equalsIgnoreCase(USUARIO_SISTEMA)?USUARIO_SERVICE_ACCOUNT_SYSTEM:this.usuario);
		bitacoraCartera.setIdPersona(this.idPersona);
		bitacoraCartera.setTipoSolicitante(this.tipoSolicitante);
		bitacoraCartera.setNumeroOperacion(this.numeroOperacion);
		bitacoraCartera.setComentarios(this.comentarios);
		return bitacoraCartera;
	}

	/**
	 * Método que retorna el valor de idBitacoraCartera.
	 * 
	 * @return valor de idBitacoraCartera.
	 */
	public Long getIdBitacoraCartera() {
		return idBitacoraCartera;
	}

	/**
	 * Método que retorna el valor de fecha.
	 * 
	 * @return valor de fecha.
	 */
	public Long getFecha() {
		return fecha;
	}

	/**
	 * Método que retorna el valor de medio.
	 * 
	 * @return valor de medio.
	 */
	public MedioCarteraEnum getMedio() {
		return medio;
	}

	/**
	 * Método que retorna el valor de resultado.
	 * 
	 * @return valor de resultado.
	 */
	public ResultadoBitacoraCarteraEnum getResultado() {
		return resultado;
	}

	/**
	 * Método que retorna el valor de usuario.
	 * 
	 * @return valor de usuario.
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Método que retorna el valor de idPersona.
	 * 
	 * @return valor de idPersona.
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * Método encargado de modificar el valor de idBitacoraCartera.
	 * 
	 * @param valor
	 *            para modificar idBitacoraCartera.
	 */
	public void setIdBitacoraCartera(Long idBitacoraCartera) {
		this.idBitacoraCartera = idBitacoraCartera;
	}

	/**
	 * Método encargado de modificar el valor de fecha.
	 * 
	 * @param valor
	 *            para modificar fecha.
	 */
	public void setFecha(Long fecha) {
		this.fecha = fecha;
	}

	/**
	 * Método encargado de modificar el valor de medio.
	 * 
	 * @param valor
	 *            para modificar medio.
	 */
	public void setMedio(MedioCarteraEnum medio) {
		this.medio = medio;
	}

	/**
	 * Método encargado de modificar el valor de resultado.
	 * 
	 * @param valor
	 *            para modificar resultado.
	 */
	public void setResultado(ResultadoBitacoraCarteraEnum resultado) {
		this.resultado = resultado;
	}

	/**
	 * Método encargado de modificar el valor de usuario.
	 * 
	 * @param valor
	 *            para modificar usuario.
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Método encargado de modificar el valor de idPersona.
	 * 
	 * @param valor
	 *            para modificar idPersona.
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * Método que retorna el valor de tipoSolicitante.
	 * 
	 * @return valor de tipoSolicitante.
	 */
	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
		return tipoSolicitante;
	}

	/**
	 * Método encargado de modificar el valor de tipoSolicitante.
	 * 
	 * @param valor
	 *            para modificar tipoSolicitante.
	 */
	public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
		this.tipoSolicitante = tipoSolicitante;
	}

	/**
     * @return the documentosSoporte
     */
    public List<DocumentoSoporteModeloDTO> getDocumentosSoporte() {
        return documentosSoporte;
    }

    /**
     * @param documentosSoporte the documentosSoporte to set
     */
    public void setDocumentosSoporte(List<DocumentoSoporteModeloDTO> documentosSoporte) {
        this.documentosSoporte = documentosSoporte;
    }

    /**
	 * Obtiene el valor de actividad
	 * 
	 * @return El valor de actividad
	 */
	public TipoActividadBitacoraEnum getActividad() {
		return actividad;
	}

	/**
	 * Establece el valor de actividad
	 * 
	 * @param actividad
	 *            El valor de actividad por asignar
	 */
	public void setActividad(TipoActividadBitacoraEnum actividad) {
		this.actividad = actividad;
	}

	/**Obtiene el valor de numeroOperacion
	 * @return El valor de numeroOperacion
	 */
	public String getNumeroOperacion() {
		return numeroOperacion;
	}

	/** Establece el valor de numeroOperacion
	 * @param numeroOperacion El valor de numeroOperacion por asignar
	 */
	public void setNumeroOperacion(String numeroOperacion) {
		this.numeroOperacion = numeroOperacion;
	}

	/**
	 * @return the idCartera
	 */
	public Long getIdCartera() {
		return idCartera;
	}

	/**
	 * @param idCartera the idCartera to set
	 */
	public void setIdCartera(Long idCartera) {
		this.idCartera = idCartera;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public Long getCnIdMensaje() {
		return this.cnIdMensaje;
	}

	public void setCnIdMensaje(Long cnIdMensaje) {
		this.cnIdMensaje = cnIdMensaje;
	}

	public String getCnObservacion() {
		return this.cnObservacion;
	}

	public void setCnObservacion(String cnObservacion) {
		this.cnObservacion = cnObservacion;
	}

	public Date getCnrFechaEventoRecibidoPorNiyaraky() {
		return this.cnrFechaEventoRecibidoPorNiyaraky;
	}

	public void setCnrFechaEventoRecibidoPorNiyaraky(Date cnrFechaEventoRecibidoPorNiyaraky) {
		this.cnrFechaEventoRecibidoPorNiyaraky = cnrFechaEventoRecibidoPorNiyaraky;
	}

	public Long getCnrEstado() {
		return this.cnrEstado;
	}

	public void setCnrEstado(Long cnrEstado) {
		this.cnrEstado = cnrEstado;
	}

	public String getCnrDescripcion() {
		return this.cnrDescripcion;
	}

	public void setCnrDescripcion(String cnrDescripcion) {
		this.cnrDescripcion = cnrDescripcion;
	}

	public Date getCnrFechaEventoRecibido() {
		return this.cnrFechaEventoRecibido;
	}

	public void setCnrFechaEventoRecibido(Date cnrFechaEventoRecibido) {
		this.cnrFechaEventoRecibido = cnrFechaEventoRecibido;
	}

	public Date getCnFechaEnvio() {
		return this.cnFechaEnvio;
	}

	public void setCnFechaEnvio(Date cnFechaEnvio) {
		this.cnFechaEnvio = cnFechaEnvio;
	}

	@Override
	public String toString() {
		return "{" +
			" idBitacoraCartera='" + getIdBitacoraCartera() + "'" +
			", fecha='" + getFecha() + "'" +
			", actividad='" + getActividad() + "'" +
			", medio='" + getMedio() + "'" +
			", resultado='" + getResultado() + "'" +
			", usuario='" + getUsuario() + "'" +
			", idPersona='" + getIdPersona() + "'" +
			", tipoSolicitante='" + getTipoSolicitante() + "'" +
			", documentosSoporte='" + getDocumentosSoporte() + "'" +
			", numeroOperacion='" + getNumeroOperacion() + "'" +
			", idCartera='" + getIdCartera() + "'" +
			", comentarios='" + getComentarios() + "'" +
			"}";
	}

}