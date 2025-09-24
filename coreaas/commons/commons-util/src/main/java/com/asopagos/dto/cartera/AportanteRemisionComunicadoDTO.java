package com.asopagos.dto.cartera;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoTareaGestionCobroEnum;
import com.asopagos.enumeraciones.cartera.ResultadoEntregaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * Clase DTO con la información de los aportantes para la remisión de un
 * comunicado.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
@XmlRootElement
public class AportanteRemisionComunicadoDTO implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -3192358088099932044L;
	/**
	 * Tipo de aportante (EMPLEADOR, PENSIONADO, INDEPENDIENTE)
	 */
	private TipoSolicitanteMovimientoAporteEnum tipoAportante;
	/**
	 * Tipo de identificación del aportante.
	 */
	private TipoIdentificacionEnum tipoIdentificacion;
	/**
	 * Número de identificación del aportante.
	 */
	private String numeroIdentificacion;
	/**
	 * Nombre completo del aportante.
	 */
	private String nombreCompleto;
	/**
	 * Periodo que se encuentra en deuda.
	 */
	private Long periodo;
	/**
	 * Destinatario a quien se envía el comunicado.
	 */
	private String destinatario;
	/**
	 * Departamento del destinatario.
	 */
	private Short departamento;
	/**
	 * Municiío del destinatario.
	 */
	private Short municipio;
	/**
	 * Dirección física del destinatario.
	 */
	private String direccion;
	/**
	 * Código postal del destinatario.
	 */
	private String codigoPostal;
	/**
	 * Teléfono del destinatario.
	 */
	private String telefono;
	/**
	 * Atributo que indica si se va a enviar o no el correo.
	 */
	private Boolean enviar;
	/**
	 * Observación impuesta por el back.
	 */
	private String observacion;
	/**
	 * Atributo que indica el resultado de la entrega de la remisión.
	 */
	private ResultadoEntregaEnum resultadoPrimeraEntrega;
	/**
	 * Atributo que indica la observación de la entrega.
	 */
	private String observacionPrimeraEntrega;
	/**
	 * Atributo que indica el id del ecm del doucmento en la entrega.
	 */
	private String idDocumentoPrimeraEntrega;
	/**
	 * Atributo que indica la fecha de entrega o segundo intento.
	 */
	private Long fechaPrimeraEntrega;
	/**
	 * Atributo que indica el resultado de la segunda entrega de la remisión.
	 */
	private ResultadoEntregaEnum resultadoSegundaEntrega;
	/**
	 * Atributo que indica la observación de la entrega.
	 */
	private String observacionSegundaEntrega;
	/**
	 * Atributo que indica el id del ecm del doucmento en la segunda entrega.
	 */
	private String idDocumentoSegundaEntrega;
	/**
	 * Atributo que indica la fecha de segunda entrega o segundo intento.
	 */
	private Long fechaSegundaEntrega;
	/**
	 * Atributo que indica el estado de la tarea.
	 */
	private EstadoTareaGestionCobroEnum estadoTarea;

	/**
	 * Consecutivo de liquidación de aportes generado para el aportante
	 */
	private String consecutivoLiquidacion;

	/**
	 * Fecha de entrega de la liquidación de aportes
	 */
	private Long fechaLiquidacion;

	/**
     * Atributo que indica el id del DetalleSolicitudGestionCobro.
     */
    private Long idDetalleSolicitudGestionCobro;
    
    /**
     * Atributo que indica el id de la primera solicitud de remisión.
     */
    private Long idPrimeraSolicitudRemision;
    
    /**
     * Atributo que representa el id.
     */
    private Long idCartera;
	
	/**
	 * Constructor de aportante remisión comunicado.
	 */
	public AportanteRemisionComunicadoDTO() {

	}

	/**
	 * Obtiene el valor de tipoAportante
	 * 
	 * @return El valor de tipoAportante
	 */
	public TipoSolicitanteMovimientoAporteEnum getTipoAportante() {
		return tipoAportante;
	}

	/**
	 * Establece el valor de tipoAportante
	 * 
	 * @param tipoAportante
	 *            El valor de tipoAportante por asignar
	 */
	public void setTipoAportante(TipoSolicitanteMovimientoAporteEnum tipoAportante) {
		this.tipoAportante = tipoAportante;
	}

	/**
	 * Obtiene el valor de tipoIdentificacion
	 * 
	 * @return El valor de tipoIdentificacion
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * Establece el valor de tipoIdentificacion
	 * 
	 * @param tipoIdentificacion
	 *            El valor de tipoIdentificacion por asignar
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * Obtiene el valor de numeroIdentificacion
	 * 
	 * @return El valor de numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * Establece el valor de numeroIdentificacion
	 * 
	 * @param numeroIdentificacion
	 *            El valor de numeroIdentificacion por asignar
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * Obtiene el valor de nombreCompleto
	 * 
	 * @return El valor de nombreCompleto
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * Establece el valor de nombreCompleto
	 * 
	 * @param nombreCompleto
	 *            El valor de nombreCompleto por asignar
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	/**
	 * Obtiene el valor de periodo
	 * 
	 * @return El valor de periodo
	 */
	public Long getPeriodo() {
		return periodo;
	}

	/**
	 * Establece el valor de periodo
	 * 
	 * @param periodo
	 *            El valor de periodo por asignar
	 */
	public void setPeriodo(Long periodo) {
		this.periodo = periodo;
	}

	/**
	 * Obtiene el valor de destinatario
	 * 
	 * @return El valor de destinatario
	 */
	public String getDestinatario() {
		return destinatario;
	}

	/**
	 * Establece el valor de destinatario
	 * 
	 * @param destinatario
	 *            El valor de destinatario por asignar
	 */
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	/**
	 * Obtiene el valor de departamento
	 * 
	 * @return El valor de departamento
	 */
	public Short getDepartamento() {
		return departamento;
	}

	/**
	 * Establece el valor de departamento
	 * 
	 * @param departamento
	 *            El valor de departamento por asignar
	 */
	public void setDepartamento(Short departamento) {
		this.departamento = departamento;
	}

	/**
	 * Obtiene el valor de municipio
	 * 
	 * @return El valor de municipio
	 */
	public Short getMunicipio() {
		return municipio;
	}

	/**
	 * Establece el valor de municipio
	 * 
	 * @param municipio
	 *            El valor de municipio por asignar
	 */
	public void setMunicipio(Short municipio) {
		this.municipio = municipio;
	}

	/**
	 * Obtiene el valor de direccion
	 * 
	 * @return El valor de direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Establece el valor de direccion
	 * 
	 * @param direccion
	 *            El valor de direccion por asignar
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Obtiene el valor de codigoPostal
	 * 
	 * @return El valor de codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * Establece el valor de codigoPostal
	 * 
	 * @param codigoPostal
	 *            El valor de codigoPostal por asignar
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/**
	 * Obtiene el valor de telefono
	 * 
	 * @return El valor de telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Establece el valor de telefono
	 * 
	 * @param telefono
	 *            El valor de telefono por asignar
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Obtiene el valor de enviar
	 * 
	 * @return El valor de enviar
	 */
	public Boolean getEnviar() {
		return enviar;
	}

	/**
	 * Establece el valor de enviar
	 * 
	 * @param enviar
	 *            El valor de enviar por asignar
	 */
	public void setEnviar(Boolean enviar) {
		this.enviar = enviar;
	}

	/**
	 * Obtiene el valor de observacion
	 * 
	 * @return El valor de observacion
	 */
	public String getObservacion() {
		return observacion;
	}

	/**
	 * Establece el valor de observacion
	 * 
	 * @param observacion
	 *            El valor de observacion por asignar
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	/**
	 * Obtiene el valor de resultadoPrimeraEntrega
	 * 
	 * @return El valor de resultadoPrimeraEntrega
	 */
	public ResultadoEntregaEnum getResultadoPrimeraEntrega() {
		return resultadoPrimeraEntrega;
	}

	/**
	 * Establece el valor de resultadoPrimeraEntrega
	 * 
	 * @param resultadoPrimeraEntrega
	 *            El valor de resultadoPrimeraEntrega por asignar
	 */
	public void setResultadoPrimeraEntrega(ResultadoEntregaEnum resultadoPrimeraEntrega) {
		this.resultadoPrimeraEntrega = resultadoPrimeraEntrega;
	}

	/**
	 * Obtiene el valor de observacionPrimeraEntrega
	 * 
	 * @return El valor de observacionPrimeraEntrega
	 */
	public String getObservacionPrimeraEntrega() {
		return observacionPrimeraEntrega;
	}

	/**
	 * Establece el valor de observacionPrimeraEntrega
	 * 
	 * @param observacionPrimeraEntrega
	 *            El valor de observacionPrimeraEntrega por asignar
	 */
	public void setObservacionPrimeraEntrega(String observacionPrimeraEntrega) {
		this.observacionPrimeraEntrega = observacionPrimeraEntrega;
	}

	/**
	 * Obtiene el valor de idDocumentoPrimeraEntrega
	 * 
	 * @return El valor de idDocumentoPrimeraEntrega
	 */
	public String getIdDocumentoPrimeraEntrega() {
		return idDocumentoPrimeraEntrega;
	}

	/**
	 * Establece el valor de idDocumentoPrimeraEntrega
	 * 
	 * @param idDocumentoPrimeraEntrega
	 *            El valor de idDocumentoPrimeraEntrega por asignar
	 */
	public void setIdDocumentoPrimeraEntrega(String idDocumentoPrimeraEntrega) {
		this.idDocumentoPrimeraEntrega = idDocumentoPrimeraEntrega;
	}

	/**
	 * Obtiene el valor de fechaPrimeraEntrega
	 * 
	 * @return El valor de fechaPrimeraEntrega
	 */
	public Long getFechaPrimeraEntrega() {
		return fechaPrimeraEntrega;
	}

	/**
	 * Establece el valor de fechaPrimeraEntrega
	 * 
	 * @param fechaPrimeraEntrega
	 *            El valor de fechaPrimeraEntrega por asignar
	 */
	public void setFechaPrimeraEntrega(Long fechaPrimeraEntrega) {
		this.fechaPrimeraEntrega = fechaPrimeraEntrega;
	}

	/**
	 * Obtiene el valor de resultadoSegundaEntrega
	 * 
	 * @return El valor de resultadoSegundaEntrega
	 */
	public ResultadoEntregaEnum getResultadoSegundaEntrega() {
		return resultadoSegundaEntrega;
	}

	/**
	 * Establece el valor de resultadoSegundaEntrega
	 * 
	 * @param resultadoSegundaEntrega
	 *            El valor de resultadoSegundaEntrega por asignar
	 */
	public void setResultadoSegundaEntrega(ResultadoEntregaEnum resultadoSegundaEntrega) {
		this.resultadoSegundaEntrega = resultadoSegundaEntrega;
	}

	/**
	 * Obtiene el valor de observacionSegundaEntrega
	 * 
	 * @return El valor de observacionSegundaEntrega
	 */
	public String getObservacionSegundaEntrega() {
		return observacionSegundaEntrega;
	}

	/**
	 * Establece el valor de observacionSegundaEntrega
	 * 
	 * @param observacionSegundaEntrega
	 *            El valor de observacionSegundaEntrega por asignar
	 */
	public void setObservacionSegundaEntrega(String observacionSegundaEntrega) {
		this.observacionSegundaEntrega = observacionSegundaEntrega;
	}

	/**
	 * Obtiene el valor de idDocumentoSegundaEntrega
	 * 
	 * @return El valor de idDocumentoSegundaEntrega
	 */
	public String getIdDocumentoSegundaEntrega() {
		return idDocumentoSegundaEntrega;
	}

	/**
	 * Establece el valor de idDocumentoSegundaEntrega
	 * 
	 * @param idDocumentoSegundaEntrega
	 *            El valor de idDocumentoSegundaEntrega por asignar
	 */
	public void setIdDocumentoSegundaEntrega(String idDocumentoSegundaEntrega) {
		this.idDocumentoSegundaEntrega = idDocumentoSegundaEntrega;
	}

	/**
	 * Obtiene el valor de fechaSegundaEntrega
	 * 
	 * @return El valor de fechaSegundaEntrega
	 */
	public Long getFechaSegundaEntrega() {
		return fechaSegundaEntrega;
	}

	/**
	 * Establece el valor de fechaSegundaEntrega
	 * 
	 * @param fechaSegundaEntrega
	 *            El valor de fechaSegundaEntrega por asignar
	 */
	public void setFechaSegundaEntrega(Long fechaSegundaEntrega) {
		this.fechaSegundaEntrega = fechaSegundaEntrega;
	}

	/**
	 * Obtiene el valor de estadoTarea
	 * 
	 * @return El valor de estadoTarea
	 */
	public EstadoTareaGestionCobroEnum getEstadoTarea() {
		return estadoTarea;
	}

	/**
	 * Establece el valor de estadoTarea
	 * 
	 * @param estadoTarea
	 *            El valor de estadoTarea por asignar
	 */
	public void setEstadoTarea(EstadoTareaGestionCobroEnum estadoTarea) {
		this.estadoTarea = estadoTarea;
	}

	/**
	 * Obtiene el valor de consecutivoLiquidacion
	 * 
	 * @return El valor de consecutivoLiquidacion
	 */
	public String getConsecutivoLiquidacion() {
		return consecutivoLiquidacion;
	}

	/**
	 * Establece el valor de consecutivoLiquidacion
	 * 
	 * @param consecutivoLiquidacion
	 *            El valor de consecutivoLiquidacion por asignar
	 */
	public void setConsecutivoLiquidacion(String consecutivoLiquidacion) {
		this.consecutivoLiquidacion = consecutivoLiquidacion;
	}

	/**
	 * Obtiene el valor de fechaLiquidacion
	 * 
	 * @return El valor de fechaLiquidacion
	 */
	public Long getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * Establece el valor de fechaLiquidacion
	 * 
	 * @param fechaLiquidacion
	 *            El valor de fechaLiquidacion por asignar
	 */
	public void setFechaLiquidacion(Long fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

    /**
     * @return the idDetalleSolicitudGestionCobro
     */
    public Long getIdDetalleSolicitudGestionCobro() {
        return idDetalleSolicitudGestionCobro;
    }

    /**
     * @param idDetalleSolicitudGestionCobro the idDetalleSolicitudGestionCobro to set
     */
    public void setIdDetalleSolicitudGestionCobro(Long idDetalleSolicitudGestionCobro) {
        this.idDetalleSolicitudGestionCobro = idDetalleSolicitudGestionCobro;
    }

    /**
     * @return the idPrimeraSolicitudRemision
     */
    public Long getIdPrimeraSolicitudRemision() {
        return idPrimeraSolicitudRemision;
    }

    /**
     * @param idPrimeraSolicitudRemision the idPrimeraSolicitudRemision to set
     */
    public void setIdPrimeraSolicitudRemision(Long idPrimeraSolicitudRemision) {
        this.idPrimeraSolicitudRemision = idPrimeraSolicitudRemision;
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

	@Override
	public String toString() {
		return "AportanteRemisionComunicadoDTO{" +
				"tipoAportante=" + tipoAportante +
				", tipoIdentificacion=" + tipoIdentificacion +
				", numeroIdentificacion='" + numeroIdentificacion + '\'' +
				", nombreCompleto='" + nombreCompleto + '\'' +
				", periodo=" + periodo +
				", destinatario='" + destinatario + '\'' +
				", departamento=" + departamento +
				", municipio=" + municipio +
				", direccion='" + direccion + '\'' +
				", codigoPostal='" + codigoPostal + '\'' +
				", telefono='" + telefono + '\'' +
				", enviar=" + enviar +
				", observacion='" + observacion + '\'' +
				", resultadoPrimeraEntrega=" + resultadoPrimeraEntrega +
				", observacionPrimeraEntrega='" + observacionPrimeraEntrega + '\'' +
				", idDocumentoPrimeraEntrega='" + idDocumentoPrimeraEntrega + '\'' +
				", fechaPrimeraEntrega=" + fechaPrimeraEntrega +
				", resultadoSegundaEntrega=" + resultadoSegundaEntrega +
				", observacionSegundaEntrega='" + observacionSegundaEntrega + '\'' +
				", idDocumentoSegundaEntrega='" + idDocumentoSegundaEntrega + '\'' +
				", fechaSegundaEntrega=" + fechaSegundaEntrega +
				", estadoTarea=" + estadoTarea +
				", consecutivoLiquidacion='" + consecutivoLiquidacion + '\'' +
				", fechaLiquidacion=" + fechaLiquidacion +
				", idDetalleSolicitudGestionCobro=" + idDetalleSolicitudGestionCobro +
				", idPrimeraSolicitudRemision=" + idPrimeraSolicitudRemision +
				", idCartera=" + idCartera +
				'}';
	}
}
