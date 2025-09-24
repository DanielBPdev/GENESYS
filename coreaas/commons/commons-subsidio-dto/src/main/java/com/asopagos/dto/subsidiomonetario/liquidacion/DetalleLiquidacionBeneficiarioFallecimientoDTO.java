package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoCumplimientoEnum;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información detallada de las cuotas asignadas a los beneficiarios
 * <b>Módulo:</b> Asopagos - HU-317-517 <br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class DetalleLiquidacionBeneficiarioFallecimientoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Nombre del beneficiario asociado al detalle de liquidación por fallecimiento
     */
    private String nombreBeneficiario;

    /**
     * Tipo de identificación del beneficiario asociado al detalle de liquidación por fallecimiento;
     */
    private TipoIdentificacionEnum tipoIdentificacionBeneficiario;

    /**
     * Número de identificación del beneficiario asociado al detalle de liquidación por fallecimiento
     */
    private String numeroIdentificacionBeneficiario;

    /**
     * Relacion familiar entre el beneficiario y el afiliado fallecido
     */
    private String parentesco;

    /**
     * Indicador de cumplimiento para beneficio dentro del proceso de liquidación
     */
    private TipoCumplimientoEnum resultado;

    /**
     * Lista de items con el detalle cuota beneficiario
     */
    private List<ItemDetalleLiquidacionBeneficiarioFallecimientoDTO> itemsDetalle;

    /**
     * Lista de detalles de los descuentos aplicados por cada entidad
     */
    private List<DescuentoEntidadBeneficiarioFallecimientoDTO> detallesDescuentosEntidades;
    
    /**periodo en que se realiza la liquidación*/
    private Date periodo;

    /**
     * @return the nombreBeneficiario
     */
    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    /**
     * @param nombreBeneficiario
     *        the nombreBeneficiario to set
     */
    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    /**
     * @return the tipoIdentificacionBeneficiario
     */
    public TipoIdentificacionEnum getTipoIdentificacionBeneficiario() {
        return tipoIdentificacionBeneficiario;
    }

    /**
     * @param tipoIdentificacionBeneficiario
     *        the tipoIdentificacionBeneficiario to set
     */
    public void setTipoIdentificacionBeneficiario(TipoIdentificacionEnum tipoIdentificacionBeneficiario) {
        this.tipoIdentificacionBeneficiario = tipoIdentificacionBeneficiario;
    }

    /**
     * @return the numeroIdentificacionBeneficiario
     */
    public String getNumeroIdentificacionBeneficiario() {
        return numeroIdentificacionBeneficiario;
    }

    /**
     * @param numeroIdentificacionBeneficiario
     *        the numeroIdentificacionBeneficiario to set
     */
    public void setNumeroIdentificacionBeneficiario(String numeroIdentificacionBeneficiario) {
        this.numeroIdentificacionBeneficiario = numeroIdentificacionBeneficiario;
    }

    /**
     * @return the parentesco
     */
    public String getParentesco() {
        return parentesco;
    }

    /**
     * @param parentesco
     *        the parentesco to set
     */
    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    /**
     * @return the resultado
     */
    public TipoCumplimientoEnum getResultado() {
        return resultado;
    }

    /**
     * @param resultado
     *        the resultado to set
     */
    public void setResultado(TipoCumplimientoEnum resultado) {
        this.resultado = resultado;
    }

    /**
     * @return the itemsDetalle
     */
    public List<ItemDetalleLiquidacionBeneficiarioFallecimientoDTO> getItemsDetalle() {
        return itemsDetalle;
    }

    /**
     * @param itemsDetalle
     *        the itemsDetalle to set
     */
    public void setItemsDetalle(List<ItemDetalleLiquidacionBeneficiarioFallecimientoDTO> itemsDetalle) {
        this.itemsDetalle = itemsDetalle;
    }

    /**
     * @return the detallesDescuentosEntidades
     */
    public List<DescuentoEntidadBeneficiarioFallecimientoDTO> getDetallesDescuentosEntidades() {
        return detallesDescuentosEntidades;
    }

    /**
     * @param detallesDescuentosEntidades
     *        the detallesDescuentosEntidades to set
     */
    public void setDetallesDescuentosEntidades(List<DescuentoEntidadBeneficiarioFallecimientoDTO> detallesDescuentosEntidades) {
        this.detallesDescuentosEntidades = detallesDescuentosEntidades;
    }

    /**
     * @return the periodo
     */
    public Date getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(Date periodo) {
        this.periodo = periodo;
    }

}
