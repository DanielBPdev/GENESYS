package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.fovis.InhabilidadSubsidioFovis;

/**
 * 
 * Contiene la información del Inhabilidad subsidio FOVIS - Procesos FOVIS 3.2.5
 * 
 * @author <a href="mailto:ecastano@heinsohn.com.co">Edward Castano</a>
 */
@XmlRootElement
public class InhabilidadSubsidioFovisModeloDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 8063825310544392502L;

    /**
     * Identificador único, llave primaria
     */
    private Long idInhabilidadSubsidioFovis;

    /**
     * Identificador de la solicitud de asignacion
     */
    private JefeHogarModeloDTO jefeHogar;

    /**
     * Asociación de los datos de la solicitud de asignación
     * en el caso que sea necesario para presentar los datos
     */
    private IntegranteHogarModeloDTO integranteHogar;

    /**
     * Fecha de creacion acta de asignacion fovis
     */
    private Long fechaInicioInhabilidad;

    /**
     * Fecha de creacion acta de asignacion fovis
     */
    private Long fechaFinInhabilidad;

    /**
     * Numero de resolucion del acta de aprobacion
     */
    private Boolean inhabilitadoParaSubsidio;

    /**
     * Identificador de la persona asociada al jefe de hogar
     */
    private Long idPersona;

    /**
     * Constructor vacio
     */
    public InhabilidadSubsidioFovisModeloDTO() {
    }

    /**
     * Constructor que recibe el entity del inhabilidad y la persona asociada
     * @param inhabilidadSubsidioFovis
     *        inhabilidad asociada a el jefe de hogar o integrante
     * @param idPersonaJefeHogar
     *        Identificador de la persona asociada al jefe de hoga
     * @param idPersonaIntegrante
     *        Identificador de la persona asociada al integrante del hogar
     */
    public InhabilidadSubsidioFovisModeloDTO(InhabilidadSubsidioFovis inhabilidadSubsidioFovis, Long idPersona) {
        convertToDTO(inhabilidadSubsidioFovis);
        this.setIdPersona(idPersona);
    }

    /**
     * Asocia los datos del DTO a la Entidad
     * 
     * @return InhabilidadSubsidioFovis
     */
    public InhabilidadSubsidioFovis convertToEntity() {

        InhabilidadSubsidioFovis inhabilidadSubsidioFovis = new InhabilidadSubsidioFovis();
        inhabilidadSubsidioFovis.setIdInhabilidadSubsidioFovis(this.getIdInhabilidadSubsidioFovis());
        inhabilidadSubsidioFovis.setInhabilitadoParaSubsidio(this.getInhabilitadoParaSubsidio());
        if (this.getIntegranteHogar() != null && this.getIntegranteHogar().getIdIntegranteHogar() != null) {
            inhabilidadSubsidioFovis.setIdIntegranteHogar(this.getIntegranteHogar().getIdIntegranteHogar());
        }

        if (this.getJefeHogar() != null && this.getJefeHogar().getIdJefeHogar() != null) {
            inhabilidadSubsidioFovis.setIdJefeHogar(this.getJefeHogar().getIdJefeHogar());
        }

        inhabilidadSubsidioFovis.setFechaFin(this.getFechaFinInhabilidad() != null ? new Date(this.getFechaFinInhabilidad()) : null);

        inhabilidadSubsidioFovis
                .setFechaInicio(this.getFechaInicioInhabilidad() != null ? new Date(this.getFechaInicioInhabilidad()) : null);

        return inhabilidadSubsidioFovis;
    }

    /**
     * Asocia los datos de la Entidad al DTO
     * 
     * @param ResultadosAsignacionFOVIS
     */
    public void convertToDTO(InhabilidadSubsidioFovis inhabilidadSubsidioFovis) {
        this.setFechaInicioInhabilidad(
                inhabilidadSubsidioFovis.getFechaInicio() != null ? inhabilidadSubsidioFovis.getFechaInicio().getTime() : null);

        this.setFechaFinInhabilidad(
                inhabilidadSubsidioFovis.getFechaFin() != null ? inhabilidadSubsidioFovis.getFechaFin().getTime() : null);

        this.setIdInhabilidadSubsidioFovis(inhabilidadSubsidioFovis.getIdInhabilidadSubsidioFovis());
        this.setInhabilitadoParaSubsidio(inhabilidadSubsidioFovis.getInhabilitadoParaSubsidio());
        if (inhabilidadSubsidioFovis.getIdJefeHogar() != null) {
            JefeHogarModeloDTO jefe = new JefeHogarModeloDTO();
            jefe.setIdJefeHogar(inhabilidadSubsidioFovis.getIdJefeHogar());
            this.setJefeHogar(jefe);
        }

        if (inhabilidadSubsidioFovis.getIdIntegranteHogar() != null) {
            IntegranteHogarModeloDTO integrante = new IntegranteHogarModeloDTO();
            integrante.setIdIntegranteHogar(inhabilidadSubsidioFovis.getIdIntegranteHogar());
            this.setIntegranteHogar(integrante);
        }
    }

    /**
     * @return the idInhabilidadSubsidioFovis
     */
    public Long getIdInhabilidadSubsidioFovis() {
        return idInhabilidadSubsidioFovis;
    }

    /**
     * @param idInhabilidadSubsidioFovis
     *        the idInhabilidadSubsidioFovis to set
     */
    public void setIdInhabilidadSubsidioFovis(Long idInhabilidadSubsidioFovis) {
        this.idInhabilidadSubsidioFovis = idInhabilidadSubsidioFovis;
    }

    /**
     * @return the fechaInicioInhabilidad
     */
    public Long getFechaInicioInhabilidad() {
        return fechaInicioInhabilidad;
    }

    /**
     * @param fechaInicioInhabilidad
     *        the fechaInicioInhabilidad to set
     */
    public void setFechaInicioInhabilidad(Long fechaInicioInhabilidad) {
        this.fechaInicioInhabilidad = fechaInicioInhabilidad;
    }

    /**
     * @return the fechaFinInhabilidad
     */
    public Long getFechaFinInhabilidad() {
        return fechaFinInhabilidad;
    }

    /**
     * @param fechaFinInhabilidad
     *        the fechaFinInhabilidad to set
     */
    public void setFechaFinInhabilidad(Long fechaFinInhabilidad) {
        this.fechaFinInhabilidad = fechaFinInhabilidad;
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

    /**
     * @return the jefeHogar
     */
    public JefeHogarModeloDTO getJefeHogar() {
        return jefeHogar;
    }

    /**
     * @param jefeHogar
     *        the jefeHogar to set
     */
    public void setJefeHogar(JefeHogarModeloDTO jefeHogar) {
        this.jefeHogar = jefeHogar;
    }

    /**
     * @return the integranteHogar
     */
    public IntegranteHogarModeloDTO getIntegranteHogar() {
        return integranteHogar;
    }

    /**
     * @param integranteHogar
     *        the integranteHogar to set
     */
    public void setIntegranteHogar(IntegranteHogarModeloDTO integranteHogar) {
        this.integranteHogar = integranteHogar;
    }

    /**
     * @return the idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * @param idPersona
     *        the idPersona to set
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

}
