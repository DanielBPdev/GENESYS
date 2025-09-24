package com.asopagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;

/**
 * <b>Descripcion:</b> DTO que contiene los datos de la afiliación del jefe de hogar fovis<br/>
 * <b>Módulo:</b> Asopagos - Fovis<br/>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa Salamanca</a>
 */
@XmlRootElement
public class AfiliacionJefeHogarDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = -434147426088567191L;

    /**
     * Estado afiliación respecto a la CCF
     */
    private EstadoAfiliadoEnum estadoAfiliacion;

    /**
     * Descripción del tipo de afiliación
     */
    private TipoAfiliadoEnum tipoAfiliado;

    /**
     * Descripción de la clasificación de afiliación
     */
    private ClasificacionEnum clasificacionAfiliado;

    /**
     * Porcentaje del pago de los aportes
     */
    private BigDecimal porcentajePagoAportes;

    /**
     * Constructor por defecto
     */
    public AfiliacionJefeHogarDTO() {
        super();
    }

    /**
     * Constructor usado en JPQL para la representación de consultas
     * @param estadoAfiliacion
     *        Estado de la persona respecto a la caja
     * @param tipoAfiliado
     *        Tipo de afiliación
     * @param clasificacionAfiliado
     *        Clasificación de la persona
     * @param porcentajePagoAportes
     *        Porcentaje para el pago de aportes
     */
    public AfiliacionJefeHogarDTO(String estadoAfiliacion, String tipoAfiliado,
            String clasificacionAfiliado, BigDecimal porcentajePagoAportes) {
        super();
        if (estadoAfiliacion != null) {
            this.estadoAfiliacion = EstadoAfiliadoEnum.valueOf(estadoAfiliacion);
        }
        if (tipoAfiliado != null) {
            this.tipoAfiliado = TipoAfiliadoEnum.valueOf(tipoAfiliado);
        }
        if (clasificacionAfiliado != null) {
            this.clasificacionAfiliado = ClasificacionEnum.valueOf(clasificacionAfiliado);
        }
        this.porcentajePagoAportes = porcentajePagoAportes;
    }

    /**
     * @return the estadoAfiliacion
     */
    public EstadoAfiliadoEnum getEstadoAfiliacion() {
        return estadoAfiliacion;
    }

    /**
     * @param estadoAfiliacion
     *        the estadoAfiliacion to set
     */
    public void setEstadoAfiliacion(EstadoAfiliadoEnum estadoAfiliacion) {
        this.estadoAfiliacion = estadoAfiliacion;
    }

    /**
     * @return the tipoAfiliado
     */
    public TipoAfiliadoEnum getTipoAfiliado() {
        return tipoAfiliado;
    }

    /**
     * @param tipoAfiliado
     *        the tipoAfiliado to set
     */
    public void setTipoAfiliado(TipoAfiliadoEnum tipoAfiliado) {
        this.tipoAfiliado = tipoAfiliado;
    }

    /**
     * @return the clasificacionAfiliado
     */
    public ClasificacionEnum getClasificacionAfiliado() {
        return clasificacionAfiliado;
    }

    /**
     * @param clasificacionAfiliado
     *        the clasificacionAfiliado to set
     */
    public void setClasificacionAfiliado(ClasificacionEnum clasificacionAfiliado) {
        this.clasificacionAfiliado = clasificacionAfiliado;
    }

    /**
     * @return the porcentajePagoAportes
     */
    public BigDecimal getPorcentajePagoAportes() {
        return porcentajePagoAportes;
    }

    /**
     * @param porcentajePagoAportes
     *        the porcentajePagoAportes to set
     */
    public void setPorcentajePagoAportes(BigDecimal porcentajePagoAportes) {
        this.porcentajePagoAportes = porcentajePagoAportes;
    }

}
