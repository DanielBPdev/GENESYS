package com.asopagos.novedades.dto;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * @author dsuesca
 *
 */
public class DatosAfiliadoRetiroDTO implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id del afiliado
     */
    private Long idAfiliado;
    
    /**
     * Fecha del último retiro del afiliado
     */
    private Date fechaRetiro;
    
    /**
     * Número de radicado de la solicitud de novedad de retiro
     */
    private String numeroRadicacion;
    
    /**
     * Transacción realizada en el retiro.
     */
    private TipoTransaccionEnum tipoTransaccion;

    /**
     * @return the idAfiliado
     */
    public Long getIdAfiliado() {
        return idAfiliado;
    }

    /**
     * 
     */
    public DatosAfiliadoRetiroDTO() {
    }
    
    /**
     * @param idAfiliado
     * @param fechaRetiro
     * @param numeroRadicacion
     * @param tipoTransaccion
     */
    public DatosAfiliadoRetiroDTO(Long idAfiliado, Date fechaRetiro, String numeroRadicacion, String tipoTransaccion) {
        super();
        this.idAfiliado = idAfiliado;
        this.fechaRetiro = fechaRetiro;
        this.numeroRadicacion = numeroRadicacion;
        this.tipoTransaccion = TipoTransaccionEnum.valueOf(tipoTransaccion);
    }

    /**
     * @param idAfiliado the idAfiliado to set
     */
    public void setIdAfiliado(Long idAfiliado) {
        this.idAfiliado = idAfiliado;
    }

    /**
     * @return the fechaRetiro
     */
    public Date getFechaRetiro() {
        return fechaRetiro;
    }

    /**
     * @param fechaRetiro the fechaRetiro to set
     */
    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    /**
     * @return the numeroRadicacion
     */
    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }

    /**
     * @param numeroRadicacion the numeroRadicacion to set
     */
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    /**
     * @return the tipoTransaccion
     */
    public TipoTransaccionEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * @param tipoTransaccion the tipoTransaccion to set
     */
    public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }
    
}
