package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.cartera.AccionCobro2H;
import com.asopagos.enumeraciones.cartera.AccionCarteraEnum;

/**
 * Entidad que representa la parametrización para la acción de cobro del metodo 2H
 * @author clmarin
 * @version 1.0
 * @created 17-nov.-2017 3:07:32 p. m.
 */
public class AccionCobro2HModeloDTO extends ParametrizacionGestionCobroModeloDTO implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -6972560578991554018L;
    /**
     * Habilitar acción de cobro 2H: Notificación de desafiliación
     */
    private Boolean accionCobro2H;
    /**
     * Tener más de X días luego de registro de Registro de Notificación Personal del
     * Aportante igual a Notificado Personalmente
     */
    private Long diasRegistro;
    /**
     * X días parametrizados luego de registrar resultado de 2E igual a Exitoso
     */
    private Long diasParametrizados;
    /**
     * Siguiente acción se realiza luego de: Resultado de envío de comunicado,
     * Registro de recepción de comunicado
     */
    private AccionCarteraEnum siguienteAccion;

    /**
     * @return the accionCobro2H
     */
    public Boolean getAccionCobro2H() {
        return accionCobro2H;
    }

    /**
     * @param accionCobro2H
     *        the accionCobro2H to set
     */
    public void setAccionCobro2H(Boolean accionCobro2H) {
        this.accionCobro2H = accionCobro2H;
    }

    /**
     * @return the diasRegistro
     */
    public Long getDiasRegistro() {
        return diasRegistro;
    }

    /**
     * @param diasRegistro
     *        the diasRegistro to set
     */
    public void setDiasRegistro(Long diasRegistro) {
        this.diasRegistro = diasRegistro;
    }

    /**
     * @return the diasParametrizados
     */
    public Long getDiasParametrizados() {
        return diasParametrizados;
    }

    /**
     * @param diasParametrizados
     *        the diasParametrizados to set
     */
    public void setDiasParametrizados(Long diasParametrizados) {
        this.diasParametrizados = diasParametrizados;
    }

    /**
     * @return the siguienteAccion
     */
    public AccionCarteraEnum getSiguienteAccion() {
        return siguienteAccion;
    }

    /**
     * @param siguienteAccion
     *        the siguienteAccion to set
     */
    public void setSiguienteAccion(AccionCarteraEnum siguienteAccion) {
        this.siguienteAccion = siguienteAccion;
    }

    /**
     * Método que sirve para convertir de una entidad a un DTO para este caso AccionCobro2HModeloDTO
     * @param accionCobro2H
     *        recibe la entity
     * @return devuelve un objeto DTO {@link AccionCobro2HModeloDTO}
     */
    public AccionCobro2HModeloDTO convertToDTO(AccionCobro2H accionCobro2H) {
        super.convertToDTO(accionCobro2H);
        /* Se setean la información al objeto AccionCobro2HModeloDTO */
        this.setAccionCobro2H(accionCobro2H.getAccionCobro2H());
        this.setDiasRegistro(accionCobro2H.getDiasRegistro());
        this.setDiasParametrizados(accionCobro2H.getDiasParametrizados());
        this.setSiguienteAccion(accionCobro2H.getSiguienteAccion());
        return this;
    }

    /**
     * Método que sirve para convertir de un DTO a una entity para este caso {@link AccionCobro2H}
     * @return devuelve un objeto Entity de {@link AccionCobro2H}
     */
    public AccionCobro2H convertToAccionCobro2HEntity() {
        /* Se instancia objeto AccionCobro2H */
        AccionCobro2H accionCobro2H = new AccionCobro2H();
        /* Se carga información de la clase padre */
        accionCobro2H = (AccionCobro2H) super.convertToEntity(accionCobro2H);
        /* Se setan los valores a accionCobro2H */
        accionCobro2H.setDiasRegistro(this.getDiasRegistro());
        accionCobro2H.setDiasParametrizados(this.getDiasParametrizados());
        accionCobro2H.setAccionCobro2H(this.getAccionCobro2H());
        accionCobro2H.setSiguienteAccion(this.getSiguienteAccion());
        return accionCobro2H;
    }
}