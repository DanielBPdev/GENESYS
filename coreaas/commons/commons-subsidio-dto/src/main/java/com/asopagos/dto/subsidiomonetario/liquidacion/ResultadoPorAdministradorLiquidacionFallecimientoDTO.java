package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;

/**
 * <b>Descripcion:</b> DTO que contiene el resultado del la liquidación de fallecimiento por administrador de subsidio <br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-317-510<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ResultadoPorAdministradorLiquidacionFallecimientoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador de condición del administrador de subsidio
     */
    private Long identificadorCondicion;

    /**
     * Tipo de identificación del administrador de subsidio
     */
    private TipoIdentificacionEnum tipoIdentificacionAdministrador;

    /**
     * Número de identificación del administrador de subsidio
     */
    private String numeroIdentificacionAdministrador;

    /**
     * Nombre del administrador de subsidio
     */
    private String nombreAdministrador;

    /**
     * Medio de pago asociado al administrador de subsidio (Cadena, ya que el medio de pago puede ser descuentos)
     */
    private String medioDePagoAdministrador;

    /**
     * Lista de items con la información detallada de los beneficiarios para el administrador de subsidio
     */
    private List<ItemResultadoLiquidacionFallecimientoDTO> itemsBeneficiarios;

    /**
     * HU 317-508 Indicador del resultado de la dispersión para el administrador de subsidio
     */
    private String resultadoDispersion;

    /**
     * HU 317-508 Total dispersado para el administrador de subsidio
     */
    private BigDecimal totalDispersado;

    /**
     * HU 317-508 Total programado para el administrador de subsidio
     */
    private BigDecimal totalProgramado;

    /**
     * @return the identificadorCondicion
     */
    public Long getIdentificadorCondicion() {
        return identificadorCondicion;
    }

    /**
     * @param identificadorCondicion
     *        the identificadorCondicion to set
     */
    public void setIdentificadorCondicion(Long identificadorCondicion) {
        this.identificadorCondicion = identificadorCondicion;
    }

    /**
     * @return the tipoIdentificacionAdministrador
     */
    public TipoIdentificacionEnum getTipoIdentificacionAdministrador() {
        return tipoIdentificacionAdministrador;
    }

    /**
     * @param tipoIdentificacionAdministrador
     *        the tipoIdentificacionAdministrador to set
     */
    public void setTipoIdentificacionAdministrador(TipoIdentificacionEnum tipoIdentificacionAdministrador) {
        this.tipoIdentificacionAdministrador = tipoIdentificacionAdministrador;
    }

    /**
     * @return the numeroIdentificacionAdministrador
     */
    public String getNumeroIdentificacionAdministrador() {
        return numeroIdentificacionAdministrador;
    }

    /**
     * @param numeroIdentificacionAdministrador
     *        the numeroIdentificacionAdministrador to set
     */
    public void setNumeroIdentificacionAdministrador(String numeroIdentificacionAdministrador) {
        this.numeroIdentificacionAdministrador = numeroIdentificacionAdministrador;
    }

    /**
     * @return the nombreAdministrador
     */
    public String getNombreAdministrador() {
        return nombreAdministrador;
    }

    /**
     * @param nombreAdministrador
     *        the nombreAdministrador to set
     */
    public void setNombreAdministrador(String nombreAdministrador) {
        this.nombreAdministrador = nombreAdministrador;
    }

    /**
     * @return the medioDePagoAdministrador
     */
    public String getMedioDePagoAdministrador() {
        return medioDePagoAdministrador;
    }

    /**
     * @param medioDePagoAdministrador
     *        the medioDePagoAdministrador to set
     */
    public void setMedioDePagoAdministrador(String medioDePagoAdministrador) {
        this.medioDePagoAdministrador = medioDePagoAdministrador;
    }

    /**
     * @return the itemsBeneficiarios
     */
    public List<ItemResultadoLiquidacionFallecimientoDTO> getItemsBeneficiarios() {
        return itemsBeneficiarios;
    }

    /**
     * @param itemsBeneficiarios
     *        the itemsBeneficiarios to set
     */
    public void setItemsBeneficiarios(List<ItemResultadoLiquidacionFallecimientoDTO> itemsBeneficiarios) {
        this.itemsBeneficiarios = itemsBeneficiarios;
    }

    /**
     * @return the resultadoDispersion
     */
    public String getResultadoDispersion() {
        return resultadoDispersion;
    }

    /**
     * @param resultadoDispersion
     *        the resultadoDispersion to set
     */
    public void setResultadoDispersion(String resultadoDispersion) {
        this.resultadoDispersion = resultadoDispersion;
    }

    /**
     * @return the totalDispersado
     */
    public BigDecimal getTotalDispersado() {
        return totalDispersado;
    }

    /**
     * @param totalDispersado
     *        the totalDispersado to set
     */
    public void setTotalDispersado(BigDecimal totalDispersado) {
        this.totalDispersado = totalDispersado;
    }

    /**
     * @return the totalProgramado
     */
    public BigDecimal getTotalProgramado() {
        return totalProgramado;
    }

    /**
     * @param totalProgramado
     *        the totalProgramado to set
     */
    public void setTotalProgramado(BigDecimal totalProgramado) {
        this.totalProgramado = totalProgramado;
    }

}
