package com.asopagos.sat.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAlias; 
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificacionSatDTO {

    private String numero_transaccion;
    private String codigo_novedad;
    private String fecha_creacion;
    private String fecha_vigencia;

    @JsonProperty(value = "Estado_Fujo") 
    private String Estado_Fujo;
    private String url;
    private String estado_afiliacion;
    
    // Constructor vacío
    public NotificacionSatDTO() {
    
    }
    
    // Constructor con todos los atributos
    public NotificacionSatDTO(String numero_transaccion, String codigo_novedad, String fecha_creacion, String fecha_vigencia, String Estado_Fujo, String url) {
        this.numero_transaccion = numero_transaccion;
        this.codigo_novedad = codigo_novedad;
        this.fecha_creacion = fecha_creacion;
        this.fecha_vigencia = fecha_vigencia;
        this.Estado_Fujo = Estado_Fujo; 
        this.url = url;
    }
    
    // Setter y Getter para numero_transaccion
    public void setNumero_transaccion(String numero_transaccion) {
        this.numero_transaccion = numero_transaccion;
    }
    
    public String getNumero_transaccion() {
        return numero_transaccion;
    }
    
    // Setter y Getter para codigo_novedad
    public void setCodigo_novedad(String codigo_novedad) {
        this.codigo_novedad = codigo_novedad;
    }
    
    public String getCodigo_novedad() {
        return codigo_novedad;
    }
    
    // Setter y Getter para fecha_creacion
    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
    
    public String getFecha_creacion() {
        return fecha_creacion;
    }
    
    // Setter y Getter para fecha_vigencia
    public void setFecha_vigencia(String fecha_vigencia) {
        this.fecha_vigencia = fecha_vigencia;
    }
    
    public String getFecha_vigencia() {
        return fecha_vigencia;
    }
    
    // Setter y Getter para estado_flujo
    @JsonProperty(value = "estado_fujo") 
    public void setEstado_Fujo(String estado) {
        if (estado != null) {
            if (estado.equals("estado_fujo")) {
                this.Estado_Fujo = "Estado_Fujo";
            } else {
                this.Estado_Fujo = estado;
            }
        }
    }
    @JsonProperty(value = "Estado_Fujo")
    public String getEstado_Fujo() {
        return Estado_Fujo;
    }
    
    // Setter y Getter para url
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getUrl() {
        return url;
    }

    public void setEstado_afiliacion(String estado_afiliacion) {
        this.estado_afiliacion = estado_afiliacion;
    }
    
    public String getEstado_afiliacion() {
        return estado_afiliacion;
    }
}
