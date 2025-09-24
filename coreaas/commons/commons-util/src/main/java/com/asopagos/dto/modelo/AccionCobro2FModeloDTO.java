package com.asopagos.dto.modelo;

import com.asopagos.entidades.ccf.cartera.AccionCobro2F;
import com.asopagos.enumeraciones.cartera.FechaConteoDiasEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * Entidad que representa la parametrización para la acción de cobro metodo 2F
 * @author clmarin
 * @version 1.0
 * @created 17-nov.-2017 3:07:30 p. m.
 */
public class AccionCobro2FModeloDTO extends ParametrizacionGestionCobroModeloDTO implements Serializable {

    /**
     * Serial version Id
     */
    private static final long serialVersionUID = 6630230501211204005L;
    /**
     * Fecha de inicio de conteo de días: Fecha de envío liquidación de aportes, Fecha
     * de entrega de liquidación de aportes
     */
    private FechaConteoDiasEnum inicioDiasConteo;
    /**
     * Número de días transcurridos desde la fecha de inicio del conteo de días
     */
    private Long diasTranscurridos;
    /**
     * Hora inicio de ejecución del proceso
     */
    private Long horaEjecucion;
    /**
     * Plazo límite de envío de comunicado
     */
    private Long limiteEnvio;
    /**
     * Tener más de X días luego de registro de Registro de Notificación
     * Personal del Aportante igual a Notificado Personalmente
     */
    private Long diasRegistro;
    /**
     * X días parametrizados luego de registrar resultado de 2E igual a Exitoso
     */
    private Long diasParametrizados;

    /**
     * comuicado parametrizado para inicio de conteo de dias
     */
    private String comunicado;

    /**
     * Método que sirve para convertir de una entidad a un DTO para este caso AccionCobro2FModeloDTO
     * @param accionCobro2F
     *        recibe la entity
     * @return devuelve un objeto DTO {@link AccionCobro2FModeloDTO}
     */
    public AccionCobro2FModeloDTO convertToDTO(AccionCobro2F accionCobro2F) {
        super.convertToDTO(accionCobro2F);
        /* Se setean la información al objeto AccionCobro2F2GModeloDTO */
        this.setInicioDiasConteo(accionCobro2F.getInicioDiasConteo());
        this.setDiasTranscurridos(accionCobro2F.getDiasTranscurridos());
        this.setHoraEjecucion(accionCobro2F.getHoraEjecucion() != null ? accionCobro2F.getHoraEjecucion().getTime() : null);
        this.setLimiteEnvio(accionCobro2F.getLimiteEnvio());
        this.setDiasRegistro(accionCobro2F.getDiasRegistro());
        this.setDiasParametrizados(accionCobro2F.getDiasParametrizados());
        this.setComunicado(accionCobro2F.getComunicado());
        return this;
    }

    /**
     * Método que sirve para convertir de un DTO a una entity para este caso {@link AccionCobro2F}
     * @return devuelve un objeto Entity de {@link AccionCobro2F}
     */
    public AccionCobro2F convertToAccionCobro2FEntity() {
        /* Se instancia objeto AccionCobro2F */
        AccionCobro2F accionCobro2F = new AccionCobro2F();
        /* Se carga información de la clase padre */
        accionCobro2F = (AccionCobro2F) super.convertToEntity(accionCobro2F);
        /* Se setan los valores a accionCobro2F2G */
        accionCobro2F.setInicioDiasConteo(this.getInicioDiasConteo());
        accionCobro2F.setDiasTranscurridos(this.getDiasTranscurridos());
        accionCobro2F.setHoraEjecucion(this.getHoraEjecucion() != null ? new Date(this.getHoraEjecucion()) : null);
        accionCobro2F.setLimiteEnvio(this.getLimiteEnvio());
        accionCobro2F.setDiasParametrizados(this.getDiasParametrizados());
        accionCobro2F.setDiasRegistro(this.getDiasRegistro());
        accionCobro2F.setComunicado(this.getComunicado());
        return accionCobro2F;
    }

    /**
     * @return the inicioDiasConteo
     */
    public FechaConteoDiasEnum getInicioDiasConteo() {
        return inicioDiasConteo;
    }

    /**
     * @param inicioDiasConteo
     *        the inicioDiasConteo to set
     */
    public void setInicioDiasConteo(FechaConteoDiasEnum inicioDiasConteo) {
        this.inicioDiasConteo = inicioDiasConteo;
    }

    /**
     * @return the diasTranscurridos
     */
    public Long getDiasTranscurridos() {
        return diasTranscurridos;
    }

    /**
     * @param diasTranscurridos
     *        the diasTranscurridos to set
     */
    public void setDiasTranscurridos(Long diasTranscurridos) {
        this.diasTranscurridos = diasTranscurridos;
    }

    /**
     * @return the horaEjecucion
     */
    public Long getHoraEjecucion() {
        return horaEjecucion;
    }

    /**
     * @param horaEjecucion
     *        the horaEjecucion to set
     */
    public void setHoraEjecucion(Long horaEjecucion) {
        this.horaEjecucion = horaEjecucion;
    }

    /**
     * @return the limiteEnvio
     */
    public Long getLimiteEnvio() {
        return limiteEnvio;
    }

    /**
     * @param limiteEnvio
     *        the limiteEnvio to set
     */
    public void setLimiteEnvio(Long limiteEnvio) {
        this.limiteEnvio = limiteEnvio;
    }

    /**
     * Obtiene el valor de diasRegistro
     * @return El valor de diasRegistro
     */
    public Long getDiasRegistro() {
        return diasRegistro;
    }

    /**
     * Establece el valor de diasRegistro
     * @param diasRegistro
     *        El valor de diasRegistro por asignar
     */
    public void setDiasRegistro(Long diasRegistro) {
        this.diasRegistro = diasRegistro;
    }

    /**
     * Obtiene el valor de diasParametrizados
     * @return El valor de diasParametrizados
     */
    public Long getDiasParametrizados() {
        return diasParametrizados;
    }

    /**
     * Establece el valor de diasParametrizados
     * @param diasParametrizados
     *        El valor de diasParametrizados por asignar
     */
    public void setDiasParametrizados(Long diasParametrizados) {
        this.diasParametrizados = diasParametrizados;
    }

    /**
     *
     * get comunicado
     * @return
     */
    public String getComunicado() {
        return comunicado;
    }

    /**
     * set comunicado
     * @param comunicado
     */
    public void setComunicado(String comunicado) {
        this.comunicado = comunicado;
    }
}