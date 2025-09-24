package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@JsonInclude(Include.NON_EMPTY)
public class DatosCotizanteIntegracionDTO implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 3434693108223860635L;

    /**
     * Tipo de identificación del cotizante.
     */
    private TipoIdentificacionEnum tipoIdentificacionCotizante;

    /**
     * Número de identificación del cotizante.
     */
    private String numeroIdentificacionCotizante;

    /**
     * Nombre completo compuesto de nombres y apellidos.
     */
    private String nombreCompletoCotizante;

    /**
     * Lista con los detalles de los aportes para el cotizante
     */
    private List<DetalleDatosCotizanteDTO> datosAportesCotizante;
    
    /**
     * Referencia a la persona asociada como cotizante
     */
    private Long idPersona;

    /**
     * Método constructor
     * 
     * @param cotizante
     */
    public DatosCotizanteIntegracionDTO(Persona cotizante) {
        this.setTipoIdentificacionCotizante(cotizante.getTipoIdentificacion()); 
        this.setNumeroIdentificacionCotizante(cotizante.getNumeroIdentificacion());
        StringBuilder nombre = new StringBuilder();
        nombre.append(cotizante.getPrimerNombre() != null ? cotizante.getPrimerNombre() + " " : "");
        nombre.append(cotizante.getSegundoNombre() != null ? cotizante.getSegundoNombre() + " " : "");
        nombre.append(cotizante.getPrimerApellido() != null ? cotizante.getPrimerApellido() + " " : "");
        nombre.append(cotizante.getSegundoApellido() != null ? cotizante.getSegundoApellido() : "");
        nombre = (nombre != null || nombre.toString().isEmpty()) ? nombre : new StringBuilder (cotizante.getRazonSocial());
        this.setNombreCompletoCotizante(nombre.toString());
        this.setIdPersona(cotizante.getIdPersona());
    }

    /**
     * 
     */
    public DatosCotizanteIntegracionDTO() {
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
     * @return the nombreCompletoCotizante
     */
    public String getNombreCompletoCotizante() {
        return nombreCompletoCotizante;
    }

    /**
     * @param nombreCompletoCotizante
     *        the nombreCompletoCotizante to set
     */
    public void setNombreCompletoCotizante(String nombreCompletoCotizante) {
        this.nombreCompletoCotizante = nombreCompletoCotizante;
    }

    /**
     * @return the datosAportesCotizante
     */
    public List<DetalleDatosCotizanteDTO> getDatosAportesCotizante() {
        return datosAportesCotizante;
    }

    /**
     * @param datosAportesCotizante
     *        the datosAportesCotizante to set
     */
    public void setDatosAportesCotizante(List<DetalleDatosCotizanteDTO> datosAportesCotizante) {
        this.datosAportesCotizante = datosAportesCotizante;
    }

    /**
     * @return the idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * @param idPersona the idPersona to set
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }
}
