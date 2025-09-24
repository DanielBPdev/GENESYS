package com.asopagos.entidaddescuento.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.UbicacionModeloDTO;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.subsidiomonetario.entidadDescuento.EntidadDescuento;
import com.asopagos.enumeraciones.subsidiomonetario.entidadDescuento.EstadoEntidadDescuentoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.entidadDescuento.TipoEntidadDescuentoEnum;

/**
 * <b>Descripción:</b> DTO que contiene los datos solicitados para realizar la gestión de entidades de descuento
 * 
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 * author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@XmlRootElement
public class EntidadDescuentoModeloDTO extends EmpresaModeloDTO implements Serializable {

    /**
     * Identificador de la versión
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la entidad de descuento
     */
    private Long idEntidadDescuento;

    /**
     * Código de la entidad de descuento
     */
    private String codigo;
    
    /**
     * Nombre del contacto de la entidad de descuento
     */
    private String nombreContacto;
    
    /**
     * Prioridad que tiene la entidad de descuento
     */
    private String prioridad;

    /**
     * Observaciones sobre la entidad de descuento
     */
    private String observaciones;

    /**
     * Tipo de entidad de descuento
     */
    private TipoEntidadDescuentoEnum tipoEntidad;

    /**
     * Estado de la entidad de descuento
     */
    private EstadoEntidadDescuentoEnum estadoEntidad;
    
    /**
     * Fecha de creación de la entidad de descuento
     */
    private Date fechaCreacion;
    
    /**
     * Nombre de la entidad de descuento de tipo Interna,
     * Cuando es externa este campo permanecera nulo
     */
    private String nombreEntidad;
    
    /**
     * Número de telefono celular de una entidad de descuento tipo interna,
     * cuando es externa este campo permanecera nulo.
     */
    private String numeroCelular;
    
    
    /**
     * Constructor vacío de la clase.
     */
    public EntidadDescuentoModeloDTO (){
        
    }    
    
    /**
     * Metodo que convierte una Entidad a un DTO.
     * @param entidadDescuento Variable que contiene los atributos de la entidad de descuento.
     * @param tipoIdentificacionEnum Variable que contiene el tipo de identificicación de la entidad de descuento.
     * @param ubicacion Variable que contiene el valor de la Ubicación de la entidad de descuento.
     */
    public void convertToDTO(EntidadDescuento entidadDescuento){     
      
        
        this.setIdEntidadDescuento(entidadDescuento.getIdEntidadDescuento());
        this.setCodigo(entidadDescuento.getCodigo());
        this.setNombreContacto(entidadDescuento.getNombreContacto());
        this.setPrioridad(String.format("%02d", entidadDescuento.getPrioridad()));
        this.setObservaciones(entidadDescuento.getObservaciones());
        this.setEstadoEntidad(entidadDescuento.getEstado());
        this.setTipoEntidad(entidadDescuento.getTipo());        
        this.setFechaCreacion(entidadDescuento.getFechaCreacion());
        this.setNumeroCelular(entidadDescuento.getNumeroCelularInterna());
        
        if((entidadDescuento.getNombreEntidad()!=null) && (entidadDescuento.getTipo().equals(TipoEntidadDescuentoEnum.INTERNA))){ 
            this.setNombreEntidad(entidadDescuento.getNombreEntidad());
        }
    }
    
    /**
     * Constructor del DTO. Crea un DTO apartir de la información
     * de la entidad de descuento sin la emmpresa
     * @param entidadDescuento. Variable que contiene la información de las entidades de descuento Internas
     */
    public EntidadDescuentoModeloDTO (EntidadDescuento entidadDescuento){
         this.convertToDTO(entidadDescuento);
    }
    
    /**
     * Constructor del DTO. Crea un DTO apartir de la información de la entidad de descuento
     * y la empresa.
     * 
     * @param entidadDescuento Variable que contiene los datos de la entidad de descuento.
     * @param empresa Variable que contiene los datos de la empresa.
     */
    public EntidadDescuentoModeloDTO(EntidadDescuento entidadDescuento, Empresa empresa, Ubicacion ubicacion){
        
        this.convertToDTO(entidadDescuento);
        super.convertToDTO(empresa);
        UbicacionModeloDTO ubicacionModeloDTO = new UbicacionModeloDTO();
        ubicacionModeloDTO.convertToDTO(ubicacion);
        setUbicacionModeloDTO(ubicacionModeloDTO);
    }
    
    /**
     * 
     * Metodo que convierte un DTO a un entity
     * 
     * @return entity de entidad de descuento.
     */
    public EntidadDescuento convertToEntity() {

        EntidadDescuento entidadDescuento = new EntidadDescuento();

        if (this.getCodigo() != null && !this.getCodigo().equals("") && !this.getCodigo().equals("null"))
            entidadDescuento.setCodigo(Long.parseLong(this.getCodigo()));
        entidadDescuento.setTipo(this.getTipoEntidad());
        entidadDescuento.setIdEmpresa(this.getIdEmpresa());
        if (this.getPrioridad() != null && !this.getPrioridad().equals("") && !this.getPrioridad().equals("null"))
            entidadDescuento.setPrioridad(Integer.parseInt(this.getPrioridad()));
        entidadDescuento.setEstado(this.getEstadoEntidad());
        entidadDescuento.setNombreContacto(this.getNombreContacto());
        entidadDescuento.setObservaciones(this.getObservaciones());
        entidadDescuento.setFechaCreacion(this.getFechaCreacion());
        entidadDescuento.setNumeroCelularInterna(this.getNumeroCelular());

        if (this.getTipoEntidad().equals(TipoEntidadDescuentoEnum.INTERNA)) {
            entidadDescuento.setNombreEntidad(this.getNombreEntidad());
        }

        return entidadDescuento;
    }
    

    /**
     * Método que permite obtener el identificador
     * @return valor del identificador
     */
    public Long getIdEntidadDescuento() {
        return idEntidadDescuento;
    }

    /**
     * Método que permite establecer el identificador
     * @param idEntidadDescuento valor del identificador
     */
    public void setIdEntidadDescuento(Long idEntidadDescuento) {
        this.idEntidadDescuento = idEntidadDescuento;
    }

    /**
     * Método que permite obtener el código
     * @return valor del código
     */
    public String getCodigo() {
        return codigo;
    }
    
    /**
     *Método que permite establecer el código
     * @param codigo valor del código
     */
    public void setCodigo(Long codigo) {
        this.codigo = String.format("%04d", codigo);
    }   
       
    /**
     * Metodo que permite obtener el nombre de la entidad de descuento 
     * (aplica a la entidad de descuento tipo interna)
     * @return el nombre de la entidad de descuento
     */
    public String getNombreEntidad() {
        return nombreEntidad;
    }
    
    /**
     * Metodo que permite establecer el nombre de la entidad de descuento
     * @param nuevo nombre de la entidad de descuento.
     */
    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }


    /**
     * Metodo que permite obtener la prioridad.
     * @return valor de la prioridad.
     */
    public String getPrioridad() {
        return prioridad;
    }

    /**
     * Metodo que permite establecer el valor de la prioridad.
     * @param valor de la prioridad a establecer.
     */
    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }


    /**
     * Método que permite obtener el nombre de contacto
     * @return valor del nombre de contacto
     */
    public String getNombreContacto() {
        return nombreContacto;
    }

    /**
     * Método que permite establecer el nombre de contacto
     * @param nombreContacto valor del nombre de contacto
     */
    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    /**
     * Método que permite obtener las observaciones
     * @return valor de las observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Método que permite establecer las observaciones
     * @param observaciones valor de las observaciones
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Método que permite obtener el tipo de entidad
     * @return valor del tipo de entidad
     */
    public TipoEntidadDescuentoEnum getTipoEntidad() {
        return tipoEntidad;
    }

    /**
     * Método que permite establecer el tipo de entidad
     * @param tipoEntidad valor del tipo de entidad
     */
    public void setTipoEntidad(TipoEntidadDescuentoEnum tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }
    
    /**
     * Método que permite obtener el estado de la entidad
     * @return valor del estado de la entidad
     */
    public EstadoEntidadDescuentoEnum getEstadoEntidad() {
        return estadoEntidad;
    }
    
    /**
     * Método que permite establecer el estado de la entidad
     * @param estadoEntidad valor del estado de la entidad
     */
    public void setEstadoEntidad(EstadoEntidadDescuentoEnum estadoEntidad) {
        this.estadoEntidad = estadoEntidad;
    }


    /**
     * metodo que permite obtener la fecha de creación de la entidad de descuento.
     * @return the fechaCreacion valor de la fecha de creación.
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }


    /**
     * Metodo que permite establecer el valor de la fecha de creación.
     * @param fechaCreacion nuevo valor de la fecha de creación
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the numeroCelular
     */
    public String getNumeroCelular() {
        return numeroCelular;
    }

    /**
     * @param numeroCelular the numeroCelular to set
     */
    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "EntidadDescuentoModeloDTO{" +
                "idEntidadDescuento=" + idEntidadDescuento +
                ", codigo='" + codigo + '\'' +
                ", nombreContacto='" + nombreContacto + '\'' +
                ", prioridad='" + prioridad + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", tipoEntidad=" + tipoEntidad +
                ", estadoEntidad=" + estadoEntidad +
                ", fechaCreacion=" + fechaCreacion +
                ", nombreEntidad='" + nombreEntidad + '\'' +
                ", numeroCelular='" + numeroCelular + '\'' +
                '}';
    }
}
