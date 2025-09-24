package com.asopagos.afiliados.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.asopagos.enumeraciones.core.TipoContratoEnum;
import com.asopagos.enumeraciones.core.TipoSalarioEnum;
import com.asopagos.enumeraciones.personas.ClaseTrabajadorEnum;

public class InfoRelacionLaboral360DTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Date fechaInicioLabores;
    private ClaseTrabajadorEnum claseTrabajador;
    private BigDecimal salarioMensual;
    private TipoSalarioEnum tipoSalario;
    private String cargo;
    private TipoContratoEnum tipoContrato;
    private Date fechaTerminacionContrato;
    private Date fechaInicioCondicionVet;
    private Date fechaFinCondicionVet; 
    
    /**
     * 
     */
    public InfoRelacionLaboral360DTO() {
    }
    
    /**
     * @param fechaInicioLabores
     * @param claseTrabajador
     * @param salarioMensual
     * @param tipoSalario
     * @param cargo
     * @param tipoContrato
     * @param fechaTerminacionContrato
     */
    public InfoRelacionLaboral360DTO(Date fechaInicioLabores, ClaseTrabajadorEnum claseTrabajador, BigDecimal salarioMensual,
            TipoSalarioEnum tipoSalario, String cargo, TipoContratoEnum tipoContrato, Date fechaTerminacionContrato) {
        this.fechaInicioLabores = fechaInicioLabores;
        this.claseTrabajador = claseTrabajador;
        this.salarioMensual = salarioMensual;
        this.tipoSalario = tipoSalario;
        this.cargo = cargo;
        this.tipoContrato = tipoContrato;
        this.fechaTerminacionContrato = fechaTerminacionContrato;
    }

    public InfoRelacionLaboral360DTO(Date fechaInicioLabores, ClaseTrabajadorEnum claseTrabajador, BigDecimal salarioMensual,
            TipoSalarioEnum tipoSalario, String cargo, TipoContratoEnum tipoContrato, Date fechaTerminacionContrato,Date fechaInicioVet,Date fechaFinVet) {
        this.fechaInicioLabores = fechaInicioLabores;
        this.claseTrabajador = claseTrabajador;
        this.salarioMensual = salarioMensual;
        this.tipoSalario = tipoSalario;
        this.cargo = cargo;
        this.tipoContrato = tipoContrato;
        this.fechaTerminacionContrato = fechaTerminacionContrato;
        this.fechaInicioCondicionVet = fechaInicioVet;
        this.fechaFinCondicionVet = fechaFinVet;
    }
    
    /**
     * @return the fechaInicioLabores
     */
    public Date getFechaInicioLabores() {
        return fechaInicioLabores;
    }
    
    /**
     * @param fechaInicioLabores the fechaInicioLabores to set
     */
    public void setFechaInicioLabores(Date fechaInicioLabores) {
        this.fechaInicioLabores = fechaInicioLabores;
    }
    
    /**
     * @return the claseTrabajador
     */
    public ClaseTrabajadorEnum getClaseTrabajador() {
        return claseTrabajador;
    }
    
    /**
     * @param claseTrabajador the claseTrabajador to set
     */
    public void setClaseTrabajador(ClaseTrabajadorEnum claseTrabajador) {
        this.claseTrabajador = claseTrabajador;
    }
    
    /**
     * @return the salarioMensual
     */
    public BigDecimal getSalarioMensual() {
        return salarioMensual;
    }
    /**
     * @param salarioMensual the salarioMensual to set
     */
    public void setSalarioMensual(BigDecimal salarioMensual) {
        this.salarioMensual = salarioMensual;
    }
    /**
     * @return the tipoSalario
     */
    public TipoSalarioEnum getTipoSalario() {
        return tipoSalario;
    }
    /**
     * @param tipoSalario the tipoSalario to set
     */
    public void setTipoSalario(TipoSalarioEnum tipoSalario) {
        this.tipoSalario = tipoSalario;
    }
    /**
     * @return the cargo
     */
    public String getCargo() {
        return cargo;
    }
    /**
     * @param cargo the cargo to set
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    /**
     * @return the tipoContrato
     */
    public TipoContratoEnum getTipoContrato() {
        return tipoContrato;
    }
    /**
     * @param tipoContrato the tipoContrato to set
     */
    public void setTipoContrato(TipoContratoEnum tipoContrato) {
        this.tipoContrato = tipoContrato;
    }
    /**
     * @return the fechaTerminacionContrato
     */
    public Date getFechaTerminacionContrato() {
        return fechaTerminacionContrato;
    }
    /**
     * @param fechaTerminacionContrato the fechaTerminacionContrato to set
     */
    public void setFechaTerminacionContrato(Date fechaTerminacionContrato) {
        this.fechaTerminacionContrato = fechaTerminacionContrato;
    }
    /**
     * @return the fechaInicioCondicionVet
     */
    public Date getFechaInicioVet() {
        return fechaInicioCondicionVet;
    }
    /**
     * @param fechaTerminacionContrato the fechaTerminacionContrato to set
     */
    public void setFechaInicioVet(Date fechaInicio) {
        this.fechaInicioCondicionVet = fechaInicio;
    }
     /**
     * @return the fechaInicioCondicionVet
     */
    public Date getFechaFincioVet() {
        return fechaFinCondicionVet;
    }
    /**
     * @param fechaTerminacionContrato the fechaTerminacionContrato to set
     */
    public void setFinInicioVet(Date fechaFin) {
        this.fechaFinCondicionVet = fechaFin;
    }
}
