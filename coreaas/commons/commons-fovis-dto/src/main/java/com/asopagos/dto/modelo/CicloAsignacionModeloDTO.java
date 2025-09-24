package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.asopagos.entidades.fovis.parametricas.CicloAsignacion;
import com.asopagos.enumeraciones.fovis.EstadoCicloAsignacionEnum;

/**
 * 
 * <b>Descripcion:</b> Clase que se encarga de almacenar los datos de el Modelo de la entidad CicloAsignacion<br/>
 * <b>Módulo:</b> Asopagos - HU-312-022 <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
public class CicloAsignacionModeloDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4654232936310571288L;
    /**
     * Identificador del ciclo de asignación
     */
    private Long idCicloAsignacion;
    /**
     * Nombre del ciclo de asignación.
     */
    private String nombre;
    /**
     * Fecha Inicio del ciclo de asignación.
     */
    private Long fechaInicio;
    /**
     * Fecha fin del ciclo de asignación.
     */
    private Long fechaFin;
    /**
     * Identificador del ciclo de asignación predecesor.
     */
    private Long cicloPredecesor;
    /**
     * Estado del Ciclo Asignación
     */
    private EstadoCicloAsignacionEnum estadoCicloAsignacion;
    /**
     * Indica si el ciclo fue o no eliminado (inactivado).
     */
    private Boolean cicloActivo;
    /**
     * Indica si se permite o no la eliminación (inactivación) en pantalla del cliclo de asignación
     */
    private Boolean permiteEliminar;
    /**
     * Lista de las modalidades habilitadas para el ciclo de asignación
     */
    private List<CicloModalidadModeloDTO> modalidadesCiclo;
    /**
     * Identificador del ciclo de asignación sucesor (no es propiedad de la entidad).
     */
    private Long idCicloSucesor;
    /**
     * Nombre del ciclo de asignación sucesor (no es propiedad de la entidad).
     */
    private String cicloSucesor;

    /**
     * Valor disponible de ciclo de asignación
     */
    private BigDecimal valorDisponible;
    
    /**
     * Fecha de asignacion planificada
     */
    private Long fechaAsignacionPlanificada;

    /**
     * Id del resultado de la ejecucion programada
     */
    private Long ejecucionProgramada;
    /**
     * Id del resultado de la ejecucion programada
     */
    private String estadoCalificacion;


    /**
     * Constructor de ParametrizacionFOVISDTO.
     */
    public CicloAsignacionModeloDTO() {
    }

    /**
     * Constructor que recibe el Entity para mapear las propiedades de la clase.
     */
    public CicloAsignacionModeloDTO(CicloAsignacion cicloAsignacion) {
        copyEntityToDTO(cicloAsignacion);
    }

    /**
     * Convierte el actual DTO en el entity equivalente.
     * @return Entity CicloAsignacion
     */
    public CicloAsignacion convertToEntity() {
        CicloAsignacion cicloAsignacion = new CicloAsignacion();
        cicloAsignacion.setIdCicloAsignacion(this.getIdCicloAsignacion());
        cicloAsignacion.setNombre(this.getNombre());
        cicloAsignacion.setFechaInicio(this.getFechaInicio() != null ? new Date(this.getFechaInicio()) : null);
        cicloAsignacion.setFechaFin(this.getFechaFin() != null ? new Date(this.getFechaFin()) : null);
        cicloAsignacion.setCicloPredecesor(this.getCicloPredecesor());
        cicloAsignacion.setEstadoCicloAsignacion(this.getEstadoCicloAsignacion());
        cicloAsignacion.setCicloActivo(this.getCicloActivo());
        cicloAsignacion.setValorDisponible(this.getValorDisponible());
        cicloAsignacion.setFechaAsignacionPlanificada(this.getFechaAsignacionPlanificada() != null ? new Date(this.getFechaAsignacionPlanificada()) : null);
        cicloAsignacion.setEjecucionProgramada(this.getEjecucionProgramada());
        cicloAsignacion.setEstadoCalificacion(this.getEstadoCalificacion());
        return cicloAsignacion;
    }

    /**
     * Copia las propiedades del DTO actual al entity que llega por parámetro.
     * @param cicloAsignacion
     * @return El CicloAsignación con las propiedades modificadas.
     */
    public CicloAsignacion copyDTOToEntity(CicloAsignacion cicloAsignacion) {
        if (this.getIdCicloAsignacion() != null) {
            cicloAsignacion.setIdCicloAsignacion(this.getIdCicloAsignacion());
        }
        if (this.getNombre() != null) {
            cicloAsignacion.setNombre(this.getNombre());
        }
        if (this.getFechaInicio() != null) {
            cicloAsignacion.setFechaInicio(new Date(this.getFechaInicio()));
        }
        if (this.getFechaFin() != null) {
            cicloAsignacion.setFechaFin(new Date(this.getFechaFin()));
        }
        if (this.getCicloPredecesor() != null) {
            cicloAsignacion.setCicloPredecesor(this.getCicloPredecesor());
        }
        if (this.getEstadoCicloAsignacion() != null) {
            cicloAsignacion.setEstadoCicloAsignacion(this.getEstadoCicloAsignacion());
        }
        if (this.getCicloActivo() != null) {
            cicloAsignacion.setCicloActivo(this.getCicloActivo());
        }

        if (this.getValorDisponible() != null) {
            cicloAsignacion.setValorDisponible(this.getValorDisponible());
        }
        if (this.getFechaAsignacionPlanificada() != null) {
            cicloAsignacion.setFechaAsignacionPlanificada((new Date(this.getFechaAsignacionPlanificada())));
        }
        return cicloAsignacion;
    }

    /**
     * Copia las propiedades del entity que llega por parámetro al actual DTO.
     * @param cicloAsignacion
     */
    public void copyEntityToDTO(CicloAsignacion cicloAsignacion) {
        if (cicloAsignacion.getIdCicloAsignacion() != null) {
            this.setIdCicloAsignacion(cicloAsignacion.getIdCicloAsignacion());
        }
        if (cicloAsignacion.getNombre() != null) {
            this.setNombre(cicloAsignacion.getNombre());
        }
        if (cicloAsignacion.getFechaInicio() != null) {
            this.setFechaInicio(cicloAsignacion.getFechaInicio().getTime());
        }
        if (cicloAsignacion.getFechaFin() != null) {
            this.setFechaFin(cicloAsignacion.getFechaFin().getTime());
        }
        if (cicloAsignacion.getCicloPredecesor() != null) {
            this.setCicloPredecesor(cicloAsignacion.getCicloPredecesor());
        }
        if (cicloAsignacion.getEstadoCicloAsignacion() != null) {
            this.setEstadoCicloAsignacion(cicloAsignacion.getEstadoCicloAsignacion());
        }
        if (cicloAsignacion.getCicloActivo() != null) {
            this.setCicloActivo(cicloAsignacion.getCicloActivo());
        }

        if (cicloAsignacion.getValorDisponible() != null) {
            this.setValorDisponible(cicloAsignacion.getValorDisponible());
        }
        
        if (cicloAsignacion.getFechaAsignacionPlanificada() != null) {
            this.setFechaAsignacionPlanificada(cicloAsignacion.getFechaAsignacionPlanificada().getTime());
        }

        if (cicloAsignacion.getEjecucionProgramada() != null) {
            this.setEjecucionProgramada(cicloAsignacion.getEjecucionProgramada());
        }

        if (cicloAsignacion.getEstadoCalificacion() != null) {
            this.setEstadoCalificacion(cicloAsignacion.getEstadoCalificacion());
        }
    }

    /**
     * @return the idCicloAsignacion
     */
    public Long getIdCicloAsignacion() {
        return idCicloAsignacion;
    }

    /**
     * @param idCicloAsignacion
     *        the idCicloAsignacion to set
     */
    public void setIdCicloAsignacion(Long idCicloAsignacion) {
        this.idCicloAsignacion = idCicloAsignacion;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     *        the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the fechaInicio
     */
    public Long getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio
     *        the fechaInicio to set
     */
    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Long getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin
     *        the fechaFin to set
     */
    public void setFechaFin(Long fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the cicloPredecesor
     */
    public Long getCicloPredecesor() {
        return cicloPredecesor;
    }

    /**
     * @param cicloPredecesor
     *        the cicloPredecesor to set
     */
    public void setCicloPredecesor(Long cicloPredecesor) {
        this.cicloPredecesor = cicloPredecesor;
    }

    /**
     * @return the estadoCicloAsignacion
     */
    public EstadoCicloAsignacionEnum getEstadoCicloAsignacion() {
        return estadoCicloAsignacion;
    }

    /**
     * @param estadoCicloAsignacion
     *        the estadoCicloAsignacion to set
     */
    public void setEstadoCicloAsignacion(EstadoCicloAsignacionEnum estadoCicloAsignacion) {
        this.estadoCicloAsignacion = estadoCicloAsignacion;
    }

    /**
     * @return the cicloActivo
     */
    public Boolean getCicloActivo() {
        return cicloActivo;
    }

    /**
     * @param cicloActivo
     *        the cicloActivo to set
     */
    public void setCicloActivo(Boolean cicloActivo) {
        this.cicloActivo = cicloActivo;
    }

    /**
     * @return the permiteEliminar
     */
    public Boolean getPermiteEliminar() {
        return permiteEliminar;
    }

    /**
     * @param permiteEliminar
     *        the permiteEliminar to set
     */
    public void setPermiteEliminar(Boolean permiteEliminar) {
        this.permiteEliminar = permiteEliminar;
    }

    /**
     * @return the modalidadesCiclo
     */
    public List<CicloModalidadModeloDTO> getModalidadesCiclo() {
        return modalidadesCiclo;
    }

    /**
     * @param modalidadesCiclo
     *        the modalidadesCiclo to set
     */
    public void setModalidadesCiclo(List<CicloModalidadModeloDTO> modalidadesCiclo) {
        this.modalidadesCiclo = modalidadesCiclo;
    }

    /**
     * @return the idCicloSucesor
     */
    public Long getIdCicloSucesor() {
        return idCicloSucesor;
    }

    /**
     * @param idCicloSucesor
     *        the idCicloSucesor to set
     */
    public void setIdCicloSucesor(Long idCicloSucesor) {
        this.idCicloSucesor = idCicloSucesor;
    }

    /**
     * @return the cicloSucesor
     */
    public String getCicloSucesor() {
        return cicloSucesor;
    }

    /**
     * @param cicloSucesor
     *        the cicloSucesor to set
     */
    public void setCicloSucesor(String cicloSucesor) {
        this.cicloSucesor = cicloSucesor;
    }

    /**
     * @return the valorDisponible
     */
    public BigDecimal getValorDisponible() {
        return valorDisponible;
    }

    /**
     * @param valorDisponible
     *        the valorDisponible to set
     */
    public void setValorDisponible(BigDecimal valorDisponible) {
        this.valorDisponible = valorDisponible;
    }

    /**
     * @return the fechaAsignacionPlanificada
     */
    public Long getFechaAsignacionPlanificada() {
        return fechaAsignacionPlanificada;
    }

    /**
     * @param fechaAsignacionPlanificada the fechaAsignacionPlanificada to set
     */
    public void setFechaAsignacionPlanificada(Long fechaAsignacionPlanificada) {
        this.fechaAsignacionPlanificada = fechaAsignacionPlanificada;
    }

    public Long getEjecucionProgramada() {
        return ejecucionProgramada;
    }

    public void setEjecucionProgramada(Long ejecucionProgramada) {
        this.ejecucionProgramada = ejecucionProgramada;
    }

    public String getEstadoCalificacion() {
        return estadoCalificacion;
    }

    public void setEstadoCalificacion(String estadoCalificacion) {
        this.estadoCalificacion = estadoCalificacion;
    }
}
