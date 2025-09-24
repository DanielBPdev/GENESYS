package com.asopagos.dto.fovis;

import java.io.Serializable;
import com.asopagos.dto.modelo.IntegranteHogarModeloDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.PersonasUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * <b>Descripcion:</b> Clase que contiene la informacion del subsidio FOVIS<br/>
 * <b>Módulo:Fovis</b> Asopagos - HU 3.2.4-053<br/>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co">Jose Arley Correa</a>
 */
@JsonInclude(Include.NON_EMPTY)
public class InformacionIntegranteHogarSubsidioFOVISDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Tipo de identificación del Integrante del hogar
     */
    private TipoIdentificacionEnum tipoId;

    /**
     * Número de identificación del Integrante del hogar
     */
    private String identificacion;

    /**
     * Nombre completo del Integrante del hogar
     */
    private String nombreCompleto;

    /**
     * Tipo Integrante Hogar
     */
    private ClasificacionEnum tipoIntegranteHogar;

    /**
     * Condición del Integrante del Hogar
     */
    private String condicionIntegrante;

    /**
     * Constructor por defecto
     */
    public InformacionIntegranteHogarSubsidioFOVISDTO() {
        super();
    }

    public InformacionIntegranteHogarSubsidioFOVISDTO(IntegranteHogarModeloDTO integranteHogarModeloDTO){
        super();
        this.setTipoId(integranteHogarModeloDTO.getTipoIdentificacion());
        this.setIdentificacion(integranteHogarModeloDTO.getNumeroIdentificacion());
        this.setNombreCompleto(PersonasUtils.obtenerNombreORazonSocial(integranteHogarModeloDTO.convertToPersonaEntity()));
        this.setTipoIntegranteHogar(integranteHogarModeloDTO.getTipoIntegranteHogar());
    }
    /**
     * @return the tipoId
     */
    public TipoIdentificacionEnum getTipoId() {
        return tipoId;
    }

    /**
     * @param tipoId
     *        the tipoId to set
     */
    public void setTipoId(TipoIdentificacionEnum tipoId) {
        this.tipoId = tipoId;
    }

    /**
     * @return the identificacion
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * @param identificacion
     *        the identificacion to set
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    /**
     * @return the nombreCompleto
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * @param nombreCompleto
     *        the nombreCompleto to set
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * @return the tipoIntegranteHogar
     */
    public ClasificacionEnum getTipoIntegranteHogar() {
        return tipoIntegranteHogar;
    }

    /**
     * @param tipoIntegranteHogar
     *        the tipoIntegranteHogar to set
     */
    public void setTipoIntegranteHogar(ClasificacionEnum tipoIntegranteHogar) {
        this.tipoIntegranteHogar = tipoIntegranteHogar;
    }

    /**
     * @return the condicionIntegrante
     */
    public String getCondicionIntegrante() {
        return condicionIntegrante;
    }

    /**
     * @param condicionIntegrante
     *        the condicionIntegrante to set
     */
    public void setCondicionIntegrante(String condicionIntegrante) {
        this.condicionIntegrante = condicionIntegrante;
    }

}
