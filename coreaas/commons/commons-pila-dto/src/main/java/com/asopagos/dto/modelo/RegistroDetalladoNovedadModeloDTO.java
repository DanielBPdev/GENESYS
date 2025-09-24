/**
 * 
 */
package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import com.asopagos.entidades.pila.staging.RegistroDetalladoNovedad;
import com.asopagos.enumeraciones.aportes.MarcaAccionNovedadEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;

/**
 * <b>Descripcion:</b> DTO para el Entity RegistroDetalladoNovedad <br/>
 * <b>Módulo:</b> Asopagos - HU-211-410, HU-211-398 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class RegistroDetalladoNovedadModeloDTO implements Serializable {
    private static final long serialVersionUID = 1842370615590073120L;

    /**
     * Código identificador de registro de novedad para registro detallado
     * */
    private Integer id;
    
    /**
     * Identificador del registro detallado al cual se asocia la novedad
     * */
    private Long registroDetallado;
    
    /**
     * Tipo de transacción para la novedad
     * */
    private TipoTransaccionEnum tipoTransaccion;
    
    /**
     * Tipo de novedad de acuerdo a la clasificación de PILA
     * */
    private String tipoNovedad;
    
    /**
     * Acción determinada para la novedad
     * */
    private MarcaAccionNovedadEnum accionNovedad;
    
    /**
     * Mensaje de procesamiento de la novedad
     * */
    private String mensajeNovedad;
    
    /**
     * Fecha de inicio de la novedad
     * */
    private Long fechaInicioNovedad;
    
    /**
     * Fecha de finalizaicón de la novedad
     * */
    private Long fechaFinNovedad;
    
    /**
     * Marca de aplicación para determinar que la novedad se va a aplicar
     * */
    private Boolean marcaAplicarNovedad;
    
    /**
     * Tipo de afiliado de la novedad
     * */
    private TipoAfiliadoEnum outTipoAfiliado;
    
    /**
     * Método para la conversión de DTO a Entity
     * */
    public RegistroDetalladoNovedad convertToEntity(){
        RegistroDetalladoNovedad registroDetalladoNovedad = new RegistroDetalladoNovedad();

        registroDetalladoNovedad.setId(this.getId());
        registroDetalladoNovedad.setRegistroDetallado(this.getRegistroDetallado());
        registroDetalladoNovedad.setTipotransaccion(this.getTipoTransaccion());
        registroDetalladoNovedad.setTipoNovedad(this.getTipoNovedad());
        registroDetalladoNovedad.setAccionNovedad(this.getAccionNovedad());
        registroDetalladoNovedad.setMensajeNovedad(this.getMensajeNovedad());
        if(this.getFechaInicioNovedad() != null){
            registroDetalladoNovedad.setFechaInicioNovedad(new Date(this.getFechaInicioNovedad()));
        }
        if(this.getFechaFinNovedad() != null){
            registroDetalladoNovedad.setFechaFinNovedad(new Date(this.getFechaFinNovedad()));
        }
        registroDetalladoNovedad.setOutTipoAfiliado(this.getOutTipoAfiliado());
        
        return registroDetalladoNovedad;
    }
    
    /**
     * Método para la conversión de Entity a DTO
     * */
    public void converToDTO(RegistroDetalladoNovedad registroDetalladoNovedad){
        this.id = registroDetalladoNovedad.getId();
        this.registroDetallado = registroDetalladoNovedad.getRegistroDetallado();
        this.tipoTransaccion = registroDetalladoNovedad.getTipotransaccion();
        this.tipoNovedad = registroDetalladoNovedad.getTipoNovedad();
        this.accionNovedad = registroDetalladoNovedad.getAccionNovedad();
        this.mensajeNovedad = registroDetalladoNovedad.getMensajeNovedad();
        if(registroDetalladoNovedad.getFechaInicioNovedad() != null){
            this.fechaInicioNovedad = registroDetalladoNovedad.getFechaInicioNovedad().getTime();
        }
        if(registroDetalladoNovedad.getFechaFinNovedad() != null){
            this.fechaFinNovedad = registroDetalladoNovedad.getFechaFinNovedad().getTime();
        }
        this.outTipoAfiliado = registroDetalladoNovedad.getOutTipoAfiliado();
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the registroDetallado
     */
    public Long getRegistroDetallado() {
        return registroDetallado;
    }

    /**
     * @param registroDetallado the registroDetallado to set
     */
    public void setRegistroDetallado(Long registroDetallado) {
        this.registroDetallado = registroDetallado;
    }

    /**
     * @return the tipoNovedad
     */
    public String getTipoNovedad() {
        return tipoNovedad;
    }

    /**
     * @param tipoNovedad the tipoNovedad to set
     */
    public void setTipoNovedad(String tipoNovedad) {
        this.tipoNovedad = tipoNovedad;
    }

    /**
     * @return the accionNovedad
     */
    public MarcaAccionNovedadEnum getAccionNovedad() {
        return accionNovedad;
    }

    /**
     * @param accionNovedad the accionNovedad to set
     */
    public void setAccionNovedad(MarcaAccionNovedadEnum accionNovedad) {
        this.accionNovedad = accionNovedad;
    }

    /**
     * @return the mensajeNovedad
     */
    public String getMensajeNovedad() {
        return mensajeNovedad;
    }

    /**
     * @param mensajeNovedad the mensajeNovedad to set
     */
    public void setMensajeNovedad(String mensajeNovedad) {
        this.mensajeNovedad = mensajeNovedad;
    }

    /**
     * @return the fechaInicioNovedad
     */
    public Long getFechaInicioNovedad() {
        return fechaInicioNovedad;
    }

    /**
     * @param fechaInicioNovedad the fechaInicioNovedad to set
     */
    public void setFechaInicioNovedad(Long fechaInicioNovedad) {
        this.fechaInicioNovedad = fechaInicioNovedad;
    }

    /**
     * @return the fechaFinNovedad
     */
    public Long getFechaFinNovedad() {
        return fechaFinNovedad;
    }

    /**
     * @param fechaFinNovedad the fechaFinNovedad to set
     */
    public void setFechaFinNovedad(Long fechaFinNovedad) {
        this.fechaFinNovedad = fechaFinNovedad;
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

    /**
     * @return the marcaAplicarNovedad
     */
    public Boolean getMarcaAplicarNovedad() {
        return marcaAplicarNovedad;
    }

    /**
     * @param marcaAplicarNovedad the marcaAplicarNovedad to set
     */
    public void setMarcaAplicarNovedad(Boolean marcaAplicarNovedad) {
        this.marcaAplicarNovedad = marcaAplicarNovedad;
    }

    /**
     * @return the outTipoAfiliado
     */
    public TipoAfiliadoEnum getOutTipoAfiliado() {
        return outTipoAfiliado;
    }

    /**
     * @param outTipoAfiliado the outTipoAfiliado to set
     */
    public void setOutTipoAfiliado(TipoAfiliadoEnum outTipoAfiliado) {
        this.outTipoAfiliado = outTipoAfiliado;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegistroDetalladoNovedadModeloDTO [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (registroDetallado != null) {
			builder.append("registroDetallado=");
			builder.append(registroDetallado);
			builder.append(", ");
		}
		if (tipoTransaccion != null) {
			builder.append("tipoTransaccion=");
			builder.append(tipoTransaccion);
			builder.append(", ");
		}
		if (tipoNovedad != null) {
			builder.append("tipoNovedad=");
			builder.append(tipoNovedad);
			builder.append(", ");
		}
		if (accionNovedad != null) {
			builder.append("accionNovedad=");
			builder.append(accionNovedad);
			builder.append(", ");
		}
		if (mensajeNovedad != null) {
			builder.append("mensajeNovedad=");
			builder.append(mensajeNovedad);
			builder.append(", ");
		}
		if (fechaInicioNovedad != null) {
			builder.append("fechaInicioNovedad=");
			builder.append(fechaInicioNovedad);
			builder.append(", ");
		}
		if (fechaFinNovedad != null) {
			builder.append("fechaFinNovedad=");
			builder.append(fechaFinNovedad);
			builder.append(", ");
		}
		if (marcaAplicarNovedad != null) {
			builder.append("marcaAplicarNovedad=");
			builder.append(marcaAplicarNovedad);
			builder.append(", ");
		}
		if (outTipoAfiliado != null) {
			builder.append("outTipoAfiliado=");
			builder.append(outTipoAfiliado);
		}
		builder.append("]");
		return builder.toString();
	}

	
}
