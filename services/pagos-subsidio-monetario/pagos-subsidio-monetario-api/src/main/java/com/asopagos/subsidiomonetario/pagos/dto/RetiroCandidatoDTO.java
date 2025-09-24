package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> DTO para registrar un retiro del tercer pagador candidato <br/>
 * <b>Módulo:</b> Asopagos - HU - 31 - 205 <br/>
 *
 * @author  <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@XmlRootElement
public class RetiroCandidatoDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3632898216566599317L;
    
    /**
     * Identificación de la transacción del tercer pagador del retiro candidato a conciliar 
     */
    private String identificacionTransaccionTerceroPagador;
    
    /**
     * Tipo de identificación del administración del subsidio del retiro candidato a conciliar
     */
    private TipoIdentificacionEnum tipoIdentificacionAdministradorSubsidio;
    
    /**
     * Numero de identificación del administrador del subsidio del retiro candidato a conciliar
     */
    private String numeroIdentificacionAdministradorSubsidio;
    
    /**
     * Valor real de la transaccion del retiro candidato a conciliar
     */
    private BigDecimal valorRealTransaccion;
    
    /**
     * Fecha de la transacción del retiro candidato a conciliar
     */
    private Date fechaTransaccion;
    
    /**
     * Hora de la transacción del retiro candidato a conciliar
     */
    private String horaTransaccion;
    
    /**
     * Departamento en el cual se realiza el retiro candidato a conciliar
     */
    private String departamento;
    
    /**
     * Municipio en el cual se realiza el retiro candidato a conciliar
     */
    private String municipio;
    
    /**
     * Tipo de subsidio por el cual se realiza el retiro candidato a conciliar
     */
    private Character tipoSubsidio;
    
   

    /**
     * @return the identificacionTransaccionTerceroPagador
     */
    public String getIdentificacionTransaccionTerceroPagador() {
        return identificacionTransaccionTerceroPagador;
    }

    /**
     * @param identificacionTransaccionTerceroPagador the identificacionTransaccionTerceroPagador to set
     */
    public void setIdentificacionTransaccionTerceroPagador(String identificacionTransaccionTerceroPagador) {
        this.identificacionTransaccionTerceroPagador = identificacionTransaccionTerceroPagador;
    }

    /**
     * @return the tipoIdentificacionAdministradorSubsidio
     */
    public TipoIdentificacionEnum getTipoIdentificacionAdministradorSubsidio() {
        return tipoIdentificacionAdministradorSubsidio;
    }

    /**
     * @param tipoIdentificacionAdministradorSubsidio the tipoIdentificacionAdministradorSubsidio to set
     */
    public void setTipoIdentificacionAdministradorSubsidio(TipoIdentificacionEnum tipoIdentificacionAdministradorSubsidio) {
        this.tipoIdentificacionAdministradorSubsidio = tipoIdentificacionAdministradorSubsidio;
    }

    /**
     * @return the numeroIdentificacionAdministradorSubsidio
     */
    public String getNumeroIdentificacionAdministradorSubsidio() {
        return numeroIdentificacionAdministradorSubsidio;
    }

    /**
     * @param numeroIdentificacionAdministradorSubsidio the numeroIdentificacionAdministradorSubsidio to set
     */
    public void setNumeroIdentificacionAdministradorSubsidio(String numeroIdentificacionAdministradorSubsidio) {
        this.numeroIdentificacionAdministradorSubsidio = numeroIdentificacionAdministradorSubsidio;
    }

    /**
     * @return the valorRealTransaccion
     */
    public BigDecimal getValorRealTransaccion() {
        return valorRealTransaccion;
    }

    /**
     * @param valorRealTransaccion the valorRealTransaccion to set
     */
    public void setValorRealTransaccion(BigDecimal valorRealTransaccion) {
        this.valorRealTransaccion = valorRealTransaccion;
    }

    /**
     * @return the fechaTransaccion
     */
    public Date getFechaTransaccion() {
        return fechaTransaccion;
    }

    /**
     * @param fechaTransaccion the fechaTransaccion to set
     */
    public void setFechaTransaccion(Date fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }   

    /**
     * @return the horaTransaccion
     */
    public String getHoraTransaccion() {
        return horaTransaccion;
    }

    /**
     * @param horaTransaccion the horaTransaccion to set
     */
    public void setHoraTransaccion(String horaTransaccion) {
        this.horaTransaccion = horaTransaccion;
    }

    /**
     * @return the departamento
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento the departamento to set
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * @return the municipio
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * @param municipio the municipio to set
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * @return the tipoSubsidio
     */
    public Character getTipoSubsidio() {
        return tipoSubsidio;
    }

    /**
     * @param tipoSubsidio the tipoSubsidio to set
     */
    public void setTipoSubsidio(Character tipoSubsidio) {
        this.tipoSubsidio = tipoSubsidio;
    }    
    

}
