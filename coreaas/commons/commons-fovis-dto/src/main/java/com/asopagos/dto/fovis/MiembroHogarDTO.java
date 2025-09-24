package com.asopagos.dto.fovis;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.dto.modelo.IntegranteHogarModeloDTO;
import com.asopagos.dto.modelo.JefeHogarModeloDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.fovis.EstadoFOVISHogarEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase que contiene la información básica de un miembro de hogar fovis<br/>
 * <b>Módulo:</b> Asopagos - Proceso 3.2.5 novedades fovis <br/>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa Salamanca</a>
 */

public class MiembroHogarDTO implements Serializable {

    /**
     * SERIAL
     */
    private static final long serialVersionUID = 1259608344519568685L;

    /**
     * Estado del miembro respecto al hogar
     */
    private EstadoFOVISHogarEnum estadoHogar;

    /**
     * Estado del miembro respecto al hogar en la asignacion
     */
    private EstadoFOVISHogarEnum estadoHogarAsignacion;
    /**
     * Identificador del miembro de hogar como integrante
     */
    private Long idIntegranteHogar;

    /**
     * Identificador del miembro de hogar como jefe
     */
    private Long idJefeHogar;

    /**
     * Ingresos mensuales del integrante actualmente
     */
    private BigDecimal ingresosMensuales;

    /**
     * Ingresos mensuales del integrante en la asignacion
     */
    private BigDecimal ingresosMensualesAsignacion;

    /**
     * Nombres y apellidos de la persona miembro
     */
    private String nombresApellidos;

    /**
     * Número de identificación de la persona miembro
     */
    private String numeroIdentificacion;

    /**
     * Parentesco del Miemgro de Hogar
     */
    private ClasificacionEnum parentesco;

    /**
     * Tipo de identificación de la persona miembro
     */
    private TipoIdentificacionEnum tipoIdentificacion;

    /**
     * Constructor por defecto
     */
    public MiembroHogarDTO() {
        super();
    }

    /**
     * Constructor usado para el manejo de consultas
     * @param estadoHogar
     *        Cadena para el estado de hogar
     * @param idIntegranteHogar
     *        Identificador del integrante
     * @param idJefeHogar
     *        Identificador del jefe
     * @param ingresosMensuales
     *        Ingresos mensuales actuales
     * @param nombresApellidos
     *        Nombres del miembro
     * @param numeroIdentificacion
     *        Numero identificacion del miembro
     * @param parentesco
     *        Cadena con el parentesco del miebro
     * @param tipoIdentificacion
     *        Cadena con el tipo de identificacion del miembro
     */
    public MiembroHogarDTO(String estadoHogar, Long idIntegranteHogar, Long idJefeHogar, BigDecimal ingresosMensuales,
            String nombresApellidos, String numeroIdentificacion, String parentesco, String tipoIdentificacion) {
        super();
        if (estadoHogar != null) {
            this.estadoHogar = EstadoFOVISHogarEnum.valueOf(estadoHogar);
        }
        this.idIntegranteHogar = idIntegranteHogar;
        this.idJefeHogar = idJefeHogar;
        this.ingresosMensuales = ingresosMensuales;
        this.nombresApellidos = nombresApellidos;
        this.numeroIdentificacion = numeroIdentificacion;
        if (parentesco != null) {
            this.parentesco = ClasificacionEnum.valueOf(parentesco);
        }
        if (tipoIdentificacion != null) {
            this.tipoIdentificacion = TipoIdentificacionEnum.valueOf(tipoIdentificacion);
        }
    }

    /**
     * Convierte la información del DTO a la del DTO de JefeHogar
     * @return DTO modelo de JefeHogar
     */
    public JefeHogarModeloDTO convertToDTOJefe() {
        JefeHogarModeloDTO jefeHogar = new JefeHogarModeloDTO();
        jefeHogar.setIdJefeHogar(this.getIdJefeHogar());
        jefeHogar.setEstadoHogar(this.getEstadoHogar());
        jefeHogar.setIngresosMensuales(this.getIngresosMensuales());
        return jefeHogar;
    }

    /**
     * Convierte la información del DTO a la del DTO de IntegranteHogar
     * @return DTO modelo de IntegranteHogar
     */
    public IntegranteHogarModeloDTO convertToDTOIntegrante() {
        IntegranteHogarModeloDTO integranteHogar = new IntegranteHogarModeloDTO();
        integranteHogar.setIdIntegranteHogar(this.getIdIntegranteHogar());
        integranteHogar.setIdJefeHogar(this.getIdJefeHogar());
        integranteHogar.setTipoIntegranteHogar(this.getParentesco());
        integranteHogar.setEstadoHogar(this.getEstadoHogar());
        integranteHogar.setIngresosMensuales(this.getIngresosMensuales());
        return integranteHogar;
    }

    /**
     * @return the estadoHogar
     */
    public EstadoFOVISHogarEnum getEstadoHogar() {
        return estadoHogar;
    }

    /**
     * @param estadoHogar
     *        the estadoHogar to set
     */
    public void setEstadoHogar(EstadoFOVISHogarEnum estadoHogar) {
        this.estadoHogar = estadoHogar;
    }

    /**
     * @return the estadoHogarAsignacion
     */
    public EstadoFOVISHogarEnum getEstadoHogarAsignacion() {
        return estadoHogarAsignacion;
    }

    /**
     * @param estadoHogarAsignacion
     *        the estadoHogarAsignacion to set
     */
    public void setEstadoHogarAsignacion(EstadoFOVISHogarEnum estadoHogarAsignacion) {
        this.estadoHogarAsignacion = estadoHogarAsignacion;
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
     * @return the ingresosMensualesAsignacion
     */
    public BigDecimal getIngresosMensualesAsignacion() {
        return ingresosMensualesAsignacion;
    }

    /**
     * @param ingresosMensualesAsignacion
     *        the ingresosMensualesAsignacion to set
     */
    public void setIngresosMensualesAsignacion(BigDecimal ingresosMensualesAsignacion) {
        this.ingresosMensualesAsignacion = ingresosMensualesAsignacion;
    }

    /**
     * @return the nombresApellidos
     */
    public String getNombresApellidos() {
        return nombresApellidos;
    }

    /**
     * @param nombresApellidos
     *        the nombresApellidos to set
     */
    public void setNombresApellidos(String nombresApellidos) {
        this.nombresApellidos = nombresApellidos;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion
     *        the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the parentesco
     */
    public ClasificacionEnum getParentesco() {
        return parentesco;
    }

    /**
     * @param parentesco
     *        the parentesco to set
     */
    public void setParentesco(ClasificacionEnum parentesco) {
        this.parentesco = parentesco;
    }

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion
     *        the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

}
