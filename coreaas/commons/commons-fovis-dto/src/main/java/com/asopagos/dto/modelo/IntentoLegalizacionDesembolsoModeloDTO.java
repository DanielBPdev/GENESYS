package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.IntentoLegalizacionDesembolso;
import com.asopagos.entidades.ccf.fovis.IntentoLegalizacionDesembolsoRequisito;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.CausaIntentoFallidoLegalizacionDesembolsoEnum;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;

/**
 * Clase DTO para el intento de Legalización y desembolso.
 * 
 * @author Alexander Quintero <alquintero@heinsohn.com.co>
 */
@XmlRootElement
public class IntentoLegalizacionDesembolsoModeloDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 1787512454005454L;
    /**
     * Código identificador de llave primaria del intento de afiliación
     */
    private Long idIntentoLegalizacionDesembolso;
    /**
     * Referencia a la solicitud asociada al intento de postulación
     */
    private Long idSolicitud;
    /**
     * Descripción de la causa por la que un intento de afiliación no resulta
     * exitoso
     */
    private CausaIntentoFallidoLegalizacionDesembolsoEnum causaIntentoFallido;
    /**
     * Descripción del tipo de transacción del proceso
     */
    private TipoTransaccionEnum tipoTransaccion;
    /**
     * Descripción de la sede de caja de compensación
     */
    private String sedeCajaCompensacion;
    /**
     * Fecha de inicio del proceso
     */
    private Date fechaInicioProceso;
    /**
     * Indica el proceso de negocio por el cual se registra el intento de
     * postulación
     */
    private ProcesoEnum proceso;
    /**
     * Indica el tipo de solicitante en la postulación. Por defecto, será
     * <b>HOGAR</b>
     */
    private String tipoSolicitante;
    /**
     * Indica la modalidad de vivienda registrado en la postulación FOVIS
     */
    private ModalidadEnum modalidad;
    /**
     * Descripción del requisito de intento de afiliación
     */
    private List<IntentoLegalizacionDesembolsoRequisito> requsitos;
    /**
     * Fecha creación
     */
    private Date fechaCreacion;
    /**
     * Usuario de creación
     */
    private String usuarioCreacion;

    /**
     * Método encargado de convertir de DTO a Entidad.
     * 
     * @return entidad convertida.
     */
    public IntentoLegalizacionDesembolso convertToIntentoLegalizacionDesembolsoEntity() {
        IntentoLegalizacionDesembolso intentoLegalizacionDesembolso = new IntentoLegalizacionDesembolso();
        intentoLegalizacionDesembolso.setIdIntentoLegalizacionDesembolso(this.getIdIntentoLegalizacionDesembolso());
        intentoLegalizacionDesembolso.setCausaIntentoFallido(this.getCausaIntentoFallido());
        intentoLegalizacionDesembolso.setFechaCreacion(this.getFechaCreacion());
        intentoLegalizacionDesembolso.setIdSolicitud(this.getIdSolicitud());
        intentoLegalizacionDesembolso.setRequsitos(this.getRequsitos());
        intentoLegalizacionDesembolso.setSedeCajaCompensacion(this.getSedeCajaCompensacion());
        intentoLegalizacionDesembolso.setUsuarioCreacion(this.getUsuarioCreacion());
        intentoLegalizacionDesembolso.setProceso(this.getProceso());
        intentoLegalizacionDesembolso.setTipoSolicitante(this.getTipoSolicitante());
        intentoLegalizacionDesembolso.setModalidad(this.getModalidad());
        return intentoLegalizacionDesembolso;
    }

    /**
     * Método encargado de convertir de Entidad a DTO.
     * 
     * @param intentoLegalizacionDesembolso
     *        entidad a convertir.
     */
    public void convertToDTO(IntentoLegalizacionDesembolso intentoLegalizacionDesembolso) {
        this.setIdIntentoLegalizacionDesembolso(intentoLegalizacionDesembolso.getIdIntentoLegalizacionDesembolso());
        this.setCausaIntentoFallido(intentoLegalizacionDesembolso.getCausaIntentoFallido());
        this.setFechaCreacion(intentoLegalizacionDesembolso.getFechaCreacion());
        this.setRequsitos(intentoLegalizacionDesembolso.getRequsitos());
        this.setIdSolicitud(intentoLegalizacionDesembolso.getIdSolicitud());
        this.setSedeCajaCompensacion(intentoLegalizacionDesembolso.getSedeCajaCompensacion());
        this.setUsuarioCreacion(intentoLegalizacionDesembolso.getUsuarioCreacion());
        this.setProceso(intentoLegalizacionDesembolso.getProceso());
        this.setTipoSolicitante(intentoLegalizacionDesembolso.getTipoSolicitante());
        this.setModalidad(intentoLegalizacionDesembolso.getModalidad());
    }

    /**
     * Método encargado de copiar un DTO a una Entidad.
     * 
     * @param intentoLegalizacionDesembolso
     *        previamente consultado.
     */
    public IntentoLegalizacionDesembolso copyDTOToEntiy(IntentoLegalizacionDesembolso intentoLegalizacionDesembolso) {
        if (this.getIdIntentoLegalizacionDesembolso() != null) {
            intentoLegalizacionDesembolso.setIdIntentoLegalizacionDesembolso(this.getIdIntentoLegalizacionDesembolso());
        }
        if (this.getCausaIntentoFallido() != null) {
            intentoLegalizacionDesembolso.setCausaIntentoFallido(this.getCausaIntentoFallido());
        }
        if (this.getFechaCreacion() != null) {
            intentoLegalizacionDesembolso.setFechaCreacion(this.getFechaCreacion());
        }
        if (this.getRequsitos() != null) {
            intentoLegalizacionDesembolso.setRequsitos(this.getRequsitos());
        }
        if (this.getSedeCajaCompensacion() != null) {
            intentoLegalizacionDesembolso.setSedeCajaCompensacion(this.getSedeCajaCompensacion());
        }
        if (this.getIdSolicitud() != null) {
            intentoLegalizacionDesembolso.setIdSolicitud(this.getIdSolicitud());
        }
        if (this.getUsuarioCreacion() != null) {
            intentoLegalizacionDesembolso.setUsuarioCreacion(this.getUsuarioCreacion());
        }
        if (this.getProceso() != null) {
            intentoLegalizacionDesembolso.setProceso(this.getProceso());
        }
        if (this.getTipoSolicitante() != null) {
            intentoLegalizacionDesembolso.setTipoSolicitante(this.getTipoSolicitante());
        }
        if (this.getModalidad() != null) {
            intentoLegalizacionDesembolso.setModalidad(this.getModalidad());
        }
        return intentoLegalizacionDesembolso;
    }

    /**
     * @return the idIntentoLegalizacionDesembolso
     */
    public Long getIdIntentoLegalizacionDesembolso() {
        return idIntentoLegalizacionDesembolso;
    }

    /**
     * @param idIntentoLegalizacionDesembolso
     *        the idIntentoLegalizacionDesembolso to set
     */
    public void setIdIntentoLegalizacionDesembolso(Long idIntentoLegalizacionDesembolso) {
        this.idIntentoLegalizacionDesembolso = idIntentoLegalizacionDesembolso;
    }

    /**
     * @return the causaIntentoFallido
     */
    public CausaIntentoFallidoLegalizacionDesembolsoEnum getCausaIntentoFallido() {
        return causaIntentoFallido;
    }

    /**
     * @param causaIntentoFallido
     *        the causaIntentoFallido to set
     */
    public void setCausaIntentoFallido(CausaIntentoFallidoLegalizacionDesembolsoEnum causaIntentoFallido) {
        this.causaIntentoFallido = causaIntentoFallido;
    }

    /**
     * @return the tipoTransaccion
     */
    public TipoTransaccionEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * @param tipoTransaccion
     *        the tipoTransaccion to set
     */
    public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    /**
     * @return the sedeCajaCompensacion
     */
    public String getSedeCajaCompensacion() {
        return sedeCajaCompensacion;
    }

    /**
     * @param sedeCajaCompensacion
     *        the sedeCajaCompensacion to set
     */
    public void setSedeCajaCompensacion(String sedeCajaCompensacion) {
        this.sedeCajaCompensacion = sedeCajaCompensacion;
    }

    /**
     * @return the fechaInicioProceso
     */
    public Date getFechaInicioProceso() {
        return fechaInicioProceso;
    }

    /**
     * @param fechaInicioProceso
     *        the fechaInicioProceso to set
     */
    public void setFechaInicioProceso(Date fechaInicioProceso) {
        this.fechaInicioProceso = fechaInicioProceso;
    }

    /**
     * @return the requsitos
     */
    public List<IntentoLegalizacionDesembolsoRequisito> getRequsitos() {
        return requsitos;
    }

    /**
     * @param requsitos
     *        the requsitos to set
     */
    public void setRequsitos(List<IntentoLegalizacionDesembolsoRequisito> requsitos) {
        this.requsitos = requsitos;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion
     *        the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the usuarioCreacion
     */
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     * @param usuarioCreacion
     *        the usuarioCreacion to set
     */
    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     * @return the idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud
     *        the idSolicitud to set
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * Obtiene el valor de proceso
     * 
     * @return El valor de proceso
     */
    public ProcesoEnum getProceso() {
        return proceso;
    }

    /**
     * Establece el valor de proceso
     * 
     * @param proceso
     *        El valor de proceso por asignar
     */
    public void setProceso(ProcesoEnum proceso) {
        this.proceso = proceso;
    }

    /**
     * Obtiene el valor de tipoSolicitante
     * 
     * @return El valor de tipoSolicitante
     */
    public String getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * Establece el valor de tipoSolicitante
     * 
     * @param tipoSolicitante
     *        El valor de tipoSolicitante por asignar
     */
    public void setTipoSolicitante(String tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * Obtiene el valor de modalidad
     * 
     * @return El valor de modalidad
     */
    public ModalidadEnum getModalidad() {
        return modalidad;
    }

    /**
     * Establece el valor de modalidad
     * 
     * @param modalidad
     *        El valor de modalidad por asignar
     */
    public void setModalidad(ModalidadEnum modalidad) {
        this.modalidad = modalidad;
    }
}
