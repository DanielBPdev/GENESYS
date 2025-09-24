package com.asopagos.aportes.composite.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.DocumentoAdministracionEstadoSolicitudDTO;
import com.asopagos.enumeraciones.aportes.DestinatarioDevolucionEnum;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.aportes.MotivoPeticionEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO para las solicitudes de devolución de aportes.
 * 
 * @author <a href="mailto:criparra@heinsohn.com.co"> Cristian David Parra
 *         Zuluaga </a>
 */
@XmlRootElement
public class SolicitudDevolucionDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -2581227462922057356L;

	/**
	 * Tipo de solicitante de la devolución.
	 */
	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;

	/**
	 * Tipo de identificación del solicitante.
	 */
	private TipoIdentificacionEnum tipoIdentificacion;

	/**
	 * Número de identificación del solicitante.
	 */
	private String numeroIdentificacion;

	/**
	 * Dígito de verificación, en caso de que se elija NIT como tipo de
	 * identificación.
	 */
	private Short dv;

	/**
	 * Nombre del solicitante de la devolución.
	 */
	private String nombreSolicitante;

	/**
	 * Estado del aportante que es quien solicita la devolución.
	 */
	private EstadoEmpleadorEnum estadoAportante;

	/**
	 * Fecha de recepción de la solicitud de devolución del aporte.
	 */
	private Long fechaRecepcion;

	/**
	 * Motivo de la petición de devolución.
	 */
	private MotivoPeticionEnum motivoPeticion;

	/**
	 * Destinatario de la devolución.
	 */
	private DestinatarioDevolucionEnum destinatario;

	/**
	 * Id de la caja de compensación.
	 */
	private Integer caja;

	/**
	 * Campo a diligenciar en caso de que el usuario elija la opción "otros" en
	 * el campo destinatario.
	 */
	private String otro;
	   /**
     * Campo a diligenciar en caso de que el usuario elija la opción "otra" en
     * el campo caja.
     */
	private String otraCaja;
	   /**
     * Campo a diligenciar en caso de que el usuario elija la opción "otro" en
     * el campo motivo.
     */
	private String otroMotivo;

	/**
	 * Monto reclamado para devolución.
	 */
	private String montoReclamado;

	/**
	 * Monto de intereses a devolver.
	 */
	private String montoIntereses;

	/**
	 * Período reclamdo para devolución.
	 */
	private List<Long> periodosReclamados;

	/**
	 * Pago efectuado luego de una solicitud de devolución de aportes.
	 */
	private PagoDTO pago;

	/**
	 * Lista de documentos a adjuntar que acompañen la solicitud de devolución
	 * de aportes.
	 */
	private List<DocumentoAdministracionEstadoSolicitudDTO> documentos;

	/**
	 * Comentarios sobre la solicitud de devolución.
	 */
	private String comentarios;

	/**
	 * Estado de la solicitud.
	 */
	private EstadoSolicitudAporteEnum estado;

	/**
	 * Id de la persona consultada.
	 */
	private Long idPersona;
	/**
	 * Id de la empresa consultada.
	 */
	private Long idEmpresa;

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
	 * Método que retorna el valor de tipoIdentifiacion.
	 * 
	 * @return valor de tipoIdentifiacion.
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * Método encargado de modificar el valor de tipoIdentifiacion.
	 * 
	 * @param valor
	 *            para modificar tipoIdentifiacion.
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * Método que retorna el valor de numeroIdentificacion.
	 * 
	 * @return valor de numeroIdentificacion.
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * Método encargado de modificar el valor de numeroIdentificacion.
	 * 
	 * @param valor
	 *            para modificar numeroIdentificacion.
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * Método que retorna el valor de dv.
	 * 
	 * @return valor de dv.
	 */
	public Short getDv() {
		return dv;
	}

	/**
	 * Método encargado de modificar el valor de dv.
	 * 
	 * @param valor
	 *            para modificar dv.
	 */
	public void setDv(Short dv) {
		this.dv = dv;
	}

	/**
	 * Método que retorna el valor de nombreSolicitante.
	 * 
	 * @return valor de nombreSolicitante.
	 */
	public String getNombreSolicitante() {
		return nombreSolicitante;
	}

	/**
	 * Método encargado de modificar el valor de nombreSolicitante.
	 * 
	 * @param valor
	 *            para modificar nombreSolicitante.
	 */
	public void setNombreSolicitante(String nombreSolicitante) {
		this.nombreSolicitante = nombreSolicitante;
	}

	/**
	 * Método que retorna el valor de estadoAportante.
	 * 
	 * @return valor de estadoAportante.
	 */
	public EstadoEmpleadorEnum getEstadoAportante() {
		return estadoAportante;
	}

	/**
	 * Método encargado de modificar el valor de estadoAportante.
	 * 
	 * @param valor
	 *            para modificar estadoAportante.
	 */
	public void setEstadoAportante(EstadoEmpleadorEnum estadoAportante) {
		this.estadoAportante = estadoAportante;
	}

	/**
	 * Método que retorna el valor de fechaRecepcion.
	 * 
	 * @return valor de fechaRecepcion.
	 */
	public Long getFechaRecepcion() {
		return fechaRecepcion;
	}

	/**
	 * Método encargado de modificar el valor de fechaRecepcion.
	 * 
	 * @param valor
	 *            para modificar fechaRecepcion.
	 */
	public void setFechaRecepcion(Long fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	/**
	 * Método que retorna el valor de motivoPeticion.
	 * 
	 * @return valor de motivoPeticion.
	 */
	public MotivoPeticionEnum getMotivoPeticion() {
		return motivoPeticion;
	}

	/**
	 * Método encargado de modificar el valor de motivoPeticion.
	 * 
	 * @param valor
	 *            para modificar motivoPeticion.
	 */
	public void setMotivoPeticion(MotivoPeticionEnum motivoPeticion) {
		this.motivoPeticion = motivoPeticion;
	}

	/**
	 * Método que retorna el valor de destinatario.
	 * 
	 * @return valor de destinatario.
	 */
	public DestinatarioDevolucionEnum getDestinatario() {
		return destinatario;
	}

	/**
	 * Método encargado de modificar el valor de destinatario.
	 * 
	 * @param valor
	 *            para modificar destinatario.
	 */
	public void setDestinatario(DestinatarioDevolucionEnum destinatario) {
		this.destinatario = destinatario;
	}

	/**
	 * Método que retorna el valor de caja.
	 * 
	 * @return valor de caja.
	 */
	public Integer getCaja() {
		return caja;
	}

	/**
	 * Método encargado de modificar el valor de caja.
	 * 
	 * @param valor
	 *            para modificar caja.
	 */
	public void setCaja(Integer caja) {
		this.caja = caja;
	}

	/**
	 * Método que retorna el valor de otro.
	 * 
	 * @return valor de otro.
	 */
	public String getOtro() {
		return otro;
	}

	/**
	 * Método encargado de modificar el valor de otro.
	 * 
	 * @param valor
	 *            para modificar otro.
	 */
	public void setOtro(String otro) {
		this.otro = otro;
	}

	/**
     * Método que retorna el valor de otraCaja.
     * @return valor de otraCaja.
     */
    public String getOtraCaja() {
        return otraCaja;
    }

    /**
     * Método que retorna el valor de otroMotivo.
     * @return valor de otroMotivo.
     */
    public String getOtroMotivo() {
        return otroMotivo;
    }

    /**
     * Método encargado de modificar el valor de otraCaja.
     * @param valor para modificar otraCaja.
     */
    public void setOtraCaja(String otraCaja) {
        this.otraCaja = otraCaja;
    }

    /**
     * Método encargado de modificar el valor de otroMotivo.
     * @param valor para modificar otroMotivo.
     */
    public void setOtroMotivo(String otroMotivo) {
        this.otroMotivo = otroMotivo;
    }

    /**
	 * Método que retorna el valor de montoReclamado.
	 * 
	 * @return valor de montoReclamado.
	 */
	public String getMontoReclamado() {
		return montoReclamado;
	}

	/**
	 * Método encargado de modificar el valor de montoReclamado.
	 * 
	 * @param valor
	 *            para modificar montoReclamado.
	 */
	public void setMontoReclamado(String montoReclamado) {
		this.montoReclamado = montoReclamado;
	}

	/**
	 * Método que retorna el valor de montoIntereses.
	 * 
	 * @return valor de montoIntereses.
	 */
	public String getMontoIntereses() {
		return montoIntereses;
	}

	/**
	 * Método encargado de modificar el valor de montoIntereses.
	 * 
	 * @param valor
	 *            para modificar montoIntereses.
	 */
	public void setMontoIntereses(String montoIntereses) {
		this.montoIntereses = montoIntereses;
	}

	/**
	 * Método que retorna el valor de periodosReclamados.
	 * 
	 * @return valor de periodosReclamados.
	 */
	public List<Long> getPeriodosReclamados() {
		return periodosReclamados;
	}

	/**
	 * Método encargado de modificar el valor de periodosReclamados.
	 * 
	 * @param valor
	 *            para modificar periodosReclamados.
	 */
	public void setPeriodosReclamados(List<Long> periodosReclamados) {
		this.periodosReclamados = periodosReclamados;
	}

	/**
	 * Método que retorna el valor de pago.
	 * 
	 * @return valor de pago.
	 */
	public PagoDTO getPago() {
		return pago;
	}

	/**
	 * Método encargado de modificar el valor de pago.
	 * 
	 * @param valor
	 *            para modificar pago.
	 */
	public void setPago(PagoDTO pago) {
		this.pago = pago;
	}

	/**
	 * Método que retorna el valor de documentos.
	 * 
	 * @return valor de documentos.
	 */
	public List<DocumentoAdministracionEstadoSolicitudDTO> getDocumentos() {
		return documentos;
	}

	/**
	 * Método encargado de modificar el valor de documentos.
	 * 
	 * @param valor
	 *            para modificar documentos.
	 */
	public void setDocumentos(List<DocumentoAdministracionEstadoSolicitudDTO> documentos) {
		this.documentos = documentos;
	}

	/**
	 * Método que retorna el valor de comentarios.
	 * 
	 * @return valor de comentarios.
	 */
	public String getComentarios() {
		return comentarios;
	}

	/**
	 * Método encargado de modificar el valor de comentarios.
	 * 
	 * @param valor
	 *            para modificar comentarios.
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	/**
	 * Método que retorna el valor de estado.
	 * 
	 * @return valor de estado.
	 */
	public EstadoSolicitudAporteEnum getEstado() {
		return estado;
	}

	/**
	 * Método encargado de modificar el valor de estado.
	 * 
	 * @param valor
	 *            para modificar estado.
	 */
	public void setEstado(EstadoSolicitudAporteEnum estado) {
		this.estado = estado;
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
	 * Método encargado de modificar el valor de idPersona.
	 * 
	 * @param valor
	 *            para modificar idPersona.
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * Método que retorna el valor de idEmpresa.
	 * 
	 * @return valor de idEmpresa.
	 */
	public Long getIdEmpresa() {
		return idEmpresa;
	}

	/**
	 * Método encargado de modificar el valor de idEmpresa.
	 * 
	 * @param valor
	 *            para modificar idEmpresa.
	 */
	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

}
