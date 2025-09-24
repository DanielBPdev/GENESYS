/**
 * 
 */
package com.asopagos.dto.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.asopagos.entidades.ccf.aportes.SolicitudAporte;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.enumeraciones.aportes.EstadoSolicitudAporteEnum;
import com.asopagos.enumeraciones.aportes.EvaluacionSolicitudEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO que representa los datos de una solicitud de aporte.
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 * @author <a href="mailto:criparra@heinsohn.com.co"> Cristian David Parra
 *         Zuluaga </a>
 */
public class SolicitudAporteModeloDTO extends SolicitudModeloDTO {
    private static final long serialVersionUID = 7412937218070064373L;

    /**
     * Código identificador de llave primaria del aportante
     */
    private Long idSolicitudAporte;

    /**
     * Estado de la solicitud de aporte.
     */
    private EstadoSolicitudAporteEnum estadoSolicitud;

    /**
     * Aporte asociado a la solicitud de aporte.
     */
    private Long idRegistroGeneral;

    /**
     * Número de identificacion del aportante.
     */
    private String numeroIdentificacion;

    /**
     * Tipo de identificacion del aportante.
     */
    private TipoIdentificacionEnum tipoIdentificacion;

    /**
     * Perido de pago.
     */
    private Long periodoPago;

    /**
     * Nombre aportante.
     */
    private String nombreAportante;

    /**
     * Evaluacion de la solicitud
     */
    private EvaluacionSolicitudEnum evaluacionSolicitud;

    /**
     * Observaciones del supervisor cuando la evaluación es rechazada.
     */
    private String observacionesSupervisor;

    /**
     * Tipo de solicitante de la devolución.
     */
    private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
    /**
     * Fecha de recepción de la solicitud de devolución del aporte.
     */
    private Long fechaRecepcion;

    /**
     * Variable que indica el id de la cuentaBancariaRecaudo
     */
    private Integer cuentaBancariaRecaudo;


    /**
     * Constructor por defecto
     * */
    public SolicitudAporteModeloDTO(){
        super();
    }
    
    /**
     * Constructor con base en entity
     * */
    public SolicitudAporteModeloDTO(SolicitudAporte solicitudAporte){
        super();
        convertToDTO(solicitudAporte);
    }
    
    /**
     * Método encargado de convertir de DTO a Entidad.
     * 
     * @return entidad convertida.
     */
    public SolicitudAporte convertToEntity() {
        SolicitudAporte solicitudAporte = new SolicitudAporte();
        solicitudAporte.setIdSolicitudAporte(this.getIdSolicitudAporte());
        solicitudAporte.setIdRegistroGeneral(this.getIdRegistroGeneral());
        Solicitud solicitudGlobal = super.convertToSolicitudEntity();
        solicitudAporte.setSolicitudGlobal(solicitudGlobal);
        solicitudAporte.setEstadoSolicitud(this.getEstadoSolicitud());
        solicitudAporte.setTipoIdentificacion(this.getTipoIdentificacion());
        solicitudAporte.setNumeroIdentificacion(this.getNumeroIdentificacion());
        solicitudAporte.setTipoSolicitante(this.getTipoSolicitante());
        solicitudAporte.setCuentaBancariaRecaudo(this.getCuentaBancariaRecaudo());
        if (this.getPeriodoPago() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            solicitudAporte.setPeriodoAporte(dateFormat.format(this.getPeriodoPago()));
        }
        solicitudAporte.setNombreAportante(this.getNombreAportante());
        return solicitudAporte;
    }

    /**
     * Método encargado de convertir de Entidad a DTO.
     * 
     * @param solicitud
     *        entidad a convertir.
     */
    public void convertToDTO(SolicitudAporte solicitudAporte) {
        if (solicitudAporte.getSolicitudGlobal() != null) {
            super.convertToDTO(solicitudAporte.getSolicitudGlobal());
        }
        this.setIdSolicitudAporte(solicitudAporte.getIdSolicitudAporte());
        this.setIdRegistroGeneral(solicitudAporte.getIdRegistroGeneral());
        this.setEstadoSolicitud(solicitudAporte.getEstadoSolicitud());
        this.setTipoIdentificacion(solicitudAporte.getTipoIdentificacion());
        this.setNumeroIdentificacion(solicitudAporte.getNumeroIdentificacion());
        this.setNombreAportante(solicitudAporte.getNombreAportante());
        this.setTipoSolicitante(solicitudAporte.getTipoSolicitante());
        this.setCuentaBancariaRecaudo(solicitudAporte.getCuentaBancariaRecaudo());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Date fechaPeriodo;
        try {
            fechaPeriodo = dateFormat.parse(solicitudAporte.getPeriodoAporte());
            this.setPeriodoPago(fechaPeriodo.getTime());
        } catch (Exception e) {
            this.setPeriodoPago(null);
        }

    }

    /**
     * Método encargado de copiar un DTO a una Entidad.
     * 
     * @param solicitudAporte
     *        previamente consultado.
     * @return solicitudAporte solicitud modificada con los datos del DTO.
     */
    public SolicitudAporte copyDTOToEntiy(SolicitudAporte solicitudAporte) {
        if (this.getIdSolicitudAporte() != null) {
            solicitudAporte.setIdSolicitudAporte(this.getIdSolicitudAporte());
        }
        if (this.getIdRegistroGeneral() != null) {
            solicitudAporte.setIdRegistroGeneral(solicitudAporte.getIdRegistroGeneral());
        }
        if (this.getEstadoSolicitud() != null) {
            solicitudAporte.setEstadoSolicitud(this.getEstadoSolicitud());
        }
        if (this.getTipoIdentificacion() != null) {
            solicitudAporte.setTipoIdentificacion(this.getTipoIdentificacion());
        }
        if (this.getNumeroIdentificacion() != null) {
            solicitudAporte.setNumeroIdentificacion(this.getNumeroIdentificacion());
        }
        if (this.getNombreAportante() != null) {
            solicitudAporte.setNombreAportante(this.getNombreAportante());
        }
        Solicitud solicitudGlobal = super.copyDTOToEntiy(solicitudAporte.getSolicitudGlobal());
        if (solicitudGlobal.getIdSolicitud() != null) {
            solicitudAporte.setSolicitudGlobal(solicitudGlobal);
        }
        if (this.getTipoSolicitante() != null) {
            solicitudAporte.setTipoSolicitante(this.getTipoSolicitante());
        }
        return solicitudAporte;
    }

    /**
     * Método que retorna el valor de idSolicitudAporte.
     * 
     * @return valor de idSolicitudAporte.
     */
    public Long getIdSolicitudAporte() {
        return idSolicitudAporte;
    }

    /**
     * Método encargado de modificar el valor de idSolicitudAporte.
     * 
     * @param valor
     *        para modificar idSolicitudAporte.
     */
    public void setIdSolicitudAporte(Long idSolicitudAporte) {
        this.idSolicitudAporte = idSolicitudAporte;
    }

    /**
     * Método que retorna el valor de estadoSolicitud.
     * 
     * @return valor de estadoSolicitud.
     */
    public EstadoSolicitudAporteEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public Integer getCuentaBancariaRecaudo() {
        return cuentaBancariaRecaudo;
    }

    public void setCuentaBancariaRecaudo(Integer cuentaBancariaRecaudo) {
        this.cuentaBancariaRecaudo = cuentaBancariaRecaudo;
    }


    /**
     * Método encargado de modificar el valor de estadoSolicitud.
     * 
     * @param valor
     *        para modificar estadoSolicitud.
     */
    public void setEstadoSolicitud(EstadoSolicitudAporteEnum estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * @return the idRegistroGeneral
     */
    public Long getIdRegistroGeneral() {
        return idRegistroGeneral;
    }

    /**
     * @param idRegistroGeneral
     *        the idRegistroGeneral to set
     */
    public void setIdRegistroGeneral(Long idRegistroGeneral) {
        this.idRegistroGeneral = idRegistroGeneral;
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
     *        para modificar numeroIdentificacion.
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * Método que retorna el valor de tipoIdentificacion.
     * 
     * @return valor de tipoIdentificacion.
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacion.
     * 
     * @param valor
     *        para modificar tipoIdentificacion.
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * Método que retorna el valor de periodoPago.
     * 
     * @return valor de periodoPago.
     */
    public Long getPeriodoPago() {
        return periodoPago;
    }

    /**
     * Método encargado de modificar el valor de periodoPago.
     * 
     * @param valor
     *        para modificar periodoPago.
     */
    public void setPeriodoPago(Long periodoPago) {
        this.periodoPago = periodoPago;
    }

    /**
     * Método que retorna el valor de nombreAportante.
     * 
     * @return valor de nombreAportante.
     */
    public String getNombreAportante() {
        return nombreAportante;
    }

    /**
     * Método encargado de modificar el valor de nombreAportante.
     * 
     * @param valor
     *        para modificar nombreAportante.
     */
    public void setNombreAportante(String nombreAportante) {
        this.nombreAportante = nombreAportante;
    }

    /**
     * Método que retorna el valor de evaluacionSolicitud.
     * 
     * @return valor de evaluacionSolicitud.
     */
    public EvaluacionSolicitudEnum getEvaluacionSolicitud() {
        return evaluacionSolicitud;
    }

    /**
     * Método encargado de modificar el valor de evaluacionSolicitud.
     * 
     * @param valor
     *        para modificar evaluacionSolicitud.
     */
    public void setEvaluacionSolicitud(EvaluacionSolicitudEnum evaluacionSolicitud) {
        this.evaluacionSolicitud = evaluacionSolicitud;
    }

    /**
     * Método que retorna el valor de observacionesSupervisor.
     * 
     * @return valor de observacionesSupervisor.
     */
    public String getObservacionesSupervisor() {
        return observacionesSupervisor;
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
     * Método encargado de modificar el valor de observacionesSupervisor.
     * 
     * @param valor
     *        para modificar observacionesSupervisor.
     */
    public void setObservacionesSupervisor(String observacionesSupervisor) {
        this.observacionesSupervisor = observacionesSupervisor;
    }

    /**
     * Método encargado de modificar el valor de tipoSolicitante.
     * 
     * @param valor
     *        para modificar tipoSolicitante.
     */
    public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
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
     *        para modificar fechaRecepcion.
     */
    public void setFechaRecepcion(Long fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }
}