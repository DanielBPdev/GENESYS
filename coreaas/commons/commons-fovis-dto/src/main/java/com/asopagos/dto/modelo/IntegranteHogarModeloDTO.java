package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import com.asopagos.dto.ListaChequeoDTO;
import com.asopagos.entidades.ccf.fovis.IntegranteHogar;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.fovis.EstadoFOVISHogarEnum;
import com.asopagos.enumeraciones.fovis.NombreCondicionEspecialEnum;

/**
 * DTO que representa los datos de la entidad MiembroHogar
 * 
 * @author Fabian López <flopez@heinsohn.com.co>
 *
 */
public class IntegranteHogarModeloDTO extends PersonaModeloDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 4293410707886082946L;

    /**
     * Identificador único, llave primaria
     */
    private Long idIntegranteHogar;

    /**
     * Asociación del Afiliado al Jefe de Hogar
     */
    private Long idJefeHogar;

    /**
     * Parentesco del Integrante Hogar
     */
    private ClasificacionEnum tipoIntegranteHogar;

    /**
     * Valida si este Integrante Hogar puede reemplazar al Jefe de Hogar
     */
    private Boolean integranteReemplazaJefeHogar;

    /**
     * Lista de chequeo de requisitos documentales
     */
    private ListaChequeoDTO listaChequeo;

    /**
     * Estado del integrante respecto al hogar
     */
    private EstadoFOVISHogarEnum estadoHogar;

    /**
     * Ingresos Mensuales del Jefe de Hogar
     */
    private BigDecimal ingresosMensuales;

    /**
     * Indica si el Integrante de Hogar pasó las validaciones de la postulación.
     */
    private Boolean integranteValido;

    /**
     * Lista de condiciones especiales asociada al Integrante del Hogar
     */
    private List<NombreCondicionEspecialEnum> condicionesEspeciales;

    /**
     * Indica si el integrante de hogar esta inhabilitado para el subsidio
     */
    private Boolean inhabilitadoParaSubsidio;

    /**
     * Indica la postulación FOVIS con la que el integrante de hogar tien asociación 
     */
    private Long idPostulacion;

    /**
     * Constructor por defecto
     */
    public IntegranteHogarModeloDTO() {
        super();
    }

    /**
     * Constructor de integrante con persona detalle
     * @param persona
     *        Entidad Persona
     * @param personaDetalle
     *        Entidad Persona Detalla
     * @param integranteHogar
     *        Entidad IntegranteHogar
     */
    public IntegranteHogarModeloDTO(Persona persona, PersonaDetalle personaDetalle, IntegranteHogar integranteHogar) {
        super(persona, personaDetalle);
        convertToDTO(integranteHogar);
    }

    /**
     * Constructor de integrante con persona básica
     * @param persona
     *        Entidad Persona
     * @param integranteHogar
     *        Entidad IntegranteHogar
     */
    public IntegranteHogarModeloDTO(Persona persona, IntegranteHogar integranteHogar) {
        super(persona, null);
        convertToDTO(integranteHogar);
    }

    /**
     * Constructor de integrante
     * @param integranteHogar
     *        Entidad integrante
     */
    public IntegranteHogarModeloDTO(IntegranteHogar integranteHogar) {
        super();
        convertToDTO(integranteHogar);
    }

    /**
     * Asocia los datos del DTO a la Entidad
     * 
     * @return MiembroHogar
     */
    public IntegranteHogar convertToEntity() {
        IntegranteHogar integranteHogar = new IntegranteHogar();
        integranteHogar.setIdIntegranteHogar(this.getIdIntegranteHogar());
        integranteHogar.setIdJefeHogar(this.getIdJefeHogar());
        integranteHogar.setTipoIntegranteHogar(this.getTipoIntegranteHogar());
        integranteHogar.setIntegranteReemplazaJefeHogar(this.getIntegranteReemplazaJefeHogar());
        integranteHogar.setIdPersona(this.getIdPersona());
        integranteHogar.setEstadoHogar(this.getEstadoHogar());
        integranteHogar.setIntegranteValido(this.getIntegranteValido());
        integranteHogar.setSalarioMensual(this.getIngresosMensuales());
        integranteHogar.setIdPostulacion(this.getIdPostulacion());
        return integranteHogar;
    }

    /**
     * Asocia los datos de la Entidad al DTO
     * 
     * @param miembroHogar
     */
    public void convertToDTO(IntegranteHogar integranteHogar) {
        this.setIdIntegranteHogar(integranteHogar.getIdIntegranteHogar());
        this.setIdPersona(integranteHogar.getIdPersona());
        this.setIdJefeHogar(integranteHogar.getIdJefeHogar());
        this.setTipoIntegranteHogar(integranteHogar.getTipoIntegranteHogar());
        this.setIntegranteReemplazaJefeHogar(integranteHogar.getIntegranteReemplazaJefeHogar());
        this.setEstadoHogar(integranteHogar.getEstadoHogar());
        this.setIntegranteValido(integranteHogar.getIntegranteValido());
        this.setIngresosMensuales(integranteHogar.getSalarioMensual());
        this.setIdPostulacion(integranteHogar.getIdPostulacion());
    }

    /**
     * Copia los datos del DTO a la Entidad.
     * 
     * @param MiembroHogar
     *        Entidad previamente consultada
     */
    public void copyDTOToEntity(IntegranteHogar integranteHogar) {
        if (this.getIdIntegranteHogar() != null) {
            integranteHogar.setIdIntegranteHogar(this.getIdIntegranteHogar());
        }
        if (this.getIdPersona() != null) {
            integranteHogar.setIdPersona(this.getIdPersona());
        }
        if (this.getIdJefeHogar() != null) {
            integranteHogar.setIdJefeHogar(this.getIdJefeHogar());
        }
        if (this.getTipoIntegranteHogar() != null) {
            integranteHogar.setTipoIntegranteHogar(this.getTipoIntegranteHogar());
        }
        if (this.getIntegranteReemplazaJefeHogar() != null) {
            integranteHogar.setIntegranteReemplazaJefeHogar(this.getIntegranteReemplazaJefeHogar());
        }
        if (this.getIntegranteValido() != null) {
            integranteHogar.setIntegranteValido(this.getIntegranteValido());
        }
        if (this.getIngresosMensuales() != null) {
            integranteHogar.setSalarioMensual(this.getIngresosMensuales());
        }
        if (this.getIdPostulacion() != null) {
            integranteHogar.setIdPostulacion(this.getIdPostulacion());
        }
        if (this.getEstadoHogar() != null) {
            integranteHogar.setEstadoHogar(this.getEstadoHogar());
        }
    }

    /**
     * @return the idIntegranteHogar
     */
    public Long getIdIntegranteHogar() {
        return idIntegranteHogar;
    }

    /**
     * @param idIntegranteHogar
     *        the idIntegranteHogar to set
     */
    public void setIdIntegranteHogar(Long idIntegranteHogar) {
        this.idIntegranteHogar = idIntegranteHogar;
    }

    /**
     * @return the idJefeHogar
     */
    public Long getIdJefeHogar() {
        return idJefeHogar;
    }

    /**
     * @param idJefeHogar
     *        the idJefeHogar to set
     */
    public void setIdJefeHogar(Long idJefeHogar) {
        this.idJefeHogar = idJefeHogar;
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
     * @return the integranteReemplazaJefeHogar
     */
    public Boolean getIntegranteReemplazaJefeHogar() {
        return integranteReemplazaJefeHogar;
    }

    /**
     * @param integranteReemplazaJefeHogar
     *        the integranteReemplazaJefeHogar to set
     */
    public void setIntegranteReemplazaJefeHogar(Boolean integranteReemplazaJefeHogar) {
        this.integranteReemplazaJefeHogar = integranteReemplazaJefeHogar;
    }

    /**
     * Obtiene el valor de listaChequeo
     * 
     * @return El valor de listaChequeo
     */
    public ListaChequeoDTO getListaChequeo() {
        return listaChequeo;
    }

    /**
     * Establece el valor de listaChequeo
     * 
     * @param listaChequeo
     *        El valor de listaChequeo por asignar
     */
    public void setListaChequeo(ListaChequeoDTO listaChequeo) {
        this.listaChequeo = listaChequeo;
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
     * @return the integranteValido
     */
    public Boolean getIntegranteValido() {
        return integranteValido;
    }

    /**
     * @param integranteValido
     *        the integranteValido to set
     */
    public void setIntegranteValido(Boolean integranteValido) {
        this.integranteValido = integranteValido;
    }

    /**
     * Obtiene el valor de condicionesEspeciales
     * 
     * @return El valor de condicionesEspeciales
     */
    public List<NombreCondicionEspecialEnum> getCondicionesEspeciales() {
        return condicionesEspeciales;
    }

    /**
     * Establece el valor de condicionesEspeciales
     * 
     * @param condicionesEspeciales
     *        El valor de condicionesEspeciales por asignar
     */
    public void setCondicionesEspeciales(List<NombreCondicionEspecialEnum> condicionesEspeciales) {
        this.condicionesEspeciales = condicionesEspeciales;
    }

    /**
     * @return the inhabilitadoParaSubsidio
     */
    public Boolean getInhabilitadoParaSubsidio() {
        return inhabilitadoParaSubsidio;
    }

    /**
     * @param inhabilitadoParaSubsidio the inhabilitadoParaSubsidio to set
     */
    public void setInhabilitadoParaSubsidio(Boolean inhabilitadoParaSubsidio) {
        this.inhabilitadoParaSubsidio = inhabilitadoParaSubsidio;
    }

    /**
     * @return the idPostulacion
     */
    public Long getIdPostulacion() {
        return idPostulacion;
    }

    /**
     * @param idPostulacion the idPostulacion to set
     */
    public void setIdPostulacion(Long idPostulacion) {
        this.idPostulacion = idPostulacion;
    }
}