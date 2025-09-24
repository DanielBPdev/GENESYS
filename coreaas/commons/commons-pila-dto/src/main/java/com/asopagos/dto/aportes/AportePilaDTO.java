package com.asopagos.dto.aportes;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.aportes.AporteDetallado;
import com.asopagos.entidades.ccf.aportes.AporteGeneral;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * @author <a href="mailto:squintero@heinsohn.com.co">Steven Quintero González.</a>
 */
@XmlRootElement
public class AportePilaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private AporteGeneral aporteGeneral;
    private AporteDetallado aporteDetallado;
    private TipoIdentificacionEnum tipoIdCotizante;
    private String numeroIdCotizante;
    private String emailCotizante;
    private String emailAportante;

    /**
     * 
     */
    public AportePilaDTO() {
    }

    /**
     * @param aporteGeneral
     * @param aporteDetallado
     * @param tipoIdCotizante
     * @param numeroIdCotizante
     * @param emailCotizante
     * @param emailAportante
     */
    public AportePilaDTO(AporteGeneral aporteGeneral, AporteDetallado aporteDetallado, TipoIdentificacionEnum tipoIdCotizante,
            String numeroIdCotizante, String emailCotizante, String emailAportante) {
        this.aporteGeneral = aporteGeneral;
        this.aporteDetallado = aporteDetallado;
        this.tipoIdCotizante = tipoIdCotizante;
        this.numeroIdCotizante = numeroIdCotizante;
        this.emailCotizante = emailCotizante;
        this.emailAportante = emailAportante;
    }

    /**
     * Método que retorna el valor de aporteGeneral.
     * @return valor de aporteGeneral.
     */
    public AporteGeneral getAporteGeneral() {
        return aporteGeneral;
    }

    /**
     * Método encargado de modificar el valor de aporteGeneral.
     * @param valor
     *        para modificar aporteGeneral.
     */
    public void setAporteGeneral(AporteGeneral aporteGeneral) {
        this.aporteGeneral = aporteGeneral;
    }

    /**
     * Método que retorna el valor de aporteDetallado.
     * @return valor de aporteDetallado.
     */
    public AporteDetallado getAporteDetallado() {
        return aporteDetallado;
    }

    /**
     * Método encargado de modificar el valor de aporteDetallado.
     * @param valor
     *        para modificar aporteDetallado.
     */
    public void setAporteDetallado(AporteDetallado aporteDetallado) {
        this.aporteDetallado = aporteDetallado;
    }

    /**
     * Método que retorna el valor de tipoIdCotizante.
     * @return valor de tipoIdCotizante.
     */
    public TipoIdentificacionEnum getTipoIdCotizante() {
        return tipoIdCotizante;
    }

    /**
     * Método encargado de modificar el valor de tipoIdCotizante.
     * @param valor
     *        para modificar tipoIdCotizante.
     */
    public void setTipoIdCotizante(TipoIdentificacionEnum tipoIdCotizante) {
        this.tipoIdCotizante = tipoIdCotizante;
    }

    /**
     * Método que retorna el valor de numeroIdCotizante.
     * @return valor de numeroIdCotizante.
     */
    public String getNumeroIdCotizante() {
        return numeroIdCotizante;
    }

    /**
     * Método encargado de modificar el valor de numeroIdCotizante.
     * @param valor
     *        para modificar numeroIdCotizante.
     */
    public void setNumeroIdCotizante(String numeroIdCotizante) {
        this.numeroIdCotizante = numeroIdCotizante;
    }

    /**
     * Método que retorna el valor de emailCotizante.
     * @return valor de emailCotizante.
     */
    public String getEmailCotizante() {
        return emailCotizante;
    }

    /**
     * Método encargado de modificar el valor de emailCotizante.
     * @param valor
     *        para modificar emailCotizante.
     */
    public void setEmailCotizante(String emailCotizante) {
        this.emailCotizante = emailCotizante;
    }

    /**
     * Método que retorna el valor de emailAportante.
     * @return valor de emailAportante.
     */
    public String getEmailAportante() {
        return emailAportante;
    }

    /**
     * Método encargado de modificar el valor de emailAportante.
     * @param valor
     *        para modificar emailAportante.
     */
    public void setEmailAportante(String emailAportante) {
        this.emailAportante = emailAportante;
    }
}