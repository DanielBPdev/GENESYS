package com.asopagos.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.core.TipoEtiquetaEnum;

/**
 * DTO utilizado para transportar la fecha de generación de un número de
 * radicado de solicitud o de un número de etiqueta de correspondencia física
 * 
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 *
 */
@XmlRootElement
public class NumeroRadicadoCorrespondenciaDTO implements Serializable {

    /**
     * SERIAL
     */
    private static final long serialVersionUID = 4516422769613365569L;

    /** Identificador de la sede de la cada de compensaciíon familiar */
    private String codigoCaja;

    /** Identificador de la sede de la cada de compensaciíon familiar */
    private String codigoSede;

    /** Fecha utilizada para la generación del radicado (YYYY) o de etiqueta (YY) */
    private String anio;

    /** número de radicado de solicitud */
    private Integer numero;

    /** número de radicado de solicitud */
    private Integer cantidad;

    /** número de radicado de solicitud */
    private Integer actual;

    /** tipo de etiqueta */
    private TipoEtiquetaEnum tipoEtiqueta;

    /**
     * Constructor por defecto
     */
    public NumeroRadicadoCorrespondenciaDTO() {
        super();
    }

    /**
     * Constructor
     * @param codigoCaja
     * @param codigoSede
     * @param anio
     * @param numero
     * @param cantidad
     */
    public NumeroRadicadoCorrespondenciaDTO(String codigoCaja, String codigoSede, String anio, Integer numero, Integer cantidad,
            TipoEtiquetaEnum tipoEtiqueta) {
        this.codigoCaja = codigoCaja;
        this.codigoSede = codigoSede;
        this.anio = anio;
        this.numero = numero;
        this.cantidad = cantidad;
        this.tipoEtiqueta = tipoEtiqueta;
        this.actual = 0;
    }

    /**
     * Retorna el valor de numero de radicado o de correspondencia según su tipo:
     * <br />
     * número de radicado -> CCAAAA###### donde:
     * • CC: código de la caja de compensación
     * • AAAA: año
     * • ######: número de consecutivo (se reinicia cada año)
     * <br />
     * <br />
     * correspondencia fisica -> CCAABB######, donde:
     * • CC: código de la caja de compensación
     * • AA: año
     * • BB: oficina / sede / punto de atención que envía
     * • ######: número consecutivo (se reinicia cada año)
     */
    public String nextValue() {

        if (cantidad.equals(actual)) {
            return null;
        }

        StringBuilder numeroRadicadoCorrespondencia = new StringBuilder();
        numeroRadicadoCorrespondencia.append(codigoCaja);
        if (TipoEtiquetaEnum.NUMERO_RADICADO.equals(tipoEtiqueta)) {
            numeroRadicadoCorrespondencia.append(anio);
        }
        else {
            numeroRadicadoCorrespondencia.append(anio.substring(2, 4));
            numeroRadicadoCorrespondencia.append(codigoSede);
        }

        numeroRadicadoCorrespondencia.append(String.format("%06d", (numero + actual)));

        actual++;

        return numeroRadicadoCorrespondencia.toString();
    }

    /**
     * @return the codigoCaja
     */
    public String getCodigoCaja() {
        return codigoCaja;
    }

    /**
     * @param codigoCaja
     *        the codigoCaja to set
     */
    public void setCodigoCaja(String codigoCaja) {
        this.codigoCaja = codigoCaja;
    }

    /**
     * @return the codigoSede
     */
    public String getCodigoSede() {
        return codigoSede;
    }

    /**
     * @param codigoSede
     *        the codigoSede to set
     */
    public void setCodigoSede(String codigoSede) {
        this.codigoSede = codigoSede;
    }

    /**
     * @return the anio
     */
    public String getAnio() {
        return anio;
    }

    /**
     * @param anio
     *        the anio to set
     */
    public void setAnio(String anio) {
        this.anio = anio;
    }

    /**
     * @return the numero
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * @param numero
     *        the numero to set
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    /**
     * @return the cantidad
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad
     *        the cantidad to set
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the actual
     */
    public Integer getActual() {
        return actual;
    }

    /**
     * @param actual
     *        the actual to set
     */
    public void setActual(Integer actual) {
        this.actual = actual;
    }

    /**
     * @return the tipoEtiqueta
     */
    public TipoEtiquetaEnum getTipoEtiqueta() {
        return tipoEtiqueta;
    }

    /**
     * @param tipoEtiqueta
     *        the tipoEtiqueta to set
     */
    public void setTipoEtiqueta(TipoEtiquetaEnum tipoEtiqueta) {
        this.tipoEtiqueta = tipoEtiqueta;
    }

}
