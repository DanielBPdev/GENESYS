package com.asopagos.dto.modelo;

import java.io.Serializable;
import com.asopagos.entidades.ccf.cartera.AccionCobro1F;
import com.asopagos.enumeraciones.cartera.AccionCarteraEnum;

/**
 * Entidad que representa la acción de cobro método 1F
 * @author clmarin
 * @version 1.0
 * @created 17-nov.-2017 3:07:23 p. m.
 */
public class AccionCobro1FModeloDTO extends ParametrizacionGestionCobroModeloDTO implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 1002934073594281385L;
    /**
     * Habilitar acción de cobro 1F: Notificación de desafiliación
     */
    private Boolean accionCobro1F;
    /**
     * Realizar acción X días parametrizados luego de la opción de la opción
     * parametrizada en el ítem Gestión de Cobro 1E
     */
    private Short diasParametrizados;
    /**
     * Siguiente acción se realiza luego de: Resultado de envío de comunicado o
     * Registro de recepción de comunicado
     */
    private AccionCarteraEnum siguienteAccion;

    /**
     * @return the accionCobro1F
     */
    public Boolean getAccionCobro1F() {
        return accionCobro1F;
    }

    /**
     * @param accionCobro1F
     *        the accionCobro1F to set
     */
    public void setAccionCobro1F(Boolean accionCobro1F) {
        this.accionCobro1F = accionCobro1F;
    }

    /**
     * @return the diasParametrizados
     */
    public Short getDiasParametrizados() {
        return diasParametrizados;
    }

    /**
     * @param diasParametrizados
     *        the diasParametrizados to set
     */
    public void setDiasParametrizados(Short diasParametrizados) {
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
     * Método que sirve para convertir de una entidad a un DTO para este caso AccionCobro1FModeloDTO
     * @param accionCobro1F
     *        recibe la entity
     * @return devuelve un objeto DTO {@link AccionCobro1FModeloDTO}
     */
    public AccionCobro1FModeloDTO convertToDTO(AccionCobro1F accionCobro1F) {
        super.convertToDTO(accionCobro1F);
        /* Se setean la información al objeto AccionCobro1FModeloDTO */
        this.setAccionCobro1F(accionCobro1F.getAccionCobro1F());
        this.setDiasParametrizados(accionCobro1F.getDiasParametrizados());
        this.setSiguienteAccion(accionCobro1F.getSiguienteAccion());
        return this;
    }

    /**
     * Método que sirve para convertir de un DTO a una entity para este caso {@link AccionCobro1F}
     * @return devuelve un objeto Entity de {@link AccionCobro1F}
     */
    public AccionCobro1F convertToAccionCobro1FEntity() {
        /* Se instancia objeto AccionCobro1F */
        AccionCobro1F accionCobro1F = new AccionCobro1F();
        /* Se carga información de la clase padre */
        accionCobro1F = (AccionCobro1F) super.convertToEntity(accionCobro1F);
        /* Se setan los valores a accionCobro1F */
        accionCobro1F.setAccionCobro1F(this.getAccionCobro1F());
        accionCobro1F.setDiasParametrizados(this.getDiasParametrizados());
        accionCobro1F.setSiguienteAccion(this.getSiguienteAccion());
        return accionCobro1F;
    }
}