package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.pila.EstadoRegistroAportesArchivoEnum;

/**
 * DTO que contiene los campos de un aporte asociado a una planilla.
 * 
 * @author Ricarod Hernandez Cediel <hhernandez@heinsohn.com.co>
 *
 */
@XmlRootElement
public class AporteDetalladoPlanillaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Código identificador de llave primaria llamada No. de operación de
     * recaudo
     */
    private Long idAporteDetallado;

    /**
     * Descripción del tipo de cotizante
     */
    private TipoAfiliadoEnum tipoCotizante;

    /**
     * Número de identificación del cotizante
     */
    private String numeroIdentificacionCotizante;

    /**
     * Estado del registro del aporte en el procesamiento del archivo
     */
    private EstadoRegistroAportesArchivoEnum estadoRegistroAporteArchivo;

    /**
     * Descripción del estado del registro a nivel de Cotizante o pensionado
     */
    private EstadoRegistroAporteEnum estadoRegistroAporte;

    /**
     * Fecha de procesamiento del aporte (Sistema al momento de relacionar o registrar)
     */
    private Date fechaProcesamientoAporte;

    /**
     * Aporte obligatorio (sumatoria de Aporte obligatorio)
     */
    private BigDecimal aporteObligatorio;

    /**
     * Indica el usuario que aprueba el registro del aporte.
     */
    private String usuarioAprobadorAporte;

    /**
     * Constructor por defecto para JSON
     */
    public AporteDetalladoPlanillaDTO() {
    }

    /**
     * Constructor para retornar la busqueda de aportes detallado
     * @param idRegistroDetallado
     * @param tipoCotizante
     * @param idCotizante
     * @param estadoRegistroAporteArchivo
     * @param estadoRegistroAporte
     * @param fechaProcesamientoAporte
     * @param aporteObligatorio
     * @param usuarioAprobadorAporte
     */
    public AporteDetalladoPlanillaDTO(Long idRegistroDetallado, TipoAfiliadoEnum tipoCotizante, String idCotizante,
            EstadoRegistroAportesArchivoEnum estadoRegistroAporteArchivo, EstadoRegistroAporteEnum estadoRegistroAporte,
            Date fechaProcesamientoAporte, BigDecimal aporteObligatorio, String usuarioAprobadorAporte) {
        super();

        this.idAporteDetallado = idRegistroDetallado;
        this.tipoCotizante = tipoCotizante;
        this.numeroIdentificacionCotizante = idCotizante;
        this.aporteObligatorio = aporteObligatorio;
        this.estadoRegistroAporte = estadoRegistroAporte;
        this.estadoRegistroAporteArchivo = estadoRegistroAporteArchivo;
        this.usuarioAprobadorAporte = usuarioAprobadorAporte;
        this.fechaProcesamientoAporte = fechaProcesamientoAporte;

    }

    /**
     * @return the idAporteDetallado
     */
    public Long getIdAporteDetallado() {
        return idAporteDetallado;
    }

    /**
     * @param idAporteDetallado
     *        the idAporteDetallado to set
     */
    public void setIdAporteDetallado(Long idAporteDetallado) {
        this.idAporteDetallado = idAporteDetallado;
    }

    /**
     * @return the tipoCotizante
     */
    public TipoAfiliadoEnum getTipoCotizante() {
        return tipoCotizante;
    }

    /**
     * @param tipoCotizante
     *        the tipoCotizante to set
     */
    public void setTipoCotizante(TipoAfiliadoEnum tipoCotizante) {
        this.tipoCotizante = tipoCotizante;
    }

    /**
     * @return the numeroIdentificacionCotizante
     */
    public String getNumeroIdentificacionCotizante() {
        return numeroIdentificacionCotizante;
    }

    /**
     * @param numeroIdentificacionCotizante
     *        the numeroIdentificacionCotizante to set
     */
    public void setNumeroIdentificacionCotizante(String numeroIdentificacionCotizante) {
        this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
    }

    /**
     * @return the estadoRegistroAporteArchivo
     */
    public EstadoRegistroAportesArchivoEnum getEstadoRegistroAporteArchivo() {
        return estadoRegistroAporteArchivo;
    }

    /**
     * @param estadoRegistroAporteArchivo
     *        the estadoRegistroAporteArchivo to set
     */
    public void setEstadoRegistroAporteArchivo(EstadoRegistroAportesArchivoEnum estadoRegistroAporteArchivo) {
        this.estadoRegistroAporteArchivo = estadoRegistroAporteArchivo;
    }

    /**
     * @return the estadoRegistroAporte
     */
    public EstadoRegistroAporteEnum getEstadoRegistroAporte() {
        return estadoRegistroAporte;
    }

    /**
     * @param estadoRegistroAporte
     *        the estadoRegistroAporte to set
     */
    public void setEstadoRegistroAporte(EstadoRegistroAporteEnum estadoRegistroAporte) {
        this.estadoRegistroAporte = estadoRegistroAporte;
    }

    /**
     * @return the fechaProcesamientoAporte
     */
    public Date getFechaProcesamientoAporte() {
        return fechaProcesamientoAporte;
    }

    /**
     * @param fechaProcesamientoAporte
     *        the fechaProcesamientoAporte to set
     */
    public void setFechaProcesamientoAporte(Date fechaProcesamientoAporte) {
        this.fechaProcesamientoAporte = fechaProcesamientoAporte;
    }

    /**
     * @return the aporteObligatorio
     */
    public BigDecimal getAporteObligatorio() {
        return aporteObligatorio;
    }

    /**
     * @param aporteObligatorio
     *        the aporteObligatorio to set
     */
    public void setAporteObligatorio(BigDecimal aporteObligatorio) {
        this.aporteObligatorio = aporteObligatorio;
    }

    /**
     * @return the usuarioAprobadorAporte
     */
    public String getUsuarioAprobadorAporte() {
        return usuarioAprobadorAporte;
    }

    /**
     * @param usuarioAprobadorAporte
     *        the usuarioAprobadorAporte to set
     */
    public void setUsuarioAprobadorAporte(String usuarioAprobadorAporte) {
        this.usuarioAprobadorAporte = usuarioAprobadorAporte;
    }

}
