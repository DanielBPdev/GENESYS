/**
 * 
 */
package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;

import com.asopagos.entidades.pila.temporal.TemNovedad;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * <b>Descripcion:</b> DTO que representa la entidad TemNovedad<br/>
 * <b>Módulo:</b> Asopagos - HU-211-410 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class TemNovedadModeloDTO implements Serializable {
    private static final long serialVersionUID = -6275562806773481489L;

    /**
     * Código identificador de llave primaria. 
     */
    private Long id;
    
    /**
     * ID de la transacción
     * */
    private Long idTransaccion;
    
    /**
     * Indicador de registro simulado
     * */
    private Boolean marcaNovedadSimulado;
    
    /**
     * Indicador de registro de novedad manual
     * */
    private Boolean marcaNovedadManual;

    /**
     * Modalidad en la que se recibe el aporte asociado a la novedad
     */
    private ModalidadRecaudoAporteEnum modalidadRecaudoAporte;
    
    /**
     * ID de registro general asociado
     * */
    private Long registroGeneral;
    
    /**
     * ID de registro detallado asociado
     * */
    private Long registroDetallado;
    
    /**
     * Tipo de identificación del aportante
     * */
    private String tipoIdAportante;
    
    /**
     * Número de ID del aportante
     * */
    private String numeroIdAportante;
    
    /**
     * Tipo de ID del cotizante
     * */
    private String tipoIdCotizante;
    
    /**
     * Número de ID del cotizante
     * */
    private String numeroIdCotizante;
    
    /**
     * Tipo de la transacción (novedad)
     * */
    private TipoTransaccionEnum tipoTransaccion;
    
    /**
     * Indicador de novedad de ingreso
     * */
    private Boolean esIngreso;
    
    /**
     * Indicador de novedad de retiro
     * */
    private Boolean esRetiro;
    
    /**
     * Fecha de inicio de novedad
     * */
    private Long fechaInicioNovedad;
    
    /**
     * Fecha de fin de la novedad
     * */
    private Long fechaFinNovedad;
    
    /**
     * Acción a tomar con la novedad
     * */
    private String accionNovedad;
    
    /**
     * Mensaje de clarificación de la novedad
     * */
    private String mensajeNovedad;
    
    /**
     * Tipo de cotizante
     * */
    private String tipoCotizante;
    
    /**
     * Primer apellido del cotizante
     * */
    private String primerApellido;
    
    /**
     * Segundo apellido del cotizante
     * */
    private String segundoApellido;
    
    /**
     * Primer nombre del cotizante
     * */
    private String primerNombre;
    
    /**
     * Segundo nombre del cotizante
     * */
    private String segundoNombre;
    
    /**
     * Código de departamento de ubicación laboral del cotizante
     * */
    private Short codigoDepartamento;
    
    /**
     * Código de municipio de ubicación laboral del cotizante
     * */
    private Short CodigoMunicipio;
    
    /**
     * Marca que indica que el aportante es reintegrable
     * */
    private Boolean esEmpleadorReintegrable;
    
    /**
     * Marca que indica que el trabajador es reintegrable
     * */
    private Boolean esTrabajadorReintegrable;
    
    /**
     * Referencia al RegistroDetalladoNovedad
     * */
    private Long registroDetalladoNovedad;
    
    /**
     * Marca de control de registro en proceso
     * */
    private Boolean enProceso;
    
    public TemNovedadModeloDTO() {
    	super();
    }
    
    public TemNovedadModeloDTO(TemNovedad temNovedad){
    	super();
    	convertToDTO(temNovedad);
    }
    
    /**
     * Método para la conversión de Entity a DTO
     * */
    public void convertToDTO(TemNovedad temNovedad){
        this.id = temNovedad.getId();
        this.idTransaccion = temNovedad.getIdTransaccion();
        this.marcaNovedadSimulado = temNovedad.getMarcaNovedadSimulado();
        this.marcaNovedadManual = temNovedad.getMarcaNovedadManual();
        this.registroGeneral = temNovedad.getRegistroGeneral();
        this.registroDetallado = temNovedad.getRegistroDetallado();
        this.registroDetalladoNovedad = temNovedad.getRegistroDetalladoNovedad();
        this.tipoIdAportante = temNovedad.getTipoIdAportante();
        this.numeroIdAportante = temNovedad.getNumeroIdAportante();
        this.tipoIdCotizante = temNovedad.getTipoIdCotizante();
        this.numeroIdCotizante = temNovedad.getNumeroIdCotizante();
        this.tipoTransaccion = temNovedad.getTipoTransaccion();
        this.esIngreso = temNovedad.getEsIngreso();
        this.esRetiro = temNovedad.getEsRetiro();
        if(temNovedad.getFechaInicioNovedad() != null){
            this.fechaInicioNovedad = temNovedad.getFechaInicioNovedad().getTime();
        }
        if(temNovedad.getFechaFinNovedad() != null){
            this.fechaFinNovedad = temNovedad.getFechaFinNovedad().getTime();
        }
        this.accionNovedad = temNovedad.getAccionNovedad();
        this.mensajeNovedad = temNovedad.getMensajeNovedad();
        this.tipoCotizante = temNovedad.getTipoCotizante();
        this.primerApellido = temNovedad.getPrimerApellido();
        this.segundoApellido = temNovedad.getSegundoApellido();
        this.primerNombre = temNovedad.getPrimerNombre();
        this.segundoNombre = temNovedad.getSegundoNombre();
        this.codigoDepartamento = temNovedad.getCodigoDepartamento();
        this.CodigoMunicipio = temNovedad.getCodigoMunicipio();
        this.modalidadRecaudoAporte = temNovedad.getModalidadRecaudoAporte();
        this.esEmpleadorReintegrable = temNovedad.getEsEmpleadorReintegrable();
        this.esTrabajadorReintegrable = temNovedad.getEsTrabajadorReintegrable();
        this.enProceso = temNovedad.getEnProceso();
    }
    
    /**
     * Método para la conversión de DTO a Entity
     * */
    public TemNovedad convetToEntity(){
        TemNovedad temNovedad = new TemNovedad();
        
        temNovedad.setId(this.getId());
        temNovedad.setIdTransaccion(this.getIdTransaccion());
        temNovedad.setMarcaNovedadSimulado(this.getMarcaNovedadSimulado());
        temNovedad.setMarcaNovedadManual(this.getMarcaNovedadManual());
        temNovedad.setRegistroGeneral(this.getRegistroGeneral());
        temNovedad.setRegistroDetallado(this.getRegistroDetallado());
        temNovedad.setRegistroDetalladoNovedad(this.getRegistroDetalladoNovedad());
        temNovedad.setTipoIdAportante(this.getTipoIdAportante());
        temNovedad.setNumeroIdAportante(this.getNumeroIdAportante());
        temNovedad.setTipoIdCotizante(this.getTipoIdCotizante());
        temNovedad.setNumeroIdCotizante(this.getNumeroIdCotizante());
        temNovedad.setTipoTransaccion(this.getTipoTransaccion());
        temNovedad.setEsIngreso(this.getEsIngreso());
        temNovedad.setEsRetiro(this.getEsRetiro());
        if(this.getFechaInicioNovedad() != null){
            temNovedad.setFechaInicioNovedad(new Date(this.getFechaInicioNovedad()));
        }
        if(this.getFechaFinNovedad() != null){
            temNovedad.setFechaFinNovedad(new Date(this.getFechaFinNovedad()));
        }
        temNovedad.setAccionNovedad(this.getAccionNovedad());
        temNovedad.setMensajeNovedad(this.getMensajeNovedad());
        temNovedad.setTipoCotizante(this.getTipoCotizante());
        temNovedad.setPrimerApellido(this.getPrimerApellido());
        temNovedad.setSegundoApellido(this.getSegundoApellido());
        temNovedad.setPrimerNombre(this.getPrimerNombre());
        temNovedad.setSegundoNombre(this.getSegundoNombre());
        temNovedad.setCodigoDepartamento(this.getCodigoDepartamento());
        temNovedad.setCodigoMunicipio(this.getCodigoMunicipio());
        temNovedad.setModalidadRecaudoAporte(this.getModalidadRecaudoAporte());
        
        temNovedad.setEsEmpleadorReintegrable(this.getEsEmpleadorReintegrable());
        temNovedad.setEsTrabajadorReintegrable(this.getEsTrabajadorReintegrable());
        temNovedad.setEnProceso(this.getEnProceso());
        
        return temNovedad;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the idTransaccion
     */
    public Long getIdTransaccion() {
        return idTransaccion;
    }

    /**
     * @param idTransaccion the idTransaccion to set
     */
    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    /**
     * @return the marcaNovedadSimulado
     */
    public Boolean getMarcaNovedadSimulado() {
        return marcaNovedadSimulado;
    }

    /**
     * @param marcaNovedadSimulado the marcaNovedadSimulado to set
     */
    public void setMarcaNovedadSimulado(Boolean marcaNovedadSimulado) {
        this.marcaNovedadSimulado = marcaNovedadSimulado;
    }

    /**
     * @return the marcaNovedadManual
     */
    public Boolean getMarcaNovedadManual() {
        return marcaNovedadManual;
    }

    /**
     * @param marcaNovedadManual the marcaNovedadManual to set
     */
    public void setMarcaNovedadManual(Boolean marcaNovedadManual) {
        this.marcaNovedadManual = marcaNovedadManual;
    }

    /**
     * @return the registroGeneral
     */
    public Long getRegistroGeneral() {
        return registroGeneral;
    }

    /**
     * @param registroGeneral the registroGeneral to set
     */
    public void setRegistroGeneral(Long registroGeneral) {
        this.registroGeneral = registroGeneral;
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
     * @return the tipoIdAportante
     */
    public String getTipoIdAportante() {
        return tipoIdAportante;
    }

    /**
     * @param tipoIdAportante the tipoIdAportante to set
     */
    public void setTipoIdAportante(String tipoIdAportante) {
        this.tipoIdAportante = tipoIdAportante;
    }

    /**
     * @return the numeroIdAportante
     */
    public String getNumeroIdAportante() {
        return numeroIdAportante;
    }

    /**
     * @param numeroIdAportante the numeroIdAportante to set
     */
    public void setNumeroIdAportante(String numeroIdAportante) {
        this.numeroIdAportante = numeroIdAportante;
    }

    /**
     * @return the tipoIdCotizante
     */
    public String getTipoIdCotizante() {
        return tipoIdCotizante;
    }

    /**
     * @param tipoIdCotizante the tipoIdCotizante to set
     */
    public void setTipoIdCotizante(String tipoIdCotizante) {
        this.tipoIdCotizante = tipoIdCotizante;
    }

    /**
     * @return the numeroIdCotizante
     */
    public String getNumeroIdCotizante() {
        return numeroIdCotizante;
    }

    /**
     * @param numeroIdCotizante the numeroIdCotizante to set
     */
    public void setNumeroIdCotizante(String numeroIdCotizante) {
        this.numeroIdCotizante = numeroIdCotizante;
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
     * @return the esIngreso
     */
    public Boolean getEsIngreso() {
        return esIngreso;
    }

    /**
     * @param esIngreso the esIngreso to set
     */
    public void setEsIngreso(Boolean esIngreso) {
        this.esIngreso = esIngreso;
    }

    /**
     * @return the esRetiro
     */
    public Boolean getEsRetiro() {
        return esRetiro;
    }

    /**
     * @param esRetiro the esRetiro to set
     */
    public void setEsRetiro(Boolean esRetiro) {
        this.esRetiro = esRetiro;
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
     * @return the accionNovedad
     */
    public String getAccionNovedad() {
        return accionNovedad;
    }

    /**
     * @param accionNovedad the accionNovedad to set
     */
    public void setAccionNovedad(String accionNovedad) {
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
     * @return the tipoCotizante
     */
    public String getTipoCotizante() {
        return tipoCotizante;
    }

    /**
     * @param tipoCotizante the tipoCotizante to set
     */
    public void setTipoCotizante(String tipoCotizante) {
        this.tipoCotizante = tipoCotizante;
    }

    /**
     * @return the primerApellido
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * @param primerApellido the primerApellido to set
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * @return the segundoApellido
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * @param segundoApellido the segundoApellido to set
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * @return the primerNombre
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * @param primerNombre the primerNombre to set
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * @return the segundoNombre
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * @param segundoNombre the segundoNombre to set
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * @return the codigoDepartamento
     */
    public Short getCodigoDepartamento() {
        return codigoDepartamento;
    }

    /**
     * @param codigoDepartamento the codigoDepartamento to set
     */
    public void setCodigoDepartamento(Short codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    /**
     * @return the codigoMunicipio
     */
    public Short getCodigoMunicipio() {
        return CodigoMunicipio;
    }

    /**
     * @param codigoMunicipio the codigoMunicipio to set
     */
    public void setCodigoMunicipio(Short codigoMunicipio) {
        CodigoMunicipio = codigoMunicipio;
    }

    /**
     * @return the modalidadRecaudoAporte
     */
    public ModalidadRecaudoAporteEnum getModalidadRecaudoAporte() {
        return modalidadRecaudoAporte;
    }

    /**
     * @param modalidadRecaudoAporte the modalidadRecaudoAporte to set
     */
    public void setModalidadRecaudoAporte(ModalidadRecaudoAporteEnum modalidadRecaudoAporte) {
        this.modalidadRecaudoAporte = modalidadRecaudoAporte;
    }

    /**
     * @return the esEmpleadorReintegrable
     */
    public Boolean getEsEmpleadorReintegrable() {
        return esEmpleadorReintegrable;
    }

    /**
     * @param esEmpleadorReintegrable the esEmpleadorReintegrable to set
     */
    public void setEsEmpleadorReintegrable(Boolean esEmpleadorReintegrable) {
        this.esEmpleadorReintegrable = esEmpleadorReintegrable;
    }

    /**
     * @return the esTrabajadorReintegrable
     */
    public Boolean getEsTrabajadorReintegrable() {
        return esTrabajadorReintegrable;
    }

    /**
     * @param esTrabajadorReintegrable the esTrabajadorReintegrable to set
     */
    public void setEsTrabajadorReintegrable(Boolean esTrabajadorReintegrable) {
        this.esTrabajadorReintegrable = esTrabajadorReintegrable;
    }

    /**
     * @return the registroDetalladoNovedad
     */
    public Long getRegistroDetalladoNovedad() {
        return registroDetalladoNovedad;
    }

    /**
     * @param registroDetalladoNovedad the registroDetalladoNovedad to set
     */
    public void setRegistroDetalladoNovedad(Long registroDetalladoNovedad) {
        this.registroDetalladoNovedad = registroDetalladoNovedad;
    }

	/**
	 * @return the enProceso
	 */
	public Boolean getEnProceso() {
		return enProceso;
	}

	/**
	 * @param enProceso the enProceso to set
	 */
	public void setEnProceso(Boolean enProceso) {
		this.enProceso = enProceso;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TemNovedadModeloDTO [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (idTransaccion != null) {
			builder.append("idTransaccion=");
			builder.append(idTransaccion);
			builder.append(", ");
		}
		if (marcaNovedadSimulado != null) {
			builder.append("marcaNovedadSimulado=");
			builder.append(marcaNovedadSimulado);
			builder.append(", ");
		}
		if (marcaNovedadManual != null) {
			builder.append("marcaNovedadManual=");
			builder.append(marcaNovedadManual);
			builder.append(", ");
		}
		if (modalidadRecaudoAporte != null) {
			builder.append("modalidadRecaudoAporte=");
			builder.append(modalidadRecaudoAporte);
			builder.append(", ");
		}
		if (registroGeneral != null) {
			builder.append("registroGeneral=");
			builder.append(registroGeneral);
			builder.append(", ");
		}
		if (registroDetallado != null) {
			builder.append("registroDetallado=");
			builder.append(registroDetallado);
			builder.append(", ");
		}
		if (tipoIdAportante != null) {
			builder.append("tipoIdAportante=");
			builder.append(tipoIdAportante);
			builder.append(", ");
		}
		if (numeroIdAportante != null) {
			builder.append("numeroIdAportante=");
			builder.append(numeroIdAportante);
			builder.append(", ");
		}
		if (tipoIdCotizante != null) {
			builder.append("tipoIdCotizante=");
			builder.append(tipoIdCotizante);
			builder.append(", ");
		}
		if (numeroIdCotizante != null) {
			builder.append("numeroIdCotizante=");
			builder.append(numeroIdCotizante);
			builder.append(", ");
		}
		if (tipoTransaccion != null) {
			builder.append("tipoTransaccion=");
			builder.append(tipoTransaccion);
			builder.append(", ");
		}
		if (esIngreso != null) {
			builder.append("esIngreso=");
			builder.append(esIngreso);
			builder.append(", ");
		}
		if (esRetiro != null) {
			builder.append("esRetiro=");
			builder.append(esRetiro);
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
		if (tipoCotizante != null) {
			builder.append("tipoCotizante=");
			builder.append(tipoCotizante);
			builder.append(", ");
		}
		if (primerApellido != null) {
			builder.append("primerApellido=");
			builder.append(primerApellido);
			builder.append(", ");
		}
		if (segundoApellido != null) {
			builder.append("segundoApellido=");
			builder.append(segundoApellido);
			builder.append(", ");
		}
		if (primerNombre != null) {
			builder.append("primerNombre=");
			builder.append(primerNombre);
			builder.append(", ");
		}
		if (segundoNombre != null) {
			builder.append("segundoNombre=");
			builder.append(segundoNombre);
			builder.append(", ");
		}
		if (codigoDepartamento != null) {
			builder.append("codigoDepartamento=");
			builder.append(codigoDepartamento);
			builder.append(", ");
		}
		if (CodigoMunicipio != null) {
			builder.append("CodigoMunicipio=");
			builder.append(CodigoMunicipio);
			builder.append(", ");
		}
		if (esEmpleadorReintegrable != null) {
			builder.append("esEmpleadorReintegrable=");
			builder.append(esEmpleadorReintegrable);
			builder.append(", ");
		}
		if (esTrabajadorReintegrable != null) {
			builder.append("esTrabajadorReintegrable=");
			builder.append(esTrabajadorReintegrable);
			builder.append(", ");
		}
		if (registroDetalladoNovedad != null) {
			builder.append("registroDetalladoNovedad=");
			builder.append(registroDetalladoNovedad);
			builder.append(", ");
		}
		if (enProceso != null) {
			builder.append("enProceso=");
			builder.append(enProceso);
		}
		builder.append("]");
		return builder.toString();
	}

}
