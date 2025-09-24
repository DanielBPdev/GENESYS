package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.entidades.ccf.fovis.JefeHogar;
import com.asopagos.entidades.ccf.fovis.PostulacionFOVIS;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.enumeraciones.fovis.EstadoFOVISHogarEnum;

/**
 * <b>Descripción: </b> DTO que representa los datos de la tabla
 * <code>JefeHogar</code> <br/>
 * <b>Historia de Usuario: </b> HU-023
 *
 * @author <a href="mailto:fvasquez@heinsohn.com.co">Ferney Alonso Vásquez
 *         Benavides</a>
 */
public class JefeHogarModeloDTO extends PersonaModeloDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 5722355533231245062L;

    /**
     * Identificador único, llave primaria
     */
    private Long idJefeHogar;

    /**
     * Asociación del Afiliado al Jefe de Hogar
     */
    private Long idAfiliado;

    /**
     * Estado del jefe respecto al hogar
     */
    private EstadoFOVISHogarEnum estadoHogar;

    /**
     * Ingresos Mensuales del Jefe de Hogar
     */
    private BigDecimal ingresosMensuales;

    /**
     * Indica si el jefe de hogar esta ingabilitado para el subsidio
     */
    private Boolean inhabilitadoParaSubsidio;
    
    private Boolean afiliable;

    /**
     * Constructor por defecto
     */
    public JefeHogarModeloDTO() {
        super();
    }

    /**
     * Constructor con datos de entidad y persona
     * @param jefeHogar
     *        Info Entidad jefe
     * @param persona
     *        Info Entidad persona
     * @param personaDetalle
     *        Info Entidad persona detalle
     */
    public JefeHogarModeloDTO(JefeHogar jefeHogar, Persona persona, PersonaDetalle personaDetalle) {
        this.convertToJefeHogarDTO(jefeHogar, persona, personaDetalle);
    }

    public JefeHogarModeloDTO(JefeHogar jefeHogar, Persona persona, PersonaDetalle personaDetalle,
                              PostulacionFOVIS postulacionFOVIS) {
        this.convertToJefeHogarDTO(jefeHogar, persona, personaDetalle, postulacionFOVIS);
    }

    /**
     * Método que convierte el DTO a la entidad equivalente
     *
     * @return Objeto <code>JefeHogar</code> resultante del proceso de
     *         conversión
     */
    public JefeHogar convertToEntity() {
        JefeHogar jefeHogar = new JefeHogar();
        jefeHogar.setIdJefeHogar(this.getIdJefeHogar());
        jefeHogar.setIdAfiliado(this.getIdAfiliado());
        jefeHogar.setEstadoHogar(this.getEstadoHogar());
        jefeHogar.setIngresosMensuales(this.getIngresosMensuales());
        return jefeHogar;
    }

    /**
     * Método que convierte la información de las entidades al DTO
     *
     * @param jefeHogar
     *        La entidad <code>JefeHogar</code> a convertir
     * @param persona La entidad <code>Persona</code> a convertir
     * @param personaDetalle La entidad <code>PersonaDetalle</code> a convertir
     */
    public void convertToJefeHogarDTO(JefeHogar jefeHogar, Persona persona, PersonaDetalle personaDetalle) {
        super.convertToDTO(persona, personaDetalle);
        this.setIdAfiliado(jefeHogar.getIdAfiliado());
        this.setIdJefeHogar(jefeHogar.getIdJefeHogar());
        this.setEstadoHogar(jefeHogar.getEstadoHogar());
        this.setIngresosMensuales(jefeHogar.getIngresosMensuales());
    }

    /**
     * Método que convierte la información de las entidades al DTO
     *
     * @param jefeHogar
     *        La entidad <code>JefeHogar</code> a convertir
     * @param persona La entidad <code>Persona</code> a convertir
     * @param personaDetalle La entidad <code>PersonaDetalle</code> a convertir
     */
    public void convertToJefeHogarDTO(JefeHogar jefeHogar, Persona persona, PersonaDetalle personaDetalle, PostulacionFOVIS postulacionFOVIS) {
        super.convertToDTO(persona, personaDetalle);
        this.setIdAfiliado(jefeHogar.getIdAfiliado());
        this.setIdJefeHogar(jefeHogar.getIdJefeHogar());
        this.setEstadoHogar(jefeHogar.getEstadoHogar());
        this.setIngresosMensuales(postulacionFOVIS.getValorSalarioAsignacion());
    }

    /**
     * Copia el DTO a la entidad
     * @param jefeHogar
     *        Entidad previamente consultada
     */
    public void copyDTOToEntity(JefeHogar jefeHogar) {
        if (this.getIngresosMensuales() != null) {
            jefeHogar.setIngresosMensuales(this.getIngresosMensuales());
        }
        if (this.getEstadoHogar() != null) {
            jefeHogar.setEstadoHogar(this.getEstadoHogar());
        }
        if (this.getIdAfiliado() != null) {
            jefeHogar.setIdAfiliado(this.getIdAfiliado());
        }
        if (this.getIdJefeHogar() != null) {
            jefeHogar.setIdJefeHogar(this.getIdJefeHogar());
        }
    }

    /**
     * Obtiene el valor de idJefeHogar
     *
     * @return El valor de idJefeHogar
     */
    public Long getIdJefeHogar() {
        return idJefeHogar;
    }

    /**
     * Establece el valor de idJefeHogar
     *
     * @param idJefeHogar
     *        El valor de idJefeHogar por asignar
     */
    public void setIdJefeHogar(Long idJefeHogar) {
        this.idJefeHogar = idJefeHogar;
    }

    /**
     * Obtiene el valor de idAfiliado
     *
     * @return El valor de idAfiliado
     */
    public Long getIdAfiliado() {
        return idAfiliado;
    }

    /**
     * Establece el valor de idAfiliado
     *
     * @param idAfiliado
     *        El valor de idAfiliado por asignar
     */
    public void setIdAfiliado(Long idAfiliado) {
        this.idAfiliado = idAfiliado;
    }

    /**
     * Obtiene el valor de estadoHogar
     *
     * @return El valor de estadoHogar
     */
    public EstadoFOVISHogarEnum getEstadoHogar() {
        return estadoHogar;
    }

    /**
     * Establece el valor de estadoHogar
     *
     * @param estadoHogar
     *        El valor de estadoHogar por asignar
     */
    public void setEstadoHogar(EstadoFOVISHogarEnum estadoHogar) {
        this.estadoHogar = estadoHogar;
    }

    /**
     * @return the ingresosMensuales
     */
    public BigDecimal getIngresosMensuales() {
        return ingresosMensuales;
    }

    /**
     * @param ingresosMensuales
     *        the ingresosMensuales to set
     */
    public void setIngresosMensuales(BigDecimal ingresosMensuales) {
        this.ingresosMensuales = ingresosMensuales;
    }

    /**
     * @return the inhabilitadoParaSubsidio
     */
    public Boolean getInhabilitadoParaSubsidio() {
        return inhabilitadoParaSubsidio;
    }

    /**
     * @param inhabilitadoParaSubsidio
     *        the inhabilitadoParaSubsidio to set
     */
    public void setInhabilitadoParaSubsidio(Boolean inhabilitadoParaSubsidio) {
        this.inhabilitadoParaSubsidio = inhabilitadoParaSubsidio;
    }
    
    public Boolean getAfiliable() {
        return afiliable;
    }
    
    public void setAfiliable(Boolean afiliable) {
        this.afiliable = afiliable;
    }

}