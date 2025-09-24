package com.asopagos.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.AccionGestionRegistroAporteEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.enumeraciones.pila.EstadoRegistroAportesArchivoEnum;

/**
 * <b>Descripción:</b> Estructura DTO que transporta los de datos de los
 * registros de aporte de planilla PILA con inconsistencias
 * <b>Historia de Usuario:</b>
 * HU-211-399
 * 
 * @author Ricardo Hernandez Cediel <hhernandez@heinsohn.com.co>
 */
public class InconsistenciaRegistroAporteDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idIndicePlanilla;
    private Long idRegistroGeneralAporte;
    private Long idRegistroDetalladoAporte;
    private EstadoRegistroAportesArchivoEnum estadoAporte;
    private String numeroPlanilla;
    private TipoIdentificacionEnum tipoIdentificacionAportante;
    private String numIdentificacionAportante;
    private String nombreAportante;
    /* Indica el código de la sucursal o de la dependencia */
    private String codigoSucursal;
    /* Indica el nombre de la sucursal o de la dependencia */
    private String nombreSucursal;
    private TipoIdentificacionEnum tipoIdentificacionCotizante;
    private String numIdentificacionCotizante;
    private EstadoEmpleadorEnum estadoAportante;
    //private EstadoAfiliadoEnum estadoCotizante;
    private String estadoCotizante;
    private AccionGestionRegistroAporteEnum accionRegistroAporte;
    private Boolean registroProcesado;
    private Boolean archivoProcesadoVsBD;
    private Boolean esSimulado;
    private BloqueValidacionEnum bloque;

    /**
     * @param idIndicePlanilla
     * @param idRegistroGeneralAporte
     * @param idRegistroDetalladoAporte
     * @param estadoAporte
     * @param numeroPlanilla
     * @param tipoIdentificacionAportante
     * @param numIdentificacionAportante
     * @param nombreAportante
     * @param tipoIdentificacionCotizante
     * @param numIdentificacionCotizante
     * @param estadoAportante
     * @param estadoCotizante
     * @param esSimulado 
     */
    public InconsistenciaRegistroAporteDTO(Long idIndicePlanilla, Long idRegistroGeneralAporte, Long idRegistroDetalladoAporte,
            EstadoRegistroAportesArchivoEnum estadoAporte, String numeroPlanilla, TipoIdentificacionEnum tipoIdentificacionAportante,
            String numIdentificacionAportante, String nombreAportante, TipoIdentificacionEnum tipoIdentificacionCotizante,
            String numIdentificacionCotizante, EstadoEmpleadorEnum estadoAportante, String estadoCotizante, Boolean esSimulado) {
        super();
        this.idIndicePlanilla = idIndicePlanilla;
        this.idRegistroGeneralAporte = idRegistroGeneralAporte;
        this.idRegistroDetalladoAporte = idRegistroDetalladoAporte;
        this.estadoAporte = estadoAporte;
        this.numeroPlanilla = numeroPlanilla;
        this.tipoIdentificacionAportante = tipoIdentificacionAportante;
        this.numIdentificacionAportante = numIdentificacionAportante;
        this.nombreAportante = nombreAportante;
        this.tipoIdentificacionCotizante = tipoIdentificacionCotizante;
        this.numIdentificacionCotizante = numIdentificacionCotizante;
        this.estadoAportante = estadoAportante;
        this.estadoCotizante = estadoCotizante;
        this.registroProcesado = false;
        this.archivoProcesadoVsBD = false;
        this.esSimulado = esSimulado;
    }

    /**
     * @param idIndicePlanilla
     * @param idRegistroGeneralAporte
     * @param idRegistroDetalladoAporte
     * @param estadoAporte
     * @param numeroPlanilla
     * @param tipoIdentificacionAportante
     * @param numIdentificacionAportante
     * @param nombreAportante
     * @param codigoSucursal
     * @param nombreSucursal
     * @param tipoIdentificacionCotizante
     * @param numIdentificacionCotizante
     * @param estadoAportante
     * @param estadoCotizante
     * @param esSimulado 
     */
    public InconsistenciaRegistroAporteDTO(Long idIndicePlanilla, Long idRegistroGeneralAporte, Long idRegistroDetalladoAporte,
            EstadoRegistroAportesArchivoEnum estadoAporte, String numeroPlanilla, TipoIdentificacionEnum tipoIdentificacionAportante,
            String numIdentificacionAportante, String nombreAportante, String codigoSucursal, String nombreSucursal,
            TipoIdentificacionEnum tipoIdentificacionCotizante, String numIdentificacionCotizante, EstadoEmpleadorEnum estadoAportante,
            String estadoCotizante, Boolean esSimulado) {
        super();
        this.idIndicePlanilla = idIndicePlanilla;
        this.idRegistroGeneralAporte = idRegistroGeneralAporte;
        this.idRegistroDetalladoAporte = idRegistroDetalladoAporte;
        this.estadoAporte = estadoAporte;
        this.numeroPlanilla = numeroPlanilla;
        this.tipoIdentificacionAportante = tipoIdentificacionAportante;
        this.numIdentificacionAportante = numIdentificacionAportante;
        this.nombreAportante = nombreAportante;
        this.codigoSucursal = codigoSucursal;
        this.nombreSucursal = nombreSucursal;
        this.tipoIdentificacionCotizante = tipoIdentificacionCotizante;
        this.numIdentificacionCotizante = numIdentificacionCotizante;
        this.estadoAportante = estadoAportante;
        this.estadoCotizante = estadoCotizante;
        this.registroProcesado = false;
        this.archivoProcesadoVsBD = false;
        this.esSimulado = esSimulado;
    }

    /**
     * @return the idIndicePlanilla
     */
    public Long getIdIndicePlanilla() {
        return idIndicePlanilla;
    }

    /**
     * @param idIndicePlanilla
     *        the idIndicePlanilla to set
     */
    public void setIdIndicePlanilla(Long idIndicePlanilla) {
        this.idIndicePlanilla = idIndicePlanilla;
    }

    /**
     * @return the idRegistroGeneralAporte
     */
    public Long getIdRegistroGeneralAporte() {
        return idRegistroGeneralAporte;
    }

    /**
     * @param idRegistroGeneralAporte
     *        the idRegistroGeneralAporte to set
     */
    public void setIdRegistroGeneralAporte(Long idRegistroGeneralAporte) {
        this.idRegistroGeneralAporte = idRegistroGeneralAporte;
    }

    /**
     * @return the idRegistroDetalladoAporte
     */
    public Long getIdRegistroDetalladoAporte() {
        return idRegistroDetalladoAporte;
    }

    /**
     * @param idRegistroDetalladoAporte
     *        the idRegistroDetalladoAporte to set
     */
    public void setIdRegistroDetalladoAporte(Long idRegistroDetalladoAporte) {
        this.idRegistroDetalladoAporte = idRegistroDetalladoAporte;
    }

    /**
     * @return the estadoAporte
     */
    public EstadoRegistroAportesArchivoEnum getEstadoAporte() {
        return estadoAporte;
    }

    /**
     * @param estadoAporte
     *        the estadoAporte to set
     */
    public void setEstadoAporte(EstadoRegistroAportesArchivoEnum estadoAporte) {
        this.estadoAporte = estadoAporte;
    }

    /**
     * @return the numeroPlanilla
     */
    public String getNumeroPlanilla() {
        return numeroPlanilla;
    }

    /**
     * @param numeroPlanilla
     *        the numeroPlanilla to set
     */
    public void setNumeroPlanilla(String numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }

    /**
     * @return the tipoIdentificacionAportante
     */
    public TipoIdentificacionEnum getTipoIdentificacionAportante() {
        return tipoIdentificacionAportante;
    }

    /**
     * @param tipoIdentificacionAportante
     *        the tipoIdentificacionAportante to set
     */
    public void setTipoIdentificacionAportante(TipoIdentificacionEnum tipoIdentificacionAportante) {
        this.tipoIdentificacionAportante = tipoIdentificacionAportante;
    }

    /**
     * @return the numIdentificacionAportante
     */
    public String getNumIdentificacionAportante() {
        return numIdentificacionAportante;
    }

    /**
     * @param numIdentificacionAportante
     *        the numIdentificacionAportante to set
     */
    public void setNumIdentificacionAportante(String numIdentificacionAportante) {
        this.numIdentificacionAportante = numIdentificacionAportante;
    }

    /**
     * @return the nombreAportante
     */
    public String getNombreAportante() {
        return nombreAportante;
    }

    /**
     * @param nombreAportante
     *        the nombreAportante to set
     */
    public void setNombreAportante(String nombreAportante) {
        this.nombreAportante = nombreAportante;
    }

    /**
     * @return the codigoSucursal
     */
    public String getCodigoSucursal() {
        return codigoSucursal;
    }

    /**
     * @param codigoSucursal
     *        the codigoSucursal to set
     */
    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    /**
     * @return the nombreSucursal
     */
    public String getNombreSucursal() {
        return nombreSucursal;
    }

    /**
     * @param nombreSucursal
     *        the nombreSucursal to set
     */
    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    /**
     * @return the tipoIdentificacionCotizante
     */
    public TipoIdentificacionEnum getTipoIdentificacionCotizante() {
        return tipoIdentificacionCotizante;
    }

    /**
     * @param tipoIdentificacionCotizante
     *        the tipoIdentificacionCotizante to set
     */
    public void setTipoIdentificacionCotizante(TipoIdentificacionEnum tipoIdentificacionCotizante) {
        this.tipoIdentificacionCotizante = tipoIdentificacionCotizante;
    }

    /**
     * @return the numIdentificacionCotizante
     */
    public String getNumIdentificacionCotizante() {
        return numIdentificacionCotizante;
    }

    /**
     * @param numIdentificacionCotizante
     *        the numIdentificacionCotizante to set
     */
    public void setNumIdentificacionCotizante(String numIdentificacionCotizante) {
        this.numIdentificacionCotizante = numIdentificacionCotizante;
    }

    /**
     * @return the estadoAportante
     */
    public EstadoEmpleadorEnum getEstadoAportante() {
        return estadoAportante;
    }

    /**
     * @param estadoAportante
     *        the estadoAportante to set
     */
    public void setEstadoAportante(EstadoEmpleadorEnum estadoAportante) {
        this.estadoAportante = estadoAportante;
    }

    /**
     * @return the estadoCotizante
     */
    public String getEstadoCotizante() {
        return estadoCotizante;
    }

    /**
     * @param estadoCotizante
     *        the estadoCotizante to set
     */
    public void setEstadoCotizante(String estadoCotizante) {
        this.estadoCotizante = estadoCotizante;
    }

    /**
     * @return the accionRegistroAporte
     */
    public AccionGestionRegistroAporteEnum getAccionRegistroAporte() {
        return accionRegistroAporte;
    }

    /**
     * @param accionRegistroAporte
     *        the accionRegistroAporte to set
     */
    public void setAccionRegistroAporte(AccionGestionRegistroAporteEnum accionRegistroAporte) {
        this.accionRegistroAporte = accionRegistroAporte;
    }

    /**
     * @return the registroProcesado
     */
    public Boolean getRegistroProcesado() {
        return registroProcesado;
    }

    /**
     * @param registroProcesado
     *        the registroProcesado to set
     */
    public void setRegistroProcesado(Boolean registroProcesado) {
        this.registroProcesado = registroProcesado;
    }

    /**
     * @return the archivoProcesadoVsBD
     */
    public Boolean getArchivoProcesadoVsBD() {
        return archivoProcesadoVsBD;
    }

    /**
     * @param archivoProcesadoVsBD
     *        the archivoProcesadoVsBD to set
     */
    public void setArchivoProcesadoVsBD(Boolean archivoProcesadoVsBD) {
        this.archivoProcesadoVsBD = archivoProcesadoVsBD;
    }

    public InconsistenciaRegistroAporteDTO() {}

    /**
     * @return the esSimulado
     */
    public Boolean getEsSimulado() {
        return esSimulado;
    }

    /**
     * @param esSimulado the esSimulado to set
     */
    public void setEsSimulado(Boolean esSimulado) {
        this.esSimulado = esSimulado;
    }

    /**
     * @return the bloque
     */
    public BloqueValidacionEnum getBloque() {
        return bloque;
    }

    /**
     * @param bloque the bloque to set
     */
    public void setBloque(BloqueValidacionEnum bloque) {
        this.bloque = bloque;
    }

}
